/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Reserva;
import java.io.Serializable;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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

    public ControllerCancelarReserva() {
    }
    
    public void recibirIdReservaCancelar(BigInteger idReserva){
        reservaCancelar = administrarReservasBO.obtenerReservaPorId(idReserva);
    }

    public void cancelarReserva(){}

    public Reserva getReservaCancelar() {
        return reservaCancelar;
    }

    public void setReservaCancelar(Reserva reservaCancelar) {
        this.reservaCancelar = reservaCancelar;
    }
    
    
}
