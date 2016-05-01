/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasUsuarioBOInterface;
import com.sirelab.dao.interfacedao.ReservaDAOInterface;
import com.sirelab.dao.interfacedao.ReservaEquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.ReservaModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ReservaSalaDAOInterface;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaEquipoElemento;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarReservasUsuarioBO implements AdministrarReservasUsuarioBOInterface {

    static Logger logger = Logger.getLogger(AdministrarReservasUsuarioBO.class);

    @EJB
    ReservaDAOInterface reservaDAO;
    @EJB
    ReservaEquipoElementoDAOInterface reservaEquipoElementoDAO;
    @EJB
    ReservaModuloLaboratorioDAOInterface reservaModuloLaboratorioDAO;
    @EJB
    ReservaSalaDAOInterface reservaSalaDAO;

    @Override
    public List<ReservaModuloLaboratorio> consultarReservasModulo(Map<String, String> filtros) {
        try {
            return reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorFiltrado(filtros);
        } catch (Exception e) {
            logger.error("Error consultarReservasModulo AdministrarReservasUsuarioBO: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaSala> consultarReservasSala(Map<String, String> filtros) {
        try {
            return reservaSalaDAO.buscarReservaSalaPorFiltrado(filtros);
        } catch (Exception e) {
            logger.error("Error consultarReservasSala AdministrarReservasUsuarioBO: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaEquipoElemento> consultarReservasEquipo(BigInteger reserva) {
        try {
            return reservaEquipoElementoDAO.buscarReservaEquipoElementosPorReserva(reserva);
        } catch (Exception e) {
            logger.error("Error consultarReservasEquipo AdministrarReservasUsuarioBO: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void modificarInformacionReserva(Reserva reserva) {
        try {
            reservaDAO.editarReserva(reserva);
        } catch (Exception e) {
            logger.error("Error modificarInformacionReserva AdministrarReservasUsuarioBO: " + e.toString(), e);
        }
    }

}
