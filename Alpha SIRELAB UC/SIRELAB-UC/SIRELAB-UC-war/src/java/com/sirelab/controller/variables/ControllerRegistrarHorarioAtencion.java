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
public class ControllerRegistrarHorarioAtencion implements Serializable {

    @EJB
    GestionarVariableHorariosAtencionBOInterface gestionarVariableHorariosAtencionBO;

    private String inputDescripcion, inputCodigo;
    private boolean validacionesDescripcion, validacionesCodigo;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
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

    public ControllerRegistrarHorarioAtencion() {
    }

    @PostConstruct
    public void init() {
        activarAceptar = false;
        inputDescripcion = null;
        inputCodigo = null;
        validacionesDescripcion = false;
        validacionesCodigo = false;
        validacionesHoraCierre = false;
        validacionesHoraCierreSabado = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        constantes = new MensajesConstantes();
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
        cargarListasHoras();
    }

    private void cargarListasHoras() {
        horaApertura = 6;
        listaHoraApertura = new ArrayList<Integer>();
        for (int i = horaApertura; i < 8; i++) {
            listaHoraApertura.add(i);
        }
        listaHoraCierre = new ArrayList<Integer>();
        horaCierre = null;
        for (int i = (horaApertura + 1); i < 22; i++) {
            listaHoraCierre.add(i);
        }
        horaAperturaSabado = 6;
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
    }

    public void validarCodigo() {
        if (Utilidades.validarNulo(inputCodigo) && (!inputCodigo.isEmpty()) && (inputCodigo.trim().length() > 0)) {
            int tam = inputCodigo.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputCodigo)) {
                    validacionesCodigo = true;
                } else {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputCodigo", new FacesMessage("El codigo se encuentra incorrecto."));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputCodigo", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputCodigo", new FacesMessage("El codigo se encuentra incorrecto."));
        }
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
            retorno = false;
        }
        return retorno;
    }

    public void registrarHorarioAtencion() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoRegistro();
                restaurarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
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
    }

    private void almacenarNuevoRegistro() {
        try {
            HorarioAtencion nuevoHorario = new HorarioAtencion();
            nuevoHorario.setCodigohorario(inputCodigo);
            nuevoHorario.setDescripcionhorario(inputDescripcion);
            nuevoHorario.setHoraapertura(horaApertura.toString());
            nuevoHorario.setHoracierre(horaCierre.toString());
            nuevoHorario.setHoraaperturasabado(horaAperturaSabado.toString());
            nuevoHorario.setHoracierresabado(horaCierreSabado.toString());
            gestionarVariableHorariosAtencionBO.crearHorarioAtencion(nuevoHorario);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarHorarioAtencion almacenarNuevoRegistro:  " + e.toString());
            logger.error("Error ControllerRegistrarHorarioAtencion almacenarNuevoRegistro: " + e.toString());
        }
    }

    public String cerrarPagina() {
        cancelarHorarioAtencion();
        return "variablesreserva";
    }

    public void restaurarFormulario() {
        inputDescripcion = null;
        inputCodigo = null;
        validacionesDescripcion = false;
        validacionesCodigo = false;
        validacionesHoraCierre = false;
        validacionesHoraCierreSabado = false;
        cargarListasHoras();
    }

    public void cancelarHorarioAtencion() {
        inputDescripcion = null;
        activarAceptar = false;
        inputCodigo = null;
        validacionesDescripcion = false;
        validacionesCodigo = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        validacionesHoraCierre = false;
        validacionesHoraCierreSabado = false;
        cargarListasHoras();
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
