/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.GuiaLaboratorio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface GuiaLaboratorioDAOInterface {

    public List<GuiaLaboratorio> buscarGuiasLaboratorioPorFiltrado(Map<String, String> filters);

    public GuiaLaboratorio buscarGuiaLaboratorioPorUbicacion(String ubicacion);

    public void crearGuiaLaboratorio(GuiaLaboratorio guia);

    public void editarGuiaLaboratorio(GuiaLaboratorio guia);

    public void eliminarGuiaLaboratorio(GuiaLaboratorio guia);

    public List<GuiaLaboratorio> consultarGuiasLaboratorio();

    public GuiaLaboratorio buscarGuiaLaboratorioPorID(BigInteger idRegistro);
}
