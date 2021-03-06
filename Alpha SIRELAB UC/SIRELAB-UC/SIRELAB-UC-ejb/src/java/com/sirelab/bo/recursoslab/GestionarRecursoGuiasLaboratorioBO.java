/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoGuiasLaboratorioBOInterface;
import com.sirelab.bo.planta.GestionarPlantaSalasBO;
import com.sirelab.dao.interfacedao.AsignaturaDAOInterface;
import com.sirelab.dao.interfacedao.AsignaturaPorPlanEstudioDAOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.GuiaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.GuiaLaboratorio;
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
public class GestionarRecursoGuiasLaboratorioBO implements GestionarRecursoGuiasLaboratorioBOInterface {

    static Logger logger = Logger.getLogger(GestionarRecursoGuiasLaboratorioBO.class);
    
    @EJB
    GuiaLaboratorioDAOInterface guiaLaboratorioDAO;
    @EJB
    AsignaturaDAOInterface asignaturaDAO;
    @EJB
    CarreraDAOInterface carreraDAO;
    @EJB
    PlanEstudiosDAOInterface planEstudiosDAO;
    @EJB
    AsignaturaPorPlanEstudioDAOInterface asignaturaPorPlanEstudioDAO;

    @Override
    public List<Carrera> consultarCarrerasRegistradas() {
        try {
            List<Carrera> lista = carreraDAO.consultarCarreras();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO consultarCarrerasRegistradas : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanesEstidoPorCarrera(BigInteger carrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosPorCarrera(carrera);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO consultarPlanesEstidoPorCarrera : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<GuiaLaboratorio> consultarGuiasLaboratorioPorParametro(Map<String, String> filtros) {
        try {
            List<GuiaLaboratorio> lista = guiaLaboratorioDAO.buscarGuiasLaboratorioPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO consultarGuiasLaboratorioPorParametro : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public GuiaLaboratorio consultarGuiaLaboratorioPorUbicacion(String ubicacion) {
        try {
            GuiaLaboratorio registro = guiaLaboratorioDAO.buscarGuiaLaboratorioPorUbicacion(ubicacion);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO consultarGuiaLaboratorioPorUbicacion : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Asignatura> consultarAsignaturasRegistradas() {
        try {
            List<Asignatura> lista = asignaturaDAO.consultarAsignaturas();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO consultarAsignaturasRegistradas: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<GuiaLaboratorio> consultarGuiasLaboratorioRegistradas() {
        try {
            List<GuiaLaboratorio> lista = guiaLaboratorioDAO.consultarGuiasLaboratorio();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO consultarGuiasLaboratorioRegistradas: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public GuiaLaboratorio obtenerGuiaLaboratorioPorID(BigInteger idRegistro) {
        try {
            GuiaLaboratorio registro = guiaLaboratorioDAO.buscarGuiaLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO obtenerGuiaLaboratorioPorID: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void crearGuiaLaboratorio(GuiaLaboratorio guiaLaboratorio) {
        try {
            guiaLaboratorioDAO.crearGuiaLaboratorio(guiaLaboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO crearGuiaLaboratorio: " + e.toString(),e);
        }
    }

    @Override
    public void modificarGuiaLaboratorio(GuiaLaboratorio guiaLaboratorio) {
        try {
            guiaLaboratorioDAO.editarGuiaLaboratorio(guiaLaboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO modificarGuiaLaboratorio: " + e.toString(),e);
        }
    }

    @Override
    public void borrarGuiaLaboratorio(GuiaLaboratorio guiaLaboratorio) {
        try {
            guiaLaboratorioDAO.eliminarGuiaLaboratorio(guiaLaboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO borrarGuiaLaboratorio: " + e.toString(),e);
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudioPorIDPlan(BigInteger plan) {
        try {
            List<AsignaturaPorPlanEstudio> lista = asignaturaPorPlanEstudioDAO.consultarAsignaturaPorPlanEstudiosIdPlanEstudio(plan);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoGuiasLaboratorioBO consultarAsignaturaPorPlanEstudioPorIDPlan: " + e.toString(),e);
            return null;
        }
    }
}
