/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.PersonaContactoDAOInterface;
import com.sirelab.entidades.PersonaContacto;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class PersonaContactoDAO implements PersonaContactoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearPersonaContacto(PersonaContacto personacontacto) {
        try {
            em.persist(personacontacto);
        } catch (Exception e) {
            System.out.println("Error crearPersonaContacto PersonaContactoDAO : " + e.toString());
        }
    }

    @Override
    public void editarPersonaContacto(PersonaContacto personacontacto) {
        try {
            em.merge(personacontacto);
        } catch (Exception e) {
            System.out.println("Error editarPersonaContacto PersonaContactoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarPersonaContacto(PersonaContacto personacontacto) {
        try {
            em.remove(em.merge(personacontacto));
        } catch (Exception e) {
            System.out.println("Error eliminarPersonaContacto PersonaContactoDAO : " + e.toString());
        }
    }

    @Override
    public List<PersonaContacto> consultarPersonasContacto() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PersonaContacto> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarPersonasContacto PersonaContactoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public PersonaContacto buscarPersonaContactoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p WHERE p.idpersonacontacto=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            PersonaContacto registro = (PersonaContacto) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarPersonaContactoPorID PersonaContactoDAO : " + e.toString());
            return null;
        }
    }
    
    @Override
    public PersonaContacto buscarPersonaContactoPorUsuario(String usuario) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p WHERE p.nombreusuario=:usuario");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("usuario", usuario);
            PersonaContacto registro = (PersonaContacto) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarPersonaContactoPorUsuario PersonaContactoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PersonaContacto> consultarPersonasContactoPorConvenioEntidad(BigInteger convenioentidad) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p WHERE p.convenioporentidad.idconvenioporentidad=:convenioentidad ORDER BY p.nombre ASC");
            query.setParameter("convenioentidad", convenioentidad);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PersonaContacto> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarPersonasContactoPorConvenioEntidad PersonaContactoDAO : " + e.toString());
            return null;
        }
    }
}
