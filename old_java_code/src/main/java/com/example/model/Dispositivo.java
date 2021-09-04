package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Sparkler on 08/11/2017.
 */
@Entity
public class Dispositivo {

    @Id
    @GeneratedValue
    private Integer id;

    private String hadwareId;
    private String users;
    private String fmversion;
    private String model;
    private String language;
    private String extras;
    private Date creacion;

    public Dispositivo() {
    }

    public Dispositivo(String hadwareId, String[] users, String fmversion, String model, String language, String extras) {
        this.hadwareId = hadwareId;
        setUsers(users);
        this.fmversion = fmversion;
        this.model = model;
        this.language = language;
        this.extras = extras;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHadwareId() {
        return hadwareId;
    }

    public void setHadwareId(String hadwareId) {
        this.hadwareId = hadwareId;
    }

    public String[] getUsers() {
        return users.split(",");
    }

    public void setUsers(String[] users) {
        StringBuilder nameBuilder = new StringBuilder();
        for (String n : users)
            nameBuilder.append(n).append(",");
        this.users = nameBuilder.toString();
    }

    public String getFmversion() {
        return fmversion;
    }

    public void setFmversion(String fmversion) {
        this.fmversion = fmversion;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dispositivo that = (Dispositivo) o;

        if (!hadwareId.equals(that.hadwareId)) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        if (fmversion != null ? !fmversion.equals(that.fmversion) : that.fmversion != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (extras != null ? !extras.equals(that.extras) : that.extras != null) return false;
        return language != null ? language.equals(that.language) : that.language == null;
    }

}
