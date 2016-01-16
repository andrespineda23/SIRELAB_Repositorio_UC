/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.EstadoEquipoDAOInterface;
import com.sirelab.entidades.EstadoEquipo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateless
public class EstadoEquipoDAO implements EstadoEquipoDAOInterface {

    static Logger logger = Logger.getLogger(EstadoEquipoDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearEstadoEquipo(EstadoEquipo estadoEquipo) {
        try {
            em.persist(estadoEquipo);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearEstadoEquipo EstadoEquipoDAO : " + e.toString());
        }
    }

    @Override
    public void editarEstadoEquipo(EstadoEquipo estadoEquipo) {
        try {
            em.merge(estadoEquipo);
        } catch (Exception e) {
            logger.error("Error editarEstadoEquipo EstadoEquipoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarEstadoEquipo(EstadoEquipo estadoEquipo) {
        try {
            em.remove(em.merge(estadoEquipo));
        } catch (Exception e) {
            logger.error("Error eliminarEstadoEquipo EstadoEquipoDAO : " + e.toString());
        }
    }

    @Override
    public List<EstadoEquipo> consultarEstadosEquipos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EstadoEquipo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EstadoEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEstadosEquipos EstadoEquipoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public EstadoEquipo buscarEstadoEquipoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EstadoEquipo p WHERE p.idestadoequipo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            EstadoEquipo registro = (EstadoEquipo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEstadoEquipoPorID EstadoEquipoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public EstadoEquipo buscarEstadoEquipoPorNombre(String nombreEstadoEquipo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EstadoEquipo p WHERE UPPER(p.nombreestadoequipo) Like :nombreEstadoEquipo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("nombreEstadoEquipo", "%" + nombreEstadoEquipo + "%");
            EstadoEquipo registro = (EstadoEquipo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEstadoEquipoPorNombre EstadoEquipoDAO : " + e.toString());
            return null;
        }
    }
}
