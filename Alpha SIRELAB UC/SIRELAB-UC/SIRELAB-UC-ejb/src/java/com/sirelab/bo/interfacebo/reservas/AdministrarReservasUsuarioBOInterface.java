/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.reservas;

import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaEquipoElemento;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AndresPineda
 */
public interface AdministrarReservasUsuarioBOInterface {

    public void modificarInformacionReserva(Reserva reserva);

    public List<ReservaModuloLaboratorio> consultarReservasModulo(Map<String, String> filtros);

    public List<ReservaSala> consultarReservasSala(Map<String, String> filtros);

    public List<ReservaEquipoElemento> consultarReservasEquipo(BigInteger reserva);
}
