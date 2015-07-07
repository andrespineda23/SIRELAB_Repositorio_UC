/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEntidadesExternasBOInterface;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
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
 * Controlador: ControllerAdministrarEntidadesExternas Este controlador se
 * encarga de administrar el funcionamiento de la pagina
 * administrar_entidadesexternas.xhtml
 *
 * @author ANDRES PINEDA
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarEntidadesExternas implements Serializable {

    @EJB
    AdministrarEntidadesExternasBOInterface administrarEntidadesExternasBO;

    private String parametroNombre, parametroApellido, parametroDocumento, parametroCorreo;
    private int parametroEstado;
    private String parametroIDEntidad, parametroNombreEntidad, parametroEmailEntidad;
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<EntidadExterna> listaEntidadesExternas;
    private List<EntidadExterna> listaEntidadesExternasTabla;
    private int posicionEntidadExternaTabla;
    private int tamTotalEntidadExterna;
    private boolean bloquearPagSigEntidadExterna, bloquearPagAntEntidadExterna;
    //
    private String paginaAnterior;

    public ControllerAdministrarEntidadesExternas() {
    }

    @PostConstruct
    public void init() {
        activarExport = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroIDEntidad = null;
        parametroNombreEntidad = null;
        parametroEmailEntidad = null;
        inicializarFiltros();
        listaEntidadesExternas = null;
        listaEntidadesExternasTabla = null;
        posicionEntidadExternaTabla = 0;
        tamTotalEntidadExterna = 0;
        bloquearPagAntEntidadExterna = true;
        bloquearPagSigEntidadExterna = true;
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    /**
     * Metodo encargado de inicializar los filtros de busqueda para el proceso
     * de consulta de entidad externas
     */
    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroApellido", null);
        filtros.put("parametroDocumento", null);
        filtros.put("parametroCorreo", null);
        filtros.put("parametroIDEntidad", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroNombreEntidad", null);
        filtros.put("parametroEmailEntidad", null);
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
            filtros.put("parametroCorreo", parametroCorreo);
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
        if ((Utilidades.validarNulo(parametroIDEntidad) == true) && (!parametroIDEntidad.isEmpty())) {
            filtros.put("parametroIDEntidad", parametroIDEntidad);
        }
        if ((Utilidades.validarNulo(parametroNombreEntidad) == true) && (!parametroNombreEntidad.isEmpty())) {
            filtros.put("parametroNombreEntidad", parametroNombreEntidad);
        }
        if ((Utilidades.validarNulo(parametroEmailEntidad) == true) && (!parametroEmailEntidad.isEmpty())) {
            filtros.put("parametroEmailEntidad", parametroEmailEntidad);
        }
    }

    /**
     * Metodo encargado de buscar las entidades externas por medio de los
     * parametros ingresados por el usuario
     */
    public void buscarEntidadesExternasPorParametros() {
        try {
            inicializarFiltros();
            listaEntidadesExternas = null;
            listaEntidadesExternas = administrarEntidadesExternasBO.consultarEntidadesExternasPorParametro(filtros);
            if (listaEntidadesExternas != null) {
                if (listaEntidadesExternas.size() > 0) {
                    activarExport = false;
                    listaEntidadesExternasTabla = new ArrayList<EntidadExterna>();
                    tamTotalEntidadExterna = listaEntidadesExternas.size();
                    posicionEntidadExternaTabla = 0;
                    cargarDatosTablaEntidadExterna();
                } else {
                    activarExport = true;
                    listaEntidadesExternasTabla = null;
                    tamTotalEntidadExterna = 0;
                    posicionEntidadExternaTabla = 0;
                    bloquearPagAntEntidadExterna = true;
                    bloquearPagSigEntidadExterna = true;
                }
            } else {
                listaEntidadesExternasTabla = null;
                tamTotalEntidadExterna = 0;
                posicionEntidadExternaTabla = 0;
                bloquearPagAntEntidadExterna = true;
                bloquearPagSigEntidadExterna = true;
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarEntidadesExternas buscarEntidadesExternasPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaEntidadExterna() {
        if (tamTotalEntidadExterna < 10) {
            for (int i = 0; i < tamTotalEntidadExterna; i++) {
                listaEntidadesExternasTabla.add(listaEntidadesExternas.get(i));
            }
            bloquearPagSigEntidadExterna = true;
            bloquearPagAntEntidadExterna = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEntidadesExternasTabla.add(listaEntidadesExternas.get(i));
            }
            bloquearPagSigEntidadExterna = false;
            bloquearPagAntEntidadExterna = true;
        }
    }

    public void cargarPaginaSiguienteEntidadExterna() {
        listaEntidadesExternasTabla = new ArrayList<EntidadExterna>();
        posicionEntidadExternaTabla = posicionEntidadExternaTabla + 10;
        int diferencia = tamTotalEntidadExterna - posicionEntidadExternaTabla;
        if (diferencia > 10) {
            for (int i = posicionEntidadExternaTabla; i < (posicionEntidadExternaTabla + 10); i++) {
                listaEntidadesExternasTabla.add(listaEntidadesExternas.get(i));
            }
            bloquearPagSigEntidadExterna = false;
            bloquearPagAntEntidadExterna = false;
        } else {
            for (int i = posicionEntidadExternaTabla; i < (posicionEntidadExternaTabla + diferencia); i++) {
                listaEntidadesExternasTabla.add(listaEntidadesExternas.get(i));
            }
            bloquearPagSigEntidadExterna = true;
            bloquearPagAntEntidadExterna = false;
        }
    }

    public void cargarPaginaAnteriorEntidadExterna() {
        listaEntidadesExternasTabla = new ArrayList<EntidadExterna>();
        posicionEntidadExternaTabla = posicionEntidadExternaTabla - 10;
        int diferencia = tamTotalEntidadExterna - posicionEntidadExternaTabla;
        if (diferencia == tamTotalEntidadExterna) {
            for (int i = posicionEntidadExternaTabla; i < (posicionEntidadExternaTabla + 10); i++) {
                listaEntidadesExternasTabla.add(listaEntidadesExternas.get(i));
            }
            bloquearPagSigEntidadExterna = false;
            bloquearPagAntEntidadExterna = true;
        } else {
            for (int i = posicionEntidadExternaTabla; i < (posicionEntidadExternaTabla + 10); i++) {
                listaEntidadesExternasTabla.add(listaEntidadesExternas.get(i));
            }
            bloquearPagSigEntidadExterna = false;
            bloquearPagAntEntidadExterna = false;
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public String limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroEmailEntidad = null;
        parametroNombreEntidad = null;
        parametroIDEntidad = null;
        parametroEstado = 1;
        inicializarFiltros();
        listaEntidadesExternas = null;
        listaEntidadesExternasTabla = null;
        bloquearPagAntEntidadExterna = true;
        bloquearPagSigEntidadExterna = true;
        posicionEntidadExternaTabla = 0;
        tamTotalEntidadExterna = 0;
        return paginaAnterior;
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un entidadexterna
     */
    public String verDetallesEntidadExterna() {
        limpiarProcesoBusqueda();
        return "detalles_entidadexterna";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "Administrar_EntidadesExternas_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Administrar_EntidadesExternas_XLS", false, false, "UTF-8", null, null);
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

    public String getParametroIDEntidad() {
        return parametroIDEntidad;
    }

    public void setParametroIDEntidad(String parametroIDEntidad) {
        this.parametroIDEntidad = parametroIDEntidad;
    }

    public String getParametroNombreEntidad() {
        return parametroNombreEntidad;
    }

    public void setParametroNombreEntidad(String parametroNombreEntidad) {
        this.parametroNombreEntidad = parametroNombreEntidad;
    }

    public String getParametroEmailEntidad() {
        return parametroEmailEntidad;
    }

    public void setParametroEmailEntidad(String parametroEmailEntidad) {
        this.parametroEmailEntidad = parametroEmailEntidad;
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

    public List<EntidadExterna> getListaEntidadesExternas() {
        return listaEntidadesExternas;
    }

    public void setListaEntidadesExternas(List<EntidadExterna> listaEntidadesExternas) {
        this.listaEntidadesExternas = listaEntidadesExternas;
    }

    public List<EntidadExterna> getListaEntidadesExternasTabla() {
        return listaEntidadesExternasTabla;
    }

    public void setListaEntidadesExternasTabla(List<EntidadExterna> listaEntidadesExternasTabla) {
        this.listaEntidadesExternasTabla = listaEntidadesExternasTabla;
    }

    public boolean isBloquearPagSigEntidadExterna() {
        return bloquearPagSigEntidadExterna;
    }

    public void setBloquearPagSigEntidadExterna(boolean bloquearPagSigEntidadExterna) {
        this.bloquearPagSigEntidadExterna = bloquearPagSigEntidadExterna;
    }

    public boolean isBloquearPagAntEntidadExterna() {
        return bloquearPagAntEntidadExterna;
    }

    public void setBloquearPagAntEntidadExterna(boolean bloquearPagAntEntidadExterna) {
        this.bloquearPagAntEntidadExterna = bloquearPagAntEntidadExterna;
    }

}
