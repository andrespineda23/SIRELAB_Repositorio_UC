/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.AyudaReservaSala;
import com.sirelab.ayuda.HoraReserva;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.ServiciosSala;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.text.DateFormat;
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
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerReservaSala1 implements Serializable {

    static Logger logger = Logger.getLogger(ControllerReservaSala1.class);

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private String fecha;
    private Integer fechaAnio;
    private AyudaFechaReserva fechaMes;
    private AyudaFechaReserva fechaDia;
    private List<AyudaFechaReserva> listaMeses;
    private List<AyudaFechaReserva> listaDias;
    private List<Integer> listaHora;
    private Integer horaIngreso;
    private boolean activarSalida;
    private AyudaReservaSala reservaSala;
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
    private List<HoraReserva> listaReservaSalida;
    private HoraReserva horaReservaSalida;
    private Integer horaInicioGeneral = null;
    private Integer horaFinGeneral = null;

    public ControllerReservaSala1() {
    }

    @PostConstruct
    public void init() {

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
        listaReservaSalida = null;
        activarSalida = true;
        horaReservaSalida = null;
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
        listaReservaSalida = null;
        activarSalida = true;
        horaReservaSalida = null;
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
                    horaInicioGeneral = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoraaperturasabado());
                    horaFinGeneral = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoracierresabado());
                    for (int i = horaInicioGeneral; i < horaFinGeneral; i++) {
                        HoraReserva hora = new HoraReserva();
                        hora.setHora(i);
                        hora.setHoraMostrar(i + ":00");
                        listaHoraReserva.add(hora);
                    }

                    List<ReservaSala> reservas = administrarReservasBO.obtenerReservasSalaPorParametros(parametroSala, cal.getTime());
                    List<HoraReserva> listaReservado = new ArrayList<HoraReserva>();
                    if (null != reservas) {
                        for (ReservaSala reserva : reservas) {
                            int horaInicio = Integer.valueOf(reserva.getReserva().getHorainicio());
                            int horaFin = Integer.valueOf(reserva.getReserva().getHorafin());
                            for (int i = horaInicio; i < horaFin; i++) {
                                HoraReserva hora = new HoraReserva();
                                hora.setHora(i);
                                hora.setHoraMostrar(i + ":00");
                                listaReservado.add(hora);
                            }
                        }
                        for (HoraReserva horaReserva : listaReservado) {
                            for (HoraReserva horaSolicitar : listaHoraReserva) {
                                if (horaReserva.getHora() == horaSolicitar.getHora()) {
                                    listaHoraReserva.remove(horaSolicitar);
                                }
                            }
                        }
                    }

                } else {
                    horaInicioGeneral = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoraapertura());
                    horaFinGeneral = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoracierre());
                    horaIngreso = null;
                    listaHoraReserva = new ArrayList<HoraReserva>();
                    for (int i = horaInicioGeneral; i < horaFinGeneral; i++) {
                        HoraReserva hora = new HoraReserva();
                        hora.setHora(i);
                        hora.setHoraMostrar(i + ":00");
                        listaHoraReserva.add(hora);
                    }

                    List<ReservaSala> reservas = administrarReservasBO.obtenerReservasSalaPorParametros(parametroSala, cal.getTime());
                    List<HoraReserva> listaReservado = new ArrayList<HoraReserva>();
                    if (null != reservas) {
                        for (ReservaSala reserva : reservas) {
                            int horaInicio = Integer.valueOf(reserva.getReserva().getHorainicio());
                            int horaFin = Integer.valueOf(reserva.getReserva().getHorafin());
                            for (int i = horaInicio; i < horaFin; i++) {
                                HoraReserva hora = new HoraReserva();
                                hora.setHora(i);
                                hora.setHoraMostrar(i + ":00");
                                listaReservado.add(hora);
                            }
                        }
                        for (HoraReserva horaReserva : listaReservado) {
                            for (int i = 0; i < listaHoraReserva.size(); i++) {
                                HoraReserva horaSolicitar = listaHoraReserva.get(i);
                                if (horaReserva.getHora() == horaSolicitar.getHora()) {
                                    listaHoraReserva.remove(horaSolicitar);
                                }
                            }
                        }
                    }

                }
                activarHora = false;
            }
        }
    }

    public void actualizarHoraIngresoReserva() {
        if (null != horaReserva) {
            listaReservaSalida = new ArrayList<HoraReserva>();
            List<HoraReserva> listaRespaldo = new ArrayList<HoraReserva>();
            activarSalida = false;
            horaReservaSalida = null;
            int horaBloque = Integer.valueOf(parametroSala.getLaboratorio().getBloquehorareserva());
            for (int i = horaReserva.getHora() + horaBloque; i <= horaFinGeneral; i++) {
                HoraReserva hora = new HoraReserva();
                hora.setHora(i);
                hora.setHoraMostrar(i + ":00");
                listaReservaSalida.add(hora);
                listaRespaldo.add(hora);
            }
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaAnio);
            cal.set(Calendar.MONTH, fechaMes.getParametro());
            cal.set(Calendar.DATE, fechaDia.getParametro());
            List<ReservaSala> reservas = administrarReservasBO.obtenerReservasSalaPorParametros(parametroSala, cal.getTime());
            List<HoraReserva> listaReservado = new ArrayList<HoraReserva>();
            if (null != reservas) {
                for (ReservaSala reserva : reservas) {
                    int horaInicio = Integer.valueOf(reserva.getReserva().getHorainicio());
                    int horaFin = Integer.valueOf(reserva.getReserva().getHorafin());
                    for (int i = horaInicio; i < horaFin; i++) {
                        HoraReserva hora = new HoraReserva();
                        hora.setHora(i);
                        hora.setHoraMostrar(i + ":00");
                        listaReservado.add(hora);
                    }
                }
                for (HoraReserva horaReserva : listaReservado) {
                    for (int i = 0; i < listaReservaSalida.size(); i++) {
                        HoraReserva horaSolicitar = listaReservaSalida.get(i);
                        if (horaReserva.getHora() == horaSolicitar.getHora()) {
                            listaReservaSalida.remove(horaSolicitar);
                        }
                    }
                }
            }
            HoraReserva posicion = null;
            for (int h = 0; h < listaRespaldo.size(); h++) {
                HoraReserva horaX = listaReservaSalida.get(h);
                HoraReserva horaSolicitar = listaRespaldo.get(h);
                if (horaX.getHora() != horaSolicitar.getHora()) {
                    posicion = horaSolicitar;
                    break;
                }
            }
            if (null != posicion) {
                listaReservaSalida = new ArrayList<HoraReserva>();
                for (int i = horaReserva.getHora() + horaBloque; i <= posicion.getHora(); i++) {
                    HoraReserva hora = new HoraReserva();
                    hora.setHora(i);
                    hora.setHoraMostrar(i + ":00");
                    listaReservaSalida.add(hora);
                }
            }

        } else {
            listaReservaSalida = null;
            activarSalida = true;
            horaReservaSalida = null;
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
        if (null == horaReservaSalida) {
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
            long dias = calcularDiferenciaFechas(cal);
            if (dias != -1) {
                if (dias > 0 && dias <= 15) {
                    Boolean respuestaReserva = administrarReservasBO.validarReservaSalaDisposible(fechaReserva, String.valueOf(horaReserva.getHora()), parametroSala.getIdsalalaboratorio());
                    if (null != respuestaReserva) {
                        if (respuestaReserva == true) {
                            AyudaReservaSala.getInstance().setFechaReserva(fechaReserva);
                            AyudaReservaSala.getInstance().setSalaLaboratorio(parametroSala);
                            AyudaReservaSala.getInstance().setHoraInicio(String.valueOf(horaReserva.getHora()));
                            AyudaReservaSala.getInstance().setHoraFin(String.valueOf(horaReservaSalida.getHora()));
                            AyudaReservaSala.getInstance().setServicioSala(parametroServicio);
                            paso2 = "reservasala2";
                            limpiarInformacion();
                        } else {
                            mensajeFormulario = "Existe una reserva solicitada en el tiempo y sala de laboratorio asignado.";
                            colorMensaje = "#FF0000";
                        }
                    }
                } else {
                    mensajeFormulario = "Recuerde que tiene un maximo de 15 días para asignar la reserva. Rectfique la información para continuar.";
                    colorMensaje = "#FF0000";
                }
            } else {
                mensajeFormulario = "La fecha ingresada es menor a la del día. Solo se realizan reservas para tiempo futuro no mayor a 15 días.";
                colorMensaje = "#FF0000";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar";
            colorMensaje = "#FF0000";
        }
        return paso2;
    }

    private long calcularDiferenciaFechas(Calendar fechaReserva) {
        Calendar fechaHoy = Calendar.getInstance();
        if (fechaReserva.getTime().after(fechaHoy.getTime())) {
            long milis1 = fechaHoy.getTimeInMillis();
            long milis2 = fechaReserva.getTimeInMillis();
            long diff = milis2 - milis1;
            long dias = diff / (24 * 60 * 60 * 1000);
            return dias;
        } else {
            return -1;
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

    public AyudaReservaSala enviarAyudaReservaSalaPasoSiguiente() {
        return reservaSala;
    }

    private void limpiarInformacion() {
        activarSala = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        fecha = df.format(now);
        listaHora = null;
        listaLaboratorios = null;
        listaSalaLaboratorio = null;
        parametroLaboratorio = null;
        parametroSala = null;
        horaIngreso = null;
        activarHora = true;
    }

    public String cancelarProcesoReserva() {
        limpiarInformacion();
        reservaSala = null;
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

    public List<Integer> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<Integer> listaHora) {
        this.listaHora = listaHora;
    }

    public Integer getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Integer horaIngreso) {
        this.horaIngreso = horaIngreso;
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

    public List<HoraReserva> getListaReservaSalida() {
        return listaReservaSalida;
    }

    public void setListaReservaSalida(List<HoraReserva> listaReservaSalida) {
        this.listaReservaSalida = listaReservaSalida;
    }

    public HoraReserva getHoraReservaSalida() {
        return horaReservaSalida;
    }

    public void setHoraReservaSalida(HoraReserva horaReservaSalida) {
        this.horaReservaSalida = horaReservaSalida;
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
