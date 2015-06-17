/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposReservasBOInterface;
import com.sirelab.entidades.TipoReserva;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarTipoReserva implements Serializable {

    @EJB
    GestionarVariableTiposReservasBOInterface gestionarVariableTiposReservaBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;

    public ControllerRegistrarTipoReserva() {
    }

    @PostConstruct
    public void init() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void validarNombre() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty())) {
            if (Utilidades.validarCaracterString(inputNombre)) {
                validacionesNombre = true;
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto."));
        }
    }

    public void registrarTipoReserva() {
        if (validacionesNombre == true) {
            almacenarRegistroNuevo();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
            restaurarFormulario();
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            TipoReserva tipoNuevo = new TipoReserva();
            tipoNuevo.setNombretiporeserva(inputNombre);
            gestionarVariableTiposReservaBO.crearTipoReserva(tipoNuevo);
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarTipoReserva almacenarRegistroNuevo: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarTipoReserva();
        return "variables_reserva";
    }

    public void cancelarTipoReserva() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    private void restaurarFormulario() {
        inputNombre = null;
        validacionesNombre = false;
    }

    //GET-SET
    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
