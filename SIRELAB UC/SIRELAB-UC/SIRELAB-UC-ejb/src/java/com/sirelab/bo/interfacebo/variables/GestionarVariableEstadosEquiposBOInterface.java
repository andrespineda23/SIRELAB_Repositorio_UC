/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.EstadoEquipo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariableEstadosEquiposBOInterface {

    public void crearEstadoEquipo(EstadoEquipo estadoEquipo);

    public void editarEstadoEquipo(EstadoEquipo estadoEquipo);

    public void borrarEstadoEquipo(EstadoEquipo estadoEquipo);

    public EstadoEquipo consultarEstadoEquipoPorID(BigInteger idRegistro);

    public List<EstadoEquipo> consultarEstadosEquiposRegistrados();
}
