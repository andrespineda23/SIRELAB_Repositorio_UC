/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoServiciosSalaBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioxServiciosDAOInterface;
import com.sirelab.dao.interfacedao.ServiciosSalaDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
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
public class GestionarRecursoServiciosSalaBO implements GestionarRecursoServiciosSalaBOInterface{
  
    static Logger logger = Logger.getLogger(GestionarRecursoServiciosSalaBO.class);
    
    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    ServiciosSalaDAOInterface serviciosSalaDAO;
    @EJB
    SalaLaboratorioxServiciosDAOInterface laboratoriosPorAreasDAO;

    @Override
    public List<Facultad> consultarFacultadesRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarParametroServiciosSalaBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarParametroServiciosSalaBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarParametroServiciosSalaBO consultarLaboratoriosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ServiciosSala> consultarServiciosSalaPorParametro(Map<String, String> filtros) {
        try {
            List<ServiciosSala> lista = serviciosSalaDAO.buscarServiciosSalaPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaServiciosSalaBO consultarServiciosSalaPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevoServiciosSala(ServiciosSala servicio) {
        try {
            serviciosSalaDAO.crearServiciosSala(servicio);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaServiciosSalaBO crearNuevaServiciosSala : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionServiciosSala(ServiciosSala servicio) {
        try {
            serviciosSalaDAO.editarServiciosSala(servicio);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaServiciosSalaBO modificarInformacionServiciosSala : " + e.toString());
        }
    }

    @Override
    public ServiciosSala obtenerServiciosSalaPorIDServiciosSala(BigInteger idServiciosSala) {
        try {
            ServiciosSala registro = serviciosSalaDAO.buscarServiciosSalaPorID(idServiciosSala);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaServiciosSalaBO obtenerServiciosSalaPorIDServiciosSala : " + e.toString());
            return null;
        }
    }

    @Override
    public ServiciosSala obtenerServiciosSalaPorCodigo(String codigo) {
        try {
            ServiciosSala registro = serviciosSalaDAO.buscarServiciosSalaPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaServiciosSalaBO obtenerServiciosSalaPorCodigo : " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarCambioEstadoServicio(BigInteger servicio) {
        try {
            List<SalaLaboratorioxServicios> lista = laboratoriosPorAreasDAO.consultarSalaLaboratorioxServiciosPorServicio(servicio);
            if (null == lista) {
                return true;
            } else {
                int contador = 0;
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getEstado() == true) {
                        contador++;
                    }
                }
                if (contador == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlantaServiciosSalaBO validarCambioEstadoServicio : " + e.toString());
            return null;
        }
    }
}
