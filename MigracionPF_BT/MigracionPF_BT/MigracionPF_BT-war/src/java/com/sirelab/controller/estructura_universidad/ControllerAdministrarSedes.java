/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarSedeBOInterface;
import com.sirelab.entidades.Sede;
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
public class ControllerAdministrarSedes implements Serializable {

    @EJB
    GestionarSedeBOInterface gestionarSedeBO;

    private String parametroNombre, parametroDireccion, parametroTelefono;
    private Map<String, String> filtros;
    //
    private List<Sede> listaSedes;
    private List<Sede> filtrarListaSedes;
    //
    private String altoTabla;
    //
    private boolean activarExport;
    //
    private String editarNombre, editarDireccion, editarTelefono;
    private Sede sedeEditar;

    public ControllerAdministrarSedes() {
    }

    @PostConstruct
    public void init() {
        parametroNombre = null;
        parametroDireccion = null;
        parametroTelefono = null;
        altoTabla = "150";
        inicializarFiltros();
        listaSedes = null;
        filtrarListaSedes = null;
        activarExport = true;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroDireccion", null);
        filtros.put("parametroTelefono", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroDireccion) == true) && (!parametroDireccion.isEmpty())) {
            filtros.put("parametroDireccion", parametroDireccion.toString());
        }
        if ((Utilidades.validarNulo(parametroTelefono) == true) && (!parametroTelefono.isEmpty())) {
            filtros.put("parametroTelefono", parametroTelefono.toString());
        }
    }

    public void buscarSedesPorParametros() {
        try {
            inicializarFiltros();
            listaSedes = null;
            listaSedes = gestionarSedeBO.consultarSedesPorParametro(filtros);
            if (listaSedes != null) {
                if (listaSedes.size() > 0) {
                    activarExport = false;
                } else {
                    activarExport = true;
                }
            } 
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarSedes buscarSedesPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroDireccion = null;
        parametroTelefono = null;
        inicializarFiltros();
        listaSedes = null;
    }

    public void dispararDialogoEditarSede(BigInteger idSede) {
        editarDireccion = null;
        editarTelefono = null;
        editarNombre = null;
        cargarInformacionUsuarioEditar(idSede);
        //RequestContext context = RequestContext.getCurrentInstance();
        //context.update("formT:formularioDialogos:EditarRegistroSede");
        //context.execute("EditarRegistroSede.show()");
    }

    public void cargarInformacionUsuarioEditar(BigInteger idSede) {
        try {
            sedeEditar = gestionarSedeBO.obtenerSedePorIDSede(idSede);
            if (sedeEditar != null) {
                editarDireccion = sedeEditar.getDireccionsede();
                editarTelefono = sedeEditar.getTelefonosede();
                editarNombre = sedeEditar.getNombresede();
            }
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarSedes cargarInformacionUsuarioEditar : " + e.toString());
        }
    }

    private boolean validarInformacionSede(int tipoRegistro) {
        boolean retorno = true;
        if (tipoRegistro == 0) {

        } else {
            if ((Utilidades.validarNulo(editarDireccion)) && (Utilidades.validarNulo(editarNombre))) {
            } else {
                retorno = false;
            }
            if (Utilidades.validarNulo(editarTelefono)) {
                if (!Utilidades.isNumber(editarTelefono)) {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarInformacionSede() {
        //RequestContext context = RequestContext.getCurrentInstance();
        if (validarInformacionSede(1) == true) {
            almacenarModificacion();
        } else {
            //  context.execute("errorInformacionSede.show()");
        }
    }

    public void almacenarModificacion() {
        //RequestContext context = RequestContext.getCurrentInstance();
        //context.execute("EditarRegistroSede.hide()");
        try {
            sedeEditar.setDireccionsede(editarDireccion);
            sedeEditar.setTelefonosede(editarTelefono);
            sedeEditar.setNombresede(editarNombre);
            gestionarSedeBO.modificarInformacionSede(sedeEditar);
            limpiarEditarSede();
//            context.execute("registroExitosoSede.show()");
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarSedes almacenarModificacion : " + e.toString());
            //context.execute("registroFallidoSede.show()");
        }
    }

    public void limpiarEditarSede() {
        sedeEditar = null;
        editarDireccion = null;
        editarTelefono = null;
        editarNombre = null;
    }

    /*    
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Gestionar_Sede_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Gestionar_Sede_XLS", false, false, "UTF-8", null, null);
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

    public String getParametroDireccion() {
        return parametroDireccion;
    }

    public void setParametroDireccion(String parametroDireccion) {
        this.parametroDireccion = parametroDireccion;
    }

    public String getParametroTelefono() {
        return parametroTelefono;
    }

    public void setParametroTelefono(String parametroTelefono) {
        this.parametroTelefono = parametroTelefono;
    }

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<Sede> getFiltrarListaSedes() {
        return filtrarListaSedes;
    }

    public void setFiltrarListaSedes(List<Sede> filtrarListaSedes) {
        this.filtrarListaSedes = filtrarListaSedes;
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

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarDireccion() {
        return editarDireccion;
    }

    public void setEditarDireccion(String editarDireccion) {
        this.editarDireccion = editarDireccion;
    }

    public String getEditarTelefono() {
        return editarTelefono;
    }

    public void setEditarTelefono(String editarTelefono) {
        this.editarTelefono = editarTelefono;
    }

    public Sede getSedeEditar() {
        return sedeEditar;
    }

    public void setSedeEditar(Sede sedeEditar) {
        this.sedeEditar = sedeEditar;
    }

}
