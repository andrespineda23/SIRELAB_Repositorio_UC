/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposActivosBOInterface;
import com.sirelab.entidades.TipoActivo;
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
public class ControllerAdministrarTipoActivo implements Serializable {

    @EJB
    GestionarVariableTiposActivosBOInterface gestionarVariableTiposActivosBO;

    private List<TipoActivo> listaTiposActivos;
    private String inputNombre;
    private TipoActivo tipoActivoEditar;
    private BigInteger idTipoActivo;
    private String nombreEditar;
    private boolean validacionesNombreNuevo, validacionesNombreEditar;
    private String mensajeFormularioNuevo, mensajeFormularioEditar;
    private String visibleEditar;

    public ControllerAdministrarTipoActivo() {
    }

    @PostConstruct
    public void init() {
        listaTiposActivos = gestionarVariableTiposActivosBO.consultarTiposActivosRegistrados();
        inputNombre = null;
        tipoActivoEditar = null;
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

    public void registrarNuevoTipoActivo() {
        if (validarResultadosValidacion(1) == true) {
            almacenarNuevoTipoActivo();
            mensajeFormularioNuevo = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormularioNuevo = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoTipoActivo() {
        TipoActivo nuevo = new TipoActivo();
        nuevo.setNombretipoactivo(inputNombre);
        gestionarVariableTiposActivosBO.crearTipoActivo(nuevo);
        cancelarNuevoRegistro();
    }

    public void cancelarNuevoRegistro() {
        validacionesNombreNuevo = false;
        inputNombre = null;
    }

    public void registrarModificacionTipoActivo() {
        if (validarResultadosValidacion(2) == true) {
            almacenarModificacionTipoActivo();
            mensajeFormularioNuevo = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormularioNuevo = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void recibirIDTipoActivoEditar(BigInteger idRegistro) {
        idTipoActivo = idRegistro;
        tipoActivoEditar = gestionarVariableTiposActivosBO.consultarTipoActivoPorID(idRegistro);
        nombreEditar = tipoActivoEditar.getNombretipoactivo();
        validacionesNombreEditar = true;
        visibleEditar = "visible";
    }

    private void almacenarModificacionTipoActivo() {
        tipoActivoEditar.setNombretipoactivo(nombreEditar);
        gestionarVariableTiposActivosBO.editarTipoActivo(tipoActivoEditar);
        tipoActivoEditar = null;
        idTipoActivo = null;
        validacionesNombreEditar = false;
        nombreEditar = null;
    }

    public void cancelarModificarRegistro() {
        visibleEditar = "hidden";
        tipoActivoEditar = null;
        idTipoActivo = null;
        validacionesNombreEditar = false;
        nombreEditar = null;
    }

    public void limpiarProcesoPagina() {
        cancelarModificarRegistro();
        cancelarNuevoRegistro();
        listaTiposActivos = null;
    }

    //GET-SET
    public List<TipoActivo> getListaTiposActivos() {
        return listaTiposActivos;
    }

    public void setListaTiposActivos(List<TipoActivo> listaTiposActivos) {
        this.listaTiposActivos = listaTiposActivos;
    }

    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public TipoActivo getTipoActivoEditar() {
        return tipoActivoEditar;
    }

    public void setTipoActivoEditar(TipoActivo tipoActivoEditar) {
        this.tipoActivoEditar = tipoActivoEditar;
    }

    public BigInteger getIdTipoActivo() {
        return idTipoActivo;
    }

    public void setIdTipoActivo(BigInteger idTipoActivo) {
        this.idTipoActivo = idTipoActivo;
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
