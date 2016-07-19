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
public class ControllerDetallesSalaLaboratorioXServicio implements Serializable {

    @EJB
    GestionarPlantaSalaLaboratorioxServiciosBOInterface gestionarPlantaSalaLaboratorioxServiciosBO;

    private SalaLaboratorioxServicios salaSalaLaboratorioPorServicioDetalles;
    private BigInteger idSalaLaboratorioPorServicio;
    private BigInteger idSalaLaboratorio;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio editarLaboratorio;
    private List<SalaLaboratorio> listaSalaLaboratorios;
    private SalaLaboratorio editarSalaLaboratorio;
    private boolean activarSalaLaboratorio;
    private List<ServiciosSala> listaServiciosSala;
    private ServiciosSala editarServicio;
    //
    private boolean validacionesLaboratorio, validacionesSalaLaboratorio, validacionesServicio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private String mensajeError;
    private boolean activarCasillas;

    public ControllerDetallesSalaLaboratorioXServicio() {
    }

    @PostConstruct
    public void init() {
        activarSalaLaboratorio = true;
        //
        activarCasillas = false;
        validacionesServicio = true;
        validacionesLaboratorio = true;
        validacionesSalaLaboratorio = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionSalaLaboratorioxServicios() {
        validacionesServicio = true;
        validacionesLaboratorio = true;
        validacionesSalaLaboratorio = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        salaSalaLaboratorioPorServicioDetalles = new SalaLaboratorioxServicios();
        mensajeError = "";
        if (activarCasillas == false) {
            recibirIDSalaLaboratorioxServiciosDetalles(this.idSalaLaboratorioPorServicio);
        } else {
            editarServicio = null;
            editarLaboratorio = null;
            editarSalaLaboratorio = null;
            editarEstado = false;
            activarSalaLaboratorio = false;
            listaServiciosSala = null;
            listaLaboratorios = null;
            listaSalaLaboratorios = null;
            mensajeError = "";
            idSalaLaboratorio = null;
            activarCasillas = false;
        }
        return "administrarlaboratoriosporareas";
    }

    public void asignarValoresVariablesSalaLaboratorioxServicios() {
        editarServicio = salaSalaLaboratorioPorServicioDetalles.getServiciosala();
        editarLaboratorio = salaSalaLaboratorioPorServicioDetalles.getSalalaboratorio().getLaboratorio();
        editarSalaLaboratorio = salaSalaLaboratorioPorServicioDetalles.getSalalaboratorio();
        editarEstado = salaSalaLaboratorioPorServicioDetalles.getEstado();
        activarSalaLaboratorio = false;
        listaServiciosSala = gestionarPlantaSalaLaboratorioxServiciosBO.consultarServiciosSalaRegistradas();
        listaLaboratorios = gestionarPlantaSalaLaboratorioxServiciosBO.consultarLaboratoriosRegistrados();
        if (Utilidades.validarNulo(editarLaboratorio)) {
            listaSalaLaboratorios = gestionarPlantaSalaLaboratorioxServiciosBO.consultarSalaLaboratorioPorIDLaboratorio(editarLaboratorio.getIdlaboratorio());
        }
        mensajeError = "";
        idSalaLaboratorio = editarSalaLaboratorio.getIdsalalaboratorio();
    }

    public void recibirIDSalaLaboratorioxServiciosDetalles(BigInteger idRegistro) {
        this.idSalaLaboratorioPorServicio = idRegistro;
        salaSalaLaboratorioPorServicioDetalles = gestionarPlantaSalaLaboratorioxServiciosBO.consultarSalaLaboratorioXServicioPorID(idRegistro);
        asignarValoresVariablesSalaLaboratorioxServicios();
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(editarLaboratorio)) {
            editarSalaLaboratorio = new SalaLaboratorio();
            listaSalaLaboratorios = gestionarPlantaSalaLaboratorioxServiciosBO.consultarSalaLaboratorioPorIDLaboratorio(editarLaboratorio.getIdlaboratorio());
            activarSalaLaboratorio = false;
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            validacionesSalaLaboratorio = false;
            editarSalaLaboratorio = new SalaLaboratorio();
            listaSalaLaboratorios = null;
            activarSalaLaboratorio = true;
            FacesContext.getCurrentInstance().addMessage("form:editarLaboratorio", new FacesMessage("El campo Laboratorio es obligatorio."));
        }
    }

    public void actualizarSalaLaboratorios() {
        if (Utilidades.validarNulo(editarSalaLaboratorio)) {
            validacionesSalaLaboratorio = true;
        } else {
            validacionesSalaLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:editarSalaLaboratorio", new FacesMessage("El campo SalaLaboratorio es obligatorio."));
        }
    }

