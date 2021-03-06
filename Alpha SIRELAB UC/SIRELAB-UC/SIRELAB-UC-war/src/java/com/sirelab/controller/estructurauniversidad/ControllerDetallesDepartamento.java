/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
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
    private MensajesConstantes constantes;
    private String mensajeError;
    private boolean activarCasillas;

    public ControllerDetallesDepartamento() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        validacionesFacultad = true;
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarCasillas = false;
        BasicConfigurator.configure();
    }

    public String restaurarInformacionDepartamento() {
        validacionesFacultad = true;
        validacionesNombre = true;
        validacionesCodigo = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        departamentoDetalles = new Departamento();
        if (activarCasillas == false) {
            recibirIDDepartamentosDetalles(idDepartamento);
        } else {
            activarCasillas = false;
            mensajeError = "";
            editarFacultad = null;
            editarCodigo = null;
            editarNombre = null;
            editarEstado = false;
            listaFacultades = null;
        }
        return "administrardepartamentos";
    }

    public void asignarValoresVariablesDepartamento() {
        mensajeError = "";
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
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.U_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. " + constantes.U_NOMBRE));
        }
    }

    public void validarCodigoDepartamento() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty()) && (editarCodigo.trim().length() > 0)) {
            int tam = editarCodigo.length();
            if (tam >= 4) {
                if (Utilidades.validarCaracteresAlfaNumericos(editarCodigo) == true) {
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
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El Codigo ingresado es incorrecto. " + constantes.U_CODIGO));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.U_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El Codigo es obligatorio. " + constantes.U_CODIGO));
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
        mensajeError = "";
        if (validacionesFacultad == false) {
            mensajeError = mensajeError + " - Facultad - ";
            retorno = false;
        }
        if (validacionesCodigo == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
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
                colorMensaje = "#FF0000";
                mensajeFormulario = "Existen carreras asociadas. Imposible cambiar el estado del departamento.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
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
            logger.error("Error ControllerDetallesDepartamento almacenarModificacionDepartamentoEnSistema:  " + e.toString(), e);
        }
    }

    public void eliminarDepartamento() {
        Integer carrera = gestionarDepartamentosBO.obtenerCarrerasAsociadas(idDepartamento);
        if (null != carrera) {
            if (carrera == 0) {
                boolean respuesta = gestionarDepartamentosBO.eliminarDepartamento(departamentoDetalles);
                if (respuesta == true) {
                    activarCasillas = true;
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "El registro ha sido eliminado con éxito. Regrese nuevamente a la pagina de consulta.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "El registro no pudo ser eliminado. Intente más tarde.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El registro no puede ser eliminado dado que tiene asociado al menos una carrera.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Ocurrio un error en la eliminación del registro. Intente más tarde.";
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

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

}
