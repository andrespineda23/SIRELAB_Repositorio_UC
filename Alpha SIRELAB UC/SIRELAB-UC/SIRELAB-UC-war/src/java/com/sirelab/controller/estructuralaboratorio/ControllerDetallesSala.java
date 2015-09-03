/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalasBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
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
public class ControllerDetallesSala implements Serializable {

    @EJB
    GestionarPlantaSalasBOInterface gestionarPlantaSalasBO;

    private SalaLaboratorio salaLaboratorioDetalles;
    private BigInteger idSalaLaboratorio;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private List<Departamento> listaDepartamentos;
    private Departamento departamentoSalaLaboratorio;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio laboratorioSalaLaboratorio;
    private boolean activarLaboratorio;
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private LaboratoriosPorAreas laboratorioPorAreaSalaLaboratorio;
    private boolean activarLabPorArea;
    private List<Sede> listaSedes;
    private Sede sedeSalaLaboratorio;
    private List<Edificio> listaEdificios;
    private Edificio edificioSalaLaboratorio;
    private boolean activarEdificio;
    private String nombreSalaLaboratorio, codigoSalaLaboratorio, ubicacionSalaLaboratorio, descripcionSalaLaboratorio;
    private String costoSalaLaboratorio, capacidadSalaLaboratorio, inversionSalaLaboratorio;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesDescripcion, validacionesUbicacion;
    private boolean validacionesCapacidad, validacionesCosto, validacionesInversion, validacionesDepartamento;
    private boolean validacionesLaboratorioPorArea, validacionesSede, validacionesEdificio, validacionesLaboratorio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarTipo;

    public ControllerDetallesSala() {
    }

