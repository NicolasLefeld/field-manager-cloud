package com.example.signatures;

import com.example.dtos.JSONLic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Sparkler on 05/09/2017.
 */
public class LicenseManager {

    //private static final String PROVIDER = "BC";
    private static final String EOL = System.getProperty("line.separator");
    private static final String LICENSE_BEGIN = "----- BEGIN LICENSE -----";
    private static final String LICENSE_END = "----- END LICENSE -----";
    private static final String SIGNATURE_BEGIN = "----- BEGIN SIGNATURE -----";
    private static final String SIGNATURE_END = "----- END SIGNATURE -----";
    private static final int SIGNATURE_LINE_LENGTH = 20;

    public static final String GSON_DATE_FORMAT = "yyyy-MM-dd";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String[] generateKeys() {
        PrivateKey privateKey;
        PublicKey publicKey;

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();

            String[] ret = new String[2];

            if (privateKey==null) throw new RuntimeException("Llave privada no generada");
            byte[] encodedPrivateKey = privateKey.getEncoded();
            ret[0] = String.valueOf(Base64Coder.encode(encodedPrivateKey));

            if (publicKey==null) throw new RuntimeException("Llave publica no generada");
            byte[] encodedPublicKey = publicKey.getEncoded();
            ret[1] = String.valueOf(Base64Coder.encode(encodedPublicKey));

            return ret;

        } catch (Exception ex) {
            throw new RuntimeException("Error al generar la llave", ex);
        }
    }

    public static String encodeData(JSONLic data, String privateKeyB64){
        Gson gson = new GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create();

        return encodeData(gson.toJson(data),privateKeyB64);
    }

    private static String encodeData(String data, String privateKeyB64){
        PrivateKey privateKey;
        try {
            privateKey = KeyFactory.getInstance("DSA")
                    .generatePrivate(
                            new PKCS8EncodedKeySpec(Base64Coder.decode(privateKeyB64))
                    );
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Error al leer la llave", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al leer la llave", e);
        }

        Signature signature = createSignatureSign();
        try {
            signature.initSign(privateKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Error al leer la llave", e);
        }

        BufferedReader sourceReader = new BufferedReader(new StringReader(data));
        String licenseText;

        try {
            boolean isLicense = true;
            String line;

            licenseText = "";

            while (isLicense && (line = sourceReader.readLine())!=null) {
                if (!line.equals(LICENSE_END)) {
                    licenseText += line + EOL;
                    signature.update(line.getBytes(), 0, line.getBytes().length);
                } else {
                    isLicense = false;
                }
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error al procesar archivo", ex);
        }

        Writer output = new StringWriter();

        try {
            char[] signatureString = Base64Coder.encode(signature.sign());

            output.write(LICENSE_BEGIN);
            output.write(EOL + licenseText);
            output.write(EOL + LICENSE_END);

            output.write(EOL + SIGNATURE_BEGIN + EOL);

            for (int i = 0; i < signatureString.length; i = i + SIGNATURE_LINE_LENGTH) {
                output.write(signatureString, i, Math.min(signatureString.length - i, SIGNATURE_LINE_LENGTH));
                if (signatureString.length - i > SIGNATURE_LINE_LENGTH) {
                    output.write(EOL);
                }
            }

            output.write(EOL + SIGNATURE_END);
        } catch (Exception ex) {
            throw new RuntimeException("Error al agregar firma", ex);
        }

        return output.toString();
    }

    public static JSONLic decodeData(String data, String publicKeyB64){

        //Reconstruir la public key desde base64
        PublicKey publicKey;
        try {
            publicKey = KeyFactory.getInstance("DSA").generatePublic(new X509EncodedKeySpec(Base64Coder.decode(publicKeyB64)));
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Error al leer la llave", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al leer la llave", e);
        }

        //Inicializar signature
        Signature signature = createSignatureSign();
        try {
            signature.initVerify(publicKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Error al leer la llave", e);
        }

        //Extraer datos y calcular la firma
        BufferedReader sourceReader = new BufferedReader(new StringReader(data));
        StringBuffer output = new StringBuffer();
        byte[] firma;

        try {
            boolean isLicense = true;
            String line;
            int index = 0;

            isLicense = false;

            while (!isLicense && (line = sourceReader.readLine())!=null) {
                if (line.equals(LICENSE_BEGIN)) {
                    isLicense = true;
                }
            }

            while (isLicense && (line = sourceReader.readLine())!=null) {

                if (!line.equals(LICENSE_END)) {
                    signature.update(line.getBytes(), 0, line.getBytes().length);

                    output.append(line);
                    output.append(EOL);
                } else {
                    isLicense = false;
                }
            }

            //Extraer la firma y convertir desde base64

            boolean isSignature = false;
            String signatureString = "";

            while (!isSignature && (line = sourceReader.readLine())!=null) {
                if (line.equals(SIGNATURE_BEGIN)) {
                    isSignature = true;
                }
            }

            while (isSignature && (line = sourceReader.readLine())!=null) {
                if (line.equals(SIGNATURE_END)) {
                    isSignature = false;
                } else {
                    signatureString += line;
                }
            }

            firma = Base64Coder.decode(signatureString);
        } catch (Exception ex) {
            throw new RuntimeException("Error extrael la info", ex);
        }

        try {
            if (signature.verify(firma)){
                Gson gson = new GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create();
                return gson.fromJson(output.toString(), JSONLic.class);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Firma incorrecta", ex);
        }

        return null;
    }


    private static Signature createSignatureSign() {
        try {
            return Signature.getInstance("SHA1withDSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}