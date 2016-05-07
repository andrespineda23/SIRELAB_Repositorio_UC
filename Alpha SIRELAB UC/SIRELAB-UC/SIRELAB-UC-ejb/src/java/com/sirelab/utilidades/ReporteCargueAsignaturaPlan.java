/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.utilidades;

import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public class ReporteCargueAsignaturaPlan {

    private List<AsignaturaPorPlanEstudio> listaAsignaturaisgnaPlan;
    private boolean archivoVacio;
    private int cantidadRegistros;
    private List<String> listaErrores;

    public ReporteCargueAsignaturaPlan() {
    }

    public List<String> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<String> listaErrores) {
        this.listaErrores = listaErrores;
    }

    public List<AsignaturaPorPlanEstudio> getListaAsignaturaisgnaPlan() {
        return listaAsignaturaisgnaPlan;
    }

    public void setListaAsignaturaisgnaPlan(List<AsignaturaPorPlanEstudio> listaAsignaturaisgnaPlan) {
        this.listaAsignaturaisgnaPlan = listaAsignaturaisgnaPlan;
    }

    public boolean isArchivoVacio() {
        return archivoVacio;
    }

    public void setArchivoVacio(boolean archivoVacio) {
        this.archivoVacio = archivoVacio;
    }

    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
