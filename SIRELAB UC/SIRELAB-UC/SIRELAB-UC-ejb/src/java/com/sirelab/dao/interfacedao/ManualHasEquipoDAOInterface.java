/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.ManualHasEquipo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface ManualHasEquipoDAOInterface {

    public void crearManualHasEquipo(ManualHasEquipo manual);

    public void editarManualHasEquipo(ManualHasEquipo manual);

    public void eliminarManualHasEquipo(ManualHasEquipo manual);

    public List<ManualHasEquipo> consultarManualHasEquipoesHasEquipo();

    public ManualHasEquipo buscarManualHasEquipoPorID(BigInteger idRegistro);

    public List<ManualHasEquipo> buscarManualHasEquipoPorEquipo(BigInteger equipo);
}
