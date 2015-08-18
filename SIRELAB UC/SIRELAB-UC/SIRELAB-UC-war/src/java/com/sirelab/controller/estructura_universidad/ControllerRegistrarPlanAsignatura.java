/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarPlanAsignaturaBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarPlanAsignatura implements Serializable {

    @EJB
    GestionarPlanAsignaturaBOInterface gestionarPlanAsignaturaBO;

    private List<Carrera> listaCarreras;
    private Carrera nuevoCarrera;
    private List<PlanEstudios> listaPlanEstudios;
    private PlanEstudios nuevoPlan;
    private boolean activarPlan;
    private List<Asignatura> listaAsignaturas;
    private Asignatura nuevoAsignatura;
    private boolean validacionesCarrera, validacionesPlanEstudio, validacionesAsignatura;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarPlanAsignatura() {
    }

    @PostConstruct
    public void init() {
        activarPlan = true;
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        nuevoCarrera = null;
        nuevoAsignatura = null;
        nuevoPlan = null;
        validacionesPlanEstudio = false;
        validacionesCarrera = false;
        validacionesAsignatura = false;
        BasicConfigurator.configure();
    }

    public void actualizarCarreras() {
        if (Utilidades.validarNulo(nuevoCarrera)) {
            nuevoPlan = null;
            listaPlanEstudios = gestionarPlanAsignaturaBO.obtenerPlanEstudiosPorCarrera(nuevoCarrera.getIdcarrera());
            activarPlan = false;
            validacionesPlanEstudio = true;
        } else {
            validacionesCarrera = false;
            validacionesPlanEstudio = false;
            listaPlanEstudios = null;
            nuevoPlan = null;
            activarPlan = true;
        }
    }

    public void actualizarPlanEstudios() {
        if (Utilidades.validarNulo(nuevoPlan)) {
            validacionesPlanEstudio = true;
        } else {
            validacionesPlanEstudio = false;
        }
    }

    public void actualizarAsignatura() {
        if (Utilidades.validarNulo(nuevoAsignatura)) {
            validacionesAsignatura = true;
        } else {
            validacionesAsignatura = false;
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCarrera == false) {
            retorno = false;
        }
        if (validacionesAsignatura == false) {
            retorno = false;
        }
        if (validacionesPlanEstudio == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarRegistroRepetido() {
        boolean retorno = true;
        AsignaturaPorPlanEstudio asignatura = gestionarPlanAsignaturaBO.buscarAsignaturaPorPlanEstudioPorIDS(nuevoPlan.getIdplanestudios(), nuevoAsignatura.getIdasignatura());
        if (null != asignatura) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoAsignatura() {
        if (validarResultadosValidacion() == true) {
            if (validarRegistroRepetido() == true) {
                almacenarNuevoAsignaturaPlanEnSistema();
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El registro ya se encuentra registrado en el sistema.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoAsignaturaPlanEnSistema() {
        try {
            AsignaturaPorPlanEstudio asignaturaNueva = new AsignaturaPorPlanEstudio();
            asignaturaNueva.setEstado(true);
            asignaturaNueva.setPlanestudio(nuevoPlan);
            asignaturaNueva.setAsignatura(nuevoAsignatura);
            gestionarPlanAsignaturaBO.crearAsignaturaPorPlanEstudio(asignaturaNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarAsignatura almacenarNuevoAsignaturaPlanEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarAsignatura almacenarNuevoAsignaturaPlanEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        nuevoCarrera = null;
        nuevoAsignatura = null;
        nuevoPlan = null;
        listaPlanEstudios = null;
        validacionesPlanEstudio = false;
        validacionesCarrera = false;
        validacionesAsignatura = false;
        mensajeFormulario = "";
        activarPlan = true;
    }

    public void cancelarRegistroPlanEstudio() {
        nuevoCarrera = null;
        nuevoAsignatura = null;
        nuevoPlan = null;
        activarPlan = true;
        validacionesPlanEstudio = false;
        validacionesCarrera = false;
        validacionesAsignatura = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        listaCarreras = null;
        listaAsignaturas = null;
        listaPlanEstudios = null;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        activarAceptar = false;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET
    public List<Carrera> getListaCarreras() {
        if (null == listaCarreras) {
            listaCarreras = gestionarPlanAsignaturaBO.obtenerCarrerasRegistradas();
        }
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public Carrera getNuevoCarrera() {
        return nuevoCarrera;
    }

    public void setNuevoCarrera(Carrera nuevoCarrera) {
        this.nuevoCarrera = nuevoCarrera;
    }

    public List<PlanEstudios> getListaPlanEstudios() {
        return listaPlanEstudios;
    }

    public void setListaPlanEstudios(List<PlanEstudios> listaPlanEstudios) {
        this.listaPlanEstudios = listaPlanEstudios;
    }

    public PlanEstudios getNuevoPlan() {
        return nuevoPlan;
    }

    public void setNuevoPlan(PlanEstudios nuevoPlan) {
        this.nuevoPlan = nuevoPlan;
    }

    public boolean isActivarPlan() {
        return activarPlan;
    }

    public void setActivarPlan(boolean activarPlan) {
        this.activarPlan = activarPlan;
    }

    public List<Asignatura> getListaAsignaturas() {
        if (null == listaAsignaturas) {
            listaAsignaturas = gestionarPlanAsignaturaBO.consultarAsignaturasRegistradas();
        }
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public Asignatura getNuevoAsignatura() {
        return nuevoAsignatura;
    }

    public void setNuevoAsignatura(Asignatura nuevoAsignatura) {
        this.nuevoAsignatura = nuevoAsignatura;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isActivarLimpiar() {
        return activarLimpiar;
    }

    public void setActivarLimpiar(boolean activarLimpiar) {
        this.activarLimpiar = activarLimpiar;
    }

    public boolean isActivarAceptar() {
        return activarAceptar;
    }

    public void setActivarAceptar(boolean activarAceptar) {
        this.activarAceptar = activarAceptar;
    }

}
