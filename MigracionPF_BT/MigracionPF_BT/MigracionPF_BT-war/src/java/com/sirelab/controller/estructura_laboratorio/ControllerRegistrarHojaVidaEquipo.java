/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.entidades.EquipoElemento;
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

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarHojaVidaEquipo implements Serializable {

    @EJB
    GestionarPlantaHojasVidaEquiposBOInterface gestionarPlantaHojasVidaEquiposBO;

    private String inputDetalle;
    private Date inputFechaEvento, inputFechaRegistro;
    private TipoEvento inputTipoEvento;
    private List<TipoEvento> listaTiposEventos;
    private boolean validacionesDetalle, validacionesFechaEvento, validacionesFechaRegistro, validacionesTipo;
    private String mensajeFormulario;
    private BigInteger idEquipo;
    private EquipoElemento equipoElemento;

    public ControllerRegistrarHojaVidaEquipo() {
    }

    @PostConstruct
    public void init() {
        inputDetalle = null;
        inputFechaEvento = new Date();
        inputFechaRegistro = new Date();
        inputTipoEvento = null;
        validacionesDetalle = false;
        listaTiposEventos = null;
        validacionesTipo = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        mensajeFormulario = "";
    }

    public void recibirIDEquipo(BigInteger idRegistro) {
        this.idEquipo = idRegistro;
        System.out.println("idRegistro : "+idRegistro);
        equipoElemento = gestionarPlantaHojasVidaEquiposBO.consultarEquipoElementoPorID(idEquipo);
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
    }

    public void validarTipoEvento() {
        if (Utilidades.validarNulo(inputTipoEvento)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputTipoEvento", new FacesMessage("El campo Tipo Evento es obligatorio."));
        }
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDetalle == false) {
            System.out.println("1");
            retorno = false;
        }
        if (validacionesFechaRegistro == false) {
            System.out.println("2");
            retorno = false;
        }
        if (validacionesFechaEvento == false) {
            retorno = false;
            System.out.println("3");
        }
        if (validacionesTipo == false) {
            System.out.println("4");
            retorno = false;
        }
        return retorno;
    }

    public void registrarHojaVidaEquipo() {
        if (validarValidacionesRegistro() == true) {
            if ((inputFechaRegistro.after(inputFechaEvento)) || (inputFechaRegistro.equals(inputFechaEvento))) {
                almacenarRegistroNuevo();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
                restaurarFormulario();
            } else {
                mensajeFormulario = "La fecha registro es menor que la fecha evento, por favor corregir para continuar.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            HojaVidaEquipo reggNuevo = new HojaVidaEquipo();
            reggNuevo.setDetalleevento(inputDetalle);
            reggNuevo.setFecharegistro(inputFechaRegistro);
            reggNuevo.setFechaevento(inputFechaEvento);
            reggNuevo.setTipoevento(inputTipoEvento);
            reggNuevo.setEquipoelemento(equipoElemento);
            System.out.println("equipoElemento : "+reggNuevo.getEquipoelemento());
            gestionarPlantaHojasVidaEquiposBO.crearHojaVidaEquipo(reggNuevo);
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarHojaVidaEquipo almacenarRegistroNuevo: " + e.toString());
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

    private void restaurarFormulario() {
        inputDetalle = null;
        inputTipoEvento = null;
        inputFechaEvento = new Date();
        inputFechaRegistro = new Date();
        validacionesDetalle = false;
        validacionesTipo = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
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

    public TipoEvento getInputTipoEvento() {
        return inputTipoEvento;
    }

    public void setInputTipoEvento(TipoEvento inputTipoEvento) {
        this.inputTipoEvento = inputTipoEvento;
    }

    public List<TipoEvento> getListaTiposEventos() {
        if (listaTiposEventos == null) {
            listaTiposEventos = gestionarPlantaHojasVidaEquiposBO.consultarTiposEventosRegistrados();
        }
        return listaTiposEventos;
    }

    public void setListaTiposEventos(List<TipoEvento> listaTiposEventos) {
        this.listaTiposEventos = listaTiposEventos;
    }

    public EquipoElemento getEquipoElemento() {
        return equipoElemento;
    }

    public void setEquipoElemento(EquipoElemento equipoElemento) {
        this.equipoElemento = equipoElemento;
    }

}
