/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaModuloLaboratorio;
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
public class ControllerDetallesReservaUsuario2 implements Serializable {

    static Logger logger = Logger.getLogger(ControllerDetallesReservaUsuario2.class);

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private ReservaModuloLaboratorio reservaModulo;
    private BigInteger idReserva;
    private boolean activarIniciar, activarCerrar, activarCancelar;

    public ControllerDetallesReservaUsuario2() {
    }

    public void recibirIdReservaSala(BigInteger idRegistro) {
        this.idReserva = idRegistro;
        activarIniciar = true;
        activarCerrar = true;
        activarCancelar = true;
        reservaModulo = administrarReservasBO.obtenerReservaModuloLaboratorioPorId(idReserva);
        cargarBotonesPagina();
    }

    public String limpiarInformacion() {
        activarIniciar = true;
        activarCerrar = true;
        activarCancelar = true;
        reservaModulo = null;
        idReserva = null;
        return "consultarreservasusuario";
    }

    private void cargarBotonesPagina() {
        if (null != reservaModulo) {
            if (reservaModulo.getReserva().getEstadoreserva().getIdestadoreserva().equals(new BigInteger("3"))) {
                activarCancelar = true;
                activarCerrar = true;
                activarIniciar = true;
            } else {
                if (reservaModulo.getReserva().getHorainicioefectiva() != null && reservaModulo.getReserva().getHorafinefectiva() != null) {
                    activarCancelar = true;
                    activarCerrar = true;
                    activarIniciar = true;
                } else {
                    if (reservaModulo.getReserva().getHorainicioefectiva() != null) {
                        activarCancelar = true;
                        activarCerrar = true;
                    } else if (reservaModulo.getReserva().getHorafinefectiva() != null) {
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
        Reserva reserva = reservaModulo.getReserva();
        String inicio = horaInicio + ":" + minutoInicio;
        reserva.setHorainicioefectiva(inicio);
        administrarReservasBO.actualizarInformacionReserva(reserva);
        reservaModulo = administrarReservasBO.obtenerReservaModuloLaboratorioPorId(idReserva);
        activarCancelar = true;
        activarIniciar = true;
    }

    public void terminarReserva() {
        GregorianCalendar hora = new GregorianCalendar();
        int horaInicio = hora.get(Calendar.HOUR);
        int minutoInicio = hora.get(Calendar.MINUTE);
        Reserva reserva = reservaModulo.getReserva();
        String inicio = horaInicio + ":" + minutoInicio;
        reserva.setHorafinefectiva(inicio);
        administrarReservasBO.actualizarInformacionReserva(reserva);
        reservaModulo = administrarReservasBO.obtenerReservaModuloLaboratorioPorId(idReserva);
        activarCancelar = true;
        activarCerrar = true;
        activarIniciar = true;
    }

    public void cancelarReserva() {
        EstadoReserva cancelacion = administrarReservasBO.obtenerEstadoCancelacionReserva();
        reservaModulo.getReserva().setEstadoreserva(cancelacion);
        administrarReservasBO.actualizarInformacionReserva(reservaModulo.getReserva());
        reservaModulo = administrarReservasBO.obtenerReservaModuloLaboratorioPorId(idReserva);
        activarCancelar = true;
        activarCerrar = true;
        activarIniciar = true;
    }

    public ReservaModuloLaboratorio getReservaModulo() {
        return reservaModulo;
    }

    public void setReservaModulo(ReservaModuloLaboratorio reservaModulo) {
        this.reservaModulo = reservaModulo;
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
