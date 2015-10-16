/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.TipoEventoDAOInterface;
import com.sirelab.entidades.TipoEvento;
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
public class TipoEventoDAO implements TipoEventoDAOInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearTipoEvento(TipoEvento tipoEvento) {
        try {
            em.persist(tipoEvento);
            em.flush();
        } catch (Exception e) {
            System.out.println("Error crearTipoEvento TipoEventoDAO : " + e.toString());
        }
    }

    @Override
    public void editarTipoEvento(TipoEvento tipoEvento) {
        try {
            em.merge(tipoEvento);
        } catch (Exception e) {
            System.out.println("Error editarTipoEvento TipoEventoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarTipoEvento(TipoEvento tipoEvento) {
        try {
            em.remove(em.merge(tipoEvento));
        } catch (Exception e) {
            System.out.println("Error eliminarTipoEvento TipoEventoDAO : " + e.toString());
        }
    }

    @Override
    public List<TipoEvento> consultarTiposEventos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoEvento p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TipoEvento> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarTiposEventos TipoEventoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public TipoEvento buscarTipoEventoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoEvento p WHERE p.idtipoevento=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            TipoEvento registro = (TipoEvento) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarTipoEventoPorID TipoEventoDAO : " + e.toString());
            return null;
        }
    }

}
