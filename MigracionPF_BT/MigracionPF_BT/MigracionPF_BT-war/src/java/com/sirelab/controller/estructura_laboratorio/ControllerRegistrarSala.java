/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.GestionarPlantaSalasBOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
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
public class ControllerRegistrarSala implements Serializable {

    @EJB
    GestionarPlantaSalasBOInterface gestionarPlantaSalasBO;

    private String nuevoNombreSala, nuevoCodigoSala, nuevoDescripcionSala, nuevoUbicacionSala, nuevoCapacidadSala, nuevoCostoSala, nuevoInversionSala;
    private LaboratoriosPorAreas nuevoLaboratorioPorArea;
    private Sede nuevoSedeSala;
    private Edificio nuevoEdificioSala;
    private boolean activarNuevoEdificio;
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private List<Sede> listaSedes;
    private List<Edificio> listaEdificios;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesDescripcion, validacionesUbicacion;
    private boolean validacionesCapacidad, validacionesCosto, validacionesInversion;
    private boolean validacionesLaboratorio, validacionesSede, validacionesEdificio;
    private String mensajeFormulario;

    public ControllerRegistrarSala() {
    }

    @PostConstruct
    public void init() {
        validacionesCosto = true;
        validacionesCapacidad = false;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesEdificio = false;
        validacionesInversion = false;
        validacionesLaboratorio = false;
        validacionesNombre = false;
        validacionesSede = false;
        validacionesUbicacion = false;
        mensajeFormulario = "";
        //
        activarNuevoEdificio = true;
        nuevoNombreSala = null;
        nuevoCodigoSala = null;
        nuevoUbicacionSala = null;
        nuevoDescripcionSala = null;
        nuevoCostoSala = null;
        nuevoCapacidadSala = null;
        nuevoInversionSala = null;
        nuevoSedeSala = null;
        nuevoEdificioSala = null;
        listaEdificios = null;
    }

