package com.sirelab.dao;

import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.entidades.Docente;
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
public class DocenteDAO implements DocenteDAOInterface {

    static Logger logger = Logger.getLogger(DocenteDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearDocente(Docente docente) {
        try {
            em.persist(docente);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearDocente DocenteDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarDocente(Docente docente) {
        try {
            em.merge(docente);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarDocente DocenteDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarDocente(Docente docente) {
        try {
            em.remove(em.merge(docente));
        } catch (Exception e) {
            logger.error("Error eliminarDocente DocenteDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<Docente> consultarDocentes() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Docente p ORDER BY p.persona.identificacionpersona ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Docente> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarDocentes DocenteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Docente buscarDocentePorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Docente p WHERE p.iddocente=:idRegistro ");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Docente registro = (Docente) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarDocentePorID DocenteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Docente buscarDocentePorIDPersona(BigInteger idPersona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Docente p WHERE p.persona.idpersona=:idPersona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idPersona", idPersona);
            Docente registro = (Docente) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarDocentePorIDPersona DocenteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Docente obtenerDocentePorCorreoDocumento(String correo, String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Docente p WHERE p.persona.emailpersona=:correo AND p.persona.identificacionpersona=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("documento", documento);
            query.setParameter("correo", correo);
            Docente registro = (Docente) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error obtenerDocentePorCorreoDocumento DocenteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Docente obtenerDocentePorCorreo(String correo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Docente p WHERE p.persona.emailpersona=:correo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            Docente registro = (Docente) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error obtenerDocentePorCorreo DocenteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Docente obtenerDocentePorDocumento(String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Docente p WHERE p.persona.identificacionpersona=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("documento", documento);
            Docente registro = (Docente) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error obtenerDocentePorDocumento DocenteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Docente> buscarDocentesPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Docente.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".persona.identificacionpersona ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<Docente> tq = em.createQuery(consulta, Docente.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarDocentesPorFiltrado DocenteDAO : " + e.toString(),e);
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
                    if ("parametroCargo".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "cargo.idtipocargo");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroFacultad".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "departamento.facultad.idfacultad");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroDepartamento".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "departamento.iddepartamento");
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

    private TypedQuery<Docente> asignarValores(TypedQuery<Docente> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroDepartamento".equals(entry.getKey()))
                        || ("parametroFacultad".equals(entry.getKey()))
                        || ("parametroCargo".equals(entry.getKey()))) {
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
