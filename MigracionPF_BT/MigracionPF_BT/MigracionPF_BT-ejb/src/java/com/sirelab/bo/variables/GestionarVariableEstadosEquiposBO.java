/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.GestionarVariableEstadosEquiposBOInterface;
import com.sirelab.dao.interfacedao.EstadoEquipoDAOInterface;
import com.sirelab.entidades.EstadoEquipo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarVariableEstadosEquiposBO implements GestionarVariableEstadosEquiposBOInterface {

    @EJB
    EstadoEquipoDAOInterface estadoEquipoDAO;

    @Override
    public void crearEstadoEquipo(EstadoEquipo estadoEquipo) {
        try {
            estadoEquipoDAO.crearEstadoEquipo(estadoEquipo);;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosEquiposBO crearEstadoEquipo : " + e.toString());
        }
    }

    @Override
    public void editarEstadoEquipo(EstadoEquipo estadoEquipo) {
        try {
            estadoEquipoDAO.editarEstadoEquipo(estadoEquipo);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosEquiposBO editarEstadoEquipo : " + e.toString());
        }
    }

    @Override
    public void borrarEstadoEquipo(EstadoEquipo estadoEquipo) {
        try {
            estadoEquipoDAO.eliminarEstadoEquipo(estadoEquipo);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosEquiposBO borrarEstadoEquipo : " + e.toString());
        }
    }

    @Override
    public EstadoEquipo consultarEstadoEquipoPorID(BigInteger idRegistro) {
        try {
            EstadoEquipo registro = estadoEquipoDAO.buscarEstadoEquipoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosEquiposBO borrarEstadoEquipo : " + e.toString());
            return null;
        }
    }

    @Override
    public List<EstadoEquipo> consultarEstadosEquiposRegistrados() {
        try {
            List<EstadoEquipo> lista = estadoEquipoDAO.consultarEstadosEquipos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableEstadosEquiposBO consultarEstadosEquiposRegistrados : " + e.toString());
            return null;
        }
    }
}
