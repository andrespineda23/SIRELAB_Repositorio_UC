/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposReservasBOInterface;
import com.sirelab.entidades.TipoReserva;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesTipoReserva implements Serializable {

    @EJB
    GestionarVariableTiposReservasBOInterface gestionarVariableTiposReservaBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;
    private boolean modificacionesRegistro;
    private BigInteger idTipoReserva;
    private TipoReserva tipoReservaDetalles;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerDetallesTipoReserva() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleTipoReserva(BigInteger idDetalle) {
        this.idTipoReserva = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
    }

    private void cargarInformacionRegistro() {
        tipoReservaDetalles = gestionarVariableTiposReservaBO.consultarTipoReservaPorID(idTipoReserva);
        if (null != tipoReservaDetalles) {
            inputNombre = tipoReservaDetalles.getNombretiporeserva();
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

    public void registrarModificacionTipoReserva() {
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
            tipoReservaDetalles.setNombretiporeserva(inputNombre);
            gestionarVariableTiposReservaBO.editarTipoReserva(tipoReservaDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesTipoReserva almacenarModificacionRegistro:  " + e.toString());
            System.out.println("Error ControllerDetallesTipoReserva almacenarModificacionRegistro: " + e.toString());
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
        idTipoReserva = null;
        modificacionesRegistro = false;
        tipoReservaDetalles = null;
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
