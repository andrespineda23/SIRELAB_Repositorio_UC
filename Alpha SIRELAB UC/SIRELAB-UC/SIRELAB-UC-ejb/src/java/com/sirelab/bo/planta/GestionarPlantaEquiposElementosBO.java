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
import com.sirelab.dao.interfacedao.HojaVidaEquipoDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ProveedorDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.TipoActivoDAOInterface;
import com.sirelab.dao.interfacedao.TipoEventoDAOInterface;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.HojaVidaEquipo;
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

/**
 *
 * @author ANDRES PINEDA
 */
@Stateful
public class GestionarPlantaEquiposElementosBO implements GestionarPlantaEquiposElementosBOInterface {

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
    HojaVidaEquipoDAOInterface hojaVidaEquipoDAO;
    @EJB
    TipoEventoDAOInterface tipoEventoDAO;

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO obtenerEncargadoLaboratorioPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarLaboratoriosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosActivosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratoriosActivos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarLaboratoriosActivosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<EstadoEquipo> consultarEstadosEquiposRegistrados() {
        try {
            List<EstadoEquipo> lista = estadoEquipoDAO.consultarEstadosEquipos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarEstadosEquiposRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TipoActivo> consultarTiposActivosRegistrador() {
        try {
            List<TipoActivo> lista = tipoActivoDAO.consultarTiposActivos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarTiposActivosRegistrador : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Proveedor> consultarProveedoresRegistrados() {
        try {
            List<Proveedor> lista = proveedorDAO.consultarProveedores();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarProveedoresRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorio(laboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarSalasLaboratorioPorLaboratorio : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioActivosIDLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioActivos(laboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarSalasLaboratorioActivosIDLaboratorio : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorio(laboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarSalasLaboratorioPorIDLaboratorio : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ModuloLaboratorio> consultarModulosLaboratorioPorIDSalaLaboratorio(BigInteger salaLaboratorio) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModuloLaboratorioPorIDSalaLaboratorio(salaLaboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarModulosLaboratorioPorIDSalaLaboratorio : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ModuloLaboratorio> consultarModulosLaboratorioActivosPorIDSalaLaboratorio(BigInteger salaLaboratorio) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModuloLaboratorioActivosPorIDSalaLaboratorio(salaLaboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarModulosLaboratorioActivosPorIDSalaLaboratorio : " + e.toString());
            return null;
        }
    }

    @Override
    public List<EquipoElemento> consultarEquiposElementosPorParametro(Map<String, String> filtros) {
        try {
            List<EquipoElemento> lista = equipoElementoDAO.buscarEquiposElementosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarEquiposElementosPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevoEquipoElemento(EquipoElemento equipoElemento) {
        try {
            equipoElementoDAO.crearEquipoElemento(equipoElemento);
            EquipoElemento nuevo = equipoElementoDAO.obtenerUltimaEquipoElementoRegistrada();
            HojaVidaEquipo hojaVida = new HojaVidaEquipo();
            hojaVida.setFechaevento(new Date());
            hojaVida.setEquipoelemento(nuevo);
            hojaVida.setFecharegistro(new Date());
            hojaVida.setTipoevento(obtenerTipoEventoPorId(new BigInteger("1")));
            hojaVida.setDetalleevento("CREACIÓN DEL EQUIPO TECNOLOGICO ("
                    + nuevo.getInventarioequipo() + " - "
                    + nuevo.getMarcaequipo() + " - "
                    + nuevo.getModulolaboratorio().getSalalaboratorio().getDescripcionsala()
                    + " - " + nuevo.getEstadoequipo().getNombreestadoequipo() + ") PARA EL MODULO " + equipoElemento.getModulolaboratorio().getStrNombreEstado() + " Y SALA " + equipoElemento.getModulolaboratorio().getSalalaboratorio().getStrNombreEstado());
            hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVida);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO crearNuevoEquipoElemento : " + e.toString());
        }
    }

    private TipoEvento obtenerTipoEventoPorId(BigInteger secuencia) {
        return tipoEventoDAO.buscarTipoEventoPorID(secuencia);
    }

    @Override
    public void modificarInformacionEquipoElemento(EquipoElemento equipoElemento, boolean cambio, EquipoElemento equipoCambio) {
        try {
            equipoElementoDAO.editarEquipoElemento(equipoElemento);
            if (cambio == false) {
                HojaVidaEquipo hojaVida = new HojaVidaEquipo();
                hojaVida.setFechaevento(new Date());
                hojaVida.setEquipoelemento(equipoElemento);
                hojaVida.setFecharegistro(new Date());
                hojaVida.setTipoevento(obtenerTipoEventoPorId(new BigInteger("2")));
                hojaVida.setDetalleevento("MODIFICACION DEL EQUIPO TECNOLOGICO ("
                        + equipoElemento.getInventarioequipo() + " - "
                        + equipoElemento.getMarcaequipo() + " - "
                        + equipoElemento.getModulolaboratorio().getSalalaboratorio().getDescripcionsala()
                        + " - " + equipoElemento.getEstadoequipo().getNombreestadoequipo() + ") PARA EL MODULO " + equipoElemento.getModulolaboratorio().getStrNombreEstado() + " Y SALA " + equipoElemento.getModulolaboratorio().getSalalaboratorio().getStrNombreEstado());
                hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVida);
            } else {
                HojaVidaEquipo hojaVida = new HojaVidaEquipo();
                hojaVida.setFechaevento(new Date());
                hojaVida.setEquipoelemento(equipoElemento);
                hojaVida.setFecharegistro(new Date());
                hojaVida.setTipoevento(obtenerTipoEventoPorId(new BigInteger("1")));
                hojaVida.setDetalleevento("CREACIÓN DEL EQUIPO TECNOLOGICO ("
                        + equipoElemento.getInventarioequipo() + " - "
                        + equipoElemento.getMarcaequipo() + " - "
                        + equipoElemento.getModulolaboratorio().getSalalaboratorio().getDescripcionsala()
                        + " - " + equipoElemento.getEstadoequipo().getNombreestadoequipo() + ") PARA EL MODULO " + equipoElemento.getModulolaboratorio().getStrNombreEstado() + " Y SALA " + equipoElemento.getModulolaboratorio().getSalalaboratorio().getStrNombreEstado());
                HojaVidaEquipo hojaVidaCambio = new HojaVidaEquipo();
                hojaVidaCambio.setFechaevento(new Date());
                hojaVidaCambio.setEquipoelemento(equipoCambio);
                hojaVidaCambio.setFecharegistro(new Date());
                hojaVidaCambio.setTipoevento(obtenerTipoEventoPorId(new BigInteger("6")));
                hojaVida.setDetalleevento("ELIMINACION DEL EQUIPO TECNOLOGICO ("
                        + equipoCambio.getInventarioequipo() + " - "
                        + equipoCambio.getMarcaequipo() + " - "
                        + equipoCambio.getModulolaboratorio().getSalalaboratorio().getDescripcionsala()
                        + " - " + equipoCambio.getEstadoequipo().getNombreestadoequipo() + ") PARA EL MODULO " + equipoCambio.getModulolaboratorio().getStrNombreEstado() + " Y SALA " + equipoCambio.getModulolaboratorio().getSalalaboratorio().getStrNombreEstado());
                hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVida);
                hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVidaCambio);
            }
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO modificarInformacionEquipoElemento : " + e.toString());
        }
    }

    @Override
    public EquipoElemento obtenerEquipoElementoPorIDEquipoElemento(BigInteger idEquipoElemento) {
        try {
            EquipoElemento registro = equipoElementoDAO.buscarEquipoElementoPorID(idEquipoElemento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO obtenerEquipoElementoPorIDEquipoElemento : " + e.toString());
            return null;
        }
    }

    @Override
    public EquipoElemento obtenerEquipoElementoPorCodigoYModulo(String codigo, BigInteger modulo) {
        try {
            EquipoElemento registro = equipoElementoDAO.buscarEquipoElementoPorCodigoYModulo(codigo, modulo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO obtenerEquipoElementoPorCodigoYModulo : " + e.toString());
            return null;
        }
    }

    @Override
    public ModuloLaboratorio obtenerModuloLaboratorioPorID(BigInteger modulo) {
        try {
            ModuloLaboratorio registro = moduloLaboratorioDAO.buscarModuloLaboratorioPorID(modulo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO obtenerModuloLaboratorioPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDEdificio(BigInteger edificio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorEdificio(edificio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaElementosEquiposBO consultarSalasLaboratorioPorIDEdificio : " + e.toString());
            return null;
        }
    }
}
