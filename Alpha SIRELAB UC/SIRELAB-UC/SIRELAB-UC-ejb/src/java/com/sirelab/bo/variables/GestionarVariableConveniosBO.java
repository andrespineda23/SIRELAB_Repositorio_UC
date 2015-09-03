/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableConveniosBOInterface;
import com.sirelab.dao.interfacedao.ConvenioDAOInterface;
import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarVariableConveniosBO implements GestionarVariableConveniosBOInterface {

    @EJB
    ConvenioDAOInterface convenioDAO;
    @EJB
    ConvenioPorEntidadDAOInterface convenioPorEntidadDAO;

    @Override
    public void crearConvenio(Convenio convenio) {
        try {
            convenioDAO.crearConvenio(convenio);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableConveniosBO crearConvenio : " + e.toString());
        }
    }

    @Override
    public void editarConvenio(Convenio convenio) {
        try {
            convenioDAO.editarConvenio(convenio);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableConveniosBO editarConvenio : " + e.toString());
        }
    }

    @Override
    public void borrarConvenio(Convenio convenio) {
        try {
            convenioDAO.eliminarConvenio(convenio);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableConveniosBO borrarConvenio : " + e.toString());
        }
    }

    @Override
    public Convenio consultarConvenioPorID(BigInteger idRegistro) {
        try {
            Convenio registro = convenioDAO.buscarConvenioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableConveniosBO consultarConvenioPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Convenio> consultarConveniosRegistrados() {
        try {
            List<Convenio> lista = convenioDAO.consultarConvenios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableConveniosBO consultarConveniosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarCambioEstadoConvenio(BigInteger convenio) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorConvenio(convenio);
            if (null == lista) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error GestionarVariableConveniosBO validarCambioEstadoConvenio : " + e.toString());
            return null;
        }
    }
}
