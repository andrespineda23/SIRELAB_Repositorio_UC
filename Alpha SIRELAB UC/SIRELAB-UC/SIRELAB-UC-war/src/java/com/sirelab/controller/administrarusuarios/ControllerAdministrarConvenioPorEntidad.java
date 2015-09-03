/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarConveniosPorEntidadBOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarConvenioPorEntidad implements Serializable {

    @EJB
    AdministrarConveniosPorEntidadBOInterface administrarConveniosPorEntidadBO;

    private List<EntidadExterna> listaEntidadesExternas;
    private EntidadExterna parametroEntidadExterna;
    private List<Convenio> listaConvenios;
    private Convenio parametroConvenio;
    private int tipoConsulta;
    private boolean activarEntidadExterna, activarConvenio;

    private int parametroEstado;
    //
    private List<ConvenioPorEntidad> listaConveniosPorEntidad;
    private List<ConvenioPorEntidad> listaConveniosPorEntidadTabla;
    private int posicionConvenioPorEntidadTabla;
    private int tamTotalConvenioPorEntidad;
    private boolean bloquearPagSigConvenioPorEntidad, bloquearPagAntConvenioPorEntidad;
    //
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarConvenioPorEntidad() {
    }

    @PostConstruct
    public void init() {
        activarEntidadExterna = true;
        activarConvenio = true;
        cantidadRegistros = "N/A";
        parametroConvenio = null;
        parametroEntidadExterna = null;
        parametroEstado = 1;
        listaConveniosPorEntidad = null;
        listaConveniosPorEntidadTabla = null;
        posicionConvenioPorEntidadTabla = 0;
        tamTotalConvenioPorEntidad = 0;
        bloquearPagAntConvenioPorEntidad = true;
        bloquearPagSigConvenioPorEntidad = true;
        listaConvenios = null;
        listaEntidadesExternas = null;
        BasicConfigurator.configure();
    }

    public void actualizarTipoConsulta() {
        if (tipoConsulta == 1) {
            activarEntidadExterna = true;
            activarConvenio = true;
        } else {
            if (tipoConsulta == 2) {
                activarEntidadExterna = false;
                activarConvenio = true;
            } else {
                activarEntidadExterna = true;
                activarConvenio = false;
            }
        }
    }

    public void buscarConveniosPorEntidadPorParametros() {
        try {
            listaConveniosPorEntidad = null;
            if (tipoConsulta == 1) {
                listaConveniosPorEntidad = administrarConveniosPorEntidadBO.buscarConveniosPorEntidad();
            } else {
                if (tipoConsulta == 2) {
                    listaConveniosPorEntidad = administrarConveniosPorEntidadBO.buscarConveniosPorEntidadPorIdEntidad(parametroEntidadExterna.getIdentidadexterna());
                } else {
                    listaConveniosPorEntidad = administrarConveniosPorEntidadBO.buscarConveniosPorEntidadPorIdConvenio(parametroConvenio.getIdconvenio());
                }
            }
            if (listaConveniosPorEntidad != null) {
                if (listaConveniosPorEntidad.size() > 0) {
                    listaConveniosPorEntidadTabla = new ArrayList<ConvenioPorEntidad>();
                    tamTotalConvenioPorEntidad = listaConveniosPorEntidad.size();
                    posicionConvenioPorEntidadTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalConvenioPorEntidad);
                    cargarDatosTablaConvenioPorEntidad();
                } else {
                    listaConveniosPorEntidadTabla = null;
                    tamTotalConvenioPorEntidad = 0;
                    posicionConvenioPorEntidadTabla = 0;
                    bloquearPagAntConvenioPorEntidad = true;
                    cantidadRegistros = String.valueOf(tamTotalConvenioPorEntidad);
                    bloquearPagSigConvenioPorEntidad = true;
                }
            } else {
                listaConveniosPorEntidadTabla = null;
                tamTotalConvenioPorEntidad = 0;
                posicionConvenioPorEntidadTabla = 0;
                bloquearPagAntConvenioPorEntidad = true;
                bloquearPagSigConvenioPorEntidad = true;
                cantidadRegistros = String.valueOf(tamTotalConvenioPorEntidad);
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarConveniosPorEntidad buscarConveniosPorEntidadPorParametros:  " + e.toString());
            System.out.println("Error ControllerAdministrarConveniosPorEntidad buscarConveniosPorEntidadPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaConvenioPorEntidad() {
        if (tamTotalConvenioPorEntidad < 10) {
            for (int i = 0; i < tamTotalConvenioPorEntidad; i++) {
                listaConveniosPorEntidadTabla.add(listaConveniosPorEntidad.get(i));
            }
            bloquearPagSigConvenioPorEntidad = true;
            bloquearPagAntConvenioPorEntidad = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaConveniosPorEntidadTabla.add(listaConveniosPorEntidad.get(i));
            }
            bloquearPagSigConvenioPorEntidad = false;
            bloquearPagAntConvenioPorEntidad = true;
        }
    }

    public void cargarPaginaSiguienteConvenioPorEntidad() {
        listaConveniosPorEntidadTabla = new ArrayList<ConvenioPorEntidad>();
        posicionConvenioPorEntidadTabla = posicionConvenioPorEntidadTabla + 10;
        int diferencia = tamTotalConvenioPorEntidad - posicionConvenioPorEntidadTabla;
        if (diferencia > 10) {
            for (int i = posicionConvenioPorEntidadTabla; i < (posicionConvenioPorEntidadTabla + 10); i++) {
                listaConveniosPorEntidadTabla.add(listaConveniosPorEntidad.get(i));
            }
            bloquearPagSigConvenioPorEntidad = false;
            bloquearPagAntConvenioPorEntidad = false;
        } else {
            for (int i = posicionConvenioPorEntidadTabla; i < (posicionConvenioPorEntidadTabla + diferencia); i++) {
                listaConveniosPorEntidadTabla.add(listaConveniosPorEntidad.get(i));
            }
            bloquearPagSigConvenioPorEntidad = true;
            bloquearPagAntConvenioPorEntidad = false;
        }
    }

    public void cargarPaginaAnteriorConvenioPorEntidad() {
        listaConveniosPorEntidadTabla = new ArrayList<ConvenioPorEntidad>();
        posicionConvenioPorEntidadTabla = posicionConvenioPorEntidadTabla - 10;
        int diferencia = tamTotalConvenioPorEntidad - posicionConvenioPorEntidadTabla;
        if (diferencia == tamTotalConvenioPorEntidad) {
            for (int i = posicionConvenioPorEntidadTabla; i < (posicionConvenioPorEntidadTabla + 10); i++) {
                listaConveniosPorEntidadTabla.add(listaConveniosPorEntidad.get(i));
            }
            bloquearPagSigConvenioPorEntidad = false;
            bloquearPagAntConvenioPorEntidad = true;
        } else {
            for (int i = posicionConvenioPorEntidadTabla; i < (posicionConvenioPorEntidadTabla + 10); i++) {
                listaConveniosPorEntidadTabla.add(listaConveniosPorEntidad.get(i));
            }
            bloquearPagSigConvenioPorEntidad = false;
            bloquearPagAntConvenioPorEntidad = false;
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public String limpiarProcesoBusqueda() {
        parametroEstado = 1;
        parametroConvenio = null;
        tipoConsulta = 1;
        activarEntidadExterna = true;
        activarConvenio = true;
        parametroEntidadExterna = null;
        listaConveniosPorEntidad = null;
        listaConveniosPorEntidadTabla = null;
        bloquearPagAntConvenioPorEntidad = true;
        bloquearPagSigConvenioPorEntidad = true;
        posicionConvenioPorEntidadTabla = 0;
        tamTotalConvenioPorEntidad = 0;
        cantidadRegistros = "N/A";
        listaConvenios = null;
        listaEntidadesExternas = null;
        return "administrarentidadesexternas";
    }

    public void limpiarDatos() {
        parametroEstado = 1;
        tipoConsulta = 1;
        parametroConvenio = null;
        activarEntidadExterna = true;
        activarConvenio = true;
        parametroEntidadExterna = null;
        listaConveniosPorEntidad = null;
        listaConveniosPorEntidadTabla = null;
        bloquearPagAntConvenioPorEntidad = true;
        bloquearPagSigConvenioPorEntidad = true;
        posicionConvenioPorEntidadTabla = 0;
        tamTotalConvenioPorEntidad = 0;
        cantidadRegistros = "N/A";
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un entidadexterna
     */
    public String verDetallesConvenioPorEntidad() {
        limpiarProcesoBusqueda();
        return "detallesconvenioporentidad";
    }
    
    public String verPersonaContacto() {
        limpiarProcesoBusqueda();
        return "administrarpersonacontacto";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "AdministrarConveniosPorEntidadPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "AdministrarConveniosPorEntidadXLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    // GET - SET
    public List<EntidadExterna> getListaEntidadesExternas() {
        if (null == listaEntidadesExternas) {
            listaEntidadesExternas = administrarConveniosPorEntidadBO.consultarEntidadesExternasRegistradas();
        }
        return listaEntidadesExternas;
    }

    public void setListaEntidadesExternas(List<EntidadExterna> listaEntidadesExternas) {
        this.listaEntidadesExternas = listaEntidadesExternas;
    }

    public EntidadExterna getParametroEntidadExterna() {
        return parametroEntidadExterna;
    }

    public void setParametroEntidadExterna(EntidadExterna parametroEntidadExterna) {
        this.parametroEntidadExterna = parametroEntidadExterna;
    }

    public List<Convenio> getListaConvenios() {
        if (null == listaConvenios) {
            listaConvenios = administrarConveniosPorEntidadBO.consultarConveniosRegistrados();
        }
        return listaConvenios;
    }

    public void setListaConvenios(List<Convenio> listaConvenios) {
        this.listaConvenios = listaConvenios;
    }

    public Convenio getParametroConvenio() {
        return parametroConvenio;
    }

    public void setParametroConvenio(Convenio parametroConvenio) {
        this.parametroConvenio = parametroConvenio;
    }

    public int getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(int tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

    public List<ConvenioPorEntidad> getListaConveniosPorEntidad() {
        return listaConveniosPorEntidad;
    }

    public void setListaConveniosPorEntidad(List<ConvenioPorEntidad> listaConveniosPorEntidad) {
        this.listaConveniosPorEntidad = listaConveniosPorEntidad;
    }

    public List<ConvenioPorEntidad> getListaConveniosPorEntidadTabla() {
        return listaConveniosPorEntidadTabla;
    }

    public void setListaConveniosPorEntidadTabla(List<ConvenioPorEntidad> listaConveniosPorEntidadTabla) {
        this.listaConveniosPorEntidadTabla = listaConveniosPorEntidadTabla;
    }

    public boolean isBloquearPagSigConvenioPorEntidad() {
        return bloquearPagSigConvenioPorEntidad;
    }

    public void setBloquearPagSigConvenioPorEntidad(boolean bloquearPagSigConvenioPorEntidad) {
        this.bloquearPagSigConvenioPorEntidad = bloquearPagSigConvenioPorEntidad;
    }

    public boolean isBloquearPagAntConvenioPorEntidad() {
        return bloquearPagAntConvenioPorEntidad;
    }

    public void setBloquearPagAntConvenioPorEntidad(boolean bloquearPagAntConvenioPorEntidad) {
        this.bloquearPagAntConvenioPorEntidad = bloquearPagAntConvenioPorEntidad;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public boolean isActivarEntidadExterna() {
        return activarEntidadExterna;
    }

    public void setActivarEntidadExterna(boolean activarEntidadExterna) {
        this.activarEntidadExterna = activarEntidadExterna;
    }

    public boolean isActivarConvenio() {
        return activarConvenio;
    }

    public void setActivarConvenio(boolean activarConvenio) {
        this.activarConvenio = activarConvenio;
    }

}
