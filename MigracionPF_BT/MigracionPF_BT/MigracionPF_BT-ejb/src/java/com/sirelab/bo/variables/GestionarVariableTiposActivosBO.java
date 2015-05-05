/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposActivosBOInterface;
import com.sirelab.dao.interfacedao.TipoActivoDAOInterface;
import com.sirelab.entidades.TipoActivo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarVariableTiposActivosBO implements GestionarVariableTiposActivosBOInterface {

    @EJB
    TipoActivoDAOInterface tipoActivoDAO;

    @Override
    public void crearTipoActivo(TipoActivo tipoActivo) {
        try {
            tipoActivoDAO.crearTipoActivo(tipoActivo);;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposActivosBO crearTipoActivo : " + e.toString());
        }
    }

    @Override
    public void editarTipoActivo(TipoActivo tipoActivo) {
        try {
            tipoActivoDAO.editarTipoActivo(tipoActivo);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposActivosBO editarTipoActivo : " + e.toString());
        }
    }

    @Override
    public void borrarTipoActivo(TipoActivo tipoActivo) {
        try {
            tipoActivoDAO.eliminarTipoActivo(tipoActivo);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposActivosBO borrarTipoActivo : " + e.toString());
        }
    }

    @Override
    public TipoActivo consultarTipoActivoPorID(BigInteger idRegistro) {
        try {
            TipoActivo registro = tipoActivoDAO.buscarTipoActivoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposActivosBO borrarTipoActivo : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TipoActivo> consultarTiposActivosRegistrados() {
        try {
            List<TipoActivo> lista = tipoActivoDAO.consultarTiposActivos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposActivosBO borrarTipoActivo : " + e.toString());
            return null;
        }
    }
}
