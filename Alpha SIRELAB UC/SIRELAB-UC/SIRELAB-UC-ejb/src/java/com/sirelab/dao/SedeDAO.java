package com.sirelab.dao;

import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.entidades.Sede;
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
public class SedeDAO implements SedeDAOInterface {

    static Logger logger = Logger.getLogger(SedeDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearSede(Sede sede) {
        try {
            em.clear();
            em.persist(sede);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearSede SedeDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarSede(Sede sede) {
        try {
            em.clear();
            em.merge(sede);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarSede SedeDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarSede(Sede sede) {
        try {
            em.clear();
            em.remove(em.merge(sede));
        } catch (Exception e) {
            logger.error("Error eliminarSede SedeDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<Sede> consultarSedes() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Sede p ORDER BY p.nombresede ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Sede> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarSedes SedeDAO : " + e.toString(),e);
            return null;
        }
    }
    @Override
    public List<Sede> consultarSedesActivos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Sede p WHERE p.estado=TRUE ORDER BY p.nombresede ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Sede> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarSedes SedeDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Sede buscarSedePorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Sede p WHERE p.idsede=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Sede registro = (Sede) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSedePorID SedeDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Sede> buscarSedesPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Sede.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".nombresede ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<Sede> tq = em.createQuery(consulta, Sede.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarSedesPorFiltrado SedeDAO : " + e.toString(),e);
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
                                .append(".nombresede")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroDireccion".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".direccionsede")
                                .append(") Like :parametroDireccion");
                        camposFiltro++;
                    }
                    if ("parametroTelefono".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".telefonosede")
                                .append(") Like :parametroTelefono");
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

    private TypedQuery<Sede> asignarValores(TypedQuery<Sede> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroTelefono".equals(entry.getKey()))
                        || ("parametroDireccion".equals(entry.getKey()))
                        || ("parametroNombre".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroEstado".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
            }
        }
        return tq;
    }

}
