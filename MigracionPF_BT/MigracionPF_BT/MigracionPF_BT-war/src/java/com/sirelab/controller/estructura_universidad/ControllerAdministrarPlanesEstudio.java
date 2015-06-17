/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarPlanesEstudiosBOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarPlanesEstudio implements Serializable {

    @EJB
    GestionarPlanesEstudiosBOInterface gestionarPlanesEstudiosBO;

    private String parametroNombre, parametroCodigo;
    private List<Facultad> listaFacultades;
    private Facultad parametroFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private List<Carrera> listaCarreras;
    private Carrera parametroCarrera;
    private boolean activarDepartamento;
    private boolean activarCarrera;
    private boolean activarNuevoDepartamento;
    private boolean activarNuevoCarrera;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<PlanEstudios> listaPlanesEstudios;
    private List<PlanEstudios> listaPlanesEstudiosTabla;
    private int posicionPlanEstudioTabla;
    private int tamTotalPlanEstudio;
    private boolean bloquearPagSigPlanEstudio, bloquearPagAntPlanEstudio;
    //
    private String altoTabla;

    public ControllerAdministrarPlanesEstudio() {
    }

    @PostConstruct
    public void init() {
        activarDepartamento = true;
        activarCarrera = true;
        activarNuevoDepartamento = true;
        activarNuevoCarrera = true;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroFacultad = new Facultad();
        parametroDepartamento = new Departamento();
        parametroCarrera = new Carrera();
        altoTabla = "150";
        inicializarFiltros();
        listaDepartamentos = null;
        listaCarreras = null;
        listaPlanesEstudiosTabla = null;
        listaPlanesEstudios = null;
        posicionPlanEstudioTabla = 0;
        tamTotalPlanEstudio = 0;
        bloquearPagAntPlanEstudio = true;
        bloquearPagSigPlanEstudio = true;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroCarrera", null);
        filtros.put("parametroFacultad", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty())) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if (Utilidades.validarNulo(parametroFacultad)) {
            if (parametroFacultad.getIdfacultad() != null) {
                filtros.put("parametroFacultad", parametroFacultad.getIdfacultad().toString());
            }
        }
        if (Utilidades.validarNulo(parametroDepartamento)) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
            }
        }
        if (Utilidades.validarNulo(parametroCarrera)) {
            if (parametroCarrera.getIdcarrera() != null) {
                filtros.put("parametroCarrera", parametroCarrera.getIdcarrera().toString());
            }
        }
    }

    public void buscarPlanesEstudiosPorParametros() {
        try {
            inicializarFiltros();
            listaPlanesEstudios = null;
            listaPlanesEstudios = gestionarPlanesEstudiosBO.consultarPlanesEstudiosPorParametro(filtros);
            if (listaPlanesEstudios != null) {
                if (listaPlanesEstudios.size() > 0) {
                    activarExport = false;
                    listaPlanesEstudiosTabla = new ArrayList<PlanEstudios>();
                    tamTotalPlanEstudio = listaPlanesEstudios.size();
                    posicionPlanEstudioTabla = 0;
                    cargarDatosTablaPlanEstudio();
                } else {
                    activarExport = true;
                    listaPlanesEstudiosTabla = null;
                    tamTotalPlanEstudio = 0;
                    posicionPlanEstudioTabla = 0;
                    bloquearPagAntPlanEstudio = true;
                    bloquearPagSigPlanEstudio = true;
                }
            } else {
                listaPlanesEstudiosTabla = null;
                tamTotalPlanEstudio = 0;
                posicionPlanEstudioTabla = 0;
                bloquearPagAntPlanEstudio = true;
                bloquearPagSigPlanEstudio = true;
            }
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarPlanesEstudios buscarPlanesEstudiosPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaPlanEstudio() {
        if (tamTotalPlanEstudio < 10) {
            for (int i = 0; i < tamTotalPlanEstudio; i++) {
                listaPlanesEstudiosTabla.add(listaPlanesEstudios.get(i));
            }
            bloquearPagSigPlanEstudio = true;
            bloquearPagAntPlanEstudio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaPlanesEstudiosTabla.add(listaPlanesEstudios.get(i));
            }
            bloquearPagSigPlanEstudio = false;
            bloquearPagAntPlanEstudio = true;
        }
    }

    public void cargarPaginaSiguientePlanEstudio() {
        listaPlanesEstudiosTabla = new ArrayList<PlanEstudios>();
        posicionPlanEstudioTabla = posicionPlanEstudioTabla + 10;
        int diferencia = tamTotalPlanEstudio - posicionPlanEstudioTabla;
        if (diferencia > 10) {
            for (int i = posicionPlanEstudioTabla; i < (posicionPlanEstudioTabla + 10); i++) {
                listaPlanesEstudiosTabla.add(listaPlanesEstudios.get(i));
            }
            bloquearPagSigPlanEstudio = false;
            bloquearPagAntPlanEstudio = false;
        } else {
            for (int i = posicionPlanEstudioTabla; i < (posicionPlanEstudioTabla + diferencia); i++) {
                listaPlanesEstudiosTabla.add(listaPlanesEstudios.get(i));
            }
            bloquearPagSigPlanEstudio = true;
            bloquearPagAntPlanEstudio = false;
        }
    }

    public void cargarPaginaAnteriorPlanEstudio() {
        listaPlanesEstudiosTabla = new ArrayList<PlanEstudios>();
        posicionPlanEstudioTabla = posicionPlanEstudioTabla - 10;
        int diferencia = tamTotalPlanEstudio - posicionPlanEstudioTabla;
        if (diferencia == tamTotalPlanEstudio) {
            for (int i = posicionPlanEstudioTabla; i < (posicionPlanEstudioTabla + 10); i++) {
                listaPlanesEstudiosTabla.add(listaPlanesEstudios.get(i));
            }
            bloquearPagSigPlanEstudio = false;
            bloquearPagAntPlanEstudio = true;
        } else {
            for (int i = posicionPlanEstudioTabla; i < (posicionPlanEstudioTabla + 10); i++) {
                listaPlanesEstudiosTabla.add(listaPlanesEstudios.get(i));
            }
            bloquearPagSigPlanEstudio = false;
            bloquearPagAntPlanEstudio = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarDepartamento = true;
        activarCarrera = true;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroDepartamento = new Departamento();
        parametroFacultad = new Facultad();
        parametroCarrera = new Carrera();
        listaDepartamentos = null;
        listaCarreras = null;
        listaPlanesEstudios = null;
        listaPlanesEstudiosTabla = null;
        posicionPlanEstudioTabla = 0;
        tamTotalPlanEstudio = 0;
        bloquearPagAntPlanEstudio = true;
        bloquearPagSigPlanEstudio = true;
        inicializarFiltros();
    }

    public void actualizarFacultades() {
        if (Utilidades.validarNulo(parametroFacultad)) {
            parametroDepartamento = new Departamento();
            listaDepartamentos = gestionarPlanesEstudiosBO.consultarDepartamentosPorIDFacultad(parametroFacultad.getIdfacultad());
            activarDepartamento = false;
            activarCarrera = true;
            listaCarreras = null;
            parametroCarrera = new Carrera();
        } else {
            parametroDepartamento = new Departamento();
            listaDepartamentos = null;
            activarDepartamento = true;
            activarCarrera = true;
            listaCarreras = null;
            parametroCarrera = new Carrera();
        }
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(parametroDepartamento)) {
            parametroCarrera = new Carrera();
            listaCarreras = gestionarPlanesEstudiosBO.consultarCarrerasPorIDDepartamento(parametroDepartamento.getIddepartamento());
            activarCarrera = false;

        } else {
            activarCarrera = true;
            listaCarreras = null;
            parametroCarrera = new Carrera();
        }
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "Gestionar_PlanesEstudios_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Gestionar_PlanesEstudios_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    // GET - SET
    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
    }

    public String getParametroCodigo() {
        return parametroCodigo;
    }

    public void setParametroCodigo(String parametroCodigo) {
        this.parametroCodigo = parametroCodigo;
    }

    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = gestionarPlanesEstudiosBO.consultarFacultadesRegistradas();
        }
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Facultad getParametroFacultad() {
        return parametroFacultad;
    }

    public void setParametroFacultad(Facultad parametroFacultad) {
        this.parametroFacultad = parametroFacultad;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getParametroDepartamento() {
        return parametroDepartamento;
    }

    public void setParametroDepartamento(Departamento parametroDepartamento) {
        this.parametroDepartamento = parametroDepartamento;
    }

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public Carrera getParametroCarrera() {
        return parametroCarrera;
    }

    public void setParametroCarrera(Carrera parametroCarrera) {
        this.parametroCarrera = parametroCarrera;
    }

    public boolean isActivarDepartamento() {
        return activarDepartamento;
    }

    public void setActivarDepartamento(boolean activarDepartamento) {
        this.activarDepartamento = activarDepartamento;
    }

    public boolean isActivarCarrera() {
        return activarCarrera;
    }

    public void setActivarCarrera(boolean activarCarrera) {
        this.activarCarrera = activarCarrera;
    }

    public boolean isActivarNuevoDepartamento() {
        return activarNuevoDepartamento;
    }

    public void setActivarNuevoDepartamento(boolean activarNuevoDepartamento) {
        this.activarNuevoDepartamento = activarNuevoDepartamento;
    }

    public boolean isActivarNuevoCarrera() {
        return activarNuevoCarrera;
    }

    public void setActivarNuevoCarrera(boolean activarNuevoCarrera) {
        this.activarNuevoCarrera = activarNuevoCarrera;
    }

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<PlanEstudios> getListaPlanesEstudios() {
        return listaPlanesEstudios;
    }

    public void setListaPlanesEstudios(List<PlanEstudios> listaPlanesEstudios) {
        this.listaPlanesEstudios = listaPlanesEstudios;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<PlanEstudios> getListaPlanesEstudiosTabla() {
        return listaPlanesEstudiosTabla;
    }

    public void setListaPlanesEstudiosTabla(List<PlanEstudios> listaPlanesEstudiosTabla) {
        this.listaPlanesEstudiosTabla = listaPlanesEstudiosTabla;
    }

    public boolean isBloquearPagSigPlanEstudio() {
        return bloquearPagSigPlanEstudio;
    }

    public void setBloquearPagSigPlanEstudio(boolean bloquearPagSigPlanEstudio) {
        this.bloquearPagSigPlanEstudio = bloquearPagSigPlanEstudio;
    }

    public boolean isBloquearPagAntPlanEstudio() {
        return bloquearPagAntPlanEstudio;
    }

    public void setBloquearPagAntPlanEstudio(boolean bloquearPagAntPlanEstudio) {
        this.bloquearPagAntPlanEstudio = bloquearPagAntPlanEstudio;
    }

}
