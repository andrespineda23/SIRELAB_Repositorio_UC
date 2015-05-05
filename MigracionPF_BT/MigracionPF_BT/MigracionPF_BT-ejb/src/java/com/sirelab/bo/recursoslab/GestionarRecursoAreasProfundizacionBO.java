/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.GestionarRecursoAreasProfundizacionBOInterface;
import com.sirelab.dao.interfacedao.AreaProfundizacionDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateless
public class GestionarRecursoAreasProfundizacionBO implements GestionarRecursoAreasProfundizacionBOInterface {

    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    AreaProfundizacionDAOInterface areaProfundizacionDAO;

    @Override
    public List<Facultad> consultarFacultadesRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarParametroAreaProfundizacionBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarParametroAreaProfundizacionBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarParametroAreaProfundizacionBO consultarLaboratoriosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<AreaProfundizacion> consultarAreasProfundizacionPorParametro(Map<String, String> filtros) {
        try {
            List<AreaProfundizacion> lista = areaProfundizacionDAO.buscarAreasProfundizacionPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaAreaProfundizacionBO consultarAreasProfundizacionPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevaAreaProfundizacion(AreaProfundizacion areaProfundizacion) {
        try {
            areaProfundizacionDAO.crearAreaProfundizacion(areaProfundizacion);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaAreaProfundizacionBO crearNuevaAreaProfundizacion : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionAreaProfundizacion(AreaProfundizacion areaProfundizacion) {
        try {
            areaProfundizacionDAO.editarAreaProfundizacion(areaProfundizacion);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaAreaProfundizacionBO modificarInformacionAreaProfundizacion : " + e.toString());
        }
    }

    @Override
    public AreaProfundizacion obtenerAreaProfundizacionPorIDAreaProfundizacion(BigInteger idAreaProfundizacion) {
        try {
            AreaProfundizacion registro = areaProfundizacionDAO.buscarAreaProfundizacionPorID(idAreaProfundizacion);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaAreaProfundizacionBO obtenerAreaProfundizacionPorIDAreaProfundizacion : " + e.toString());
            return null;
        }
    }

    @Override
    public AreaProfundizacion obtenerAreaProfundizacionPorCodigo(String codigo) {
        try {
            AreaProfundizacion registro = areaProfundizacionDAO.buscarAreaProfundizacionPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaAreaProfundizacionBO obtenerAreaProfundizacionPorCodigo : " + e.toString());
            return null;
        }
    }

}
