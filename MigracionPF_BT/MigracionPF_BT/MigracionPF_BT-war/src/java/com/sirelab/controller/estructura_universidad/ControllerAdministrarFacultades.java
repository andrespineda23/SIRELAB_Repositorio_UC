package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarFacultadesBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarFacultades implements Serializable {

    @EJB
    GestionarFacultadesBOInterface gestionarFacultadBO;

    private String parametroNombre, parametroCodigo;
    private Map<String, String> filtros;
    //
    private List<Facultad> listaFacultades;
    private List<Facultad> filtrarListaFacultades;
    //
    private String altoTabla;
    //
    private boolean activarExport;

    public ControllerAdministrarFacultades() {
    }

    @PostConstruct
    public void init() {
        parametroNombre = null;
        parametroCodigo = null;
        altoTabla = "150";
        inicializarFiltros();
        listaFacultades = null;
        filtrarListaFacultades = null;
        activarExport = true;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty())) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
    }

    public void buscarFacultadesPorParametros() {
        try {
            //RequestContext context = RequestContext.getCurrentInstance();
            inicializarFiltros();
            listaFacultades = null;
            listaFacultades = gestionarFacultadBO.consultarFacultadesPorParametro(filtros);
            if (listaFacultades != null) {
                if (listaFacultades.size() > 0) {
                    //activarFiltrosTabla();
                    activarExport = false;
                } else {
                    activarExport = true;
                    //context.execute("consultaSinDatos.show();");
                }
            } else {
                activarExport = true;
                //context.execute("consultaSinDatos.show();");
            }
            // context.update("form:datosBusqueda");
            //context.update("form:exportarXLS");
            //context.update("form:exportarXML");
            //context.update("form:exportarPDF");
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarFacultades buscarFacultadesPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        if (null != listaFacultades) {
            //desactivarFiltrosTabla();
        }
        parametroNombre = null;
        parametroCodigo = null;
        inicializarFiltros();
        listaFacultades = null;
        //RequestContext.getCurrentInstance().update("formT:form:panelMenu");
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Gestionar_Facultad_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Gestionar_Facultad_XLS", false, false, "UTF-8", null, null);
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

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public List<Facultad> getListaFacultades() {
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public List<Facultad> getFiltrarListaFacultades() {
        return filtrarListaFacultades;
    }

    public void setFiltrarListaFacultades(List<Facultad> filtrarListaFacultades) {
        this.filtrarListaFacultades = filtrarListaFacultades;
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

}
