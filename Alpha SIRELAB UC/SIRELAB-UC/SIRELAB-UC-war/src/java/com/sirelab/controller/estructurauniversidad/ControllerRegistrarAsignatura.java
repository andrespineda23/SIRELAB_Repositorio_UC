/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarAsignaturasBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
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

    //
    private String nuevoNombre, nuevoCredito, nuevoCodigo;
    //
    private boolean validacionesNombre, validacionesCredito, validacionesCodigo;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarAsignatura() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        nuevoCodigo = null;
        nuevoCredito = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesCredito = false;
        validacionesNombre = false;
        mensajeError = "";
        BasicConfigurator.configure();
    }

    public void validarNombreAsignatura() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.U_NOMBRE_ASIG));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. "+constantes.U_NOMBRE_ASIG));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio. "+constantes.U_NOMBRE_ASIG));
        }
    }

    public void validarCodigoAsignatura() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty()) && (nuevoCodigo.trim().length() > 0)) {
            int tam = nuevoCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto. "+constantes.U_CODIGO));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. "+constantes.U_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio. "+constantes.U_CODIGO));
        }
    }

    public void validarCreditosAsignatura() {
        if (Utilidades.validarNulo(nuevoCredito) && (!nuevoCredito.isEmpty()) && (nuevoCredito.trim().length() > 0)) {
            if (Utilidades.isNumber(nuevoCredito) == true) {
                validacionesCredito = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:nuevoCredito", new FacesMessage("El credito ingresado es incorrecto. "+constantes.U_CREDITO));
                validacionesCredito = false;
            }
        } else {
            validacionesCredito = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCredito", new FacesMessage("El credito es obligatorio. "+constantes.U_CREDITO));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesCodigo == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        if (validacionesCredito == false) {
            mensajeError = mensajeError + " - Creditos - ";
            retorno = false;
        }

        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        Asignatura asignatura = gestionarAsignaturasBO.obtenerAsignaturaPorCodigo(nuevoCodigo);
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
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el plan de estudio seleccionado.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: "+mensajeError;
        }
    }

    private void almacenarNuevoAsignaturaEnSistema() {
        try {
            Asignatura asignaturaNueva = new Asignatura();
            asignaturaNueva.setNombreasignatura(nuevoNombre);
            asignaturaNueva.setCodigoasignatura(nuevoCodigo);
            asignaturaNueva.setEstado(true);
            Integer creditos = Integer.valueOf(nuevoCredito);
            asignaturaNueva.setNumerocreditos(creditos.intValue());
            gestionarAsignaturasBO.crearAsignatura(asignaturaNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarAsignatura almacenarNuevoAsignaturaEnSistema : " + e.toString(),e);
        }
    }

    public void limpiarFormulario() {
        nuevoCodigo = null;
        nuevoCredito = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesCredito = false;
        mensajeError = "";
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroPlanEstudio() {
        nuevoCodigo = null;
        nuevoCredito = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesCredito = false;
        validacionesNombre = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        mensajeError = "";
        activarCasillas = false;
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
