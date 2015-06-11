/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.GestionarPlantaLaboratoriosBOInterface;
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

    public ControllerRegistrarLaboratorio() {
    }

    @PostConstruct
    public void init() {
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
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCodigoLaboratorio() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
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
        Laboratorio registro = gestionarPlantaLaboratoriosBO.obtenerLaboratorioPorCodigoYDepartamento(nuevoCodigo, nuevoDepartamento.getIddepartamento());
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoLaboratorio() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoLaboratorioEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                mensajeFormulario = "El codigo ya esta registrado con el departamento seleccionado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenarNuevoLaboratorioEnSistema() {
        try {
            Laboratorio laboratorioNuevo = new Laboratorio();
            laboratorioNuevo.setNombrelaboratorio(nuevoNombre);
            laboratorioNuevo.setCodigolaboratorio(nuevoCodigo);
            laboratorioNuevo.setDepartamento(nuevoDepartamento);
            gestionarPlantaLaboratoriosBO.crearNuevaLaboratorio(laboratorioNuevo);
            limpiarFormulario();
        } catch (Exception e) {
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
        mensajeFormulario = "";
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

}
