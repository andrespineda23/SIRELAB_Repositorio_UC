package com.sirelab.dao;

import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.entidades.Carrera;
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
public class CarreraDAO implements CarreraDAOInterface {

    static Logger logger = Logger.getLogger(CarreraDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearCarrera(Carrera carrera) {
        try {
            em.persist(carrera);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearCarrera CarreraDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarCarrera(Carrera carrera) {
        try {
            em.merge(carrera);
        } catch (Exception e) {
            logger.error("Error editarCarrera CarreraDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarCarrera(Carrera carrera) {
        try {
            em.remove(em.merge(carrera));
        } catch (Exception e) {
            logger.error("Error eliminarCarrera CarreraDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<Carrera> consultarCarreras() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Carrera p ORDER BY p.codigocarrera ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Carrera> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarCarreras CarreraDAO : " + e.toString(),e);
            return null;
        }
    }
    @Override
    public List<Carrera> consultarCarrerasActivos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Carrera p WHERE p.estado=TRUE ORDER BY p.codigocarrera ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Carrera> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarCarreras CarreraDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Carrera buscarCarreraPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Carrera p WHERE p.idcarrera=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Carrera registro = (Carrera) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarCarreraPorID CarreraDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Carrera buscarCarreraPorCodigoYDepartamento(String codigo, BigInteger departamento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Carrera p WHERE p.codigocarrera=:codigo AND p.departamento.iddepartamento=:departamento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            query.setParameter("departamento", departamento);
            Carrera registro = (Carrera) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarCarreraPorCodigoYDepartamento CarreraDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Carrera buscarCarreraPorCodigo(String codigo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Carrera p WHERE p.codigocarrera=:codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            Carrera registro = (Carrera) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarCarreraPorCodigo CarreraDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Carrera> consultarCarrerasPorDepartamento(BigInteger idDepartamento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Carrera p WHERE p.departamento.iddepartamento=:idDepartamento ORDER BY p.codigocarrera ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idDepartamento", idDepartamento);
            List<Carrera> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarCarreras CarreraDAO : " + e.toString(),e);
            return null;
        }
    }
    @Override
    public List<Carrera> consultarCarrerasActivosPorDepartamento(BigInteger idDepartamento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Carrera p WHERE p.departamento.iddepartamento=:idDepartamento AND p.estado=true ORDER BY p.codigocarrera ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idDepartamento", idDepartamento);
            List<Carrera> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarCarreras CarreraDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Carrera> buscarCarrerasPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Carrera.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".codigocarrera ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<Carrera> tq = em.createQuery(consulta, Carrera.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarCarrerasPorFiltrado CarreraDAO : " + e.toString(),e);
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
                                .append(".nombrecarrera")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroCodigo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".codigocarrera")
                                .append(") Like :parametroCodigo");
                        camposFiltro++;
                    }
                    if ("parametroDepartamento".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "departamento.iddepartamento");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroFacultad".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "departamento.facultad.idfacultad");
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

    private TypedQuery<Carrera> asignarValores(TypedQuery<Carrera> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroNombre".equals(entry.getKey()))
                        || ("parametroCodigo".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if (("parametroDepartamento".equals(entry.getKey()))
                        || ("parametroFacultad".equals(entry.getKey()))) {
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
