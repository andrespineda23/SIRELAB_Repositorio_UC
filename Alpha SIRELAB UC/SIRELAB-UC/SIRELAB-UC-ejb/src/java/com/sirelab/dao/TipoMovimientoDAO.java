/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.TipoMovimientoDAOInterface;
import com.sirelab.entidades.TipoMovimiento;
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
public class TipoMovimientoDAO implements TipoMovimientoDAOInterface {

    static Logger logger = Logger.getLogger(TipoMovimientoDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearTipoMovimiento(TipoMovimiento tipoMovimiento) {
        try {
            em.persist(tipoMovimiento);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearTipoMovimiento TipoMovimientoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarTipoMovimiento(TipoMovimiento tipoMovimiento) {
        try {
            em.merge(tipoMovimiento);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarTipoMovimiento TipoMovimientoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarTipoMovimiento(TipoMovimiento tipoMovimiento) {
        try {
            em.remove(em.merge(tipoMovimiento));
        } catch (Exception e) {
            logger.error("Error eliminarTipoMovimiento TipoMovimientoDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<TipoMovimiento> consultarTiposMovimientos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoMovimiento p ORDER BY p.nombretipo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TipoMovimiento> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarTiposMovimientos TipoMovimientoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public TipoMovimiento buscarTipoMovimientoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoMovimiento p WHERE p.idtipomovimiento=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            TipoMovimiento registro = (TipoMovimiento) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarTipoMovimientoPorID TipoMovimientoDAO : " + e.toString(),e);
            return null;
        }
    }
}
