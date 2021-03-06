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
import java.math.BigInteger;
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
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private MensajesConstantes constantes;
    private String mensajeError;
    private boolean activarCasillas;

    public ControllerDetallesEdificio() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        validacionesDescripcion = true;
        validacionesDireccion = true;
        validacionesSede = true;
        validacionesHorario = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarCasillas = false;
        BasicConfigurator.configure();
    }

    public String restaurarInformacionEdificio() {
        validacionesDescripcion = true;
        validacionesDireccion = true;
        validacionesHorario = true;
        validacionesSede = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        edificioDetalles = new Edificio();
        if (activarCasillas == false) {
            recibirIDEdificiosDetalles(idEdificio);
        } else {
            activarCasillas = false;
            mensajeError = "";
            editarDescripcion = null;
            editarDireccion = null;
            editarSede = null;
            editarEstado = false;
            editarHorario = null;
            listaSedes = null;
            listaHorariosAtencion = null;
        }
        return "administraredificios";
    }

    public void asignarValoresVariablesEdificio() {
        mensajeError = "";
        editarDescripcion = edificioDetalles.getDescripcionedificio();
        editarDireccion = edificioDetalles.getDireccion();
        editarSede = edificioDetalles.getSede();
        editarEstado = edificioDetalles.getEstado();
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
        if (Utilidades.validarNulo(editarDescripcion) && (!editarDescripcion.isEmpty()) && (editarDescripcion.trim().length() > 0)) {
            int tam = editarDescripcion.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarDescripcion)) {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarDescripcion", new FacesMessage("La descripción ingresada es incorrecta. " + constantes.U_NOMBRE));
                } else {
                    validacionesDescripcion = true;
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDescripcion", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDescripcion", new FacesMessage("La descripción es obligatoria. " + constantes.U_NOMBRE));
        }
    }

    public void validarDireccionEdificio() {
        if (Utilidades.validarNulo(editarDireccion) && (!editarDireccion.isEmpty()) && (editarDireccion.trim().length() > 0)) {
            int tam = editarDireccion.length();
            if (tam >= 8) {
                if (!Utilidades.validarDirecciones(editarDireccion)) {
                    validacionesDireccion = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("La dirección ingresada es incorrecta. " + constantes.USUARIO_DIRECCION));
                } else {
                    validacionesDireccion = true;
                }
            } else {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. " + constantes.USUARIO_DIRECCION));
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
        mensajeError = "";
        if (validacionesDireccion == false) {
            mensajeError = mensajeError + " - Dirección - ";
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            mensajeError = mensajeError + " - Descripción - ";
            retorno = false;
        }
        if (validacionesSede == false) {
            mensajeError = mensajeError + " - Sede - ";
            retorno = false;
        }
        if (validacionesHorario == false) {
            mensajeError = mensajeError + " - Horario - ";
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionEdificio() {
        if (validarResultadosValidacion() == true) {
            almacenarModificacionEdificioEnSistema();
            recibirIDEdificiosDetalles(this.idEdificio);
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    private void almacenarModificacionEdificioEnSistema() {
        try {
            edificioDetalles.setDescripcionedificio(editarDescripcion);
            edificioDetalles.setDireccion(editarDireccion);
            edificioDetalles.setSede(editarSede);
            edificioDetalles.setEstado(editarEstado);
            edificioDetalles.setHorarioatencion(editarHorario);
            gestionarEdificiosBO.modificarInformacionEdificio(edificioDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEdificio almacenarModificacionEdificioEnSistema : " + e.toString(), e);
        }
    }

    public void eliminarEdificio() {
        Integer sala = gestionarEdificiosBO.obtenerSalasAsociadas(idEdificio);
        if (null != sala) {
            if (sala == 0) {
                boolean respuesta = gestionarEdificiosBO.eliminarEdificio(edificioDetalles);
                if (respuesta == true) {
                    activarCasillas = true;
                    colorMensaje = "green";
                    mensajeFormulario = "El registro ha sido eliminado con éxito. Regrese nuevamente a la pagina de consulta.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "El registro no pudo ser eliminado. Intente más tarde.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El registro no puede ser eliminado dado que tiene asociado una sala de laboratorio.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Ocurrio un error en la eliminación del registro. Intente más tarde.";
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

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isEditarEstado() {
        return editarEstado;
    }

    public void setEditarEstado(boolean editarEstado) {
        this.editarEstado = editarEstado;
    }

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

}
