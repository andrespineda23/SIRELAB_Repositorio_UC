/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.AdministradorEdificioDAOInterface;
import com.sirelab.entidades.AdministradorEdificio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class AdministradorEdificioDAO implements AdministradorEdificioDAOInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearAdministradorEdificio(AdministradorEdificio administradoredificio) {
        try {
            em.persist(administradoredificio);
        } catch (Exception e) {
            System.out.println("Error crearAdministradorEdificio AdministradorEdificioDAO : " + e.toString());
        }
    }

    @Override
    public void editarAdministradorEdificio(AdministradorEdificio administradoredificio) {
        try {
            em.merge(administradoredificio);
        } catch (Exception e) {
            System.out.println("Error editarAdministradorEdificio AdministradorEdificioDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarAdministradorEdificio(AdministradorEdificio administradoredificio) {
        try {
            em.remove(em.merge(administradoredificio));
        } catch (Exception e) {
            System.out.println("Error eliminarAdministradorEdificio AdministradorEdificioDAO : " + e.toString());
        }
    }

    @Override
    public List<AdministradorEdificio> consultarAdministradoresEdificio() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AdministradorEdificio p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<AdministradorEdificio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarAdministradorEdificios AdministradorEdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public AdministradorEdificio buscarAdministradorEdificioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AdministradorEdificio p WHERE p.idadministradoredificio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            AdministradorEdificio registro = (AdministradorEdificio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarAdministradorEdificioPorID AdministradorEdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public AdministradorEdificio buscarAdministradorEdificioPorIDPersona(BigInteger idPersona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AdministradorEdificio p WHERE p.persona.idpersona=:idPersona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idPersona", idPersona);
            AdministradorEdificio registro = (AdministradorEdificio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarAdministradorEdificioPorIDPersona AdministradorEdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public AdministradorEdificio buscarAdministradorEdificioPorPorCorreoNumDocumento(String correo, String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AdministradorEdificio p WHERE p.persona.emailpersona=:correo AND p.persona.identificacionpersona=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            query.setParameter("documento", documento);
            AdministradorEdificio registro = (AdministradorEdificio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarAdministradorEdificioPorPorCorreoNumDocumento AdministradorEdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public AdministradorEdificio buscarAdministradorEdificioPorPorCorreo(String correo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AdministradorEdificio p WHERE p.persona.emailpersona=:correo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            AdministradorEdificio registro = (AdministradorEdificio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarAdministradorEdificioPorPorCorreo AdministradorEdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public AdministradorEdificio buscarAdministradorEdificioPorPorDocumento(String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AdministradorEdificio p WHERE p.persona.identificacionpersona=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("documento", documento);
            AdministradorEdificio registro = (AdministradorEdificio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarAdministradorEdificioPorPorDocumento AdministradorEdificioDAO : " + e.toString());
            return null;
        }
    }

    
    
    @Override
    public AdministradorEdificio obtenerUltimaAdministradorEdificioRegistrada() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AdministradorEdificio p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<AdministradorEdificio> registros = query.getResultList();
            if (registros != null) {
                int tam = registros.size();
                AdministradorEdificio ultimoRegistro = registros.get(tam - 1);
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error obtenerUltimaAdministradorEdificioRegistrada AdministradorEdificioDAO : " + e.toString());
            return null;
        }
    }
}
