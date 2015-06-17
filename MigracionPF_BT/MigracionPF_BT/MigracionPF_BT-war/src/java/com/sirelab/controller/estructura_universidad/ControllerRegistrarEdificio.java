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
public class ControllerRegistrarEdificio implements Serializable {

    @EJB
    GestionarEdificiosBOInterface gestionarEdificiosBO;

    private String nuevoDescripcion, nuevoDireccion;
    private List<Sede> listaSedes;
    private Sede nuevoSede;
    private List<HorarioAtencion> listaHorariosAtencion;
    private HorarioAtencion nuevoHorario;
    //
    private boolean validacionesDescripcion, validacionesDireccion, validacionesSede, validacionesHorario;
    private String mensajeFormulario;

    public ControllerRegistrarEdificio() {
    }

    @PostConstruct
    public void init() {
        nuevoDescripcion = null;
        nuevoDireccion = null;
        nuevoSede = null;
        nuevoHorario = null;
        validacionesDescripcion = false;
        validacionesDireccion = true;
        validacionesSede = false;
        validacionesSede = false;
        mensajeFormulario = "";
    }

    public void validarDescripcionEdificio() {
        if (Utilidades.validarNulo(nuevoDescripcion) && (!nuevoDescripcion.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoDescripcion)) {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("La descripción ingresada es incorrecta."));
            } else {
                validacionesDescripcion = true;
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("La descripción es obligatoria."));
        }
    }

    public void validarDireccionEdificio() {
        if (Utilidades.validarNulo(nuevoDireccion) && (!nuevoDireccion.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoDireccion)) {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección ingresada es incorrecta."));
            } else {
                validacionesDireccion = true;
            }
        }
    }

    public void validarSedeEdificio() {
        if (Utilidades.validarNulo(nuevoSede)) {
            validacionesSede = true;
        } else {
            validacionesSede = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoSede", new FacesMessage("La sede es obligatoria."));
        }
    }

    public void validarHorarioEdificio() {
        if (Utilidades.validarNulo(nuevoHorario)) {
            validacionesHorario = true;
        } else {
            validacionesHorario = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoHorario", new FacesMessage("El horario es obligatorio."));
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

    public void registrarNuevoEdificio() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoEdificioEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoEdificioEnSistema() {
        try {
            Edificio edificioNuevo = new Edificio();
            edificioNuevo.setDescripcionedificio(nuevoDescripcion);
            edificioNuevo.setDireccion(nuevoDireccion);
            edificioNuevo.setHorarioatencion(nuevoHorario);
            edificioNuevo.setSede(nuevoSede);
            gestionarEdificiosBO.crearNuevaEdificio(edificioNuevo);
            limpiarFormulario();
        } catch (Exception e) {
            System.out.println("Error ControllerLogin almacenarNuevoEdificioEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        nuevoDescripcion = null;
        nuevoDireccion = null;
        nuevoSede = null;
        validacionesDescripcion = false;
        validacionesDireccion = true;
        validacionesSede = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroEdificio() {
        nuevoDescripcion = null;
        nuevoDireccion = null;
        nuevoSede = null;
        nuevoHorario = null;
        validacionesDescripcion = false;
        validacionesDireccion = true;
        validacionesSede = false;
        validacionesHorario = false;
        mensajeFormulario = "";
        listaSedes = null;
    }

    //GET-SET
    public String getNuevoDescripcion() {
        return nuevoDescripcion;
    }

    public void setNuevoDescripcion(String nuevoDescripcion) {
        this.nuevoDescripcion = nuevoDescripcion;
    }

    public String getNuevoDireccion() {
        return nuevoDireccion;
    }

    public void setNuevoDireccion(String nuevoDireccion) {
        this.nuevoDireccion = nuevoDireccion;
    }

    public Sede getNuevoSede() {
        return nuevoSede;
    }

    public void setNuevoSede(Sede nuevoSede) {
        this.nuevoSede = nuevoSede;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public List<Sede> getListaSedes() {
        if (listaSedes == null) {
            listaSedes = gestionarEdificiosBO.consultarSedesRegistradas();
        }
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<HorarioAtencion> getListaHorariosAtencion() {
        if (listaHorariosAtencion == null) {
            listaHorariosAtencion = gestionarEdificiosBO.consultarHorariosAtencionRegistrados();
        }
        return listaHorariosAtencion;
    }

    public void setListaHorariosAtencion(List<HorarioAtencion> listaHorariosAtencion) {
        this.listaHorariosAtencion = listaHorariosAtencion;
    }

    public HorarioAtencion getNuevoHorario() {
        return nuevoHorario;
    }

    public void setNuevoHorario(HorarioAtencion nuevoHorario) {
        this.nuevoHorario = nuevoHorario;
    }

}
