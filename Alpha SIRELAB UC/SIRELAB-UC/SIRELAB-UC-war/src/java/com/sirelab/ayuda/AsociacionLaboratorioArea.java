/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.entidades.AreaProfundizacion;
import java.io.Serializable;

/**
 *
 * @author ELECTRONICA
 */
public class AsociacionLaboratorioArea implements Serializable{

    private AreaProfundizacion areaProfundizacion;
    private boolean activo;

    public AsociacionLaboratorioArea() {
    }

    public AsociacionLaboratorioArea(AreaProfundizacion areaProfundizacion, boolean activo) {
        this.areaProfundizacion = areaProfundizacion;
        this.activo = activo;
    }

    public AreaProfundizacion getAreaProfundizacion() {
        return areaProfundizacion;
    }

    public void setAreaProfundizacion(AreaProfundizacion areaProfundizacion) {
        this.areaProfundizacion = areaProfundizacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
