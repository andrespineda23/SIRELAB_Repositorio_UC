/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoServiciosSalaBOInterface;
import com.sirelab.entidades.ServiciosSala;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarServiciosSala implements Serializable {

    @EJB
    GestionarRecursoServiciosSalaBOInterface gestionarRecursoServiciosSalaBO;

    private String nuevoNombre, nuevoCodigo, nuevoCosto;
    private boolean validacionesNombre, validacionesCodigo, validacionesCosto;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarServiciosSala() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarAceptar = false;
        nuevoCodigo = null;
        nuevoNombre = null;
        nuevoCosto = null;
        validacionesCodigo = false;
        validacionesCosto = false;
        validacionesNombre = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
    }

    public void validarNombreServiciosSala() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.SERVICIO_NOM));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.SERVICIO_NOM));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio. " + constantes.SERVICIO_NOM));
        }

    }

    public void validarCodigoServiciosSala() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty()) && (nuevoCodigo.trim().length() > 0)) {
            int tam = nuevoCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.RECURSO_COD));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.RECURSO_COD));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio. " + constantes.RECURSO_COD));
        }
    }

    public void validarCostoServiciosSala() {
        if (Utilidades.validarNulo(nuevoCosto) && (!nuevoCosto.isEmpty()) && (nuevoCosto.trim().length() > 0)) {
            if (!Utilidades.isNumber(nuevoCosto)) {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCosto", new FacesMessage("El costo ingresado es incorrecto. " + constantes.RECURSO_COSTO));
            } else {
                validacionesCosto = true;
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCosto", new FacesMessage("El costo es obligatorio. " + constantes.RECURSO_COD));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        ServiciosSala registro = gestionarRecursoServiciosSalaBO.obtenerServiciosSalaPorCodigo(nuevoCodigo);
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoServiciosSala() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoServiciosSalaEnSistema();
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoServiciosSalaEnSistema() {
        try {
            ServiciosSala servicioNuevo = new ServiciosSala();
            servicioNuevo.setNombreservicio(nuevoNombre);
            servicioNuevo.setCodigoservicio(nuevoCodigo);
            servicioNuevo.setCostoservicio(Integer.valueOf(nuevoCosto).intValue());
            servicioNuevo.setEstado(true);
            gestionarRecursoServiciosSalaBO.crearNuevoServiciosSala(servicioNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarServiciosSala almacenarNuevoServiciosSalaEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarServiciosSala almacenarNuevoServiciosSalaEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        nuevoCodigo = null;
        nuevoCosto = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        validacionesCosto = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroServiciosSala() {
        nuevoCodigo = null;
        nuevoNombre = null;
        nuevoCosto = null;
        validacionesCodigo = false;
        validacionesCosto = false;
        validacionesNombre = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
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

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
    }

    public String getNuevoCosto() {
        return nuevoCosto;
    }

    public void setNuevoCosto(String nuevoCosto) {
        this.nuevoCosto = nuevoCosto;
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
