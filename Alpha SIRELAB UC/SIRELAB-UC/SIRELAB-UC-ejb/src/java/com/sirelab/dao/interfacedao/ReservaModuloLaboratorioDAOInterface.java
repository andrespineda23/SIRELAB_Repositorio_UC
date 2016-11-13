/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.ReservaModuloLaboratorio;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface ReservaModuloLaboratorioDAOInterface {

    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorFechasYPeriodo(Date fechaInicio, Date fechaFin, BigInteger periodo);

    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorPeriodoYTipoUsuario(BigInteger usuario, BigInteger periodo);

    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorIdSalaYPeriodo(BigInteger sala, BigInteger periodo);

    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorIdPeriodoAcademico(BigInteger periodo);

    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorTipoUsuario(BigInteger tipoUsuario);

    public void crearReservaModuloLaboratorio(ReservaModuloLaboratorio reserva);

    public void editarReservaModuloLaboratorio(ReservaModuloLaboratorio reserva);

    public void eliminarReservaModuloLaboratorio(ReservaModuloLaboratorio reserva);

    public List<ReservaModuloLaboratorio> consultarReservaModuloLaboratoriosModuloLaboratorio();

    public ReservaModuloLaboratorio buscarReservaModuloLaboratorioPorID(BigInteger idRegistro);

    public ReservaModuloLaboratorio buscarReservaModuloLaboratorioPorFechaHoraSala(Date fecha, String horaInicio, BigInteger sala);

    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratoriosModuloLaboratorioPorPersona(BigInteger persona);

    public List<ReservaModuloLaboratorio> buscarCantidadReservaModuloLaboratorioPorParametros(Date fecha, String horaInicio, BigInteger sala);

    public List<ReservaModuloLaboratorio> buscarReservaModuloPorPersona(BigInteger persona);

    public ReservaModuloLaboratorio buscarReservaModuloLaboratorioPorIdReserva(BigInteger reserva);

    public List<ReservaModuloLaboratorio> buscarReservaModuloLaboratorioPorFiltrado(Map<String, String> filters);
}
