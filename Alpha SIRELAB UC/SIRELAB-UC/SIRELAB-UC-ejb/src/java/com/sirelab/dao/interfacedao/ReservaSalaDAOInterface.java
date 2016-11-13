/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.ReservaSala;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface ReservaSalaDAOInterface {

    public List<ReservaSala> consultarReservaSalasPorTipoUsuarioYPeriodo(BigInteger tipoUsuario, BigInteger periodo);

    public List<ReservaSala> consultarReservaSalasPorFechasYPeriodo(Date fechaInicio, Date fechaFin, BigInteger periodo);

    public void crearReservaSala(ReservaSala reserva);

    public List<ReservaSala> consultarReservaSalasPorUsuarioYPeriodo(String usuario, BigInteger periodo);

    public List<ReservaSala> consultarReservaSalasSala(BigInteger periodo);

    public void editarReservaSala(ReservaSala reserva);

    public void eliminarReservaSala(ReservaSala reserva);

    public List<ReservaSala> consultarReservaSalasSala();

    public ReservaSala buscarReservaSalaPorID(BigInteger idRegistro);

    public List<ReservaSala> buscarReservaSalasSalaPorPersona(BigInteger persona);

    public ReservaSala buscarReservaSalaPorFechaHoraSala(Date fecha, String horaInicio, BigInteger sala);

    public List<ReservaSala> buscarReservaSalasSalaPorParametros(BigInteger idSala, Date fecha);

    public List<ReservaSala> buscarReservaSalaParaReservaModulo(BigInteger sala, Date fecha, BigInteger tipoReserva);

    public ReservaSala buscarReservaSalaPorIdReserva(BigInteger reserva);

    public List<ReservaSala> buscarReservaSalaPorFiltrado(Map<String, String> filters);

    public List<ReservaSala> consultarReservaSalasPorIdSalaYPeriodo(BigInteger sala, BigInteger periodo);
}
