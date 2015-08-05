/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.recursos;

import com.sirelab.entidades.Manual;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarRecursoManualesBOInterface {

    public List<Manual> consultarManualesPorParametro(Map<String, String> filtros);

    public Manual obtenerManualPorID(BigInteger manual);

    public Manual consultarManualPorUbicacion(String ubicacion);

    public void crearManual(Manual manual);

    public void editarManual(Manual manual);
}
