/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.TipoComponente;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarVariableTiposComponentesBOInterface {

    public void crearTipoComponente(TipoComponente tipoComponente);

    public void editarTipoComponente(TipoComponente tipoComponente);

    public void borrarTipoComponente(TipoComponente tipoComponente);

    public TipoComponente consultarTipoComponentePorID(BigInteger idRegistro);

    public List<TipoComponente> consultarTiposComponentesRegistrados();
}
