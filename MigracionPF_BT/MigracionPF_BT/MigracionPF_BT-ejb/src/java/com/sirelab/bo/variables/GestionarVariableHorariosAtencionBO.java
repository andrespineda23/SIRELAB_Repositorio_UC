/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.GestionarVariableHorariosAtencionBOInterface;
import com.sirelab.dao.interfacedao.HorarioAtencionDAOInterface;
import com.sirelab.entidades.HorarioAtencion;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarVariableHorariosAtencionBO implements GestionarVariableHorariosAtencionBOInterface {

    @EJB
    HorarioAtencionDAOInterface horarioAtencionInterface;

    @Override
    public List<HorarioAtencion> consultarPeriodosAcademicos() {
        try {
            List<HorarioAtencion> lista = horarioAtencionInterface.consultarHorariosAtencion();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableHorariosAtencionBO consultarPeriodosAcademicos: " + e.toString());
            return null;
        }
    }

    @Override
    public HorarioAtencion consultarHorarioAtencionPorID(BigInteger idRegistro) {
        try {
            HorarioAtencion registro = horarioAtencionInterface.buscarHorarioAtencionPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableHorariosAtencionBO consultarHorarioAtencionPorID: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearHorarioAtencion(HorarioAtencion horario) {
        try {
            horarioAtencionInterface.crearHorarioAtencion(horario);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableHorariosAtencionBO crearHorarioAtencion: " + e.toString());
        }
    }

    @Override
    public void editarHorarioAtencion(HorarioAtencion horario) {
        try {
            horarioAtencionInterface.editarHorarioAtencion(horario);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableHorariosAtencionBO editarHorarioAtencion: " + e.toString());
        }
    }
}
