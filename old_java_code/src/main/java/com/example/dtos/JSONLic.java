package com.example.dtos;

import java.util.Date;

/**
 * Created by Sparkler on 15/09/2017.
 */

public class JSONLic {
    String device_id;
    String client_name;
    String licence_number;
    String client_number;
    Date expiration_date;
    Date start_date;

    public JSONLic(String device_id, String client_name, String licence_number, String client_number, Date expiration_date, Date start_date) {
        this.device_id = device_id;
        this.client_name = client_name;
        this.licence_number = licence_number;
        this.client_number = client_number;
        this.expiration_date = expiration_date;
        this.start_date = start_date;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getLicence_number() {
        return licence_number;
    }

    public void setLicence_number(String licence_number) {
        this.licence_number = licence_number;
    }

    public String getClient_number() {
        return client_number;
    }

    public void setClient_number(String client_number) {
        this.client_number = client_number;
    }
}
