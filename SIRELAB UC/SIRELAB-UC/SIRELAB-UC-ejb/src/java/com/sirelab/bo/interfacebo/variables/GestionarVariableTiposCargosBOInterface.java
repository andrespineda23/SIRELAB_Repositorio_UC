/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.TipoCargo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarVariableTiposCargosBOInterface {

    public void crearTipoCargo(TipoCargo tipoCargo);

    public void editarTipoCargo(TipoCargo tipoCargo);

    public void borrarTipoCargo(TipoCargo tipoCargo);

    public TipoCargo consultarTipoCargoPorID(BigInteger idRegistro);

    public List<TipoCargo> consultarTiposCargosRegistrados();
}
