package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarPlanesEstudiosBOInterface;
import com.sirelab.dao.interfacedao.AsignaturaDAOInterface;
import com.sirelab.dao.interfacedao.AsignaturaPorPlanEstudioDAOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.PlanEstudios;
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
public class GestionarPlanesEstudiosBO implements GestionarPlanesEstudiosBOInterface {

    static Logger logger = Logger.getLogger(GestionarPlanesEstudiosBO.class);

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
    @EJB
    AsignaturaDAOInterface asignaturaDAO;

    @Override
    public List<Facultad> consultarFacultadesRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO consultarFacultadesRegistradas : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Facultad> consultarFacultadesActivosRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultadesActivas();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO consultarFacultadesRegistradas : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO consultarDepartamentosPorIDFacultad : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosActivosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosActivosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO consultarDepartamentosPorIDFacultad : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Carrera> consultarCarrerasPorIDDepartamento(BigInteger departamentos) {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasPorDepartamento(departamentos);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO consultarPlanesEstudiosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Carrera> consultarCarrerasActivosPorIDDepartamento(BigInteger departamentos) {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasActivosPorDepartamento(departamentos);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO consultarPlanesEstudiosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanesEstudiosPorParametro(Map<String, String> filtros) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.buscarPlanesEstudiosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO consultarPlanesEstudiosPorParametro : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void crearNuevoPlanEstudio(PlanEstudios planEstudio, List<Asignatura> lista) {
        try {
            planEstudiosDAO.crearPlanEstudios(planEstudio);
            if (null != lista) {
                PlanEstudios nuevo = planEstudiosDAO.obtenerUltimaPlanEstudiosRegistrada();
                for (int i = 0; i < lista.size(); i++) {
                    AsignaturaPorPlanEstudio registro = new AsignaturaPorPlanEstudio();
                    registro.setEstado(true);
                    registro.setAsignatura(lista.get(i));
                    registro.setPlanestudio(nuevo);
                    asignaturaPorPlanEstudioDAO.crearAsignaturaPorPlanEstudio(registro);
                }
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO crearNuevoPlanEstudio : " + e.toString(), e);
        }
    }

    @Override
    public void modificarInformacionPlanEstudios(PlanEstudios planEstudio) {
        try {
            planEstudiosDAO.editarPlanEstudios(planEstudio);
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO modificarInformacionPlanEstudios : " + e.toString(), e);
        }
    }

    @Override
    public void modificarInformacionAsignaturaPorPlanEstudio(List<AsignaturaPorPlanEstudio> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                asignaturaPorPlanEstudioDAO.editarAsignaturaPorPlanEstudio(lista.get(i));
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO modificarInformacionAsignaturaPorPlanEstudio : " + e.toString(), e);
        }
    }

    @Override
    public PlanEstudios obtenerPlanEstudiosPorIDPlanEstudio(BigInteger idPlanEstudio) {
        try {
            PlanEstudios registro = planEstudiosDAO.buscarPlanEstudiosPorID(idPlanEstudio);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO obtenerPlanEstudiosPorIDPlanEstudio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public PlanEstudios obtenerPlanEstudioPorCodigo(String codigo) {
        try {
            PlanEstudios registro = planEstudiosDAO.buscarPlanEstudiosPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO obtenerPlanEstudioPorCodigo : " + e.toString(), e);
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
            logger.error("Error GestionarPlanesEstudiosBO validarCambioEstadoPlan : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Asignatura> obtenerAsignaturasRegistradas() {
        try {
            List<Asignatura> lista = asignaturaDAO.consultarAsignaturasActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO obtenerAsignaturasRegistradas : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<AsignaturaPorPlanEstudio> obtenerAsignaturaPorPlanEstudioPorIdPlan(BigInteger plan) {
        try {
            List<AsignaturaPorPlanEstudio> lista = asignaturaPorPlanEstudioDAO.consultarAsignaturaPorPlanEstudiosIdPlanEstudio(plan);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO obtenerAsignaturaPorPlanEstudioPorIdPlan : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public boolean eliminarPlanEstudio(PlanEstudios plan) {
        try {
            planEstudiosDAO.eliminarPlanEstudios(plan);
            return true;
        } catch (Exception e) {
            logger.error("Error GestionarPlanesEstudiosBO eliminarPlanEstudio : " + e.toString(), e);
            return false;
        }
    }
}
