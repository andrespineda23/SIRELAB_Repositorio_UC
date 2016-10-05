/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.variables.GestionarVariablePeriodosAcademicosBOInterface;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class ControllerRegistrarPeriodoAcademico implements Serializable {

    @EJB
    GestionarVariablePeriodosAcademicosBOInterface gestionarVariablePeriodosAcademicosBO;

    private String inputDetalle;
    private String inputFechaInicio, inputFechaFin;
    private boolean validacionesDetalle, validacionesFechaInicio, validacionesFechaFin;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarPeriodoAcademico() {
    }

    @PostConstruct
    public void init() {
        inputDetalle = null;
        //Date fecha1 = new Date();
        //DateFormat df = DateFormat.getDateInstance();
        inputFechaInicio = null;//df.format(fecha1);
        inputFechaFin = null;//df.format(fecha1);
        validacionesDetalle = false;
        validacionesFechaFin = false;
        validacionesFechaInicio = false;
        activarLimpiar = true;
        constantes = new MensajesConstantes();
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
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
    }

    public void validarFechaInicio() {
        if (Utilidades.validarNulo(inputFechaInicio)) {
            Date fechaValidar = new Date(inputFechaInicio);
            validacionesFechaInicio = true;
            try {
            } catch (Exception e) {
                validacionesFechaInicio = true;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha inicial fue incorrecta. El sistema asigno la fecha del día."));
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                inputFechaInicio = formateador.format(new Date());
            }
        } else {
            validacionesFechaInicio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha inicial del periodo academico es obligatoria"));
        }
    }

    public void validarFechaFin() {
        if (Utilidades.validarNulo(inputFechaFin)) {
            Date fechaValidar = new Date(inputFechaFin);
            validacionesFechaInicio = true;
            try {
            } catch (Exception e) {
                validacionesFechaInicio = true;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha final fue incorrecta. El sistema asigno la fecha del día."));
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                inputFechaInicio = formateador.format(new Date());
            }
        } else {
            validacionesFechaInicio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha final del periodo academico es obligatoria"));
        }
    }

    private boolean validarPeriodosAcademicosExistentes() {
        Integer cantidad = gestionarVariablePeriodosAcademicosBO.obtenerPeriodosAcademicosActivos();
        boolean respuest = true;
        if (null != cantidad) {
            if (cantidad > 0) {
                respuest = false;
            }
        }
        return respuest;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDetalle == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarPeriodoAcademico() {
        if (validarValidacionesRegistro() == true) {
            int comparacion = 1;
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = formateador.parse(inputFechaInicio);
                date2 = formateador.parse(inputFechaFin);
            } catch (ParseException e) {
                // Error, la cadena de texto no se puede convertir en fecha.
            }
            comparacion = date1.compareTo(date2);
            if (comparacion < 0) {
                if (validarPeriodosAcademicosExistentes() == true) {
                    almacenarRegistroNuevo();
                    restaurarFormulario();
                    activarLimpiar = false;
                    activarAceptar = true;
                    activarCasillas = true;
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "Existe registrado un periodo academico ACTIVO a la fecha. Cierre el periodo para continuar.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "La fecha final es menor que la fecha inicial, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            PeriodoAcademico periodoNuevo = new PeriodoAcademico();
            periodoNuevo.setDetalleperiodo(inputDetalle);
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = formateador.parse(inputFechaInicio);
                date2 = formateador.parse(inputFechaFin);
            } catch (ParseException e) {
                // Error, la cadena de texto no se puede convertir en fecha.
            }
            periodoNuevo.setFechainicial(date1);
            periodoNuevo.setFechafinal(date2);
            periodoNuevo.setEstado(true);
            gestionarVariablePeriodosAcademicosBO.crearPeriodoAcademico(periodoNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarPeriodoAcademico almacenarRegistroNuevo: " + e.toString(), e);
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
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
    }

    public String cerrarPagina() {
        cancelarPeriodoAcademico();
        return "variablesreserva";
    }

    private void restaurarFormulario() {
        inputDetalle = null;
        inputFechaInicio = null;
        inputFechaFin = null;
        validacionesDetalle = false;
        validacionesFechaFin = false;
        validacionesFechaInicio = false;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarAceptar = false;
        activarLimpiar = true;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
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

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isActivarLimpiar() {
        return activarLimpiar;
    }

    public void setActivarLimpiar(boolean activarLimpiar) {
        this.activarLimpiar = activarLimpiar;
    }

    public boolean isActivarAceptar() {
        return activarAceptar;
    }

    public void setActivarAceptar(boolean activarAceptar) {
        this.activarAceptar = activarAceptar;
    }

}
