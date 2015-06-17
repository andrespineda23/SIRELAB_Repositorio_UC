/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo;

import com.sirelab.entidades.HorarioAtencion;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariableHorariosAtencionBOInterface {

    public List<HorarioAtencion> consultarPeriodosAcademicos();

    public void crearHorarioAtencion(HorarioAtencion horario);

    public void editarHorarioAtencion(HorarioAtencion horario);

    public HorarioAtencion consultarHorarioAtencionPorID(BigInteger idRegistro);
}
