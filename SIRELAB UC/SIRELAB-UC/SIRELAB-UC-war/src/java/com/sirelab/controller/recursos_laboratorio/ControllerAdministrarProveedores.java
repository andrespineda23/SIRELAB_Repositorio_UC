/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoProveedoresBOInterface;
import com.sirelab.entidades.Proveedor;
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
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarProveedores implements Serializable {

    @EJB
    GestionarRecursoProveedoresBOInterface gestionarRecursoProveedoresBO;

    private String parametroNombre, parametroNIT, parametroTelefono, parametroDireccion;
    private Map<String, String> filtros;
    //
    private List<Proveedor> listaProveedores;
    private List<Proveedor> listaProveedoresTabla;
    private int posicionProveedorTabla;
    private int tamTotalProveedor;
    private boolean bloquearPagSigProveedor, bloquearPagAntProveedor;
    //
    private String altoTabla;
    //
    private boolean activarExport;
    //
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerAdministrarProveedores() {
    }

    @PostConstruct
    public void init() {
        parametroNombre = null;
        parametroNIT = null;
        parametroDireccion = null;
        parametroTelefono = null;
        altoTabla = "150";
        inicializarFiltros();
        listaProveedores = null;
        listaProveedoresTabla = null;
        posicionProveedorTabla = 0;
        tamTotalProveedor = 0;
        bloquearPagAntProveedor = true;
        bloquearPagSigProveedor = true;
        activarExport = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroTelefono", null);
        filtros.put("parametroDireccion", null);
        filtros.put("parametroNIT", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroNIT) == true) && (!parametroNIT.isEmpty())) {
            filtros.put("parametroNIT", parametroNIT.toString());
        }
        if ((Utilidades.validarNulo(parametroTelefono) == true) && (!parametroTelefono.isEmpty())) {
            filtros.put("parametroTelefono", parametroTelefono.toString());
        }
        if ((Utilidades.validarNulo(parametroDireccion) == true) && (!parametroDireccion.isEmpty())) {
            filtros.put("parametroDireccion", parametroDireccion.toString());
        }
    }

    public void buscarProveedoresPorParametros() {
        try {
            inicializarFiltros();
            listaProveedores = null;
            listaProveedores = gestionarRecursoProveedoresBO.consultarProveedoresPorParametro(filtros);
            if (listaProveedores != null) {
                if (listaProveedores.size() > 0) {
                    activarExport = false;
                    listaProveedoresTabla = new ArrayList<Proveedor>();
                    tamTotalProveedor = listaProveedores.size();
                    posicionProveedorTabla = 0;
                    cargarDatosTablaProveedor();
                } else {
                    activarExport = true;
                    listaProveedoresTabla = null;
                    tamTotalProveedor = 0;
                    posicionProveedorTabla = 0;
                    bloquearPagAntProveedor = true;
                    bloquearPagSigProveedor = true;
                }
            } else {
                listaProveedoresTabla = null;
                tamTotalProveedor = 0;
                posicionProveedorTabla = 0;
                bloquearPagAntProveedor = true;
                bloquearPagSigProveedor = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarProveedores buscarProveedoresPorParametros:  "+e.toString());
            System.out.println("Error ControllerGestionarProveedores buscarProveedoresPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaProveedor() {
        if (tamTotalProveedor < 10) {
            for (int i = 0; i < tamTotalProveedor; i++) {
                listaProveedoresTabla.add(listaProveedores.get(i));
            }
            bloquearPagSigProveedor = true;
            bloquearPagAntProveedor = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaProveedoresTabla.add(listaProveedores.get(i));
            }
            bloquearPagSigProveedor = false;
            bloquearPagAntProveedor = true;
        }
    }

    public void cargarPaginaSiguienteProveedor() {
        listaProveedoresTabla = new ArrayList<Proveedor>();
        posicionProveedorTabla = posicionProveedorTabla + 10;
        int diferencia = tamTotalProveedor - posicionProveedorTabla;
        if (diferencia > 10) {
            for (int i = posicionProveedorTabla; i < (posicionProveedorTabla + 10); i++) {
                listaProveedoresTabla.add(listaProveedores.get(i));
            }
            bloquearPagSigProveedor = false;
            bloquearPagAntProveedor = false;
        } else {
            for (int i = posicionProveedorTabla; i < (posicionProveedorTabla + diferencia); i++) {
                listaProveedoresTabla.add(listaProveedores.get(i));
            }
            bloquearPagSigProveedor = true;
            bloquearPagAntProveedor = false;
        }
    }

    public void cargarPaginaAnteriorProveedor() {
        listaProveedoresTabla = new ArrayList<Proveedor>();
        posicionProveedorTabla = posicionProveedorTabla - 10;
        int diferencia = tamTotalProveedor - posicionProveedorTabla;
        if (diferencia == tamTotalProveedor) {
            for (int i = posicionProveedorTabla; i < (posicionProveedorTabla + 10); i++) {
                listaProveedoresTabla.add(listaProveedores.get(i));
            }
            bloquearPagSigProveedor = false;
            bloquearPagAntProveedor = true;
        } else {
            for (int i = posicionProveedorTabla; i < (posicionProveedorTabla + 10); i++) {
                listaProveedoresTabla.add(listaProveedores.get(i));
            }
            bloquearPagSigProveedor = false;
            bloquearPagAntProveedor = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroNIT = null;
        parametroDireccion = null;
        parametroTelefono = null;
        listaProveedores = null;
        listaProveedoresTabla = null;
        posicionProveedorTabla = 0;
        tamTotalProveedor = 0;
        bloquearPagAntProveedor = true;
        bloquearPagSigProveedor = true;
        inicializarFiltros();
        return paginaAnterior;
    }

    /*
     public void activarFiltrosTabla() {
     altoTabla = "128";
     FacesContext c = FacesContext.getCurrentInstance();
     nombreTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:nombreTabla");
     nombreTabla.setFilterStyle("width: 80px");
     nitTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:nitTabla");
     nitTabla.setFilterStyle("width: 80px");
     direccionTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:direccionTabla");
     direccionTabla.setFilterStyle("width: 80px");
     telefonoTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:telefonoTabla");
     telefonoTabla.setFilterStyle("width: 80px");
     }

     public void desactivarFiltrosTabla() {
     altoTabla = "150";
     FacesContext c = FacesContext.getCurrentInstance();
     nombreTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:nombreTabla");
     nombreTabla.setFilterStyle("display: none; visibility: hidden;");
     nitTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:nitTabla");
     nitTabla.setFilterStyle("display: none; visibility: hidden;");
     direccionTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:direccionTabla");
     direccionTabla.setFilterStyle("display: none; visibility: hidden;");
     telefonoTabla = (Column) c.getViewRoot().findComponent("formT:form:datosBusqueda:telefonoTabla");
     telefonoTabla.setFilterStyle("display: none; visibility: hidden;");
     listaProveedoresTabla = null;
     }
     */
    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Recursos_Proveedor_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Recursos_Proveedor_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }*/
    // GET - SET
    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
    }

    public String getParametroNIT() {
        return parametroNIT;
    }

    public void setParametroNIT(String parametroNIT) {
        this.parametroNIT = parametroNIT;
    }

    public String getParametroTelefono() {
        return parametroTelefono;
    }

    public void setParametroTelefono(String parametroTelefono) {
        this.parametroTelefono = parametroTelefono;
    }

    public String getParametroDireccion() {
        return parametroDireccion;
    }

    public void setParametroDireccion(String parametroDireccion) {
        this.parametroDireccion = parametroDireccion;
    }

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
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

    public List<Proveedor> getListaProveedoresTabla() {
        return listaProveedoresTabla;
    }

    public void setListaProveedoresTabla(List<Proveedor> listaProveedoresTabla) {
        this.listaProveedoresTabla = listaProveedoresTabla;
    }

    public boolean isBloquearPagSigProveedor() {
        return bloquearPagSigProveedor;
    }

    public void setBloquearPagSigProveedor(boolean bloquearPagSigProveedor) {
        this.bloquearPagSigProveedor = bloquearPagSigProveedor;
    }

    public boolean isBloquearPagAntProveedor() {
        return bloquearPagAntProveedor;
    }

    public void setBloquearPagAntProveedor(boolean bloquearPagAntProveedor) {
        this.bloquearPagAntProveedor = bloquearPagAntProveedor;
    }

}