    public void actualizarServiciosSala() {
        if (Utilidades.validarNulo(editarServicio)) {
            validacionesServicio = true;
        } else {
            validacionesServicio = false;
            FacesContext.getCurrentInstance().addMessage("form:editarServicio", new FacesMessage("El campo Area Profundización es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesLaboratorio == false) {
            mensajeError = mensajeError + " - Laboratorio - ";
            retorno = false;
        }
        if (validacionesSalaLaboratorio == false) {
            mensajeError = mensajeError + " - Sala - ";
            retorno = false;
        }
        if (validacionesServicio == false) {
            mensajeError = mensajeError + " - Servicio - ";
            retorno = false;
        }
        return retorno;
    }

    private boolean validarRegistroRepetido() {
        boolean retorno = true;
        SalaLaboratorioxServicios registro = gestionarPlantaSalaLaboratorioxServiciosBO.consultarSalaLaboratorioXServicioPorSalayServicio(editarSalaLaboratorio.getIdsalalaboratorio(), editarServicio.getIdserviciossala());
        if (null != registro) {
            if (!salaSalaLaboratorioPorServicioDetalles.getIdsalalaboratorioxservicios().equals(registro.getIdsalalaboratorioxservicios())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificarSalaLaboratorioPorServicio() {
        if (validarResultadosValidacion() == true) {
            if (Utilidades.validarNulo(editarSalaLaboratorio)) {
                if (validarRegistroRepetido() == true) {
                    almacenarModificarSalaLaboratorioPorServicioEnSistema();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "El registro ya se encuentra ingresado en el sistema.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Seleccione la sala de laboratorio antes de continuar.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    private void almacenarModificarSalaLaboratorioPorServicioEnSistema() {
        try {
            salaSalaLaboratorioPorServicioDetalles.setServiciosala(editarServicio);
            salaSalaLaboratorioPorServicioDetalles.setSalalaboratorio(editarSalaLaboratorio);
            salaSalaLaboratorioPorServicioDetalles.setEstado(editarEstado);
            gestionarPlantaSalaLaboratorioxServiciosBO.editarSalaLaboratorioxServicios(salaSalaLaboratorioPorServicioDetalles);;
        } catch (Exception e) {
            logger.error("Error ControllerDetallesSalaLaboratorioXServicio almacenarModificarSalaLaboratorioPorAreaEnSistema:  " + e.toString(), e);

        }
    }

    public void eliminarSalaLaboratorioXServicio() {
        activarCasillas = true;
        gestionarPlantaSalaLaboratorioxServiciosBO.eliminarSalaLaboratorioxServicios(salaSalaLaboratorioPorServicioDetalles);
        colorMensaje = "#FF0000";
        mensajeFormulario = "El registro ha sido eliminado con éxito. Regrese nuevamente a la pagina de consulta.";
    }

    //GET-SET
    public SalaLaboratorioxServicios getSalaLaboratorioPorServicioDetalles() {
        return salaSalaLaboratorioPorServicioDetalles;
    }

    public void setSalaLaboratorioPorServicioDetalles(SalaLaboratorioxServicios salaSalaLaboratorioPorServicioDetalles) {
        this.salaSalaLaboratorioPorServicioDetalles = salaSalaLaboratorioPorServicioDetalles;
    }

    public BigInteger getIdSalaLaboratorioPorServicio() {
        return idSalaLaboratorioPorServicio;
    }

    public void setIdSalaLaboratorioPorServicio(BigInteger idSalaLaboratorioPorServicio) {
        this.idSalaLaboratorioPorServicio = idSalaLaboratorioPorServicio;
    }

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getEditarLaboratorio() {
        return editarLaboratorio;
    }

    public void setEditarLaboratorio(Laboratorio editarLaboratorio) {
        this.editarLaboratorio = editarLaboratorio;
    }

    public List<SalaLaboratorio> getListaSalaLaboratorios() {
        return listaSalaLaboratorios;
    }

    public void setListaSalaLaboratorios(List<SalaLaboratorio> listaSalaLaboratorios) {
        this.listaSalaLaboratorios = listaSalaLaboratorios;
    }

    public SalaLaboratorio getEditarSalaLaboratorio() {
        return editarSalaLaboratorio;
    }

    public void setEditarSalaLaboratorio(SalaLaboratorio editarSalaLaboratorio) {
        this.editarSalaLaboratorio = editarSalaLaboratorio;
    }

    public boolean isActivarSalaLaboratorio() {
        return activarSalaLaboratorio;
    }

    public void setActivarSalaLaboratorio(boolean activarSalaLaboratorio) {
        this.activarSalaLaboratorio = activarSalaLaboratorio;
    }

    public List<ServiciosSala> getListaServiciosSala() {
        return listaServiciosSala;
    }

    public void setListaServiciosSala(List<ServiciosSala> listaServiciosSala) {
        this.listaServiciosSala = listaServiciosSala;
    }

    public ServiciosSala getEditarServicio() {
        return editarServicio;
    }

    public void setEditarServicio(ServiciosSala editarServicio) {
        this.editarServicio = editarServicio;
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

    public BigInteger getIdSalaLaboratorio() {
        return idSalaLaboratorio;
    }

    public void setIdSalaLaboratorio(BigInteger idSalaLaboratorio) {
        this.idSalaLaboratorio = idSalaLaboratorio;
    }

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

}
