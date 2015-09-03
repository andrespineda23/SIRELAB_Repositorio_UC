/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaModulosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
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
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private List<SalaLaboratorio> listaSalasLaboratorios;
    private boolean activarNuevoLaboratorio;
    private boolean activarNuevoSala;
    private boolean activarNuevoLabPorArea;
    private String nuevoCodigoModulo, nuevoDetalleModulo, nuevoCapacidadModulo, nuevoCostoModulo, nuevoInversionModulo;
    private Departamento nuevoDepartamentoModulo;
    private Laboratorio nuevoLaboratorioModulo;
    private LaboratoriosPorAreas nuevoLaboratorioPorAreaModulo;
    private SalaLaboratorio nuevoSalaLaboratorioModulo;
    //
    private boolean validacionesCodigo, validacionesDetalle, validacionesCapacidad, validacionesCosto, validacionesInversion, validacionesDepartamento;
    private boolean validacionesLaboratorioPorArea, validacionesSala, validacionesLaboratorio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarModulo() {
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
        validacionesLaboratorio = false;
        validacionesDepartamento = false;
        validacionesLaboratorioPorArea = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        activarNuevoSala = true;
        activarNuevoLaboratorio = true;
        activarNuevoLabPorArea = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        nuevoInversionModulo = null;
        nuevoDepartamentoModulo = null;
        nuevoLaboratorioModulo = null;
        nuevoSalaLaboratorioModulo = null;
        nuevoLaboratorioPorAreaModulo = null;
        BasicConfigurator.configure();
    }

    public void limpiarRegistroModuloLaboratorio() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesLaboratorio = false;
        validacionesDepartamento = false;
        validacionesLaboratorioPorArea = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        activarNuevoSala = true;
        activarNuevoLaboratorio = true;
        activarNuevoLabPorArea = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        nuevoInversionModulo = null;
        nuevoSalaLaboratorioModulo = null;
        nuevoLaboratorioModulo = null;
        nuevoDepartamentoModulo = null;
        nuevoLaboratorioPorAreaModulo = null;
        listaSalasLaboratorios = null;
        listaLaboratoriosPorAreas = null;
        listaLaboratorios = null;
        listaDepartamentos = null;
    }

    public void validarDetalleModulo() {
        if (Utilidades.validarNulo(nuevoDetalleModulo) && (!nuevoDetalleModulo.isEmpty()) && (nuevoDetalleModulo.trim().length() > 0)) {
            int tam = nuevoDetalleModulo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoDetalleModulo)) {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El detalle ingresado es incorrecto."));
                } else {
                    validacionesDetalle = true;
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El tamaño minimo es 4 caracteres."));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDetalleModulo", new FacesMessage("El detalle es obligatorio."));
        }

    }

    public void validarCodigoModulo() {
        if (Utilidades.validarNulo(nuevoCodigoModulo) && (!nuevoCodigoModulo.isEmpty()) && (nuevoCodigoModulo.trim().length() > 0)) {
            int tam = nuevoCodigoModulo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigoModulo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoModulo", new FacesMessage("El codigo ingresado es incorrecto."));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoModulo", new FacesMessage("El tamaño minimo es 4 caracteres."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoModulo", new FacesMessage("El codigo es obligatorio."));
        }
    }

    public void validarCostoAlquilerModulo() {
        if (Utilidades.validarNulo(nuevoCostoModulo) && (!nuevoCostoModulo.isEmpty()) && (nuevoCostoModulo.trim().length() > 0)) {
            if (Utilidades.isNumber(nuevoCostoModulo)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoModulo", new FacesMessage("El costo se encuentra incorrecto."));
            }
        }
    }

    public void validarCapacidadModulo() {
        if (Utilidades.validarNulo(nuevoCapacidadModulo) && (!nuevoCapacidadModulo.isEmpty()) && (nuevoCapacidadModulo.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoCapacidadModulo)) == false) {
                validacionesCapacidad = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCapacidadModulo", new FacesMessage("La capacidad ingresada se encuentra incorrecta."));
            } else {
                validacionesCapacidad = true;
            }
        }
    }

    public void validarInversionModulo() {
        if (Utilidades.validarNulo(nuevoInversionModulo) && (!nuevoInversionModulo.isEmpty()) && (nuevoInversionModulo.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoInversionModulo)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoInversionModulo", new FacesMessage("El valor de inversión se encuentra incorrecto."));
            } else {
                validacionesInversion = true;
            }
        }
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(nuevoDepartamentoModulo)) {
            nuevoLaboratorioModulo = new Laboratorio();
            listaLaboratorios = gestionarPlantaModulosBO.consultarLaboratoriosPorIDDepartamento(nuevoDepartamentoModulo.getIddepartamento());
            activarNuevoLaboratorio = false;
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            validacionesLaboratorio = false;
            validacionesSala = false;

            nuevoLaboratorioModulo = new Laboratorio();
            nuevoSalaLaboratorioModulo = new SalaLaboratorio();
            nuevoLaboratorioPorAreaModulo = new LaboratoriosPorAreas();

            listaLaboratorios = null;
            listaLaboratoriosPorAreas = null;
            listaSalasLaboratorios = null;

            activarNuevoSala = true;
            activarNuevoLabPorArea = true;
            activarNuevoLaboratorio = true;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDepartamentoModulo", new FacesMessage("El Departamento es obligatorio."));
        }
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(nuevoLaboratorioModulo)) {
            nuevoLaboratorioPorAreaModulo = new LaboratoriosPorAreas();
            listaLaboratoriosPorAreas = gestionarPlantaModulosBO.consultarLaboratoriosPorAreasPorLaboratorio(nuevoLaboratorioModulo.getIdlaboratorio());
            activarNuevoLabPorArea = false;
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            validacionesLaboratorioPorArea = false;
            validacionesSala = false;

            nuevoSalaLaboratorioModulo = new SalaLaboratorio();
            nuevoLaboratorioPorAreaModulo = new LaboratoriosPorAreas();

            listaSalasLaboratorios = null;
            listaLaboratoriosPorAreas = null;

            activarNuevoSala = true;
            activarNuevoLabPorArea = true;
            FacesContext.getCurrentInstance().addMessage("form:nuevoLaboratorioModulo", new FacesMessage("El laboratorio es obligatorio."));
        }
    }

    public void actualizarLaboratoriosPorAreas() {
        if (Utilidades.validarNulo(nuevoLaboratorioPorAreaModulo)) {
            nuevoSalaLaboratorioModulo = new SalaLaboratorio();
            listaSalasLaboratorios = gestionarPlantaModulosBO.consultarSalasLaboratoriosPorIDLaboratorioArea(nuevoLaboratorioPorAreaModulo.getIdlaboratoriosporareas());
            activarNuevoSala = false;
            validacionesLaboratorioPorArea = true;
        } else {
            validacionesLaboratorioPorArea = false;
            validacionesSala = false;

            nuevoSalaLaboratorioModulo = new SalaLaboratorio();

            listaSalasLaboratorios = null;
            activarNuevoSala = true;
            validacionesLaboratorioPorArea = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoLaboratorioPorAreaModulo", new FacesMessage("El area de profundización es obligatorio."));
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
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesCapacidad == false) {
            retorno = false;
        }
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesDetalle == false) {
            retorno = false;
        }
        if (validacionesInversion == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            retorno = false;
        }
        if (validacionesLaboratorioPorArea == false) {
            retorno = false;
        }
        if (validacionesSala == false) {
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
                    colorMensaje = "red";
                    mensajeFormulario = "La sala ya se encuentra llena. No es posible crear el modulo. Capacidad maxima (" + nuevoSalaLaboratorioModulo.getCapacidadsala() + ").";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ya esta registrado con el edificio y laboratorio por area seleccionado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
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
            logger.error("Error ControllerGestionarPlantaModulos almacenaNuevoModuloEnSistema:  " + e.toString());
            System.out.println("Error ControllerGestionarPlantaModulos almacenaNuevoModuloEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        validacionesDetalle = false;
        validacionesCodigo = false;
        validacionesLaboratorio = false;
        validacionesLaboratorioPorArea = false;
        validacionesDepartamento = false;
        validacionesSala = false;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        mensajeFormulario = "";
        activarNuevoSala = true;
        activarNuevoLabPorArea = true;
        activarNuevoLaboratorio = true;
        nuevoCodigoModulo = null;
        nuevoDetalleModulo = null;
        nuevoCostoModulo = null;
        nuevoCapacidadModulo = null;
        nuevoInversionModulo = null;
        nuevoLaboratorioPorAreaModulo = null;
        nuevoLaboratorioModulo = null;
        nuevoSalaLaboratorioModulo = null;
        nuevoLaboratorioPorAreaModulo = null;
        listaLaboratorios = null;
        listaLaboratoriosPorAreas = null;
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

    public boolean isActivarNuevoLabPorArea() {
        return activarNuevoLabPorArea;
    }

    public void setActivarNuevoLabPorArea(boolean activarNuevoLabPorArea) {
        this.activarNuevoLabPorArea = activarNuevoLabPorArea;
    }

    public Laboratorio getNuevoLaboratorioModulo() {
        return nuevoLaboratorioModulo;
    }

    public void setNuevoLaboratorioModulo(Laboratorio nuevoLaboratorioModulo) {
        this.nuevoLaboratorioModulo = nuevoLaboratorioModulo;
    }

    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreas() {
        return listaLaboratoriosPorAreas;
    }

    public void setListaLaboratoriosPorAreas(List<LaboratoriosPorAreas> listaLaboratoriosPorAreas) {
        this.listaLaboratoriosPorAreas = listaLaboratoriosPorAreas;
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

    public LaboratoriosPorAreas getNuevoLaboratorioPorAreaModulo() {
        return nuevoLaboratorioPorAreaModulo;
    }

    public void setNuevoLaboratorioPorAreaModulo(LaboratoriosPorAreas nuevoLaboratorioPorAreaModulo) {
        this.nuevoLaboratorioPorAreaModulo = nuevoLaboratorioPorAreaModulo;
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
