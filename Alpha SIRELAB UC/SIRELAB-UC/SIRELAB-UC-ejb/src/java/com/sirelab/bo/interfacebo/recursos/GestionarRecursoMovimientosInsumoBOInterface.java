/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.recursos;

import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.MovimientoInsumo;
import com.sirelab.entidades.TipoMovimiento;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarRecursoMovimientosInsumoBOInterface {

    public List<MovimientoInsumo> consultarMovimientosInsumoPorIDInsumo(BigInteger insumo);

    public Insumo obtenerInsumoPorID(BigInteger insumo);

    public MovimientoInsumo obtenerMovimientoInsumoPorID(BigInteger movimientoInsumo);

    public void crearMovimientoInsumo(MovimientoInsumo movimientoInsumo);

    public void editarMovimientoInsumo(MovimientoInsumo movimientoInsumo);

    public List<TipoMovimiento> obtenerTipoMovimientoRegistrado();

    public List<EquipoElemento> obtenerEquipoElementoRegistrado();

    public void crearMovimientoInsumoAEquipo(MovimientoInsumo movimientoInsumo, EquipoElemento equipo);
}
