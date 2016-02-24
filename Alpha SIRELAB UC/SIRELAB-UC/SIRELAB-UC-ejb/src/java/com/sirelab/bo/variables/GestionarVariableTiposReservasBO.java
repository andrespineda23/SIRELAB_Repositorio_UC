/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposReservasBOInterface;
import com.sirelab.dao.interfacedao.TipoReservaDAOInterface;
import com.sirelab.entidades.TipoReserva;
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
public class GestionarVariableTiposReservasBO implements GestionarVariableTiposReservasBOInterface {

    static Logger logger = Logger.getLogger(GestionarVariableTiposReservasBO.class);
    
    @EJB
    TipoReservaDAOInterface tipoReservaDAO;

    @Override
    public void crearTipoReserva(TipoReserva tipoReserva) {
        try {
            tipoReservaDAO.crearTipoReserva(tipoReserva);;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposReservasBO crearTipoReserva : " + e.toString(),e);
        }
    }

    @Override
    public void editarTipoReserva(TipoReserva tipoReserva) {
        try {
            tipoReservaDAO.editarTipoReserva(tipoReserva);
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposReservasBO editarTipoReserva : " + e.toString(),e);
        }
    }

    @Override
    public void borrarTipoReserva(TipoReserva tipoReserva) {
        try {
            tipoReservaDAO.eliminarTipoReserva(tipoReserva);
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposReservasBO borrarTipoReserva : " + e.toString(),e);
        }
    }

    @Override
    public TipoReserva consultarTipoReservaPorID(BigInteger idRegistro) {
        try {
            TipoReserva registro = tipoReservaDAO.buscarTipoReservaPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposReservasBO borrarTipoReserva : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<TipoReserva> consultarTiposReservasRegistrados() {
        try {
            List<TipoReserva> lista = tipoReservaDAO.consultarTiposReservas();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposReservasBO consultarTiposReservasRegistrados : " + e.toString(),e);
            return null;
        }
    }
}
