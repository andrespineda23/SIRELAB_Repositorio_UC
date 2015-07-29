/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaModulosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.utilidades.UsuarioLogin;
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
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesModulo implements Serializable {

    @EJB
    GestionarPlantaModulosBOInterface gestionarPlantaModulosBO;

    private ModuloLaboratorio moduloLaboratorioDetalles;
    private BigInteger idModuloLaboratorio;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private List<Departamento> listaDepartamentos;
    private List<Laboratorio> listaLaboratorios;
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private Departamento departamentoModuloLaboratorio;
    private LaboratoriosPorAreas laboratorioPorAreaModuloLaboratorio;
    private List<SalaLaboratorio> listaSalasLaboratorio;
    private SalaLaboratorio salaModuloLaboratorio;
    private Laboratorio laboratorioModuloLaboratorio;
    private boolean activarLaboratorio;
    private boolean activarSala;
    private boolean activarLabPorArea;
    private String detalleModuloLaboratorio, codigoModuloLaboratorio;
    private String costoModuloLaboratorio, capacidadModuloLaboratorio, inversionModuloLaboratorio;
    //
    private boolean validacionesCodigo, validacionesDetalle, validacionesCapacidad, validacionesCosto, validacionesInversion;
    private boolean validacionesLaboratorioPorArea, validacionesSala, validacionesLaboratorio, validacionesDepartamento;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesModulo() {
    }

    @PostConstruct
    public void init() {
        activarSala = true;
        activarLaboratorio = true;
        activarLabPorArea = true;
        validacionesDetalle = true;
        validacionesCodigo = true;
        validacionesLaboratorio = true;
        validacionesDepartamento = true;
        validacionesLaboratorioPorArea = true;
        validacionesSala = true;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        //
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            disabledEditar = false;
        }
        BasicConfigurator.configure();
    }

    public void recibirIDModuloLaboratorioDetalles(BigInteger idModuloLaboratorio) {
        this.idModuloLaboratorio = idModuloLaboratorio;
        moduloLaboratorioDetalles = gestionarPlantaModulosBO.obtenerModuloLaboratorioPorIDModuloLaboratorio(idModuloLaboratorio);
        if (moduloLaboratorioDetalles.getEstadomodulo() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesModuloLaboratorio();
    }

    public void asignarValoresVariablesModuloLaboratorio() {
        detalleModuloLaboratorio = moduloLaboratorioDetalles.getDetallemodulo();
        codigoModuloLaboratorio = moduloLaboratorioDetalles.getCodigomodulo();
        costoModuloLaboratorio = moduloLaboratorioDetalles.getCostoalquiler().toString();
        capacidadModuloLaboratorio = moduloLaboratorioDetalles.getCapacidadmodulo().toString();
        inversionModuloLaboratorio = moduloLaboratorioDetalles.getCostomodulo().toString();
        salaModuloLaboratorio = moduloLaboratorioDetalles.getSalalaboratorio();
        laboratorioModuloLaboratorio = moduloLaboratorioDetalles.getSalalaboratorio().getLaboratoriosporareas().getLaboratorio();
        departamentoModuloLaboratorio = moduloLaboratorioDetalles.getSalalaboratorio().getLaboratoriosporareas().getLaboratorio().getDepartamento();
        laboratorioPorAreaModuloLaboratorio = moduloLaboratorioDetalles.getSalalaboratorio().getLaboratoriosporareas();
        listaDepartamentos = gestionarPlantaModulosBO.consultarDepartamentosRegistrados();
        listaLaboratorios = gestionarPlantaModulosBO.consultarLaboratoriosPorIDDepartamento(departamentoModuloLaboratorio.getIddepartamento());
        listaLaboratoriosPorAreas = gestionarPlantaModulosBO.consultarLaboratoriosPorAreasPorLaboratorio(laboratorioModuloLaboratorio.getIdlaboratorio());
        listaSalasLaboratorio = gestionarPlantaModulosBO.consultarSalasLaboratorioPorIDArea(laboratorioPorAreaModuloLaboratorio.getIdlaboratoriosporareas());
    }

    public void activarEditarRegistro() {
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        activarSala = false;
        activarLaboratorio = false;
        activarLabPorArea = false;
    }

    public String restaurarInformacionModuloLaboratorio() {
        moduloLaboratorioDetalles = new ModuloLaboratorio();
        moduloLaboratorioDetalles = gestionarPlantaModulosBO.obtenerModuloLaboratorioPorIDModuloLaboratorio(idModuloLaboratorio);
        if (moduloLaboratorioDetalles.getEstadomodulo() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        activarSala = true;
        activarLaboratorio = true;
        activarLabPorArea = true;
        listaSalasLaboratorio = null;
        listaDepartamentos = null;
        listaLaboratorios = null;
        listaLaboratoriosPorAreas = null;
        validacionesDetalle = true;
        validacionesCodigo = true;
        validacionesLaboratorio = true;
        validacionesLaboratorio = true;
        validacionesLaboratorioPorArea = true;
        validacionesSala = true;
        validacionesCapacidad = true;
        validacionesCosto = true;
        validacionesInversion = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        asignarValoresVariablesModuloLaboratorio();
        return "administrar_modulos";
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(departamentoModuloLaboratorio)) {
            laboratorioModuloLaboratorio = new Laboratorio();
            listaLaboratorios = gestionarPlantaModulosBO.consultarLaboratoriosPorIDDepartamento(departamentoModuloLaboratorio.getIddepartamento());
            activarLaboratorio = false;
            validacionesDepartamento = true;
        } else {
            validacionesLaboratorio = false;
            validacionesLaboratorioPorArea = false;
            validacionesSala = false;
            validacionesDepartamento = false;

            laboratorioPorAreaModuloLaboratorio = new LaboratoriosPorAreas();
            salaModuloLaboratorio = new SalaLaboratorio();
            laboratorioModuloLaboratorio = new Laboratorio();

            listaLaboratoriosPorAreas = null;
            listaSalasLaboratorio = null;
            listaLaboratorios = null;

            activarLabPorArea = true;
            activarLaboratorio = true;
            activarSala = true;
            FacesContext.getCurrentInstance().addMessage("form:departamentoModuloLaboratorio", new FacesMessage("El campo Departamento es obligatorio.."));
        }
        modificacionesRegistroModuloLaboratorio();
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(laboratorioModuloLaboratorio)) {
            laboratorioPorAreaModuloLaboratorio = new LaboratoriosPorAreas();
            listaLaboratoriosPorAreas = gestionarPlantaModulosBO.consultarLaboratoriosPorAreasPorLaboratorio(laboratorioModuloLaboratorio.getIdlaboratorio());
            activarLabPorArea = false;
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            validacionesLaboratorioPorArea = false;
            validacionesSala = false;
            laboratorioPorAreaModuloLaboratorio = new LaboratoriosPorAreas();
            salaModuloLaboratorio = new SalaLaboratorio();
            listaLaboratoriosPorAreas = null;
            listaSalasLaboratorio = null;
            activarLabPorArea = true;
            activarSala = true;
            FacesContext.getCurrentInstance().addMessage("form:laboratorioModuloLaboratorio", new FacesMessage("El campo Laboratorio es obligatorio.."));
        }
        modificacionesRegistroModuloLaboratorio();
    }

    public void actualizarLaboratoriosPorAreas() {
        if (Utilidades.validarNulo(laboratorioPorAreaModuloLaboratorio)) {
            salaModuloLaboratorio = new SalaLaboratorio();
            listaSalasLaboratorio = gestionarPlantaModulosBO.consultarSalasLaboratoriosPorIDLaboratorioArea(laboratorioPorAreaModuloLaboratorio.getIdlaboratoriosporareas());
            activarSala = false;
            validacionesLaboratorioPorArea = true;
        } else {
            validacionesLaboratorioPorArea = false;
            validacionesSala = false;
            salaModuloLaboratorio = new SalaLaboratorio();
            listaSalasLaboratorio = null;
            activarSala = true;
            FacesContext.getCurrentInstance().addMessage("form:laboratorioPorAreaModuloLaboratorio", new FacesMessage("El campo Area Profundización es obligatorio.."));
        }
        modificacionesRegistroModuloLaboratorio();
    }

    public void actualizarSalasLaboratorio() {
        if (Utilidades.validarNulo(salaModuloLaboratorio)) {
            validacionesSala = true;
        } else {
            validacionesSala = false;
            FacesContext.getCurrentInstance().addMessage("form:salaModuloLaboratorio", new FacesMessage("El campo Sala Laboratorio es obligatorio.."));
        }
        modificacionesRegistroModuloLaboratorio();
    }

    public void validarDetalleModulo() {
        if (Utilidades.validarNulo(detalleModuloLaboratorio) && (!detalleModuloLaboratorio.isEmpty())) {
            if (!Utilidades.validarCaracterString(detalleModuloLaboratorio)) {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:detalleModuloLaboratorio", new FacesMessage("El detalle ingresado es incorrecto."));
            } else {
                validacionesDetalle = true;
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:detalleModuloLaboratorio", new FacesMessage("El detalle es obligatorio."));
        }
        modificacionesRegistroModuloLaboratorio();
    }

    public void validarCodigoModulo() {
        if (Utilidades.validarNulo(codigoModuloLaboratorio) && (!codigoModuloLaboratorio.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(codigoModuloLaboratorio)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:codigoModuloLaboratorio", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:codigoModuloLaboratorio", new FacesMessage("El codigo es obligatorio."));
        }
        modificacionesRegistroModuloLaboratorio();
    }

    public void validarCostoAlquilerModulo() {
        if (Utilidades.validarNulo(costoModuloLaboratorio) && (!costoModuloLaboratorio.isEmpty())) {
            if (Utilidades.isNumber(costoModuloLaboratorio)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:costoModuloLaboratorio", new FacesMessage("El costo se encuentra incorrecto."));
            }
        }
        modificacionesRegistroModuloLaboratorio();
    }

    public void validarCapacidadModulo() {
        if (Utilidades.validarNulo(capacidadModuloLaboratorio) && (!capacidadModuloLaboratorio.isEmpty())) {
            if ((Utilidades.isNumber(capacidadModuloLaboratorio)) == false) {
                validacionesCapacidad = false;
                FacesContext.getCurrentInstance().addMessage("form:capacidadModuloLaboratorio", new FacesMessage("La capacidad ingresada se encuentra incorrecta."));
            } else {
                validacionesCapacidad = true;
            }
        }
        modificacionesRegistroModuloLaboratorio();
    }

    public void validarInversionModulo() {
        if (Utilidades.validarNulo(inversionModuloLaboratorio) && (!inversionModuloLaboratorio.isEmpty())) {
            if ((Utilidades.isNumber(inversionModuloLaboratorio)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:inversionModuloLaboratorio", new FacesMessage("El valor de inversión se encuentra incorrecto."));
            } else {
                validacionesInversion = true;
            }
        }
        modificacionesRegistroModuloLaboratorio();
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesCapacidad == false) {
            retorno = false;
        }
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesDetalle == false) {
            retorno = false;
        }
        if (validacionesInversion == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            retorno = false;
        }
        if (validacionesLaboratorioPorArea == false) {
            retorno = false;
        }
        if (validacionesSala == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        ModuloLaboratorio modulo = gestionarPlantaModulosBO.obtenerModuloLaboratorioPorCodigoYSala(codigoModuloLaboratorio, salaModuloLaboratorio.getIdsalalaboratorio());
        if (null != modulo) {
            if (!moduloLaboratorioDetalles.getIdmodulolaboratorio().equals(modulo.getIdmodulolaboratorio())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void almacenarModificacionesModulo() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                if (validarCodigoRepetido() == true) {
                    modificarInformacionModuloLaboratorio();
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "El codigo ya esta registrado con el edificio y laboratorio por area seleccionado.";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            restaurarInformacionModuloLaboratorio();
            colorMensaje = "black";
            mensajeFormulario = "No se presento algun cambio en el registro. No se realizo ningun proceso de almacenamiento.";
        }
    }

    public void modificarInformacionModuloLaboratorio() {
        try {
            moduloLaboratorioDetalles.setCodigomodulo(codigoModuloLaboratorio);
            moduloLaboratorioDetalles.setCostomodulo(new BigInteger(inversionModuloLaboratorio));
            moduloLaboratorioDetalles.setDetallemodulo(detalleModuloLaboratorio);
            moduloLaboratorioDetalles.setCostoalquiler(new BigInteger(costoModuloLaboratorio));
            moduloLaboratorioDetalles.setCapacidadmodulo(Integer.valueOf(capacidadModuloLaboratorio).intValue());
            moduloLaboratorioDetalles.setSalalaboratorio(salaModuloLaboratorio);
            gestionarPlantaModulosBO.modificarInformacionModuloLaboratorio(moduloLaboratorioDetalles);
            restaurarInformacionModuloLaboratorio();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlantaModulo almacenarNuevoModuloLaboratorioEnSistema:  " + e.toString());
            System.out.println("Error ControllerDetallesPlantaModulo almacenarNuevoModuloLaboratorioEnSistema : " + e.toString());

        }
    }

    public void modificacionesRegistroModuloLaboratorio() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    public void activarModuloLaboratorio() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(true);
                moduloLaboratorioDetalles.setEstadomodulo(bool);
                gestionarPlantaModulosBO.modificarInformacionModuloLaboratorio(moduloLaboratorioDetalles);
                restaurarInformacionModuloLaboratorio();
                colorMensaje = "green";
                mensajeFormulario = "Se ha activado el modulo/banco de trabajo.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesModulosLaboratorio activarModuloLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesModulosLaboratorio activarModuloLaboratorio : " + e.toString());
        }
    }

    public void inactivarModuloLaboratorio() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(false);
                moduloLaboratorioDetalles.setEstadomodulo(bool);
                gestionarPlantaModulosBO.modificarInformacionModuloLaboratorio(moduloLaboratorioDetalles);
                moduloLaboratorioDetalles = new ModuloLaboratorio();
                restaurarInformacionModuloLaboratorio();
                colorMensaje = "green";
                mensajeFormulario = "Se ha inactivado el modulo/banco de trabajo.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesModulosLaboratorio inactivarModuloLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesModulosLaboratorio inactivarModuloLaboratorio : " + e.toString());
        }
    }

    //GET - SET
    public ModuloLaboratorio getModuloLaboratorioDetalles() {
        return moduloLaboratorioDetalles;
    }

    public void setModuloLaboratorioDetalles(ModuloLaboratorio moduloLaboratorioDetalles) {
        this.moduloLaboratorioDetalles = moduloLaboratorioDetalles;
    }

    public BigInteger getIdModuloLaboratorio() {
        return idModuloLaboratorio;
    }

    public void setIdModuloLaboratorio(BigInteger idModuloLaboratorio) {
        this.idModuloLaboratorio = idModuloLaboratorio;
    }

    public boolean isActivarEditar() {
        return activarEditar;
    }

    public void setActivarEditar(boolean activarEditar) {
        this.activarEditar = activarEditar;
    }

    public boolean isDisabledEditar() {
        return disabledEditar;
    }

    public void setDisabledEditar(boolean disabledEditar) {
        this.disabledEditar = disabledEditar;
    }

    public boolean isModificacionRegistro() {
        return modificacionRegistro;
    }

    public void setModificacionRegistro(boolean modificacionRegistro) {
        this.modificacionRegistro = modificacionRegistro;
    }

    public boolean isDisabledActivar() {
        return disabledActivar;
    }

    public void setDisabledActivar(boolean disabledActivar) {
        this.disabledActivar = disabledActivar;
    }

    public boolean isDisabledInactivar() {
        return disabledInactivar;
    }

    public void setDisabledInactivar(boolean disabledInactivar) {
        this.disabledInactivar = disabledInactivar;
    }

    public boolean isVisibleGuardar() {
        return visibleGuardar;
    }

    public void setVisibleGuardar(boolean visibleGuardar) {
        this.visibleGuardar = visibleGuardar;
    }

    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreas() {
        return listaLaboratoriosPorAreas;
    }

    public void setListaLaboratoriosPorAreas(List<LaboratoriosPorAreas> listaLaboratoriosPorAreas) {
        this.listaLaboratoriosPorAreas = listaLaboratoriosPorAreas;
    }

    public LaboratoriosPorAreas getLaboratorioPorAreaModuloLaboratorio() {
        return laboratorioPorAreaModuloLaboratorio;
    }

    public void setLaboratorioPorAreaModuloLaboratorio(LaboratoriosPorAreas laboratorioPorAreaModuloLaboratorio) {
        this.laboratorioPorAreaModuloLaboratorio = laboratorioPorAreaModuloLaboratorio;
    }

    public List<SalaLaboratorio> getListaSalasLaboratorio() {
        return listaSalasLaboratorio;
    }

    public void setListaSalasLaboratorio(List<SalaLaboratorio> listaSalasLaboratorio) {
        this.listaSalasLaboratorio = listaSalasLaboratorio;
    }

    public SalaLaboratorio getSalaModuloLaboratorio() {
        return salaModuloLaboratorio;
    }

    public void setSalaModuloLaboratorio(SalaLaboratorio salaModuloLaboratorio) {
        this.salaModuloLaboratorio = salaModuloLaboratorio;
    }

    public boolean isActivarSala() {
        return activarSala;
    }

    public void setActivarSala(boolean activarSala) {
        this.activarSala = activarSala;
    }

    public String getDetalleModuloLaboratorio() {
        return detalleModuloLaboratorio;
    }

    public void setDetalleModuloLaboratorio(String detalleModuloLaboratorio) {
        this.detalleModuloLaboratorio = detalleModuloLaboratorio;
    }

    public String getCodigoModuloLaboratorio() {
        return codigoModuloLaboratorio;
    }

    public void setCodigoModuloLaboratorio(String codigoModuloLaboratorio) {
        this.codigoModuloLaboratorio = codigoModuloLaboratorio;
    }

    public String getCostoModuloLaboratorio() {
        return costoModuloLaboratorio;
    }

    public void setCostoModuloLaboratorio(String costoModuloLaboratorio) {
        this.costoModuloLaboratorio = costoModuloLaboratorio;
    }

    public String getCapacidadModuloLaboratorio() {
        return capacidadModuloLaboratorio;
    }

    public void setCapacidadModuloLaboratorio(String capacidadModuloLaboratorio) {
        this.capacidadModuloLaboratorio = capacidadModuloLaboratorio;
    }

    public String getInversionModuloLaboratorio() {
        return inversionModuloLaboratorio;
    }

    public void setInversionModuloLaboratorio(String inversionModuloLaboratorio) {
        this.inversionModuloLaboratorio = inversionModuloLaboratorio;
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

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getLaboratorioModuloLaboratorio() {
        return laboratorioModuloLaboratorio;
    }

    public void setLaboratorioModuloLaboratorio(Laboratorio laboratorioModuloLaboratorio) {
        this.laboratorioModuloLaboratorio = laboratorioModuloLaboratorio;
    }

    public boolean isActivarLabPorArea() {
        return activarLabPorArea;
    }

    public void setActivarLabPorArea(boolean activarLabPorArea) {
        this.activarLabPorArea = activarLabPorArea;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getDepartamentoModuloLaboratorio() {
        return departamentoModuloLaboratorio;
    }

    public void setDepartamentoModuloLaboratorio(Departamento departamentoModuloLaboratorio) {
        this.departamentoModuloLaboratorio = departamentoModuloLaboratorio;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

}
