/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.variables.GestionarVariablePeriodosAcademicosBOInterface;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    private String inputFechaInicio, inputFechaFin;
    private boolean validacionesDetalle;
    private String mensajeFormulario;
    private BigInteger idPeriodoAcademico;
    private PeriodoAcademico periodoAcademicoEditar;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;

    public ControllerDetallesPeriodoAcademico() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
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
            Date fecha1 = periodoAcademicoEditar.getFechainicial();
            Date fecha2 = periodoAcademicoEditar.getFechafinal();
            DateFormat df = DateFormat.getDateInstance();
            inputFechaInicio = df.format(fecha1);
            inputFechaFin = df.format(fecha2);
            validacionesDetalle = true;
            modificacionesRegistro = false;
        }
    }

    public void validarDetalle() {
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty()) && (inputDetalle.trim().length() > 0)) {
            int tam = inputDetalle.length();
            if (tam >= 3) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputDetalle)) {
                    validacionesDetalle = true;
                } else {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.VARIABLE_PERIODOACA));
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El tamaño minimo permitido es 3 caracteres. " + constantes.VARIABLE_PERIODOACA));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.VARIABLE_PERIODOACA));
        }
        modificacionesRegistro = true;
    }

    public void actualizarEstado() {
        modificacionesRegistro = true;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDetalle == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarPeriodosAcademicosExistentes() {
        Integer cantidad = gestionarVariablePeriodosAcademicosBO.obtenerPeriodosAcademicosActivos();
        boolean respuest = true;
        if (null != cantidad) {
            if (cantidad > 1) {
                respuest = false;
            }
        }
        return respuest;
    }

    public void registrarModificacionPeriodoAcademico() {
        if (modificacionesRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                if (validarPeriodosAcademicosExistentes() == true) {
                    almacenarModificacionRegistro();
                    cargarInformacionRegistro();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "Existe registrado un periodo academico ACTIVO a la fecha. Cierre el periodo academico ACTIVO para realizar el registro.";
                }
            } else {
                colorMensaje = "#FF0000";
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
            gestionarVariablePeriodosAcademicosBO.editarPeriodoAcademico(periodoAcademicoEditar);
        } catch (Exception e) {
            logger.error("Error ControllerDetallePeriodoAcademico almacenarModificacionRegistro: " + e.toString(), e);
        }
    }

    public void cancelarPeriodoAcademico() {
        inputDetalle = null;
        inputFechaInicio = null;
        inputFechaFin = null;
        validacionesDetalle = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        idPeriodoAcademico = null;
        periodoAcademicoEditar = null;
        modificacionesRegistro = false;
    }

    public String cerrarPagina() {
        cancelarPeriodoAcademico();
        return "variablesreserva";
    }

    //GET-SET
    public String getInputDetalle() {
        return inputDetalle;
    }

    public void setInputDetalle(String inputDetalle) {
        this.inputDetalle = inputDetalle;
    }

    public String getInputFechaInicio() {
        return inputFechaInicio;
    }

    public void setInputFechaInicio(String inputFechaInicio) {
        this.inputFechaInicio = inputFechaInicio;
    }

    public String getInputFechaFin() {
        return inputFechaFin;
    }

    public void setInputFechaFin(String inputFechaFin) {
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

    public PeriodoAcademico getPeriodoAcademicoEditar() {
        return periodoAcademicoEditar;
    }

    public void setPeriodoAcademicoEditar(PeriodoAcademico periodoAcademicoEditar) {
        this.periodoAcademicoEditar = periodoAcademicoEditar;
    }

}
