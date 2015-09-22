/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ReservaSalaDAOInterface;
import com.sirelab.entidades.ReservaSala;
import java.math.BigInteger;
import java.util.Date;
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
public class ReservaSalaDAO implements ReservaSalaDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearReservaSala(ReservaSala reserva) {
        try {
            em.persist(reserva);
        } catch (Exception e) {
            System.out.println("Error crearReservaSala ReservaSalaDAO : " + e.toString());
        }
    }

    @Override
    public void editarReservaSala(ReservaSala reserva) {
        try {
            em.merge(reserva);
        } catch (Exception e) {
            System.out.println("Error editarReservaSala ReservaSalaDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarReservaSala(ReservaSala reserva) {
        try {
            em.remove(em.merge(reserva));
        } catch (Exception e) {
            System.out.println("Error eliminarReservaSala ReservaSalaDAO : " + e.toString());
        }
    }

    @Override
    public List<ReservaSala> consultarReservaSalasSala() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaSala p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ReservaSala> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarReservaSalasSala ReservaSalaDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public ReservaSala buscarReservaSalaPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaSala p WHERE p.idreservasala=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            ReservaSala registro = (ReservaSala) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarReservaSalaPorID ReservaSalaDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public ReservaSala buscarReservaSalaPorFechaHoraSala(Date fecha, String horaInicio, BigInteger sala) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaSala p WHERE p.salalaboratorio.idsalalaboratorio =:sala AND p.reserva.fechareserva =:fecha AND p.reserva.horainicio=:horaInicio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("fecha", fecha);
            query.setParameter("horaInicio", horaInicio);
            query.setParameter("sala", sala);
            ReservaSala registro = (ReservaSala) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarReservaSalaPorFechaHoraSala ReservaSalaDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ReservaSala> buscarReservaSalasSalaPorPersona(BigInteger persona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaSala p WHERE p.reserva.persona.idpersona=:persona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("persona", persona);
            List<ReservaSala> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarReservaSalasSalaPorPersona ReservaSalaDAO : " + e.toString());
            return null;
        }
    }
}
