package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarPlanesEstudiosBOInterface;
import com.sirelab.dao.interfacedao.AsignaturaPorPlanEstudioDAOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
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
public class GestionarPlanesEstudiosBO implements GestionarPlanesEstudiosBOInterface {

    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    CarreraDAOInterface carreraDAO;
    @EJB
    PlanEstudiosDAOInterface planEstudiosDAO;
    @EJB
    AsignaturaPorPlanEstudioDAOInterface asignaturaPorPlanEstudioDAO;

    @Override
    public List<Facultad> consultarFacultadesRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Facultad> consultarFacultadesActivosRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultadesActivas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosActivosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosActivosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Carrera> consultarCarrerasPorIDDepartamento(BigInteger departamentos) {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasPorDepartamento(departamentos);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO consultarPlanesEstudiosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Carrera> consultarCarrerasActivosPorIDDepartamento(BigInteger departamentos) {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasActivosPorDepartamento(departamentos);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO consultarPlanesEstudiosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanesEstudiosPorParametro(Map<String, String> filtros) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.buscarPlanesEstudiosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO consultarPlanesEstudiosPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevoPlanEstudio(PlanEstudios planEstudio) {
        try {
            planEstudiosDAO.crearPlanEstudios(planEstudio);
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO crearNuevoPlanEstudio : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionPlanEstudios(PlanEstudios planEstudio) {
        try {
            planEstudiosDAO.editarPlanEstudios(planEstudio);
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO modificarInformacionPlanEstudios : " + e.toString());
        }
    }

    @Override
    public PlanEstudios obtenerPlanEstudiosPorIDPlanEstudio(BigInteger idPlanEstudio) {
        try {
            PlanEstudios registro = planEstudiosDAO.buscarPlanEstudiosPorID(idPlanEstudio);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO obtenerPlanEstudiosPorIDPlanEstudio : " + e.toString());
            return null;
        }
    }

    @Override
    public PlanEstudios obtenerPlanEstudioPorCodigo(String codigo) {
        try {
            PlanEstudios registro = planEstudiosDAO.buscarPlanEstudiosPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlanesEstudiosBO obtenerPlanEstudioPorCodigo : " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarCambioEstadoPlan(BigInteger plan) {
        try {
            List<AsignaturaPorPlanEstudio> lista = asignaturaPorPlanEstudioDAO.consultarAsignaturaPorPlanEstudiosIdPlanEstudio(plan);
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
            System.out.println("Error GestionarPlanesEstudiosBO validarCambioEstadoPlan : " + e.toString());
            return null;
        }
    }

}
