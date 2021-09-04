package com.example.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Sparkler on 08/11/2017.
 */
@Entity
public class Licencia {

    @Id
    @GeneratedValue
    private Integer id;

    private String nroLicencia;
    private boolean banned;

    @OneToMany(mappedBy = "licencia", cascade = CascadeType.ALL)
    private Set<Periodo> periodos;

    @OneToMany(mappedBy = "licencia", cascade = CascadeType.ALL)
    private Set<Activacion> activacions;

    @OneToMany(mappedBy = "licencia", cascade = CascadeType.ALL)
    private Set<Reporte> reportes;

    @ManyToOne
    private Cliente cliente;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNroLicencia() {
        return nroLicencia;
    }

    public void setNroLicencia(String nroLicencia) {
        this.nroLicencia = nroLicencia;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public Set<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Set<Periodo> periodos) {
        this.periodos = periodos;
    }

    public Set<Activacion> getActivacions() {
        return activacions;
    }

    public void setActivacions(Set<Activacion> activacions) {
        this.activacions = activacions;
    }

    public Set<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(Set<Reporte> reportes) {
        this.reportes = reportes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
