package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarFacultadesBOInterface;
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
public class ControllerAdministrarFacultades implements Serializable {

    @EJB
    GestionarFacultadesBOInterface gestionarFacultadBO;

    private String parametroNombre, parametroCodigo;
    private Map<String, String> filtros;
    //
    private List<Facultad> listaFacultades;
    private List<Facultad> listaFacultadesTabla;
    private int posicionFacultadTabla;
    private int tamTotalFacultad;
    private boolean bloquearPagSigFacultad, bloquearPagAntFacultad;
    //
    private String altoTabla;
    //
    private boolean activarExport;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarFacultades() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        parametroNombre = null;
        parametroCodigo = null;
        altoTabla = "150";
        inicializarFiltros();
        listaFacultades = null;
        listaFacultadesTabla = null;
        posicionFacultadTabla = 0;
        tamTotalFacultad = 0;
        bloquearPagAntFacultad = true;
        bloquearPagSigFacultad = true;
        activarExport = true;
        BasicConfigurator.configure();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
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
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
    }

    public void buscarFacultadesPorParametros() {
        try {
            inicializarFiltros();
            listaFacultades = null;
            listaFacultades = gestionarFacultadBO.consultarFacultadesPorParametro(filtros);
            if (listaFacultades != null) {
                if (listaFacultades.size() > 0) {
                    activarExport = false;
                    listaFacultadesTabla = new ArrayList<Facultad>();
                    tamTotalFacultad = listaFacultades.size();
                    posicionFacultadTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalFacultad);
                    cargarDatosTablaFacultad();
                } else {
                    activarExport = true;
                    listaFacultadesTabla = null;
                    tamTotalFacultad = 0;
                    posicionFacultadTabla = 0;
                    bloquearPagAntFacultad = true;
                    cantidadRegistros = String.valueOf(tamTotalFacultad);
                    bloquearPagSigFacultad = true;
                }
            } else {
                listaFacultadesTabla = null;
                tamTotalFacultad = 0;
                posicionFacultadTabla = 0;
                bloquearPagAntFacultad = true;
                cantidadRegistros = String.valueOf(tamTotalFacultad);
                bloquearPagSigFacultad = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarFacultades buscarFacultadesPorParametros:  " + e.toString());
            System.out.println("Error ControllerGestionarFacultades buscarFacultadesPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaFacultad() {
        if (tamTotalFacultad < 10) {
            for (int i = 0; i < tamTotalFacultad; i++) {
                listaFacultadesTabla.add(listaFacultades.get(i));
            }
            bloquearPagSigFacultad = true;
            bloquearPagAntFacultad = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaFacultadesTabla.add(listaFacultades.get(i));
            }
            bloquearPagSigFacultad = false;
            bloquearPagAntFacultad = true;
        }
    }

    public void cargarPaginaSiguienteFacultad() {
        listaFacultadesTabla = new ArrayList<Facultad>();
        posicionFacultadTabla = posicionFacultadTabla + 10;
        int diferencia = tamTotalFacultad - posicionFacultadTabla;
        if (diferencia > 10) {
            for (int i = posicionFacultadTabla; i < (posicionFacultadTabla + 10); i++) {
                listaFacultadesTabla.add(listaFacultades.get(i));
            }
            bloquearPagSigFacultad = false;
            bloquearPagAntFacultad = false;
        } else {
            for (int i = posicionFacultadTabla; i < (posicionFacultadTabla + diferencia); i++) {
                listaFacultadesTabla.add(listaFacultades.get(i));
            }
            bloquearPagSigFacultad = true;
            bloquearPagAntFacultad = false;
        }
    }

    public void cargarPaginaAnteriorFacultad() {
        listaFacultadesTabla = new ArrayList<Facultad>();
        posicionFacultadTabla = posicionFacultadTabla - 10;
        int diferencia = tamTotalFacultad - posicionFacultadTabla;
        if (diferencia == tamTotalFacultad) {
            for (int i = posicionFacultadTabla; i < (posicionFacultadTabla + 10); i++) {
                listaFacultadesTabla.add(listaFacultades.get(i));
            }
            bloquearPagSigFacultad = false;
            bloquearPagAntFacultad = true;
        } else {
            for (int i = posicionFacultadTabla; i < (posicionFacultadTabla + 10); i++) {
                listaFacultadesTabla.add(listaFacultades.get(i));
            }
            bloquearPagSigFacultad = false;
            bloquearPagAntFacultad = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroEstado = 1;
        listaFacultades = null;
        listaFacultadesTabla = null;
        posicionFacultadTabla = 0;
        tamTotalFacultad = 0;
        bloquearPagAntFacultad = true;
        cantidadRegistros = "N/A";
        bloquearPagSigFacultad = true;
        inicializarFiltros();
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

    public List<Facultad> getListaFacultadesTabla() {
        return listaFacultadesTabla;
    }

    public void setListaFacultadesTabla(List<Facultad> listaFacultadesTabla) {
        this.listaFacultadesTabla = listaFacultadesTabla;
    }

    public boolean isBloquearPagSigFacultad() {
        return bloquearPagSigFacultad;
    }

    public void setBloquearPagSigFacultad(boolean bloquearPagSigFacultad) {
        this.bloquearPagSigFacultad = bloquearPagSigFacultad;
    }

    public boolean isBloquearPagAntFacultad() {
        return bloquearPagAntFacultad;
    }

    public void setBloquearPagAntFacultad(boolean bloquearPagAntFacultad) {
        this.bloquearPagAntFacultad = bloquearPagAntFacultad;
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
