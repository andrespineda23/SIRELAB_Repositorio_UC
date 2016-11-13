/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.reporte;

import com.sirelab.bo.interfacebo.reporte.AdministradorGeneradorReportesBOInterface;
import com.sirelab.dao.interfacedao.AdministradorEdificioDAOInterface;
import com.sirelab.dao.interfacedao.ComponenteEquipoDAOInterface;
import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.EstudianteDAOInterface;
import com.sirelab.dao.interfacedao.ModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.PeriodoAcademicoDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.ProveedorDAOInterface;
import com.sirelab.dao.interfacedao.ReservaModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ReservaSalaDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Proveedor;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.entidades.SalaLaboratorio;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
@Stateful
public class AdministradorGeneradorReportesBO implements AdministradorGeneradorReportesBOInterface {

    static Logger logger = Logger.getLogger(AdministradorGeneradorReportesBO.class);

    @EJB
    EstudianteDAOInterface estudianteDAO;
    @EJB
    DocenteDAOInterface docenteDAO;
    @EJB
    ModuloLaboratorioDAOInterface moduloLaboratorioDAO;
    @EJB
    ComponenteEquipoDAOInterface componenteEquipoDAO;
    @EJB
    ProveedorDAOInterface proveedorDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    @EJB
    AdministradorEdificioDAOInterface administradorEdificioDAO;
    @EJB
    PeriodoAcademicoDAOInterface periodoAcademicoDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    ReservaModuloLaboratorioDAOInterface reservaModuloLaboratorioDAO;
    @EJB
    ReservaSalaDAOInterface reservaSalaDAO;
    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorPeriodoAcademico(BigInteger periodo) {
        try {
            List<ReservaModuloLaboratorio> lista = null;
            if (null != periodo) {
                lista = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorIdPeriodoAcademico(periodo);
            } else {
                lista = reservaModuloLaboratorioDAO.consultarReservaModuloLaboratoriosModuloLaboratorio();
            }
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasModuloLaboratorioPorPeriodoAcademico : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaSala> obtenerReservasSalaPorPeriodoAcademico(BigInteger periodo) {
        try {
            List<ReservaSala> lista = null;
            if (null != periodo) {
                lista = reservaSalaDAO.consultarReservaSalasSala(periodo);
            } else {
                lista = reservaSalaDAO.consultarReservaSalasSala();
            }
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasSalaPorPeriodoAcademico : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorTipoUsuario(Integer usuario) {
        try {
            List<ReservaModuloLaboratorio> lista = null;
            PeriodoAcademico periodo = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            if (0 != usuario) {
                if (usuario == 1) {
                    lista = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorPeriodoYTipoUsuario(new BigInteger("3"), periodo.getIdperiodoacademico());
                } else {
                    lista = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorPeriodoYTipoUsuario(new BigInteger("2"), periodo.getIdperiodoacademico());
                }
            } else {
                lista = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorIdPeriodoAcademico(periodo.getIdperiodoacademico());
            }
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasModuloLaboratorioPorTipoUsuario : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaSala> obtenerReservasSalaPorTipoUsuario(Integer usuario) {
        try {
            List<ReservaSala> lista = null;
            PeriodoAcademico periodo = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            if (0 != usuario) {
                if (usuario == 1) {
                    lista = reservaSalaDAO.consultarReservaSalasPorTipoUsuarioYPeriodo(new BigInteger("3"), periodo.getIdperiodoacademico());
                } else {
                    lista = reservaSalaDAO.consultarReservaSalasPorTipoUsuarioYPeriodo(new BigInteger("2"), periodo.getIdperiodoacademico());
                }
            } else {
                lista = reservaSalaDAO.consultarReservaSalasSala(periodo.getIdperiodoacademico());
            }
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasSalaPorPeriodoAcademico : " + e.toString(), e);
            return null;
        }
    }

    public List<SalaLaboratorio> obtenerSalasLaboratorio() {
        try {
            List<SalaLaboratorio> sala = salaLaboratorioDAO.consultarSalasLaboratorios();
            return sala;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerSalasLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    public List<Persona> obtenerPersonasDelSistema() {
        try {
            List<Persona> persona = personaDAO.consultarPersonas();
            return persona;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerPersonasDelSistema : " + e.toString(), e);
            return null;
        }
    }

    public List<PeriodoAcademico> obtenerPeriodosAcademicos() {
        try {
            return periodoAcademicoDAO.consultarPeriodosAcademicos();
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerPeriodosAcademicos : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorSala(BigInteger sala) {
        try {
            PeriodoAcademico periodo = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            List<ReservaModuloLaboratorio> lista = null;
            lista = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorIdSalaYPeriodo(sala, periodo.getIdperiodoacademico());
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasModuloLaboratorioPorSala : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaSala> obtenerReservasSalaPorSala(BigInteger sala) {
        try {
            PeriodoAcademico periodo = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            List<ReservaSala> lista = null;
            lista = reservaSalaDAO.consultarReservaSalasPorIdSalaYPeriodo(sala, periodo.getIdperiodoacademico());
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasSalaPorSala : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorUsuario(String usuario) {
        try {
            PeriodoAcademico periodo = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            List<ReservaModuloLaboratorio> lista = null;
            if (null != usuario && !usuario.isEmpty()) {
                lista = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorUsuarioYPeriodo(usuario, periodo.getIdperiodoacademico());
            }
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasModuloLaboratorioPorUsuario : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaSala> obtenerReservasSalaPorUsuario(String usuario) {
        try {
            PeriodoAcademico periodo = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            List<ReservaSala> lista = null;
            if (null != usuario && !usuario.isEmpty()) {
                lista = reservaSalaDAO.consultarReservaSalasPorUsuarioYPeriodo(usuario, periodo.getIdperiodoacademico());
            }
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasSalaPorUsuario : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaModuloLaboratorio> obtenerReservasModuloLaboratorioPorFechas(String fechaInicio, String fechaFin) {
        try {
            List<ReservaModuloLaboratorio> lista = null;
            PeriodoAcademico periodo = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = formateador.parse(fechaInicio);
            } catch (ParseException e) {
                date1 = new Date();
                formateador.format(date1);
            }
            try {
                date2 = formateador.parse(fechaFin);
            } catch (ParseException e) {
                date2 = new Date();
                formateador.format(date2);
            }
            lista = reservaModuloLaboratorioDAO.buscarReservaModuloLaboratorioPorFechasYPeriodo(date1, date2, periodo.getIdperiodoacademico());
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasModuloLaboratorioPorFechas : " + e.toString(), e);
            return null;
        }
    }

    public List<ReservaSala> obtenerReservasSalaPorFechas(String fechaInicio, String fechaFin) {
        try {
            List<ReservaSala> lista = null;
            PeriodoAcademico periodo = periodoAcademicoDAO.buscarPeriodoAcademicoActual();
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = formateador.parse(fechaInicio);
            } catch (ParseException e) {
                date1 = new Date();
                formateador.format(date1);
            }
            try {
                date2 = formateador.parse(fechaFin);
            } catch (ParseException e) {
                date2 = new Date();
                formateador.format(date2);
            }
            lista = reservaSalaDAO.consultarReservaSalasPorFechasYPeriodo(date1, date2, periodo.getIdperiodoacademico());
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO obtenerReservasSalaPorFechas : " + e.toString(), e);
            return null;
        }
    }

    public List<EquipoElemento> consultarEquiposdeTrabajoRegistrados() {
        try {
            List<EquipoElemento> lista = equipoElementoDAO.consultarEquiposElementos();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO consultarEquiposdeTrabajoRegistrados : " + e.toString(), e);
            return null;
        }
    }

    public List<ModuloLaboratorio> consultarModuloLaboratorioRegistrados() {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.consultarModulosLaboratorios();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO consultarModuloLaboratorioRegistrados : " + e.toString(), e);
            return null;
        }
    }

    public List<ComponenteEquipo> consultarComponentesRegistrados() {
        try {
            List<ComponenteEquipo> lista = componenteEquipoDAO.consultarComponentesEquipos();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO consultarComponentesRegistrados : " + e.toString(), e);
            return null;
        }
    }

    public List<Proveedor> consultarProveedoresRegistrados() {
        try {
            List<Proveedor> lista = proveedorDAO.consultarProveedores();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministradorGeneradorReportesBO consultarProveedoresRegistrados : " + e.toString(), e);
            return null;
        }
    }

}
