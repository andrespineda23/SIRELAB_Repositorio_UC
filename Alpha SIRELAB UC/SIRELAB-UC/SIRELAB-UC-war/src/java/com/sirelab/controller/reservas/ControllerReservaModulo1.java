/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaModulo;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
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
    private List<Integer> listaHora;
    private Integer horaIngreso;
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
    }

    private boolean validarFecha() {
        boolean retorno = true;
        if (Utilidades.validarNulo(fecha)) {
            if (!Utilidades.fechaIngresadaCorrecta(fecha)) {
                retorno = false;
            }
        } else {
            retorno = false;
        }
        return retorno;
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
        if ((Utilidades.validarNulo(parametroSala) == true) && (Utilidades.validarNulo(fecha))) {
            int diaSemana = fecha.getDay();
            if (diaSemana != 0) {
                if (diaSemana == 6) {
                    Integer horaInicio = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoraaperturasabado());
                    Integer horaFin = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoracierresabado());
                    horaIngreso = null;
                    listaHora = new ArrayList<Integer>();
                    for (int i = horaInicio; i < horaFin - 2; i++) {
                        listaHora.add(i);
                    }
                } else {
                    Integer horaInicio = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoraapertura());
                    Integer horaFin = Integer.valueOf(parametroSala.getEdificio().getHorarioatencion().getHoracierre());
                    horaIngreso = null;
                    listaHora = new ArrayList<Integer>();
                    for (int i = horaInicio; i < horaFin - 2; i++) {
                        listaHora.add(i);
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
        if (validarFecha() == false) {
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
            Boolean respuestaReserva = administrarReservasBO.validarReservaSalaDisposible(fecha, String.valueOf(horaIngreso), parametroSala.getIdsalalaboratorio());
            if (null != respuestaReserva) {
                if (respuestaReserva == true) {
                    List<ReservaModuloLaboratorio> reservaModuloLaboratorio = administrarReservasBO.obtenerCantidadReservasModuloPorParametros(fecha, String.valueOf(horaIngreso), parametroSala.getIdsalalaboratorio());
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
                        reservaModulo.setFechaReserva(fecha);
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

}
