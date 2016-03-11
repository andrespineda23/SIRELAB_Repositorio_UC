package com.sirelab.dao;

import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.entidades.EntidadExterna;
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
public class EntidadExternaDAO implements EntidadExternaDAOInterface {

    static Logger logger = Logger.getLogger(EntidadExternaDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearEntidadExterna(EntidadExterna entidadexterna) {
        try {
            em.persist(entidadexterna);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearEntidadExterna EntidadExternaDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarEntidadExterna(EntidadExterna entidadexterna) {
        try {
            em.merge(entidadexterna);
        } catch (Exception e) {
            logger.error("Error editarEntidadExterna EntidadExternaDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarEntidadExterna(EntidadExterna entidadexterna) {
        try {
            em.remove(em.merge(entidadexterna));
        } catch (Exception e) {
            logger.error("Error eliminarEntidadExterna EntidadExternaDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<EntidadExterna> consultarEntidadesExternas() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EntidadExterna p ORDER BY p.identidadexterna ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EntidadExterna> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEntidadExternas EntidadExternaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EntidadExterna> consultarEntidadesExternasActivas() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EntidadExterna p WHERE p.estado=true ORDER BY p.identidadexterna ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EntidadExterna> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEntidadExternas EntidadExternaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EntidadExterna buscarEntidadExternaPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EntidadExterna p WHERE p.identidadexterna=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            EntidadExterna registro = (EntidadExterna) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEntidadExternaPorID EntidadExternaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EntidadExterna buscarEntidadExternaPorIdentificacionEntidad(String identificacion) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EntidadExterna p WHERE p.identificacion=:identificacion");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("identificacion", identificacion);
            EntidadExterna registro = (EntidadExterna) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEntidadExternaPorIdentificacionEntidad EntidadExternaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EntidadExterna buscarEntidadExternaPorCorreoNumDocumento(String correo, String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EntidadExterna p WHERE p.emailentidad=:correo AND p.identificacion=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            query.setParameter("documento", documento);
            EntidadExterna registro = (EntidadExterna) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEntidadExternaPorCorreoNumDocumento EntidadExternaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EntidadExterna buscarEntidadExternaPorCorreo(String correo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EntidadExterna p WHERE p.emailentidad=:correo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            EntidadExterna registro = (EntidadExterna) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEntidadExternaPorCorreo EntidadExternaDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EntidadExterna> buscarEntidadesExternasPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(EntidadExterna.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".identidadexterna ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<EntidadExterna> tq = em.createQuery(consulta, EntidadExterna.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarEntidadesExternasPorFiltrado EntidadExternaDAO : " + e.toString(),e);
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
                                .append(".nombreentidad")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroSector".equals(entry.getKey())) {
                        wheres.append(alias)
                                .append(".sector.idsectorentidad")
                                .append(" =:parametroSector");
                        camposFiltro++;
                    }
                    if ("parametroDocumento".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".identificacion")
                                .append(") Like :parametroDocumento");
                        camposFiltro++;
                    }
                    if ("parametroCorreo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".emailentidad")
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

    private TypedQuery<EntidadExterna> asignarValores(TypedQuery<EntidadExterna> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroCorreo".equals(entry.getKey()))
                        || ("parametroDocumento".equals(entry.getKey()))
                        || ("parametroNombre".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if (("parametroEstado".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
                if (("parametroSector".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
            }
        }
        return tq;
    }
}
