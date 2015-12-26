/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.planta;

import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.ServiciosSala;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AndresPineda
 */
public interface GestionarPlantaSalaLaboratorioxServiciosBOInterface {

    public SalaLaboratorioxServicios consultarSalaLaboratorioXServicioPorID(BigInteger idRegistro);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro);

    public SalaLaboratorioxServicios consultarSalaLaboratorioXServicioPorSalayServicio(BigInteger sala, BigInteger servicio);

    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServiciosPorParametro(Map<String, String> filtros);

    public List<Departamento> consultarDepartamentosRegistrados();

    public List<Departamento> consultarDepartamentosActivosRegistrados();

    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento);

    public List<SalaLaboratorio> consultarSalaLaboratorioPorIDLaboratorio(BigInteger laboratorio);

    public List<Laboratorio> consultarLaboratoriosActivosPorIDDepartamento(BigInteger departamento);

    public List<SalaLaboratorio> consultarSalaLaboratoriosActivosPorIDLaboratorio(BigInteger laboratorio);

    public List<Laboratorio> consultarLaboratoriosRegistrados();

    public List<ServiciosSala> consultarServiciosSalaRegistradas();

    public void crearSalaLaboratorioxServicios(SalaLaboratorioxServicios registro);

    public void editarSalaLaboratorioxServicios(SalaLaboratorioxServicios registro);

    public void eliminarSalaLaboratorioxServicios(SalaLaboratorioxServicios registro);

    public SalaLaboratorio obtenerSalaLaboratorioPorId(BigInteger idRegistro);
}
