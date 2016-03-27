/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.TipoEvento;
import com.sirelab.utilidades.UsuarioLogin;
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
public class ControllerRegistrarHojaVidaEquipo implements Serializable {

    @EJB
    GestionarPlantaHojasVidaEquiposBOInterface gestionarPlantaHojasVidaEquiposBO;

    private String inputDetalle;
    private String inputFechaEvento, inputFechaFinEvento, inputFechaRegistro;
    private String inputCosto;
    private TipoEvento inputTipoEvento;
    private boolean validacionesFechaRegistro, validacionesFechaEvento, validacionesFechaFinEvento;
    private List<TipoEvento> listaTiposEventos;
    private boolean validacionesDetalle, validacionesCosto, validacionesTipo;
    private String mensajeFormulario;
    private BigInteger idEquipo;
    private EquipoElemento equipoElemento;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private boolean fechaDiferidaEvento, fechaDiferidaRegistro;
    private MensajesConstantes constantes;
    private AyudaFechaReserva fechaEventoAnio;
    private AyudaFechaReserva fechaEventoMes;
    private AyudaFechaReserva fechaEventoDia;
    private List<AyudaFechaReserva> listaAniosEvento;
    private List<AyudaFechaReserva> listaMesesEvento;
    private List<AyudaFechaReserva> listaDiasEvento;
    private AyudaFechaReserva fechaFinEventoAnio;
    private AyudaFechaReserva fechaFinEventoMes;
    private AyudaFechaReserva fechaFinEventoDia;
    private List<AyudaFechaReserva> listaAniosFinEvento;
    private List<AyudaFechaReserva> listaMesesFinEvento;
    private List<AyudaFechaReserva> listaDiasFinEvento;
    private AyudaFechaReserva fechaRegistroAnio;
    private AyudaFechaReserva fechaRegistroMes;
    private AyudaFechaReserva fechaRegistroDia;
    private List<AyudaFechaReserva> listaAniosRegistro;
    private List<AyudaFechaReserva> listaMesesRegistro;
    private List<AyudaFechaReserva> listaDiasRegistro;

