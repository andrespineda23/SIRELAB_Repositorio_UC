/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.TipoReservaDAOInterface;
import com.sirelab.entidades.TipoReserva;
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
public class TipoReservaDAO implements TipoReservaDAOInterface{

    static Logger logger = Logger.getLogger(TipoReservaDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearTipoReserva(TipoReserva tipoReserva) {
        try {
            em.persist(tipoReserva);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearTipoReserva TipoReservaDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarTipoReserva(TipoReserva tipoReserva) {
        try {
            em.merge(tipoReserva);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarTipoReserva TipoReservaDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarTipoReserva(TipoReserva tipoReserva) {
        try {
            em.remove(em.merge(tipoReserva));
        } catch (Exception e) {
            logger.error("Error eliminarTipoReserva TipoReservaDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<TipoReserva> consultarTiposReservas() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoReserva p ORDER BY p.nombretiporeserva ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TipoReserva> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarTiposReservas TipoReservaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public TipoReserva buscarTipoReservaPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoReserva p WHERE p.idtiporeserva=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            TipoReserva registro = (TipoReserva) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarTipoReservaPorID TipoReservaDAO : " + e.toString(),e);
            return null;
        }
    }
}
