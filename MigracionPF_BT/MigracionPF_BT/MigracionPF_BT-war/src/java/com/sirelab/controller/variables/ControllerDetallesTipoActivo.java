/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposActivosBOInterface;
import com.sirelab.entidades.TipoActivo;
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
public class ControllerDetallesTipoActivo implements Serializable {

    @EJB
    GestionarVariableTiposActivosBOInterface gestionarVariableTiposActivoBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;
    private boolean modificacionesRegistro;
    private BigInteger idTipoActivo;
    private TipoActivo tipoActivoDetalle;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerDetallesTipoActivo() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleTipoActivo(BigInteger idDetalle) {
        this.idTipoActivo = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
    }

    private void cargarInformacionRegistro() {
        tipoActivoDetalle = gestionarVariableTiposActivoBO.consultarTipoActivoPorID(idTipoActivo);
        if (null != tipoActivoDetalle) {
            inputNombre = tipoActivoDetalle.getNombretipoactivo();
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

    public void registrarModificacionTipoActivo() {
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
            tipoActivoDetalle.setNombretipoactivo(inputNombre);
            gestionarVariableTiposActivoBO.editarTipoActivo(tipoActivoDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetalleTipoActivo almacenarModificacionRegistro:  " + e.toString());
            System.out.println("Error ControllerDetalleTipoActivo almacenarModificacionRegistro: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarTipoActivo();
        return "variables_inventario";
    }

    public void cancelarTipoActivo() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "";
        idTipoActivo = null;
        modificacionesRegistro = false;
        tipoActivoDetalle = null;
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
