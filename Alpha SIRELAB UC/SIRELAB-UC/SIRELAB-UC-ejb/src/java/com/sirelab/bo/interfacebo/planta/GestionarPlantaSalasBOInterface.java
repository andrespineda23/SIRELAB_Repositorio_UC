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
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.Sede;
import com.sirelab.entidades.ServiciosSala;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface GestionarPlantaSalasBOInterface {

    public List<Laboratorio> consultarLaboratoriosRegistrados();

    public List<Departamento> consultarDepartamentosRegistrados();

    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento);

    public List<Sede> consultarSedesRegistradas();

    public List<Edificio> consultarEdificiosPorIDSede(BigInteger sede);

    public List<SalaLaboratorio> consultarSalasLaboratoriosPorParametro(Map<String, String> filtros);

    public void crearNuevaSalaLaboratorio(SalaLaboratorio salaLaboratorio, List<ServiciosSala> listaServicios);

    public void modificarInformacionSalaLaboratorio(SalaLaboratorio salaLaboratorio);

    public SalaLaboratorio obtenerSalaLaboratorioPorIDSalaLaboratorio(BigInteger idSalaLaboratorio);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro);

    public List<Departamento> consultarDepartamentosActivosRegistrados();

    public List<Laboratorio> consultarLaboratoriosActivosPorIDDepartamento(BigInteger departamento);

    public List<Sede> consultarSedesActivosRegistradas();

    public List<Edificio> consultarEdificiosActivosPorIDSede(BigInteger sede);

    public Laboratorio obtenerLaboratorioPorId(BigInteger idRegistro);

    public List<ServiciosSala> consultarServiciosSalaRegistradas();

    public List<ServiciosSala> consultarServiciosSalaActivosRegistradas();

    public SalaLaboratorio obtenerSalaLaboratorioPorCodigoEdificioyLaboratorio(String codigo, BigInteger edificio, BigInteger laboratorio);

    public List<SalaLaboratorioxServicios> obtenerSalaLaboratorioxServiciosPorIdSala(BigInteger sala);

    public void almacenarModificacionesSalaServicio(List<SalaLaboratorioxServicios> lista);

    public String obtenerCostoCalculadoSalaLaboratorio(BigInteger idSala);
}
