/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableEstadosReservasBOInterface;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesEstadoReserva implements Serializable {

    @EJB
    GestionarVariableEstadosReservasBOInterface gestionarVariableEstadosReservaBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;
    private boolean modificacionesRegistro;
    private BigInteger idEstadoReserva;
    private EstadoReserva estadoReservaDetalles;

    public ControllerDetallesEstadoReserva() {
    }

    @PostConstruct
    public void init() {

    }

    public void recibirIDDetalleEstadoReserva(BigInteger idDetalle) {
        this.idEstadoReserva = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
    }

    private void cargarInformacionRegistro() {
        estadoReservaDetalles = gestionarVariableEstadosReservaBO.consultarEstadoReservaPorID(idEstadoReserva);
        if (null != estadoReservaDetalles) {
            inputNombre = estadoReservaDetalles.getNombreestadoreserva();
            validacionesNombre = true;
            modificacionesRegistro = false;
        }

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
        modificacionesRegistro = true;
    }

    public void registrarModificacionEstadoReserva() {
        if (modificacionesRegistro == true) {
            if (validacionesNombre == true) {
                almacenarModificacionRegistro();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
                cargarInformacionRegistro();
            } else {
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificacionRegistro() {
        try {
            estadoReservaDetalles.setNombreestadoreserva(inputNombre);
            gestionarVariableEstadosReservaBO.editarEstadoReserva(estadoReservaDetalles);
        } catch (Exception e) {
            System.out.println("Error ControlleDetallesEstadoReserva almacenarModificacionRegistro: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarEstadoReserva();
        return "variables_reserva";
    }

    public void cancelarEstadoReserva() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "";
        idEstadoReserva = null;
        estadoReservaDetalles = null;
        modificacionesRegistro = false;
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
