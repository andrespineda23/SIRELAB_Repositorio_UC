/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariablePeriodosAcademicosBOInterface;
import com.sirelab.dao.interfacedao.PeriodoAcademicoDAOInterface;
import com.sirelab.entidades.PeriodoAcademico;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarVariablePeriodosAcademicosBO implements GestionarVariablePeriodosAcademicosBOInterface {

    @EJB
    PeriodoAcademicoDAOInterface periodoAcademicoInterface;

    @Override
    public List<PeriodoAcademico> consultarPeriodosAcademicos() {
        try {
            List<PeriodoAcademico> lista = periodoAcademicoInterface.consultarPeriodosAcademicos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePeriodosAcademicosBO consultarPeriodosAcademicos: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearPeriodoAcademico(PeriodoAcademico periodo) {
        try {
            periodoAcademicoInterface.crearPeriodoAcademico(periodo);
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePeriodosAcademicosBO crearPeriodoAcademico: " + e.toString());
        }
    }

    @Override
    public void editarPeriodoAcademico(PeriodoAcademico periodo) {
        try {
            periodoAcademicoInterface.editarPeriodoAcademico(periodo);
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePeriodosAcademicosBO editarPeriodoAcademico: " + e.toString());
        }
    }

    @Override
    public PeriodoAcademico consultarPeriodoAcademicoPorID(BigInteger idRegistro) {
        try {
            PeriodoAcademico registro = periodoAcademicoInterface.buscarPeriodoAcademicoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePeriodosAcademicosBO consultarPeriodoAcademicoPorID: " + e.toString());
            return null;
        }
    }
}
