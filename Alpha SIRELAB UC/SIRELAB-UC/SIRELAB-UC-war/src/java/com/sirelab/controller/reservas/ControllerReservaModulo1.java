/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.AyudaReservaModulo;
import com.sirelab.ayuda.AyudaReservaSala;
import com.sirelab.ayuda.HoraReserva;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.ServiciosSala;
import com.sirelab.entidades.TipoReserva;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class ControllerReservaModulo1 implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private String fecha;
    private Integer fechaAnio;
    private AyudaFechaReserva fechaMes;
    private AyudaFechaReserva fechaDia;
    private List<AyudaFechaReserva> listaMeses;
    private List<AyudaFechaReserva> listaDias;
    private boolean activarSalida;
    private AyudaReservaModulo reservaModulo;
    //
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio parametroLaboratorio;
    private List<ServiciosSala> listaServicios;
    private ServiciosSala parametroServicio;
    private List<SalaLaboratorio> listaSalaLaboratorio;
    private SalaLaboratorio parametroSala;
    private boolean activarSala;
    private String mensajeFormulario, colorMensaje;
    private boolean activarHora;
    private List<HoraReserva> listaHoraReserva;
    private HoraReserva horaReserva;
    private Integer horaInicioGeneral = null;
    private Integer horaFinGeneral = null;

    public ControllerReservaModulo1() {
    }

    public void cargarInformacionReserva() {
        activarHora = true;
        activarSala = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        fecha = df.format(now);
        parametroLaboratorio = null;
        parametroSala = null;
        cargarFechaReserva();
        activarSalida = true;
    }

    private void cargarFechaReserva() {
        PeriodoAcademico ultimoPeriodoAcademico = administrarReservasBO.obtenerUltimoPeriodoAcademico();
        if (null != ultimoPeriodoAcademico) {
            Date fechaInicio = ultimoPeriodoAcademico.getFechainicial();
            Date fechaFin = ultimoPeriodoAcademico.getFechafinal();
            Date fechaHoy = new Date();
            if (fechaHoy.after(fechaInicio) && fechaHoy.before(fechaFin)) {
                fechaAnio = fechaInicio.getYear() + 1900;
                listaMeses = new ArrayList<AyudaFechaReserva>();
                fechaMes = new AyudaFechaReserva();
                fechaMes.setParametro(fechaHoy.getMonth());
                int mes = fechaHoy.getMonth() + 1;
                fechaMes.setMensajeMostrar(String.valueOf(mes));
                listaMeses.add(fechaMes);
                fechaDia = new AyudaFechaReserva();
                fechaDia.setMensajeMostrar(fechaHoy.getDate() + ":00");
                fechaDia.setParametro(fechaHoy.getDate());
                listaDias = new ArrayList<AyudaFechaReserva>();
                listaDias.add(fechaDia);
            }
        }
    }

    private boolean validarHora() {
        boolean retorno = true;
        if (!Utilidades.validarNulo(horaReserva)) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarLaboratorio() {
        boolean retorno = true;
        if (!Utilidades.validarNulo(parametroLaboratorio)) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarSala() {
        boolean retorno = true;
        if (!Utilidades.validarNulo(parametroSala)) {
            retorno = false;
        }
        activarHoraReserva();
        return retorno;
    }

    public void limpiarInformacionLaboratorio() {
        parametroLaboratorio = null;
        parametroSala = null;
        listaSalaLaboratorio = null;
        activarSala = true;
        activarHora = true;
        parametroServicio = null;
        activarSalida = true;
    }

    public void activarHoraReserva() {
        boolean validarSala = false;
        if (parametroSala == null) {
            validarSala = false;
            activarHora = true;
        } else {
            validarSala = true;
            activarHora = false;
        }
        if (validarSala) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaAnio);
            cal.set(Calendar.MONTH, fechaMes.getParametro());
            cal.set(Calendar.DATE, fechaDia.getParametro());
            int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
            listaHoraReserva = new ArrayList<HoraReserva>();
            if (diaSemana != 1) {
                List<ReservaSala> reservasModulo = administrarReservasBO.obtenerReservasModuloSalas(cal.getTime(), parametroSala.getIdsalalaboratorio());
                if (null != reservasModulo) {
                    for (int i = 0; i < reservasModulo.size(); i++) {
                        HoraReserva hora = new HoraReserva();
                        int horaInicio = Integer.valueOf(reservasModulo.get(i).getReserva().getHorainicio());
                        int horaFin = Integer.valueOf(reservasModulo.get(i).getReserva().getHorainicio());
                        Date fechaHoy = new Date();
                        if (horaFin < fechaHoy.getHours()) {
                            hora.setHora(horaInicio);
                            hora.setHoraModuloFin(horaFin);
                            hora.setHoraModuloInicio(horaInicio);
                            hora.setHoraMostrar(horaInicio + ":00 - " + horaFin + ":00");
                            listaHoraReserva.add(hora);
                        }
                    }
                    activarHora = false;
                }
            }
        }
    }

    private boolean validarCamposReserva() {
        boolean retorno = true;
        if (validarSala() == false) {
            retorno = false;
        }
        if (validarLaboratorio() == false) {
            retorno = false;
        }
        if (validarHora() == false) {
            retorno = false;
        }
        return retorno;
    }

    public String consultarReservaARealizar() {
        String paso2 = "";
        if (validarCamposReserva() == true) {
            Date fechaReserva = null;
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaAnio);
            cal.set(Calendar.MONTH, fechaMes.getParametro());
            cal.set(Calendar.DATE, fechaDia.getParametro());
            fechaReserva = cal.getTime();
            formateador.format(fechaReserva);
            List<ReservaModuloLaboratorio> reservaModuloLaboratorio = administrarReservasBO.obtenerCantidadReservasModuloPorParametros(fechaReserva, horaReserva.getHoraModuloInicio().toString(), parametroSala.getIdsalalaboratorio());
            List<ModuloLaboratorio> moduloLaboratorio = administrarReservasBO.obtenerModuloLaboratoriosPorSala(parametroSala.getIdsalalaboratorio());
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
                AyudaReservaModulo.getInstance().setFechaReserva(fechaReserva);
                AyudaReservaModulo.getInstance().setModuloLaboratorio(moduloLaboratorio.get(0));
                AyudaReservaModulo.getInstance().setHoraInicio(String.valueOf(horaReserva.getHoraModuloInicio()));
                AyudaReservaModulo.getInstance().setHoraFin(String.valueOf(horaReserva.getHoraModuloFin()));
                AyudaReservaModulo.getInstance().setServicioSala(parametroServicio);
                paso2 = "reservamodulo3";
                registrarReservaEnSistema();
                limpiarInformacion();
            } else {
                mensajeFormulario = "Los modulos de la sala asignada ya se encuentran asignados en su totalidad. Intente en otro horario";
                colorMensaje = "#FF0000";
            }

        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar";
            colorMensaje = "#FF0000";
        }
        return paso2;
    }

    private void registrarReservaEnSistema() {
        try {
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            Persona personaReserva = administrarReservasBO.obtenerPersonaConsultarReservas(usuarioLoginSistema.getNombreTipoUsuario(), usuarioLoginSistema.getIdUsuarioLogin());
            //
            Reserva reservaRegistro = new Reserva();
            EstadoReserva estado = administrarReservasBO.obtenerEstadoReservaPorId(new BigInteger("1"));
            reservaRegistro.setEstadoreserva(estado);
            reservaRegistro.setServiciosala(AyudaReservaModulo.getInstance().getServicioSala());
            reservaRegistro.setFechareserva(AyudaReservaModulo.getInstance().getFechaReserva());
            reservaRegistro.setHorainicio(AyudaReservaModulo.getInstance().getHoraInicio());
            reservaRegistro.setHorafin(AyudaReservaModulo.getInstance().getHoraFin());
            reservaRegistro.setPersona(personaReserva);
            PeriodoAcademico periodo = administrarReservasBO.obtenerUltimoPeriodoAcademico();
            reservaRegistro.setPeriodoacademico(periodo);
            Integer numeroReserva = administrarReservasBO.obtenerNumeroReservaDia(AyudaReservaModulo.getInstance().getFechaReserva());
            SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
            String fechaStr = formato.format(AyudaReservaModulo.getInstance().getFechaReserva());
            if (null != numeroReserva) {
                String numReserva = fechaStr + " - " + numeroReserva.toString();
                reservaRegistro.setNumeroreserva(numReserva);
            } else {
                String numReserva = fechaStr + " - " + 1;
                reservaRegistro.setNumeroreserva(numReserva);
            }
            reservaRegistro.setValorreserva(0);
            TipoReserva parametroTipoReserva = null;
            if ("DOCENTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                parametroTipoReserva = administrarReservasBO.obtenerTipoReservaPorId(new BigInteger("2"));
            } else {
                if ("ESTUDIANTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                    parametroTipoReserva = administrarReservasBO.obtenerTipoReservaPorId(new BigInteger("2"));
                } else {
                    parametroTipoReserva = administrarReservasBO.obtenerTipoReservaPorId(new BigInteger("3"));
                }
            }
            reservaRegistro.setTiporeserva(parametroTipoReserva);
            //
            ReservaModuloLaboratorio reservaModuloRegistro = new ReservaModuloLaboratorio();
            reservaModuloRegistro.setModulolaboratorio(AyudaReservaModulo.getInstance().getModuloLaboratorio());
            Reserva reservaPersona = administrarReservasBO.registrarNuevaReservaModulo(reservaRegistro, reservaModuloRegistro);
            AyudaReservaModulo.getInstance().setReserva(reservaPersona);
        } catch (Exception e) {
            
        }
    }

    public void actualizarLaboratorioYServicio() {
        boolean laboratorio = Utilidades.validarNulo(parametroLaboratorio);
        boolean servicio = Utilidades.validarNulo(parametroServicio);
        if (laboratorio == true && servicio == true) {
            parametroSala = new SalaLaboratorio();
            listaSalaLaboratorio = administrarReservasBO.consultarSalaLaboratorioPorIdLaboratorioYServicio(parametroLaboratorio.getIdlaboratorio(), parametroServicio.getIdserviciossala());
            activarSala = false;
        } else {
            if (laboratorio == true && servicio == false) {
                parametroSala = new SalaLaboratorio();
                listaSalaLaboratorio = administrarReservasBO.consultarSalaLaboratorioPorIdLaboratorioReserva(parametroLaboratorio.getIdlaboratorio());
                activarSala = false;
            } else {
                parametroSala = new SalaLaboratorio();
                activarSala = true;
                listaSalaLaboratorio = null;
            }
        }
        activarHora = true;
    }

    public void actualizarServicioSala() {
        if (Utilidades.validarNulo(parametroLaboratorio)) {
            parametroSala = new SalaLaboratorio();
            listaSalaLaboratorio = administrarReservasBO.consultarSalaLaboratorioPorIdLaboratorio(parametroLaboratorio.getIdlaboratorio());
            activarSala = false;
        } else {
            parametroSala = new SalaLaboratorio();
            activarSala = true;
            listaSalaLaboratorio = null;
        }
    }

    public AyudaReservaModulo enviarAyudaReservaModuloPasoSiguiente() {
        return reservaModulo;
    }

    private void limpiarInformacion() {
        activarSala = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        fecha = df.format(now);
        listaLaboratorios = null;
        listaSalaLaboratorio = null;
        parametroLaboratorio = null;
        parametroSala = null;
        activarHora = true;
    }

    public String cancelarProcesoReserva() {
        limpiarInformacion();
        reservaModulo = null;
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

    public List<Laboratorio> getListaLaboratorios() {
        if (null == listaLaboratorios) {
            listaLaboratorios = administrarReservasBO.consultarLaboratoriosRegistradosActivos();
        }
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getParametroLaboratorio() {
        return parametroLaboratorio;
    }

    public void setParametroLaboratorio(Laboratorio parametroLaboratorio) {
        this.parametroLaboratorio = parametroLaboratorio;
    }

    public List<SalaLaboratorio> getListaSalaLaboratorio() {
        return listaSalaLaboratorio;
    }

    public void setListaSalaLaboratorio(List<SalaLaboratorio> listaSalaLaboratorio) {
        this.listaSalaLaboratorio = listaSalaLaboratorio;
    }

    public SalaLaboratorio getParametroSala() {
        return parametroSala;
    }

    public void setParametroSala(SalaLaboratorio parametroSala) {
        this.parametroSala = parametroSala;
    }

    public boolean isActivarSala() {
        return activarSala;
    }

    public void setActivarSala(boolean activarSala) {
        this.activarSala = activarSala;
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

    public boolean isActivarHora() {
        return activarHora;
    }

    public void setActivarHora(boolean activarHora) {
        this.activarHora = activarHora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<HoraReserva> getListaHoraReserva() {
        return listaHoraReserva;
    }

    public void setListaHoraReserva(List<HoraReserva> listaHoraReserva) {
        this.listaHoraReserva = listaHoraReserva;
    }

    public HoraReserva getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(HoraReserva horaReserva) {
        this.horaReserva = horaReserva;
    }

    public Integer getFechaAnio() {
        return fechaAnio;
    }

    public void setFechaAnio(Integer fechaAnio) {
        this.fechaAnio = fechaAnio;
    }

    public AyudaFechaReserva getFechaMes() {
        return fechaMes;
    }

    public void setFechaMes(AyudaFechaReserva fechaMes) {
        this.fechaMes = fechaMes;
    }

    public AyudaFechaReserva getFechaDia() {
        return fechaDia;
    }

    public void setFechaDia(AyudaFechaReserva fechaDia) {
        this.fechaDia = fechaDia;
    }

    public List<AyudaFechaReserva> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(List<AyudaFechaReserva> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public List<AyudaFechaReserva> getListaDias() {
        return listaDias;
    }

    public void setListaDias(List<AyudaFechaReserva> listaDias) {
        this.listaDias = listaDias;
    }

    public List<ServiciosSala> getListaServicios() {
        if (null == listaServicios) {
            listaServicios = administrarReservasBO.listaServiciosSalaActivos();
        }
        return listaServicios;
    }

    public void setListaServicios(List<ServiciosSala> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public ServiciosSala getParametroServicio() {
        return parametroServicio;
    }

    public void setParametroServicio(ServiciosSala parametroServicio) {
        this.parametroServicio = parametroServicio;
    }

    public boolean isActivarSalida() {
        return activarSalida;
    }

    public void setActivarSalida(boolean activarSalida) {
        this.activarSalida = activarSalida;
    }

    public Integer getHoraInicioGeneral() {
        return horaInicioGeneral;
    }

    public void setHoraInicioGeneral(Integer horaInicioGeneral) {
        this.horaInicioGeneral = horaInicioGeneral;
    }

    public Integer getHoraFinGeneral() {
        return horaFinGeneral;
    }

    public void setHoraFinGeneral(Integer horaFinGeneral) {
        this.horaFinGeneral = horaFinGeneral;
    }

}
