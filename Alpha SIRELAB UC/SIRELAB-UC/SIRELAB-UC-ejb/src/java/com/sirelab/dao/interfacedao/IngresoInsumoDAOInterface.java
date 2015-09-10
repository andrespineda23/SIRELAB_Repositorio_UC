/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.IngresoInsumo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface IngresoInsumoDAOInterface {

    public void crearIngresoInsumo(IngresoInsumo insumo);

    public void editarIngresoInsumo(IngresoInsumo insumo);

    public void eliminarIngresoInsumo(IngresoInsumo insumo);

    public List<IngresoInsumo> consultarIngresoInsumos();

    public IngresoInsumo buscarIngresoInsumoPorID(BigInteger idRegistro);

    public List<IngresoInsumo> buscarIngresoInsumoPorIdInsumo(BigInteger idInsumo);
}
