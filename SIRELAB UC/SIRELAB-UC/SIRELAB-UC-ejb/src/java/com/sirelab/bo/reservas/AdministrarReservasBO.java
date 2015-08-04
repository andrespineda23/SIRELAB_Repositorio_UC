/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.dao.interfacedao.ReservaDAOInterface;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Reserva;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarReservasBO implements AdministrarReservasBOInterface {

    @EJB
    ReservaDAOInterface reservaDAO;
    @EJB
    DocenteDAOInterface docenteDAO;

    @Override
    public List<Reserva> consultarReservasPorPersona(BigInteger persona) {
        try {
            List<Reserva> lista = reservaDAO.buscarReservasPorPersona(persona);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarReservasPorPersona: " + e.toString());
            return null;
        }
    }

    //@Override
    public Docente consultarDocentePorID(BigInteger idRegistro) {
        try {
            Docente registro = docenteDAO.buscarDocentePorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarDocentePorID: " + e.toString());
            return null;
        }
    }
}
