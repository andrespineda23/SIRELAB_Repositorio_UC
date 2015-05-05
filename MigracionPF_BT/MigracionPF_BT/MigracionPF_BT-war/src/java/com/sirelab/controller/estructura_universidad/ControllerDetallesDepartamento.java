/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarDepartamentosBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Departamento;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesDepartamento implements Serializable {

    @EJB
    GestionarDepartamentosBOInterface gestionarDepartamentosBO;

    private Departamento departamentoDetalles;
    private BigInteger idDepartamento;
    private String editarNombre;
    private List<Facultad> listaFacultades;
    private Facultad editarFacultad;
    //
    private boolean validacionesNombre, validacionesFacultad;
    private String mensajeFormulario;

    public ControllerDetallesDepartamento() {
    }

    @PostConstruct
    public void init() {
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "";
        listaFacultades = gestionarDepartamentosBO.consultarFacultadesRegistradas();
    }

    public void restaurarInformacionDepartamento() {
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "";
        departamentoDetalles = new Departamento();
        recibirIDDepartamentosDetalles(idDepartamento);
    }

    public void asignarValoresVariablesDepartamento() {
        editarFacultad = null;
        editarNombre = null;
    }

    public void recibirIDDepartamentosDetalles(BigInteger idDetalle) {
        this.idDepartamento = idDetalle;
        departamentoDetalles = gestionarDepartamentosBO.obtenerDepartamentoPorIDDepartamento(idDepartamento);
        asignarValoresVariablesDepartamento();
    }

    public void validarNombreDepartamento() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(editarNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrectp."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarFacultadDepartamento() {
        if (Utilidades.validarNulo(editarFacultad)) {
            validacionesFacultad = true;
        } else {
            validacionesFacultad = false;
            FacesContext.getCurrentInstance().addMessage("form:editarFacultad", new FacesMessage("La facultad es obligatoria."));
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

    public void registrarModificacionDepartamento() {
        if (validarResultadosValidacion() == true) {
            almacenarModificacionDepartamentoEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionDepartamentoEnSistema() {
        try {
            departamentoDetalles.setNombredepartamento(editarNombre);
            departamentoDetalles.setFacultad(editarFacultad);
            gestionarDepartamentosBO.modificarInformacionDepartamento(departamentoDetalles);
        } catch (Exception e) {
            System.out.println("Error almacenarNuevoDepartamentoEnSistema almacenarNuevoDepartamentoEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public Departamento getDepartamentoDetalles() {
        return departamentoDetalles;
    }

    public void setDepartamentoDetalles(Departamento departamentoDetalles) {
        this.departamentoDetalles = departamentoDetalles;
    }

    public BigInteger getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(BigInteger idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public List<Facultad> getListaFacultades() {
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Facultad getEditarFacultad() {
        return editarFacultad;
    }

    public void setEditarFacultad(Facultad editarFacultad) {
        this.editarFacultad = editarFacultad;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
