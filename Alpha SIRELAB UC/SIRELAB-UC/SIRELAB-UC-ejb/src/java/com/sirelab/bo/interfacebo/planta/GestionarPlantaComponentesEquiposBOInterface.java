/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.planta;

import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.TipoComponente;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarPlantaComponentesEquiposBOInterface {

    public List<TipoComponente> consultarTiposComponentesRegistrados();

    public EquipoElemento consultarEquipoElementoPorID(BigInteger idRegistro);

    public List<ComponenteEquipo> consultarComponentesEquipoPorIDEquipo(BigInteger idRegistro);

    public ComponenteEquipo consultarComponentePorCodigoYEquipo(String codigo, BigInteger equipo);

    public void crearComponenteEquipo(ComponenteEquipo componenteEquipo, String usuario);

    public void editarComponenteEquipo(ComponenteEquipo componenteEquipo, String usuario);

    public void eliminarComponenteEquipo(ComponenteEquipo componenteEquipo);

    public ComponenteEquipo consultarComponenteEquipoPorID(BigInteger idRegistro);

    public boolean eliminarComponente(ComponenteEquipo componenteEquipo);
}
