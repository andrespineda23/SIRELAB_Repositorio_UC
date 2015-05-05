/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarFacultadesBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@RequestScoped
public class ControllerDetallesFacultad implements Serializable {

    @EJB
    GestionarFacultadesBOInterface gestionarFacultadBO;

    private Facultad facultadDetalles;
    private BigInteger idFacultad;
    private String editarNombre, editarCodigo;
    private boolean validacionesNombre, validacionesCodigo;
    private String mensajeFormulario;

    public ControllerDetallesFacultad() {
    }

    @PostConstruct
    public void init() {
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "";
    }

    public void restaurarInformacionFacultad() {
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "";
        facultadDetalles = new Facultad();
        recibirIDFacultadesDetalles(idFacultad);
    }

    public void asignarValoresVariablesFacultad() {
        editarCodigo = facultadDetalles.getCodigofacultad();
        editarNombre = facultadDetalles.getNombrefacultad();
    }

    public void recibirIDFacultadesDetalles(BigInteger idArea) {
        this.idFacultad = idArea;
        facultadDetalles = gestionarFacultadBO.obtenerFacultadPorIDCodigo(editarCodigo);
        asignarValoresVariablesFacultad();
    }

    public void validarNombreFacultad() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(editarNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarCodigoFacultad() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                Facultad registro = gestionarFacultadBO.obtenerFacultadPorIDCodigo(editarCodigo);
                if (null == registro) {
                    validacionesCodigo = true;
                } else {
                    if (!facultadDetalles.getIdfacultad().equals(registro.getIdfacultad())) {
                        validacionesCodigo = false;
                        FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado ya se encuentra registrado."));
                    } else {
                        validacionesCodigo = false;
                    }
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio."));
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

    public void registrarModificacionFacultad() {
        if (validarResultadosValidacion() == true) {
            almacenarModificacionFacultadEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionFacultadEnSistema() {
        try {
            Facultad editarRegistro = new Facultad();
            editarRegistro.setCodigofacultad(editarCodigo);
            editarRegistro.setNombrefacultad(editarNombre);
            gestionarFacultadBO.modificarInformacionFacultad(editarRegistro);
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarFacultades almacenarNuevoFacultadEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public Facultad getFacultadDetalles() {
        return facultadDetalles;
    }

    public void setFacultadDetalles(Facultad facultadDetalles) {
        this.facultadDetalles = facultadDetalles;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarCodigo() {
        return editarCodigo;
    }

    public void setEditarCodigo(String editarCodigo) {
        this.editarCodigo = editarCodigo;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
