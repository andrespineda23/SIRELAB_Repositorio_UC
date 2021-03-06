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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarModulo implements Serializable {

    @EJB
    GestionarPlantaModulosBOInterface gestionarPlantaModulosBO;

    private List<Departamento> listaDepartamentos;
    private List<Laboratorio> listaLaboratorios;
    private List<SalaLaboratorio> listaSalasLaboratorios;
    private boolean activarNuevoLaboratorio;
    private boolean activarNuevoSala;
    private String nuevoCodigoModulo, nuevoDetalleModulo, nuevoCapacidadModulo, nuevoCostoModulo, nuevoInversionModulo;
    private Departamento nuevoDepartamentoModulo;
    private Laboratorio nuevoLaboratorioModulo;
    private SalaLaboratorio nuevoSalaLaboratorioModulo;
    //
    private boolean validacionesCodigo, validacionesDetalle, validacionesCapacidad, validacionesCosto, validacionesInversion, validacionesDepartamento;
    private boolean validacionesSala, validacionesLaboratorio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarModulo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesLaboratorio = false;
        validacionesDepartamento = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        activarNuevoSala = true;
        activarNuevoLaboratorio = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        mensajeError = "";
        nuevoInversionModulo = null;
        nuevoDepartamentoModulo = null;
        nuevoLaboratorioModulo = null;
        nuevoSalaLaboratorioModulo = null;
        BasicConfigurator.configure();
    }

    public void limpiarRegistroModuloLaboratorio() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        mensajeError = "";
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesLaboratorio = false;
        validacionesDepartamento = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        activarNuevoSala = true;
        activarNuevoLaboratorio = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        nuevoInversionModulo = null;
        nuevoSalaLaboratorioModulo = null;
        nuevoLaboratorioModulo = null;
        nuevoDepartamentoModulo = null;
        listaSalasLaboratorios = null;
        listaLaboratorios = null;
        listaDepartamentos = null;
    }

    public void validarDetalleModulo() {
        if (Utilidades.validarNulo(nuevoDetalleModulo) && (!nuevoDetalleModulo.isEmpty()) && (nuevoDetalleModulo.trim().length() > 0)) {
            int tam = nuevoDetalleModulo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoDetalleModulo)) {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesDetalle = true;
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El tamaño minimo es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El nombre es obligatorio. " + constantes.INVENTARIO_NOMBRE));
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

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(nuevoDepartamentoModulo)) {
            nuevoLaboratorioModulo = new Laboratorio();
            listaLaboratorios = gestionarPlantaModulosBO.consultarLaboratoriosActivosPorIDDepartamento(nuevoDepartamentoModulo.getIddepartamento());
            activarNuevoLaboratorio = false;
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            validacionesLaboratorio = false;
            validacionesSala = false;

            nuevoLaboratorioModulo = new Laboratorio();
            nuevoSalaLaboratorioModulo = new SalaLaboratorio();

            listaLaboratorios = null;
            listaSalasLaboratorios = null;

            activarNuevoSala = true;
            activarNuevoLaboratorio = true;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDepartamentoModulo", new FacesMessage("El Departamento es obligatorio."));
        }
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(nuevoLaboratorioModulo)) {
            nuevoSalaLaboratorioModulo = new SalaLaboratorio();
            listaSalasLaboratorios = gestionarPlantaModulosBO.consultarSalasLaboratorioPorIDLaboratorio(nuevoLaboratorioModulo.getIdlaboratorio());
            activarNuevoSala = false;
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            validacionesSala = false;
            nuevoSalaLaboratorioModulo = new SalaLaboratorio();
            listaSalasLaboratorios = null;
            activarNuevoSala = true;
            FacesContext.getCurrentInstance().addMessage("form:nuevoLaboratorioModulo", new FacesMessage("El laboratorio es obligatorio."));
        }
    }

    public void actualizarSalas() {
        if (Utilidades.validarNulo(nuevoSalaLaboratorioModulo)) {
            validacionesSala = true;
        } else {
            validacionesSala = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoSalaLaboratorioModulo", new FacesMessage("La sala de laboratorio es obligatoria."));
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
        if (validacionesDepartamento == false) {
            mensajeError = mensajeError + " - Departamento - ";
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            mensajeError = mensajeError + " - Laboratorio - ";
            retorno = false;
        }
        if (validacionesSala == false) {
            mensajeError = mensajeError + " - Sala - ";
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

    private boolean validarCantidadModulosSala() {
        boolean retorno = true;
        int cantidadActual = gestionarPlantaModulosBO.validarCantidadModulosSala(nuevoSalaLaboratorioModulo.getIdsalalaboratorio());
        if (cantidadActual != -1) {
            int cantidadMax = nuevoSalaLaboratorioModulo.getCapacidadsala();
            if (cantidadActual < cantidadMax) {
                retorno = true;
            } else {
                retorno = false;
            }
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
                if (validarCantidadModulosSala() == true) {
                    almacenaNuevoModuloEnSistema();
                    limpiarFormulario();
                    activarLimpiar = false;
                    activarAceptar = true;
                    activarCasillas = true;
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "La sala ya se encuentra llena. No es posible crear el modulo. Capacidad maxima (" + nuevoSalaLaboratorioModulo.getCapacidadsala() + ").";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El codigo ya esta registrado en el sistea.";
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
            salaNuevo.setCostoalquiler(new BigInteger(nuevoCostoModulo));
            salaNuevo.setCostomodulo(new BigInteger(nuevoInversionModulo));
            salaNuevo.setCapacidadmodulo(Integer.valueOf(nuevoCapacidadModulo));
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
        validacionesLaboratorio = false;
        validacionesDepartamento = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        mensajeError = "";
        validacionesCosto = true;
        validacionesInversion = true;
        mensajeFormulario = "";
        activarNuevoSala = true;
        activarNuevoLaboratorio = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        nuevoInversionModulo = null;
        nuevoLaboratorioModulo = null;
        nuevoSalaLaboratorioModulo = null;
        listaLaboratorios = null;
        listaSalasLaboratorios = null;
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
    public List<Departamento> getListaDepartamentos() {
        if (null == listaDepartamentos) {
            listaDepartamentos = gestionarPlantaModulosBO.consultarDepartamentosRegistrados();
        }
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public boolean isActivarNuevoLaboratorio() {
        return activarNuevoLaboratorio;
    }

    public void setActivarNuevoLaboratorio(boolean activarNuevoLaboratorio) {
        this.activarNuevoLaboratorio = activarNuevoLaboratorio;
    }

    public Departamento getNuevoDepartamentoModulo() {
        return nuevoDepartamentoModulo;
    }

    public void setNuevoDepartamentoModulo(Departamento nuevoDepartamentoModulo) {
        this.nuevoDepartamentoModulo = nuevoDepartamentoModulo;
    }

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getNuevoLaboratorioModulo() {
        return nuevoLaboratorioModulo;
    }

    public void setNuevoLaboratorioModulo(Laboratorio nuevoLaboratorioModulo) {
        this.nuevoLaboratorioModulo = nuevoLaboratorioModulo;
    }

    public List<SalaLaboratorio> getListaSalasLaboratorios() {
        return listaSalasLaboratorios;
    }

    public void setListaSalasLaboratorios(List<SalaLaboratorio> listaSalasLaboratorios) {
        this.listaSalasLaboratorios = listaSalasLaboratorios;
    }

    public boolean isActivarNuevoSala() {
        return activarNuevoSala;
    }

    public void setActivarNuevoSala(boolean activarNuevoSala) {
        this.activarNuevoSala = activarNuevoSala;
    }

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
