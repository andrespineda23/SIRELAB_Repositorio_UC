/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Controlador: ControllerAdministrarEncargadosLaboratorios Este controlador se
 * encarga del funcionamiento de la pagina
 * administrar_encargadoslaboratorios.xhtml
 *
 * @author ANDRES PINEDA
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarEncargadosLaboratorios implements Serializable {

    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarEncargadosLaboratoriosBO;

    private String parametroNombre, parametroApellido, parametroDocumento, parametroCorreo;
    private List<Facultad> listaFacultades;
    private Facultad parametroFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio parametroLaboratorio;
    private int parametroEstado;
    private Map<String, String> filtros;
    //
    private boolean activoDepartamento, activoLaboratorio;
    //
    private boolean activarExport;
    //
    private List<EncargadoLaboratorio> listaEncargadosLaboratorios;
    private List<EncargadoLaboratorio> filtrarListaEncargadosLaboratorios;

    public ControllerAdministrarEncargadosLaboratorios() {
    }

    @PostConstruct
    public void init() {
        activarExport = true;
        activoDepartamento = true;
        activoLaboratorio = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroFacultad = null;
        parametroDepartamento = null;
        parametroLaboratorio = null;
        parametroEstado = 1;
        listaFacultades = administrarEncargadosLaboratoriosBO.obtenerListaFacultades();
        inicializarFiltros();
        listaEncargadosLaboratorios = null;
        filtrarListaEncargadosLaboratorios = null;
    }

    /**
     * Metodo encargado de inicializar los filtros de busqueda para el proceso
     * de consulta de encargados
     */
    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroApellido", null);
        filtros.put("parametroDocumento", null);
        filtros.put("parametroCorreo", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroFacultad", null);
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroLaboratorio", null);
        agregarFiltrosAdicionales();
    }

    /**
     * Metodo encargado de agregar los valores al filtro de busqueda
     */
    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre);
        }
        if ((Utilidades.validarNulo(parametroApellido) == true) && (!parametroApellido.isEmpty())) {
            filtros.put("parametroApellido", parametroApellido);
        }
        if ((Utilidades.validarNulo(parametroDocumento) == true) && (!parametroDocumento.isEmpty())) {
            filtros.put("parametroDocumento", parametroDocumento);
        }
        if ((Utilidades.validarNulo(parametroCorreo) == true) && (!parametroCorreo.isEmpty())) {
            filtros.put("parametroCorreo", parametroCorreo + "@ucentral.edu.co");
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
        if (null != parametroFacultad) {
            filtros.put("parametroFacultad", parametroFacultad.getIdfacultad().toString());
        }
        if (null != parametroDepartamento) {
            filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
        }
        if (null != parametroLaboratorio) {
            filtros.put("parametroLaboratorio", parametroLaboratorio.getIdlaboratorio().toString());
        }
    }

    /**
     * Metodo encargado de buscar los encargados por medio de los parametros
     * ingresados por el usuario
     */
    public void buscarEncargadosLaboratoriosPorParametros() {
        try {
            //RequestContext context = RequestContext.getCurrentInstance();
            inicializarFiltros();
            listaEncargadosLaboratorios = null;
            listaEncargadosLaboratorios = administrarEncargadosLaboratoriosBO.consultarEncargadoLaboratoriosPorParametro(filtros);
            if (listaEncargadosLaboratorios != null) {
                if (listaEncargadosLaboratorios.size() > 0) {
                    activarExport = false;
                } else {
                    activarExport = true;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta no ha retornado ningun resultado de busqueda.", "Consulta de Personal Laboratorio");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("message", message);
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta no ha retornado ningun resultado de busqueda.", "Consulta de Personal Laboratorio");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("message", message);
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarEncargadosLaboratorios buscarEncargadosLaboratoriosPorParametros : " + e.toString());
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public void limpiarProcesoBusqueda() {
        activarExport = true;
        activoDepartamento = true;
        activoLaboratorio = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroFacultad = null;
        parametroDepartamento = null;
        parametroLaboratorio = null;
        parametroEstado = 1;
        inicializarFiltros();
        listaEncargadosLaboratorios = null;
    }

    /**
     * Metodo encargado de actualizar las facultades
     */
    public void actualizarFacultades() {
        try {
            if (Utilidades.validarNulo(parametroFacultad)) {
                activoDepartamento = false;
                parametroDepartamento = new Departamento();
                listaDepartamentos = administrarEncargadosLaboratoriosBO.obtenerDepartamentosPorIDFacultad(parametroFacultad.getIdfacultad());
                activoLaboratorio = true;
                parametroLaboratorio = new Laboratorio();
                listaLaboratorios = null;
            } else {
                activoDepartamento = true;
                activoLaboratorio = true;
                listaDepartamentos = null;
                parametroDepartamento = new Departamento();
                listaLaboratorios = null;
                parametroLaboratorio = new Laboratorio();
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarEncargadosLaboratorios actualizarFacultades : " + e.toString());
        }
    }

    /**
     * Metodo encargado de actualizar los departamentos
     */
    public void actualizarDepartamentos() {
        try {
            if (Utilidades.validarNulo(parametroDepartamento)) {
                activoLaboratorio = false;
                parametroLaboratorio = new Laboratorio();
                listaLaboratorios = administrarEncargadosLaboratoriosBO.obtenerLaboratoriosPorIDDepartamento(parametroDepartamento.getIddepartamento());
            } else {
                activoLaboratorio = true;
                listaLaboratorios = null;
                parametroLaboratorio = new Laboratorio();
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarEncargadosLaboratorios actualizarDepartamentos : " + e.toString());
        }
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un estudiante
     *
     * @return Pagina detalles
     */
    public String verDetallesEncargadoLaboratorio() {
        limpiarProcesoBusqueda();
        return "detalles_encargadolaboratorio";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "Administrar_EncargadosLaboratorios_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Administrar_EncargadosLaboratorios_XLS", false, false, "UTF-8", null, null);
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

    public String getParametroApellido() {
        return parametroApellido;
    }

    public void setParametroApellido(String parametroApellido) {
        this.parametroApellido = parametroApellido;
    }

    public String getParametroDocumento() {
        return parametroDocumento;
    }

    public void setParametroDocumento(String parametroDocumento) {
        this.parametroDocumento = parametroDocumento;
    }

    public String getParametroCorreo() {
        return parametroCorreo;
    }

    public void setParametroCorreo(String parametroCorreo) {
        this.parametroCorreo = parametroCorreo;
    }

    public List<Facultad> getListaFacultades() {
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

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getParametroLaboratorio() {
        return parametroLaboratorio;
    }

    public void setParametroLaboratorio(Laboratorio parametroLaboratorio) {
        this.parametroLaboratorio = parametroLaboratorio;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public boolean isActivoDepartamento() {
        return activoDepartamento;
    }

    public void setActivoDepartamento(boolean activoDepartamento) {
        this.activoDepartamento = activoDepartamento;
    }

    public boolean isActivoLaboratorio() {
        return activoLaboratorio;
    }

    public void setActivoLaboratorio(boolean activoLaboratorio) {
        this.activoLaboratorio = activoLaboratorio;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<EncargadoLaboratorio> getListaEncargadosLaboratorios() {
        return listaEncargadosLaboratorios;
    }

    public void setListaEncargadosLaboratorios(List<EncargadoLaboratorio> listaEncargadosLaboratorios) {
        this.listaEncargadosLaboratorios = listaEncargadosLaboratorios;
    }

    public List<EncargadoLaboratorio> getFiltrarListaEncargadosLaboratorios() {
        return filtrarListaEncargadosLaboratorios;
    }

    public void setFiltrarListaEncargadosLaboratorios(List<EncargadoLaboratorio> filtrarListaEncargadosLaboratorios) {
        this.filtrarListaEncargadosLaboratorios = filtrarListaEncargadosLaboratorios;
    }

}
