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

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class AsignaturaPorPlanEstudioDAO implements AsignaturaPorPlanEstudioDAOInterface {

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
        } catch (Exception e) {
            System.out.println("Error crearAsignaturaPorPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString());
        }
    }

    @Override
    public void editarAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignatura) {
        try {
            em.clear();
            em.merge(asignatura);
        } catch (Exception e) {
            System.out.println("Error editarAsignaturaPorPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignatura) {
        try {
            em.clear();
            em.remove(em.merge(asignatura));
        } catch (Exception e) {
            System.out.println("Error eliminarAsignaturaPorPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString());
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturasPorPlanEstudio() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<AsignaturaPorPlanEstudio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarAsignaturasPorPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudiosIdPlanEstudio(BigInteger planEstudio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p WHERE p.planestudio.idplanestudios=:planEstudio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("planEstudio", planEstudio);
            List<AsignaturaPorPlanEstudio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarAsignaturaPorPlanEstudiosIdPlanEstudio AsignaturaPorPlanEstudioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudiosIdAsignatura(BigInteger asignatura) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AsignaturaPorPlanEstudio p WHERE p.asignatura.idasignatura=:asignatura");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("asignatura", asignatura);
            List<AsignaturaPorPlanEstudio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarAsignaturaPorPlanEstudiosIdAsignatura AsignaturaPorPlanEstudioDAO : " + e.toString());
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
            System.out.println("Error buscarAsignaturaPorPlanEstudioPorID AsignaturaPorPlanEstudioDAO : " + e.toString());
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
            System.out.println("Error buscarAsignaturaPorPlanEstudioPorPlanYAsignatura AsignaturaPorPlanEstudioDAO : " + e.toString());
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
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".estado ASC";
            System.out.println("consulta : " + consulta);
            TypedQuery<AsignaturaPorPlanEstudio> tq = em.createQuery(consulta, AsignaturaPorPlanEstudio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarAsignaturaPorPlanEstudioPorPlanEstudioPorFiltrado AsignaturaPorPlanEstudioDAO : " + e.toString());
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
                    if ("parametroCarrera".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "planestudio.carrera.idcarrera");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroPlanEstudio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "planestudio.idplanestudios");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroAsignatura".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "asignatura.idasignatura");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroEstado".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "estado");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                }
            }
        }
        jpql = jpql + wheres /*+ " ORDER BY " + alias + ".id ASC"*/;
        System.out.println(jpql);
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
                if ("parametroPlanEstudio".equals(entry.getKey())) {
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
