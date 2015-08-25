/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarConveniosPorEntidadBOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.utilidades.UsuarioLogin;
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
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesConvenioPorEntidad implements Serializable {

    @EJB
    AdministrarConveniosPorEntidadBOInterface administrarConveniosPorEntidadBO;

    private ConvenioPorEntidad convenioPorEntidadDetalle;
    private BigInteger idConvenioPorEntidad;
    private boolean modificacionRegistro;
    private boolean activarEstado;
    private List<EntidadExterna> listaEntidadesExternas;
    private EntidadExterna inputEntidadExterna;
    private List<Convenio> listaConvenios;
    private Convenio inputConvenio;
    private boolean inputEstado;
    //
    private boolean validacionesEntidad, validacionesConvenio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesConvenioPorEntidad() {
    }

    @PostConstruct
    public void init() {
        colorMensaje = "black";
        validacionesConvenio = true;
        validacionesEntidad = true;
        mensajeFormulario = "N/A";
        //
        modificacionRegistro = false;
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            activarEstado = false;
        }
        BasicConfigurator.configure();
    }

    /**
     * Metodo encargado de asignar los valores de la entidad externa que sera
     * visualizado
     */
    public void asignarValoresVariablesConvenioPorEntidad() {
        inputConvenio = convenioPorEntidadDetalle.getConvenio();
        inputEntidadExterna = convenioPorEntidadDetalle.getEntidadexterna();
        inputEstado = convenioPorEntidadDetalle.getEstado();
        listaConvenios = administrarConveniosPorEntidadBO.consultarConveniosRegistrados();
        listaEntidadesExternas = administrarConveniosPorEntidadBO.consultarEntidadesExternasRegistradas();
    }

    /**
     * Metodo encargado de recibir el ID de la entidad externa que sera
     * visualizado
     *
     * @param idConvenioPorEntidad ID de la entidad externa
     */
    public void recibirIDConveniosPorEntidadDetalles(BigInteger idConvenioPorEntidad) {
        this.idConvenioPorEntidad = idConvenioPorEntidad;
        convenioPorEntidadDetalle = administrarConveniosPorEntidadBO.obtenerConvenioPorEntidadPorID(idConvenioPorEntidad);
        asignarValoresVariablesConvenioPorEntidad();
    }

    /**
     * Metodo encargado de restaurar la información de la entidad externa
     */
    public String restaurarInformacionConvenioPorEntidad() {
        validacionesConvenio = true;
        validacionesEntidad = true;
        convenioPorEntidadDetalle = new ConvenioPorEntidad();
        convenioPorEntidadDetalle = administrarConveniosPorEntidadBO.obtenerConvenioPorEntidadPorID(idConvenioPorEntidad);
        asignarValoresVariablesConvenioPorEntidad();
        modificacionRegistro = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        return "administrar_convenioporentidad";
    }

    public void validarEntidadExterna() {
        if (Utilidades.validarNulo(inputEntidadExterna)) {
            validacionesEntidad = true;
        } else {
            validacionesEntidad = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEntidadExterna", new FacesMessage("El campo Entidad Externa es obligatorio."));
        }
        modificacionesRegistroConvenioPorEntidad();
    }

    public void validarConvenio() {
        if (Utilidades.validarNulo(inputConvenio)) {
            validacionesConvenio = true;
        } else {
            validacionesEntidad = false;
            FacesContext.getCurrentInstance().addMessage("form:inputConvenio", new FacesMessage("El campo Convenio es obligatorio."));
        }
        modificacionesRegistroConvenioPorEntidad();
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
            if (!convenioPorEntidadDetalle.getIdconvenioporentidad().equals(registro.getIdconvenioporentidad())) {
                retorno = false;
            }
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo encargado
     */
    public void almacenarModificacionesConvenioPorEntidad() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                if (validarRegistroRepetido() == true) {
                    modificarInformacionConvenioPorEntidad();
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
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No se presento algun cambio en el registro. No se realizo ningun proceso de almacenamiento.";
        }
    }

    /**
     * Metodo encargado de almacenar en el sistema de información las
     * modificaciones del registro
     */
    public void modificarInformacionConvenioPorEntidad() {
        try {
            convenioPorEntidadDetalle.setConvenio(inputConvenio);
            convenioPorEntidadDetalle.setEntidadexterna(inputEntidadExterna);
            convenioPorEntidadDetalle.setEstado(inputEstado);
            administrarConveniosPorEntidadBO.editarConvenioPorEntidad(convenioPorEntidadDetalle);
            restaurarInformacionConvenioPorEntidad();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesConvenioPorEntidad modificarInformacionConvenioPorEntidad:  " + e.toString());
            System.out.println("Error ControllerDetallesConvenioPorEntidad modificarInformacionConvenioPorEntidad : " + e.toString());
        }
    }

    /**
     * Metodo encargado de registrar alguna modificación del registro
     */
    public void modificacionesRegistroConvenioPorEntidad() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    // GET - SET
    public ConvenioPorEntidad getConvenioPorEntidadDetalle() {
        return convenioPorEntidadDetalle;
    }

    public void setConvenioPorEntidadDetalle(ConvenioPorEntidad convenioPorEntidadDetalle) {
        this.convenioPorEntidadDetalle = convenioPorEntidadDetalle;
    }

    public BigInteger getIdConvenioPorEntidad() {
        return idConvenioPorEntidad;
    }

    public void setIdConvenioPorEntidad(BigInteger idConvenioPorEntidad) {
        this.idConvenioPorEntidad = idConvenioPorEntidad;
    }

    public boolean isActivarEstado() {
        return activarEstado;
    }

    public void setActivarEstado(boolean activarEstado) {
        this.activarEstado = activarEstado;
    }

    public List<EntidadExterna> getListaEntidadesExternas() {
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

    public boolean isInputEstado() {
        return inputEstado;
    }

    public void setInputEstado(boolean inputEstado) {
        this.inputEstado = inputEstado;
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

}
