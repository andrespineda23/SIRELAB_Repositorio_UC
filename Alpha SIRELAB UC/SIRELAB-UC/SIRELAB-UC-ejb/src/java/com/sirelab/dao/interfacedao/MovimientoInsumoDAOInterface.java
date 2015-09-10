/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.MovimientoInsumo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface MovimientoInsumoDAOInterface {

    public void crearMovimientoInsumo(MovimientoInsumo movimientoInsumo);

    public void editarMovimientoInsumo(MovimientoInsumo movimientoInsumo);

    public void eliminarMovimientoInsumo(MovimientoInsumo movimientoInsumo);

    public List<MovimientoInsumo> consultarMovimientosInsumo();

    public List<MovimientoInsumo> consultarMovimientosInsumoPorInsumo(BigInteger insumo);

    public MovimientoInsumo buscarMovimientoInsumoPorID(BigInteger idRegistro);

    public MovimientoInsumo obtenerUltimaMovimientoInsumoRegistrada();
}
