package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoPorEdificioDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.SalaLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.EncargadoPorEdificio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateful
public class AdministrarEncargadosLaboratoriosBO implements AdministrarEncargadosLaboratoriosBOInterface {

    static Logger logger = Logger.getLogger(AdministrarEncargadosLaboratoriosBO.class);

    @EJB
    EncargadoPorEdificioDAOInterface encargadoPorEdificioDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    SalaLaboratorioDAOInterface salaLaboratorioDAO;

    @Override
    public List<EncargadoLaboratorio> consultarEncargadoLaboratoriosPorParametro(Map<String, String> filtros) {
        try {
            List<EncargadoLaboratorio> lista = encargadoLaboratorioDAO.buscarEncargadosLaboratoriosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO consultarEncargadoLaboratoriosPorParametro : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorIDEncargadoLaboratorio(BigInteger idEncargadoLaboratorio) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idEncargadoLaboratorio);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoLaboratorioPorIDEncargadoLaboratorio : " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public List<Facultad> obtenerListaFacultades() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerListaFacultades : " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public List<Facultad> obtenerListaFacultadesActivos() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultadesActivas();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerListaFacultadesActivos : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Departamento> obtenerDepartamentosPorIDFacultad(BigInteger idFacultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(idFacultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerDepartamentosPorIDFacultad : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Departamento> obtenerDepartamentosActivosPorIDFacultad(BigInteger idFacultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosActivosPorIDFacultad(idFacultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerDepartamentosPorIDFacultad : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Laboratorio> obtenerLaboratoriosPorIDDepartamento(BigInteger idDepartamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(idDepartamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerLaboratoriosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Laboratorio> obtenerLaboratoriosActivosPorIDDepartamento(BigInteger idDepartamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioActivosPorIDDepartamento(idDepartamento);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerLaboratoriosActivosPorIDDepartamento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorCorreoNumDocumento(String correo, String documento) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorPorCorreoNumDocumento(correo, documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoLaboratorioPorCorreoNumDocumento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorCorreo(String correo) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorPorCorreo(correo);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoLaboratorioPorCorreo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorDocumento(String documento) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorPorDocumento(documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoLaboratorioPorDocumento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void actualizarInformacionEncargadoLaboratorio(EncargadoLaboratorio personalLab) {
        try {
            encargadoLaboratorioDAO.editarEncargadoLaboratorio(personalLab);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO actualizarInformacionEncargadoLaboratorio : " + e.toString(), e);
        }
    }

    //@Override
    public void actualizarInformacionPersona(Persona persona) {
        try {
            personaDAO.editarPersona(persona);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO actualizarInformacionPersona : " + e.toString(), e);
        }
    }

    //@Override
    public void actualizarInformacionUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO actualizarInformacionUsuario : " + e.toString(), e);

        }
    }

    @Override
    public void almacenarNuevoEncargadoLaboratorioEnSistema(Usuario usuarioNuevo, Persona personaNuevo, EncargadoLaboratorio personalNuevo) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorNombre("ENCARGADOLAB");
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
            Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
            personalNuevo.setPersona(personaRegistrada);
            encargadoLaboratorioDAO.crearEncargadoLaboratorio(personalNuevo);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO almacenarNuevoEncargadoLaboratorioEnSistema : " + e.toString(), e);
        }
    }

    @Override
    public Departamento obtenerDepartamentoPorCodigo(String codigo) {
        try {
            Departamento registro = departamentoDAO.buscarDepartamentoPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerDepartamentoPorCodigo: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Laboratorio obtenerLaboratorioPorCodigo(String codigo) {
        try {
            Laboratorio registro = laboratorioDAO.buscarLaboratorioPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerLaboratorioPorCodigo: " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Edificio buscarEdificioPorIdEncargadoEdificio(BigInteger idRegistro) {
        EncargadoPorEdificio obj = encargadoPorEdificioDAO.buscarEncargadoPorEdificioPorID(idRegistro);
        if (null != obj) {
            return obj.getEdificio();
        } else {
            return null;
        }
    }

    @Override
    public List<SalaLaboratorio> obtenerSalaLaboratorioPorEdificio(BigInteger edificio) {
        try {
            List<SalaLaboratorio> lista = salaLaboratorioDAO.buscarSalasLaboratoriosPorEdificio(edificio);
            return lista;
        } catch (Exception e) {
            return null;
        }
    }

    public Persona obtenerPersonaSistemaPorCorreo(String correo) {
        try {
            return personaDAO.buscarPersonaPorCorreo(correo);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerPersonaSistemaPorCorreo : " + e.toString(), e);
            return null;
        }
    }

    public Persona obtenerPersonaSistemaPorIdentificacion(String identificacion) {
        try {
            return personaDAO.buscarPersonaPorDocumento(identificacion);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerPersonaSistemaPorIdentificacion : " + e.toString(), e);
            return null;
        }
    }
}
