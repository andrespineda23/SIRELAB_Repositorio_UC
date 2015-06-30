/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.GestionarRecursoMovimientosInsumoBOInterface;
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

    public ControllerRegistrarMovimientoInsumo() {
    }

    @PostConstruct
    public void init() {
        validacionesCantidad = false;
        validacionesCosto = false;
        validacionesTipo = false;
        validacionesFecha = true;
        mensajeFormulario = "";
        //
        nuevoTipoMovimiento = null;
        nuevoCantidadMovimiento = null;
        nuevoCostoMovimiento = null;
        nuevoFechaMovimiento = new Date();
    }

    public void recibirIDInsumo(BigInteger idRegistro) {
        insumoRegistro = gestionarRecursoMovimientosInsumoBO.obtenerInsumoPorID(idRegistro);
    }

    public void validarTipoMovimiento() {
        if (Utilidades.validarNulo(nuevoTipoMovimiento) && (!nuevoTipoMovimiento.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoTipoMovimiento)) {
                validacionesTipo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoTipoMovimiento", new FacesMessage("El tipo ingresado es incorrecto."));
            } else {
                validacionesTipo = true;
            }
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTipoMovimiento", new FacesMessage("El tipo es obligatorio."));
        }

    }

    public void validarCantidadMovimiento() {
        if (Utilidades.validarNulo(nuevoCantidadMovimiento) && (!nuevoCantidadMovimiento.isEmpty())) {
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
        if (Utilidades.validarNulo(nuevoCostoMovimiento) && (!nuevoCostoMovimiento.isEmpty())) {
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
        mensajeFormulario = "";
        return "administrar_movimientoinsumo";
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
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
            limpiarFormulario();
        } else {
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

}
