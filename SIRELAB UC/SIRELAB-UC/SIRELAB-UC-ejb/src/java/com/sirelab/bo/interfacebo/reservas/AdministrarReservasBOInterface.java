/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.reservas;

import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Reserva;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministrarReservasBOInterface {

    public List<Reserva> consultarReservasPorPersona(BigInteger persona);

    public Docente consultarDocentePorID(BigInteger idRegistro);
}
