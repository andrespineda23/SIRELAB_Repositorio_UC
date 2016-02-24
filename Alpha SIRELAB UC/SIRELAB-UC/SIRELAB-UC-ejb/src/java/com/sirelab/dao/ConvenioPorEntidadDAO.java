/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.entidades.ConvenioPorEntidad;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class ConvenioPorEntidadDAO implements ConvenioPorEntidadDAOInterface {

    static Logger logger = Logger.getLogger(ConvenioPorEntidadDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearConvenioPorEntidad(ConvenioPorEntidad convenio) {
        try {
            em.persist(convenio);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearConvenioPorEntidad ConvenioPorEntidadDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarConvenioPorEntidad(ConvenioPorEntidad convenio) {
        try {
            em.merge(convenio);
        } catch (Exception e) {
            logger.error("Error editarConvenioPorEntidad ConvenioPorEntidadDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarConvenioPorEntidad(ConvenioPorEntidad convenio) {
        try {
            em.remove(em.merge(convenio));
        } catch (Exception e) {
            logger.error("Error eliminarConvenioPorEntidad ConvenioPorEntidadDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<ConvenioPorEntidad> consultarConveniosPorEntidad() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ConvenioPorEntidad p ORDER BY p.entidadexterna.identidadexterna ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ConvenioPorEntidad> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarConveniosPorEntidad ConvenioPorEntidadDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public ConvenioPorEntidad buscarConvenioPorEntidadPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ConvenioPorEntidad p WHERE p.idconvenioporentidad=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            ConvenioPorEntidad registro = (ConvenioPorEntidad) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarConvenioPorEntidadPorID ConvenioPorEntidadDAO : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public ConvenioPorEntidad buscarConvenioPorEntidadPorParametros(BigInteger entidad, BigInteger convenio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ConvenioPorEntidad p WHERE p.entidadexterna.identidadexterna=:entidad AND p.convenio.idconvenio=:convenio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("entidad", entidad);
            query.setParameter("convenio", convenio);
            ConvenioPorEntidad registro = (ConvenioPorEntidad) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarConvenioPorEntidadPorParametros ConvenioPorEntidadDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> consultarConveniosPorEntidadPorEntidad(BigInteger entidad) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ConvenioPorEntidad p WHERE p.entidadexterna.identidadexterna=:entidad ORDER BY p.entidadexterna.identidadexterna ASC");
            query.setParameter("entidad", entidad);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ConvenioPorEntidad> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarConveniosPorEntidadPorEntidad ConvenioPorEntidadDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> consultarConveniosPorEntidadPorConvenio(BigInteger convenio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ConvenioPorEntidad p WHERE p.convenio.idconvenio=:convenio ORDER BY p.entidadexterna.identidadexterna ASC");
            query.setParameter("convenio", convenio);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ConvenioPorEntidad> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarConveniosPorEntidadPorConvenio ConvenioPorEntidadDAO : " + e.toString(),e);
            return null;
        }
    }
}
