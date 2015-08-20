/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoMovimientosInsumoBOInterface;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.MovimientoInsumo;
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
public class ControllerDetallesMovimientoInsumo implements Serializable {

    @EJB
    GestionarRecursoMovimientosInsumoBOInterface gestionarRecursoMovimientosInsumoBO;

    private String editarTipoMovimiento, editarCantidadMovimiento, editarCostoMovimiento;
    private Date editarFechaMovimiento;
    private Insumo editarInsumo;
    private boolean validacionesTipo, validacionesCantidad, validacionesCosto, validacionesFecha;
    private String mensajeFormulario;
    private BigInteger idMovimiento;
    private MovimientoInsumo movimientoInsumoDetalle;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesMovimientoInsumo() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIDMovimientoInsumo(BigInteger idRegistro) {
        this.idMovimiento = idRegistro;
        cargarInformacionRegistro();
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    private void cargarInformacionRegistro() {
        movimientoInsumoDetalle = gestionarRecursoMovimientosInsumoBO.obtenerMovimientoInsumoPorID(idMovimiento);
        if (null != movimientoInsumoDetalle) {
            editarCantidadMovimiento = String.valueOf(movimientoInsumoDetalle.getCantidadmovimiento());
            editarCostoMovimiento = String.valueOf(movimientoInsumoDetalle.getCostomovimiento());
            editarFechaMovimiento = movimientoInsumoDetalle.getFechamovimiento();
            editarInsumo = movimientoInsumoDetalle.getInsumo();
            editarTipoMovimiento = movimientoInsumoDetalle.getTipomovimiento();

            validacionesCantidad = true;
            validacionesCosto = true;
            validacionesTipo = true;
            validacionesFecha = true;
        }
        modificacionesRegistro = false;
    }

    public void validarTipoMovimiento() {
        if (Utilidades.validarNulo(editarTipoMovimiento) && (!editarTipoMovimiento.isEmpty()) && (editarTipoMovimiento.trim().length() > 0)) {
            int tam = editarTipoMovimiento.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(editarTipoMovimiento)) {
                    validacionesTipo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarTipoMovimiento", new FacesMessage("El tipo ingresado es incorrecto."));
                } else {
                    validacionesTipo = true;
                }
            } else {
                validacionesTipo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarTipoMovimiento", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarTipoMovimiento", new FacesMessage("El tipo es obligatorio."));
        }
        modificacionesRegistro = true;
    }

    public void validarCantidadMovimiento() {
        if (Utilidades.validarNulo(editarCantidadMovimiento) && (!editarCantidadMovimiento.isEmpty()) && (editarCantidadMovimiento.trim().length() > 0)) {
            if (!Utilidades.isNumber(editarCantidadMovimiento)) {
                validacionesCantidad = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCantidadMovimiento", new FacesMessage("La cantidad se encuentra incorrecta."));
            } else {
                validacionesCantidad = true;
            }
        } else {
            validacionesCantidad = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCantidadMovimiento", new FacesMessage("La cantidad es obligatoria."));
        }
        modificacionesRegistro = true;
    }

    public void validarCostoMovimiento() {
        if (Utilidades.validarNulo(editarCostoMovimiento) && (!editarCostoMovimiento.isEmpty()) && (editarCostoMovimiento.trim().length() > 0)) {
            if ((Utilidades.isNumber(editarCostoMovimiento)) == false) {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCostoMovimiento", new FacesMessage("El costo se encuentra incorrecto."));
            } else {
                validacionesCosto = true;
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCostoMovimiento", new FacesMessage("El costo es obligatorio."));
        }
        modificacionesRegistro = true;
    }

    public void validarFechaMovimiento() {
        if (Utilidades.validarNulo(editarFechaMovimiento)) {
            if ((Utilidades.fechaIngresadaCorrecta(editarFechaMovimiento)) == false) {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:editarFechaMovimiento", new FacesMessage("La fecha se encuentra incorrecta."));
            } else {
                validacionesCosto = true;
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:editarFechaMovimiento", new FacesMessage("La fecha es obligatoria."));
        }
        modificacionesRegistro = true;
    }

    public String limpiarRegistroMovimientoLaboratorio() {
        editarTipoMovimiento = null;
        editarCantidadMovimiento = null;
        editarCostoMovimiento = null;
        editarFechaMovimiento = new Date();
        //
        validacionesCantidad = false;
        validacionesCosto = false;
        validacionesTipo = false;
        validacionesFecha = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        idMovimiento = null;
        movimientoInsumoDetalle = null;
        modificacionesRegistro = false;
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
     * del editar docente
     */
    public void registrarModificacionMovimiento() {
        if (modificacionesRegistro == true) {
            if (validarResultadosValidacion() == true) {
                almacenaModificacionMovimientoEnSistema();
                cargarInformacionRegistro();
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    public void almacenaModificacionMovimientoEnSistema() {
        try {
            movimientoInsumoDetalle.setTipomovimiento(editarTipoMovimiento);
            movimientoInsumoDetalle.setCantidadmovimiento(Integer.parseInt(editarCantidadMovimiento));
            movimientoInsumoDetalle.setCostomovimiento(Long.parseLong(editarCostoMovimiento));
            movimientoInsumoDetalle.setFechamovimiento(editarFechaMovimiento);
            movimientoInsumoDetalle.setInsumo(editarInsumo);
            gestionarRecursoMovimientosInsumoBO.editarMovimientoInsumo(movimientoInsumoDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesMovimientoInsumo almacenaModificacionMovimientoEnSistema:  " + e.toString());
            System.out.println("Error ControllerDetallesMovimientoInsumo almacenaModificacionMovimientoEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public String getEditarTipoMovimiento() {
        return editarTipoMovimiento;
    }

    public void setEditarTipoMovimiento(String editarTipoMovimiento) {
        this.editarTipoMovimiento = editarTipoMovimiento;
    }

    public String getEditarCantidadMovimiento() {
        return editarCantidadMovimiento;
    }

    public void setEditarCantidadMovimiento(String editarCantidadMovimiento) {
        this.editarCantidadMovimiento = editarCantidadMovimiento;
    }

    public String getEditarCostoMovimiento() {
        return editarCostoMovimiento;
    }

    public void setEditarCostoMovimiento(String editarCostoMovimiento) {
        this.editarCostoMovimiento = editarCostoMovimiento;
    }

    public Date getEditarFechaMovimiento() {
        return editarFechaMovimiento;
    }

    public void setEditarFechaMovimiento(Date editarFechaMovimiento) {
        this.editarFechaMovimiento = editarFechaMovimiento;
    }

    public Insumo getEditarInsumo() {
        return editarInsumo;
    }

    public void setEditarInsumo(Insumo editarInsumo) {
        this.editarInsumo = editarInsumo;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public MovimientoInsumo getMovimientoInsumoDetalle() {
        return movimientoInsumoDetalle;
    }

    public void setMovimientoInsumoDetalle(MovimientoInsumo movimientoInsumoDetalle) {
        this.movimientoInsumoDetalle = movimientoInsumoDetalle;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
