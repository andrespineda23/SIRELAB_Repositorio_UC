/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.TipoEvento;
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
public class ControllerDetallesHojaVidaEquipo implements Serializable {

    @EJB
    GestionarPlantaHojasVidaEquiposBOInterface gestionarPlantaHojasVidaEquiposBO;

    private String inputDetalle;
    private String inputFechaEvento, inputFechaRegistro;
    private TipoEvento inputTipoEvento;
    private List<TipoEvento> listaTiposEventos;
    private boolean validacionesDetalle, validacionesFechaEvento, validacionesFechaRegistro, validacionesTipo;
    private String mensajeFormulario;
    private BigInteger idEquipo;
    private BigInteger idHojaVidaEquipo;
    private HojaVidaEquipo hojaVidaEquipoDetalle;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean fechaDiferidaEvento, fechaDiferidaRegistro;
    private MensajesConstantes constantes;
    private AyudaFechaReserva fechaEventoAnio;
    private AyudaFechaReserva fechaEventoMes;
    private AyudaFechaReserva fechaEventoDia;
    private List<AyudaFechaReserva> listaAniosEvento;
    private List<AyudaFechaReserva> listaMesesEvento;
    private List<AyudaFechaReserva> listaDiasEvento;
    private AyudaFechaReserva fechaRegistroMes;
    private AyudaFechaReserva fechaRegistroAnio;
    private AyudaFechaReserva fechaRegistroDia;
    private List<AyudaFechaReserva> listaAniosRegistro;
    private List<AyudaFechaReserva> listaMesesRegistro;
    private List<AyudaFechaReserva> listaDiasRegistro;

    public ControllerDetallesHojaVidaEquipo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void recibirIDHojaVidaEquipo(BigInteger idRegistro) {
        this.idHojaVidaEquipo = idRegistro;
        cargarInformacionRegistro();
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    private void cargarInformacionRegistro() {
        hojaVidaEquipoDetalle = gestionarPlantaHojasVidaEquiposBO.consultarHojaVidaEquipoPorID(idHojaVidaEquipo);
        if (null != hojaVidaEquipoDetalle) {
            inputDetalle = hojaVidaEquipoDetalle.getDetalleevento();
            Date fecha = hojaVidaEquipoDetalle.getFechaevento();
            DateFormat df = DateFormat.getDateInstance();
            Date fecha2 = hojaVidaEquipoDetalle.getFecharegistro();
            inputFechaEvento = df.format(fecha);
            inputFechaRegistro = df.format(fecha2);
            idEquipo = hojaVidaEquipoDetalle.getEquipoelemento().getIdequipoelemento();
            validacionesDetalle = true;
            validacionesFechaEvento = true;
            validacionesFechaRegistro = true;
            modificacionesRegistro = false;
            validacionesTipo = true;
            inputTipoEvento = hojaVidaEquipoDetalle.getTipoevento();
            listaTiposEventos = gestionarPlantaHojasVidaEquiposBO.consultarTiposEventosRegistrados();
            fechaDiferidaEvento = true;
            fechaDiferidaRegistro = true;
            cargarFechaReserva();
        }
    }

    private void cargarFechaReserva() {
        int anioRegistro1 = hojaVidaEquipoDetalle.getFechaevento().getYear() + 1900;
        int anioRegistro2 = hojaVidaEquipoDetalle.getFecharegistro().getYear() + 1900;
        int anioActual = new Date().getYear() + 1900;
        listaAniosEvento = new ArrayList<AyudaFechaReserva>();
        listaAniosRegistro = new ArrayList<AyudaFechaReserva>();
        for (int i = 2000; i <= anioActual; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaAniosEvento.add(ayuda);
        }
        for (int i = 2000; i <= anioActual; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaAniosRegistro.add(ayuda);
        }
        fechaEventoAnio = obtenerAnioActual(anioRegistro1, 1);
        fechaRegistroAnio = obtenerAnioActual(anioRegistro2, 2);
        listaMesesEvento = new ArrayList<AyudaFechaReserva>();
        listaMesesRegistro = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesRegistro.add(ayuda);
            listaMesesEvento.add(ayuda);
        }
        fechaEventoMes = obtenerMesExacto(hojaVidaEquipoDetalle.getFechaevento().getMonth(), 1);
        fechaRegistroMes = obtenerMesExacto(hojaVidaEquipoDetalle.getFecharegistro().getMonth(), 2);
        actualizarInformacionEventoDia();
        actualizarInformacionRegistroDia();
        fechaEventoDia = obtenerDiaExacto(hojaVidaEquipoDetalle.getFechaevento().getDate(), 1);
        fechaRegistroDia = obtenerDiaExacto(hojaVidaEquipoDetalle.getFecharegistro().getDate(), 2);
    }

