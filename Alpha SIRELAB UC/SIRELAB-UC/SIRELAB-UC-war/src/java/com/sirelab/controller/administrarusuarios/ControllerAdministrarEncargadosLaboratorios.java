/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
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
 * Controlador: ControllerAdministrarEncargadosLaboratorios Este controlador se
 * encarga del funcionamiento de la pagina
 * administrarencargadoslaboratorios.xhtml
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
    private List<EncargadoLaboratorio> listaEncargadosLaboratoriosTabla;
    private int posicionEncargadoLaboratorioTabla;
    private int tamTotalEncargadoLaboratorio;
    private boolean bloquearPagSigEncargadoLaboratorio, bloquearPagAntEncargadoLaboratorio;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarEncargadosLaboratorios() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
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
        listaEncargadosLaboratoriosTabla = null;
        posicionEncargadoLaboratorioTabla = 0;
        tamTotalEncargadoLaboratorio = 0;
        bloquearPagSigEncargadoLaboratorio = true;
        bloquearPagAntEncargadoLaboratorio = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
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
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty()) && (parametroNombre.trim().length() > 0)) {
            filtros.put("parametroNombre", parametroNombre);
        }
        if ((Utilidades.validarNulo(parametroApellido) == true) && (!parametroApellido.isEmpty()) && (parametroApellido.trim().length() > 0)) {
            filtros.put("parametroApellido", parametroApellido);
        }
        if ((Utilidades.validarNulo(parametroDocumento) == true) && (!parametroDocumento.isEmpty()) && (parametroDocumento.trim().length() > 0)) {
            filtros.put("parametroDocumento", parametroDocumento);
        }
        if ((Utilidades.validarNulo(parametroCorreo) == true) && (!parametroCorreo.isEmpty()) && (parametroCorreo.trim().length() > 0)) {
            filtros.put("parametroCorreo", parametroCorreo);
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
        if (Utilidades.validarNulo(parametroFacultad)) {
            if (parametroFacultad.getIdfacultad() != null) {
                filtros.put("parametroFacultad", parametroFacultad.getIdfacultad().toString());
            }
        }
        if (Utilidades.validarNulo(parametroDepartamento)) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
            }
        }
        if (Utilidades.validarNulo(parametroLaboratorio)) {
            if (parametroLaboratorio.getIdlaboratorio() != null) {
                filtros.put("parametroLaboratorio", parametroLaboratorio.getIdlaboratorio().toString());
            }
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
                    listaEncargadosLaboratoriosTabla = new ArrayList<EncargadoLaboratorio>();
                    tamTotalEncargadoLaboratorio = listaEncargadosLaboratorios.size();
                    posicionEncargadoLaboratorioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalEncargadoLaboratorio);
                    cargarDatosTablaEncargadoLaboratorio();
                } else {
                    activarExport = true;
                    listaEncargadosLaboratoriosTabla = null;
                    tamTotalEncargadoLaboratorio = 0;
                    posicionEncargadoLaboratorioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalEncargadoLaboratorio);
                    bloquearPagAntEncargadoLaboratorio = true;
                    bloquearPagSigEncargadoLaboratorio = true;
                }
            } else {
                listaEncargadosLaboratoriosTabla = null;
                tamTotalEncargadoLaboratorio = 0;
                posicionEncargadoLaboratorioTabla = 0;
                bloquearPagAntEncargadoLaboratorio = true;
                cantidadRegistros = String.valueOf(tamTotalEncargadoLaboratorio);
                bloquearPagSigEncargadoLaboratorio = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarEncargadosLaboratorios buscarEncargadosLaboratoriosPorParametros:  " + e.toString(),e);
            logger.error("Error ControllerAdministrarEncargadosLaboratorios buscarEncargadosLaboratoriosPorParametros : " + e.toString(),e);
        }
    }

    private void cargarDatosTablaEncargadoLaboratorio() {
        if (tamTotalEncargadoLaboratorio < 10) {
            for (int i = 0; i < tamTotalEncargadoLaboratorio; i++) {
                listaEncargadosLaboratoriosTabla.add(listaEncargadosLaboratorios.get(i));
            }
            bloquearPagSigEncargadoLaboratorio = true;
            bloquearPagAntEncargadoLaboratorio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEncargadosLaboratoriosTabla.add(listaEncargadosLaboratorios.get(i));
            }
            bloquearPagSigEncargadoLaboratorio = false;
            bloquearPagAntEncargadoLaboratorio = true;
        }
    }

    public void cargarPaginaSiguienteEncargadoLaboratorio() {
        listaEncargadosLaboratoriosTabla = new ArrayList<EncargadoLaboratorio>();
        posicionEncargadoLaboratorioTabla = posicionEncargadoLaboratorioTabla + 10;
        int diferencia = tamTotalEncargadoLaboratorio - posicionEncargadoLaboratorioTabla;
        if (diferencia > 10) {
            for (int i = posicionEncargadoLaboratorioTabla; i < (posicionEncargadoLaboratorioTabla + 10); i++) {
                listaEncargadosLaboratoriosTabla.add(listaEncargadosLaboratorios.get(i));
            }
            bloquearPagSigEncargadoLaboratorio = false;
            bloquearPagAntEncargadoLaboratorio = false;
        } else {
            for (int i = posicionEncargadoLaboratorioTabla; i < (posicionEncargadoLaboratorioTabla + diferencia); i++) {
                listaEncargadosLaboratoriosTabla.add(listaEncargadosLaboratorios.get(i));
            }
            bloquearPagSigEncargadoLaboratorio = true;
            bloquearPagAntEncargadoLaboratorio = false;
        }
    }

    public void cargarPaginaAnteriorEncargadoLaboratorio() {
        listaEncargadosLaboratoriosTabla = new ArrayList<EncargadoLaboratorio>();
        posicionEncargadoLaboratorioTabla = posicionEncargadoLaboratorioTabla - 10;
        int diferencia = tamTotalEncargadoLaboratorio - posicionEncargadoLaboratorioTabla;
        if (diferencia == tamTotalEncargadoLaboratorio) {
            for (int i = posicionEncargadoLaboratorioTabla; i < (posicionEncargadoLaboratorioTabla + 10); i++) {
                listaEncargadosLaboratoriosTabla.add(listaEncargadosLaboratorios.get(i));
            }
            bloquearPagSigEncargadoLaboratorio = false;
            bloquearPagAntEncargadoLaboratorio = true;
        } else {
            for (int i = posicionEncargadoLaboratorioTabla; i < (posicionEncargadoLaboratorioTabla + 10); i++) {
                listaEncargadosLaboratoriosTabla.add(listaEncargadosLaboratorios.get(i));
            }
            bloquearPagSigEncargadoLaboratorio = false;
            bloquearPagAntEncargadoLaboratorio = false;
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public String limpiarProcesoBusqueda() {
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
        listaEncargadosLaboratorios = null;
        listaEncargadosLaboratoriosTabla = null;
        tamTotalEncargadoLaboratorio = 0;
        posicionEncargadoLaboratorioTabla = 0;
        bloquearPagAntEncargadoLaboratorio = true;
        bloquearPagSigEncargadoLaboratorio = true;
        listaDepartamentos = null;
        listaFacultades = null;
        listaLaboratorios = null;
        inicializarFiltros();
        cantidadRegistros = "N/A";
        return paginaAnterior;
    }

    public void limpiarDatos() {
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
        listaEncargadosLaboratorios = null;
        listaEncargadosLaboratoriosTabla = null;
        tamTotalEncargadoLaboratorio = 0;
        posicionEncargadoLaboratorioTabla = 0;
        bloquearPagAntEncargadoLaboratorio = true;
        bloquearPagSigEncargadoLaboratorio = true;
        cantidadRegistros = "N/A";
    }

    public String registrarNuevoEncargado() {
        limpiarProcesoBusqueda();
        return "registrarencargadolaboratorio";
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
            logger.error("Error ControllerAdministrarEncargadosLaboratorios actualizarFacultades:  " + e.toString(),e);
            logger.error("Error ControllerAdministrarEncargadosLaboratorios actualizarFacultades : " + e.toString(),e);
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
            logger.error("Error ControllerAdministrarEncargadosLaboratorios actualizarDepartamentos:  " + e.toString(),e);
            logger.error("Error ControllerAdministrarEncargadosLaboratorios actualizarDepartamentos : " + e.toString(),e);
        }
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un estudiante
     *
     * @return Pagina detalles
     */
    public String verDetallesEncargadoLaboratorio() {
        limpiarProcesoBusqueda();
        return "detallesencargadolaboratorio";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "AdministrarEncargadosLaboratoriosPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "AdministrarEncargadosLaboratoriosXLS", false, false, "UTF-8", null, null);
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

    public List<EncargadoLaboratorio> getListaEncargadosLaboratoriosTabla() {
        return listaEncargadosLaboratoriosTabla;
    }

    public void setListaEncargadosLaboratoriosTabla(List<EncargadoLaboratorio> listaEncargadosLaboratoriosTabla) {
        this.listaEncargadosLaboratoriosTabla = listaEncargadosLaboratoriosTabla;
    }

    public boolean isBloquearPagSigEncargadoLaboratorio() {
        return bloquearPagSigEncargadoLaboratorio;
    }

    public void setBloquearPagSigEncargadoLaboratorio(boolean bloquearPagSigEncargadoLaboratorio) {
        this.bloquearPagSigEncargadoLaboratorio = bloquearPagSigEncargadoLaboratorio;
    }

    public boolean isBloquearPagAntEncargadoLaboratorio() {
        return bloquearPagAntEncargadoLaboratorio;
    }

    public void setBloquearPagAntEncargadoLaboratorio(boolean bloquearPagAntEncargadoLaboratorio) {
        this.bloquearPagAntEncargadoLaboratorio = bloquearPagAntEncargadoLaboratorio;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
