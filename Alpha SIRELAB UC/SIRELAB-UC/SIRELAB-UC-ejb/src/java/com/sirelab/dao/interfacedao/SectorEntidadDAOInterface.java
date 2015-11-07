/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.SectorEntidad;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface SectorEntidadDAOInterface {

    public void crearSectorEntidad(SectorEntidad sectorEntidad);

    public void editarSectorEntidad(SectorEntidad sectorEntidad);

    public void eliminarSectorEntidad(SectorEntidad sectorEntidad);

    public List<SectorEntidad> consultarSectoresEntidad();

    public SectorEntidad buscarSectorEntidadPorID(BigInteger idRegistro);

}
