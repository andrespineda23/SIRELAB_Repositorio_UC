/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoInsumosBOInterface;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.Proveedor;
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
public class ControllerAdministrarInsumos implements Serializable {

    @EJB
    GestionarRecursoInsumosBOInterface gestionarRecursoInsumosBO;

    private String parametroNombre, parametroCodigo, parametroMarca, parametroModelo;
    private Map<String, String> filtros;
    //
    private List<Insumo> listaInsumos;
    private List<Insumo> listaInsumosTabla;
    private int posicionInsumoTabla;
    private int tamTotalInsumo;
    private boolean bloquearPagSigInsumo, bloquearPagAntInsumo;
    //
    private String altoTabla;
    //
    private boolean activarExport;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarInsumos() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        parametroNombre = null;
        parametroCodigo = null;
        parametroModelo = null;
        parametroMarca = null;
        altoTabla = "150";
        inicializarFiltros();
        listaInsumos = null;
        listaInsumosTabla = null;
        posicionInsumoTabla = 0;
        tamTotalInsumo = 0;
        bloquearPagAntInsumo = true;
        bloquearPagSigInsumo = true;
        activarExport = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroMarca", null);
        filtros.put("parametroModelo", null);
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
        if ((Utilidades.validarNulo(parametroMarca) == true) && (!parametroMarca.isEmpty()) && (parametroMarca.trim().length() > 0)) {
            filtros.put("parametroMarca", parametroMarca.toString());
        }
        if ((Utilidades.validarNulo(parametroModelo) == true) && (!parametroModelo.isEmpty()) && (parametroModelo.trim().length() > 0)) {
            filtros.put("parametroModelo", parametroModelo.toString());
        }

    }

    public void buscarInsumosPorParametros() {
        try {
            inicializarFiltros();
            listaInsumos = null;
            listaInsumos = gestionarRecursoInsumosBO.consultarInsumosPorParametro(filtros);
            if (listaInsumos != null) {
                if (listaInsumos.size() > 0) {
                    activarExport = false;
                    listaInsumosTabla = new ArrayList<Insumo>();
                    tamTotalInsumo = listaInsumos.size();
                    posicionInsumoTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalInsumo);
                    cargarDatosTablaInsumo();
                } else {
                    activarExport = true;
                    listaInsumosTabla = null;
                    tamTotalInsumo = 0;
                    posicionInsumoTabla = 0;
                    bloquearPagAntInsumo = true;
                    cantidadRegistros = String.valueOf(tamTotalInsumo);
                    bloquearPagSigInsumo = true;
                }
            } else {
                listaInsumosTabla = null;
                tamTotalInsumo = 0;
                posicionInsumoTabla = 0;
                bloquearPagAntInsumo = true;
                cantidadRegistros = String.valueOf(tamTotalInsumo);
                bloquearPagSigInsumo = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarInsumos buscarInsumosPorParametros:  " + e.toString(),e);
            logger.error("Error ControllerGestionarInsumos buscarInsumosPorParametros : " + e.toString(),e);
        }
    }

    private void cargarDatosTablaInsumo() {
        if (tamTotalInsumo < 10) {
            for (int i = 0; i < tamTotalInsumo; i++) {
                listaInsumosTabla.add(listaInsumos.get(i));
            }
            bloquearPagSigInsumo = true;
            bloquearPagAntInsumo = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaInsumosTabla.add(listaInsumos.get(i));
            }
            bloquearPagSigInsumo = false;
            bloquearPagAntInsumo = true;
        }
    }

    public void cargarPaginaSiguienteInsumo() {
        listaInsumosTabla = new ArrayList<Insumo>();
        posicionInsumoTabla = posicionInsumoTabla + 10;
        int diferencia = tamTotalInsumo - posicionInsumoTabla;
        if (diferencia > 10) {
            for (int i = posicionInsumoTabla; i < (posicionInsumoTabla + 10); i++) {
                listaInsumosTabla.add(listaInsumos.get(i));
            }
            bloquearPagSigInsumo = false;
            bloquearPagAntInsumo = false;
        } else {
            for (int i = posicionInsumoTabla; i < (posicionInsumoTabla + diferencia); i++) {
                listaInsumosTabla.add(listaInsumos.get(i));
            }
            bloquearPagSigInsumo = true;
            bloquearPagAntInsumo = false;
        }
    }

    public void cargarPaginaAnteriorInsumo() {
        listaInsumosTabla = new ArrayList<Insumo>();
        posicionInsumoTabla = posicionInsumoTabla - 10;
        int diferencia = tamTotalInsumo - posicionInsumoTabla;
        if (diferencia == tamTotalInsumo) {
            for (int i = posicionInsumoTabla; i < (posicionInsumoTabla + 10); i++) {
                listaInsumosTabla.add(listaInsumos.get(i));
            }
            bloquearPagSigInsumo = false;
            bloquearPagAntInsumo = true;
        } else {
            for (int i = posicionInsumoTabla; i < (posicionInsumoTabla + 10); i++) {
                listaInsumosTabla.add(listaInsumos.get(i));
            }
            bloquearPagSigInsumo = false;
            bloquearPagAntInsumo = false;
        }
    }

    public void limpiarDatos() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroModelo = null;
        parametroMarca = null;
        listaInsumos = null;
        listaInsumosTabla = null;
        posicionInsumoTabla = 0;
        tamTotalInsumo = 0;
        bloquearPagAntInsumo = true;
        cantidadRegistros = "N/A";
        bloquearPagSigInsumo = true;
        inicializarFiltros();
    }

    public String limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        cantidadRegistros = "N/A";
        parametroModelo = null;
        parametroMarca = null;
        listaInsumos = null;
        listaInsumosTabla = null;
        posicionInsumoTabla = 0;
        tamTotalInsumo = 0;
        bloquearPagAntInsumo = true;
        bloquearPagSigInsumo = true;
        inicializarFiltros();
        return paginaAnterior;
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "RecursosInsumoPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "RecursosInsumoXLS", false, false, "UTF-8", null, null);
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

    public List<Insumo> getListaInsumos() {
        return listaInsumos;
    }

    public void setListaInsumos(List<Insumo> listaInsumos) {
        this.listaInsumos = listaInsumos;
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

    public List<Insumo> getListaInsumosTabla() {
        return listaInsumosTabla;
    }

    public void setListaInsumosTabla(List<Insumo> listaInsumosTabla) {
        this.listaInsumosTabla = listaInsumosTabla;
    }

    public boolean isBloquearPagSigInsumo() {
        return bloquearPagSigInsumo;
    }

    public void setBloquearPagSigInsumo(boolean bloquearPagSigInsumo) {
        this.bloquearPagSigInsumo = bloquearPagSigInsumo;
    }

    public boolean isBloquearPagAntInsumo() {
        return bloquearPagAntInsumo;
    }

    public void setBloquearPagAntInsumo(boolean bloquearPagAntInsumo) {
        this.bloquearPagAntInsumo = bloquearPagAntInsumo;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
