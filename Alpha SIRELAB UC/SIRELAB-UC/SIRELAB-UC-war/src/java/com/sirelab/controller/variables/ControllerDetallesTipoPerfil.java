/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposPerfilesBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class ControllerDetallesTipoPerfil implements Serializable {

    @EJB
    GestionarVariableTiposPerfilesBOInterface gestionarVariableTiposPerfilesBO;

    private String inputCodigo, inputNombreRegistro, inputCodigoRegistro;
    private boolean validacionesCodigoRegistro;
    private String mensajeFormulario;
    private BigInteger idTipoPerfil;
    private TipoPerfil tipoPerfilEditar;
    private boolean modificacionRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private List<String> listaTiposPerfiles;
    private String tipoPerfil;
    private boolean activarCasillasAdd;
    private String colorMensaje;

    public ControllerDetallesTipoPerfil() {
    }

    @PostConstruct
    public void init() {
        BasicConfigurator.configure();
    }

    public void recibirIDDetalleTipoPerfil(BigInteger idDetalle) {
        this.idTipoPerfil = idDetalle;
        listaTiposPerfiles = new ArrayList<String>();
        listaTiposPerfiles.add("DEPARTAMENTO");
        listaTiposPerfiles.add("LABORATORIO");
        listaTiposPerfiles.add("AREA PROFUNDIZACIÓN");
        activarCasillasAdd = false;
        cargarInformacionRegistro();
        mensajeFormulario = "N/A";
        colorMensaje = "black";
    }

    private void cargarInformacionRegistro() {
        tipoPerfilEditar = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorID(idTipoPerfil);
        if (null != tipoPerfilEditar) {
            inputCodigo = tipoPerfilEditar.getCodigo();
            tipoPerfil = tipoPerfilEditar.getNombre();
            inputNombreRegistro = tipoPerfilEditar.getNombreregistro();
            inputCodigoRegistro = tipoPerfilEditar.getCodigoregistro();
            validacionesCodigoRegistro = true;
            modificacionRegistro = false;
        }

    }

    public void actualizarTipoPerfil() {
        if ("DEPARTAMENTO".equalsIgnoreCase(tipoPerfil)) {
            inputCodigo = "3";
            activarCasillasAdd = false;
        }
        if ("LABORATORIO".equalsIgnoreCase(tipoPerfil)) {
            inputCodigo = "4";
            activarCasillasAdd = false;
        }
        if ("AREA PROFUNDIZACIÓN".equalsIgnoreCase(tipoPerfil)) {
            inputCodigo = "5";
            activarCasillasAdd = false;
        }
    }

    public void validarCodigoRegistro() {
        if (Utilidades.validarNulo(inputCodigoRegistro) && (!inputCodigoRegistro.isEmpty())  && (inputCodigoRegistro.trim().length() > 0)) {
            if (inputCodigo.equalsIgnoreCase("3")) {
                Departamento registro = gestionarVariableTiposPerfilesBO.consultarDepartamentoPorCodigo(inputCodigoRegistro);
                if (null != registro) {
                    inputNombreRegistro = registro.getNombredepartamento();
                } else {
                    validacionesCodigoRegistro = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputCodigoRegistro", new FacesMessage("El codigo no se encuentre registrado."));
                }
            }
            if (inputCodigo.equalsIgnoreCase("4")) {
                Laboratorio registro = gestionarVariableTiposPerfilesBO.consultaLaboratorioPorCodigo(inputCodigoRegistro);
                if (null != registro) {
                    inputNombreRegistro = registro.getNombrelaboratorio();
                } else {
                    validacionesCodigoRegistro = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputCodigoRegistro", new FacesMessage("El codigo no se encuentre registrado."));
                }
            }
            if (inputCodigo.equalsIgnoreCase("5")) {
                AreaProfundizacion registro = gestionarVariableTiposPerfilesBO.consultarAreaProfundizacionPorCodigo(inputCodigoRegistro);
                if (null != registro) {
                    inputNombreRegistro = registro.getNombrearea();
                } else {
                    validacionesCodigoRegistro = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputCodigoRegistro", new FacesMessage("El codigo no se encuentre registrado."));
                }
            }
        } else {
            validacionesCodigoRegistro = false;
            FacesContext.getCurrentInstance().addMessage("form:inputCodigoRegistro", new FacesMessage("El codigo se encuentra incorrecto."));
        }
        modificacionRegistro = true;
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesCodigoRegistro == false) {
            retorno = false;
        }
        return retorno;
    }

    public boolean validarCodigoRepetido() {
        boolean retorno = true;
        TipoPerfil registro = gestionarVariableTiposPerfilesBO.consultarTipoPerfilPorCodigo(inputCodigo);
        if (null != registro) {
            if (!tipoPerfilEditar.getIdtipoperfil().equals(registro.getIdtipoperfil())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionTipoPerfil() {
        if (modificacionRegistro == true) {
            if (validarValidacionesRegistro() == true) {
                if (validarCodigoRepetido() == true) {
                    almacenarModificacionRegistro();
                    cargarInformacionRegistro();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "green";
            mensajeFormulario = "No existen modificaciones para ser almacenadas.";
        }
    }

    private void almacenarModificacionRegistro() {
        try {
            tipoPerfilEditar.setCodigo(inputCodigo);
            tipoPerfilEditar.setCodigoregistro(inputCodigoRegistro);
            tipoPerfilEditar.setNombre(tipoPerfil);
            tipoPerfilEditar.setNombreregistro(inputNombreRegistro);
            gestionarVariableTiposPerfilesBO.editarTipoPerfil(tipoPerfilEditar);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesTipoPerfil almacenarModificacionRegistro:  " + e.toString());
            System.out.println("Error ControllerDetallesTipoPerfil almacenarModificacionRegistro: " + e.toString());
        }
    }

    public void cancelarTipoPerfil() {
        tipoPerfil = "DEPARTAMENTO";
        inputCodigo = "3";
        activarCasillasAdd = false;
        idTipoPerfil = null;
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        validacionesCodigoRegistro = false;
        mensajeFormulario = "N/A";
        colorMensaje = "green";
        modificacionRegistro = false;
    }

    public String cerrarPagina() {
        cancelarTipoPerfil();
        return "variablesusuario";
    }

    //GET-SET
    public List<String> getListaTiposPerfiles() {
        return listaTiposPerfiles;
    }

    public void setListaTiposPerfiles(List<String> listaTiposPerfiles) {
        this.listaTiposPerfiles = listaTiposPerfiles;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public boolean isActivarCasillasAdd() {
        return activarCasillasAdd;
    }

    public void setActivarCasillasAdd(boolean activarCasillasAdd) {
        this.activarCasillasAdd = activarCasillasAdd;
    }

    public String getInputCodigo() {
        return inputCodigo;
    }

    public void setInputCodigo(String inputCodigo) {
        this.inputCodigo = inputCodigo;
    }

    public String getInputNombreRegistro() {
        return inputNombreRegistro;
    }

    public void setInputNombreRegistro(String inputNombreRegistro) {
        this.inputNombreRegistro = inputNombreRegistro;
    }

    public String getInputCodigoRegistro() {
        return inputCodigoRegistro;
    }

    public void setInputCodigoRegistro(String inputCodigoRegistro) {
        this.inputCodigoRegistro = inputCodigoRegistro;
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

}
