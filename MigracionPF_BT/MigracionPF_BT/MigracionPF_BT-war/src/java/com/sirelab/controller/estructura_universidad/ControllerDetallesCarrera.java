/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarCarrerasBOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
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
public class ControllerDetallesCarrera implements Serializable {

    @EJB
    GestionarCarrerasBOInterface gestionarCarrerasBO;

    private Carrera carreraDetalles;
    private BigInteger idCarrera;
    private List<Facultad> listaFacultades;
    private List<Departamento> listaDepartamentos;
    private String editarNombre, editarCodigo;
    private Facultad editarFacultad;
    private Departamento editarDepartamento;
    private boolean activarModificacionDepartamento;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesFacultad, validacionesDepartamento;
    private String mensajeFormulario;

    public ControllerDetallesCarrera() {
    }

    @PostConstruct
    public void init() {
        listaFacultades = gestionarCarrerasBO.consultarFacultadesRegistradas();
        activarModificacionDepartamento = true;
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "";

    }

    public void restaurarInformacionCarrera() {
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "";
        carreraDetalles = new Carrera();
        recibirIDCarrerasDetalles(idCarrera);
    }

    public void asignarValoresVariablesCarrera() {
        editarCodigo = null;
        editarDepartamento = null;
        editarFacultad = null;
        editarNombre = null;
    }

    public void recibirIDCarrerasDetalles(BigInteger idRegistro) {
        this.idCarrera = idRegistro;
        carreraDetalles = gestionarCarrerasBO.obtenerCarreraPorIDCarrera(idCarrera);
        asignarValoresVariablesCarrera();
    }

    public void validarNombreCarrera() {
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

    public void validarCodigoCarrera() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty())) {
            if (!Utilidades.validarCaracterString(editarCodigo)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio."));
        }
    }

    public void validarDepartamentoCarrera() {
        if (Utilidades.validarNulo(editarDepartamento)) {
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDepartamento", new FacesMessage("El departamento es obligatorio."));
        }
    }

    public void actualizarFacultad() {
        if (Utilidades.validarNulo(editarFacultad)) {
            editarDepartamento = null;
            listaDepartamentos = gestionarCarrerasBO.consultarDepartamentosPorIDFacultad(editarFacultad.getIdfacultad());
            activarModificacionDepartamento = false;
            validacionesFacultad = true;
        } else {
            validacionesDepartamento = false;
            validacionesFacultad = false;
            editarDepartamento = null;
            listaDepartamentos = null;
            activarModificacionDepartamento = true;
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
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesFacultad == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        Carrera carrera = gestionarCarrerasBO.obtenerCarreraPorCodigoYDepartamento(editarCodigo, editarDepartamento.getIddepartamento());
        if (null != carrera) {
            if (!carreraDetalles.getIdcarrera().equals(carrera.getIdcarrera())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionCarrera() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarModificacionCarreraEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el departamento seleccionado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionCarreraEnSistema() {
        try {
            carreraDetalles.setNombrecarrera(editarNombre);
            carreraDetalles.setCodigocarrera(editarCodigo);
            carreraDetalles.setDepartamento(editarDepartamento);
            gestionarCarrerasBO.modificarInformacionCarrera(carreraDetalles);
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarCarreras almacenarModificacionCarreraEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public Carrera getCarreraDetalles() {
        return carreraDetalles;
    }

    public void setCarreraDetalles(Carrera carreraDetalles) {
        this.carreraDetalles = carreraDetalles;
    }

    public BigInteger getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(BigInteger idCarrera) {
        this.idCarrera = idCarrera;
    }

    public List<Facultad> getListaFacultades() {
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
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

    public Facultad getEditarFacultad() {
        return editarFacultad;
    }

    public void setEditarFacultad(Facultad editarFacultad) {
        this.editarFacultad = editarFacultad;
    }

    public Departamento getEditarDepartamento() {
        return editarDepartamento;
    }

    public void setEditarDepartamento(Departamento editarDepartamento) {
        this.editarDepartamento = editarDepartamento;
    }

    public boolean isActivarModificacionDepartamento() {
        return activarModificacionDepartamento;
    }

    public void setActivarModificacionDepartamento(boolean activarModificacionDepartamento) {
        this.activarModificacionDepartamento = activarModificacionDepartamento;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
