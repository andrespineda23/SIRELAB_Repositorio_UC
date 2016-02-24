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
    private boolean validacionesDetalle, validacionesFechaInicio, validacionesFechaFin;
    private String mensajeFormulario;
    private BigInteger idPeriodoAcademico;
    private PeriodoAcademico periodoAcademicoEditar;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;
    private AyudaFechaReserva fechaInicioAnio;
    private AyudaFechaReserva fechaInicioMes;
    private AyudaFechaReserva fechaInicioDia;
    private List<AyudaFechaReserva> listaAniosInicio;
    private List<AyudaFechaReserva> listaMesesInicio;
    private List<AyudaFechaReserva> listaDiasInicio;
    private AyudaFechaReserva fechaFinAnio;
    private AyudaFechaReserva fechaFinMes;
    private AyudaFechaReserva fechaFinDia;
    private List<AyudaFechaReserva> listaAniosFin;
    private List<AyudaFechaReserva> listaMesesFin;
    private List<AyudaFechaReserva> listaDiasFin;

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

    private void cargarFechasConvenio() {
        int anioRegistro1 = periodoAcademicoEditar.getFechainicial().getYear() + 1900;
        int anioRegistro2 = periodoAcademicoEditar.getFechafinal().getYear() + 1900;
        int anioActual = new Date().getYear() + 1900;
        listaAniosInicio = new ArrayList<AyudaFechaReserva>();
        listaAniosFin = new ArrayList<AyudaFechaReserva>();
        for (int i = 2000; i <= anioActual; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaAniosInicio.add(ayuda);
        }
        for (int i = 2000; i <= anioActual; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaAniosFin.add(ayuda);
        }
        fechaInicioAnio = obtenerAnioActual(anioRegistro1, 1);
        fechaFinAnio = obtenerAnioActual(anioRegistro2, 2);
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
        fechaInicioMes = obtenerMesExacto(periodoAcademicoEditar.getFechainicial().getMonth(), 1);
        fechaFinMes = obtenerMesExacto(periodoAcademicoEditar.getFechafinal().getMonth(), 2);
        actualizarInformacionInicioDia();
        actualizarInformacionFinDia();
        fechaInicioDia = obtenerDiaExacto(periodoAcademicoEditar.getFechainicial().getDate(), 1);
        fechaFinDia = obtenerDiaExacto(periodoAcademicoEditar.getFechafinal().getDate(), 2);
    }

    public void actualizarInformacionAnioInicio() {
        listaMesesInicio = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesInicio.add(ayuda);
        }
        fechaInicioMes = obtenerMesExacto(0, 1);
        actualizarInformacionInicioDia();
        fechaInicioDia = obtenerDiaExacto(1, 1);
        modificacionesRegistro = true;
    }

    public void actualizarInformacionAnioFin() {
        listaMesesFin = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesFin.add(ayuda);
        }
        fechaFinMes = obtenerMesExacto(0, 1);
        actualizarInformacionFinDia();
        fechaFinDia = obtenerDiaExacto(1, 2);
        modificacionesRegistro = true;
    }

    private AyudaFechaReserva obtenerAnioActual(int anio, int op) {
        AyudaFechaReserva ayuda = null;
        if (op == 1) {
            for (int i = 0; i < listaAniosInicio.size(); i++) {
                if (anio == listaAniosInicio.get(i).getParametro()) {
                    ayuda = listaAniosInicio.get(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < listaAniosFin.size(); i++) {
                if (anio == listaAniosFin.get(i).getParametro()) {
                    ayuda = listaAniosFin.get(i);
                    break;
                }
            }
        }
        return ayuda;
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
        ahoraCal.set(fechaInicioAnio.getParametro(), fechaInicioMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        listaDiasInicio = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDiasInicio.add(ayuda);
        }
        modificacionesRegistro = true;
    }

    public void actualizarInformacionFinDia() {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.set(fechaFinAnio.getParametro(), fechaFinMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        listaDiasFin = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDiasFin.add(ayuda);
        }
        modificacionesRegistro = true;
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
            validacionesFechaFin = true;
            validacionesFechaInicio = true;
            modificacionesRegistro = false;
        }
        cargarFechasConvenio();
    }

    public void modificacionDias() {
        modificacionesRegistro = true;
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
        modificacionesRegistro = true;
    }

    public void validarFechaInicio() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaInicioAnio.getParametro());
        cal.set(Calendar.MONTH, fechaInicioMes.getParametro());
        cal.set(Calendar.DATE, fechaInicioDia.getParametro());

        if (Utilidades.fechaIngresadaCorrecta(cal.getTime().toString())) {
            validacionesFechaInicio = true;
        } else {
            validacionesFechaInicio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaFin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaFinAnio.getParametro());
        cal.set(Calendar.MONTH, fechaFinMes.getParametro());
        cal.set(Calendar.DATE, fechaFinDia.getParametro());
        if (Utilidades.fechaIngresadaCorrecta(cal.getTime().toString())) {
            validacionesFechaFin = true;
        } else {
            validacionesFechaFin = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
        }
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                int comparacion = 1;
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, fechaInicioAnio.getParametro());
                cal.set(Calendar.MONTH, fechaInicioMes.getParametro());
                cal.set(Calendar.DATE, fechaInicioDia.getParametro());
                Calendar cal2 = Calendar.getInstance();
                cal2.set(Calendar.YEAR, fechaFinAnio.getParametro());
                cal2.set(Calendar.MONTH, fechaFinMes.getParametro());
                cal2.set(Calendar.DATE, fechaFinDia.getParametro());

                Date date1 = cal.getTime();
                Date date2 = cal2.getTime();

                comparacion = date1.compareTo(date2);

                if (comparacion < 0) {
                    if (validarPeriodosAcademicosExistentes() == true) {
                        almacenarModificacionRegistro();
                        cargarInformacionRegistro();
                        colorMensaje = "green";
                        mensajeFormulario = "El formulario ha sido ingresado con exito.";
                    } else {
                        colorMensaje = "red";
                        mensajeFormulario = "Existe registrado un periodo academico a la fecha. Cierre el periodo para continuar.";
                    }
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
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaInicioAnio.getParametro());
            cal.set(Calendar.MONTH, fechaInicioMes.getParametro());
            cal.set(Calendar.DATE, fechaInicioDia.getParametro());
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.YEAR, fechaFinAnio.getParametro());
            cal2.set(Calendar.MONTH, fechaFinMes.getParametro());
            cal2.set(Calendar.DATE, fechaFinDia.getParametro());
            Date fecha1 = cal.getTime();
            Date fecha2 = cal2.getTime();
            periodoAcademicoEditar.setFechainicial(fecha1);
            periodoAcademicoEditar.setFechafinal(fecha2);
            gestionarVariablePeriodosAcademicosBO.editarPeriodoAcademico(periodoAcademicoEditar);
        } catch (Exception e) {
            logger.error("Error ControllerDetallePeriodoAcademico almacenarModificacionRegistro:  " + e.toString(),e);
            logger.error("Error ControllerDetallePeriodoAcademico almacenarModificacionRegistro: " + e.toString(),e);
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

    public AyudaFechaReserva getFechaInicioMes() {
        return fechaInicioMes;
    }

    public AyudaFechaReserva getFechaInicioAnio() {
        return fechaInicioAnio;
    }

    public void setFechaInicioAnio(AyudaFechaReserva fechaInicioAnio) {
        this.fechaInicioAnio = fechaInicioAnio;
    }

    public List<AyudaFechaReserva> getListaAniosInicio() {
        return listaAniosInicio;
    }

    public void setListaAniosInicio(List<AyudaFechaReserva> listaAniosInicio) {
        this.listaAniosInicio = listaAniosInicio;
    }

    public AyudaFechaReserva getFechaFinAnio() {
        return fechaFinAnio;
    }

    public void setFechaFinAnio(AyudaFechaReserva fechaFinAnio) {
        this.fechaFinAnio = fechaFinAnio;
    }

    public List<AyudaFechaReserva> getListaAniosFin() {
        return listaAniosFin;
    }

    public void setListaAniosFin(List<AyudaFechaReserva> listaAniosFin) {
        this.listaAniosFin = listaAniosFin;
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
