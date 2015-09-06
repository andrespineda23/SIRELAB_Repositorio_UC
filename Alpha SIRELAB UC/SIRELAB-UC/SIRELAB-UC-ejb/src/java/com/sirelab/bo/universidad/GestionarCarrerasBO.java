package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarCarrerasBOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
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
public class GestionarCarrerasBO implements GestionarCarrerasBOInterface {

    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    CarreraDAOInterface carreraDAO;
    @EJB
    PlanEstudiosDAOInterface planEstudiosDAO;

    //@Override
    public List<Facultad> consultarFacultadesRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Facultad> consultarFacultadesActivosRegistradas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultadesActivas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosActivosPorIDFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosActivosPorIDFacultad(facultad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Carrera> consultarCarrerasPorParametro(Map<String, String> filtros) {
        try {
            List<Carrera> lista = carreraDAO.buscarCarrerasPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO consultarCarrerasPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevaCarrera(Carrera carrera) {
        try {
            carreraDAO.crearCarrera(carrera);
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO crearNuevaCarrera : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionCarrera(Carrera carrera) {
        try {
            carreraDAO.editarCarrera(carrera);
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO modificarInformacionCarrera : " + e.toString());
        }
    }

    @Override
    public Carrera obtenerCarreraPorIDCarrera(BigInteger idCarrera) {
        try {
            Carrera registro = carreraDAO.buscarCarreraPorID(idCarrera);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO consultarDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public Carrera obtenerCarreraPorCodigo(String codigo) {
        try {
            Carrera registro = carreraDAO.buscarCarreraPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarCarrerasBO obtenerCarreraPorCodigoYDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarCambioEstadoCarrera(BigInteger carrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosPorCarrera(carrera);
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
            System.out.println("Error GestionarCarrerasBO validarCambioEstadoCarrera : " + e.toString());
            return null;
        }
    }

}
