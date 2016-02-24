/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ServiciosSala;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ELECTRONICA
 */
public class AyudaReservaModulo implements Serializable {

    private String horaInicio;
    private String horaFin;
    private ModuloLaboratorio moduloLaboratorio;
    private Date fechaReserva;
    private Reserva reserva;
    private String nombreAsignatura;
    private String rutaGuia;
    private ServiciosSala servicioSala;

    private static AyudaReservaModulo instance = null;

    protected AyudaReservaModulo() {
        // Exists only to defeat instantiation.
    }

    public static AyudaReservaModulo getInstance() {
        if (instance == null) {
            instance = new AyudaReservaModulo();
        }
        return instance;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public ModuloLaboratorio getModuloLaboratorio() {
        return moduloLaboratorio;
    }

    public void setModuloLaboratorio(ModuloLaboratorio moduloLaboratorio) {
        this.moduloLaboratorio = moduloLaboratorio;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getRutaGuia() {
        return rutaGuia;
    }

    public void setRutaGuia(String rutaGuia) {
        this.rutaGuia = rutaGuia;
    }

    public ServiciosSala getServicioSala() {
        return servicioSala;
    }

    public void setServicioSala(ServiciosSala servicioSala) {
        this.servicioSala = servicioSala;
    }

}
