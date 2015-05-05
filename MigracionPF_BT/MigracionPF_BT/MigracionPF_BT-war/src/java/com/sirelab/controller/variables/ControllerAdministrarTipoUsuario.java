/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposUsuarioBOInterface;
import com.sirelab.entidades.TipoUsuario;
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
public class ControllerAdministrarTipoUsuario implements Serializable {

    @EJB
    GestionarVariableTiposUsuarioBOInterface gestionarVariableTiposUsuarioBO;

    private List<TipoUsuario> listaTiposUsuario;
    private String inputNombre;
    private TipoUsuario tipoUsuarioEditar;
    private BigInteger idTipoUsuario;
    private String nombreEditar;
    private boolean validacionesNombreNuevo, validacionesNombreEditar;
    private String mensajeFormularioNuevo, mensajeFormularioEditar;
    private String visibleEditar;

    public ControllerAdministrarTipoUsuario() {
    }

    @PostConstruct
    public void init() {
        listaTiposUsuario = gestionarVariableTiposUsuarioBO.consultarTiposUsuariosRegistrados();
        inputNombre = null;
        tipoUsuarioEditar = null;
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

    public void registrarNuevoTipoUsuario() {
        if (validarResultadosValidacion(1) == true) {
            almacenarNuevoTipoUsuario();
            mensajeFormularioNuevo = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormularioNuevo = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoTipoUsuario() {
        TipoUsuario nuevo = new TipoUsuario();
        nuevo.setNombretipousuario(inputNombre);
        gestionarVariableTiposUsuarioBO.crearTipoUsuario(nuevo);
        cancelarNuevoRegistro();
    }

    public void cancelarNuevoRegistro() {
        validacionesNombreNuevo = false;
        inputNombre = null;
    }

    public void registrarModificacionTipoUsuario() {
        if (validarResultadosValidacion(2) == true) {
            almacenarModificacionTipoUsuario();
            mensajeFormularioNuevo = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormularioNuevo = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void recibirIDTipoUsuarioEditar(BigInteger idRegistro) {
        idTipoUsuario = idRegistro;
        tipoUsuarioEditar = gestionarVariableTiposUsuarioBO.consultarTipoUsuarioPorID(idRegistro);
        nombreEditar = tipoUsuarioEditar.getNombretipousuario();
        validacionesNombreEditar = true;
        visibleEditar = "visible";
    }

    private void almacenarModificacionTipoUsuario() {
        tipoUsuarioEditar.setNombretipousuario(nombreEditar);
        gestionarVariableTiposUsuarioBO.editarTipoUsuario(tipoUsuarioEditar);
        tipoUsuarioEditar = null;
        idTipoUsuario = null;
        validacionesNombreEditar = false;
        nombreEditar = null;
    }

    public void cancelarModificarRegistro() {
        visibleEditar = "hidden";
        tipoUsuarioEditar = null;
        idTipoUsuario = null;
        validacionesNombreEditar = false;
        nombreEditar = null;
    }

    public void limpiarProcesoPagina() {
        cancelarModificarRegistro();
        cancelarNuevoRegistro();
        listaTiposUsuario = null;
    }

    //GET-SET
    public List<TipoUsuario> getListaTiposUsuario() {
        return listaTiposUsuario;
    }

    public void setListaTiposUsuario(List<TipoUsuario> listaTiposUsuario) {
        this.listaTiposUsuario = listaTiposUsuario;
    }

    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public TipoUsuario getTipoUsuarioEditar() {
        return tipoUsuarioEditar;
    }

    public void setTipoUsuarioEditar(TipoUsuario tipoUsuarioEditar) {
        this.tipoUsuarioEditar = tipoUsuarioEditar;
    }

    public BigInteger getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(BigInteger idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
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
