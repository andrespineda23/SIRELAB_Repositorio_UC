/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.EstadoReservaDAOInterface;
import com.sirelab.entidades.EstadoReserva;
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
public class EstadoReservaDAO implements EstadoReservaDAOInterface {

    static Logger logger = Logger.getLogger(EstadoReservaDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearEstadoReserva(EstadoReserva estadoReserva) {
        try {
            em.persist(estadoReserva);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearEstadoReserva EstadoReservaDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarEstadoReserva(EstadoReserva estadoReserva) {
        try {
            em.merge(estadoReserva);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarEstadoReserva EstadoReservaDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarEstadoReserva(EstadoReserva estadoReserva) {
        try {
            em.remove(em.merge(estadoReserva));
        } catch (Exception e) {
            logger.error("Error eliminarEstadoReserva EstadoReservaDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<EstadoReserva> consultarEstadosReservas() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EstadoReserva p ORDER BY p.nombreestadoreserva ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EstadoReserva> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEstadosReservas EstadoReservaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EstadoReserva buscarEstadoReservaPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EstadoReserva p WHERE p.idestadoreserva=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            EstadoReserva registro = (EstadoReserva) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEstadoReservaPorID EstadoReservaDAO : " + e.toString(),e);
            return null;
        }
    }
}
