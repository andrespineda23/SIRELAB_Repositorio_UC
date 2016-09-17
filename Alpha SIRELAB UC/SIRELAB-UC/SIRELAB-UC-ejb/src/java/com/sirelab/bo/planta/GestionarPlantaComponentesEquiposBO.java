/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaComponentesEquiposBOInterface;
import com.sirelab.dao.interfacedao.ComponenteEquipoDAOInterface;
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.TipoComponenteDAOInterface;
import com.sirelab.dao.interfacedao.TipoEventoDAOInterface;
import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.TipoComponente;
import com.sirelab.entidades.TipoEvento;
import java.math.BigInteger;
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
    TipoEventoDAOInterface tipoEventoDAO;

    @Override
    public List<TipoComponente> consultarTiposComponentesRegistrados() {
        try {
            List<TipoComponente> lista = tipoComponenteDAO.consultarTiposComponentes();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarTiposComponentesRegistrados : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EquipoElemento consultarEquipoElementoPorID(BigInteger idRegistro) {
        try {
            EquipoElemento registro = equipoElementoDAO.buscarEquipoElementoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarEquipoElementoPorID : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public ComponenteEquipo consultarComponenteEquipoPorID(BigInteger idRegistro) {
        try {
            ComponenteEquipo registro = componenteEquipoDAO.buscarComponenteEquipoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarComponenteEquipoPorID : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<ComponenteEquipo> consultarComponentesEquipoPorIDEquipo(BigInteger idRegistro) {
        try {
            List<ComponenteEquipo> lista = componenteEquipoDAO.consultarComponentesEquiposPorEquipo(idRegistro);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarComponentesEquipoPorIDEquipo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public ComponenteEquipo consultarComponentePorCodigoYEquipo(String codigo, BigInteger equipo) {
        try {
            ComponenteEquipo registro = componenteEquipoDAO.buscarComponenteEquipoPorCodigoYEquipo(codigo, equipo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO consultarComponentePorCodigoYEquipo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void crearComponenteEquipo(ComponenteEquipo componenteEquipo, String usuario) {
        try {
            componenteEquipoDAO.crearComponenteEquipo(componenteEquipo);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO crearComponenteEquipo : " + e.toString(), e);
        }
    }

    @Override
    public void editarComponenteEquipo(ComponenteEquipo componenteEquipo, String usuario) {
        try {
            componenteEquipoDAO.editarComponenteEquipo(componenteEquipo);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO editarComponenteEquipo : " + e.toString(), e);
        }
    }

    @Override
    public void eliminarComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            componenteEquipoDAO.eliminarComponenteEquipo(componenteEquipo);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO eliminarComponenteEquipo : " + e.toString(), e);
        }
    }

    @Override
    public boolean eliminarComponente(ComponenteEquipo componenteEquipo) {
        try {
            componenteEquipoDAO.eliminarComponenteEquipo(componenteEquipo);
            return true;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaComponentesEquiposBO eliminarComponente : " + e.toString(), e);
            return false;
        }
    }
}
