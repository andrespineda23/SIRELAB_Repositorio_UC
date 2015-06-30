/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.GestionarVariableTiposComponentesBOInterface;
import com.sirelab.dao.interfacedao.TipoComponenteDAOInterface;
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
public class GestionarVariableTiposComponentesBO implements GestionarVariableTiposComponentesBOInterface{

    @EJB
    TipoComponenteDAOInterface tipoComponenteDAO;

    @Override
    public void crearTipoComponente(TipoComponente tipoComponente) {
        try {
            tipoComponenteDAO.crearTipoComponente(tipoComponente);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposComponentesBO crearTipoComponente : " + e.toString());
        }
    }

    @Override
    public void editarTipoComponente(TipoComponente tipoComponente) {
        try {
            tipoComponenteDAO.editarTipoComponente(tipoComponente);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposComponentesBO editarTipoComponente : " + e.toString());
        }
    }

    @Override
    public void borrarTipoComponente(TipoComponente tipoComponente) {
        try {
            tipoComponenteDAO.eliminarTipoComponente(tipoComponente);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposComponentesBO borrarTipoComponente : " + e.toString());
        }
    }

    @Override
    public TipoComponente consultarTipoComponentePorID(BigInteger idRegistro) {
        try {
            TipoComponente registro = tipoComponenteDAO.buscarTipoComponentePorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposComponentesBO borrarTipoComponente : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TipoComponente> consultarTiposComponentesRegistrados() {
        try {
            List<TipoComponente> lista = tipoComponenteDAO.consultarTiposComponentes();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposComponentesBO borrarTipoComponente : " + e.toString());
            return null;
        }
    }
}
