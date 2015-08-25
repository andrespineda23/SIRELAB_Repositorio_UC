/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.Convenio;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface ConvenioDAOInterface {

    public void crearConvenio(Convenio convenio);

    public void editarConvenio(Convenio convenio);

    public void eliminarConvenio(Convenio convenio);

    public List<Convenio> consultarConvenios();

    public Convenio buscarConvenioPorID(BigInteger idRegistro);
}
