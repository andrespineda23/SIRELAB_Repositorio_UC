/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.recursos;

import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ServiciosSala;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AndresPineda
 */
public interface GestionarRecursoServiciosSalaBOInterface {

    public List<Facultad> consultarFacultadesRegistradas();

    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad);

    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento);

    public List<ServiciosSala> consultarServiciosSalaPorParametro(Map<String, String> filtros);

    public void crearNuevoServiciosSala(ServiciosSala servicio);

    public void modificarInformacionServiciosSala(ServiciosSala servicio);

    public ServiciosSala obtenerServiciosSalaPorIDServiciosSala(BigInteger idServiciosSala);

    public ServiciosSala obtenerServiciosSalaPorCodigo(String codigo);

    public Boolean validarCambioEstadoServicio(BigInteger servicio);
}