    @PostConstruct
    public void init() {
        validacionesCosto = true;
        validacionesCapacidad = true;
        validacionesCodigo = true;
        validacionesDescripcion = true;
        validacionesEdificio = true;
        validacionesInversion = true;
        validacionesLaboratorio = true;
        validacionesDepartamento = true;
        validacionesLaboratorioPorArea = true;
        validacionesNombre = true;
        validacionesSede = true;
        validacionesUbicacion = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public void recibirIDSalasLaboratorioDetalles(BigInteger idSalaLaboratorio) {
        activarEditar = true;
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            disabledEditar = false;
        }
        this.idSalaLaboratorio = idSalaLaboratorio;
        salaLaboratorioDetalles = gestionarPlantaSalasBO.obtenerSalaLaboratorioPorIDSalaLaboratorio(idSalaLaboratorio);
        if (salaLaboratorioDetalles.getEstadosala() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesSalaLaboratorio();
    }

    public void asignarValoresVariablesSalaLaboratorio() {
        editarTipo = salaLaboratorioDetalles.getSalaprivada();
        nombreSalaLaboratorio = salaLaboratorioDetalles.getNombresala();
        codigoSalaLaboratorio = salaLaboratorioDetalles.getCodigosala();
        ubicacionSalaLaboratorio = salaLaboratorioDetalles.getPisoubicacion();
        descripcionSalaLaboratorio = salaLaboratorioDetalles.getDescripcionsala();
        costoSalaLaboratorio = String.valueOf(salaLaboratorioDetalles.getCostoalquiler());
        capacidadSalaLaboratorio = String.valueOf(salaLaboratorioDetalles.getCapacidadsala());
        inversionSalaLaboratorio = salaLaboratorioDetalles.getValorinversion().toString();
        edificioSalaLaboratorio = salaLaboratorioDetalles.getEdificio();
        sedeSalaLaboratorio = salaLaboratorioDetalles.getEdificio().getSede();
        departamentoSalaLaboratorio = salaLaboratorioDetalles.getLaboratoriosporareas().getLaboratorio().getDepartamento();
        laboratorioSalaLaboratorio = salaLaboratorioDetalles.getLaboratoriosporareas().getLaboratorio();
        laboratorioPorAreaSalaLaboratorio = salaLaboratorioDetalles.getLaboratoriosporareas();
        listaSedes = gestionarPlantaSalasBO.consultarSedesRegistradas();
        listaEdificios = gestionarPlantaSalasBO.consultarEdificiosPorIDSede(sedeSalaLaboratorio.getIdsede());
        listaDepartamentos = gestionarPlantaSalasBO.consultarDepartamentosRegistrados();
        listaLaboratorios = gestionarPlantaSalasBO.consultarLaboratoriosPorIDDepartamento(departamentoSalaLaboratorio.getIddepartamento());
        listaLaboratoriosPorAreas = gestionarPlantaSalasBO.consultarLaboratoriosPorAreasPorLaboratorio(laboratorioSalaLaboratorio.getIdlaboratorio());
        activarEdificio = true;
        activarLabPorArea = true;
        activarLaboratorio = true;
        visibleGuardar = false;
        //
    }

    public void activarEditarRegistro() {
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        activarEdificio = false;
        activarLabPorArea = false;
        activarLaboratorio = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    public String restaurarInformacionSalaLaboratorio() {
        salaLaboratorioDetalles = new SalaLaboratorio();
        recibirIDSalasLaboratorioDetalles(this.idSalaLaboratorio);
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        return "administrarsalas";
    }

    public void validarNombreSala() {
        if (Utilidades.validarNulo(nombreSalaLaboratorio) && (!nombreSalaLaboratorio.isEmpty()) && (nombreSalaLaboratorio.trim().length() > 0)) {
            int tam = nombreSalaLaboratorio.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nombreSalaLaboratorio)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreSalaLaboratorio", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreSalaLaboratorio", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreSalaLaboratorio", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistroSala();
    }

    public void validarCodigoSala() {
        if (Utilidades.validarNulo(codigoSalaLaboratorio) && (!codigoSalaLaboratorio.isEmpty()) && (codigoSalaLaboratorio.trim().length() > 0)) {
            int tam = codigoSalaLaboratorio.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(codigoSalaLaboratorio)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:codigoSalaLaboratorio", new FacesMessage("El codigo ingresado es incorrecto."));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:codigoSalaLaboratorio", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:codigoSalaLaboratorio", new FacesMessage("El codigo es obligatorio."));
        }
        modificacionesRegistroSala();
    }

    public void validarUbicacionSala() {
        if (Utilidades.validarNulo(ubicacionSalaLaboratorio) && (!ubicacionSalaLaboratorio.isEmpty()) && (ubicacionSalaLaboratorio.trim().length() > 0)) {
            int tam = ubicacionSalaLaboratorio.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(ubicacionSalaLaboratorio)) {
                    validacionesUbicacion = true;
                } else {
                    validacionesUbicacion = false;
                    FacesContext.getCurrentInstance().addMessage("form:ubicacionSalaLaboratorio", new FacesMessage("La ubicación se encuentra incorrecta."));
                }
            } else {
                validacionesUbicacion = false;
                FacesContext.getCurrentInstance().addMessage("form:ubicacionSalaLaboratorio", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesUbicacion = false;
            FacesContext.getCurrentInstance().addMessage("form:ubicacionSalaLaboratorio", new FacesMessage("La ubicación es obligatoria."));
        }
        modificacionesRegistroSala();
    }

    public void validarCostoAlquilerSala() {
        if (Utilidades.validarNulo(costoSalaLaboratorio) && (!costoSalaLaboratorio.isEmpty()) && (costoSalaLaboratorio.trim().length() > 0)) {
            if (Utilidades.isNumber(costoSalaLaboratorio)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:costoSalaLaboratorio", new FacesMessage("El costo se encuentra incorrecto."));
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:costoSalaLaboratorio", new FacesMessage("El costo es obligatorio."));
        }
        modificacionesRegistroSala();
    }

    public void validarCapacidadSala() {
        if (Utilidades.validarNulo(capacidadSalaLaboratorio) && (!capacidadSalaLaboratorio.isEmpty()) && (capacidadSalaLaboratorio.trim().length() > 0)) {
            if ((Utilidades.isNumber(capacidadSalaLaboratorio)) == false) {
                validacionesCapacidad = false;
                FacesContext.getCurrentInstance().addMessage("form:capacidadSalaLaboratorio", new FacesMessage("La capacidad ingresada se encuentra incorrecta."));
            } else {
                validacionesCapacidad = true;
            }
        } else {
            validacionesCapacidad = false;
            FacesContext.getCurrentInstance().addMessage("form:capacidadSalaLaboratorio", new FacesMessage("La capacidad es obligatoria."));
        }
        modificacionesRegistroSala();
    }

    public void validarDescripcionSala() {
        if (Utilidades.validarNulo(descripcionSalaLaboratorio) && (!descripcionSalaLaboratorio.isEmpty()) && (descripcionSalaLaboratorio.trim().length() > 0)) {
            int tam = descripcionSalaLaboratorio.length();
            if (tam >= 20) {
                if ((Utilidades.validarCaracteresAlfaNumericos(descripcionSalaLaboratorio)) == false) {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:descripcionSalaLaboratorio", new FacesMessage("La descripción se encuentra incorrecta."));
                } else {
                    validacionesDescripcion = true;
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:descripcionSalaLaboratorio", new FacesMessage("El tamaño minimo permitido es 20 caracteres."));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:descripcionSalaLaboratorio", new FacesMessage("La descripción es obligatoria."));
        }
        modificacionesRegistroSala();
    }

    public void validarInversionSala() {
        if (Utilidades.validarNulo(inversionSalaLaboratorio) && (!inversionSalaLaboratorio.isEmpty()) && (inversionSalaLaboratorio.trim().length() > 0)) {
            if ((Utilidades.isNumber(inversionSalaLaboratorio)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:inversionSalaLaboratorio", new FacesMessage("El valor de inversión se encuentra incorrecto."));
            } else {
                validacionesInversion = true;
            }
        }
        modificacionesRegistroSala();
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(departamentoSalaLaboratorio)) {
            validacionesDepartamento = true;
            listaLaboratorios = gestionarPlantaSalasBO.consultarLaboratoriosPorIDDepartamento(departamentoSalaLaboratorio.getIddepartamento());
            activarLabPorArea = false;
            laboratorioSalaLaboratorio = null;
        } else {
            validacionesDepartamento = false;
            validacionesLaboratorio = false;
            validacionesLaboratorioPorArea = false;

            listaLaboratorios = null;
            listaLaboratoriosPorAreas = null;

            activarLabPorArea = true;
            activarLaboratorio = true;

            laboratorioSalaLaboratorio = null;
            laboratorioPorAreaSalaLaboratorio = null;

            FacesContext.getCurrentInstance().addMessage("form:laboratorioSalaDepartamento", new FacesMessage("El laboratorio por area es obligatorio."));
        }
        modificacionesRegistroSala();
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(laboratorioSalaLaboratorio)) {
            validacionesLaboratorio = true;
            listaLaboratoriosPorAreas = gestionarPlantaSalasBO.consultarLaboratoriosPorAreasPorLaboratorio(laboratorioSalaLaboratorio.getIdlaboratorio());
            activarLabPorArea = false;
            laboratorioPorAreaSalaLaboratorio = null;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:laboratorioSalaLaboratorio", new FacesMessage("El laboratorio por area es obligatorio."));
            validacionesLaboratorioPorArea = false;
            listaLaboratoriosPorAreas = null;
            activarLabPorArea = true;
            laboratorioPorAreaSalaLaboratorio = null;
        }
        modificacionesRegistroSala();
    }

    public void actualizarLaboratoriosPorArea() {
        if (Utilidades.validarNulo(laboratorioPorAreaSalaLaboratorio)) {
            validacionesLaboratorioPorArea = true;
        } else {
            validacionesLaboratorioPorArea = false;
            FacesContext.getCurrentInstance().addMessage("form:laboratorioPorAreaSalaLaboratorio", new FacesMessage("El area de profundización es obligatorio."));
        }
        modificacionesRegistroSala();
    }

    public void actualizarTipoSala() {
        modificacionesRegistroSala();
    }

    public void actualizarSedes() {
        try {
            if (Utilidades.validarNulo(sedeSalaLaboratorio)) {
                activarEdificio = false;
                edificioSalaLaboratorio = null;
                listaEdificios = gestionarPlantaSalasBO.consultarEdificiosPorIDSede(sedeSalaLaboratorio.getIdsede());
                validacionesSede = true;
            } else {
                validacionesSede = false;
                validacionesEdificio = false;
                edificioSalaLaboratorio = null;
                listaEdificios = null;
                activarEdificio = true;
                FacesContext.getCurrentInstance().addMessage("form:sedeSalaLaboratorio", new FacesMessage("El campo Sede es obligatorio."));
            }
            modificacionesRegistroSala();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlantaSala actualizarSedes:  " + e.toString());
            System.out.println("Error ControllerDetallesPlantaSala actualizarSedes : " + e.toString());
        }
    }

    public void actualizarEdificios() {
        if (Utilidades.validarNulo(edificioSalaLaboratorio)) {
            validacionesEdificio = true;
        } else {
            validacionesEdificio = false;
            FacesContext.getCurrentInstance().addMessage("form:edificioSalaLaboratorio", new FacesMessage("El edificio es obligatorio."));
        }
        modificacionesRegistroSala();
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
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            retorno = false;
        }
        if (validacionesEdificio == false) {
            retorno = false;
        }
        if (validacionesInversion == false) {
            retorno = false;
        }
        if (validacionesLaboratorioPorArea == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesSede == false) {
            retorno = false;
        }
        if (validacionesUbicacion == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        SalaLaboratorio sala = gestionarPlantaSalasBO.obtenerSalaLaboratorioPorCodigoEdificioLabArea(codigoSalaLaboratorio, edificioSalaLaboratorio.getIdedificio(), laboratorioPorAreaSalaLaboratorio.getIdlaboratoriosporareas());
        if (null != sala) {
            if (!salaLaboratorioDetalles.getIdsalalaboratorio().equals(sala.getIdsalalaboratorio())) {
                retorno = false;
            }
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarModificacionSala() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenaModificacionSalaEnSistema();
                recibirIDSalasLaboratorioDetalles(this.idSalaLaboratorio);
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
    }

    public void almacenaModificacionSalaEnSistema() {
        try {
            salaLaboratorioDetalles.setNombresala(nombreSalaLaboratorio);
            salaLaboratorioDetalles.setCodigosala(codigoSalaLaboratorio);
            salaLaboratorioDetalles.setPisoubicacion(ubicacionSalaLaboratorio);
            salaLaboratorioDetalles.setDescripcionsala(descripcionSalaLaboratorio);
            salaLaboratorioDetalles.setCostoalquiler(Long.parseLong(costoSalaLaboratorio));
            salaLaboratorioDetalles.setCapacidadsala(Integer.parseInt(capacidadSalaLaboratorio));
            salaLaboratorioDetalles.setValorinversion(new BigInteger(inversionSalaLaboratorio));
            salaLaboratorioDetalles.setEdificio(edificioSalaLaboratorio);
            salaLaboratorioDetalles.setSalaprivada(editarTipo);
            salaLaboratorioDetalles.setLaboratoriosporareas(laboratorioPorAreaSalaLaboratorio);
            gestionarPlantaSalasBO.modificarInformacionSalaLaboratorio(salaLaboratorioDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaSalas almacenaModificacionSalaEnSistema:  " + e.toString());
            System.out.println("Error ControllerGestionarPlantaSalas almacenaModificacionSalaEnSistema : " + e.toString());
        }
    }

    public void modificacionesRegistroSala() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    public void activarSalaLaboratorio() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(true);
                salaLaboratorioDetalles.setEstadosala(bool);
                gestionarPlantaSalasBO.modificarInformacionSalaLaboratorio(salaLaboratorioDetalles);
                restaurarInformacionSalaLaboratorio();
                colorMensaje = "green";
                mensajeFormulario = "Se ha activado la sala.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesSalasLaboratorio activarSalaLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesSalasLaboratorio activarSalaLaboratorio : " + e.toString());
        }
    }

    public void inactivarSalaLaboratorio() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(false);
                salaLaboratorioDetalles.setEstadosala(bool);
                gestionarPlantaSalasBO.modificarInformacionSalaLaboratorio(salaLaboratorioDetalles);
                salaLaboratorioDetalles = new SalaLaboratorio();
                restaurarInformacionSalaLaboratorio();
                colorMensaje = "green";
                mensajeFormulario = "Se ha inactivado la sala.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesSalasLaboratorio inactivarSalaLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesSalasLaboratorio inactivarSalaLaboratorio : " + e.toString());
        }
    }

    //GET - SET
    public SalaLaboratorio getSalaLaboratorioDetalles() {
        return salaLaboratorioDetalles;
    }

    public void setSalaLaboratorioDetalles(SalaLaboratorio salaLaboratorioDetalles) {
        this.salaLaboratorioDetalles = salaLaboratorioDetalles;
    }

    public BigInteger getIdSalaLaboratorio() {
        return idSalaLaboratorio;
    }

    public void setIdSalaLaboratorio(BigInteger idSalaLaboratorio) {
        this.idSalaLaboratorio = idSalaLaboratorio;
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

    public LaboratoriosPorAreas getLaboratorioPorAreaSalaLaboratorio() {
        return laboratorioPorAreaSalaLaboratorio;
    }

    public void setLaboratorioPorAreaSalaLaboratorio(LaboratoriosPorAreas laboratorioPorAreaSalaLaboratorio) {
        this.laboratorioPorAreaSalaLaboratorio = laboratorioPorAreaSalaLaboratorio;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public Sede getSedeSalaLaboratorio() {
        return sedeSalaLaboratorio;
    }

    public void setSedeSalaLaboratorio(Sede sedeSalaLaboratorio) {
        this.sedeSalaLaboratorio = sedeSalaLaboratorio;
    }

    public List<Edificio> getListaEdificios() {
        return listaEdificios;
    }

    public void setListaEdificios(List<Edificio> listaEdificios) {
        this.listaEdificios = listaEdificios;
    }

    public Edificio getEdificioSalaLaboratorio() {
        return edificioSalaLaboratorio;
    }

    public void setEdificioSalaLaboratorio(Edificio edificioSalaLaboratorio) {
        this.edificioSalaLaboratorio = edificioSalaLaboratorio;
    }

    public boolean isActivarEdificio() {
        return activarEdificio;
    }

    public void setActivarEdificio(boolean activarEdificio) {
        this.activarEdificio = activarEdificio;
    }

    public String getNombreSalaLaboratorio() {
        return nombreSalaLaboratorio;
    }

    public void setNombreSalaLaboratorio(String nombreSalaLaboratorio) {
        this.nombreSalaLaboratorio = nombreSalaLaboratorio;
    }

    public String getCodigoSalaLaboratorio() {
        return codigoSalaLaboratorio;
    }

    public void setCodigoSalaLaboratorio(String codigoSalaLaboratorio) {
        this.codigoSalaLaboratorio = codigoSalaLaboratorio;
    }

    public String getUbicacionSalaLaboratorio() {
        return ubicacionSalaLaboratorio;
    }

    public void setUbicacionSalaLaboratorio(String ubicacionSalaLaboratorio) {
        this.ubicacionSalaLaboratorio = ubicacionSalaLaboratorio;
    }

    public String getDescripcionSalaLaboratorio() {
        return descripcionSalaLaboratorio;
    }

    public void setDescripcionSalaLaboratorio(String descripcionSalaLaboratorio) {
        this.descripcionSalaLaboratorio = descripcionSalaLaboratorio;
    }

    public String getCostoSalaLaboratorio() {
        return costoSalaLaboratorio;
    }

    public void setCostoSalaLaboratorio(String costoSalaLaboratorio) {
        this.costoSalaLaboratorio = costoSalaLaboratorio;
    }

    public String getCapacidadSalaLaboratorio() {
        return capacidadSalaLaboratorio;
    }

    public void setCapacidadSalaLaboratorio(String capacidadSalaLaboratorio) {
        this.capacidadSalaLaboratorio = capacidadSalaLaboratorio;
    }

    public String getInversionSalaLaboratorio() {
        return inversionSalaLaboratorio;
    }

    public void setInversionSalaLaboratorio(String inversionSalaLaboratorio) {
        this.inversionSalaLaboratorio = inversionSalaLaboratorio;
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

    public Laboratorio getLaboratorioSalaLaboratorio() {
        return laboratorioSalaLaboratorio;
    }

    public void setLaboratorioSalaLaboratorio(Laboratorio laboratorioSalaLaboratorio) {
        this.laboratorioSalaLaboratorio = laboratorioSalaLaboratorio;
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

    public Departamento getDepartamentoSalaLaboratorio() {
        return departamentoSalaLaboratorio;
    }

    public void setDepartamentoSalaLaboratorio(Departamento departamentoSalaLaboratorio) {
        this.departamentoSalaLaboratorio = departamentoSalaLaboratorio;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public boolean isEditarTipo() {
        return editarTipo;
    }

    public void setEditarTipo(boolean editarTipo) {
        this.editarTipo = editarTipo;
    }

}
