/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarPlanesEstudiosBOInterface;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private MensajesConstantes constantes;
    private String mensajeError;
    private List<AsignaturaPorPlanEstudio> listaAsignaturaPorPlanEstudio;
    private List<AsignaturaPorPlanEstudio> listaAsignaturaPorPlanEstudioTabla;
    private int posicionAsignaturaTabla;
    private int tamTotalAsignatura;
    private boolean bloquearPagSigAsignatura, bloquearPagAntAsignatura;
    private boolean activarCasillas;

    public ControllerDetallesPlanEstudio() {
    }

    @PostConstruct
    public void init() {
        activarCasillas = false;
        constantes = new MensajesConstantes();
        activarModificacionCarrera = true;
        activarModificacionDepartamento = true;
        validacionesCarrera = true;
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionPlanEstudio() {
        validacionesCarrera = true;
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        planEstudiosDetalles = new PlanEstudios();
        if (activarCasillas == false) {
            recibirIDPlanesEstudioDetalles(idPlanEstudios);
        } else {
            activarCasillas = false;
            mensajeError = "";
            editarCarrera = null;
            editarEstado = false;
            editarCodigo = null;
            editarDepartamento = null;
            editarFacultad = null;
            editarNombre = null;
            activarModificacionDepartamento = false;
            listaFacultades = null;
            listaDepartamentos = null;
            activarModificacionCarrera = false;
            listaCarreras = null;
            listaAsignaturaPorPlanEstudio = null;
        }
        return "administrarplanesestudio";
    }

    public void asignarValoresVariablesPlanEstudio() {
        mensajeError = "";
        editarCarrera = planEstudiosDetalles.getCarrera();
        editarEstado = planEstudiosDetalles.getEstado();
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
        listaAsignaturaPorPlanEstudio = gestionarPlanesEstudiosBO.obtenerAsignaturaPorPlanEstudioPorIdPlan(idPlanEstudios);
        if (null != listaAsignaturaPorPlanEstudio) {
            listaAsignaturaPorPlanEstudioTabla = new ArrayList<AsignaturaPorPlanEstudio>();
            tamTotalAsignatura = listaAsignaturaPorPlanEstudio.size();
        }
        posicionAsignaturaTabla = 0;
        cargarDatosAsignaturaTabla();
    }

    private void cargarDatosAsignaturaTabla() {
        if (tamTotalAsignatura < 10) {
            for (int i = 0; i < tamTotalAsignatura; i++) {
                listaAsignaturaPorPlanEstudioTabla.add(listaAsignaturaPorPlanEstudio.get(i));
            }
            bloquearPagSigAsignatura = true;
            bloquearPagAntAsignatura = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaAsignaturaPorPlanEstudioTabla.add(listaAsignaturaPorPlanEstudio.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = true;
        }
    }

    public void cargarPaginaSiguienteAsignatura() {
        listaAsignaturaPorPlanEstudioTabla = new ArrayList<AsignaturaPorPlanEstudio>();
        posicionAsignaturaTabla = posicionAsignaturaTabla + 10;
        int diferencia = tamTotalAsignatura - posicionAsignaturaTabla;
        if (diferencia > 10) {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturaPorPlanEstudioTabla.add(listaAsignaturaPorPlanEstudio.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = false;
        } else {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + diferencia); i++) {
                listaAsignaturaPorPlanEstudioTabla.add(listaAsignaturaPorPlanEstudio.get(i));
            }
            bloquearPagSigAsignatura = true;
            bloquearPagAntAsignatura = false;
        }
    }

    public void cargarPaginaAnteriorAisgnatura() {
        listaAsignaturaPorPlanEstudioTabla = new ArrayList<AsignaturaPorPlanEstudio>();
        posicionAsignaturaTabla = posicionAsignaturaTabla - 10;
        int diferencia = tamTotalAsignatura - posicionAsignaturaTabla;
        if (diferencia == tamTotalAsignatura) {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturaPorPlanEstudioTabla.add(listaAsignaturaPorPlanEstudio.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = true;
        } else {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturaPorPlanEstudioTabla.add(listaAsignaturaPorPlanEstudio.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = false;
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
            editarCarrera = null;
            activarModificacionDepartamento = true;
            activarModificacionCarrera = true;
            listaDepartamentos = null;
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
            listaCarreras = null;
            activarModificacionCarrera = true;

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
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.U_NOMBRE_PLAN));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE_PLAN));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. " + constantes.U_NOMBRE_PLAN));
        }
    }

    public void validarCodigoPlanEstudio() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty()) && (editarCodigo.trim().length() > 0)) {
            int tam = editarCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.U_CODIGO_CARR));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.U_CODIGO_CARR));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio. " + constantes.U_CODIGO_CARR));
        }
    }

    private boolean validarCambioEstado() {
        boolean retorno = true;
        if (editarEstado == false) {
            Boolean validaciones = gestionarPlanesEstudiosBO.validarCambioEstadoPlan(planEstudiosDetalles.getIdplanestudios());
            if (null != validaciones) {
                if (validaciones == true) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesCodigo == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            mensajeError = mensajeError + " - Departamento - ";
            retorno = false;
        }
        if (validacionesFacultad == false) {
            mensajeError = mensajeError + " - Facultad - ";
            retorno = false;
        }
        if (validacionesCarrera == false) {
            mensajeError = mensajeError + " - Carrera - ";
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        PlanEstudios planEstudios = gestionarPlanesEstudiosBO.obtenerPlanEstudioPorCodigo(editarCodigo);
        if (null != planEstudios) {
            if (!planEstudiosDetalles.getIdplanestudios().equals(planEstudios.getIdplanestudios())) {
                retorno = false;
            }
        }
        return retorno;
    }

    private boolean validarAsociacionPlanAsignatura() {
        boolean retorno = true;
        int antiguos = validarRegistroAntiguosAsociacion();
        if (antiguos == 0) {
            retorno = false;
        }
        return retorno;
    }

    private int validarRegistroAntiguosAsociacion() {
        int cantidad = 0;
        if (Utilidades.validarNulo(listaAsignaturaPorPlanEstudio)) {
            for (int i = 0; i < listaAsignaturaPorPlanEstudio.size(); i++) {
                if (listaAsignaturaPorPlanEstudio.get(i).getEstado() == true) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    public void registrarModificacionPlanEstudio() {
        if (validarResultadosValidacion() == true) {
            if (Utilidades.validarNulo(editarCarrera)) {
                if (validarCodigoRepetido() == true) {
                    if (validarAsociacionPlanAsignatura() == true) {
                        if (validarCambioEstado() == true) {
                            almacenarModificacionPlanEstudioEnSistema();
                            restaurarInformacionPlanEstudio();
                            colorMensaje = "green";
                            mensajeFormulario = "El formulario ha sido ingresado con exito.";
                        } else {
                            colorMensaje = "#FF0000";
                            mensajeFormulario = "Existen asignaturas asociadas. Imposible cambiar el estado del plan de estudio.";
                        }
                    } else {
                        colorMensaje = "#FF0000";
                        mensajeFormulario = "Es necesario que al menos una asignatura se encuentre asociada al plan de estudio.";
                    }
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Seleccione una carrera para continuar con el proceso.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    private void almacenarModificacionPlanEstudioEnSistema() {
        try {
            planEstudiosDetalles.setNombreplanestudio(editarNombre);
            planEstudiosDetalles.setCodigoplanestudio(editarCodigo);
            planEstudiosDetalles.setEstado(editarEstado);
            planEstudiosDetalles.setCarrera(editarCarrera);
            gestionarPlanesEstudiosBO.modificarInformacionPlanEstudios(planEstudiosDetalles);
            gestionarPlanesEstudiosBO.modificarInformacionAsignaturaPorPlanEstudio(listaAsignaturaPorPlanEstudio);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlanEstudio almacenarModificacionPlanEstudioEnSistema : " + e.toString(), e);
        }
    }

    public void eliminarPlanEstudio() {
        List<AsignaturaPorPlanEstudio> lista = gestionarPlanesEstudiosBO.obtenerAsignaturaPorPlanEstudioPorIdPlan(idPlanEstudios);
        if (null == lista) {
            boolean respuesta = gestionarPlanesEstudiosBO.eliminarPlanEstudio(planEstudiosDetalles);
            if (respuesta == true) {
                activarCasillas = true;
                colorMensaje = "#FF0000";
                mensajeFormulario = "El registro ha sido eliminado con éxito. Regrese nuevamente a la pagina de consulta.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El registro no pudo ser eliminado. Intente más tarde.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "El registro no puede ser eliminado dado que tiene asociado asignaturas.";
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

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isEditarEstado() {
        return editarEstado;
    }

    public void setEditarEstado(boolean editarEstado) {
        this.editarEstado = editarEstado;
    }

    public List<AsignaturaPorPlanEstudio> getListaAsignaturaPorPlanEstudio() {
        return listaAsignaturaPorPlanEstudio;
    }

    public void setListaAsignaturaPorPlanEstudio(List<AsignaturaPorPlanEstudio> listaAsignaturaPorPlanEstudio) {
        this.listaAsignaturaPorPlanEstudio = listaAsignaturaPorPlanEstudio;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public List<AsignaturaPorPlanEstudio> getListaAsignaturaPorPlanEstudioTabla() {
        return listaAsignaturaPorPlanEstudioTabla;
    }

    public void setListaAsignaturaPorPlanEstudioTabla(List<AsignaturaPorPlanEstudio> listaAsignaturaPorPlanEstudioTabla) {
        this.listaAsignaturaPorPlanEstudioTabla = listaAsignaturaPorPlanEstudioTabla;
    }

    public int getPosicionAsignaturaTabla() {
        return posicionAsignaturaTabla;
    }

    public void setPosicionAsignaturaTabla(int posicionAsignaturaTabla) {
        this.posicionAsignaturaTabla = posicionAsignaturaTabla;
    }

    public int getTamTotalAsignatura() {
        return tamTotalAsignatura;
    }

    public void setTamTotalAsignatura(int tamTotalAsignatura) {
        this.tamTotalAsignatura = tamTotalAsignatura;
    }

    public boolean isBloquearPagSigAsignatura() {
        return bloquearPagSigAsignatura;
    }

    public void setBloquearPagSigAsignatura(boolean bloquearPagSigAsignatura) {
        this.bloquearPagSigAsignatura = bloquearPagSigAsignatura;
    }

    public boolean isBloquearPagAntAsignatura() {
        return bloquearPagAntAsignatura;
    }

    public void setBloquearPagAntAsignatura(boolean bloquearPagAntAsignatura) {
        this.bloquearPagAntAsignatura = bloquearPagAntAsignatura;
    }

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

}
