/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaTransferenciaElementosBOInterface;
import com.sirelab.dao.interfacedao.ComponenteEquipoDAOInterface;
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.HojaVidaEquipoDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.TipoEventoDAOInterface;
import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.TipoEvento;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarPlantaTransferenciaElementosBO implements GestionarPlantaTransferenciaElementosBOInterface {

    static Logger logger = Logger.getLogger(GestionarPlantaTransferenciaElementosBO.class);

    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;
    @EJB
    ModuloLaboratorioDAOInterface moduloLaboratorioDAO;
    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;
    @EJB
    ComponenteEquipoDAOInterface componenteEquipoDAO;
    @EJB
    HojaVidaEquipoDAOInterface hojaVidaEquipoDAO;
    @EJB
    TipoEventoDAOInterface tipoEventoDAO;

    //@Override
    public List<Laboratorio> obtenerLaboratoriosRegistrados() {
        try {
            return laboratorioDAO.consultarLaboratorios();
        } catch (Exception e) {
            logger.error("Error GestionarPlantaTransferenciaElementosBO obtenerLaboratoriosRegistrados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> obtenerSalasLaboratorioPorLaboratorio(BigInteger laboratorio) {
        try {
            return salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorio(laboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaTransferenciaElementosBO obtenerSalasLaboratorioPorLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ModuloLaboratorio> obtenerModulosLaboratorioPorSala(BigInteger sala) {
        try {
            return moduloLaboratorioDAO.buscarModuloLaboratorioPorIDSalaLaboratorio(sala);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaTransferenciaElementosBO obtenerModulosLaboratorioPorSala : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<EquipoElemento> obtenerEquiposElementosPorModulo(BigInteger modulo) {
        try {
            Map<String, String> mapa = new HashMap<String, String>();
            mapa.put("parametroModuloLaboratorio", modulo.toString());
            return equipoElementoDAO.buscarEquiposElementosPorFiltrado(mapa);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaTransferenciaElementosBO obtenerEquiposElementosPorModulo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ComponenteEquipo> obtenerComponentesPorEquipo(BigInteger equipo) {
        try {
            return componenteEquipoDAO.consultarComponentesEquiposPorEquipo(equipo);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaTransferenciaElementosBO obtenerComponentesPorEquipo : " + e.toString(), e);
            return null;
        }
    }

    private TipoEvento obtenerTipoEvento() {
        return tipoEventoDAO.buscarTipoEventoPorID(new BigInteger("4"));
    }

    @Override
    public void almacenarTransferenciaEquipo(EquipoElemento equipoTransferir, ModuloLaboratorio moduloNuevo, String usuario) {
        try {
            TipoEvento evento = obtenerTipoEvento();
            HojaVidaEquipo hoja1 = new HojaVidaEquipo();
            hoja1.setCosto("0");
            hoja1.setTipoevento(evento);
            hoja1.setDetalleevento("TRASLADO DEL EQUIPO (" + equipoTransferir.getInventarioequipo() + " / " + equipoTransferir.getNombreequipo() + ") CON MODULO (" + equipoTransferir.getModulolaboratorio().getCodigomodulo() + " / " + equipoTransferir.getModulolaboratorio().getDetallemodulo() + ") AL NUEVO MODULO (" + moduloNuevo.getCodigomodulo() + " /" + moduloNuevo.getDetallemodulo() + ")");
            hoja1.setEquipoelemento(equipoTransferir);
            hoja1.setFechafinevento(new Date());
            hoja1.setFecharegistro(new Date());
            hoja1.setFechaevento(new Date());
            hoja1.setUsuariomodificacion(usuario);
            hojaVidaEquipoDAO.crearHojaVidaEquipo(hoja1);
            equipoTransferir.setModulolaboratorio(moduloNuevo);
            equipoElementoDAO.editarEquipoElemento(equipoTransferir);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaTransferenciaElementosBO almacenarTransferenciaEquipo : " + e.toString(), e);
        }
    }

    @Override
    public void almacenarTransferenciaComponente(ComponenteEquipo componenteTransferir, EquipoElemento equipoNuevo, String usuario) {
        try {
            TipoEvento evento = obtenerTipoEvento();
            HojaVidaEquipo hoja1 = new HojaVidaEquipo();
            hoja1.setCosto("0");
            hoja1.setTipoevento(evento);
            hoja1.setDetalleevento("TRASLADO DEL COMPONENTE (" + componenteTransferir.getCodigocomponete() + " / " + componenteTransferir.getDescripcioncomponente() + ") CON EQUIPO (" + componenteTransferir.getEquipoelemento().getInventarioequipo() + " / " + componenteTransferir.getEquipoelemento().getNombreequipo() + ") AL NUEVO EQUIPO (" + equipoNuevo.getInventarioequipo() + " /" + equipoNuevo.getNombreequipo() + ")");
            hoja1.setEquipoelemento(componenteTransferir.getEquipoelemento());
            hoja1.setFechafinevento(new Date());
            hoja1.setFecharegistro(new Date());
            hoja1.setFechaevento(new Date());
            hoja1.setUsuariomodificacion(usuario);
            hojaVidaEquipoDAO.crearHojaVidaEquipo(hoja1);
            componenteTransferir.setEquipoelemento(equipoNuevo);
            HojaVidaEquipo hoja2 = new HojaVidaEquipo();
            hoja2.setCosto("0");
            hoja2.setTipoevento(evento);
            hoja2.setDetalleevento("CREACIÓN DEL COMPONENTE (" + componenteTransferir.getCodigocomponete() + " / " + componenteTransferir.getDescripcioncomponente() + ") PARA EL EQUIPO (" + componenteTransferir.getEquipoelemento().getInventarioequipo() + " / " + componenteTransferir.getEquipoelemento().getNombreequipo() + ")");
            hoja2.setEquipoelemento(componenteTransferir.getEquipoelemento());
            hoja2.setFechafinevento(new Date());
            hoja2.setFecharegistro(new Date());
            hoja2.setFechaevento(new Date());
            hoja2.setUsuariomodificacion(usuario);
            componenteEquipoDAO.editarComponenteEquipo(componenteTransferir);
            hojaVidaEquipoDAO.crearHojaVidaEquipo(hoja2);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaTransferenciaElementosBO almacenarTransferenciaComponente : " + e.toString(), e);
        }
    }

}
