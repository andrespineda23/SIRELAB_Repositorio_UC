/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarEdificiosBOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.HorarioAtencion;
import com.sirelab.entidades.Sede;
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

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesEdificio implements Serializable {

    @EJB
    GestionarEdificiosBOInterface gestionarEdificiosBO;

    private Edificio edificioDetalles;
    private BigInteger idEdificio;
    private String editarDescripcion, editarDireccion;
    private List<Sede> listaSedes;
    private Sede editarSede;
    private List<HorarioAtencion> listaHorariosAtencion;
    private HorarioAtencion editarHorario;
    //
    private boolean validacionesDescripcion, validacionesDireccion, validacionesSede, validacionesHorario;
    private String mensajeFormulario;

    public ControllerDetallesEdificio() {
    }

    @PostConstruct
    public void init() {
        validacionesDescripcion = true;
        validacionesDireccion = true;
        validacionesSede = true;
        validacionesHorario = true;
        mensajeFormulario = "";
    }

    public void restaurarInformacionEdificio() {
        validacionesDescripcion = true;
        validacionesDireccion = true;
        validacionesHorario = true;
        validacionesSede = true;
        mensajeFormulario = "";
        edificioDetalles = new Edificio();
        recibirIDEdificiosDetalles(idEdificio);
    }

    public void asignarValoresVariablesEdificio() {
        editarDescripcion = edificioDetalles.getDescripcionedificio();
        editarDireccion = edificioDetalles.getDireccion();
        editarSede = edificioDetalles.getSede();
        editarHorario = edificioDetalles.getHorarioatencion();
        listaSedes = gestionarEdificiosBO.consultarSedesRegistradas();
        listaHorariosAtencion = gestionarEdificiosBO.consultarHorariosAtencionRegistrados();
    }

    public void recibirIDEdificiosDetalles(BigInteger idRegistro) {
        this.idEdificio = idRegistro;
        edificioDetalles = gestionarEdificiosBO.obtenerEdificioPorIDEdificio(idEdificio);
        asignarValoresVariablesEdificio();
    }

    public void validarDescripcionEdificio() {
        if (Utilidades.validarNulo(editarDescripcion) && (!editarDescripcion.isEmpty())) {
            if (!Utilidades.validarCaracterString(editarDescripcion)) {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDescripcion", new FacesMessage("La descripción ingresada es incorrecta."));
            } else {
                validacionesDescripcion = true;
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDescripcion", new FacesMessage("La descripción es obligatoria."));
        }
    }

    public void validarDireccionEdificio() {
        if (Utilidades.validarNulo(editarDireccion) && (!editarDireccion.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(editarDireccion)) {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("La dirección ingresada es incorrecta."));
            } else {
                validacionesDireccion = true;
            }
        }
    }

    public void validarSedeEdificio() {
        if (Utilidades.validarNulo(editarSede)) {
            validacionesSede = true;
        } else {
            validacionesSede = false;
            FacesContext.getCurrentInstance().addMessage("form:editarSede", new FacesMessage("La sede es obligatoria."));
        }
    }

    public void validarHorarioEdificio() {
        if (Utilidades.validarNulo(editarHorario)) {
            validacionesHorario = true;
        } else {
            validacionesHorario = false;
            FacesContext.getCurrentInstance().addMessage("form:editarHorario", new FacesMessage("El horario es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesDireccion == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesSede == false) {
            retorno = false;
        }
        if (validacionesHorario == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionEdificio() {
        if (validarResultadosValidacion() == true) {
            almacenarModificacionEdificioEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
            recibirIDEdificiosDetalles(this.idEdificio);
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionEdificioEnSistema() {
        try {
            edificioDetalles.setDescripcionedificio(editarDireccion);
            edificioDetalles.setDireccion(editarDescripcion);
            edificioDetalles.setSede(editarSede);
            edificioDetalles.setHorarioatencion(editarHorario);
            gestionarEdificiosBO.modificarInformacionEdificio(edificioDetalles);
        } catch (Exception e) {
            System.out.println("Error ControllerLogin almacenarNuevoEdificioEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public Edificio getEdificioDetalles() {
        return edificioDetalles;
    }

    public void setEdificioDetalles(Edificio edificioDetalles) {
        this.edificioDetalles = edificioDetalles;
    }

    public String getEditarDescripcion() {
        return editarDescripcion;
    }

    public void setEditarDescripcion(String editarDescripcion) {
        this.editarDescripcion = editarDescripcion;
    }

    public String getEditarDireccion() {
        return editarDireccion;
    }

    public void setEditarDireccion(String editarDireccion) {
        this.editarDireccion = editarDireccion;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public Sede getEditarSede() {
        return editarSede;
    }

    public void setEditarSede(Sede editarSede) {
        this.editarSede = editarSede;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public List<HorarioAtencion> getListaHorariosAtencion() {
        return listaHorariosAtencion;
    }

    public void setListaHorariosAtencion(List<HorarioAtencion> listaHorariosAtencion) {
        this.listaHorariosAtencion = listaHorariosAtencion;
    }

    public HorarioAtencion getEditarHorario() {
        return editarHorario;
    }

    public void setEditarHorario(HorarioAtencion editarHorario) {
        this.editarHorario = editarHorario;
    }

}
