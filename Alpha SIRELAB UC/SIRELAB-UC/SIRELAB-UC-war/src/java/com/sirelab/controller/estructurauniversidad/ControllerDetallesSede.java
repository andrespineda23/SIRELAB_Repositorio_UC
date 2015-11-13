/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarSedeBOInterface;
import com.sirelab.entidades.Sede;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesSede implements Serializable {

    @EJB
    GestionarSedeBOInterface gestionarSedeBO;

    private Sede sedeDetalles;
    private BigInteger idSede;
    private String editarNombre, editarDireccion, editarTelefono;
    //
    private boolean validacionesNombre, validacionesDireccion, validacionesTelefono;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private MensajesConstantes constantes;

    public ControllerDetallesSede() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        validacionesDireccion = true;
        validacionesNombre = true;
        validacionesTelefono = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionSede() {
        validacionesDireccion = true;
        validacionesNombre = true;
        validacionesTelefono = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        sedeDetalles = new Sede();
        recibirIDSedesDetalles(idSede);
        return "administrarsedes";
    }

    public void asignarValoresVariablesSede() {
        editarDireccion = sedeDetalles.getDireccionsede();
        editarNombre = sedeDetalles.getNombresede();
        editarEstado = sedeDetalles.getEstado();
        editarTelefono = sedeDetalles.getTelefonosede();
    }

    public void recibirIDSedesDetalles(BigInteger idDetalle) {
        this.idSede = idDetalle;
        sedeDetalles = gestionarSedeBO.obtenerSedePorIDSede(idSede);
        asignarValoresVariablesSede();
    }

    public void validarNombreSede() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.U_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. "+constantes.U_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarDireccionSede() {
        if (Utilidades.validarNulo(editarDireccion) && (!editarDireccion.isEmpty()) && (editarDireccion.trim().length() > 0)) {
            int tam = editarDireccion.length();
            if (tam >= 8) {
                if (!Utilidades.validarDirecciones(editarDireccion)) {
                    validacionesDireccion = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("La dirección ingresada es incorrecta. "+constantes.USUARIO_DIRECCION));
                } else {
                    validacionesDireccion = true;
                }
            } else {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. "+constantes.USUARIO_DIRECCION));
            }
        } else {
            validacionesDireccion = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("La dirección es obligatoria. "+constantes.USUARIO_DIRECCION));
        }
    }

    public void validarTelefonoSede() {
        if (Utilidades.validarNulo(editarTelefono) && (!editarTelefono.isEmpty()) && (editarTelefono.trim().length() > 0)) {
            if (!Utilidades.validarCaracteresAlfaNumericos(editarTelefono)) {
                validacionesTelefono = false;
                FacesContext.getCurrentInstance().addMessage("form:editarTelefono", new FacesMessage("El telefono ingresado es incorrecto. "+constantes.ENTIDAD_TELEFONO));
            } else {
                validacionesTelefono = true;
            }
        } else {
            validacionesTelefono = false;
            FacesContext.getCurrentInstance().addMessage("form:editarTelefono", new FacesMessage("El telefono es obligatorio. "+constantes.ENTIDAD_TELEFONO));
        }
    }

    private boolean validarCambioEstado() {
        boolean retorno = true;
        if (editarEstado == false) {
            Boolean validacion = gestionarSedeBO.validarcambioEstadoSede(sedeDetalles.getIdsede());
            if (null != validacion) {
                if (validacion == true) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesDireccion == false) {
            retorno = false;
        }

        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesTelefono == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionSede() {
        if (validarResultadosValidacion() == true) {
            if (validarCambioEstado() == true) {
                almacenarModificacionSedeEnSistema();
                recibirIDSedesDetalles(this.idSede);
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen edificios asociados. Imposible cambiar estado de sede.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionSedeEnSistema() {
        try {
            sedeDetalles.setDireccionsede(editarDireccion);
            sedeDetalles.setNombresede(editarNombre);
            sedeDetalles.setTelefonosede(editarTelefono);
            sedeDetalles.setEstado(editarEstado);
            gestionarSedeBO.modificarInformacionSede(sedeDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesSede almacenarModificacionSedeEnSistema:  " + e.toString());
            System.out.println("Error ControllerDetallesSede almacenarModificacionSedeEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public Sede getSedeDetalles() {
        return sedeDetalles;
    }

    public void setSedeDetalles(Sede sedeDetalles) {
        this.sedeDetalles = sedeDetalles;
    }

    public BigInteger getIdSede() {
        return idSede;
    }

    public void setIdSede(BigInteger idSede) {
        this.idSede = idSede;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarDireccion() {
        return editarDireccion;
    }

    public void setEditarDireccion(String editarDireccion) {
        this.editarDireccion = editarDireccion;
    }

    public String getEditarTelefono() {
        return editarTelefono;
    }

    public void setEditarTelefono(String editarTelefono) {
        this.editarTelefono = editarTelefono;
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

    public boolean isEditarEstado() {
        return editarEstado;
    }

    public void setEditarEstado(boolean editarEstado) {
        this.editarEstado = editarEstado;
    }

}
