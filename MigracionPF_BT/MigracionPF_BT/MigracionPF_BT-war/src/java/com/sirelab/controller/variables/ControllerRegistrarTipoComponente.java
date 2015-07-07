/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposComponentesBOInterface;
import com.sirelab.entidades.TipoComponente;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarTipoComponente implements Serializable {

    @EJB
    GestionarVariableTiposComponentesBOInterface gestionarVariableTiposComponenteBO;

    private String inputNombre;
    private boolean validacionesNombre;
    private String mensajeFormulario;

    public ControllerRegistrarTipoComponente() {
    }

    @PostConstruct
    public void init() {
        inputNombre = null;
        validacionesNombre = false;
        mensajeFormulario = "";
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
    }

    public void registrarTipoComponente() {
        if (validacionesNombre == true) {
            almacenarRegistroNuevo();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
            restaurarFormulario();
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            TipoComponente tipoNuevo = new TipoComponente();
            tipoNuevo.setNombretipo(inputNombre);
            gestionarVariableTiposComponenteBO.crearTipoComponente(tipoNuevo);
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarTipoComponente almacenarRegistroNuevo: " + e.toString());
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
    }

    private void restaurarFormulario() {
        inputNombre = null;
        validacionesNombre = false;
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
