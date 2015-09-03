/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarPersonasContactoBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarPersonasContactoBOInterface;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.PersonaContacto;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.PersonaContacto;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarPersonaContacto implements Serializable {

    @EJB
    AdministrarPersonasContactoBOInterface administrarPersonasContactoBO;

    private String parametroNombre, parametroApellido, parametroDocumento, parametroCorreo;
    private int parametroEstado;
    private List<Convenio> listaConvenios;
    private Convenio parametroConvenio;
    private List<EntidadExterna> listaEntidadesExternas;
    private EntidadExterna parametroEntidad;
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<PersonaContacto> listaPersonasContacto;
    private List<PersonaContacto> listaPersonasContactoTabla;
    private int posicionPersonaContactoTabla;
    private int tamTotalPersonaContacto;
    private boolean bloquearPagSigPersonaContacto, bloquearPagAntPersonaContacto;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarPersonaContacto() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroEstado = 1;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroEntidad = null;
        parametroConvenio = null;
        inicializarFiltros();
        listaPersonasContacto = null;
        listaPersonasContactoTabla = null;
        posicionPersonaContactoTabla = 0;
        tamTotalPersonaContacto = 0;
        bloquearPagSigPersonaContacto = true;
        bloquearPagAntPersonaContacto = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    /**
     * Metodo encargado de inicializar los filtros de busqueda para el proceso
     * de consulta de docentes
     */
    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroApellido", null);
        filtros.put("parametroDocumento", null);
        filtros.put("parametroCorreo", null);
        filtros.put("parametroEntidad", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroConvenio", null);
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
        if (Utilidades.validarNulo(parametroConvenio) == true) {
            if (parametroConvenio.getIdconvenio() != null) {
                filtros.put("parametroConvenio", parametroConvenio.getIdconvenio().toString());
            }
        }
        if (Utilidades.validarNulo(parametroEntidad) == true) {
            if (parametroEntidad.getIdentidadexterna() != null) {
                filtros.put("parametroEntidad", parametroEntidad.getIdentidadexterna().toString());
            }
        }
    }

    /**
     * Metodo encargado de buscar los docentes por medio de los parametros
     * ingresados por el usuario
     */
    public void buscarPersonasContactoPorParametros() {
        try {
            inicializarFiltros();
            listaPersonasContacto = null;
            listaPersonasContacto = administrarPersonasContactoBO.consultarPersonasContactoPorParametro(filtros);
            if (listaPersonasContacto != null) {
                if (listaPersonasContacto.size() > 0) {
                    activarExport = false;
                    listaPersonasContactoTabla = new ArrayList<PersonaContacto>();
                    tamTotalPersonaContacto = listaPersonasContacto.size();
                    cantidadRegistros = String.valueOf(tamTotalPersonaContacto);
                    posicionPersonaContactoTabla = 0;
                    cargarDatosTablaPersonaContacto();
                } else {
                    activarExport = true;
                    listaPersonasContactoTabla = null;
                    tamTotalPersonaContacto = 0;
                    cantidadRegistros = String.valueOf(tamTotalPersonaContacto);
                    posicionPersonaContactoTabla = 0;
                    bloquearPagAntPersonaContacto = true;
                    bloquearPagSigPersonaContacto = true;
                }
            } else {
                listaPersonasContactoTabla = null;
                tamTotalPersonaContacto = 0;
                cantidadRegistros = String.valueOf(tamTotalPersonaContacto);
                posicionPersonaContactoTabla = 0;
                bloquearPagAntPersonaContacto = true;
                bloquearPagSigPersonaContacto = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarPersonasContacto buscarPersonasContactoPorParametros:  " + e.toString());
            System.out.println("Error ControllerAdministrarPersonasContacto buscarPersonasContactoPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaPersonaContacto() {
        if (tamTotalPersonaContacto < 10) {
            for (int i = 0; i < tamTotalPersonaContacto; i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = true;
            bloquearPagAntPersonaContacto = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = false;
            bloquearPagAntPersonaContacto = true;
        }
    }

    public void cargarPaginaSiguientePersonaContacto() {
        listaPersonasContactoTabla = new ArrayList<PersonaContacto>();
        posicionPersonaContactoTabla = posicionPersonaContactoTabla + 10;
        int diferencia = tamTotalPersonaContacto - posicionPersonaContactoTabla;
        if (diferencia > 10) {
            for (int i = posicionPersonaContactoTabla; i < (posicionPersonaContactoTabla + 10); i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = false;
            bloquearPagAntPersonaContacto = false;
        } else {
            for (int i = posicionPersonaContactoTabla; i < (posicionPersonaContactoTabla + diferencia); i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = true;
            bloquearPagAntPersonaContacto = false;
        }
    }

    public void cargarPaginaAnteriorPersonaContacto() {
        listaPersonasContactoTabla = new ArrayList<PersonaContacto>();
        posicionPersonaContactoTabla = posicionPersonaContactoTabla - 10;
        int diferencia = tamTotalPersonaContacto - posicionPersonaContactoTabla;
        if (diferencia == tamTotalPersonaContacto) {
            for (int i = posicionPersonaContactoTabla; i < (posicionPersonaContactoTabla + 10); i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = false;
            bloquearPagAntPersonaContacto = true;
        } else {
            for (int i = posicionPersonaContactoTabla; i < (posicionPersonaContactoTabla + 10); i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = false;
            bloquearPagAntPersonaContacto = false;
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
        parametroEntidad = null;
        parametroConvenio = null;
        parametroEstado = 1;
        inicializarFiltros();
        listaPersonasContacto = null;
        listaPersonasContactoTabla = null;
        tamTotalPersonaContacto = 0;
        posicionPersonaContactoTabla = 0;
        bloquearPagAntPersonaContacto = true;
        bloquearPagSigPersonaContacto = true;
        listaEntidadesExternas = null;
        listaConvenios = null;
        cantidadRegistros = "N/A";
        return paginaAnterior;
    }

    public void limpiarDatos() {
        activarExport = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroEntidad = null;
        parametroConvenio = null;
        parametroEstado = 1;
        listaPersonasContacto = null;
        listaPersonasContactoTabla = null;
        tamTotalPersonaContacto = 0;
        posicionPersonaContactoTabla = 0;
        bloquearPagAntPersonaContacto = true;
        bloquearPagSigPersonaContacto = true;
        cantidadRegistros = "N/A";
        inicializarFiltros();
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un docente
     */
    public String verDetallesPersonaContacto() {
        limpiarProcesoBusqueda();
        return "detallespersonacontacto";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "AdministrarPersonasContactoPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "AdministrarPersonasContactoXLS", false, false, "UTF-8", null, null);
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

    public List<Convenio> getListaConvenios() {
        if (null == listaConvenios) {
            listaConvenios = administrarPersonasContactoBO.obtenerConveniosRegistradas();
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

    public List<EntidadExterna> getListaEntidadesExternas() {
        if (null == listaEntidadesExternas) {
            listaEntidadesExternas = administrarPersonasContactoBO.obtenerEntidadesExternasRegistradas();
        }
        return listaEntidadesExternas;
    }

    public void setListaEntidadesExternas(List<EntidadExterna> listaEntidadesExternas) {
        this.listaEntidadesExternas = listaEntidadesExternas;
    }

    public EntidadExterna getParametroEntidad() {
        return parametroEntidad;
    }

    public void setParametroEntidad(EntidadExterna parametroEntidad) {
        this.parametroEntidad = parametroEntidad;
    }

    public List<PersonaContacto> getListaPersonasContacto() {
        return listaPersonasContacto;
    }

    public void setListaPersonasContacto(List<PersonaContacto> listaPersonasContacto) {
        this.listaPersonasContacto = listaPersonasContacto;
    }

    public List<PersonaContacto> getListaPersonasContactoTabla() {
        return listaPersonasContactoTabla;
    }

    public void setListaPersonasContactoTabla(List<PersonaContacto> listaPersonasContactoTabla) {
        this.listaPersonasContactoTabla = listaPersonasContactoTabla;
    }

    public int getPosicionPersonaContactoTabla() {
        return posicionPersonaContactoTabla;
    }

    public void setPosicionPersonaContactoTabla(int posicionPersonaContactoTabla) {
        this.posicionPersonaContactoTabla = posicionPersonaContactoTabla;
    }

    public int getTamTotalPersonaContacto() {
        return tamTotalPersonaContacto;
    }

    public void setTamTotalPersonaContacto(int tamTotalPersonaContacto) {
        this.tamTotalPersonaContacto = tamTotalPersonaContacto;
    }

    public boolean isBloquearPagSigPersonaContacto() {
        return bloquearPagSigPersonaContacto;
    }

    public void setBloquearPagSigPersonaContacto(boolean bloquearPagSigPersonaContacto) {
        this.bloquearPagSigPersonaContacto = bloquearPagSigPersonaContacto;
    }

    public boolean isBloquearPagAntPersonaContacto() {
        return bloquearPagAntPersonaContacto;
    }

    public void setBloquearPagAntPersonaContacto(boolean bloquearPagAntPersonaContacto) {
        this.bloquearPagAntPersonaContacto = bloquearPagAntPersonaContacto;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
