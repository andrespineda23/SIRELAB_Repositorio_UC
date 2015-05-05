/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposPerfilesBOInterface;
import com.sirelab.entidades.TipoPerfil;
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
public class ControllerAdministrarTipoPerfil implements Serializable {

    @EJB
    GestionarVariableTiposPerfilesBOInterface gestionarVariableTiposPerfilesBO;

    private List<TipoPerfil> listaTiposPerfiles;
    private String inputNombre, inputCodigo;
    private TipoPerfil tipoPerfilEditar;
    private BigInteger idTipoPerfil;
    private String nombreEditar, codigoEditar;
    private boolean validacionesNombreNuevo, validacionesNombreEditar;
    private boolean validacionesCodigoNuevo, validacionesCodigoEditar;
    private String mensajeFormularioNuevo, mensajeFormularioEditar;
    private String visibleEditar;

    public ControllerAdministrarTipoPerfil() {
    }

    @PostConstruct
    public void init() {
        listaTiposPerfiles = gestionarVariableTiposPerfilesBO.consultarTiposPerfilesRegistrados();
        inputNombre = null;
        inputCodigo = null;
        tipoPerfilEditar = null;
        nombreEditar = null;
        codigoEditar = null;
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

    public void validarCodigoNuevo() {
        if (Utilidades.validarNulo(inputCodigo) && (!inputCodigo.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputCodigo)) {
                TipoPerfil registro = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorCodigo(inputCodigo);
                if (null != registro) {
                    validacionesCodigoNuevo = true;
                } else {
                    validacionesCodigoNuevo = false;
                    FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputCodigo", new FacesMessage("El codigo ya se encuentra registrado."));
                }
            } else {
                validacionesCodigoNuevo = false;
                FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputCodigo", new FacesMessage("El codigo se encuentra incorrecto."));
            }
        } else {
            validacionesCodigoNuevo = false;
            FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputCodigo", new FacesMessage("El campo Codigo es obligatorio."));
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

    public void validarCodigoEditar() {
        if (Utilidades.validarNulo(codigoEditar) && (!codigoEditar.isEmpty())) {
            if (Utilidades.validarCaracterString(codigoEditar)) {
                TipoPerfil registro = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorCodigo(codigoEditar);
                if (null != registro) {
                    validacionesCodigoEditar = true;
                } else {
                    if (!tipoPerfilEditar.getIdtipoperfil().equals(registro.getIdtipoperfil())) {
                        validacionesCodigoEditar = false;
                        FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:inputCodigo", new FacesMessage("El codigo ya se encuentra registrado."));
                    } else {
                        validacionesCodigoEditar = true;
                    }
                }
            } else {
                validacionesCodigoEditar = false;
                FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:codigoEditar", new FacesMessage("El codigo se encuentra incorrecto."));
            }
        } else {
            validacionesCodigoEditar = false;
            FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:codigoEditar", new FacesMessage("El campo Codigo es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion(int tipoR) {
        boolean retorno = true;
        if (tipoR == 1) {
            if (validacionesNombreNuevo == false) {
                retorno = false;
            }
            if (validacionesCodigoNuevo == false) {
                retorno = false;
            }
        } else {
            if (validacionesNombreEditar == false) {
                retorno = false;
            }
            if (validacionesCodigoEditar == false) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarNuevoTipoPerfil() {
        if (validarResultadosValidacion(1) == true) {
            almacenarNuevoTipoPerfil();
            mensajeFormularioNuevo = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormularioNuevo = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoTipoPerfil() {
        TipoPerfil nuevo = new TipoPerfil();
        nuevo.setCodigo(inputCodigo);
        nuevo.setNombre(inputNombre);
        gestionarVariableTiposPerfilesBO.crearTipoPerfil(nuevo);
        cancelarNuevoRegistro();
    }

    public void cancelarNuevoRegistro() {
        validacionesNombreNuevo = false;
        validacionesCodigoNuevo = false;
        inputNombre = null;
        inputCodigo = null;
    }

    public void registrarModificacionTipoPerfil() {
        if (validarResultadosValidacion(2) == true) {
            almacenarModificacionTipoPerfil();
            mensajeFormularioNuevo = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormularioNuevo = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void recibirIDTipoPerfilEditar(BigInteger idRegistro) {
        idTipoPerfil = idRegistro;
        tipoPerfilEditar = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorID(idRegistro);
        nombreEditar = tipoPerfilEditar.getNombre();
        codigoEditar = tipoPerfilEditar.getCodigo();
        validacionesNombreEditar = true;
        validacionesCodigoEditar = true;
        visibleEditar = "visible";
    }

    private void almacenarModificacionTipoPerfil() {
        tipoPerfilEditar.setNombre(nombreEditar);
        tipoPerfilEditar.setCodigo(codigoEditar);
        gestionarVariableTiposPerfilesBO.editarTipoPerfil(tipoPerfilEditar);
        tipoPerfilEditar = null;
        idTipoPerfil = null;
        validacionesNombreEditar = false;
        validacionesCodigoEditar = false;
        codigoEditar = null;
        nombreEditar = null;
    }

    public void cancelarModificarRegistro() {
        visibleEditar = "hidden";
        tipoPerfilEditar = null;
        idTipoPerfil = null;
        validacionesNombreEditar = false;
        validacionesCodigoEditar = false;
        codigoEditar = null;
        nombreEditar = null;
    }

    public void limpiarProcesoPagina() {
        cancelarModificarRegistro();
        cancelarNuevoRegistro();
        listaTiposPerfiles = null;
    }

    //GET-SET
    public List<TipoPerfil> getListaTiposPerfiles() {
        return listaTiposPerfiles;
    }

    public void setListaTiposPerfiles(List<TipoPerfil> listaTiposPerfiles) {
        this.listaTiposPerfiles = listaTiposPerfiles;
    }

    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public String getInputCodigo() {
        return inputCodigo;
    }

    public void setInputCodigo(String inputCodigo) {
        this.inputCodigo = inputCodigo;
    }

    public TipoPerfil getTipoPerfilEditar() {
        return tipoPerfilEditar;
    }

    public void setTipoPerfilEditar(TipoPerfil tipoPerfilEditar) {
        this.tipoPerfilEditar = tipoPerfilEditar;
    }

    public BigInteger getIdTipoPerfil() {
        return idTipoPerfil;
    }

    public void setIdTipoPerfil(BigInteger idTipoPerfil) {
        this.idTipoPerfil = idTipoPerfil;
    }

    public String getNombreEditar() {
        return nombreEditar;
    }

    public void setNombreEditar(String nombreEditar) {
        this.nombreEditar = nombreEditar;
    }

    public String getCodigoEditar() {
        return codigoEditar;
    }

    public void setCodigoEditar(String codigoEditar) {
        this.codigoEditar = codigoEditar;
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
