package com.example.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Sparkler on 08/11/2017.
 */
@Entity
public class Activacion {

    public static final int ACTIVATED_ERROR = 0;
    public static final int ACTIVATED_ACTIVADO = 1;

    @Id
    @GeneratedValue
    private Integer id;

    private Date cuando;
    private int actived;
    private String ip;

    @OneToOne
    private Dispositivo dispositivo;

    @ManyToOne
    private Licencia licencia;

    public Activacion() {
    }

    public Activacion(Date cuando, int actived, String ip, Dispositivo dispositivo) {
        this.cuando = cuando;
        this.actived = actived;
        this.ip = ip;
        this.dispositivo = dispositivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCuando() {
        return cuando;
    }

    public void setCuando(Date cuando) {
        this.cuando = cuando;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public int getActived() {
        return actived;
    }

    public void setActived(int actived) {
        this.actived = actived;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }
}
