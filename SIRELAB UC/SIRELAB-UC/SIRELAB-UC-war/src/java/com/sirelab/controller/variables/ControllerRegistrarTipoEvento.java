/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposEventosBOInterface;
import com.sirelab.entidades.TipoEvento;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarTipoEvento implements Serializable {

    @EJB
    GestionarVariableTiposEventosBOInterface gestionarVariableTiposEventoBO;

    private String inputDetalle;
    private boolean validacionesDetalle;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerRegistrarTipoEvento() {
    }

    @PostConstruct
    public void init() {
        inputDetalle = null;
        validacionesDetalle = false;
        mensajeFormulario = "";
        BasicConfigurator.configure();
    }

    public void validarDetalle() {
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty())) {
            if (Utilidades.validarCaracterString(inputDetalle)) {
                validacionesDetalle = true;
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El detalle se encuentra incorrecto."));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El detalle se encuentra incorrecto."));
        }
    }

    public void registrarTipoEvento() {
        if (validacionesDetalle == true) {
            almacenarRegistroNuevo();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
            restaurarFormulario();
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            TipoEvento tipoNuevo = new TipoEvento();
            tipoNuevo.setDetalleevento(inputDetalle);
            gestionarVariableTiposEventoBO.crearTipoEvento(tipoNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarTipoEvento almacenarRegistroNuevo:  " + e.toString());
            System.out.println("Error ControllerRegistrarTipoEvento almacenarRegistroNuevo: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarTipoEvento();
        return "variables_inventario";
    }

    public void cancelarTipoEvento() {
        inputDetalle = null;
        validacionesDetalle = false;
        mensajeFormulario = "";
    }

    private void restaurarFormulario() {
        inputDetalle = null;
        validacionesDetalle = false;
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

}
