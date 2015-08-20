/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.LaboratoriosPorAreasDAOInterface;
import com.sirelab.entidades.LaboratoriosPorAreas;
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
 * @author AndresPineda
 */
@Stateless
public class LaboratoriosPorAreasDAO implements LaboratoriosPorAreasDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearLaboratoriosPorAreas(LaboratoriosPorAreas laboratorioPorArea) {
        try {
            em.persist(laboratorioPorArea);
        } catch (Exception e) {
            System.out.println("Error crearLaboratoriosPorAreas LaboratoriosPorAreasDAO : " + e.toString());
        }
    }

    @Override
    public void editarLaboratoriosPorAreas(LaboratoriosPorAreas laboratorioPorArea) {
        try {
            em.merge(laboratorioPorArea);
        } catch (Exception e) {
            System.out.println("Error editarLaboratoriosPorAreas LaboratoriosPorAreasDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarLaboratoriosPorAreas(LaboratoriosPorAreas laboratorioPorArea) {
        try {
            em.remove(em.merge(laboratorioPorArea));
        } catch (Exception e) {
            System.out.println("Error eliminarLaboratoriosPorAreas LaboratoriosPorAreasDAO : " + e.toString());
        }
    }

    @Override
    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreas() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM LaboratoriosPorAreas p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<LaboratoriosPorAreas> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarLaboratoriosPorAreassPorAreas LaboratoriosPorAreasDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreasPorLaboratorios(BigInteger laboratorio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM LaboratoriosPorAreas p WHERE p.laboratorio.idlaboratorio=:laboratorio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("laboratorio", laboratorio);
            List<LaboratoriosPorAreas> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarLaboratoriosPorAreassPorAreas LaboratoriosPorAreasDAO : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreasPorArea(BigInteger area) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM LaboratoriosPorAreas p WHERE p.areaprofundizacion.idareaprofundizacion=:area");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("area", area);
            List<LaboratoriosPorAreas> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarLaboratoriosPorAreasPorArea LaboratoriosPorAreasDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public LaboratoriosPorAreas buscarLaboratoriosPorAreasPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM LaboratoriosPorAreas p WHERE p.idlaboratoriosporareas=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            LaboratoriosPorAreas registro = (LaboratoriosPorAreas) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarLaboratoriosPorAreasPorID LaboratoriosPorAreasDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public LaboratoriosPorAreas buscarLaboratoriosPorAreasPorLabYArea(BigInteger laboratorio, BigInteger area) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM LaboratoriosPorAreas p WHERE p.laboratorio.idlaboratorio=:laboratorio AND p.areaprofundizacion.idareaprofundizacion=:area");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("area", area);
            query.setParameter("laboratorio", laboratorio);
            LaboratoriosPorAreas registro = (LaboratoriosPorAreas) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarLaboratoriosPorAreasPorLabYArea LaboratoriosPorAreasDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<LaboratoriosPorAreas> buscarLaboratoriosPorAreasPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(LaboratoriosPorAreas.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            System.out.println("jpql2.toString() : " + jpql2.toString());
            TypedQuery<LaboratoriosPorAreas> tq = em.createQuery(jpql2.toString(), LaboratoriosPorAreas.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarLaboratoriosPorAreassPorFiltrado LaboratoriosPorAreasDAO : " + e.toString());
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
                    if ("parametroDepartamento".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "laboratorio.departamento.iddepartamento");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroLaboratorio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "laboratorio.idlaboratorio");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroArea".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "areaprofundizacion.idareaprofundizacion");
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

    private TypedQuery<LaboratoriosPorAreas> asignarValores(TypedQuery<LaboratoriosPorAreas> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroArea".equals(entry.getKey()))
                        || ("parametroDepartamento".equals(entry.getKey()))
                        || ("parametroLaboratorio".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }

            }
        }
        return tq;
    }

}
