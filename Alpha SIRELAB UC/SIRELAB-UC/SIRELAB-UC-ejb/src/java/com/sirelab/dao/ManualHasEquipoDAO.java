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

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class ManualHasEquipoDAO implements ManualHasEquipoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearManualHasEquipo(ManualHasEquipo manual) {
        try {
            em.persist(manual);
        } catch (Exception e) {
            System.out.println("Error crearManualHasEquipo ManualHasEquipoDAO : " + e.toString());
        }
    }

    @Override
    public void editarManualHasEquipo(ManualHasEquipo manual) {
        try {
            em.merge(manual);
        } catch (Exception e) {
            System.out.println("Error editarManualHasEquipo ManualHasEquipoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarManualHasEquipo(ManualHasEquipo manual) {
        try {
            em.remove(em.merge(manual));
        } catch (Exception e) {
            System.out.println("Error eliminarManualHasEquipo ManualHasEquipoDAO : " + e.toString());
        }
    }

    @Override
    public List<ManualHasEquipo> consultarManualHasEquipoesHasEquipo() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ManualHasEquipo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ManualHasEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarManualHasEquipoesHasEquipo ManualHasEquipoDAO : " + e.toString());
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
            System.out.println("Error buscarManualHasEquipoPorID ManualHasEquipoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ManualHasEquipo> buscarManualHasEquipoPorEquipo(BigInteger equipo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM ManualHasEquipo p WHERE p.equipoelemento.idequipoelemento=:equipo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("equipo", equipo);
            List<ManualHasEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error buscarManualHasEquipoPorEquipo ManualHasEquipoDAO : " + e.toString());
            return null;
        }
    }
}
