
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
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
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
import com.sirelab.dao.interfacedao.SalaLaboratorioxServiciosDAOInterface;
import com.sirelab.dao.interfacedao.ServiciosSalaDAOInterface;
import com.sirelab.dao.interfacedao.TipoReservaDAOInterface;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.EquipoElemento;
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
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.ServiciosSala;
import com.sirelab.entidades.TipoReserva;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarReservasBO implements AdministrarReservasBOInterface {

    static Logger logger = Logger.getLogger(AdministrarReservasBO.class);

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
    @EJB
    SalaLaboratorioxServiciosDAOInterface salaLaboratorioxServiciosDAO;
    @EJB
    ServiciosSalaDAOInterface serviciosSalaDAO;
    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;

    @Override
    public List<ReservaSala> consultarReservasSalaPorPersona(BigInteger persona) {
        try {
            List<ReservaSala> lista = reservaSalaDAO.buscarReservaSalasSalaPorPersona(persona);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarReservasSalaPorPersona: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> consultarReservasModuloPorPersona(BigInteger persona) {
        try {
            List<ReservaModuloLaboratorio> lista = reservaModuloLaboratorioDAO.buscarReservaModuloPorPersona(persona);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarReservasModuloPorPersona: " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public Docente consultarDocentePorID(BigInteger idRegistro) {
        try {
            Docente registro = docenteDAO.buscarDocentePorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarDocentePorID: " + e.toString(), e);
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
                Docente docente = docenteDAO.buscarDocentePorID(idPersona);
                registro = docente.getPersona();
            }
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerPersonaConsultarReservas : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosRegistradosActivos() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarLaboratoriosRegistradosActivos: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalaLaboratorioPorIdLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioActivosPublicos(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarSalaLaboratorioPorIdLaboratorio: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalaLaboratorioPorIdLaboratorioReserva(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioActivosPublicos(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarSalaLaboratorioPorIdLaboratorio: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalaLaboratorioPorIdLaboratorioYServicio(BigInteger laboratorio, BigInteger servicio) {
        try {
            List<SalaLaboratorioxServicios> lista = salaLaboratorioxServiciosDAO.buscarSalasLaboratorioxServiciosPorLaboratorioyServicioActivoPublico(laboratorio, servicio);
            if (null != lista) {
                List<SalaLaboratorio> salas = new ArrayList<SalaLaboratorio>();
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getSalalaboratorio().getEstadosala() == true) {
                        salas.add(lista.get(i).getSalalaboratorio());
                    }
                }
                return salas;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarSalaLaboratorioPorIdLaboratorio: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Boolean validarReservasPersonaSegunHoraFecha(BigInteger usuario, String tipoUsuario, String horaInicio, Date fecha) {
        try {
            Persona persona = new Persona();
            if ("ESTUDIANTE".equalsIgnoreCase(tipoUsuario)) {
                Estudiante estudiante = estudianteDAO.buscarEstudiantePorID(usuario);
                persona = estudiante.getPersona();
            } else if ("DOCENTE".equalsIgnoreCase(tipoUsuario)) {
                Docente docente = docenteDAO.buscarDocentePorID(usuario);
                persona = docente.getPersona();
            }
            Reserva reserva = reservaDAO.buscarUltimaReservaPersonaConEstado(persona.getIdpersona(), horaInicio, fecha);
            if (null != reserva) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO validarReservasPersonaSegunHoraFecha: " + e.toString(), e);
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
            logger.error("Error AdministrarReservasBO validarReservaSalaDisposible: " + e.toString(), e);
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
            logger.error("Error AdministrarReservasBO validarReservaModuloSalaDisposible: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaModuloLaboratorio> obtenerCantidadReservasModuloPorParametros(Date fecha, String horaInicio, BigInteger sala) {
        try {
            List<ReservaModuloLaboratorio> registro = reservaModuloLaboratorioDAO.buscarCantidadReservaModuloLaboratorioPorParametros(fecha, horaInicio, sala);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerCantidadReservasModuloPorParametros: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<TipoReserva> consultarTiposReservas() {
        try {
            List<TipoReserva> lista = tipoReservaDAO.consultarTiposReservas();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarTiposReservas: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<GuiaLaboratorio> consultarGuiasLaboratorioPorIdAreaPlan(BigInteger areaPlan) {
        try {
            List<GuiaLaboratorio> lista = guiaLaboratorioDAO.consultarGuiasLaboratorioPorIdAreaPlan(areaPlan);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarGuiasLaboratorio: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> consultarAsignaturasPorPlanEstudioPorIdPlan(BigInteger plan) {
        try {
            List<AsignaturaPorPlanEstudio> lista = asignaturaPorPlanEstudioDAO.consultarAsignaturaPorPlanEstudiosActivoIdPlanEstudio(plan);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarAsignaturasPorPlanEstudioPorIdPlan: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanEstudiosActivos() {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudios();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarPlanEstudiosActivos: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EstadoReserva obtenerEstadoReservaPorId(BigInteger idRegistro) {
        try {
            EstadoReserva registro = estadoReservaDAO.buscarEstadoReservaPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerEstadoReservaPorId: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public PeriodoAcademico obtenerUltimoPeriodoAcademico() {
        try {
            PeriodoAcademico registro = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerUltimoPeriodoAcademico: " + e.toString(), e);
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
            logger.error("Error AdministrarReservasBO registrarNuevaReservaSala: " + e.toString(), e);
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
            logger.error("Error AdministrarReservasBO registrarNuevaReservaModulo: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ReservaEquipoElemento> obtenerReservasEquipoPorIdReserva(BigInteger reserva) {
        try {
            List<ReservaEquipoElemento> lista = reservaEquipoElementoDAO.buscarReservaEquipoElementosPorReserva(reserva);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerReservasEquipoPorIdReserva: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void actualizarValorReserva(Reserva reserva) {
        try {
            reservaDAO.editarReserva(reserva);;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO actualizarValorReserva: " + e.toString(), e);
        }
    }

    @Override
    public List<ModuloLaboratorio> obtenerModuloLaboratoriosPorSala(BigInteger sala) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModuloLaboratorioActivosPorIDSalaLaboratorio(sala);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerModuloLaboratoriosPorSala: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public ReservaSala obtenerReservaSalaPorId(BigInteger idReserva) {
        try {
            ReservaSala registro = reservaSalaDAO.buscarReservaSalaPorID(idReserva);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerReservaSalaPorId: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Reserva obtenerReservaPorId(BigInteger idReserva) {
        try {
            Reserva registro = reservaDAO.buscarReservaPorID(idReserva);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerReservaPorId: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public ReservaModuloLaboratorio obtenerReservaModuloLaboratorioPorId(BigInteger idRegistro) {
        try {
            ReservaModuloLaboratorio registro = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerReservaModuloLaboratorioPorId: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void actualizarGuiaLaboratorioReserva(ReservaSala reservaSala) {
        try {
            reservaSalaDAO.editarReservaSala(reservaSala);
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO actualizarGuiaLaboratorioReserva: " + e.toString(), e);
        }
    }

    @Override
    public TipoReserva obtenerTipoReservaPorId(BigInteger idRegistro) {
        try {
            TipoReserva registro = tipoReservaDAO.buscarTipoReservaPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO actualizarGuiaLaboratorioReserva: " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public List<ServiciosSala> listaServiciosSalaActivos() {
        try {
            List<ServiciosSala> lista = serviciosSalaDAO.consultarServiciosSalaActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO listaServiciosSalaActivos: " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaSala> obtenerReservasSalaPorParametros(SalaLaboratorio sala, Date fecha) {
        try {
            List<ReservaSala> lista = reservaSalaDAO.buscarReservaSalasSalaPorParametros(sala.getIdsalalaboratorio(), fecha);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerReservasSalaPorParametros: " + e.toString(), e);
            return null;
        }
    }

    public Integer obtenerNumeroReservaDia(Date fecha) {
        return reservaDAO.obtenerCantidadReservasDia(fecha);
    }

    public List<ReservaSala> obtenerReservasModuloSalas(Date fecha, BigInteger sala) {
        try {
            TipoReserva tipo = tipoReservaDAO.buscarTipoReservaPorID(new BigInteger("4"));
            return reservaSalaDAO.buscarReservaSalaParaReservaModulo(sala, fecha, tipo.getIdtiporeserva());
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerReservasModuloSalas: " + e.toString(), e);
            return null;
        }
    }

    public Reserva obtenerReservaPorNumero(String numero) {
        try {
            return reservaDAO.buscarReservaPorNumero(numero);
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerReservaPorNumero: " + e.toString(), e);
            return null;
        }
    }

    public ReservaSala obtenterReservaSalaPorIdReserva(BigInteger reserva) {
        try {
            return reservaSalaDAO.buscarReservaSalaPorIdReserva(reserva);
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenterReservaSalaPorIdReserva: " + e.toString(), e);
            return null;
        }
    }

    public ReservaModuloLaboratorio obtenterReservaModuloPorIdReserva(BigInteger reserva) {
        try {
            return reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorIdReserva(reserva);
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenterReservaModuloPorIdReserva: " + e.toString(), e);
            return null;
        }
    }

    public void almacenarReservaEquipo(ReservaEquipoElemento reserva) {
        try {
            reservaEquipoElementoDAO.crearReservaEquipoElemento(reserva);
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO almacenarReservaEquipo: " + e.toString(), e);
        }
    }

    public List<SalaLaboratorio> buscarBodegaPorLaboratorioEdificio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarBodegasPorLaboratorio(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO buscarBodegaPorLaboratorioEdificio: " + e.toString(), e);
            return null;
        }
    }

    public List<EquipoElemento> obtenerEquiposBodega(BigInteger bodega) {
        try {
            return equipoElementoDAO.consultarEquiposElementosBodegaPublicos(bodega);
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerEquiposBodega: " + e.toString(), e);
            return null;
        }
    }

    public void actualizarInformacionReserva(Reserva reserva, int operacion) {
        try {
            if (1 == operacion) {
                EstadoReserva estado = estadoReservaDAO.buscarEstadoReservaPorID(new BigInteger("5"));
                reserva.setEstadoreserva(estado);
            } else if (operacion == 2) {
                EstadoReserva estado = estadoReservaDAO.buscarEstadoReservaPorID(new BigInteger("4"));
                reserva.setEstadoreserva(estado);
            }
            reservaDAO.editarReserva(reserva);
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO actualizarInformacionReserva: " + e.toString(), e);
        }
    }

    public EstadoReserva obtenerEstadoCancelacionReserva() {
        try {
            return estadoReservaDAO.buscarEstadoReservaPorID(new BigInteger("3"));
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerEstadoCancelacionReserva: " + e.toString(), e);
            return null;
        }
    }

    public void cambiarEstadoReserva(Reserva reserva) {
        try {
            EstadoReserva cancelada = obtenerEstadoCancelacionReserva();
            reserva.setEstadoreserva(cancelada);
            actualizarInformacionReserva(reserva, 0);
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO administrarReservasBO: " + e.toString(), e);
        }
    }

    public List<SalaLaboratorioxServicios> obtenerServiciosPorSala(BigInteger servicio) {
        try {
            List<SalaLaboratorioxServicios> servicios = salaLaboratorioxServiciosDAO.consultarSalaLaboratorioxServiciosPorSala(servicio);
            return servicios;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO obtenerServiciosPorSala: " + e.toString(), e);
            return null;
        }
    }

    public ReservaSala consultarReservaSalaPorCodigo(String codigo) {
        try {
            ReservaSala reserva = reservaSalaDAO.buscarReservaSalaPorCodigoReserva(codigo);
            return reserva;
        } catch (Exception e) {
            logger.error("Error AdministrarReservasBO consultarReservaSalaPorCodigo: " + e.toString(), e);
            return null;
        }
    }
}
