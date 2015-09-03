/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoMovimientosInsumoBOInterface;
import com.sirelab.entidades.MovimientoInsumo;
import com.sirelab.entidades.Insumo;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarMovimientoInsumo implements Serializable {

    @EJB
    GestionarRecursoMovimientosInsumoBOInterface gestionarRecursoMovimientosInsumoBO;

    private String nuevoTipoMovimiento, nuevoCantidadMovimiento, nuevoCostoMovimiento;
    private Date nuevoFechaMovimiento;
    private boolean validacionesTipo, validacionesCantidad, validacionesCosto, validacionesFecha;
    private String mensajeFormulario;
    private Insumo insumoRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarMovimientoInsumo() {
    }

    @PostConstruct
    public void init() {
        validacionesCantidad = false;
        validacionesCosto = false;
        validacionesTipo = false;
        validacionesFecha = true;
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        mensajeFormulario = "N/A";
        //
        nuevoTipoMovimiento = null;
        nuevoCantidadMovimiento = null;
        nuevoCostoMovimiento = null;
        nuevoFechaMovimiento = new Date();
        BasicConfigurator.configure();
    }

    public void recibirIDInsumo(BigInteger idRegistro) {
        insumoRegistro = gestionarRecursoMovimientosInsumoBO.obtenerInsumoPorID(idRegistro);
    }

    public void validarTipoMovimiento() {
        if (Utilidades.validarNulo(nuevoTipoMovimiento) && (!nuevoTipoMovimiento.isEmpty()) && (nuevoTipoMovimiento.trim().length() > 0)) {
            int tam = nuevoTipoMovimiento.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(nuevoTipoMovimiento)) {
                    validacionesTipo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoTipoMovimiento", new FacesMessage("El tipo ingresado es incorrecto."));
                } else {
                    validacionesTipo = true;
                }
            } else {
                validacionesTipo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoTipoMovimiento", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTipoMovimiento", new FacesMessage("El tipo es obligatorio."));
        }

    }

    public void validarCantidadMovimiento() {
        if (Utilidades.validarNulo(nuevoCantidadMovimiento) && (!nuevoCantidadMovimiento.isEmpty()) && (nuevoCantidadMovimiento.trim().length() > 0)) {
            if (!Utilidades.isNumber(nuevoCantidadMovimiento)) {
                validacionesCantidad = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadMovimiento", new FacesMessage("La cantidad se encuentra incorrecta."));
            } else {
                validacionesCantidad = true;
            }
        } else {
            validacionesCantidad = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadMovimiento", new FacesMessage("La cantidad es obligatoria."));
        }
    }

    public void validarCostoMovimiento() {
        if (Utilidades.validarNulo(nuevoCostoMovimiento) && (!nuevoCostoMovimiento.isEmpty()) && (nuevoCostoMovimiento.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoCostoMovimiento)) == false) {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoMovimiento", new FacesMessage("El costo se encuentra incorrecto."));
            } else {
                validacionesCosto = true;
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCostoMovimiento", new FacesMessage("El costo es obligatorio."));
        }
    }

    public void validarFechaMovimiento() {
        if (Utilidades.validarNulo(nuevoFechaMovimiento)) {
            if ((Utilidades.fechaIngresadaCorrecta(nuevoFechaMovimiento)) == false) {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoFechaMovimiento", new FacesMessage("La fecha se encuentra incorrecta."));
            } else {
                validacionesCosto = true;
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoFechaMovimiento", new FacesMessage("La fecha es obligatoria."));
        }
    }

    public String limpiarRegistroMovimientoLaboratorio() {
        nuevoTipoMovimiento = null;
        nuevoCantidadMovimiento = null;
        nuevoCostoMovimiento = null;
        nuevoFechaMovimiento = new Date();
        //
        validacionesCantidad = false;
        validacionesCosto = false;
        validacionesTipo = false;
        validacionesFecha = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        return "administrarmovimientoinsumo";
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCantidad == false) {
            retorno = false;
        }
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesTipo == false) {
            retorno = false;
        }
        if (validacionesFecha == false) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoMovimiento() {
        if (validarResultadosValidacion() == true) {
            almacenaNuevoMovimientoEnSistema();
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

    public void almacenaNuevoMovimientoEnSistema() {
        try {
            MovimientoInsumo movimientoNuevo = new MovimientoInsumo();
            movimientoNuevo.setTipomovimiento(nuevoTipoMovimiento);
            movimientoNuevo.setCantidadmovimiento(Integer.parseInt(nuevoCantidadMovimiento));
            movimientoNuevo.setCostomovimiento(Long.parseLong(nuevoCostoMovimiento));
            movimientoNuevo.setFechamovimiento(nuevoFechaMovimiento);
            movimientoNuevo.setInsumo(insumoRegistro);
            gestionarRecursoMovimientosInsumoBO.crearMovimientoInsumo(movimientoNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarMovimiento almacenaNuevoMovimientoEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarMovimiento almacenaNuevoMovimientoEnSistema : " + e.toString());
        }
    }

    private void limpiarFormulario() {
        nuevoTipoMovimiento = null;
        nuevoCantidadMovimiento = null;
        nuevoFechaMovimiento = new Date();
        nuevoCostoMovimiento = null;
        //
        validacionesCantidad = false;
        validacionesCosto = false;
        validacionesTipo = false;
        validacionesFecha = false;
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
    public String getNuevoTipoMovimiento() {
        return nuevoTipoMovimiento;
    }

    public void setNuevoTipoMovimiento(String nuevoTipoMovimiento) {
        this.nuevoTipoMovimiento = nuevoTipoMovimiento;
    }

    public String getNuevoCantidadMovimiento() {
        return nuevoCantidadMovimiento;
    }

    public void setNuevoCantidadMovimiento(String nuevoCantidadMovimiento) {
        this.nuevoCantidadMovimiento = nuevoCantidadMovimiento;
    }

    public String getNuevoCostoMovimiento() {
        return nuevoCostoMovimiento;
    }

    public void setNuevoCostoMovimiento(String nuevoCostoMovimiento) {
        this.nuevoCostoMovimiento = nuevoCostoMovimiento;
    }

    public Date getNuevoFechaMovimiento() {
        return nuevoFechaMovimiento;
    }

    public void setNuevoFechaMovimiento(Date nuevoFechaMovimiento) {
        this.nuevoFechaMovimiento = nuevoFechaMovimiento;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public Insumo getInsumoRegistro() {
        return insumoRegistro;
    }

    public void setInsumoRegistro(Insumo insumoRegistro) {
        this.insumoRegistro = insumoRegistro;
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
