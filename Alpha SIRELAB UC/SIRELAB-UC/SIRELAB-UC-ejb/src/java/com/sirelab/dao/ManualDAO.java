/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ManualDAOInterface;
import com.sirelab.entidades.Manual;
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
public class ManualDAO implements ManualDAOInterface {

    static Logger logger = Logger.getLogger(ManualDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearManual(Manual manual) {
        try {
            em.persist(manual);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearManual ManualDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarManual(Manual manual) {
        try {
            em.merge(manual);
        } catch (Exception e) {
            logger.error("Error editarManual ManualDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarManual(Manual manual) {
        try {
            em.remove(em.merge(manual));
        } catch (Exception e) {
            logger.error("Error eliminarManual ManualDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<Manual> consultarManuales() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Manual p ORDER BY p.nombremanual ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Manual> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarManuales ManualDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Manual buscarManualPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Manual p WHERE p.idmanual=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Manual registro = (Manual) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarManualPorID ManualDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Manual buscarManualPorUbicacion(String ubicacion) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Manual p WHERE p.ubicacionmanual=:ubicacion");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("ubicacion", ubicacion);
            Manual registro = (Manual) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarManualPorUbicacion ManualDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Manual> buscarManualesPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Manual.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".nombremanual ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<Manual> tq = em.createQuery(consulta, Manual.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarManualesPorFiltrado ManualDAO : " + e.toString(),e);
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
                                .append(".nombremanual")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroTipo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".tipomanual")
                                .append(") Like :parametroTipo");
                        camposFiltro++;
                    }
                    if ("parametroUbicacion".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".ubicacionmanual")
                                .append(") Like :parametroUbicacion");
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

    private TypedQuery<Manual> asignarValores(TypedQuery<Manual> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if ("parametroNombre".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroTipo".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroUbicacion".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }

            }
        }
        return tq;
    }

    @Override
    public Manual obtenerUltimoManualRegistrado() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Manual p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Manual> registros = query.getResultList();
            if (registros != null) {
                int tam = registros.size();
                Manual ultimoRegistro = registros.get(tam - 1);
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error obtenerUltimoManualRegistrado ManualDAO : " + e.toString(),e);
            return null;
        }
    }
}
