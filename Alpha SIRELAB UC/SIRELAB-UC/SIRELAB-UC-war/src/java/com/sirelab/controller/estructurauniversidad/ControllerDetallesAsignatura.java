/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarAsignaturasBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
    //
    private String editarNombre, editarCredito, editarCodigo;
    //
    private boolean validacionesNombre, validacionesCredito, validacionesCodigo;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerDetallesAsignatura() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionAsignatura() {
        validacionesCodigo = true;
        validacionesCredito = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        asignaturaDetalles = new Asignatura();
        recibirIDAsignaturasDetalles(idAsignatura);
        return "administrarasignaturas";
    }

    public void asignarValoresVariablesAsignatura() {
        editarCodigo = asignaturaDetalles.getCodigoasignatura();
        editarCredito = String.valueOf(asignaturaDetalles.getNumerocreditos());
        editarNombre = asignaturaDetalles.getNombreasignatura();
        editarEstado = asignaturaDetalles.getEstado();
        //
        validacionesCodigo = true;
        validacionesCredito = true;
        validacionesNombre = true;
mensajeError = "";
    }

    public void recibirIDAsignaturasDetalles(BigInteger idDetalle) {
        this.idAsignatura = idDetalle;
        asignaturaDetalles = gestionarAsignaturasBO.obtenerAsignaturaPorIDAsignatura(idAsignatura);
        asignarValoresVariablesAsignatura();
    }

    public void validarNombreAsignatura() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.U_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. "+constantes.U_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. "+constantes.U_NOMBRE));
        }
    }

    public void validarCodigoAsignatura() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty()) && (editarCodigo.trim().length() > 0)) {
            int tam = editarCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.U_CODIGO));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.U_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio. " + constantes.U_CODIGO));
        }
    }

    public void validarCreditosAsignatura() {
        if (Utilidades.validarNulo(editarCredito) && (!editarCredito.isEmpty()) && (editarCredito.trim().length() > 0)) {
            if (!Utilidades.isNumber(editarCredito)) {
                validacionesCredito = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCredito", new FacesMessage("El credito ingresado es incorrecto. "+constantes.U_CREDITO));
            } else {
                validacionesCredito = true;
            }
        } else {
            validacionesCredito = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCredito", new FacesMessage("El credito es obligatorio. "+constantes.U_CREDITO));
        }
    }

    private boolean validarCambioEstado() {
        boolean retorno = true;
        if (editarEstado == false) {
            Boolean validaciones = gestionarAsignaturasBO.validarCambioEstadoAsignatura(asignaturaDetalles.getIdasignatura());
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
        if (validacionesCredito == false) {
            mensajeError = mensajeError + " - Creditos - ";
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        Asignatura asignatura = gestionarAsignaturasBO.obtenerAsignaturaPorCodigo(editarCodigo);
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
                if (validarCambioEstado() == true) {
                    almacenarModificacionAsignaturaEnSistema();
                    recibirIDAsignaturasDetalles(this.idAsignatura);
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "Existen planes de estudio asociados. Imposible cambiar el estado de la asignatura.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el plan de estudio seleccionado.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: "+mensajeError;
        }
    }

    private void almacenarModificacionAsignaturaEnSistema() {
        try {
            asignaturaDetalles.setNombreasignatura(editarNombre);
            Integer creditos = Integer.valueOf(editarCredito);
            asignaturaDetalles.setCodigoasignatura(editarCodigo);
            asignaturaDetalles.setEstado(editarEstado);
            asignaturaDetalles.setNumerocreditos(creditos.intValue());
            gestionarAsignaturasBO.modificarInformacionAsignatura(asignaturaDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarAsignaturas almacenarModificacionAsignaturaEnSistema:  " + e.toString(),e);
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

}
