/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.SalaLaboratorio;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ELECTRONICA
 */
public class AyudaReservaSala implements Serializable {

    private String horaInicio;
    private Date fechaReserva;
    private SalaLaboratorio salaLaboratorio;

    public AyudaReservaSala() {
    }

    public AyudaReservaSala(String horaInicio, Date fechaReserva, SalaLaboratorio salaLaboratorio) {
        this.horaInicio = horaInicio;
        this.fechaReserva = fechaReserva;
        this.salaLaboratorio = salaLaboratorio;
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

}
