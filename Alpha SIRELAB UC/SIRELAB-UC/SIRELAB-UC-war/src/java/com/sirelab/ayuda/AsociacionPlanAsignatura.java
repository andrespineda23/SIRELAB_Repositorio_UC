/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.Asignatura;
import java.io.Serializable;

/**
 *
 * @author ELECTRONICA
 */
public class AsociacionPlanAsignatura implements Serializable {

    private Asignatura asignatura;
    private boolean activo;

    public AsociacionPlanAsignatura() {
    }

    public AsociacionPlanAsignatura(Asignatura asignatura, boolean activo) {
        this.asignatura = asignatura;
        this.activo = activo;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
