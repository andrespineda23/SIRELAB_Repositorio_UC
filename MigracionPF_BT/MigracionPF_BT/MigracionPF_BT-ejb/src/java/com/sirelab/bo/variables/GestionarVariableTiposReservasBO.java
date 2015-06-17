/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposReservasBOInterface;
import com.sirelab.dao.interfacedao.TipoReservaDAOInterface;
import com.sirelab.entidades.TipoReserva;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarVariableTiposReservasBO implements GestionarVariableTiposReservasBOInterface {

    @EJB
    TipoReservaDAOInterface tipoReservaDAO;

    @Override
    public void crearTipoReserva(TipoReserva tipoReserva) {
        try {
            tipoReservaDAO.crearTipoReserva(tipoReserva);;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposReservasBO crearTipoReserva : " + e.toString());
        }
    }

    @Override
    public void editarTipoReserva(TipoReserva tipoReserva) {
        try {
            tipoReservaDAO.editarTipoReserva(tipoReserva);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposReservasBO editarTipoReserva : " + e.toString());
        }
    }

    @Override
    public void borrarTipoReserva(TipoReserva tipoReserva) {
        try {
            tipoReservaDAO.eliminarTipoReserva(tipoReserva);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposReservasBO borrarTipoReserva : " + e.toString());
        }
    }

    @Override
    public TipoReserva consultarTipoReservaPorID(BigInteger idRegistro) {
        try {
            TipoReserva registro = tipoReservaDAO.buscarTipoReservaPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposReservasBO borrarTipoReserva : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TipoReserva> consultarTiposReservasRegistrados() {
        try {
            List<TipoReserva> lista = tipoReservaDAO.consultarTiposReservas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposReservasBO consultarTiposReservasRegistrados : " + e.toString());
            return null;
        }
    }
}
