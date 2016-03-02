/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.reservas;

import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.GuiaLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaEquipoElemento;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.ServiciosSala;
import com.sirelab.entidades.TipoReserva;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministrarReservasBOInterface {

    public List<ReservaSala> consultarReservasSalaPorPersona(BigInteger persona);

    public Docente consultarDocentePorID(BigInteger idRegistro);

    public Persona obtenerPersonaConsultarReservas(String tipoUsuario, BigInteger idPersona);

    public List<Laboratorio> consultarLaboratoriosRegistradosActivos();

    public List<SalaLaboratorio> consultarSalaLaboratorioPorIdLaboratorio(BigInteger laboratorio);

    public Boolean validarReservaSalaDisposible(Date fecha, String horaInicio, BigInteger sala);

    public List<TipoReserva> consultarTiposReservas();

    public List<GuiaLaboratorio> consultarGuiasLaboratorioPorIdAreaPlan(BigInteger areaPlan);

    public List<AsignaturaPorPlanEstudio> consultarAsignaturasPorPlanEstudioPorIdPlan(BigInteger plan);

    public List<PlanEstudios> consultarPlanEstudiosActivos();

    public EstadoReserva obtenerEstadoReservaPorId(BigInteger idRegistro);

    public PeriodoAcademico obtenerUltimoPeriodoAcademico();

    public Reserva registrarNuevaReservaSala(Reserva reserva, ReservaSala reservaSala);

    public List<ReservaEquipoElemento> obtenerReservasEquipoPorIdReserva(BigInteger reserva);

    public void actualizarValorReserva(Reserva reserva);

    public Boolean validarReservaModuloSalaDisposible(Date fecha, String horaInicio, BigInteger sala);

    public List<ReservaModuloLaboratorio> obtenerCantidadReservasModuloPorParametros(Date fecha, String horaInicio, BigInteger sala);

    public List<ModuloLaboratorio> obtenerModuloLaboratoriosPorSala(BigInteger sala);

    public Reserva registrarNuevaReservaModulo(Reserva reserva, ReservaModuloLaboratorio reservaModuloLaboratorio);

    public List<ReservaModuloLaboratorio> consultarReservasModuloPorPersona(BigInteger persona);

    public ReservaSala obtenerReservaSalaPorId(BigInteger idReserva);

    public Reserva obtenerReservaPorId(BigInteger idReserva);

    public ReservaModuloLaboratorio obtenerReservaModuloLaboratorioPorId(BigInteger idRegistro);

    public void actualizarGuiaLaboratorioReserva(ReservaSala reservaSala);

    public TipoReserva obtenerTipoReservaPorId(BigInteger idRegistro);

    public List<ServiciosSala> listaServiciosSalaActivos();

    public List<SalaLaboratorio> consultarSalaLaboratorioPorIdLaboratorioYServicio(BigInteger laboratorio, BigInteger servicio);

    public List<ReservaSala> obtenerReservasSalaPorParametros(SalaLaboratorio sala, Date fecha);

    public Integer obtenerNumeroReservaDia(Date fecha);

    public List<ReservaSala> obtenerReservasModuloSalas(Date fecha, BigInteger sala);

    public Reserva obtenerReservaPorNumero(String numero);

    public ReservaModuloLaboratorio obtenterReservaModuloPorIdReserva(BigInteger reserva);

    public ReservaSala obtenterReservaSalaPorIdReserva(BigInteger reserva);

    public void almacenarReservaEquipo(ReservaEquipoElemento reserva);

    public List<SalaLaboratorio> consultarSalaLaboratorioPorIdLaboratorioReserva(BigInteger laboratorio);

    public List<SalaLaboratorio> buscarBodegaPorLaboratorioEdificio(BigInteger laboratorio);

    public List<EquipoElemento> obtenerEquiposBodega(BigInteger bodega);
}
