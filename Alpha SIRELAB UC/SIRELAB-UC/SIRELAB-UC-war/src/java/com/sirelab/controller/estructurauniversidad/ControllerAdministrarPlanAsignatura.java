/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.bo.interfacebo.universidad.GestionarPlanAsignaturaBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarPlanAsignatura implements Serializable {

    @EJB
    GestionarPlanAsignaturaBOInterface gestionarPlanAsignaturaBO;

    private List<Carrera> listaCarreras;
    private Carrera parametroCarrera;
    private List<PlanEstudios> listaPlanEstudios;
    private PlanEstudios parametroPlan;
    private boolean activarPlan;
    private List<Asignatura> listaAsignaturas;
    private Asignatura parametroAsignatura;

    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<AsignaturaPorPlanEstudio> listaAsignaturaPlanEstudio;
    private List<AsignaturaPorPlanEstudio> listaAsignaturaPlanEstudioTabla;
    private int posicionAsignaturaPlanTabla;
    private int tamTotalAsignaturaPlan;
    private boolean bloquearPagSigAsignaturaPlan, bloquearPagAntAsignaturaPlan;
    //
    private String altoTabla;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarPlanAsignatura() {
    }

    @PostConstruct
    public void init() {
        activarPlan = true;
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroCarrera = new Carrera();
        parametroAsignatura = new Asignatura();
        parametroPlan = new PlanEstudios();
        altoTabla = "150";
        inicializarFiltros();
        listaAsignaturas = null;
        listaPlanEstudios = null;
        listaCarreras = null;
        listaAsignaturaPlanEstudioTabla = null;
        listaAsignaturaPlanEstudio = null;
        posicionAsignaturaPlanTabla = 0;
        tamTotalAsignaturaPlan = 0;
        bloquearPagAntAsignaturaPlan = true;
        bloquearPagSigAsignaturaPlan = true;
        BasicConfigurator.configure();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroCarrera", null);
        filtros.put("parametroPlan", null);
        filtros.put("parametroAsignatura", null);
        filtros.put("parametroEstado", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if (Utilidades.validarNulo(parametroCarrera)) {
            if (parametroCarrera.getIdcarrera() != null) {
                filtros.put("parametroCarrera", parametroCarrera.getIdcarrera().toString());
            }
        }
        if (Utilidades.validarNulo(parametroPlan)) {
            if (parametroPlan.getIdplanestudios() != null) {
                filtros.put("parametroPlan", parametroPlan.getIdplanestudios().toString());
            }
        }
        if (Utilidades.validarNulo(parametroAsignatura)) {
            if (parametroAsignatura.getIdasignatura() != null) {
                filtros.put("parametroAsignatura", parametroAsignatura.getIdasignatura().toString());
            }
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
    }

    public void buscarAsignaturaPorPlanEstudioPorParametros() {
        try {
            inicializarFiltros();
            listaAsignaturaPlanEstudio = null;
            listaAsignaturaPlanEstudio = gestionarPlanAsignaturaBO.consultarAsignaturaPorPlanPorParametro(filtros);
            if (listaAsignaturaPlanEstudio != null) {
                if (listaAsignaturaPlanEstudio.size() > 0) {
                    activarExport = false;
                    listaAsignaturaPlanEstudioTabla = new ArrayList<AsignaturaPorPlanEstudio>();
                    tamTotalAsignaturaPlan = listaAsignaturaPlanEstudio.size();
                    posicionAsignaturaPlanTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalAsignaturaPlan);
                    cargarDatosTablaAsignaturaPlan();
                } else {
                    activarExport = true;
                    listaAsignaturaPlanEstudioTabla = null;
                    tamTotalAsignaturaPlan = 0;
                    posicionAsignaturaPlanTabla = 0;
                    bloquearPagAntAsignaturaPlan = true;
                    cantidadRegistros = String.valueOf(tamTotalAsignaturaPlan);
                    bloquearPagSigAsignaturaPlan = true;
                }
            } else {
                listaAsignaturaPlanEstudioTabla = null;
                tamTotalAsignaturaPlan = 0;
                posicionAsignaturaPlanTabla = 0;
                bloquearPagAntAsignaturaPlan = true;
                bloquearPagSigAsignaturaPlan = true;
                cantidadRegistros = String.valueOf(tamTotalAsignaturaPlan);
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarPlanAsignatura buscarAsignaturaPorPlanEstudioPorParametros:  " + e.toString());
            logger.error("Error ControllerAdministrarPlanAsignatura buscarAsignaturaPorPlanEstudioPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaAsignaturaPlan() {
        if (tamTotalAsignaturaPlan < 10) {
            for (int i = 0; i < tamTotalAsignaturaPlan; i++) {
                listaAsignaturaPlanEstudioTabla.add(listaAsignaturaPlanEstudio.get(i));
            }
            bloquearPagSigAsignaturaPlan = true;
            bloquearPagAntAsignaturaPlan = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaAsignaturaPlanEstudioTabla.add(listaAsignaturaPlanEstudio.get(i));
            }
            bloquearPagSigAsignaturaPlan = false;
            bloquearPagAntAsignaturaPlan = true;
        }
    }

    public void cargarPaginaSiguienteAsignaturaPlan() {
        listaAsignaturaPlanEstudioTabla = new ArrayList<AsignaturaPorPlanEstudio>();
        posicionAsignaturaPlanTabla = posicionAsignaturaPlanTabla + 10;
        int diferencia = tamTotalAsignaturaPlan - posicionAsignaturaPlanTabla;
        if (diferencia > 10) {
            for (int i = posicionAsignaturaPlanTabla; i < (posicionAsignaturaPlanTabla + 10); i++) {
                listaAsignaturaPlanEstudioTabla.add(listaAsignaturaPlanEstudio.get(i));
            }
            bloquearPagSigAsignaturaPlan = false;
            bloquearPagAntAsignaturaPlan = false;
        } else {
            for (int i = posicionAsignaturaPlanTabla; i < (posicionAsignaturaPlanTabla + diferencia); i++) {
                listaAsignaturaPlanEstudioTabla.add(listaAsignaturaPlanEstudio.get(i));
            }
            bloquearPagSigAsignaturaPlan = true;
            bloquearPagAntAsignaturaPlan = false;
        }
    }

    public void cargarPaginaAnteriorAsignaturaPlan() {
        listaAsignaturaPlanEstudioTabla = new ArrayList<AsignaturaPorPlanEstudio>();
        posicionAsignaturaPlanTabla = posicionAsignaturaPlanTabla - 10;
        int diferencia = tamTotalAsignaturaPlan - posicionAsignaturaPlanTabla;
        if (diferencia == tamTotalAsignaturaPlan) {
            for (int i = posicionAsignaturaPlanTabla; i < (posicionAsignaturaPlanTabla + 10); i++) {
                listaAsignaturaPlanEstudioTabla.add(listaAsignaturaPlanEstudio.get(i));
            }
            bloquearPagSigAsignaturaPlan = false;
            bloquearPagAntAsignaturaPlan = true;
        } else {
            for (int i = posicionAsignaturaPlanTabla; i < (posicionAsignaturaPlanTabla + 10); i++) {
                listaAsignaturaPlanEstudioTabla.add(listaAsignaturaPlanEstudio.get(i));
            }
            bloquearPagSigAsignaturaPlan = false;
            bloquearPagAntAsignaturaPlan = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroEstado = 1;
        parametroCarrera = new Carrera();
        parametroAsignatura = new Asignatura();
        parametroPlan = new PlanEstudios();

        listaPlanEstudios = null;
        listaAsignaturas = null;
        listaPlanEstudios = null;
        cantidadRegistros = "N/A";

        listaAsignaturaPlanEstudio = null;
        listaAsignaturaPlanEstudioTabla = null;
        posicionAsignaturaPlanTabla = 0;
        tamTotalAsignaturaPlan = 0;
        bloquearPagAntAsignaturaPlan = true;
        bloquearPagSigAsignaturaPlan = true;
        activarPlan = true;
        inicializarFiltros();
    }

    public void limpiarDatos() {
        activarPlan = true;
        activarExport = true;
        cantidadRegistros = "N/A";
        parametroCarrera = new Carrera();
        parametroAsignatura = new Asignatura();
        parametroPlan = new PlanEstudios();
        listaPlanEstudios = null;
        parametroEstado = 1;
        listaAsignaturaPlanEstudio = null;
        listaAsignaturaPlanEstudioTabla = null;
        posicionAsignaturaPlanTabla = 0;
        tamTotalAsignaturaPlan = 0;
        bloquearPagAntAsignaturaPlan = true;
        bloquearPagSigAsignaturaPlan = true;
        inicializarFiltros();
    }

    public void actualizarCarreras() {
        if (Utilidades.validarNulo(parametroCarrera)) {
            parametroPlan = new PlanEstudios();
            listaPlanEstudios = gestionarPlanAsignaturaBO.obtenerPlanEstudiosPorCarrera(parametroCarrera.getIdcarrera());
            activarPlan = false;

        } else {
            activarPlan = true;
            listaPlanEstudios = null;
            parametroPlan = new PlanEstudios();
        }
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "GestionarPlanesEstudiosPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "GestionarPlanesEstudiosXLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    // GET - SET
    public List<Carrera> getListaCarreras() {
        if (null == listaCarreras) {
            listaCarreras = gestionarPlanAsignaturaBO.obtenerCarrerasRegistradas();
        }
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

    public List<PlanEstudios> getListaPlanEstudios() {
        return listaPlanEstudios;
    }

    public void setListaPlanEstudios(List<PlanEstudios> listaPlanEstudios) {
        this.listaPlanEstudios = listaPlanEstudios;
    }

    public PlanEstudios getParametroPlan() {
        return parametroPlan;
    }

    public void setParametroPlan(PlanEstudios parametroPlan) {
        this.parametroPlan = parametroPlan;
    }

    public boolean isActivarPlan() {
        return activarPlan;
    }

    public void setActivarPlan(boolean activarPlan) {
        this.activarPlan = activarPlan;
    }

    public List<Asignatura> getListaAsignaturas() {
        if (null == listaAsignaturas) {
            listaAsignaturas = gestionarPlanAsignaturaBO.consultarAsignaturasRegistradas();
        }
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public Asignatura getParametroAsignatura() {
        return parametroAsignatura;
    }

    public void setParametroAsignatura(Asignatura parametroAsignatura) {
        this.parametroAsignatura = parametroAsignatura;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<AsignaturaPorPlanEstudio> getListaAsignaturaPlanEstudio() {
        return listaAsignaturaPlanEstudio;
    }

    public void setListaAsignaturaPlanEstudio(List<AsignaturaPorPlanEstudio> listaAsignaturaPlanEstudio) {
        this.listaAsignaturaPlanEstudio = listaAsignaturaPlanEstudio;
    }

    public List<AsignaturaPorPlanEstudio> getListaAsignaturaPlanEstudioTabla() {
        return listaAsignaturaPlanEstudioTabla;
    }

    public void setListaAsignaturaPlanEstudioTabla(List<AsignaturaPorPlanEstudio> listaAsignaturaPlanEstudioTabla) {
        this.listaAsignaturaPlanEstudioTabla = listaAsignaturaPlanEstudioTabla;
    }

    public int getPosicionAsignaturaPlanTabla() {
        return posicionAsignaturaPlanTabla;
    }

    public void setPosicionAsignaturaPlanTabla(int posicionAsignaturaPlanTabla) {
        this.posicionAsignaturaPlanTabla = posicionAsignaturaPlanTabla;
    }

    public int getTamTotalAsignaturaPlan() {
        return tamTotalAsignaturaPlan;
    }

    public void setTamTotalAsignaturaPlan(int tamTotalAsignaturaPlan) {
        this.tamTotalAsignaturaPlan = tamTotalAsignaturaPlan;
    }

    public boolean isBloquearPagSigAsignaturaPlan() {
        return bloquearPagSigAsignaturaPlan;
    }

    public void setBloquearPagSigAsignaturaPlan(boolean bloquearPagSigAsignaturaPlan) {
        this.bloquearPagSigAsignaturaPlan = bloquearPagSigAsignaturaPlan;
    }

    public boolean isBloquearPagAntAsignaturaPlan() {
        return bloquearPagAntAsignaturaPlan;
    }

    public void setBloquearPagAntAsignaturaPlan(boolean bloquearPagAntAsignaturaPlan) {
        this.bloquearPagAntAsignaturaPlan = bloquearPagAntAsignaturaPlan;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

}
