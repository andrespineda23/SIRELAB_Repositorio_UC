/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.PersonaContactoDAOInterface;
import com.sirelab.entidades.PersonaContacto;
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
 * @author ELECTRONICA
 */
@Stateless
public class PersonaContactoDAO implements PersonaContactoDAOInterface {

    static Logger logger = Logger.getLogger(PersonaContactoDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearPersonaContacto(PersonaContacto personacontacto) {
        try {
            em.persist(personacontacto);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearPersonaContacto PersonaContactoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarPersonaContacto(PersonaContacto personacontacto) {
        try {
            em.merge(personacontacto);
        } catch (Exception e) {
            logger.error("Error editarPersonaContacto PersonaContactoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarPersonaContacto(PersonaContacto personacontacto) {
        try {
            em.remove(em.merge(personacontacto));
        } catch (Exception e) {
            logger.error("Error eliminarPersonaContacto PersonaContactoDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<PersonaContacto> consultarPersonasContacto() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p ORDER BY p.persona.identificacionpersona ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PersonaContacto> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarPersonasContacto PersonaContactoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public PersonaContacto buscarPersonaContactoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p WHERE p.idpersonacontacto=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            PersonaContacto registro = (PersonaContacto) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarPersonaContactoPorID PersonaContactoDAO : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public PersonaContacto buscarPersonaContactoPorUsuario(String usuario) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p WHERE p.persona.usuario.nombreusuario=:usuario");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("usuario", usuario);
            PersonaContacto registro = (PersonaContacto) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarPersonaContactoPorUsuario PersonaContactoDAO : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public PersonaContacto buscarPersonaContactoPorIDPersona(BigInteger idPersona) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p WHERE p.persona.idpersona=:idPersona");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idPersona", idPersona);
            PersonaContacto registro = (PersonaContacto) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarPersonaContactoPorIDPersona PersonaContactoDAO : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public PersonaContacto buscarPersonaContactoPorCorreo(String correo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p WHERE p.persona.emailpersona=:correo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("correo", correo);
            PersonaContacto registro = (PersonaContacto) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarPersonaContactoPorUsuario PersonaContactoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<PersonaContacto> consultarPersonasContactoPorConvenioEntidad(BigInteger convenioentidad) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PersonaContacto p WHERE p.convenioporentidad.idconvenioporentidad=:convenioentidad ORDER BY p.persona.identificacionpersona ASC");
            query.setParameter("convenioentidad", convenioentidad);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PersonaContacto> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarPersonasContactoPorConvenioEntidad PersonaContactoDAO : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public List<PersonaContacto> buscarPersonasContactoPorFiltrado(Map<String, String> filters) {
        try {
            final String alias = "a";
            final StringBuilder jpql = new StringBuilder();
            String jpql2;
            jpql.append("SELECT a FROM ").append(PersonaContacto.class.getSimpleName()).append(" " + alias);
            //
            jpql2 = adicionarFiltros(jpql.toString(), filters, alias);
            //
            String consulta = jpql2 + " " + "ORDER BY " + alias + ".persona.identificacionpersona ASC";
            logger.error("consulta : " + consulta);
            TypedQuery<PersonaContacto> tq = em.createQuery(consulta, PersonaContacto.class);
            tq = asignarValores(tq, filters);
            return tq.getResultList();
        } catch (Exception e) {
            logger.error("Error buscarPersonasContactoPorFiltrado PersonaContactoDAO : " + e.toString(),e);
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
                    if ("parametroEntidad".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "convenioporentidad.entidadexterna.identidadexterna");
                        wheres.append("= :").append(entry.getKey());
                        camposFiltro++;
                    }
                    if ("parametroConvenio".equals(entry.getKey())) {
                        wheres.append(alias).append("." + "convenioporentidad.convenio.idconvenio");
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
                                .append(".nombre")
                                .append(") Like :parametroNombre");
                        camposFiltro++;
                    }
                    if ("parametroApellido".equals(entry.getKey())) {
                        wheres.append("UPPER(").append(alias)
                                .append(".apellido")
                                .append(") Like :parametroApellido");
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
                                .append(".correo")
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

    private TypedQuery<PersonaContacto> asignarValores(TypedQuery<PersonaContacto> tq, Map<String, String> filters) {

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (null != entry.getValue() && !entry.getValue().isEmpty()) {
                if (("parametroConvenio".equals(entry.getKey()))
                        || ("parametroEntidad".equals(entry.getKey()))) {
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
