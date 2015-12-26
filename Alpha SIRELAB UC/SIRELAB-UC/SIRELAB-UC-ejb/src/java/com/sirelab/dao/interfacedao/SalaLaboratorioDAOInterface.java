/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.SalaLaboratorio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface SalaLaboratorioDAOInterface {

    public List<SalaLaboratorio> buscarSalasLaboratoriosPorDepartamento(BigInteger departamento);

    public List<SalaLaboratorio> buscarSalasLaboratoriosPorLaboratorioActivos(BigInteger laboratorio);

    public SalaLaboratorio buscarSalaLaboratorioPorCodigoyEdificioyLaboratorio(String codigo, BigInteger edificio, BigInteger laboratorio);

    public void crearSalaLaboratorio(SalaLaboratorio salalaboratorio);

    public SalaLaboratorio obtenerUltimoSalaLaboratorioRegistrado();

    public void editarSalaLaboratorio(SalaLaboratorio salalaboratorio);

    public void eliminarSalaLaboratorio(SalaLaboratorio salalaboratorio);

    public List<SalaLaboratorio> consultarSalasLaboratorios();

    public SalaLaboratorio buscarSalaLaboratorioPorID(BigInteger idRegistro);

    public List<SalaLaboratorio> buscarSalasLaboratoriosPorFiltrado(Map<String, String> filters);

    public List<SalaLaboratorio> buscarSalasLaboratoriosPorEdificio(BigInteger edificio);

    public List<SalaLaboratorio> buscarSalasLaboratoriosPorLaboratorio(BigInteger laboratorio);
}
