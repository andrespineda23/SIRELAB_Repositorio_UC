/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableConveniosBOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.Convenio;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.Date;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarConvenio implements Serializable {

    @EJB
    GestionarVariableConveniosBOInterface gestionarVariableConveniosBO;

    private String inputNombre, inputDescripcion, inputValor;
    private Date inputFechaInicio, inputFechaFin;
    private boolean validacionesNombre, validacionesDescripcion, validacionesValor, validacionesFechaInicio, validacionesFechaFin;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarConvenio() {
    }

    @PostConstruct
    public void init() {
        inputNombre = null;
        inputDescripcion = null;
        inputValor = "0";
        inputFechaInicio = new Date();
        inputFechaFin = null;
        validacionesNombre = false;
        validacionesDescripcion = false;
        validacionesValor = true;
        validacionesFechaFin = true;
        validacionesFechaInicio = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
    }

    public void validarNombre() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty()) && (inputNombre.trim().length() > 0)) {
            int tam = inputNombre.length();
            if (tam >= 3) {
                if (Utilidades.validarCaracterString(inputNombre)) {
                    validacionesNombre = true;
                } else {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto."));
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo permitido es 3 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto."));
        }
    }

    public void validarDescripcion() {
        if (Utilidades.validarNulo(inputDescripcion) && (!inputDescripcion.isEmpty()) && (inputDescripcion.trim().length() > 0)) {
            int tam = inputDescripcion.length();
            if (tam >= 3) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputDescripcion)) {
                    validacionesDescripcion = true;
                } else {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción se encuentra incorrecta."));
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("El tamaño minimo permitido es 3 caracteres."));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción es obligatoria."));
        }
    }

    public void validarValor() {
        if (Utilidades.validarNulo(inputValor) && (!inputValor.isEmpty()) && (inputValor.trim().length() > 0)) {
            if (Utilidades.isNumber(inputValor)) {
                validacionesValor = true;
            } else {
                validacionesValor = true;
                FacesContext.getCurrentInstance().addMessage("form:inputValor", new FacesMessage("El valor se encuentra incorrecto."));
            }
        } else {
            validacionesValor = true;
            FacesContext.getCurrentInstance().addMessage("form:inputValor", new FacesMessage("El valor es obligatoria."));
        }
    }

    public void validarFechaInicio() {
        if (Utilidades.validarNulo(inputFechaInicio)) {
            if (Utilidades.fechaIngresadaCorrecta(inputFechaInicio)) {
                validacionesFechaInicio = true;
            } else {
                validacionesFechaInicio = false;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
            }
        } else {
            validacionesFechaInicio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
        }
    }

    public void validarFechaFin() {
        if (Utilidades.validarNulo(inputFechaFin)) {
            if (Utilidades.fechaIngresadaCorrecta(inputFechaFin)) {
                validacionesFechaFin = true;
            } else {
                validacionesFechaFin = true;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
            }
        }
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesValor == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesFechaFin == false) {
            retorno = false;
        }
        if (validacionesFechaInicio == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarFechasRegistro() {
        boolean retorno = true;
        if (Utilidades.validarNulo(inputFechaFin)) {
            if (inputFechaFin.after(inputFechaInicio)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarConvenio() {
        if (validarValidacionesRegistro() == true) {
            if (validarFechasRegistro() == true) {
                almacenarRegistroNuevo();
                restaurarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "La fecha final es menor que la fecha inicial, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            Convenio convenioNuevo = new Convenio();
            convenioNuevo.setNombreconvenio(inputNombre);
            convenioNuevo.setValor(Integer.valueOf(inputValor));
            convenioNuevo.setEstado(true);
            convenioNuevo.setDescripcion(inputDescripcion);
            convenioNuevo.setFechafinal(inputFechaFin);
            convenioNuevo.setFechainicial(inputFechaInicio);
            gestionarVariableConveniosBO.crearConvenio(convenioNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarConvenio almacenarRegistroNuevo:  " + e.toString());
            System.out.println("Error ControllerRegistrarConvenio almacenarRegistroNuevo: " + e.toString());
        }
    }

    public void cancelarConvenio() {
        inputNombre = null;
        inputDescripcion = null;
        inputValor = "0";
        inputFechaInicio = new Date();
        inputFechaFin = null;
        validacionesNombre = false;
        validacionesDescripcion = false;
        validacionesValor = true;
        validacionesFechaFin = true;
        validacionesFechaInicio = false;
        mensajeFormulario = "N/A";
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
    }

    public String cerrarPagina() {
        cancelarConvenio();
        return "variables_usuario";
    }

    private void restaurarFormulario() {
        inputNombre = null;
        inputValor = "0";
        inputDescripcion = null;
        inputFechaInicio = new Date();
        inputFechaFin = null;
        validacionesNombre = false;
        validacionesDescripcion = false;
        validacionesValor = true;
        validacionesFechaFin = true;
        validacionesFechaInicio = false;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarAceptar = false;
        activarLimpiar = true;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET
    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public String getInputDescripcion() {
        return inputDescripcion;
    }

    public void setInputDescripcion(String inputDescripcion) {
        this.inputDescripcion = inputDescripcion;
    }

    public String getInputValor() {
        return inputValor;
    }

    public void setInputValor(String inputValor) {
        this.inputValor = inputValor;
    }

    public Date getInputFechaInicio() {
        return inputFechaInicio;
    }

    public void setInputFechaInicio(Date inputFechaInicio) {
        this.inputFechaInicio = inputFechaInicio;
    }

    public Date getInputFechaFin() {
        return inputFechaFin;
    }

    public void setInputFechaFin(Date inputFechaFin) {
        this.inputFechaFin = inputFechaFin;
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
