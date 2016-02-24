package com.sirelab.dao;

import com.sirelab.dao.interfacedao.EstudianteDAOInterface;
import com.sirelab.entidades.Estudiante;
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
public class EstudianteDAO implements EstudianteDAOInterface {

    static Logger logger = Logger.getLogger(EstudianteDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearEstudiante(Estudiante estudiante) {
        try {
            em.clear();
            em.persist(estudiante);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearEstudiante EstudianteDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarEstudiante(Estudiante estudiante) {
        try {
            logger.error("Estado : " + estudiante.getPersona().getUsuario().getEstado());
            estudiante.getPersona().setNombrespersona("Pruebaa");
            em.merge(estudiante);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarEstudiante EstudianteDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarEstudiante(Estudiante estudiante) {
        try {
            em.clear();
            em.remove(em.merge(estudiante));
        } catch (Exception e) {
            logger.error("Error eliminarEstudiante EstudianteDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<Estudiante> consultarEstudiantes() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Estudiante p ORDER BY p.persona.identificacionpersona ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Estudiante> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEstudiantes EstudianteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Estudiante buscarEstudiantePorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Estudiante p WHERE p.idestudiante=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Estudiante registro = (Estudiante) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEstudiantePorID EstudianteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Estudiante buscarEstudiantePorDocumento(String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Estudiante p WHERE p.persona.identificacionpersona=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("documento", documento);
            Estudiante registro = (Estudiante) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEstudiantePorDocumento EstudianteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Estudiante buscarEstudiantePorCorreo(String correo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Estudiante p WHERE p.persona.emailpersona=:correo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            Estudiante registro = (Estudiante) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEstudiantePorCorreo EstudianteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Estudiante buscarEstudiantePorIDPersona(BigInteger idPersona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Estudiante p WHERE p.persona.idpersona=:idPersona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idPersona", idPersona);
            Estudiante registro = (Estudiante) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEstudiantePorIDPersona EstudianteDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Estudiante> buscarEstudiantesPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(Estudiante.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".persona.identificacionpersona ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<Estudiante> tq = em.createQuery(consulta, Estudiante.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarEstudiantesPorFiltrado EstudianteDAO : " + e.toString(),e);
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
                    if ("parametroSemestre".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "semestreestudiante");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroTipo".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "tipoestudiante");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroDepartamento".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "planestudio.carrera.departamento.iddepartamento");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroCarrera".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "planestudio.carrera.idcarrera");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroPlanEst".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "planestudio.idplanestudios");
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

    private TypedQuery<Estudiante> asignarValores(TypedQuery<Estudiante> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroDepartamento".equals(entry.getKey()))
                        || ("parametroCarrera".equals(entry.getKey()))
                        || ("parametroPlanEst".equals(entry.getKey()))) {
                    //
                    tq.setParameter(entry.getKey(), new BigInteger(entry.getValue()));
                }
                if ("parametroCorreo".equals(entry.getKey())) {
                    //
                    tq.setParameter(entry.getKey(), "%" + entry.getValue().toUpperCase() + "%");
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
                if (("parametroSemestre".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), Integer.valueOf(entry.getValue()));
                }
                if (("parametroTipo".equals(entry.getKey()))) {
                    tq.setParameter(entry.getKey(), Integer.valueOf(entry.getValue()));
                }
            }
        }
        return tq;
    }

    @Override
    public Estudiante buscarEstudiantePorDocumentoYCorreo(String correo, String documento) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Estudiante p WHERE p.persona.emailpersona=:correo AND p.persona.identificacionpersona=:documento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            query.setParameter("documento", documento);
            Estudiante registro = (Estudiante) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarEstudiantePorDocumentoYCorreo EstudianteDAO : " + e.toString(),e);
            return null;
        }
    }

}
