/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaModulosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarModuloCargado implements Serializable {

    @EJB
    GestionarPlantaModulosBOInterface gestionarPlantaModulosBO;

    private String nuevoCodigoModulo, nuevoDetalleModulo, nuevoCapacidadModulo, nuevoCostoModulo, nuevoInversionModulo;
    private Departamento nuevoDepartamentoModulo;
    private Laboratorio nuevoLaboratorioModulo;
    private SalaLaboratorio nuevoSalaLaboratorioModulo;
    //
    private boolean validacionesCodigo, validacionesDetalle, validacionesCapacidad, validacionesCosto, validacionesInversion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarModuloCargado() {
    }

    @PostConstruct
    public void init() {
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = "0";
        nuevoCapacidadModulo = "0";
        nuevoInversionModulo = "0";
        mensajeError = "";
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    public void cargarInformacionSalaLaboratorio(BigInteger sala) {
        nuevoSalaLaboratorioModulo = gestionarPlantaModulosBO.obtenerSalaLaboratorioPorID(sala);
        if (null != nuevoSalaLaboratorioModulo) {
            nuevoDepartamentoModulo = nuevoSalaLaboratorioModulo.getLaboratorio().getDepartamento();
            nuevoLaboratorioModulo = nuevoSalaLaboratorioModulo.getLaboratorio();
        }
    }

    public void limpiarRegistroModuloLaboratorio() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        mensajeError = "";
        nuevoCostoModulo = "0";
        nuevoCapacidadModulo = "0";
        nuevoInversionModulo = "0";
    }

    public void validarDetalleModulo() {
        if (Utilidades.validarNulo(nuevoDetalleModulo) && (!nuevoDetalleModulo.isEmpty()) && (nuevoDetalleModulo.trim().length() > 0)) {
            int tam = nuevoDetalleModulo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoDetalleModulo)) {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El detalle ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesDetalle = true;
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El tamaño minimo es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El detalle es obligatorio. " + constantes.INVENTARIO_NOMBRE));
        }

    }

    public void validarCodigoModulo() {
        if (Utilidades.validarNulo(nuevoCodigoModulo) && (!nuevoCodigoModulo.isEmpty()) && (nuevoCodigoModulo.trim().length() > 0)) {
            int tam = nuevoCodigoModulo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigoModulo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoModulo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.INVENTARIO_CODIGO));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoModulo", new FacesMessage("El tamaño minimo es 4 caracteres. " + constantes.INVENTARIO_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoModulo", new FacesMessage("El codigo es obligatorio. " + constantes.INVENTARIO_CODIGO));
        }
    }

    public void validarCostoAlquilerModulo() {
        if (Utilidades.validarNulo(nuevoCostoModulo) && (!nuevoCostoModulo.isEmpty()) && (nuevoCostoModulo.trim().length() > 0)) {
            if (Utilidades.isNumber(nuevoCostoModulo)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoModulo", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
            }
        }
    }

    public void validarCapacidadModulo() {
        if (Utilidades.validarNulo(nuevoCapacidadModulo) && (!nuevoCapacidadModulo.isEmpty()) && (nuevoCapacidadModulo.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoCapacidadModulo)) == false) {
                validacionesCapacidad = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCapacidadModulo", new FacesMessage("La capacidad ingresada se encuentra incorrecta. " + constantes.INVENTARIO_CAPACIDAD));
            } else {
                validacionesCapacidad = true;
            }
        }
    }

    public void validarInversionModulo() {
        if (Utilidades.validarNulo(nuevoInversionModulo) && (!nuevoInversionModulo.isEmpty()) && (nuevoInversionModulo.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoInversionModulo)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoInversionModulo", new FacesMessage("El valor de inversión se encuentra incorrecto. " + constantes.INVENTARIO_COST_INV));
            } else {
                validacionesInversion = true;
            }
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesCosto == false) {
            mensajeError = mensajeError + " - Costo Alquiler - ";
            retorno = false;
        }
        if (validacionesCapacidad == false) {
            mensajeError = mensajeError + " - Capacidad - ";
            retorno = false;
        }
        if (validacionesCodigo == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesDetalle == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        if (validacionesInversion == false) {
            mensajeError = mensajeError + " - Costo Inversión - ";
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        ModuloLaboratorio modulo = gestionarPlantaModulosBO.obtenerModuloLaboratorioPorCodigoYSala(nuevoCodigoModulo, nuevoSalaLaboratorioModulo.getIdsalalaboratorio());
        if (null != modulo) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCapacidadSala() {
        boolean retorno = true;
        List<ModuloLaboratorio> modulos = gestionarPlantaModulosBO.consultarModulosLaboratorioPorIdSala(nuevoSalaLaboratorioModulo.getIdsalalaboratorio());
        int capacidad = 0;
        if (null != modulos) {
            capacidad = modulos.size();
        }
        if (nuevoSalaLaboratorioModulo.getCapacidadsala() > capacidad) {
            retorno = true;
        } else {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoModulo() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                if (validarCapacidadSala() == true) {
                    almacenaNuevoModuloEnSistema();
                    limpiarFormulario();
                    activarLimpiar = false;
                    activarAceptar = true;
                    activarCasillas = true;
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "La capacidad de la sala de laboratorio se encuentra completa.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El codigo ya esta registrado en el sistema.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    private void almacenaNuevoModuloEnSistema() {
        try {
            ModuloLaboratorio salaNuevo = new ModuloLaboratorio();
            salaNuevo.setCodigomodulo(nuevoCodigoModulo);
            salaNuevo.setDetallemodulo(nuevoDetalleModulo);
            try {
                if (Utilidades.validarNulo(nuevoCostoModulo) && (!nuevoCostoModulo.isEmpty()) && (nuevoCostoModulo.trim().length() > 0)) {
                    salaNuevo.setCostoalquiler(new BigInteger(nuevoCostoModulo));
                } else {
                    salaNuevo.setCostoalquiler(new BigInteger("0"));
                }
            } catch (Exception e) {
                salaNuevo.setCostoalquiler(new BigInteger("0"));
            }
            try {
                if (Utilidades.validarNulo(nuevoInversionModulo) && (!nuevoInversionModulo.isEmpty()) && (nuevoInversionModulo.trim().length() > 0)) {
                    salaNuevo.setCostomodulo(new BigInteger(nuevoInversionModulo));
                } else {
                    salaNuevo.setCostomodulo(new BigInteger("0"));
                }
            } catch (Exception e) {
                salaNuevo.setCostomodulo(new BigInteger("0"));
            }
            try {
                if (Utilidades.validarNulo(nuevoCapacidadModulo) && (!nuevoCapacidadModulo.isEmpty()) && (nuevoCapacidadModulo.trim().length() > 0)) {
                    salaNuevo.setCapacidadmodulo(Integer.valueOf(nuevoCapacidadModulo));
                } else {
                    salaNuevo.setCapacidadmodulo(Integer.valueOf("0"));
                }
            } catch (Exception e) {
                salaNuevo.setCapacidadmodulo(Integer.valueOf("0"));
            }
            salaNuevo.setEstadomodulo(true);
            salaNuevo.setSalalaboratorio(nuevoSalaLaboratorioModulo);
            gestionarPlantaModulosBO.crearNuevoModuloLaboratorio(salaNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaModulos almacenaNuevoModuloEnSistema:  " + e.toString(), e);
            logger.error("Error ControllerGestionarPlantaModulos almacenaNuevoModuloEnSistema : " + e.toString(), e);
        }
    }

    public void limpiarFormulario() {
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        mensajeFormulario = "";
        mensajeError = "";
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = "0";
        nuevoCapacidadModulo = "0";
        nuevoInversionModulo = "0";
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        activarAceptar = false;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET
    public String getNuevoCodigoModulo() {
        return nuevoCodigoModulo;
    }

    public void setNuevoCodigoModulo(String nuevoCodigoModulo) {
        this.nuevoCodigoModulo = nuevoCodigoModulo;
    }

    public String getNuevoDetalleModulo() {
        return nuevoDetalleModulo;
    }

    public void setNuevoDetalleModulo(String nuevoDetalleModulo) {
        this.nuevoDetalleModulo = nuevoDetalleModulo;
    }

    public String getNuevoCapacidadModulo() {
        return nuevoCapacidadModulo;
    }

    public void setNuevoCapacidadModulo(String nuevoCapacidadModulo) {
        this.nuevoCapacidadModulo = nuevoCapacidadModulo;
    }

    public String getNuevoCostoModulo() {
        return nuevoCostoModulo;
    }

    public void setNuevoCostoModulo(String nuevoCostoModulo) {
        this.nuevoCostoModulo = nuevoCostoModulo;
    }

    public String getNuevoInversionModulo() {
        return nuevoInversionModulo;
    }

    public void setNuevoInversionModulo(String nuevoInversionModulo) {
        this.nuevoInversionModulo = nuevoInversionModulo;
    }

    public Departamento getNuevoDepartamentoModulo() {
        return nuevoDepartamentoModulo;
    }

    public void setNuevoDepartamentoModulo(Departamento nuevoDepartamentoModulo) {
        this.nuevoDepartamentoModulo = nuevoDepartamentoModulo;
    }

    public Laboratorio getNuevoLaboratorioModulo() {
        return nuevoLaboratorioModulo;
    }

    public void setNuevoLaboratorioModulo(Laboratorio nuevoLaboratorioModulo) {
        this.nuevoLaboratorioModulo = nuevoLaboratorioModulo;
    }

    public SalaLaboratorio getNuevoSalaLaboratorioModulo() {
        return nuevoSalaLaboratorioModulo;
    }

    public void setNuevoSalaLaboratorioModulo(SalaLaboratorio nuevoSalaLaboratorioModulo) {
        this.nuevoSalaLaboratorioModulo = nuevoSalaLaboratorioModulo;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
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
