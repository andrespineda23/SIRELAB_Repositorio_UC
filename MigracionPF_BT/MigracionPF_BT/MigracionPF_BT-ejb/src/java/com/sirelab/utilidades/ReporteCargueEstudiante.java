/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.utilidades;

import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Usuario;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public class ReporteCargueEstudiante {

    private List<Estudiante> listaEstudiantes;
    private List<Persona> listaPersonas;
    private List<Usuario> listaUsuarios;
    private List<String> listaErrores;
    private boolean archivoVacio;
    private int cantidadRegistros;

    public ReporteCargueEstudiante() {
    }

    public boolean isArchivoVacio() {
        return archivoVacio;
    }

    public void setArchivoVacio(boolean archivoVacio) {
        this.archivoVacio = archivoVacio;
    }

    public List<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(List<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public List<Persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<String> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<String> listaErrores) {
        this.listaErrores = listaErrores;
    }

    public int getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(int cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
