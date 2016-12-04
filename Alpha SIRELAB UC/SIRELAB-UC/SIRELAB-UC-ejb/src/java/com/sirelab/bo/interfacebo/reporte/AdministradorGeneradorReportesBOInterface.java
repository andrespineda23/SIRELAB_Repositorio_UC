/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.reporte;

import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Proveedor;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.entidades.SalaLaboratorio;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministradorGeneradorReportesBOInterface {

    public List<HojaVidaEquipo> consultarHojaVidaEquipos();

    public List<Proveedor> consultarProveedoresRegistrados();

    public List<ReservaSala> obtenerReservasSalaPorUsuario(String usuario);

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorUsuario(String usuario);

    public List<ReservaSala> obtenerReservasSalaPorFechas(String fechaInicio, String fechaFin);

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorFechas(String fechaInicio, String fechaFin);

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorTipoUsuario(Integer usuario);

    public List<ReservaSala> obtenerReservasSalaPorTipoUsuario(Integer usuario);

    public List<Persona> obtenerPersonasDelSistema();

    public List<EquipoElemento> consultarEquiposdeTrabajoRegistrados();

    public List<SalaLaboratorio> obtenerSalasLaboratorio();

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorPeriodoAcademico(BigInteger periodo);

    public List<ReservaSala> obtenerReservasSalaPorPeriodoAcademico(BigInteger periodo);

    public List<PeriodoAcademico> obtenerPeriodosAcademicos();

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorSala(BigInteger sala);

    public List<ReservaSala> obtenerReservasSalaPorSala(BigInteger sala);

    public List<ModuloLaboratorio> consultarModuloLaboratorioRegistrados();

    public List<ComponenteEquipo> consultarComponentesRegistrados();
}
