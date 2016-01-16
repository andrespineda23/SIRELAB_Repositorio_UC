package com.sirelab.dao;

import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.entidades.PlanEstudios;
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
public class PlanEstudiosDAO implements PlanEstudiosDAOInterface {

    static Logger logger = Logger.getLogger(PlanEstudiosDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearPlanEstudios(PlanEstudios planestudios) {
        try {
            em.persist(planestudios);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearPlanEstudios PlanEstudiosDAO : " + e.toString());
        }
    }

    @Override
    public void editarPlanEstudios(PlanEstudios planestudios) {
        try {
            em.merge(planestudios);
        } catch (Exception e) {
            logger.error("Error editarPlanEstudios PlanEstudiosDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarPlanEstudios(PlanEstudios planestudios) {
        try {
            em.remove(em.merge(planestudios));
        } catch (Exception e) {
            logger.error("Error eliminarPlanEstudios PlanEstudiosDAO : " + e.toString());
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanesEstudios() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PlanEstudios p WHERE p.estado=TRUE");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PlanEstudios> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarPlanesEstudios PlanEstudiosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public PlanEstudios buscarPlanEstudiosPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PlanEstudios p WHERE p.idplanestudios=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            PlanEstudios registro = (PlanEstudios) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarPlanEstudiosPorID PlanEstudiosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public PlanEstudios buscarPlanEstudiosPorCodigoYCarrera(String codigo, BigInteger carrera) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PlanEstudios p WHERE p.codigoplanestudio=:codigo AND p.carrera.idcarrera=:carrera");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            query.setParameter("carrera", carrera);
            PlanEstudios registro = (PlanEstudios) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarPlanEstudiosPorCodigoYCarrera PlanEstudiosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public PlanEstudios buscarPlanEstudiosPorCodigo(String codigo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PlanEstudios p WHERE p.codigoplanestudio=:codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            PlanEstudios registro = (PlanEstudios) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarPlanEstudiosPorCodigo PlanEstudiosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanesEstudiosPorCarrera(BigInteger idCarrera) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PlanEstudios p WHERE p.carrera.idcarrera=:idCarrera");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idCarrera", idCarrera);
            List<PlanEstudios> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarPlanesEstudiosPorCarrera PlanEstudiosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanesEstudiosActivosPorCarrera(BigInteger idCarrera) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PlanEstudios p WHERE p.carrera.idcarrera=:idCarrera AND p.estado=true");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idCarrera", idCarrera);
            List<PlanEstudios> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarPlanesEstudiosActivosPorCarrera PlanEstudiosDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PlanEstudios> buscarPlanesEstudiosPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(PlanEstudios.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".codigoplanestudio ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<PlanEstudios> tq = em.createQuery(consulta, PlanEstudios.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarPlanesEstudiosPorFiltrado PlanEstudiosDAO : " + e.toString());
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
                                .append(".nombreplanestudio")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroCodigo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".codigoplanestudio")
                                .append(") Like :parametroCodigo");
                        camposFiltro++;
                    }
                    if ("parametroCarrera".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "carrera.idcarrera");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroDepartamento".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "carrera.departamento.iddepartamento");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroFacultad".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "carrera.departamento.facultad.idfacultad");
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

    private TypedQuery<PlanEstudios> asignarValores(TypedQuery<PlanEstudios> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroNombre".equals(entry.getKey()))
                        || ("parametroCodigo".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if (("parametroDepartamento".equals(entry.getKey()))
                        || ("parametroCarrera".equals(entry.getKey()))
                        || ("parametroFacultad".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
                if ("parametroEstado".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
            }
        }
        return tq;
    }

    @Override
    public PlanEstudios obtenerUltimaPlanEstudiosRegistrada() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PlanEstudios p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PlanEstudios> registros = query.getResultList();
            if (registros != null) {
                int tam = registros.size();
                PlanEstudios ultimoRegistro = registros.get(tam - 1);
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error obtenerUltimaPlanEstudiosRegistrada PlanEstudiosDAO : " + e.toString());
            return null;
        }
    }
}
