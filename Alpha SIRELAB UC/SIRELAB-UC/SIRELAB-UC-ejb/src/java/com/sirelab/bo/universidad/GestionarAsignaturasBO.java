package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarAsignaturasBOInterface;
import com.sirelab.bo.reservas.AdministrarReservasBO;
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
public class GestionarAsignaturasBO implements GestionarAsignaturasBOInterface {

    static Logger logger = Logger.getLogger(GestionarAsignaturasBO.class);
    
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
    @EJB
    AsignaturaPorPlanEstudioDAOInterface asignaturaPorPlanEstudioDAO;

    @Override
    public List<Departamento> consultarDepartamentosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarAsignaturasBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Carrera> consultarCarrerasPorIDDepartamento(BigInteger departamentos) {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasPorDepartamento(departamentos);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarAsignaturasBO consultarCarrerasPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PlanEstudios> consultarPlanesEstudiosPorIDCarrera(BigInteger carrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosPorCarrera(carrera);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarAsignaturasBO consultarPlanesEstudiosPorIDCarrera : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Asignatura> consultarAsignaturasPorParametro(Map<String, String> filtros) {
        try {
            List<Asignatura> lista = asignaturaDAO.buscarAsignaturasPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarAsignaturasBO consultarAsignaturasPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void modificarInformacionAsignatura(Asignatura asignatura) {
        try {
            asignaturaDAO.editarAsignatura(asignatura);
        } catch (Exception e) {
            logger.error("Error GestionarAsignaturasBO modificarInformacionAsignatura : " + e.toString());
        }
    }

    @Override
    public Asignatura obtenerAsignaturaPorIDAsignatura(BigInteger idAsignatura) {
        try {
            Asignatura registro = asignaturaDAO.buscarAsignaturaPorID(idAsignatura);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarAsignaturasBO obtenerAsignaturaPorIDAsignatura : " + e.toString());
            return null;
        }
    }

    @Override
    public Asignatura obtenerAsignaturaPorCodigo(String codigo) {
        try {
            Asignatura registro = asignaturaDAO.buscarAsignaturaPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarAsignaturasBO obtenerAsignaturaPorCodigoYPlanEstudio : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearAsignatura(Asignatura asignatura) {
        try {
            asignaturaDAO.crearAsignatura(asignatura);
        } catch (Exception e) {
            logger.error("Error GestionarAsignaturasBO crearAsignatura : " + e.toString());
        }
    }

    @Override
    public Boolean validarCambioEstadoAsignatura(BigInteger asignatura) {
        try {
            List<AsignaturaPorPlanEstudio> lista = asignaturaPorPlanEstudioDAO.consultarAsignaturaPorPlanEstudiosIdAsignatura(asignatura);
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
            logger.error("Error GestionarAsignaturasBO validarCambioEstadoAsignatura : " + e.toString());
            return null;
        }
    }

}
