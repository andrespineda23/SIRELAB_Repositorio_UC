/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.EncargadoPorEdificio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface EncargadoPorEdificioDAOInterface {

    public void crearEncargadoPorEdificio(EncargadoPorEdificio encargado);

    public void editarEncargadoPorEdificio(EncargadoPorEdificio encargado);

    public void eliminarEncargadoPorEdificio(EncargadoPorEdificio encargado);

    public List<EncargadoPorEdificio> consultarEncargadosPorEdificio();

    public EncargadoPorEdificio buscarEncargadoPorEdificioPorID(BigInteger idRegistro);

    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorIDEdificio(BigInteger idEdificio);

    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioActivosPorIDSede(BigInteger idEdificio);

    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorFiltrado(Map<String, String> filters);

    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorIDAdministrador(BigInteger idAdministrador);
    
}
