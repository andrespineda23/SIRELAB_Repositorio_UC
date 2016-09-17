/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.universidad;

import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface GestionarCarrerasBOInterface {

    public List<Facultad> consultarFacultadesRegistradas();

    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad);

    public List<Carrera> consultarCarrerasPorParametro(Map<String, String> filtros);

    public void crearNuevaCarrera(Carrera carrera);

    public void modificarInformacionCarrera(Carrera carrera);

    public Carrera obtenerCarreraPorIDCarrera(BigInteger idCarrera);

    public Carrera obtenerCarreraPorCodigo(String codigo);

    public Boolean validarCambioEstadoCarrera(BigInteger carrera);

    public List<Facultad> consultarFacultadesActivosRegistradas();

    public List<Departamento> consultarDepartamentosActivosPorIDFacultad(BigInteger facultad);

    public boolean eliminarCarrera(Carrera carrera);

    public Integer obtenerPlanesEstudioAsociados(BigInteger carrera);
}
