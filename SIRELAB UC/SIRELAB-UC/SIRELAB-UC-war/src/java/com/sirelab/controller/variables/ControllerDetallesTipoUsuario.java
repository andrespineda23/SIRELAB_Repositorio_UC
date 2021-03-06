/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposUsuarioBOInterface;
import com.sirelab.entidades.TipoUsuario;
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
public class ControllerDetallesTipoUsuario implements Serializable {

    @EJB
    GestionarVariableTiposUsuarioBOInterface gestionarVariableTiposUsuarioBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;
    private BigInteger idTipoUsuario;
    private TipoUsuario tipoUsuarioDetalle;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesTipoUsuario() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleTipoUsuario(BigInteger idDetalle) {
        this.idTipoUsuario = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    private void cargarInformacionRegistro() {
        tipoUsuarioDetalle = gestionarVariableTiposUsuarioBO.consultarTipoUsuarioPorID(idTipoUsuario);
        if (null != tipoUsuarioDetalle) {
            inputNombre = tipoUsuarioDetalle.getNombretipousuario();
            validacionesNombre = true;
            modificacionesRegistro = false;
        }
    }

    public void validarNombre() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty())  && (inputNombre.trim().length() > 0)) {
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

    public void registrarModificacionTipoUsuario() {
        if (modificacionesRegistro == true) {
            if (validacionesNombre == true) {
                almacenarModificacionRegistro();
                cargarInformacionRegistro();
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificacionRegistro() {
        try {
            tipoUsuarioDetalle.setNombretipousuario(inputNombre);
            gestionarVariableTiposUsuarioBO.editarTipoUsuario(tipoUsuarioDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesTipoUsuario almacenarModificacionRegistro:  " + e.toString());
            System.out.println("Error ControllerDetallesTipoUsuario almacenarModificacionRegistro: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarTipoUsuario();
        return "variables_usuario";
    }

    public void cancelarTipoUsuario() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        idTipoUsuario = null;
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

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
