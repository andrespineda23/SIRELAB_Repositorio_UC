/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.GestionarRecursoAreasProfundizacionBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarAreasProfundizacion implements Serializable {

    @EJB
    GestionarRecursoAreasProfundizacionBOInterface gestionarRecursoAreaProfundizacionBO;

    private String parametroNombre, parametroCodigo;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<AreaProfundizacion> listaAreasProfundizacion;
    private List<AreaProfundizacion> filtrarListaAreasProfundizacion;
    //
    private String altoTabla;

    public ControllerAdministrarAreasProfundizacion() {
    }

    @PostConstruct
    public void init() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        altoTabla = "150";
        inicializarFiltros();
        listaAreasProfundizacion = null;
        filtrarListaAreasProfundizacion = null;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty())) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
    }

    public void buscarAreasProfundizacionPorParametros() {
        try {
            inicializarFiltros();
            listaAreasProfundizacion = null;
            listaAreasProfundizacion = gestionarRecursoAreaProfundizacionBO.consultarAreasProfundizacionPorParametro(filtros);
            if (listaAreasProfundizacion != null) {
                if (listaAreasProfundizacion.size() > 0) {
                    activarExport = false;
                } else {
                    activarExport = true;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta no ha obtenido resultados de busqueda.", "Consulta de Areas");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("message", message);
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta no ha obtenido resultados de busqueda.", "Consulta de Areas");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("message", message);
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarAreaProfudizacion buscarLaboratoriosPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        inicializarFiltros();
        listaAreasProfundizacion = null;
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Administrar_Areas_Profundizacion_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Administrar_Areas_Profundizacion_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }*/
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

    public List<AreaProfundizacion> getListaAreasProfundizacion() {
        return listaAreasProfundizacion;
    }

    public void setListaAreasProfundizacion(List<AreaProfundizacion> listaAreasProfundizacion) {
        this.listaAreasProfundizacion = listaAreasProfundizacion;
    }

    public List<AreaProfundizacion> getFiltrarListaAreasProfundizacion() {
        return filtrarListaAreasProfundizacion;
    }

    public void setFiltrarListaAreasProfundizacion(List<AreaProfundizacion> filtrarListaAreasProfundizacion) {
        this.filtrarListaAreasProfundizacion = filtrarListaAreasProfundizacion;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

}
