/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller;

import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerCambiarContrasenia implements Serializable {

    private String mensajeFormulario;
    private String colorMensaje;
    private String contraseniaAntigua, contraseniaNueva, contraseniaNueva2;
    private String paginaAnterior;
    private boolean validacionesAntigua, validacionesNueva, validacionesNueva2;

    public ControllerCambiarContrasenia() {
    }

    @PostConstruct
    public void init() {
        mensajeFormulario = "black";
        colorMensaje = "N/A";
        contraseniaAntigua = null;
        contraseniaNueva = null;
        validacionesAntigua = false;
        validacionesNueva = false;
        validacionesNueva2 = false;
        contraseniaNueva2 = null;
    }

    public void recibirPaginaAnterior(String page) {
        paginaAnterior = page;
    }

    public void validarContraseniaAnterior() {
        FacesContext.getCurrentInstance().addMessage("form:moduloEquipoElemento", new FacesMessage("El campo Modulo/Banco de Trabajo es obligatorio."));
    }

    public void validarContraseniaNueva() {
        if (Utilidades.validarNulo(contraseniaNueva) && (!contraseniaNueva.isEmpty()) && (contraseniaNueva.trim().length() > 0)) {
            //contraseniaNueva
        } else {
            FacesContext.getCurrentInstance().addMessage("form:moduloEquipoElemento", new FacesMessage("El campo Modulo/Banco de Trabajo es obligatorio."));
        }
    }

    public void validarContraseniaNueva2() {
        if (Utilidades.validarNulo(contraseniaNueva2) && (!contraseniaNueva2.isEmpty()) && (contraseniaNueva2.trim().length() > 0)) {
            if (validacionesNueva == true) {
            } else {
                FacesContext.getCurrentInstance().addMessage("form:moduloEquipoElemento", new FacesMessage("El campo Modulo/Banco de Trabajo es obligatorio."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:moduloEquipoElemento", new FacesMessage("El campo Modulo/Banco de Trabajo es obligatorio."));
        }
    }

    public void cambiarContrasenia() {
    }

    public void limpiarProceso() {
        mensajeFormulario = "black";
        colorMensaje = "N/A";
        contraseniaAntigua = null;
        contraseniaNueva = null;
        contraseniaNueva2 = null;
        validacionesAntigua = false;
        validacionesNueva = false;
        validacionesNueva2 = false;
    }

    public String cancelarProceso() {
        return paginaAnterior;
    }
}
