/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasUsuarioBOInterface;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerConsultarReservasUsuarios2 implements Serializable {

    @EJB
    AdministrarReservasUsuarioBOInterface administrarReservasUsuarioBO;

    private String parametroNumero, parametroFecha;
    private Map<String, String> filtros;
    private boolean activarExport;
    //
    private List<ReservaModuloLaboratorio> listaReservaModuloLaboratorio;
    private List<ReservaModuloLaboratorio> listaReservaModuloLaboratorioTabla;
    private int posicionReservaModuloLaboratorioTabla;
    private int tamTotalReservaModuloLaboratorio;
    private boolean bloquearPagSigReservaModuloLaboratorio, bloquearPagAntReservaModuloLaboratorio;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerConsultarReservasUsuarios2() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNumero = null;
        inicializarFiltros();
        listaReservaModuloLaboratorio = null;
        listaReservaModuloLaboratorioTabla = null;
        posicionReservaModuloLaboratorioTabla = 0;
        tamTotalReservaModuloLaboratorio = 0;
        bloquearPagSigReservaModuloLaboratorio = true;
        bloquearPagAntReservaModuloLaboratorio = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        parametroFecha = sdf.format(new Date());
        buscarReservasPorParametros();
    }

    public void buscarInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        parametroFecha = sdf.format(new Date());
        buscarReservasPorParametros();
    }

    /**
     * Metodo encargado de inicializar los filtros de busqueda para el proceso
     * de consulta de encargados
     */
    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNumero", null);
        filtros.put("parametroFecha", null);
        agregarFiltrosAdicionales();
    }

    /**
     * Metodo encargado de agregar los valores al filtro de busqueda
     */
    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNumero) == true) && (!parametroNumero.isEmpty()) && (parametroNumero.trim().length() > 0)) {
            filtros.put("parametroNumero", parametroNumero);
        }
        if ((Utilidades.validarNulo(parametroFecha) == true) && (!parametroFecha.isEmpty()) && (parametroFecha.trim().length() > 0)) {
            if (parametroFecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date;
                String date2 = "";
                try {
                    date = sdf.parse(parametroFecha);

                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                    date2 = sdf2.format(date);
                } catch (ParseException ex) {

                }
                filtros.put("parametroFecha", date2);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(new Date());
                filtros.put("parametroFecha", date);
            }

        }
    }

    public void buscarReservasPorParametros() {
        try {
            inicializarFiltros();
            listaReservaModuloLaboratorio = null;
            listaReservaModuloLaboratorio = administrarReservasUsuarioBO.consultarReservasModulo(filtros);
            if (listaReservaModuloLaboratorio != null) {
                if (listaReservaModuloLaboratorio.size() > 0) {
                    activarExport = false;
                    listaReservaModuloLaboratorioTabla = new ArrayList<ReservaModuloLaboratorio>();
                    tamTotalReservaModuloLaboratorio = listaReservaModuloLaboratorio.size();
                    posicionReservaModuloLaboratorioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalReservaModuloLaboratorio);
                    cargarDatosTablaReservaModuloLaboratorio();
                } else {
                    activarExport = true;
                    listaReservaModuloLaboratorioTabla = null;
                    tamTotalReservaModuloLaboratorio = 0;
                    posicionReservaModuloLaboratorioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalReservaModuloLaboratorio);
                    bloquearPagAntReservaModuloLaboratorio = true;
                    bloquearPagSigReservaModuloLaboratorio = true;
                }
            } else {
                listaReservaModuloLaboratorioTabla = null;
                tamTotalReservaModuloLaboratorio = 0;
                posicionReservaModuloLaboratorioTabla = 0;
                bloquearPagAntReservaModuloLaboratorio = true;
                cantidadRegistros = String.valueOf(tamTotalReservaModuloLaboratorio);
                bloquearPagSigReservaModuloLaboratorio = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerConsultarReservasUsuarios2 buscarReservasPorParametros:  " + e.toString(), e);
        }
    }

    private void cargarDatosTablaReservaModuloLaboratorio() {
        if (tamTotalReservaModuloLaboratorio < 10) {
            for (int i = 0; i < tamTotalReservaModuloLaboratorio; i++) {
                listaReservaModuloLaboratorioTabla.add(listaReservaModuloLaboratorio.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = true;
            bloquearPagAntReservaModuloLaboratorio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaReservaModuloLaboratorioTabla.add(listaReservaModuloLaboratorio.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = false;
            bloquearPagAntReservaModuloLaboratorio = true;
        }
    }

    public void cargarPaginaSiguienteReservaModuloLaboratorio() {
        listaReservaModuloLaboratorioTabla = new ArrayList<ReservaModuloLaboratorio>();
        posicionReservaModuloLaboratorioTabla = posicionReservaModuloLaboratorioTabla + 10;
        int diferencia = tamTotalReservaModuloLaboratorio - posicionReservaModuloLaboratorioTabla;
        if (diferencia > 10) {
            for (int i = posicionReservaModuloLaboratorioTabla; i < (posicionReservaModuloLaboratorioTabla + 10); i++) {
                listaReservaModuloLaboratorioTabla.add(listaReservaModuloLaboratorio.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = false;
            bloquearPagAntReservaModuloLaboratorio = false;
        } else {
            for (int i = posicionReservaModuloLaboratorioTabla; i < (posicionReservaModuloLaboratorioTabla + diferencia); i++) {
                listaReservaModuloLaboratorioTabla.add(listaReservaModuloLaboratorio.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = true;
            bloquearPagAntReservaModuloLaboratorio = false;
        }
    }

    public void cargarPaginaAnteriorReservaModuloLaboratorio() {
        listaReservaModuloLaboratorioTabla = new ArrayList<ReservaModuloLaboratorio>();
        posicionReservaModuloLaboratorioTabla = posicionReservaModuloLaboratorioTabla - 10;
        int diferencia = tamTotalReservaModuloLaboratorio - posicionReservaModuloLaboratorioTabla;
        if (diferencia == tamTotalReservaModuloLaboratorio) {
            for (int i = posicionReservaModuloLaboratorioTabla; i < (posicionReservaModuloLaboratorioTabla + 10); i++) {
                listaReservaModuloLaboratorioTabla.add(listaReservaModuloLaboratorio.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = false;
            bloquearPagAntReservaModuloLaboratorio = true;
        } else {
            for (int i = posicionReservaModuloLaboratorioTabla; i < (posicionReservaModuloLaboratorioTabla + 10); i++) {
                listaReservaModuloLaboratorioTabla.add(listaReservaModuloLaboratorio.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = false;
            bloquearPagAntReservaModuloLaboratorio = false;
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public String limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNumero = null;
        parametroFecha = null;
        listaReservaModuloLaboratorio = null;
        listaReservaModuloLaboratorioTabla = null;
        tamTotalReservaModuloLaboratorio = 0;
        posicionReservaModuloLaboratorioTabla = 0;
        bloquearPagAntReservaModuloLaboratorio = true;
        bloquearPagSigReservaModuloLaboratorio = true;
        inicializarFiltros();
        cantidadRegistros = "N/A";
        return paginaAnterior;
    }

    public String paginaDetalles() {
        limpiarDatos();
        return "detallesreservausuario2";
    }

    public void limpiarDatos() {
        activarExport = true;
        parametroNumero = null;
        parametroFecha = null;
        listaReservaModuloLaboratorio = null;
        listaReservaModuloLaboratorioTabla = null;
        tamTotalReservaModuloLaboratorio = 0;
        posicionReservaModuloLaboratorioTabla = 0;
        bloquearPagAntReservaModuloLaboratorio = true;
        bloquearPagSigReservaModuloLaboratorio = true;
        cantidadRegistros = "N/A";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "AdministrarAdministradoresEdificioPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "AdministrarAdministradoresEdificioXLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    // GET - SET
    public String getParametroNumero() {
        return parametroNumero;
    }

    public void setParametroNumero(String parametroNumero) {
        this.parametroNumero = parametroNumero;
    }

    public String getParametroFecha() {
        return parametroFecha;
    }

    public void setParametroFecha(String parametroFecha) {
        this.parametroFecha = parametroFecha;
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

    public List<ReservaModuloLaboratorio> getListaReservaModuloLaboratorio() {
        return listaReservaModuloLaboratorio;
    }

    public void setListaReservaModuloLaboratorio(List<ReservaModuloLaboratorio> listaReservaModuloLaboratorio) {
        this.listaReservaModuloLaboratorio = listaReservaModuloLaboratorio;
    }

    public List<ReservaModuloLaboratorio> getListaReservaModuloLaboratorioTabla() {
        return listaReservaModuloLaboratorioTabla;
    }

    public void setListaReservaModuloLaboratorioTabla(List<ReservaModuloLaboratorio> listaReservaModuloLaboratorioTabla) {
        this.listaReservaModuloLaboratorioTabla = listaReservaModuloLaboratorioTabla;
    }

    public int getPosicionReservaModuloLaboratorioTabla() {
        return posicionReservaModuloLaboratorioTabla;
    }

    public void setPosicionReservaModuloLaboratorioTabla(int posicionReservaModuloLaboratorioTabla) {
        this.posicionReservaModuloLaboratorioTabla = posicionReservaModuloLaboratorioTabla;
    }

    public int getTamTotalReservaModuloLaboratorio() {
        return tamTotalReservaModuloLaboratorio;
    }

    public void setTamTotalReservaModuloLaboratorio(int tamTotalReservaModuloLaboratorio) {
        this.tamTotalReservaModuloLaboratorio = tamTotalReservaModuloLaboratorio;
    }

    public boolean isBloquearPagSigReservaModuloLaboratorio() {
        return bloquearPagSigReservaModuloLaboratorio;
    }

    public void setBloquearPagSigReservaModuloLaboratorio(boolean bloquearPagSigReservaModuloLaboratorio) {
        this.bloquearPagSigReservaModuloLaboratorio = bloquearPagSigReservaModuloLaboratorio;
    }

    public boolean isBloquearPagAntReservaModuloLaboratorio() {
        return bloquearPagAntReservaModuloLaboratorio;
    }

    public void setBloquearPagAntReservaModuloLaboratorio(boolean bloquearPagAntReservaModuloLaboratorio) {
        this.bloquearPagAntReservaModuloLaboratorio = bloquearPagAntReservaModuloLaboratorio;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
