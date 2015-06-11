/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposPerfilesBOInterface;
import com.sirelab.bo.interfacebo.GestionarVariableTiposUsuarioBOInterface;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.entidades.TipoUsuario;
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
public class ControllerVariablesUsuario implements Serializable {

    @EJB
    GestionarVariableTiposUsuarioBOInterface gestionarVariableTiposUsuarioBO;
    @EJB
    GestionarVariableTiposPerfilesBOInterface gestionarVariableTiposPerfilesBO;

    private List<TipoPerfil> listaTiposPerfiles;
    private List<TipoUsuario> listaTiposUsuario;

    public ControllerVariablesUsuario() {
    }

    @PostConstruct
    public void init() {
        listaTiposUsuario = gestionarVariableTiposUsuarioBO.consultarTiposUsuariosRegistrados();
        listaTiposPerfiles = gestionarVariableTiposPerfilesBO.consultarTiposPerfilesRegistrados();
    }

    public void limpiarInformacion() {
        listaTiposUsuario = null;
        listaTiposPerfiles = null;
    }

    //GET-SET
    public List<TipoPerfil> getListaTiposPerfiles() {
        return listaTiposPerfiles;
    }

    public void setListaTiposPerfiles(List<TipoPerfil> listaTiposPerfiles) {
        this.listaTiposPerfiles = listaTiposPerfiles;
    }

    public List<TipoUsuario> getListaTiposUsuario() {
        return listaTiposUsuario;
    }

    public void setListaTiposUsuario(List<TipoUsuario> listaTiposUsuario) {
        this.listaTiposUsuario = listaTiposUsuario;
    }

}
