/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.SalaLaboratorioxServiciosDAOInterface;
import com.sirelab.entidades.SalaLaboratorioxServicios;
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
 * @author AndresPineda
 */
@Stateless
public class SalaLaboratorioXServiciosDAO implements SalaLaboratorioxServiciosDAOInterface {

    static Logger logger = Logger.getLogger(SalaLaboratorioXServiciosDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearSalaLaboratorioxServicios(SalaLaboratorioxServicios salalaboratorioxarea) {
        try {
            em.persist(salalaboratorioxarea);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearSalaLaboratorioxServicios SalaLaboratorioxServiciosDAO : " + e.toString());
        }
    }

    @Override
    public void editarSalaLaboratorioxServicios(SalaLaboratorioxServicios salalaboratorioxarea) {
        try {
            em.merge(salalaboratorioxarea);
        } catch (Exception e) {
            logger.error("Error editarSalaLaboratorioxServicios SalaLaboratorioxServiciosDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarSalaLaboratorioxServicios(SalaLaboratorioxServicios salalaboratorioxarea) {
        try {
            em.remove(em.merge(salalaboratorioxarea));
        } catch (Exception e) {
            logger.error("Error eliminarSalaLaboratorioxServicios SalaLaboratorioxServiciosDAO : " + e.toString());
        }
    }

    @Override
    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServicios() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorioxServicios p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SalaLaboratorioxServicios> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarSalaLaboratorioxServiciossPorAreas SalaLaboratorioxServiciosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServiciosActivos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorioxServicios p WHERE p.estado=true");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SalaLaboratorioxServicios> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarSalaLaboratorioxServiciossPorAreas SalaLaboratorioxServiciosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServiciosPorSala(BigInteger sala) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorioxServicios p WHERE p.salalaboratorio.idsalalaboratorio=:sala");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("sala", sala);
            List<SalaLaboratorioxServicios> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarSalaLaboratorioxServiciosPorSala SalaLaboratorioxServiciosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServiciosPorServicio(BigInteger servicio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorioxServicios p WHERE p.serviciosala=:servicio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("servicio", servicio);
            List<SalaLaboratorioxServicios> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarSalaLaboratorioxServiciosPorServicio SalaLaboratorioxServiciosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public SalaLaboratorioxServicios buscarSalaLaboratorioxServiciosPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorioxServicios p WHERE p.idsalalaboratorioxservicios=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            SalaLaboratorioxServicios registro = (SalaLaboratorioxServicios) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalaLaboratorioxServiciosPorID SalaLaboratorioxServiciosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public SalaLaboratorioxServicios buscarSalaLaboratorioxServiciosPorSalayServicio(BigInteger sala, BigInteger servicio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorioxServicios p WHERE p.salalaboratorio.idsalalaboratorio=:sala AND p.serviciosala.idserviciossala=:servicio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("sala", sala);
            query.setParameter("servicio", servicio);
            SalaLaboratorioxServicios registro = (SalaLaboratorioxServicios) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalaLaboratorioxServiciosPorLabYArea SalaLaboratorioxServiciosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorioxServicios> buscarSalaLaboratorioxServiciosPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(SalaLaboratorioxServicios.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            logger.error("jpql2.toString() : " + jpql2.toString());
            TypedQuery<SalaLaboratorioxServicios> tq = em.createQuery(jpql2.toString(), SalaLaboratorioxServicios.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarSalaLaboratorioxServiciossPorFiltrado SalaLaboratorioxServiciosDAO : " + e.toString());
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
                        wheres.append(alias).append("." + "salalaboratorio.laboratorio.departamento.iddepartamento");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroLaboratorio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "salalaboratorio.laboratorio.idlaboratorio");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroServicio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "serviciosala.idserviciossala");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroSala".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "salalaboratorio.idsalalaboratorio");
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
        logger.error(jpql);
        if (jpql.trim()
                .endsWith("WHERE")) {
            jpql = jpql.replace("WHERE", "");
        }
        return jpql;
    }

    private TypedQuery<SalaLaboratorioxServicios> asignarValores(TypedQuery<SalaLaboratorioxServicios> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroServicio".equals(entry.getKey()))
                        || ("parametroSala".equals(entry.getKey()))
                        || ("parametroDepartamento".equals(entry.getKey()))
                        || ("parametroLaboratorio".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
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
