/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoManualesBOInterface;
import com.sirelab.entidades.Manual;
import com.sirelab.utilidades.Utilidades;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesManual implements Serializable {

    @EJB
    GestionarRecursoManualesBOInterface gestionarRecursoManualesBO;

    private Manual manualDetalle;
    private BigInteger idManual;
    private String editarNombre, editarTipo, editarUbicacion;
    //
    private boolean validacionesNombre, validacionesTipo, validacionesArchivo, validacionesUbicacion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private final String pathArchivo = "C:\\SIRELAB\\Manuales de Equipo\\";
    private String rutaArchivo;
    private Part archivo;
    private boolean modificacionArchivo;
    private boolean activarUbicacion, activarArchivo;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerDetallesManual() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        modificacionArchivo = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionManual() {
        validacionesTipo = true;
        validacionesArchivo = true;
        validacionesUbicacion = true;
        validacionesNombre = true;
        modificacionArchivo = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        manualDetalle = new Manual();
        recibirIDManualesDetalles(idManual);
        return "administrarguiaslaboratorio";
    }

    public void asignarValoresVariablesManual() {
        editarTipo = manualDetalle.getTipomanual();
        mensajeError = "";
        editarNombre = manualDetalle.getNombremanual();
        //
        validacionesTipo = true;
        validacionesUbicacion = true;
        validacionesArchivo = true;
        validacionesNombre = true;
        modificacionArchivo = false;
        if ("DIGITAL".equalsIgnoreCase(editarTipo)) {
            activarUbicacion = true;
            activarArchivo = false;
            editarUbicacion = null;
            archivo = null;
        } else {
            activarUbicacion = false;
            activarArchivo = true;
            archivo = null;
            editarUbicacion = manualDetalle.getUbicacionmanual();
        }
    }

    public void recibirIDManualesDetalles(BigInteger idDetalle) {
        this.idManual = idDetalle;
        manualDetalle = gestionarRecursoManualesBO.obtenerManualPorID(idManual);
        asignarValoresVariablesManual();
    }

    public void validarNombreManual() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.RECURSO_NOM_MAN));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres. "+constantes.RECURSO_NOM_MAN));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio "+constantes.RECURSO_NOM_MAN));
        }
    }

    public void validarUbicacionManual() {
        if (Utilidades.validarNulo(editarUbicacion) && (!editarUbicacion.isEmpty()) && (editarUbicacion.trim().length() > 0)) {
            int tam = editarUbicacion.length();
            if (tam >= 7) {
                validacionesUbicacion = true;
            } else {
                validacionesUbicacion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarUbicacion", new FacesMessage("El tamaño minimo permitido es 7 caracteres. "+constantes.RECURSO_INS_UBI));
            }
        } else {
            validacionesUbicacion = false;
            FacesContext.getCurrentInstance().addMessage("form:editarUbicacion", new FacesMessage("El campo ubicación es obligatorio. "+constantes.RECURSO_INS_UBI));
        }
    }

    public void actualizarTipo() {
        if ("DIGITAL".equalsIgnoreCase(editarTipo)) {
            activarUbicacion = true;
            activarArchivo = false;
            validacionesUbicacion = true;
            editarUbicacion = null;
            validacionesArchivo = false;
            archivo = null;
        } else {
            validacionesArchivo = true;
            archivo = null;
            validacionesUbicacion = false;
            activarArchivo = true;
            activarUbicacion = false;
            editarUbicacion = null;
        }
    }

    public void actualizarArchivoSeleccionado() {
        if (Utilidades.validarNulo(archivo)) {
            String filename = getFilename(archivo);
            String rutaArchivoInicial = pathArchivo + filename;
            String extension = "";
            int i = rutaArchivoInicial.lastIndexOf('.');
            if (i > 0) {
                extension = rutaArchivoInicial.substring(i + 1);
            }
            if ("pdf".equals(extension)) {
                Manual registro = gestionarRecursoManualesBO.consultarManualPorUbicacion(rutaArchivoInicial);
                if (null == registro) {
                    validacionesArchivo = true;
                } else {
                    if (!registro.getIdmanual().equals(manualDetalle.getIdmanual())) {
                        FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("El manual ya se encuentra almacenado en el sistema."));
                        validacionesArchivo = false;
                    } else {
                        validacionesArchivo = true;
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("Formato incorrecto. Solo se permite archivos PDF."));
                validacionesArchivo = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("El archivo de guía laboratorio es obligatorio."));
            validacionesArchivo = false;
        }
        modificacionArchivo = true;
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesTipo == false) {
            mensajeError = mensajeError + " - Tipo - ";
            retorno = false;
        }
        if (validacionesArchivo == false) {
            mensajeError = mensajeError + " - Archivo - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        if (validacionesUbicacion == false) {
            mensajeError = mensajeError + " - Ubicación - ";
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionManual() {
        if (validarResultadosValidacion() == true) {
            almacenarModificacionManualEnSistema();
            recibirIDManualesDetalles(this.idManual);
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: "+mensajeError;
        }
    }

    private void almacenarModificacionManualEnSistema() {
        try {
            manualDetalle.setNombremanual(editarNombre);
            manualDetalle.setTipomanual(editarTipo);
            if (modificacionArchivo == true) {
                cargarGuiaAServidor();
                manualDetalle.setTipomanual(rutaArchivo);
            } else {
                manualDetalle.setTipomanual(editarUbicacion);
            }
            gestionarRecursoManualesBO.editarManual(manualDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarManuales almacenarModificacionManualEnSistema:  " + e.toString(),e);
        }
    }

    private void cargarGuiaAServidor() throws FileNotFoundException, IOException {
        if (Utilidades.validarNulo(archivo)) {
            String filename = getFilename(archivo);
            rutaArchivo = pathArchivo + filename;
            String extension = "";
            int i = rutaArchivo.lastIndexOf('.');
            if (i > 0) {
                extension = rutaArchivo.substring(i + 1);
            }
            if ("pdf".equals(extension)) {
                InputStream is = archivo.getInputStream();
                FileOutputStream os = new FileOutputStream(rutaArchivo);
                int ch = is.read();
                while (ch != -1) {
                    os.write(ch);
                    ch = is.read();
                }
                os.close();
            }
        }
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    //GET-SET
    public Manual getManualDetalle() {
        return manualDetalle;
    }

    public void setManualDetalle(Manual manualDetalle) {
        this.manualDetalle = manualDetalle;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarTipo() {
        return editarTipo;
    }

    public void setEditarTipo(String editarTipo) {
        this.editarTipo = editarTipo;
    }

    public String getEditarUbicacion() {
        return editarUbicacion;
    }

    public void setEditarUbicacion(String editarUbicacion) {
        this.editarUbicacion = editarUbicacion;
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

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

    public boolean isModificacionArchivo() {
        return modificacionArchivo;
    }

    public void setModificacionArchivo(boolean modificacionArchivo) {
        this.modificacionArchivo = modificacionArchivo;
    }

    public boolean isActivarUbicacion() {
        return activarUbicacion;
    }

    public void setActivarUbicacion(boolean activarUbicacion) {
        this.activarUbicacion = activarUbicacion;
    }

    public boolean isActivarArchivo() {
        return activarArchivo;
    }

    public void setActivarArchivo(boolean activarArchivo) {
        this.activarArchivo = activarArchivo;
    }

}
