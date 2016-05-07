/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.utilidades;

import com.sirelab.entidades.Asignatura;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public class ReporteCargueAsignatura {

    private List<Asignatura> listaAsignaturas;
    private boolean archivoVacio;
    private int cantidadRegistros;
    private List<String> listaErrores;

    public ReporteCargueAsignatura() {
    }

    public List<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
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

    public List<String> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<String> listaErrores) {
        this.listaErrores = listaErrores;
    }

}
