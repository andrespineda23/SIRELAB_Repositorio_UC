package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.GestionarPlantaModulosBOInterface;
import com.sirelab.dao.interfacedao.AreaProfundizacionDAOInterface;
import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratoriosPorAreasDAOInterface;
import com.sirelab.dao.interfacedao.ModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.ModuloLaboratorio;
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
public class GestionarPlantaModulosBO implements GestionarPlantaModulosBOInterface {

    @EJB
    ModuloLaboratorioDAOInterface moduloLaboratorioDAO;
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
    public List<LaboratoriosPorAreas> consultarLaboratoriosPorAreasRegistradas() {
        try {
            List<LaboratoriosPorAreas> lista = laboratoriosPorAreasDAO.consultarLaboratoriosPorAreassPorAreas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarLaboratoriosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO obtenerEncargadoLaboratorioPorID : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Laboratorio> consultarLaboratoriosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarLaboratoriosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public List<AreaProfundizacion> consultarAreasProfundizacionPorIDLaboratorio(BigInteger laboratorio) {
        try {
            List<AreaProfundizacion> lista = areaProfundizacionDAO.buscarAreaProfundizacionPorIDLaboratorio(laboratorio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarAreasProfundizacionPorIDLaboratorio : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Sede> consultarSedesRegistradas() {
        try {
            List<Sede> lista = sedeDAO.consultarSedes();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarSedesRegistradas : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Edificio> consultarEdificiosPorIDSede(BigInteger sede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosPorIDSede(sede);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarEdificiosPorIDSede : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDEdificio(BigInteger edificio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorEdificio(edificio);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarSalasLaboratorioPorIDEdificio : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratorioPorIDArea(BigInteger areaProfundizacion) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorAreaProfundizacion(areaProfundizacion);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarSalasLaboratorioPorParametroFiltrado : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ModuloLaboratorio> consultarModulosLaboratorioPorParametro(Map<String, String> filtros) {
        try {
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModulosLaboratoriosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarModulosLaboratorioPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevoModuloLaboratorio(ModuloLaboratorio moduloLaboratorio) {
        try {
            moduloLaboratorioDAO.crearModuloLaboratorio(moduloLaboratorio);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO crearNuevoModuloLaboratorio : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionModuloLaboratorio(ModuloLaboratorio moduloLaboratorio) {
        try {
            moduloLaboratorioDAO.editarModuloLaboratorio(moduloLaboratorio);
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO modificarInformacionModuloLaboratorio : " + e.toString());
        }
    }

    @Override
    public ModuloLaboratorio obtenerModuloLaboratorioPorIDModuloLaboratorio(BigInteger idModuloLaboratorio) {
        try {
            ModuloLaboratorio registro = moduloLaboratorioDAO.buscarModuloLaboratorioPorID(idModuloLaboratorio);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO obtenerModuloLaboratorioPorIDModuloLaboratorio : " + e.toString());
            return null;
        }
    }

    @Override
    public ModuloLaboratorio obtenerModuloLaboratorioPorCodigoYSala(String codigo, BigInteger sala) {
        try {
            ModuloLaboratorio registro = moduloLaboratorioDAO.buscarModuloLaboratorioPorCodigoYSala(codigo, sala);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO obtenerModuloLaboratorioPorCodigoYSala : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> consultarSalasLaboratoriosPorIDLaboratorioArea(BigInteger laboratorioArea) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorLaboratorioArea(laboratorioArea);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarPlantaModulosBO consultarSalasLaboratoriosPorIDLaboratorioArea : " + e.toString());
            return null;
        }
    }

}
