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
public class ControllerDetallesPlanEstudio implements Serializable {

    @EJB
    GestionarPlanesEstudiosBOInterface gestionarPlanesEstudiosBO;

    private PlanEstudios planEstudiosDetalles;
    private BigInteger idPlanEstudios;
    private List<Facultad> listaFacultades;
    private List<Departamento> listaDepartamentos;
    private List<Carrera> listaCarreras;
    private boolean activarModificacionDepartamento;
    private boolean activarModificacionCarrera;
    private String editarNombre, editarCodigo;
    private Facultad editarFacultad;
    private Departamento editarDepartamento;
    private Carrera editarCarrera;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesFacultad, validacionesDepartamento, validacionesCarrera;
    private String mensajeFormulario;

    public ControllerDetallesPlanEstudio() {
    }

    @PostConstruct
    public void init() {
        activarModificacionCarrera = true;
        activarModificacionDepartamento = true;
        validacionesCarrera = true;
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "";
    }

    public void restaurarInformacionPlanEstudio() {
        validacionesCarrera = true;
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "";
        planEstudiosDetalles = new PlanEstudios();
        recibirIDPlanesEstudioDetalles(idPlanEstudios);
    }

    public void asignarValoresVariablesPlanEstudio() {
        editarCarrera = planEstudiosDetalles.getCarrera();
        editarCodigo = planEstudiosDetalles.getCodigoplanestudio();
        editarDepartamento = planEstudiosDetalles.getCarrera().getDepartamento();
        editarFacultad = planEstudiosDetalles.getCarrera().getDepartamento().getFacultad();
        editarNombre = planEstudiosDetalles.getNombreplanestudio();
        activarModificacionDepartamento = false;
        listaFacultades = gestionarPlanesEstudiosBO.consultarFacultadesRegistradas();
        if (Utilidades.validarNulo(editarFacultad)) {
            listaDepartamentos = gestionarPlanesEstudiosBO.consultarDepartamentosPorIDFacultad(editarFacultad.getIdfacultad());
        }
        activarModificacionCarrera = false;
        if (Utilidades.validarNulo(editarDepartamento)) {
            listaCarreras = gestionarPlanesEstudiosBO.consultarCarrerasPorIDDepartamento(editarDepartamento.getIddepartamento());
        }
    }

    public void recibirIDPlanesEstudioDetalles(BigInteger idRegistro) {
        this.idPlanEstudios = idRegistro;
        planEstudiosDetalles = gestionarPlanesEstudiosBO.obtenerPlanEstudiosPorIDPlanEstudio(idPlanEstudios);
        asignarValoresVariablesPlanEstudio();
    }

    public void actualizarFacultad() {
        if (Utilidades.validarNulo(editarFacultad)) {
            editarDepartamento = null;
            listaDepartamentos = gestionarPlanesEstudiosBO.consultarDepartamentosPorIDFacultad(editarFacultad.getIdfacultad());
            activarModificacionDepartamento = false;
            editarCarrera = null;
            activarModificacionCarrera = true;
            listaCarreras = null;
            validacionesFacultad = true;
        } else {
            validacionesDepartamento = false;
            validacionesFacultad = false;
            validacionesCarrera = false;
            editarDepartamento = null;
            listaDepartamentos = null;
            activarModificacionDepartamento = true;
            editarCarrera = null;
            activarModificacionCarrera = true;
            listaCarreras = null;
        }
    }

    public void actualizarDepartamento() {
        if (Utilidades.validarNulo(editarDepartamento)) {
            editarCarrera = null;
            listaCarreras = gestionarPlanesEstudiosBO.consultarCarrerasPorIDDepartamento(editarDepartamento.getIddepartamento());
            activarModificacionCarrera = false;
            validacionesDepartamento = true;
        } else {
            validacionesCarrera = false;
            validacionesDepartamento = false;
            editarCarrera = null;
            listaDepartamentos = null;
            activarModificacionDepartamento = true;
        }
    }

    public void validarCarreraPlanEstudio() {
        if (Utilidades.validarNulo(editarCarrera)) {
            validacionesCarrera = true;
        } else {
            validacionesCarrera = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCarrera", new FacesMessage("La carrera es obligatoria."));
        }
    }

    public void validarNombrePlanEstudio() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(editarNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCodigoPlanEstudio() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio."));
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
        PlanEstudios planEstudios = gestionarPlanesEstudiosBO.obtenerPlanEstudioPorCodigoYCarrera(editarCodigo, editarDepartamento.getIddepartamento());
        if (null != planEstudios) {
            if (!planEstudiosDetalles.getIdplanestudios().equals(planEstudios.getIdplanestudios())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionPlanEstudio() {
        if (validarResultadosValidacion() == true) {
            if (Utilidades.validarNulo(editarCarrera)) {
                if (validarCodigoRepetido() == true) {
                    almacenarModificacionPlanEstudioEnSistema();
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                    recibirIDPlanesEstudioDetalles(this.idPlanEstudios);
                } else {
                    mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el departamento seleccionado.";
                }
            } else {
                mensajeFormulario = "Seleccione una carrera para continuar con el proceso.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionPlanEstudioEnSistema() {
        try {
            planEstudiosDetalles.setNombreplanestudio(editarNombre);
            planEstudiosDetalles.setCodigoplanestudio(editarCodigo);
            planEstudiosDetalles.setCarrera(editarCarrera);
            gestionarPlanesEstudiosBO.modificarInformacionPlanEstudios(planEstudiosDetalles);
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarPlanesEstudios almacenarModificacionPlanEstudioEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public PlanEstudios getPlanEstudiosDetalles() {
        return planEstudiosDetalles;
    }

    public void setPlanEstudiosDetalles(PlanEstudios planEstudiosDetalles) {
        this.planEstudiosDetalles = planEstudiosDetalles;
    }

    public BigInteger getIdPlanEstudios() {
        return idPlanEstudios;
    }

    public void setIdPlanEstudios(BigInteger idPlanEstudios) {
        this.idPlanEstudios = idPlanEstudios;
    }

    public List<Facultad> getListaFacultades() {
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

    public boolean isActivarModificacionDepartamento() {
        return activarModificacionDepartamento;
    }

    public void setActivarModificacionDepartamento(boolean activarModificacionDepartamento) {
        this.activarModificacionDepartamento = activarModificacionDepartamento;
    }

    public boolean isActivarModificacionCarrera() {
        return activarModificacionCarrera;
    }

    public void setActivarModificacionCarrera(boolean activarModificacionCarrera) {
        this.activarModificacionCarrera = activarModificacionCarrera;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarCodigo() {
        return editarCodigo;
    }

    public void setEditarCodigo(String editarCodigo) {
        this.editarCodigo = editarCodigo;
    }

    public Facultad getEditarFacultad() {
        return editarFacultad;
    }

    public void setEditarFacultad(Facultad editarFacultad) {
        this.editarFacultad = editarFacultad;
    }

    public Departamento getEditarDepartamento() {
        return editarDepartamento;
    }

    public void setEditarDepartamento(Departamento editarDepartamento) {
        this.editarDepartamento = editarDepartamento;
    }

    public Carrera getEditarCarrera() {
        return editarCarrera;
    }

    public void setEditarCarrera(Carrera editarCarrera) {
        this.editarCarrera = editarCarrera;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
