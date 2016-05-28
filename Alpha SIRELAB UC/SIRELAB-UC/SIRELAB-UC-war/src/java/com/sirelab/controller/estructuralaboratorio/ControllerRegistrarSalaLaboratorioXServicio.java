/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalaLaboratorioxServiciosBOInterface;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.ServiciosSala;
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
public class ControllerRegistrarSalaLaboratorioXServicio implements Serializable {

    @EJB
    GestionarPlantaSalaLaboratorioxServiciosBOInterface gestionarPlantaSalaLaboratorioxServiciosBO;

    private SalaLaboratorio nuevoSalaLaboratorio;
    private List<ServiciosSala> listaServiciosSala;
    private ServiciosSala nuevoServicio;
    //
    private boolean validacionesServicio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private BigInteger idSalaLaboratorio;

    public ControllerRegistrarSalaLaboratorioXServicio() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIdSalaLaboratorio(BigInteger idSalaLaboratorio) {
        this.idSalaLaboratorio = idSalaLaboratorio;
        nuevoSalaLaboratorio = gestionarPlantaSalaLaboratorioxServiciosBO.obtenerSalaLaboratorioPorId(idSalaLaboratorio);
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        nuevoServicio = null;
        //
        validacionesServicio = false;
    }

    public void actualizarServiciosSala() {
        if (Utilidades.validarNulo(nuevoServicio)) {
            validacionesServicio = true;
        } else {
            validacionesServicio = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoServicio", new FacesMessage("El campo Servicio es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesServicio == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarRegistroRepetido() {
        boolean retorno = true;
        SalaLaboratorioxServicios registro = gestionarPlantaSalaLaboratorioxServiciosBO.consultarSalaLaboratorioXServicioPorSalayServicio(nuevoSalaLaboratorio.getIdsalalaboratorio(), nuevoServicio.getIdserviciossala());
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoSalaLaboratorioXServicio() {
        if (validarResultadosValidacion() == true) {
            if (validarRegistroRepetido() == true) {
                almacenarNuevoSalaLaboratorioXServicioEnSistema();
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "EL registro ya se encuentra registrado en el sistema.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenarNuevoSalaLaboratorioXServicioEnSistema() {
        try {
            SalaLaboratorioxServicios laboratorioNuevo = new SalaLaboratorioxServicios();
            laboratorioNuevo.setServiciosala(nuevoServicio);
            laboratorioNuevo.setEstado(true);
            laboratorioNuevo.setSalalaboratorio(nuevoSalaLaboratorio);
            gestionarPlantaSalaLaboratorioxServiciosBO.crearSalaLaboratorioxServicios(laboratorioNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarSalaLaboratorioXServicio almacenarNuevoSalaLaboratorioXServicioEnSistema:  " + e.toString(),e);
            logger.error("Error ControllerRegistrarSalaLaboratorioXServicio almacenarNuevoSalaLaboratorioXServicioEnSistema : " + e.toString(),e);

        }
    }

    public void limpiarFormulario() {
        nuevoServicio = null;
        validacionesServicio = false;
        mensajeFormulario = "";
    }

    public String cancelarRegistroSalaLaboratorioXServicio() {
        listaServiciosSala = null;
        nuevoServicio = null;
        activarAceptar = false;
        nuevoSalaLaboratorio = null;
        validacionesServicio = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        return "detallessala";
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
    public SalaLaboratorio getNuevoSalaLaboratorio() {
        return nuevoSalaLaboratorio;
    }

    public void setNuevoSalaLaboratorio(SalaLaboratorio nuevoSalaLaboratorio) {
        this.nuevoSalaLaboratorio = nuevoSalaLaboratorio;
    }

    public List<ServiciosSala> getListaServiciosSala() {
        if (null == listaServiciosSala) {
            listaServiciosSala = gestionarPlantaSalaLaboratorioxServiciosBO.consultarServiciosSalaRegistradas();
        }
        return listaServiciosSala;
    }

    public void setListaServiciosSala(List<ServiciosSala> listaServiciosSala) {
        this.listaServiciosSala = listaServiciosSala;
    }

    public ServiciosSala getNuevoServicio() {
        return nuevoServicio;
    }

    public void setNuevoServicio(ServiciosSala nuevoServicio) {
        this.nuevoServicio = nuevoServicio;
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

    public BigInteger getIdSalaLaboratorio() {
        return idSalaLaboratorio;
    }

    public void setIdSalaLaboratorio(BigInteger idSalaLaboratorio) {
        this.idSalaLaboratorio = idSalaLaboratorio;
    }

}
