/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaModulo;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.TipoReserva;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerReservaModulo2 implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private AyudaReservaModulo reservaModulo;
    private TipoReserva parametroTipoReserva;
    private String mensajeFormulario, colorMensaje;
    private boolean validacionesTipo;
    private boolean adicionarElementos;

    public ControllerReservaModulo2() {
    }

    @PostConstruct
    public void init() {
        adicionarElementos = false;
        reservaModulo = (AyudaReservaModulo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reservaModulo");
        parametroTipoReserva = null;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        validacionesTipo = false;
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("DOCENTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            parametroTipoReserva = administrarReservasBO.obtenerTipoReservaPorId(new BigInteger("2"));
        } else {
            if ("ESTUDIANTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                parametroTipoReserva = administrarReservasBO.obtenerTipoReservaPorId(new BigInteger("2"));
            } else {
                parametroTipoReserva = administrarReservasBO.obtenerTipoReservaPorId(new BigInteger("3"));
            }
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesTipo == false) {
            retorno = false;
        }
        return retorno;
    }

    public String finalizarProcesoReserva() {
        String paginaSiguiente = "";
        if (validarResultadosValidacion() == true) {
            Boolean respuestaReserva = administrarReservasBO.validarReservaSalaDisposible(reservaModulo.getFechaReserva(), String.valueOf(reservaModulo.getHoraInicio()), reservaModulo.getModuloLaboratorio().getSalalaboratorio().getIdsalalaboratorio());
            if (null != respuestaReserva) {
                if (respuestaReserva == true) {
                    List<ReservaModuloLaboratorio> reservaModuloLaboratorio = administrarReservasBO.obtenerCantidadReservasModuloPorParametros(reservaModulo.getFechaReserva(), String.valueOf(reservaModulo.getHoraInicio()), reservaModulo.getModuloLaboratorio().getSalalaboratorio().getIdsalalaboratorio());
                    List<ModuloLaboratorio> moduloLaboratorio = administrarReservasBO.obtenerModuloLaboratoriosPorSala(reservaModulo.getModuloLaboratorio().getSalalaboratorio().getIdsalalaboratorio());
                    int cantidadReserva = 0;
                    int cantidadModulo = 0;
                    if (Utilidades.validarNulo(reservaModuloLaboratorio)) {
                        cantidadReserva = reservaModuloLaboratorio.size();
                    }
                    if (Utilidades.validarNulo(moduloLaboratorio)) {
                        cantidadModulo = moduloLaboratorio.size();
                    }
                    if (cantidadReserva < cantidadModulo) {
                        for (int i = 0; i < cantidadModulo; i++) {
                            for (int j = 0; j < cantidadReserva; j++) {
                                if (moduloLaboratorio.get(i).getIdmodulolaboratorio().equals(reservaModuloLaboratorio.get(j).getModulolaboratorio().getIdmodulolaboratorio())) {
                                    moduloLaboratorio.remove(i);
                                }
                            }
                        }
                        reservaModulo.setModuloLaboratorio(moduloLaboratorio.get(0));
                        paginaSiguiente = "reservamodulo3";
                        registrarReservaEnSistema();
                    } else {
                        mensajeFormulario = "Los modulos de la sala asignada ya se encuentran asignados en su totalidad.";
                        colorMensaje = "red";
                    }
                } else {
                    mensajeFormulario = "Existe una reserva solicitada en el tiempo y sala de laboratorio asignado.";
                    colorMensaje = "red";
                }
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar";
            colorMensaje = "red";
        }

        return paginaSiguiente;
    }

    private void registrarReservaEnSistema() {
        try {
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            Persona personaReserva = administrarReservasBO.obtenerPersonaConsultarReservas(usuarioLoginSistema.getNombreTipoUsuario(), usuarioLoginSistema.getIdUsuarioLogin());
            //
            Reserva reservaRegistro = new Reserva();
            EstadoReserva estado = administrarReservasBO.obtenerEstadoReservaPorId(new BigInteger("1"));
            reservaRegistro.setEstadoreserva(estado);
            reservaRegistro.setFechareserva(reservaModulo.getFechaReserva());
            reservaRegistro.setHorainicio(reservaModulo.getHoraInicio());
            Integer horafinal = Integer.valueOf(reservaModulo.getHoraInicio()) + 2;
            reservaRegistro.setHorafin(String.valueOf(horafinal));
            reservaRegistro.setPersona(personaReserva);
            PeriodoAcademico periodo = administrarReservasBO.obtenerUltimoPeriodoAcademico();
            reservaRegistro.setPeriodoacademico(periodo);
            reservaRegistro.setNumeroreserva("0");
            reservaRegistro.setValorreserva(0);
            reservaRegistro.setTiporeserva(parametroTipoReserva);
            //
            ReservaModuloLaboratorio reservaModuloRegistro = new ReservaModuloLaboratorio();
            reservaModuloRegistro.setModulolaboratorio(reservaModulo.getModuloLaboratorio());
            Reserva reservaPersona = administrarReservasBO.registrarNuevaReservaModulo(reservaRegistro, reservaModuloRegistro);
            if (null != reservaPersona) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reservaPersona", reservaPersona);
            }
            limpiarDatosParaPaso3();
        } catch (Exception e) {
            System.out.println("Error ControllerPaso2Reserva registrarReservaEnSistema: " + e.toString());
        }
    }

    private void limpiarDatosParaPaso3() {
        adicionarElementos = false;
        parametroTipoReserva = null;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        validacionesTipo = false;
    }

    public String cancelarReserva() {
        adicionarElementos = false;
        parametroTipoReserva = null;

        mensajeFormulario = "N/A";
        colorMensaje = "black";
        validacionesTipo = false;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("reservaModulo");

        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("DOCENTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            return "iniciodocente";
        } else {
            if ("ESTUDIANTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                return "inicioestudiante";
            } else {
                return "iniciodocente";
            }
        }

    }

    public AyudaReservaModulo getReservaModulo() {
        return reservaModulo;
    }

    public void setReservaModulo(AyudaReservaModulo reservaModulo) {
        this.reservaModulo = reservaModulo;
    }

    public TipoReserva getParametroTipoReserva() {
        return parametroTipoReserva;
    }

    public void setParametroTipoReserva(TipoReserva parametroTipoReserva) {
        this.parametroTipoReserva = parametroTipoReserva;
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

    public boolean isValidacionesTipo() {
        return validacionesTipo;
    }

    public void setValidacionesTipo(boolean validacionesTipo) {
        this.validacionesTipo = validacionesTipo;
    }

    public boolean isAdicionarElementos() {
        return adicionarElementos;
    }

    public void setAdicionarElementos(boolean adicionarElementos) {
        this.adicionarElementos = adicionarElementos;
    }

}
