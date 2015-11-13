/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.SectorEntidad;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariableSectoresEntidadesBOInterface {

    public void crearSectorEntidad(SectorEntidad sectorEntidad);

    public void editarSectorEntidad(SectorEntidad sectorEntidad);

    public void borrarSectorEntidad(SectorEntidad sectorEntidad);

    public SectorEntidad consultarSectorEntidadPorID(BigInteger idRegistro);

    public List<SectorEntidad> consultarSectoresEntidadesRegistrados();
}
