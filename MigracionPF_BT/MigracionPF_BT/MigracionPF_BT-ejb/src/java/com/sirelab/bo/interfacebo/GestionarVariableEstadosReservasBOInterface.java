/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo;

import com.sirelab.entidades.EstadoReserva;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarVariableEstadosReservasBOInterface {

    public void crearEstadoReserva(EstadoReserva estadoReserva);

    public void editarEstadoReserva(EstadoReserva estadoReserva);

    public void borrarEstadoReserva(EstadoReserva estadoReserva);

    public EstadoReserva consultarEstadoReservaPorID(BigInteger idRegistro);

    public List<EstadoReserva> consultarEstadosReservasRegistrados();
}
