/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoGuiasLaboratorioBOInterface;
import com.sirelab.entidades.GuiaLaboratorio;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.utilidades.Utilidades;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesGuiaLaboratorio implements Serializable {

    @EJB
    GestionarRecursoGuiasLaboratorioBOInterface gestionarRecursoGuiasLaboratorioBO;

    private GuiaLaboratorio guiaLaboratorioDetalle;
    private BigInteger idGuiaLaboratorio;
    private List<Carrera> listaCarreras;
    private List<PlanEstudios> listaPlanEstudios;
    private List<AsignaturaPorPlanEstudio> listaAsignaturas;
    //
    private boolean activarModificacionPlanEstudios;
    private boolean activarModificacionAsignatura;
    //
    private String editarNombre, editarDescripcion;
    private Carrera editarCarrera;
    private PlanEstudios editarPlanEstudios;
    private AsignaturaPorPlanEstudio editarAsignatura;
    //
    private boolean validacionesNombre, validacionesDescripcion, validacionesArchivo;
    private boolean validacionesCarrera, validacionesPlanEstudios, validacionesAsignatura;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private final String pathArchivo = "C:\\SIRELAB\\Guias Laboratorio\\";
    private String rutaArchivo;
    private Part archivo;
    private boolean modificacionArchivo;
    private MensajesConstantes constantes;

    public ControllerDetallesGuiaLaboratorio() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        modificacionArchivo = false;
        activarModificacionPlanEstudios = true;
        activarModificacionAsignatura = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionGuiaLaboratorio() {
        validacionesPlanEstudios = true;
        validacionesDescripcion = true;
        validacionesCarrera = true;
        validacionesArchivo = true;
        modificacionArchivo = false;
        validacionesNombre = true;
        validacionesAsignatura = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        guiaLaboratorioDetalle = new GuiaLaboratorio();
        recibirIDGuiasLaboratorioDetalles(idGuiaLaboratorio);
        return "administrarguiaslaboratorio";
    }

    public void asignarValoresVariablesGuiaLaboratorio() {
        editarPlanEstudios = guiaLaboratorioDetalle.getAsignaturaporplanestudio().getPlanestudio();
        editarDescripcion = guiaLaboratorioDetalle.getDescripcion();
        editarCarrera = guiaLaboratorioDetalle.getAsignaturaporplanestudio().getPlanestudio().getCarrera();
        editarNombre = guiaLaboratorioDetalle.getNombreguia();
        editarAsignatura = guiaLaboratorioDetalle.getAsignaturaporplanestudio();
        listaCarreras = gestionarRecursoGuiasLaboratorioBO.consultarCarrerasRegistradas();
        //
        validacionesPlanEstudios = true;
        validacionesDescripcion = true;
        validacionesCarrera = true;
        validacionesArchivo = true;
        validacionesNombre = true;
        validacionesAsignatura = true;
        activarModificacionPlanEstudios = false;
        modificacionArchivo = false;
        if (Utilidades.validarNulo(editarCarrera)) {
            listaPlanEstudios = gestionarRecursoGuiasLaboratorioBO.consultarPlanesEstidoPorCarrera(editarCarrera.getIdcarrera());
        }
        activarModificacionAsignatura = false;
        if (Utilidades.validarNulo(editarPlanEstudios)) {
            listaAsignaturas = gestionarRecursoGuiasLaboratorioBO.consultarAsignaturaPorPlanEstudioPorIDPlan(editarPlanEstudios.getIdplanestudios());
        }
    }

    public void recibirIDGuiasLaboratorioDetalles(BigInteger idDetalle) {
        this.idGuiaLaboratorio = idDetalle;
        guiaLaboratorioDetalle = gestionarRecursoGuiasLaboratorioBO.obtenerGuiaLaboratorioPorID(idGuiaLaboratorio);
        asignarValoresVariablesGuiaLaboratorio();
    }

    public void actualizarCarreras() {
        if (Utilidades.validarNulo(editarCarrera)) {
            editarPlanEstudios = null;
            listaPlanEstudios = gestionarRecursoGuiasLaboratorioBO.consultarPlanesEstidoPorCarrera(editarCarrera.getIdcarrera());
            activarModificacionPlanEstudios = false;
            listaAsignaturas = null;
            editarAsignatura = null;
            activarModificacionAsignatura = true;
            validacionesCarrera = true;
        } else {
            validacionesPlanEstudios = false;
            validacionesCarrera = false;
            validacionesAsignatura = false;
            editarPlanEstudios = null;
            listaPlanEstudios = null;
            listaAsignaturas = null;
            editarAsignatura = null;
            activarModificacionPlanEstudios = true;
            activarModificacionAsignatura = true;
        }
    }

    public void actualizarPlanEstudios() {
        if (Utilidades.validarNulo(editarPlanEstudios)) {
            editarAsignatura = null;
            listaAsignaturas = gestionarRecursoGuiasLaboratorioBO.consultarAsignaturaPorPlanEstudioPorIDPlan(editarPlanEstudios.getIdplanestudios());
            activarModificacionAsignatura = false;
            validacionesPlanEstudios = true;
        } else {
            validacionesPlanEstudios = false;
            validacionesAsignatura = false;
            listaAsignaturas = null;
            editarAsignatura = null;
            activarModificacionAsignatura = true;
            FacesContext.getCurrentInstance().addMessage("form:editarPlanEstudios", new FacesMessage("La PlanEstudios es obligatoria."));
        }
    }

    public void actualizarAsignatura() {
        if (Utilidades.validarNulo(editarAsignatura)) {
            validacionesAsignatura = true;
        } else {
            validacionesAsignatura = false;
        }
    }

    public void validarNombreGuiaLaboratorio() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.RECURSO_GUIA_NOM));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.RECURSO_GUIA_NOM));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. " + constantes.RECURSO_GUIA_NOM));
        }
    }

    public void validarDescripcionGuiaLaboratorio() {
        if (Utilidades.validarNulo(editarDescripcion) && (!editarDescripcion.isEmpty()) && (editarDescripcion.trim().length() > 0)) {
            int tam = editarDescripcion.length();
            if (tam >= 20) {
                validacionesDescripcion = true;
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDescripcion", new FacesMessage("El tamaño minimo permitido es 20 caracteres. " + constantes.RECURSO_GUIA_DES));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDescripcion", new FacesMessage("La descripción ingresada es incorrecta. " + constantes.RECURSO_GUIA_DES));
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
                GuiaLaboratorio registro = gestionarRecursoGuiasLaboratorioBO.consultarGuiaLaboratorioPorUbicacion(rutaArchivoInicial);
                if (null == registro) {
                    validacionesArchivo = true;
                } else {
                    if (!registro.getIdguialaboratorio().equals(guiaLaboratorioDetalle.getIdguialaboratorio())) {
                        FacesContext.getCurrentInstance().addMessage("form:archivo", new FacesMessage("El archivo ya se encuentra almacenado en el sistema."));
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
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesArchivo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesCarrera == false) {
            retorno = false;
        }
        if (validacionesAsignatura == false) {
            retorno = false;
        }
        if (validacionesPlanEstudios == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionGuiaLaboratorio() {
        if (validarResultadosValidacion() == true) {
            almacenarModificacionGuiaLaboratorioEnSistema();
            recibirIDGuiasLaboratorioDetalles(this.idGuiaLaboratorio);
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionGuiaLaboratorioEnSistema() {
        try {
            guiaLaboratorioDetalle.setDescripcion(editarDescripcion);
            guiaLaboratorioDetalle.setNombreguia(editarNombre);
            guiaLaboratorioDetalle.setAsignaturaporplanestudio(editarAsignatura);
            if (modificacionArchivo == true) {
                cargarGuiaAServidor();
                guiaLaboratorioDetalle.setUbicacionguia(rutaArchivo);
            }
            gestionarRecursoGuiasLaboratorioBO.modificarGuiaLaboratorio(guiaLaboratorioDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarGuiasLaboratorio almacenarModificacionGuiaLaboratorioEnSistema:  " + e.toString());
            System.out.println("Error ControllerGestionarGuiasLaboratorio almacenarModificacionGuiaLaboratorioEnSistema : " + e.toString());
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
    public GuiaLaboratorio getGuiaLaboratorioDetalle() {
        return guiaLaboratorioDetalle;
    }

    public void setGuiaLaboratorioDetalle(GuiaLaboratorio guiaLaboratorioDetalle) {
        this.guiaLaboratorioDetalle = guiaLaboratorioDetalle;
    }

    public List<Carrera> getListaCarreras() {
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

    public boolean isActivarModificacionPlanEstudios() {
        return activarModificacionPlanEstudios;
    }

    public void setActivarModificacionPlanEstudios(boolean activarModificacionPlanEstudios) {
        this.activarModificacionPlanEstudios = activarModificacionPlanEstudios;
    }

    public boolean isActivarModificacionAsignatura() {
        return activarModificacionAsignatura;
    }

    public void setActivarModificacionAsignatura(boolean activarModificacionAsignatura) {
        this.activarModificacionAsignatura = activarModificacionAsignatura;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarDescripcion() {
        return editarDescripcion;
    }

    public void setEditarDescripcion(String editarDescripcion) {
        this.editarDescripcion = editarDescripcion;
    }

    public Carrera getEditarCarrera() {
        return editarCarrera;
    }

    public void setEditarCarrera(Carrera editarCarrera) {
        this.editarCarrera = editarCarrera;
    }

    public PlanEstudios getEditarPlanEstudios() {
        return editarPlanEstudios;
    }

    public void setEditarPlanEstudios(PlanEstudios editarPlanEstudios) {
        this.editarPlanEstudios = editarPlanEstudios;
    }

    public List<AsignaturaPorPlanEstudio> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<AsignaturaPorPlanEstudio> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public AsignaturaPorPlanEstudio getEditarAsignatura() {
        return editarAsignatura;
    }

    public void setEditarAsignatura(AsignaturaPorPlanEstudio editarAsignatura) {
        this.editarAsignatura = editarAsignatura;
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

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

}
