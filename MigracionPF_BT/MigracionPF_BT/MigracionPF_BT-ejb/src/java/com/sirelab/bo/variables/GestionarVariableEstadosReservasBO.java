/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.GestionarVariableEstadosReservasBOInterface;
import com.sirelab.dao.interfacedao.EstadoReservaDAOInterface;
import com.sirelab.entidades.EstadoReserva;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarVariableEstadosReservasBO implements GestionarVariableEstadosReservasBOInterface {

    @EJB
    EstadoReservaDAOInterface estadoReservaDAO;

    @Override
    public void crearEstadoReserva(EstadoReserva estadoReserva) {
        try {
            estadoReservaDAO.crearEstadoReserva(estadoReserva);;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosReservasBO crearEstadoReserva : " + e.toString());
        }
    }

    @Override
    public void editarEstadoReserva(EstadoReserva estadoReserva) {
        try {
            estadoReservaDAO.editarEstadoReserva(estadoReserva);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosReservasBO editarEstadoReserva : " + e.toString());
        }
    }

    @Override
    public void borrarEstadoReserva(EstadoReserva estadoReserva) {
        try {
            estadoReservaDAO.eliminarEstadoReserva(estadoReserva);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosReservasBO borrarEstadoReserva : " + e.toString());
        }
    }

    @Override
    public EstadoReserva consultarEstadoReservaPorID(BigInteger idRegistro) {
        try {
            EstadoReserva registro = estadoReservaDAO.buscarEstadoReservaPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosReservasBO borrarEstadoReserva : " + e.toString());
            return null;
        }
    }

    @Override
    public List<EstadoReserva> consultarEstadosReservasRegistrados() {
        try {
            List<EstadoReserva> lista = estadoReservaDAO.consultarEstadosReservas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosReservasBO consultarEstadosReservasRegistrados : " + e.toString());
            return null;
        }
    }
}
