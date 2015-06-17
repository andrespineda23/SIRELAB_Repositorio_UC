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
import java.util.ArrayList;
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
    private List<Sede> listaSedesTabla;
    private int posicionSedeTabla;
    private int tamTotalSede;
    private boolean bloquearPagSigSede, bloquearPagAntSede;
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
        listaSedesTabla = null;
        posicionSedeTabla = 0;
        tamTotalSede = 0;
        bloquearPagAntSede = true;
        bloquearPagSigSede = true;
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
                    listaSedesTabla = new ArrayList<Sede>();
                    tamTotalSede = listaSedes.size();
                    posicionSedeTabla = 0;
                    cargarDatosTablaSede();
                } else {
                    activarExport = true;
                    listaSedesTabla = null;
                    tamTotalSede = 0;
                    posicionSedeTabla = 0;
                    bloquearPagAntSede = true;
                    bloquearPagSigSede = true;
                }
            } else {
                listaSedesTabla = null;
                tamTotalSede = 0;
                posicionSedeTabla = 0;
                bloquearPagAntSede = true;
                bloquearPagSigSede = true;
            }
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarSedes buscarSedesPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaSede() {
        if (tamTotalSede < 10) {
            for (int i = 0; i < tamTotalSede; i++) {
                listaSedesTabla.add(listaSedes.get(i));
            }
            bloquearPagSigSede = true;
            bloquearPagAntSede = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaSedesTabla.add(listaSedes.get(i));
            }
            bloquearPagSigSede = false;
            bloquearPagAntSede = true;
        }
    }

    public void cargarPaginaSiguienteSede() {
        listaSedesTabla = new ArrayList<Sede>();
        posicionSedeTabla = posicionSedeTabla + 10;
        int diferencia = tamTotalSede - posicionSedeTabla;
        if (diferencia > 10) {
            for (int i = posicionSedeTabla; i < (posicionSedeTabla + 10); i++) {
                listaSedesTabla.add(listaSedes.get(i));
            }
            bloquearPagSigSede = false;
            bloquearPagAntSede = false;
        } else {
            for (int i = posicionSedeTabla; i < (posicionSedeTabla + diferencia); i++) {
                listaSedesTabla.add(listaSedes.get(i));
            }
            bloquearPagSigSede = true;
            bloquearPagAntSede = false;
        }
    }

    public void cargarPaginaAnteriorSede() {
        listaSedesTabla = new ArrayList<Sede>();
        posicionSedeTabla = posicionSedeTabla - 10;
        int diferencia = tamTotalSede - posicionSedeTabla;
        if (diferencia == tamTotalSede) {
            for (int i = posicionSedeTabla; i < (posicionSedeTabla + 10); i++) {
                listaSedesTabla.add(listaSedes.get(i));
            }
            bloquearPagSigSede = false;
            bloquearPagAntSede = true;
        } else {
            for (int i = posicionSedeTabla; i < (posicionSedeTabla + 10); i++) {
                listaSedesTabla.add(listaSedes.get(i));
            }
            bloquearPagSigSede = false;
            bloquearPagAntSede = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarExport = true;
        parametroNombre = null;
        parametroDireccion = null;
        parametroTelefono = null;
        listaSedes = null;
        listaSedesTabla = null;
        posicionSedeTabla = 0;
        tamTotalSede = 0;
        bloquearPagAntSede = true;
        bloquearPagSigSede = true;
        inicializarFiltros();
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

    public List<Sede> getListaSedesTabla() {
        return listaSedesTabla;
    }

    public void setListaSedesTabla(List<Sede> listaSedesTabla) {
        this.listaSedesTabla = listaSedesTabla;
    }

    public boolean isBloquearPagSigSede() {
        return bloquearPagSigSede;
    }

    public void setBloquearPagSigSede(boolean bloquearPagSigSede) {
        this.bloquearPagSigSede = bloquearPagSigSede;
    }

    public boolean isBloquearPagAntSede() {
        return bloquearPagAntSede;
    }

    public void setBloquearPagAntSede(boolean bloquearPagAntSede) {
        this.bloquearPagAntSede = bloquearPagAntSede;
    }

}
