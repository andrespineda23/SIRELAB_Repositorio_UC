/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.entidades.EquipoElemento;
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
public class EquipoElementoDAO implements EquipoElementoDAOInterface {

    static Logger logger = Logger.getLogger(EquipoElementoDAO.class);

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearEquipoElemento(EquipoElemento equipoelemento) {
        try {
            em.persist(equipoelemento);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearEquipoElemento EquipoElementoDAO : " + e.toString(), e);
        }
    }

    @Override
    public void editarEquipoElemento(EquipoElemento equipoelemento) {
        try {
            em.merge(equipoelemento);
            em.flush();
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarEquipoElemento EquipoElementoDAO : " + e.toString(), e);
        }
    }

    @Override
    public void eliminarEquipoElemento(EquipoElemento equipoelemento) {
        try {
            em.remove(em.merge(equipoelemento));
            em.flush();
        } catch (Exception e) {
            logger.error("Error eliminarEquipoElemento EquipoElementoDAO : " + e.toString(), e);
        }
    }

    @Override
    public List<EquipoElemento> consultarEquiposElementos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EquipoElemento p ORDER BY p.modulolaboratorio.salalaboratorio.laboratorio.idlaboratorio ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EquipoElemento> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEquiposElementos EquipoElementoDAO : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<EquipoElemento> consultarEquiposElementosPorModulo(BigInteger modulo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EquipoElemento p WHERE p.modulolaboratorio.idmodulolaboratorio =:modulo");
            query.setParameter("modulo", modulo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EquipoElemento> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEquiposElementosPorModulo EquipoElementoDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<EquipoElemento> consultarEquiposElementosBodegaPublicos(BigInteger sala) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EquipoElemento p WHERE p.modulolaboratorio.salalaboratorio.idsalalaboratorio=:sala AND p.privado=false ORDER BY p.inventarioequipo ASC");
            query.setParameter("sala", sala);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EquipoElemento> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEquiposElementosBodegaPublicos EquipoElementoDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EquipoElemento buscarEquipoElementoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EquipoElemento p WHERE p.idequipoelemento=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            EquipoElemento registro = (EquipoElemento) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEquipoElementoPorID EquipoElementoDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EquipoElemento buscarEquipoElementoPorCodigoYModulo(String codigo, BigInteger modulo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EquipoElemento p WHERE p.inventarioequipo=:codigo AND p.modulolaboratorio.idmodulolaboratorio=:modulo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            query.setParameter("modulo", modulo);
            EquipoElemento registro = (EquipoElemento) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEquipoElementoPorCodigoYModulo EquipoElementoDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<EquipoElemento> buscarEquiposElementosPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(EquipoElemento.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".inventarioequipo ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<EquipoElemento> tq = em.createQuery(consulta, EquipoElemento.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarEquiposElementosPorFiltrado EquipoElementoDAO : " + e.toString(), e);
            return null;
        }
    }

    private String adicionarFiltros(String jpql, Map<String, String> filters, String alias) {
        final StringBuilder wheres = new StringBuilder();
        int camposFiltro = 0;
        logger.error("filters : " + filters);
        if (null != filters && !filters.isEmpty()) {
            wheres.append(" WHERE ");
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                    if (camposFiltro > 0) {
                        wheres.append(" AND ");
                    }
                    if ("parametroNombre".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".nombreequipo")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroInventario".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".inventarioequipo")
                                .append(") Like :parametroInventario");
                        camposFiltro++;
                    }
                    if ("parametroMarca".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".marcaequipo")
                                .append(") Like :parametroMarca");
                        camposFiltro++;
                    }
                    if ("parametroSerie".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".seriequipo")
                                .append(") Like :parametroSerie");
                        camposFiltro++;
                    }
                    if ("parametroModelo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".modeloequipo")
                                .append(") Like :parametroModelo");
                        camposFiltro++;
                    }
                    if ("parametroEstado".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "estadoequipo.idestadoequipo");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroModuloLaboratorio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "modulolaboratorio.idmodulolaboratorio");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroSalaLaboratorio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "modulolaboratorio.salalaboratorio.idsalalaboratorio");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroLaboratorio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "modulolaboratorio.salalaboratorio.laboratorio.idlaboratorio");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroTipoActivo".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "tipoactivo.idtipoactivo");
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

    private TypedQuery<EquipoElemento> asignarValores(TypedQuery<EquipoElemento> tq, Map<String, String> filters) {
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroNombre".equals(entry.getKey()))
                        || ("parametroInventario".equals(entry.getKey()))
                        || ("parametroMarca".equals(entry.getKey()))
                        || ("parametroSerie".equals(entry.getKey()))
                        || ("parametroModelo".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
                }
                if (("parametroTipoActivo".equals(entry.getKey()))
                        || ("parametroModuloLaboratorio".equals(entry.getKey()))
                        || ("parametroSalaLaboratorio".equals(entry.getKey()))
                        || ("parametroLaboratorio".equals(entry.getKey()))
                        || ("parametroTipoActivo".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
            }
        }
        return tq;
    }

    @Override
    public EquipoElemento obtenerUltimaEquipoElementoRegistrada() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EquipoElemento p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EquipoElemento> registros = query.getResultList();
            if (registros != null) {
                int tam = registros.size();
                EquipoElemento ultimoRegistro = registros.get(tam - 1);
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error obtenerUltimaEquipoElementoRegistrada EquipoElementoDAO : " + e.toString(), e);
            return null;
        }
    }
}
