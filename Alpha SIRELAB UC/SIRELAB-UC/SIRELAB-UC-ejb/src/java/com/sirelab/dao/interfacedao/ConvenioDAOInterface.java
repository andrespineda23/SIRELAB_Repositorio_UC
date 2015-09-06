/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.Convenio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface ConvenioDAOInterface {

    public void crearConvenio(Convenio convenio);

    public void editarConvenio(Convenio convenio);

    public void eliminarConvenio(Convenio convenio);

    public List<Convenio> consultarConvenios();

    public List<Convenio> buscarConvenioPorFiltrado(Map<String, String> filters);

    public Convenio buscarConvenioPorID(BigInteger idRegistro);

    public List<Convenio> consultarConveniosActivos();
}
