/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.ReservaEquipoElemento;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface ReservaEquipoElementoDAOInterface {

    public void crearReservaEquipoElemento(ReservaEquipoElemento reserva);

    public void editarReservaEquipoElemento(ReservaEquipoElemento reserva);

    public void eliminarReservaEquipoElemento(ReservaEquipoElemento reserva);

    public List<ReservaEquipoElemento> consultarReservaEquipoElementos();

    public ReservaEquipoElemento buscarReservaEquipoElementoPorID(BigInteger idRegistro);

    public List<ReservaEquipoElemento> buscarReservaEquipoElementosPorPersona(BigInteger persona);

    public List<ReservaEquipoElemento> buscarReservaEquipoElementosPorReserva(BigInteger reserva);
}
