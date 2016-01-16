/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.GuiaLaboratorioDAOInterface;
import com.sirelab.entidades.GuiaLaboratorio;
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
public class GuiaLaboratorioDAO implements GuiaLaboratorioDAOInterface {

    static Logger logger = Logger.getLogger(GuiaLaboratorioDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearGuiaLaboratorio(GuiaLaboratorio guia) {
        try {
            em.persist(guia);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearGuiaLaboratorio GuiaLaboratorioDAO : " + e.toString());
        }
    }

    @Override
    public void editarGuiaLaboratorio(GuiaLaboratorio guia) {
        try {
            em.merge(guia);
        } catch (Exception e) {
            logger.error("Error editarGuiaLaboratorio GuiaLaboratorioDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarGuiaLaboratorio(GuiaLaboratorio guia) {
        try {
            em.remove(em.merge(guia));
        } catch (Exception e) {
            logger.error("Error eliminarGuiaLaboratorio GuiaLaboratorioDAO : " + e.toString());
        }
    }

    @Override
    public List<GuiaLaboratorio> consultarGuiasLaboratorio() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM GuiaLaboratorio p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<GuiaLaboratorio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarGuiasLaboratorio GuiaLaboratorioDAO : " + e.toString());
            return null;
        }
    }
    @Override
    public List<GuiaLaboratorio> consultarGuiasLaboratorioPorIdAreaPlan(BigInteger areaPlan) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM GuiaLaboratorio p WHERE p.asignaturaporplanestudio.idasignaturaporplanestudio=:areaPlan");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("areaPlan", areaPlan);
            List<GuiaLaboratorio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarGuiasLaboratorio GuiaLaboratorioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public GuiaLaboratorio buscarGuiaLaboratorioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM GuiaLaboratorio p WHERE p.idguialaboratorio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            GuiaLaboratorio registro = (GuiaLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarGuiaLaboratorioPorID GuiaLaboratorioDAO : " + e.toString());
            return null;
        }
    }
    
    @Override
    public GuiaLaboratorio buscarGuiaLaboratorioPorUbicacion(String ubicacion) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM GuiaLaboratorio p WHERE p.ubicacionguia=:ubicacion");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("ubicacion", ubicacion);
            GuiaLaboratorio registro = (GuiaLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarGuiaLaboratorioPorUbicacion GuiaLaboratorioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<GuiaLaboratorio> buscarGuiasLaboratorioPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(GuiaLaboratorio.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".nombreguia ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<GuiaLaboratorio> tq = em.createQuery(consulta, GuiaLaboratorio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarGuiasLaboratorioPorFiltrado GuiaLaboratorioDAO : " + e.toString());
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
                    if ("parametroNombre".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".nombreguia")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroAsignatura".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "asignaturaporplanestudio.asignatura.idasignatura");
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

    private TypedQuery<GuiaLaboratorio> asignarValores(TypedQuery<GuiaLaboratorio> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if ("parametroNombre".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroAsignatura".equals(entry.getKey())) {
                    //
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }

            }
        }
        return tq;
    }
}
