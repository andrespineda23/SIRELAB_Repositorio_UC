package com.sirelab.dao;

import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.entidades.EncargadoLaboratorio;
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
public class EncargadoLaboratorioDAO implements EncargadoLaboratorioDAOInterface {

    static Logger logger = Logger.getLogger(EncargadoLaboratorioDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearEncargadoLaboratorio(EncargadoLaboratorio encargadolaboratorio) {
        try {
            em.persist(encargadolaboratorio);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearEncargadoLaboratorio EncargadoLaboratorioDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarEncargadoLaboratorio(EncargadoLaboratorio encargadolaboratorio) {
        try {
            em.merge(encargadolaboratorio);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarEncargadoLaboratorio EncargadoLaboratorioDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarEncargadoLaboratorio(EncargadoLaboratorio encargadolaboratorio) {
        try {
            em.remove(em.merge(encargadolaboratorio));
        } catch (Exception e) {
            logger.error("Error eliminarEncargadoLaboratorio EncargadoLaboratorioDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<EncargadoLaboratorio> consultarEncargadosLaboratorios() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoLaboratorio p ORDER BY p.persona.identificacionpersona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EncargadoLaboratorio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEncargadoLaboratorios EncargadoLaboratorioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio buscarEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            logger.error("buscarEncargadoLaboratorioPorID: "+idRegistro);
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoLaboratorio p WHERE p.idencargadolaboratorio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            EncargadoLaboratorio registro = (EncargadoLaboratorio) query.getSingleResult();
            logger.error("registro: "+registro);
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEncargadoLaboratorioPorID EncargadoLaboratorioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio buscarEncargadoLaboratorioPorIDPersona(BigInteger idPersona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoLaboratorio p WHERE p.persona.idpersona=:idPersona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idPersona", idPersona);
            EncargadoLaboratorio registro = (EncargadoLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEncargadoLaboratorioPorIDPersona EncargadoLaboratorioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio buscarEncargadoLaboratorioPorPorCorreoNumDocumento(String correo, String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoLaboratorio p WHERE p.persona.emailpersona=:correo AND p.persona.identificacionpersona=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            query.setParameter("documento", documento);
            EncargadoLaboratorio registro = (EncargadoLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEncargadoLaboratorioPorPorCorreoNumDocumento EncargadoLaboratorioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio buscarEncargadoLaboratorioPorPorCorreo(String correo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoLaboratorio p WHERE p.persona.emailpersona=:correo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            EncargadoLaboratorio registro = (EncargadoLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEncargadoLaboratorioPorPorCorreo EncargadoLaboratorioDAO : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public EncargadoLaboratorio buscarEncargadoLaboratorioPorPorDocumento(String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM EncargadoLaboratorio p WHERE p.persona.identificacionpersona=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("documento", documento);
            EncargadoLaboratorio registro = (EncargadoLaboratorio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEncargadoLaboratorioPorPorDocumento EncargadoLaboratorioDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EncargadoLaboratorio> buscarEncargadosLaboratoriosPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(EncargadoLaboratorio.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".persona.identificacionpersona ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<EncargadoLaboratorio> tq = em.createQuery(consulta, EncargadoLaboratorio.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarEncargadosLaboratoriosPorFiltrado EncargadoLaboratorioDAO : " + e.toString(),e);
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
                    if ("parametroFacultad".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "laboratorio.departamento.facultad.idfacultad");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroDepartamento".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "laboratorio.departamento.iddepartamento");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroLaboratorio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "laboratorio.idlaboratorio");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroEstado".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "persona.usuario.estado");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroNombre".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".persona.nombrespersona")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroApellido".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".persona.apellidospersona")
                                .append(") Like :parametroApellido");
                        camposFiltro++;
                    }
                    if ("parametroDocumento".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".persona.identificacionpersona")
                                .append(") Like :parametroDocumento");
                        camposFiltro++;
                    }
                    if ("parametroCorreo".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".persona.emailpersona")
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

    private TypedQuery<EncargadoLaboratorio> asignarValores(TypedQuery<EncargadoLaboratorio> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroFacultad".equals(entry.getKey()))
                        || ("parametroDepartamento".equals(entry.getKey()))
                        || ("parametroLaboratorio".equals(entry.getKey()))) {
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
