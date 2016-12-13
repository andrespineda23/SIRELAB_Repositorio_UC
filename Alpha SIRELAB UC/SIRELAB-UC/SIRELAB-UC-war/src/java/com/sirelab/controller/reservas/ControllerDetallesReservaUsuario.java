/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.ReservaEquipoElemento;
import com.sirelab.entidades.ReservaSala;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesReservaUsuario implements Serializable {

    static Logger logger = Logger.getLogger(ControllerDetallesReservaSala.class);

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private ReservaSala reservaSala;
    private BigInteger idReserva;
    private boolean activarIniciar, activarCerrar, activarCancelar;
    private List<ReservaEquipoElemento> listaEquiposReservados;
    private List<ReservaEquipoElemento> listaEquiposReservadosTabla;
    private int posicionEquiposTabla;
    private int tamTotalEquipos;
    private boolean bloquearPagSigEquipos, bloquearPagAntEquipos;

    public ControllerDetallesReservaUsuario() {
    }

    public void recibirIdReservaSala(BigInteger idRegistro) {
        listaEquiposReservados = null;
        listaEquiposReservadosTabla = null;
        posicionEquiposTabla = 0;
        tamTotalEquipos = 0;
        bloquearPagAntEquipos = true;
        bloquearPagSigEquipos = true;
        this.idReserva = idRegistro;
        activarIniciar = true;
        activarCerrar = true;
        activarCancelar = true;
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idReserva);
        if (null != reservaSala) {
            listaEquiposReservados = administrarReservasBO.obtenerReservasEquipoPorIdReserva(reservaSala.getReserva().getIdreserva());
            listaEquiposReservadosTabla = new ArrayList<ReservaEquipoElemento>();
            tamTotalEquipos = listaEquiposReservados.size();
            posicionEquiposTabla = 0;
            cargarDatosTablaEquipos();

        }
        cargarBotonesPagina();
    }

    public void limpiarInformacion() {
        activarIniciar = true;
        activarCerrar = true;
        activarCancelar = true;
        reservaSala = null;
        idReserva = null;
    }

    private void cargarBotonesPagina() {
        if (null != reservaSala) {
            if (reservaSala.getReserva().getEstadoreserva().getIdestadoreserva().equals(new BigInteger("3"))) {
                activarCancelar = true;
                activarCerrar = true;
                activarIniciar = true;
            } else {
                if (reservaSala.getReserva().getHorainicioefectiva() != null && reservaSala.getReserva().getHorafinefectiva() != null) {
                    activarCancelar = true;
                    activarCerrar = true;
                    activarIniciar = true;
                } else {
                    if (reservaSala.getReserva().getHorainicioefectiva() != null) {
                        activarCancelar = true;
                        activarCerrar = true;
                    } else if (reservaSala.getReserva().getHorafinefectiva() != null) {
                        activarCancelar = true;
                        activarCerrar = true;
                        activarIniciar = true;
                    } else {
                        activarCancelar = false;
                        activarCerrar = false;
                        activarIniciar = false;
                    }
                }
            }
        }
    }

    public void iniciarReserva() {
        GregorianCalendar hora = new GregorianCalendar();
        int horaInicio = hora.get(Calendar.HOUR);
        int minutoInicio = hora.get(Calendar.MINUTE);
        String inicio = horaInicio + ":" + minutoInicio;
        reservaSala.getReserva().setHorainicio(inicio);
        administrarReservasBO.actualizarInformacionReserva(reservaSala.getReserva(), 1);
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idReserva);
        activarCancelar = true;
        activarIniciar = true;
    }

    public void terminarReserva() {
        GregorianCalendar hora = new GregorianCalendar();
        int horaInicio = hora.get(Calendar.HOUR);
        int minutoInicio = hora.get(Calendar.MINUTE);
        String inicio = horaInicio + ":" + minutoInicio;
        reservaSala.getReserva().setHorafin(inicio);
        administrarReservasBO.actualizarInformacionReserva(reservaSala.getReserva(), 2);
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idReserva);
        activarCerrar = true;
        activarIniciar = true;
    }

    public void cancelarReserva() {
        EstadoReserva cancelacion = administrarReservasBO.obtenerEstadoCancelacionReserva();
        reservaSala.getReserva().setEstadoreserva(cancelacion);
        administrarReservasBO.actualizarInformacionReserva(reservaSala.getReserva(), 0);
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idReserva);
        activarCancelar = true;
        activarCerrar = true;
        activarIniciar = true;
    }

    private void cargarDatosTablaEquipos() {
        if (tamTotalEquipos < 10) {
            for (int i = 0; i < tamTotalEquipos; i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = true;
            bloquearPagAntEquipos = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = false;
            bloquearPagAntEquipos = true;
        }
    }

    public void cargarPaginaSiguienteEquipos() {
        listaEquiposReservadosTabla = new ArrayList<ReservaEquipoElemento>();
        posicionEquiposTabla = posicionEquiposTabla + 10;
        int diferencia = tamTotalEquipos - posicionEquiposTabla;
        if (diferencia > 10) {
            for (int i = posicionEquiposTabla; i < (posicionEquiposTabla + 10); i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = false;
            bloquearPagAntEquipos = false;
        } else {
            for (int i = posicionEquiposTabla; i < (posicionEquiposTabla + diferencia); i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = true;
            bloquearPagAntEquipos = false;
        }
    }

    public void cargarPaginaAnteriorEquipos() {
        listaEquiposReservadosTabla = new ArrayList<ReservaEquipoElemento>();
        posicionEquiposTabla = posicionEquiposTabla - 10;
        int diferencia = tamTotalEquipos - posicionEquiposTabla;
        if (diferencia == tamTotalEquipos) {
            for (int i = posicionEquiposTabla; i < (posicionEquiposTabla + 10); i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = false;
            bloquearPagAntEquipos = true;
        } else {
            for (int i = posicionEquiposTabla; i < (posicionEquiposTabla + 10); i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = false;
            bloquearPagAntEquipos = false;
        }
    }

    public ReservaSala getReservaSala() {
        return reservaSala;
    }

    public void setReservaSala(ReservaSala reservaSala) {
        this.reservaSala = reservaSala;
    }

    public boolean isActivarIniciar() {
        return activarIniciar;
    }

    public void setActivarIniciar(boolean activarIniciar) {
        this.activarIniciar = activarIniciar;
    }

    public boolean isActivarCerrar() {
        return activarCerrar;
    }

    public void setActivarCerrar(boolean activarCerrar) {
        this.activarCerrar = activarCerrar;
    }

    public boolean isActivarCancelar() {
        return activarCancelar;
    }

    public void setActivarCancelar(boolean activarCancelar) {
        this.activarCancelar = activarCancelar;
    }

    public List<ReservaEquipoElemento> getListaEquiposReservados() {
        return listaEquiposReservados;
    }

    public void setListaEquiposReservados(List<ReservaEquipoElemento> listaEquiposReservados) {
        this.listaEquiposReservados = listaEquiposReservados;
    }

    public List<ReservaEquipoElemento> getListaEquiposReservadosTabla() {
        return listaEquiposReservadosTabla;
    }

    public void setListaEquiposReservadosTabla(List<ReservaEquipoElemento> listaEquiposReservadosTabla) {
        this.listaEquiposReservadosTabla = listaEquiposReservadosTabla;
    }

    public int getPosicionEquiposTabla() {
        return posicionEquiposTabla;
    }

    public void setPosicionEquiposTabla(int posicionEquiposTabla) {
        this.posicionEquiposTabla = posicionEquiposTabla;
    }

    public int getTamTotalEquipos() {
        return tamTotalEquipos;
    }

    public void setTamTotalEquipos(int tamTotalEquipos) {
        this.tamTotalEquipos = tamTotalEquipos;
    }

    public boolean isBloquearPagSigEquipos() {
        return bloquearPagSigEquipos;
    }

    public void setBloquearPagSigEquipos(boolean bloquearPagSigEquipos) {
        this.bloquearPagSigEquipos = bloquearPagSigEquipos;
    }

    public boolean isBloquearPagAntEquipos() {
        return bloquearPagAntEquipos;
    }

    public void setBloquearPagAntEquipos(boolean bloquearPagAntEquipos) {
        this.bloquearPagAntEquipos = bloquearPagAntEquipos;
    }

    
    
}
