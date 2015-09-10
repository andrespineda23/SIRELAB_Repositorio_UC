/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.TipoMovimiento;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface TipoMovimientoDAOInterface {

    public void crearTipoMovimiento(TipoMovimiento tipoMovimiento);

    public void editarTipoMovimiento(TipoMovimiento tipoMovimiento);

    public void eliminarTipoMovimiento(TipoMovimiento tipoMovimiento);

    public List<TipoMovimiento> consultarTiposMovimientos();

    public TipoMovimiento buscarTipoMovimientoPorID(BigInteger idRegistro);
}
