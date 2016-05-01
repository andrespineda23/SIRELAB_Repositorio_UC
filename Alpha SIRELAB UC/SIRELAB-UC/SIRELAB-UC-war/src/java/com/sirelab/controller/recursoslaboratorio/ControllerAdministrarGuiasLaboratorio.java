/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoGuiasLaboratorioBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.GuiaLaboratorio;
import com.sirelab.utilidades.Utilidades;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarGuiasLaboratorio implements Serializable {

    @EJB
    GestionarRecursoGuiasLaboratorioBOInterface gestionarRecursoGuiaLaboratorioBO;

    private String parametroNombre;
    private String parametroCodigo;
    private List<Asignatura> listaAsignaturas;
    private Asignatura parametroAsignatura;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<GuiaLaboratorio> listaGuiasLaboratorio;
    private List<GuiaLaboratorio> listaGuiasLaboratorioTabla;
    private int posicionGuiaLaboratorioTabla;
    private int tamTotalGuiaLaboratorio;
    private boolean bloquearPagSigGuiaLaboratorio, bloquearPagAntGuiaLaboratorio;
    //
    private String altoTabla;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarGuiasLaboratorio() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroCodigo = null;
        parametroNombre = null;
        parametroAsignatura = null;
        altoTabla = "150";
        inicializarFiltros();
        listaGuiasLaboratorio = null;
        listaGuiasLaboratorioTabla = null;
        posicionGuiaLaboratorioTabla = 0;
        tamTotalGuiaLaboratorio = 0;
        bloquearPagAntGuiaLaboratorio = true;
        bloquearPagSigGuiaLaboratorio = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroAsignatura", null);
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
        if ((Utilidades.validarNulo(parametroAsignatura) == true)) {
            if (parametroAsignatura.getIdasignatura() != null) {
                filtros.put("parametroAsignatura", parametroAsignatura.getIdasignatura().toString());
            }
        }
    }

    public void buscarGuiasLaboratorioPorParametros() {
        try {
            inicializarFiltros();
            listaGuiasLaboratorio = null;
            listaGuiasLaboratorio = gestionarRecursoGuiaLaboratorioBO.consultarGuiasLaboratorioPorParametro(filtros);
            if (listaGuiasLaboratorio != null) {
                if (listaGuiasLaboratorio.size() > 0) {
                    activarExport = false;
                    listaGuiasLaboratorioTabla = new ArrayList<GuiaLaboratorio>();
                    tamTotalGuiaLaboratorio = listaGuiasLaboratorio.size();
                    posicionGuiaLaboratorioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalGuiaLaboratorio);
                    cargarDatosTablaGuiaLaboratorio();
                } else {
                    activarExport = true;
                    listaGuiasLaboratorioTabla = null;
                    tamTotalGuiaLaboratorio = 0;
                    posicionGuiaLaboratorioTabla = 0;
                    bloquearPagAntGuiaLaboratorio = true;
                    bloquearPagSigGuiaLaboratorio = true;
                    cantidadRegistros = String.valueOf(tamTotalGuiaLaboratorio);
                }
            } else {
                listaGuiasLaboratorioTabla = null;
                tamTotalGuiaLaboratorio = 0;
                posicionGuiaLaboratorioTabla = 0;
                bloquearPagAntGuiaLaboratorio = true;
                bloquearPagSigGuiaLaboratorio = true;
                cantidadRegistros = String.valueOf(tamTotalGuiaLaboratorio);
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarGuiasLaboratorio buscarLaboratoriosPorParametros:  " + e.toString(), e);
            logger.error("Error ControllerAdministrarGuiasLaboratorio buscarLaboratoriosPorParametros : " + e.toString(), e);
        }
    }

    private void cargarDatosTablaGuiaLaboratorio() {
        if (tamTotalGuiaLaboratorio < 10) {
            for (int i = 0; i < tamTotalGuiaLaboratorio; i++) {
                listaGuiasLaboratorioTabla.add(listaGuiasLaboratorio.get(i));
            }
            bloquearPagSigGuiaLaboratorio = true;
            bloquearPagAntGuiaLaboratorio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaGuiasLaboratorioTabla.add(listaGuiasLaboratorio.get(i));
            }
            bloquearPagSigGuiaLaboratorio = false;
            bloquearPagAntGuiaLaboratorio = true;
        }
    }

    public void cargarPaginaSiguienteGuiaLaboratorio() {
        listaGuiasLaboratorioTabla = new ArrayList<GuiaLaboratorio>();
        posicionGuiaLaboratorioTabla = posicionGuiaLaboratorioTabla + 10;
        int diferencia = tamTotalGuiaLaboratorio - posicionGuiaLaboratorioTabla;
        if (diferencia > 10) {
            for (int i = posicionGuiaLaboratorioTabla; i < (posicionGuiaLaboratorioTabla + 10); i++) {
                listaGuiasLaboratorioTabla.add(listaGuiasLaboratorio.get(i));
            }
            bloquearPagSigGuiaLaboratorio = false;
            bloquearPagAntGuiaLaboratorio = false;
        } else {
            for (int i = posicionGuiaLaboratorioTabla; i < (posicionGuiaLaboratorioTabla + diferencia); i++) {
                listaGuiasLaboratorioTabla.add(listaGuiasLaboratorio.get(i));
            }
            bloquearPagSigGuiaLaboratorio = true;
            bloquearPagAntGuiaLaboratorio = false;
        }
    }

    public void cargarPaginaAnteriorGuiaLaboratorio() {
        listaGuiasLaboratorioTabla = new ArrayList<GuiaLaboratorio>();
        posicionGuiaLaboratorioTabla = posicionGuiaLaboratorioTabla - 10;
        int diferencia = tamTotalGuiaLaboratorio - posicionGuiaLaboratorioTabla;
        if (diferencia == tamTotalGuiaLaboratorio) {
            for (int i = posicionGuiaLaboratorioTabla; i < (posicionGuiaLaboratorioTabla + 10); i++) {
                listaGuiasLaboratorioTabla.add(listaGuiasLaboratorio.get(i));
            }
            bloquearPagSigGuiaLaboratorio = false;
            bloquearPagAntGuiaLaboratorio = true;
        } else {
            for (int i = posicionGuiaLaboratorioTabla; i < (posicionGuiaLaboratorioTabla + 10); i++) {
                listaGuiasLaboratorioTabla.add(listaGuiasLaboratorio.get(i));
            }
            bloquearPagSigGuiaLaboratorio = false;
            bloquearPagAntGuiaLaboratorio = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarExport = true;
        parametroCodigo = null;
        parametroNombre = null;
        parametroAsignatura = null;
        listaGuiasLaboratorio = null;
        listaGuiasLaboratorioTabla = null;
        listaAsignaturas = null;
        posicionGuiaLaboratorioTabla = 0;
        tamTotalGuiaLaboratorio = 0;
        bloquearPagAntGuiaLaboratorio = true;
        bloquearPagSigGuiaLaboratorio = true;
        cantidadRegistros = "N/A";
        inicializarFiltros();
        return paginaAnterior;
    }

    public void limpiarDatos() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroAsignatura = null;
        listaGuiasLaboratorio = null;
        listaGuiasLaboratorioTabla = null;
        posicionGuiaLaboratorioTabla = 0;
        tamTotalGuiaLaboratorio = 0;
        bloquearPagAntGuiaLaboratorio = true;
        bloquearPagSigGuiaLaboratorio = true;
        cantidadRegistros = "N/A";
        inicializarFiltros();
    }

    public void descargarGuiaLaboratorio(String ruta) throws FileNotFoundException, IOException {
        File ficheroPDF = new File(ruta);
        FacesContext ctx = FacesContext.getCurrentInstance();
        FileInputStream fis = new FileInputStream(ficheroPDF);
        byte[] bytes = new byte[1000];
        int read = 0;
        if (!ctx.getResponseComplete()) {
            String fileName = ficheroPDF.getName();
            String contentType = "application/pdf";
            HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            ServletOutputStream out = response.getOutputStream();
            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            ctx.responseComplete();
        }
    }

    // GET - SET
    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
    }

    public List<Asignatura> getListaAsignaturas() {
        if (listaAsignaturas == null) {
            listaAsignaturas = gestionarRecursoGuiaLaboratorioBO.consultarAsignaturasRegistradas();
        }
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public Asignatura getParametroAsignatura() {
        return parametroAsignatura;
    }

    public void setParametroAsignatura(Asignatura parametroAsignatura) {
        this.parametroAsignatura = parametroAsignatura;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<GuiaLaboratorio> getListaGuiasLaboratorio() {
        return listaGuiasLaboratorio;
    }

    public void setListaGuiasLaboratorio(List<GuiaLaboratorio> listaGuiasLaboratorio) {
        this.listaGuiasLaboratorio = listaGuiasLaboratorio;
    }

    public List<GuiaLaboratorio> getListaGuiasLaboratorioTabla() {
        return listaGuiasLaboratorioTabla;
    }

    public void setListaGuiasLaboratorioTabla(List<GuiaLaboratorio> listaGuiasLaboratorioTabla) {
        this.listaGuiasLaboratorioTabla = listaGuiasLaboratorioTabla;
    }

    public boolean isBloquearPagSigGuiaLaboratorio() {
        return bloquearPagSigGuiaLaboratorio;
    }

    public void setBloquearPagSigGuiaLaboratorio(boolean bloquearPagSigGuiaLaboratorio) {
        this.bloquearPagSigGuiaLaboratorio = bloquearPagSigGuiaLaboratorio;
    }

    public boolean isBloquearPagAntGuiaLaboratorio() {
        return bloquearPagAntGuiaLaboratorio;
    }

    public void setBloquearPagAntGuiaLaboratorio(boolean bloquearPagAntGuiaLaboratorio) {
        this.bloquearPagAntGuiaLaboratorio = bloquearPagAntGuiaLaboratorio;
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

    public String getParametroCodigo() {
        return parametroCodigo;
    }

    public void setParametroCodigo(String parametroCodigo) {
        this.parametroCodigo = parametroCodigo;
    }

}
