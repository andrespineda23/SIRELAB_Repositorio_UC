/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.EstadoReserva;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface EstadoReservaDAOInterface {

    public void crearEstadoReserva(EstadoReserva estadoReserva);

    public void editarEstadoReserva(EstadoReserva estadoReserva);

    public void eliminarEstadoReserva(EstadoReserva estadoReserva);

    public List<EstadoReserva> consultarEstadosReservas();

    public EstadoReserva buscarEstadoReservaPorID(BigInteger idRegistro);
}
