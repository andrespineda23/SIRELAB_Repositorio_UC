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

/**
 *
 * @author ELECTRONICA
 */
public interface ReservaModuloLaboratorioDAOInterface {

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
}
