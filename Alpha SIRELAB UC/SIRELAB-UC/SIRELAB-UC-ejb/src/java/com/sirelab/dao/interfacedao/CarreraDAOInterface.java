package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.Carrera;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface CarreraDAOInterface {

    public Carrera buscarCarreraPorCodigo(String codigo);

    public void crearCarrera(Carrera carrera);

    public void editarCarrera(Carrera carrera);

    public void eliminarCarrera(Carrera carrera);

    public List<Carrera> consultarCarreras();

    public Carrera buscarCarreraPorID(BigInteger idRegistro);

    public Carrera buscarCarreraPorCodigoYDepartamento(String codigo, BigInteger departamento);

    public List<Carrera> consultarCarrerasPorDepartamento(BigInteger idDepartamento);

    public List<Carrera> buscarCarrerasPorFiltrado(Map<String, String> filters);

    public List<Carrera> consultarCarrerasActivos();

    public List<Carrera> consultarCarrerasActivosPorDepartamento(BigInteger idDepartamento);

}
