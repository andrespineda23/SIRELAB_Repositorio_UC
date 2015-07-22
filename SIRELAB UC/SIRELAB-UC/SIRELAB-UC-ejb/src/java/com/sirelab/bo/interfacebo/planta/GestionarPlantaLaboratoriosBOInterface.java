/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.planta;

import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface GestionarPlantaLaboratoriosBOInterface {

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro);

    public List<Facultad> consultarFacultadesRegistradas();

    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad);

    public List<Laboratorio> consultarLaboratoriosPorParametro(Map<String, String> filtros);

    public void crearNuevaLaboratorio(Laboratorio laboratorio);

    public Laboratorio obtenerLaboratorioPorIDLaboratorio(BigInteger idLaboratorio);

    public void modificarInformacionLaboratorio(Laboratorio laboratorio);

    public Laboratorio obtenerLaboratorioPorCodigo(String codigo);

    public Departamento consultarDepartamentoPorNombre(String nombre);
}
