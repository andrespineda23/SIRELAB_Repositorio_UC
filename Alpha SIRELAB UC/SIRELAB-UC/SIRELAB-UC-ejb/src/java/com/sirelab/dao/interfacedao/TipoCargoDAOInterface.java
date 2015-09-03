/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.TipoCargo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface TipoCargoDAOInterface {

    public void crearTipoCargo(TipoCargo tipoCargo);

    public void editarTipoCargo(TipoCargo tipoCargo);

    public void eliminarTipoCargo(TipoCargo tipoCargo);

    public List<TipoCargo> consultarTiposCargos();

    public TipoCargo buscarTipoCargoPorID(BigInteger idRegistro);
}
