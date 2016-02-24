/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaLaboratoriosBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ControllerDetallesLaboratorio implements Serializable {

    @EJB
    GestionarPlantaLaboratoriosBOInterface gestionarPlantaLaboratoriosBO;
    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarValidadorTipoUsuario;

    private Laboratorio laboratorioDetalles;
    private BigInteger idLaboratorio;
    private List<Facultad> listaFacultades;
    private List<Departamento> listaDepartamentos;
    private boolean activarModificacionDepartamento;
    //
    private String editarNombre, editarCodigo;
    private Facultad editarFacultad;
    private Departamento editarDepartamento;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesFacultad, validacionesDepartamento;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarEstado;
    private MensajesConstantes constantes;
    private boolean activarModificaconFacultad;
    private boolean perfilConsulta;
    private TipoPerfil tipoPerfil;
    private UsuarioLogin usuarioLoginSistema;

    public ControllerDetallesLaboratorio() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarModificacionDepartamento = true;
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String registrarAreaProfundizacion() {
        restaurarInformacionLaboratorio();
        return "registrarlaboratorioporarea";
    }

    public String restaurarInformacionLaboratorio() {
        validacionesCodigo = true;
        validacionesDepartamento = true;
        validacionesFacultad = true;
        validacionesNombre = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        laboratorioDetalles = new Laboratorio();
        recibirIDLaboratoriosDetalles(idLaboratorio);
        return "administrarlaboratorios";
    }

    private Map<String, Object> validarSesionAdicionales(String nombre, String codigo) {
        Map<String, Object> lista = new HashMap<String, Object>();
        if ("DEPARTAMENTO".equalsIgnoreCase(nombre)) {
            Departamento registro = administrarValidadorTipoUsuario.obtenerDepartamentoPorCodigo(codigo);
            if (null != registro) {
                lista.put("DEPARTAMENTO", registro);
            }
        }
        if ("LABORATORIO".equalsIgnoreCase(nombre)) {
            Laboratorio registro = administrarValidadorTipoUsuario.obtenerLaboratorioPorCodigo(codigo);
            if (null != registro) {
                lista.put("LABORATORIO", registro);
            }
        }
        return lista;
    }

    private void cargarInformacionPerfil() {
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            perfilConsulta = true;
        } else {
            if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                boolean validarPerfilConsulta = validarSesionConsulta();
                if (validarPerfilConsulta == false) {
                    perfilConsulta = true;
                    Map<String, Object> datosPerfil = validarSesionAdicionales(tipoPerfil.getNombre(), tipoPerfil.getCodigoregistro());
                    if (null != datosPerfil) {
                        if (datosPerfil.containsKey("DEPARTAMENTO")) {
                            activarModificacionDepartamento = true;
                            activarModificaconFacultad = true;
                            editarDepartamento = (Departamento) datosPerfil.get("DEPARTAMENTO");
                            editarFacultad = editarDepartamento.getFacultad();
                            listaDepartamentos = new ArrayList<Departamento>();
                            listaFacultades = new ArrayList<Facultad>();
                            listaDepartamentos.add(editarDepartamento);
                            listaFacultades.add(editarFacultad);
                        }
                    } else {
                        activarModificacionDepartamento = false;
                        activarModificaconFacultad = false;
                    }
                } else {
                    perfilConsulta = false;
                }
            }
        }
    }

    private boolean validarSesionConsulta() {
        boolean retorno = false;
        tipoPerfil = administrarValidadorTipoUsuario.buscarTipoPerfilPorIDEncargado(usuarioLoginSistema.getIdUsuarioLogin());
        if (null != tipoPerfil) {
            if ("CONSULTA".equalsIgnoreCase(tipoPerfil.getNombre())) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void asignarValoresVariablesLaboratorio() {
        editarCodigo = laboratorioDetalles.getCodigolaboratorio();
        editarDepartamento = laboratorioDetalles.getDepartamento();
        editarEstado = laboratorioDetalles.getEstado();
        editarNombre = laboratorioDetalles.getNombrelaboratorio();
        editarFacultad = laboratorioDetalles.getDepartamento().getFacultad();
        activarModificacionDepartamento = false;
        listaFacultades = listaFacultades = gestionarPlantaLaboratoriosBO.consultarFacultadesRegistradas();
        if (Utilidades.validarNulo(editarFacultad)) {
            listaDepartamentos = gestionarPlantaLaboratoriosBO.consultarDepartamentosPorIDFacultad(editarFacultad.getIdfacultad());
        }
    }

    public void recibirIDLaboratoriosDetalles(BigInteger idRegistro) {
        this.idLaboratorio = idRegistro;
        laboratorioDetalles = gestionarPlantaLaboratoriosBO.obtenerLaboratorioPorIDLaboratorio(idLaboratorio);
        asignarValoresVariablesLaboratorio();
        cargarInformacionPerfil();
    }

    public void actualizarFacultades() {
        if (Utilidades.validarNulo(editarFacultad)) {
            editarDepartamento = null;
            listaDepartamentos = gestionarPlantaLaboratoriosBO.consultarDepartamentosPorIDFacultad(editarFacultad.getIdfacultad());
            activarModificacionDepartamento = false;
            validacionesFacultad = true;
        } else {
            validacionesDepartamento = false;
            validacionesFacultad = false;
            editarDepartamento = null;
            listaDepartamentos = null;
            activarModificacionDepartamento = true;
            FacesContext.getCurrentInstance().addMessage("form:editarFacultad", new FacesMessage("El campo Facultad es obligatorio."));
        }
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(editarDepartamento)) {
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDepartamento", new FacesMessage("El campo Departamento es obligatorio."));
        }
    }

    public void validarNombreLaboratorio() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. " + constantes.INVENTARIO_NOMBRE));
        }
    }

    public void validarCodigoLaboratorio() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty()) && (editarCodigo.trim().length() > 0)) {
            int tam = editarCodigo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.INVENTARIO_CODIGO_LAB));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_CODIGO_LAB));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio. " + constantes.INVENTARIO_CODIGO_LAB));
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
        Laboratorio registro = gestionarPlantaLaboratoriosBO.obtenerLaboratorioPorCodigo(editarCodigo);
        if (null != registro) {
            if (!laboratorioDetalles.getIdlaboratorio().equals(registro.getIdlaboratorio())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionLaboratorio() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarModificacionLaboratorioEnSistema();
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

    public void almacenarModificacionLaboratorioEnSistema() {
        try {
            laboratorioDetalles.setNombrelaboratorio(editarNombre);
            laboratorioDetalles.setCodigolaboratorio(editarCodigo);
            laboratorioDetalles.setDepartamento(editarDepartamento);
            laboratorioDetalles.setEstado(editarEstado);
            gestionarPlantaLaboratoriosBO.modificarInformacionLaboratorio(laboratorioDetalles);
            restaurarInformacionLaboratorio();
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaLaboratorios almacenarModificacionLaboratorioEnSistema:  " + e.toString(),e);
            logger.error("Error ControllerGestionarPlantaLaboratorios almacenarModificacionLaboratorioEnSistema : " + e.toString(),e);

        }
    }

    //GET-SET
    public Laboratorio getLaboratorioDetalles() {
        return laboratorioDetalles;
    }

    public void setLaboratorioDetalles(Laboratorio laboratorioDetalles) {
        this.laboratorioDetalles = laboratorioDetalles;
    }

    public BigInteger getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(BigInteger idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public List<Facultad> getListaFacultades() {
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

    public boolean isActivarModificacionDepartamento() {
        return activarModificacionDepartamento;
    }

    public void setActivarModificacionDepartamento(boolean activarModificacionDepartamento) {
        this.activarModificacionDepartamento = activarModificacionDepartamento;
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

    public Facultad getEditarFacultad() {
        return editarFacultad;
    }

    public void setEditarFacultad(Facultad editarFacultad) {
        this.editarFacultad = editarFacultad;
    }

    public Departamento getEditarDepartamento() {
        return editarDepartamento;
    }

    public void setEditarDepartamento(Departamento editarDepartamento) {
        this.editarDepartamento = editarDepartamento;
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

    public boolean isActivarModificaconFacultad() {
        return activarModificaconFacultad;
    }

    public void setActivarModificaconFacultad(boolean activarModificaconFacultad) {
        this.activarModificaconFacultad = activarModificaconFacultad;
    }

}
