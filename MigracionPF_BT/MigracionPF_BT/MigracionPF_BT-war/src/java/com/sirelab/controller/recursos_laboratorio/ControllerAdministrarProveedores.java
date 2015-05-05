/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.GestionarRecursoProveedoresBOInterface;
import com.sirelab.entidades.Proveedor;
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
public class ControllerAdministrarProveedores implements Serializable {

    @EJB
    GestionarRecursoProveedoresBOInterface gestionarRecursoProveedoresBO;

    private String parametroNombre, parametroNIT, parametroTelefono, parametroDireccion;
    private Map<String, String> filtros;
    //
    private List<Proveedor> listaProveedores;
    private List<Proveedor> filtrarListaProveedores;
    //
    private String altoTabla;
    //
    private boolean activarExport;

    public ControllerAdministrarProveedores() {
    }

    @PostConstruct
    public void init() {
        parametroNombre = null;
        parametroNIT = null;
        parametroDireccion = null;
        parametroTelefono = null;
        altoTabla = "150";
        inicializarFiltros();
        listaProveedores = null;
        filtrarListaProveedores = null;
        activarExport = true;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroTelefono", null);
        filtros.put("parametroDireccion", null);
        filtros.put("parametroNIT", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroNIT) == true) && (!parametroNIT.isEmpty())) {
            filtros.put("parametroNIT", parametroNIT.toString());
        }
        if ((Utilidades.validarNulo(parametroTelefono) == true) && (!parametroTelefono.isEmpty())) {
            filtros.put("parametroTelefono", parametroTelefono.toString());
        }
        if ((Utilidades.validarNulo(parametroDireccion) == true) && (!parametroDireccion.isEmpty())) {
            filtros.put("parametroDireccion", parametroDireccion.toString());
        }
    }

    public void buscarProveedoresPorParametros() {
        try {
            //RequestContext context = RequestContext.getCurrentInstance();
            inicializarFiltros();
            listaProveedores = null;
            listaProveedores = gestionarRecursoProveedoresBO.consultarProveedoresPorParametro(filtros);
            if (listaProveedores != null) {
                if (listaProveedores.size() > 0) {
                    //activarFiltrosTabla();
                    activarExport = false;
                } else {
                    activarExport = true;
                    //context.execute("consultaSinDatos.show();");
                }
            } else {
                activarExport = true;
                //context.execute("consultaSinDatos.show();");
            }
            //context.update("form:datosBusqueda");
            //context.update("form:exportarXLS");
            //context.update("form:exportarXML");
            //context.update("form:exportarPDF");
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarProveedores buscarProveedoresPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        if (null != listaProveedores) {
            //desactivarFiltrosTabla();
        }
        parametroNombre = null;
        parametroNIT = null;
        parametroDireccion = null;
        parametroTelefono = null;
        inicializarFiltros();
        listaProveedores = null;
        //RequestContext.getCurrentInstance().update("formT:form:panelMenu");
    }

    /*
     public void activarFiltrosTabla() {
     altoTabla = "128";
     FacesContext c = FacesContext.getCurrentInstance();
     nombreTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:nombreTabla");
     nombreTabla.setFilterStyle("width: 80px");
     nitTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:nitTabla");
     nitTabla.setFilterStyle("width: 80px");
     direccionTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:direccionTabla");
     direccionTabla.setFilterStyle("width: 80px");
     telefonoTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:telefonoTabla");
     telefonoTabla.setFilterStyle("width: 80px");
     }

     public void desactivarFiltrosTabla() {
     altoTabla = "150";
     FacesContext c = FacesContext.getCurrentInstance();
     nombreTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:nombreTabla");
     nombreTabla.setFilterStyle("display: none; visibility: hidden;");
     nitTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:nitTabla");
     nitTabla.setFilterStyle("display: none; visibility: hidden;");
     direccionTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:direccionTabla");
     direccionTabla.setFilterStyle("display: none; visibility: hidden;");
     telefonoTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:telefonoTabla");
     telefonoTabla.setFilterStyle("display: none; visibility: hidden;");
     filtrarListaProveedores = null;
     }
     */
    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Recursos_Proveedor_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Recursos_Proveedor_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }*/
    // GET - SET
    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
    }

    public String getParametroNIT() {
        return parametroNIT;
    }

    public void setParametroNIT(String parametroNIT) {
        this.parametroNIT = parametroNIT;
    }

    public String getParametroTelefono() {
        return parametroTelefono;
    }

    public void setParametroTelefono(String parametroTelefono) {
        this.parametroTelefono = parametroTelefono;
    }

    public String getParametroDireccion() {
        return parametroDireccion;
    }

    public void setParametroDireccion(String parametroDireccion) {
        this.parametroDireccion = parametroDireccion;
    }

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public List<Proveedor> getFiltrarListaProveedores() {
        return filtrarListaProveedores;
    }

    public void setFiltrarListaProveedores(List<Proveedor> filtrarListaProveedores) {
        this.filtrarListaProveedores = filtrarListaProveedores;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

}