    public void validarNombreSala() {
        if (Utilidades.validarNulo(nuevoNombreSala) && (!nuevoNombreSala.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombreSala)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombreSala", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombreSala", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarCodigoSala() {
        if (Utilidades.validarNulo(nuevoCodigoSala) && (!nuevoCodigoSala.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigoSala)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoSala", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoSala", new FacesMessage("El codigo es obligatorio."));
        }
    }

    public void validarUbicacionSala() {
        if (Utilidades.validarNulo(nuevoUbicacionSala) && (!nuevoUbicacionSala.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(nuevoUbicacionSala)) {
                validacionesUbicacion = true;
            } else {
                validacionesUbicacion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoUbicacionSala", new FacesMessage("La ubicación se encuentra incorrecta."));
            }
        } else {
            validacionesUbicacion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoUbicacionSala", new FacesMessage("La ubicación es obligatoria."));
        }
    }

    public void validarCostoAlquilerSala() {
        if (Utilidades.validarNulo(nuevoCostoSala) && (!nuevoCostoSala.isEmpty())) {
            if (Utilidades.isNumber(nuevoCostoSala)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoSala", new FacesMessage("El costo se encuentra incorrecto."));
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCostoSala", new FacesMessage("El costo es obligatorio."));
        }
    }

    public void validarCapacidadSala() {
        if (Utilidades.validarNulo(nuevoCapacidadSala) && (!nuevoCapacidadSala.isEmpty())) {
            if ((Utilidades.isNumber(nuevoCapacidadSala)) == false) {
                validacionesCapacidad = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCapacidadSala", new FacesMessage("La capacidad ingresada se encuentra incorrecta."));
            } else {
                validacionesCapacidad = true;
            }
        } else {
            validacionesCapacidad = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCapacidadSala", new FacesMessage("La capacidad es obligatoria."));
        }
    }

    public void validarDescripcionSala() {
        if (Utilidades.validarNulo(nuevoDescripcionSala) && (!nuevoDescripcionSala.isEmpty())) {
            if ((Utilidades.validarCaracteresAlfaNumericos(nuevoDescripcionSala)) == false) {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionSala", new FacesMessage("La descripción se encuentra incorrecta."));
            } else {
                validacionesDescripcion = true;
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionSala", new FacesMessage("La descripción es obligatoria."));
        }
    }

    public void validarInversionSala() {
        if (Utilidades.validarNulo(nuevoInversionSala) && (!nuevoInversionSala.isEmpty())) {
            if ((Utilidades.isNumber(nuevoInversionSala)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoInversionSala", new FacesMessage("El valor de inversión se encuentra incorrecto."));
            } else {
                validacionesInversion = true;
            }
        }
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(nuevoLaboratorioPorArea)) {
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoLaboratorioPorArea", new FacesMessage("El laboratorio por area es obligatorio."));
        }
    }

    public void actualizarNuevoSede() {
        if (Utilidades.validarNulo(nuevoSedeSala)) {
            nuevoEdificioSala = null;
            listaEdificios = gestionarPlantaSalasBO.consultarEdificiosPorIDSede(nuevoSedeSala.getIdsede());
            activarNuevoEdificio = false;
            validacionesSede = true;
        } else {
            validacionesSede = false;
            validacionesEdificio = false;
            nuevoEdificioSala = null;
            listaEdificios = null;
            activarNuevoEdificio = true;
            FacesContext.getCurrentInstance().addMessage("form:nuevoSedeSala", new FacesMessage("La sede es obligatoria."));
        }
    }

    public void actualizarEdificios() {
        if (Utilidades.validarNulo(nuevoEdificioSala)) {
            validacionesEdificio = true;
        } else {
            validacionesEdificio = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoEdificioSala", new FacesMessage("El edificio es obligatorio."));
        }
    }

    public void limpiarRegistroSalaLaboratorio() {
        activarNuevoEdificio = true;
        nuevoNombreSala = null;
        nuevoCodigoSala = null;
        nuevoUbicacionSala = null;
        nuevoDescripcionSala = null;
        nuevoCostoSala = null;
        nuevoCapacidadSala = null;
        nuevoInversionSala = null;
        nuevoSedeSala = null;
        nuevoEdificioSala = null;
        listaEdificios = null;
        //
        validacionesCosto = true;
        validacionesCapacidad = false;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesEdificio = false;
        validacionesInversion = false;
        validacionesLaboratorio = false;
        validacionesNombre = false;
        validacionesSede = false;
        validacionesUbicacion = false;
        mensajeFormulario = "";
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
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesEdificio == false) {
            retorno = false;
        }
        if (validacionesInversion == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesSede == false) {
            retorno = false;
        }
        if (validacionesUbicacion == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        SalaLaboratorio sala = gestionarPlantaSalasBO.obtenerSalaLaboratorioPorCodigoEdificioLabArea(nuevoCodigoSala, nuevoEdificioSala.getIdedificio(), nuevoLaboratorioPorArea.getIdlaboratoriosporareas());
        if (null != sala) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoSala() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenaNuevoSalaEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                mensajeFormulario = "El codigo ya esta registrado con el edificio y laboratorio por area seleccionado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenaNuevoSalaEnSistema() {
        try {
            SalaLaboratorio salaNuevo = new SalaLaboratorio();
            salaNuevo.setNombresala(nuevoNombreSala);
            salaNuevo.setCodigosala(nuevoCodigoSala);
            salaNuevo.setPisoubicacion(nuevoUbicacionSala);
            salaNuevo.setDescripcionsala(nuevoDescripcionSala);
            salaNuevo.setCostoalquiler(Long.parseLong(nuevoCostoSala));
            salaNuevo.setEstadosala(true);
            salaNuevo.setCapacidadsala(Integer.parseInt(nuevoCapacidadSala));
            salaNuevo.setValorinversion(new BigInteger(nuevoInversionSala));
            salaNuevo.setEdificio(nuevoEdificioSala);
            salaNuevo.setLaboratoriosporareas(nuevoLaboratorioPorArea);
            gestionarPlantaSalasBO.crearNuevaSalaLaboratorio(salaNuevo);
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarPlantaSalas almacenaNuevoSalaEnSistema : " + e.toString());
        }
    }

    //GET-SET    
    public String getNuevoNombreSala() {
        return nuevoNombreSala;
    }

    public void setNuevoNombreSala(String nuevoNombreSala) {
        this.nuevoNombreSala = nuevoNombreSala;
    }

    public String getNuevoCodigoSala() {
        return nuevoCodigoSala;
    }

    public void setNuevoCodigoSala(String nuevoCodigoSala) {
        this.nuevoCodigoSala = nuevoCodigoSala;
    }

    public String getNuevoDescripcionSala() {
        return nuevoDescripcionSala;
    }

    public void setNuevoDescripcionSala(String nuevoDescripcionSala) {
        this.nuevoDescripcionSala = nuevoDescripcionSala;
    }

    public String getNuevoUbicacionSala() {
        return nuevoUbicacionSala;
    }

    public void setNuevoUbicacionSala(String nuevoUbicacionSala) {
        this.nuevoUbicacionSala = nuevoUbicacionSala;
    }

    public String getNuevoCapacidadSala() {
        return nuevoCapacidadSala;
    }

    public void setNuevoCapacidadSala(String nuevoCapacidadSala) {
        this.nuevoCapacidadSala = nuevoCapacidadSala;
    }

    public String getNuevoCostoSala() {
        return nuevoCostoSala;
    }

    public void setNuevoCostoSala(String nuevoCostoSala) {
        this.nuevoCostoSala = nuevoCostoSala;
    }

    public String getNuevoInversionSala() {
        return nuevoInversionSala;
    }

    public void setNuevoInversionSala(String nuevoInversionSala) {
        this.nuevoInversionSala = nuevoInversionSala;
    }

    public LaboratoriosPorAreas getNuevoLaboratorioPorArea() {
        return nuevoLaboratorioPorArea;
    }

    public void setNuevoLaboratorioPorArea(LaboratoriosPorAreas nuevoLaboratorioPorArea) {
        this.nuevoLaboratorioPorArea = nuevoLaboratorioPorArea;
    }

    public Sede getNuevoSedeSala() {
        return nuevoSedeSala;
    }

    public void setNuevoSedeSala(Sede nuevoSedeSala) {
        this.nuevoSedeSala = nuevoSedeSala;
    }

    public Edificio getNuevoEdificioSala() {
        return nuevoEdificioSala;
    }

    public void setNuevoEdificioSala(Edificio nuevoEdificioSala) {
        this.nuevoEdificioSala = nuevoEdificioSala;
    }

    public boolean isActivarNuevoEdificio() {
        return activarNuevoEdificio;
    }

    public void setActivarNuevoEdificio(boolean activarNuevoEdificio) {
        this.activarNuevoEdificio = activarNuevoEdificio;
    }

    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreas() {
        if (listaLaboratoriosPorAreas == null) {
            listaLaboratoriosPorAreas = gestionarPlantaSalasBO.consultarLaboratoriosPorAreasRegistradas();
        }
        return listaLaboratoriosPorAreas;
    }

    public void setListaLaboratoriosPorAreas(List<LaboratoriosPorAreas> listaLaboratoriosPorAreas) {
        this.listaLaboratoriosPorAreas = listaLaboratoriosPorAreas;
    }

    public List<Sede> getListaSedes() {
        if (listaSedes == null) {
            listaSedes = gestionarPlantaSalasBO.consultarSedesRegistradas();
        }
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<Edificio> getListaEdificios() {
        return listaEdificios;
    }

    public void setListaEdificios(List<Edificio> listaEdificios) {
        this.listaEdificios = listaEdificios;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
