/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ReservaModuloLaboratorioDAOInterface;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
            logger.error("Error crearReservaModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString(), e);
        }
    }

    @Override
    public void editarReservaModuloLaboratorio(ReservaModuloLaboratorio reserva) {
        try {
            em.merge(reserva);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarReservaModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString(), e);
        }
    }

    @Override
    public void eliminarReservaModuloLaboratorio(ReservaModuloLaboratorio reserva) {
        try {
            em.remove(em.merge(reserva));
        } catch (Exception e) {
            logger.error("Error eliminarReservaModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString(), e);
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> consultarReservaModuloLaboratoriosModuloLaboratorio() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p  ORDER BY p.reserva.fechareserva ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ReservaModuloLaboratorio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarReservaModuloLaboratoriosModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString(), e);
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
            logger.error("Error buscarReservaModuloLaboratorioPorID ReservaModuloLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public ReservaModuloLaboratorio buscarReservaModuloLaboratorioPorIdReserva(BigInteger reserva) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.reserva.idreserva=:reserva");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("reserva", reserva);
            ReservaModuloLaboratorio registro = (ReservaModuloLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratorioPorIdReserva ReservaModuloLaboratorioDAO : " + e.toString(), e);
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
            logger.error("Error buscarReservaModuloLaboratorioPorFechaHoraModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> buscarCantidadReservaModuloLaboratorioPorParametros(Date fecha, String horaInicio, BigInteger sala) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.modulolaboratorio.salalaboratorio.idsalalaboratorio =:sala AND p.reserva.fechareserva =:fecha AND p.reserva.horainicio=:horaInicio AND p.reserva.estadoreserva.idestadoreserva != 1 ORDER BY p.reserva.fechareserva ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("fecha", fecha);
            query.setParameter("horaInicio", horaInicio);
            query.setParameter("sala", sala);
            List<ReservaModuloLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratorioPorFechaHoraModuloLaboratorio ReservaModuloLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratoriosModuloLaboratorioPorPersona(BigInteger persona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.reserva.persona.idpersona=:persona  ORDER BY p.reserva.fechareserva ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("persona", persona);
            List<ReservaModuloLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratoriosModuloLaboratorioPorPersona ReservaModuloLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorIdPeriodoAcademico(BigInteger periodo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.reserva.periodoacademico.idperiodoacademico=:periodo  ORDER BY p.reserva.fechareserva ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("periodo", periodo);
            List<ReservaModuloLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratorioPorIdPeriodoAcademico ReservaModuloLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorIdSala(BigInteger sala) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.modulolaboratorio.salalaboratorio.idsalalaboratorio=:sala ORDER BY p.reserva.fechareserva ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("sala", sala);
            List<ReservaModuloLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratorioPorIdSala ReservaModuloLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> buscarReservaModuloPorPersona(BigInteger persona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ReservaModuloLaboratorio p WHERE p.reserva.persona.idpersona=:persona ORDER BY p.reserva.fechareserva ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("persona", persona);
            List<ReservaModuloLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarReservaModulosModuloPorPersona ReservaModuloDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(ReservaModuloLaboratorio.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".reserva.horainicio ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<ReservaModuloLaboratorio> tq = em.createQuery(consulta, ReservaModuloLaboratorio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarReservaModuloLaboratorioPorFiltrado ReservaModuloLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    private String adicionarFiltros(String jpql, Map<String, String> filters, String alias) {
        final StringBuilder wheres = new StringBuilder();
        int camposFiltro = 0;
        if (null != filters && !filters.isEmpty()) {
            wheres.append(" WHERE ");
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                    if (camposFiltro > 0) {
                        wheres.append(" AND ");
                    }
                    if ("parametroNumero".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".reserva.numeroreserva")
                                .append(") Like :parametroNumero");
                        camposFiltro++;
                    }
                    if ("parametroFecha".equals(entry.getKey())) {
                        wheres.append(alias).append(".reserva." + "fechareserva");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                }
            }
        }
        jpql = jpql + wheres /*+ " ORDER BY " + alias + ".id ASC"*/;
        logger.error(jpql);
        if (jpql.trim()
                .endsWith("WHERE")) {
            jpql = jpql.replace("WHERE", "");
        }
        return jpql;
    }

    private TypedQuery<ReservaModuloLaboratorio> asignarValores(TypedQuery<ReservaModuloLaboratorio> tq, Map<String, String> filters) {
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if ("parametroNumero".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroFecha".equals(entry.getKey())) {
                    Date fecha = new Date(entry.getValue());
                    System.out.println("entry.getValue(): " + entry.getValue());
                    String pattern = "dd/MM/yyyy";
                    SimpleDateFormat format = new SimpleDateFormat(pattern);
                    try {
                        fecha = format.parse(entry.getValue());
                        //                    System.out.println(fecha);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println("new Date(entry.getValue(): " + fecha);
                    tq.setParameter(entry.getKey(), fecha);
                }
            }
        }
        return tq;
    }
}
