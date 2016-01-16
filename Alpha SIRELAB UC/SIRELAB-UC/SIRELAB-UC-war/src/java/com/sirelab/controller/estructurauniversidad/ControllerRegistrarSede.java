/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarSedeBOInterface;
import com.sirelab.entidades.Sede;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarSede implements Serializable {

    @EJB
    GestionarSedeBOInterface gestionarSedeBO;

    private String nuevoNombre, nuevoDireccion, nuevoTelefono;
    //
    private boolean validacionesNombre, validacionesDireccion, validacionesTelefono;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarSede() {
    }

    @PostConstruct
    public void init() {
        nuevoNombre = null;
        activarAceptar = false;
        nuevoDireccion = null;
        nuevoTelefono = null;
        validacionesDireccion = false;
        validacionesNombre = false;
        validacionesTelefono = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void validarNombreSede() {
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

    public void validarDireccionSede() {
        if (Utilidades.validarNulo(nuevoDireccion) && (!nuevoDireccion.isEmpty()) && (nuevoDireccion.trim().length() > 0)) {
            int tam = nuevoDireccion.length();
            if (tam >= 8) {
                if (!Utilidades.validarDirecciones(nuevoDireccion)) {
                    validacionesDireccion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección ingresada es incorrecta. "+constantes.USUARIO_DIRECCION));
                } else {
                    validacionesDireccion = true;
                }
            } else {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. "+constantes.USUARIO_DIRECCION));
            }
        } else {
            validacionesDireccion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección es obligatoria. "+constantes.USUARIO_DIRECCION));
        }
    }

    public void validarTelefonoSede() {
        if (Utilidades.validarNulo(nuevoTelefono) && (!nuevoTelefono.isEmpty()) && (nuevoTelefono.trim().length() > 0)) {
            if (!Utilidades.isNumber(nuevoTelefono)) {
                validacionesTelefono = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono", new FacesMessage("El telefono ingresado es incorrecto. "+constantes.ENTIDAD_TELEFONO));
            } else {
                validacionesTelefono = true;
            }
        } else {
            validacionesTelefono = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono", new FacesMessage("El telefono es obligatorio. "+constantes.ENTIDAD_TELEFONO));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesDireccion == false) {
            retorno = false;
        }

        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesTelefono == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoSede() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoSedeEnSistema();
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoSedeEnSistema() {
        try {
            Sede nuevaSede = new Sede();
            nuevaSede.setDireccionsede(nuevoDireccion);
            nuevaSede.setNombresede(nuevoNombre);
            nuevaSede.setTelefonosede(nuevoTelefono);
            nuevaSede.setEstado(true);
            gestionarSedeBO.crearNuevaSede(nuevaSede);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarSede almacenarNuevoSedeEnSistema:  " + e.toString());
            logger.error("Error ControllerRegistrarSede almacenarNuevoSedeEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        nuevoNombre = null;
        nuevoDireccion = null;
        nuevoTelefono = null;
        validacionesDireccion = false;
        validacionesNombre = false;
        validacionesTelefono = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroSede() {
        nuevoNombre = null;
        nuevoDireccion = null;
        nuevoTelefono = null;
        validacionesDireccion = false;
        validacionesNombre = false;
        validacionesTelefono = false;
        activarAceptar = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
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
    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoDireccion() {
        return nuevoDireccion;
    }

    public void setNuevoDireccion(String nuevoDireccion) {
        this.nuevoDireccion = nuevoDireccion;
    }

    public String getNuevoTelefono() {
        return nuevoTelefono;
    }

    public void setNuevoTelefono(String nuevoTelefono) {
        this.nuevoTelefono = nuevoTelefono;
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

}
