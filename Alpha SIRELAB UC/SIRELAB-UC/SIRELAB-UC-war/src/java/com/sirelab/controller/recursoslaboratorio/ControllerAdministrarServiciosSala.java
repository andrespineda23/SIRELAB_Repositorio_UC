/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoServiciosSalaBOInterface;
import com.sirelab.entidades.ServiciosSala;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarServiciosSala implements Serializable {

    @EJB
    GestionarRecursoServiciosSalaBOInterface gestionarRecursoServiciosSalaBO;

    private String parametroNombre, parametroCodigo;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<ServiciosSala> listaServiciosSala;
    private List<ServiciosSala> listaServiciosSalaTabla;
    private int posicionServiciosSalaTabla;
    private int tamTotalServiciosSala;
    private boolean bloquearPagSigServiciosSala, bloquearPagAntServiciosSala;
    //
    private String altoTabla;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarServiciosSala() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNombre = null;
        parametroEstado = 1;
        parametroCodigo = null;
        altoTabla = "150";
        inicializarFiltros();
        listaServiciosSala = null;
        listaServiciosSalaTabla = null;
        posicionServiciosSalaTabla = 0;
        tamTotalServiciosSala = 0;
        bloquearPagAntServiciosSala = true;
        bloquearPagSigServiciosSala = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroEstado", null);
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty()) && (parametroNombre.trim().length() > 0)) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty()) && (parametroCodigo.trim().length() > 0)) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
    }

    public void buscarServiciosSalaPorParametros() {
        try {
            inicializarFiltros();
            listaServiciosSala = null;
            listaServiciosSala = gestionarRecursoServiciosSalaBO.consultarServiciosSalaPorParametro(filtros);
            if (listaServiciosSala != null) {
                if (listaServiciosSala.size() > 0) {
                    activarExport = false;
                    listaServiciosSalaTabla = new ArrayList<ServiciosSala>();
                    tamTotalServiciosSala = listaServiciosSala.size();
                    posicionServiciosSalaTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalServiciosSala);
                    cargarDatosTablaServiciosSala();
                } else {
                    activarExport = true;
                    listaServiciosSalaTabla = null;
                    tamTotalServiciosSala = 0;
                    posicionServiciosSalaTabla = 0;
                    bloquearPagAntServiciosSala = true;
                    bloquearPagSigServiciosSala = true;
                    cantidadRegistros = String.valueOf(tamTotalServiciosSala);
                }
            } else {
                listaServiciosSalaTabla = null;
                tamTotalServiciosSala = 0;
                posicionServiciosSalaTabla = 0;
                bloquearPagAntServiciosSala = true;
                bloquearPagSigServiciosSala = true;
                cantidadRegistros = String.valueOf(tamTotalServiciosSala);
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarServiciosSala buscarServiciosSalaPorParametros:  " + e.toString());
            logger.error("Error ControllerAdministrarServiciosSala buscarServiciosSalaPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaServiciosSala() {
        if (tamTotalServiciosSala < 10) {
            for (int i = 0; i < tamTotalServiciosSala; i++) {
                listaServiciosSalaTabla.add(listaServiciosSala.get(i));
            }
            bloquearPagSigServiciosSala = true;
            bloquearPagAntServiciosSala = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaServiciosSalaTabla.add(listaServiciosSala.get(i));
            }
            bloquearPagSigServiciosSala = false;
            bloquearPagAntServiciosSala = true;
        }
    }

    public void cargarPaginaSiguienteServiciosSala() {
        listaServiciosSalaTabla = new ArrayList<ServiciosSala>();
        posicionServiciosSalaTabla = posicionServiciosSalaTabla + 10;
        int diferencia = tamTotalServiciosSala - posicionServiciosSalaTabla;
        if (diferencia > 10) {
            for (int i = posicionServiciosSalaTabla; i < (posicionServiciosSalaTabla + 10); i++) {
                listaServiciosSalaTabla.add(listaServiciosSala.get(i));
            }
            bloquearPagSigServiciosSala = false;
            bloquearPagAntServiciosSala = false;
        } else {
            for (int i = posicionServiciosSalaTabla; i < (posicionServiciosSalaTabla + diferencia); i++) {
                listaServiciosSalaTabla.add(listaServiciosSala.get(i));
            }
            bloquearPagSigServiciosSala = true;
            bloquearPagAntServiciosSala = false;
        }
    }

    public void cargarPaginaAnteriorServiciosSala() {
        listaServiciosSalaTabla = new ArrayList<ServiciosSala>();
        posicionServiciosSalaTabla = posicionServiciosSalaTabla - 10;
        int diferencia = tamTotalServiciosSala - posicionServiciosSalaTabla;
        if (diferencia == tamTotalServiciosSala) {
            for (int i = posicionServiciosSalaTabla; i < (posicionServiciosSalaTabla + 10); i++) {
                listaServiciosSalaTabla.add(listaServiciosSala.get(i));
            }
            bloquearPagSigServiciosSala = false;
            bloquearPagAntServiciosSala = true;
        } else {
            for (int i = posicionServiciosSalaTabla; i < (posicionServiciosSalaTabla + 10); i++) {
                listaServiciosSalaTabla.add(listaServiciosSala.get(i));
            }
            bloquearPagSigServiciosSala = false;
            bloquearPagAntServiciosSala = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        listaServiciosSala = null;
        listaServiciosSalaTabla = null;
        posicionServiciosSalaTabla = 0;
        parametroEstado = 1;
        tamTotalServiciosSala = 0;
        bloquearPagAntServiciosSala = true;
        bloquearPagSigServiciosSala = true;
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
     exporter.export(context, tabla, "AdministrarServiciosSalaPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "AdministrarServiciosSalaXLS", false, false, "UTF-8", null, null);
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

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<ServiciosSala> getListaServiciosSala() {
        return listaServiciosSala;
    }

    public void setListaServiciosSala(List<ServiciosSala> listaServiciosSala) {
        this.listaServiciosSala = listaServiciosSala;
    }

    public List<ServiciosSala> getListaServiciosSalaTabla() {
        return listaServiciosSalaTabla;
    }

    public void setListaServiciosSalaTabla(List<ServiciosSala> listaServiciosSalaTabla) {
        this.listaServiciosSalaTabla = listaServiciosSalaTabla;
    }

    public int getPosicionServiciosSalaTabla() {
        return posicionServiciosSalaTabla;
    }

    public void setPosicionServiciosSalaTabla(int posicionServiciosSalaTabla) {
        this.posicionServiciosSalaTabla = posicionServiciosSalaTabla;
    }

    public int getTamTotalServiciosSala() {
        return tamTotalServiciosSala;
    }

    public void setTamTotalServiciosSala(int tamTotalServiciosSala) {
        this.tamTotalServiciosSala = tamTotalServiciosSala;
    }

    public boolean isBloquearPagSigServiciosSala() {
        return bloquearPagSigServiciosSala;
    }

    public void setBloquearPagSigServiciosSala(boolean bloquearPagSigServiciosSala) {
        this.bloquearPagSigServiciosSala = bloquearPagSigServiciosSala;
    }

    public boolean isBloquearPagAntServiciosSala() {
        return bloquearPagAntServiciosSala;
    }

    public void setBloquearPagAntServiciosSala(boolean bloquearPagAntServiciosSala) {
        this.bloquearPagAntServiciosSala = bloquearPagAntServiciosSala;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
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
