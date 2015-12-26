/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.ServiciosSala;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AndresPineda
 */
public interface ServiciosSalaDAOInterface {
    public void crearServiciosSala(ServiciosSala serviciossala);
    public void editarServiciosSala(ServiciosSala serviciossala);
    public void eliminarServiciosSala(ServiciosSala serviciossala);
    public List<ServiciosSala> consultarServiciosSala();
    public List<ServiciosSala> consultarServiciosSalaActivos();
    public ServiciosSala buscarServiciosSalaPorID(BigInteger idRegistro);
    public ServiciosSala buscarServiciosSalaPorCodigo(String codigo);
    public List<ServiciosSala> buscarServiciosSalaPorFiltrado(Map<String, String> filters);
}
