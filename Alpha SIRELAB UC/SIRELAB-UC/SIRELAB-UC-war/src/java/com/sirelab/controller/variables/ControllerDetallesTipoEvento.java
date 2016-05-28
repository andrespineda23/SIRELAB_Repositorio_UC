/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposEventosBOInterface;
import com.sirelab.entidades.TipoEvento;
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
public class ControllerDetallesTipoEvento implements Serializable {

    @EJB
    GestionarVariableTiposEventosBOInterface gestionarVariableTiposEventoBO;

    private String inputDetalle, inputObservacion;
    private boolean validacionesDetalle, validacionesObservacion;
    private String mensajeFormulario;
    private BigInteger idTipoEvento;
    private TipoEvento tipoEventoDetalle;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;

    public ControllerDetallesTipoEvento() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleTipoEvento(BigInteger idDetalle) {
        this.idTipoEvento = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    private void cargarInformacionRegistro() {
        tipoEventoDetalle = gestionarVariableTiposEventoBO.consultarTipoEventoPorID(idTipoEvento);
        if (null != tipoEventoDetalle) {
            inputDetalle = tipoEventoDetalle.getDetalleevento();
            validacionesDetalle = true;
            inputObservacion = tipoEventoDetalle.getObservacion();
            validacionesObservacion = true;
            modificacionesRegistro = false;
        }
    }

    public void validarDetalle() {
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty()) && (inputDetalle.trim().length() > 0)) {
            int tam = inputDetalle.length();
            if (tam >= 3) {
                if (Utilidades.validarCaracterString(inputDetalle)) {
                    validacionesDetalle = true;
                } else {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El detalle se encuentra incorrecto. " + constantes.VARIABLE_NOMBRE));
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El tamaño minimo permitido es 3 caracteres. " + constantes.VARIABLE_NOMBRE));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El detalle se encuentra incorrecto. " + constantes.VARIABLE_NOMBRE));
        }
        modificacionesRegistro = true;
    }

    public void validarObservacion() {
        if (Utilidades.validarNulo(inputObservacion) && (!inputObservacion.isEmpty()) && (inputObservacion.trim().length() > 0)) {
            int tam = inputObservacion.length();
            if (tam >= 6) {
                validacionesObservacion = true;
            } else {
                validacionesObservacion = false;
                FacesContext.getCurrentInstance().addMessage("form:inputObservacion", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.VARIABLE_NOMBRE));
            }
        } else {
            validacionesObservacion = false;
            FacesContext.getCurrentInstance().addMessage("form:inputObservacion", new FacesMessage("La observación se encuentra incorrecta. " + constantes.VARIABLE_NOMBRE));
        }
    }

    public void registrarModificacionTipoEvento() {
        if (modificacionesRegistro == true) {
            if (validacionesDetalle == true && validacionesObservacion == true) {
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
            tipoEventoDetalle.setDetalleevento(inputDetalle);
            tipoEventoDetalle.setObservacion(inputObservacion);
            gestionarVariableTiposEventoBO.editarTipoEvento(tipoEventoDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetalleTipoEvento almacenarModificacionRegistro: " + e.toString(), e);
        }
    }

    public String cerrarPagina() {
        cancelarTipoEvento();
        return "variablesinventario";
    }

    public void cancelarTipoEvento() {
        inputDetalle = null;
        validacionesDetalle = false;
        inputObservacion = null;
        validacionesObservacion = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        modificacionesRegistro = false;
        idTipoEvento = null;
        tipoEventoDetalle = null;
    }

    //GET-SET
    public String getInputDetalle() {
        return inputDetalle;
    }

    public void setInputDetalle(String inputDetalle) {
        this.inputDetalle = inputDetalle;
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

    public String getInputObservacion() {
        return inputObservacion;
    }

    public void setInputObservacion(String inputObservacion) {
        this.inputObservacion = inputObservacion;
    }

}
