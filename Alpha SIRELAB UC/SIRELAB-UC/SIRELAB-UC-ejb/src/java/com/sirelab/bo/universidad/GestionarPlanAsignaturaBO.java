/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarPlanAsignaturaBOInterface;
import com.sirelab.dao.interfacedao.AsignaturaDAOInterface;
import com.sirelab.dao.interfacedao.AsignaturaPorPlanEstudioDAOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.PlanEstudios;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarPlanAsignaturaBO implements GestionarPlanAsignaturaBOInterface {

    static Logger logger = Logger.getLogger(GestionarPlanAsignaturaBO.class);
    
    @EJB
    CarreraDAOInterface carreraDAO;
    @EJB
    AsignaturaDAOInterface asignaturaDAO;
    @EJB
    PlanEstudiosDAOInterface planEstudiosDAO;
    @EJB
    AsignaturaPorPlanEstudioDAOInterface asignaturaPorPlanEstudioDAO;

    @Override
    public List<Carrera> obtenerCarrerasRegistradas() {
        try {
            List<Carrera> lista = carreraDAO.consultarCarreras();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO obtenerCarrerasRegistradas: " + e.toString(),e);
            return null;
        }
    }
    @Override
    public List<Carrera> obtenerCarrerasActivasRegistradas() {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO obtenerCarrerasRegistradas: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<PlanEstudios> obtenerPlanEstudiosPorCarrera(BigInteger carrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosPorCarrera(carrera);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO obtenerPlanEstudiosPorCarrera: " + e.toString(),e);
            return null;

        }
    }
    @Override
    public List<PlanEstudios> obtenerPlanEstudiosActivosPorCarrera(BigInteger carrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosActivosPorCarrera(carrera);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO obtenerPlanEstudiosPorCarrera: " + e.toString(),e);
            return null;

        }
    }

    @Override
    public List<Asignatura> consultarAsignaturasRegistradas() {
        try {
            List<Asignatura> lista = asignaturaDAO.consultarAsignaturas();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO consultarAsignaturasRegistradas: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Asignatura> consultarAsignaturasActivosRegistradas() {
        try {
            List<Asignatura> lista = asignaturaDAO.consultarAsignaturasActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO consultarAsignaturasRegistradas: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void crearAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignaturaPorPlanEstudio) {
        try {
            asignaturaPorPlanEstudioDAO.crearAsignaturaPorPlanEstudio(asignaturaPorPlanEstudio);
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO crearAsignaturaPorPlanEstudio: " + e.toString(),e);
        }
    }

    @Override
    public void editarAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignaturaPorPlanEstudio) {
        try {
            asignaturaPorPlanEstudioDAO.editarAsignaturaPorPlanEstudio(asignaturaPorPlanEstudio);
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO editarAsignaturaPorPlanEstudio: " + e.toString(),e);
        }
    }
    
    @Override
    public AsignaturaPorPlanEstudio buscarAsignaturaPorPlanEstudioPorIDS(BigInteger plan,BigInteger asignatura) {
        try {
           AsignaturaPorPlanEstudio registro = asignaturaPorPlanEstudioDAO.buscarAsignaturaPorPlanEstudioPorPlanYAsignatura(plan, asignatura);
           return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO buscarAsignaturaPorPlanEstudioPorIDS: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public AsignaturaPorPlanEstudio obtenerAsignaturaPorPlanEstudioPorID(BigInteger idRegistro) {
        try {
            AsignaturaPorPlanEstudio registro = asignaturaPorPlanEstudioDAO.buscarAsignaturaPorPlanEstudioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO obtenerAsignaturaPorPlanEstudioPorID: " + e.toString(),e);
            return null;
        }
    }
    
     @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanPorParametro(Map<String, String> filtros) {
        try {
            List<AsignaturaPorPlanEstudio> lista = asignaturaPorPlanEstudioDAO.buscarAsignaturaPorPlanEstudioPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanAsignaturaBO consultarAsignaturaPorPlanPorParametro : " + e.toString(),e);
            return null;
        }
    }

}
