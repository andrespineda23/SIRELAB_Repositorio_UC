/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableEstadosEquiposBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposActivosBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposComponentesBOInterface;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposEventosBOInterface;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.TipoActivo;
import com.sirelab.entidades.TipoComponente;
import com.sirelab.entidades.TipoEvento;
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
public class ControllerVariablesInventario implements Serializable {

    @EJB
    GestionarVariableEstadosEquiposBOInterface gestionarVariableEstadosEquiposBO;
    @EJB
    GestionarVariableTiposActivosBOInterface gestionarVariableTiposActivosBO;
    @EJB
    GestionarVariableTiposEventosBOInterface gestionarVariableTiposEventosBO;
    @EJB
    GestionarVariableTiposComponentesBOInterface gestionarVariableTiposComponentesBO;

    private List<TipoActivo> listaTiposActivos;
    private List<TipoActivo> listaTiposActivosTabla;
    private List<EstadoEquipo> listaEstadosEquipos;
    private List<EstadoEquipo> listaEstadosEquiposTabla;
    private List<TipoEvento> listaTiposEventos;
    private List<TipoEvento> listaTiposEventosTabla;
    private List<TipoComponente> listaTiposComponentes;
    private List<TipoComponente> listaTiposComponentesTabla;
    private int posicionTipoActivoTabla, posicionEstadoEquipoTabla, posicionTipoEventoTabla, posicionTipoComponenteTabla;
    private int tamTotalTipoActivo, tamTotalEstadoEquipo, tamTotalTipoEvento, tamTotalTipoComponente;
    private boolean bloquearPagSigTipoComponente, bloquearPagAntTipoComponente;
    private boolean bloquearPagSigTipoActivo, bloquearPagAntTipoActivo;
    private boolean bloquearPagSigTipoEvento, bloquearPagAntTipoEvento;
    private boolean bloquearPagSigEstadoEquipo, bloquearPagAntEstadoEquipo;

    public ControllerVariablesInventario() {
    }

    @PostConstruct
    public void init() {
    }

    public void iniciarInformacionTablas() {
        listaTiposActivos = gestionarVariableTiposActivosBO.consultarTiposActivosRegistrados();
        if (null != listaTiposActivos) {
            listaTiposActivosTabla = new ArrayList<TipoActivo>();
            tamTotalTipoActivo = listaTiposActivos.size();
        }
        posicionTipoActivoTabla = 0;
        cargarDatosTablaTipoActivo();
        listaEstadosEquipos = gestionarVariableEstadosEquiposBO.consultarEstadosEquiposRegistrados();
        if (null != listaEstadosEquipos) {
            listaEstadosEquiposTabla = new ArrayList<EstadoEquipo>();
            tamTotalEstadoEquipo = listaEstadosEquipos.size();
        }
        posicionEstadoEquipoTabla = 0;
        cargarDatosTablaEstadoEquipo();
        listaTiposEventos = gestionarVariableTiposEventosBO.consultarTiposEventosRegistrados();
        if (null != listaTiposEventos) {
            listaTiposEventosTabla = new ArrayList<TipoEvento>();
            tamTotalTipoEvento = listaTiposEventos.size();
        }
        posicionTipoEventoTabla = 0;
        cargarDatosTablaTipoEvento();
        listaTiposComponentes = gestionarVariableTiposComponentesBO.consultarTiposComponentesRegistrados();
        if (null != listaTiposComponentes) {
            listaTiposComponentesTabla = new ArrayList<TipoComponente>();
            tamTotalTipoComponente = listaTiposComponentes.size();
        }
        posicionTipoComponenteTabla = 0;
        cargarDatosTablaTipoComponente();
    }

