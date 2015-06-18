/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableHorariosAtencionBOInterface;
import com.sirelab.entidades.HorarioAtencion;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesHorarioAtencion implements Serializable {

    @EJB
    GestionarVariableHorariosAtencionBOInterface gestionarVariableHorariosAtencionBO;

    private String inputDescripcion, inputCodigo;
    private boolean validacionesDescripcion, validacionesCodigo;
    private String mensajeFormulario;
    private boolean modificacionesRegistro;
    private BigInteger idHorarioAtencion;
    private HorarioAtencion horarioAtencionDetalles;

    public ControllerDetallesHorarioAtencion() {
    }

    public void recibirIDDetalleHorarioAtencion(BigInteger idDetalle) {
        this.idHorarioAtencion = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
    }

    private void cargarInformacionRegistro() {
        horarioAtencionDetalles = gestionarVariableHorariosAtencionBO.consultarHorarioAtencionPorID(idHorarioAtencion);
        if (null != horarioAtencionDetalles) {
            inputDescripcion = horarioAtencionDetalles.getDescripcionhorario();
            inputCodigo = horarioAtencionDetalles.getCodigohorario();
            validacionesDescripcion = true;
            validacionesCodigo = true;
            modificacionesRegistro = false;
        }
    }

    public void validarDescripcion() {
        if (Utilidades.validarNulo(inputDescripcion) && (!inputDescripcion.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputDescripcion)) {
                validacionesDescripcion = true;
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción se encuentra incorrecta."));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción se encuentra incorrecta."));
        }
        modificacionesRegistro = true;
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
        modificacionesRegistro = true;
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        return retorno;
    }

    public boolean validarCodigoRepetido() {
        boolean retorno = true;
        HorarioAtencion registro = gestionarVariableHorariosAtencionBO.consultarHorarioAtencionPorCodigo(inputCodigo);
        if (null != registro) {
            if (!horarioAtencionDetalles.getIdhorarioatencion().equals(registro.getIdhorarioatencion())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionHorarioAtencion() {
        if (modificacionesRegistro == true) {
            if (validarResultadosValidacion() == true) {
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
            horarioAtencionDetalles.setCodigohorario(inputCodigo);
            horarioAtencionDetalles.setDescripcionhorario(inputDescripcion);
            gestionarVariableHorariosAtencionBO.editarHorarioAtencion(horarioAtencionDetalles);
        } catch (Exception e) {
            System.out.println("Error ControllerDetallesHorarioAtencion almacenarModificacionRegistro: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarHorarioAtencion();
        return "variables_reserva";
    }

    public void cancelarHorarioAtencion() {
        inputDescripcion = null;
        inputCodigo = null;
        validacionesDescripcion = false;
        validacionesCodigo = false;
        mensajeFormulario = "";
        idHorarioAtencion = null;
        modificacionesRegistro = false;
        horarioAtencionDetalles = null;
    }

    //GET-SET
    public String getInputDescripcion() {
        return inputDescripcion;
    }

    public void setInputDescripcion(String inputDescripcion) {
        this.inputDescripcion = inputDescripcion;
    }

    public String getInputCodigo() {
        return inputCodigo;
    }

    public void setInputCodigo(String inputCodigo) {
        this.inputCodigo = inputCodigo;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
