/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.ReservaSala;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface ReservaSalaDAOInterface {

    public void crearReservaSala(ReservaSala reserva);

    public void editarReservaSala(ReservaSala reserva);

    public void eliminarReservaSala(ReservaSala reserva);

    public List<ReservaSala> consultarReservaSalasSala();

    public ReservaSala buscarReservaSalaPorID(BigInteger idRegistro);

    public List<ReservaSala> buscarReservaSalasSalaPorPersona(BigInteger persona);
}
