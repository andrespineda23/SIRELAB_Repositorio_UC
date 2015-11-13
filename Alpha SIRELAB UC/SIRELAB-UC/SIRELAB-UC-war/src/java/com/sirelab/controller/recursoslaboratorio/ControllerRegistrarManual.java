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
public class ControllerRegistrarManual implements Serializable {

    @EJB
    GestionarRecursoManualesBOInterface gestionarRecursoManualBO;

    private String nuevoNombre, nuevoTipo, nuevoUbicacion;
    private Part archivo;
    private boolean validacionesNombre, validacionesTipo, validacionesArchivo, validacionesUbicacion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private final String pathArchivo = "C:\\SIRELAB\\Manuales de Equipo\\";
    private String rutaArchivo;
    private boolean activarUbicacion, activarArchivo;
    private MensajesConstantes constantes;

    public ControllerRegistrarManual() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarArchivo = true;
        activarUbicacion = false;
        rutaArchivo = "";
        activarAceptar = false;
        nuevoTipo = "FISICO";
        nuevoUbicacion = null;
        nuevoNombre = null;
        archivo = null;
        validacionesTipo = false;
        validacionesNombre = false;
        validacionesArchivo = false;
        validacionesUbicacion = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
    }

    public void validarNombreManual() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.RECURSO_NOM_MAN));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 5 caracteres."+constantes.RECURSO_NOM_MAN));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio. "+constantes.RECURSO_NOM_MAN));
        }

    }

    public void validarUbicacionManual() {
        if (Utilidades.validarNulo(nuevoUbicacion) && (!nuevoUbicacion.isEmpty()) && (nuevoUbicacion.trim().length() > 0)) {
            int tam = nuevoUbicacion.length();
            if (tam >= 7) {
                validacionesUbicacion = true;
            } else {
                validacionesUbicacion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoUbicacion", new FacesMessage("El tamaño minimo permitido es 7 caracteres. "+constantes.RECURSO_INS_UBI));
            }
        } else {
            validacionesUbicacion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoUbicacion", new FacesMessage("El campo ubicación es obligatorio. "+constantes.RECURSO_INS_UBI));
        }
    }

    public void actualizarTipo() {
        if ("DIGITAL".equalsIgnoreCase(nuevoTipo)) {
            activarUbicacion = true;
            activarArchivo = false;
            validacionesUbicacion = true;
            nuevoUbicacion = null;
            validacionesArchivo = false;
            archivo = null;
        } else {
            validacionesArchivo = true;
            archivo = null;
            validacionesUbicacion = false;
            activarArchivo = true;
            activarUbicacion = false;
            nuevoUbicacion = null;
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
                Manual registro = gestionarRecursoManualBO.consultarManualPorUbicacion(rutaArchivoInicial);
                if (null == registro) {
                    validacionesArchivo = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("El archivo ya se encuentra almacenado en el sistema."));
                    validacionesArchivo = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("Formato incorrecto. Solo se permite archivos PDF."));
                validacionesArchivo = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("El archivo de guía laboratorio es obligatorio."));
            validacionesArchivo = false;
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesTipo == false) {
            retorno = false;
        }
        if (validacionesArchivo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesUbicacion == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoManual() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoManualEnSistema();
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
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

    private void almacenarNuevoManualEnSistema() {
        try {
            Manual guiaNuevo = new Manual();
            guiaNuevo.setNombremanual(nuevoNombre);
            guiaNuevo.setTipomanual(nuevoTipo);
            gestionarRecursoManualBO.crearManual(guiaNuevo);
            if (activarArchivo == false) {
                cargarGuiaAServidor();
                guiaNuevo.setUbicacionmanual(rutaArchivo);
            } else {
                guiaNuevo.setUbicacionmanual(nuevoUbicacion);
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarManual almacenarNuevoManualEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarManual almacenarNuevoManualEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        rutaArchivo = "";
        activarArchivo = true;
        activarUbicacion = false;
        nuevoTipo = "FISICO";
        archivo = null;
        nuevoNombre = null;
        nuevoUbicacion = null;
        validacionesTipo = false;
        validacionesArchivo = false;
        validacionesNombre = false;
        validacionesUbicacion = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroManual() {
        rutaArchivo = "";
        activarArchivo = true;
        activarUbicacion = false;
        nuevoTipo = "FISICO";
        archivo = null;
        nuevoNombre = null;
        nuevoUbicacion = null;
        validacionesTipo = false;
        validacionesNombre = false;
        validacionesArchivo = false;
        validacionesUbicacion = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
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
    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoTipo() {
        return nuevoTipo;
    }

    public void setNuevoTipo(String nuevoTipo) {
        this.nuevoTipo = nuevoTipo;
    }

    public String getNuevoUbicacion() {
        return nuevoUbicacion;
    }

    public void setNuevoUbicacion(String nuevoUbicacion) {
        this.nuevoUbicacion = nuevoUbicacion;
    }

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
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

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
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
