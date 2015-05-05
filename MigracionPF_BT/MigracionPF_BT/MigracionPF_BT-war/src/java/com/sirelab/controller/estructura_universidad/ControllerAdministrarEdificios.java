/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarEdificiosBOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Sede;
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
public class ControllerAdministrarEdificios implements Serializable {

    @EJB
    GestionarEdificiosBOInterface gestionarEdificiosBO;

    private String parametroDescripcion, parametroDireccion;
    private List<Sede> listaSedes;
    private Sede parametroSede;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<Edificio> listaEdificios;
    private List<Edificio> filtrarListaEdificios;
    //
    private String altoTabla;

    public ControllerAdministrarEdificios() {
    }

    @PostConstruct
    public void init() {
        activarExport = true;
        parametroDescripcion = null;
        parametroDireccion = null;
        parametroSede = new Sede();
        listaSedes = gestionarEdificiosBO.consultarSedesRegistradas();
        altoTabla = "150";
        inicializarFiltros();
        listaEdificios = null;
        filtrarListaEdificios = null;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroDescripcion", null);
        filtros.put("parametroDireccion", null);
        filtros.put("parametroSede", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroDescripcion) == true) && (!parametroDescripcion.isEmpty())) {
            filtros.put("parametroDescripcion", parametroDescripcion.toString());
        }
        if ((Utilidades.validarNulo(parametroDireccion) == true) && (!parametroDireccion.isEmpty())) {
            filtros.put("parametroDireccion", parametroDireccion.toString());
        }
        if (parametroSede.getIdsede() != null) {
            filtros.put("parametroSede", parametroSede.getIdsede().toString());
        }
    }

    public void buscarEdificiosPorParametros() {
        try {
            //RequestContext context = RequestContext.getCurrentInstance();
            inicializarFiltros();
            listaEdificios = null;
            listaEdificios = gestionarEdificiosBO.consultarEdificiosPorParametro(filtros);
            if (listaEdificios != null) {
                if (listaEdificios.size() > 0) {
                    activarExport = false;
                } else {
                    activarExport = true;
                    //      context.execute("consultaSinDatos.show();");
                }
            } else {
                //context.execute("consultaSinDatos.show()");
            }
            //context.update("form:datosBusqueda");
            //context.update("form:exportarXLS");
            //context.update("form:exportarXML");
            //context.update("form:exportarPDF");
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarEdificios buscarEdificiosPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroDescripcion = null;
        parametroDireccion = null;
        parametroSede = new Sede();
        inicializarFiltros();
        listaEdificios = null;
        //RequestContext.getCurrentInstance().update("formT:form:panelMenu");
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "Gestionar_Edificios_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Gestionar_Edificios_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    // GET - SET
    public String getParametroDescripcion() {
        return parametroDescripcion;
    }

    public void setParametroDescripcion(String parametroDescripcion) {
        this.parametroDescripcion = parametroDescripcion;
    }

    public String getParametroDireccion() {
        return parametroDireccion;
    }

    public void setParametroDireccion(String parametroDireccion) {
        this.parametroDireccion = parametroDireccion;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public Sede getParametroSede() {
        return parametroSede;
    }

    public void setParametroSede(Sede parametroSede) {
        this.parametroSede = parametroSede;
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

    public List<Edificio> getListaEdificios() {
        return listaEdificios;
    }

    public void setListaEdificios(List<Edificio> listaEdificios) {
        this.listaEdificios = listaEdificios;
    }

    public List<Edificio> getFiltrarListaEdificios() {
        return filtrarListaEdificios;
    }

    public void setFiltrarListaEdificios(List<Edificio> filtrarListaEdificios) {
        this.filtrarListaEdificios = filtrarListaEdificios;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

}
