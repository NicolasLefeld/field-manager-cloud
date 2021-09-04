package com.example.service;

import com.example.model.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Sparkler on 09/11/2017.
 */
public class StandarQueries {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public StandarQueries(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Cliente getClienteByNroCliente(String nroCliente){
        CriteriaQuery<Cliente> cq = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = cq.from(Cliente.class);

        cq.select(
                root
        ).where(
                criteriaBuilder.equal(root.get("nroCliente"),nroCliente)
        );

        List<Cliente> resultList = entityManager.createQuery(cq).setMaxResults(1).getResultList();
        if (resultList.size()<1) return null;

        return resultList.get(0);
    }

    public Licencia getLicenciaByNroLicencia(String nroLicencia, Cliente cliente, Boolean banned){
        CriteriaQuery<Licencia> cq = criteriaBuilder.createQuery(Licencia.class);
        Root<Licencia> root = cq.from(Licencia.class);

        Predicate precicate;

        if (banned==null){
            precicate = criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("nroLicencia"), nroLicencia),
                    criteriaBuilder.equal(root.get("cliente"), cliente)
            );
        }else{
            precicate = criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("nroLicencia"), nroLicencia),
                    criteriaBuilder.equal(root.get("cliente"), cliente),
                    criteriaBuilder.equal(root.get("banned"), banned)
            );
        }

        cq.select(root).where(precicate);

        List<Licencia> resultList = entityManager.createQuery(cq).setMaxResults(1).getResultList();
        if (resultList.size()<1) return null;

        return resultList.get(0);

    }

    public List<Periodo> getPeriodos(Licencia licencia, Date hoy){
        CriteriaQuery<Periodo> cq = criteriaBuilder.createQuery(Periodo.class);
        Root<Periodo> root = cq.from(Periodo.class);

        Path<Object> hasta = root.get("hasta");

        cq.select(
                root
        ).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("licencia"),licencia),
                        criteriaBuilder.lessThanOrEqualTo(root.<Date>get("desde"), hoy),
                        criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("hasta"), hoy)
                )

        ).orderBy(criteriaBuilder.desc(hasta));
        return entityManager.createQuery(cq).getResultList();
    }

    public Activacion getLastActivacion(Licencia licencia){
        CriteriaQuery<Activacion> cq = criteriaBuilder.createQuery(Activacion.class);
        Root<Activacion> root = cq.from(Activacion.class);

        cq.select(
                root
        ).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("licencia"),licencia),
                        criteriaBuilder.equal(root.get("actived"), Activacion.ACTIVATED_ACTIVADO)
                )

        ).orderBy(
                criteriaBuilder.desc(root.get("cuando"))
        );

        List<Activacion> resultList = entityManager.createQuery(cq).setMaxResults(1).getResultList();
        if (resultList.size()<1) return null;

        return resultList.get(0);
    }

    public Dispositivo verifyDispositivo(Dispositivo dispositivo){
        CriteriaQuery<Dispositivo> cq = criteriaBuilder.createQuery(Dispositivo.class);
        Root<Dispositivo> root = cq.from(Dispositivo.class);

        cq.select(
                root
        ).where(
                        criteriaBuilder.equal(root.get("hadwareId"),dispositivo.getHadwareId())
        ).orderBy(
                criteriaBuilder.desc(root.get("creacion"))
        );

        List<Dispositivo> resultList = entityManager.createQuery(cq).setMaxResults(1).getResultList();
        if (resultList.size()>0 && dispositivo.equals(resultList.get(0)))
            return resultList.get(0);

        dispositivo.setCreacion(new Date());
        entityManager.persist(dispositivo);
        return dispositivo;

    }
}
