/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposComponentesBOInterface;
import com.sirelab.entidades.TipoComponente;
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
public class ControllerDetallesTipoComponente implements Serializable {

    @EJB
    GestionarVariableTiposComponentesBOInterface gestionarVariableTiposComponenteBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;
    private boolean modificacionesRegistro;
    private BigInteger idTipoComponente;
    private TipoComponente tipoComponenteDetalle;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;

    public ControllerDetallesTipoComponente() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleTipoComponente(BigInteger idDetalle) {
        this.idTipoComponente = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    private void cargarInformacionRegistro() {
        tipoComponenteDetalle = gestionarVariableTiposComponenteBO.consultarTipoComponentePorID(idTipoComponente);
        if (null != tipoComponenteDetalle) {
            inputNombre = tipoComponenteDetalle.getNombretipo();
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
                    FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto. "+constantes.VARIABLE_NOMBRE));
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo permitido es 3 caracteres. "+constantes.VARIABLE_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto. "+constantes.VARIABLE_NOMBRE));
        }
        modificacionesRegistro = true;
    }

    public void registrarModificacionTipoComponente() {
        if (modificacionesRegistro == true) {
            if (validacionesNombre == true) {
                almacenarModificacionRegistro();
                cargarInformacionRegistro();
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificacionRegistro() {
        try {
            tipoComponenteDetalle.setNombretipo(inputNombre);
            gestionarVariableTiposComponenteBO.editarTipoComponente(tipoComponenteDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetalleTipoComponente almacenarModificacionRegistro:  " + e.toString(),e);
            logger.error("Error ControllerDetalleTipoComponente almacenarModificacionRegistro: " + e.toString(),e);
        }
    }

    public String cerrarPagina() {
        cancelarTipoComponente();
        return "variablesinventario";
    }

    public void cancelarTipoComponente() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        idTipoComponente = null;
        modificacionesRegistro = false;
        tipoComponenteDetalle = null;
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

    public BigInteger getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(BigInteger idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public TipoComponente getTipoComponenteDetalle() {
        return tipoComponenteDetalle;
    }

    public void setTipoComponenteDetalle(TipoComponente tipoComponenteDetalle) {
        this.tipoComponenteDetalle = tipoComponenteDetalle;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
