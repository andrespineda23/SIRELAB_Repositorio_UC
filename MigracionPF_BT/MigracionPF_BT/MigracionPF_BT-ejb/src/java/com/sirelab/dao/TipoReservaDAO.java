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

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class TipoReservaDAO implements TipoReservaDAOInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearTipoReserva(TipoReserva tipoReserva) {
        try {
            em.persist(tipoReserva);
        } catch (Exception e) {
            System.out.println("Error crearTipoReserva TipoReservaDAO : " + e.toString());
        }
    }

    @Override
    public void editarTipoReserva(TipoReserva tipoReserva) {
        try {
            em.merge(tipoReserva);
        } catch (Exception e) {
            System.out.println("Error editarTipoReserva TipoReservaDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarTipoReserva(TipoReserva tipoReserva) {
        try {
            em.remove(em.merge(tipoReserva));
        } catch (Exception e) {
            System.out.println("Error eliminarTipoReserva TipoReservaDAO : " + e.toString());
        }
    }

    @Override
    public List<TipoReserva> consultarTiposReservas() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoReserva p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TipoReserva> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarTiposReservas TipoReservaDAO : " + e.toString());
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
            System.out.println("Error buscarTipoReservaPorID TipoReservaDAO : " + e.toString());
            return null;
        }
    }
}
