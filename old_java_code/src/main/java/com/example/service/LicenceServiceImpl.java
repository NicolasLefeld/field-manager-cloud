package com.example.service;

import com.example.dtos.JSONLic;
import com.example.dtos.JSONLicRequest;
import com.example.dtos.JSONLicResponse;
import com.example.model.*;
import com.example.signatures.LicenseManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Created by Sparkler on 08/11/2017.
 */
@Service
public class LicenceServiceImpl implements LicenceService {

    public static final String GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'Z";

    @PersistenceContext
    EntityManager em;

    @Transactional
    public String activate(String data, String ip){

        Date now = new Date();

        Gson gson = new GsonBuilder().setDateFormat(GSON_DATE_FORMAT).create();

        JSONLicRequest jsonLicRequest = gson.fromJson(data,JSONLicRequest.class);

        //TODO validar fecha dispositivo
        StandarQueries standarQueries = new StandarQueries(em);

        Dispositivo dispositivo = new Dispositivo(jsonLicRequest.getDevice_id(), jsonLicRequest.getUsers(), jsonLicRequest.getVersion(), jsonLicRequest.getModel(), jsonLicRequest.getLanguage(), jsonLicRequest.getExtras());
        dispositivo = standarQueries.verifyDispositivo(dispositivo);
        Activacion activacion = new Activacion(now, Activacion.ACTIVATED_ERROR, ip, dispositivo);

        JSONLicResponse response = null;

        Cliente cliente = standarQueries.getClienteByNroCliente(jsonLicRequest.getClient_number().trim().toLowerCase());


        if (cliente != null){
            Licencia licencia = standarQueries.getLicenciaByNroLicencia(jsonLicRequest.getLicence_number().trim().toLowerCase(), cliente, false);

            if (licencia!=null){
                activacion.setLicencia(licencia);

                List<Periodo> periodos = standarQueries.getPeriodos(licencia, now);
                Periodo periodo = null;
                if (periodos != null && periodos.size() > 0 && ((periodo = periodos.get(0)) != null)){
                    Activacion lastActivacion = standarQueries.getLastActivacion(licencia);
                    if (lastActivacion == null || lastActivacion.getDispositivo().getHadwareId().equals(jsonLicRequest.getDevice_id())){
                        JSONLic jsonLic = new JSONLic(jsonLicRequest.getDevice_id(),cliente.getNombre(), jsonLicRequest.getLicence_number(), jsonLicRequest.getClient_number(),periodo.getHasta(), periodo.getDesde());
                        String encLic = LicenseManager.encodeData(jsonLic, cliente.getPrivateKey());
                        LicenseManager.decodeData(encLic, cliente.getPublicKey());
                        String[] lics = new String[]{encLic};
                        response = new JSONLicResponse(JSONLicResponse.LICENCE_RESPONSE_ACTIVATED, "TestLic", lics);
                        activacion.setActived(Activacion.ACTIVATED_ACTIVADO);
                    }else{
                        //activado para otro dispositivo
                        response = new JSONLicResponse(JSONLicResponse.LICENCE_RESPONSE_WRONG_HARDWARE, null, null);
                        // activacion.actived =
                    }
                }else{
                    //licencia vencida
                    response = new JSONLicResponse(JSONLicResponse.LICENCE_RESPONSE_EXPIRED, null, null);
                    //activacion.actived =
                }
            }else{
                //licencia inexistente o baneada
                response = new JSONLicResponse(JSONLicResponse.LICENCE_RESPONSE_INVALID_LICENCE, null, null);
                //activacion.actived =
            }
        }else {
            //cliente inexistente
            response = new JSONLicResponse(JSONLicResponse.LICENCE_RESPONSE_INVALID_CLIENT, null, null);
            //activacion.actived =
        }

        if (response == null) response = new JSONLicResponse(JSONLicResponse.LICENCE_RESPONSE_UNKNOWN, null, null);
        em.persist(activacion);
        return gson.toJson(response);
    }
}