    public ControllerRegistrarHojaVidaEquipo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        inputDetalle = null;
        inputCosto = "0";
        inputTipoEvento = null;
        validacionesDetalle = false;
        validacionesCosto = true;
        listaTiposEventos = null;
        validacionesTipo = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        validacionesFechaFinEvento = false;
        BasicConfigurator.configure();
        fechaDiferidaEvento = true;
        fechaDiferidaRegistro = true;
    }

    public void recibirIDEquipo(BigInteger idRegistro) {
        this.idEquipo = idRegistro;
        equipoElemento = gestionarPlantaHojasVidaEquiposBO.consultarEquipoElementoPorID(idEquipo);
        cargarFechaHV();
    }

    private void cargarFechaHV() {
        Date fechaHoy = new Date();
        int anioActual = fechaHoy.getYear() + 1900;
        listaAniosEvento = new ArrayList<AyudaFechaReserva>();
        listaAniosFinEvento = new ArrayList<AyudaFechaReserva>();
        listaAniosRegistro = new ArrayList<AyudaFechaReserva>();
        for (int i = 2000; i <= anioActual; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaAniosEvento.add(ayuda);
        }
        for (int i = 2000; i <= anioActual + 5; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaAniosFinEvento.add(ayuda);
        }
        for (int i = 2000; i <= anioActual; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaAniosRegistro.add(ayuda);
        }
        fechaEventoAnio = obtenerAnioActual(anioActual, 1);
        fechaRegistroAnio = obtenerAnioActual(anioActual, 2);
        fechaFinEventoAnio = obtenerAnioActual(anioActual, 3);
        listaMesesEvento = new ArrayList<AyudaFechaReserva>();
        listaMesesFinEvento = new ArrayList<AyudaFechaReserva>();
        listaMesesRegistro = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesRegistro.add(ayuda);
            listaMesesEvento.add(ayuda);
            listaMesesFinEvento.add(ayuda);
        }
        fechaEventoMes = obtenerMesExacto(fechaHoy.getMonth(), 1);
        fechaRegistroMes = obtenerMesExacto(fechaHoy.getMonth(), 2);
        fechaFinEventoMes = obtenerMesExacto(fechaHoy.getMonth(), 3);
        actualizarInformacionEventoDia();
        actualizarInformacionEventoFinDia();
        actualizarInformacionRegistroDia();
        fechaEventoDia = obtenerDiaExacto(fechaHoy.getDate(), 1);
        fechaRegistroDia = obtenerDiaExacto(fechaHoy.getDate(), 2);
        fechaFinEventoDia = obtenerDiaExacto(fechaHoy.getDate(), 3);
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
            if (op == 2) {
                for (int i = 0; i < listaAniosRegistro.size(); i++) {
                    if (anio == listaAniosRegistro.get(i).getParametro()) {
                        ayuda = listaAniosRegistro.get(i);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < listaAniosFinEvento.size(); i++) {
                    if (anio == listaAniosFinEvento.get(i).getParametro()) {
                        ayuda = listaAniosFinEvento.get(i);
                        break;
                    }
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
            if (op == 2) {
                for (int i = 0; i < listaMesesRegistro.size(); i++) {
                    if (mes == listaMesesRegistro.get(i).getParametro()) {
                        ayuda = listaMesesRegistro.get(i);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < listaMesesFinEvento.size(); i++) {
                    if (mes == listaMesesFinEvento.get(i).getParametro()) {
                        ayuda = listaMesesFinEvento.get(i);
                        break;
                    }
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
            if (op == 2) {
                for (int i = 0; i < listaDiasRegistro.size(); i++) {
                    if (dia == listaDiasRegistro.get(i).getParametro()) {
                        ayuda = listaDiasRegistro.get(i);
                        break;
                    }
                }
            } else {
                for (int i = 0; i < listaDiasFinEvento.size(); i++) {
                    if (dia == listaDiasFinEvento.get(i).getParametro()) {
                        ayuda = listaDiasFinEvento.get(i);
                        break;
                    }
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
    }

    public void actualizarInformacionAnioFinEvento() {
        listaMesesFinEvento = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesFinEvento.add(ayuda);
        }
        fechaFinEventoMes = obtenerMesExacto(0, 3);
        actualizarInformacionEventoFinDia();
        fechaFinEventoDia = obtenerDiaExacto(1, 4);
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
    }

    public void actualizarInformacionEventoFinDia() {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.set(fechaFinEventoAnio.getParametro(), fechaFinEventoMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        listaDiasFinEvento = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDiasFinEvento.add(ayuda);
        }
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
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El tamaño minimo es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.INVENTARIO_NOMBRE));
        }
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
            FacesContext.getCurrentInstance().addMessage("form:inputFechaEvento", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
        }
    }

    public void validarFechaFinEvento() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaFinEventoAnio.getParametro());
        cal.set(Calendar.MONTH, fechaFinEventoMes.getParametro());
        cal.set(Calendar.DATE, fechaFinEventoDia.getParametro());
        if (Utilidades.fechaIngresadaCorrecta(cal.getTime().toString())) {
            validacionesFechaFinEvento = true;
        } else {
            validacionesFechaFinEvento = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaFinEvento", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
        }
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
    }

    public void validarCosto() {
        if (Utilidades.validarNulo(inputCosto) && (!inputCosto.isEmpty()) && (inputCosto.trim().length() > 0)) {
            if (Utilidades.isNumber(inputCosto)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:inputCosto", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
            }
        }
    }

    public void validarTipoEvento() {
        if (Utilidades.validarNulo(inputTipoEvento)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputTipoEvento", new FacesMessage("El campo Tipo Evento es obligatorio."));
        }
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

    public void registrarHojaVidaEquipo() {
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
                almacenarRegistroNuevo();
                restaurarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "La fecha registro es menor que la fecha evento, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            HojaVidaEquipo reggNuevo = new HojaVidaEquipo();
            reggNuevo.setDetalleevento(inputDetalle);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaRegistroAnio.getParametro());
            cal.set(Calendar.MONTH, fechaRegistroMes.getParametro());
            cal.set(Calendar.DATE, fechaRegistroDia.getParametro());

            Calendar cal2 = Calendar.getInstance();
            cal2.set(Calendar.YEAR, fechaEventoAnio.getParametro());
            cal2.set(Calendar.MONTH, fechaEventoMes.getParametro());
            cal2.set(Calendar.DATE, fechaEventoDia.getParametro());
            Calendar cal3 = Calendar.getInstance();
            cal3.set(Calendar.YEAR, fechaFinEventoAnio.getParametro());
            cal3.set(Calendar.MONTH, fechaFinEventoMes.getParametro());
            cal3.set(Calendar.DATE, fechaFinEventoDia.getParametro());
            Date fecha1 = cal.getTime();
            Date fecha2 = cal2.getTime();
            Date fecha3 = cal3.getTime();
            reggNuevo.setFecharegistro(fecha1);
            reggNuevo.setFechaevento(fecha2);
            reggNuevo.setFechafinevento(fecha3);
            reggNuevo.setCosto(inputCosto);
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            reggNuevo.setUsuariomodificacion(usuarioLoginSistema.getUserUsuario());
            reggNuevo.setTipoevento(inputTipoEvento);
            reggNuevo.setEquipoelemento(equipoElemento);
            reggNuevo.setObservaciones(inputTipoEvento.getObservacion() + ": " + equipoElemento.getNombreequipo() + " / " + equipoElemento.getInventarioequipo());
            gestionarPlantaHojasVidaEquiposBO.crearHojaVidaEquipo(reggNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarHojaVidaEquipo almacenarRegistroNuevo:  " + e.toString(), e);
        }
    }

    public void cancelarHojaVidaEquipo() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        inputDetalle = null;
        inputCosto = "0";
        Date fecha = new Date();
        DateFormat df = DateFormat.getDateInstance();
        inputFechaRegistro = df.format(fecha);
        inputFechaEvento = df.format(fecha);
        inputFechaFinEvento = df.format(fecha);
        validacionesDetalle = false;
        validacionesCosto = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        validacionesFechaFinEvento = false;
        fechaDiferidaEvento = true;
        fechaDiferidaRegistro = true;
    }

    public String cerrarPagina() {
        cancelarHojaVidaEquipo();
        return "hojadevida";
    }

    private void restaurarFormulario() {
        inputDetalle = null;
        inputCosto = "0";
        inputTipoEvento = null;
        inputFechaEvento = new String();
        inputFechaFinEvento = new String();
        inputFechaRegistro = new String();
        validacionesDetalle = false;
        validacionesTipo = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        fechaDiferidaEvento = true;
        fechaDiferidaRegistro = true;
        cargarFechaHV();
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        activarAceptar = false;
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

    public TipoEvento getInputTipoEvento() {
        return inputTipoEvento;
    }

    public void setInputTipoEvento(TipoEvento inputTipoEvento) {
        this.inputTipoEvento = inputTipoEvento;
    }

    public List<TipoEvento> getListaTiposEventos() {
        if (listaTiposEventos == null) {
            listaTiposEventos = gestionarPlantaHojasVidaEquiposBO.consultarTiposEventosRegistrados();
        }
        return listaTiposEventos;
    }

    public void setListaTiposEventos(List<TipoEvento> listaTiposEventos) {
        this.listaTiposEventos = listaTiposEventos;
    }

    public EquipoElemento getEquipoElemento() {
        return equipoElemento;
    }

    public void setEquipoElemento(EquipoElemento equipoElemento) {
        this.equipoElemento = equipoElemento;
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

    public AyudaFechaReserva getFechaRegistroMes() {
        return fechaRegistroMes;
    }

    public void setFechaRegistroMes(AyudaFechaReserva fechaRegistroMes) {
        this.fechaRegistroMes = fechaRegistroMes;
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

    public String getInputFechaFinEvento() {
        return inputFechaFinEvento;
    }

    public void setInputFechaFinEvento(String inputFechaFinEvento) {
        this.inputFechaFinEvento = inputFechaFinEvento;
    }

    public String getInputCosto() {
        return inputCosto;
    }

    public void setInputCosto(String inputCosto) {
        this.inputCosto = inputCosto;
    }

    public AyudaFechaReserva getFechaFinEventoAnio() {
        return fechaFinEventoAnio;
    }

    public void setFechaFinEventoAnio(AyudaFechaReserva fechaFinEventoAnio) {
        this.fechaFinEventoAnio = fechaFinEventoAnio;
    }

    public AyudaFechaReserva getFechaFinEventoMes() {
        return fechaFinEventoMes;
    }

    public void setFechaFinEventoMes(AyudaFechaReserva fechaFinEventoMes) {
        this.fechaFinEventoMes = fechaFinEventoMes;
    }

    public AyudaFechaReserva getFechaFinEventoDia() {
        return fechaFinEventoDia;
    }

    public void setFechaFinEventoDia(AyudaFechaReserva fechaFinEventoDia) {
        this.fechaFinEventoDia = fechaFinEventoDia;
    }

    public List<AyudaFechaReserva> getListaAniosFinEvento() {
        return listaAniosFinEvento;
    }

    public void setListaAniosFinEvento(List<AyudaFechaReserva> listaAniosFinEvento) {
        this.listaAniosFinEvento = listaAniosFinEvento;
    }

    public List<AyudaFechaReserva> getListaMesesFinEvento() {
        return listaMesesFinEvento;
    }

    public void setListaMesesFinEvento(List<AyudaFechaReserva> listaMesesFinEvento) {
        this.listaMesesFinEvento = listaMesesFinEvento;
    }

    public List<AyudaFechaReserva> getListaDiasFinEvento() {
        return listaDiasFinEvento;
    }

    public void setListaDiasFinEvento(List<AyudaFechaReserva> listaDiasFinEvento) {
        this.listaDiasFinEvento = listaDiasFinEvento;
    }

}
