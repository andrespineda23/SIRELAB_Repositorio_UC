/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposPerfilesBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarTipoPerfil implements Serializable {

    @EJB
    GestionarVariableTiposPerfilesBOInterface gestionarVariableTiposPerfilesBO;

    private String inputCodigo, inputNombreRegistro, inputCodigoRegistro;
    private boolean validacionesCodigoRegistro;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private List<String> listaTiposPerfiles;
    private String tipoPerfil;
    private boolean activarCasillasAdd;
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarTipoPerfil() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarAceptar = false;
        activarCasillasAdd = false;
        tipoPerfil = "DEPARTAMENTO";
        inputCodigo = "3";
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        validacionesCodigoRegistro = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        listaTiposPerfiles = new ArrayList<String>();
        listaTiposPerfiles.add("DEPARTAMENTO");
        listaTiposPerfiles.add("LABORATORIO");
        listaTiposPerfiles.add("AREA PROFUNDIZACIÓN");
        BasicConfigurator.configure();
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
        if ("ÁREA PROFUNDIZACIÓN".equalsIgnoreCase(tipoPerfil)) {
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
            FacesContext.getCurrentInstance().addMessage("form:inputCodigoRegistro", new FacesMessage("El codigo se encuentra incorrecto. "+constantes.VARIABLE_NOMBRE));
        }
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
            retorno = false;
        }
        return retorno;
    }

    public void registrarTipoPerfil() {
        if (validarValidacionesRegistro() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarRegistroNuevo();
                restaurarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
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
    }

    private void almacenarRegistroNuevo() {
        try {
            TipoPerfil tipoNuevo = new TipoPerfil();
            tipoNuevo.setCodigo(inputCodigo);
            tipoNuevo.setCodigoregistro(inputCodigoRegistro);
            tipoNuevo.setNombre(tipoPerfil);
            tipoNuevo.setNombreregistro(inputNombreRegistro);
            gestionarVariableTiposPerfilesBO.crearTipoPerfil(tipoNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarTipoPerfil almacenarRegistroNuevo:  " + e.toString());
            System.out.println("Error ControllerRegistrarTipoPerfil almacenarRegistroNuevo: " + e.toString());
        }
    }

    public void cancelarTipoPerfil() {
        tipoPerfil = "DEPARTAMENTO";
        inputCodigo = "3";
        inputCodigo = null;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        activarCasillasAdd = false;
        validacionesCodigoRegistro = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
    }

    public String cerrarPagina() {
        cancelarTipoPerfil();
        return "variablesusuario";
    }

    private void restaurarFormulario() {
        tipoPerfil = "DEPARTAMENTO";
        inputCodigo = "3";
        inputCodigo = null;
        activarCasillasAdd = false;
        inputNombreRegistro = null;
        inputCodigoRegistro = null;
        validacionesCodigoRegistro = false;
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
    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public List<String> getListaTiposPerfiles() {
        return listaTiposPerfiles;
    }

    public void setListaTiposPerfiles(List<String> listaTiposPerfiles) {
        this.listaTiposPerfiles = listaTiposPerfiles;
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

    public boolean isActivarCasillasAdd() {
        return activarCasillasAdd;
    }

    public void setActivarCasillasAdd(boolean activarCasillasAdd) {
        this.activarCasillasAdd = activarCasillasAdd;
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
