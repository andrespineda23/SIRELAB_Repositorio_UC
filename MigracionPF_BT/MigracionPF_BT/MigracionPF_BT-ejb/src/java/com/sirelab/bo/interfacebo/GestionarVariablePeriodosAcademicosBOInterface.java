/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo;

import com.sirelab.entidades.PeriodoAcademico;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariablePeriodosAcademicosBOInterface {

    public List<PeriodoAcademico> consultarPeriodosAcademicos();

    public void crearPeriodoAcademico(PeriodoAcademico periodo);

    public void editarPeriodoAcademico(PeriodoAcademico periodo);

    public PeriodoAcademico consultarPeriodoAcademicoPorID(BigInteger idRegistro);
}
