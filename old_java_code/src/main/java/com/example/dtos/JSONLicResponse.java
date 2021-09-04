package com.example.dtos;

/**
 * Created by Sparkler on 15/09/2017.
 */

public class JSONLicResponse {

    public static final String LICENCE_RESPONSE_ACTIVATED = "activated";
    public static final String LICENCE_RESPONSE_WRONG_HARDWARE = "wrong_hardware";
    public static final String LICENCE_RESPONSE_EXPIRED = "expired";
    public static final String LICENCE_RESPONSE_INVALID_LICENCE = "invalid_licence";
    public static final String LICENCE_RESPONSE_INVALID_CLIENT = "invalid_client";
    public static final String LICENCE_RESPONSE_WRONG_DATE = "wrong_date";
    public static final String LICENCE_RESPONSE_UNKNOWN = "unknown";

    private String respuesta;
    private String userMessage;
    private String[] jsonLicCryp;

    public JSONLicResponse(String respuesta, String userMessage, String[] jsonLicCryp) {
        this.respuesta = respuesta;
        this.userMessage = userMessage;
        this.jsonLicCryp = jsonLicCryp;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String[] getJsonLicCryp() {
        return jsonLicCryp;
    }

    public void setJsonLicCryp(String[] jsonLicCryp) {
        this.jsonLicCryp = jsonLicCryp;
    }
}
