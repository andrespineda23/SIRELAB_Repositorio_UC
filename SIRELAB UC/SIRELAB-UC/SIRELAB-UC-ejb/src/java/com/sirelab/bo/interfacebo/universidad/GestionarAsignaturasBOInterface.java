/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.universidad;

import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.PlanEstudios;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface GestionarAsignaturasBOInterface {

    public List<Departamento> consultarDepartamentosRegistrados();

    public List<Carrera> consultarCarrerasPorIDDepartamento(BigInteger departamentos);

    public List<PlanEstudios> consultarPlanesEstudiosPorIDCarrera(BigInteger carrera);

    public List<Asignatura> consultarAsignaturasPorParametro(Map<String, String> filtros);

    public void modificarInformacionAsignatura(Asignatura asignatura);

    public Asignatura obtenerAsignaturaPorIDAsignatura(BigInteger idAsignatura);

    public Asignatura obtenerAsignaturaPorCodigo(String codigo);

    public void crearAsignaturaPorPlanEstudio(Asignatura asignatura, PlanEstudios planEstudios);

    public AsignaturaPorPlanEstudio consultarAsignaturaPorPlanEstudioRegistrado(BigInteger plan, String codigo);

}
