/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.recursos;

import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.GuiaLaboratorio;
import com.sirelab.entidades.PlanEstudios;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarRecursoGuiasLaboratorioBOInterface {

    public List<Carrera> consultarCarrerasRegistradas();

    public GuiaLaboratorio consultarGuiaLaboratorioPorUbicacion(String ubicacion);

    public List<PlanEstudios> consultarPlanesEstidoPorCarrera(BigInteger carrera);

    public List<GuiaLaboratorio> consultarGuiasLaboratorioPorParametro(Map<String, String> filtros);

    public List<Asignatura> consultarAsignaturasRegistradas();

    public List<GuiaLaboratorio> consultarGuiasLaboratorioRegistradas();

    public GuiaLaboratorio obtenerGuiaLaboratorioPorID(BigInteger idRegistro);

    public void crearGuiaLaboratorio(GuiaLaboratorio guiaLaboratorio);

    public void modificarGuiaLaboratorio(GuiaLaboratorio guiaLaboratorio);

    public void borrarGuiaLaboratorio(GuiaLaboratorio guiaLaboratorio);

    public List<AsignaturaPorPlanEstudio> consultarAsignaturaPorPlanEstudioPorIDPlan(BigInteger plan);
}
