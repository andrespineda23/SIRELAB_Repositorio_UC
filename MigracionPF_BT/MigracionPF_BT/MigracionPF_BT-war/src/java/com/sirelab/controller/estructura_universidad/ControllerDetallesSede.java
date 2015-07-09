/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

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
    
    public ControllerDetallesSede() {
    }
    
    @PostConstruct
    public void init() {
        validacionesDireccion = true;
        validacionesNombre = true;
        validacionesTelefono = true;
        mensajeFormulario = "";
        BasicConfigurator.configure();
    }
    
    public void restaurarInformacionSede() {
        validacionesDireccion = true;
        validacionesNombre = true;
        validacionesTelefono = true;
        mensajeFormulario = "";
        sedeDetalles = new Sede();
        recibirIDSedesDetalles(idSede);
    }
    
    public void asignarValoresVariablesSede() {
        editarDireccion = sedeDetalles.getDireccionsede();
        editarNombre = sedeDetalles.getNombresede();
        editarTelefono = sedeDetalles.getTelefonosede();
    }
    
    public void recibirIDSedesDetalles(BigInteger idDetalle) {
        this.idSede = idDetalle;
        sedeDetalles = gestionarSedeBO.obtenerSedePorIDSede(idSede);
        asignarValoresVariablesSede();
    }
    
    public void validarNombreSede() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(editarNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio."));
        }
        
    }
    
    public void validarDireccionSede() {
        if (Utilidades.validarNulo(editarDireccion) && (!editarDireccion.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(editarDireccion)) {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("La dirección ingresada es incorrecta."));
            } else {
                validacionesDireccion = true;
            }
        } else {
            validacionesDireccion = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("La dirección es obligatoria."));
        }
    }
    
    public void validarTelefonoSede() {
        if (Utilidades.validarNulo(editarTelefono) && (!editarTelefono.isEmpty())) {
            if (!Utilidades.isNumber(editarTelefono)) {
                validacionesTelefono = false;
                FacesContext.getCurrentInstance().addMessage("form:editarTelefono", new FacesMessage("El telefono ingresado es incorrecto."));
            } else {
                validacionesTelefono = true;
            }
        } else {
            validacionesTelefono = false;
            FacesContext.getCurrentInstance().addMessage("form:editarTelefono", new FacesMessage("El telefono es obligatorio."));
        }
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
            almacenarModificacionSedeEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
            recibirIDSedesDetalles(this.idSede);
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }
    
    private void almacenarModificacionSedeEnSistema() {
        try {
            sedeDetalles.setDireccionsede(editarDireccion);
            sedeDetalles.setNombresede(editarNombre);
            sedeDetalles.setTelefonosede(editarTelefono);
            gestionarSedeBO.modificarInformacionSede(sedeDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesSede almacenarModificacionSedeEnSistema:  "+e.toString());
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
    
}
