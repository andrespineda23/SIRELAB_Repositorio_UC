package com.sirelab.dao;

import com.sirelab.dao.interfacedao.AsignaturaDAOInterface;
import com.sirelab.entidades.Asignatura;
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
public class AsignaturaDAO implements AsignaturaDAOInterface {

    
    static Logger logger = Logger.getLogger(AsignaturaDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearAsignatura(Asignatura asignatura) {
        try {
            em.clear();
            em.persist(asignatura);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearAsignatura AsignaturaDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarAsignatura(Asignatura asignatura) {
        try {
            em.clear();
            em.merge(asignatura);
        } catch (Exception e) {
            logger.error("Error editarAsignatura AsignaturaDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarAsignatura(Asignatura asignatura) {
        try {
            em.clear();
            em.remove(em.merge(asignatura));
        } catch (Exception e) {
            logger.error("Error eliminarAsignatura AsignaturaDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<Asignatura> consultarAsignaturas() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Asignatura p ORDER BY p.codigoasignatura ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Asignatura> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarAsignaturas AsignaturaDAO : " + e.toString(),e);
            return null;
        }
    }
    @Override
    public List<Asignatura> consultarAsignaturasActivos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Asignatura p WHERE p.estado=true  ORDER BY p.codigoasignatura ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Asignatura> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarAsignaturas AsignaturaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Asignatura buscarAsignaturaPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Asignatura p WHERE p.idasignatura=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Asignatura registro = (Asignatura) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarAsignaturaPorID AsignaturaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Asignatura buscarAsignaturaPorCodigo(String codigo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Asignatura p WHERE p.codigoasignatura=:codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            Asignatura registro = (Asignatura) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarAsignaturaPorCodigo AsignaturaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Asignatura> buscarAsignaturasPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Asignatura.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".codigoasignatura ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<Asignatura> tq = em.createQuery(consulta, Asignatura.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarAsignaturasPorFiltrado AsignaturaDAO : " + e.toString(),e);
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
                                .append(".nombreasignatura")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroCodigo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".codigoasignatura")
                                .append(") Like :parametroCodigo");
                        camposFiltro++;
                    }
                    if ("parametroCreditos".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "numerocreditos");
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

    private TypedQuery<Asignatura> asignarValores(TypedQuery<Asignatura> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if ("parametroNombre".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroCodigo".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroCreditos".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Integer.valueOf(entry.getValue()));
                }
                if ("parametroEstado".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
            }
        }
        return tq;
    }

    @Override
    public Asignatura obtenerUltimaAsignaturaRegistrada() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Asignatura p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Asignatura> registros = query.getResultList();
            if (registros != null) {
                int tam = registros.size();
                Asignatura ultimoRegistro = registros.get(tam - 1);
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error obtenerUltimaAsignaturaRegistrada AsignaturaDAO : " + e.toString(),e);
            return null;
        }
    }
}
