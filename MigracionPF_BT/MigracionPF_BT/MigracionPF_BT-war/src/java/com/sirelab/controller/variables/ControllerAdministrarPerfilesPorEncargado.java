/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariablePerfilesPorEncargadoBOInterface;
import com.sirelab.entidades.PerfilPorEncargado;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarPerfilesPorEncargado implements Serializable {

    @EJB
    GestionarVariablePerfilesPorEncargadoBOInterface gestionarVariablePerfilesPorEncargadoBO;

    private List<PerfilPorEncargado> listaPerfilesPorEncargado;
    private List<TipoPerfil> listaTiposPerfiles;
    private TipoPerfil parametroTipoPerfil;

    public ControllerAdministrarPerfilesPorEncargado() {
    }

    @PostConstruct
    public void init() {
        listaPerfilesPorEncargado = null;
        listaTiposPerfiles = gestionarVariablePerfilesPorEncargadoBO.consultarTiposPerfilesRegistrados();
        parametroTipoPerfil = null;
    }

    public void buscarPerfilesPorEncargadoPorParametros() {
        try {
            BigInteger secuencia = null;
            if (Utilidades.validarNulo(parametroTipoPerfil)) {
                secuencia = parametroTipoPerfil.getIdtipoperfil();
            } else {
                secuencia = null;
            }
            listaPerfilesPorEncargado = null;
            listaPerfilesPorEncargado = gestionarVariablePerfilesPorEncargadoBO.consultarPerfilesPorIDPerfil(secuencia);
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarPerfilesPorEncargado buscarPerfilesPorEncargadoPorParametros :" + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        listaPerfilesPorEncargado = null;
        listaTiposPerfiles = gestionarVariablePerfilesPorEncargadoBO.consultarTiposPerfilesRegistrados();
        parametroTipoPerfil = null;
    }

    //GET-SET
    public List<PerfilPorEncargado> getListaPerfilesPorEncargado() {
        return listaPerfilesPorEncargado;
    }

    public void setListaPerfilesPorEncargado(List<PerfilPorEncargado> listaPerfilesPorEncargado) {
        this.listaPerfilesPorEncargado = listaPerfilesPorEncargado;
    }

    public List<TipoPerfil> getListaTiposPerfiles() {
        return listaTiposPerfiles;
    }

    public void setListaTiposPerfiles(List<TipoPerfil> listaTiposPerfiles) {
        this.listaTiposPerfiles = listaTiposPerfiles;
    }

    public TipoPerfil getParametroTipoPerfil() {
        return parametroTipoPerfil;
    }

    public void setParametroTipoPerfil(TipoPerfil parametroTipoPerfil) {
        this.parametroTipoPerfil = parametroTipoPerfil;
    }

}
