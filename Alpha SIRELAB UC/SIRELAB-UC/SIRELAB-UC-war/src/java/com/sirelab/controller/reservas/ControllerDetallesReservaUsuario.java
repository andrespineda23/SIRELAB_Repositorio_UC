/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.ReservaSala;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    public ControllerDetallesReservaUsuario() {
    }

    public void recibirIdReservaSala(BigInteger idRegistro) {
        this.idReserva = idRegistro;
        activarIniciar = true;
        activarCerrar = true;
        activarCancelar = true;
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idReserva);
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
        administrarReservasBO.actualizarInformacionReserva(reservaSala.getReserva());
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
        administrarReservasBO.actualizarInformacionReserva(reservaSala.getReserva());
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idReserva);
        activarCerrar = true;
        activarIniciar = true;
    }

    public void cancelarReserva() {
        EstadoReserva cancelacion = administrarReservasBO.obtenerEstadoCancelacionReserva();
        reservaSala.getReserva().setEstadoreserva(cancelacion);
        administrarReservasBO.actualizarInformacionReserva(reservaSala.getReserva());
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idReserva);
        activarCancelar = true;
        activarCerrar = true;
        activarIniciar = true;
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

}
