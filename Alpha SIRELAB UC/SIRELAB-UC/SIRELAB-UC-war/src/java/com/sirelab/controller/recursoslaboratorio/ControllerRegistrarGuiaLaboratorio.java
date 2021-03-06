/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoGuiasLaboratorioBOInterface;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.GuiaLaboratorio;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class ControllerRegistrarGuiaLaboratorio implements Serializable {

    @EJB
    GestionarRecursoGuiasLaboratorioBOInterface gestionarRecursoGuiaLaboratorioBO;

    private String nuevoNombre, nuevoDescripcion, nuevoCodigo;
    private Part archivo;
    private List<Carrera> listaCarreras;
    private List<PlanEstudios> listaPlanEstudios;
    private List<AsignaturaPorPlanEstudio> listaAsignaturas;
    private Carrera nuevoCarrera;
    private PlanEstudios nuevoPlanEstudio;
    private boolean activarPlan;
    private AsignaturaPorPlanEstudio nuevoAsignatura;
    private boolean activarAsignatura;
    private boolean validacionesNombre, validacionesDescripcion, validacionesArchivo;
    private boolean validacionesCarrera, validacionesPlan, validacionesAsignatura;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private final String pathArchivo = "C:\\SIRELAB\\Guias Laboratorio\\";
    private String rutaArchivo;
    private String paginaAnterior;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarGuiaLaboratorio() {
    }

    @PostConstruct
    public void init() {
        rutaArchivo = "";
        activarAceptar = false;
        activarAsignatura = true;
        activarPlan = true;
        nuevoDescripcion = null;
        nuevoAsignatura = null;
        nuevoPlanEstudio = null;
        nuevoNombre = null;
        nuevoCodigo = null;
        nuevoCarrera = null;
        archivo = null;
        validacionesDescripcion = false;
        validacionesNombre = false;
        validacionesArchivo = false;
        validacionesCarrera = false;
        validacionesPlan = false;
        validacionesAsignatura = false;
        constantes = new MensajesConstantes();
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
        mensajeError = "";
    }

    public void recibirPaginaAnterior(String page) {
        this.paginaAnterior = page;
    }

    public String retornarPaginaAnterior() {
        if (null != paginaAnterior) {
            if (!paginaAnterior.isEmpty()) {
                return paginaAnterior;
            } else {
                return "administrarguiaslaboratorio";
            }
        } else {
            return "administrarguiaslaboratorio";
        }
    }

    public void validarNombreGuiaLaboratorio() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.RECURSO_GUIA_NOM));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.RECURSO_GUIA_NOM));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio. " + constantes.RECURSO_GUIA_NOM));
        }

    }

    public void validarDescripcionGuiaLaboratorio() {
        if (Utilidades.validarNulo(nuevoDescripcion) && (!nuevoDescripcion.isEmpty()) && (nuevoDescripcion.trim().length() > 0)) {
            int tam = nuevoDescripcion.length();
            if (tam >= 20) {
                validacionesDescripcion = true;
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("El tamaño minimo permitido es 20 caracteres. " + constantes.RECURSO_GUIA_DES));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("La descripción es obligatoria. " + constantes.RECURSO_GUIA_DES));
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
                validacionesArchivo = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("Formato incorrecto. Solo se permite archivos PDF."));
                validacionesArchivo = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("El archivo de guía laboratorio es obligatorio."));
            validacionesArchivo = false;
        }
    }

    public void actualizarCarreras() {
        if (Utilidades.validarNulo(nuevoCarrera)) {
            nuevoPlanEstudio = null;
            listaPlanEstudios = gestionarRecursoGuiaLaboratorioBO.consultarPlanesEstidoPorCarrera(nuevoCarrera.getIdcarrera());
            activarPlan = false;
            validacionesCarrera = true;
        } else {
            validacionesCarrera = false;
            validacionesPlan = false;
            validacionesAsignatura = false;

            listaPlanEstudios = null;
            listaAsignaturas = null;

            nuevoPlanEstudio = null;
            nuevoAsignatura = null;

            activarPlan = true;
            activarAsignatura = true;
        }
    }

    public void actualizarPlanEstudios() {
        if (Utilidades.validarNulo(nuevoPlanEstudio)) {
            nuevoAsignatura = null;
            listaAsignaturas = gestionarRecursoGuiaLaboratorioBO.consultarAsignaturaPorPlanEstudioPorIDPlan(nuevoPlanEstudio.getIdplanestudios());
            activarAsignatura = false;
            validacionesPlan = true;
        } else {
            validacionesPlan = false;
            validacionesAsignatura = false;
            listaAsignaturas = null;
            nuevoAsignatura = null;
            activarAsignatura = true;
        }
    }

    public void actualizarAsignaturas() {
        if (Utilidades.validarNulo(nuevoAsignatura)) {
            validacionesAsignatura = true;
        } else {
            validacionesAsignatura = false;
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesDescripcion == false) {
            mensajeError = mensajeError + " - Descripción - ";
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
        if (validacionesAsignatura == false) {
            mensajeError = mensajeError + " - Asignatura - ";
            retorno = false;
        }
        if (validacionesPlan == false) {
            mensajeError = mensajeError + " - Plan Estd. - ";
            retorno = false;
        }
        if (validacionesCarrera == false) {
            mensajeError = mensajeError + " - Carrera - ";
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoGuiaLaboratorio() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoGuiaLaboratorioEnSistema();
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Errores en el formulario ingresado. Errores: " + mensajeError;
        }
    }

    private void cargarGuiaAServidor() throws FileNotFoundException, IOException {
        if (Utilidades.validarNulo(archivo)) {
            String filename = getFilename(archivo);
            DateFormat formatter = new SimpleDateFormat("ddMMMMyyyy-hhmmss");
            Date date = Calendar.getInstance().getTime();
            String today = formatter.format(date);
            rutaArchivo = pathArchivo + today + "-" + filename;
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

    private void almacenarNuevoGuiaLaboratorioEnSistema() {
        try {
            cargarGuiaAServidor();
            GuiaLaboratorio guiaNuevo = new GuiaLaboratorio();
            guiaNuevo.setNombreguia(nuevoNombre);
            List<GuiaLaboratorio> totalidad = gestionarRecursoGuiaLaboratorioBO.consultarGuiasLaboratorioRegistradas();
            if (null != totalidad) {
                nuevoCodigo = "GL" + String.valueOf(totalidad.size());
            } else {
                nuevoCodigo = "GL" + String.valueOf(1);
            }
            guiaNuevo.setCodigoguia(nuevoCodigo);
            guiaNuevo.setDescripcion(nuevoDescripcion);
            guiaNuevo.setUbicacionguia(rutaArchivo);
            guiaNuevo.setAsignaturaporplanestudio(nuevoAsignatura);
            gestionarRecursoGuiaLaboratorioBO.crearGuiaLaboratorio(guiaNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarGuiaLaboratorio almacenarNuevoGuiaLaboratorioEnSistema:  " + e.toString(), e);
        }
    }

    public void limpiarFormulario() {
        rutaArchivo = "";
        activarAsignatura = true;
        activarPlan = true;
        nuevoDescripcion = null;
        nuevoCodigo = null;
        archivo = null;
        nuevoNombre = null;
        mensajeError = "";
        nuevoAsignatura = null;
        nuevoCarrera = null;
        nuevoPlanEstudio = null;
        validacionesDescripcion = false;
        validacionesArchivo = false;
        validacionesNombre = false;
        validacionesAsignatura = false;
        validacionesCarrera = false;
        validacionesPlan = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroGuiaLaboratorio() {
        rutaArchivo = "";
        listaAsignaturas = null;
        listaCarreras = null;
        listaPlanEstudios = null;
        nuevoDescripcion = null;
        archivo = null;
        nuevoNombre = null;
        nuevoAsignatura = null;
        nuevoCarrera = null;
        nuevoPlanEstudio = null;
        validacionesDescripcion = false;
        validacionesNombre = false;
        nuevoCodigo = null;
        validacionesArchivo = false;
        mensajeError = "";
        validacionesAsignatura = false;
        validacionesCarrera = false;
        validacionesPlan = false;
        mensajeFormulario = "N/A";
        activarAsignatura = true;
        activarPlan = true;
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

    public String getNuevoDescripcion() {
        return nuevoDescripcion;
    }

    public void setNuevoDescripcion(String nuevoDescripcion) {
        this.nuevoDescripcion = nuevoDescripcion;
    }

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

    public List<Carrera> getListaCarreras() {
        if (null == listaCarreras) {
            listaCarreras = gestionarRecursoGuiaLaboratorioBO.consultarCarrerasRegistradas();
        }
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public List<PlanEstudios> getListaPlanEstudios() {
        return listaPlanEstudios;
    }

    public void setListaPlanEstudios(List<PlanEstudios> listaPlanEstudios) {
        this.listaPlanEstudios = listaPlanEstudios;
    }

    public List<AsignaturaPorPlanEstudio> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<AsignaturaPorPlanEstudio> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public AsignaturaPorPlanEstudio getNuevoAsignatura() {
        return nuevoAsignatura;
    }

    public void setNuevoAsignatura(AsignaturaPorPlanEstudio nuevoAsignatura) {
        this.nuevoAsignatura = nuevoAsignatura;
    }

    public Carrera getNuevoCarrera() {
        return nuevoCarrera;
    }

    public void setNuevoCarrera(Carrera nuevoCarrera) {
        this.nuevoCarrera = nuevoCarrera;
    }

    public PlanEstudios getNuevoPlanEstudio() {
        return nuevoPlanEstudio;
    }

    public void setNuevoPlanEstudio(PlanEstudios nuevoPlanEstudio) {
        this.nuevoPlanEstudio = nuevoPlanEstudio;
    }

    public boolean isActivarPlan() {
        return activarPlan;
    }

    public void setActivarPlan(boolean activarPlan) {
        this.activarPlan = activarPlan;
    }

    public boolean isActivarAsignatura() {
        return activarAsignatura;
    }

    public void setActivarAsignatura(boolean activarAsignatura) {
        this.activarAsignatura = activarAsignatura;
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

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
    }

}
