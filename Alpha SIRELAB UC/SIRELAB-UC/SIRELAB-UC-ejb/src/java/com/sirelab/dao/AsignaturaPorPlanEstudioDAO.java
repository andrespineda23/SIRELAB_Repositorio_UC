/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.AsignaturaPorPlanEstudioDAOInterface;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
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
public class AsignaturaPorPlanEstudioDAO implements AsignaturaPorPlanEstudioDAOInterface {

    static Logger logger = Logger.getLogger(AsignaturaPorPlanEstudioDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignatura) {
        try {
            em.clear();
            em.persist(asignatura);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearAsignaturaPorPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignatura) {
        try {
            em.clear();
            em.merge(asignatura);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarAsignaturaPorPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignatura) {
        try {
            em.clear();
            em.remove(em.merge(asignatura));
        } catch (Exception e) {
            logger.error("Error eliminarAsignaturaPorPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturasPorPlanEstudio() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p  ORDER BY p.asignatura.codigoasignatura ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<AsignaturaPorPlanEstudio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarAsignaturasPorPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudiosIdPlanEstudio(BigInteger planEstudio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p WHERE p.planestudio.idplanestudios=:planEstudio  ORDER BY p.asignatura.codigoasignatura ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("planEstudio", planEstudio);
            List<AsignaturaPorPlanEstudio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarAsignaturaPorPlanEstudiosIdPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
            return null;
        }
    }
    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudiosActivoIdPlanEstudio(BigInteger planEstudio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p WHERE p.planestudio.idplanestudios=:planEstudio AND p.estado=true  ORDER BY p.asignatura.codigoasignatura ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("planEstudio", planEstudio);
            List<AsignaturaPorPlanEstudio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarAsignaturaPorPlanEstudiosIdPlanEstudio consultarAsignaturaPorPlanEstudiosActivoIdPlanEstudio : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudiosIdAsignatura(BigInteger asignatura) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p WHERE p.asignatura.idasignatura=:asignatura  ORDER BY p.asignatura.codigoasignatura ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("asignatura", asignatura);
            List<AsignaturaPorPlanEstudio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarAsignaturaPorPlanEstudiosIdAsignatura AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public AsignaturaPorPlanEstudio buscarAsignaturaPorPlanEstudioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p WHERE p.idasignaturaporplanestudio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            AsignaturaPorPlanEstudio registro = (AsignaturaPorPlanEstudio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarAsignaturaPorPlanEstudioPorID AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public AsignaturaPorPlanEstudio buscarAsignaturaPorPlanEstudioPorPlanYAsignatura(BigInteger plan, BigInteger asignatura) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p WHERE p.planestudio.idplanestudios=:plan AND p.asignatura.idasignatura=:asignatura");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("plan", plan);
            query.setParameter("asignatura", asignatura);
            AsignaturaPorPlanEstudio registro = (AsignaturaPorPlanEstudio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarAsignaturaPorPlanEstudioPorPlanYAsignatura AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> buscarAsignaturaPorPlanEstudioPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(AsignaturaPorPlanEstudio.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".asignatura.codigoasignatura ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<AsignaturaPorPlanEstudio> tq = em.createQuery(consulta, AsignaturaPorPlanEstudio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarAsignaturaPorPlanEstudioPorPlanEstudioPorFiltrado AsignaturaPorPlanEstudioDAO : " + e.toString(),e);
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
                    if ("parametroPlan".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "planestudio.idplanestudios");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro=10;
                    }
                    if ("parametroAsignatura".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "asignatura.idasignatura");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro=10;
                    }
                    if ("parametroEstado".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "estado");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro=10;
                    }
                    if ("parametroCarrera".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "planestudio.carrera.idcarrera");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro=10;
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

    private TypedQuery<AsignaturaPorPlanEstudio> asignarValores(TypedQuery<AsignaturaPorPlanEstudio> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if ("parametroAsignatura".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
                if ("parametroCarrera".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
                if ("parametroPlan".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
                if ("parametroEstado".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
            }
        }
        return tq;
    }
}
