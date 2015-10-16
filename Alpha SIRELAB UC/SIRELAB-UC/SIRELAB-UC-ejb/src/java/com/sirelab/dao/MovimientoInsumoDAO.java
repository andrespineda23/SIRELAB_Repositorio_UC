/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.MovimientoInsumoDAOInterface;
import com.sirelab.entidades.MovimientoInsumo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class MovimientoInsumoDAO implements MovimientoInsumoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearMovimientoInsumo(MovimientoInsumo movimientoInsumo) {
        try {
            em.persist(movimientoInsumo);
            em.flush();
        } catch (Exception e) {
            System.out.println("Error crearMovimientoInsumo MovimientoInsumoDAO : " + e.toString());
        }
    }

    @Override
    public void editarMovimientoInsumo(MovimientoInsumo movimientoInsumo) {
        try {
            em.merge(movimientoInsumo);
        } catch (Exception e) {
            System.out.println("Error editarMovimientoInsumo MovimientoInsumoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarMovimientoInsumo(MovimientoInsumo movimientoInsumo) {
        try {
            em.remove(em.merge(movimientoInsumo));
        } catch (Exception e) {
            System.out.println("Error eliminarMovimientoInsumo MovimientoInsumoDAO : " + e.toString());
        }
    }

    @Override
    public List<MovimientoInsumo> consultarMovimientosInsumo() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<MovimientoInsumo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarMovimientosInsumo MovimientoInsumoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<MovimientoInsumo> consultarMovimientosInsumoPorInsumo(BigInteger insumo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumo p WHERE p.insumo.idinsumo=:insumo ORDER BY p.fechamovimiento ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("insumo", insumo);
            List<MovimientoInsumo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarMovimientosInsumoPorInsumo MovimientoInsumoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public MovimientoInsumo buscarMovimientoInsumoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumo p WHERE p.idmovimientoinsumo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            MovimientoInsumo registro = (MovimientoInsumo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarMovimientoInsumoPorID MovimientoInsumoDAO : " + e.toString());
            return null;
        }
    }
    
    
    @Override
    public MovimientoInsumo obtenerUltimaMovimientoInsumoRegistrada() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM MovimientoInsumo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<MovimientoInsumo> registros = query.getResultList();
            if (registros != null) {
                int tam = registros.size();
                MovimientoInsumo ultimoRegistro = registros.get(tam - 1);
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error obtenerUltimaMovimientoInsumoRegistrada MovimientoInsumoDAO : " + e.toString());
            return null;
        }
    }
}
