/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.AyudaReservaModulo;
import com.sirelab.ayuda.HoraReserva;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ControllerReservaModulo1 implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private Date fecha;
    private Integer fechaAnio;
    private AyudaFechaReserva fechaMes;
    private AyudaFechaReserva fechaDia;
    private List<AyudaFechaReserva> listaMeses;
    private List<AyudaFechaReserva> listaDias;

    private List<HoraReserva> listaHoraReserva;
    private HoraReserva horaIngreso;
    //
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio parametroLaboratorio;
    private List<SalaLaboratorio> listaSalaLaboratorio;
    private SalaLaboratorio parametroSala;
    private boolean activarSala;
    private String mensajeFormulario, colorMensaje;
    private boolean activarHora;

    public ControllerReservaModulo1() {
    }

    @PostConstruct
    public void init() {
        activarHora = true;
        activarSala = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        fecha = null;
        parametroLaboratorio = null;
        parametroSala = null;
        cargarFechaReserva();
    }

    private void cargarFechaReserva() {
        PeriodoAcademico ultimoPeriodoAcademico = administrarReservasBO.obtenerUltimoPeriodoAcademico();
        if (null != ultimoPeriodoAcademico) {
            Date fechaInicio = ultimoPeriodoAcademico.getFechainicial();
            Date fechaFin = ultimoPeriodoAcademico.getFechafinal();
            Date fechaHoy = new Date();
            if (fechaHoy.after(fechaInicio) && fechaHoy.before(fechaFin)) {
                System.out.println("Fecha bien");
                fechaAnio = fechaInicio.getYear() + 1900;
                listaMeses = new ArrayList<AyudaFechaReserva>();
                for (int i = fechaInicio.getMonth(); i < fechaFin.getMonth() + 1; i++) {
                    AyudaFechaReserva ayuda = new AyudaFechaReserva();
                    ayuda.setParametro(i);
                    int mes = i + 1;
                    ayuda.setMensajeMostrar(String.valueOf(mes));
                    listaMeses.add(ayuda);
                }
                if (!listaMeses.isEmpty()) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fechaInicio);
                    int day = cal.get(Calendar.DATE);
                    listaMeses.get(0).setDiaInicio(day);
                    cal.setTime(fechaFin);
                    day = cal.get(Calendar.DATE);
                    int tam = listaMeses.size() - 1;
                    listaMeses.get(tam).setDiaFin(day);
                }
                fechaMes = obtenerMesExacto(fechaHoy.getMonth());
                actualizarInformacionDia();
                fechaDia = obtenerDiaExacto(fechaHoy.getDate());
            }
        }
    }

    private AyudaFechaReserva obtenerMesExacto(int mes) {
        AyudaFechaReserva ayuda = null;
        for (int i = 0; i < listaMeses.size(); i++) {
            if (mes == listaMeses.get(i).getParametro()) {
                ayuda = listaMeses.get(i);
                break;
            }
        }
        return ayuda;
    }

    private AyudaFechaReserva obtenerDiaExacto(int dia) {
        AyudaFechaReserva ayuda = null;
        for (int i = 0; i < listaDias.size(); i++) {
            if (dia == listaDias.get(i).getParametro()) {
                ayuda = listaDias.get(i);
                break;
            }
        }
        return ayuda;
    }

    public void actualizarInformacionDia() {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.set(Integer.valueOf(fechaAnio), fechaMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        if (null != fechaMes.getDiaInicio()) {
            diaInicio = fechaMes.getDiaInicio();
        }
        if (null != fechaMes.getDiaFin()) {
            diaFin = fechaMes.getDiaFin();
        }
        listaDias = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDias.add(ayuda);
        }
    }

    private boolean validarHora() {
        boolean retorno = true;
        if (!Utilidades.validarNulo(horaIngreso)) {
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
        return retorno;
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
            horaIngreso = null;
            if (diaSemana != 1) {
                if (diaSemana == 7) {
                    Integer horaInicio = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoraaperturasabado());
                    Integer horaFin = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoracierresabado());

                    int horaBloque = Integer.valueOf(parametroSala.getLaboratorio().getBloquehorareserva());
                    for (int i = horaInicio; i < horaFin - horaBloque; i++) {
                        HoraReserva hora = new HoraReserva();
                        hora.setHora(i);
                        hora.setHoraMostrar(i + ":00");
                        listaHoraReserva.add(hora);
                    }
                } else {
                    Integer horaInicio = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoraapertura());
                    Integer horaFin = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoracierre());
                    horaIngreso = null;
                    listaHoraReserva = new ArrayList<HoraReserva>();
                    int horaBloque = Integer.valueOf(parametroSala.getLaboratorio().getBloquehorareserva());
                    for (int i = horaInicio; i < horaFin - horaBloque; i++) {
                        HoraReserva hora = new HoraReserva();
                        hora.setHora(i);
                        hora.setHoraMostrar(i + ":00");
                        listaHoraReserva.add(hora);
                    }
                }
                activarHora = false;
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
            Boolean respuestaReserva = administrarReservasBO.validarReservaSalaDisposible(fechaReserva, String.valueOf(horaIngreso), parametroSala.getIdsalalaboratorio());
            if (null != respuestaReserva) {
                if (respuestaReserva == true) {
                    List<ReservaModuloLaboratorio> reservaModuloLaboratorio = administrarReservasBO.obtenerCantidadReservasModuloPorParametros(fechaReserva, String.valueOf(horaIngreso), parametroSala.getIdsalalaboratorio());
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

                        AyudaReservaModulo reservaModulo = new AyudaReservaModulo();
                        reservaModulo.setFechaReserva(fechaReserva);
                        reservaModulo.setModuloLaboratorio(moduloLaboratorio.get(0));
                        reservaModulo.setHoraInicio(String.valueOf(horaIngreso));
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reservaModulo", reservaModulo);
                        paso2 = "reservamodulo2";
                        limpiarInformacion();
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
        return paso2;
    }

    public void actualizarLaboratorio() {
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

    private void limpiarInformacion() {
        activarSala = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        fecha = null;
        listaHoraReserva = null;
        horaIngreso = null;
        listaLaboratorios = null;
        listaSalaLaboratorio = null;
        parametroLaboratorio = null;
        parametroSala = null;
        horaIngreso = null;
        activarHora = true;
    }

    public String cancelarProcesoReserva() {
        limpiarInformacion();
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

}
