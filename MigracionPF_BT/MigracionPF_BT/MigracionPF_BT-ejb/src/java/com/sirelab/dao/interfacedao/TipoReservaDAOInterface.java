/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.TipoReserva;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface TipoReservaDAOInterface {

    public void crearTipoReserva(TipoReserva tipoReserva);

    public void editarTipoReserva(TipoReserva tipoReserva);

    public void eliminarTipoReserva(TipoReserva tipoReserva);

    public List<TipoReserva> consultarTiposReservas();

    public TipoReserva buscarTipoReservaPorID(BigInteger idRegistro);
}
