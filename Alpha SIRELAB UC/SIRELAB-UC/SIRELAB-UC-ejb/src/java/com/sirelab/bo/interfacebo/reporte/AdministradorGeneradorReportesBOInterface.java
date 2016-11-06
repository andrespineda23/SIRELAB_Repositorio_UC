/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.reporte;

import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.Persona;
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

    public List<Persona> obtenerPersonasDelSistema();

    public List<EquipoElemento> consultarEquiposdeTrabajoRegistrados();

    public List<SalaLaboratorio> obtenerSalasLaboratorio();

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorPeriodoAcademico(BigInteger periodo);

    public List<ReservaSala> obtenerReservasSalaPorPeriodoAcademico(BigInteger periodo);

    public List<PeriodoAcademico> obtenerPeriodosAcademicos();

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorSala(BigInteger sala);

    public List<ReservaSala> obtenerReservasSalaPorSala(BigInteger sala);

}
