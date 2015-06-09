/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.HorarioAtencionDAOInterface;
import com.sirelab.entidades.HorarioAtencion;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class HorarioAtencionDAO implements HorarioAtencionDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearHorarioAtencion(HorarioAtencion horario) {
        try {
            em.persist(horario);
        } catch (Exception e) {
            System.out.println("Error crearHorarioAtencion HorarioAtencionDAO : " + e.toString());
        }
    }

    @Override
    public void editarHorarioAtencion(HorarioAtencion horario) {
        try {
            em.merge(horario);
        } catch (Exception e) {
            System.out.println("Error editarHorarioAtencion HorarioAtencionDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarHorarioAtencion(HorarioAtencion horario) {
        try {
            em.remove(em.merge(horario));
        } catch (Exception e) {
            System.out.println("Error eliminarHorarioAtencion HorarioAtencionDAO : " + e.toString());
        }
    }

    @Override
    public List<HorarioAtencion> consultarHorariosAtencion() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM HorarioAtencion p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HorarioAtencion> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarHorariosAtencion HorarioAtencionDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public HorarioAtencion buscarHorarioAtencionPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM HorarioAtencion p WHERE p.idhorarioatencion=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            HorarioAtencion registro = (HorarioAtencion) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarHorarioAtencionPorID HorarioAtencionDAO : " + e.toString());
            return null;
        }
    }
}
