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
public class ControllerDetallesTipoEvento implements Serializable {

    @EJB
    GestionarVariableTiposEventosBOInterface gestionarVariableTiposEventoBO;

    private String inputDetalle;
    private boolean validacionesDetalle;
    private String mensajeFormulario;
    private BigInteger idTipoEvento;
    private TipoEvento tipoEventoDetalle;
    private boolean modificacionesRegistro;

    public ControllerDetallesTipoEvento() {
    }

    @PostConstruct
    public void init() {
    }

    public void recibirIDDetalleTipoEvento(BigInteger idDetalle) {
        this.idTipoEvento = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
    }

    private void cargarInformacionRegistro() {
        tipoEventoDetalle = gestionarVariableTiposEventoBO.consultarTipoEventoPorID(idTipoEvento);
        if (null != tipoEventoDetalle) {
            inputDetalle = tipoEventoDetalle.getDetalleevento();
            validacionesDetalle = true;
            modificacionesRegistro = false;
        }
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
        modificacionesRegistro = true;
    }

    public void registrarModificacionTipoEvento() {
        if (modificacionesRegistro == true) {
            if (validacionesDetalle == true) {
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
            tipoEventoDetalle.setDetalleevento(inputDetalle);
            gestionarVariableTiposEventoBO.editarTipoEvento(tipoEventoDetalle);
        } catch (Exception e) {
            System.out.println("Error ControllerDetalleTipoEvento almacenarModificacionRegistro: " + e.toString());
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

}
