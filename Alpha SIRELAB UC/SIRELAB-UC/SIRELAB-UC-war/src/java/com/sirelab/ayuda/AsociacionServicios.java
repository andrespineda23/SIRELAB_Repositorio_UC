/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.ServiciosSala;
import java.io.Serializable;

/**
 *
 * @author AndresPineda
 */
public class AsociacionServicios implements Serializable {

    private ServiciosSala servicio;
    private boolean activo;

    public AsociacionServicios() {
    }

    public AsociacionServicios(ServiciosSala servicio, boolean activo) {
        this.servicio = servicio;
        this.activo = activo;
    }

    public ServiciosSala getServicio() {
        return servicio;
    }

    public void setServicio(ServiciosSala servicio) {
        this.servicio = servicio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
