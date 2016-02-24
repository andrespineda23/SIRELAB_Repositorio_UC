/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposCargosBOInterface;
import com.sirelab.entidades.TipoCargo;
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
public class ControllerDetallesTipoCargo implements Serializable {

    @EJB
    GestionarVariableTiposCargosBOInterface gestionarVariableTiposCargoBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;
    private BigInteger idTipoCargo;
    private TipoCargo tipoCargoDetalle;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;
    
    public ControllerDetallesTipoCargo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleTipoCargo(BigInteger idDetalle) {
        this.idTipoCargo = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    private void cargarInformacionRegistro() {
        tipoCargoDetalle = gestionarVariableTiposCargoBO.consultarTipoCargoPorID(idTipoCargo);
        if (null != tipoCargoDetalle) {
            inputNombre = tipoCargoDetalle.getNombrecargo();
            validacionesNombre = true;
            modificacionesRegistro = false;
        }
    }

    public void validarNombre() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty()) && (inputNombre.trim().length() > 0)) {
            int tam = inputNombre.length();
            if (tam >= 3) {
                if (Utilidades.validarCaracterString(inputNombre)) {
                    validacionesNombre = true;
                } else {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El Nombre se encuentra incorrecto. "+constantes.VARIABLE_NOMBRE));
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo permitido es 3 caracteres. "+constantes.VARIABLE_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El Nombre es obligatorio. "+constantes.VARIABLE_NOMBRE));
        }
        modificacionesRegistro = true;
    }

    public void registrarModificacionTipoCargo() {
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
            tipoCargoDetalle.setNombrecargo(inputNombre);
            gestionarVariableTiposCargoBO.editarTipoCargo(tipoCargoDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetalleTipoCargo almacenarModificacionRegistro:  " + e.toString(),e);
            logger.error("Error ControllerDetalleTipoCargo almacenarModificacionRegistro: " + e.toString(),e);
        }
    }

    public String cerrarPagina() {
        cancelarTipoCargo();
        return "variablesusuario";
    }

    public void cancelarTipoCargo() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        modificacionesRegistro = false;
        idTipoCargo = null;
        tipoCargoDetalle = null;
    }

    //GET-SET
    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public boolean isValidacionesNombre() {
        return validacionesNombre;
    }

    public void setValidacionesNombre(boolean validacionesNombre) {
        this.validacionesNombre = validacionesNombre;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public BigInteger getIdTipoCargo() {
        return idTipoCargo;
    }

    public void setIdTipoCargo(BigInteger idTipoCargo) {
        this.idTipoCargo = idTipoCargo;
    }

    public TipoCargo getTipoCargoDetalle() {
        return tipoCargoDetalle;
    }

    public void setTipoCargoDetalle(TipoCargo tipoCargoDetalle) {
        this.tipoCargoDetalle = tipoCargoDetalle;
    }

    public boolean isModificacionesRegistro() {
        return modificacionesRegistro;
    }

    public void setModificacionesRegistro(boolean modificacionesRegistro) {
        this.modificacionesRegistro = modificacionesRegistro;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