    private AyudaFechaReserva obtenerAnioActual(int anio, int op) {
        AyudaFechaReserva ayuda = null;
        if (op == 1) {
            for (int i = 0; i < listaAniosEvento.size(); i++) {
                if (anio == listaAniosEvento.get(i).getParametro()) {
                    ayuda = listaAniosEvento.get(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < listaAniosRegistro.size(); i++) {
                if (anio == listaAniosRegistro.get(i).getParametro()) {
                    ayuda = listaAniosRegistro.get(i);
                    break;
                }
            }
        }
        return ayuda;
    }

    private AyudaFechaReserva obtenerMesExacto(int mes, int op) {
        AyudaFechaReserva ayuda = null;
        if (op == 1) {
            for (int i = 0; i < listaMesesEvento.size(); i++) {
                if (mes == listaMesesEvento.get(i).getParametro()) {
                    ayuda = listaMesesEvento.get(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < listaMesesRegistro.size(); i++) {
                if (mes == listaMesesRegistro.get(i).getParametro()) {
                    ayuda = listaMesesRegistro.get(i);
                    break;
                }
            }
        }
        return ayuda;
    }

    private AyudaFechaReserva obtenerDiaExacto(int dia, int op) {
        AyudaFechaReserva ayuda = null;
        if (op == 1) {
            for (int i = 0; i < listaDiasEvento.size(); i++) {
                if (dia == listaDiasEvento.get(i).getParametro()) {
                    ayuda = listaDiasEvento.get(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < listaDiasRegistro.size(); i++) {
                if (dia == listaDiasRegistro.get(i).getParametro()) {
                    ayuda = listaDiasRegistro.get(i);
                    break;
                }
            }
        }
        return ayuda;
    }

    public void actualizarInformacionAnioEvento() {
        listaMesesEvento = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesEvento.add(ayuda);
        }
        fechaEventoMes = obtenerMesExacto(0, 1);
        actualizarInformacionEventoDia();
        fechaEventoDia = obtenerDiaExacto(1, 1);
        modificacionesRegistro = true;
    }

    public void actualizarInformacionAnioRegistro() {
        listaMesesRegistro = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesRegistro.add(ayuda);
        }
        fechaRegistroMes = obtenerMesExacto(0, 2);
        actualizarInformacionRegistroDia();
        fechaRegistroDia = obtenerDiaExacto(1, 2);
        modificacionesRegistro = true;
    }

    public void actualizarInformacionEventoDia() {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.set(fechaEventoAnio.getParametro(), fechaEventoMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        listaDiasEvento = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDiasEvento.add(ayuda);
        }
        modificacionesRegistro = true;
    }

    public void actualizarInformacionRegistroDia() {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.set(fechaRegistroAnio.getParametro(), fechaRegistroMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        listaDiasRegistro = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDiasRegistro.add(ayuda);
        }
        modificacionesRegistro = true;
    }

    public void modificarDias() {
        modificacionesRegistro = true;
    }

    public void validarDetalle() {
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty()) && (inputDetalle.trim().length() > 0)) {
            int tam = inputDetalle.length();
            if (tam >= 4) {
                if (Utilidades.validarCaracterString(inputDetalle)) {
                    validacionesDetalle = true;
                } else {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.INVENTARIO_NOMBRE));
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.INVENTARIO_NOMBRE));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaEvento() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaEventoAnio.getParametro());
        cal.set(Calendar.MONTH, fechaEventoMes.getParametro());
        cal.set(Calendar.DATE, fechaEventoDia.getParametro());
        if (Utilidades.fechaIngresadaCorrecta(cal.getTime().toString())) {
            validacionesFechaEvento = true;
        } else {
            validacionesFechaEvento = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaEvento", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
        }

        modificacionesRegistro = true;
    }

