package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarDepartamentosBOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
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
public class GestionarDepartamentosBO implements GestionarDepartamentosBOInterface {

    static Logger logger = Logger.getLogger(GestionarDepartamentosBO.class);

    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    CarreraDAOInterface carreraDAO;

    //@Override
    public List<Facultad> consultarFacultadesRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO consultarFacultadesRegistradas : " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public List<Facultad> consultarFacultadesActivosRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultadesActivas();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO consultarFacultadesRegistradas : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosPorParametro(Map<String, String> filtros) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO consultarDepartamentosPorParametro : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void crearNuevaDepartamento(Departamento departamento) {
        try {
            departamentoDAO.crearDepartamento(departamento);
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO crearNuevaDepartamento : " + e.toString(), e);
        }
    }

    @Override
    public void modificarInformacionDepartamento(Departamento departamento) {
        try {
            departamentoDAO.editarDepartamento(departamento);
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO crearNuevaDepartamento : " + e.toString(), e);
        }
    }

    @Override
    public Departamento obtenerDepartamentoPorIDDepartamento(BigInteger idDepartamento) {
        try {
            Departamento registro = departamentoDAO.buscarDepartamentoPorID(idDepartamento);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO obtenerDepartamentoPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Departamento obtenerDepartamentoPorCodigo(String codigo) {
        try {
            Departamento registro = departamentoDAO.buscarDepartamentoPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO obtenerDepartamentoPorCodigo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Boolean validarCambioEstadoDepartamento(BigInteger departamento) {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasPorDepartamento(departamento);
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
            logger.error("Error GestionarDepartamentosBO validarCambioEstadoDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Integer obtenerCarrerasAsociadas(BigInteger departamento) {
        try {
            List<Carrera> carreras = carreraDAO.consultarCarrerasPorDepartamento(departamento);
            if (null == carreras) {
                return 0;
            } else {
                return carreras.size();
            }
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO obtenerCarrerasAsociadas : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public boolean eliminarDepartamento(Departamento departamento) {
        try {
            departamentoDAO.eliminarDepartamento(departamento);
            return true;
        } catch (Exception e) {
            logger.error("Error GestionarDepartamentosBO eliminarDepartamento : " + e.toString(), e);
            return false;
        }
    }
}
