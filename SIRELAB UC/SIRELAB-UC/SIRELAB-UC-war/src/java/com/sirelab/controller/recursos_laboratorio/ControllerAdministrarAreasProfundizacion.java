/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoAreasProfundizacionBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

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
    private List<AreaProfundizacion> listaAreasProfundizacionTabla;
    private int posicionAreaProfundizacionTabla;
    private int tamTotalAreaProfundizacion;
    private boolean bloquearPagSigAreaProfundizacion, bloquearPagAntAreaProfundizacion;
    //
    private String altoTabla;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarAreasProfundizacion() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        altoTabla = "150";
        inicializarFiltros();
        listaAreasProfundizacion = null;
        listaAreasProfundizacionTabla = null;
        posicionAreaProfundizacionTabla = 0;
        tamTotalAreaProfundizacion = 0;
        bloquearPagAntAreaProfundizacion = true;
        bloquearPagSigAreaProfundizacion = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
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
                    listaAreasProfundizacionTabla = new ArrayList<AreaProfundizacion>();
                    tamTotalAreaProfundizacion = listaAreasProfundizacion.size();
                    posicionAreaProfundizacionTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalAreaProfundizacion);
                    cargarDatosTablaAreaProfundizacion();
                } else {
                    activarExport = true;
                    listaAreasProfundizacionTabla = null;
                    tamTotalAreaProfundizacion = 0;
                    posicionAreaProfundizacionTabla = 0;
                    bloquearPagAntAreaProfundizacion = true;
                    bloquearPagSigAreaProfundizacion = true;
                    cantidadRegistros = String.valueOf(tamTotalAreaProfundizacion);
                }
            } else {
                listaAreasProfundizacionTabla = null;
                tamTotalAreaProfundizacion = 0;
                posicionAreaProfundizacionTabla = 0;
                bloquearPagAntAreaProfundizacion = true;
                bloquearPagSigAreaProfundizacion = true;
                cantidadRegistros = String.valueOf(tamTotalAreaProfundizacion);
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarAreaProfudizacion buscarLaboratoriosPorParametros:  " + e.toString());
            System.out.println("Error ControllerAdministrarAreaProfudizacion buscarLaboratoriosPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaAreaProfundizacion() {
        if (tamTotalAreaProfundizacion < 10) {
            for (int i = 0; i < tamTotalAreaProfundizacion; i++) {
                listaAreasProfundizacionTabla.add(listaAreasProfundizacion.get(i));
            }
            bloquearPagSigAreaProfundizacion = true;
            bloquearPagAntAreaProfundizacion = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaAreasProfundizacionTabla.add(listaAreasProfundizacion.get(i));
            }
            bloquearPagSigAreaProfundizacion = false;
            bloquearPagAntAreaProfundizacion = true;
        }
    }

    public void cargarPaginaSiguienteAreaProfundizacion() {
        listaAreasProfundizacionTabla = new ArrayList<AreaProfundizacion>();
        posicionAreaProfundizacionTabla = posicionAreaProfundizacionTabla + 10;
        int diferencia = tamTotalAreaProfundizacion - posicionAreaProfundizacionTabla;
        if (diferencia > 10) {
            for (int i = posicionAreaProfundizacionTabla; i < (posicionAreaProfundizacionTabla + 10); i++) {
                listaAreasProfundizacionTabla.add(listaAreasProfundizacion.get(i));
            }
            bloquearPagSigAreaProfundizacion = false;
            bloquearPagAntAreaProfundizacion = false;
        } else {
            for (int i = posicionAreaProfundizacionTabla; i < (posicionAreaProfundizacionTabla + diferencia); i++) {
                listaAreasProfundizacionTabla.add(listaAreasProfundizacion.get(i));
            }
            bloquearPagSigAreaProfundizacion = true;
            bloquearPagAntAreaProfundizacion = false;
        }
    }

    public void cargarPaginaAnteriorAreaProfundizacion() {
        listaAreasProfundizacionTabla = new ArrayList<AreaProfundizacion>();
        posicionAreaProfundizacionTabla = posicionAreaProfundizacionTabla - 10;
        int diferencia = tamTotalAreaProfundizacion - posicionAreaProfundizacionTabla;
        if (diferencia == tamTotalAreaProfundizacion) {
            for (int i = posicionAreaProfundizacionTabla; i < (posicionAreaProfundizacionTabla + 10); i++) {
                listaAreasProfundizacionTabla.add(listaAreasProfundizacion.get(i));
            }
            bloquearPagSigAreaProfundizacion = false;
            bloquearPagAntAreaProfundizacion = true;
        } else {
            for (int i = posicionAreaProfundizacionTabla; i < (posicionAreaProfundizacionTabla + 10); i++) {
                listaAreasProfundizacionTabla.add(listaAreasProfundizacion.get(i));
            }
            bloquearPagSigAreaProfundizacion = false;
            bloquearPagAntAreaProfundizacion = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        listaAreasProfundizacion = null;
        listaAreasProfundizacionTabla = null;
        posicionAreaProfundizacionTabla = 0;
        tamTotalAreaProfundizacion = 0;
        bloquearPagAntAreaProfundizacion = true;
        bloquearPagSigAreaProfundizacion = true;
        cantidadRegistros = "N/A";
        inicializarFiltros();
        return paginaAnterior;
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

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<AreaProfundizacion> getListaAreasProfundizacionTabla() {
        return listaAreasProfundizacionTabla;
    }

    public void setListaAreasProfundizacionTabla(List<AreaProfundizacion> listaAreasProfundizacionTabla) {
        this.listaAreasProfundizacionTabla = listaAreasProfundizacionTabla;
    }

    public boolean isBloquearPagSigAreaProfundizacion() {
        return bloquearPagSigAreaProfundizacion;
    }

    public void setBloquearPagSigAreaProfundizacion(boolean bloquearPagSigAreaProfundizacion) {
        this.bloquearPagSigAreaProfundizacion = bloquearPagSigAreaProfundizacion;
    }

    public boolean isBloquearPagAntAreaProfundizacion() {
        return bloquearPagAntAreaProfundizacion;
    }

    public void setBloquearPagAntAreaProfundizacion(boolean bloquearPagAntAreaProfundizacion) {
        this.bloquearPagAntAreaProfundizacion = bloquearPagAntAreaProfundizacion;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
