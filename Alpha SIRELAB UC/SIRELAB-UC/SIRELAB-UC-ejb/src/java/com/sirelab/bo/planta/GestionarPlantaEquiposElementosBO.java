/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaEquiposElementosBOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.EstadoEquipoDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ProveedorDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.TipoActivoDAOInterface;
import com.sirelab.dao.interfacedao.TipoEventoDAOInterface;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.Proveedor;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.TipoActivo;
import com.sirelab.entidades.TipoEvento;
import java.math.BigInteger;
import java.util.Date;
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
public class GestionarPlantaEquiposElementosBO implements GestionarPlantaEquiposElementosBOInterface {

    static Logger logger = Logger.getLogger(GestionarPlantaEquiposElementosBO.class);

    @EJB
    ModuloLaboratorioDAOInterface moduloLaboratorioDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;
    @EJB
    TipoActivoDAOInterface tipoActivoDAO;
    @EJB
    EstadoEquipoDAOInterface estadoEquipoDAO;
    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;
    @EJB
    ProveedorDAOInterface proveedorDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    @EJB
    TipoEventoDAOInterface tipoEventoDAO;

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO obtenerEncargadoLaboratorioPorID : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarLaboratoriosRegistrados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosActivosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratoriosActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarLaboratoriosActivosRegistrados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<EstadoEquipo> consultarEstadosEquiposRegistrados() {
        try {
            List<EstadoEquipo> lista = estadoEquipoDAO.consultarEstadosEquipos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarEstadosEquiposRegistrados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<TipoActivo> consultarTiposActivosRegistrador() {
        try {
            List<TipoActivo> lista = tipoActivoDAO.consultarTiposActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarTiposActivosRegistrador : " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public List<Proveedor> consultarProveedoresRegistrados() {
        try {
            List<Proveedor> lista = proveedorDAO.consultarProveedores();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarProveedoresRegistrados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorio(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarSalasLaboratorioPorLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioActivosIDLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioActivos(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarSalasLaboratorioActivosIDLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorio(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarSalasLaboratorioPorIDLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ModuloLaboratorio> consultarModulosLaboratorioPorIDSalaLaboratorio(BigInteger salaLaboratorio) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModuloLaboratorioPorIDSalaLaboratorio(salaLaboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarModulosLaboratorioPorIDSalaLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ModuloLaboratorio> consultarModulosLaboratorioActivosPorIDSalaLaboratorio(BigInteger salaLaboratorio) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModuloLaboratorioActivosPorIDSalaLaboratorio(salaLaboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarModulosLaboratorioActivosPorIDSalaLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<EquipoElemento> consultarEquiposElementosPorParametro(Map<String, String> filtros) {
        try {
            List<EquipoElemento> lista = equipoElementoDAO.buscarEquiposElementosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarEquiposElementosPorParametro : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void crearNuevoEquipoElemento(EquipoElemento equipoElemento, String usuario) {
        try {
            equipoElementoDAO.crearEquipoElemento(equipoElemento);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO crearNuevoEquipoElemento : " + e.toString(), e);
        }
    }

    private TipoEvento obtenerTipoEventoPorId(BigInteger secuencia) {
        return tipoEventoDAO.buscarTipoEventoPorID(secuencia);
    }

    @Override
    public void modificarInformacionEquipoElemento(EquipoElemento equipoElemento, String usuario, boolean cambioEstado) {
        try {
            equipoElementoDAO.editarEquipoElemento(equipoElemento);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO modificarInformacionEquipoElemento : " + e.toString(), e);
        }
    }

    @Override
    public EquipoElemento obtenerEquipoElementoPorIDEquipoElemento(BigInteger idEquipoElemento) {
        try {
            EquipoElemento registro = equipoElementoDAO.buscarEquipoElementoPorID(idEquipoElemento);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO obtenerEquipoElementoPorIDEquipoElemento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EquipoElemento obtenerEquipoElementoPorCodigoYModulo(String codigo, BigInteger modulo) {
        try {
            EquipoElemento registro = equipoElementoDAO.buscarEquipoElementoPorCodigoYModulo(codigo, modulo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO obtenerEquipoElementoPorCodigoYModulo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public ModuloLaboratorio obtenerModuloLaboratorioPorID(BigInteger modulo) {
        try {
            ModuloLaboratorio registro = moduloLaboratorioDAO.buscarModuloLaboratorioPorID(modulo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO obtenerModuloLaboratorioPorID : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDEdificio(BigInteger edificio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorEdificio(edificio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaElementosEquiposBO consultarSalasLaboratorioPorIDEdificio : " + e.toString(), e);
            return null;
        }
    }
}
