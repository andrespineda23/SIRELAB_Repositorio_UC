/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresEdificioBOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoPorEdificio;
import com.sirelab.entidades.Sede;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarAdministradoresEdificio implements Serializable {

    @EJB
    AdministrarAdministradoresEdificioBOInterface administrarAdministradoresEdificioBO;

    private String parametroNombre, parametroApellido, parametroDocumento, parametroCorreo;
    private List<Sede> listaSedes;
    private Sede parametroSede;
    private List<Edificio> listaEdificios;
    private Edificio parametroEdificio;
    private int parametroEstado;
    private Map<String, String> filtros;
    //
    private boolean activoEdificio;
    //
    private boolean activarExport;
    //
    private List<EncargadoPorEdificio> listaEncargadosPorEdificio;
    private List<EncargadoPorEdificio> listaEncargadosPorEdificioTabla;
    private int posicionEncargadoPorEdificioTabla;
    private int tamTotalEncargadoPorEdificio;
    private boolean bloquearPagSigEncargadoPorEdificio, bloquearPagAntEncargadoPorEdificio;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarAdministradoresEdificio() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarExport = true;
        activoEdificio = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroSede = null;
        parametroEdificio = null;
        parametroEstado = 1;
        listaSedes = administrarAdministradoresEdificioBO.obtenerListaSedes();
        inicializarFiltros();
        listaEncargadosPorEdificio = null;
        listaEncargadosPorEdificioTabla = null;
        posicionEncargadoPorEdificioTabla = 0;
        tamTotalEncargadoPorEdificio = 0;
        bloquearPagSigEncargadoPorEdificio = true;
        bloquearPagAntEncargadoPorEdificio = true;
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
        filtros.put("parametroSede", null);
        filtros.put("parametroEdificio", null);
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
        if (Utilidades.validarNulo(parametroSede)) {
            if (parametroSede.getIdsede() != null) {
                filtros.put("parametroSede", parametroSede.getIdsede().toString());
            }
        }
        if (Utilidades.validarNulo(parametroEdificio)) {
            if (parametroEdificio.getIdedificio() != null) {
                filtros.put("parametroEdificio", parametroEdificio.getIdedificio().toString());
            }
        }
    }

    public void buscarAdministradoresEdificioPorParametros() {
        try {
            inicializarFiltros();
            listaEncargadosPorEdificio = null;
            listaEncargadosPorEdificio = administrarAdministradoresEdificioBO.consultarEncargadosPorEdificioPorParametro(filtros);
            if (listaEncargadosPorEdificio != null) {
                if (listaEncargadosPorEdificio.size() > 0) {
                    activarExport = false;
                    listaEncargadosPorEdificioTabla = new ArrayList<EncargadoPorEdificio>();
                    tamTotalEncargadoPorEdificio = listaEncargadosPorEdificio.size();
                    posicionEncargadoPorEdificioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalEncargadoPorEdificio);
                    cargarDatosTablaEncargadoPorEdificio();
                } else {
                    activarExport = true;
                    listaEncargadosPorEdificioTabla = null;
                    tamTotalEncargadoPorEdificio = 0;
                    posicionEncargadoPorEdificioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalEncargadoPorEdificio);
                    bloquearPagAntEncargadoPorEdificio = true;
                    bloquearPagSigEncargadoPorEdificio = true;
                }
            } else {
                listaEncargadosPorEdificioTabla = null;
                tamTotalEncargadoPorEdificio = 0;
                posicionEncargadoPorEdificioTabla = 0;
                bloquearPagAntEncargadoPorEdificio = true;
                cantidadRegistros = String.valueOf(tamTotalEncargadoPorEdificio);
                bloquearPagSigEncargadoPorEdificio = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarAdministradoresEdificio buscarAdministradoresEdificioPorParametros:  " + e.toString());
            System.out.println("Error ControllerAdministrarAdministradoresEdificio buscarAdministradoresEdificioPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaEncargadoPorEdificio() {
        if (tamTotalEncargadoPorEdificio < 10) {
            for (int i = 0; i < tamTotalEncargadoPorEdificio; i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = true;
            bloquearPagAntEncargadoPorEdificio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = false;
            bloquearPagAntEncargadoPorEdificio = true;
        }
    }

    public void cargarPaginaSiguienteEncargadoPorEdificio() {
        listaEncargadosPorEdificioTabla = new ArrayList<EncargadoPorEdificio>();
        posicionEncargadoPorEdificioTabla = posicionEncargadoPorEdificioTabla + 10;
        int diferencia = tamTotalEncargadoPorEdificio - posicionEncargadoPorEdificioTabla;
        if (diferencia > 10) {
            for (int i = posicionEncargadoPorEdificioTabla; i < (posicionEncargadoPorEdificioTabla + 10); i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = false;
            bloquearPagAntEncargadoPorEdificio = false;
        } else {
            for (int i = posicionEncargadoPorEdificioTabla; i < (posicionEncargadoPorEdificioTabla + diferencia); i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = true;
            bloquearPagAntEncargadoPorEdificio = false;
        }
    }

    public void cargarPaginaAnteriorEncargadoPorEdificio() {
        listaEncargadosPorEdificioTabla = new ArrayList<EncargadoPorEdificio>();
        posicionEncargadoPorEdificioTabla = posicionEncargadoPorEdificioTabla - 10;
        int diferencia = tamTotalEncargadoPorEdificio - posicionEncargadoPorEdificioTabla;
        if (diferencia == tamTotalEncargadoPorEdificio) {
            for (int i = posicionEncargadoPorEdificioTabla; i < (posicionEncargadoPorEdificioTabla + 10); i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = false;
            bloquearPagAntEncargadoPorEdificio = true;
        } else {
            for (int i = posicionEncargadoPorEdificioTabla; i < (posicionEncargadoPorEdificioTabla + 10); i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = false;
            bloquearPagAntEncargadoPorEdificio = false;
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public String limpiarProcesoBusqueda() {
        activarExport = true;
        activoEdificio = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroSede = null;
        parametroEdificio = null;
        parametroEstado = 1;
        listaEncargadosPorEdificio = null;
        listaEncargadosPorEdificioTabla = null;
        tamTotalEncargadoPorEdificio = 0;
        posicionEncargadoPorEdificioTabla = 0;
        bloquearPagAntEncargadoPorEdificio = true;
        bloquearPagSigEncargadoPorEdificio = true;
        listaEdificios = null;
        listaSedes = null;
        inicializarFiltros();
        cantidadRegistros = "N/A";
        return paginaAnterior;
    }
    
    public String dirigirPaginaNuevoRegistro(){
        limpiarProcesoBusqueda();
        return "registraradministradoredificio";
    }

    public void limpiarDatos() {
        activarExport = true;
        activoEdificio = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroSede = null;
        parametroEdificio = null;
        parametroEstado = 1;
        listaEncargadosPorEdificio = null;
        listaEncargadosPorEdificioTabla = null;
        tamTotalEncargadoPorEdificio = 0;
        posicionEncargadoPorEdificioTabla = 0;
        bloquearPagAntEncargadoPorEdificio = true;
        bloquearPagSigEncargadoPorEdificio = true;
        cantidadRegistros = "N/A";
    }

    /**
     * Metodo encargado de actualizar las facultades
     */
    public void actualizarSedes() {
        try {
            if (Utilidades.validarNulo(parametroSede)) {
                activoEdificio = false;
                parametroEdificio = new Edificio();
                listaEdificios = administrarAdministradoresEdificioBO.obtenerEdificiosPorIDSede(parametroSede.getIdsede());

            } else {
                activoEdificio = true;
                listaEdificios = null;
                parametroEdificio = new Edificio();
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarAdministradoresEdificio actualizarSedes:  " + e.toString());
            System.out.println("Error ControllerAdministrarAdministradoresEdificio actualizarSedes : " + e.toString());
        }
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un estudiante
     *
     * @return Pagina detalles
     */
    public String verDetallesEncargadoPorEdificio() {
        limpiarProcesoBusqueda();
        return "detallesencargadolaboratorio";
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

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public Sede getParametroSede() {
        return parametroSede;
    }

    public void setParametroSede(Sede parametroSede) {
        this.parametroSede = parametroSede;
    }

    public List<Edificio> getListaEdificios() {
        return listaEdificios;
    }

    public void setListaEdificios(List<Edificio> listaEdificios) {
        this.listaEdificios = listaEdificios;
    }

    public Edificio getParametroEdificio() {
        return parametroEdificio;
    }

    public void setParametroEdificio(Edificio parametroEdificio) {
        this.parametroEdificio = parametroEdificio;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

    public boolean isActivoEdificio() {
        return activoEdificio;
    }

    public void setActivoEdificio(boolean activoEdificio) {
        this.activoEdificio = activoEdificio;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<EncargadoPorEdificio> getListaEncargadosPorEdificio() {
        return listaEncargadosPorEdificio;
    }

    public void setListaEncargadosPorEdificio(List<EncargadoPorEdificio> listaEncargadosPorEdificio) {
        this.listaEncargadosPorEdificio = listaEncargadosPorEdificio;
    }

    public List<EncargadoPorEdificio> getListaEncargadosPorEdificioTabla() {
        return listaEncargadosPorEdificioTabla;
    }

    public void setListaEncargadosPorEdificioTabla(List<EncargadoPorEdificio> listaEncargadosPorEdificioTabla) {
        this.listaEncargadosPorEdificioTabla = listaEncargadosPorEdificioTabla;
    }

    public int getPosicionEncargadoPorEdificioTabla() {
        return posicionEncargadoPorEdificioTabla;
    }

    public void setPosicionEncargadoPorEdificioTabla(int posicionEncargadoPorEdificioTabla) {
        this.posicionEncargadoPorEdificioTabla = posicionEncargadoPorEdificioTabla;
    }

    public int getTamTotalEncargadoPorEdificio() {
        return tamTotalEncargadoPorEdificio;
    }

    public void setTamTotalEncargadoPorEdificio(int tamTotalEncargadoPorEdificio) {
        this.tamTotalEncargadoPorEdificio = tamTotalEncargadoPorEdificio;
    }

    public boolean isBloquearPagSigEncargadoPorEdificio() {
        return bloquearPagSigEncargadoPorEdificio;
    }

    public void setBloquearPagSigEncargadoPorEdificio(boolean bloquearPagSigEncargadoPorEdificio) {
        this.bloquearPagSigEncargadoPorEdificio = bloquearPagSigEncargadoPorEdificio;
    }

    public boolean isBloquearPagAntEncargadoPorEdificio() {
        return bloquearPagAntEncargadoPorEdificio;
    }

    public void setBloquearPagAntEncargadoPorEdificio(boolean bloquearPagAntEncargadoPorEdificio) {
        this.bloquearPagAntEncargadoPorEdificio = bloquearPagAntEncargadoPorEdificio;
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
