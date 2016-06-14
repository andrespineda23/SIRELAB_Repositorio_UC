/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.Reserva;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface ReservaDAOInterface {

    public void crearReserva(Reserva reserva);

    public void editarReserva(Reserva reserva);

    public void eliminarReserva(Reserva reserva);

    public List<Reserva> consultarReservas();

    public Reserva buscarReservaPorID(BigInteger idRegistro);

    public List<Reserva> buscarReservasPorPersona(BigInteger persona);

    public List<Reserva> buscarReservasPorFecha(Date fecha);

    public List<Reserva> buscarReservasPorEstadoReserva(BigInteger estado);

    public Reserva buscarUltimaReservaPersona(BigInteger persona, String horaInicio, Date fecha);

    public Integer obtenerCantidadReservasDia(Date fecha);

    public Reserva buscarReservaPorNumero(String numero);

    public Reserva buscarUltimaReservaPersonaConEstado(BigInteger persona, String horaInicio, Date fecha);
}
