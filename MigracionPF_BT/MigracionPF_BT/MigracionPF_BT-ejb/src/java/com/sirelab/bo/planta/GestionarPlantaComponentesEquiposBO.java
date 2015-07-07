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
import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.TipoComponente;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarPlantaComponentesEquiposBO implements GestionarPlantaComponentesEquiposBOInterface {

    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;
    @EJB
    ComponenteEquipoDAOInterface componenteEquipoDAO;
    @EJB
    TipoComponenteDAOInterface tipoComponenteDAO;

    @Override
    public List<TipoComponente> consultarTiposComponentesRegistrados() {
        try {
            List<TipoComponente> lista = tipoComponenteDAO.consultarTiposComponentes();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaComponentesEquiposBO consultarTiposComponentesRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public EquipoElemento consultarEquipoElementoPorID(BigInteger idRegistro) {
        try {
            EquipoElemento registro = equipoElementoDAO.buscarEquipoElementoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaComponentesEquiposBO consultarEquipoElementoPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public ComponenteEquipo consultarComponenteEquipoPorID(BigInteger idRegistro) {
        try {
            ComponenteEquipo registro = componenteEquipoDAO.buscarComponenteEquipoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaComponentesEquiposBO consultarComponenteEquipoPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ComponenteEquipo> consultarComponentesEquipoPorIDEquipo(BigInteger idRegistro) {
        try {
            List<ComponenteEquipo> lista = componenteEquipoDAO.consultarComponentesEquiposPorEquipo(idRegistro);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaComponentesEquiposBO consultarComponentesEquipoPorIDEquipo : " + e.toString());
            return null;
        }
    }

    @Override
    public ComponenteEquipo consultarComponentePorCodigoYEquipo(String codigo, BigInteger equipo) {
        try {
            ComponenteEquipo registro = componenteEquipoDAO.buscarComponenteEquipoPorCodigoYEquipo(codigo, equipo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaComponentesEquiposBO consultarComponentePorCodigoYEquipo : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            componenteEquipoDAO.crearComponenteEquipo(componenteEquipo);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaComponentesEquiposBO crearComponenteEquipo : " + e.toString());
        }
    }

    @Override
    public void editarComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            componenteEquipoDAO.editarComponenteEquipo(componenteEquipo);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaComponentesEquiposBO editarComponenteEquipo : " + e.toString());
        }
    }

    @Override
    public void eliminarComponenteEquipo(ComponenteEquipo componenteEquipo) {
        try {
            componenteEquipoDAO.eliminarComponenteEquipo(componenteEquipo);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaComponentesEquiposBO eliminarComponenteEquipo : " + e.toString());
        }
    }
}
