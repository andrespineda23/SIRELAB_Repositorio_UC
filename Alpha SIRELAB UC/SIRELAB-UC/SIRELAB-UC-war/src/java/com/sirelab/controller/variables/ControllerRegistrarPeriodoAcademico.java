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
import java.text.DateFormat;
import java.text.ParseException;
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
    private Integer fechaAnio;
    private AyudaFechaReserva fechaInicioMes;
    private AyudaFechaReserva fechaInicioDia;
    private List<AyudaFechaReserva> listaMesesInicio;
    private List<AyudaFechaReserva> listaDiasInicio;
    private AyudaFechaReserva fechaFinMes;
    private AyudaFechaReserva fechaFinDia;
    private List<AyudaFechaReserva> listaMesesFin;
    private List<AyudaFechaReserva> listaDiasFin;

    public ControllerRegistrarPeriodoAcademico() {
    }

    @PostConstruct
    public void init() {
        inputDetalle = null;
        Date fecha1 = new Date();
        DateFormat df = DateFormat.getDateInstance();
        inputFechaInicio = df.format(fecha1);
        inputFechaFin = df.format(fecha1);
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
        cargarFechasConvenio();
    }

    private void cargarFechasConvenio() {
        Date fechaHoy = new Date();
        fechaAnio = fechaHoy.getYear() + 1900;
        listaMesesInicio = new ArrayList<AyudaFechaReserva>();
        listaMesesFin = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesFin.add(ayuda);
            listaMesesInicio.add(ayuda);
        }
        fechaInicioMes = obtenerMesExacto(fechaHoy.getMonth(),1);
        fechaFinMes = obtenerMesExacto(fechaHoy.getMonth(),2);
        actualizarInformacionInicioDia();
        actualizarInformacionFinDia();
        fechaInicioDia = obtenerDiaExacto(fechaHoy.getDate(),1);
        fechaFinDia = obtenerDiaExacto(fechaHoy.getDate(),2);
    }

    private AyudaFechaReserva obtenerMesExacto(int mes, int op) {
        AyudaFechaReserva ayuda = null;
        if (op == 1) {
            for (int i = 0; i < listaMesesInicio.size(); i++) {
                if (mes == listaMesesInicio.get(i).getParametro()) {
                    ayuda = listaMesesInicio.get(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < listaMesesFin.size(); i++) {
                if (mes == listaMesesFin.get(i).getParametro()) {
                    ayuda = listaMesesFin.get(i);
                    break;
                }
            }
        }
        return ayuda;
    }

    private AyudaFechaReserva obtenerDiaExacto(int dia, int op) {
        AyudaFechaReserva ayuda = null;
        if (op == 1) {
            for (int i = 0; i < listaDiasInicio.size(); i++) {
                if (dia == listaDiasInicio.get(i).getParametro()) {
                    ayuda = listaDiasInicio.get(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < listaDiasFin.size(); i++) {
                if (dia == listaDiasFin.get(i).getParametro()) {
                    ayuda = listaDiasFin.get(i);
                    break;
                }
            }
        }
        return ayuda;
    }

    public void actualizarInformacionInicioDia() {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.set(Integer.valueOf(fechaAnio), fechaInicioMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        listaDiasInicio = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDiasInicio.add(ayuda);
        }
    }

    public void actualizarInformacionFinDia() {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.set(Integer.valueOf(fechaAnio), fechaFinMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        listaDiasFin = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDiasFin.add(ayuda);
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
                    FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.VARIABLE_NOMBRE));
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El tamaño minimo permitido es 3 caracteres. " + constantes.VARIABLE_NOMBRE));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.VARIABLE_NOMBRE));
        }
    }

    public void validarFechaInicio() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaAnio);
        cal.set(Calendar.MONTH, fechaInicioMes.getParametro());
        cal.set(Calendar.DATE, fechaInicioDia.getParametro());

        if (Utilidades.fechaIngresadaCorrecta(cal.getTime().toString())) {
            validacionesFechaInicio = true;
        } else {
            validacionesFechaInicio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
        }
    }

    public void validarFechaFin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaAnio);
        cal.set(Calendar.MONTH, fechaFinMes.getParametro());
        cal.set(Calendar.DATE, fechaFinDia.getParametro());

        if (Utilidades.fechaIngresadaCorrecta(cal.getTime().toString())) {
            validacionesFechaFin = true;
        } else {
            validacionesFechaFin = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
        }
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

    public void registrarPeriodoAcademico() {
        if (validarValidacionesRegistro() == true) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null;
            Date date2 = null;
            int comparacion = 1;
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaAnio);
            cal.set(Calendar.MONTH, fechaInicioMes.getParametro());
            cal.set(Calendar.DATE, fechaInicioDia.getParametro());
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.YEAR, fechaAnio);
            cal2.set(Calendar.MONTH, fechaFinMes.getParametro());
            cal2.set(Calendar.DATE, fechaFinDia.getParametro());
            try {
                date1 = sdf.parse(cal.getTime().toString());
                date2 = sdf.parse(cal2.getTime().toString());
            } catch (ParseException ex) {
                comparacion = 1;
            }
            comparacion = date1.compareTo(date2);

            if (comparacion < 0) {
                almacenarRegistroNuevo();
                restaurarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "La fecha final es menor que la fecha inicial, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            PeriodoAcademico periodoNuevo = new PeriodoAcademico();
            periodoNuevo.setDetalleperiodo(inputDetalle);

            String pattern = "dd/MM/yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date fecha1 = null;
            Date fecha2 = null;
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaAnio);
            cal.set(Calendar.MONTH, fechaInicioMes.getParametro());
            cal.set(Calendar.DATE, fechaInicioDia.getParametro());
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.YEAR, fechaAnio);
            cal2.set(Calendar.MONTH, fechaFinMes.getParametro());
            cal2.set(Calendar.DATE, fechaFinDia.getParametro());
            try {
                fecha1 = format.parse(cal2.getTime().toString());
                fecha2 = format.parse(cal.getTime().toString());
                periodoNuevo.setFechafinal(fecha1);
                periodoNuevo.setFechainicial(fecha2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            gestionarVariablePeriodosAcademicosBO.crearPeriodoAcademico(periodoNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarPeriodoAcademico almacenarRegistroNuevo:  " + e.toString());
            logger.error("Error ControllerRegistrarPeriodoAcademico almacenarRegistroNuevo: " + e.toString());
        }
    }

    public void cancelarPeriodoAcademico() {
        inputDetalle = null;
        Date fecha1 = new Date();
        DateFormat df = DateFormat.getDateInstance();
        inputFechaInicio = df.format(fecha1);
        inputFechaFin = df.format(fecha1);
        validacionesDetalle = false;
        validacionesFechaFin = false;
        validacionesFechaInicio = false;
        mensajeFormulario = "N/A";
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        cargarFechasConvenio();
    }

    public String cerrarPagina() {
        cancelarPeriodoAcademico();
        return "variablesreserva";
    }

    private void restaurarFormulario() {
        inputDetalle = null;
        Date fecha1 = new Date();
        DateFormat df = DateFormat.getDateInstance();
        inputFechaInicio = df.format(fecha1);
        inputFechaFin = df.format(fecha1);
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

    public Integer getFechaAnio() {
        return fechaAnio;
    }

    public void setFechaAnio(Integer fechaAnio) {
        this.fechaAnio = fechaAnio;
    }

    public AyudaFechaReserva getFechaInicioMes() {
        return fechaInicioMes;
    }

    public void setFechaInicioMes(AyudaFechaReserva fechaInicioMes) {
        this.fechaInicioMes = fechaInicioMes;
    }

    public AyudaFechaReserva getFechaInicioDia() {
        return fechaInicioDia;
    }

    public void setFechaInicioDia(AyudaFechaReserva fechaInicioDia) {
        this.fechaInicioDia = fechaInicioDia;
    }

    public List<AyudaFechaReserva> getListaMesesInicio() {
        return listaMesesInicio;
    }

    public void setListaMesesInicio(List<AyudaFechaReserva> listaMesesInicio) {
        this.listaMesesInicio = listaMesesInicio;
    }

    public List<AyudaFechaReserva> getListaDiasInicio() {
        return listaDiasInicio;
    }

    public void setListaDiasInicio(List<AyudaFechaReserva> listaDiasInicio) {
        this.listaDiasInicio = listaDiasInicio;
    }

    public AyudaFechaReserva getFechaFinMes() {
        return fechaFinMes;
    }

    public void setFechaFinMes(AyudaFechaReserva fechaFinMes) {
        this.fechaFinMes = fechaFinMes;
    }

    public AyudaFechaReserva getFechaFinDia() {
        return fechaFinDia;
    }

    public void setFechaFinDia(AyudaFechaReserva fechaFinDia) {
        this.fechaFinDia = fechaFinDia;
    }

    public List<AyudaFechaReserva> getListaMesesFin() {
        return listaMesesFin;
    }

    public void setListaMesesFin(List<AyudaFechaReserva> listaMesesFin) {
        this.listaMesesFin = listaMesesFin;
    }

    public List<AyudaFechaReserva> getListaDiasFin() {
        return listaDiasFin;
    }

    public void setListaDiasFin(List<AyudaFechaReserva> listaDiasFin) {
        this.listaDiasFin = listaDiasFin;
    }

}
