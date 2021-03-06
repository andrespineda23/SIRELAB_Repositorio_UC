/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.LaboratoriosPorAreas;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AndresPineda
 */
public interface LaboratoriosPorAreasDAOInterface {

    public void crearLaboratoriosPorAreas(LaboratoriosPorAreas laboratorioPorArea);

    public void editarLaboratoriosPorAreas(LaboratoriosPorAreas laboratorioPorArea);

    public void eliminarLaboratoriosPorAreas(LaboratoriosPorAreas laboratorioPorArea);

    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreassPorAreas();

    public LaboratoriosPorAreas buscarLaboratoriosPorAreasPorID(BigInteger idRegistro);

    public List<LaboratoriosPorAreas> buscarLaboratoriosPorAreasPorFiltrado(Map<String, String> filters);

    public LaboratoriosPorAreas buscarLaboratoriosPorAreasPorLabYArea(BigInteger laboratorio, BigInteger area);
}
