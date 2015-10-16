/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.HojaVidaEquipoDAOInterface;
import com.sirelab.entidades.HojaVidaEquipo;
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
public class HojaVidaEquipoDAO implements HojaVidaEquipoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearHojaVidaEquipo(HojaVidaEquipo hojavidaequipo) {
        try {
            em.persist(hojavidaequipo);
            em.flush();
        } catch (Exception e) {
            System.out.println("Error crearHojaVidaEquipo HojaVidaEquipoDAO : " + e.toString());
        }
    }

    @Override
    public void editarHojaVidaEquipo(HojaVidaEquipo hojavidaequipo) {
        try {
            em.merge(hojavidaequipo);
        } catch (Exception e) {
            System.out.println("Error editarHojaVidaEquipo HojaVidaEquipoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarHojaVidaEquipo(HojaVidaEquipo hojavidaequipo) {
        try {
            em.remove(em.merge(hojavidaequipo));
        } catch (Exception e) {
            System.out.println("Error eliminarHojaVidaEquipo HojaVidaEquipoDAO : " + e.toString());
        }
    }

    @Override
    public List<HojaVidaEquipo> consultarHojasVidaEquipo() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM HojaVidaEquipo p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HojaVidaEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarHojasVidaEquipo HojaVidaEquipoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public List<HojaVidaEquipo> consultarHojaVidaPorIDEquipo(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM HojaVidaEquipo p WHERE p.equipoelemento.idequipoelemento=:idRegistro ORDER BY p.fechaevento");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            List<HojaVidaEquipo> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarHojaVidaPorIDEquipo HojaVidaEquipoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public HojaVidaEquipo buscarHojaVidaEquipoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM HojaVidaEquipo p WHERE p.idhojavidaequipo=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            HojaVidaEquipo registro = (HojaVidaEquipo) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarHojaVidaEquipoPorID HojaVidaEquipoDAO : " + e.toString());
            return null;
        }
    }
}
