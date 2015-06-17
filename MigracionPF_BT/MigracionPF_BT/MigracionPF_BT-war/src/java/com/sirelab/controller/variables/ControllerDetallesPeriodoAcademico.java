/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariablePeriodosAcademicosBOInterface;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
public class ControllerDetallesPeriodoAcademico implements Serializable {

    @EJB
    GestionarVariablePeriodosAcademicosBOInterface gestionarVariablePeriodosAcademicosBO;

    private String inputDetalle;
    private Date inputFechaInicio, inputFechaFin;
    private boolean validacionesDetalle, validacionesFechaInicio, validacionesFechaFin;
    private String mensajeFormulario;
    private BigInteger idPeriodoAcademico;
    private PeriodoAcademico periodoAcademicoEditar;
    private boolean modificacionesRegistro;

    public ControllerDetallesPeriodoAcademico() {
    }

    @PostConstruct
    public void init() {
    }

    public void recibirIDDetallePeriodoAcademico(BigInteger idDetalle) {
        this.idPeriodoAcademico = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "";
    }

    private void cargarInformacionRegistro() {
        periodoAcademicoEditar = gestionarVariablePeriodosAcademicosBO.consultarPeriodoAcademicoPorID(idPeriodoAcademico);
        if (null != periodoAcademicoEditar) {
            inputDetalle = periodoAcademicoEditar.getDetalleperiodo();
            inputFechaInicio = periodoAcademicoEditar.getFechainicial();
            inputFechaFin = periodoAcademicoEditar.getFechafinal();
            validacionesDetalle = true;
            validacionesFechaFin = true;
            validacionesFechaInicio = true;
            modificacionesRegistro = false;
        }
    }

    public void validarDetalle() {
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty())) {
            if (Utilidades.validarCaracterString(inputDetalle)) {
                validacionesDetalle = true;
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto."));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto."));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaInicio() {
        if (Utilidades.validarNulo(inputFechaInicio)) {
            if (Utilidades.fechaIngresadaCorrecta(inputFechaInicio)) {
                validacionesFechaInicio = true;
            } else {
                validacionesFechaInicio = false;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
            }
        } else {
            validacionesFechaInicio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaFin() {
        if (Utilidades.validarNulo(inputFechaFin)) {
            if (Utilidades.fechaIngresadaCorrecta(inputFechaFin)) {
                validacionesFechaFin = true;
            } else {
                validacionesFechaFin = false;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
            }
        } else {
            validacionesFechaFin = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
        }
        modificacionesRegistro = true;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDetalle == false) {
            retorno = false;
        }
        if (validacionesFechaFin == false) {
            retorno = false;
        }
        if (validacionesFechaInicio == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionPeriodoAcademico() {
        if (modificacionesRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                if (inputFechaFin.after(inputFechaInicio)) {
                    almacenarModificaionRegistro();
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                    cargarInformacionRegistro();
                } else {
                    mensajeFormulario = "La fecha final es menor o igual que la fecha inicial, por favor corregir para continuar.";
                }
            } else {
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificaionRegistro() {
        try {
            periodoAcademicoEditar.setDetalleperiodo(inputDetalle);
            periodoAcademicoEditar.setFechafinal(inputFechaFin);
            periodoAcademicoEditar.setFechainicial(inputFechaInicio);
            gestionarVariablePeriodosAcademicosBO.editarPeriodoAcademico(periodoAcademicoEditar);
        } catch (Exception e) {
            System.out.println("Error ControllerDetallePeriodoAcademico almacenarModificaionRegistro: " + e.toString());
        }
    }

    public void cancelarPeriodoAcademico() {
        inputDetalle = null;
        inputFechaInicio = null;
        inputFechaFin = null;
        validacionesDetalle = false;
        validacionesFechaFin = false;
        validacionesFechaInicio = false;
        mensajeFormulario = "";
        idPeriodoAcademico = null;
        periodoAcademicoEditar = null;
        modificacionesRegistro = false;
    }

    public String cerrarPagina() {
        cancelarPeriodoAcademico();
        return "variables_reserva";
    }

    //GET-SET
    public String getInputDetalle() {
        return inputDetalle;
    }

    public void setInputDetalle(String inputDetalle) {
        this.inputDetalle = inputDetalle;
    }

    public Date getInputFechaInicio() {
        return inputFechaInicio;
    }

    public void setInputFechaInicio(Date inputFechaInicio) {
        this.inputFechaInicio = inputFechaInicio;
    }

    public Date getInputFechaFin() {
        return inputFechaFin;
    }

    public void setInputFechaFin(Date inputFechaFin) {
        this.inputFechaFin = inputFechaFin;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
