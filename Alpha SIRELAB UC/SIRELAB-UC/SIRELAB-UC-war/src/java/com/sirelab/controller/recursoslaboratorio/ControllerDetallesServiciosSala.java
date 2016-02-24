/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoServiciosSalaBOInterface;
import com.sirelab.entidades.ServiciosSala;
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
public class ControllerDetallesServiciosSala implements Serializable {

    @EJB
    GestionarRecursoServiciosSalaBOInterface gestionarRecursoServiciosSalaBO;

    private ServiciosSala serviciosSalaDetalle;
    private BigInteger idServiciosSala;
    private String editarNombre, editarCodigo, editarCosto;
    private boolean validacionesNombre, validacionesCodigo, validacionesCosto;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private MensajesConstantes constantes;

    public ControllerDetallesServiciosSala() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        validacionesCodigo = true;
        validacionesNombre = true;
        validacionesCosto = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionServiciosSala() {
        validacionesCodigo = true;
        validacionesNombre = true;
        validacionesCosto = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black"; 
        serviciosSalaDetalle = new ServiciosSala();
        recibirIDServiciosSalaDetalles(idServiciosSala);
        return "administrarserviciossala";
    }

    public void asignarValoresVariablesServiciosSala() {
        editarCodigo = serviciosSalaDetalle.getCodigoservicio();
        editarNombre = serviciosSalaDetalle.getNombreservicio();
        editarCosto = String.valueOf(serviciosSalaDetalle.getCostoservicio());
        editarEstado = serviciosSalaDetalle.getEstado();
    }

    public void recibirIDServiciosSalaDetalles(BigInteger idArea) {
        this.idServiciosSala = idArea;
        serviciosSalaDetalle = gestionarRecursoServiciosSalaBO.obtenerServiciosSalaPorIDServiciosSala(idServiciosSala);
        asignarValoresVariablesServiciosSala();
    }

    public void validarNombreServiciosSala() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.SERVICIO_NOM));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.SERVICIO_NOM));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. " + constantes.SERVICIO_NOM));
        }

    }

    public void validarCodigoServiciosSala() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty()) && (editarCodigo.trim().length() > 0)) {
            int tam = editarCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.RECURSO_COD));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.RECURSO_COD));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio. " + constantes.RECURSO_COD));
        }
    }

    public void validarCostoServiciosSala() {
        if (Utilidades.validarNulo(editarCosto) && (!editarCosto.isEmpty()) && (editarCosto.trim().length() > 0)) {
            if (!Utilidades.isNumber(editarCosto)) {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCosto", new FacesMessage("El costo ingresado es incorrecto. " + constantes.RECURSO_COSTO));
            } else {
                validacionesCosto = true;
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCosto", new FacesMessage("El costo es obligatorio. " + constantes.RECURSO_COSTO));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        ServiciosSala registro = gestionarRecursoServiciosSalaBO.obtenerServiciosSalaPorCodigo(editarCodigo);
        if (null != registro) {
            if (!serviciosSalaDetalle.getIdserviciossala().equals(registro.getIdserviciossala())) {
                retorno = false;
            }
        }
        return retorno;
    }

    private boolean validarCambioEstado() {
        boolean retorno = true;
        if (editarEstado == false) {
            Boolean validacion = gestionarRecursoServiciosSalaBO.validarCambioEstadoServicio(serviciosSalaDetalle.getIdserviciossala());
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

    public void modificarInformacionServiciosSala() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                if (validarCambioEstado() == true) {
                    almacenarModificacionServiciosSala();
                    recibirIDServiciosSalaDetalles(this.idServiciosSala);
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "Existen asociaciones con salas de laboratorios. Imposible cambiar el estado.";
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

    private void almacenarModificacionServiciosSala() {
        try {
            serviciosSalaDetalle.setNombreservicio(editarNombre);
            serviciosSalaDetalle.setCodigoservicio(editarCodigo);
            serviciosSalaDetalle.setEstado(editarEstado);
            serviciosSalaDetalle.setCostoservicio(Integer.parseInt(editarCosto));
            gestionarRecursoServiciosSalaBO.modificarInformacionServiciosSala(serviciosSalaDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesServiciosSala almacenarModificacionServiciosSala:  " + e.toString(),e);
            logger.error("Error ControllerDetallesServiciosSala almacenarModificacionServiciosSala : " + e.toString(),e);
        }
    }

    //GET-SET
    public ServiciosSala getServiciosSalaDetalle() {
        return serviciosSalaDetalle;
    }

    public void setServiciosSalaDetalle(ServiciosSala serviciosSalaDetalle) {
        this.serviciosSalaDetalle = serviciosSalaDetalle;
    }

    public BigInteger getIdServiciosSala() {
        return idServiciosSala;
    }

    public void setIdServiciosSala(BigInteger idServiciosSala) {
        this.idServiciosSala = idServiciosSala;
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

    public String getEditarCosto() {
        return editarCosto;
    }

    public void setEditarCosto(String editarCosto) {
        this.editarCosto = editarCosto;
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

    public MensajesConstantes getConstantes() {
        return constantes;
    }

    public void setConstantes(MensajesConstantes constantes) {
        this.constantes = constantes;
    }

}
