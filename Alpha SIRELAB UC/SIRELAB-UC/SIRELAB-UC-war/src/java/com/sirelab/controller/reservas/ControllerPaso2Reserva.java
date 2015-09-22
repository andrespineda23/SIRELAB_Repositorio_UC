/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaSala;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.GuiaLaboratorio;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaSala;
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
public class ControllerPaso2Reserva implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private AyudaReservaSala reservaSala;
    private List<TipoReserva> listaTipoReserva;
    private TipoReserva parametroTipoReserva;
    private List<PlanEstudios> listaPlanEstudios;
    private PlanEstudios parametroPlanEstudios;
    private List<AsignaturaPorPlanEstudio> listaAsignaturaPorPlanEstudio;
    private AsignaturaPorPlanEstudio parametroAsignaturaPorPlanEstudio;
    private boolean activarAsignatura;
    private List<GuiaLaboratorio> listaGuiaLaboratorio;
    private GuiaLaboratorio parametroGuiaLaboratorio;
    private boolean activarGuia;
    private String mensajeFormulario, colorMensaje;
    private boolean validacionesTipo, validacionesGuia, validacionesAsignatura, validacionesPlan;
    private boolean adicionarElementos;

    public ControllerPaso2Reserva() {
    }

    @PostConstruct
    public void init() {
        adicionarElementos = false;
        reservaSala = (AyudaReservaSala) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reservaSala");
        parametroTipoReserva = null;
        parametroGuiaLaboratorio = null;
        parametroAsignaturaPorPlanEstudio = null;
        parametroPlanEstudios = null;
        activarAsignatura = true;
        activarGuia = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        validacionesTipo = false;
        validacionesGuia = false;
        validacionesAsignatura = false;
        validacionesPlan = false;
    }

    public void actualizarPlanEstudios() {
        if (Utilidades.validarNulo(parametroPlanEstudios)) {
            parametroAsignaturaPorPlanEstudio = null;
            listaAsignaturaPorPlanEstudio = administrarReservasBO.consultarAsignaturasPorPlanEstudioPorIdPlan(parametroPlanEstudios.getIdplanestudios());
            activarAsignatura = false;
            activarGuia = true;
            validacionesGuia = false;
            validacionesPlan = true;
            validacionesAsignatura = false;
        } else {
            validacionesPlan = false;
            validacionesGuia = false;
            validacionesAsignatura = false;

            listaAsignaturaPorPlanEstudio = null;
            listaGuiaLaboratorio = null;

            parametroAsignaturaPorPlanEstudio = null;
            parametroGuiaLaboratorio = null;

            activarAsignatura = true;
            activarGuia = true;
        }
    }

    public void actualizaAsignatura() {
        if (Utilidades.validarNulo(parametroAsignaturaPorPlanEstudio)) {
            parametroGuiaLaboratorio = null;
            listaGuiaLaboratorio = administrarReservasBO.consultarGuiasLaboratorioPorIdAreaPlan(parametroAsignaturaPorPlanEstudio.getIdasignaturaporplanestudio());
            activarGuia = false;
            validacionesAsignatura = true;
            validacionesGuia = false;
        } else {
            validacionesGuia = false;
            validacionesAsignatura = false;

            listaGuiaLaboratorio = null;

            parametroGuiaLaboratorio = null;

            activarGuia = true;
        }
    }

    public void actualizarGuia() {
        if (Utilidades.validarNulo(parametroGuiaLaboratorio)) {
            validacionesGuia = true;
        } else {
            validacionesGuia = false;
        }
    }

    public void actualizarTipo() {
        if (Utilidades.validarNulo(parametroTipoReserva)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesAsignatura == false) {
            retorno = false;
        }
        if (validacionesGuia == false) {
            retorno = false;
        }
        if (validacionesPlan == false) {
            retorno = false;
        }
        if (validacionesTipo == false) {
            retorno = false;
        }
        return retorno;
    }

    public String finalizarProcesoReserva() {
        String paginaSiguiente = "";
        if (validarResultadosValidacion() == true) {
            Boolean respuestaReserva = administrarReservasBO.validarReservaSalaDisposible(reservaSala.getFechaReserva(), reservaSala.getHoraInicio(), reservaSala.getSalaLaboratorio().getIdsalalaboratorio());
            if (null != respuestaReserva) {
                if (respuestaReserva == true) {
                    //Proceso siguiente de la reservaRegistro
                    registrarReservaEnSistema();
                    if (adicionarElementos == false) {
                        paginaSiguiente = "paso3Reserva";
                    } else {
                        paginaSiguiente = "paso3Reserva";
                    }
                } else {
                    mensajeFormulario = "La reservaRegistro que ha solicitado ya ha sido asignada a otro usuario.";
                    colorMensaje = "red";
                }
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario. Por favor, solucionar para continuar";
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
            reservaRegistro.setFechareserva(reservaSala.getFechaReserva());
            reservaRegistro.setHorainicio(reservaSala.getHoraInicio());
            Integer horafinal = Integer.valueOf(reservaSala.getHoraInicio()) + 2;
            reservaRegistro.setHorafin(String.valueOf(horafinal));
            reservaRegistro.setPersona(personaReserva);
            PeriodoAcademico periodo = administrarReservasBO.obtenerUltimoPeriodoAcademico();
            reservaRegistro.setPeriodoacademico(periodo);
            reservaRegistro.setNumeroreserva("0");
            reservaRegistro.setValorreserva(0);
            reservaRegistro.setTiporeserva(parametroTipoReserva);
            //
            ReservaSala reservaSalaRegistro = new ReservaSala();
            reservaSalaRegistro.setAsignaturaporplanestudio(parametroAsignaturaPorPlanEstudio);
            reservaSalaRegistro.setGuialaboratorio(parametroGuiaLaboratorio);
            reservaSalaRegistro.setSalalaboratorio(reservaSala.getSalaLaboratorio());
            reservaSalaRegistro.setTiporeservasala(parametroTipoReserva.getNombretiporeserva());
            Reserva reservaPersona = administrarReservasBO.registrarNuevaReservaSala(reservaRegistro, reservaSalaRegistro);
            if (null != reservaPersona) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reservaPersona", reservaPersona);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("asignaturaReserva", parametroAsignaturaPorPlanEstudio.getAsignatura().getNombreasignatura());
            }
            limpiarDatosParaPaso3();
        } catch (Exception e) {
            System.out.println("Error ControllerPaso2Reserva registrarReservaEnSistema: " + e.toString());
        }
    }

    private void limpiarDatosParaPaso3() {
        adicionarElementos = false;
        parametroTipoReserva = null;
        parametroGuiaLaboratorio = null;
        parametroAsignaturaPorPlanEstudio = null;
        parametroPlanEstudios = null;
        activarAsignatura = true;
        activarGuia = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        validacionesTipo = false;
        validacionesGuia = false;
        validacionesAsignatura = false;
        validacionesPlan = false;
        listaAsignaturaPorPlanEstudio = null;
        listaGuiaLaboratorio = null;
        listaPlanEstudios = null;
        listaTipoReserva = null;
    }

    public void cancelarReserva() {
        adicionarElementos = false;
        parametroTipoReserva = null;
        parametroGuiaLaboratorio = null;
        parametroAsignaturaPorPlanEstudio = null;
        parametroPlanEstudios = null;
        activarAsignatura = true;
        activarGuia = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        validacionesTipo = false;
        validacionesGuia = false;
        validacionesAsignatura = false;
        validacionesPlan = false;
        listaAsignaturaPorPlanEstudio = null;
        listaGuiaLaboratorio = null;
        listaPlanEstudios = null;
        listaTipoReserva = null;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("reservaSala");
    }

    public void descargarGuiaLaboratorio(){}
    
    public AyudaReservaSala getReservaSala() {
        return reservaSala;
    }

    public void setReservaSala(AyudaReservaSala reservaSala) {
        this.reservaSala = reservaSala;
    }

    public List<TipoReserva> getListaTipoReserva() {
        if (null == listaTipoReserva) {
            listaTipoReserva = administrarReservasBO.consultarTiposReservas();
        }
        return listaTipoReserva;
    }

    public void setListaTipoReserva(List<TipoReserva> listaTipoReserva) {
        this.listaTipoReserva = listaTipoReserva;
    }

    public TipoReserva getParametroTipoReserva() {
        return parametroTipoReserva;
    }

    public void setParametroTipoReserva(TipoReserva parametroTipoReserva) {
        this.parametroTipoReserva = parametroTipoReserva;
    }

    public List<GuiaLaboratorio> getListaGuiaLaboratorio() {
        return listaGuiaLaboratorio;
    }

    public void setListaGuiaLaboratorio(List<GuiaLaboratorio> listaGuiaLaboratorio) {
        this.listaGuiaLaboratorio = listaGuiaLaboratorio;
    }

    public GuiaLaboratorio getParametroGuiaLaboratorio() {
        return parametroGuiaLaboratorio;
    }

    public void setParametroGuiaLaboratorio(GuiaLaboratorio parametroGuiaLaboratorio) {
        this.parametroGuiaLaboratorio = parametroGuiaLaboratorio;
    }

    public List<AsignaturaPorPlanEstudio> getListaAsignaturaPorPlanEstudio() {
        return listaAsignaturaPorPlanEstudio;
    }

    public void setListaAsignaturaPorPlanEstudio(List<AsignaturaPorPlanEstudio> listaAsignaturaPorPlanEstudio) {
        this.listaAsignaturaPorPlanEstudio = listaAsignaturaPorPlanEstudio;
    }

    public AsignaturaPorPlanEstudio getParametroAsignaturaPorPlanEstudio() {
        return parametroAsignaturaPorPlanEstudio;
    }

    public void setParametroAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio parametroAsignaturaPorPlanEstudio) {
        this.parametroAsignaturaPorPlanEstudio = parametroAsignaturaPorPlanEstudio;
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

    public List<PlanEstudios> getListaPlanEstudios() {
        if (null == listaPlanEstudios) {
            listaPlanEstudios = administrarReservasBO.consultarPlanEstudiosActivos();
        }
        return listaPlanEstudios;
    }

    public void setListaPlanEstudios(List<PlanEstudios> listaPlanEstudios) {
        this.listaPlanEstudios = listaPlanEstudios;
    }

    public PlanEstudios getParametroPlanEstudios() {
        return parametroPlanEstudios;
    }

    public void setParametroPlanEstudios(PlanEstudios parametroPlanEstudios) {
        this.parametroPlanEstudios = parametroPlanEstudios;
    }

    public boolean isActivarAsignatura() {
        return activarAsignatura;
    }

    public void setActivarAsignatura(boolean activarAsignatura) {
        this.activarAsignatura = activarAsignatura;
    }

    public boolean isActivarGuia() {
        return activarGuia;
    }

    public void setActivarGuia(boolean activarGuia) {
        this.activarGuia = activarGuia;
    }

    public boolean isAdicionarElementos() {
        return adicionarElementos;
    }

    public void setAdicionarElementos(boolean adicionarElementos) {
        this.adicionarElementos = adicionarElementos;
    }

}
