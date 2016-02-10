/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarConveniosBOInterface;
import com.sirelab.entidades.Convenio;
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
public class ControllerRegistrarConvenio implements Serializable {

    @EJB
    GestionarConveniosBOInterface gestionarConvenioBO;

    private String inputNombre, inputDescripcion, inputValor;
    private String inputFechaInicio, inputFechaFin;
    private boolean validacionesNombre, validacionesDescripcion, validacionesValor, validacionesFechaInicio, validacionesFechaFin;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
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

    public ControllerRegistrarConvenio() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        inputNombre = null;
        inputDescripcion = null;
        inputValor = "0";
        Date fecha1 = new Date();
        DateFormat df = DateFormat.getDateInstance();
        inputFechaInicio = df.format(fecha1);
        inputFechaFin = df.format(fecha1);
        validacionesNombre = false;
        validacionesDescripcion = false;
        validacionesValor = true;
        validacionesFechaFin = true;
        validacionesFechaInicio = true;
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
        cargarFechasConvenio();
    }

    private void cargarFechasConvenio() {
        Date fechaHoy = new Date();
        int anioActual = fechaHoy.getYear() + 1900;
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
        fechaInicioAnio = obtenerAnioActual(anioActual, 1);
        fechaFinAnio = obtenerAnioActual(anioActual, 2);
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
        fechaInicioMes = obtenerMesExacto(fechaHoy.getMonth(), 1);
        fechaFinMes = obtenerMesExacto(fechaHoy.getMonth(), 1);
        actualizarInformacionInicioDia();
        actualizarInformacionFinDia();
        fechaInicioDia = obtenerDiaExacto(fechaHoy.getDate(), 1);
        fechaFinDia = obtenerDiaExacto(fechaHoy.getDate(), 2);
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
    }

    public void validarNombre() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty()) && (inputNombre.trim().length() > 0)) {
            int tam = inputNombre.length();
            if (tam >= 6) {
                if (Utilidades.validarCaracterString(inputNombre)) {
                    validacionesNombre = true;
                } else {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.U_NOMBRE));
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.U_NOMBRE));
        }
    }

    public void validarDescripcion() {
        if (Utilidades.validarNulo(inputDescripcion) && (!inputDescripcion.isEmpty()) && (inputDescripcion.trim().length() > 0)) {
            int tam = inputDescripcion.length();
            if (tam >= 20) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputDescripcion)) {
                    validacionesDescripcion = true;
                } else {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción se encuentra incorrecta. " + constantes.U_DESCRIP));
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("El tamaño minimo permitido es 20 caracteres. " + constantes.U_DESCRIP));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción es obligatoria. " + constantes.U_DESCRIP));
        }
    }

    public void validarValor() {
        if (Utilidades.validarNulo(inputValor) && (!inputValor.isEmpty()) && (inputValor.trim().length() > 0)) {
            if (Utilidades.isNumber(inputValor)) {
                validacionesValor = true;
            } else {
                validacionesValor = true;
                FacesContext.getCurrentInstance().addMessage("form:inputValor", new FacesMessage("El valor se encuentra incorrecto. " + constantes.U_COSTO));
            }
        } else {
            validacionesValor = true;
            FacesContext.getCurrentInstance().addMessage("form:inputValor", new FacesMessage("El valor es obligatoria. " + constantes.U_COSTO));
        }
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
    }

    private boolean validarValidacionesFin() {
        boolean retorno = true;
        if (validacionesValor == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarFechasFin() {
        boolean retorno = true;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaInicioAnio.getParametro());
        cal.set(Calendar.MONTH, fechaInicioMes.getParametro());
        cal.set(Calendar.DATE, fechaInicioDia.getParametro());
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, fechaFinAnio.getParametro());
        cal2.set(Calendar.MONTH, fechaFinMes.getParametro());
        cal2.set(Calendar.DATE, fechaFinDia.getParametro());
        if (cal.getTime().before(cal2.getTime())) {
            retorno = true;
        } else {
            retorno = false;
        }

        return retorno;
    }

    public void registrarConvenio() {
        if (validarValidacionesFin() == true) {
            if (validarFechasFin() == true) {
                almacenarFinNuevo();
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

    private void almacenarFinNuevo() {
        try {
            Convenio convenioNuevo = new Convenio();
            convenioNuevo.setNombreconvenio(inputNombre);
            convenioNuevo.setValor(Integer.valueOf(inputValor));
            convenioNuevo.setEstado(true);
            convenioNuevo.setDescripcion(inputDescripcion);
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
            convenioNuevo.setFechainicial(fecha1);
            convenioNuevo.setFechafinal(fecha2);
            gestionarConvenioBO.crearConvenio(convenioNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarConvenio almacenarFinNuevo:  " + e.toString());
            logger.error("Error ControllerRegistrarConvenio almacenarFinNuevo: " + e.toString());
        }
    }

    public void cancelarConvenio() {
        inputNombre = null;
        inputDescripcion = null;
        inputValor = "0";
        Date fecha1 = new Date();
        DateFormat df = DateFormat.getDateInstance();
        inputFechaInicio = df.format(fecha1);
        inputFechaFin = df.format(fecha1);
        validacionesNombre = false;
        validacionesDescripcion = false;
        validacionesValor = true;
        validacionesFechaFin = false;
        validacionesFechaInicio = false;
        mensajeFormulario = "N/A";
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
    }

    public String cerrarPagina() {
        cancelarConvenio();
        return "administrarconvenios";
    }

    private void restaurarFormulario() {
        inputNombre = null;
        inputValor = "0";
        inputDescripcion = null;
        Date fecha1 = new Date();
        DateFormat df = DateFormat.getDateInstance();
        inputFechaInicio = df.format(fecha1);
        inputFechaFin = df.format(fecha1);
        validacionesNombre = false;
        validacionesDescripcion = false;
        validacionesValor = true;
        validacionesFechaFin = false;
        validacionesFechaInicio = false;
        cargarFechasConvenio();
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
    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public String getInputDescripcion() {
        return inputDescripcion;
    }

    public void setInputDescripcion(String inputDescripcion) {
        this.inputDescripcion = inputDescripcion;
    }

    public String getInputValor() {
        return inputValor;
    }

    public void setInputValor(String inputValor) {
        this.inputValor = inputValor;
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
