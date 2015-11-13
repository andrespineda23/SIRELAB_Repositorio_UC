/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarConveniosBOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesConvenio implements Serializable {

    @EJB
    GestionarConveniosBOInterface gestionarConvenioBO;

    private Convenio convenioEditar;
    private BigInteger idConvenio;
    private String inputNombre, inputDescripcion, inputValor;
    private Date inputFechaInicio, inputFechaFin;
    private boolean validacionesNombre, validacionesDescripcion, validacionesValor, validacionesFechaInicio, validacionesFechaFin;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean modificacionesRegistro;
    private boolean estadoConvenio;
    private MensajesConstantes constantes;

    public ControllerDetallesConvenio() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleConvenio(BigInteger idRegistro) {
        this.idConvenio = idRegistro;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    private void cargarInformacionRegistro() {
        convenioEditar = gestionarConvenioBO.consultarConvenioPorID(idConvenio);
        if (null != convenioEditar) {
            inputNombre = convenioEditar.getNombreconvenio();
            inputDescripcion = convenioEditar.getDescripcion();
            inputValor = String.valueOf(convenioEditar.getValor());
            inputFechaInicio = convenioEditar.getFechainicial();
            inputFechaFin = convenioEditar.getFechafinal();
            estadoConvenio = convenioEditar.getEstado();
            validacionesNombre = true;
            validacionesDescripcion = true;
            validacionesValor = true;
            validacionesFechaFin = true;
            validacionesFechaInicio = true;
            modificacionesRegistro = false;
        }
    }

    public void validarNombre() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty()) && (inputNombre.trim().length() > 0)) {
            int tam = inputNombre.length();
            if (tam >= 6) {
                if (Utilidades.validarCaracterString(inputNombre)) {
                    validacionesNombre = true;
                } else {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.U_NOMBRE));
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.U_NOMBRE));
        }
        modificacionesRegistro = true;
    }

    public void validarDescripcion() {
        if (Utilidades.validarNulo(inputDescripcion) && (!inputDescripcion.isEmpty()) && (inputDescripcion.trim().length() > 0)) {
            int tam = inputDescripcion.length();
            if (tam >= 20) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputDescripcion)) {
                    validacionesDescripcion = true;
                } else {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción se encuentra incorrecta. " + constantes.U_DESCRIP));
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("El tamaño minimo permitido es 20 caracteres. " + constantes.U_DESCRIP));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción es obligatoria. " + constantes.U_DESCRIP));
        }
        modificacionesRegistro = true;
    }

    public void validarValor() {
        if (Utilidades.validarNulo(inputValor) && (!inputValor.isEmpty()) && (inputValor.trim().length() > 0)) {
            if (Utilidades.isNumber(inputValor)) {
                validacionesValor = true;
            } else {
                validacionesValor = true;
                FacesContext.getCurrentInstance().addMessage("form:inputValor", new FacesMessage("El valor se encuentra incorrecto. " + constantes.U_COSTO));
            }
        } else {
            validacionesValor = true;
            FacesContext.getCurrentInstance().addMessage("form:inputValor", new FacesMessage("El valor es obligatoria. " + constantes.U_COSTO));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaInicio() {
        if (Utilidades.validarNulo(inputFechaInicio)) {

            inputFechaInicio = new Date();
            if (Utilidades.fechaIngresadaCorrecta(inputFechaInicio)) {
                validacionesFechaInicio = true;
            } else {
                validacionesFechaInicio = false;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
            }
        } else {
            validacionesFechaInicio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaInicio", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaFin() {
        if (Utilidades.validarNulo(inputFechaFin)) {
            if (Utilidades.fechaIngresadaCorrecta(inputFechaFin)) {
                validacionesFechaFin = true;
            } else {
                validacionesFechaFin = false;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
            }
        } else {
            validacionesFechaFin = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaFin", new FacesMessage("La fecha es obligatoria. Formato (dd/mm/yyyy)"));
        }
        modificacionesRegistro = true;
    }

    public void actualizarEstado() {
        modificacionesRegistro = true;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesValor == false) {
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
        if (modificacionesRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                if (validarFechasRegistro() == true) {
                    almacenarRegistroNuevo();
                    recibirIDDetalleConvenio(this.idConvenio);
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
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No existen modificaciones para ser almacenadas en el sistema.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            convenioEditar.setNombreconvenio(inputNombre);
            convenioEditar.setValor(Integer.valueOf(inputValor));
            convenioEditar.setEstado(estadoConvenio);
            convenioEditar.setDescripcion(inputDescripcion);
            convenioEditar.setFechafinal(inputFechaFin);
            convenioEditar.setFechainicial(inputFechaInicio);
            gestionarConvenioBO.editarConvenio(convenioEditar);
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
        colorMensaje = "black";
    }

    public String cerrarPagina() {
        cancelarConvenio();
        return "administrarconvenios";
    }

    //GET-SET
    public Convenio getConvenioEditar() {
        return convenioEditar;
    }

    public void setConvenioEditar(Convenio convenioEditar) {
        this.convenioEditar = convenioEditar;
    }

    public BigInteger getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(BigInteger idConvenio) {
        this.idConvenio = idConvenio;
    }

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

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isEstadoConvenio() {
        return estadoConvenio;
    }

    public void setEstadoConvenio(boolean estadoConvenio) {
        this.estadoConvenio = estadoConvenio;
    }

}
