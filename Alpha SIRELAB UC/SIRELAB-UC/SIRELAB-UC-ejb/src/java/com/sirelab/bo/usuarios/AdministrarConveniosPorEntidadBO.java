/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarConveniosPorEntidadBOInterface;
import com.sirelab.dao.interfacedao.ConvenioDAOInterface;
import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarConveniosPorEntidadBO implements AdministrarConveniosPorEntidadBOInterface {

    @EJB
    ConvenioPorEntidadDAOInterface convenioPorEntidadDAO;
    @EJB
    ConvenioDAOInterface convenioDAOInterface;
    @EJB
    EntidadExternaDAOInterface entidadExternaDAO;

    @Override
    public List<Convenio> consultarConveniosRegistrados() {
        try {
            List<Convenio> lista = convenioDAOInterface.consultarConvenios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO consultarConveniosRegistrados: " + e.toString());
            return null;
        }
    }

    @Override
    public List<EntidadExterna> consultarEntidadesExternasRegistradas() {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.consultarEntidadesExternas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO consultarEntidadesExternasRegistradas: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> buscarConveniosPorEntidad() {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidad();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO buscarConveniosPorEntidad: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> buscarConveniosPorEntidadPorIdEntidad(BigInteger entidad) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorEntidad(entidad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO buscarConveniosPorEntidadPorIdEntidad: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> buscarConveniosPorEntidadPorIdConvenio(BigInteger convenio) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorConvenio(convenio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO buscarConveniosPorEntidadPorIdConvenio: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearConvenioPorEntidad(ConvenioPorEntidad convenio) {
        try {
            convenioPorEntidadDAO.crearConvenioPorEntidad(convenio);
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO crearConvenioPorEntidad: " + e.toString());
        }
    }

    @Override
    public void editarConvenioPorEntidad(ConvenioPorEntidad convenio) {
        try {
            convenioPorEntidadDAO.editarConvenioPorEntidad(convenio);
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO editarConvenioPorEntidad: " + e.toString());
        }
    }

    @Override
    public ConvenioPorEntidad obtenerConvenioPorEntidadPorID(BigInteger idRegistro) {
        try {
            ConvenioPorEntidad registro = convenioPorEntidadDAO.buscarConvenioPorEntidadPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO obtenerConvenioPorEntidadPorID: " + e.toString());
            return null;
        }
    }

    @Override
    public ConvenioPorEntidad obtenerConvenioPorEntidadPorParametros(BigInteger entidad, BigInteger convenio) {
        try {
            ConvenioPorEntidad registro = convenioPorEntidadDAO.buscarConvenioPorEntidadPorParametros(entidad, convenio);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarConveniosPorEntidadBO obtenerConvenioPorEntidadPorParametros: " + e.toString());
            return null;
        }
    }

}
