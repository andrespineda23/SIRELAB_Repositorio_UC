package com.sirelab.controller.estructurauniversidad;

import com.sirelab.bo.interfacebo.universidad.GestionarAsignaturasBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.PlanEstudios;
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
public class ControllerAdministrarAsignaturas implements Serializable {

    @EJB
    GestionarAsignaturasBOInterface gestionarAsignaturasBO;

    private String parametroNombre, parametroCreditos, parametroCodigo;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<Asignatura> listaAsignaturas;
    private List<Asignatura> listaAsignaturasTabla;
    private int posicionAsignaturaTabla;
    private int tamTotalAsignatura;
    private boolean bloquearPagSigAsignatura, bloquearPagAntAsignatura;
    //
    private String altoTabla;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarAsignaturas() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroCreditos = null;
        parametroEstado = 1;
        altoTabla = "150";
        listaAsignaturas = null;
        listaAsignaturasTabla = null;
        posicionAsignaturaTabla = 0;
        tamTotalAsignatura = 0;
        bloquearPagAntAsignatura = true;
        bloquearPagSigAsignatura = true;
        inicializarFiltros();
        BasicConfigurator.configure();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroCreditos", null);
        filtros.put("parametroNombre", null);
        filtros.put("parametroEstado", null);
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
        if ((Utilidades.validarNulo(parametroCreditos) == true) && (!parametroCreditos.isEmpty()) && (parametroCreditos.trim().length() > 0)) {
            filtros.put("parametroCreditos", parametroCreditos.toString());
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
    }

    public void buscarAsignaturasPorParametros() {
        try {
            inicializarFiltros();
            listaAsignaturas = null;
            listaAsignaturas = gestionarAsignaturasBO.consultarAsignaturasPorParametro(filtros);
            if (listaAsignaturas != null) {
                if (listaAsignaturas.size() > 0) {
                    activarExport = false;
                    listaAsignaturasTabla = new ArrayList<Asignatura>();
                    tamTotalAsignatura = listaAsignaturas.size();
                    posicionAsignaturaTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalAsignatura);
                    cargarDatosTablaAsignatura();
                } else {
                    activarExport = true;
                    listaAsignaturasTabla = null;
                    tamTotalAsignatura = 0;
                    posicionAsignaturaTabla = 0;
                    bloquearPagAntAsignatura = true;
                    cantidadRegistros = String.valueOf(tamTotalAsignatura);
                    bloquearPagSigAsignatura = true;
                }
            } else {
                listaAsignaturasTabla = null;
                tamTotalAsignatura = 0;
                posicionAsignaturaTabla = 0;
                bloquearPagAntAsignatura = true;
                bloquearPagSigAsignatura = true;
                cantidadRegistros = String.valueOf(tamTotalAsignatura);
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarAsignaturas buscarAsignaturasPorParametros:  " + e.toString(),e);
            logger.error("Error ControllerGestionarAsignaturas buscarAsignaturasPorParametros : " + e.toString(),e);
        }
    }

    private void cargarDatosTablaAsignatura() {
        if (tamTotalAsignatura < 10) {
            for (int i = 0; i < tamTotalAsignatura; i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = true;
            bloquearPagAntAsignatura = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = true;
        }
    }

    public void cargarPaginaSiguienteAsignatura() {
        listaAsignaturasTabla = new ArrayList<Asignatura>();
        posicionAsignaturaTabla = posicionAsignaturaTabla + 10;
        int diferencia = tamTotalAsignatura - posicionAsignaturaTabla;
        if (diferencia > 10) {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = false;
        } else {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + diferencia); i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = true;
            bloquearPagAntAsignatura = false;
        }
    }

    public void cargarPaginaAnteriorAsignatura() {
        listaAsignaturasTabla = new ArrayList<Asignatura>();
        posicionAsignaturaTabla = posicionAsignaturaTabla - 10;
        int diferencia = tamTotalAsignatura - posicionAsignaturaTabla;
        if (diferencia == tamTotalAsignatura) {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = true;
        } else {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroEstado = 1;
        parametroCreditos = null;
        listaAsignaturas = null;
        listaAsignaturasTabla = null;
        posicionAsignaturaTabla = 0;
        tamTotalAsignatura = 0;
        bloquearPagAntAsignatura = true;
        bloquearPagSigAsignatura = true;
        cantidadRegistros = "N/A";
        inicializarFiltros();
    }

    public void limpiarDatos() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroEstado = 1;
        parametroCreditos = null;
        listaAsignaturas = null;
        listaAsignaturasTabla = null;
        posicionAsignaturaTabla = 0;
        tamTotalAsignatura = 0;
        bloquearPagAntAsignatura = true;
        bloquearPagSigAsignatura = true;
        inicializarFiltros();
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "GestionarPlanesEstudiosPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "GestionarPlanesEstudiosXLS", false, false, "UTF-8", null, null);
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

    public String getParametroCreditos() {
        return parametroCreditos;
    }

    public void setParametroCreditos(String parametroCreditos) {
        this.parametroCreditos = parametroCreditos;
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

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<Asignatura> getListaAsignaturasTabla() {
        return listaAsignaturasTabla;
    }

    public void setListaAsignaturasTabla(List<Asignatura> listaAsignaturasTabla) {
        this.listaAsignaturasTabla = listaAsignaturasTabla;
    }

    public boolean isBloquearPagSigAsignatura() {
        return bloquearPagSigAsignatura;
    }

    public void setBloquearPagSigAsignatura(boolean bloquearPagSigAsignatura) {
        this.bloquearPagSigAsignatura = bloquearPagSigAsignatura;
    }

    public boolean isBloquearPagAntAsignatura() {
        return bloquearPagAntAsignatura;
    }

    public void setBloquearPagAntAsignatura(boolean bloquearPagAntAsignatura) {
        this.bloquearPagAntAsignatura = bloquearPagAntAsignatura;
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
