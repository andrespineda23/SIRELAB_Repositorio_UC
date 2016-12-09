/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaEquipo;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaEquipoElemento;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.utilidades.UsuarioLogin;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerReservaEquipo implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private String numeroReserva;
    private String mensajeFormulario;
    private String colorMensaje;
    private Reserva reservaPorNumero;
    private List<AyudaReservaEquipo> listaEquipos;
    private List<AyudaReservaEquipo> listaEquiposTabla;
    private int posicionEquipoTabla;
    private int tamTotalEquipo;
    private boolean bloquearPagSigEquipo, bloquearPagAntEquipo;
    private List<AyudaReservaEquipo> listaEquiposReserva;
    private List<AyudaReservaEquipo> listaEquiposReservaTabla;
    private int posicionEquipo2Tabla;
    private int tamTotalEquipo2;
    private boolean bloquearPagSigEquipo2, bloquearPagAntEquipo2;
    private boolean disabledTablas;

    public ControllerReservaEquipo() {
        disabledTablas = false;
    }

    @PostConstruct
    public void init() {
    }

    public void consultarNumeroReserva() {
        disabledTablas = false;
        if (null != numeroReserva) {
            if (!numeroReserva.isEmpty()) {
                reservaPorNumero = administrarReservasBO.obtenerReservaPorNumero(numeroReserva);
                if (null != reservaPorNumero) {
                    if (reservaPorNumero.getFechareserva().after(new Date())) {
                        ReservaSala reservaSala = administrarReservasBO.obtenterReservaSalaPorIdReserva(reservaPorNumero.getIdreserva());
                        ReservaModuloLaboratorio reservaModulo = administrarReservasBO.obtenterReservaModuloPorIdReserva(reservaPorNumero.getIdreserva());
                        if (null != reservaModulo) {
                            mensajeFormulario = numeroReserva + " - " + "Reserva de Modulo: " + reservaModulo.getModulolaboratorio().getCodigomodulo() + " - " + reservaModulo.getModulolaboratorio().getSalalaboratorio().getNombresala() + " - " + reservaModulo.getModulolaboratorio().getSalalaboratorio().getEdificio().getDescripcionedificio();
                            BigInteger laboratorio = reservaModulo.getModulolaboratorio().getSalalaboratorio().getLaboratorio().getIdlaboratorio();
                            cargarInformacionBodegaSala(laboratorio);
                        }
                        if (null != reservaSala) {
                            mensajeFormulario = numeroReserva + " - " + "Reserva de Sala: " + reservaSala.getSalalaboratorio().getNombresala() + " - " + reservaSala.getSalalaboratorio().getEdificio().getDescripcionedificio();
                            BigInteger laboratorio = reservaSala.getSalalaboratorio().getLaboratorio().getIdlaboratorio();
                            cargarInformacionBodegaSala(laboratorio);
                        }
                    } else {
                        mensajeFormulario = "La reserva ingresada es menor a la fecha del día de hoy.";
                        colorMensaje = "red";
                    }
                } else {
                    mensajeFormulario = "El número de reserva ingresado no se encuentra registrado en el sistema.";
                    colorMensaje = "red";
                }
            } else {
                mensajeFormulario = "El número de reserva es obligatorio.";
                colorMensaje = "red";
            }
        } else {
            mensajeFormulario = "El número de reserva es obligatorio.";
            colorMensaje = "red";
        }
    }

    public void adicionarEquipoReserva(AyudaReservaEquipo registro) {
        listaEquipos.remove(registro);
        if (null == listaEquiposReserva) {
            listaEquiposReserva = new ArrayList<AyudaReservaEquipo>();
        }
        listaEquiposTabla = new ArrayList<AyudaReservaEquipo>();
        tamTotalEquipo = this.listaEquipos.size();
        posicionEquipoTabla = 0;
        cargarDatosEquipo();
        //
        listaEquiposReserva.add(registro);
        listaEquiposReservaTabla = new ArrayList<AyudaReservaEquipo>();
        tamTotalEquipo2 = this.listaEquiposReserva.size();
        posicionEquipo2Tabla = 0;
        cargarDatosEquipoReserva();
    }

    public void almacenarReservasEquipos() {
        if (null != listaEquiposReserva) {
            int cantidad = listaEquiposReserva.size();
            if (cantidad >= 1) {
                guardarReservasEquiposEnSistema();
                limpiarInformacionReserva();
                mensajeFormulario = "Las reservas fueron almacenadas con éxito. Recuerde que los equipos se entregaran según disponibilidad del laboratorio.";
                colorMensaje = "green";
            } else {
                mensajeFormulario = "No se ha seleccionado algun equipo para la reserva";
                colorMensaje = "red";
            }
        } else {
            mensajeFormulario = "No se ha seleccionado algun equipo para la reserva";
            colorMensaje = "red";
        }
    }

    private void limpiarInformacionReserva() {
        numeroReserva = "";
        colorMensaje = "black";
        reservaPorNumero = null;
        disabledTablas = false;
        listaEquipos = null;
        listaEquiposTabla = null;
        tamTotalEquipo = 0;
        posicionEquipoTabla = 0;
        bloquearPagAntEquipo = true;
        bloquearPagSigEquipo = true;
        listaEquiposReserva = null;
        listaEquiposReservaTabla = null;
        tamTotalEquipo2 = 0;
        posicionEquipo2Tabla = 0;
        bloquearPagAntEquipo2 = true;
        bloquearPagSigEquipo2 = true;
    }

    private void guardarReservasEquiposEnSistema() {
        for (int i = 0; i < listaEquiposReserva.size(); i++) {
            ReservaEquipoElemento reserva = new ReservaEquipoElemento();
            reserva.setCantidad(1);
            reserva.setReserva(reservaPorNumero);
            reserva.setEquipoelemento(listaEquiposReserva.get(i).getEquipo());
            administrarReservasBO.almacenarReservaEquipo(reserva);
        }
    }

    private void cargarInformacionBodegaSala(BigInteger laboratorio) {
        //Cargar información bodega sala
        List<SalaLaboratorio> salasLaboratorio = administrarReservasBO.buscarBodegaPorLaboratorioEdificio(laboratorio);
        if (null != salasLaboratorio) {
            List<EquipoElemento> listaEquipos = new ArrayList<EquipoElemento>();
            for (SalaLaboratorio sala : salasLaboratorio) {
                List<EquipoElemento> auxiliar = administrarReservasBO.obtenerEquiposBodega(sala.getIdsalalaboratorio());
                listaEquipos = guardarEquiposLista(listaEquipos, auxiliar);
            }
            cargarInformacionAyudaReservaEquipo(listaEquipos);
        } else {
            mensajeFormulario = "No se encontro la bodega asociada al laboratorio de la sala. Pongase en contacto con el laboratorio para mas informacion";
            colorMensaje = "red";
        }
    }

    private void cargarInformacionAyudaReservaEquipo(List<EquipoElemento> listaEquipos) {
        this.listaEquipos = new ArrayList<AyudaReservaEquipo>();
        for (EquipoElemento equipo : listaEquipos) {
            AyudaReservaEquipo ayuda = new AyudaReservaEquipo();
            ayuda.setCantidadExistencia(1);
            ayuda.setEquipo(equipo);
            this.listaEquipos.add(ayuda);
        }
        if (null != listaEquipos) {
            if (listaEquipos.size() > 0) {
                cargarTablas();
            } else {
                mensajeFormulario = "No se encontraron posibles equipos para ser asociados a la reserva. Pongase en contacto con el laboratorio para mas informacion";
                colorMensaje = "red";
            }
        } else {
            mensajeFormulario = "No se encontraron posibles equipos para ser asociados a la reserva. Pongase en contacto con el laboratorio para mas informacion";
            colorMensaje = "red";
        }

    }

    private void cargarTablas() {
        listaEquiposTabla = new ArrayList<AyudaReservaEquipo>();
        tamTotalEquipo = this.listaEquipos.size();
        posicionEquipoTabla = 0;
        cargarDatosEquipo();
        listaEquiposReservaTabla = new ArrayList<AyudaReservaEquipo>();
        listaEquiposReserva = new ArrayList<AyudaReservaEquipo>();
        tamTotalEquipo2 = 0;
        posicionEquipo2Tabla = 0;
        bloquearPagAntEquipo2 = true;
        bloquearPagSigEquipo2 = true;
        disabledTablas = true;
    }

    private List<EquipoElemento> guardarEquiposLista(List<EquipoElemento> listaEquipos, List<EquipoElemento> auxiliar) {
        for (EquipoElemento equipo : auxiliar) {
            listaEquipos.add(equipo);
        }
        return listaEquipos;
    }

    private void cargarDatosEquipo() {
        if (tamTotalEquipo < 10) {
            for (int i = 0; i < tamTotalEquipo; i++) {
                listaEquiposTabla.add(listaEquipos.get(i));
            }
            bloquearPagSigEquipo = true;
            bloquearPagAntEquipo = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEquiposTabla.add(listaEquipos.get(i));
            }
            bloquearPagSigEquipo = false;
            bloquearPagAntEquipo = true;
        }
    }

    public void cargarPaginaSiguienteEquipo() {
        listaEquiposTabla = new ArrayList<AyudaReservaEquipo>();
        posicionEquipoTabla = posicionEquipoTabla + 10;
        int diferencia = tamTotalEquipo - posicionEquipoTabla;
        if (diferencia > 10) {
            for (int i = posicionEquipoTabla; i < (posicionEquipoTabla + 10); i++) {
                listaEquiposTabla.add(listaEquipos.get(i));
            }
            bloquearPagSigEquipo = false;
            bloquearPagAntEquipo = false;
        } else {
            for (int i = posicionEquipoTabla; i < (posicionEquipoTabla + diferencia); i++) {
                listaEquiposTabla.add(listaEquipos.get(i));
            }
            bloquearPagSigEquipo = true;
            bloquearPagAntEquipo = false;
        }
    }

    public void cargarPaginaAnteriorEquipo() {
        listaEquiposTabla = new ArrayList<AyudaReservaEquipo>();
        posicionEquipoTabla = posicionEquipoTabla - 10;
        int diferencia = tamTotalEquipo - posicionEquipoTabla;
        if (diferencia == tamTotalEquipo) {
            for (int i = posicionEquipoTabla; i < (posicionEquipoTabla + 10); i++) {
                listaEquiposTabla.add(listaEquipos.get(i));
            }
            bloquearPagSigEquipo = false;
            bloquearPagAntEquipo = true;
        } else {
            for (int i = posicionEquipoTabla; i < (posicionEquipoTabla + 10); i++) {
                listaEquiposTabla.add(listaEquipos.get(i));
            }
            bloquearPagSigEquipo = false;
            bloquearPagAntEquipo = false;
        }
    }

    private void cargarDatosEquipoReserva() {
        if (tamTotalEquipo2 < 10) {
            for (int i = 0; i < tamTotalEquipo2; i++) {
                listaEquiposReservaTabla.add(listaEquiposReserva.get(i));
            }
            bloquearPagSigEquipo2 = true;
            bloquearPagAntEquipo2 = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEquiposReservaTabla.add(listaEquiposReserva.get(i));
            }
            bloquearPagSigEquipo2 = false;
            bloquearPagAntEquipo2 = true;
        }
    }

    public void cargarPaginaSiguienteEquipoReserva() {
        listaEquiposReservaTabla = new ArrayList<AyudaReservaEquipo>();
        posicionEquipo2Tabla = posicionEquipo2Tabla + 10;
        int diferencia = tamTotalEquipo2 - posicionEquipo2Tabla;
        if (diferencia > 10) {
            for (int i = posicionEquipo2Tabla; i < (posicionEquipo2Tabla + 10); i++) {
                listaEquiposReservaTabla.add(listaEquiposReserva.get(i));
            }
            bloquearPagSigEquipo2 = false;
            bloquearPagAntEquipo2 = false;
        } else {
            for (int i = posicionEquipo2Tabla; i < (posicionEquipo2Tabla + diferencia); i++) {
                listaEquiposReservaTabla.add(listaEquiposReserva.get(i));
            }
            bloquearPagSigEquipo2 = true;
            bloquearPagAntEquipo2 = false;
        }
    }

    public void cargarPaginaAnteriorEquipoReserva() {
        listaEquiposReservaTabla = new ArrayList<AyudaReservaEquipo>();
        posicionEquipo2Tabla = posicionEquipo2Tabla - 10;
        int diferencia = tamTotalEquipo2 - posicionEquipo2Tabla;
        if (diferencia == tamTotalEquipo2) {
            for (int i = posicionEquipo2Tabla; i < (posicionEquipo2Tabla + 10); i++) {
                listaEquiposReservaTabla.add(listaEquiposReserva.get(i));
            }
            bloquearPagSigEquipo2 = false;
            bloquearPagAntEquipo2 = true;
        } else {
            for (int i = posicionEquipo2Tabla; i < (posicionEquipo2Tabla + 10); i++) {
                listaEquiposReservaTabla.add(listaEquiposReserva.get(i));
            }
            bloquearPagSigEquipo2 = false;
            bloquearPagAntEquipo2 = false;
        }
    }

    public String cancelarProcesoReserva() {
        limpiarInformacionReserva();
        mensajeFormulario = "";
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

    public String getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(String numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public Reserva getReservaPorNumero() {
        return reservaPorNumero;
    }

    public void setReservaPorNumero(Reserva reservaPorNumero) {
        this.reservaPorNumero = reservaPorNumero;
    }

    public List<AyudaReservaEquipo> getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(List<AyudaReservaEquipo> listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public List<AyudaReservaEquipo> getListaEquiposReserva() {
        return listaEquiposReserva;
    }

    public void setListaEquiposReserva(List<AyudaReservaEquipo> listaEquiposReserva) {
        this.listaEquiposReserva = listaEquiposReserva;
    }

    public List<AyudaReservaEquipo> getListaEquiposTabla() {
        return listaEquiposTabla;
    }

    public void setListaEquiposTabla(List<AyudaReservaEquipo> listaEquiposTabla) {
        this.listaEquiposTabla = listaEquiposTabla;
    }

    public int getPosicionEquipoTabla() {
        return posicionEquipoTabla;
    }

    public void setPosicionEquipoTabla(int posicionEquipoTabla) {
        this.posicionEquipoTabla = posicionEquipoTabla;
    }

    public int getTamTotalEquipo() {
        return tamTotalEquipo;
    }

    public void setTamTotalEquipo(int tamTotalEquipo) {
        this.tamTotalEquipo = tamTotalEquipo;
    }

    public boolean isBloquearPagSigEquipo() {
        return bloquearPagSigEquipo;
    }

    public void setBloquearPagSigEquipo(boolean bloquearPagSigEquipo) {
        this.bloquearPagSigEquipo = bloquearPagSigEquipo;
    }

    public boolean isBloquearPagAntEquipo() {
        return bloquearPagAntEquipo;
    }

    public void setBloquearPagAntEquipo(boolean bloquearPagAntEquipo) {
        this.bloquearPagAntEquipo = bloquearPagAntEquipo;
    }

    public List<AyudaReservaEquipo> getListaEquiposReservaTabla() {
        return listaEquiposReservaTabla;
    }

    public void setListaEquiposReservaTabla(List<AyudaReservaEquipo> listaEquiposReservaTabla) {
        this.listaEquiposReservaTabla = listaEquiposReservaTabla;
    }

    public int getPosicionEquipo2Tabla() {
        return posicionEquipo2Tabla;
    }

    public void setPosicionEquipo2Tabla(int posicionEquipo2Tabla) {
        this.posicionEquipo2Tabla = posicionEquipo2Tabla;
    }

    public int getTamTotalEquipo2() {
        return tamTotalEquipo2;
    }

    public void setTamTotalEquipo2(int tamTotalEquipo2) {
        this.tamTotalEquipo2 = tamTotalEquipo2;
    }

    public boolean isBloquearPagSigEquipo2() {
        return bloquearPagSigEquipo2;
    }

    public void setBloquearPagSigEquipo2(boolean bloquearPagSigEquipo2) {
        this.bloquearPagSigEquipo2 = bloquearPagSigEquipo2;
    }

    public boolean isBloquearPagAntEquipo2() {
        return bloquearPagAntEquipo2;
    }

    public void setBloquearPagAntEquipo2(boolean bloquearPagAntEquipo2) {
        this.bloquearPagAntEquipo2 = bloquearPagAntEquipo2;
    }

    public boolean isDisabledTablas() {
        return disabledTablas;
    }

    public void setDisabledTablas(boolean disabledTablas) {
        this.disabledTablas = disabledTablas;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
