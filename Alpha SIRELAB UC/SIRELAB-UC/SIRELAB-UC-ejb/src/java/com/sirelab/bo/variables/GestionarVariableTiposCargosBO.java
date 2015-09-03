/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposCargosBOInterface;
import com.sirelab.dao.interfacedao.TipoCargoDAOInterface;
import com.sirelab.entidades.TipoCargo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarVariableTiposCargosBO implements GestionarVariableTiposCargosBOInterface {

    @EJB
    TipoCargoDAOInterface tipoCargoDAO;

    @Override
    public void crearTipoCargo(TipoCargo tipoCargo) {
        try {
            tipoCargoDAO.crearTipoCargo(tipoCargo);;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposCargosBO crearTipoCargo : " + e.toString());
        }
    }

    @Override
    public void editarTipoCargo(TipoCargo tipoCargo) {
        try {
            tipoCargoDAO.editarTipoCargo(tipoCargo);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposCargosBO editarTipoCargo : " + e.toString());
        }
    }

    @Override
    public void borrarTipoCargo(TipoCargo tipoCargo) {
        try {
            tipoCargoDAO.eliminarTipoCargo(tipoCargo);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposCargosBO borrarTipoCargo : " + e.toString());
        }
    }

    @Override
    public TipoCargo consultarTipoCargoPorID(BigInteger idRegistro) {
        try {
            TipoCargo registro = tipoCargoDAO.buscarTipoCargoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposCargosBO borrarTipoCargo : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<TipoCargo> consultarTiposCargosRegistrados() {
        try {
            List<TipoCargo> lista = tipoCargoDAO.consultarTiposCargos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposCargosBO borrarTipoCargo : " + e.toString());
            return null;
        }
    }
}
