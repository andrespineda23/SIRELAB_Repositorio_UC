/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.EquipoElemento;
import java.io.Serializable;

/**
 *
 * @author AndresPineda
 */
public class AyudaReservaEquipo implements Serializable {

    private EquipoElemento equipo;
    private int cantidadExistencia;

    public EquipoElemento getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoElemento equipo) {
        this.equipo = equipo;
    }

    public int getCantidadExistencia() {
        return cantidadExistencia;
    }

    public void setCantidadExistencia(int cantidadExistencia) {
        this.cantidadExistencia = cantidadExistencia;
    }

}
