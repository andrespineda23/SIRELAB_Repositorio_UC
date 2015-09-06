/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.Departamento;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface DepartamentoDAOInterface {

    public void crearDepartamento(Departamento departamento);

    public Departamento buscarDepartamentoPorCodigo(String codigo);

    public void editarDepartamento(Departamento departamento);

    public void eliminarDepartamento(Departamento departamento);

    public List<Departamento> consultarDepartamentos();

    public Departamento buscarDepartamentoPorID(BigInteger idRegistro);

    public List<Departamento> buscarDepartamentosPorIDFacultad(BigInteger idFacultad);

    public List<Departamento> buscarDepartamentosPorFiltrado(Map<String, String> filters);

    public Departamento buscarDepartamentoPorNombre(String nombre);

    public List<Departamento> buscarDepartamentosActivosPorIDFacultad(BigInteger idFacultad);

    public List<Departamento> consultarDepartamentosActivos();
}
