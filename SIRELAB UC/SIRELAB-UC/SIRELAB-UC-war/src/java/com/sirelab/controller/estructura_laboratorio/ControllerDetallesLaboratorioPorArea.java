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
public class ControllerDetallesLaboratorioPorArea implements Serializable {

    @EJB
    GestionarPlantaLaboratoriosPorAreasBOInterface gestionarPlantaLaboratoriosPorAreasBO;

    private LaboratoriosPorAreas laboratorioPorAreaDetalles;
    private BigInteger idLaboratorioPorArea;
    private List<Departamento> listaDepartamentos;
    private Departamento editarDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio editarLaboratorio;
    private boolean activarLaboratorio;
    private List<AreaProfundizacion> listaAreasProfundizacion;
    private AreaProfundizacion editarArea;
    //
    private boolean validacionesDepartamento, validacionesLaboratorio, validacionesArea;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesLaboratorioPorArea() {
    }

    @PostConstruct
    public void init() {
        activarLaboratorio = true;
        //
        validacionesArea = true;
        validacionesDepartamento = true;
        validacionesLaboratorio = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionLaboratoriosPorAreas() {
        validacionesArea = true;
        validacionesDepartamento = true;
        validacionesLaboratorio = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        laboratorioPorAreaDetalles = new LaboratoriosPorAreas();
        recibirIDLaboratoriosPorAreasDetalles(this.idLaboratorioPorArea);
        return "administrar_laboratoriosporareas";
    }

    public void asignarValoresVariablesLaboratoriosPorAreas() {
        editarArea = laboratorioPorAreaDetalles.getAreaprofundizacion();
        editarDepartamento = laboratorioPorAreaDetalles.getLaboratorio().getDepartamento();
        editarLaboratorio = laboratorioPorAreaDetalles.getLaboratorio();
        activarLaboratorio = false;
        listaAreasProfundizacion = gestionarPlantaLaboratoriosPorAreasBO.consultarAreasProfundizacionRegistradas();
        listaDepartamentos = gestionarPlantaLaboratoriosPorAreasBO.consultarDepartamentosRegistrados();
        if (Utilidades.validarNulo(editarDepartamento)) {
            listaLaboratorios = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratoriosPorIDDepartamento(editarDepartamento.getIddepartamento());
        }
    }

    public void recibirIDLaboratoriosPorAreasDetalles(BigInteger idRegistro) {
        this.idLaboratorioPorArea = idRegistro;
        laboratorioPorAreaDetalles = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratorioPorAreaPorID(idRegistro);
        asignarValoresVariablesLaboratoriosPorAreas();
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(editarDepartamento)) {
            editarLaboratorio = new Laboratorio();
            listaLaboratorios = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratoriosPorIDDepartamento(editarDepartamento.getIddepartamento());
            activarLaboratorio = false;
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            validacionesLaboratorio = false;
            editarLaboratorio = new Laboratorio();
            listaLaboratorios = null;
            activarLaboratorio = true;
            FacesContext.getCurrentInstance().addMessage("form:editarDepartamento", new FacesMessage("El campo Departamento es obligatorio."));
        }
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(editarLaboratorio)) {
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:editarLaboratorio", new FacesMessage("El campo Laboratorio es obligatorio."));
        }
    }

    public void actualizarAreaProfundizacion() {
        if (Utilidades.validarNulo(editarArea)) {
            validacionesArea = true;
        } else {
            validacionesArea = false;
            FacesContext.getCurrentInstance().addMessage("form:editarArea", new FacesMessage("El campo Area Profundización es obligatorio."));
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
        LaboratoriosPorAreas registro = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratorioPorAreaPorLabYArea(editarLaboratorio.getIdlaboratorio(), editarArea.getIdareaprofundizacion());
        if (null != registro) {
            if (!laboratorioPorAreaDetalles.getIdlaboratoriosporareas().equals(registro.getIdlaboratoriosporareas())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificarLaboratorioPorArea() {
        if (validarResultadosValidacion() == true) {
            if (Utilidades.validarNulo(editarLaboratorio)) {
                if (validarRegistroRepetido() == true) {
                    almacenarModificarLaboratorioPorAreaEnSistema();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "El registro ya se encuentra registrado en el sistema.";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Seleccione el laboratorio antes de continuar.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificarLaboratorioPorAreaEnSistema() {
        try {
            laboratorioPorAreaDetalles.setAreaprofundizacion(editarArea);
            laboratorioPorAreaDetalles.setLaboratorio(editarLaboratorio);
            gestionarPlantaLaboratoriosPorAreasBO.editarLaboratoriosPorAreas(laboratorioPorAreaDetalles);;
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaLaboratorios almacenarModificarLaboratorioPorAreaEnSistema:  " + e.toString());
            System.out.println("Error ControllerGestionarPlantaLaboratorios almacenarModificarLaboratorioPorAreaEnSistema : " + e.toString());

        }
    }

    //GET-SET
    public LaboratoriosPorAreas getLaboratorioPorAreaDetalles() {
        return laboratorioPorAreaDetalles;
    }

    public void setLaboratorioPorAreaDetalles(LaboratoriosPorAreas laboratorioPorAreaDetalles) {
        this.laboratorioPorAreaDetalles = laboratorioPorAreaDetalles;
    }

    public BigInteger getIdLaboratorioPorArea() {
        return idLaboratorioPorArea;
    }

    public void setIdLaboratorioPorArea(BigInteger idLaboratorioPorArea) {
        this.idLaboratorioPorArea = idLaboratorioPorArea;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getEditarDepartamento() {
        return editarDepartamento;
    }

    public void setEditarDepartamento(Departamento editarDepartamento) {
        this.editarDepartamento = editarDepartamento;
    }

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getEditarLaboratorio() {
        return editarLaboratorio;
    }

    public void setEditarLaboratorio(Laboratorio editarLaboratorio) {
        this.editarLaboratorio = editarLaboratorio;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public List<AreaProfundizacion> getListaAreasProfundizacion() {
        return listaAreasProfundizacion;
    }

    public void setListaAreasProfundizacion(List<AreaProfundizacion> listaAreasProfundizacion) {
        this.listaAreasProfundizacion = listaAreasProfundizacion;
    }

    public AreaProfundizacion getEditarArea() {
        return editarArea;
    }

    public void setEditarArea(AreaProfundizacion editarArea) {
        this.editarArea = editarArea;
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

}
