/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarPlanesEstudiosBOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarPlanEstudio implements Serializable {

    @EJB
    GestionarPlanesEstudiosBOInterface gestionarPlanesEstudiosBO;

    private List<Facultad> listaFacultades;
    private List<Departamento> listaDepartamentos;
    private List<Carrera> listaCarreras;
    private boolean activarNuevoDepartamento;
    private boolean activarNuevoCarrera;
    private String nuevoNombre, nuevoCodigo;
    private Facultad nuevoFacultad;
    private Departamento nuevoDepartamento;
    private Carrera nuevoCarrera;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesFacultad, validacionesDepartamento, validacionesCarrera;
    private String mensajeFormulario;

    public ControllerRegistrarPlanEstudio() {
    }

    @PostConstruct
    public void init() {
        activarNuevoCarrera = true;
        activarNuevoDepartamento = true;
        nuevoCarrera = null;
        nuevoCodigo = null;
        nuevoDepartamento = null;
        nuevoFacultad = null;
        nuevoNombre = null;
        validacionesCarrera = false;
        validacionesCodigo = false;
        validacionesDepartamento = false;
        validacionesFacultad = false;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void actualizarFacultad() {

        if (Utilidades.validarNulo(nuevoFacultad)) {
            nuevoDepartamento = null;
            listaDepartamentos = gestionarPlanesEstudiosBO.consultarDepartamentosPorIDFacultad(nuevoFacultad.getIdfacultad());
            activarNuevoDepartamento = false;
            nuevoCarrera = null;
            activarNuevoCarrera = true;
            listaCarreras = null;
            validacionesFacultad = true;
        } else {
            validacionesDepartamento = false;
            validacionesFacultad = false;
            validacionesCarrera = false;
            nuevoDepartamento = null;
            listaDepartamentos = null;
            activarNuevoDepartamento = true;
            nuevoCarrera = null;
            activarNuevoCarrera = true;
            listaCarreras = null;
        }
    }

    public void actualizarDepartamento() {
        if (Utilidades.validarNulo(nuevoDepartamento)) {
            nuevoCarrera = null;
            listaCarreras = gestionarPlanesEstudiosBO.consultarCarrerasPorIDDepartamento(nuevoDepartamento.getIddepartamento());
            activarNuevoCarrera = false;
            validacionesDepartamento = true;
        } else {
            validacionesCarrera = false;
            validacionesDepartamento = false;
            nuevoCarrera = null;
            listaDepartamentos = null;
            activarNuevoDepartamento = true;
        }
    }

    public void validarCarreraPlanEstudio() {
        if (Utilidades.validarNulo(nuevoCarrera)) {
            validacionesCarrera = true;
        } else {
            validacionesCarrera = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCarrera", new FacesMessage("La carrera es obligatoria."));
        }
    }

    public void validarNombrePlanEstudio() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCodigoPlanEstudio() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesFacultad == false) {
            retorno = false;
        }
        if (validacionesCarrera == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        PlanEstudios planEstudios = gestionarPlanesEstudiosBO.obtenerPlanEstudioPorCodigoYCarrera(nuevoCodigo, nuevoDepartamento.getIddepartamento());
        if (null != planEstudios) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoPlanEstudio() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoPlanEstudioEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el departamento seleccionado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoPlanEstudioEnSistema() {
        try {
            PlanEstudios planNuevo = new PlanEstudios();
            planNuevo.setNombreplanestudio(nuevoNombre);
            planNuevo.setCodigoplanestudio(nuevoCodigo);
            planNuevo.setCarrera(nuevoCarrera);
            gestionarPlanesEstudiosBO.crearNuevoPlanEstudio(planNuevo);
            limpiarFormulario();
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarPlanesEstudios almacenarNuevoPlanEstudioEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        activarNuevoCarrera = true;
        activarNuevoDepartamento = true;
        nuevoCarrera = null;
        nuevoCodigo = null;
        nuevoDepartamento = null;
        nuevoFacultad = null;
        nuevoNombre = null;
        validacionesCarrera = false;
        validacionesCodigo = false;
        validacionesDepartamento = false;
        validacionesFacultad = false;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroPlanEstudio() {
        activarNuevoCarrera = true;
        activarNuevoDepartamento = true;
        nuevoCarrera = null;
        nuevoCodigo = null;
        nuevoDepartamento = null;
        nuevoFacultad = null;
        nuevoNombre = null;
        validacionesCarrera = false;
        validacionesCodigo = false;
        validacionesDepartamento = false;
        validacionesFacultad = false;
        validacionesNombre = false;
        mensajeFormulario = "";
        listaCarreras = null;
        listaDepartamentos = null;
        listaFacultades = null;
    }

    //GET-SET
    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = gestionarPlanesEstudiosBO.consultarFacultadesRegistradas();
        }
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public boolean isActivarNuevoDepartamento() {
        return activarNuevoDepartamento;
    }

    public void setActivarNuevoDepartamento(boolean activarNuevoDepartamento) {
        this.activarNuevoDepartamento = activarNuevoDepartamento;
    }

    public boolean isActivarNuevoCarrera() {
        return activarNuevoCarrera;
    }

    public void setActivarNuevoCarrera(boolean activarNuevoCarrera) {
        this.activarNuevoCarrera = activarNuevoCarrera;
    }

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
    }

    public Facultad getNuevoFacultad() {
        return nuevoFacultad;
    }

    public void setNuevoFacultad(Facultad nuevoFacultad) {
        this.nuevoFacultad = nuevoFacultad;
    }

    public Departamento getNuevoDepartamento() {
        return nuevoDepartamento;
    }

    public void setNuevoDepartamento(Departamento nuevoDepartamento) {
        this.nuevoDepartamento = nuevoDepartamento;
    }

    public Carrera getNuevoCarrera() {
        return nuevoCarrera;
    }

    public void setNuevoCarrera(Carrera nuevoCarrera) {
        this.nuevoCarrera = nuevoCarrera;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
