/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.bo.interfacebo.universidad.GestionarCarrerasBOInterface;
import com.sirelab.entidades.Carrera;
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
public class ControllerAdministrarCarreras implements Serializable {

    @EJB
    GestionarCarrerasBOInterface gestionarCarrerasBO;

    private String parametroNombre, parametroCodigo;
    private List<Facultad> listaFacultades;
    private Facultad parametroFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private boolean activarDepartamento;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<Carrera> listaCarreras;
    private List<Carrera> listaCarrerasTabla;
    private int posicionCarreraTabla;
    private int tamTotalCarrera;
    private boolean bloquearPagSigCarrera, bloquearPagAntCarrera;
    //
    private String altoTabla;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarCarreras() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarDepartamento = true;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroFacultad = new Facultad();
        parametroDepartamento = new Departamento();
        altoTabla = "150";
        inicializarFiltros();
        listaDepartamentos = null;
        listaCarreras = null;
        listaCarrerasTabla = null;
        posicionCarreraTabla = 0;
        tamTotalCarrera = 0;
        bloquearPagAntCarrera = true;
        bloquearPagSigCarrera = true;
        BasicConfigurator.configure();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        filtros.put("parametroDepartamento", null);
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
        if (Utilidades.validarNulo(parametroFacultad) == true) {
            if (parametroFacultad.getIdfacultad() != null) {
                filtros.put("parametroFacultad", parametroFacultad.getIdfacultad().toString());
            }
        }
        if (Utilidades.validarNulo(parametroDepartamento) == true) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
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

    public void buscarCarrerasPorParametros() {
        try {
            inicializarFiltros();
            listaCarreras = null;
            listaCarreras = gestionarCarrerasBO.consultarCarrerasPorParametro(filtros);
            if (listaCarreras != null) {
                if (listaCarreras.size() > 0) {
                    activarExport = false;
                    listaCarrerasTabla = new ArrayList<Carrera>();
                    tamTotalCarrera = listaCarreras.size();
                    posicionCarreraTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalCarrera);
                    cargarDatosTablaCarrera();
                } else {
                    activarExport = true;
                    listaCarrerasTabla = null;
                    tamTotalCarrera = 0;
                    posicionCarreraTabla = 0;
                    bloquearPagAntCarrera = true;
                    cantidadRegistros = String.valueOf(tamTotalCarrera);
                    bloquearPagSigCarrera = true;
                }
            } else {
                listaCarrerasTabla = null;
                tamTotalCarrera = 0;
                posicionCarreraTabla = 0;
                bloquearPagAntCarrera = true;
                cantidadRegistros = String.valueOf(tamTotalCarrera);
                bloquearPagSigCarrera = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarCarreras buscarCarrerasPorParametros:  " + e.toString(),e);
            logger.error("Error ControllerGestionarCarreras buscarCarrerasPorParametros : " + e.toString(),e);
        }
    }

