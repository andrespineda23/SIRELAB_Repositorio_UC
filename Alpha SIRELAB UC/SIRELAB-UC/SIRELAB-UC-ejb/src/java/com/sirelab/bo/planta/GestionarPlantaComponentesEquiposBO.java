/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.planta;

import com.sirelab.bo.cargue.AdministrarCargueArchivoEstudianteBO;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaComponentesEquiposBOInterface;
import com.sirelab.dao.interfacedao.ComponenteEquipoDAOInterface;
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.HojaVidaEquipoDAOInterface;
import com.sirelab.dao.interfacedao.TipoComponenteDAOInterface;
import com.sirelab.dao.interfacedao.TipoEventoDAOInterface;
import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.TipoComponente;
import com.sirelab.entidades.TipoEvento;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarPlantaComponentesEquiposBO implements GestionarPlantaComponentesEquiposBOInterface {
    
    static Logger logger = Logger.getLogger(GestionarPlantaComponentesEquiposBO.class);

    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;
    @EJB
    ComponenteEquipoDAOInterface componenteEquipoDAO;
    @EJB
    TipoComponenteDAOInterface tipoComponenteDAO;
    @EJB
    HojaVidaEquipoDAOInterface hojaVidaEquipoDAO;
    @EJB
    TipoEventoDAOInterface tipoEventoDAO;

    @Override
    public List<TipoComponente> consultarTiposComponentesRegistrados() {
        try {
            List<TipoComponente> lista = tipoComponenteDAO.consultarTiposComponentes();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarTiposComponentesRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public EquipoElemento consultarEquipoElementoPorID(BigInteger idRegistro) {
        try {
            EquipoElemento registro = equipoElementoDAO.buscarEquipoElementoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarEquipoElementoPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public ComponenteEquipo consultarComponenteEquipoPorID(BigInteger idRegistro) {
        try {
            ComponenteEquipo registro = componenteEquipoDAO.buscarComponenteEquipoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarComponenteEquipoPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ComponenteEquipo> consultarComponentesEquipoPorIDEquipo(BigInteger idRegistro) {
        try {
            List<ComponenteEquipo> lista = componenteEquipoDAO.consultarComponentesEquiposPorEquipo(idRegistro);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarComponentesEquipoPorIDEquipo : " + e.toString());
            return null;
        }
    }

    @Override
    public ComponenteEquipo consultarComponentePorCodigoYEquipo(String codigo, BigInteger equipo) {
        try {
            ComponenteEquipo registro = componenteEquipoDAO.buscarComponenteEquipoPorCodigoYEquipo(codigo, equipo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarComponentePorCodigoYEquipo : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            componenteEquipoDAO.crearComponenteEquipo(componenteEquipo);
            HojaVidaEquipo hojaVida = new HojaVidaEquipo();
            hojaVida.setFechaevento(new Date());
            hojaVida.setEquipoelemento(componenteEquipo.getEquipoelemento());
            hojaVida.setFecharegistro(new Date());
            hojaVida.setTipoevento(obtenerTipoEventoPorId(new BigInteger("7")));
            hojaVida.setDetalleevento("CREACIÓN DE UN NUEVO COMPONENTE ("
                    + componenteEquipo.getCodigocomponete() + " - "
                    + componenteEquipo.getDescripcioncomponente() + " - "
                    + componenteEquipo.getTipocomponente() + ") PARA EL EQUIPO " + componenteEquipo.getEquipoelemento().getInventarioequipo());
            hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVida);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO crearComponenteEquipo : " + e.toString());
        }
    }

    private TipoEvento obtenerTipoEventoPorId(BigInteger secuencia) {
        return tipoEventoDAO.buscarTipoEventoPorID(secuencia);
    }

    @Override
    public void editarComponenteEquipo(ComponenteEquipo componenteEquipo, boolean cambioEquipo, EquipoElemento equipoCambio) {
        try {
            componenteEquipoDAO.editarComponenteEquipo(componenteEquipo);
            if (cambioEquipo == false) {
                HojaVidaEquipo hojaVida = new HojaVidaEquipo();
                hojaVida.setFechaevento(new Date());
                hojaVida.setEquipoelemento(componenteEquipo.getEquipoelemento());
                hojaVida.setFecharegistro(new Date());
                hojaVida.setTipoevento(obtenerTipoEventoPorId(new BigInteger("8")));
                hojaVida.setDetalleevento("MODIFICACIÓN INFORMACIÓN COMPONENTE ("
                        + componenteEquipo.getCodigocomponete() + " - "
                        + componenteEquipo.getDescripcioncomponente() + " - "
                        + componenteEquipo.getTipocomponente() + " - " + componenteEquipo.getStrEstado() + ") PARA EL EQUIPO " + componenteEquipo.getEquipoelemento().getInventarioequipo());
                hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVida);
            } else {
                HojaVidaEquipo hojaVida = new HojaVidaEquipo();
                hojaVida.setFechaevento(new Date());
                hojaVida.setEquipoelemento(componenteEquipo.getEquipoelemento());
                hojaVida.setFecharegistro(new Date());
                hojaVida.setTipoevento(obtenerTipoEventoPorId(new BigInteger("7")));
                hojaVida.setDetalleevento("CREACIÓN DE UN NUEVO COMPONENTE ("
                        + componenteEquipo.getCodigocomponete() + " - "
                        + componenteEquipo.getDescripcioncomponente() + " - "
                        + componenteEquipo.getTipocomponente() + " - " + componenteEquipo.getStrEstado() + ") PARA EL EQUIPO " + componenteEquipo.getEquipoelemento().getInventarioequipo());
                hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVida);
                HojaVidaEquipo hojaVidaCambio = new HojaVidaEquipo();
                hojaVidaCambio.setFechaevento(new Date());
                hojaVidaCambio.setEquipoelemento(componenteEquipo.getEquipoelemento());
                hojaVidaCambio.setFecharegistro(new Date());
                hojaVidaCambio.setTipoevento(obtenerTipoEventoPorId(new BigInteger("5")));
                hojaVidaCambio.setDetalleevento("ELIMINACIÓN DE UN COMPONENTE ("
                        + componenteEquipo.getCodigocomponete() + " - "
                        + componenteEquipo.getDescripcioncomponente() + " - "
                        + componenteEquipo.getTipocomponente() + " - " + componenteEquipo.getStrEstado() + ") PARA EL EQUIPO " + componenteEquipo.getEquipoelemento().getInventarioequipo());
                hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVidaCambio);
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO editarComponenteEquipo : " + e.toString());
        }
    }

    @Override
    public void eliminarComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            componenteEquipoDAO.eliminarComponenteEquipo(componenteEquipo);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO eliminarComponenteEquipo : " + e.toString());
        }
    }
}
