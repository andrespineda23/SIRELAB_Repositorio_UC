/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ReservaEquipoElementoDAOInterface;
import com.sirelab.entidades.ReservaEquipoElemento;
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
public class ReservaEquipoElementoDAO implements ReservaEquipoElementoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearReservaEquipoElemento(ReservaEquipoElemento reserva) {
        try {
            em.persist(reserva);
        } catch (Exception e) {
            System.out.println("Error crearReservaEquipoElemento ReservaEquipoElementoDAO : " + e.toString());
        }
    }

    @Override
    public void editarReservaEquipoElemento(ReservaEquipoElemento reserva) {
        try {
            em.merge(reserva);
        } catch (Exception e) {
            System.out.println("Error editarReservaEquipoElemento ReservaEquipoElementoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarReservaEquipoElemento(ReservaEquipoElemento reserva) {
        try {
            em.remove(em.merge(reserva));
        } catch (Exception e) {
            System.out.println("Error eliminarReservaEquipoElemento ReservaEquipoElementoDAO : " + e.toString());
        }
    }

    @Override
    public List<ReservaEquipoElemento> consultarReservaEquipoElementos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaEquipoElemento p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ReservaEquipoElemento> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarReservaEquipoElementos ReservaEquipoElementoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public ReservaEquipoElemento buscarReservaEquipoElementoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaEquipoElemento p WHERE p.idreservaequipoelemento=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            ReservaEquipoElemento registro = (ReservaEquipoElemento) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarReservaEquipoElementoPorID ReservaEquipoElementoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ReservaEquipoElemento> buscarReservaEquipoElementosPorPersona(BigInteger persona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaEquipoElemento p WHERE p.reserva.persona.idpersona=:persona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("persona", persona);
            List<ReservaEquipoElemento> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarReservaEquipoElementosPorPersona ReservaEquipoElementoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ReservaEquipoElemento> buscarReservaEquipoElementosPorReserva(BigInteger reserva) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaEquipoElemento p WHERE p.reserva.idreserva=:reserva");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("reserva", reserva);
            List<ReservaEquipoElemento> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarReservaEquipoElementosPorReserva ReservaEquipoElementoDAO : " + e.toString());
            return null;
        }
    }
}
