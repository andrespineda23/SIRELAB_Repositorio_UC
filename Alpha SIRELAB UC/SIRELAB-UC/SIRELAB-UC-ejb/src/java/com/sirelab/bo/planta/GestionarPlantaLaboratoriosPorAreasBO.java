/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaLaboratoriosPorAreasBOInterface;
import com.sirelab.dao.interfacedao.AreaProfundizacionDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratoriosPorAreasDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.SalaLaboratorio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarPlantaLaboratoriosPorAreasBO implements GestionarPlantaLaboratoriosPorAreasBOInterface {

    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    LaboratoriosPorAreasDAOInterface laboratoriosPorAreasDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    AreaProfundizacionDAOInterface areaProfundizacionDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;

    @Override
    public LaboratoriosPorAreas consultarLaboratorioPorAreaPorID(BigInteger idRegistro) {
        try {
            LaboratoriosPorAreas registro = laboratoriosPorAreasDAO.buscarLaboratoriosPorAreasPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO consultarLaboratorioPorAreaPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO obtenerEncargadoLaboratorioPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public LaboratoriosPorAreas consultarLaboratorioPorAreaPorLabYArea(BigInteger laboratorio, BigInteger area) {
        try {
            LaboratoriosPorAreas registro = laboratoriosPorAreasDAO.buscarLaboratoriosPorAreasPorLabYArea(laboratorio, area);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO consultarLaboratorioPorAreaPorLabYArea : " + e.toString());
            return null;
        }
    }

    @Override
    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreasPorParametro(Map<String, String> filtros) {
        try {
            List<LaboratoriosPorAreas> lista = laboratoriosPorAreasDAO.buscarLaboratoriosPorAreasPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO consultarLaboratoriosPorAreasPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO consultarDepartamentosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO consultarLaboratoriosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<AreaProfundizacion> consultarAreasProfundizacionRegistradas() {
        try {
            List<AreaProfundizacion> lista = areaProfundizacionDAO.consultarAreasProfundizacion();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO consultarAreasProfundizacionRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearLaboratoriosPorAreas(LaboratoriosPorAreas registro) {
        try {
            laboratoriosPorAreasDAO.crearLaboratoriosPorAreas(registro);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO crearLaboratoriosPorAreas : " + e.toString());
        }
    }

    @Override
    public void editarLaboratoriosPorAreas(LaboratoriosPorAreas registro) {
        try {
            laboratoriosPorAreasDAO.editarLaboratoriosPorAreas(registro);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO editarLaboratoriosPorAreas : " + e.toString());
        }
    }

    @Override
    public void eliminarLaboratoriosPorAreas(LaboratoriosPorAreas registro) {
        try {
            laboratoriosPorAreasDAO.eliminarLaboratoriosPorAreas(registro);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO eliminarLaboratoriosPorAreas : " + e.toString());
        }
    }

    @Override
    public Boolean validarCambioEstadoRegistro(BigInteger laboratorioporarea) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioArea(laboratorioporarea);
            if (null == lista) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaLaboratoriosPorAreasBO validarCambioEstadoRegistro : " + e.toString());
            return null;
        }
    }
}
