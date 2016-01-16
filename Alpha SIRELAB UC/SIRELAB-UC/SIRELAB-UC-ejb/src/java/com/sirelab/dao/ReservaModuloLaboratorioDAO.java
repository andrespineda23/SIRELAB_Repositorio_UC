/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ReservaModuloLaboratorioDAOInterface;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class ReservaModuloLaboratorioDAO implements ReservaModuloLaboratorioDAOInterface {

    static Logger logger = Logger.getLogger(ReservaModuloLaboratorioDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearReservaModuloLaboratorio(ReservaModuloLaboratorio reserva) {
        try {
            em.persist(reserva);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearReservaModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString());
        }
    }

    @Override
    public void editarReservaModuloLaboratorio(ReservaModuloLaboratorio reserva) {
        try {
            em.merge(reserva);
        } catch (Exception e) {
            logger.error("Error editarReservaModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarReservaModuloLaboratorio(ReservaModuloLaboratorio reserva) {
        try {
            em.remove(em.merge(reserva));
        } catch (Exception e) {
            logger.error("Error eliminarReservaModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString());
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> consultarReservaModuloLaboratoriosModuloLaboratorio() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ReservaModuloLaboratorio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarReservaModuloLaboratoriosModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public ReservaModuloLaboratorio buscarReservaModuloLaboratorioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.idreservamodulolaboratorio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            ReservaModuloLaboratorio registro = (ReservaModuloLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratorioPorID ReservaModuloLaboratorioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public ReservaModuloLaboratorio buscarReservaModuloLaboratorioPorFechaHoraSala(Date fecha, String horaInicio, BigInteger sala) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.modulolaboratorio.salalaboratorio.idsalalaboratorio =:sala AND p.reserva.fechareserva =:fecha AND p.reserva.horainicio=:horaInicio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("fecha", fecha);
            query.setParameter("horaInicio", horaInicio);
            query.setParameter("sala", sala);
            ReservaModuloLaboratorio registro = (ReservaModuloLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratorioPorFechaHoraModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<ReservaModuloLaboratorio> buscarCantidadReservaModuloLaboratorioPorParametros(Date fecha, String horaInicio, BigInteger sala) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.modulolaboratorio.salalaboratorio.idsalalaboratorio =:sala AND p.reserva.fechareserva =:fecha AND p.reserva.horainicio=:horaInicio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("fecha", fecha);
            query.setParameter("horaInicio", horaInicio);
            query.setParameter("sala", sala);
            List<ReservaModuloLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratorioPorFechaHoraModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratoriosModuloLaboratorioPorPersona(BigInteger persona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.reserva.persona.idpersona=:persona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("persona", persona);
            List<ReservaModuloLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratoriosModuloLaboratorioPorPersona ReservaModuloLaboratorioDAO : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<ReservaModuloLaboratorio> buscarReservaModuloPorPersona(BigInteger persona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.reserva.persona.idpersona=:persona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("persona", persona);
            List<ReservaModuloLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModulosModuloPorPersona ReservaModuloDAO : " + e.toString());
            return null;
        }
    }
}
