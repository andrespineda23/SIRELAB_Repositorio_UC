/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.dao.interfacedao.AsignaturaPorPlanEstudioDAOInterface;
import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.dao.interfacedao.EstadoReservaDAOInterface;
import com.sirelab.dao.interfacedao.EstudianteDAOInterface;
import com.sirelab.dao.interfacedao.GuiaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.PeriodoAcademicoDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.dao.interfacedao.ReservaDAOInterface;
import com.sirelab.dao.interfacedao.ReservaEquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.ReservaModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ReservaSalaDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.TipoReservaDAOInterface;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.EstadoReserva;
import com.sirelab.entidades.Estudiante;
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
import com.sirelab.entidades.TipoReserva;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarReservasBO implements AdministrarReservasBOInterface {

    @EJB
    ReservaDAOInterface reservaDAO;
    @EJB
    ReservaSalaDAOInterface reservaSalaDAO;
    @EJB
    DocenteDAOInterface docenteDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    EstudianteDAOInterface estudianteDAO;
    @EJB
    EntidadExternaDAOInterface entidadExternaDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;
    @EJB
    TipoReservaDAOInterface tipoReservaDAO;
    @EJB
    GuiaLaboratorioDAOInterface guiaLaboratorioDAO;
    @EJB
    AsignaturaPorPlanEstudioDAOInterface asignaturaPorPlanEstudioDAO;
    @EJB
    PlanEstudiosDAOInterface planEstudiosDAO;
    @EJB
    EstadoReservaDAOInterface estadoReservaDAO;
    @EJB
    PeriodoAcademicoDAOInterface periodoAcademicoDAO;
    @EJB
    ReservaEquipoElementoDAOInterface reservaEquipoElementoDAO;
    @EJB
    ReservaModuloLaboratorioDAOInterface reservaModuloLaboratorioDAO;
    @EJB
    ModuloLaboratorioDAOInterface moduloLaboratorioDAO;

    @Override
    public List<ReservaSala> consultarReservasSalaPorPersona(BigInteger persona) {
        try {
            List<ReservaSala> lista = reservaSalaDAO.buscarReservaSalasSalaPorPersona(persona);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarReservasSalaPorPersona: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> consultarReservasModuloPorPersona(BigInteger persona) {
        try {
            List<ReservaModuloLaboratorio> lista = reservaModuloLaboratorioDAO.buscarReservaModuloPorPersona(persona);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarReservasModuloPorPersona: " + e.toString());
            return null;
        }
    }

    //@Override
    public Docente consultarDocentePorID(BigInteger idRegistro) {
        try {
            Docente registro = docenteDAO.buscarDocentePorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarDocentePorID: " + e.toString());
            return null;
        }
    }

    @Override
    public Persona obtenerPersonaConsultarReservas(String tipoUsuario, BigInteger idPersona) {
        // idTipoUsuario : 2- Docente / 3- Estudiante / 4-EncargadoLab / 5-EntidadExterna
        try {
            Persona registro = null;
            if ("ESTUDIANTE".equalsIgnoreCase(tipoUsuario)) {
                Estudiante estudiante = estudianteDAO.buscarEstudiantePorID(idPersona);
                registro = estudiante.getPersona();
            } else {
                if ("DOCENTE".equalsIgnoreCase(tipoUsuario)) {
                    Docente docente = docenteDAO.buscarDocentePorID(idPersona);
                    registro = docente.getPersona();
                } else {
                    EntidadExterna entidad = entidadExternaDAO.buscarEntidadExternaPorID(idPersona);
                    //registro = entidad.getPersona();
                }
            }
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerPersonaConsultarReservas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosRegistradosActivos() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarLaboratoriosRegistradosActivos: " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalaLaboratorioPorIdLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioActivos(laboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarSalaLaboratorioPorIdLaboratorio: " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarReservaSalaDisposible(Date fecha, String horaInicio, BigInteger sala) {
        try {
            ReservaSala registro = reservaSalaDAO.buscarReservaSalaPorFechaHoraSala(fecha, horaInicio, sala);
            if (null == registro) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO validarReservaSalaDisposible: " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarReservaModuloSalaDisposible(Date fecha, String horaInicio, BigInteger sala) {
        try {
            ReservaModuloLaboratorio registro = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorFechaHoraSala(fecha, horaInicio, sala);
            if (null == registro) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO validarReservaModuloSalaDisposible: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> obtenerCantidadReservasModuloPorParametros(Date fecha, String horaInicio, BigInteger sala) {
        try {
            List<ReservaModuloLaboratorio> registro = reservaModuloLaboratorioDAO.buscarCantidadReservaModuloLaboratorioPorParametros(fecha, horaInicio, sala);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerCantidadReservasModuloPorParametros: " + e.toString());
            return null;
        }
    }

    @Override
    public List<TipoReserva> consultarTiposReservas() {
        try {
            List<TipoReserva> lista = tipoReservaDAO.consultarTiposReservas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarTiposReservas: " + e.toString());
            return null;
        }
    }

    @Override
    public List<GuiaLaboratorio> consultarGuiasLaboratorioPorIdAreaPlan(BigInteger areaPlan) {
        try {
            List<GuiaLaboratorio> lista = guiaLaboratorioDAO.consultarGuiasLaboratorioPorIdAreaPlan(areaPlan);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarGuiasLaboratorio: " + e.toString());
            return null;
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturasPorPlanEstudioPorIdPlan(BigInteger plan) {
        try {
            List<AsignaturaPorPlanEstudio> lista = asignaturaPorPlanEstudioDAO.consultarAsignaturaPorPlanEstudiosActivoIdPlanEstudio(plan);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarAsignaturasPorPlanEstudioPorIdPlan: " + e.toString());
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanEstudiosActivos() {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarPlanEstudiosActivos: " + e.toString());
            return null;
        }
    }

    @Override
    public EstadoReserva obtenerEstadoReservaPorId(BigInteger idRegistro) {
        try {
            EstadoReserva registro = estadoReservaDAO.buscarEstadoReservaPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerEstadoReservaPorId: " + e.toString());
            return null;
        }
    }

    @Override
    public PeriodoAcademico obtenerUltimoPeriodoAcademico() {
        try {
            PeriodoAcademico registro = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerUltimoPeriodoAcademico: " + e.toString());
            return null;
        }
    }

    @Override
    public Reserva registrarNuevaReservaSala(Reserva reserva, ReservaSala reservaSala) {
        try {
            reservaDAO.crearReserva(reserva);
            Reserva nuevaReserva = reservaDAO.buscarUltimaReservaPersona(reserva.getPersona().getIdpersona(), reserva.getHorainicio(), reserva.getFechareserva());
            reservaSala.setReserva(nuevaReserva);
            reservaSalaDAO.crearReservaSala(reservaSala);
            return nuevaReserva;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO registrarNuevaReservaSala: " + e.toString());
            return null;
        }
    }

    @Override
    public Reserva registrarNuevaReservaModulo(Reserva reserva, ReservaModuloLaboratorio reservaModuloLaboratorio) {
        try {
            reservaDAO.crearReserva(reserva);
            Reserva nuevaReserva = reservaDAO.buscarUltimaReservaPersona(reserva.getPersona().getIdpersona(), reserva.getHorainicio(), reserva.getFechareserva());
            reservaModuloLaboratorio.setReserva(nuevaReserva);
            reservaModuloLaboratorioDAO.crearReservaModuloLaboratorio(reservaModuloLaboratorio);
            return nuevaReserva;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO registrarNuevaReservaModulo: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ReservaEquipoElemento> obtenerReservasEquipoPorIdReserva(BigInteger reserva) {
        try {
            List<ReservaEquipoElemento> lista = reservaEquipoElementoDAO.buscarReservaEquipoElementosPorReserva(reserva);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerReservasEquipoPorIdReserva: " + e.toString());
            return null;
        }
    }

    @Override
    public void actualizarValorReserva(Reserva reserva) {
        try {
            reservaDAO.editarReserva(reserva);;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO actualizarValorReserva: " + e.toString());
        }
    }

    @Override
    public List<ModuloLaboratorio> obtenerModuloLaboratoriosPorSala(BigInteger sala) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModuloLaboratorioActivosPorIDSalaLaboratorio(sala);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerModuloLaboratoriosPorSala: " + e.toString());
            return null;
        }
    }

    @Override
    public ReservaSala obtenerReservaSalaPorId(BigInteger idReserva) {
        try {
            ReservaSala registro = reservaSalaDAO.buscarReservaSalaPorID(idReserva);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerReservaSalaPorId: " + e.toString());
            return null;
        }
    }

    @Override
    public Reserva obtenerReservaPorId(BigInteger idReserva) {
        try {
            Reserva registro = reservaDAO.buscarReservaPorID(idReserva);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerReservaPorId: " + e.toString());
            return null;
        }
    }

    @Override
    public ReservaModuloLaboratorio obtenerReservaModuloLaboratorioPorId(BigInteger idRegistro) {
        try {
            ReservaModuloLaboratorio registro = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerReservaModuloLaboratorioPorId: " + e.toString());
            return null;
        }
    }

    @Override
    public void actualizarGuiaLaboratorioReserva(ReservaSala reservaSala) {
        try {
            reservaSalaDAO.editarReservaSala(reservaSala);
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO actualizarGuiaLaboratorioReserva: " + e.toString());
        }
    }

    @Override
    public TipoReserva obtenerTipoReservaPorId(BigInteger idRegistro) {
        try {
            TipoReserva registro = tipoReservaDAO.buscarTipoReservaPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO actualizarGuiaLaboratorioReserva: " + e.toString());
            return null;
        }
    }
}
