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
public class ControllerRegistrarTipoPerfil implements Serializable {

    @EJB
    GestionarVariableTiposPerfilesBOInterface gestionarVariableTiposPerfilesBO;

    private String inputNombre, inputCodigo, inputNombreRegistro, inputCodigoRegistro;
    private boolean validacionesNombre, validacionesCodigo, validacionesNombreRegistro, validacionesCodigoRegistro;
    private String mensajeFormulario;

    public ControllerRegistrarTipoPerfil() {
    }

    @PostConstruct
    public void init() {
        inputNombre = null;
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        validacionesNombre = false;
        validacionesCodigo = false;
        validacionesNombreRegistro = false;
        validacionesCodigoRegistro = false;
        mensajeFormulario = "";
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
            retorno = false;
        }
        return retorno;
    }

    public void registrarTipoPerfil() {
        if (validarValidacionesRegistro() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarRegistroNuevo();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
                restaurarFormulario();
            } else {
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            TipoPerfil tipoNuevo = new TipoPerfil();
            tipoNuevo.setCodigo(inputCodigo);
            tipoNuevo.setCodigoregistro(inputCodigoRegistro);
            tipoNuevo.setNombre(inputNombre);
            tipoNuevo.setNombreregistro(inputNombreRegistro);
            gestionarVariableTiposPerfilesBO.crearTipoPerfil(tipoNuevo);
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarTipoPerfil almacenarRegistroNuevo: " + e.toString());
        }
    }

    public void cancelarTipoPerfil() {
        inputNombre = null;
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        validacionesNombre = false;
        validacionesCodigo = false;
        validacionesNombreRegistro = false;
        validacionesCodigoRegistro = false;
        mensajeFormulario = "";
    }

    public String cerrarPagina() {
        cancelarTipoPerfil();
        return "variables_usuario";
    }

    private void restaurarFormulario() {
        inputNombre = null;
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        validacionesNombre = false;
        validacionesCodigo = false;
        validacionesNombreRegistro = false;
        validacionesCodigoRegistro = false;
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
