/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.TipoEvento;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
public class ControllerDetallesHojaVidaEquipo implements Serializable {

    @EJB
    GestionarPlantaHojasVidaEquiposBOInterface gestionarPlantaHojasVidaEquiposBO;

    private String inputDetalle;
    private Date inputFechaEvento, inputFechaRegistro;
    private TipoEvento inputTipoEvento;
    private List<TipoEvento> listaTiposEventos;
    private boolean validacionesDetalle, validacionesFechaEvento, validacionesFechaRegistro, validacionesTipo;
    private String mensajeFormulario;
    private BigInteger idEquipo;
    private BigInteger idHojaVidaEquipo;
    private HojaVidaEquipo hojaVidaEquipoDetalle;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesHojaVidaEquipo() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIDHojaVidaEquipo(BigInteger idRegistro) {
        this.idHojaVidaEquipo = idRegistro;
        cargarInformacionRegistro();
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    private void cargarInformacionRegistro() {
        hojaVidaEquipoDetalle = gestionarPlantaHojasVidaEquiposBO.consultarHojaVidaEquipoPorID(idHojaVidaEquipo);
        if (null != hojaVidaEquipoDetalle) {
            inputDetalle = hojaVidaEquipoDetalle.getDetalleevento();
            inputFechaEvento = hojaVidaEquipoDetalle.getFechaevento();
            inputFechaRegistro = hojaVidaEquipoDetalle.getFecharegistro();
            idEquipo = hojaVidaEquipoDetalle.getEquipoelemento().getIdequipoelemento();
            validacionesDetalle = true;
            validacionesFechaEvento = true;
            validacionesFechaRegistro = true;
            modificacionesRegistro = false;
            validacionesTipo = true;
            inputTipoEvento = hojaVidaEquipoDetalle.getTipoevento();
            listaTiposEventos = gestionarPlantaHojasVidaEquiposBO.consultarTiposEventosRegistrados();
        }
    }

    public void validarDetalle() {
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty()) && (inputDetalle.trim().length() > 0)) {
            int tam = inputDetalle.length();
            if (tam >= 4) {
                if (Utilidades.validarCaracterString(inputDetalle)) {
                    validacionesDetalle = true;
                } else {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto."));
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto."));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaEvento() {
        if (Utilidades.validarNulo(inputFechaEvento)) {
            if (Utilidades.fechaIngresadaCorrecta(inputFechaEvento)) {
                validacionesFechaEvento = true;
            } else {
                validacionesFechaEvento = false;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaEvento", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
            }
        } else {
            validacionesFechaEvento = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaEvento", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaRegistro() {
        if (Utilidades.validarNulo(inputFechaRegistro)) {
            if (Utilidades.fechaIngresadaCorrecta(inputFechaRegistro)) {
                validacionesFechaRegistro = true;
            } else {
                validacionesFechaRegistro = false;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaRegistro", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
            }
        } else {
            validacionesFechaRegistro = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaRegistro", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
        }
        modificacionesRegistro = true;
    }

    public void validarTipoEvento() {
        if (Utilidades.validarNulo(inputTipoEvento)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputTipoEvento", new FacesMessage("El campo Tipo Evento es obligatorio."));
        }
        modificacionesRegistro = true;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDetalle == false) {
            retorno = false;
        }
        if (validacionesFechaRegistro == false) {
            retorno = false;
        }
        if (validacionesFechaEvento == false) {
            retorno = false;
        }
        if (validacionesTipo == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionHojaVidaEquipo() {
        if (modificacionesRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                if ((inputFechaRegistro.after(inputFechaEvento)) || (inputFechaRegistro.equals(inputFechaEvento))) {
                    almacenarModificacionRegistro();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                    cargarInformacionRegistro();
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "La fecha registro es menor que la fecha evento, por favor corregir para continuar.";
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
            hojaVidaEquipoDetalle.setDetalleevento(inputDetalle);
            hojaVidaEquipoDetalle.setFecharegistro(inputFechaRegistro);
            hojaVidaEquipoDetalle.setTipoevento(inputTipoEvento);
            hojaVidaEquipoDetalle.setFechaevento(inputFechaEvento);
            gestionarPlantaHojasVidaEquiposBO.editarHojaVidaEquipo(hojaVidaEquipoDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarHojaVidaEquipo almacenarModificacionRegistro:  " + e.toString());
            System.out.println("Error ControllerRegistrarHojaVidaEquipo almacenarModificacionRegistro: " + e.toString());
        }
    }

    public void cancelarHojaVidaEquipo() {
        inputDetalle = null;
        inputTipoEvento = null;
        inputFechaEvento = new Date();
        inputFechaRegistro = new Date();
        validacionesDetalle = false;
        validacionesTipo = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    public String cerrarPagina() {
        cancelarHojaVidaEquipo();
        return "hojadevida";
    }

    //GET-SET
    public String getInputDetalle() {
        return inputDetalle;
    }

    public void setInputDetalle(String inputDetalle) {
        this.inputDetalle = inputDetalle;
    }

    public Date getInputFechaEvento() {
        return inputFechaEvento;
    }

    public void setInputFechaEvento(Date inputFechaEvento) {
        this.inputFechaEvento = inputFechaEvento;
    }

    public Date getInputFechaRegistro() {
        return inputFechaRegistro;
    }

    public void setInputFechaRegistro(Date inputFechaRegistro) {
        this.inputFechaRegistro = inputFechaRegistro;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public BigInteger getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(BigInteger idEquipo) {
        this.idEquipo = idEquipo;
    }

    public BigInteger getIdHojaVidaEquipo() {
        return idHojaVidaEquipo;
    }

    public void setIdHojaVidaEquipo(BigInteger idHojaVidaEquipo) {
        this.idHojaVidaEquipo = idHojaVidaEquipo;
    }

    public TipoEvento getInputTipoEvento() {
        return inputTipoEvento;
    }

    public void setInputTipoEvento(TipoEvento inputTipoEvento) {
        this.inputTipoEvento = inputTipoEvento;
    }

    public List<TipoEvento> getListaTiposEventos() {
        return listaTiposEventos;
    }

    public void setListaTiposEventos(List<TipoEvento> listaTiposEventos) {
        this.listaTiposEventos = listaTiposEventos;
    }

    public HojaVidaEquipo getHojaVidaEquipoDetalle() {
        return hojaVidaEquipoDetalle;
    }

    public void setHojaVidaEquipoDetalle(HojaVidaEquipo hojaVidaEquipoDetalle) {
        this.hojaVidaEquipoDetalle = hojaVidaEquipoDetalle;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
