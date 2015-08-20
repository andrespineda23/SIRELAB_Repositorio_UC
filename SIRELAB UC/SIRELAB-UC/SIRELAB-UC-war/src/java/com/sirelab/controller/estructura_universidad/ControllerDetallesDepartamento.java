/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarDepartamentosBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Departamento;
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
public class ControllerDetallesDepartamento implements Serializable {

    @EJB
    GestionarDepartamentosBOInterface gestionarDepartamentosBO;

    private Departamento departamentoDetalles;
    private BigInteger idDepartamento;
    private String editarNombre, editarCodigo;
    private List<Facultad> listaFacultades;
    private Facultad editarFacultad;
    //
    private boolean validacionesNombre, validacionesFacultad, validacionesCodigo;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;

    public ControllerDetallesDepartamento() {
    }

    @PostConstruct
    public void init() {
        validacionesFacultad = true;
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionDepartamento() {
        validacionesFacultad = true;
        validacionesNombre = true;
        validacionesCodigo = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        departamentoDetalles = new Departamento();
        recibirIDDepartamentosDetalles(idDepartamento);
        return "administrar_departamentos";
    }

    public void asignarValoresVariablesDepartamento() {
        editarFacultad = departamentoDetalles.getFacultad();
        editarCodigo = departamentoDetalles.getCodigodepartamento();
        editarNombre = departamentoDetalles.getNombredepartamento();
        editarEstado = departamentoDetalles.getEstado();
        listaFacultades = gestionarDepartamentosBO.consultarFacultadesRegistradas();
    }

    public void recibirIDDepartamentosDetalles(BigInteger idDetalle) {
        this.idDepartamento = idDetalle;
        departamentoDetalles = gestionarDepartamentosBO.obtenerDepartamentoPorIDDepartamento(idDepartamento);
        asignarValoresVariablesDepartamento();
    }

    public void validarNombreDepartamento() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCodigoDepartamento() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty()) && (editarCodigo.trim().length() > 0)) {
            int tam = editarCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                    Departamento registro = gestionarDepartamentosBO.obtenerDepartamentoPorCodigo(editarCodigo);
                    if (null != registro) {
                        if (departamentoDetalles.getIddepartamento().equals(registro.getIddepartamento())) {
                            validacionesCodigo = true;
                        } else {
                            validacionesCodigo = false;
                            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El Codigo ya se encuentra registrado."));
                        }
                    }
                } else {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El Codigo ingresado es incorrecto."));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El Codigo es obligatorio."));
        }
    }

    public void validarFacultadDepartamento() {
        if (Utilidades.validarNulo(editarFacultad)) {
            validacionesFacultad = true;
        } else {
            validacionesFacultad = false;
            FacesContext.getCurrentInstance().addMessage("form:editarFacultad", new FacesMessage("La facultad es obligatoria."));
        }
    }

    private boolean validarCambioEstado() {
        boolean retorno = true;
        if (editarEstado == false) {
            Boolean validar = gestionarDepartamentosBO.validarCambioEstadoDepartamento(departamentoDetalles.getIddepartamento());
            if (null != validar) {
                if (validar == true) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesFacultad == false) {
            retorno = false;
        }
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionDepartamento() {
        if (validarResultadosValidacion() == true) {
            if (validarCambioEstado() == true) {
                almacenarModificacionDepartamentoEnSistema();
                recibirIDDepartamentosDetalles(this.idDepartamento);
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen carreras asociadas. Imposible cambiar el estado del departamento.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionDepartamentoEnSistema() {
        try {
            departamentoDetalles.setNombredepartamento(editarNombre);
            departamentoDetalles.setFacultad(editarFacultad);
            departamentoDetalles.setCodigodepartamento(editarCodigo);
            departamentoDetalles.setEstado(editarEstado);
            gestionarDepartamentosBO.modificarInformacionDepartamento(departamentoDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesDepartamento almacenarModificacionDepartamentoEnSistema:  " + e.toString());
            System.out.println("Error ControllerDetallesDepartamento almacenarModificacionDepartamentoEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public Departamento getDepartamentoDetalles() {
        return departamentoDetalles;
    }

    public void setDepartamentoDetalles(Departamento departamentoDetalles) {
        this.departamentoDetalles = departamentoDetalles;
    }

    public BigInteger getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(BigInteger idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public List<Facultad> getListaFacultades() {
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Facultad getEditarFacultad() {
        return editarFacultad;
    }

    public void setEditarFacultad(Facultad editarFacultad) {
        this.editarFacultad = editarFacultad;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getEditarCodigo() {
        return editarCodigo;
    }

    public void setEditarCodigo(String editarCodigo) {
        this.editarCodigo = editarCodigo;
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

}
