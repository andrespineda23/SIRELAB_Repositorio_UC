package com.sirelab.dao;

import com.sirelab.dao.interfacedao.AreaProfundizacionDAOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.log4j.*;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateless
public class AreaProfundizacionDAO implements AreaProfundizacionDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public void crearAreaProfundizacion(AreaProfundizacion areaprofundizacion) {
        try {
            BasicConfigurator.configure();
            em.persist(areaprofundizacion);
            logger.info("Mensaje Info");
        } catch (Exception e) {
            System.out.println("Error crearAreaProfundizacion AreaProfundizacionDAO : " + e.toString());
        }
    }

    @Override
    public void editarAreaProfundizacion(AreaProfundizacion areaprofundizacion) {
        try {
            em.merge(areaprofundizacion);
        } catch (Exception e) {
            System.out.println("Error editarAreaProfundizacion AreaProfundizacionDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarAreaProfundizacion(AreaProfundizacion areaprofundizacion) {
        try {
            em.remove(em.merge(areaprofundizacion));
        } catch (Exception e) {
            System.out.println("Error eliminarAreaProfundizacion AreaProfundizacionDAO : " + e.toString());
        }
    }

    @Override
    public List<AreaProfundizacion> consultarAreasProfundizacion() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AreaProfundizacion p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<AreaProfundizacion> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarAreasProfundizacion AreaProfundizacionDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public AreaProfundizacion buscarAreaProfundizacionPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AreaProfundizacion p WHERE p.idareaprofundizacion=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            AreaProfundizacion registro = (AreaProfundizacion) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarAreaProfundizacionPorID AreaProfundizacionDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public AreaProfundizacion buscarAreaProfundizacionPorCodigo(String codigo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AreaProfundizacion p WHERE p.codigoarea=:codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            AreaProfundizacion registro = (AreaProfundizacion) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarAreaProfundizacionPorCodigo AreaProfundizacionDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<AreaProfundizacion> buscarAreaProfundizacionPorIDLaboratorio(BigInteger idLaboratorio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM AreaProfundizacion p WHERE p.laboratorio.idlaboratorio=:idLaboratorio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idLaboratorio", idLaboratorio);
            List<AreaProfundizacion> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error buscarAreaProfundizacionPorIDLaboratorio AreaProfundizacionDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<AreaProfundizacion> buscarAreasProfundizacionPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(AreaProfundizacion.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".nombrearea ASC";
            System.out.println("consulta : " + consulta);
            TypedQuery<AreaProfundizacion> tq = em.createQuery(consulta, AreaProfundizacion.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarAreasProfundizacionPorFiltrado AreaProfundizacionDAO : " + e.toString());
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
                                .append(".nombrearea")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroCodigo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".codigoarea")
                                .append(") Like :parametroCodigo");
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

    private TypedQuery<AreaProfundizacion> asignarValores(TypedQuery<AreaProfundizacion> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroNombre".equals(entry.getKey()))
                        || ("parametroCodigo".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
            }
        }
        return tq;
    }
}
