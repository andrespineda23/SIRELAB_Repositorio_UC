/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableEstadosEquiposBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableSectoresEntidadesBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposCargosBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposPerfilesBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposUsuarioBOInterface;
import com.sirelab.entidades.SectorEntidad;
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
    GestionarVariableSectoresEntidadesBOInterface gestionarVariableSectoresEntidadesBO;

    private List<TipoPerfil> listaTiposPerfiles;
    private List<TipoPerfil> listaTiposPerfilesTabla;
    private List<TipoUsuario> listaTiposUsuario;
    private List<TipoUsuario> listaTiposUsuarioTabla;
    private List<TipoCargo> listaTiposCargos;
    private List<TipoCargo> listaTiposCargosTabla;
    private List<SectorEntidad> listaSectorEntidad;
    private List<SectorEntidad> listaSectorEntidadTabla;
    private int posicionTipoPerfilTabla, posicionTipoUsuarioTabla, posicionTipoCargoTabla, posicionSectorEntidadTabla;
    private int tamTotalTipoPerfil, tamTotalTipoUsuario, tamTotalTipoCargo, tamTotalSectorEntidad;
    private boolean bloquearPagSigTipoPerfil, bloquearPagAntTipoPerfil;
    private boolean bloquearPagSigTipoUsuario, bloquearPagAntTipoUsuario;
    private boolean bloquearPagSigTipoCargo, bloquearPagAntTipoCargo;
    private boolean bloquearPagSigSectorEntidad, bloquearPagAntSectorEntidad;

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
        listaSectorEntidad = gestionarVariableSectoresEntidadesBO.consultarSectoresEntidadesRegistrados();
        if (null != listaSectorEntidad) {
            listaSectorEntidadTabla = new ArrayList<SectorEntidad>();
            tamTotalSectorEntidad = listaSectorEntidad.size();
        }
        posicionSectorEntidadTabla = 0;
        cargarDatosTablaSectorEntidad();
    }

    private void cargarDatosTablaSectorEntidad() {
        if (tamTotalSectorEntidad < 10) {
            for (int i = 0; i < tamTotalSectorEntidad; i++) {
                listaSectorEntidadTabla.add(listaSectorEntidad.get(i));
            }
            bloquearPagSigSectorEntidad = true;
            bloquearPagAntSectorEntidad = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaSectorEntidadTabla.add(listaSectorEntidad.get(i));
            }
            bloquearPagSigSectorEntidad = false;
            bloquearPagAntSectorEntidad = true;
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
        listaSectorEntidad = null;
        posicionTipoPerfilTabla = 0;
        posicionTipoUsuarioTabla = 0;
        posicionTipoCargoTabla = 0;
        posicionSectorEntidadTabla = 0;
        tamTotalTipoPerfil = 0;
        tamTotalTipoCargo = 0;
        tamTotalTipoUsuario = 0;
        tamTotalSectorEntidad = 0;
        listaTiposPerfilesTabla = null;
        listaTiposCargosTabla = null;
        listaTiposUsuarioTabla = null;
        listaSectorEntidadTabla = null;
        bloquearPagSigTipoPerfil = true;
        bloquearPagSigTipoCargo = true;
        bloquearPagAntTipoPerfil = true;
        bloquearPagAntSectorEntidad = true;
        bloquearPagSigTipoUsuario = true;
        bloquearPagAntTipoUsuario = true;
        bloquearPagAntTipoCargo = true;
        bloquearPagSigSectorEntidad = true;
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

    public void cargarPaginaSiguienteSectorEntidad() {
        listaSectorEntidadTabla = new ArrayList<SectorEntidad>();
        posicionSectorEntidadTabla = posicionSectorEntidadTabla + 10;
        int diferencia = tamTotalSectorEntidad - posicionSectorEntidadTabla;
        if (diferencia > 10) {
            for (int i = posicionSectorEntidadTabla; i < (posicionSectorEntidadTabla + 10); i++) {
                listaSectorEntidadTabla.add(listaSectorEntidad.get(i));
            }
            bloquearPagSigSectorEntidad = false;
            bloquearPagAntSectorEntidad = false;
        } else {
            for (int i = posicionSectorEntidadTabla; i < (posicionSectorEntidadTabla + diferencia); i++) {
                listaSectorEntidadTabla.add(listaSectorEntidad.get(i));
            }
            bloquearPagSigSectorEntidad = true;
            bloquearPagAntSectorEntidad = false;
        }
    }

    public void cargarPaginaAnteriorSectorEntidad() {
        listaSectorEntidadTabla = new ArrayList<SectorEntidad>();
        posicionSectorEntidadTabla = posicionSectorEntidadTabla - 10;
        int diferencia = tamTotalSectorEntidad - posicionSectorEntidadTabla;
        if (diferencia == tamTotalSectorEntidad) {
            for (int i = posicionSectorEntidadTabla; i < (posicionSectorEntidadTabla + 10); i++) {
                listaSectorEntidadTabla.add(listaSectorEntidad.get(i));
            }
            bloquearPagSigSectorEntidad = false;
            bloquearPagAntSectorEntidad = true;
        } else {
            for (int i = posicionSectorEntidadTabla; i < (posicionSectorEntidadTabla + 10); i++) {
                listaSectorEntidadTabla.add(listaSectorEntidad.get(i));
            }
            bloquearPagSigSectorEntidad = false;
            bloquearPagAntSectorEntidad = false;
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

    public List<SectorEntidad> getListaSectorEntidad() {
        return listaSectorEntidad;
    }

    public void setListaSectorEntidad(List<SectorEntidad> listaSectorEntidad) {
        this.listaSectorEntidad = listaSectorEntidad;
    }

    public List<SectorEntidad> getListaSectorEntidadTabla() {
        return listaSectorEntidadTabla;
    }

    public void setListaSectorEntidadTabla(List<SectorEntidad> listaSectorEntidadTabla) {
        this.listaSectorEntidadTabla = listaSectorEntidadTabla;
    }

    public boolean isBloquearPagSigSectorEntidad() {
        return bloquearPagSigSectorEntidad;
    }

    public void setBloquearPagSigSectorEntidad(boolean bloquearPagSigSectorEntidad) {
        this.bloquearPagSigSectorEntidad = bloquearPagSigSectorEntidad;
    }

    public boolean isBloquearPagAntSectorEntidad() {
        return bloquearPagAntSectorEntidad;
    }

    public void setBloquearPagAntSectorEntidad(boolean bloquearPagAntSectorEntidad) {
        this.bloquearPagAntSectorEntidad = bloquearPagAntSectorEntidad;
    }

}
