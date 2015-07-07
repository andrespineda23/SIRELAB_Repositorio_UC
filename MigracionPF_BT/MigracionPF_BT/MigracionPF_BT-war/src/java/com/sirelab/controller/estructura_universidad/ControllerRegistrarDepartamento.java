/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarDepartamentosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.List;
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
public class ControllerRegistrarDepartamento implements Serializable {

    @EJB
    GestionarDepartamentosBOInterface gestionarDepartamentosBO;

    private String nuevoNombre;
    private List<Facultad> listaFacultades;
    private Facultad nuevoFacultad;
    //
    private boolean validacionesNombre, validacionesFacultad;
    private String mensajeFormulario;

    public ControllerRegistrarDepartamento() {
    }

    @PostConstruct
    public void init() {
        nuevoFacultad = null;
        nuevoNombre = null;
        validacionesFacultad = false;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void validarNombreDepartamento() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrectp."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarFacultadDepartamento() {
        if (Utilidades.validarNulo(nuevoFacultad)) {
            validacionesFacultad = true;
        } else {
            validacionesFacultad = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoFacultad", new FacesMessage("La facultad es obligatoria."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesFacultad == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoDepartamento() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoDepartamentoEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenarNuevoDepartamentoEnSistema() {
        try {
            Departamento departamentoNuevo = new Departamento();
            departamentoNuevo.setNombredepartamento(nuevoNombre);
            departamentoNuevo.setFacultad(nuevoFacultad);
            gestionarDepartamentosBO.crearNuevaDepartamento(departamentoNuevo);
            limpiarFormulario();
        } catch (Exception e) {
            System.out.println("Error almacenarNuevoDepartamentoEnSistema almacenarNuevoDepartamentoEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        nuevoFacultad = null;
        nuevoNombre = null;
        validacionesFacultad = false;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroDepartamento() {
        nuevoFacultad = null;
        nuevoNombre = null;
        validacionesFacultad = false;
        validacionesNombre = false;
        mensajeFormulario = "";
        listaFacultades = null;
    }

    //GET-SET
    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = gestionarDepartamentosBO.consultarFacultadesRegistradas();
        }
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Facultad getNuevoFacultad() {
        return nuevoFacultad;
    }

    public void setNuevoFacultad(Facultad nuevoFacultad) {
        this.nuevoFacultad = nuevoFacultad;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
