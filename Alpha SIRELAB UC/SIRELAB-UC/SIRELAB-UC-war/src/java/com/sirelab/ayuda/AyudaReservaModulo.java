/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.ModuloLaboratorio;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ELECTRONICA
 */
public class AyudaReservaModulo implements Serializable {

    private String horaInicio;
    private Date fechaReserva;
    private ModuloLaboratorio moduloLaboratorio;

    public AyudaReservaModulo() {
    }

    public AyudaReservaModulo(String horaInicio, Date fechaReserva, ModuloLaboratorio moduloLaboratorio) {
        this.horaInicio = horaInicio;
        this.fechaReserva = fechaReserva;
        this.moduloLaboratorio = moduloLaboratorio;
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

    public ModuloLaboratorio getModuloLaboratorio() {
        return moduloLaboratorio;
    }

    public void setModuloLaboratorio(ModuloLaboratorio moduloLaboratorio) {
        this.moduloLaboratorio = moduloLaboratorio;
    }

}
