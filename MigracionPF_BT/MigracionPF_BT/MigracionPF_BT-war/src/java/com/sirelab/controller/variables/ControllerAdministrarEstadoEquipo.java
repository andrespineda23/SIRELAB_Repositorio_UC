/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableEstadosEquiposBOInterface;
import com.sirelab.entidades.EstadoEquipo;
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
public class ControllerAdministrarEstadoEquipo implements Serializable {

    @EJB
    GestionarVariableEstadosEquiposBOInterface gestionarVariableEstadosEquiposBO;

    private List<EstadoEquipo> listaEstadosEquipos;
    private String inputNombre;
    private EstadoEquipo estadoEquipoEditar;
    private BigInteger idEstadoEquipo;
    private String nombreEditar;
    private boolean validacionesNombreNuevo, validacionesNombreEditar;
    private String mensajeFormularioNuevo, mensajeFormularioEditar;
    private String visibleEditar;

    public ControllerAdministrarEstadoEquipo() {
    }

    @PostConstruct
    public void init() {
        listaEstadosEquipos = gestionarVariableEstadosEquiposBO.consultarEstadosEquiposRegistrados();
        inputNombre = null;
        estadoEquipoEditar = null;
        nombreEditar = null;
        validacionesNombreEditar = false;
        validacionesNombreNuevo = false;
        visibleEditar = "hidden";
    }

    public void validarNombreNuevo() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty())) {
            if (Utilidades.validarCaracterString(inputNombre)) {
                validacionesNombreNuevo = true;
            } else {
                validacionesNombreNuevo = false;
                FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputNombre", new FacesMessage("El nombre se encuentra incorrecto."));
            }
        } else {
            validacionesNombreNuevo = false;
            FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputNombre", new FacesMessage("El campo Nombre es obligatorio."));
        }
    }

    public void validarNombreEditar() {
        if (Utilidades.validarNulo(nombreEditar) && (!nombreEditar.isEmpty())) {
            if (Utilidades.validarCaracterString(nombreEditar)) {
                validacionesNombreEditar = true;
            } else {
                validacionesNombreEditar = false;
                FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:nombreEditar", new FacesMessage("El nombre se encuentra incorrecto."));
            }
        } else {
            validacionesNombreEditar = false;
            FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:nombreEditar", new FacesMessage("El campo Nombre es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion(int tipoR) {
        boolean retorno = true;
        if (tipoR == 1) {
            if (validacionesNombreNuevo == false) {
                retorno = false;
            }
        } else {
            if (validacionesNombreEditar == false) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarNuevoEstadoEquipo() {
        if (validarResultadosValidacion(1) == true) {
            almacenarNuevoEstadoEquipo();
            mensajeFormularioNuevo = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormularioNuevo = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoEstadoEquipo() {
        EstadoEquipo nuevo = new EstadoEquipo();
        nuevo.setNombreestadoequipo(inputNombre);
        gestionarVariableEstadosEquiposBO.crearEstadoEquipo(nuevo);
        cancelarNuevoRegistro();
    }

    public void cancelarNuevoRegistro() {
        validacionesNombreNuevo = false;
        inputNombre = null;
    }

    public void registrarModificacionEstadoEquipo() {
        if (validarResultadosValidacion(2) == true) {
            almacenarModificacionEstadoEquipo();
            mensajeFormularioNuevo = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormularioNuevo = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void recibirIDEstadoEquipoEditar(BigInteger idRegistro) {
        idEstadoEquipo = idRegistro;
        estadoEquipoEditar = gestionarVariableEstadosEquiposBO.consultarEstadoEquipoPorID(idRegistro);
        nombreEditar = estadoEquipoEditar.getNombreestadoequipo();
        validacionesNombreEditar = true;
        visibleEditar = "visible";
    }

    private void almacenarModificacionEstadoEquipo() {
        estadoEquipoEditar.setNombreestadoequipo(nombreEditar);
        gestionarVariableEstadosEquiposBO.editarEstadoEquipo(estadoEquipoEditar);
        estadoEquipoEditar = null;
        idEstadoEquipo = null;
        validacionesNombreEditar = false;
        nombreEditar = null;
    }

    public void cancelarModificarRegistro() {
        visibleEditar = "hidden";
        estadoEquipoEditar = null;
        idEstadoEquipo = null;
        validacionesNombreEditar = false;
        nombreEditar = null;
    }

    public void limpiarProcesoPagina() {
        cancelarModificarRegistro();
        cancelarNuevoRegistro();
        listaEstadosEquipos = null;
    }

    //GET-SET
    public List<EstadoEquipo> getListaEstadosEquipos() {
        return listaEstadosEquipos;
    }

    public void setListaEstadosEquipos(List<EstadoEquipo> listaEstadosEquipos) {
        this.listaEstadosEquipos = listaEstadosEquipos;
    }

    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public EstadoEquipo getEstadoEquipoEditar() {
        return estadoEquipoEditar;
    }

    public void setEstadoEquipoEditar(EstadoEquipo estadoEquipoEditar) {
        this.estadoEquipoEditar = estadoEquipoEditar;
    }

    public BigInteger getIdEstadoEquipo() {
        return idEstadoEquipo;
    }

    public void setIdEstadoEquipo(BigInteger idEstadoEquipo) {
        this.idEstadoEquipo = idEstadoEquipo;
    }

    public String getNombreEditar() {
        return nombreEditar;
    }

    public void setNombreEditar(String nombreEditar) {
        this.nombreEditar = nombreEditar;
    }

    public String getMensajeFormularioNuevo() {
        return mensajeFormularioNuevo;
    }

    public void setMensajeFormularioNuevo(String mensajeFormularioNuevo) {
        this.mensajeFormularioNuevo = mensajeFormularioNuevo;
    }

    public String getMensajeFormularioEditar() {
        return mensajeFormularioEditar;
    }

    public void setMensajeFormularioEditar(String mensajeFormularioEditar) {
        this.mensajeFormularioEditar = mensajeFormularioEditar;
    }

    public String getVisibleEditar() {
        return visibleEditar;
    }

    public void setVisibleEditar(String visibleEditar) {
        this.visibleEditar = visibleEditar;
    }

}
