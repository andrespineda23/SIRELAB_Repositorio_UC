/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoAreasProfundizacionBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
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
public class ControllerDetallesAreaProfundizacion implements Serializable {

    @EJB
    GestionarRecursoAreasProfundizacionBOInterface gestionarRecursoAreaProfundizacionBO;

    private AreaProfundizacion areaProfundizacionDetalle;
    private BigInteger idAreaProfundizacion;
    private String editarNombre, editarCodigo;
    private boolean validacionesNombre, validacionesCodigo;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;

    public ControllerDetallesAreaProfundizacion() {
    }

    @PostConstruct
    public void init() {
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionAreaProfundizacion() {
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        areaProfundizacionDetalle = new AreaProfundizacion();
        recibirIDAreasProfundizacionDetalles(idAreaProfundizacion);
        return "administrarareasprofundizacion";
    }

    public void asignarValoresVariablesAreaProfundizacion() {
        editarCodigo = areaProfundizacionDetalle.getCodigoarea();
        editarNombre = areaProfundizacionDetalle.getNombrearea();
        editarEstado = areaProfundizacionDetalle.getEstado();
    }

    public void recibirIDAreasProfundizacionDetalles(BigInteger idArea) {
        this.idAreaProfundizacion = idArea;
        areaProfundizacionDetalle = gestionarRecursoAreaProfundizacionBO.obtenerAreaProfundizacionPorIDAreaProfundizacion(idAreaProfundizacion);
        asignarValoresVariablesAreaProfundizacion();
    }

    public void validarNombreAreaProfundizacion() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarCodigoAreaProfundizacion() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty()) && (editarCodigo.trim().length() > 0)) {
            int tam = editarCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
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
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        AreaProfundizacion registro = gestionarRecursoAreaProfundizacionBO.obtenerAreaProfundizacionPorCodigo(editarCodigo);
        if (null != registro) {
            if (!areaProfundizacionDetalle.getIdareaprofundizacion().equals(registro.getIdareaprofundizacion())) {
                retorno = false;
            }
        }
        return retorno;
    }

    private boolean validarCambioEstado() {
        boolean retorno = true;
        if (editarEstado == false) {
            Boolean validacion = gestionarRecursoAreaProfundizacionBO.validarCambioEstadoArea(areaProfundizacionDetalle.getIdareaprofundizacion());
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

    public void modificarInformacionAreaProfundizacion() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                if (validarCambioEstado() == true) {
                    almacenarModificacionAreaProfundizacion();
                    recibirIDAreasProfundizacionDetalles(this.idAreaProfundizacion);
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "Existen asociaciones con laboratorios. Imposible cambiar el estado.";
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

    private void almacenarModificacionAreaProfundizacion() {
        try {
            areaProfundizacionDetalle.setNombrearea(editarNombre);
            areaProfundizacionDetalle.setCodigoarea(editarCodigo);
            gestionarRecursoAreaProfundizacionBO.modificarInformacionAreaProfundizacion(areaProfundizacionDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAreaProfundizacion almacenarModificacionAreaProfundizacion:  " + e.toString());
            System.out.println("Error ControllerDetallesAreaProfundizacion almacenarModificacionAreaProfundizacion : " + e.toString());
        }
    }

    //GET-SET
    public AreaProfundizacion getAreaProfundizacionDetalle() {
        return areaProfundizacionDetalle;
    }

    public void setAreaProfundizacionDetalle(AreaProfundizacion areaProfundizacionDetalle) {
        this.areaProfundizacionDetalle = areaProfundizacionDetalle;
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
