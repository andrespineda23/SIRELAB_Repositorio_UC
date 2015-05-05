/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.Facultad;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface FacultadDAOInterface {

    public void crearFacultad(Facultad facultad);

    public void editarFacultad(Facultad facultad);

    public void eliminarFacultad(Facultad facultad);

    public List<Facultad> consultarFacultades();

    public Facultad buscarFacultadPorCodigo(String codigo);

    public Facultad buscarFacultadPorID(BigInteger idRegistro);

    public List<Facultad> buscarFacultadesPorFiltrado(Map<String, String> filters);

}
