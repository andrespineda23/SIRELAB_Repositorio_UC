/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ComponenteEquipoDAOInterface;
import com.sirelab.entidades.ComponenteEquipo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class ComponenteEquipoDAO implements ComponenteEquipoDAOInterface {

    static Logger logger = Logger.getLogger(ComponenteEquipoDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            em.persist(componenteEquipo);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearComponenteEquipo ComponenteEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            em.merge(componenteEquipo);
        } catch (Exception e) {
            logger.error("Error editarComponenteEquipo ComponenteEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            em.remove(em.merge(componenteEquipo));
        } catch (Exception e) {
            logger.error("Error eliminarComponenteEquipo ComponenteEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<ComponenteEquipo> consultarComponentesEquipos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ComponenteEquipo p ORDER BY p.codigocomponete ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ComponenteEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarEquiposElementos ComponenteEquipoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<ComponenteEquipo> consultarComponentesEquiposPorEquipo(BigInteger equipo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ComponenteEquipo p WHERE p.equipoelemento.idequipoelemento=:equipo ORDER BY P.codigocomponete ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("equipo", equipo);
            List<ComponenteEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarComponentesEquiposPorEquipo ComponenteEquipoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public ComponenteEquipo buscarComponenteEquipoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ComponenteEquipo p WHERE p.idcomponenteequipo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            ComponenteEquipo registro = (ComponenteEquipo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarComponenteEquipoPorID ComponenteEquipoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public ComponenteEquipo buscarComponenteEquipoPorCodigoYEquipo(String codigo, BigInteger equipo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ComponenteEquipo p WHERE p.codigocomponete=:codigo AND p.equipoelemento.idequipoelemento=:equipo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            query.setParameter("equipo", equipo);
            ComponenteEquipo registro = (ComponenteEquipo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarComponenteEquipoPorCodigoYEquipo ComponenteEquipoDAO : " + e.toString(),e);
            return null;
        }
    }

}
