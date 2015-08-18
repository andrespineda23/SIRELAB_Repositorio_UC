/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.universidad;

import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.PlanEstudios;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarPlanAsignaturaBOInterface {

    public List<Carrera> obtenerCarrerasRegistradas();

    public List<PlanEstudios> obtenerPlanEstudiosPorCarrera(BigInteger carrera);

    public List<Asignatura> consultarAsignaturasRegistradas();

    public void crearAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignaturaPorPlanEstudio);

    public void editarAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignaturaPorPlanEstudio);

    public AsignaturaPorPlanEstudio obtenerAsignaturaPorPlanEstudioPorID(BigInteger idRegistro);

    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanPorParametro(Map<String, String> filtros);

    public AsignaturaPorPlanEstudio buscarAsignaturaPorPlanEstudioPorIDS(BigInteger plan, BigInteger asignatura);
}
