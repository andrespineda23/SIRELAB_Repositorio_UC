package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaModulosBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateful
public class GestionarPlantaModulosBO implements GestionarPlantaModulosBOInterface {

    static Logger logger = Logger.getLogger(GestionarPlantaModulosBO.class);

    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    ModuloLaboratorioDAOInterface moduloLaboratorioDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;
    @EJB
    EdificioDAOInterface edificioDAO;
    @EJB
    SedeDAOInterface sedeDAO;
    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;

    @Override
    public List<Departamento> consultarDepartamentosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarDepartamentosRegistrados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosActivosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentosActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarDepartamentosRegistrados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarLaboratoriosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosActivosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioActivosPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarLaboratoriosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO obtenerEncargadoLaboratorioPorID : " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public List<Laboratorio> consultarLaboratoriosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarLaboratoriosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public List<Sede> consultarSedesRegistradas() {
        try {
            List<Sede> lista = sedeDAO.consultarSedes();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarSedesRegistradas : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Edificio> consultarEdificiosPorIDSede(BigInteger sede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosPorIDSede(sede);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarEdificiosPorIDSede : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDEdificio(BigInteger edificio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorEdificio(edificio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarSalasLaboratorioPorIDEdificio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDDepartamento(BigInteger departamento) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarSalasLaboratorioPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorio(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarSalasLaboratorioPorIDLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ModuloLaboratorio> consultarModulosLaboratorioPorParametro(Map<String, String> filtros) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModulosLaboratoriosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarModulosLaboratorioPorParametro : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void crearNuevoModuloLaboratorio(ModuloLaboratorio moduloLaboratorio) {
        try {
            moduloLaboratorioDAO.crearModuloLaboratorio(moduloLaboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO crearNuevoModuloLaboratorio : " + e.toString(), e);
        }
    }

    @Override
    public void modificarInformacionModuloLaboratorio(ModuloLaboratorio moduloLaboratorio) {
        try {
            moduloLaboratorioDAO.editarModuloLaboratorio(moduloLaboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO modificarInformacionModuloLaboratorio : " + e.toString(), e);
        }
    }

    @Override
    public ModuloLaboratorio obtenerModuloLaboratorioPorIDModuloLaboratorio(BigInteger idModuloLaboratorio) {
        try {
            ModuloLaboratorio registro = moduloLaboratorioDAO.buscarModuloLaboratorioPorID(idModuloLaboratorio);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO obtenerModuloLaboratorioPorIDModuloLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public ModuloLaboratorio obtenerModuloLaboratorioPorCodigoYSala(String codigo, BigInteger sala) {
        try {
            ModuloLaboratorio registro = moduloLaboratorioDAO.buscarModuloLaboratorioPorCodigoYSala(codigo, sala);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO obtenerModuloLaboratorioPorCodigoYSala : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratoriosActivosPorIDLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioActivos(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO consultarSalasLaboratoriosActivosPorIDLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public SalaLaboratorio obtenerSalaLaboratorioPorID(BigInteger sala) {
        try {
            SalaLaboratorio registro = salaLaboratorioDAO.buscarSalaLaboratorioPorID(sala);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO obtenerSalaLaboratorioPorID : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public int validarCantidadModulosSala(BigInteger sala) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModuloLaboratorioPorIDSalaLaboratorio(sala);
            if (null != lista) {
                int tam = lista.size();
                return tam;
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO validarCantidadModulosSala : " + e.toString(), e);
            return -1;
        }
    }

    @Override
    public List<SalaLaboratorio> obtenerSalasLaboratorioPorEdificio(BigInteger edificio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorEdificio(edificio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO obtenerSalasLaboratorioPorEdificio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Integer obtenerEquiposAsociados(BigInteger modulo) {
        try {
            Integer equipos = equipoElementoDAO.consultarEquiposElementosPorModulo(modulo).size();
            if (null == equipos) {
                return 0;
            } else {
                return equipos;
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO obtenerEquiposAsociados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public boolean eliminarModuloLaboratorio(ModuloLaboratorio modulo) {
        try {
            moduloLaboratorioDAO.eliminarModuloLaboratorio(modulo);
            return true;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaModulosBO eliminarModuloLaboratorio : " + e.toString(), e);
            return false;
        }
    }
}
