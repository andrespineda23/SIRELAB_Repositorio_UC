/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.variables;

import com.sirelab.bo.interfacebo.GestionarVariablePerfilesPorEncargadoBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.PerfilPorEncargado;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarPerfilPorEncargado implements Serializable {

    @EJB
    GestionarVariablePerfilesPorEncargadoBOInterface gestionarVariablePerfilesPorEncargadoBO;

    private List<TipoPerfil> listaTiposPerfiles;
    private TipoPerfil tipoPerfilSeleccionado;
    private List<Departamento> listaDepartamentos;
    private Departamento departamentoSeleccionado;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio laboratorioSeleccionado;
    private boolean activarDepartamento, activarLaboratorio;
    private boolean validacionesTipo, validacionesDepartamento, validacionesLaboratorio;
    private String mensajeFormulario;

    public ControllerRegistrarPerfilPorEncargado() {
    }

    @PostConstruct
    public void init() {
        listaTiposPerfiles = gestionarVariablePerfilesPorEncargadoBO.consultarTiposPerfilesRegistrados();
        listaDepartamentos = null;
        listaLaboratorios = null;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
        validacionesTipo = false;
        mensajeFormulario = "";
    }

    public void actualizarTipoPerfil() {
        if (Utilidades.validarNulo(tipoPerfilSeleccionado)) {
            validacionesTipo = true;
            if ("DEPARTAMENTO".equalsIgnoreCase(tipoPerfilSeleccionado.getNombre())) {
                listaDepartamentos = gestionarVariablePerfilesPorEncargadoBO.consultarDepartamentosRegistrados();
                activarDepartamento = false;
                activarLaboratorio = true;
            } else {
                listaLaboratorios = gestionarVariablePerfilesPorEncargadoBO.consultarLaboratoriosRegistrados();
                activarDepartamento = true;
                activarLaboratorio = false;
            }
        } else {
            activarDepartamento = true;
            activarLaboratorio = true;
            validacionesDepartamento = false;
            validacionesLaboratorio = false;
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:tipoPerfilSeleccionado", new FacesMessage("El campo Tipo Perfil es obligatorio."));
        }
    }

    public void actualizarDepartamento() {
        if (Utilidades.validarNulo(departamentoSeleccionado)) {
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            FacesContext.getCurrentInstance().addMessage("form:departamentoSeleccionado", new FacesMessage("El campo Departamento es obligatorio."));
        }
    }

    public void actualizarLaboratorio() {
        if (Utilidades.validarNulo(laboratorioSeleccionado)) {
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:laboratorioSeleccionado", new FacesMessage("El campo Departamento es obligatorio."));
        }
    }

    private boolean obtenerResultadoValidacion() {
        boolean retorno = true;
        if (validacionesTipo == false) {
            retorno = false;
        } else {
            if (activarDepartamento == false) {
                if (validacionesDepartamento == false) {
                    retorno = false;
                }
            } else {
                if (validacionesLaboratorio == false) {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void registrarNuevoPerfilPorEncargado() {
        if (obtenerResultadoValidacion() == true) {
            almacenarPerfilPorEncargadoEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarPerfilPorEncargadoEnSistema() {
        PerfilPorEncargado nuevo = new PerfilPorEncargado();
        nuevo.setTipoperfil(tipoPerfilSeleccionado);
        if (activarDepartamento == false) {
            nuevo.setIndicetabla(departamentoSeleccionado.getIddepartamento());
        } else {
            nuevo.setIndicetabla(laboratorioSeleccionado.getIdlaboratorio());
        }
        gestionarVariablePerfilesPorEncargadoBO.crearPerfilPorEncargado(nuevo);
        cancelarRegistroPerfilPorEncargado();
    }

    public void cancelarRegistroPerfilPorEncargado() {
        listaDepartamentos = null;
        listaLaboratorios = null;
        activarDepartamento = true;
        activarLaboratorio = true;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
        validacionesTipo = false;
        tipoPerfilSeleccionado = null;
        departamentoSeleccionado = null;
        laboratorioSeleccionado = null;
    }

    //GET-SET
    public List<TipoPerfil> getListaTiposPerfiles() {
        return listaTiposPerfiles;
    }

    public void setListaTiposPerfiles(List<TipoPerfil> listaTiposPerfiles) {
        this.listaTiposPerfiles = listaTiposPerfiles;
    }

    public TipoPerfil getTipoPerfilSeleccionado() {
        return tipoPerfilSeleccionado;
    }

    public void setTipoPerfilSeleccionado(TipoPerfil tipoPerfilSeleccionado) {
        this.tipoPerfilSeleccionado = tipoPerfilSeleccionado;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(Departamento departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getLaboratorioSeleccionado() {
        return laboratorioSeleccionado;
    }

    public void setLaboratorioSeleccionado(Laboratorio laboratorioSeleccionado) {
        this.laboratorioSeleccionado = laboratorioSeleccionado;
    }

    public boolean isActivarDepartamento() {
        return activarDepartamento;
    }

    public void setActivarDepartamento(boolean activarDepartamento) {
        this.activarDepartamento = activarDepartamento;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
