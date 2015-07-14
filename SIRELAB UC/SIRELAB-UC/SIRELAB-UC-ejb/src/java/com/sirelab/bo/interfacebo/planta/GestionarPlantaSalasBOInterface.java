/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.planta;

import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface GestionarPlantaSalasBOInterface {

    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreasRegistradas();

    public List<Departamento> consultarDepartamentosRegistrados();

    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento);

    public List<AreaProfundizacion> consultarAreasProfundizacionRegistradas();

    public List<Sede> consultarSedesRegistradas();

    public List<Edificio> consultarEdificiosPorIDSede(BigInteger sede);

    public List<SalaLaboratorio> consultarSalasLaboratoriosPorParametro(Map<String, String> filtros);

    public void crearNuevaSalaLaboratorio(SalaLaboratorio salaLaboratorio);

    public void modificarInformacionSalaLaboratorio(SalaLaboratorio salaLaboratorio);

    public SalaLaboratorio obtenerSalaLaboratorioPorIDSalaLaboratorio(BigInteger idSalaLaboratorio);

    public SalaLaboratorio obtenerSalaLaboratorioPorCodigoEdificioLabArea(String codigo, BigInteger edificio, BigInteger laboratorioArea);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro);

}
