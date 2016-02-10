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
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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
public class ControllerDetallesConvenio implements Serializable {

    @EJB
    GestionarConveniosBOInterface gestionarConvenioBO;

    private Convenio convenioEditar;
    private BigInteger idConvenio;
    private String inputNombre, inputDescripcion, inputValor;
    private String inputFechaInicio, inputFechaFin;
    private boolean validacionesNombre, validacionesDescripcion, validacionesValor, validacionesFechaInicio, validacionesFechaFin;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean modificacionesRegistro;
    private boolean estadoConvenio;
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

    public ControllerDetallesConvenio() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleConvenio(BigInteger idRegistro) {
        this.idConvenio = idRegistro;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    private void cargarInformacionRegistro() {
        convenioEditar = gestionarConvenioBO.consultarConvenioPorID(idConvenio);
        if (null != convenioEditar) {
            inputNombre = convenioEditar.getNombreconvenio();
            inputDescripcion = convenioEditar.getDescripcion();
            inputValor = String.valueOf(convenioEditar.getValor());
            Date fecha1 = convenioEditar.getFechainicial();
            DateFormat df = DateFormat.getDateInstance();
            inputFechaInicio = df.format(fecha1);
            Date fecha2 = convenioEditar.getFechafinal();
            if (null != fecha2) {
                inputFechaFin = df.format(fecha2);
            } else {
                inputFechaFin = null;
            }
            estadoConvenio = convenioEditar.getEstado();
            validacionesNombre = true;
            validacionesDescripcion = true;
            validacionesValor = true;
            validacionesFechaFin = true;
            validacionesFechaInicio = true;
            modificacionesRegistro = false;
            cargarFechasConvenio();
        }
    }

    private void cargarFechasConvenio() {
        int anioRegistro1 = convenioEditar.getFechainicial().getYear() + 1900;
        int anioRegistro2 = convenioEditar.getFechafinal().getYear() + 1900;
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
        fechaInicioMes = obtenerMesExacto(convenioEditar.getFechainicial().getMonth(), 1);
        fechaFinMes = obtenerMesExacto(convenioEditar.getFechafinal().getMonth(), 1);
        actualizarInformacionInicioDia();
        actualizarInformacionFinDia();
        fechaInicioDia = obtenerDiaExacto(convenioEditar.getFechainicial().getDate(), 1);
        fechaFinDia = obtenerDiaExacto(convenioEditar.getFechafinal().getDate(), 2);
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

    public void actualizarInformacionDias() {
        modificacionesRegistro = true;
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
        modificacionesRegistro = true;
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
        modificacionesRegistro = true;
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

    public void actualizarEstado() {
        modificacionesRegistro = true;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesValor == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarFechasRegistro() {
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
        if (modificacionesRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                if (validarFechasRegistro() == true) {
                    almacenarRegistroNuevo();
                    recibirIDDetalleConvenio(this.idConvenio);
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
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No existen modificaciones para ser almacenadas en el sistema.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            convenioEditar.setNombreconvenio(inputNombre);
            convenioEditar.setValor(Integer.valueOf(inputValor));
            convenioEditar.setEstado(estadoConvenio);
            convenioEditar.setDescripcion(inputDescripcion);
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
            convenioEditar.setFechainicial(fecha1);
            convenioEditar.setFechafinal(fecha2);
            gestionarConvenioBO.editarConvenio(convenioEditar);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarConvenio almacenarRegistroNuevo:  " + e.toString());
            logger.error("Error ControllerRegistrarConvenio almacenarRegistroNuevo: " + e.toString());
        }
    }

    public void cancelarConvenio() {
        inputNombre = null;
        inputDescripcion = null;
        inputValor = "0";
        inputFechaInicio = null;
        inputFechaFin = null;
        validacionesNombre = false;
        validacionesDescripcion = false;
        validacionesValor = true;
        validacionesFechaFin = true;
        validacionesFechaInicio = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    public String cerrarPagina() {
        cancelarConvenio();
        return "administrarconvenios";
    }

    //GET-SET
    public Convenio getConvenioEditar() {
        return convenioEditar;
    }

    public void setConvenioEditar(Convenio convenioEditar) {
        this.convenioEditar = convenioEditar;
    }

    public BigInteger getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(BigInteger idConvenio) {
        this.idConvenio = idConvenio;
    }

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

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isEstadoConvenio() {
        return estadoConvenio;
    }

    public void setEstadoConvenio(boolean estadoConvenio) {
        this.estadoConvenio = estadoConvenio;
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
