/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableConveniosBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposCargosBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposPerfilesBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposUsuarioBOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.TipoCargo;
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
    @EJB
    GestionarVariableTiposCargosBOInterface gestionarVariableTiposCargosBO;
    @EJB
    GestionarVariableConveniosBOInterface gestionarVariableConveniosBO;

    private List<TipoPerfil> listaTiposPerfiles;
    private List<TipoPerfil> listaTiposPerfilesTabla;
    private List<TipoUsuario> listaTiposUsuario;
    private List<TipoUsuario> listaTiposUsuarioTabla;
    private List<TipoCargo> listaTiposCargos;
    private List<TipoCargo> listaTiposCargosTabla;
    private List<Convenio> listaConvenios;
    private List<Convenio> listaConveniosTabla;
    private int posicionTipoPerfilTabla, posicionTipoUsuarioTabla, posicionTipoCargoTabla, posicionConvenioTabla;
    private int tamTotalTipoPerfil, tamTotalTipoUsuario, tamTotalTipoCargo, tamTotalConvenio;
    private boolean bloquearPagSigTipoPerfil, bloquearPagAntTipoPerfil;
    private boolean bloquearPagSigTipoUsuario, bloquearPagAntTipoUsuario;
    private boolean bloquearPagSigTipoCargo, bloquearPagAntTipoCargo;
    private boolean bloquearPagSigConvenio, bloquearPagAntConvenio;

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
        listaTiposCargos = gestionarVariableTiposCargosBO.consultarTiposCargosRegistrados();
        if (null != listaTiposCargos) {
            listaTiposCargosTabla = new ArrayList<TipoCargo>();
            tamTotalTipoCargo = listaTiposCargos.size();
        }
        posicionTipoCargoTabla = 0;
        cargarDatosTablaTipoCargo();
        listaConvenios = gestionarVariableConveniosBO.consultarConveniosRegistrados();
        if (null != listaConvenios) {
            listaConveniosTabla = new ArrayList<Convenio>();
            tamTotalConvenio = listaConvenios.size();
        }
        cargarDatosTablaConvenio();
    }

    private void cargarDatosTablaConvenio() {
        if (tamTotalConvenio < 10) {
            for (int i = 0; i < tamTotalConvenio; i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = true;
            bloquearPagAntConvenio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = false;
            bloquearPagAntConvenio = true;
        }
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
        listaTiposCargos = null;
        listaConvenios = null;
        posicionTipoPerfilTabla = 0;
        posicionConvenioTabla = 0;
        posicionTipoUsuarioTabla = 0;
        posicionTipoCargoTabla = 0;
        tamTotalTipoPerfil = 0;
        tamTotalConvenio = 0;
        tamTotalTipoCargo = 0;
        tamTotalTipoUsuario = 0;
        listaTiposPerfilesTabla = null;
        listaTiposCargosTabla = null;
        listaTiposUsuarioTabla = null;
        listaConveniosTabla = null;
        bloquearPagSigTipoPerfil = true;
        bloquearPagSigConvenio = true;
        bloquearPagSigTipoCargo = true;
        bloquearPagAntTipoPerfil = true;
        bloquearPagSigTipoUsuario = true;
        bloquearPagAntTipoUsuario = true;
        bloquearPagAntConvenio = true;
        bloquearPagAntTipoCargo = true;
    }

    public void cargarPaginaSiguienteConvenio() {
        listaConveniosTabla = new ArrayList<Convenio>();
        posicionConvenioTabla = posicionConvenioTabla + 10;
        int diferencia = tamTotalConvenio - posicionConvenioTabla;
        if (diferencia > 10) {
            for (int i = posicionConvenioTabla; i < (posicionConvenioTabla + 10); i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = false;
            bloquearPagAntConvenio = false;
        } else {
            for (int i = posicionConvenioTabla; i < (posicionConvenioTabla + diferencia); i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = true;
            bloquearPagAntConvenio = false;
        }
    }

    public void cargarPaginaAnteriorConvenio() {
        listaConveniosTabla = new ArrayList<Convenio>();
        posicionConvenioTabla = posicionConvenioTabla - 10;
        int diferencia = tamTotalConvenio - posicionConvenioTabla;
        if (diferencia == tamTotalConvenio) {
            for (int i = posicionConvenioTabla; i < (posicionConvenioTabla + 10); i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = false;
            bloquearPagAntConvenio = true;
        } else {
            for (int i = posicionConvenioTabla; i < (posicionConvenioTabla + 10); i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = false;
            bloquearPagAntConvenio = false;
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

    public List<Convenio> getListaConvenios() {
        return listaConvenios;
    }

    public void setListaConvenios(List<Convenio> listaConvenios) {
        this.listaConvenios = listaConvenios;
    }

    public List<Convenio> getListaConveniosTabla() {
        return listaConveniosTabla;
    }

    public void setListaConveniosTabla(List<Convenio> listaConveniosTabla) {
        this.listaConveniosTabla = listaConveniosTabla;
    }

    public boolean isBloquearPagSigConvenio() {
        return bloquearPagSigConvenio;
    }

    public void setBloquearPagSigConvenio(boolean bloquearPagSigConvenio) {
        this.bloquearPagSigConvenio = bloquearPagSigConvenio;
    }

    public boolean isBloquearPagAntConvenio() {
        return bloquearPagAntConvenio;
    }

    public void setBloquearPagAntConvenio(boolean bloquearPagAntConvenio) {
        this.bloquearPagAntConvenio = bloquearPagAntConvenio;
    }

}