    private void cargarDatosTablaCarrera() {
        if (tamTotalCarrera < 10) {
            for (int i = 0; i < tamTotalCarrera; i++) {
                listaCarrerasTabla.add(listaCarreras.get(i));
            }
            bloquearPagSigCarrera = true;
            bloquearPagAntCarrera = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaCarrerasTabla.add(listaCarreras.get(i));
            }
            bloquearPagSigCarrera = false;
            bloquearPagAntCarrera = true;
        }
    }

    public void cargarPaginaSiguienteCarrera() {
        listaCarrerasTabla = new ArrayList<Carrera>();
        posicionCarreraTabla = posicionCarreraTabla + 10;
        int diferencia = tamTotalCarrera - posicionCarreraTabla;
        if (diferencia > 10) {
            for (int i = posicionCarreraTabla; i < (posicionCarreraTabla + 10); i++) {
                listaCarrerasTabla.add(listaCarreras.get(i));
            }
            bloquearPagSigCarrera = false;
            bloquearPagAntCarrera = false;
        } else {
            for (int i = posicionCarreraTabla; i < (posicionCarreraTabla + diferencia); i++) {
                listaCarrerasTabla.add(listaCarreras.get(i));
            }
            bloquearPagSigCarrera = true;
            bloquearPagAntCarrera = false;
        }
    }

    public void cargarPaginaAnteriorCarrera() {
        listaCarrerasTabla = new ArrayList<Carrera>();
        posicionCarreraTabla = posicionCarreraTabla - 10;
        int diferencia = tamTotalCarrera - posicionCarreraTabla;
        if (diferencia == tamTotalCarrera) {
            for (int i = posicionCarreraTabla; i < (posicionCarreraTabla + 10); i++) {
                listaCarrerasTabla.add(listaCarreras.get(i));
            }
            bloquearPagSigCarrera = false;
            bloquearPagAntCarrera = true;
        } else {
            for (int i = posicionCarreraTabla; i < (posicionCarreraTabla + 10); i++) {
                listaCarrerasTabla.add(listaCarreras.get(i));
            }
            bloquearPagSigCarrera = false;
            bloquearPagAntCarrera = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarDepartamento = true;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroDepartamento = new Departamento();
        parametroFacultad = new Facultad();

        listaDepartamentos = null;
        listaFacultades = null;

        listaCarreras = null;
        listaCarrerasTabla = null;
        posicionCarreraTabla = 0;
        parametroEstado = 1;
        tamTotalCarrera = 0;
        cantidadRegistros = "N/A";
        bloquearPagAntCarrera = true;
        bloquearPagSigCarrera = true;
        inicializarFiltros();
    }

    public void limpiarDatos() {
        activarDepartamento = true;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroEstado = 1;
        parametroDepartamento = new Departamento();
        parametroFacultad = new Facultad();
        listaDepartamentos = null;
        listaCarreras = null;
        listaCarrerasTabla = null;
        posicionCarreraTabla = 0;
        cantidadRegistros = "N/A";
        tamTotalCarrera = 0;
        bloquearPagAntCarrera = true;
        bloquearPagSigCarrera = true;
        inicializarFiltros();
    }

    public void actualizarFacultades() {
        if (Utilidades.validarNulo(parametroFacultad)) {
            parametroDepartamento = new Departamento();
            listaDepartamentos = gestionarCarrerasBO.consultarDepartamentosPorIDFacultad(parametroFacultad.getIdfacultad());
            activarDepartamento = false;
        } else {
            parametroDepartamento = new Departamento();
            listaDepartamentos = null;
            activarDepartamento = true;
        }
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "GestionarCarrerasPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "GestionarCarrerasXLS", false, false, "UTF-8", null, null);
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

    public String getParametroCodigo() {
        return parametroCodigo;
    }

    public void setParametroCodigo(String parametroCodigo) {
        this.parametroCodigo = parametroCodigo;
    }

    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = gestionarCarrerasBO.consultarFacultadesRegistradas();
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

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getParametroDepartamento() {
        return parametroDepartamento;
    }

    public void setParametroDepartamento(Departamento parametroDepartamento) {
        this.parametroDepartamento = parametroDepartamento;
    }

    public boolean isActivarDepartamento() {
        return activarDepartamento;
    }

    public void setActivarDepartamento(boolean activarDepartamento) {
        this.activarDepartamento = activarDepartamento;
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

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<Carrera> getListaCarrerasTabla() {
        return listaCarrerasTabla;
    }

    public void setListaCarrerasTabla(List<Carrera> listaCarrerasTabla) {
        this.listaCarrerasTabla = listaCarrerasTabla;
    }

    public boolean isBloquearPagSigCarrera() {
        return bloquearPagSigCarrera;
    }

    public void setBloquearPagSigCarrera(boolean bloquearPagSigCarrera) {
        this.bloquearPagSigCarrera = bloquearPagSigCarrera;
    }

    public boolean isBloquearPagAntCarrera() {
        return bloquearPagAntCarrera;
    }

    public void setBloquearPagAntCarrera(boolean bloquearPagAntCarrera) {
        this.bloquearPagAntCarrera = bloquearPagAntCarrera;
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
