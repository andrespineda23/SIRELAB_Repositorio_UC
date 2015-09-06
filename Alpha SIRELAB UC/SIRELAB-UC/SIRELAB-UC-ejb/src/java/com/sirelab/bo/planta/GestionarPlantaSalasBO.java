package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalasBOInterface;
import com.sirelab.dao.interfacedao.AreaProfundizacionDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratoriosPorAreasDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
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
public class GestionarPlantaSalasBO implements GestionarPlantaSalasBOInterface {

    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    AreaProfundizacionDAOInterface areaProfundizacionDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;
    @EJB
    EdificioDAOInterface edificioDAO;
    @EJB
    SedeDAOInterface sedeDAO;
    @EJB
    LaboratoriosPorAreasDAOInterface laboratoriosPorAreasDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarFacultadesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarLaboratoriosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreasPorLaboratorio(BigInteger laboratorio) {
        try {
            List<LaboratoriosPorAreas> lista = laboratoriosPorAreasDAO.consultarLaboratoriosPorAreasPorLaboratorios(laboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarLaboratoriosPorAreasPorLaboratorio : " + e.toString());
            return null;
        }
    }
    @Override
    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreasActivosPorLaboratorio(BigInteger laboratorio) {
        try {
            List<LaboratoriosPorAreas> lista = laboratoriosPorAreasDAO.consultarLaboratoriosPorAreasActivosPorLaboratorios(laboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarLaboratoriosPorAreasPorLaboratorio : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarDepartamentosRegistrados : " + e.toString());
            return null;
        }
    }
    @Override
    public List<Departamento> consultarDepartamentosActivosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentosActivos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarDepartamentosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarLaboratoriosPorIDDepartamento : " + e.toString());
            return null;
        }
    }
    @Override
    public List<Laboratorio> consultarLaboratoriosActivosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioActivosPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarLaboratoriosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<AreaProfundizacion> consultarAreasProfundizacionRegistradas() {
        try {
            List<AreaProfundizacion> lista = areaProfundizacionDAO.consultarAreasProfundizacion();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarAreasProfundizacionRegistradas : " + e.toString());
            return null;
        }
    }
    @Override
    public List<AreaProfundizacion> consultarAreasProfundizacionActivosRegistradas() {
        try {
            List<AreaProfundizacion> lista = areaProfundizacionDAO.consultarAreasProfundizacionActivos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarAreasProfundizacionRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Sede> consultarSedesRegistradas() {
        try {
            List<Sede> lista = sedeDAO.consultarSedes();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarSedesRegistradas : " + e.toString());
            return null;
        }
    }
    @Override
    public List<Sede> consultarSedesActivosRegistradas() {
        try {
            List<Sede> lista = sedeDAO.consultarSedesActivos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarSedesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Edificio> consultarEdificiosPorIDSede(BigInteger sede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosPorIDSede(sede);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarEdificiosPorIDSede : " + e.toString());
            return null;
        }
    }
    @Override
    public List<Edificio> consultarEdificiosActivosPorIDSede(BigInteger sede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosActivosPorIDSede(sede);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarEdificiosPorIDSede : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratoriosPorParametro(Map<String, String> filtros) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO consultarSalasLaboratoriosPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevaSalaLaboratorio(SalaLaboratorio salaLaboratorio) {
        try {
            salaLaboratorioDAO.crearSalaLaboratorio(salaLaboratorio);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO crearNuevaSalaLaboratorio : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionSalaLaboratorio(SalaLaboratorio salaLaboratorio) {
        try {
            salaLaboratorioDAO.editarSalaLaboratorio(salaLaboratorio);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO modificarInformacionSalaLaboratorio : " + e.toString());
        }
    }

    @Override
    public SalaLaboratorio obtenerSalaLaboratorioPorIDSalaLaboratorio(BigInteger idSalaLaboratorio) {
        try {
            SalaLaboratorio registro = salaLaboratorioDAO.buscarSalaLaboratorioPorID(idSalaLaboratorio);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO obtenerSalaLaboratorioPorIDSalaLaboratorio : " + e.toString());
            return null;
        }
    }

    @Override
    public SalaLaboratorio obtenerSalaLaboratorioPorCodigoEdificioLabArea(String codigo, BigInteger edificio, BigInteger laboratorioArea) {
        try {
            SalaLaboratorio registro = salaLaboratorioDAO.buscarSalaLaboratorioPorCodigoEdificioLaboratorioArea(codigo, edificio, laboratorioArea);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaSalasBO obtenerSalaLaboratorioPorCodigoEdificioLabArea : " + e.toString());
            return null;
        }
    }

}
