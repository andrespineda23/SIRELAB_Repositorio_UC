/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalasBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.Sede;
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
    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarValidadorTipoUsuario;

    private SalaLaboratorio salaLaboratorioDetalles;
    private BigInteger idSalaLaboratorio;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private List<Departamento> listaDepartamentos;
    private Departamento departamentoSalaLaboratorio;
    private boolean activarDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio laboratorioSalaLaboratorio;
    private boolean activarLaboratorio;
    private List<Sede> listaSedes;
    private Sede sedeSalaLaboratorio;
    private boolean activarSede;
    private List<Edificio> listaEdificios;
    private Edificio edificioSalaLaboratorio;
    private boolean activarEdificio;
    private String nombreSalaLaboratorio, codigoSalaLaboratorio, ubicacionSalaLaboratorio, descripcionSalaLaboratorio;
    private String costoSalaLaboratorio, capacidadSalaLaboratorio, inversionSalaLaboratorio;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesDescripcion, validacionesUbicacion;
    private boolean validacionesCapacidad, validacionesCosto, validacionesInversion, validacionesDepartamento;
    private boolean validacionesSede, validacionesEdificio, validacionesLaboratorio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean editarTipo;
    private MensajesConstantes constantes;
    private boolean perfilConsulta;
    private boolean costoAlquilerDigitado;
    private String mensajeError;

    private List<SalaLaboratorioxServicios> listaServiciosAsociados;
    private List<SalaLaboratorioxServicios> listaServiciosAsociadosTabla;
    private int posicionServiciosTabla;
    private int tamTotalServicios;
    private boolean bloquearPagSigServicios, bloquearPagAntServicios;

    public ControllerDetallesSala() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        validacionesCosto = true;
        validacionesCapacidad = true;
        validacionesCodigo = true;
        validacionesDescripcion = true;
        validacionesEdificio = true;
        validacionesInversion = true;
        validacionesLaboratorio = true;
        validacionesDepartamento = true;
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
        listaServiciosAsociados = gestionarPlantaSalasBO.obtenerSalaLaboratorioxServiciosPorIdSala(this.idSalaLaboratorio);
        if (null != listaServiciosAsociados) {
            listaServiciosAsociadosTabla = new ArrayList<SalaLaboratorioxServicios>();
            tamTotalServicios = listaServiciosAsociados.size();
        }
        posicionServiciosTabla = 0;
        cargarDatosServiciosTabla();
        if (salaLaboratorioDetalles.getEstadosala() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesSalaLaboratorio();
    }

    private void cargarDatosServiciosTabla() {
        if (tamTotalServicios < 10) {
            for (int i = 0; i < tamTotalServicios; i++) {
                listaServiciosAsociadosTabla.add(listaServiciosAsociados.get(i));
            }
            bloquearPagSigServicios = true;
            bloquearPagAntServicios = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaServiciosAsociadosTabla.add(listaServiciosAsociados.get(i));
            }
            bloquearPagSigServicios = false;
            bloquearPagAntServicios = true;
        }
    }

    public void cargarPaginaSiguienteServicios() {
        listaServiciosAsociadosTabla = new ArrayList<SalaLaboratorioxServicios>();
        posicionServiciosTabla = posicionServiciosTabla + 10;
        int diferencia = tamTotalServicios - posicionServiciosTabla;
        if (diferencia > 10) {
            for (int i = posicionServiciosTabla; i < (posicionServiciosTabla + 10); i++) {
                listaServiciosAsociadosTabla.add(listaServiciosAsociados.get(i));
            }
            bloquearPagSigServicios = false;
            bloquearPagAntServicios = false;
        } else {
            for (int i = posicionServiciosTabla; i < (posicionServiciosTabla + diferencia); i++) {
                listaServiciosAsociadosTabla.add(listaServiciosAsociados.get(i));
            }
            bloquearPagSigServicios = true;
            bloquearPagAntServicios = false;
        }
    }

    public void cargarPaginaAnteriorServicios() {
        listaServiciosAsociadosTabla = new ArrayList<SalaLaboratorioxServicios>();
        posicionServiciosTabla = posicionServiciosTabla - 10;
        int diferencia = tamTotalServicios - posicionServiciosTabla;
        if (diferencia == tamTotalServicios) {
            for (int i = posicionServiciosTabla; i < (posicionServiciosTabla + 10); i++) {
                listaServiciosAsociadosTabla.add(listaServiciosAsociados.get(i));
            }
            bloquearPagSigServicios = false;
            bloquearPagAntServicios = true;
        } else {
            for (int i = posicionServiciosTabla; i < (posicionServiciosTabla + 10); i++) {
                listaServiciosAsociadosTabla.add(listaServiciosAsociados.get(i));
            }
            bloquearPagSigServicios = false;
            bloquearPagAntServicios = false;
        }
    }

    public void asignarValoresVariablesSalaLaboratorio() {
        mensajeError = "";
        editarTipo = salaLaboratorioDetalles.getSalaprivada();
        nombreSalaLaboratorio = salaLaboratorioDetalles.getNombresala();
        codigoSalaLaboratorio = salaLaboratorioDetalles.getCodigosala();
        ubicacionSalaLaboratorio = salaLaboratorioDetalles.getPisoubicacion();
        descripcionSalaLaboratorio = salaLaboratorioDetalles.getDescripcionsala();
        capacidadSalaLaboratorio = String.valueOf(salaLaboratorioDetalles.getCapacidadsala());
        inversionSalaLaboratorio = salaLaboratorioDetalles.getValorinversion().toString();
        edificioSalaLaboratorio = salaLaboratorioDetalles.getEdificio();
        sedeSalaLaboratorio = salaLaboratorioDetalles.getEdificio().getSede();
        departamentoSalaLaboratorio = salaLaboratorioDetalles.getLaboratorio().getDepartamento();
        laboratorioSalaLaboratorio = salaLaboratorioDetalles.getLaboratorio();
        listaSedes = gestionarPlantaSalasBO.consultarSedesRegistradas();
        listaEdificios = gestionarPlantaSalasBO.consultarEdificiosPorIDSede(sedeSalaLaboratorio.getIdsede());
        listaDepartamentos = gestionarPlantaSalasBO.consultarDepartamentosRegistrados();
        listaLaboratorios = gestionarPlantaSalasBO.consultarLaboratoriosPorIDDepartamento(departamentoSalaLaboratorio.getIddepartamento());
        costoAlquilerDigitado = salaLaboratorioDetalles.getCostosaladigitado();
        if (costoAlquilerDigitado == false) {
            costoSalaLaboratorio = String.valueOf(salaLaboratorioDetalles.getCostoalquiler());
        } else {
            costoSalaLaboratorio = gestionarPlantaSalasBO.obtenerCostoCalculadoSalaLaboratorio(idSalaLaboratorio);
        }
        activarEdificio = true;
        activarLaboratorio = true;
        visibleGuardar = false;
        //
        cargarInformacionPerfil();
    }

    private void cargarInformacionPerfil() {
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            perfilConsulta = true;
        } else {
            if ("ADMINISTRADOREDIFICIO".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                Edificio edificio = obtenerEdificio(usuarioLoginSistema.getIdUsuarioLogin());
                edificioSalaLaboratorio = edificio;
                sedeSalaLaboratorio = edificio.getSede();
                activarSede = true;
                activarEdificio = true;
                activarDepartamento = false;
                activarLaboratorio = false;
                listaSedes = new ArrayList<Sede>();
                listaEdificios = new ArrayList<Edificio>();
                listaSedes.add(sedeSalaLaboratorio);
                listaEdificios.add(edificioSalaLaboratorio);
            }
        }
    }

    private Edificio obtenerEdificio(BigInteger usuario) {
        Edificio edificio = administrarValidadorTipoUsuario.buscarEdificioPorIdEncargadoEdificio(usuario);
        return edificio;
    }

    public void activarEditarRegistro() {
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        activarEdificio = false;
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
                    FacesContext.getCurrentInstance().addMessage("form:nombreSalaLaboratorio", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreSalaLaboratorio", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreSalaLaboratorio", new FacesMessage("El nombre es obligatorio. " + constantes.INVENTARIO_NOMBRE));
        }
        modificacionesRegistroSala();
    }

    public void validarCodigoSala() {
        if (Utilidades.validarNulo(codigoSalaLaboratorio) && (!codigoSalaLaboratorio.isEmpty()) && (codigoSalaLaboratorio.trim().length() > 0)) {
            int tam = codigoSalaLaboratorio.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(codigoSalaLaboratorio)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:codigoSalaLaboratorio", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.INVENTARIO_CODIGO));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:codigoSalaLaboratorio", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:codigoSalaLaboratorio", new FacesMessage("El codigo es obligatorio. " + constantes.INVENTARIO_CODIGO));
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
                    FacesContext.getCurrentInstance().addMessage("form:ubicacionSalaLaboratorio", new FacesMessage("La ubicación se encuentra incorrecta. " + constantes.INVENTARIO_UBICACION));
                }
            } else {
                validacionesUbicacion = false;
                FacesContext.getCurrentInstance().addMessage("form:ubicacionSalaLaboratorio", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_UBICACION));
            }
        } else {
            validacionesUbicacion = false;
            FacesContext.getCurrentInstance().addMessage("form:ubicacionSalaLaboratorio", new FacesMessage("La ubicación es obligatoria. " + constantes.INVENTARIO_UBICACION));
        }
        modificacionesRegistroSala();
    }

    public void validarCostoAlquilerSala() {
        if (Utilidades.validarNulo(costoSalaLaboratorio) && (!costoSalaLaboratorio.isEmpty()) && (costoSalaLaboratorio.trim().length() > 0)) {
            if (Utilidades.isNumber(costoSalaLaboratorio)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:costoSalaLaboratorio", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:costoSalaLaboratorio", new FacesMessage("El costo es obligatorio. " + constantes.INVENTARIO_COST_ALQ));
        }
        modificacionesRegistroSala();
    }

    public void validarCapacidadSala() {
        if (Utilidades.validarNulo(capacidadSalaLaboratorio) && (!capacidadSalaLaboratorio.isEmpty()) && (capacidadSalaLaboratorio.trim().length() > 0)) {
            if ((Utilidades.isNumber(capacidadSalaLaboratorio)) == false) {
                validacionesCapacidad = false;
                FacesContext.getCurrentInstance().addMessage("form:capacidadSalaLaboratorio", new FacesMessage("La capacidad ingresada se encuentra incorrecta. " + constantes.INVENTARIO_CAPACIDAD));
            } else {
                validacionesCapacidad = true;
            }
        } else {
            validacionesCapacidad = false;
            FacesContext.getCurrentInstance().addMessage("form:capacidadSalaLaboratorio", new FacesMessage("La capacidad es obligatoria. " + constantes.INVENTARIO_CAPACIDAD));
        }
        modificacionesRegistroSala();
    }

    public void validarDescripcionSala() {
        if (Utilidades.validarNulo(descripcionSalaLaboratorio) && (!descripcionSalaLaboratorio.isEmpty()) && (descripcionSalaLaboratorio.trim().length() > 0)) {
            int tam = descripcionSalaLaboratorio.length();
            if (tam >= 20) {
                if ((Utilidades.validarCaracteresAlfaNumericos(descripcionSalaLaboratorio)) == false) {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:descripcionSalaLaboratorio", new FacesMessage("La descripción se encuentra incorrecta. " + constantes.INVENTARIO_DESCRIP));
                } else {
                    validacionesDescripcion = true;
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:descripcionSalaLaboratorio", new FacesMessage("El tamaño minimo permitido es 20 caracteres. " + constantes.INVENTARIO_DESCRIP));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:descripcionSalaLaboratorio", new FacesMessage("La descripción es obligatoria. " + constantes.INVENTARIO_DESCRIP));
        }
        modificacionesRegistroSala();
    }

    public void validarInversionSala() {
        if (Utilidades.validarNulo(inversionSalaLaboratorio) && (!inversionSalaLaboratorio.isEmpty()) && (inversionSalaLaboratorio.trim().length() > 0)) {
            if ((Utilidades.isNumber(inversionSalaLaboratorio)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:inversionSalaLaboratorio", new FacesMessage("El valor de inversión se encuentra incorrecto. " + constantes.INVENTARIO_COST_INV));
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
            laboratorioSalaLaboratorio = null;
        } else {
            validacionesDepartamento = false;
            validacionesLaboratorio = false;
            listaLaboratorios = null;
            activarLaboratorio = true;
            laboratorioSalaLaboratorio = null;
            FacesContext.getCurrentInstance().addMessage("form:departamentoSalaLaboratorio", new FacesMessage("El departamento es obligatorio."));
        }
        modificacionesRegistroSala();
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(laboratorioSalaLaboratorio)) {
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:laboratorioSalaLaboratorio", new FacesMessage("El laboratorio es obligatorio."));
        }
        modificacionesRegistroSala();
    }

    public void actualizarTipoSala() {
        modificacionesRegistroSala();
    }

    public void cambiarCostoAlquilerDigitado() {
        if (costoAlquilerDigitado == true) {
            costoSalaLaboratorio = "0";
            validacionesCosto = true;
            costoSalaLaboratorio = gestionarPlantaSalasBO.obtenerCostoCalculadoSalaLaboratorio(idSalaLaboratorio);
        } else {
            costoSalaLaboratorio = "0";
            validacionesCosto = false;
        }
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
            logger.error("Error ControllerDetallesPlantaSala actualizarSedes : " + e.toString(), e);
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
        mensajeError = "";
        if (validacionesCosto == false) {
            mensajeError = mensajeError + " - Costo Alquiler - ";
            retorno = false;
        }
        if (validacionesCapacidad == false) {
            mensajeError = mensajeError + " - Capacidad - ";
            retorno = false;
        }
        if (validacionesCodigo == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            mensajeError = mensajeError + " - Descripción - ";
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            mensajeError = mensajeError + " - Departamento - ";
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            mensajeError = mensajeError + " - Laboratorio - ";
            retorno = false;
        }
        if (validacionesEdificio == false) {
            mensajeError = mensajeError + " - Edificio - ";
            retorno = false;
        }
        if (validacionesInversion == false) {
            mensajeError = mensajeError + " - Costo Inversión - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        if (validacionesSede == false) {
            mensajeError = mensajeError + " - Sede - ";
            retorno = false;
        }
        if (validacionesUbicacion == false) {
            mensajeError = mensajeError + " - Ubicación - ";
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        SalaLaboratorio sala = gestionarPlantaSalasBO.obtenerSalaLaboratorioPorCodigoEdificioyLaboratorio(codigoSalaLaboratorio, edificioSalaLaboratorio.getIdedificio(), laboratorioSalaLaboratorio.getIdlaboratorio());
        if (null != sala) {
            if (!salaLaboratorioDetalles.getIdsalalaboratorio().equals(sala.getIdsalalaboratorio())) {
                retorno = false;
            }
        }
        return retorno;
    }

    private boolean validarAsociacionServicio() {
        boolean retorno = false;
        if (null != listaServiciosAsociados) {
            int contador = 0;
            for (int i = 0; i < listaServiciosAsociados.size(); i++) {
                if (listaServiciosAsociados.get(i).getEstado() == true) {
                    contador++;
                }
            }
            if (contador > 0) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void registrarModificacionSala() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                if (validarAsociacionServicio() == true) {
                    almacenaModificacionSalaEnSistema();
                    recibirIDSalasLaboratorioDetalles(this.idSalaLaboratorio);
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "La sala de laboratorio debe tener asociado al menos 1 servicio.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El codigo ya esta registrado en el sistema.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    public void almacenaModificacionSalaEnSistema() {
        try {
            salaLaboratorioDetalles.setCostosaladigitado(costoAlquilerDigitado);
            salaLaboratorioDetalles.setNombresala(nombreSalaLaboratorio);
            salaLaboratorioDetalles.setCodigosala(codigoSalaLaboratorio);
            salaLaboratorioDetalles.setPisoubicacion(ubicacionSalaLaboratorio);
            salaLaboratorioDetalles.setDescripcionsala(descripcionSalaLaboratorio);
            salaLaboratorioDetalles.setCostoalquiler(Long.parseLong(costoSalaLaboratorio));
            salaLaboratorioDetalles.setCapacidadsala(Integer.parseInt(capacidadSalaLaboratorio));
            salaLaboratorioDetalles.setValorinversion(new BigInteger(inversionSalaLaboratorio));
            salaLaboratorioDetalles.setEdificio(edificioSalaLaboratorio);
            salaLaboratorioDetalles.setSalaprivada(editarTipo);
            salaLaboratorioDetalles.setLaboratorio(laboratorioSalaLaboratorio);
            gestionarPlantaSalasBO.modificarInformacionSalaLaboratorio(salaLaboratorioDetalles);
            gestionarPlantaSalasBO.almacenarModificacionesSalaServicio(listaServiciosAsociados);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaSalas almacenaModificacionSalaEnSistema:  " + e.toString(), e);
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
                colorMensaje = "#FF0000";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesSalasLaboratorio activarSalaLaboratorio:  " + e.toString(), e);
            logger.error("Error ControllerDetallesSalasLaboratorio activarSalaLaboratorio : " + e.toString(), e);
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
                colorMensaje = "#FF0000";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesSalasLaboratorio inactivarSalaLaboratorio:  " + e.toString(), e);
            logger.error("Error ControllerDetallesSalasLaboratorio inactivarSalaLaboratorio : " + e.toString(), e);
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

    public boolean isActivarDepartamento() {
        return activarDepartamento;
    }

    public void setActivarDepartamento(boolean activarDepartamento) {
        this.activarDepartamento = activarDepartamento;
    }

    public boolean isActivarSede() {
        return activarSede;
    }

    public void setActivarSede(boolean activarSede) {
        this.activarSede = activarSede;
    }

    public boolean isPerfilConsulta() {
        return perfilConsulta;
    }

    public void setPerfilConsulta(boolean perfilConsulta) {
        this.perfilConsulta = perfilConsulta;
    }

    public List<SalaLaboratorioxServicios> getListaServiciosAsociados() {
        return listaServiciosAsociados;
    }

    public void setListaServiciosAsociados(List<SalaLaboratorioxServicios> listaServiciosAsociados) {
        this.listaServiciosAsociados = listaServiciosAsociados;
    }

    public boolean isCostoAlquilerDigitado() {
        return costoAlquilerDigitado;
    }

    public void setCostoAlquilerDigitado(boolean costoAlquilerDigitado) {
        this.costoAlquilerDigitado = costoAlquilerDigitado;
    }

    public List<SalaLaboratorioxServicios> getListaServiciosAsociadosTabla() {
        return listaServiciosAsociadosTabla;
    }

    public void setListaServiciosAsociadosTabla(List<SalaLaboratorioxServicios> listaServiciosAsociadosTabla) {
        this.listaServiciosAsociadosTabla = listaServiciosAsociadosTabla;
    }

    public int getPosicionServiciosTabla() {
        return posicionServiciosTabla;
    }

    public void setPosicionServiciosTabla(int posicionServiciosTabla) {
        this.posicionServiciosTabla = posicionServiciosTabla;
    }

    public int getTamTotalServicios() {
        return tamTotalServicios;
    }

    public void setTamTotalServicios(int tamTotalServicios) {
        this.tamTotalServicios = tamTotalServicios;
    }

    public boolean isBloquearPagSigServicios() {
        return bloquearPagSigServicios;
    }

    public void setBloquearPagSigServicios(boolean bloquearPagSigServicios) {
        this.bloquearPagSigServicios = bloquearPagSigServicios;
    }

    public boolean isBloquearPagAntServicios() {
        return bloquearPagAntServicios;
    }

    public void setBloquearPagAntServicios(boolean bloquearPagAntServicios) {
        this.bloquearPagAntServicios = bloquearPagAntServicios;
    }

}
