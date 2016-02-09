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
public class AyudaFechaReserva {

    private String mensajeMostrar;
    private int parametro;
    private Integer diaInicio;
    private Integer diaFin;

    public AyudaFechaReserva() {
    }

    public String getMensajeMostrar() {
        return mensajeMostrar;
    }

    public void setMensajeMostrar(String mensajeMostrar) {
        this.mensajeMostrar = mensajeMostrar;
    }

    public int getParametro() {
        return parametro;
    }

    public void setParametro(int parametro) {
        this.parametro = parametro;
    }

    public Integer getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(Integer diaInicio) {
        this.diaInicio = diaInicio;
    }

    public Integer getDiaFin() {
        return diaFin;
    }

    public void setDiaFin(Integer diaFin) {
        this.diaFin = diaFin;
    }

}
