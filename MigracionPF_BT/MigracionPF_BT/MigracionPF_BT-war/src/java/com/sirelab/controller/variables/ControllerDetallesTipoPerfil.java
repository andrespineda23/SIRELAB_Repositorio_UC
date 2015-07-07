/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposPerfilesBOInterface;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesTipoPerfil implements Serializable {

    @EJB
    GestionarVariableTiposPerfilesBOInterface gestionarVariableTiposPerfilesBO;

    private String inputNombre, inputCodigo, inputNombreRegistro, inputCodigoRegistro;
    private boolean validacionesNombre, validacionesCodigo, validacionesNombreRegistro, validacionesCodigoRegistro;
    private String mensajeFormulario;
    private BigInteger idTipoPerfil;
    private TipoPerfil tipoPerfilEditar;
    private boolean modificacionRegistro;

    public ControllerDetallesTipoPerfil() {
    }

    @PostConstruct
    public void init() {
    }

    public void recibirIDDetalleTipoPerfil(BigInteger idDetalle) {
        this.idTipoPerfil = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
    }

    private void cargarInformacionRegistro() {
        tipoPerfilEditar = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorID(idTipoPerfil);
        if (null != tipoPerfilEditar) {
            inputCodigo = tipoPerfilEditar.getCodigo();
            inputNombre = tipoPerfilEditar.getNombre();
            inputNombreRegistro = tipoPerfilEditar.getNombreregistro();
            inputCodigoRegistro = tipoPerfilEditar.getCodigoregistro();
            validacionesCodigo = true;
            validacionesCodigoRegistro = true;
            validacionesNombre = true;
            validacionesNombreRegistro = true;
            modificacionRegistro = false;
        }

    }

    public void validarNombre() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty())) {
            if (Utilidades.validarCaracterString(inputNombre)) {
                validacionesNombre = true;
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto."));
        }
        modificacionRegistro = true;
    }

    public void validarCodigo() {
        if (Utilidades.validarNulo(inputCodigo) && (!inputCodigo.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputCodigo)) {
                validacionesCodigo = true;
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputCodigo", new FacesMessage("El codigo se encuentra incorrecto."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputCodigo", new FacesMessage("El codigo se encuentra incorrecto."));
        }
        modificacionRegistro = true;
    }

    public void validarNombreRegistro() {
        if (Utilidades.validarNulo(inputNombreRegistro) && (!inputNombreRegistro.isEmpty())) {
            if (Utilidades.validarCaracterString(inputNombreRegistro)) {
                validacionesNombreRegistro = true;
            } else {
                validacionesNombreRegistro = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombreRegistro", new FacesMessage("El nombre se encuentra incorrecto."));
            }
        } else {
            validacionesNombreRegistro = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombreRegistro", new FacesMessage("El nombre se encuentra incorrecto."));
        }
        modificacionRegistro = true;
    }

    public void validarCodigoRegistro() {
        if (Utilidades.validarNulo(inputCodigoRegistro) && (!inputCodigoRegistro.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputCodigoRegistro)) {
                validacionesCodigoRegistro = true;
            } else {
                validacionesCodigoRegistro = false;
                FacesContext.getCurrentInstance().addMessage("form:inputCodigoRegistro", new FacesMessage("El codigo se encuentra incorrecto."));
            }
        } else {
            validacionesCodigoRegistro = false;
            FacesContext.getCurrentInstance().addMessage("form:inputCodigoRegistro", new FacesMessage("El codigo se encuentra incorrecto."));
        }
        modificacionRegistro = true;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesNombreRegistro == false) {
            retorno = false;
        }
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesCodigoRegistro == false) {
            retorno = false;
        }
        return retorno;
    }

    public boolean validarCodigoRepetido() {
        boolean retorno = true;
        TipoPerfil registro = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorCodigo(inputCodigo);
        if (null != registro) {
            if (!tipoPerfilEditar.getIdtipoperfil().equals(registro.getIdtipoperfil())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionTipoPerfil() {
        if (modificacionRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                if (validarCodigoRepetido() == true) {
                    almacenarModificacionRegistro();
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                    cargarInformacionRegistro();
                } else {
                    mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
                }
            } else {
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificacionRegistro() {
        try {
            tipoPerfilEditar.setCodigo(inputCodigo);
            tipoPerfilEditar.setCodigoregistro(inputCodigoRegistro);
            tipoPerfilEditar.setNombre(inputNombre);
            tipoPerfilEditar.setNombreregistro(inputNombreRegistro);
            gestionarVariableTiposPerfilesBO.editarTipoPerfil(tipoPerfilEditar);
        } catch (Exception e) {
            System.out.println("Error ControllerDetallesTipoPerfil almacenarModificacionRegistro: " + e.toString());
        }
    }

    public void cancelarTipoPerfil() {
        idTipoPerfil = null;
        inputNombre = null;
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        validacionesNombre = false;
        validacionesCodigo = false;
        validacionesNombreRegistro = false;
        validacionesCodigoRegistro = false;
        mensajeFormulario = "";
        modificacionRegistro = false;
    }

    public String cerrarPagina() {
        cancelarTipoPerfil();
        return "variables_usuario";
    }

    //GET-SET
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

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
