/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposComponentesBOInterface;
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

    public ControllerDetallesTipoComponente() {
    }

    @PostConstruct
    public void init() {
    }

    public void recibirIDDetalleTipoComponente(BigInteger idDetalle) {
        this.idTipoComponente = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
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

    public void registrarModificacionTipoComponente() {
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
            tipoComponenteDetalle.setNombretipo(inputNombre);
            gestionarVariableTiposComponenteBO.editarTipoComponente(tipoComponenteDetalle);
        } catch (Exception e) {
            System.out.println("Error ControllerDetalleTipoComponente almacenarModificacionRegistro: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarTipoComponente();
        return "variables_inventario";
    }

    public void cancelarTipoComponente() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "";
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

}
