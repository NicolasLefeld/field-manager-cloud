package com.example.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Sparkler on 08/11/2017.
 */
@Entity
public class Cliente {

    @Id
    @GeneratedValue
    private Integer id;

    private String nombre;
    private String nroCliente;
    private String publicKey;
    private String privateKey;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<Licencia> licencias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNroCliente() {
        return nroCliente;
    }

    public void setNroCliente(String nroCliente) {
        this.nroCliente = nroCliente;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Set<Licencia> getLicencias() {
        return licencias;
    }

    public void setLicencias(Set<Licencia> licencias) {
        this.licencias = licencias;
    }

}
