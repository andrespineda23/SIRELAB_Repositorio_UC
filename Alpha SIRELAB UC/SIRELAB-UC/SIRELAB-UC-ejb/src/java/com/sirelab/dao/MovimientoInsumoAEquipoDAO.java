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

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class MovimientoInsumoAEquipoDAO implements MovimientoInsumoAEquipoDAOInterface {

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
            System.out.println("Error crearMovimientoInsumoAEquipo MovimientoInsumoAEquipoDAO : " + e.toString());
        }
    }

    @Override
    public void editarMovimientoInsumoAEquipo(MovimientoInsumoAEquipo movimiento) {
        try {
            em.merge(movimiento);
        } catch (Exception e) {
            System.out.println("Error editarMovimientoInsumoAEquipo MovimientoInsumoAEquipoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarMovimientoInsumoAEquipo(MovimientoInsumoAEquipo movimiento) {
        try {
            em.remove(em.merge(movimiento));
        } catch (Exception e) {
            System.out.println("Error eliminarMovimientoInsumoAEquipo MovimientoInsumoAEquipoDAO : " + e.toString());
        }
    }

    @Override
    public List<MovimientoInsumoAEquipo> consultarMovimientoInsumoAEquipos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumoAEquipo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<MovimientoInsumoAEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarMovimientoInsumoAEquipos MovimientoInsumoAEquipoDAO : " + e.toString());
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
            System.out.println("Error buscarMovimientoInsumoAEquipoPorID MovimientoInsumoAEquipoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<MovimientoInsumoAEquipo> buscarMovimientoInsumoAEquipoPorIdMovimiento(BigInteger idMovimiento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumoAEquipo p WHERE p.movimientoinsumo.idmovimientoinsumo=:idMovimiento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idMovimiento", idMovimiento);
            List<MovimientoInsumoAEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error buscarMovimientoInsumoAEquipoPorIdMovimiento MovimientoInsumoAEquipoDAO : " + e.toString());
            return null;
        }
    }
}
