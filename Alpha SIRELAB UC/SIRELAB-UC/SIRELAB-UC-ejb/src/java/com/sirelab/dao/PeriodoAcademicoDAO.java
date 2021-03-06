/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.PeriodoAcademicoDAOInterface;
import com.sirelab.entidades.PeriodoAcademico;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PeriodoAcademicoDAO implements PeriodoAcademicoDAOInterface {

    static Logger logger = Logger.getLogger(PeriodoAcademicoDAO.class);

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearPeriodoAcademico(PeriodoAcademico periodo) {
        try {
            em.persist(periodo);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearPeriodoAcademico PeriodoAcademicoDAO : " + e.toString(), e);
        }
    }

    @Override
    public void editarPeriodoAcademico(PeriodoAcademico periodo) {
        try {
            em.merge(periodo);
            em.flush();
        } catch (Exception e) {
            logger.error("Error editarPeriodoAcademico PeriodoAcademicoDAO : " + e.toString(), e);
        }
    }

    @Override
    public void eliminarPeriodoAcademico(PeriodoAcademico periodo) {
        try {
            em.remove(em.merge(periodo));
        } catch (Exception e) {
            logger.error("Error eliminarPeriodoAcademico PeriodoAcademicoDAO : " + e.toString(), e);
        }
    }

    @Override
    public List<PeriodoAcademico> consultarPeriodosAcademicos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PeriodoAcademico p ORDER BY p.fechainicial ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PeriodoAcademico> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarPeriodosAcademicos PeriodoAcademicoDAO : " + e.toString(), e);
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
            logger.error("Error buscarPeriodoAcademicoPorID PeriodoAcademicoDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public PeriodoAcademico buscarPeriodoAcademicoActual() {
        try {
            em.clear();
            Date hoy = new Date();
            Query query = em.createQuery("SELECT p FROM PeriodoAcademico p WHERE p.fechafinal >=:hoy AND p.fechainicial <=:hoy");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("hoy", hoy);
            PeriodoAcademico registro = (PeriodoAcademico) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarPeriodoAcademicoPorID PeriodoAcademicoDAO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Integer obtenerCantidadPeriodosAcademicosActivos() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PeriodoAcademico p WHERE p.estado = true");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PeriodoAcademico> lista = query.getResultList();
            if (null != lista) {
                return lista.size();
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.error("Error obtenerCantidadPeriodosAcademicosActivos PeriodoAcademicoDAO : " + e.toString(), e);
            return null;
        }
    }
}
