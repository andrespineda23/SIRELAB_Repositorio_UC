/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

/**
 *
 * @author AndresPineda
 */
public class HoraReserva {

    private String horaMostrar;
    private Integer hora;
    private Integer horaModuloInicio;
    private Integer horaModuloFin;

    public HoraReserva() {
    }

    public String getHoraMostrar() {
        return horaMostrar;
    }

    public void setHoraMostrar(String horaMostrar) {
        this.horaMostrar = horaMostrar;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getHoraModuloInicio() {
        return horaModuloInicio;
    }

    public void setHoraModuloInicio(Integer horaModuloInicio) {
        this.horaModuloInicio = horaModuloInicio;
    }

    public Integer getHoraModuloFin() {
        return horaModuloFin;
    }

    public void setHoraModuloFin(Integer horaModuloFin) {
        this.horaModuloFin = horaModuloFin;
    }

}
