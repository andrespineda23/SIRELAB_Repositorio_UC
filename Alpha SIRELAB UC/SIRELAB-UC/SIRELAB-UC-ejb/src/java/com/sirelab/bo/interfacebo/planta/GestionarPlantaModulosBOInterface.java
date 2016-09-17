/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.planta;

import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface GestionarPlantaModulosBOInterface {

    public List<Departamento> consultarDepartamentosRegistrados();

    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro);

    public ModuloLaboratorio obtenerModuloLaboratorioPorCodigoYSala(String codigo, BigInteger sala);

    public List<Laboratorio> consultarLaboratoriosRegistrados();

    public List<Sede> consultarSedesRegistradas();

    public List<Edificio> consultarEdificiosPorIDSede(BigInteger sede);

    public List<ModuloLaboratorio> consultarModulosLaboratorioPorParametro(Map<String, String> filtros);

    public void crearNuevoModuloLaboratorio(ModuloLaboratorio moduloLaboratorio);

    public void modificarInformacionModuloLaboratorio(ModuloLaboratorio moduloLaboratorio);

    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDDepartamento(BigInteger departamento);

    public ModuloLaboratorio obtenerModuloLaboratorioPorIDModuloLaboratorio(BigInteger idModuloLaboratorio);

    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDEdificio(BigInteger edificio);

    public SalaLaboratorio obtenerSalaLaboratorioPorID(BigInteger sala);

    public int validarCantidadModulosSala(BigInteger sala);

    public List<Departamento> consultarDepartamentosActivosRegistrados();

    public List<Laboratorio> consultarLaboratoriosActivosPorIDDepartamento(BigInteger departamento);

    public List<SalaLaboratorio> obtenerSalasLaboratorioPorEdificio(BigInteger edificio);

    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDLaboratorio(BigInteger laboratorio);

    public List<SalaLaboratorio> consultarSalasLaboratoriosActivosPorIDLaboratorio(BigInteger laboratorio);

    public Integer obtenerEquiposAsociados(BigInteger modulo);

    public boolean eliminarModuloLaboratorio(ModuloLaboratorio modulo);

}
