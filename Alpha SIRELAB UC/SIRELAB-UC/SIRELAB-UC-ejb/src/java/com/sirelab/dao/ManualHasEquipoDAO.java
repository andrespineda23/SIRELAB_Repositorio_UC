/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ManualHasEquipoDAOInterface;
import com.sirelab.entidades.ManualHasEquipo;
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
public class ManualHasEquipoDAO implements ManualHasEquipoDAOInterface {

    static Logger logger = Logger.getLogger(ManualHasEquipoDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearManualHasEquipo(ManualHasEquipo manual) {
        try {
            em.persist(manual);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearManualHasEquipo ManualHasEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void editarManualHasEquipo(ManualHasEquipo manual) {
        try {
            em.merge(manual);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarManualHasEquipo ManualHasEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public void eliminarManualHasEquipo(ManualHasEquipo manual) {
        try {
            em.remove(em.merge(manual));
        } catch (Exception e) {
            logger.error("Error eliminarManualHasEquipo ManualHasEquipoDAO : " + e.toString(),e);
        }
    }

    @Override
    public List<ManualHasEquipo> consultarManualHasEquipoesHasEquipo() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ManualHasEquipo p ORDER BY p.equipoelemento.inventarioequipo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ManualHasEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarManualHasEquipoesHasEquipo ManualHasEquipoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public ManualHasEquipo buscarManualHasEquipoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ManualHasEquipo p WHERE p.idmanualhasequipo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            ManualHasEquipo registro = (ManualHasEquipo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarManualHasEquipoPorID ManualHasEquipoDAO : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<ManualHasEquipo> buscarManualHasEquipoPorEquipo(BigInteger equipo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ManualHasEquipo p WHERE p.equipoelemento.idequipoelemento=:equipo ORDER BY p.equipoelemento.inventarioequipo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("equipo", equipo);
            List<ManualHasEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error buscarManualHasEquipoPorEquipo ManualHasEquipoDAO : " + e.toString(),e);
            return null;
        }
    }
}
