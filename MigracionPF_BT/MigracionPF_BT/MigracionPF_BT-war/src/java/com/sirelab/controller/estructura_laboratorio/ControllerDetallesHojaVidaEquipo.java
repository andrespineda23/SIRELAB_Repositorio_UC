/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.entidades.HojaVidaEquipo;
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
    private boolean validacionesDetalle, validacionesFechaEvento, validacionesFechaRegistro;
    private String mensajeFormulario;
    private BigInteger idEquipo;
    private BigInteger idHojaVidaEquipo;
    private HojaVidaEquipo hojaVidaEquipoDetalle;
    private boolean modificacionesRegistro;

    public ControllerDetallesHojaVidaEquipo() {
    }

    @PostConstruct
    public void init() {
    }

    public void recibirIDHojaVidaEquipo(BigInteger idRegistro) {
        this.idHojaVidaEquipo = idRegistro;
        cargarInformacionRegistro();
        mensajeFormulario = "";
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
        }
    }

    public void validarDetalle() {
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty())) {
            if (Utilidades.validarCaracterString(inputDetalle)) {
                validacionesDetalle = true;
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto."));
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
        return retorno;
    }

    public void registrarModificacionHojaVidaEquipo() {
        if (modificacionesRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                if ((inputFechaRegistro.after(inputFechaEvento)) || (inputFechaRegistro.equals(inputFechaEvento))) {
                    almacenarModificacionRegistro();
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                    cargarInformacionRegistro();
                } else {
                    mensajeFormulario = "La fecha registro es menor que la fecha evento, por favor corregir para continuar.";
                }
            } else {
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificacionRegistro() {
        try {
            hojaVidaEquipoDetalle.setDetalleevento(inputDetalle);
            hojaVidaEquipoDetalle.setFecharegistro(inputFechaRegistro);
            hojaVidaEquipoDetalle.setFechaevento(inputFechaEvento);
            gestionarPlantaHojasVidaEquiposBO.editarHojaVidaEquipo(hojaVidaEquipoDetalle);
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarHojaVidaEquipo almacenarModificacionRegistro: " + e.toString());
        }
    }

    public void cancelarHojaVidaEquipo() {
        inputDetalle = null;
        inputFechaEvento = new Date();
        inputFechaRegistro = new Date();
        validacionesDetalle = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        mensajeFormulario = "";
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

}
