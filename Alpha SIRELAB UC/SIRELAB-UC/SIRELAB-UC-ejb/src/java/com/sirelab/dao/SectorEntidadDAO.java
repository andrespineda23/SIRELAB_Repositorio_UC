/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.SectorEntidadDAOInterface;
import com.sirelab.entidades.SectorEntidad;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class SectorEntidadDAO implements SectorEntidadDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearSectorEntidad(SectorEntidad sectorEntidad) {
        try {
            em.persist(sectorEntidad);
            em.flush();
        } catch (Exception e) {
            System.out.println("Error crearSectorEntidad SectorEntidadDAO : " + e.toString());
        }
    }

    @Override
    public void editarSectorEntidad(SectorEntidad sectorEntidad) {
        try {
            em.merge(sectorEntidad);
        } catch (Exception e) {
            System.out.println("Error editarSectorEntidad SectorEntidadDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarSectorEntidad(SectorEntidad sectorEntidad) {
        try {
            em.remove(em.merge(sectorEntidad));
        } catch (Exception e) {
            System.out.println("Error eliminarSectorEntidad SectorEntidadDAO : " + e.toString());
        }
    }

    @Override
    public List<SectorEntidad> consultarSectoresEntidad() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SectorEntidad p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SectorEntidad> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarTiposMovimientos SectorEntidadDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public SectorEntidad buscarSectorEntidadPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM SectorEntidad p WHERE p.idsectorentidad=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            SectorEntidad registro = (SectorEntidad) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarSectorEntidadPorID SectorEntidadDAO : " + e.toString());
            return null;
        }
    }
}
