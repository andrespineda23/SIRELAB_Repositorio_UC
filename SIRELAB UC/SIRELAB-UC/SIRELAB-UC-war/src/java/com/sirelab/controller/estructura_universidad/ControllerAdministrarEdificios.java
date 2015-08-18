/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarEdificiosBOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Sede;
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
    private List<Edificio> listaEdificiosTabla;
    private int posicionEdificioTabla;
    private int tamTotalEdificio;
    private boolean bloquearPagSigEdificio, bloquearPagAntEdificio;
    //
    private String altoTabla;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarEdificios() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroDescripcion = null;
        parametroDireccion = null;
        parametroSede = new Sede();
        listaSedes = gestionarEdificiosBO.consultarSedesRegistradas();
        altoTabla = "150";
        inicializarFiltros();
        listaEdificios = null;
        listaEdificiosTabla = null;
        posicionEdificioTabla = 0;
        tamTotalEdificio = 0;
        bloquearPagAntEdificio = true;
        bloquearPagSigEdificio = true;
        BasicConfigurator.configure();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroDescripcion", null);
        filtros.put("parametroDireccion", null);
        filtros.put("parametroSede", null);
        filtros.put("parametroEstado", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroDescripcion) == true) && (!parametroDescripcion.isEmpty()) && (parametroDescripcion.trim().length() > 0)) {
            filtros.put("parametroDescripcion", parametroDescripcion.toString());
        }
        if ((Utilidades.validarNulo(parametroDireccion) == true) && (!parametroDireccion.isEmpty()) && (parametroDireccion.trim().length() > 0)) {
            filtros.put("parametroDireccion", parametroDireccion.toString());
        }
        if (Utilidades.validarNulo(parametroSede) == true) {
            if (parametroSede.getIdsede() != null) {
                filtros.put("parametroSede", parametroSede.getIdsede().toString());
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

    public void buscarEdificiosPorParametros() {
        try {
            inicializarFiltros();
            listaEdificios = null;
            listaEdificios = gestionarEdificiosBO.consultarEdificiosPorParametro(filtros);
            if (listaEdificios != null) {
                if (listaEdificios.size() > 0) {
                    activarExport = false;
                    listaEdificiosTabla = new ArrayList<Edificio>();
                    tamTotalEdificio = listaEdificios.size();
                    posicionEdificioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalEdificio);
                    cargarDatosTablaEdificio();
                } else {
                    activarExport = true;
                    listaEdificiosTabla = null;
                    tamTotalEdificio = 0;
                    posicionEdificioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalEdificio);
                    bloquearPagAntEdificio = true;
                    bloquearPagSigEdificio = true;
                }
            } else {
                listaEdificiosTabla = null;
                tamTotalEdificio = 0;
                posicionEdificioTabla = 0;
                cantidadRegistros = String.valueOf(tamTotalEdificio);
                bloquearPagAntEdificio = true;
                bloquearPagSigEdificio = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarEdificios buscarEdificiosPorParametros:  " + e.toString());
            System.out.println("Error ControllerAdministrarEdificios buscarEdificiosPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaEdificio() {
        if (tamTotalEdificio < 10) {
            for (int i = 0; i < tamTotalEdificio; i++) {
                listaEdificiosTabla.add(listaEdificios.get(i));
            }
            bloquearPagSigEdificio = true;
            bloquearPagAntEdificio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEdificiosTabla.add(listaEdificios.get(i));
            }
            bloquearPagSigEdificio = false;
            bloquearPagAntEdificio = true;
        }
    }

    public void cargarPaginaSiguienteEdificio() {
        listaEdificiosTabla = new ArrayList<Edificio>();
        posicionEdificioTabla = posicionEdificioTabla + 10;
        int diferencia = tamTotalEdificio - posicionEdificioTabla;
        if (diferencia > 10) {
            for (int i = posicionEdificioTabla; i < (posicionEdificioTabla + 10); i++) {
                listaEdificiosTabla.add(listaEdificios.get(i));
            }
            bloquearPagSigEdificio = false;
            bloquearPagAntEdificio = false;
        } else {
            for (int i = posicionEdificioTabla; i < (posicionEdificioTabla + diferencia); i++) {
                listaEdificiosTabla.add(listaEdificios.get(i));
            }
            bloquearPagSigEdificio = true;
            bloquearPagAntEdificio = false;
        }
    }

    public void cargarPaginaAnteriorEdificio() {
        listaEdificiosTabla = new ArrayList<Edificio>();
        posicionEdificioTabla = posicionEdificioTabla - 10;
        int diferencia = tamTotalEdificio - posicionEdificioTabla;
        if (diferencia == tamTotalEdificio) {
            for (int i = posicionEdificioTabla; i < (posicionEdificioTabla + 10); i++) {
                listaEdificiosTabla.add(listaEdificios.get(i));
            }
            bloquearPagSigEdificio = false;
            bloquearPagAntEdificio = true;
        } else {
            for (int i = posicionEdificioTabla; i < (posicionEdificioTabla + 10); i++) {
                listaEdificiosTabla.add(listaEdificios.get(i));
            }
            bloquearPagSigEdificio = false;
            bloquearPagAntEdificio = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroDescripcion = null;
        parametroDireccion = null;
        parametroSede = new Sede();
        listaEdificios = null;
        listaSedes = null;
        listaEdificiosTabla = null;
        posicionEdificioTabla = 0;
        parametroEstado = 1;
        cantidadRegistros = "N/A";
        tamTotalEdificio = 0;
        bloquearPagAntEdificio = true;
        bloquearPagSigEdificio = true;
        inicializarFiltros();
    }

    public void limpiarDatos() {
        activarExport = true;
        parametroDescripcion = null;
        parametroDireccion = null;
        parametroSede = new Sede();
        listaEdificios = null;
        listaEdificiosTabla = null;
        cantidadRegistros = "N/A";
        posicionEdificioTabla = 0;
        tamTotalEdificio = 0;
        parametroEstado = 1;
        bloquearPagAntEdificio = true;
        bloquearPagSigEdificio = true;
        inicializarFiltros();
    }

    public String paginaDetalles() {
        limpiarDatos();
        return "detalles_edificio";
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

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<Edificio> getListaEdificiosTabla() {
        return listaEdificiosTabla;
    }

    public void setListaEdificiosTabla(List<Edificio> listaEdificiosTabla) {
        this.listaEdificiosTabla = listaEdificiosTabla;
    }

    public boolean isBloquearPagSigEdificio() {
        return bloquearPagSigEdificio;
    }

    public void setBloquearPagSigEdificio(boolean bloquearPagSigEdificio) {
        this.bloquearPagSigEdificio = bloquearPagSigEdificio;
    }

    public boolean isBloquearPagAntEdificio() {
        return bloquearPagAntEdificio;
    }

    public void setBloquearPagAntEdificio(boolean bloquearPagAntEdificio) {
        this.bloquearPagAntEdificio = bloquearPagAntEdificio;
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
