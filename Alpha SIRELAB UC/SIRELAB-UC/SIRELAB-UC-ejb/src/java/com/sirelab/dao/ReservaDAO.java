/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ReservaDAOInterface;
import com.sirelab.entidades.Reserva;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ReservaDAO implements ReservaDAOInterface {

    static Logger logger = Logger.getLogger(ReservaDAO.class);

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearReserva(Reserva reserva) {
        try {
            em.persist(reserva);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearReserva ReservaDAO : " + e.toString(), e);
        }
    }

    @Override
    public void editarReserva(Reserva reserva) {
        try {
            em.merge(reserva);
        } catch (Exception e) {
            logger.error("Error editarReserva ReservaDAO : " + e.toString(), e);
        }
    }

    @Override
    public void eliminarReserva(Reserva reserva) {
        try {
            em.remove(em.merge(reserva));
        } catch (Exception e) {
            logger.error("Error eliminarReserva ReservaDAO : " + e.toString(), e);
        }
    }

    @Override
    public List<Reserva> consultarReservas() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Reserva p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Reserva> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarReservas ReservaDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Reserva buscarReservaPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Reserva p WHERE p.idreserva=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Reserva registro = (Reserva) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaPorID ReservaDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Reserva> buscarReservasPorPersona(BigInteger persona) {
        try {
            em.clear();
            Date diaHoy = new Date();
            Query query = em.createQuery("SELECT p FROM Reserva p WHERE p.persona.idpersona=:persona AND p.fechareserva>=:diaHoy");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("diaHoy", diaHoy);
            query.setParameter("persona", persona);
            List<Reserva> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservasPorPersona ReservaDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Reserva buscarUltimaReservaPersona(BigInteger persona, String horaInicio, Date fecha) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Reserva p WHERE p.persona.idpersona=:persona AND p.horainicio=:horaInicio AND p.fechareserva=:fecha");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("persona", persona);
            query.setParameter("horaInicio", horaInicio);
            query.setParameter("fecha", fecha);
            Reserva registro = (Reserva) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarUltimaReservaPersona ReservaDAO : " + e.toString(), e);
            return null;
        }
    }
    
    
    @Override
    public Reserva buscarUltimaReservaPersonaConEstado(BigInteger persona, String horaInicio, Date fecha) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Reserva p WHERE p.persona.idpersona=:persona AND p.horainicio=:horaInicio AND p.fechareserva=:fecha AND p.estadoreserva.idestadoreserva=:estado");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("persona", persona);
            query.setParameter("horaInicio", horaInicio);
            query.setParameter("fecha", fecha);
            query.setParameter("estado", BigInteger.valueOf(1));
            Reserva registro = (Reserva) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarUltimaReservaPersona ReservaDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Reserva> buscarReservasPorFecha(Date fecha) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Reserva p WHERE p.fechareserva=:fecha");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("fecha", fecha);
            List<Reserva> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservasPorFecha ReservaDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Reserva> buscarReservasPorEstadoReserva(BigInteger estado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Reserva p WHERE p.estadoreserva.idestadoreserva=:estado");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("estado", estado);
            List<Reserva> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservasPorEstadoReserva ReservaDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Integer obtenerCantidadReservasDia(Date fecha) {
        try {
            em.clear();
            //SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            //String fechaStr = formato.format(fecha);
            //fcha = new Date(fechaStr);
            Query query = em.createQuery("SELECT p FROM Reserva p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Reserva> registro = query.getResultList();
            System.out.println("registro: "+registro);
            if (null != registro) {
                return registro.size();
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.error("Error obtenerCantidadReservasDia ReservaDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Reserva buscarReservaPorNumero(String numero) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Reserva p WHERE p.numeroreserva=:numero");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("numero", numero);
            Reserva registro = (Reserva) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaPorNumero ReservaDAO : " + e.toString(), e);
            return null;
        }
    }
}
