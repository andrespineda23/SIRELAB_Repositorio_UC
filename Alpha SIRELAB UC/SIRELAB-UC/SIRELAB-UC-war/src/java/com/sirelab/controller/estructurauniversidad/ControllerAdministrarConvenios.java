/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.bo.interfacebo.universidad.GestionarConveniosBOInterface;
import com.sirelab.entidades.Convenio;
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
public class ControllerAdministrarConvenios implements Serializable {

    @EJB
    GestionarConveniosBOInterface gestionarConvenioBO;

    private String parametroNombre;
    private Map<String, String> filtros;
    //
    private List<Convenio> listaConvenios;
    private List<Convenio> listaConveniosTabla;
    private int posicionConvenioTabla;
    private int tamTotalConvenio;
    private boolean bloquearPagSigConvenio, bloquearPagAntConvenio;
    //
    private String altoTabla;
    //
    private boolean activarExport;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarConvenios() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        parametroNombre = null;

        altoTabla = "150";
        inicializarFiltros();
        listaConvenios = null;
        listaConveniosTabla = null;
        posicionConvenioTabla = 0;
        tamTotalConvenio = 0;
        bloquearPagAntConvenio = true;
        bloquearPagSigConvenio = true;
        activarExport = true;
        BasicConfigurator.configure();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroEstado", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty()) && (parametroNombre.trim().length() > 0)) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
    }

    public void buscarConveniosPorParametros() {
        try {
            inicializarFiltros();
            listaConvenios = null;
            listaConvenios = gestionarConvenioBO.consultarConveniosPorParametro(filtros);
            if (listaConvenios != null) {
                if (listaConvenios.size() > 0) {
                    activarExport = false;
                    listaConveniosTabla = new ArrayList<Convenio>();
                    tamTotalConvenio = listaConvenios.size();
                    posicionConvenioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalConvenio);
                    cargarDatosTablaConvenio();
                } else {
                    activarExport = true;
                    listaConveniosTabla = null;
                    tamTotalConvenio = 0;
                    posicionConvenioTabla = 0;
                    bloquearPagAntConvenio = true;
                    cantidadRegistros = String.valueOf(tamTotalConvenio);
                    bloquearPagSigConvenio = true;
                }
            } else {
                listaConveniosTabla = null;
                tamTotalConvenio = 0;
                posicionConvenioTabla = 0;
                bloquearPagAntConvenio = true;
                cantidadRegistros = String.valueOf(tamTotalConvenio);
                bloquearPagSigConvenio = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarConvenios buscarConveniosPorParametros:  " + e.toString(),e);
            logger.error("Error ControllerGestionarConvenios buscarConveniosPorParametros : " + e.toString(),e);
        }
    }

    private void cargarDatosTablaConvenio() {
        if (tamTotalConvenio < 10) {
            for (int i = 0; i < tamTotalConvenio; i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = true;
            bloquearPagAntConvenio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = false;
            bloquearPagAntConvenio = true;
        }
    }

    public void cargarPaginaSiguienteConvenio() {
        listaConveniosTabla = new ArrayList<Convenio>();
        posicionConvenioTabla = posicionConvenioTabla + 10;
        int diferencia = tamTotalConvenio - posicionConvenioTabla;
        if (diferencia > 10) {
            for (int i = posicionConvenioTabla; i < (posicionConvenioTabla + 10); i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = false;
            bloquearPagAntConvenio = false;
        } else {
            for (int i = posicionConvenioTabla; i < (posicionConvenioTabla + diferencia); i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = true;
            bloquearPagAntConvenio = false;
        }
    }

    public void cargarPaginaAnteriorConvenio() {
        listaConveniosTabla = new ArrayList<Convenio>();
        posicionConvenioTabla = posicionConvenioTabla - 10;
        int diferencia = tamTotalConvenio - posicionConvenioTabla;
        if (diferencia == tamTotalConvenio) {
            for (int i = posicionConvenioTabla; i < (posicionConvenioTabla + 10); i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = false;
            bloquearPagAntConvenio = true;
        } else {
            for (int i = posicionConvenioTabla; i < (posicionConvenioTabla + 10); i++) {
                listaConveniosTabla.add(listaConvenios.get(i));
            }
            bloquearPagSigConvenio = false;
            bloquearPagAntConvenio = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;

        parametroEstado = 1;
        listaConvenios = null;
        listaConveniosTabla = null;
        posicionConvenioTabla = 0;
        tamTotalConvenio = 0;
        bloquearPagAntConvenio = true;
        cantidadRegistros = "N/A";
        bloquearPagSigConvenio = true;
        inicializarFiltros();
    }
    public String paginaDetalles() {
        activarExport = true;
        parametroNombre = null;
        parametroEstado = 1;
        listaConvenios = null;
        listaConveniosTabla = null;
        posicionConvenioTabla = 0;
        tamTotalConvenio = 0;
        bloquearPagAntConvenio = true;
        cantidadRegistros = "N/A";
        bloquearPagSigConvenio = true;
        return "detallesconvenio";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "GestionarConvenioPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "GestionarConvenioXLS", false, false, "UTF-8", null, null);
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

    public List<Convenio> getListaConvenios() {
        return listaConvenios;
    }

    public void setListaConvenios(List<Convenio> listaConvenios) {
        this.listaConvenios = listaConvenios;
    }

    public List<Convenio> getListaConveniosTabla() {
        return listaConveniosTabla;
    }

    public void setListaConveniosTabla(List<Convenio> listaConveniosTabla) {
        this.listaConveniosTabla = listaConveniosTabla;
    }

    public int getPosicionConvenioTabla() {
        return posicionConvenioTabla;
    }

    public void setPosicionConvenioTabla(int posicionConvenioTabla) {
        this.posicionConvenioTabla = posicionConvenioTabla;
    }

    public int getTamTotalConvenio() {
        return tamTotalConvenio;
    }

    public void setTamTotalConvenio(int tamTotalConvenio) {
        this.tamTotalConvenio = tamTotalConvenio;
    }

    public boolean isBloquearPagSigConvenio() {
        return bloquearPagSigConvenio;
    }

    public void setBloquearPagSigConvenio(boolean bloquearPagSigConvenio) {
        this.bloquearPagSigConvenio = bloquearPagSigConvenio;
    }

    public boolean isBloquearPagAntConvenio() {
        return bloquearPagAntConvenio;
    }

    public void setBloquearPagAntConvenio(boolean bloquearPagAntConvenio) {
        this.bloquearPagAntConvenio = bloquearPagAntConvenio;
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
