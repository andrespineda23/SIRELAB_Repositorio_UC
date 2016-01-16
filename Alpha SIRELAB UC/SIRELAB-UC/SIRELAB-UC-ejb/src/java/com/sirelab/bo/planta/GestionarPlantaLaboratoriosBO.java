package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaLaboratoriosBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateful
public class GestionarPlantaLaboratoriosBO implements GestionarPlantaLaboratoriosBOInterface {

    static Logger logger = Logger.getLogger(GestionarPlantaLaboratoriosBO.class);
    
    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public Departamento consultarDepartamentoPorNombre(String nombre) {
        try {
            Departamento registro = departamentoDAO.buscarDepartamentoPorNombre(nombre);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO consultarDepartamentoPorNombre : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Facultad> consultarFacultadesRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Facultad> consultarFacultadesActivosRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultadesActivas();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO consultarFacultadesActivosRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosActivosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosActivosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO consultarDepartamentosActivosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosPorParametro(Map<String, String> filtros) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratoriosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO consultarLaboratoriosPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevoLaboratorio(Laboratorio laboratorio) {
        try {
            laboratorioDAO.crearLaboratorio(laboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO crearNuevaLaboratorio : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionLaboratorio(Laboratorio laboratorio) {
        try {
            laboratorioDAO.editarLaboratorio(laboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO modificarInformacionLaboratorio : " + e.toString());
        }
    }

    @Override
    public Laboratorio obtenerLaboratorioPorIDLaboratorio(BigInteger idLaboratorio) {
        try {
            Laboratorio registro = laboratorioDAO.buscarLaboratorioPorID(idLaboratorio);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public Laboratorio obtenerLaboratorioPorCodigo(String codigo) {
        try {
            Laboratorio registro = laboratorioDAO.buscarLaboratorioPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaLaboratorioBO obtenerLaboratorioPorCodigo : " + e.toString());
            return null;
        }
    }

}
