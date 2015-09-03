/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.EquipoElemento;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerHojaVidaEquipo implements Serializable {

    @EJB
    GestionarPlantaHojasVidaEquiposBOInterface gestionarPlantaHojasVidaEquiposBO;

    private EquipoElemento equipoElementoDetalle;
    //
    private List<HojaVidaEquipo> listaHojasVidaEquipos;
    private List<HojaVidaEquipo> listaHojasVidaEquiposTabla;
    private int posicionHojaVidaTabla;
    private int tamTotalHojaVida;
    private boolean bloquearPagSigHojaVida, bloquearPagAntHojaVida;
    private String cantidadRegistros;
    //

    public ControllerHojaVidaEquipo() {
    }

    @PostConstruct
    public void init() {
        listaHojasVidaEquiposTabla = null;
        listaHojasVidaEquipos = null;
        posicionHojaVidaTabla = 0;
        tamTotalHojaVida = 0;
        bloquearPagAntHojaVida = true;
        bloquearPagSigHojaVida = true;
    }

    public void recibirIDEquipoElemento(BigInteger idRegistro) {
        equipoElementoDetalle = gestionarPlantaHojasVidaEquiposBO.consultarEquipoElementoPorID(idRegistro);
        obtenerHojaVidasAsociadosAlEquipo();
    }

    private void obtenerHojaVidasAsociadosAlEquipo() {
        if (null != equipoElementoDetalle) {
            listaHojasVidaEquipos = gestionarPlantaHojasVidaEquiposBO.consultarHojaVidaPorIDEquipo(equipoElementoDetalle.getIdequipoelemento());
            if (null != listaHojasVidaEquipos) {
                listaHojasVidaEquiposTabla = new ArrayList<HojaVidaEquipo>();
                tamTotalHojaVida = listaHojasVidaEquipos.size();
                posicionHojaVidaTabla = 0;
                cantidadRegistros = String.valueOf(tamTotalHojaVida);
                cargarDatosTablaHojaVidaEquipo();
            }
        }
    }

    private void cargarDatosTablaHojaVidaEquipo() {
        if (tamTotalHojaVida < 10) {
            for (int i = 0; i < tamTotalHojaVida; i++) {
                listaHojasVidaEquiposTabla.add(listaHojasVidaEquipos.get(i));
            }
            bloquearPagSigHojaVida = true;
            bloquearPagAntHojaVida = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaHojasVidaEquiposTabla.add(listaHojasVidaEquipos.get(i));
            }
            bloquearPagSigHojaVida = false;
            bloquearPagAntHojaVida = true;
        }
    }

    public void cargarPaginaSiguienteHojaVidaEquipo() {
        listaHojasVidaEquiposTabla = new ArrayList<HojaVidaEquipo>();
        posicionHojaVidaTabla = posicionHojaVidaTabla + 10;
        int diferencia = tamTotalHojaVida - posicionHojaVidaTabla;
        if (diferencia > 10) {
            for (int i = posicionHojaVidaTabla; i < (posicionHojaVidaTabla + 10); i++) {
                listaHojasVidaEquiposTabla.add(listaHojasVidaEquipos.get(i));
            }
            bloquearPagSigHojaVida = false;
            bloquearPagAntHojaVida = false;
        } else {
            for (int i = posicionHojaVidaTabla; i < (posicionHojaVidaTabla + diferencia); i++) {
                listaHojasVidaEquiposTabla.add(listaHojasVidaEquipos.get(i));
            }
            bloquearPagSigHojaVida = true;
            bloquearPagAntHojaVida = false;
        }
    }

    public void cargarPaginaAnteriorHojaVidaEquipo() {
        listaHojasVidaEquiposTabla = new ArrayList<HojaVidaEquipo>();
        posicionHojaVidaTabla = posicionHojaVidaTabla - 10;
        int diferencia = tamTotalHojaVida - posicionHojaVidaTabla;
        if (diferencia == tamTotalHojaVida) {
            for (int i = posicionHojaVidaTabla; i < (posicionHojaVidaTabla + 10); i++) {
                listaHojasVidaEquiposTabla.add(listaHojasVidaEquipos.get(i));
            }
            bloquearPagSigHojaVida = false;
            bloquearPagAntHojaVida = true;
        } else {
            for (int i = posicionHojaVidaTabla; i < (posicionHojaVidaTabla + 10); i++) {
                listaHojasVidaEquiposTabla.add(listaHojasVidaEquipos.get(i));
            }
            bloquearPagSigHojaVida = false;
            bloquearPagAntHojaVida = false;
        }
    }

    public String registrarNuevoHojaVida() {
        listaHojasVidaEquipos = null;
        listaHojasVidaEquiposTabla = null;
        posicionHojaVidaTabla = 0;
        tamTotalHojaVida = 0;
        bloquearPagAntHojaVida = true;
        bloquearPagSigHojaVida = true;
        return "registrarhojavidaequipo";
    }

    public String limpiarProcesoBusqueda() {
        listaHojasVidaEquipos = null;
        listaHojasVidaEquiposTabla = null;
        posicionHojaVidaTabla = 0;
        tamTotalHojaVida = 0;
        cantidadRegistros = "N/A";
        bloquearPagAntHojaVida = true;
        bloquearPagSigHojaVida = true;
        return "detallesequipo";
    }

    /*    
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "PlataEquiposdeModuloPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "PlataEquiposdeModuloXLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    public String cambiarPaginaDetalles() {
        limpiarProcesoBusqueda();
        return "detallescomponente";
    }

    // GET - SET
    public EquipoElemento getEquipoElementoDetalle() {
        return equipoElementoDetalle;
    }

    public void setEquipoElementoDetalle(EquipoElemento equipoElementoDetalle) {
        this.equipoElementoDetalle = equipoElementoDetalle;
    }

    public List<HojaVidaEquipo> getListaHojasVidaEquipos() {
        return listaHojasVidaEquipos;
    }

    public void setListaHojasVidaEquipos(List<HojaVidaEquipo> listaHojasVidaEquipos) {
        this.listaHojasVidaEquipos = listaHojasVidaEquipos;
    }

    public List<HojaVidaEquipo> getListaHojasVidaEquiposTabla() {
        return listaHojasVidaEquiposTabla;
    }

    public void setListaHojasVidaEquiposTabla(List<HojaVidaEquipo> listaHojasVidaEquiposTabla) {
        this.listaHojasVidaEquiposTabla = listaHojasVidaEquiposTabla;
    }

    public boolean isBloquearPagSigHojaVida() {
        return bloquearPagSigHojaVida;
    }

    public void setBloquearPagSigHojaVida(boolean bloquearPagSigHojaVida) {
        this.bloquearPagSigHojaVida = bloquearPagSigHojaVida;
    }

    public boolean isBloquearPagAntHojaVida() {
        return bloquearPagAntHojaVida;
    }

    public void setBloquearPagAntHojaVida(boolean bloquearPagAntHojaVida) {
        this.bloquearPagAntHojaVida = bloquearPagAntHojaVida;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

}
