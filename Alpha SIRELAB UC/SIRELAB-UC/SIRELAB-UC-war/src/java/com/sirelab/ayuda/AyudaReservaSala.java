/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.ServiciosSala;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ELECTRONICA
 */
public class AyudaReservaSala implements Serializable {

    private String horaInicio;
    private String horaFin;
    private Date fechaReserva;
    private SalaLaboratorio salaLaboratorio;
    private Reserva reserva;
    private String nombreAsignatura;
    private String rutaGuia;
    private ServiciosSala servicioSala;

    private static AyudaReservaSala instance = null;

    protected AyudaReservaSala() {
        // Exists only to defeat instantiation.
    }

    public static AyudaReservaSala getInstance() {
        if (instance == null) {
            instance = new AyudaReservaSala();
        }
        return instance;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public SalaLaboratorio getSalaLaboratorio() {
        return salaLaboratorio;
    }

    public void setSalaLaboratorio(SalaLaboratorio salaLaboratorio) {
        this.salaLaboratorio = salaLaboratorio;
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

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

}
