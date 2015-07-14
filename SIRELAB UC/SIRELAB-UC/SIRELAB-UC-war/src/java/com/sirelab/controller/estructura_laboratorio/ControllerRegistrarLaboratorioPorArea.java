/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaLaboratoriosPorAreasBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarLaboratorioPorArea implements Serializable {

    @EJB
    GestionarPlantaLaboratoriosPorAreasBOInterface gestionarPlantaLaboratoriosPorAreasBO;

    private List<Departamento> listaDepartamentos;
    private Departamento nuevoDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio nuevoLaboratorio;
    private boolean activarLaboratorio;
    private List<AreaProfundizacion> listaAreasProfundizacion;
    private AreaProfundizacion nuevoArea;
    //
    private boolean validacionesDepartamento, validacionesLaboratorio, validacionesArea;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerRegistrarLaboratorioPorArea() {
    }

    @PostConstruct
    public void init() {
        nuevoArea = null;
        nuevoDepartamento = null;
        nuevoLaboratorio = null;
        activarLaboratorio = true;
        //
        validacionesArea = false;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
        mensajeFormulario = "";
        BasicConfigurator.configure();
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(nuevoDepartamento)) {
            nuevoLaboratorio = new Laboratorio();
            listaLaboratorios = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratoriosPorIDDepartamento(nuevoDepartamento.getIddepartamento());
            activarLaboratorio = false;
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            validacionesLaboratorio = false;
            nuevoLaboratorio = new Laboratorio();
            listaLaboratorios = null;
            activarLaboratorio = true;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDepartamento", new FacesMessage("El campo Departamento es obligatorio."));
        }
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(nuevoLaboratorio)) {
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoLaboratorio", new FacesMessage("El campo Laboratorio es obligatorio."));
        }
    }

    public void actualizarAreaProfundizacion() {
        if (Utilidades.validarNulo(nuevoArea)) {
            validacionesArea = true;
        } else {
            validacionesArea = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoArea", new FacesMessage("El campo Area Profundización es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            retorno = false;
        }
        if (validacionesArea == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarRegistroRepetido() {
        boolean retorno = true;
        LaboratoriosPorAreas registro = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratorioPorAreaPorLabYArea(nuevoLaboratorio.getIdlaboratorio(), nuevoArea.getIdareaprofundizacion());
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoLaboratorioPorArea() {
        if (validarResultadosValidacion() == true) {
            if (validarRegistroRepetido() == true) {
                almacenarNuevoLaboratorioPorAreaEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                mensajeFormulario = "EL registro ya se encuentra registrado en el sistema.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenarNuevoLaboratorioPorAreaEnSistema() {
        try {
            LaboratoriosPorAreas laboratorioNuevo = new LaboratoriosPorAreas();
            laboratorioNuevo.setAreaprofundizacion(nuevoArea);
            laboratorioNuevo.setLaboratorio(nuevoLaboratorio);
            gestionarPlantaLaboratoriosPorAreasBO.crearLaboratoriosPorAreas(laboratorioNuevo);
            limpiarFormulario();
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaLaboratorios almacenarNuevoLaboratorioPorAreaEnSistema:  "+e.toString());
            System.out.println("Error ControllerGestionarPlantaLaboratorios almacenarNuevoLaboratorioPorAreaEnSistema : " + e.toString());

        }
    }

    public void limpiarFormulario() {
        nuevoArea = null;
        nuevoDepartamento = null;
        nuevoLaboratorio = null;
        activarLaboratorio = true;
        validacionesArea = false;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
        mensajeFormulario = "";
    }

    public void cancelarRegistroLaboratorioPorArea() {
        listaAreasProfundizacion = null;
        listaDepartamentos = null;
        nuevoArea = null;
        nuevoDepartamento = null;
        nuevoLaboratorio = null;
        activarLaboratorio = true;
        validacionesArea = false;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
        mensajeFormulario = "";
    }

    //GET-SET
    public List<Departamento> getListaDepartamentos() {
        if (listaDepartamentos == null) {
            listaDepartamentos = gestionarPlantaLaboratoriosPorAreasBO.consultarDepartamentosRegistrados();
        }
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getNuevoDepartamento() {
        return nuevoDepartamento;
    }

    public void setNuevoDepartamento(Departamento nuevoDepartamento) {
        this.nuevoDepartamento = nuevoDepartamento;
    }

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getNuevoLaboratorio() {
        return nuevoLaboratorio;
    }

    public void setNuevoLaboratorio(Laboratorio nuevoLaboratorio) {
        this.nuevoLaboratorio = nuevoLaboratorio;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public List<AreaProfundizacion> getListaAreasProfundizacion() {
        if (listaAreasProfundizacion == null) {
            listaAreasProfundizacion = gestionarPlantaLaboratoriosPorAreasBO.consultarAreasProfundizacionRegistradas();
        }
        return listaAreasProfundizacion;
    }

    public void setListaAreasProfundizacion(List<AreaProfundizacion> listaAreasProfundizacion) {
        this.listaAreasProfundizacion = listaAreasProfundizacion;
    }

    public AreaProfundizacion getNuevoArea() {
        return nuevoArea;
    }

    public void setNuevoArea(AreaProfundizacion nuevoArea) {
        this.nuevoArea = nuevoArea;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
