/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.planta;

import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AndresPineda
 */
public interface GestionarPlantaLaboratoriosPorAreasBOInterface {

    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreasPorParametro(Map<String, String> filtros);

    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento);

    public List<AreaProfundizacion> consultarAreasProfundizacionRegistradas();

    public void crearLaboratoriosPorAreas(LaboratoriosPorAreas registro);

    public void editarLaboratoriosPorAreas(LaboratoriosPorAreas registro);

    public void eliminarLaboratoriosPorAreas(LaboratoriosPorAreas registro);

    public List<Departamento> consultarDepartamentosRegistrados();

    public LaboratoriosPorAreas consultarLaboratorioPorAreaPorID(BigInteger idRegistro);

    public LaboratoriosPorAreas consultarLaboratorioPorAreaPorLabYArea(BigInteger laboratorio, BigInteger area);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro);

    public Boolean validarCambioEstadoRegistro(BigInteger laboratorioporarea);

    public List<Departamento> consultarDepartamentosActivosRegistrados();

    public List<Laboratorio> consultarLaboratoriosActivosPorIDDepartamento(BigInteger departamento);
}
