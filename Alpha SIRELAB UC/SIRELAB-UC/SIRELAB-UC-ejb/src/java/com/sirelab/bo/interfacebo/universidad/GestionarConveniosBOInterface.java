/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.universidad;

import com.sirelab.entidades.Convenio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarConveniosBOInterface {

    public List<Convenio> consultarConveniosPorParametro(Map<String, String> filtros);

    public void crearConvenio(Convenio convenio);

    public void editarConvenio(Convenio convenio);

    public void borrarConvenio(Convenio convenio);

    public Convenio consultarConvenioPorID(BigInteger idRegistro);

    public List<Convenio> consultarConveniosRegistrados();

    public Boolean validarCambioEstadoConvenio(BigInteger convenio);
}
