/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableEstadosReservasBOInterface;
import com.sirelab.dao.interfacedao.EstadoReservaDAOInterface;
import com.sirelab.entidades.EstadoReserva;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarVariableEstadosReservasBO implements GestionarVariableEstadosReservasBOInterface {

    static Logger logger = Logger.getLogger(GestionarVariableEstadosReservasBO.class);
    
    @EJB
    EstadoReservaDAOInterface estadoReservaDAO;

    @Override
    public void crearEstadoReserva(EstadoReserva estadoReserva) {
        try {
            estadoReservaDAO.crearEstadoReserva(estadoReserva);;
        } catch (Exception e) {
            logger.error("Error GestionarVariableEstadosReservasBO crearEstadoReserva : " + e.toString(),e);
        }
    }

    @Override
    public void editarEstadoReserva(EstadoReserva estadoReserva) {
        try {
            estadoReservaDAO.editarEstadoReserva(estadoReserva);
        } catch (Exception e) {
            logger.error("Error GestionarVariableEstadosReservasBO editarEstadoReserva : " + e.toString(),e);
        }
    }

    @Override
    public void borrarEstadoReserva(EstadoReserva estadoReserva) {
        try {
            estadoReservaDAO.eliminarEstadoReserva(estadoReserva);
        } catch (Exception e) {
            logger.error("Error GestionarVariableEstadosReservasBO borrarEstadoReserva : " + e.toString(),e);
        }
    }

    @Override
    public EstadoReserva consultarEstadoReservaPorID(BigInteger idRegistro) {
        try {
            EstadoReserva registro = estadoReservaDAO.buscarEstadoReservaPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarVariableEstadosReservasBO borrarEstadoReserva : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EstadoReserva> consultarEstadosReservasRegistrados() {
        try {
            List<EstadoReserva> lista = estadoReservaDAO.consultarEstadosReservas();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarVariableEstadosReservasBO consultarEstadosReservasRegistrados : " + e.toString(),e);
            return null;
        }
    }
}
