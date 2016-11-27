package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarDocentesBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.TipoCargoDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.TipoCargo;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
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
public class AdministrarDocentesBO implements AdministrarDocentesBOInterface {

    static Logger logger = Logger.getLogger(AdministrarDocentesBO.class);

    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    DocenteDAOInterface docenteDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    TipoCargoDAOInterface tipoCargoDAO;

    @Override
    public List<Docente> consultarDocentesPorParametro(Map<String, String> filtros) {
        try {
            List<Docente> lista = docenteDAO.buscarDocentesPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO consultarDocentesPorParametro : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Docente obtenerDocentePorIDDocente(BigInteger idDocente) {
        try {
            Docente registro = docenteDAO.buscarDocentePorID(idDocente);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerDocentePorIDDocente : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<TipoCargo> obtenerListaTiposCargos() {
        try {
            List<TipoCargo> lista = tipoCargoDAO.consultarTiposCargos();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerListaTiposCargos : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Facultad> obtenerListaFacultades() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerListaFacultades : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Facultad> obtenerListaFacultadesActivas() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultadesActivas();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerListaFacultadesActivas : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Departamento> obtenerDepartamentosPorIDFacultad(BigInteger idFacultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(idFacultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerDepartamentosPorIDFacultad : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Departamento> obtenerDepartamentosActivosPorIDFacultad(BigInteger idFacultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosActivosPorIDFacultad(idFacultad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerDepartamentosActivosPorIDFacultad : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Docente obtenerDocentePorCorreo(String correo) {
        try {
            Docente registro = docenteDAO.obtenerDocentePorCorreo(correo);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerDocentePorCorreo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Docente obtenerDocentePorDocumento(String documento) {
        try {
            Docente registro = docenteDAO.obtenerDocentePorDocumento(documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerDocentePorDocumento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Docente obtenerDocentePorCorreoNumDocumento(String correo, String documento) {
        try {
            Docente registro = docenteDAO.obtenerDocentePorCorreoDocumento(correo, documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerDocentePorCorreoNumDocumento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void actualizarInformacionDocente(Docente docente) {
        try {
            docenteDAO.editarDocente(docente);
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO actualizarInformacionDocente : " + e.toString(), e);
        }
    }

    //@Override 
    public void actualizarInformacionPersona(Persona persona) {
        try {
            personaDAO.editarPersona(persona);
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO actualizarInformacionPersona : " + e.toString(), e);
        }
    }

    //@Override 
    public void actualizarInformacionUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO actualizarInformacionUsuario : " + e.toString(), e);

        }
    }

    @Override
    public void almacenarNuevoDocenteEnSistema(Usuario usuarioNuevo, Persona personaNuevo, Docente docenteNuevo) {
        try {
            int sec = 1;
            //usuarioNuevo.setIdusuario(idUsuario);
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorNombre("DOCENTE");
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            //personaNuevo.setIdpersona(idPersona);
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
            Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
            //estudianteNuevo.setIdestudiante(idEstudiante);
            docenteNuevo.setPersona(personaRegistrada);
            docenteDAO.crearDocente(docenteNuevo);
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO almacenarNuevoDocenteEnSistema : " + e.toString(), e);
        }
    }

    public Persona obtenerPersonaSistemaPorCorreo(String correo) {
        try {
            return personaDAO.buscarPersonaPorCorreo(correo);
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerPersonaSistemaPorCorreo : " + e.toString(), e);
            return null;
        }
    }

    public Persona obtenerPersonaSistemaPorIdentificacion(String identificacion) {
        try {
            return personaDAO.buscarPersonaPorDocumento(identificacion);
        } catch (Exception e) {
            logger.error("Error AdministrarDocentesBO obtenerPersonaSistemaPorIdentificacion : " + e.toString(), e);
            return null;
        }
    }
}
