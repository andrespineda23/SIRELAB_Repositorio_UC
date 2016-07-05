/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.EncargadoPorEdificioDAOInterface;
import com.sirelab.entidades.EncargadoPorEdificio;
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
public class EncargadoPorEdificioDAO implements EncargadoPorEdificioDAOInterface {

    static Logger logger = Logger.getLogger(EncargadoPorEdificioDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearEncargadoPorEdificio(EncargadoPorEdificio encargado) {
        try {
            em.clear();
            em.persist(encargado);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearEdificio EncargadoPorEdificioDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarEncargadoPorEdificio(EncargadoPorEdificio encargado) {
        try {
            em.clear();
            em.merge(encargado);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarEdificio EncargadoPorEdificioDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarEncargadoPorEdificio(EncargadoPorEdificio encargado) {
        try {
            em.clear();
            em.remove(em.merge(encargado));
        } catch (Exception e) {
            logger.error("Error eliminarEdificio EncargadoPorEdificioDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<EncargadoPorEdificio> consultarEncargadosPorEdificio() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoPorEdificio p WHERE p.estado=TRUE ORDER BY p.administradoredificio.persona.identificacionpersona ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EncargadoPorEdificio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEncargadosPorEdificio EncargadoPorEdificioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EncargadoPorEdificio buscarEncargadoPorEdificioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoPorEdificio p WHERE p.idencargadoporedificio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            EncargadoPorEdificio registro = (EncargadoPorEdificio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEdificioPorID EncargadoPorEdificioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorIDEdificio(BigInteger idEdificio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoPorEdificio p WHERE p.edificio.idedificio=:idEdificio ORDER BY p.administradoredificio.persona.identificacionpersona ASC");
            query.setParameter("idEdificio", idEdificio);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EncargadoPorEdificio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error buscarEncargadosPorEdificioPorIDSede EncargadoPorEdificioDAO : " + e.toString(),e);
            return null;
        }
    }
    @Override
    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorIDAdministrador(BigInteger idAdministrador) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoPorEdificio p WHERE p.administradoredificio.idadministradoredificio=:idAdministrador ORDER BY p.administradoredificio.persona.identificacionpersona ASC");
            query.setParameter("idAdministrador", idAdministrador);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EncargadoPorEdificio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error buscarEncargadosPorEdificioPorIDAdministrador EncargadoPorEdificioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioActivosPorIDSede(BigInteger idEdificio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoPorEdificio p WHERE p.edificio.idedificio=:idEdificio AND p.estado=true ORDER BY p.administradoredificio.persona.identificacionpersona ASC");
            query.setParameter("idEdificio", idEdificio);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EncargadoPorEdificio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error buscarEncargadosPorEdificioPorIDSede EncargadoPorEdificioDAO : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(EncargadoPorEdificio.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".administradoredificio.persona.identificacionpersona ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<EncargadoPorEdificio> tq = em.createQuery(consulta, EncargadoPorEdificio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarAdministradoresEdificioPorFiltrado EncargadoPorEdificioDAO : " + e.toString(),e);
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
                    if ("parametroSede".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "edificio.sede.idsede");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroEdificio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "edificio.idedificio");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroEstado".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "administradoredificio.persona.usuario.estado");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroNombre".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".administradoredificio.persona.nombrespersona")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroApellido".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".administradoredificio.persona.apellidospersona")
                                .append(") Like :parametroApellido");
                        camposFiltro++;
                    }
                    if ("parametroDocumento".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".administradoredificio.persona.identificacionpersona")
                                .append(") Like :parametroDocumento");
                        camposFiltro++;
                    }
                    if ("parametroCorreo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".administradoredificio.persona.emailpersona")
                                .append(") Like :parametroCorreo");
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

    private TypedQuery<EncargadoPorEdificio> asignarValores(TypedQuery<EncargadoPorEdificio> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroSede".equals(entry.getKey()))
                        || ("parametroEdificio".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
                if (("parametroCorreo".equals(entry.getKey()))
                        || ("parametroDocumento".equals(entry.getKey()))
                        || ("parametroNombre".equals(entry.getKey()))
                        || ("parametroApellido".equals(entry.getKey()))) {
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
