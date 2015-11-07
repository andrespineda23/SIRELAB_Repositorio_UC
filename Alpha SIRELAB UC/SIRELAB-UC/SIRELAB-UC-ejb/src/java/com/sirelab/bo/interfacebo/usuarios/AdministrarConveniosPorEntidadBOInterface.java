/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.usuarios;

import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministrarConveniosPorEntidadBOInterface {

    public List<Convenio> consultarConveniosRegistrados();

    public List<Convenio> consultarConveniosActivosRegistrados();

    public List<EntidadExterna> consultarEntidadesExternasActivasRegistradas();

    public List<EntidadExterna> consultarEntidadesExternasRegistradas();

    public List<ConvenioPorEntidad> buscarConveniosPorEntidadPorIdEntidad(BigInteger entidad);

    public List<ConvenioPorEntidad> buscarConveniosPorEntidadPorIdConvenio(BigInteger convenio);

    public void crearConvenioPorEntidad(ConvenioPorEntidad convenio);

    public void editarConvenioPorEntidad(ConvenioPorEntidad convenio);

    public ConvenioPorEntidad obtenerConvenioPorEntidadPorID(BigInteger idRegistro);

    public List<ConvenioPorEntidad> buscarConveniosPorEntidad();

    public ConvenioPorEntidad obtenerConvenioPorEntidadPorParametros(BigInteger entidad, BigInteger convenio);

    public List<ConvenioPorEntidad> obtenerConvenioPorEntidadPorIdEntidad(BigInteger idEntidad);
}
