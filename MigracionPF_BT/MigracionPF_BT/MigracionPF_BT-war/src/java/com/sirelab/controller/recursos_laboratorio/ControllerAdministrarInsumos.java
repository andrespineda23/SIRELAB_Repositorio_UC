/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.GestionarRecursoInsumosBOInterface;
import com.sirelab.entidades.Insumo;
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
public class ControllerAdministrarInsumos implements Serializable {

    @EJB
    GestionarRecursoInsumosBOInterface gestionarRecursoInsumosBO;

    private String parametroNombre, parametroCodigo, parametroMarca, parametroModelo;
    private Map<String, String> filtros;
    private List<Proveedor> listaProveedores;
    private Proveedor parametroProveedor;
    //
    private List<Insumo> listaInsumos;
    private List<Insumo> filtrarListaInsumos;
    //
    private String altoTabla;
    //
    private boolean activarExport;

    public ControllerAdministrarInsumos() {
    }

    @PostConstruct
    public void init() {
        parametroNombre = null;
        parametroCodigo = null;
        parametroModelo = null;
        parametroMarca = null;
        parametroProveedor = null;
        altoTabla = "150";
        inicializarFiltros();
        listaInsumos = null;
        filtrarListaInsumos = null;
        activarExport = true;
        listaProveedores = gestionarRecursoInsumosBO.consultarProveedoresRegistrados();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroMarca", null);
        filtros.put("parametroModelo", null);
        filtros.put("parametroCodigo", null);
        filtros.put("parametroProveedor", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty())) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if ((Utilidades.validarNulo(parametroMarca) == true) && (!parametroMarca.isEmpty())) {
            filtros.put("parametroMarca", parametroMarca.toString());
        }
        if ((Utilidades.validarNulo(parametroModelo) == true) && (!parametroModelo.isEmpty())) {
            filtros.put("parametroModelo", parametroModelo.toString());
        }
        if ((Utilidades.validarNulo(parametroProveedor) == true)) {
            filtros.put("parametroProveedor", parametroProveedor.getIdproveedor().toString());
        }
    }

    public void buscarInsumosPorParametros() {
        try {
            //RequestContext context = RequestContext.getCurrentInstance();
            inicializarFiltros();
            listaInsumos = null;
            listaInsumos = gestionarRecursoInsumosBO.consultarInsumosPorParametro(filtros);
            if (listaInsumos != null) {
                if (listaInsumos.size() > 0) {
                    activarExport = false;
                } else {
                    activarExport = true;
                    //      context.execute("consultaSinDatos.show();");
                }
            } else {
                activarExport = true;
//                context.execute("consultaSinDatos.show();");
            }
            //          context.update("form:datosBusqueda");
            //        context.update("form:exportarXLS");
            //      context.update("form:exportarXML");
            //    context.update("form:exportarPDF");
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarInsumos buscarInsumosPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroModelo = null;
        parametroMarca = null;
        parametroProveedor = null;
        inicializarFiltros();
        listaInsumos = null;
        //RequestContext.getCurrentInstance().update("formT:form:panelMenu");
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Recursos_Insumo_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Recursos_Insumo_XLS", false, false, "UTF-8", null, null);
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

    public String getParametroMarca() {
        return parametroMarca;
    }

    public void setParametroMarca(String parametroMarca) {
        this.parametroMarca = parametroMarca;
    }

    public String getParametroModelo() {
        return parametroModelo;
    }

    public void setParametroModelo(String parametroModelo) {
        this.parametroModelo = parametroModelo;
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

    public Proveedor getParametroProveedor() {
        return parametroProveedor;
    }

    public void setParametroProveedor(Proveedor parametroProveedor) {
        this.parametroProveedor = parametroProveedor;
    }

    public List<Insumo> getListaInsumos() {
        return listaInsumos;
    }

    public void setListaInsumos(List<Insumo> listaInsumos) {
        this.listaInsumos = listaInsumos;
    }

    public List<Insumo> getFiltrarListaInsumos() {
        return filtrarListaInsumos;
    }

    public void setFiltrarListaInsumos(List<Insumo> filtrarListaInsumos) {
        this.filtrarListaInsumos = filtrarListaInsumos;
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
