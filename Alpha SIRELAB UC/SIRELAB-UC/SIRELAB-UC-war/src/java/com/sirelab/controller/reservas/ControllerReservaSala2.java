/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaModulo;
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
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerReservaSala2 implements Serializable {

    static Logger logger = Logger.getLogger(ControllerReservaSala2.class);

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private AyudaReservaSala reservaSala;
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

    public ControllerReservaSala2() {
        reservaSala = AyudaReservaSala.getInstance();
    }

    @PostConstruct
    public void init() {
        adicionarElementos = false;
        parametroTipoReserva = null;
        parametroGuiaLaboratorio = null;
        parametroAsignaturaPorPlanEstudio = null;
        parametroPlanEstudios = null;
        activarAsignatura = true;
        activarGuia = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        validacionesTipo = true;
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
                    //Boolean respuesta2 = administrarReservasBO.validarReservaModuloSalaDisposible(reservaSala.getFechaReserva(), reservaSala.getHoraInicio(), reservaSala.getSalaLaboratorio().getIdsalalaboratorio());
                    //if (null != respuesta2) {
                        //if (respuesta2 == true) {
                            //Proceso siguiente de la reservaRegistro
                            registrarReservaEnSistema();
                            if (adicionarElementos == false) {
                                paginaSiguiente = "reservasala3";
                            } else {
                                paginaSiguiente = "reservasala3";
                            }
                        //} else {
                        //    mensajeFormulario = "La reserva que ha solicitado ya ha sido asignada a otro usuario.";
                        //    colorMensaje = "red";
                        //}
                    //}
                } else {
                    mensajeFormulario = "La reserva que ha solicitado ya ha sido asignada a otro usuario.";
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
            Integer numeroReserva = administrarReservasBO.obtenerNumeroReservaDia(AyudaReservaSala.getInstance().getFechaReserva());
            SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
            String fechaStr = formato.format(AyudaReservaSala.getInstance().getFechaReserva());
            if (null != numeroReserva) {
                String numReserva = fechaStr + " - " + numeroReserva.toString();
                reservaRegistro.setNumeroreserva(numReserva);
            } else {
                String numReserva = fechaStr + " - " + 1;
                reservaRegistro.setNumeroreserva(numReserva);
            }
            reservaRegistro.setValorreserva(0);
            reservaRegistro.setTiporeserva(parametroTipoReserva);
            reservaRegistro.setServiciosala(reservaSala.getServicioSala());
            //
            ReservaSala reservaSalaRegistro = new ReservaSala();
            reservaSalaRegistro.setAsignaturaporplanestudio(parametroAsignaturaPorPlanEstudio);
            reservaSalaRegistro.setGuialaboratorio(parametroGuiaLaboratorio);
            reservaSalaRegistro.setSalalaboratorio(reservaSala.getSalaLaboratorio());
            reservaSalaRegistro.setTiporeservasala(parametroTipoReserva.getNombretiporeserva());
            Reserva reservaPersona = administrarReservasBO.registrarNuevaReservaSala(reservaRegistro, reservaSalaRegistro);

            if (null != reservaPersona) {
                AyudaReservaSala.getInstance().setReserva(reservaPersona);
                AyudaReservaSala.getInstance().setNombreAsignatura(parametroAsignaturaPorPlanEstudio.getAsignatura().getNombreasignatura());
                AyudaReservaSala.getInstance().setRutaGuia(parametroGuiaLaboratorio.getUbicacionguia());
                System.out.println("guardo la info en la segunda parte: " + AyudaReservaSala.getInstance().getNombreAsignatura());
            }
            limpiarDatosParaPaso3();
        } catch (Exception e) {
            logger.error("Error ControllerPaso2Reserva registrarReservaEnSistema: " + e.toString(), e);
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
    }

    private void limpiarInformacion() {
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
    }

    public String cancelarReserva() {
        limpiarInformacion();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("reservaSala");
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

    public AyudaReservaSala enviarAyudaReservaSalaPasoSiguiente() {
        return reservaSala;
    }

    public void descargarGuiaLaboratorio() {
    }

    public AyudaReservaSala getReservaSala() {
        return reservaSala;
    }

    public void setReservaSala(AyudaReservaSala reservaSala) {
        this.reservaSala = reservaSala;
    }

    public TipoReserva getParametroTipoReserva() {
        if (null == parametroTipoReserva) {
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            if ("DOCENTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                parametroTipoReserva = administrarReservasBO.obtenerTipoReservaPorId(new BigInteger("1"));
            } else {
                if ("ENTIDADEXTERNA".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                    parametroTipoReserva = administrarReservasBO.obtenerTipoReservaPorId(new BigInteger("3"));
                }
            }
        }
        actualizarTipo();
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
