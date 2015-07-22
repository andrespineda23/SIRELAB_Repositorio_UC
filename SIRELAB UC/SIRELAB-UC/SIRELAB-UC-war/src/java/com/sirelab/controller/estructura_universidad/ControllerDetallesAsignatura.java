/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarAsignaturasBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesAsignatura implements Serializable {

    @EJB
    GestionarAsignaturasBOInterface gestionarAsignaturasBO;

    private Asignatura asignaturaDetalles;
    private BigInteger idAsignatura;
    private List<Departamento> listaDepartamentos;
    private List<Carrera> listaCarreras;
    private List<PlanEstudios> listaPlanesEstudios;
    //
    private boolean activarModificacionCarrera;
    private boolean activarModificacionPlanEstudio;
    //
    private String editarNombre, editarCredito, editarCodigo;
    private Departamento editarDepartamento;
    private Carrera editarCarrera;
    private PlanEstudios editarPlanEstudio;
    //
    private boolean validacionesNombre, validacionesCredito, validacionesCodigo;
    private boolean validacionesDepartamento, validacionesCarrera, validacionesPlanEstudio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesAsignatura() {
    }

    @PostConstruct
    public void init() {
        activarModificacionCarrera = true;
        activarModificacionPlanEstudio = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionAsignatura() {
        validacionesCarrera = true;
        validacionesCodigo = true;
        validacionesCredito = true;
        validacionesDepartamento = true;
        validacionesNombre = true;
        validacionesPlanEstudio = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        asignaturaDetalles = new Asignatura();
        recibirIDAsignaturasDetalles(idAsignatura);
        return "administrar_asignaturas";
    }

    public void asignarValoresVariablesAsignatura() {
        editarCarrera = asignaturaDetalles.getPlanestudios().getCarrera();
        editarCodigo = asignaturaDetalles.getCodigoasignatura();
        editarCredito = String.valueOf(asignaturaDetalles.getNumerocreditos());
        editarDepartamento = asignaturaDetalles.getPlanestudios().getCarrera().getDepartamento();
        editarNombre = asignaturaDetalles.getNombreasignatura();
        editarPlanEstudio = asignaturaDetalles.getPlanestudios();
        listaDepartamentos = gestionarAsignaturasBO.consultarDepartamentosRegistrados();
        //
        validacionesCarrera = true;
        validacionesCodigo = true;
        validacionesCredito = true;
        validacionesDepartamento = true;
        validacionesNombre = true;
        validacionesPlanEstudio = true;
        getListaDepartamentos();
        activarModificacionCarrera = false;
        if (Utilidades.validarNulo(editarDepartamento)) {
            listaCarreras = gestionarAsignaturasBO.consultarCarrerasPorIDDepartamento(editarDepartamento.getIddepartamento());
        }
        activarModificacionPlanEstudio = false;
        if (Utilidades.validarNulo(editarCarrera)) {
            listaPlanesEstudios = gestionarAsignaturasBO.consultarPlanesEstudiosPorIDCarrera(editarCarrera.getIdcarrera());
        }
    }

    public void recibirIDAsignaturasDetalles(BigInteger idDetalle) {
        this.idAsignatura = idDetalle;
        asignaturaDetalles = gestionarAsignaturasBO.obtenerAsignaturaPorIDAsignatura(idAsignatura);
        asignarValoresVariablesAsignatura();
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(editarDepartamento)) {
            editarCarrera = null;
            listaCarreras = gestionarAsignaturasBO.consultarCarrerasPorIDDepartamento(editarDepartamento.getIddepartamento());
            activarModificacionCarrera = false;
            listaPlanesEstudios = null;
            editarPlanEstudio = null;
            activarModificacionPlanEstudio = true;
            validacionesDepartamento = true;
        } else {
            validacionesCarrera = false;
            validacionesDepartamento = false;
            validacionesPlanEstudio = false;
            editarCarrera = null;
            listaCarreras = null;
            listaPlanesEstudios = null;
            editarPlanEstudio = null;
            activarModificacionCarrera = true;
            activarModificacionPlanEstudio = true;
        }
    }

    public void actualizarCarreras() {
        if (Utilidades.validarNulo(editarCarrera)) {
            editarPlanEstudio = null;
            listaPlanesEstudios = gestionarAsignaturasBO.consultarPlanesEstudiosPorIDCarrera(editarCarrera.getIdcarrera());
            activarModificacionPlanEstudio = false;
            validacionesCarrera = true;
        } else {
            validacionesCarrera = false;
            validacionesPlanEstudio = false;
            listaPlanesEstudios = null;
            editarPlanEstudio = null;
            activarModificacionPlanEstudio = true;
            FacesContext.getCurrentInstance().addMessage("form:editarCarrera", new FacesMessage("La Carrera es obligatoria."));
        }
    }

    public void actualizarPlanEstudios() {
        if (Utilidades.validarNulo(editarPlanEstudio)) {
            validacionesPlanEstudio = true;
        } else {
            validacionesPlanEstudio = false;
        }
    }

    public void validarNombreAsignatura() {
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

    public void validarCodigoAsignatura() {
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

    public void validarCreditosAsignatura() {
        if (Utilidades.validarNulo(editarCredito) && (!editarCredito.isEmpty())) {
            if (!Utilidades.isNumber(editarCredito)) {
                validacionesCredito = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCredito", new FacesMessage("El credito ingresado es incorrecto."));
            } else {
                validacionesCredito = true;
            }
        } else {
            validacionesCredito = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCredito", new FacesMessage("El credito es obligatorio."));
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
        if (validacionesCredito == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesPlanEstudio == false) {
            retorno = false;
        }
        if (validacionesCarrera == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        Asignatura asignatura = gestionarAsignaturasBO.obtenerAsignaturaPorCodigoYPlanEstudio(editarCodigo, editarPlanEstudio.getIdplanestudios());
        if (null != asignatura) {
            if (!asignaturaDetalles.getIdasignatura().equals(asignatura.getIdasignatura())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionAsignatura() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarModificacionAsignaturaEnSistema();
                recibirIDAsignaturasDetalles(this.idAsignatura);
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el plan de estudio seleccionado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionAsignaturaEnSistema() {
        try {
            asignaturaDetalles.setNombreasignatura(editarNombre);
            Integer creditos = Integer.valueOf(editarCredito);
            asignaturaDetalles.setNumerocreditos(creditos.intValue());
            asignaturaDetalles.setPlanestudios(editarPlanEstudio);
            gestionarAsignaturasBO.modificarInformacionAsignatura(asignaturaDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarAsignaturas almacenarModificacionAsignaturaEnSistema:  " + e.toString());
            System.out.println("Error ControllerGestionarAsignaturas almacenarModificacionAsignaturaEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public Asignatura getAsignaturaDetalles() {
        return asignaturaDetalles;
    }

    public void setAsignaturaDetalles(Asignatura asignaturaDetalles) {
        this.asignaturaDetalles = asignaturaDetalles;
    }

    public BigInteger getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(BigInteger idAsignatura) {
        this.idAsignatura = idAsignatura;
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

    public List<PlanEstudios> getListaPlanesEstudios() {
        return listaPlanesEstudios;
    }

    public void setListaPlanesEstudios(List<PlanEstudios> listaPlanesEstudios) {
        this.listaPlanesEstudios = listaPlanesEstudios;
    }

    public boolean isActivarModificacionCarrera() {
        return activarModificacionCarrera;
    }

    public void setActivarModificacionCarrera(boolean activarModificacionCarrera) {
        this.activarModificacionCarrera = activarModificacionCarrera;
    }

    public boolean isActivarModificacionPlanEstudio() {
        return activarModificacionPlanEstudio;
    }

    public void setActivarModificacionPlanEstudio(boolean activarModificacionPlanEstudio) {
        this.activarModificacionPlanEstudio = activarModificacionPlanEstudio;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarCredito() {
        return editarCredito;
    }

    public void setEditarCredito(String editarCredito) {
        this.editarCredito = editarCredito;
    }

    public String getEditarCodigo() {
        return editarCodigo;
    }

    public void setEditarCodigo(String editarCodigo) {
        this.editarCodigo = editarCodigo;
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

    public PlanEstudios getEditarPlanEstudio() {
        return editarPlanEstudio;
    }

    public void setEditarPlanEstudio(PlanEstudios editarPlanEstudio) {
        this.editarPlanEstudio = editarPlanEstudio;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
