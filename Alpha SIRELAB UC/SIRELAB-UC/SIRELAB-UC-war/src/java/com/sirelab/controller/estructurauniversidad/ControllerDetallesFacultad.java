/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructurauniversidad;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.universidad.GestionarFacultadesBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesFacultad implements Serializable {

    @EJB
    GestionarFacultadesBOInterface gestionarFacultadBO;

    private Facultad facultadDetalles;
    private BigInteger idFacultad;
    private String editarNombre, editarCodigo;
    private boolean validacionesNombre, validacionesCodigo;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private MensajesConstantes constantes;
    private String mensajeError;
    private boolean activarCasillas;

    public ControllerDetallesFacultad() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarCasillas = false;
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionFacultad() {
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        facultadDetalles = new Facultad();
        if (activarCasillas == false) {
            recibirIDFacultadesDetalles(idFacultad);
        } else {
            activarCasillas = false;
            facultadDetalles = null;
            idFacultad = null;
            editarEstado = false;
            editarCodigo = null;
            editarNombre = null;
            mensajeError = "";
        }
        return "administrarfacultades";
    }

    public void asignarValoresVariablesFacultad() {
        editarEstado = facultadDetalles.getEstado();
        editarCodigo = facultadDetalles.getCodigofacultad();
        editarNombre = facultadDetalles.getNombrefacultad();
        mensajeError = "";
    }

    public void recibirIDFacultadesDetalles(BigInteger idFacultadDetalle) {
        this.idFacultad = idFacultadDetalle;
        facultadDetalles = gestionarFacultadBO.obtenerFacultadPorIDFacultad(idFacultadDetalle);
        asignarValoresVariablesFacultad();
    }

    public void validarNombreFacultad() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 6) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.U_NOMBRE_ASIG));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.U_NOMBRE_ASIG));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. " + constantes.U_NOMBRE_ASIG));
        }

    }

    public void validarCodigoFacultad() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty()) && (editarCodigo.trim().length() > 0)) {
            int tam = editarCodigo.length();
            if (tam >= 4) {
                if (Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                    Facultad registro = gestionarFacultadBO.obtenerFacultadPorIDCodigo(editarCodigo);
                    if (null == registro) {
                        validacionesCodigo = true;
                    } else {
                        if (!facultadDetalles.getIdfacultad().equals(registro.getIdfacultad())) {
                            validacionesCodigo = false;
                            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado ya se encuentra registrado."));
                        } else {
                            validacionesCodigo = false;
                        }
                    }
                } else {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.U_CODIGO_FAC));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.U_CODIGO_FAC));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio. " + constantes.U_CODIGO_FAC));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
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

    private boolean validarCambioEstado() {
        boolean retorna = true;
        if (editarEstado == false) {
            Boolean cambioEstado = gestionarFacultadBO.validarCambioEstadoFacultad(facultadDetalles.getIdfacultad());
            if (null != cambioEstado) {
                if (cambioEstado == true) {
                    retorna = true;
                } else {
                    retorna = false;
                }
            }
        }
        return retorna;
    }

    public void registrarModificacionFacultad() {
        if (validarResultadosValidacion() == true) {
            if (validarCambioEstado() == true) {
                almacenarModificacionFacultadEnSistema();
                recibirIDFacultadesDetalles(this.idFacultad);
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "La facultad tiene departamentos asociados. Es imposible cambiar el estado.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    private void almacenarModificacionFacultadEnSistema() {
        try {
            facultadDetalles.setEstado(editarEstado);
            facultadDetalles.setCodigofacultad(editarCodigo);
            facultadDetalles.setNombrefacultad(editarNombre);
            gestionarFacultadBO.modificarInformacionFacultad(facultadDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesFacultad almacenarModificacionFacultadEnSistema : " + e.toString(), e);
        }
    }

    public void eliminarFacultad() {
        Integer departamentos = gestionarFacultadBO.obtenerDepartamentosAsociados(idFacultad);
        if (null != departamentos) {
            if (departamentos == 0) {
                boolean respuesta = gestionarFacultadBO.eliminarFacultad(facultadDetalles);
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
                mensajeFormulario = "El registro no puede ser eliminado dado que tiene asociado al menos un departamento.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Ocurrio un error en la eliminación del registro. Intente más tarde.";
        }
    }

    //GET-SET
    public Facultad getFacultadDetalles() {
        return facultadDetalles;
    }

    public void setFacultadDetalles(Facultad facultadDetalles) {
        this.facultadDetalles = facultadDetalles;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarCodigo() {
        return editarCodigo;
    }

    public void setEditarCodigo(String editarCodigo) {
        this.editarCodigo = editarCodigo;
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

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

}
