/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.EntidadExterna;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface EntidadExternaDAOInterface {

    public void crearEntidadExterna(EntidadExterna entidadexterna);

    public void editarEntidadExterna(EntidadExterna entidadexterna);

    public void eliminarEntidadExterna(EntidadExterna entidadexterna);

    public List<EntidadExterna> consultarEntidadesExternas();

    public List<EntidadExterna> consultarEntidadesExternasActivas();

    public EntidadExterna buscarEntidadExternaPorID(BigInteger idRegistro);

    public EntidadExterna buscarEntidadExternaPorCorreoNumDocumento(String correo, String documento);

    public List<EntidadExterna> buscarEntidadesExternasPorFiltrado(Map<String, String> filters);

    public EntidadExterna buscarEntidadExternaPorIdentificacionEntidad(String identificacion);

    public EntidadExterna buscarEntidadExternaPorCorreo(String correo);
}
