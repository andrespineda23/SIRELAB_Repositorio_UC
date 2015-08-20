/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.reservas;

import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.ReservaSala;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministrarReservasBOInterface {

    public List<ReservaSala> consultarReservasSalaPorPersona(BigInteger persona);

    public Docente consultarDocentePorID(BigInteger idRegistro);

    public Persona obtenerPersonaConsultarReservas(String tipoUsuario, BigInteger idPersona);
}
