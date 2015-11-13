/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.AsociacionPlanAsignatura;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarPlanesEstudiosBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
    private List<Asignatura> listaAsignaturas;
    private List<AsociacionPlanAsignatura> listaAsociacionPlanAsignatura;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesFacultad, validacionesDepartamento, validacionesCarrera;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarPlanEstudio() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarAceptar = false;
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
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
        cargarAsociacionPlanAsignatura();
    }

    private void cargarAsociacionPlanAsignatura() {
        listaAsignaturas = gestionarPlanesEstudiosBO.obtenerAsignaturasRegistradas();
        if (null != listaAsignaturas) {
            listaAsociacionPlanAsignatura = new ArrayList<AsociacionPlanAsignatura>();
            for (int i = 0; i < listaAsignaturas.size(); i++) {
                AsociacionPlanAsignatura nuevo = new AsociacionPlanAsignatura();
                nuevo.setActivo(false);
                nuevo.setAsignatura(listaAsignaturas.get(i));
                listaAsociacionPlanAsignatura.add(nuevo);
            }
        }
    }

    public void actualizarFacultad() {

        if (Utilidades.validarNulo(nuevoFacultad)) {
            nuevoDepartamento = null;
            listaDepartamentos = gestionarPlanesEstudiosBO.consultarDepartamentosActivosPorIDFacultad(nuevoFacultad.getIdfacultad());
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
            listaCarreras = gestionarPlanesEstudiosBO.consultarCarrerasActivosPorIDDepartamento(nuevoDepartamento.getIddepartamento());
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
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.U_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. "+constantes.U_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio. "+constantes.U_NOMBRE));
        }
    }

    public void validarCodigoPlanEstudio() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty()) && (nuevoCodigo.trim().length() > 0)) {
            int tam = nuevoCodigo.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto. "+constantes.U_CODIGO_CARR));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. "+constantes.U_CODIGO_CARR));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio. "+constantes.U_CODIGO_CARR));
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
        PlanEstudios planEstudios = gestionarPlanesEstudiosBO.obtenerPlanEstudioPorCodigo(nuevoCodigo);
        if (null != planEstudios) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarAsociacionAsignatura() {
        boolean retorno = false;
        if (Utilidades.validarNulo(listaAsociacionPlanAsignatura)) {
            for (int i = 0; i < listaAsociacionPlanAsignatura.size(); i++) {
                if (listaAsociacionPlanAsignatura.get(i).isActivo() == true) {
                    retorno = true;
                }
            }
        }
        return retorno;
    }

    public void registrarNuevoPlanEstudio() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                if (validarAsociacionAsignatura() == true) {
                    almacenarNuevoPlanEstudioEnSistema();
                    limpiarFormulario();
                    activarAceptar = true;
                    activarLimpiar = false;
                    activarCasillas = true;
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "Es necesario asociar al menos una asignatura al plan de estudio.";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoPlanEstudioEnSistema() {
        try {
            PlanEstudios planNuevo = new PlanEstudios();
            planNuevo.setNombreplanestudio(nuevoNombre);
            planNuevo.setCodigoplanestudio(nuevoCodigo);
            planNuevo.setCarrera(nuevoCarrera);
            planNuevo.setEstado(true);
            List<Asignatura> lista = cargarAsignaturas();
            gestionarPlanesEstudiosBO.crearNuevoPlanEstudio(planNuevo, lista);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarPlanEstudio almacenarNuevoPlanEstudioEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarPlanEstudio almacenarNuevoPlanEstudioEnSistema : " + e.toString());
        }
    }

    private List<Asignatura> cargarAsignaturas() {
        List<Asignatura> lista = null;
        if (Utilidades.validarNulo(listaAsociacionPlanAsignatura)) {
            lista = new ArrayList<Asignatura>();
            for (int i = 0; i < listaAsociacionPlanAsignatura.size(); i++) {
                if (listaAsociacionPlanAsignatura.get(i).isActivo() == true) {
                    lista.add(listaAsociacionPlanAsignatura.get(i).getAsignatura());
                }
            }
        }
        return lista;
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
        cargarAsociacionPlanAsignatura();
    }

    public void cancelarRegistroPlanEstudio() {
        activarNuevoCarrera = true;
        activarNuevoDepartamento = true;
        nuevoCarrera = null;
        nuevoCodigo = null;
        listaAsignaturas = null;
        listaAsociacionPlanAsignatura = null;
        nuevoDepartamento = null;
        nuevoFacultad = null;
        nuevoNombre = null;
        validacionesCarrera = false;
        validacionesCodigo = false;
        validacionesDepartamento = false;
        validacionesFacultad = false;
        validacionesNombre = false;
        mensajeFormulario = "N/A";
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        listaCarreras = null;
        listaDepartamentos = null;
        listaFacultades = null;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        activarAceptar = false;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET
    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = gestionarPlanesEstudiosBO.consultarFacultadesActivosRegistradas();
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

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isActivarLimpiar() {
        return activarLimpiar;
    }

    public void setActivarLimpiar(boolean activarLimpiar) {
        this.activarLimpiar = activarLimpiar;
    }

    public boolean isActivarAceptar() {
        return activarAceptar;
    }

    public void setActivarAceptar(boolean activarAceptar) {
        this.activarAceptar = activarAceptar;
    }

    public List<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public List<AsociacionPlanAsignatura> getListaAsociacionPlanAsignatura() {
        return listaAsociacionPlanAsignatura;
    }

    public void setListaAsociacionPlanAsignatura(List<AsociacionPlanAsignatura> listaAsociacionPlanAsignatura) {
        this.listaAsociacionPlanAsignatura = listaAsociacionPlanAsignatura;
    }

}
