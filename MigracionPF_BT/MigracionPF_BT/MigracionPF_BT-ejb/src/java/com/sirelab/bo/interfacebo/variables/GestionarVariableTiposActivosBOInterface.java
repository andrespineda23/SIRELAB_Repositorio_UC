/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.TipoActivo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariableTiposActivosBOInterface {

    public void crearTipoActivo(TipoActivo tipoActivo);

    public void editarTipoActivo(TipoActivo tipoActivo);

    public void borrarTipoActivo(TipoActivo tipoActivo);

    public TipoActivo consultarTipoActivoPorID(BigInteger idRegistro);

    public List<TipoActivo> consultarTiposActivosRegistrados();
}
