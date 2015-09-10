/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.MovimientoInsumoAEquipo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface MovimientoInsumoAEquipoDAOInterface {

    public void crearMovimientoInsumoAEquipo(MovimientoInsumoAEquipo movimiento);

    public void editarMovimientoInsumoAEquipo(MovimientoInsumoAEquipo movimiento);

    public void eliminarMovimientoInsumoAEquipo(MovimientoInsumoAEquipo movimiento);

    public List<MovimientoInsumoAEquipo> consultarMovimientoInsumoAEquipos();

    public MovimientoInsumoAEquipo buscarMovimientoInsumoAEquipoPorID(BigInteger idRegistro);

    public List<MovimientoInsumoAEquipo> buscarMovimientoInsumoAEquipoPorIdMovimiento(BigInteger idMovimiento);
}
