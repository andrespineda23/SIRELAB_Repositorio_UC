/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface AsignaturaPorPlanEstudioDAOInterface {

    public void crearAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignatura);

    public void editarAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignatura);

    public void eliminarAsignaturaPorPlanEstudio(AsignaturaPorPlanEstudio asignatura);

    public List<AsignaturaPorPlanEstudio> consultarAsignaturasPorPlanEstudio();

    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudiosIdPlanEstudio(BigInteger planEstudio);
    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudiosActivoIdPlanEstudio(BigInteger planEstudio);

    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudiosIdAsignatura(BigInteger asignatura);

    public AsignaturaPorPlanEstudio buscarAsignaturaPorPlanEstudioPorID(BigInteger idRegistro);

    public AsignaturaPorPlanEstudio buscarAsignaturaPorPlanEstudioPorPlanYAsignatura(BigInteger plan, BigInteger asignatura);

    public List<AsignaturaPorPlanEstudio> buscarAsignaturaPorPlanEstudioPorFiltrado(Map<String, String> filters);
}
