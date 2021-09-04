package com.example.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Sparkler on 08/11/2017.
 */
@Entity
public class Reporte {

    @Id
    @GeneratedValue
    private Integer id;

    private Date cuando;

    @OneToOne
    private Dispositivo dispositivo;
    private Integer ip;
    private Integer accion;

    @ManyToOne
    private Licencia licencia;

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

    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    public Integer getAccion() {
        return accion;
    }

    public void setAccion(Integer accion) {
        this.accion = accion;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }
}
