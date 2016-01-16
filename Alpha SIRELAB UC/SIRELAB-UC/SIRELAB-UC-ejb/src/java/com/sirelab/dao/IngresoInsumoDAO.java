/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.IngresoInsumoDAOInterface;
import com.sirelab.entidades.IngresoInsumo;
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
public class IngresoInsumoDAO implements IngresoInsumoDAOInterface {

    static Logger logger = Logger.getLogger(IngresoInsumoDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearIngresoInsumo(IngresoInsumo insumo) {
        try {
            em.persist(insumo);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearIngresoInsumo IngresoInsumoDAO : " + e.toString());
        }
    }

    @Override
    public void editarIngresoInsumo(IngresoInsumo insumo) {
        try {
            em.merge(insumo);
        } catch (Exception e) {
            logger.error("Error editarIngresoInsumo IngresoInsumoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarIngresoInsumo(IngresoInsumo insumo) {
        try {
            em.remove(em.merge(insumo));
        } catch (Exception e) {
            logger.error("Error eliminarIngresoInsumo IngresoInsumoDAO : " + e.toString());
        }
    }

    @Override
    public List<IngresoInsumo> consultarIngresoInsumos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM IngresoInsumo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<IngresoInsumo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarIngresoInsumos IngresoInsumoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public IngresoInsumo buscarIngresoInsumoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM IngresoInsumo p WHERE p.idingresoinsumo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            IngresoInsumo registro = (IngresoInsumo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarIngresoInsumoPorID IngresoInsumoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<IngresoInsumo> buscarIngresoInsumoPorIdInsumo(BigInteger idInsumo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM IngresoInsumo p WHERE p.insumo.idinsumo=:idInsumo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idInsumo", idInsumo);
            List<IngresoInsumo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error buscarIngresoInsumoPorIdInsumo IngresoInsumoDAO : " + e.toString());
            return null;
        }
    }

}
