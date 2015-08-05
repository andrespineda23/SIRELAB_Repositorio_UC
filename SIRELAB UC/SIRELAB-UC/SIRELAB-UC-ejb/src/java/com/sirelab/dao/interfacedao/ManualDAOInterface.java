/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.Manual;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface ManualDAOInterface {

    public void crearManual(Manual manual);

    public void editarManual(Manual manual);

    public void eliminarManual(Manual manual);

    public List<Manual> consultarManuales();

    public Manual buscarManualPorID(BigInteger idRegistro);

    public Manual buscarManualPorUbicacion(String ubicacion);

    public List<Manual> buscarManualesPorFiltrado(Map<String, String> filters);

    public Manual obtenerUltimoManualRegistrado();
}
