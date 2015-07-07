/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaModulosBOInterface;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarModulo implements Serializable {

    @EJB
    GestionarPlantaModulosBOInterface gestionarPlantaModulosBO;

    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private List<SalaLaboratorio> listaSalasLaboratorios;
    private boolean activarNuevoSala;
    private String nuevoCodigoModulo, nuevoDetalleModulo, nuevoCapacidadModulo, nuevoCostoModulo, nuevoInversionModulo;
    private LaboratoriosPorAreas nuevoLaboratorioPorAreaModulo;
    private SalaLaboratorio nuevoSalaLaboratorioModulo;
    //
    private boolean validacionesCodigo, validacionesDetalle, validacionesCapacidad, validacionesCosto, validacionesInversion;
    private boolean validacionesLaboratorio, validacionesSala;
    private String mensajeFormulario;

    public ControllerRegistrarModulo() {
    }

    @PostConstruct
    public void init() {
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesLaboratorio = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        mensajeFormulario = "";
        activarNuevoSala = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        nuevoInversionModulo = null;
        nuevoSalaLaboratorioModulo = null;
        nuevoLaboratorioPorAreaModulo = null;
    }

    public void limpiarRegistroModuloLaboratorio() {
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesLaboratorio = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        mensajeFormulario = "";
        activarNuevoSala = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        nuevoInversionModulo = null;
        nuevoSalaLaboratorioModulo = null;
        nuevoLaboratorioPorAreaModulo = null;
        listaSalasLaboratorios = null;
    }

    public void validarDetalleModulo() {
        if (Utilidades.validarNulo(nuevoDetalleModulo) && (!nuevoDetalleModulo.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoDetalleModulo)) {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El detalle ingresado es incorrecto."));
            } else {
                validacionesDetalle = true;
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El detalle es obligatorio."));
        }

    }

    public void validarCodigoModulo() {
        if (Utilidades.validarNulo(nuevoCodigoModulo) && (!nuevoCodigoModulo.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigoModulo)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoModulo", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoModulo", new FacesMessage("El codigo es obligatorio."));
        }
    }

    public void validarCostoAlquilerModulo() {
        if (Utilidades.validarNulo(nuevoCostoModulo) && (!nuevoCostoModulo.isEmpty())) {
            if (Utilidades.isNumber(nuevoCostoModulo)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoModulo", new FacesMessage("El costo se encuentra incorrecto."));
            }
        }
    }

    public void validarCapacidadModulo() {
        if (Utilidades.validarNulo(nuevoCapacidadModulo) && (!nuevoCapacidadModulo.isEmpty())) {
            if ((Utilidades.isNumber(nuevoCapacidadModulo)) == false) {
                validacionesCapacidad = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCapacidadModulo", new FacesMessage("La capacidad ingresada se encuentra incorrecta."));
            } else {
                validacionesCapacidad = true;
            }
        }
    }

    public void validarInversionModulo() {
        if (Utilidades.validarNulo(nuevoInversionModulo) && (!nuevoInversionModulo.isEmpty())) {
            if ((Utilidades.isNumber(nuevoInversionModulo)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoInversionModulo", new FacesMessage("El valor de inversión se encuentra incorrecto."));
            } else {
                validacionesInversion = true;
            }
        }
    }

    public void actualizarLaboratoriosPorAreas() {
        if (Utilidades.validarNulo(nuevoLaboratorioPorAreaModulo)) {
            nuevoSalaLaboratorioModulo = new SalaLaboratorio();
            listaSalasLaboratorios = gestionarPlantaModulosBO.consultarSalasLaboratoriosPorIDLaboratorioArea(nuevoLaboratorioPorAreaModulo.getIdlaboratoriosporareas());
            activarNuevoSala = false;
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            validacionesSala = false;
            nuevoSalaLaboratorioModulo = new SalaLaboratorio();
            listaSalasLaboratorios = null;
            activarNuevoSala = true;
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoLaboratorioPorAreaModulo", new FacesMessage("El laboratorio por area es obligatorio."));
        }
    }

    public void actualizarSalas() {
        if (Utilidades.validarNulo(nuevoSalaLaboratorioModulo)) {
            validacionesSala = true;
        } else {
            validacionesSala = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoSalaLaboratorioModulo", new FacesMessage("La sala de laboratorio es obligatoria."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesCapacidad == false) {
            retorno = false;
        }
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesDetalle == false) {
            retorno = false;
        }
        if (validacionesInversion == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            retorno = false;
        }
        if (validacionesSala == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        ModuloLaboratorio modulo = gestionarPlantaModulosBO.obtenerModuloLaboratorioPorCodigoYSala(nuevoCodigoModulo, nuevoSalaLaboratorioModulo.getIdsalalaboratorio());
        if (null != modulo) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoModulo() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenaNuevoModuloEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                mensajeFormulario = "El codigo ya esta registrado con el edificio y laboratorio por area seleccionado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenaNuevoModuloEnSistema() {
        try {
            ModuloLaboratorio salaNuevo = new ModuloLaboratorio();
            salaNuevo.setCodigomodulo(nuevoCodigoModulo);
            salaNuevo.setDetallemodulo(nuevoDetalleModulo);
            salaNuevo.setCostoalquiler(new BigInteger(nuevoCostoModulo));
            salaNuevo.setCostomodulo(new BigInteger(nuevoInversionModulo));
            salaNuevo.setCapacidadmodulo(Integer.valueOf(nuevoCapacidadModulo));
            salaNuevo.setEstadomodulo(true);
            salaNuevo.setSalalaboratorio(nuevoSalaLaboratorioModulo);
            gestionarPlantaModulosBO.crearNuevoModuloLaboratorio(salaNuevo);
            limpiarFormulario();
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarPlantaModulos almacenaNuevoModuloEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesLaboratorio = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        mensajeFormulario = "";
        activarNuevoSala = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        nuevoInversionModulo = null;
        nuevoSalaLaboratorioModulo = null;
        nuevoLaboratorioPorAreaModulo = null;
    }

    //GET-SET
    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreas() {
        if (listaLaboratoriosPorAreas == null) {
            listaLaboratoriosPorAreas = gestionarPlantaModulosBO.consultarLaboratoriosPorAreasRegistradas();
        }
        return listaLaboratoriosPorAreas;
    }

    public void setListaLaboratoriosPorAreas(List<LaboratoriosPorAreas> listaLaboratoriosPorAreas) {
        this.listaLaboratoriosPorAreas = listaLaboratoriosPorAreas;
    }

    public List<SalaLaboratorio> getListaSalasLaboratorios() {
        return listaSalasLaboratorios;
    }

    public void setListaSalasLaboratorios(List<SalaLaboratorio> listaSalasLaboratorios) {
        this.listaSalasLaboratorios = listaSalasLaboratorios;
    }

    public boolean isActivarNuevoSala() {
        return activarNuevoSala;
    }

    public void setActivarNuevoSala(boolean activarNuevoSala) {
        this.activarNuevoSala = activarNuevoSala;
    }

    public String getNuevoCodigoModulo() {
        return nuevoCodigoModulo;
    }

    public void setNuevoCodigoModulo(String nuevoCodigoModulo) {
        this.nuevoCodigoModulo = nuevoCodigoModulo;
    }

    public String getNuevoDetalleModulo() {
        return nuevoDetalleModulo;
    }

    public void setNuevoDetalleModulo(String nuevoDetalleModulo) {
        this.nuevoDetalleModulo = nuevoDetalleModulo;
    }

    public String getNuevoCapacidadModulo() {
        return nuevoCapacidadModulo;
    }

    public void setNuevoCapacidadModulo(String nuevoCapacidadModulo) {
        this.nuevoCapacidadModulo = nuevoCapacidadModulo;
    }

    public String getNuevoCostoModulo() {
        return nuevoCostoModulo;
    }

    public void setNuevoCostoModulo(String nuevoCostoModulo) {
        this.nuevoCostoModulo = nuevoCostoModulo;
    }

    public String getNuevoInversionModulo() {
        return nuevoInversionModulo;
    }

    public void setNuevoInversionModulo(String nuevoInversionModulo) {
        this.nuevoInversionModulo = nuevoInversionModulo;
    }

    public LaboratoriosPorAreas getNuevoLaboratorioPorAreaModulo() {
        return nuevoLaboratorioPorAreaModulo;
    }

    public void setNuevoLaboratorioPorAreaModulo(LaboratoriosPorAreas nuevoLaboratorioPorAreaModulo) {
        this.nuevoLaboratorioPorAreaModulo = nuevoLaboratorioPorAreaModulo;
    }

    public SalaLaboratorio getNuevoSalaLaboratorioModulo() {
        return nuevoSalaLaboratorioModulo;
    }

    public void setNuevoSalaLaboratorioModulo(SalaLaboratorio nuevoSalaLaboratorioModulo) {
        this.nuevoSalaLaboratorioModulo = nuevoSalaLaboratorioModulo;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
