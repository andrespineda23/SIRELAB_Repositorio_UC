/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaLaboratoriosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
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
public class ControllerRegistrarLaboratorio implements Serializable {

    @EJB
    GestionarPlantaLaboratoriosBOInterface gestionarPlantaLaboratoriosBO;

    private List<Facultad> listaFacultades;
    private List<Departamento> listaDepartamentos;
    private boolean activarNuevoDepartamento;
    //
    private String nuevoNombre, nuevoCodigo;
    private Facultad nuevoFacultad;
    private Departamento nuevoDepartamento;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesFacultad, validacionesDepartamento;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarLaboratorio() {
    }

    @PostConstruct
    public void init() {
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        activarNuevoDepartamento = true;
        nuevoCodigo = null;
        nuevoDepartamento = null;
        nuevoNombre = null;
        nuevoFacultad = null;
        //
        validacionesCodigo = false;
        validacionesDepartamento = false;
        validacionesFacultad = false;
        validacionesNombre = false;
        BasicConfigurator.configure();
    }

    public void actualizarFacultades() {
        if (Utilidades.validarNulo(nuevoFacultad)) {
            nuevoDepartamento = null;
            listaDepartamentos = gestionarPlantaLaboratoriosBO.consultarDepartamentosPorIDFacultad(nuevoFacultad.getIdfacultad());
            activarNuevoDepartamento = false;
            validacionesFacultad = true;
        } else {
            validacionesDepartamento = false;
            validacionesFacultad = false;
            nuevoDepartamento = null;
            listaDepartamentos = null;
            activarNuevoDepartamento = true;
            FacesContext.getCurrentInstance().addMessage("form:nuevoFacultad", new FacesMessage("El campo Facultad es obligatorio."));
        }
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(nuevoDepartamento)) {
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDepartamento", new FacesMessage("El campo Departamento es obligatorio."));
        }
    }

    public void validarNombreLaboratorio() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo es 4 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCodigoLaboratorio() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty()) && (nuevoCodigo.trim().length() > 0)) {
            int tam = nuevoCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El tamaño minimo es 4 caracteres."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesFacultad == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        Laboratorio registro = gestionarPlantaLaboratoriosBO.obtenerLaboratorioPorCodigo(nuevoCodigo);
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoLaboratorio() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoLaboratorioEnSistema();
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ya esta registrado con el departamento seleccionado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenarNuevoLaboratorioEnSistema() {
        try {
            Laboratorio laboratorioNuevo = new Laboratorio();
            laboratorioNuevo.setNombrelaboratorio(nuevoNombre);
            laboratorioNuevo.setEstado(true);
            laboratorioNuevo.setCodigolaboratorio(nuevoCodigo);
            laboratorioNuevo.setDepartamento(nuevoDepartamento);
            gestionarPlantaLaboratoriosBO.crearNuevaLaboratorio(laboratorioNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaLaboratorios almacenarNuevoLaboratorioEnSistema:  " + e.toString());
            System.out.println("Error ControllerGestionarPlantaLaboratorios almacenarNuevoLaboratorioEnSistema : " + e.toString());

        }
    }

    public void limpiarFormulario() {
        activarNuevoDepartamento = true;
        nuevoCodigo = null;
        nuevoDepartamento = null;
        nuevoNombre = null;
        nuevoFacultad = null;
        //
        validacionesCodigo = false;
        validacionesDepartamento = false;
        validacionesFacultad = false;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroLaboratorio() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        listaFacultades = null;
        listaDepartamentos = null;
        activarNuevoDepartamento = true;
        nuevoCodigo = null;
        nuevoDepartamento = null;
        nuevoNombre = null;
        nuevoFacultad = null;
        //
        validacionesCodigo = false;
        validacionesDepartamento = false;
        validacionesFacultad = false;
        validacionesNombre = false;
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
    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = gestionarPlantaLaboratoriosBO.consultarFacultadesRegistradas();
        }
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public boolean isActivarNuevoDepartamento() {
        return activarNuevoDepartamento;
    }

    public void setActivarNuevoDepartamento(boolean activarNuevoDepartamento) {
        this.activarNuevoDepartamento = activarNuevoDepartamento;
    }

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
    }

    public Facultad getNuevoFacultad() {
        return nuevoFacultad;
    }

    public void setNuevoFacultad(Facultad nuevoFacultad) {
        this.nuevoFacultad = nuevoFacultad;
    }

    public Departamento getNuevoDepartamento() {
        return nuevoDepartamento;
    }

    public void setNuevoDepartamento(Departamento nuevoDepartamento) {
        this.nuevoDepartamento = nuevoDepartamento;
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

}