    private void cargarDatosTablaTipoComponente() {
        if (tamTotalTipoComponente < 10) {
            for (int i = 0; i < tamTotalTipoComponente; i++) {
                listaTiposComponentesTabla.add(listaTiposComponentes.get(i));
            }
            bloquearPagSigTipoComponente = true;
            bloquearPagAntTipoComponente = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaTiposComponentesTabla.add(listaTiposComponentes.get(i));
            }
            bloquearPagSigTipoComponente = false;
            bloquearPagAntTipoComponente = true;
        }
    }

    private void cargarDatosTablaTipoEvento() {
        if (tamTotalTipoEvento < 10) {
            for (int i = 0; i < tamTotalTipoEvento; i++) {
                listaTiposEventosTabla.add(listaTiposEventos.get(i));
            }
            bloquearPagSigTipoEvento = true;
            bloquearPagAntTipoEvento = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaTiposEventosTabla.add(listaTiposEventos.get(i));
            }
            bloquearPagSigTipoEvento = false;
            bloquearPagAntTipoEvento = true;
        }
    }

    private void cargarDatosTablaTipoActivo() {
        if (tamTotalTipoActivo < 10) {
            for (int i = 0; i < tamTotalTipoActivo; i++) {
                listaTiposActivosTabla.add(listaTiposActivos.get(i));
            }
            bloquearPagSigTipoActivo = true;
            bloquearPagAntTipoActivo = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaTiposActivosTabla.add(listaTiposActivos.get(i));
            }
            bloquearPagSigTipoActivo = false;
            bloquearPagAntTipoActivo = true;
        }
    }

    private void cargarDatosTablaEstadoEquipo() {
        if (tamTotalEstadoEquipo < 10) {
            for (int i = 0; i < tamTotalEstadoEquipo; i++) {
                listaEstadosEquiposTabla.add(listaEstadosEquipos.get(i));
            }
            bloquearPagSigEstadoEquipo = true;
            bloquearPagAntEstadoEquipo = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEstadosEquiposTabla.add(listaEstadosEquipos.get(i));
            }
            bloquearPagSigEstadoEquipo = false;
            bloquearPagAntEstadoEquipo = true;
        }
    }

    public void limpiarInformacion() {
        listaTiposActivos = null;
        listaTiposEventos = null;
        listaEstadosEquipos = null;
        listaTiposComponentes = null;
        listaTiposComponentesTabla = null;
        listaEstadosEquiposTabla = null;
        listaTiposActivosTabla = null;
        listaTiposEventosTabla = null;
        posicionEstadoEquipoTabla = 0;
        posicionTipoComponenteTabla = 0;
        posicionTipoActivoTabla = 0;
        posicionTipoEventoTabla = 0;
        tamTotalEstadoEquipo = 0;
        tamTotalTipoActivo = 0;
        tamTotalTipoComponente = 0;
        tamTotalTipoEvento = 0;
        bloquearPagAntEstadoEquipo = true;
        bloquearPagAntTipoActivo = true;
        bloquearPagAntTipoEvento = true;
        bloquearPagAntTipoComponente = true;
        bloquearPagSigTipoComponente = true;
        bloquearPagSigEstadoEquipo = true;
        bloquearPagSigTipoActivo = true;
        bloquearPagSigTipoEvento = true;
    }

    public void cargarPaginaSiguienteTipoComponente() {
        listaTiposComponentesTabla = new ArrayList<TipoComponente>();
        posicionTipoComponenteTabla = posicionTipoComponenteTabla + 10;
        int diferencia = tamTotalTipoComponente - posicionTipoComponenteTabla;
        if (diferencia > 10) {
            for (int i = posicionTipoComponenteTabla; i < (posicionTipoComponenteTabla + 10); i++) {
                listaTiposComponentesTabla.add(listaTiposComponentes.get(i));
            }
            bloquearPagSigTipoComponente = false;
            bloquearPagAntTipoComponente = false;
        } else {
            for (int i = posicionTipoComponenteTabla; i < (posicionTipoComponenteTabla + diferencia); i++) {
                listaTiposComponentesTabla.add(listaTiposComponentes.get(i));
            }
            bloquearPagSigTipoComponente = true;
            bloquearPagAntTipoComponente = false;
        }
    }

    public void cargarPaginaAnteriorTipoComponente() {
        listaTiposComponentesTabla = new ArrayList<TipoComponente>();
        posicionTipoComponenteTabla = posicionTipoComponenteTabla - 10;
        int diferencia = tamTotalTipoComponente - posicionTipoComponenteTabla;
        if (diferencia == tamTotalTipoComponente) {
            for (int i = posicionTipoComponenteTabla; i < (posicionTipoComponenteTabla + 10); i++) {
                listaTiposComponentesTabla.add(listaTiposComponentes.get(i));
            }
            bloquearPagSigTipoComponente = false;
            bloquearPagAntTipoComponente = true;
        } else {
            for (int i = posicionTipoComponenteTabla; i < (posicionTipoComponenteTabla + 10); i++) {
                listaTiposComponentesTabla.add(listaTiposComponentes.get(i));
            }
            bloquearPagSigTipoComponente = false;
            bloquearPagAntTipoComponente = false;
        }
    }

    public void cargarPaginaSiguienteTipoActivo() {
        listaTiposActivosTabla = new ArrayList<TipoActivo>();
        posicionTipoActivoTabla = posicionTipoActivoTabla + 10;
        int diferencia = tamTotalTipoActivo - posicionTipoActivoTabla;
        if (diferencia > 10) {
            for (int i = posicionTipoActivoTabla; i < (posicionTipoActivoTabla + 10); i++) {
                listaTiposActivosTabla.add(listaTiposActivos.get(i));
            }
            bloquearPagSigTipoActivo = false;
            bloquearPagAntTipoActivo = false;
        } else {
            for (int i = posicionTipoActivoTabla; i < (posicionTipoActivoTabla + diferencia); i++) {
                listaTiposActivosTabla.add(listaTiposActivos.get(i));
            }
            bloquearPagSigTipoActivo = true;
            bloquearPagAntTipoActivo = false;
        }
    }

    public void cargarPaginaAnteriorTipoActivo() {
        listaTiposActivosTabla = new ArrayList<TipoActivo>();
        posicionTipoActivoTabla = posicionTipoActivoTabla - 10;
        int diferencia = tamTotalTipoActivo - posicionTipoActivoTabla;
        if (diferencia == tamTotalTipoActivo) {
            for (int i = posicionTipoActivoTabla; i < (posicionTipoActivoTabla + 10); i++) {
                listaTiposActivosTabla.add(listaTiposActivos.get(i));
            }
            bloquearPagSigTipoActivo = false;
            bloquearPagAntTipoActivo = true;
        } else {
            for (int i = posicionTipoActivoTabla; i < (posicionTipoActivoTabla + 10); i++) {
                listaTiposActivosTabla.add(listaTiposActivos.get(i));
            }
            bloquearPagSigTipoActivo = false;
            bloquearPagAntTipoActivo = false;
        }
    }

    public void cargarPaginaSiguienteEstadoEquipo() {
        listaEstadosEquiposTabla = new ArrayList<EstadoEquipo>();
        posicionEstadoEquipoTabla = posicionEstadoEquipoTabla + 10;
        int diferencia = tamTotalEstadoEquipo - posicionEstadoEquipoTabla;
        if (diferencia > 10) {
            for (int i = posicionEstadoEquipoTabla; i < (posicionEstadoEquipoTabla + 10); i++) {
                listaEstadosEquiposTabla.add(listaEstadosEquipos.get(i));
            }
            bloquearPagSigEstadoEquipo = false;
            bloquearPagAntEstadoEquipo = false;
        } else {
            for (int i = posicionEstadoEquipoTabla; i < (posicionEstadoEquipoTabla + diferencia); i++) {
                listaEstadosEquiposTabla.add(listaEstadosEquipos.get(i));
            }
            bloquearPagSigEstadoEquipo = true;
            bloquearPagAntEstadoEquipo = false;
        }
    }

    public void cargarPaginaAnteriorEstadoEquipo() {
        listaEstadosEquiposTabla = new ArrayList<EstadoEquipo>();
        posicionEstadoEquipoTabla = posicionEstadoEquipoTabla - 10;
        int diferencia = tamTotalEstadoEquipo - posicionEstadoEquipoTabla;
        if (diferencia == tamTotalEstadoEquipo) {
            for (int i = posicionEstadoEquipoTabla; i < (posicionEstadoEquipoTabla + 10); i++) {
                listaEstadosEquiposTabla.add(listaEstadosEquipos.get(i));
            }
            bloquearPagSigEstadoEquipo = false;
            bloquearPagAntEstadoEquipo = true;
        } else {
            for (int i = posicionEstadoEquipoTabla; i < (posicionEstadoEquipoTabla + 10); i++) {
                listaEstadosEquiposTabla.add(listaEstadosEquipos.get(i));
            }
            bloquearPagSigEstadoEquipo = false;
            bloquearPagAntEstadoEquipo = false;
        }
    }

    public void cargarPaginaSiguienteTipoEvento() {
        listaTiposEventosTabla = new ArrayList<TipoEvento>();
        posicionTipoEventoTabla = posicionTipoEventoTabla + 10;
        int diferencia = tamTotalTipoEvento - posicionTipoEventoTabla;
        if (diferencia > 10) {
            for (int i = posicionTipoEventoTabla; i < (posicionTipoEventoTabla + 10); i++) {
                listaTiposEventosTabla.add(listaTiposEventos.get(i));
            }
            bloquearPagSigTipoEvento = false;
            bloquearPagAntTipoEvento = false;
        } else {
            for (int i = posicionTipoEventoTabla; i < (posicionTipoEventoTabla + diferencia); i++) {
                listaTiposEventosTabla.add(listaTiposEventos.get(i));
            }
            bloquearPagSigTipoEvento = true;
            bloquearPagAntTipoEvento = false;
        }
    }

    public void cargarPaginaAnteriorTipoEvento() {
        listaTiposEventosTabla = new ArrayList<TipoEvento>();
        posicionTipoEventoTabla = posicionTipoEventoTabla - 10;
        int diferencia = tamTotalTipoEvento - posicionTipoEventoTabla;
        if (diferencia == tamTotalTipoEvento) {
            for (int i = posicionTipoEventoTabla; i < (posicionTipoEventoTabla + 10); i++) {
                listaTiposEventosTabla.add(listaTiposEventos.get(i));
            }
            bloquearPagSigTipoEvento = false;
            bloquearPagAntTipoEvento = true;
        } else {
            for (int i = posicionTipoEventoTabla; i < (posicionTipoEventoTabla + 10); i++) {
                listaTiposEventosTabla.add(listaTiposEventos.get(i));
            }
            bloquearPagSigTipoEvento = false;
            bloquearPagAntTipoEvento = false;
        }
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

    public List<TipoEvento> getListaTiposEventos() {
        return listaTiposEventos;
    }

    public void setListaTiposEventos(List<TipoEvento> listaTiposEventos) {
        this.listaTiposEventos = listaTiposEventos;
    }

    public List<TipoActivo> getListaTiposActivosTabla() {
        return listaTiposActivosTabla;
    }

    public void setListaTiposActivosTabla(List<TipoActivo> listaTiposActivosTabla) {
        this.listaTiposActivosTabla = listaTiposActivosTabla;
    }

    public List<EstadoEquipo> getListaEstadosEquiposTabla() {
        return listaEstadosEquiposTabla;
    }

    public void setListaEstadosEquiposTabla(List<EstadoEquipo> listaEstadosEquiposTabla) {
        this.listaEstadosEquiposTabla = listaEstadosEquiposTabla;
    }

    public List<TipoEvento> getListaTiposEventosTabla() {
        return listaTiposEventosTabla;
    }

    public void setListaTiposEventosTabla(List<TipoEvento> listaTiposEventosTabla) {
        this.listaTiposEventosTabla = listaTiposEventosTabla;
    }

    public boolean isBloquearPagSigTipoActivo() {
        return bloquearPagSigTipoActivo;
    }

    public void setBloquearPagSigTipoActivo(boolean bloquearPagSigTipoActivo) {
        this.bloquearPagSigTipoActivo = bloquearPagSigTipoActivo;
    }

    public boolean isBloquearPagAntTipoActivo() {
        return bloquearPagAntTipoActivo;
    }

    public void setBloquearPagAntTipoActivo(boolean bloquearPagAntTipoActivo) {
        this.bloquearPagAntTipoActivo = bloquearPagAntTipoActivo;
    }

    public boolean isBloquearPagSigTipoEvento() {
        return bloquearPagSigTipoEvento;
    }

    public void setBloquearPagSigTipoEvento(boolean bloquearPagSigTipoEvento) {
        this.bloquearPagSigTipoEvento = bloquearPagSigTipoEvento;
    }

    public boolean isBloquearPagAntTipoEvento() {
        return bloquearPagAntTipoEvento;
    }

    public void setBloquearPagAntTipoEvento(boolean bloquearPagAntTipoEvento) {
        this.bloquearPagAntTipoEvento = bloquearPagAntTipoEvento;
    }

    public boolean isBloquearPagSigEstadoEquipo() {
        return bloquearPagSigEstadoEquipo;
    }

    public void setBloquearPagSigEstadoEquipo(boolean bloquearPagSigEstadoEquipo) {
        this.bloquearPagSigEstadoEquipo = bloquearPagSigEstadoEquipo;
    }

    public boolean isBloquearPagAntEstadoEquipo() {
        return bloquearPagAntEstadoEquipo;
    }

    public void setBloquearPagAntEstadoEquipo(boolean bloquearPagAntEstadoEquipo) {
        this.bloquearPagAntEstadoEquipo = bloquearPagAntEstadoEquipo;
    }

    public List<TipoComponente> getListaTiposComponentes() {
        return listaTiposComponentes;
    }

    public void setListaTiposComponentes(List<TipoComponente> listaTiposComponentes) {
        this.listaTiposComponentes = listaTiposComponentes;
    }

    public List<TipoComponente> getListaTiposComponentesTabla() {
        return listaTiposComponentesTabla;
    }

    public void setListaTiposComponentesTabla(List<TipoComponente> listaTiposComponentesTabla) {
        this.listaTiposComponentesTabla = listaTiposComponentesTabla;
    }

    public boolean isBloquearPagSigTipoComponente() {
        return bloquearPagSigTipoComponente;
    }

    public void setBloquearPagSigTipoComponente(boolean bloquearPagSigTipoComponente) {
        this.bloquearPagSigTipoComponente = bloquearPagSigTipoComponente;
    }

    public boolean isBloquearPagAntTipoComponente() {
        return bloquearPagAntTipoComponente;
    }

    public void setBloquearPagAntTipoComponente(boolean bloquearPagAntTipoComponente) {
        this.bloquearPagAntTipoComponente = bloquearPagAntTipoComponente;
    }

}
