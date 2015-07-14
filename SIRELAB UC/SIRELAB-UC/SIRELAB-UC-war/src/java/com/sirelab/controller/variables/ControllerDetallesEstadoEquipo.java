/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableEstadosEquiposBOInterface;
import com.sirelab.entidades.EstadoEquipo;
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
public class ControllerDetallesEstadoEquipo implements Serializable{

    @EJB
    GestionarVariableEstadosEquiposBOInterface gestionarVariableEstadosEquiposBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;
    private BigInteger idEstadoEquipo;
    private boolean modificacionesRegistro;
    private EstadoEquipo estadoEquipoDetalle;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerDetallesEstadoEquipo() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleEstadoEquipo(BigInteger idDetalle) {
        this.idEstadoEquipo = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
    }

    private void cargarInformacionRegistro() {
        estadoEquipoDetalle = gestionarVariableEstadosEquiposBO.consultarEstadoEquipoPorID(idEstadoEquipo);
        if (null != estadoEquipoDetalle) {
            inputNombre = estadoEquipoDetalle.getNombreestadoequipo();
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

    public void registrarModificacionEstadoEquipo() {
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
            estadoEquipoDetalle.setNombreestadoequipo(inputNombre);
            gestionarVariableEstadosEquiposBO.editarEstadoEquipo(estadoEquipoDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEstadoEquipo almacenarModificacionRegistro:  " + e.toString());
            System.out.println("Error ControllerDetallesEstadoEquipo almacenarModificacionRegistro: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarEstadoEquipo();
        return "variables_inventario";
    }

    public void cancelarEstadoEquipo() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "";
        idEstadoEquipo = null;
        estadoEquipoDetalle = null;
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
