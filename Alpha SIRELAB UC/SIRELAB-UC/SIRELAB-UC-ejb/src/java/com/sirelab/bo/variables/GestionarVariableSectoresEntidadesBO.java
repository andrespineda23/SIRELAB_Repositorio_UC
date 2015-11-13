/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableSectoresEntidadesBOInterface;
import com.sirelab.dao.interfacedao.SectorEntidadDAOInterface;
import com.sirelab.entidades.SectorEntidad;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarVariableSectoresEntidadesBO implements GestionarVariableSectoresEntidadesBOInterface {

    @EJB
    SectorEntidadDAOInterface sectorEntidadDAO;

    @Override
    public void crearSectorEntidad(SectorEntidad sectorEntidad) {
        try {
            sectorEntidadDAO.crearSectorEntidad(sectorEntidad);;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableSectoresEntidadesBO crearSectorEntidad : " + e.toString());
        }
    }

    @Override
    public void editarSectorEntidad(SectorEntidad sectorEntidad) {
        try {
            sectorEntidadDAO.editarSectorEntidad(sectorEntidad);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableSectoresEntidadesBO editarSectorEntidad : " + e.toString());
        }
    }

    @Override
    public void borrarSectorEntidad(SectorEntidad sectorEntidad) {
        try {
            sectorEntidadDAO.eliminarSectorEntidad(sectorEntidad);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableSectoresEntidadesBO borrarSectorEntidad : " + e.toString());
        }
    }

    @Override
    public SectorEntidad consultarSectorEntidadPorID(BigInteger idRegistro) {
        try {
            SectorEntidad registro = sectorEntidadDAO.buscarSectorEntidadPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableSectoresEntidadesBO borrarSectorEntidad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SectorEntidad> consultarSectoresEntidadesRegistrados() {
        try {
            List<SectorEntidad> lista = sectorEntidadDAO.consultarSectoresEntidad();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableSectoresEntidadesBO consultarSectoresEntidadesRegistrados : " + e.toString());
            return null;
        }
    }
}
