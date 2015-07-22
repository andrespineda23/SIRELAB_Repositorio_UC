/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoMovimientosInsumoBOInterface;
import com.sirelab.entidades.MovimientoInsumo;
import com.sirelab.entidades.Insumo;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarMovimientoInsumo implements Serializable {

    @EJB
    GestionarRecursoMovimientosInsumoBOInterface gestionarRecursoMovimientosInsumoBO;

    private Insumo insumoDetalle;
    //
    private List<MovimientoInsumo> listaMovimientosInsumo;
    private List<MovimientoInsumo> listaMovimientosInsumoTabla;
    private int posicionMovimientoTabla;
    private int tamTotalMovimiento;
    private boolean bloquearPagSigMovimiento, bloquearPagAntMovimiento;
    private String cantidadRegistros;

    public ControllerAdministrarMovimientoInsumo() {
    }

    @PostConstruct
    public void init() {
        listaMovimientosInsumoTabla = null;
        listaMovimientosInsumo = null;
        posicionMovimientoTabla = 0;
        tamTotalMovimiento = 0;
        bloquearPagAntMovimiento = true;
        bloquearPagSigMovimiento = true;
    }

    public void recibirIDInsumo(BigInteger idRegistro) {
        insumoDetalle = gestionarRecursoMovimientosInsumoBO.obtenerInsumoPorID(idRegistro);
        obtenerMovimientosAsociadosAlEquipo();
    }

    private void obtenerMovimientosAsociadosAlEquipo() {
        if (null != insumoDetalle) {
            listaMovimientosInsumo = gestionarRecursoMovimientosInsumoBO.consultarMovimientosInsumoPorIDInsumo(insumoDetalle.getIdinsumo());
            if (null != listaMovimientosInsumo) {
                listaMovimientosInsumoTabla = new ArrayList<MovimientoInsumo>();
                tamTotalMovimiento = listaMovimientosInsumo.size();
                posicionMovimientoTabla = 0;
                cantidadRegistros = String.valueOf(tamTotalMovimiento);
                cargarDatosTablaMovimientoInsumo();
            }
        }
    }

    private void cargarDatosTablaMovimientoInsumo() {
        if (tamTotalMovimiento < 10) {
            for (int i = 0; i < tamTotalMovimiento; i++) {
                listaMovimientosInsumoTabla.add(listaMovimientosInsumo.get(i));
            }
            bloquearPagSigMovimiento = true;
            bloquearPagAntMovimiento = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaMovimientosInsumoTabla.add(listaMovimientosInsumo.get(i));
            }
            bloquearPagSigMovimiento = false;
            bloquearPagAntMovimiento = true;
        }
    }

    public void cargarPaginaSiguienteMovimientoInsumo() {
        listaMovimientosInsumoTabla = new ArrayList<MovimientoInsumo>();
        posicionMovimientoTabla = posicionMovimientoTabla + 10;
        int diferencia = tamTotalMovimiento - posicionMovimientoTabla;
        if (diferencia > 10) {
            for (int i = posicionMovimientoTabla; i < (posicionMovimientoTabla + 10); i++) {
                listaMovimientosInsumoTabla.add(listaMovimientosInsumo.get(i));
            }
            bloquearPagSigMovimiento = false;
            bloquearPagAntMovimiento = false;
        } else {
            for (int i = posicionMovimientoTabla; i < (posicionMovimientoTabla + diferencia); i++) {
                listaMovimientosInsumoTabla.add(listaMovimientosInsumo.get(i));
            }
            bloquearPagSigMovimiento = true;
            bloquearPagAntMovimiento = false;
        }
    }

    public void cargarPaginaAnteriorMovimientoInsumo() {
        listaMovimientosInsumoTabla = new ArrayList<MovimientoInsumo>();
        posicionMovimientoTabla = posicionMovimientoTabla - 10;
        int diferencia = tamTotalMovimiento - posicionMovimientoTabla;
        if (diferencia == tamTotalMovimiento) {
            for (int i = posicionMovimientoTabla; i < (posicionMovimientoTabla + 10); i++) {
                listaMovimientosInsumoTabla.add(listaMovimientosInsumo.get(i));
            }
            bloquearPagSigMovimiento = false;
            bloquearPagAntMovimiento = true;
        } else {
            for (int i = posicionMovimientoTabla; i < (posicionMovimientoTabla + 10); i++) {
                listaMovimientosInsumoTabla.add(listaMovimientosInsumo.get(i));
            }
            bloquearPagSigMovimiento = false;
            bloquearPagAntMovimiento = false;
        }
    }

    public String registrarNuevoMovimiento() {
        listaMovimientosInsumo = null;
        listaMovimientosInsumoTabla = null;
        posicionMovimientoTabla = 0;
        tamTotalMovimiento = 0;
        bloquearPagAntMovimiento = true;
        bloquearPagSigMovimiento = true;
        return "registrar_movimientoinsumo";
    }

    public String limpiarProcesoBusqueda() {
        listaMovimientosInsumo = null;
        listaMovimientosInsumoTabla = null;
        posicionMovimientoTabla = 0;
        tamTotalMovimiento = 0;
        bloquearPagAntMovimiento = true;
        bloquearPagSigMovimiento = true;
        cantidadRegistros = "N/A";
        return "detalles_insumo";
    }

    /*    
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Plata_Equipos_de_Modulo_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Plata_Equipos_de_Modulo_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    public String cambiarPaginaDetalles() {
        limpiarProcesoBusqueda();
        return "detalles_movimientoinsumo";
    }

    // GET - SET
    public Insumo getInsumoDetalle() {
        return insumoDetalle;
    }

    public void setInsumoDetalle(Insumo insumoDetalle) {
        this.insumoDetalle = insumoDetalle;
    }

    public List<MovimientoInsumo> getListaMovimientosInsumoTabla() {
        return listaMovimientosInsumoTabla;
    }

    public void setListaMovimientosInsumoTabla(List<MovimientoInsumo> listaMovimientosInsumoTabla) {
        this.listaMovimientosInsumoTabla = listaMovimientosInsumoTabla;
    }

    public boolean isBloquearPagSigMovimiento() {
        return bloquearPagSigMovimiento;
    }

    public void setBloquearPagSigMovimiento(boolean bloquearPagSigMovimiento) {
        this.bloquearPagSigMovimiento = bloquearPagSigMovimiento;
    }

    public boolean isBloquearPagAntMovimiento() {
        return bloquearPagAntMovimiento;
    }

    public void setBloquearPagAntMovimiento(boolean bloquearPagAntMovimiento) {
        this.bloquearPagAntMovimiento = bloquearPagAntMovimiento;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