    public void validarFechaRegistro() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaRegistroAnio.getParametro());
        cal.set(Calendar.MONTH, fechaRegistroMes.getParametro());
        cal.set(Calendar.DATE, fechaRegistroDia.getParametro());
        if (Utilidades.fechaIngresadaCorrecta(cal.getTime().toString())) {
            validacionesFechaRegistro = true;
        } else {
            validacionesFechaRegistro = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaRegistro", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
        }

        modificacionesRegistro = true;
    }

    public void validarTipoEvento() {
        if (Utilidades.validarNulo(inputTipoEvento)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputTipoEvento", new FacesMessage("El campo Tipo Evento es obligatorio."));
        }
        modificacionesRegistro = true;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDetalle == false) {
            retorno = false;
        }
        if (validacionesTipo == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionHojaVidaEquipo() {
        if (modificacionesRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, fechaRegistroAnio.getParametro());
                cal.set(Calendar.MONTH, fechaRegistroMes.getParametro());
                cal.set(Calendar.DATE, fechaRegistroDia.getParametro());

                Calendar cal2 = Calendar.getInstance();
                cal2.set(Calendar.YEAR, fechaEventoAnio.getParametro());
                cal2.set(Calendar.MONTH, fechaEventoMes.getParametro());
                cal2.set(Calendar.DATE, fechaEventoDia.getParametro());

                Date date1 = cal.getTime();
                Date date2 = cal2.getTime();
                int comparacion = 1;
                comparacion = date1.compareTo(date2);
                if (comparacion != 1) {
                    almacenarModificacionRegistro();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                    cargarInformacionRegistro();
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "La fecha registro es menor que la fecha evento, por favor corregir para continuar.";
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
            hojaVidaEquipoDetalle.setDetalleevento(inputDetalle);
            hojaVidaEquipoDetalle.setFecharegistro(new Date(inputFechaRegistro));
            hojaVidaEquipoDetalle.setTipoevento(inputTipoEvento);
            hojaVidaEquipoDetalle.setFechaevento(new Date(inputFechaEvento));
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaRegistroAnio.getParametro());
            cal.set(Calendar.MONTH, fechaRegistroMes.getParametro());
            cal.set(Calendar.DATE, fechaRegistroDia.getParametro());
            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.YEAR, fechaEventoAnio.getParametro());
            cal2.set(Calendar.MONTH, fechaEventoMes.getParametro());
            cal2.set(Calendar.DATE, fechaEventoDia.getParametro());
            Date fecha1 = cal.getTime();
            Date fecha2 = cal2.getTime();
            hojaVidaEquipoDetalle.setFecharegistro(fecha1);
            hojaVidaEquipoDetalle.setFechaevento(fecha2);
            gestionarPlantaHojasVidaEquiposBO.editarHojaVidaEquipo(hojaVidaEquipoDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarHojaVidaEquipo almacenarModificacionRegistro:  " + e.toString());
            logger.error("Error ControllerRegistrarHojaVidaEquipo almacenarModificacionRegistro: " + e.toString());
        }
    }

    public void cancelarHojaVidaEquipo() {
        inputDetalle = null;
        fechaDiferidaEvento = true;
        fechaDiferidaRegistro = true;
        inputTipoEvento = null;
        inputFechaEvento = null;
        inputFechaRegistro = null;
        validacionesDetalle = false;
        validacionesTipo = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    public String cerrarPagina() {
        cancelarHojaVidaEquipo();
        return "hojadevida";
    }

    //GET-SET
    public String getInputDetalle() {
        return inputDetalle;
    }

    public void setInputDetalle(String inputDetalle) {
        this.inputDetalle = inputDetalle;
    }

    public String getInputFechaEvento() {
        return inputFechaEvento;
    }

    public void setInputFechaEvento(String inputFechaEvento) {
        this.inputFechaEvento = inputFechaEvento;
    }

    public String getInputFechaRegistro() {
        return inputFechaRegistro;
    }

    public void setInputFechaRegistro(String inputFechaRegistro) {
        this.inputFechaRegistro = inputFechaRegistro;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public BigInteger getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(BigInteger idEquipo) {
        this.idEquipo = idEquipo;
    }

    public BigInteger getIdHojaVidaEquipo() {
        return idHojaVidaEquipo;
    }

    public void setIdHojaVidaEquipo(BigInteger idHojaVidaEquipo) {
        this.idHojaVidaEquipo = idHojaVidaEquipo;
    }

    public TipoEvento getInputTipoEvento() {
        return inputTipoEvento;
    }

    public void setInputTipoEvento(TipoEvento inputTipoEvento) {
        this.inputTipoEvento = inputTipoEvento;
    }

    public List<TipoEvento> getListaTiposEventos() {
        return listaTiposEventos;
    }

    public void setListaTiposEventos(List<TipoEvento> listaTiposEventos) {
        this.listaTiposEventos = listaTiposEventos;
    }

    public HojaVidaEquipo getHojaVidaEquipoDetalle() {
        return hojaVidaEquipoDetalle;
    }

    public void setHojaVidaEquipoDetalle(HojaVidaEquipo hojaVidaEquipoDetalle) {
        this.hojaVidaEquipoDetalle = hojaVidaEquipoDetalle;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isFechaDiferidaEvento() {
        return fechaDiferidaEvento;
    }

    public void setFechaDiferidaEvento(boolean fechaDiferidaEvento) {
        this.fechaDiferidaEvento = fechaDiferidaEvento;
    }

    public boolean isFechaDiferidaRegistro() {
        return fechaDiferidaRegistro;
    }

    public void setFechaDiferidaRegistro(boolean fechaDiferidaRegistro) {
        this.fechaDiferidaRegistro = fechaDiferidaRegistro;
    }

    public AyudaFechaReserva getFechaEventoAnio() {
        return fechaEventoAnio;
    }

    public void setFechaEventoAnio(AyudaFechaReserva fechaEventoAnio) {
        this.fechaEventoAnio = fechaEventoAnio;
    }

    public List<AyudaFechaReserva> getListaAniosEvento() {
        return listaAniosEvento;
    }

    public void setListaAniosEvento(List<AyudaFechaReserva> listaAniosEvento) {
        this.listaAniosEvento = listaAniosEvento;
    }

    public AyudaFechaReserva getFechaRegistroMes() {
        return fechaRegistroMes;
    }

    public void setFechaRegistroMes(AyudaFechaReserva fechaRegistroMes) {
        this.fechaRegistroMes = fechaRegistroMes;
    }

    public AyudaFechaReserva getFechaRegistroAnio() {
        return fechaRegistroAnio;
    }

    public void setFechaRegistroAnio(AyudaFechaReserva fechaRegistroAnio) {
        this.fechaRegistroAnio = fechaRegistroAnio;
    }

    public List<AyudaFechaReserva> getListaAniosRegistro() {
        return listaAniosRegistro;
    }

    public void setListaAniosRegistro(List<AyudaFechaReserva> listaAniosRegistro) {
        this.listaAniosRegistro = listaAniosRegistro;
    }

    public AyudaFechaReserva getFechaEventoMes() {
        return fechaEventoMes;
    }

    public void setFechaEventoMes(AyudaFechaReserva fechaEventoMes) {
        this.fechaEventoMes = fechaEventoMes;
    }

    public AyudaFechaReserva getFechaEventoDia() {
        return fechaEventoDia;
    }

    public void setFechaEventoDia(AyudaFechaReserva fechaEventoDia) {
        this.fechaEventoDia = fechaEventoDia;
    }

    public List<AyudaFechaReserva> getListaMesesEvento() {
        return listaMesesEvento;
    }

    public void setListaMesesEvento(List<AyudaFechaReserva> listaMesesEvento) {
        this.listaMesesEvento = listaMesesEvento;
    }

    public List<AyudaFechaReserva> getListaDiasEvento() {
        return listaDiasEvento;
    }

    public void setListaDiasEvento(List<AyudaFechaReserva> listaDiasEvento) {
        this.listaDiasEvento = listaDiasEvento;
    }

    public AyudaFechaReserva getFechaRegistroDia() {
        return fechaRegistroDia;
    }

    public void setFechaRegistroDia(AyudaFechaReserva fechaRegistroDia) {
        this.fechaRegistroDia = fechaRegistroDia;
    }

    public List<AyudaFechaReserva> getListaMesesRegistro() {
        return listaMesesRegistro;
    }

    public void setListaMesesRegistro(List<AyudaFechaReserva> listaMesesRegistro) {
        this.listaMesesRegistro = listaMesesRegistro;
    }

    public List<AyudaFechaReserva> getListaDiasRegistro() {
        return listaDiasRegistro;
    }

    public void setListaDiasRegistro(List<AyudaFechaReserva> listaDiasRegistro) {
        this.listaDiasRegistro = listaDiasRegistro;
    }

}
