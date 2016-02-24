/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.PeriodoAcademico;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PeriodoAcademicoDAOInterface {

    public Integer obtenerCantidadPeriodosAcademicosActivos() ;
    
    public void crearPeriodoAcademico(PeriodoAcademico periodo);

    public void editarPeriodoAcademico(PeriodoAcademico periodo);

    public void eliminarPeriodoAcademico(PeriodoAcademico periodo);

    public List<PeriodoAcademico> consultarPeriodosAcademicos();

    public PeriodoAcademico buscarPeriodoAcademicoPorID(BigInteger idRegistro);

    public PeriodoAcademico buscarPeriodoAcademicoActual();

}
