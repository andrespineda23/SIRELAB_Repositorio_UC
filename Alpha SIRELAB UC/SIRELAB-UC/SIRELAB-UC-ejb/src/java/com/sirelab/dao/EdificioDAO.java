package com.sirelab.dao;

import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.entidades.Edificio;
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
public class EdificioDAO implements EdificioDAOInterface {

    static Logger logger = Logger.getLogger(EdificioDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearEdificio(Edificio edificio) {
        try {
            em.clear();
            em.persist(edificio);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearEdificio EdificioDAO : " + e.toString());
        }
    }

    @Override
    public void editarEdificio(Edificio edificio) {
        try {
            em.clear();
            em.merge(edificio);
        } catch (Exception e) {
            logger.error("Error editarEdificio EdificioDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarEdificio(Edificio edificio) {
        try {
            em.clear();
            em.remove(em.merge(edificio));
        } catch (Exception e) {
            logger.error("Error eliminarEdificio EdificioDAO : " + e.toString());
        }
    }

    @Override
    public List<Edificio> consultarEdificios() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Edificio p WHERE p.estado=TRUE");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Edificio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEdificios EdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public Edificio buscarEdificioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Edificio p WHERE p.idedificio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Edificio registro = (Edificio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEdificioPorID EdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Edificio> buscarEdificiosPorIDSede(BigInteger idSede) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Edificio p WHERE p.sede.idsede=:idSede");
            query.setParameter("idSede", idSede);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Edificio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error buscarEdificiosPorIDSede EdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Edificio> buscarEdificiosActivosPorIDSede(BigInteger idSede) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Edificio p WHERE p.sede.idsede=:idSede AND p.estado=true");
            query.setParameter("idSede", idSede);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Edificio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error buscarEdificiosPorIDSede EdificioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Edificio> buscarEdificiosPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Edificio.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".descripcionedificio ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<Edificio> tq = em.createQuery(consulta, Edificio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarEdificiosPorFiltrado EdificioDAO : " + e.toString());
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
                    if ("parametroDireccion".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".direccion")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroDescripcion".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".descripcionedificio")
                                .append(") Like :parametroDireccion");
                        camposFiltro++;
                    }
                    if ("parametroSede".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "sede.idsede");
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

    private TypedQuery<Edificio> asignarValores(TypedQuery<Edificio> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroDireccion".equals(entry.getKey()))
                        || ("parametroDescripcion".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroSede".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
                if ("parametroEstado".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
            }
        }
        return tq;
    }

}
