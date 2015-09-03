/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.bo.interfacebo.universidad.GestionarDepartamentosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
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
public class ControllerAdministrarDepartamentos implements Serializable {

    @EJB
    GestionarDepartamentosBOInterface gestionarDepartamentosBO;

    private String parametroNombre, parametroCodigo;
    private List<Facultad> listaFacultades;
    private Facultad parametroFacultad;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<Departamento> listaDepartamentos;
    private List<Departamento> listaDepartamentosTabla;
    private int posicionDepartamentoTabla;
    private int tamTotalDepartamento;
    private boolean bloquearPagSigDepartamento, bloquearPagAntDepartamento;
    //
    private String altoTabla;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarDepartamentos() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNombre = null;
        parametroFacultad = new Facultad();
        listaFacultades = null;
        altoTabla = "150";
        inicializarFiltros();
        listaDepartamentos = null;
        listaDepartamentosTabla = null;
        posicionDepartamentoTabla = 0;
        tamTotalDepartamento = 0;
        bloquearPagAntDepartamento = true;
        bloquearPagSigDepartamento = true;
        BasicConfigurator.configure();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroCodigo", null);
        filtros.put("parametroNombre", null);
        filtros.put("parametroFacultad", null);
        filtros.put("parametroEstado", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty()) && (parametroNombre.trim().length() > 0)) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty()) && (parametroCodigo.trim().length() > 0)) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if (Utilidades.validarNulo(parametroFacultad)) {
            if (parametroFacultad.getIdfacultad() != null) {
                filtros.put("parametroFacultad", parametroFacultad.getIdfacultad().toString());
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

    public void buscarDepartamentosPorParametros() {
        try {
            inicializarFiltros();
            listaDepartamentos = null;
            listaDepartamentos = gestionarDepartamentosBO.consultarDepartamentosPorParametro(filtros);
            if (listaDepartamentos != null) {
                if (listaDepartamentos.size() > 0) {
                    activarExport = false;
                    listaDepartamentosTabla = new ArrayList<Departamento>();
                    tamTotalDepartamento = listaDepartamentos.size();
                    posicionDepartamentoTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalDepartamento);
                    cargarDatosTablaDepartamento();
                } else {
                    activarExport = true;
                    listaDepartamentosTabla = null;
                    tamTotalDepartamento = 0;
                    posicionDepartamentoTabla = 0;
                    bloquearPagAntDepartamento = true;
                    cantidadRegistros = String.valueOf(tamTotalDepartamento);
                    bloquearPagSigDepartamento = true;
                }
            } else {
                listaDepartamentosTabla = null;
                tamTotalDepartamento = 0;
                posicionDepartamentoTabla = 0;
                bloquearPagAntDepartamento = true;
                cantidadRegistros = String.valueOf(tamTotalDepartamento);
                bloquearPagSigDepartamento = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarDepartamentos buscarDepartamentosPorParametros:  " + e.toString());
            System.out.println("Error ControllerAdministrarDepartamentos buscarDepartamentosPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaDepartamento() {
        if (tamTotalDepartamento < 10) {
            for (int i = 0; i < tamTotalDepartamento; i++) {
                listaDepartamentosTabla.add(listaDepartamentos.get(i));
            }
            bloquearPagSigDepartamento = true;
            bloquearPagAntDepartamento = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaDepartamentosTabla.add(listaDepartamentos.get(i));
            }
            bloquearPagSigDepartamento = false;
            bloquearPagAntDepartamento = true;
        }
    }

    public void cargarPaginaSiguienteDepartamento() {
        listaDepartamentosTabla = new ArrayList<Departamento>();
        posicionDepartamentoTabla = posicionDepartamentoTabla + 10;
        int diferencia = tamTotalDepartamento - posicionDepartamentoTabla;
        if (diferencia > 10) {
            for (int i = posicionDepartamentoTabla; i < (posicionDepartamentoTabla + 10); i++) {
                listaDepartamentosTabla.add(listaDepartamentos.get(i));
            }
            bloquearPagSigDepartamento = false;
            bloquearPagAntDepartamento = false;
        } else {
            for (int i = posicionDepartamentoTabla; i < (posicionDepartamentoTabla + diferencia); i++) {
                listaDepartamentosTabla.add(listaDepartamentos.get(i));
            }
            bloquearPagSigDepartamento = true;
            bloquearPagAntDepartamento = false;
        }
    }

    public void cargarPaginaAnteriorDepartamento() {
        listaDepartamentosTabla = new ArrayList<Departamento>();
        posicionDepartamentoTabla = posicionDepartamentoTabla - 10;
        int diferencia = tamTotalDepartamento - posicionDepartamentoTabla;
        if (diferencia == tamTotalDepartamento) {
            for (int i = posicionDepartamentoTabla; i < (posicionDepartamentoTabla + 10); i++) {
                listaDepartamentosTabla.add(listaDepartamentos.get(i));
            }
            bloquearPagSigDepartamento = false;
            bloquearPagAntDepartamento = true;
        } else {
            for (int i = posicionDepartamentoTabla; i < (posicionDepartamentoTabla + 10); i++) {
                listaDepartamentosTabla.add(listaDepartamentos.get(i));
            }
            bloquearPagSigDepartamento = false;
            bloquearPagAntDepartamento = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroFacultad = new Facultad();
        listaDepartamentos = null;
        listaDepartamentosTabla = null;
        posicionDepartamentoTabla = 0;
        tamTotalDepartamento = 0;
        bloquearPagAntDepartamento = true;
        listaFacultades = null;
        bloquearPagSigDepartamento = true;
        cantidadRegistros = "N/A";
        parametroEstado = 1;
        inicializarFiltros();
    }

    public void limpiarDatos() {
        activarExport = true;
        parametroNombre = null;
        parametroFacultad = new Facultad();
        cantidadRegistros = "N/A";
        listaDepartamentos = null;
        listaDepartamentosTabla = null;
        parametroCodigo = null;
        parametroEstado = 1;
        posicionDepartamentoTabla = 0;
        tamTotalDepartamento = 0;
        bloquearPagAntDepartamento = true;
        bloquearPagSigDepartamento = true;
        inicializarFiltros();
    }


    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "GestionarDepartamentosPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "GestionarDepartamentosXLS", false, false, "UTF-8", null, null);
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
        if (null == listaFacultades) {
            listaFacultades = gestionarDepartamentosBO.consultarFacultadesRegistradas();
        }
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

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<Departamento> getListaDepartamentosTabla() {
        return listaDepartamentosTabla;
    }

    public void setListaDepartamentosTabla(List<Departamento> listaDepartamentosTabla) {
        this.listaDepartamentosTabla = listaDepartamentosTabla;
    }

    public boolean isBloquearPagSigDepartamento() {
        return bloquearPagSigDepartamento;
    }

    public void setBloquearPagSigDepartamento(boolean bloquearPagSigDepartamento) {
        this.bloquearPagSigDepartamento = bloquearPagSigDepartamento;
    }

    public boolean isBloquearPagAntDepartamento() {
        return bloquearPagAntDepartamento;
    }

    public void setBloquearPagAntDepartamento(boolean bloquearPagAntDepartamento) {
        this.bloquearPagAntDepartamento = bloquearPagAntDepartamento;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public String getParametroCodigo() {
        return parametroCodigo;
    }

    public void setParametroCodigo(String parametroCodigo) {
        this.parametroCodigo = parametroCodigo;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

}
