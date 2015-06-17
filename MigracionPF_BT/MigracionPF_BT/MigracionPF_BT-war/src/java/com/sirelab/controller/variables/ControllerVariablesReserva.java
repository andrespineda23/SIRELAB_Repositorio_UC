/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariableEstadosReservasBOInterface;
import com.sirelab.bo.interfacebo.GestionarVariableHorariosAtencionBOInterface;
import com.sirelab.bo.interfacebo.GestionarVariablePeriodosAcademicosBOInterface;
import com.sirelab.bo.interfacebo.GestionarVariableTiposReservasBOInterface;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.HorarioAtencion;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.TipoReserva;
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
public class ControllerVariablesReserva implements Serializable {

    @EJB
    GestionarVariableHorariosAtencionBOInterface gestionarVariableHorariosAtencionBO;
    @EJB
    GestionarVariablePeriodosAcademicosBOInterface gestionarVariablePeriodosAcademicosBO;
    @EJB
    GestionarVariableTiposReservasBOInterface gestionarVariableTiposReservasBO;
    @EJB
    GestionarVariableEstadosReservasBOInterface gestionarVariableEstadosReservasBO;

    private List<HorarioAtencion> listaHorariosAtencion;
    private List<HorarioAtencion> listaHorariosAtencionTabla;
    private List<PeriodoAcademico> listaPeriodosAcademicos;
    private List<PeriodoAcademico> listaPeriodosAcademicosTabla;
    private List<TipoReserva> listaTipoReserva;
    private List<TipoReserva> listaTipoReservaTabla;
    private List<EstadoReserva> listaEstadoReserva;
    private List<EstadoReserva> listaEstadoReservaTabla;
    private int posicionHorarioAtencionTabla, posicionPeriodoAcademicoTabla, posicionTipoReservaTabla, posicionEstadoReservaTabla;
    private int tamTotalHorarioAtencion, tamTotalPeriodoAcademico, tamTotalTipoReserva, tamTotalEstadoReserva;
    private boolean bloquearPagSigHorarioAtencion, bloquearPagAntHorarioAtencion;
    private boolean bloquearPagSigPeriodoAcademico, bloquearPagAntPeriodoAcademico;
    private boolean bloquearPagSigTipoReserva, bloquearPagAntTipoReserva;
    private boolean bloquearPagSigEstadoReserva, bloquearPagAntEstadoReserva;

    public ControllerVariablesReserva() {
    }

    @PostConstruct
    public void init() {
    }

    public void iniciarInformacionTablas() {
        listaHorariosAtencion = gestionarVariableHorariosAtencionBO.consultarPeriodosAcademicos();
        if (null != listaHorariosAtencion) {
            listaHorariosAtencionTabla = new ArrayList<HorarioAtencion>();
            tamTotalHorarioAtencion = listaHorariosAtencion.size();
        }
        posicionHorarioAtencionTabla = 0;
        cargarDatosTablaHorarioAtencion();
        listaPeriodosAcademicos = gestionarVariablePeriodosAcademicosBO.consultarPeriodosAcademicos();
        if (null != listaPeriodosAcademicos) {
            listaPeriodosAcademicosTabla = new ArrayList<PeriodoAcademico>();
            tamTotalPeriodoAcademico = listaPeriodosAcademicos.size();
        }
        posicionPeriodoAcademicoTabla = 0;
        cargarDatosTablaPeriodoAcademico();
        listaTipoReserva = gestionarVariableTiposReservasBO.consultarTiposReservasRegistrados();
        if (null != listaTipoReserva) {
            listaTipoReservaTabla = new ArrayList<TipoReserva>();
            tamTotalTipoReserva = listaTipoReserva.size();
        }
        posicionTipoReservaTabla = 0;
        cargarDatosTablaTipoReserva();
        listaEstadoReserva = gestionarVariableEstadosReservasBO.consultarEstadosReservasRegistrados();
        if (null != listaEstadoReserva) {
            listaEstadoReservaTabla = new ArrayList<EstadoReserva>();
            tamTotalEstadoReserva = listaEstadoReserva.size();
        }
        posicionEstadoReservaTabla = 0;
        cargarDatosTablaEstadoReserva();
    }

