/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.TipoCargoDAOInterface;
import com.sirelab.entidades.TipoCargo;
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
public class TipoCargoDAO implements TipoCargoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearTipoCargo(TipoCargo tipoCargo) {
        try {
            em.persist(tipoCargo);
        } catch (Exception e) {
            System.out.println("Error crearTipoCargo TipoCargoDAO : " + e.toString());
        }
    }

    @Override
    public void editarTipoCargo(TipoCargo tipoCargo) {
        try {
            em.merge(tipoCargo);
        } catch (Exception e) {
            System.out.println("Error editarTipoCargo TipoCargoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarTipoCargo(TipoCargo tipoCargo) {
        try {
            em.remove(em.merge(tipoCargo));
        } catch (Exception e) {
            System.out.println("Error eliminarTipoCargo TipoCargoDAO : " + e.toString());
        }
    }

    @Override
    public List<TipoCargo> consultarTiposCargos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoCargo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TipoCargo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarTiposCargos TipoCargoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public TipoCargo buscarTipoCargoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoCargo p WHERE p.idtipocargo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            TipoCargo registro = (TipoCargo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarTipoCargoPorID TipoCargoDAO : " + e.toString());
            return null;
        }
    }
}
