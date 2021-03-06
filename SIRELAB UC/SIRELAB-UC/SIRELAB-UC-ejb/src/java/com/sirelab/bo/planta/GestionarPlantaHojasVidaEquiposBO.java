/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.HojaVidaEquipoDAOInterface;
import com.sirelab.dao.interfacedao.TipoEventoDAOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.TipoEvento;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarPlantaHojasVidaEquiposBO implements GestionarPlantaHojasVidaEquiposBOInterface {

    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;
    @EJB
    HojaVidaEquipoDAOInterface hojaVidaEquipoDAO;
    @EJB
    TipoEventoDAOInterface tipoEventoDAO;

    @Override
    public List<TipoEvento> consultarTiposEventosRegistrados() {
        try {
            List<TipoEvento> lista = tipoEventoDAO.consultarTiposEventos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaHojasVidaEquiposBO consultarTiposEventosRegistrados: " + e.toString());
            return null;
        }
    }

    @Override
    public HojaVidaEquipo consultarHojaVidaEquipoPorID(BigInteger hojavida) {
        try {
            HojaVidaEquipo registro = hojaVidaEquipoDAO.buscarHojaVidaEquipoPorID(hojavida);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaHojasVidaEquiposBO consultarHojaVidaEquipoPorID: " + e.toString());
            return null;
        }
    }

    @Override
    public EquipoElemento consultarEquipoElementoPorID(BigInteger equipo) {
        try {
            EquipoElemento registro = equipoElementoDAO.buscarEquipoElementoPorID(equipo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaHojasVidaEquiposBO consultarEquipoElementoPorID: " + e.toString());
            return null;
        }
    }

    @Override
    public List<HojaVidaEquipo> consultarHojaVidaPorIDEquipo(BigInteger equipo) {
        try {
            List<HojaVidaEquipo> lista = hojaVidaEquipoDAO.consultarHojaVidaPorIDEquipo(equipo);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaHojasVidaEquiposBO consultarHojaVidaPorIDEquipo: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearHojaVidaEquipo(HojaVidaEquipo hojaVidaEquipo) {
        try {
            hojaVidaEquipoDAO.crearHojaVidaEquipo(hojaVidaEquipo);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaHojasVidaEquiposBO crearHojaVidaEquipo: " + e.toString());
        }
    }

    @Override
    public void editarHojaVidaEquipo(HojaVidaEquipo hojaVidaEquipo) {
        try {
            hojaVidaEquipoDAO.editarHojaVidaEquipo(hojaVidaEquipo);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaHojasVidaEquiposBO editarHojaVidaEquipo: " + e.toString());
        }
    }

}
