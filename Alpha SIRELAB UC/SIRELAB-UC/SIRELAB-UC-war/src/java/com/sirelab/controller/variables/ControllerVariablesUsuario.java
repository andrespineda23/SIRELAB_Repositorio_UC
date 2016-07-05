/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposCargosBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposUsuarioBOInterface;
import com.sirelab.entidades.TipoCargo;
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
    GestionarVariableTiposCargosBOInterface gestionarVariableTiposCargosBO;

    private List<TipoUsuario> listaTiposUsuario;
    private List<TipoUsuario> listaTiposUsuarioTabla;
    private List<TipoCargo> listaTiposCargos;
    private List<TipoCargo> listaTiposCargosTabla;
    private int posicionTipoUsuarioTabla, posicionTipoCargoTabla;
    private int tamTotalTipoUsuario, tamTotalTipoCargo;
    private boolean bloquearPagSigTipoUsuario, bloquearPagAntTipoUsuario;
    private boolean bloquearPagSigTipoCargo, bloquearPagAntTipoCargo;

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
        listaTiposCargos = gestionarVariableTiposCargosBO.consultarTiposCargosRegistrados();
        if (null != listaTiposCargos) {
            listaTiposCargosTabla = new ArrayList<TipoCargo>();
            tamTotalTipoCargo = listaTiposCargos.size();
        }
        posicionTipoCargoTabla = 0;
        cargarDatosTablaTipoCargo();
    }

    private void cargarDatosTablaTipoCargo() {
        if (tamTotalTipoCargo < 10) {
            for (int i = 0; i < tamTotalTipoCargo; i++) {
                listaTiposCargosTabla.add(listaTiposCargos.get(i));
            }
            bloquearPagSigTipoCargo = true;
            bloquearPagAntTipoCargo = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaTiposCargosTabla.add(listaTiposCargos.get(i));
            }
            bloquearPagSigTipoCargo = false;
            bloquearPagAntTipoCargo = true;
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

    public void cargarPaginaSiguienteTipoCargo() {
        listaTiposCargosTabla = new ArrayList<TipoCargo>();
        posicionTipoCargoTabla = posicionTipoCargoTabla + 10;
        int diferencia = tamTotalTipoCargo - posicionTipoCargoTabla;
        if (diferencia > 10) {
            for (int i = posicionTipoCargoTabla; i < (posicionTipoCargoTabla + 10); i++) {
                listaTiposCargosTabla.add(listaTiposCargos.get(i));
            }
            bloquearPagSigTipoCargo = false;
            bloquearPagAntTipoCargo = false;
        } else {
            for (int i = posicionTipoCargoTabla; i < (posicionTipoCargoTabla + diferencia); i++) {
                listaTiposCargosTabla.add(listaTiposCargos.get(i));
            }
            bloquearPagSigTipoCargo = true;
            bloquearPagAntTipoCargo = false;
        }
    }

    public void cargarPaginaAnteriorTipoCargo() {
        listaTiposCargosTabla = new ArrayList<TipoCargo>();
        posicionTipoCargoTabla = posicionTipoCargoTabla - 10;
        int diferencia = tamTotalTipoCargo - posicionTipoCargoTabla;
        if (diferencia == tamTotalTipoCargo) {
            for (int i = posicionTipoCargoTabla; i < (posicionTipoCargoTabla + 10); i++) {
                listaTiposCargosTabla.add(listaTiposCargos.get(i));
            }
            bloquearPagSigTipoCargo = false;
            bloquearPagAntTipoCargo = true;
        } else {
            for (int i = posicionTipoCargoTabla; i < (posicionTipoCargoTabla + 10); i++) {
                listaTiposCargosTabla.add(listaTiposCargos.get(i));
            }
            bloquearPagSigTipoCargo = false;
            bloquearPagAntTipoCargo = false;
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
    
    public void limpiarInformacion() {
        listaTiposUsuario = null;
        listaTiposCargos = null;
        posicionTipoUsuarioTabla = 0;
        posicionTipoCargoTabla = 0;
        tamTotalTipoCargo = 0;
        tamTotalTipoUsuario = 0;
        listaTiposCargosTabla = null;
        listaTiposUsuarioTabla = null;
        bloquearPagSigTipoCargo = true;
        bloquearPagSigTipoUsuario = true;
        bloquearPagAntTipoUsuario = true;
        bloquearPagAntTipoCargo = true;
    }

    //GET-SET
    public List<TipoUsuario> getListaTiposUsuario() {
        return listaTiposUsuario;
    }

    public void setListaTiposUsuario(List<TipoUsuario> listaTiposUsuario) {
        this.listaTiposUsuario = listaTiposUsuario;
    }

    public List<TipoUsuario> getListaTiposUsuarioTabla() {
        return listaTiposUsuarioTabla;
    }

    public void setListaTiposUsuarioTabla(List<TipoUsuario> listaTiposUsuarioTabla) {
        this.listaTiposUsuarioTabla = listaTiposUsuarioTabla;
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

    public List<TipoCargo> getListaTiposCargos() {
        return listaTiposCargos;
    }

    public void setListaTiposCargos(List<TipoCargo> listaTiposCargos) {
        this.listaTiposCargos = listaTiposCargos;
    }

    public List<TipoCargo> getListaTiposCargosTabla() {
        return listaTiposCargosTabla;
    }

    public void setListaTiposCargosTabla(List<TipoCargo> listaTiposCargosTabla) {
        this.listaTiposCargosTabla = listaTiposCargosTabla;
    }

    public boolean isBloquearPagSigTipoCargo() {
        return bloquearPagSigTipoCargo;
    }

    public void setBloquearPagSigTipoCargo(boolean bloquearPagSigTipoCargo) {
        this.bloquearPagSigTipoCargo = bloquearPagSigTipoCargo;
    }

    public boolean isBloquearPagAntTipoCargo() {
        return bloquearPagAntTipoCargo;
    }

    public void setBloquearPagAntTipoCargo(boolean bloquearPagAntTipoCargo) {
        this.bloquearPagAntTipoCargo = bloquearPagAntTipoCargo;
    }

}
