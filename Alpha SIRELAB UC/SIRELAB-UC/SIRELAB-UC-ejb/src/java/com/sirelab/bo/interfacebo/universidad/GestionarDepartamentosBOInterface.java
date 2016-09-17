/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.universidad;

import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Facultad;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface GestionarDepartamentosBOInterface {

    public List<Facultad> consultarFacultadesRegistradas();

    public List<Departamento> consultarDepartamentosPorParametro(Map<String, String> filtros);

    public void crearNuevaDepartamento(Departamento departamento);

    public void modificarInformacionDepartamento(Departamento departamento);

    public Departamento obtenerDepartamentoPorCodigo(String codigo);

    public Departamento obtenerDepartamentoPorIDDepartamento(BigInteger idDepartamento);

    public Boolean validarCambioEstadoDepartamento(BigInteger departamento);

    public List<Facultad> consultarFacultadesActivosRegistradas();

    public boolean eliminarDepartamento(Departamento departamento);

    public Integer obtenerCarrerasAsociadas(BigInteger departamento);
}
