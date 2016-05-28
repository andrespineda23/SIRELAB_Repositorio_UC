/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.bo.interfacebo.universidad.GestionarPlanAsignaturaBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesPlanAsignatura implements Serializable {

    @EJB
    GestionarPlanAsignaturaBOInterface gestionarPlanAsignaturaBO;

    private AsignaturaPorPlanEstudio planAsignaturaDetalles;
    private BigInteger idPlanAsignatura;
    private List<Carrera> listaCarreras;
    private List<PlanEstudios> listaPlanEstudios;
    private List<Asignatura> listaAsignaturas;
    private boolean activarModificacionPlan;
    private Carrera editarCarrera;
    private PlanEstudios editarPlan;
    private Asignatura editarAsignatura;
    //
    private boolean validacionesCarrera, validacionesPlan, validacionesAsignatura;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private String mensajeError;

    public ControllerDetallesPlanAsignatura() {
    }

    @PostConstruct
    public void init() {
        activarModificacionPlan = true;
        validacionesAsignatura = true;
        validacionesPlan = true;
        validacionesCarrera = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionPlanEstudio() {
        validacionesAsignatura = true;
        validacionesPlan = true;
        validacionesCarrera = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        planAsignaturaDetalles = new AsignaturaPorPlanEstudio();
        recibirIDPlanAsignaturaDetalles(idPlanAsignatura);
        return "administrarplanasignatura";
    }

    public void asignarValoresVariablesPlanAsignatura() {
        mensajeError = "";
        editarAsignatura = planAsignaturaDetalles.getAsignatura();
        editarEstado = planAsignaturaDetalles.getEstado();
        editarPlan = planAsignaturaDetalles.getPlanestudio();
        editarCarrera = planAsignaturaDetalles.getPlanestudio().getCarrera();
        activarModificacionPlan = false;
        listaCarreras = gestionarPlanAsignaturaBO.obtenerCarrerasRegistradas();
        if (Utilidades.validarNulo(editarCarrera)) {
            listaPlanEstudios = gestionarPlanAsignaturaBO.obtenerPlanEstudiosPorCarrera(editarCarrera.getIdcarrera());
        }
        listaAsignaturas = gestionarPlanAsignaturaBO.consultarAsignaturasRegistradas();
    }

    public void recibirIDPlanAsignaturaDetalles(BigInteger idRegistro) {
        this.idPlanAsignatura = idRegistro;
        planAsignaturaDetalles = gestionarPlanAsignaturaBO.obtenerAsignaturaPorPlanEstudioPorID(idPlanAsignatura);
        asignarValoresVariablesPlanAsignatura();
    }

    public void actualizarCarrera() {
        if (Utilidades.validarNulo(editarCarrera)) {
            editarPlan = null;
            listaPlanEstudios = gestionarPlanAsignaturaBO.obtenerPlanEstudiosPorCarrera(editarCarrera.getIdcarrera());
            activarModificacionPlan = false;
            validacionesCarrera = true;
        } else {
            validacionesCarrera = false;
            validacionesPlan = false;
            editarPlan = null;
            listaPlanEstudios = null;
            activarModificacionPlan = true;
        }
    }

    public void validarPlan() {
        if (Utilidades.validarNulo(editarPlan)) {
            validacionesPlan = true;
        } else {
            validacionesPlan = false;
            FacesContext.getCurrentInstance().addMessage("form:editarPlan", new FacesMessage("El plan de estudios es obligatorio."));
        }
    }

    public void validarAsignatura() {
        if (Utilidades.validarNulo(editarAsignatura)) {
            validacionesAsignatura = true;
        } else {
            validacionesAsignatura = false;
            FacesContext.getCurrentInstance().addMessage("form:editarAsignatura", new FacesMessage("La asignatura es obligatoria."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesPlan == false) {
            mensajeError = mensajeError + " - Plan Estd. - ";
            retorno = false;
        }
        if (validacionesCarrera == false) {
            mensajeError = mensajeError + " - Carrera - ";
            retorno = false;
        }
        if (validacionesAsignatura == false) {
            mensajeError = mensajeError + " - Asignatura - ";
            retorno = false;
        }
        return retorno;
    }

    private boolean validarRegistroRepetido() {
        boolean retorno = true;
        AsignaturaPorPlanEstudio registro = gestionarPlanAsignaturaBO.buscarAsignaturaPorPlanEstudioPorIDS(editarPlan.getIdplanestudios(), editarAsignatura.getIdasignatura());
        if (null != registro) {
            if (!planAsignaturaDetalles.getIdasignaturaporplanestudio().equals(registro.getIdasignaturaporplanestudio())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionPlanEstudio() {
        if (validarResultadosValidacion() == true) {
            if (validarRegistroRepetido() == true) {
                almacenarModificacionPlanEstudioEnSistema();
                recibirIDPlanAsignaturaDetalles(this.idPlanAsignatura);
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El registro ya esta almacenado en el sistema.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: "+mensajeError;
        }
    }

    private void almacenarModificacionPlanEstudioEnSistema() {
        try {
            planAsignaturaDetalles.setAsignatura(editarAsignatura);
            planAsignaturaDetalles.setEstado(editarEstado);
            planAsignaturaDetalles.setPlanestudio(editarPlan);
            gestionarPlanAsignaturaBO.editarAsignaturaPorPlanEstudio(planAsignaturaDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlanEstudio almacenarModificacionPlanEstudioEnSistema:  " + e.toString(),e);
        }
    }

    //GET-SET
    public BigInteger getIdPlanAsignatura() {
        return idPlanAsignatura;
    }

    public void setIdPlanAsignatura(BigInteger idPlanAsignatura) {
        this.idPlanAsignatura = idPlanAsignatura;
    }

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public List<PlanEstudios> getListaPlanEstudios() {
        return listaPlanEstudios;
    }

    public void setListaPlanEstudios(List<PlanEstudios> listaPlanEstudios) {
        this.listaPlanEstudios = listaPlanEstudios;
    }

    public List<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public boolean isActivarModificacionPlan() {
        return activarModificacionPlan;
    }

    public void setActivarModificacionPlan(boolean activarModificacionPlan) {
        this.activarModificacionPlan = activarModificacionPlan;
    }

    public Carrera getEditarCarrera() {
        return editarCarrera;
    }

    public void setEditarCarrera(Carrera editarCarrera) {
        this.editarCarrera = editarCarrera;
    }

    public PlanEstudios getEditarPlan() {
        return editarPlan;
    }

    public void setEditarPlan(PlanEstudios editarPlan) {
        this.editarPlan = editarPlan;
    }

    public Asignatura getEditarAsignatura() {
        return editarAsignatura;
    }

    public void setEditarAsignatura(Asignatura editarAsignatura) {
        this.editarAsignatura = editarAsignatura;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isEditarEstado() {
        return editarEstado;
    }

    public void setEditarEstado(boolean editarEstado) {
        this.editarEstado = editarEstado;
    }

}
