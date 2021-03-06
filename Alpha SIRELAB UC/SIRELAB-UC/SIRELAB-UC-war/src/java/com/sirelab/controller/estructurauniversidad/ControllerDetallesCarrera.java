/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarCarrerasBOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
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
public class ControllerDetallesCarrera implements Serializable {

    @EJB
    GestionarCarrerasBOInterface gestionarCarrerasBO;

    private Carrera carreraDetalles;
    private BigInteger idCarrera;
    private List<Facultad> listaFacultades;
    private List<Departamento> listaDepartamentos;
    private String editarNombre, editarCodigo;
    private Facultad editarFacultad;
    private Departamento editarDepartamento;
    private boolean activarModificacionDepartamento;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesFacultad, validacionesDepartamento;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private MensajesConstantes constantes;
    private String mensajeError;
    private boolean activarCasillas;

    public ControllerDetallesCarrera() {
    }

    @PostConstruct
    public void init() {
        activarModificacionDepartamento = true;
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
        activarCasillas = false;
        constantes = new MensajesConstantes();
    }

    public String restaurarInformacionCarrera() {
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        carreraDetalles = new Carrera();
        if (activarCasillas == false) {
            recibirIDCarrerasDetalles(idCarrera);
        } else {
            activarCasillas = false;
            mensajeError = "";
            editarCodigo = null;
            editarDepartamento = null;
            editarFacultad = null;
            editarNombre = null;
            editarEstado = false;
            activarModificacionDepartamento = false;
            listaFacultades = null;
            listaDepartamentos = null;
        }
        return "administrarcarreras";
    }

    public void asignarValoresVariablesCarrera() {
        mensajeError = "";
        editarCodigo = carreraDetalles.getCodigocarrera();
        editarDepartamento = carreraDetalles.getDepartamento();
        editarFacultad = carreraDetalles.getDepartamento().getFacultad();
        editarNombre = carreraDetalles.getNombrecarrera();
        editarEstado = carreraDetalles.getEstado();
        activarModificacionDepartamento = false;
        listaFacultades = gestionarCarrerasBO.consultarFacultadesRegistradas();
        if (Utilidades.validarNulo(editarFacultad)) {
            listaDepartamentos = gestionarCarrerasBO.consultarDepartamentosPorIDFacultad(editarFacultad.getIdfacultad());
        }
    }

    public void recibirIDCarrerasDetalles(BigInteger idRegistro) {
        this.idCarrera = idRegistro;
        carreraDetalles = gestionarCarrerasBO.obtenerCarreraPorIDCarrera(idCarrera);
        asignarValoresVariablesCarrera();
    }

    public void validarNombreCarrera() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.U_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. " + constantes.U_NOMBRE));
        }
    }

    public void validarCodigoCarrera() {
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
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_CODIGO_CARR));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio. " + constantes.U_CODIGO_CARR));
        }
    }

    public void validarDepartamentoCarrera() {
        if (Utilidades.validarNulo(editarDepartamento)) {
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDepartamento", new FacesMessage("El departamento es obligatorio."));
        }
    }

    public void actualizarFacultad() {
        if (Utilidades.validarNulo(editarFacultad)) {
            editarDepartamento = null;
            listaDepartamentos = gestionarCarrerasBO.consultarDepartamentosPorIDFacultad(editarFacultad.getIdfacultad());
            activarModificacionDepartamento = false;
            validacionesFacultad = true;
        } else {
            validacionesDepartamento = false;
            validacionesFacultad = false;
            editarDepartamento = null;
            listaDepartamentos = null;
            activarModificacionDepartamento = true;
        }
    }

    private boolean validarCambioEstado() {
        boolean retorno = true;
        if (editarEstado == false) {
            Boolean validacion = gestionarCarrerasBO.validarCambioEstadoCarrera(carreraDetalles.getIdcarrera());
            if (null != validacion) {
                if (validacion == true) {
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
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        Carrera carrera = gestionarCarrerasBO.obtenerCarreraPorCodigo(editarCodigo);
        if (null != carrera) {
            if (!carreraDetalles.getIdcarrera().equals(carrera.getIdcarrera())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionCarrera() {
        if (validarResultadosValidacion() == true) {
            if (Utilidades.validarNulo(editarDepartamento)) {
                if (validarCodigoRepetido() == true) {
                    if (validarCambioEstado() == true) {
                        almacenarModificacionCarreraEnSistema();
                        recibirIDCarrerasDetalles(this.idCarrera);
                        colorMensaje = "green";
                        mensajeFormulario = "El formulario ha sido ingresado con exito.";
                    } else {
                        colorMensaje = "#FF0000";
                        mensajeFormulario = "Existen asociados planes de estudio. Imposible cambiar el estado de la carrera.";
                    }
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Seleccione un departamento para continuar con el proceso.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    private void almacenarModificacionCarreraEnSistema() {
        try {
            carreraDetalles.setNombrecarrera(editarNombre);
            carreraDetalles.setCodigocarrera(editarCodigo);
            carreraDetalles.setDepartamento(editarDepartamento);
            carreraDetalles.setEstado(editarEstado);
            gestionarCarrerasBO.modificarInformacionCarrera(carreraDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarCarreras almacenarModificacionCarreraEnSistema : " + e.toString(), e);
        }
    }

    public void eliminarCarrera() {
        Integer plan = gestionarCarrerasBO.obtenerPlanesEstudioAsociados(idCarrera);
        if (null != plan) {
            if (plan == 0) {
                boolean respuesta = gestionarCarrerasBO.eliminarCarrera(carreraDetalles);
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
                mensajeFormulario = "El registro no puede ser eliminado dado que tiene asociado al menos un plan de estudio.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Ocurrio un error en la eliminación del registro. Intente más tarde.";
        }
    }

    //GET-SET
    public Carrera getCarreraDetalles() {
        return carreraDetalles;
    }

    public void setCarreraDetalles(Carrera carreraDetalles) {
        this.carreraDetalles = carreraDetalles;
    }

    public BigInteger getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(BigInteger idCarrera) {
        this.idCarrera = idCarrera;
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

    public boolean isActivarModificacionDepartamento() {
        return activarModificacionDepartamento;
    }

    public void setActivarModificacionDepartamento(boolean activarModificacionDepartamento) {
        this.activarModificacionDepartamento = activarModificacionDepartamento;
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

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

}
