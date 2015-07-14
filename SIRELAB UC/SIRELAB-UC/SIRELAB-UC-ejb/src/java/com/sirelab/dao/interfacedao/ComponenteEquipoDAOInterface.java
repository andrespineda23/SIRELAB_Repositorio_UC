/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.ComponenteEquipo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface ComponenteEquipoDAOInterface {

    public void crearComponenteEquipo(ComponenteEquipo componenteEquipo);

    public void editarComponenteEquipo(ComponenteEquipo componenteEquipo);

    public void eliminarComponenteEquipo(ComponenteEquipo componenteEquipo);

    public List<ComponenteEquipo> consultarComponentesEquipos();

    public List<ComponenteEquipo> consultarComponentesEquiposPorEquipo(BigInteger equipo);

    public ComponenteEquipo buscarComponenteEquipoPorID(BigInteger idRegistro);

    public ComponenteEquipo buscarComponenteEquipoPorCodigoYEquipo(String codigo, BigInteger equipo);
}
