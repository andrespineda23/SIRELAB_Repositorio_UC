/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Reserva;
import com.sirelab.utilidades.UsuarioLogin;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ControllerCancelarReserva implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private Reserva reservaCancelar;
    private String mensajeFormulario;
    private String colorMensaje;
    private boolean bloquearBotones;

    public ControllerCancelarReserva() {
    }

    public void recibirIdReservaCancelar(BigInteger idReserva) {
        reservaCancelar = administrarReservasBO.obtenerReservaPorId(idReserva);
        bloquearBotones = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    public String cancelarProceso() {
        bloquearBotones = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        reservaCancelar = null;
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        String paginaAnterior = "";
        if ("estudiante".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            paginaAnterior = "inicioestudiante";
        } else {
            if ("docente".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                paginaAnterior = "iniciodocente";
            }
        }
        return paginaAnterior;
    }

    public void cancelarReserva() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formateador.parse((reservaCancelar.getFechareserva()).toString());
            date2 = formateador.parse(new Date().toString());
        } catch (ParseException e) {
            // Error, la cadena de texto no se puede convertir en fecha.
        }
        if (date1.compareTo(date2) < 0) {
            bloquearBotones = true;
            administrarReservasBO.cambiarEstadoReserva(reservaCancelar);
            mensajeFormulario = "La reserva fue cancelada con éxito.";
            colorMensaje = "green";
        } else {
            mensajeFormulario = "La cancelación de la reserva supera el tiempo limite de 24 horas.";
            colorMensaje = "red";
        }
    }

    public Reserva getReservaCancelar() {
        return reservaCancelar;
    }

    public void setReservaCancelar(Reserva reservaCancelar) {
        this.reservaCancelar = reservaCancelar;
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

    public boolean isBloquearBotones() {
        return bloquearBotones;
    }

    public void setBloquearBotones(boolean bloquearBotones) {
        this.bloquearBotones = bloquearBotones;
    }

}
