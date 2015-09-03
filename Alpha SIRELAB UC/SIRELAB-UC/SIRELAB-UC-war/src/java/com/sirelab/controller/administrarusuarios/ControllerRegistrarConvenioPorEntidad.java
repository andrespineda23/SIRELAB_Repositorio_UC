/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarConveniosPorEntidadBOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarConvenioPorEntidad implements Serializable {

    //
    @EJB
    AdministrarConveniosPorEntidadBOInterface administrarConveniosPorEntidadBO;
    //
    private List<EntidadExterna> listaEntidadesExternas;
    private EntidadExterna inputEntidadExterna;
    private List<Convenio> listaConvenios;
    private Convenio inputConvenio;
    private boolean validacionesEntidad, validacionesConvenio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarConvenioPorEntidad() {
    }

    @PostConstruct
    public void init() {
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesConvenio = false;
        validacionesEntidad = false;
        //
        inputConvenio = null;
        inputEntidadExterna = null;
        listaConvenios = null;
        listaEntidadesExternas = null;
        BasicConfigurator.configure();
    }

    public void validarEntidadExterna() {
        if (Utilidades.validarNulo(inputEntidadExterna)) {
            validacionesEntidad = true;
        } else {
            validacionesEntidad = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEntidadExterna", new FacesMessage("El campo Entidad Externa es obligatorio."));
        }
    }

    public void validarConvenio() {
        if (Utilidades.validarNulo(inputConvenio)) {
            validacionesConvenio = true;
        } else {
            validacionesEntidad = false;
            FacesContext.getCurrentInstance().addMessage("form:inputConvenio", new FacesMessage("El campo Convenio es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesConvenio == false) {
            retorno = false;
        }
        if (validacionesEntidad == false) {
            retorno = false;
        }
        return retorno;
    }

    public boolean validarRegistroRepetido() {
        boolean retorno = true;
        ConvenioPorEntidad registro = administrarConveniosPorEntidadBO.obtenerConvenioPorEntidadPorParametros(inputEntidadExterna.getIdentidadexterna(), inputConvenio.getIdconvenio());
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * de la nueva entidad externa
     */
    public void registrarNuevoConvenioPorEntidad() {
        if (validarResultadosValidacion() == true) {
            if (validarRegistroRepetido() == true) {
                almacenarNuevoConvenioPorEntidadEnSistema();
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El registro ya se encuentra registrado en el sistema.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void limpiarFormulario() {
        validacionesConvenio = false;
        validacionesEntidad = false;
        mensajeFormulario = "";
        inputConvenio = null;
        inputEntidadExterna = null;
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroConvenioPorEntidad() {
        mensajeFormulario = "N/A";
        activarCasillas = false;
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        validacionesConvenio = false;
        validacionesEntidad = false;
        mensajeFormulario = "";
        inputConvenio = null;
        inputEntidadExterna = null;
        listaConvenios = null;
        listaEntidadesExternas = null;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información de la
     * nueva entidad externa
     */
    public void almacenarNuevoConvenioPorEntidadEnSistema() {
        try {
            ConvenioPorEntidad nuevoRegistro = new ConvenioPorEntidad();
            nuevoRegistro.setConvenio(inputConvenio);
            nuevoRegistro.setEntidadexterna(inputEntidadExterna);
            nuevoRegistro.setEstado(true);
            administrarConveniosPorEntidadBO.crearConvenioPorEntidad(nuevoRegistro);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarConvenioPorEntidad almacenarNuevoConvenioPorEntidadEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarConvenioPorEntidad almacenarNuevoConvenioPorEntidadEnSistema : " + e.toString());
        }
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

    //GET - SET
    public List<EntidadExterna> getListaEntidadesExternas() {
        if (null == listaEntidadesExternas) {
            listaEntidadesExternas = administrarConveniosPorEntidadBO.consultarEntidadesExternasRegistradas();
        }
        return listaEntidadesExternas;
    }

    public void setListaEntidadesExternas(List<EntidadExterna> listaEntidadesExternas) {
        this.listaEntidadesExternas = listaEntidadesExternas;
    }

    public EntidadExterna getInputEntidadExterna() {
        return inputEntidadExterna;
    }

    public void setInputEntidadExterna(EntidadExterna inputEntidadExterna) {
        this.inputEntidadExterna = inputEntidadExterna;
    }

    public List<Convenio> getListaConvenios() {
        if (null == listaConvenios) {
            listaConvenios = administrarConveniosPorEntidadBO.consultarConveniosRegistrados();
        }
        return listaConvenios;
    }

    public void setListaConvenios(List<Convenio> listaConvenios) {
        this.listaConvenios = listaConvenios;
    }

    public Convenio getInputConvenio() {
        return inputConvenio;
    }

    public void setInputConvenio(Convenio inputConvenio) {
        this.inputConvenio = inputConvenio;
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
