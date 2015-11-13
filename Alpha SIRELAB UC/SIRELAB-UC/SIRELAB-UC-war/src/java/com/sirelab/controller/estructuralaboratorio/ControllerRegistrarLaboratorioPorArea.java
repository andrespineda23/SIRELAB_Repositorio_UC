/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaLaboratoriosPorAreasBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
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
public class ControllerRegistrarLaboratorioPorArea implements Serializable {

    @EJB
    GestionarPlantaLaboratoriosPorAreasBOInterface gestionarPlantaLaboratoriosPorAreasBO;

    private Laboratorio nuevoLaboratorio;
    private List<AreaProfundizacion> listaAreasProfundizacion;
    private AreaProfundizacion nuevoArea;
    //
    private boolean validacionesArea;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private BigInteger idLaboratorio;

    public ControllerRegistrarLaboratorioPorArea() {
    }

    @PostConstruct
    public void init() {

        BasicConfigurator.configure();
    }

    public void recibirIdLaboratorio(BigInteger idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
        nuevoLaboratorio = gestionarPlantaLaboratoriosPorAreasBO.obtenerLaboratorioPorId(idLaboratorio);
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        nuevoArea = null;
        //
        validacionesArea = false;
    }

    public void actualizarAreaProfundizacion() {
        if (Utilidades.validarNulo(nuevoArea)) {
            validacionesArea = true;
        } else {
            validacionesArea = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoArea", new FacesMessage("El campo Area Profundización es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesArea == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarRegistroRepetido() {
        boolean retorno = true;
        LaboratoriosPorAreas registro = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratorioPorAreaPorLabYArea(nuevoLaboratorio.getIdlaboratorio(), nuevoArea.getIdareaprofundizacion());
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoLaboratorioPorArea() {
        if (validarResultadosValidacion() == true) {
            if (validarRegistroRepetido() == true) {
                almacenarNuevoLaboratorioPorAreaEnSistema();
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "EL registro ya se encuentra registrado en el sistema.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenarNuevoLaboratorioPorAreaEnSistema() {
        try {
            LaboratoriosPorAreas laboratorioNuevo = new LaboratoriosPorAreas();
            laboratorioNuevo.setAreaprofundizacion(nuevoArea);
            laboratorioNuevo.setEstado(true);
            laboratorioNuevo.setLaboratorio(nuevoLaboratorio);
            gestionarPlantaLaboratoriosPorAreasBO.crearLaboratoriosPorAreas(laboratorioNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaLaboratorios almacenarNuevoLaboratorioPorAreaEnSistema:  " + e.toString());
            System.out.println("Error ControllerGestionarPlantaLaboratorios almacenarNuevoLaboratorioPorAreaEnSistema : " + e.toString());

        }
    }

    public void limpiarFormulario() {
        nuevoArea = null;
        nuevoLaboratorio = null;
        validacionesArea = false;
        mensajeFormulario = "";
    }

    public String cancelarRegistroLaboratorioPorArea() {
        listaAreasProfundizacion = null;
        nuevoArea = null;
        activarAceptar = false;
        nuevoLaboratorio = null;
        validacionesArea = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        return "detalleslaboratorio";
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
    public Laboratorio getNuevoLaboratorio() {
        return nuevoLaboratorio;
    }

    public void setNuevoLaboratorio(Laboratorio nuevoLaboratorio) {
        this.nuevoLaboratorio = nuevoLaboratorio;
    }

    public List<AreaProfundizacion> getListaAreasProfundizacion() {
        if (listaAreasProfundizacion == null) {
            listaAreasProfundizacion = gestionarPlantaLaboratoriosPorAreasBO.consultarAreasProfundizacionRegistradas();
        }
        return listaAreasProfundizacion;
    }

    public void setListaAreasProfundizacion(List<AreaProfundizacion> listaAreasProfundizacion) {
        this.listaAreasProfundizacion = listaAreasProfundizacion;
    }

    public AreaProfundizacion getNuevoArea() {
        return nuevoArea;
    }

    public void setNuevoArea(AreaProfundizacion nuevoArea) {
        this.nuevoArea = nuevoArea;
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

    public BigInteger getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(BigInteger idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

}
