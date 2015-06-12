/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableEstadosEquiposBOInterface;
import com.sirelab.bo.interfacebo.GestionarVariableTiposActivosBOInterface;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.TipoActivo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerVariablesInventario implements Serializable {

    @EJB
    GestionarVariableEstadosEquiposBOInterface gestionarVariableEstadosEquiposBO;
    @EJB
    GestionarVariableTiposActivosBOInterface gestionarVariableTiposActivosBO;

    private List<TipoActivo> listaTiposActivos;
    private List<EstadoEquipo> listaEstadosEquipos;

    public ControllerVariablesInventario() {
    }

    @PostConstruct
    public void init() {
        listaTiposActivos = gestionarVariableTiposActivosBO.consultarTiposActivosRegistrados();
        listaEstadosEquipos = gestionarVariableEstadosEquiposBO.consultarEstadosEquiposRegistrados();
    }

    public void limpiarInformacion() {
        listaTiposActivos = null;
        listaEstadosEquipos = null;
    }

    //GET-SET
    public List<TipoActivo> getListaTiposActivos() {
        return listaTiposActivos;
    }

    public void setListaTiposActivos(List<TipoActivo> listaTiposActivos) {
        this.listaTiposActivos = listaTiposActivos;
    }

    public List<EstadoEquipo> getListaEstadosEquipos() {
        return listaEstadosEquipos;
    }

    public void setListaEstadosEquipos(List<EstadoEquipo> listaEstadosEquipos) {
        this.listaEstadosEquipos = listaEstadosEquipos;
    }

}
