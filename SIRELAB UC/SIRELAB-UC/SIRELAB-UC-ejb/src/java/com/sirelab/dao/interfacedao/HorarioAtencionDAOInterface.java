/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.HorarioAtencion;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface HorarioAtencionDAOInterface {

    public void crearHorarioAtencion(HorarioAtencion horario);

    public void editarHorarioAtencion(HorarioAtencion horario);

    public void eliminarHorarioAtencion(HorarioAtencion horario);

    public List<HorarioAtencion> consultarHorariosAtencion();

    public HorarioAtencion buscarHorarioAtencionPorID(BigInteger idRegistro);

    public HorarioAtencion buscarHorarioAtencionPorCodigo(String codigo);
}
