/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoManualesBOInterface;
import com.sirelab.entidades.Manual;
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
public class ControllerAdministrarManuales implements Serializable {

    @EJB
    GestionarRecursoManualesBOInterface gestionarRecursoManualBO;

    private String parametroNombre, parametroTipo, parametroUbicacion;
    //
    private Map<String, String> filtros;
    //    private boolean activarExport;
    //
    private List<Manual> listaManuales;
    private List<Manual> listaManualesTabla;
    private int posicionManualTabla;
    private int tamTotalManual;
    private boolean bloquearPagSigManual, bloquearPagAntManual;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private boolean activarUbicacion;

    public ControllerAdministrarManuales() {
    }

    @PostConstruct
    public void init() {
        activarUbicacion = false;
        cantidadRegistros = "N/A";
        parametroNombre = null;
        parametroTipo = null;
        parametroUbicacion = null;
        inicializarFiltros();
        listaManuales = null;
        listaManualesTabla = null;
        posicionManualTabla = 0;
        tamTotalManual = 0;
        bloquearPagAntManual = true;
        bloquearPagSigManual = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroTipo", null);
        filtros.put("parametroUbicacion", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())  && (parametroNombre.trim().length() > 0)) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroTipo) == true) && (!parametroTipo.isEmpty())  && (parametroTipo.trim().length() > 0)) {
            filtros.put("parametroTipo", parametroTipo.toString());
        }
        if ((Utilidades.validarNulo(parametroUbicacion) == true) && (!parametroUbicacion.isEmpty())  && (parametroUbicacion.trim().length() > 0)) {
            filtros.put("parametroUbicacion", parametroUbicacion.toString());
        }
    }

    public void buscarManualesPorParametros() {
        try {
            inicializarFiltros();
            listaManuales = null;
            listaManuales = gestionarRecursoManualBO.consultarManualesPorParametro(filtros);
            if (listaManuales != null) {
                if (listaManuales.size() > 0) {
                    listaManualesTabla = new ArrayList<Manual>();
                    tamTotalManual = listaManuales.size();
                    posicionManualTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalManual);
                    cargarDatosTablaManual();
                } else {
                    listaManualesTabla = null;
                    tamTotalManual = 0;
                    posicionManualTabla = 0;
                    bloquearPagAntManual = true;
                    bloquearPagSigManual = true;
                    cantidadRegistros = String.valueOf(tamTotalManual);
                }
            } else {
                listaManualesTabla = null;
                tamTotalManual = 0;
                posicionManualTabla = 0;
                bloquearPagAntManual = true;
                bloquearPagSigManual = true;
                cantidadRegistros = String.valueOf(tamTotalManual);
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarManuales buscarLaboratoriosPorParametros:  " + e.toString(),e);
            logger.error("Error ControllerAdministrarManuales buscarLaboratoriosPorParametros : " + e.toString(),e);
        }
    }

    private void cargarDatosTablaManual() {
        if (tamTotalManual < 10) {
            for (int i = 0; i < tamTotalManual; i++) {
                listaManualesTabla.add(listaManuales.get(i));
            }
            bloquearPagSigManual = true;
            bloquearPagAntManual = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaManualesTabla.add(listaManuales.get(i));
            }
            bloquearPagSigManual = false;
            bloquearPagAntManual = true;
        }
    }

    public void cargarPaginaSiguienteManual() {
        listaManualesTabla = new ArrayList<Manual>();
        posicionManualTabla = posicionManualTabla + 10;
        int diferencia = tamTotalManual - posicionManualTabla;
        if (diferencia > 10) {
            for (int i = posicionManualTabla; i < (posicionManualTabla + 10); i++) {
                listaManualesTabla.add(listaManuales.get(i));
            }
            bloquearPagSigManual = false;
            bloquearPagAntManual = false;
        } else {
            for (int i = posicionManualTabla; i < (posicionManualTabla + diferencia); i++) {
                listaManualesTabla.add(listaManuales.get(i));
            }
            bloquearPagSigManual = true;
            bloquearPagAntManual = false;
        }
    }

    public void cargarPaginaAnteriorManual() {
        listaManualesTabla = new ArrayList<Manual>();
        posicionManualTabla = posicionManualTabla - 10;
        int diferencia = tamTotalManual - posicionManualTabla;
        if (diferencia == tamTotalManual) {
            for (int i = posicionManualTabla; i < (posicionManualTabla + 10); i++) {
                listaManualesTabla.add(listaManuales.get(i));
            }
            bloquearPagSigManual = false;
            bloquearPagAntManual = true;
        } else {
            for (int i = posicionManualTabla; i < (posicionManualTabla + 10); i++) {
                listaManualesTabla.add(listaManuales.get(i));
            }
            bloquearPagSigManual = false;
            bloquearPagAntManual = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        parametroNombre = null;
        parametroTipo = null;
        parametroUbicacion = null;
        activarUbicacion = false;
        listaManuales = null;
        listaManualesTabla = null;
        posicionManualTabla = 0;
        tamTotalManual = 0;
        bloquearPagAntManual = true;
        bloquearPagSigManual = true;
        cantidadRegistros = "N/A";
        inicializarFiltros();
        return paginaAnterior;
    }

    public void limpiarDatos() {
        parametroNombre = null;
        parametroTipo = null;
        parametroUbicacion = null;
        listaManuales = null;
        listaManualesTabla = null;
        posicionManualTabla = 0;
        tamTotalManual = 0;
        bloquearPagAntManual = true;
        activarUbicacion = false;
        bloquearPagSigManual = true;
        cantidadRegistros = "N/A";
        inicializarFiltros();
    }

    public void actualizarTipo() {
        if (null != parametroTipo) {
            if ("DIGITAL".equalsIgnoreCase(parametroTipo)) {
                activarUbicacion = true;
            } else {
                activarUbicacion = false;
            }
        } else {
            activarUbicacion = false;
        }
    }

    // GET - SET
    public boolean isActivarUbicacion() {
        return activarUbicacion;
    }

    public void setActivarUbicacion(boolean activarUbicacion) {
        this.activarUbicacion = activarUbicacion;
    }

    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
    }

    public String getParametroTipo() {
        return parametroTipo;
    }

    public void setParametroTipo(String parametroTipo) {
        this.parametroTipo = parametroTipo;
    }

    public String getParametroUbicacion() {
        return parametroUbicacion;
    }

    public void setParametroUbicacion(String parametroUbicacion) {
        this.parametroUbicacion = parametroUbicacion;
    }

    public List<Manual> getListaManuales() {
        return listaManuales;
    }

    public void setListaManuales(List<Manual> listaManuales) {
        this.listaManuales = listaManuales;
    }

    public List<Manual> getListaManualesTabla() {
        return listaManualesTabla;
    }

    public void setListaManualesTabla(List<Manual> listaManualesTabla) {
        this.listaManualesTabla = listaManualesTabla;
    }

    public int getPosicionManualTabla() {
        return posicionManualTabla;
    }

    public void setPosicionManualTabla(int posicionManualTabla) {
        this.posicionManualTabla = posicionManualTabla;
    }

    public int getTamTotalManual() {
        return tamTotalManual;
    }

    public void setTamTotalManual(int tamTotalManual) {
        this.tamTotalManual = tamTotalManual;
    }

    public boolean isBloquearPagSigManual() {
        return bloquearPagSigManual;
    }

    public void setBloquearPagSigManual(boolean bloquearPagSigManual) {
        this.bloquearPagSigManual = bloquearPagSigManual;
    }

    public boolean isBloquearPagAntManual() {
        return bloquearPagAntManual;
    }

    public void setBloquearPagAntManual(boolean bloquearPagAntManual) {
        this.bloquearPagAntManual = bloquearPagAntManual;
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

}
