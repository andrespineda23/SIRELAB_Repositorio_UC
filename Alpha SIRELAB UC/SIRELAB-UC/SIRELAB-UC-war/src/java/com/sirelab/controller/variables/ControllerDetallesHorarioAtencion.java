/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.variables.GestionarVariableHorariosAtencionBOInterface;
import com.sirelab.entidades.HorarioAtencion;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesHorarioAtencion implements Serializable {

    @EJB
    GestionarVariableHorariosAtencionBOInterface gestionarVariableHorariosAtencionBO;

    private String inputDescripcion, inputCodigo;
    private boolean validacionesDescripcion, validacionesCodigo;
    private String mensajeFormulario;
    private boolean modificacionesRegistro;
    private BigInteger idHorarioAtencion;
    private HorarioAtencion horarioAtencionDetalles;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private List<Integer> listaHoraApertura;
    private Integer horaApertura;
    private List<Integer> listaHoraCierre;
    private Integer horaCierre;
    private boolean validacionesHoraCierre;
    private List<Integer> listaHoraAperturaSabado;
    private Integer horaAperturaSabado;
    private List<Integer> listaHoraCierreSabado;
    private Integer horaCierreSabado;
    private boolean validacionesHoraCierreSabado;
    private MensajesConstantes constantes;

    public ControllerDetallesHorarioAtencion() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleHorarioAtencion(BigInteger idDetalle) {
        this.idHorarioAtencion = idDetalle;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    private void cargarInformacionRegistro() {
        horarioAtencionDetalles = gestionarVariableHorariosAtencionBO.consultarHorarioAtencionPorID(idHorarioAtencion);
        if (null != horarioAtencionDetalles) {
            inputDescripcion = horarioAtencionDetalles.getDescripcionhorario();
            inputCodigo = horarioAtencionDetalles.getCodigohorario();
            validacionesDescripcion = true;
            validacionesCodigo = true;
            validacionesHoraCierre = true;
            validacionesHoraCierreSabado = true;
            modificacionesRegistro = false;
            horaApertura = Integer.valueOf(horarioAtencionDetalles.getHoraapertura());
            horaCierre = Integer.valueOf(horarioAtencionDetalles.getHoracierre());
            horaAperturaSabado = Integer.valueOf(horarioAtencionDetalles.getHoraaperturasabado());
            horaCierreSabado = Integer.valueOf(horarioAtencionDetalles.getHoracierresabado());

            cargarListasHoras();
        }
    }

    private void cargarListasHoras() {
        listaHoraApertura = new ArrayList<Integer>();
        for (int i = horaApertura; i < 8; i++) {
            listaHoraApertura.add(i);
        }
        listaHoraCierre = new ArrayList<Integer>();
        horaCierre = null;
        for (int i = (horaApertura + 1); i < 22; i++) {
            listaHoraCierre.add(i);
        }
        listaHoraAperturaSabado = new ArrayList<Integer>();
        for (int i = horaAperturaSabado; i < 8; i++) {
            listaHoraAperturaSabado.add(i);
        }
        listaHoraCierreSabado = new ArrayList<Integer>();
        horaCierreSabado = null;
        for (int i = (horaAperturaSabado + 1); i < 16; i++) {
            listaHoraCierreSabado.add(i);
        }
    }

    public void actualizarHoraApertura() {
        listaHoraCierre = new ArrayList<Integer>();
        horaCierre = null;
        for (int i = (horaApertura + 1); i < 22; i++) {
            listaHoraCierre.add(i);
        }
        validacionesHoraCierre = false;
    }

    public void validarHoraCierre() {
        if (Utilidades.validarNulo(horaCierre)) {
            validacionesHoraCierre = true;
        } else {
            validacionesHoraCierre = false;
        }
    }

    public void actualizarHoraAperturaSabado() {
        listaHoraCierreSabado = new ArrayList<Integer>();
        horaCierreSabado = null;
        for (int i = (horaAperturaSabado + 1); i < 16; i++) {
            listaHoraCierreSabado.add(i);
        }
        validacionesHoraCierreSabado = false;
    }

    public void validarHoraCierreSabado() {
        if (Utilidades.validarNulo(horaCierreSabado)) {
            validacionesHoraCierreSabado = true;
        } else {
            validacionesHoraCierreSabado = false;
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
                    FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción se encuentra incorrecta. "+constantes.VARIABLE_DESCR));
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("El tamaño minimo permitido es 3 caracteres. "+constantes.VARIABLE_DESCR));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDescripcion", new FacesMessage("La descripción se encuentra incorrecta. "+constantes.VARIABLE_DESCR));
        }
        modificacionesRegistro = true;
    }

    public void validarCodigo() {
        if (Utilidades.validarNulo(inputCodigo) && (!inputCodigo.isEmpty()) && (inputCodigo.trim().length() > 0)) {
            int tam = inputCodigo.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputCodigo)) {
                    validacionesCodigo = true;
                } else {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputCodigo", new FacesMessage("El codigo se encuentra incorrecto. "+constantes.VARIABLE_COD));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputCodigo", new FacesMessage("El tamaño minimo permitido es 2 caracteres. "+constantes.VARIABLE_COD));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputCodigo", new FacesMessage("El codigo se encuentra incorrecto. "+constantes.VARIABLE_COD));
        }
        modificacionesRegistro = true;
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesHoraCierre == false) {
            retorno = false;
        }
        if (validacionesHoraCierreSabado == false) {
            retorno = false;
        }
        return retorno;
    }

    public boolean validarCodigoRepetido() {
        boolean retorno = true;
        HorarioAtencion registro = gestionarVariableHorariosAtencionBO.consultarHorarioAtencionPorCodigo(inputCodigo);
        if (null != registro) {
            if (!horarioAtencionDetalles.getIdhorarioatencion().equals(registro.getIdhorarioatencion())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionHorarioAtencion() {
        if (modificacionesRegistro == true) {
            if (validarResultadosValidacion() == true) {
                if (validarCodigoRepetido() == true) {
                    almacenarModificacionRegistro();
                    cargarInformacionRegistro();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificacionRegistro() {
        try {
            horarioAtencionDetalles.setCodigohorario(inputCodigo);
            horarioAtencionDetalles.setDescripcionhorario(inputDescripcion);
            horarioAtencionDetalles.setHoraapertura(horaApertura.toString());
            horarioAtencionDetalles.setHoracierre(horaCierre.toString());
            horarioAtencionDetalles.setHoraaperturasabado(horaAperturaSabado.toString());
            horarioAtencionDetalles.setHoracierresabado(horaCierreSabado.toString());
            gestionarVariableHorariosAtencionBO.editarHorarioAtencion(horarioAtencionDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesHorarioAtencion almacenarModificacionRegistro:  " + e.toString());
            logger.error("Error ControllerDetallesHorarioAtencion almacenarModificacionRegistro: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarHorarioAtencion();
        return "variablesreserva";
    }

    public void cancelarHorarioAtencion() {
        inputDescripcion = null;
        inputCodigo = null;
        validacionesDescripcion = false;
        validacionesCodigo = false;
        validacionesHoraCierreSabado = false;
        validacionesHoraCierre = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        idHorarioAtencion = null;
        modificacionesRegistro = false;
        horarioAtencionDetalles = null;
        cargarListasHoras();
    }

    //GET-SET
    public String getInputDescripcion() {
        return inputDescripcion;
    }

    public void setInputDescripcion(String inputDescripcion) {
        this.inputDescripcion = inputDescripcion;
    }

    public String getInputCodigo() {
        return inputCodigo;
    }

    public void setInputCodigo(String inputCodigo) {
        this.inputCodigo = inputCodigo;
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

    public HorarioAtencion getHorarioAtencionDetalles() {
        return horarioAtencionDetalles;
    }

    public void setHorarioAtencionDetalles(HorarioAtencion horarioAtencionDetalles) {
        this.horarioAtencionDetalles = horarioAtencionDetalles;
    }

    public List<Integer> getListaHoraApertura() {
        return listaHoraApertura;
    }

    public void setListaHoraApertura(List<Integer> listaHoraApertura) {
        this.listaHoraApertura = listaHoraApertura;
    }

    public Integer getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(Integer horaApertura) {
        this.horaApertura = horaApertura;
    }

    public List<Integer> getListaHoraCierre() {
        return listaHoraCierre;
    }

    public void setListaHoraCierre(List<Integer> listaHoraCierre) {
        this.listaHoraCierre = listaHoraCierre;
    }

    public Integer getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Integer horaCierre) {
        this.horaCierre = horaCierre;
    }

    public List<Integer> getListaHoraAperturaSabado() {
        return listaHoraAperturaSabado;
    }

    public void setListaHoraAperturaSabado(List<Integer> listaHoraAperturaSabado) {
        this.listaHoraAperturaSabado = listaHoraAperturaSabado;
    }

    public Integer getHoraAperturaSabado() {
        return horaAperturaSabado;
    }

    public void setHoraAperturaSabado(Integer horaAperturaSabado) {
        this.horaAperturaSabado = horaAperturaSabado;
    }

    public List<Integer> getListaHoraCierreSabado() {
        return listaHoraCierreSabado;
    }

    public void setListaHoraCierreSabado(List<Integer> listaHoraCierreSabado) {
        this.listaHoraCierreSabado = listaHoraCierreSabado;
    }

    public Integer getHoraCierreSabado() {
        return horaCierreSabado;
    }

    public void setHoraCierreSabado(Integer horaCierreSabado) {
        this.horaCierreSabado = horaCierreSabado;
    }

}
