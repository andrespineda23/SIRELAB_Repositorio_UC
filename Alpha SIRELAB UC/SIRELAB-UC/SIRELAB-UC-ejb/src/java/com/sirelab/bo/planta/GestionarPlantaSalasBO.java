package com.sirelab.bo.planta;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalasBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.ModuloLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioxServiciosDAOInterface;
import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.dao.interfacedao.ServiciosSalaDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.Sede;
import com.sirelab.entidades.ServiciosSala;
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
public class GestionarPlantaSalasBO implements GestionarPlantaSalasBOInterface {
    
    static Logger logger = Logger.getLogger(GestionarPlantaSalasBO.class);
    
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;
    @EJB
    EdificioDAOInterface edificioDAO;
    @EJB
    SedeDAOInterface sedeDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    @EJB
    ModuloLaboratorioDAOInterface moduloLaboratorioDAO;
    @EJB
    ServiciosSalaDAOInterface serviciosSalaDAO;
    @EJB
    SalaLaboratorioxServiciosDAOInterface salaLaboratorioxServiciosDAO;
    
    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorID(BigInteger idRegistro) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarFacultadesRegistradas : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Laboratorio> consultarLaboratoriosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarLaboratoriosRegistrados : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Departamento> consultarDepartamentosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarDepartamentosRegistrados : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Departamento> consultarDepartamentosActivosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentosActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarDepartamentosRegistrados : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Laboratorio> consultarLaboratoriosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarLaboratoriosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Laboratorio> consultarLaboratoriosActivosPorIDDepartamento(BigInteger departamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioActivosPorIDDepartamento(departamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarLaboratoriosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<ServiciosSala> consultarServiciosSalaRegistradas() {
        try {
            List<ServiciosSala> lista = serviciosSalaDAO.consultarServiciosSala();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarServiciosSalaRegistradas : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<ServiciosSala> consultarServiciosSalaActivosRegistradas() {
        try {
            List<ServiciosSala> lista = serviciosSalaDAO.consultarServiciosSalaActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarServiciosSalaRegistradas : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Sede> consultarSedesRegistradas() {
        try {
            List<Sede> lista = sedeDAO.consultarSedes();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarSedesRegistradas : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Sede> consultarSedesActivosRegistradas() {
        try {
            List<Sede> lista = sedeDAO.consultarSedesActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarSedesRegistradas : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Edificio> consultarEdificiosPorIDSede(BigInteger sede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosPorIDSede(sede);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarEdificiosPorIDSede : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<Edificio> consultarEdificiosActivosPorIDSede(BigInteger sede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosActivosPorIDSede(sede);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarEdificiosPorIDSede : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<SalaLaboratorio> consultarSalasLaboratoriosPorParametro(Map<String, String> filtros) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO consultarSalasLaboratoriosPorParametro : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public void crearNuevaSalaLaboratorio(SalaLaboratorio salaLaboratorio, List<ServiciosSala> listaServicios) {
        try {
            salaLaboratorioDAO.crearSalaLaboratorio(salaLaboratorio);
            SalaLaboratorio nuevaSala = salaLaboratorioDAO.obtenerUltimoSalaLaboratorioRegistrado();
            for (int i = 0; i < listaServicios.size(); i++) {
                SalaLaboratorioxServicios obj = new SalaLaboratorioxServicios();
                obj.setSalalaboratorio(nuevaSala);
                obj.setEstado(true);
                obj.setServiciosala(listaServicios.get(i));
                salaLaboratorioxServiciosDAO.crearSalaLaboratorioxServicios(obj);
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO crearNuevaSalaLaboratorio : " + e.toString(), e);
        }
    }
    
    @Override
    public void modificarInformacionSalaLaboratorio(SalaLaboratorio salaLaboratorio) {
        try {
            salaLaboratorioDAO.editarSalaLaboratorio(salaLaboratorio);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO modificarInformacionSalaLaboratorio : " + e.toString(), e);
        }
    }
    
    @Override
    public void almacenarModificacionesSalaServicio(List<SalaLaboratorioxServicios> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                salaLaboratorioxServiciosDAO.editarSalaLaboratorioxServicios(lista.get(i));
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO almacenarModificacionesSalaServicio : " + e.toString(), e);
        }
    }
    
    @Override
    public SalaLaboratorio obtenerSalaLaboratorioPorIDSalaLaboratorio(BigInteger idSalaLaboratorio) {
        try {
            SalaLaboratorio registro = salaLaboratorioDAO.buscarSalaLaboratorioPorID(idSalaLaboratorio);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO obtenerSalaLaboratorioPorIDSalaLaboratorio : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public SalaLaboratorio obtenerSalaLaboratorioPorCodigoEdificioyLaboratorio(String codigo, BigInteger edificio, BigInteger laboratorio) {
        try {
            SalaLaboratorio registro = salaLaboratorioDAO.buscarSalaLaboratorioPorCodigoyEdificioyLaboratorio(codigo, edificio, laboratorio);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO obtenerSalaLaboratorioPorCodigoEdificioyLaboratorio : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public Laboratorio obtenerLaboratorioPorId(BigInteger idRegistro) {
        try {
            Laboratorio registro = laboratorioDAO.buscarLaboratorioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO obtenerLaboratorioPorId : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public List<SalaLaboratorioxServicios> obtenerSalaLaboratorioxServiciosPorIdSala(BigInteger sala) {
        try {
            List<SalaLaboratorioxServicios> lista = salaLaboratorioxServiciosDAO.consultarSalaLaboratorioxServiciosPorSala(sala);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO obtenerSalaLaboratorioxServiciosPorIdSala : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public String obtenerCostoCalculadoSalaLaboratorio(BigInteger idSala) {
        try {
            Integer valor = 0;
            List<ModuloLaboratorio> lista = moduloLaboratorioDAO.buscarModuloLaboratorioPorIDSalaLaboratorio(idSala);
            if (null != lista) {
                for (int i = 0; i < lista.size(); i++) {
                    valor = valor + lista.get(i).getCostoalquiler().intValue();
                }
            }
            return valor.toString();
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO obtenerCostoCalculadoSalaLaboratorio : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public Integer obtenerModulosAsociados(BigInteger salaLaboratorio) {
        try {
            Integer cantidad = moduloLaboratorioDAO.buscarModuloLaboratorioPorIDSalaLaboratorio(salaLaboratorio).size();
            if (null == cantidad) {
                return 0;
            } else {
                return cantidad.intValue();
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO obtenerModulosAsociados : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public Integer obtenerServiciosAsociados(BigInteger salaLaboratorio) {
        try {
            Integer cantidad = salaLaboratorioxServiciosDAO.consultarSalaLaboratorioxServiciosPorSala(salaLaboratorio).size();
            if (null == cantidad) {
                return 0;
            } else {
                return cantidad.intValue();
            }
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO obtenerModulosAsociados : " + e.toString(), e);
            return null;
        }
    }
    
    @Override
    public void eliminarSalaLaboratorio(SalaLaboratorio sala) {
        try {
            salaLaboratorioDAO.eliminarSalaLaboratorio(sala);
        } catch (Exception e) {
            logger.error("Error GestionarPlantaSalasBO eliminarSalaLaboratorio : " + e.toString(), e);
        }
    }
    
}
