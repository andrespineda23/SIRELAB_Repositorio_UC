/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ConvenioDAOInterface;
import com.sirelab.entidades.Convenio;
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
public class ConvenioDAO implements ConvenioDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearConvenio(Convenio convenio) {
        try {
            em.persist(convenio);
            em.flush();
        } catch (Exception e) {
            System.out.println("Error crearConvenio ConvenioDAO : " + e.toString());
        }
    }

    @Override
    public void editarConvenio(Convenio convenio) {
        try {
            em.merge(convenio);
        } catch (Exception e) {
            System.out.println("Error editarConvenio ConvenioDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarConvenio(Convenio convenio) {
        try {
            em.remove(em.merge(convenio));
        } catch (Exception e) {
            System.out.println("Error eliminarConvenio ConvenioDAO : " + e.toString());
        }
    }

    @Override
    public List<Convenio> consultarConvenios() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Convenio p ORDER BY p.nombreconvenio ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Convenio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarConvenios ConvenioDAO : " + e.toString());
            return null;
        }
    }
    @Override
    public List<Convenio> consultarConveniosActivos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Convenio p WHERE p.estado=true ORDER BY p.nombreconvenio ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Convenio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarConveniosActivos ConvenioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public Convenio buscarConvenioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Convenio p WHERE p.idconvenio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Convenio registro = (Convenio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarConvenioPorID ConvenioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Convenio> buscarConvenioPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Convenio.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".nombreconvenio ASC";
            System.out.println("consulta : " + consulta);
            TypedQuery<Convenio> tq = em.createQuery(consulta, Convenio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarConvenioPorFiltrado ConvenioDAO : " + e.toString());
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
                    if ("parametroEstado".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "estado");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroNombre".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".nombreconvenio")
                                .append(") Like :parametroNombre");
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

    private TypedQuery<Convenio> asignarValores(TypedQuery<Convenio> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if ("parametroNombre".equals(entry.getKey())) {
                    //
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if (("parametroEstado".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
            }
        }
        return tq;
    }
}
