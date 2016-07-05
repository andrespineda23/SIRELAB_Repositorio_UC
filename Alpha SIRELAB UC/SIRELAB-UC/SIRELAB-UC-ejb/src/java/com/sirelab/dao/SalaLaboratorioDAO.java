package com.sirelab.dao;

import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.entidades.SalaLaboratorio;
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
public class SalaLaboratorioDAO implements SalaLaboratorioDAOInterface {

    static Logger logger = Logger.getLogger(SalaLaboratorioDAO.class);

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearSalaLaboratorio(SalaLaboratorio salalaboratorio) {
        try {
            em.persist(salalaboratorio);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearSalaLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
        }
    }

    @Override
    public void editarSalaLaboratorio(SalaLaboratorio salalaboratorio) {
        try {
            em.merge(salalaboratorio);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarSalaLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
        }
    }

    @Override
    public void eliminarSalaLaboratorio(SalaLaboratorio salalaboratorio) {
        try {
            em.remove(em.merge(salalaboratorio));
        } catch (Exception e) {
            logger.error("Error eliminarSalaLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorios() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p ORDER BY p.codigosala ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SalaLaboratorio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarSalasLaboratorios SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public SalaLaboratorio buscarSalaLaboratorioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.idsalalaboratorio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            SalaLaboratorio registro = (SalaLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalaLaboratorioPorID SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> buscarSalasLaboratoriosPorEdificio(BigInteger edificio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.edificio.idedificio=:edificio ORDER BY p.codigosala ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("edificio", edificio);
            List<SalaLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalasLaboratoriosPorEdificio SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> buscarSalasLaboratoriosPorDepartamento(BigInteger departamento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.laboratorio.departamento.iddepartamento=:departamento ORDER BY p.codigosala ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("departamento", departamento);
            List<SalaLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalasLaboratoriosPorDepartamento SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public SalaLaboratorio buscarSalaLaboratorioPorCodigoyEdificioyLaboratorio(String codigo, BigInteger edificio, BigInteger laboratorio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.codigosala=:codigo AND p.edificio.idedificio=:edificio AND p.laboratorio.idlaboratorio=:laboratorio");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            query.setParameter("edificio", edificio);
            query.setParameter("laboratorio", laboratorio);
            SalaLaboratorio registro = (SalaLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalaLaboratorioPorEdificioyLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> buscarSalasLaboratoriosPorLaboratorio(BigInteger laboratorio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.laboratorio.idlaboratorio=:laboratorio ORDER BY p.codigosala ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("laboratorio", laboratorio);
            List<SalaLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalasLaboratoriosPorLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> buscarSalasLaboratoriosPorLaboratorioActivos(BigInteger laboratorio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.laboratorio.idlaboratorio=:laboratorio AND p.estadosala=true ORDER BY p.codigosala ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("laboratorio", laboratorio);
            List<SalaLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalasLaboratoriosPorLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> buscarSalasLaboratoriosPorLaboratorioActivosPublicos(BigInteger laboratorio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.laboratorio.idlaboratorio=:laboratorio AND p.estadosala=true AND p.salaprivada=false ORDER BY p.codigosala ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("laboratorio", laboratorio);
            List<SalaLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalasLaboratoriosPorLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> buscarBodegasPorLaboratorio(BigInteger laboratorio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.laboratorio.idlaboratorio=:laboratorio AND p.esbodega=true AND p.estadosala=true ORDER BY p.codigosala ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("laboratorio", laboratorio);
            List<SalaLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarBodegasPorLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> buscarSalasLaboratoriosPorLaboratorioActivosPublicosReserva(BigInteger laboratorio) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p WHERE p.laboratorio.idlaboratorio=:laboratorio AND p.esbodega=false AND p.estadosala=true AND p.salaprivada=false ORDER BY p.codigosala ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("laboratorio", laboratorio);
            List<SalaLaboratorio> registro = query.getResultList();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarSalasLaboratoriosPorLaboratorio SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> buscarSalasLaboratoriosPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(SalaLaboratorio.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".codigosala ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<SalaLaboratorio> tq = em.createQuery(consulta, SalaLaboratorio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarSalasLaboratoriosPorFiltrado SalaLaboratorioDAO : " + e.toString(), e);
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
                                .append(".nombresala")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroCodigo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".codigosala")
                                .append(") Like :parametroCodigo");
                        camposFiltro++;
                    }
                    if ("parametroEstado".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "estadosala");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroPrivada".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "salaprivada");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroCapacidad".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "capacidadsala");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroLaboratorio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "laboratorio.idlaboratorio");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroDepartamento".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "laboratorio.departamento.iddepartamento");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
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

    private TypedQuery<SalaLaboratorio> asignarValores(TypedQuery<SalaLaboratorio> tq, Map<String, String> filters) {
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroNombre".equals(entry.getKey()))
                        || ("parametroCodigo".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if ("parametroEstado".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
                if ("parametroPrivada".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Boolean.valueOf(entry.getValue()));
                }
                if ("parametroCapacidad".equals(entry.getKey())) {
                    tq.setParameter(entry.getKey(), Integer.parseInt(entry.getValue()));
                }
                if (("parametroEdificio".equals(entry.getKey()))
                        || ("parametroSede".equals(entry.getKey()))
                        || ("parametroLaboratorio".equals(entry.getKey()))
                        || ("parametroDepartamento".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
            }
        }
        return tq;
    }

    @Override
    public SalaLaboratorio obtenerUltimoSalaLaboratorioRegistrado() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SalaLaboratorio p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SalaLaboratorio> registros = query.getResultList();
            if (registros != null) {
                int tam = registros.size();
                SalaLaboratorio ultimoRegistro = registros.get(tam - 1);
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error obtenerUltimaSalaLaboratorioRegistrada SalaLaboratorioDAO : " + e.toString(), e);
            return null;
        }
    }

}