    private void cargarDatosTablaHorarioAtencion() {
        if (tamTotalHorarioAtencion < 10) {
            for (int i = 0; i < tamTotalHorarioAtencion; i++) {
                listaHorariosAtencionTabla.add(listaHorariosAtencion.get(i));
            }
            bloquearPagSigHorarioAtencion = true;
            bloquearPagAntHorarioAtencion = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaHorariosAtencionTabla.add(listaHorariosAtencion.get(i));
            }
            bloquearPagSigHorarioAtencion = false;
            bloquearPagAntHorarioAtencion = true;
        }
    }

    private void cargarDatosTablaEstadoReserva() {
        if (tamTotalEstadoReserva < 10) {
            for (int i = 0; i < tamTotalEstadoReserva; i++) {
                listaEstadoReservaTabla.add(listaEstadoReserva.get(i));
            }
            bloquearPagSigEstadoReserva = true;
            bloquearPagAntEstadoReserva = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEstadoReservaTabla.add(listaEstadoReserva.get(i));
            }
            bloquearPagSigEstadoReserva = false;
            bloquearPagAntEstadoReserva = true;
        }
    }

    private void cargarDatosTablaTipoReserva() {
        if (tamTotalTipoReserva < 10) {
            for (int i = 0; i < tamTotalTipoReserva; i++) {
                listaTipoReservaTabla.add(listaTipoReserva.get(i));
            }
            bloquearPagSigTipoReserva = true;
            bloquearPagAntTipoReserva = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaTipoReservaTabla.add(listaTipoReserva.get(i));
            }
            bloquearPagSigTipoReserva = false;
            bloquearPagAntTipoReserva = true;
        }
    }

    private void cargarDatosTablaPeriodoAcademico() {
        if (tamTotalPeriodoAcademico < 10) {
            for (int i = 0; i < tamTotalPeriodoAcademico; i++) {
                listaPeriodosAcademicosTabla.add(listaPeriodosAcademicos.get(i));
            }
            bloquearPagSigPeriodoAcademico = true;
            bloquearPagAntPeriodoAcademico = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaPeriodosAcademicosTabla.add(listaPeriodosAcademicos.get(i));
            }
            bloquearPagSigPeriodoAcademico = false;
            bloquearPagAntPeriodoAcademico = true;
        }
    }

    public void limpiarInformacion() {
        listaHorariosAtencion = null;
        listaPeriodosAcademicos = null;
        listaEstadoReserva = null;
        listaTipoReserva = null;
        listaPeriodosAcademicosTabla = null;
        listaEstadoReservaTabla = null;
        listaTipoReservaTabla = null;
        listaHorariosAtencionTabla = null;
        posicionHorarioAtencionTabla = 0;
        posicionEstadoReservaTabla = 0;
        posicionPeriodoAcademicoTabla = 0;
        posicionTipoReservaTabla = 0;
        tamTotalEstadoReserva = 0;
        tamTotalHorarioAtencion = 0;
        tamTotalPeriodoAcademico = 0;
        tamTotalTipoReserva = 0;
        bloquearPagAntEstadoReserva = true;
        bloquearPagAntPeriodoAcademico = true;
        bloquearPagAntTipoReserva = true;
        bloquearPagAntHorarioAtencion = true;
        bloquearPagSigEstadoReserva = true;
        bloquearPagSigHorarioAtencion = true;
        bloquearPagSigPeriodoAcademico = true;
        bloquearPagSigTipoReserva = true;
    }

    public void cargarPaginaSiguienteHorarioAtencion() {
        listaHorariosAtencionTabla = new ArrayList<HorarioAtencion>();
        posicionHorarioAtencionTabla = posicionHorarioAtencionTabla + 10;
        int diferencia = tamTotalHorarioAtencion - posicionHorarioAtencionTabla;
        if (diferencia > 10) {
            for (int i = posicionHorarioAtencionTabla; i < (posicionHorarioAtencionTabla + 10); i++) {
                listaHorariosAtencionTabla.add(listaHorariosAtencion.get(i));
            }
            bloquearPagSigHorarioAtencion = false;
            bloquearPagAntHorarioAtencion = false;
        } else {
            for (int i = posicionHorarioAtencionTabla; i < (posicionHorarioAtencionTabla + diferencia); i++) {
                listaHorariosAtencionTabla.add(listaHorariosAtencion.get(i));
            }
            bloquearPagSigHorarioAtencion = true;
            bloquearPagAntHorarioAtencion = false;
        }
    }

    public void cargarPaginaAnteriorHorarioAtencion() {
        listaHorariosAtencionTabla = new ArrayList<HorarioAtencion>();
        posicionHorarioAtencionTabla = posicionHorarioAtencionTabla - 10;
        int diferencia = tamTotalHorarioAtencion - posicionHorarioAtencionTabla;
        if (diferencia == tamTotalHorarioAtencion) {
            for (int i = posicionHorarioAtencionTabla; i < (posicionHorarioAtencionTabla + 10); i++) {
                listaHorariosAtencionTabla.add(listaHorariosAtencion.get(i));
            }
            bloquearPagSigHorarioAtencion = false;
            bloquearPagAntHorarioAtencion = true;
        } else {
            for (int i = posicionHorarioAtencionTabla; i < (posicionHorarioAtencionTabla + 10); i++) {
                listaHorariosAtencionTabla.add(listaHorariosAtencion.get(i));
            }
            bloquearPagSigHorarioAtencion = false;
            bloquearPagAntHorarioAtencion = false;
        }
    }

    public void cargarPaginaSiguientePeriodoAcademico() {
        listaPeriodosAcademicosTabla = new ArrayList<PeriodoAcademico>();
        posicionPeriodoAcademicoTabla = posicionPeriodoAcademicoTabla + 10;
        int diferencia = tamTotalPeriodoAcademico - posicionPeriodoAcademicoTabla;
        if (diferencia > 10) {
            for (int i = posicionPeriodoAcademicoTabla; i < (posicionPeriodoAcademicoTabla + 10); i++) {
                listaPeriodosAcademicosTabla.add(listaPeriodosAcademicos.get(i));
            }
            bloquearPagSigPeriodoAcademico = false;
            bloquearPagAntPeriodoAcademico = false;
        } else {
            for (int i = posicionPeriodoAcademicoTabla; i < (posicionPeriodoAcademicoTabla + diferencia); i++) {
                listaPeriodosAcademicosTabla.add(listaPeriodosAcademicos.get(i));
            }
            bloquearPagSigPeriodoAcademico = true;
            bloquearPagAntPeriodoAcademico = false;
        }
    }

    public void cargarPaginaAnteriorPeriodoAcademico() {
        listaPeriodosAcademicosTabla = new ArrayList<PeriodoAcademico>();
        posicionPeriodoAcademicoTabla = posicionPeriodoAcademicoTabla - 10;
        int diferencia = tamTotalPeriodoAcademico - posicionPeriodoAcademicoTabla;
        if (diferencia == tamTotalPeriodoAcademico) {
            for (int i = posicionPeriodoAcademicoTabla; i < (posicionPeriodoAcademicoTabla + 10); i++) {
                listaPeriodosAcademicosTabla.add(listaPeriodosAcademicos.get(i));
            }
            bloquearPagSigPeriodoAcademico = false;
            bloquearPagAntPeriodoAcademico = true;
        } else {
            for (int i = posicionPeriodoAcademicoTabla; i < (posicionPeriodoAcademicoTabla + 10); i++) {
                listaPeriodosAcademicosTabla.add(listaPeriodosAcademicos.get(i));
            }
            bloquearPagSigPeriodoAcademico = false;
            bloquearPagAntPeriodoAcademico = false;
        }
    }

    public void cargarPaginaSiguienteTipoReserva() {
        listaTipoReservaTabla = new ArrayList<TipoReserva>();
        posicionTipoReservaTabla = posicionTipoReservaTabla + 10;
        int diferencia = tamTotalTipoReserva - posicionTipoReservaTabla;
        if (diferencia > 10) {
            for (int i = posicionTipoReservaTabla; i < (posicionTipoReservaTabla + 10); i++) {
                listaTipoReservaTabla.add(listaTipoReserva.get(i));
            }
            bloquearPagSigTipoReserva = false;
            bloquearPagAntTipoReserva = false;
        } else {
            for (int i = posicionTipoReservaTabla; i < (posicionTipoReservaTabla + diferencia); i++) {
                listaTipoReservaTabla.add(listaTipoReserva.get(i));
            }
            bloquearPagSigTipoReserva = true;
            bloquearPagAntTipoReserva = false;
        }
    }

    public void cargarPaginaAnteriorTipoReserva() {
        listaTipoReservaTabla = new ArrayList<TipoReserva>();
        posicionTipoReservaTabla = posicionTipoReservaTabla - 10;
        int diferencia = tamTotalTipoReserva - posicionTipoReservaTabla;
        if (diferencia == tamTotalTipoReserva) {
            for (int i = posicionTipoReservaTabla; i < (posicionTipoReservaTabla + 10); i++) {
                listaTipoReservaTabla.add(listaTipoReserva.get(i));
            }
            bloquearPagSigTipoReserva = false;
            bloquearPagAntTipoReserva = true;
        } else {
            for (int i = posicionTipoReservaTabla; i < (posicionTipoReservaTabla + 10); i++) {
                listaTipoReservaTabla.add(listaTipoReserva.get(i));
            }
            bloquearPagSigTipoReserva = false;
            bloquearPagAntTipoReserva = false;
        }
    }

    public void cargarPaginaSiguienteEstadoReserva() {
        listaEstadoReservaTabla = new ArrayList<EstadoReserva>();
        posicionEstadoReservaTabla = posicionEstadoReservaTabla + 10;
        int diferencia = tamTotalEstadoReserva - posicionEstadoReservaTabla;
        if (diferencia > 10) {
            for (int i = posicionEstadoReservaTabla; i < (posicionEstadoReservaTabla + 10); i++) {
                listaEstadoReservaTabla.add(listaEstadoReserva.get(i));
            }
            bloquearPagSigEstadoReserva = false;
            bloquearPagAntEstadoReserva = false;
        } else {
            for (int i = posicionEstadoReservaTabla; i < (posicionEstadoReservaTabla + diferencia); i++) {
                listaEstadoReservaTabla.add(listaEstadoReserva.get(i));
            }
            bloquearPagSigEstadoReserva = true;
            bloquearPagAntEstadoReserva = false;
        }
    }

    public void cargarPaginaAnteriorEstadoReserva() {
        listaEstadoReservaTabla = new ArrayList<EstadoReserva>();
        posicionEstadoReservaTabla = posicionEstadoReservaTabla - 10;
        int diferencia = tamTotalEstadoReserva - posicionEstadoReservaTabla;
        if (diferencia == tamTotalEstadoReserva) {
            for (int i = posicionEstadoReservaTabla; i < (posicionEstadoReservaTabla + 10); i++) {
                listaEstadoReservaTabla.add(listaEstadoReserva.get(i));
            }
            bloquearPagSigEstadoReserva = false;
            bloquearPagAntEstadoReserva = true;
        } else {
            for (int i = posicionEstadoReservaTabla; i < (posicionEstadoReservaTabla + 10); i++) {
                listaEstadoReservaTabla.add(listaEstadoReserva.get(i));
            }
            bloquearPagSigEstadoReserva = false;
            bloquearPagAntEstadoReserva = false;
        }
    }

    //GET-SET
    public List<HorarioAtencion> getListaHorariosAtencion() {
        return listaHorariosAtencion;
    }

    public void setListaHorariosAtencion(List<HorarioAtencion> listaHorariosAtencion) {
        this.listaHorariosAtencion = listaHorariosAtencion;
    }

    public List<PeriodoAcademico> getListaPeriodosAcademicos() {
        return listaPeriodosAcademicos;
    }

    public void setListaPeriodosAcademicos(List<PeriodoAcademico> listaPeriodosAcademicos) {
        this.listaPeriodosAcademicos = listaPeriodosAcademicos;
    }

    public List<TipoReserva> getListaTipoReserva() {
        return listaTipoReserva;
    }

    public void setListaTipoReserva(List<TipoReserva> listaTipoReserva) {
        this.listaTipoReserva = listaTipoReserva;
    }

    public List<EstadoReserva> getListaEstadoReserva() {
        return listaEstadoReserva;
    }

    public void setListaEstadoReserva(List<EstadoReserva> listaEstadoReserva) {
        this.listaEstadoReserva = listaEstadoReserva;
    }

    public List<PeriodoAcademico> getListaPeriodosAcademicosTabla() {
        return listaPeriodosAcademicosTabla;
    }

    public void setListaPeriodosAcademicosTabla(List<PeriodoAcademico> listaPeriodosAcademicosTabla) {
        this.listaPeriodosAcademicosTabla = listaPeriodosAcademicosTabla;
    }

    public List<TipoReserva> getListaTipoReservaTabla() {
        return listaTipoReservaTabla;
    }

    public void setListaTipoReservaTabla(List<TipoReserva> listaTipoReservaTabla) {
        this.listaTipoReservaTabla = listaTipoReservaTabla;
    }

    public List<EstadoReserva> getListaEstadoReservaTabla() {
        return listaEstadoReservaTabla;
    }

    public void setListaEstadoReservaTabla(List<EstadoReserva> listaEstadoReservaTabla) {
        this.listaEstadoReservaTabla = listaEstadoReservaTabla;
    }

    public boolean isBloquearPagSigPeriodoAcademico() {
        return bloquearPagSigPeriodoAcademico;
    }

    public void setBloquearPagSigPeriodoAcademico(boolean bloquearPagSigPeriodoAcademico) {
        this.bloquearPagSigPeriodoAcademico = bloquearPagSigPeriodoAcademico;
    }

    public boolean isBloquearPagAntPeriodoAcademico() {
        return bloquearPagAntPeriodoAcademico;
    }

    public void setBloquearPagAntPeriodoAcademico(boolean bloquearPagAntPeriodoAcademico) {
        this.bloquearPagAntPeriodoAcademico = bloquearPagAntPeriodoAcademico;
    }

    public boolean isBloquearPagSigTipoReserva() {
        return bloquearPagSigTipoReserva;
    }

    public void setBloquearPagSigTipoReserva(boolean bloquearPagSigTipoReserva) {
        this.bloquearPagSigTipoReserva = bloquearPagSigTipoReserva;
    }

    public boolean isBloquearPagAntTipoReserva() {
        return bloquearPagAntTipoReserva;
    }

    public void setBloquearPagAntTipoReserva(boolean bloquearPagAntTipoReserva) {
        this.bloquearPagAntTipoReserva = bloquearPagAntTipoReserva;
    }

    public boolean isBloquearPagSigEstadoReserva() {
        return bloquearPagSigEstadoReserva;
    }

    public void setBloquearPagSigEstadoReserva(boolean bloquearPagSigEstadoReserva) {
        this.bloquearPagSigEstadoReserva = bloquearPagSigEstadoReserva;
    }

    public boolean isBloquearPagAntEstadoReserva() {
        return bloquearPagAntEstadoReserva;
    }

    public void setBloquearPagAntEstadoReserva(boolean bloquearPagAntEstadoReserva) {
        this.bloquearPagAntEstadoReserva = bloquearPagAntEstadoReserva;
    }

    public List<HorarioAtencion> getListaHorariosAtencionTabla() {
        return listaHorariosAtencionTabla;
    }

    public void setListaHorariosAtencionTabla(List<HorarioAtencion> listaHorariosAtencionTabla) {
        this.listaHorariosAtencionTabla = listaHorariosAtencionTabla;
    }

    public boolean isBloquearPagSigHorarioAtencion() {
        return bloquearPagSigHorarioAtencion;
    }

    public void setBloquearPagSigHorarioAtencion(boolean bloquearPagSigHorarioAtencion) {
        this.bloquearPagSigHorarioAtencion = bloquearPagSigHorarioAtencion;
    }

    public boolean isBloquearPagAntHorarioAtencion() {
        return bloquearPagAntHorarioAtencion;
    }

    public void setBloquearPagAntHorarioAtencion(boolean bloquearPagAntHorarioAtencion) {
        this.bloquearPagAntHorarioAtencion = bloquearPagAntHorarioAtencion;
    }

}
