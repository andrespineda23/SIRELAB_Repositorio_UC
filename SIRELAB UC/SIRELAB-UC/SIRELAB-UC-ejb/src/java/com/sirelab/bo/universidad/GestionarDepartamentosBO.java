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
import javax.ejb.Stateless;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateless
public class GestionarDepartamentosBO implements GestionarDepartamentosBOInterface {

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
            System.out.println("Error GestionarDepartamentosBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosPorParametro(Map<String, String> filtros) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarDepartamentosBO consultarDepartamentosPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevaDepartamento(Departamento departamento) {
        try {
            departamentoDAO.crearDepartamento(departamento);
        } catch (Exception e) {
            System.out.println("Error GestionarDepartamentosBO crearNuevaDepartamento : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionDepartamento(Departamento departamento) {
        try {
            departamentoDAO.editarDepartamento(departamento);
        } catch (Exception e) {
            System.out.println("Error GestionarDepartamentosBO crearNuevaDepartamento : " + e.toString());
        }
    }

    @Override
    public Departamento obtenerDepartamentoPorIDDepartamento(BigInteger idDepartamento) {
        try {
            Departamento registro = departamentoDAO.buscarDepartamentoPorID(idDepartamento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarDepartamentosBO obtenerDepartamentoPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public Departamento obtenerDepartamentoPorCodigo(String codigo) {
        try {
            Departamento registro = departamentoDAO.buscarDepartamentoPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarDepartamentosBO obtenerDepartamentoPorCodigo : " + e.toString());
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
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error GestionarDepartamentosBO validarCambioEstadoDepartamento : " + e.toString());
            return null;
        }
    }
}
