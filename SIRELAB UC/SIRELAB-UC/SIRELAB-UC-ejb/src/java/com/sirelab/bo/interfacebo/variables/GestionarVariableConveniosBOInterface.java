/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.Convenio;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarVariableConveniosBOInterface {

    public void crearConvenio(Convenio convenio);

    public void editarConvenio(Convenio convenio);

    public void borrarConvenio(Convenio convenio);

    public Convenio consultarConvenioPorID(BigInteger idRegistro);

    public List<Convenio> consultarConveniosRegistrados();

    public Boolean validarCambioEstadoConvenio(BigInteger convenio);
}
