/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasUsuarioBOInterface;
import com.sirelab.entidades.ReservaSala;
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
public class ControllerConsultarReservasUsuarios implements Serializable {

    @EJB
    AdministrarReservasUsuarioBOInterface administrarReservasUsuarioBO;

    private String parametroNumero, parametroFecha;
    private Map<String, String> filtros;
    private boolean activarExport;
    //
    private List<ReservaSala> listaReservaSala;
    private List<ReservaSala> listaReservaSalaTabla;
    private int posicionReservaSalaTabla;
    private int tamTotalReservaSala;
    private boolean bloquearPagSigReservaSala, bloquearPagAntReservaSala;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerConsultarReservasUsuarios() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNumero = null;
        inicializarFiltros();
        listaReservaSala = null;
        listaReservaSalaTabla = null;
        posicionReservaSalaTabla = 0;
        tamTotalReservaSala = 0;
        bloquearPagSigReservaSala = true;
        bloquearPagAntReservaSala = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        parametroFecha = sdf.format(new Date());
//        buscarReservasPorParametros();
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
                    date2 = sdf.format(date);
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
            listaReservaSala = null;
            listaReservaSala = administrarReservasUsuarioBO.consultarReservasSala(filtros);
            if (listaReservaSala != null) {
                if (listaReservaSala.size() > 0) {
                    activarExport = false;
                    listaReservaSalaTabla = new ArrayList<ReservaSala>();
                    tamTotalReservaSala = listaReservaSala.size();
                    posicionReservaSalaTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalReservaSala);
                    cargarDatosTablaReservaSala();
                } else {
                    activarExport = true;
                    listaReservaSalaTabla = null;
                    tamTotalReservaSala = 0;
                    posicionReservaSalaTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalReservaSala);
                    bloquearPagAntReservaSala = true;
                    bloquearPagSigReservaSala = true;
                }
            } else {
                listaReservaSalaTabla = null;
                tamTotalReservaSala = 0;
                posicionReservaSalaTabla = 0;
                bloquearPagAntReservaSala = true;
                cantidadRegistros = String.valueOf(tamTotalReservaSala);
                bloquearPagSigReservaSala = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarAdministradoresEdificio buscarAdministradoresEdificioPorParametros:  " + e.toString(), e);
        }
    }

    private void cargarDatosTablaReservaSala() {
        if (tamTotalReservaSala < 10) {
            for (int i = 0; i < tamTotalReservaSala; i++) {
                listaReservaSalaTabla.add(listaReservaSala.get(i));
            }
            bloquearPagSigReservaSala = true;
            bloquearPagAntReservaSala = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaReservaSalaTabla.add(listaReservaSala.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = true;
        }
    }

    public void cargarPaginaSiguienteReservaSala() {
        listaReservaSalaTabla = new ArrayList<ReservaSala>();
        posicionReservaSalaTabla = posicionReservaSalaTabla + 10;
        int diferencia = tamTotalReservaSala - posicionReservaSalaTabla;
        if (diferencia > 10) {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservaSalaTabla.add(listaReservaSala.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = false;
        } else {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + diferencia); i++) {
                listaReservaSalaTabla.add(listaReservaSala.get(i));
            }
            bloquearPagSigReservaSala = true;
            bloquearPagAntReservaSala = false;
        }
    }

    public void cargarPaginaAnteriorReservaSala() {
        listaReservaSalaTabla = new ArrayList<ReservaSala>();
        posicionReservaSalaTabla = posicionReservaSalaTabla - 10;
        int diferencia = tamTotalReservaSala - posicionReservaSalaTabla;
        if (diferencia == tamTotalReservaSala) {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservaSalaTabla.add(listaReservaSala.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = true;
        } else {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservaSalaTabla.add(listaReservaSala.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = false;
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
        listaReservaSala = null;
        listaReservaSalaTabla = null;
        tamTotalReservaSala = 0;
        posicionReservaSalaTabla = 0;
        bloquearPagAntReservaSala = true;
        bloquearPagSigReservaSala = true;
        inicializarFiltros();
        cantidadRegistros = "N/A";
        return paginaAnterior;
    }

    public String dirigirPaginaNuevoRegistro() {
        limpiarProcesoBusqueda();
        return "registraradministradoredificio";
    }

    public void limpiarDatos() {
        activarExport = true;
        parametroNumero = null;
        parametroFecha = null;
        listaReservaSala = null;
        listaReservaSalaTabla = null;
        tamTotalReservaSala = 0;
        posicionReservaSalaTabla = 0;
        bloquearPagAntReservaSala = true;
        bloquearPagSigReservaSala = true;
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

    public List<ReservaSala> getListaReservaSala() {
        return listaReservaSala;
    }

    public void setListaReservaSala(List<ReservaSala> listaReservaSala) {
        this.listaReservaSala = listaReservaSala;
    }

    public List<ReservaSala> getListaReservaSalaTabla() {
        return listaReservaSalaTabla;
    }

    public void setListaReservaSalaTabla(List<ReservaSala> listaReservaSalaTabla) {
        this.listaReservaSalaTabla = listaReservaSalaTabla;
    }

    public int getPosicionReservaSalaTabla() {
        return posicionReservaSalaTabla;
    }

    public void setPosicionReservaSalaTabla(int posicionReservaSalaTabla) {
        this.posicionReservaSalaTabla = posicionReservaSalaTabla;
    }

    public int getTamTotalReservaSala() {
        return tamTotalReservaSala;
    }

    public void setTamTotalReservaSala(int tamTotalReservaSala) {
        this.tamTotalReservaSala = tamTotalReservaSala;
    }

    public boolean isBloquearPagSigReservaSala() {
        return bloquearPagSigReservaSala;
    }

    public void setBloquearPagSigReservaSala(boolean bloquearPagSigReservaSala) {
        this.bloquearPagSigReservaSala = bloquearPagSigReservaSala;
    }

    public boolean isBloquearPagAntReservaSala() {
        return bloquearPagAntReservaSala;
    }

    public void setBloquearPagAntReservaSala(boolean bloquearPagAntReservaSala) {
        this.bloquearPagAntReservaSala = bloquearPagAntReservaSala;
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
