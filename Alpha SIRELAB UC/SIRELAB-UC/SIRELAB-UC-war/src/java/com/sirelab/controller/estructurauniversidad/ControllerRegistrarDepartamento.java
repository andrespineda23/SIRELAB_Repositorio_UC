/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarDepartamentosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
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
public class ControllerRegistrarDepartamento implements Serializable {

    @EJB
    GestionarDepartamentosBOInterface gestionarDepartamentosBO;

    private String nuevoNombre, nuevoCodigo;
    private List<Facultad> listaFacultades;
    private Facultad nuevoFacultad;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesFacultad;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarDepartamento() {
    }

    @PostConstruct
    public void init() {
        activarAceptar = false;
        nuevoFacultad = null;
        nuevoCodigo = null;
        nuevoNombre = null;
        validacionesFacultad = false;
        validacionesNombre = false;
        validacionesCodigo = false;
        activarLimpiar = true;
        colorMensaje = "black";
        constantes = new MensajesConstantes();
        mensajeError = "";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
    }

    public void validarNombreDepartamento() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.U_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio. " + constantes.U_NOMBRE));
        }
    }

    public void validarCodigoDepartamento() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty()) && (nuevoCodigo.trim().length() > 0)) {
            int tam = nuevoCodigo.length();
            if (tam >= 4) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                    Departamento registro = gestionarDepartamentosBO.obtenerDepartamentoPorCodigo(nuevoCodigo);
                    if (null != registro) {
                        validacionesCodigo = false;
                        FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado esta registrado."));
                    } else {
                        validacionesCodigo = true;
                    }
                } else {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.U_CODIGO));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.U_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio. " + constantes.U_CODIGO));
        }
    }

    public void validarFacultadDepartamento() {
        if (Utilidades.validarNulo(nuevoFacultad)) {
            validacionesFacultad = true;
        } else {
            validacionesFacultad = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoFacultad", new FacesMessage("La facultad es obligatoria."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesFacultad == false) {
            mensajeError = mensajeError + " - Facultad - ";
            retorno = false;
        }
        if (validacionesCodigo == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoDepartamento() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoDepartamentoEnSistema();
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: "+mensajeError;
        }
    }

    public void almacenarNuevoDepartamentoEnSistema() {
        try {
            Departamento departamentoNuevo = new Departamento();
            departamentoNuevo.setCodigodepartamento(nuevoCodigo);
            departamentoNuevo.setNombredepartamento(nuevoNombre);
            departamentoNuevo.setFacultad(nuevoFacultad);
            departamentoNuevo.setEstado(true);
            gestionarDepartamentosBO.crearNuevaDepartamento(departamentoNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarDepartamento almacenarNuevoDepartamentoEnSistema:  " + e.toString(),e);
        }
    }

    public void limpiarFormulario() {
        nuevoFacultad = null;
        nuevoCodigo = null;
        nuevoNombre = null;
        validacionesFacultad = false;
        validacionesNombre = false;
        validacionesCodigo = false;
        mensajeError = "";
        mensajeFormulario = "";
    }

    public void cancelarRegistroDepartamento() {
        nuevoFacultad = null;
        nuevoCodigo = null;
        nuevoNombre = null;
        validacionesFacultad = false;
        validacionesNombre = false;
        validacionesCodigo = false;
        mensajeFormulario = "N/A";
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeError = "";
        listaFacultades = null;
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

    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = gestionarDepartamentosBO.consultarFacultadesActivosRegistradas();
        }
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Facultad getNuevoFacultad() {
        return nuevoFacultad;
    }

    public void setNuevoFacultad(Facultad nuevoFacultad) {
        this.nuevoFacultad = nuevoFacultad;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
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
