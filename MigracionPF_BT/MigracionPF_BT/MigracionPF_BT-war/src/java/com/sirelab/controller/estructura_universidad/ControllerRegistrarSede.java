/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarSedeBOInterface;
import com.sirelab.entidades.Sede;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarSede implements Serializable {

    @EJB
    GestionarSedeBOInterface gestionarSedeBO;

    private String nuevoNombre, nuevoDireccion, nuevoTelefono;
    //
    private boolean validacionesNombre, validacionesDireccion, validacionesTelefono;
    private String mensajeFormulario;
     private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerRegistrarSede() {
    }

    @PostConstruct
    public void init() {
        nuevoNombre = null;
        nuevoDireccion = null;
        nuevoTelefono = null;
        validacionesDireccion = false;
        validacionesNombre = false;
        validacionesTelefono = false;
        mensajeFormulario = "";
         BasicConfigurator.configure();
    }

    public void validarNombreSede() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarDireccionSede() {
        if (Utilidades.validarNulo(nuevoDireccion) && (!nuevoDireccion.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoDireccion)) {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección ingresada es incorrecta."));
            } else {
                validacionesDireccion = true;
            }
        } else {
            validacionesDireccion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección es obligatoria."));
        }
    }

    public void validarTelefonoSede() {
        if (Utilidades.validarNulo(nuevoTelefono) && (!nuevoTelefono.isEmpty())) {
            if (!Utilidades.isNumber(nuevoTelefono)) {
                validacionesTelefono = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono", new FacesMessage("El telefono ingresado es incorrecto."));
            } else {
                validacionesTelefono = true;
            }
        } else {
            validacionesTelefono = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono", new FacesMessage("El telefono es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesDireccion == false) {
            retorno = false;
        }

        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesTelefono == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoSede() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoSedeEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoSedeEnSistema() {
        try {
            Sede nuevaSede = new Sede();
            nuevaSede.setDireccionsede(nuevoDireccion);
            nuevaSede.setNombresede(nuevoNombre);
            nuevaSede.setTelefonosede(nuevoTelefono);
            gestionarSedeBO.crearNuevaSede(nuevaSede);
            cancelarRegistroSede();
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarSede almacenarNuevoSedeEnSistema:  "+e.toString());
            System.out.println("Error ControllerRegistrarSede almacenarNuevoSedeEnSistema : " + e.toString());
        }
    }

    public void cancelarRegistroSede() {
        nuevoNombre = null;
        nuevoDireccion = null;
        nuevoTelefono = null;
        validacionesDireccion = false;
        validacionesNombre = false;
        validacionesTelefono = false;
        mensajeFormulario = "";
    }

    //GET-SET
    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoDireccion() {
        return nuevoDireccion;
    }

    public void setNuevoDireccion(String nuevoDireccion) {
        this.nuevoDireccion = nuevoDireccion;
    }

    public String getNuevoTelefono() {
        return nuevoTelefono;
    }

    public void setNuevoTelefono(String nuevoTelefono) {
        this.nuevoTelefono = nuevoTelefono;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
