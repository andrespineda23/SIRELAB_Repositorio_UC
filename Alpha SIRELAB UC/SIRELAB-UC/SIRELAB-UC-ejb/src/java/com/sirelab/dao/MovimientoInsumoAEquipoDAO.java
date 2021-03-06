/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.MovimientoInsumoAEquipoDAOInterface;
import com.sirelab.entidades.MovimientoInsumoAEquipo;
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
public class MovimientoInsumoAEquipoDAO implements MovimientoInsumoAEquipoDAOInterface {

    static Logger logger = Logger.getLogger(MovimientoInsumoAEquipoDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearMovimientoInsumoAEquipo(MovimientoInsumoAEquipo movimiento) {
        try {
            em.persist(movimiento);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearMovimientoInsumoAEquipo MovimientoInsumoAEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarMovimientoInsumoAEquipo(MovimientoInsumoAEquipo movimiento) {
        try {
            em.merge(movimiento);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarMovimientoInsumoAEquipo MovimientoInsumoAEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarMovimientoInsumoAEquipo(MovimientoInsumoAEquipo movimiento) {
        try {
            em.remove(em.merge(movimiento));
        } catch (Exception e) {
            logger.error("Error eliminarMovimientoInsumoAEquipo MovimientoInsumoAEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<MovimientoInsumoAEquipo> consultarMovimientoInsumoAEquipos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumoAEquipo p ORDER BY p.movimientoinsumo.fechamovimiento ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<MovimientoInsumoAEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarMovimientoInsumoAEquipos MovimientoInsumoAEquipoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public MovimientoInsumoAEquipo buscarMovimientoInsumoAEquipoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumoAEquipo p WHERE p.idmovimientoinsumoaequipo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            MovimientoInsumoAEquipo registro = (MovimientoInsumoAEquipo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarMovimientoInsumoAEquipoPorID MovimientoInsumoAEquipoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<MovimientoInsumoAEquipo> buscarMovimientoInsumoAEquipoPorIdMovimiento(BigInteger idMovimiento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumoAEquipo p WHERE p.movimientoinsumo.idmovimientoinsumo=:idMovimiento ORDER BY p.movimientoinsumo.fechamovimiento ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idMovimiento", idMovimiento);
            List<MovimientoInsumoAEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error buscarMovimientoInsumoAEquipoPorIdMovimiento MovimientoInsumoAEquipoDAO : " + e.toString(),e);
            return null;
        }
    }
}
