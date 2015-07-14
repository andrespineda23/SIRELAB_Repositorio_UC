/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.PeriodoAcademicoDAOInterface;
import com.sirelab.entidades.PeriodoAcademico;
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
public class PeriodoAcademicoDAO implements PeriodoAcademicoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearPeriodoAcademico(PeriodoAcademico periodo) {
        try {
            em.persist(periodo);
        } catch (Exception e) {
            System.out.println("Error crearPeriodoAcademico PeriodoAcademicoDAO : " + e.toString());
        }
    }

    @Override
    public void editarPeriodoAcademico(PeriodoAcademico periodo) {
        try {
            em.merge(periodo);
        } catch (Exception e) {
            System.out.println("Error editarPeriodoAcademico PeriodoAcademicoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarPeriodoAcademico(PeriodoAcademico periodo) {
        try {
            em.remove(em.merge(periodo));
        } catch (Exception e) {
            System.out.println("Error eliminarPeriodoAcademico PeriodoAcademicoDAO : " + e.toString());
        }
    }

    @Override
    public List<PeriodoAcademico> consultarPeriodosAcademicos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PeriodoAcademico p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PeriodoAcademico> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarPeriodosAcademicos PeriodoAcademicoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public PeriodoAcademico buscarPeriodoAcademicoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PeriodoAcademico p WHERE p.idperiodoacademico=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            PeriodoAcademico registro = (PeriodoAcademico) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarPeriodoAcademicoPorID PeriodoAcademicoDAO : " + e.toString());
            return null;
        }
    }

}
