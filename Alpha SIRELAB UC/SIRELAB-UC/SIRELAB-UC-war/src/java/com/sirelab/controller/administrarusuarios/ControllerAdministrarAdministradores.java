/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresBOInterface;
import com.sirelab.entidades.Persona;
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
 * Controlador: ControllerAdministrarAdministradores Este controlador esta
 * encargado de administrar los adminitradores del sistema de información
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarAdministradores implements Serializable {

    @EJB
    AdministrarAdministradoresBOInterface administrarAdministradoresBO;

    private String parametroNombre, parametroApellido, parametroDocumento, parametroCorreo;
    private int parametroEstado;
    private Map<String, String> filtros;
    //
    private boolean activoDepartamento;
    //
    private boolean activarExport;
    //
    private List<Persona> listaAdministradores;
    private List<Persona> listaAdministradoresTabla;
    private int posicionAdministradorTabla;
    private int tamTotalAdministrador;
    private boolean bloquearPagSigAdministrador, bloquearPagAntAdministrador;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarAdministradores() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        inicializarFiltros();
        listaAdministradores = null;
        listaAdministradoresTabla = null;
        posicionAdministradorTabla = 0;
        tamTotalAdministrador = 0;
        bloquearPagAntAdministrador = true;
        bloquearPagSigAdministrador = true;
        BasicConfigurator.configure();
    }

    /**
     * Metodo encargado de inicializar los filtros de busqueda para el proceso
     * de consulta de administradores
     */
    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroApellido", null);
        filtros.put("parametroDocumento", null);
        filtros.put("parametroCorreo", null);
        filtros.put("parametroEstado", null);
        agregarFiltrosAdicionales();
    }

    /**
     * Metodo encargado de agregar los valores al filtro de busqueda
     */
    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())  && (parametroNombre.trim().length() > 0)) {
            filtros.put("parametroNombre", parametroNombre);
        }
        if ((Utilidades.validarNulo(parametroApellido) == true) && (!parametroApellido.isEmpty())  && (parametroApellido.trim().length() > 0)) {
            filtros.put("parametroApellido", parametroApellido);
        }
        if ((Utilidades.validarNulo(parametroDocumento) == true) && (!parametroDocumento.isEmpty())  && (parametroDocumento.trim().length() > 0)) {
            filtros.put("parametroDocumento", parametroDocumento);
        }
        if ((Utilidades.validarNulo(parametroCorreo) == true) && (!parametroCorreo.isEmpty())  && (parametroCorreo.trim().length() > 0)) {
            filtros.put("parametroCorreo", parametroCorreo);
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
    }

    /**
     * Metodo encargado de buscar los administradores por medio de los
     * parametros ingresados por el usuario
     */
    public void buscarAdministradoresPorParametros() {
        try {
            inicializarFiltros();
            listaAdministradores = null;
            listaAdministradores = administrarAdministradoresBO.consultarAdministradoresPorParametro(filtros);
            if (listaAdministradores != null) {
                if (listaAdministradores.size() > 0) {
                    activarExport = false;
                    listaAdministradoresTabla = new ArrayList<Persona>();
                    tamTotalAdministrador = listaAdministradores.size();
                    posicionAdministradorTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalAdministrador);
                    cargarDatosTablaAdministrador();
                } else {
                    activarExport = true;
                    listaAdministradoresTabla = null;
                    tamTotalAdministrador = 0;
                    posicionAdministradorTabla = 0;
                    bloquearPagAntAdministrador = true;
                    bloquearPagSigAdministrador = true;
                    cantidadRegistros = String.valueOf(tamTotalAdministrador);
                }
            } else {
                listaAdministradoresTabla = null;
                tamTotalAdministrador = 0;
                posicionAdministradorTabla = 0;
                bloquearPagAntAdministrador = true;
                cantidadRegistros = String.valueOf(tamTotalAdministrador);
                bloquearPagSigAdministrador = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarAdministradores buscarAdministradoresPorParametros:  " + e.toString());
            System.out.println("Error ControllerAdministrarAdministradores buscarAdministradoresPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaAdministrador() {
        if (tamTotalAdministrador < 10) {
            for (int i = 0; i < tamTotalAdministrador; i++) {
                listaAdministradoresTabla.add(listaAdministradores.get(i));
            }
            bloquearPagSigAdministrador = true;
            bloquearPagAntAdministrador = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaAdministradoresTabla.add(listaAdministradores.get(i));
            }
            bloquearPagSigAdministrador = false;
            bloquearPagAntAdministrador = true;
        }
    }

    public void cargarPaginaSiguienteAdministrador() {
        listaAdministradoresTabla = new ArrayList<Persona>();
        posicionAdministradorTabla = posicionAdministradorTabla + 10;
        int diferencia = tamTotalAdministrador - posicionAdministradorTabla;
        if (diferencia > 10) {
            for (int i = posicionAdministradorTabla; i < (posicionAdministradorTabla + 10); i++) {
                listaAdministradoresTabla.add(listaAdministradores.get(i));
            }
            bloquearPagSigAdministrador = false;
            bloquearPagAntAdministrador = false;
        } else {
            for (int i = posicionAdministradorTabla; i < (posicionAdministradorTabla + diferencia); i++) {
                listaAdministradoresTabla.add(listaAdministradores.get(i));
            }
            bloquearPagSigAdministrador = true;
            bloquearPagAntAdministrador = false;
        }
    }

    public void cargarPaginaAnteriorAdministrador() {
        listaAdministradoresTabla = new ArrayList<Persona>();
        posicionAdministradorTabla = posicionAdministradorTabla - 10;
        int diferencia = tamTotalAdministrador - posicionAdministradorTabla;
        if (diferencia == tamTotalAdministrador) {
            for (int i = posicionAdministradorTabla; i < (posicionAdministradorTabla + 10); i++) {
                listaAdministradoresTabla.add(listaAdministradores.get(i));
            }
            bloquearPagSigAdministrador = false;
            bloquearPagAntAdministrador = true;
        } else {
            for (int i = posicionAdministradorTabla; i < (posicionAdministradorTabla + 10); i++) {
                listaAdministradoresTabla.add(listaAdministradores.get(i));
            }
            bloquearPagSigAdministrador = false;
            bloquearPagAntAdministrador = false;
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroEstado = 1;
        cantidadRegistros = "N/A";
        listaAdministradores = null;
        listaAdministradoresTabla = null;
        posicionAdministradorTabla = 0;
        tamTotalAdministrador = 0;
        bloquearPagAntAdministrador = true;
        bloquearPagSigAdministrador = true;
        inicializarFiltros();
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "AdministrarAdministradoresPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "AdministrarAdministradoresXLS", false, false, "UTF-8", null, null);
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

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<Persona> getListaAdministradores() {
        return listaAdministradores;
    }

    public void setListaAdministradores(List<Persona> listaAdministradores) {
        this.listaAdministradores = listaAdministradores;
    }

    public List<Persona> getListaAdministradoresTabla() {
        return listaAdministradoresTabla;
    }

    public void setListaAdministradoresTabla(List<Persona> listaAdministradoresTabla) {
        this.listaAdministradoresTabla = listaAdministradoresTabla;
    }

    public boolean isBloquearPagSigAdministrador() {
        return bloquearPagSigAdministrador;
    }

    public void setBloquearPagSigAdministrador(boolean bloquearPagSigAdministrador) {
        this.bloquearPagSigAdministrador = bloquearPagSigAdministrador;
    }

    public boolean isBloquearPagAntAdministrador() {
        return bloquearPagAntAdministrador;
    }

    public void setBloquearPagAntAdministrador(boolean bloquearPagAntAdministrador) {
        this.bloquearPagAntAdministrador = bloquearPagAntAdministrador;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
