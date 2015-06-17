/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.GestionarPlantaComponentesEquiposBOInterface;
import com.sirelab.entidades.ComponenteEquipo;
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
public class ControllerAdministrarComponentes implements Serializable {

    @EJB
    GestionarPlantaComponentesEquiposBOInterface gestionarPlantaComponentesEquiposBO;

    private EquipoElemento equipoElementoDetalle;
    //
    private List<ComponenteEquipo> listaComponentesEquipos;
    private List<ComponenteEquipo> listaComponentesEquiposTabla;
    private int posicionComponenteTabla;
    private int tamTotalComponente;
    private boolean bloquearPagSigComponente, bloquearPagAntComponente;
    //

    public ControllerAdministrarComponentes() {
    }

    @PostConstruct
    public void init() {
        listaComponentesEquiposTabla = null;
        listaComponentesEquipos = null;
        posicionComponenteTabla = 0;
        tamTotalComponente = 0;
        bloquearPagAntComponente = true;
        bloquearPagSigComponente = true;
    }

    public void recibirIDEquipoElemento(BigInteger idRegistro) {
        equipoElementoDetalle = gestionarPlantaComponentesEquiposBO.consultarEquipoElementoPorID(idRegistro);
        obtenerComponentesAsociadosAlEquipo();
    }

    private void obtenerComponentesAsociadosAlEquipo() {
        if (null != equipoElementoDetalle) {
            listaComponentesEquipos = gestionarPlantaComponentesEquiposBO.consultarComponentesEquipoPorIDEquipo(equipoElementoDetalle.getIdequipoelemento());
            if (null != listaComponentesEquipos) {
                listaComponentesEquiposTabla = new ArrayList<ComponenteEquipo>();
                tamTotalComponente = listaComponentesEquipos.size();
                posicionComponenteTabla = 0;
                cargarDatosTablaComponenteEquipo();
            }
        }
    }

    private void cargarDatosTablaComponenteEquipo() {
        if (tamTotalComponente < 10) {
            for (int i = 0; i < tamTotalComponente; i++) {
                listaComponentesEquiposTabla.add(listaComponentesEquipos.get(i));
            }
            bloquearPagSigComponente = true;
            bloquearPagAntComponente = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaComponentesEquiposTabla.add(listaComponentesEquipos.get(i));
            }
            bloquearPagSigComponente = false;
            bloquearPagAntComponente = true;
        }
    }

    public void cargarPaginaSiguienteComponenteEquipo() {
        listaComponentesEquiposTabla = new ArrayList<ComponenteEquipo>();
        posicionComponenteTabla = posicionComponenteTabla + 10;
        int diferencia = tamTotalComponente - posicionComponenteTabla;
        if (diferencia > 10) {
            for (int i = posicionComponenteTabla; i < (posicionComponenteTabla + 10); i++) {
                listaComponentesEquiposTabla.add(listaComponentesEquipos.get(i));
            }
            bloquearPagSigComponente = false;
            bloquearPagAntComponente = false;
        } else {
            for (int i = posicionComponenteTabla; i < (posicionComponenteTabla + diferencia); i++) {
                listaComponentesEquiposTabla.add(listaComponentesEquipos.get(i));
            }
            bloquearPagSigComponente = true;
            bloquearPagAntComponente = false;
        }
    }

    public void cargarPaginaAnteriorComponenteEquipo() {
        listaComponentesEquiposTabla = new ArrayList<ComponenteEquipo>();
        posicionComponenteTabla = posicionComponenteTabla - 10;
        int diferencia = tamTotalComponente - posicionComponenteTabla;
        if (diferencia == tamTotalComponente) {
            for (int i = posicionComponenteTabla; i < (posicionComponenteTabla + 10); i++) {
                listaComponentesEquiposTabla.add(listaComponentesEquipos.get(i));
            }
            bloquearPagSigComponente = false;
            bloquearPagAntComponente = true;
        } else {
            for (int i = posicionComponenteTabla; i < (posicionComponenteTabla + 10); i++) {
                listaComponentesEquiposTabla.add(listaComponentesEquipos.get(i));
            }
            bloquearPagSigComponente = false;
            bloquearPagAntComponente = false;
        }
    }

    public String registrarNuevoComponente() {
        listaComponentesEquipos = null;
        listaComponentesEquiposTabla = null;
        posicionComponenteTabla = 0;
        tamTotalComponente = 0;
        bloquearPagAntComponente = true;
        bloquearPagSigComponente = true;
        return "registrar_componente";
    }

    public String limpiarProcesoBusqueda() {
        listaComponentesEquipos = null;
        listaComponentesEquiposTabla = null;
        posicionComponenteTabla = 0;
        tamTotalComponente = 0;
        bloquearPagAntComponente = true;
        bloquearPagSigComponente = true;
        return "detalles_equipo";
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
        return "detalles_componente";
    }

    // GET - SET
    public EquipoElemento getEquipoElementoDetalle() {
        return equipoElementoDetalle;
    }

    public void setEquipoElementoDetalle(EquipoElemento equipoElementoDetalle) {
        this.equipoElementoDetalle = equipoElementoDetalle;
    }

    public List<ComponenteEquipo> getListaComponentesEquipos() {
        return listaComponentesEquipos;
    }

    public void setListaComponentesEquipos(List<ComponenteEquipo> listaComponentesEquipos) {
        this.listaComponentesEquipos = listaComponentesEquipos;
    }

    public List<ComponenteEquipo> getListaComponentesEquiposTabla() {
        return listaComponentesEquiposTabla;
    }

    public void setListaComponentesEquiposTabla(List<ComponenteEquipo> listaComponentesEquiposTabla) {
        this.listaComponentesEquiposTabla = listaComponentesEquiposTabla;
    }

    public boolean isBloquearPagSigComponente() {
        return bloquearPagSigComponente;
    }

    public void setBloquearPagSigComponente(boolean bloquearPagSigComponente) {
        this.bloquearPagSigComponente = bloquearPagSigComponente;
    }

    public boolean isBloquearPagAntComponente() {
        return bloquearPagAntComponente;
    }

    public void setBloquearPagAntComponente(boolean bloquearPagAntComponente) {
        this.bloquearPagAntComponente = bloquearPagAntComponente;
    }

}
