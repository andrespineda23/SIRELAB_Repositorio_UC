/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.HojaVidaEquipo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface HojaVidaEquipoDAOInterface {

    public List<HojaVidaEquipo> consultarHojasVidaEquipoReporte();

    public void crearHojaVidaEquipo(HojaVidaEquipo hojavidaequipo);

    public void editarHojaVidaEquipo(HojaVidaEquipo hojavidaequipo);

    public void eliminarHojaVidaEquipo(HojaVidaEquipo hojavidaequipo);

    public List<HojaVidaEquipo> consultarHojaVidaPorIDEquipo(BigInteger idRegistro);

    public List<HojaVidaEquipo> consultarHojasVidaEquipo();

    public HojaVidaEquipo buscarHojaVidaEquipoPorID(BigInteger idRegistro);

}
