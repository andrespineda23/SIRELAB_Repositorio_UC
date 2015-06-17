/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo;

import com.sirelab.entidades.TipoReserva;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarVariableTiposReservasBOInterface {

    public void crearTipoReserva(TipoReserva tipoReserva);

    public void editarTipoReserva(TipoReserva tipoReserva);

    public void borrarTipoReserva(TipoReserva tipoReserva);

    public TipoReserva consultarTipoReservaPorID(BigInteger idRegistro);

    public List<TipoReserva> consultarTiposReservasRegistrados();
}
