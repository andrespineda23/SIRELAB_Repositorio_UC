/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalaLaboratorioxServiciosBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioxServiciosDAOInterface;
import com.sirelab.dao.interfacedao.ServiciosSalaDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.ServiciosSala;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarPlantaSalaLaboratorioxServiciosBO implements GestionarPlantaSalaLaboratorioxServiciosBOInterface {
    
    static Logger logger = Logger.getLogger(GestionarPlantaSalaLaboratorioxServiciosBO.class);
    
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    SalaLaboratorioxServiciosDAOInterface salaLaboratorioXServiciosDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    ServiciosSalaDAOInterface serviciosSalaDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;
    
    @Override
    public SalaLaboratorioxServicios consultarSalaLaboratorioXServicioPorID(BigInteger idRegistro) {
        try {
            SalaLaboratorioxServicios registro = salaLaboratorioXServiciosDAO.buscarSalaLaboratorioxServiciosPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarSalaLaboratorioXServicioPorID : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO obtenerEncargadoLaboratorioPorID : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public SalaLaboratorioxServicios consultarSalaLaboratorioXServicioPorSalayServicio(BigInteger sala, BigInteger servicio) {
        try {
            SalaLaboratorioxServicios registro = salaLaboratorioXServiciosDAO.buscarSalaLaboratorioxServiciosPorSalayServicio(sala, servicio);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarSalaLaboratorioXServicioPorSalayServicio : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<SalaLaboratorioxServicios> consultarSalaLaboratorioxServiciosPorParametro(Map<String, String> filtros) {
        try {
            List<SalaLaboratorioxServicios> lista = salaLaboratorioXServiciosDAO.buscarSalaLaboratorioxServiciosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarSalaLaboratorioxServiciosPorParametro : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Departamento> consultarDepartamentosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarDepartamentosRegistrados : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Laboratorio> consultarLaboratoriosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarLaboratoriosRegistrados : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Departamento> consultarDepartamentosActivosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentosActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarDepartamentosRegistrados : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarLaboratoriosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<SalaLaboratorio> consultarSalaLaboratorioPorIDLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorio(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarSalaLaboraatorioPorIDLaboratorio : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Laboratorio> consultarLaboratoriosActivosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioActivosPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarLaboratoriosActivosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<SalaLaboratorio> consultarSalaLaboratoriosActivosPorIDLaboratorio(BigInteger laboratorio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioActivos(laboratorio);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarSalaLaboratoriosActivosPorIDLaboratorio : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<ServiciosSala> consultarServiciosSalaRegistradas() {
        try {
            List<ServiciosSala> lista = serviciosSalaDAO.consultarServiciosSala();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO consultarServiciosSalaRegistradas : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public void crearSalaLaboratorioxServicios(SalaLaboratorioxServicios registro) {
        try {
            salaLaboratorioXServiciosDAO.crearSalaLaboratorioxServicios(registro);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO crearSalaLaboratorioxServicios : " + e.toString(), e);
        }
    }
    
    @Override
    public void editarSalaLaboratorioxServicios(SalaLaboratorioxServicios registro) {
        try {
            salaLaboratorioXServiciosDAO.editarSalaLaboratorioxServicios(registro);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO editarSalaLaboratorioxServicios : " + e.toString(), e);
        }
    }
    
    @Override
    public void eliminarSalaLaboratorioxServicios(SalaLaboratorioxServicios registro) {
        try {
            salaLaboratorioXServiciosDAO.eliminarSalaLaboratorioxServicios(registro);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO eliminarSalaLaboratorioxServicios : " + e.toString(), e);
        }
    }
    
    @Override
    public SalaLaboratorio obtenerSalaLaboratorioPorId(BigInteger idRegistro) {
        try {
            SalaLaboratorio registro = salaLaboratorioDAO.buscarSalaLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO obtenerSalaLaboratorioPorId : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public void eliminarSalaLaboratorioXServicio(SalaLaboratorioxServicios registro) {
        try {
            salaLaboratorioXServiciosDAO.eliminarSalaLaboratorioxServicios(registro);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalaLaboratorioxServiciosBO eliminarSalaLaboratorioXServicio : " + e.toString(), e);
        }
    }
}
