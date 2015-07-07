/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarFacultadesBOInterface;
import com.sirelab.entidades.Facultad;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarFacultad implements Serializable {

    @EJB
    GestionarFacultadesBOInterface gestionarFacultadBO;

    private String nuevoNombre, nuevoCodigo;
    private boolean validacionesNombre, validacionesCodigo;
    private String mensajeFormulario;

    public ControllerRegistrarFacultad() {
    }

    @PostConstruct
    public void init() {
        nuevoCodigo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void validarNombreFacultad() {
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

    public void validarCodigoFacultad() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                Facultad registro = gestionarFacultadBO.obtenerFacultadPorIDCodigo(nuevoCodigo);
                if (null == registro) {
                    validacionesCodigo = true;
                } else {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado ya se encuentra registrado."));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoFacultad() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoFacultadEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoFacultadEnSistema() {
        try {
            Facultad nuevaFacultad = new Facultad();
            nuevaFacultad.setCodigofacultad(nuevoCodigo);
            nuevaFacultad.setNombrefacultad(nuevoNombre);
            gestionarFacultadBO.crearNuevaFacultad(nuevaFacultad);
            cancelarRegistroFacultad();
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarFacultades almacenarNuevoFacultadEnSistema : " + e.toString());
        }
    }

    public void cancelarRegistroFacultad() {
        nuevoCodigo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    //GET-SET
    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
