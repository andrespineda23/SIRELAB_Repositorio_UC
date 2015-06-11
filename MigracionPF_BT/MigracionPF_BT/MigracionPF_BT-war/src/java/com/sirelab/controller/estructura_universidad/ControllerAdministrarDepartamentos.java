/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarDepartamentosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerAdministrarDepartamentos implements Serializable {

    @EJB
    GestionarDepartamentosBOInterface gestionarDepartamentosBO;

    private String parametroNombre;
    private List<Facultad> listaFacultades;
    private Facultad parametroFacultad;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<Departamento> listaDepartamentos;
    private List<Departamento> filtrarListaDepartamentos;
    //
    private String altoTabla;

    public ControllerAdministrarDepartamentos() {
    }

    @PostConstruct
    public void init() {
        activarExport = true;
        parametroNombre = null;
        parametroFacultad = new Facultad();
        listaFacultades = gestionarDepartamentosBO.consultarFacultadesRegistradas();
        altoTabla = "150";
        inicializarFiltros();
        listaDepartamentos = null;
        filtrarListaDepartamentos = null;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroFacultad", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if (Utilidades.validarNulo(parametroFacultad)) {
            if (parametroFacultad.getIdfacultad() != null) {
                filtros.put("parametroFacultad", parametroFacultad.getIdfacultad().toString());
            }
        }
    }

    public void buscarDepartamentosPorParametros() {
        try {
            inicializarFiltros();
            listaDepartamentos = null;
            listaDepartamentos = gestionarDepartamentosBO.consultarDepartamentosPorParametro(filtros);
            if (listaDepartamentos != null) {
                if (listaDepartamentos.size() > 0) {
                    activarExport = false;
                } else {
                    activarExport = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarDepartamentos buscarDepartamentosPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroFacultad = new Facultad();
        inicializarFiltros();
        listaDepartamentos = null;
        //RequestContext.getCurrentInstance().update("formT:form:panelMenu");
    }


    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "Gestionar_Departamentos_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Gestionar_Departamentos_XLS", false, false, "UTF-8", null, null);
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

    public List<Facultad> getListaFacultades() {
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

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<Departamento> getFiltrarListaDepartamentos() {
        return filtrarListaDepartamentos;
    }

    public void setFiltrarListaDepartamentos(List<Departamento> filtrarListaDepartamentos) {
        this.filtrarListaDepartamentos = filtrarListaDepartamentos;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

}
