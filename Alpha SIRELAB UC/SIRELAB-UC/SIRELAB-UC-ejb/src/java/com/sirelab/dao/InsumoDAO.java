/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.InsumoDAOInterface;
import com.sirelab.entidades.Insumo;
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
 * @author ANDRES PINEDA
 */
@Stateless
public class InsumoDAO implements InsumoDAOInterface {

    static Logger logger = Logger.getLogger(InsumoDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearInsumo(Insumo insumo) {
        try {
            em.persist(insumo);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearInsumo InsumoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarInsumo(Insumo insumo) {
        try {
            em.merge(insumo);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarInsumo InsumoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarInsumo(Insumo insumo) {
        try {
            em.remove(em.merge(insumo));
        } catch (Exception e) {
            logger.error("Error eliminarInsumo InsumoDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<Insumo> consultarInsumos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Insumo p ORDER BY p.codigoinsumo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Insumo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarInsumos InsumoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Insumo buscarInsumoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Insumo p WHERE p.idinsumo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Insumo registro = (Insumo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarInsumoPorID InsumoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Insumo buscarInsumoPorCodigo(String codigo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Insumo p WHERE p.codigoinsumo=:codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            Insumo registro = (Insumo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarInsumoPorCodigo InsumoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Insumo> buscarInsumosPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Insumo.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".codigoinsumo ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<Insumo> tq = em.createQuery(consulta, Insumo.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarInsumosPorFiltrado InsumoDAO : " + e.toString(),e);
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
                                .append(".nombreinsumo")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroCodigo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".codigoinsumo")
                                .append(") Like :parametroCodigo");
                        camposFiltro++;
                    }
                    if ("parametroMarca".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".marcainsumo")
                                .append(") Like :parametroMarca");
                        camposFiltro++;
                    }
                    if ("parametroModelo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".modeloinsumo")
                                .append(") Like :parametroModelo");
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

    private TypedQuery<Insumo> asignarValores(TypedQuery<Insumo> tq, Map<String, String> filters) {
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroNombre".equals(entry.getKey()))
                        || ("parametroCodigo".equals(entry.getKey()))
                        || ("parametroMarca".equals(entry.getKey()))
                        || ("parametroModelo".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
            }
        }
        return tq;
    }
    
    @Override
    public Insumo obtenerUltimaInsumoRegistrada() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Insumo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Insumo> registros = query.getResultList();
            if (registros != null) {
                int tam = registros.size();
                Insumo ultimoRegistro = registros.get(tam - 1);
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error obtenerUltimaInsumoRegistrada InsumoDAO : " + e.toString(),e);
            return null;
        }
    }

}
