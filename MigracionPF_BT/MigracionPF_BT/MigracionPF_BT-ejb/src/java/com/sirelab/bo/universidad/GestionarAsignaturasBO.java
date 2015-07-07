package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarAsignaturasBOInterface;
import com.sirelab.dao.interfacedao.AsignaturaDAOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.PlanEstudios;
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
public class GestionarAsignaturasBO implements GestionarAsignaturasBOInterface {

    @EJB
    AsignaturaDAOInterface asignaturaDAO;
    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    CarreraDAOInterface carreraDAO;
    @EJB
    PlanEstudiosDAOInterface planEstudiosDAO;

    @Override
    public List<Departamento> consultarDepartamentosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarAsignaturasBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Carrera> consultarCarrerasPorIDDepartamento(BigInteger departamentos) {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasPorDepartamento(departamentos);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarAsignaturasBO consultarCarrerasPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanesEstudiosPorIDCarrera(BigInteger carrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosPorCarrera(carrera);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarAsignaturasBO consultarPlanesEstudiosPorIDCarrera : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Asignatura> consultarAsignaturasPorParametro(Map<String, String> filtros) {
        try {
            List<Asignatura> lista = asignaturaDAO.buscarAsignaturasPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarAsignaturasBO consultarAsignaturasPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevoAsignatura(Asignatura asignatura) {
        try {
            asignaturaDAO.crearAsignatura(asignatura);
        } catch (Exception e) {
            System.out.println("Error GestionarAsignaturasBO crearNuevoAsignatura : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionAsignatura(Asignatura asignatura) {
        try {
            asignaturaDAO.editarAsignatura(asignatura);
        } catch (Exception e) {
            System.out.println("Error GestionarAsignaturasBO modificarInformacionAsignatura : " + e.toString());
        }
    }

    @Override
    public Asignatura obtenerAsignaturaPorIDAsignatura(BigInteger idAsignatura) {
        try {
            Asignatura registro = asignaturaDAO.buscarAsignaturaPorID(idAsignatura);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarAsignaturasBO obtenerAsignaturaPorIDAsignatura : " + e.toString());
            return null;
        }
    }
    
    @Override
    public Asignatura obtenerAsignaturaPorCodigoYPlanEstudio(String codigo, BigInteger plan) {
        try {
            Asignatura registro = asignaturaDAO.buscarAsignaturaPorCodigoYPlanEstudio(codigo, plan);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarAsignaturasBO obtenerAsignaturaPorCodigoYPlanEstudio : " + e.toString());
            return null;
        }
    }

}
