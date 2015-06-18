/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo;

import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.HojaVidaEquipo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarPlantaHojasVidaEquiposBOInterface {

    public EquipoElemento consultarEquipoElementoPorID(BigInteger equipo);

    public List<HojaVidaEquipo> consultarHojaVidaPorIDEquipo(BigInteger equipo);

    public void crearHojaVidaEquipo(HojaVidaEquipo hojaVidaEquipo);

    public HojaVidaEquipo consultarHojaVidaEquipoPorID(BigInteger hojavida);

    public void editarHojaVidaEquipo(HojaVidaEquipo hojaVidaEquipo);
}
