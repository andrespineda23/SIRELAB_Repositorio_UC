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

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class TipoMovimientoDAO implements TipoMovimientoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearTipoMovimiento(TipoMovimiento tipoMovimiento) {
        try {
            em.persist(tipoMovimiento);
        } catch (Exception e) {
            System.out.println("Error crearTipoMovimiento TipoMovimientoDAO : " + e.toString());
        }
    }

    @Override
    public void editarTipoMovimiento(TipoMovimiento tipoMovimiento) {
        try {
            em.merge(tipoMovimiento);
        } catch (Exception e) {
            System.out.println("Error editarTipoMovimiento TipoMovimientoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarTipoMovimiento(TipoMovimiento tipoMovimiento) {
        try {
            em.remove(em.merge(tipoMovimiento));
        } catch (Exception e) {
            System.out.println("Error eliminarTipoMovimiento TipoMovimientoDAO : " + e.toString());
        }
    }

    @Override
    public List<TipoMovimiento> consultarTiposMovimientos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoMovimiento p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TipoMovimiento> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarTiposMovimientos TipoMovimientoDAO : " + e.toString());
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
            System.out.println("Error buscarTipoMovimientoPorID TipoMovimientoDAO : " + e.toString());
            return null;
        }
    }
}