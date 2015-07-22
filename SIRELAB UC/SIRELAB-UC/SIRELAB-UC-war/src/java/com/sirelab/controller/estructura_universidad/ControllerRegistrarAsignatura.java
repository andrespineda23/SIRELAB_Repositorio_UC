/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarAsignaturasBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarAsignatura implements Serializable {

    @EJB
    GestionarAsignaturasBOInterface gestionarAsignaturasBO;

    private List<Departamento> listaDepartamentos;
    private List<Carrera> listaCarreras;
    private List<PlanEstudios> listaPlanesEstudios;
    //
    private boolean activarNuevoCarrera;
    private boolean activarNuevoPlanEstudio;
    //
    private String nuevoNombre, nuevoCredito, nuevoCodigo;
    private Departamento nuevoDepartamento;
    private Carrera nuevoCarrera;
    private PlanEstudios nuevoPlanEstudio;
    //
    private boolean validacionesNombre, validacionesCredito, validacionesCodigo;
    private boolean validacionesDepartamento, validacionesCarrera, validacionesPlanEstudio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;

    public ControllerRegistrarAsignatura() {
    }

    @PostConstruct
    public void init() {
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        activarNuevoCarrera = true;
        activarNuevoPlanEstudio = true;
        nuevoCarrera = null;
        nuevoCodigo = null;
        nuevoCredito = null;
        nuevoDepartamento = null;
        nuevoNombre = null;
        nuevoPlanEstudio = null;
        validacionesCarrera = false;
        validacionesCodigo = false;
        validacionesCredito = false;
        validacionesDepartamento = false;
        validacionesNombre = false;
        validacionesPlanEstudio = false;
        BasicConfigurator.configure();
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(nuevoDepartamento)) {
            nuevoCarrera = null;
            listaCarreras = gestionarAsignaturasBO.consultarCarrerasPorIDDepartamento(nuevoDepartamento.getIddepartamento());
            activarNuevoCarrera = false;
            listaPlanesEstudios = null;
            nuevoPlanEstudio = null;
            activarNuevoPlanEstudio = true;
            validacionesDepartamento = true;
        } else {
            validacionesCarrera = false;
            validacionesDepartamento = false;
            validacionesPlanEstudio = false;
            nuevoCarrera = null;
            listaCarreras = null;
            listaPlanesEstudios = null;
            nuevoPlanEstudio = null;
            activarNuevoCarrera = true;
            activarNuevoPlanEstudio = true;
        }
    }

    public void actualizarCarreras() {
        if (Utilidades.validarNulo(nuevoCarrera)) {
            nuevoPlanEstudio = null;
            listaPlanesEstudios = gestionarAsignaturasBO.consultarPlanesEstudiosPorIDCarrera(nuevoCarrera.getIdcarrera());
            activarNuevoPlanEstudio = false;
            validacionesCarrera = true;
        } else {
            validacionesCarrera = false;
            validacionesPlanEstudio = false;
            listaPlanesEstudios = null;
            nuevoPlanEstudio = null;
            activarNuevoPlanEstudio = true;
        }
    }

    public void actualizarPlanEstudios() {
        if (Utilidades.validarNulo(nuevoPlanEstudio)) {
            validacionesPlanEstudio = true;
        } else {
            validacionesPlanEstudio = false;
        }
    }

    public void validarNombreAsignatura() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCodigoAsignatura() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio."));
        }
    }

    public void validarCreditosAsignatura() {
        if (Utilidades.validarNulo(nuevoCredito) && (!nuevoCredito.isEmpty())) {
            if (Utilidades.isNumber(nuevoCredito) == true) {
                validacionesCredito = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:nuevoCredito", new FacesMessage("El credito ingresado es incorrecto."));
                validacionesCredito = false;
            }
        } else {
            validacionesCredito = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCredito", new FacesMessage("El credito es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesCredito == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesPlanEstudio == false) {
            retorno = false;
        }
        if (validacionesCarrera == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        Asignatura asignatura = gestionarAsignaturasBO.obtenerAsignaturaPorCodigoYPlanEstudio(nuevoCodigo, nuevoPlanEstudio.getIdplanestudios());
        if (null != asignatura) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoAsignatura() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoAsignaturaEnSistema();
                limpiarFormulario();
                activarLimpiar = false;
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el plan de estudio seleccionado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoAsignaturaEnSistema() {
        try {
            Asignatura asignaturaNueva = new Asignatura();
            asignaturaNueva.setNombreasignatura(nuevoNombre);
            asignaturaNueva.setCodigoasignatura(nuevoCodigo);
            Integer creditos = Integer.valueOf(nuevoCredito);
            asignaturaNueva.setNumerocreditos(creditos.intValue());
            asignaturaNueva.setPlanestudios(nuevoPlanEstudio);
            gestionarAsignaturasBO.crearNuevoAsignatura(asignaturaNueva);
            activarCasillas = true;
            colorMensaje = "green";
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarAsignatura almacenarNuevoAsignaturaEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarAsignatura almacenarNuevoAsignaturaEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        activarNuevoCarrera = true;
        activarNuevoPlanEstudio = true;
        nuevoCarrera = null;
        nuevoCodigo = null;
        nuevoCredito = null;
        nuevoDepartamento = null;
        nuevoNombre = null;
        nuevoPlanEstudio = null;
        validacionesCarrera = false;
        validacionesCodigo = false;
        validacionesCredito = false;
        validacionesDepartamento = false;
        validacionesNombre = false;
        validacionesPlanEstudio = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroPlanEstudio() {
        activarNuevoCarrera = true;
        activarNuevoPlanEstudio = true;
        nuevoCarrera = null;
        nuevoCodigo = null;
        nuevoCredito = null;
        nuevoDepartamento = null;
        nuevoNombre = null;
        nuevoPlanEstudio = null;
        validacionesCarrera = false;
        validacionesCodigo = false;
        validacionesCredito = false;
        validacionesDepartamento = false;
        validacionesNombre = false;
        validacionesPlanEstudio = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        listaCarreras = null;
        listaDepartamentos = null;
        listaPlanesEstudios = null;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET
    public List<Departamento> getListaDepartamentos() {
        if (listaDepartamentos == null) {
            listaDepartamentos = gestionarAsignaturasBO.consultarDepartamentosRegistrados();
        }
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public List<PlanEstudios> getListaPlanesEstudios() {
        return listaPlanesEstudios;
    }

    public void setListaPlanesEstudios(List<PlanEstudios> listaPlanesEstudios) {
        this.listaPlanesEstudios = listaPlanesEstudios;
    }

    public boolean isActivarNuevoCarrera() {
        return activarNuevoCarrera;
    }

    public void setActivarNuevoCarrera(boolean activarNuevoCarrera) {
        this.activarNuevoCarrera = activarNuevoCarrera;
    }

    public boolean isActivarNuevoPlanEstudio() {
        return activarNuevoPlanEstudio;
    }

    public void setActivarNuevoPlanEstudio(boolean activarNuevoPlanEstudio) {
        this.activarNuevoPlanEstudio = activarNuevoPlanEstudio;
    }

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoCredito() {
        return nuevoCredito;
    }

    public void setNuevoCredito(String nuevoCredito) {
        this.nuevoCredito = nuevoCredito;
    }

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
    }

    public Departamento getNuevoDepartamento() {
        return nuevoDepartamento;
    }

    public void setNuevoDepartamento(Departamento nuevoDepartamento) {
        this.nuevoDepartamento = nuevoDepartamento;
    }

    public Carrera getNuevoCarrera() {
        return nuevoCarrera;
    }

    public void setNuevoCarrera(Carrera nuevoCarrera) {
        this.nuevoCarrera = nuevoCarrera;
    }

    public PlanEstudios getNuevoPlanEstudio() {
        return nuevoPlanEstudio;
    }

    public void setNuevoPlanEstudio(PlanEstudios nuevoPlanEstudio) {
        this.nuevoPlanEstudio = nuevoPlanEstudio;
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

}
