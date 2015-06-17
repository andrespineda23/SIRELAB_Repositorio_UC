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
import java.util.ArrayList;
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
    private List<TipoPerfil> listaTiposPerfilesTabla;
    private List<TipoUsuario> listaTiposUsuario;
    private List<TipoUsuario> listaTiposUsuarioTabla;
    private int posicionTipoPerfilTabla, posicionTipoUsuarioTabla;
    private int tamTotalTipoPerfil, tamTotalTipoUsuario;
    private boolean bloquearPagSigTipoPerfil, bloquearPagAntTipoPerfil;
    private boolean bloquearPagSigTipoUsuario, bloquearPagAntTipoUsuario;

    public ControllerVariablesUsuario() {
    }

    @PostConstruct
    public void init() {
    }

    public void iniciarInformacionTablas() {
        listaTiposUsuario = gestionarVariableTiposUsuarioBO.consultarTiposUsuariosRegistrados();
        if (null != listaTiposUsuario) {
            listaTiposUsuarioTabla = new ArrayList<TipoUsuario>();
            tamTotalTipoUsuario = listaTiposUsuario.size();
        }
        posicionTipoUsuarioTabla = 0;
        cargarDatosTablaTipoUsuario();
        listaTiposPerfiles = gestionarVariableTiposPerfilesBO.consultarTiposPerfilesRegistrados();
        if (null != listaTiposPerfiles) {
            listaTiposPerfilesTabla = new ArrayList<TipoPerfil>();
            tamTotalTipoPerfil = listaTiposPerfiles.size();
        }
        posicionTipoPerfilTabla = 0;
        cargarDatosTablaTipoPerfil();
    }

    private void cargarDatosTablaTipoPerfil() {
        if (tamTotalTipoPerfil < 10) {
            for (int i = 0; i < tamTotalTipoPerfil; i++) {
                listaTiposPerfilesTabla.add(listaTiposPerfiles.get(i));
            }
            bloquearPagSigTipoPerfil = true;
            bloquearPagAntTipoPerfil = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaTiposPerfilesTabla.add(listaTiposPerfiles.get(i));
            }
            bloquearPagSigTipoPerfil = false;
            bloquearPagAntTipoPerfil = true;
        }
    }

    private void cargarDatosTablaTipoUsuario() {
        if (tamTotalTipoUsuario < 10) {
            for (int i = 0; i < tamTotalTipoUsuario; i++) {
                listaTiposUsuarioTabla.add(listaTiposUsuario.get(i));
            }
            bloquearPagSigTipoUsuario = true;
            bloquearPagAntTipoUsuario = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaTiposUsuarioTabla.add(listaTiposUsuario.get(i));
            }
            bloquearPagSigTipoUsuario = false;
            bloquearPagAntTipoUsuario = true;
        }
    }

    public void limpiarInformacion() {
        listaTiposUsuario = null;
        listaTiposPerfiles = null;
        posicionTipoPerfilTabla = 0;
        posicionTipoUsuarioTabla = 0;
        tamTotalTipoPerfil = 0;
        tamTotalTipoUsuario = 0;
        listaTiposPerfilesTabla = null;
        listaTiposUsuarioTabla = null;
        bloquearPagSigTipoPerfil = true;
        bloquearPagAntTipoPerfil = true;
        bloquearPagSigTipoUsuario = true;
        bloquearPagAntTipoUsuario = true;
    }

    public void cargarPaginaSiguienteTipoPerfil() {
        listaTiposPerfilesTabla = new ArrayList<TipoPerfil>();
        posicionTipoPerfilTabla = posicionTipoPerfilTabla + 10;
        int diferencia = tamTotalTipoPerfil - posicionTipoPerfilTabla;
        if (diferencia > 10) {
            for (int i = posicionTipoPerfilTabla; i < (posicionTipoPerfilTabla + 10); i++) {
                listaTiposPerfilesTabla.add(listaTiposPerfiles.get(i));
            }
            bloquearPagSigTipoPerfil = false;
            bloquearPagAntTipoPerfil = false;
        } else {
            for (int i = posicionTipoPerfilTabla; i < (posicionTipoPerfilTabla + diferencia); i++) {
                listaTiposPerfilesTabla.add(listaTiposPerfiles.get(i));
            }
            bloquearPagSigTipoPerfil = true;
            bloquearPagAntTipoPerfil = false;
        }
    }

    public void cargarPaginaAnteriorTipoPerfil() {
        listaTiposPerfilesTabla = new ArrayList<TipoPerfil>();
        posicionTipoPerfilTabla = posicionTipoPerfilTabla - 10;
        int diferencia = tamTotalTipoPerfil - posicionTipoPerfilTabla;
        if (diferencia == tamTotalTipoPerfil) {
            for (int i = posicionTipoPerfilTabla; i < (posicionTipoPerfilTabla + 10); i++) {
                listaTiposPerfilesTabla.add(listaTiposPerfiles.get(i));
            }
            bloquearPagSigTipoPerfil = false;
            bloquearPagAntTipoPerfil = true;
        } else {
            for (int i = posicionTipoPerfilTabla; i < (posicionTipoPerfilTabla + 10); i++) {
                listaTiposPerfilesTabla.add(listaTiposPerfiles.get(i));
            }
            bloquearPagSigTipoPerfil = false;
            bloquearPagAntTipoPerfil = false;
        }
    }

    public void cargarPaginaSiguienteTipoUsuario() {
        listaTiposUsuarioTabla = new ArrayList<TipoUsuario>();
        posicionTipoUsuarioTabla = posicionTipoUsuarioTabla + 10;
        int diferencia = tamTotalTipoUsuario - posicionTipoUsuarioTabla;
        if (diferencia > 10) {
            for (int i = posicionTipoUsuarioTabla; i < (posicionTipoUsuarioTabla + 10); i++) {
                listaTiposUsuarioTabla.add(listaTiposUsuario.get(i));
            }
            bloquearPagSigTipoUsuario = false;
            bloquearPagAntTipoUsuario = false;
        } else {
            for (int i = posicionTipoUsuarioTabla; i < (posicionTipoUsuarioTabla + diferencia); i++) {
                listaTiposUsuarioTabla.add(listaTiposUsuario.get(i));
            }
            bloquearPagSigTipoUsuario = true;
            bloquearPagAntTipoUsuario = false;
        }
    }

    public void cargarPaginaAnteriorTipoUsuario() {
        listaTiposUsuarioTabla = new ArrayList<TipoUsuario>();
        posicionTipoUsuarioTabla = posicionTipoUsuarioTabla - 10;
        int diferencia = tamTotalTipoUsuario - posicionTipoUsuarioTabla;
        if (diferencia == tamTotalTipoUsuario) {
            for (int i = posicionTipoUsuarioTabla; i < (posicionTipoUsuarioTabla + 10); i++) {
                listaTiposUsuarioTabla.add(listaTiposUsuario.get(i));
            }
            bloquearPagSigTipoUsuario = false;
            bloquearPagAntTipoUsuario = true;
        } else {
            for (int i = posicionTipoUsuarioTabla; i < (posicionTipoUsuarioTabla + 10); i++) {
                listaTiposUsuarioTabla.add(listaTiposUsuario.get(i));
            }
            bloquearPagSigTipoUsuario = false;
            bloquearPagAntTipoUsuario = false;
        }
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

    public List<TipoPerfil> getListaTiposPerfilesTabla() {
        return listaTiposPerfilesTabla;
    }

    public void setListaTiposPerfilesTabla(List<TipoPerfil> listaTiposPerfilesTabla) {
        this.listaTiposPerfilesTabla = listaTiposPerfilesTabla;
    }

    public List<TipoUsuario> getListaTiposUsuarioTabla() {
        return listaTiposUsuarioTabla;
    }

    public void setListaTiposUsuarioTabla(List<TipoUsuario> listaTiposUsuarioTabla) {
        this.listaTiposUsuarioTabla = listaTiposUsuarioTabla;
    }

    public boolean isBloquearPagSigTipoPerfil() {
        return bloquearPagSigTipoPerfil;
    }

    public void setBloquearPagSigTipoPerfil(boolean bloquearPagSigTipoPerfil) {
        this.bloquearPagSigTipoPerfil = bloquearPagSigTipoPerfil;
    }

    public boolean isBloquearPagAntTipoPerfil() {
        return bloquearPagAntTipoPerfil;
    }

    public void setBloquearPagAntTipoPerfil(boolean bloquearPagAntTipoPerfil) {
        this.bloquearPagAntTipoPerfil = bloquearPagAntTipoPerfil;
    }

    public boolean isBloquearPagSigTipoUsuario() {
        return bloquearPagSigTipoUsuario;
    }

    public void setBloquearPagSigTipoUsuario(boolean bloquearPagSigTipoUsuario) {
        this.bloquearPagSigTipoUsuario = bloquearPagSigTipoUsuario;
    }

    public boolean isBloquearPagAntTipoUsuario() {
        return bloquearPagAntTipoUsuario;
    }

    public void setBloquearPagAntTipoUsuario(boolean bloquearPagAntTipoUsuario) {
        this.bloquearPagAntTipoUsuario = bloquearPagAntTipoUsuario;
    }

}
