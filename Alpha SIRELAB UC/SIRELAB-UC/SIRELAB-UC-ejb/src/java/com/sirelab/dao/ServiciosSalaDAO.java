/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ServiciosSalaDAOInterface;
import com.sirelab.entidades.ServiciosSala;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class ServiciosSalaDAO implements ServiciosSalaDAOInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void crearServiciosSala(ServiciosSala serviciossala) {
        try {
            BasicConfigurator.configure();
            em.persist(serviciossala);
            logger.info("Mensaje Info");
            em.flush();
        } catch (Exception e) {
            System.out.println("Error crearServiciosSala ServiciosSalaDAO : " + e.toString());
        }
    }

    @Override
    public void editarServiciosSala(ServiciosSala serviciossala) {
        try {
            em.merge(serviciossala);
        } catch (Exception e) {
            System.out.println("Error editarServiciosSala ServiciosSalaDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarServiciosSala(ServiciosSala serviciossala) {
        try {
            em.remove(em.merge(serviciossala));
        } catch (Exception e) {
            System.out.println("Error eliminarServiciosSala ServiciosSalaDAO : " + e.toString());
        }
    }

    @Override
    public List<ServiciosSala> consultarServiciosSala() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ServiciosSala p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ServiciosSala> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarAreasProfundizacion ServiciosSalaDAO : " + e.toString());
            return null;
        }
    }
    @Override
    public List<ServiciosSala> consultarServiciosSalaActivos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ServiciosSala p WHERE p.estado=true");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ServiciosSala> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarAreasProfundizacion ServiciosSalaDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public ServiciosSala buscarServiciosSalaPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ServiciosSala p WHERE p.idserviciossala=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            ServiciosSala registro = (ServiciosSala) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarServiciosSalaPorID ServiciosSalaDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public ServiciosSala buscarServiciosSalaPorCodigo(String codigo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ServiciosSala p WHERE p.codigoservicio=:codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            ServiciosSala registro = (ServiciosSala) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarServiciosSalaPorCodigo ServiciosSalaDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ServiciosSala> buscarServiciosSalaPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(ServiciosSala.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".nombreservicio ASC";
            System.out.println("consulta : " + consulta);
            TypedQuery<ServiciosSala> tq = em.createQuery(consulta, ServiciosSala.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarAreasProfundizacionPorFiltrado ServiciosSalaDAO : " + e.toString());
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
                                .append(".nombreservicio")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroCodigo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".codigoservicio")
                                .append(") Like :parametroCodigo");
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

    private TypedQuery<ServiciosSala> asignarValores(TypedQuery<ServiciosSala> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroNombre".equals(entry.getKey()))
                        || ("parametroCodigo".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroEstado".equals(entry.getKey())) {
                    //
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }

            }
        }
        return tq;
    }
}
