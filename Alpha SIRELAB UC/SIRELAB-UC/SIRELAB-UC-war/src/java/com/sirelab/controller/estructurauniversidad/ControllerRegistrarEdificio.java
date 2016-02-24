/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarEdificiosBOInterface;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

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
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarEdificio() {
    }

    @PostConstruct
    public void init() {
        activarAceptar = false;
        nuevoDescripcion = null;
        nuevoDireccion = null;
        nuevoSede = null;
        nuevoHorario = null;
        validacionesDescripcion = false;
        validacionesDireccion = true;
        validacionesSede = false;
        validacionesSede = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void validarDescripcionEdificio() {
        if (Utilidades.validarNulo(nuevoDescripcion) && (!nuevoDescripcion.isEmpty()) && (nuevoDescripcion.trim().length() > 0)) {
            int tam = nuevoDescripcion.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoDescripcion)) {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("La descripción ingresada es incorrecta. " + constantes.U_NOMBRE));
                } else {
                    validacionesDescripcion = true;
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("La descripción es obligatoria. " + constantes.U_NOMBRE));
        }
    }

    public void validarDireccionEdificio() {
        if (Utilidades.validarNulo(nuevoDireccion) && (!nuevoDireccion.isEmpty()) && (nuevoDireccion.trim().length() > 0)) {
            int tam = nuevoDireccion.length();
            if (tam >= 8) {
                if (!Utilidades.validarCaracterString(nuevoDireccion)) {
                    validacionesDireccion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección ingresada es incorrecta. " + constantes.USUARIO_DIRECCION));
                } else {
                    validacionesDireccion = true;
                }
            } else {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. " + constantes.USUARIO_DIRECCION));
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
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
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
            edificioNuevo.setEstado(true);
            gestionarEdificiosBO.crearNuevaEdificio(edificioNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarEdificio almacenarNuevoEdificioEnSistema:  " + e.toString(),e);
            logger.error("Error ControllerRegistrarEdificio almacenarNuevoEdificioEnSistema : " + e.toString(),e);
        }
    }

    public void limpiarFormulario() {
        nuevoDescripcion = null;
        nuevoDireccion = null;
        nuevoSede = null;
        nuevoHorario = null;
        validacionesDescripcion = false;
        validacionesDireccion = true;
        validacionesSede = false;
        validacionesHorario = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroEdificio() {
        nuevoDescripcion = null;
        nuevoDireccion = null;
        nuevoSede = null;
        activarAceptar = false;
        nuevoHorario = null;
        validacionesDescripcion = false;
        validacionesDireccion = true;
        validacionesSede = false;
        validacionesHorario = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        listaSedes = null;
        listaHorariosAtencion = null;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarAceptar = false;
        activarLimpiar = true;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
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
            listaSedes = gestionarEdificiosBO.consultarSedesActivosRegistradas();
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
