/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.SalaLaboratorioxServicios;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AndresPineda
 */
public interface SalaLaboratorioxServiciosDAOInterface {

    public void crearSalaLaboratorioxServicios(SalaLaboratorioxServicios salalaboratorioxarea);

    public void editarSalaLaboratorioxServicios(SalaLaboratorioxServicios salalaboratorioxarea);

    public void eliminarSalaLaboratorioxServicios(SalaLaboratorioxServicios salalaboratorioxarea);

    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServicios();

    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServiciosActivos();

    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServiciosPorSala(BigInteger sala);

    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServiciosPorServicio(BigInteger servicio);

    public SalaLaboratorioxServicios buscarSalaLaboratorioxServiciosPorID(BigInteger idRegistro);

    public SalaLaboratorioxServicios buscarSalaLaboratorioxServiciosPorSalayServicio(BigInteger sala, BigInteger servicio);

    public List<SalaLaboratorioxServicios> buscarSalaLaboratorioxServiciosPorFiltrado(Map<String, String> filters);

    public List<SalaLaboratorioxServicios> buscarSalasLaboratorioxServiciosPorLaboratorioyServicio(BigInteger laboratorio, BigInteger servicio);

    public List<SalaLaboratorioxServicios> buscarSalasLaboratorioxServiciosPorLaboratorioyServicioActivo(BigInteger laboratorio, BigInteger servicio);

    public List<SalaLaboratorioxServicios> buscarSalasLaboratorioxServiciosPorLaboratorioyServicioActivoPublico(BigInteger laboratorio, BigInteger servicio);

}
