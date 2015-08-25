/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.ConvenioPorEntidad;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface ConvenioPorEntidadDAOInterface {

    public void crearConvenioPorEntidad(ConvenioPorEntidad convenio);

    public void editarConvenioPorEntidad(ConvenioPorEntidad convenio);

    public void eliminarConvenioPorEntidad(ConvenioPorEntidad convenio);

    public List<ConvenioPorEntidad> consultarConveniosPorEntidad();

    public ConvenioPorEntidad buscarConvenioPorEntidadPorID(BigInteger idRegistro);

    public List<ConvenioPorEntidad> consultarConveniosPorEntidadPorEntidad(BigInteger entidad);

    public List<ConvenioPorEntidad> consultarConveniosPorEntidadPorConvenio(BigInteger convenio);

    public ConvenioPorEntidad buscarConvenioPorEntidadPorParametros(BigInteger entidad, BigInteger convenio);
}
