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
    private String inputNombre, inputCodigo, inputNombreRegistro, inputCodigoRegistro;
    private TipoPerfil tipoPerfilEditar;
    private BigInteger idTipoPerfil;
    private String nombreEditar, codigoEditar, nombreRegistroEditar, codigoRegistroEditar;
    private boolean validacionesNombreNuevo, validacionesNombreEditar;
    private boolean validacionesCodigoNuevo, validacionesCodigoEditar;
    private boolean validacionesNombreRegistroNuevo, validacionesNombreRegistroEditar;
    private boolean validacionesCodigoRegistroNuevo, validacionesCodigoRegistroEditar;
    private String mensajeFormularioNuevo, mensajeFormularioEditar;
    private boolean visibleEditar;

    public ControllerAdministrarTipoPerfil() {
    }

    @PostConstruct
    public void init() {
        inputNombre = null;
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        tipoPerfilEditar = null;
        nombreEditar = null;
        codigoEditar = null;
        validacionesNombreEditar = false;
        validacionesNombreNuevo = false;
        visibleEditar = true;
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

    public void validarNombreRegistroNuevo() {
        if (Utilidades.validarNulo(inputNombreRegistro) && (!inputNombreRegistro.isEmpty())) {
            if (Utilidades.validarCaracterString(inputNombreRegistro)) {
                validacionesNombreRegistroNuevo = true;
            } else {
                validacionesNombreRegistroNuevo = false;
                FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputNombreRegistro", new FacesMessage("El nombre se encuentra incorrecto."));
            }
        } else {
            validacionesNombreRegistroNuevo = false;
            FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputNombreRegistro", new FacesMessage("El campo Nombre es obligatorio."));
        }
    }

    public void validarCodigoNuevo() {
        if (Utilidades.validarNulo(inputCodigo) && (!inputCodigo.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputCodigo)) {
                TipoPerfil registro = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorCodigo(inputCodigo);
                if (null == registro) {
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

    public void validarCodigoRegistroNuevo() {
        if (Utilidades.validarNulo(inputCodigoRegistro) && (!inputCodigoRegistro.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputCodigoRegistro)) {
                validacionesCodigoRegistroNuevo = true;
            } else {
                validacionesCodigoRegistroNuevo = false;
                FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputCodigoRegistro", new FacesMessage("El codigo se encuentra incorrecto."));
            }
        } else {
            validacionesCodigoRegistroNuevo = false;
            FacesContext.getCurrentInstance().addMessage("formularioGeneral:formRegistrar:inputCodigoRegistro", new FacesMessage("El campo Codigo es obligatorio."));
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

    public void validarNombreRegistroEditar() {
        if (Utilidades.validarNulo(nombreRegistroEditar) && (!nombreRegistroEditar.isEmpty())) {
            if (Utilidades.validarCaracterString(nombreRegistroEditar)) {
                validacionesNombreRegistroEditar = true;
            } else {
                validacionesNombreRegistroEditar = false;
                FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:nombreRegistroEditar", new FacesMessage("El nombre se encuentra incorrecto."));
            }
        } else {
            validacionesNombreRegistroEditar = false;
            FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:nombreRegistroEditar", new FacesMessage("El campo Nombre es obligatorio."));
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

    public void validarCodigoRegistroEditar() {
        if (Utilidades.validarNulo(codigoRegistroEditar) && (!codigoRegistroEditar.isEmpty())) {
            if (Utilidades.validarCaracterString(codigoRegistroEditar)) {
                validacionesCodigoRegistroEditar = true;
            } else {
                validacionesCodigoRegistroEditar = false;
                FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:codigoRegistroEditar", new FacesMessage("El codigo se encuentra incorrecto."));
            }
        } else {
            validacionesCodigoRegistroEditar = false;
            FacesContext.getCurrentInstance().addMessage("formularioGeneral:formEditar:codigoRegistroEditar", new FacesMessage("El campo Codigo es obligatorio."));
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
            if (validacionesNombreRegistroNuevo == false) {
                retorno = false;
            }
            if (validacionesCodigoRegistroNuevo == false) {
                retorno = false;
            }
        } else {
            if (validacionesNombreEditar == false) {
                retorno = false;
            }
            if (validacionesCodigoEditar == false) {
                retorno = false;
            }
            if (validacionesNombreRegistroEditar == false) {
                retorno = false;
            }
            if (validacionesCodigoRegistroEditar == false) {
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
        nuevo.setCodigoregistro(inputCodigoRegistro);
        nuevo.setNombreregistro(inputNombreRegistro);
        gestionarVariableTiposPerfilesBO.crearTipoPerfil(nuevo);
        cancelarNuevoRegistro();
        listaTiposPerfiles = null;
        getListaTiposPerfiles();
    }

    public void cancelarNuevoRegistro() {
        visibleEditar = true; 
        inputNombre = null;
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        validacionesNombreRegistroNuevo = false;
        validacionesCodigoRegistroNuevo = false;
        validacionesNombreNuevo = false;
        validacionesCodigoNuevo = false;
        mensajeFormularioNuevo = "";
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
        visibleEditar = false;
        idTipoPerfil = idRegistro;
        tipoPerfilEditar = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorID(idRegistro);
        nombreEditar = tipoPerfilEditar.getNombre();
        codigoEditar = tipoPerfilEditar.getCodigo();
        nombreRegistroEditar = tipoPerfilEditar.getNombreregistro();
        codigoRegistroEditar = tipoPerfilEditar.getCodigoregistro();
        validacionesNombreEditar = true;
        validacionesCodigoEditar = true;
        validacionesNombreRegistroEditar = true;
        validacionesCodigoRegistroEditar = true;
    }

    private void almacenarModificacionTipoPerfil() {
        tipoPerfilEditar.setNombre(nombreEditar);
        tipoPerfilEditar.setCodigo(codigoEditar);
        tipoPerfilEditar.setCodigoregistro(codigoRegistroEditar);
        tipoPerfilEditar.setNombreregistro(nombreRegistroEditar);
        gestionarVariableTiposPerfilesBO.editarTipoPerfil(tipoPerfilEditar);
        cancelarModificarRegistro();
        listaTiposPerfiles = null;
        getListaTiposPerfiles();
    }

    public void cancelarModificarRegistro() {
        visibleEditar = true;
        tipoPerfilEditar = null;
        idTipoPerfil = null;
        validacionesNombreEditar = false;
        validacionesCodigoEditar = false;
        codigoEditar = null;
        nombreEditar = null;
        validacionesNombreRegistroEditar = false;
        validacionesCodigoRegistroEditar = false;
        codigoRegistroEditar = null;
        nombreRegistroEditar = null;
        mensajeFormularioEditar = "";
    }

    public void limpiarProcesoPagina() {
        cancelarModificarRegistro();
        cancelarNuevoRegistro();
        listaTiposPerfiles = null;
    }

    //GET-SET
    public List<TipoPerfil> getListaTiposPerfiles() {
        if (listaTiposPerfiles == null) {
            listaTiposPerfiles = gestionarVariableTiposPerfilesBO.consultarTiposPerfilesRegistrados();
        }
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

    public boolean isVisibleEditar() {
        return visibleEditar;
    }

    public void setVisibleEditar(boolean visibleEditar) {
        this.visibleEditar = visibleEditar;
    }

    public String getInputNombreRegistro() {
        return inputNombreRegistro;
    }

    public void setInputNombreRegistro(String inputNombreRegistro) {
        this.inputNombreRegistro = inputNombreRegistro;
    }

    public String getInputCodigoRegistro() {
        return inputCodigoRegistro;
    }

    public void setInputCodigoRegistro(String inputCodigoRegistro) {
        this.inputCodigoRegistro = inputCodigoRegistro;
    }

    public String getNombreRegistroEditar() {
        return nombreRegistroEditar;
    }

    public void setNombreRegistroEditar(String nombreRegistroEditar) {
        this.nombreRegistroEditar = nombreRegistroEditar;
    }

    public String getCodigoRegistroEditar() {
        return codigoRegistroEditar;
    }

    public void setCodigoRegistroEditar(String codigoRegistroEditar) {
        this.codigoRegistroEditar = codigoRegistroEditar;
    }

}
