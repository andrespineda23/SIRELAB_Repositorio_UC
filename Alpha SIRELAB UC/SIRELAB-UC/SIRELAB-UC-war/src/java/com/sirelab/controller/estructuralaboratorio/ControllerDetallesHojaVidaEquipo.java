/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;


import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.TipoEvento;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    private String inputFechaEvento, inputFechaFinEvento, inputFechaRegistro;
    private String inputObservacion, inputUsuario, inputCosto;
    private TipoEvento inputTipoEvento;
    private String mensajeFormulario;
    private BigInteger idEquipo;
    private BigInteger idHojaVidaEquipo;
    private HojaVidaEquipo hojaVidaEquipoDetalle;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean fechaDiferidaEvento, fechaDiferidaRegistro;
    private MensajesConstantes constantes;

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
            inputObservacion = hojaVidaEquipoDetalle.getObservaciones();
            inputUsuario = hojaVidaEquipoDetalle.getUsuariomodificacion();
            inputCosto = hojaVidaEquipoDetalle.getCosto();
            DateFormat df = DateFormat.getDateInstance();
            Date fecha = hojaVidaEquipoDetalle.getFechaevento();
            Date fecha2 = hojaVidaEquipoDetalle.getFecharegistro();
            Date fecha3 = hojaVidaEquipoDetalle.getFechafinevento();
            inputFechaEvento = df.format(fecha);
            inputFechaRegistro = df.format(fecha2);
            inputFechaFinEvento = df.format(fecha3);
            idEquipo = hojaVidaEquipoDetalle.getEquipoelemento().getIdequipoelemento();
            inputTipoEvento = hojaVidaEquipoDetalle.getTipoevento();
        }
    }

    public void cancelarHojaVidaEquipo() {
        inputDetalle = null;
        inputCosto = null;
        inputObservacion = null;
        inputUsuario = null;
        inputFechaFinEvento = null;
        fechaDiferidaEvento = true;
        fechaDiferidaRegistro = true;
        inputTipoEvento = null;
        inputFechaEvento = null;
        inputFechaRegistro = null;
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

    public String getInputFechaFinEvento() {
        return inputFechaFinEvento;
    }

    public void setInputFechaFinEvento(String inputFechaFinEvento) {
        this.inputFechaFinEvento = inputFechaFinEvento;
    }

    public String getInputObservacion() {
        return inputObservacion;
    }

    public void setInputObservacion(String inputObservacion) {
        this.inputObservacion = inputObservacion;
    }

    public String getInputUsuario() {
        return inputUsuario;
    }

    public void setInputUsuario(String inputUsuario) {
        this.inputUsuario = inputUsuario;
    }

    public String getInputCosto() {
        return inputCosto;
    }

    public void setInputCosto(String inputCosto) {
        this.inputCosto = inputCosto;
    }

}
