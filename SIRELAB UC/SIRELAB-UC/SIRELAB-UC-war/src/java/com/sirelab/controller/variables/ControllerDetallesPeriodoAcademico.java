/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariablePeriodosAcademicosBOInterface;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

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
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesPeriodoAcademico() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIDDetallePeriodoAcademico(BigInteger idDetalle) {
        this.idPeriodoAcademico = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
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
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty()) && (inputDetalle.trim().length() > 0)) {
            int tam = inputDetalle.length();
            if (tam >= 3) {
                if (Utilidades.validarCaracterString(inputDetalle)) {
                    validacionesDetalle = true;
                } else {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto."));
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El tamaño minimo permitido es 3 caracteres."));
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
                    almacenarModificacionRegistro();
                    cargarInformacionRegistro();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "La fecha final es menor o igual que la fecha inicial, por favor corregir para continuar.";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificacionRegistro() {
        try {
            periodoAcademicoEditar.setDetalleperiodo(inputDetalle);
            periodoAcademicoEditar.setFechafinal(inputFechaFin);
            periodoAcademicoEditar.setFechainicial(inputFechaInicio);
            gestionarVariablePeriodosAcademicosBO.editarPeriodoAcademico(periodoAcademicoEditar);
        } catch (Exception e) {
            logger.error("Error ControllerDetallePeriodoAcademico almacenarModificacionRegistro:  " + e.toString());
            System.out.println("Error ControllerDetallePeriodoAcademico almacenarModificacionRegistro: " + e.toString());
        }
    }

    public void cancelarPeriodoAcademico() {
        inputDetalle = null;
        inputFechaInicio = null;
        inputFechaFin = null;
        validacionesDetalle = false;
        validacionesFechaFin = false;
        validacionesFechaInicio = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
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

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
