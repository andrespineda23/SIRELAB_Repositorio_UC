/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresEdificioBOInterface;
import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.dao.interfacedao.AdministradorEdificioDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoPorEdificioDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.AdministradorEdificio;
import com.sirelab.entidades.EncargadoPorEdificio;
import com.sirelab.entidades.Sede;
import com.sirelab.entidades.Persona;
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
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarAdministradoresEdificioBO implements AdministrarAdministradoresEdificioBOInterface {

    static Logger logger = Logger.getLogger(AdministrarAdministradoresEdificioBO.class);

    @EJB
    AdministradorEdificioDAOInterface administradorEdificioDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    SedeDAOInterface sedeDAO;
    @EJB
    EdificioDAOInterface edificioDAO;
    @EJB
    EncargadoPorEdificioDAOInterface encargadoPorEdificioDAOInterface;

    @Override
    public List<EncargadoPorEdificio> consultarEncargadosPorEdificioPorParametro(Map<String, String> filtros) {
        try {
            List<EncargadoPorEdificio> lista = encargadoPorEdificioDAOInterface.buscarEncargadosPorEdificioPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO consultarEncargadosPorEdificioPorParametro : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public EncargadoPorEdificio obtenerEncargadoPorEdificioPorIDEncargadoPorEdificio(BigInteger idEncargadoPorEdificio) {
        try {
            EncargadoPorEdificio registro = encargadoPorEdificioDAOInterface.buscarEncargadoPorEdificioPorID(idEncargadoPorEdificio);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoPorEdificioPorIDEncargadoPorEdificio : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Sede> obtenerListaSedes() {
        try {
            List<Sede> lista = sedeDAO.consultarSedes();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerListaSedes : " + e.toString(), e);
            return null;
        }
    }

    //@Override
    public List<Sede> obtenerListaSedesActivos() {
        try {
            List<Sede> lista = sedeDAO.consultarSedesActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerListaSedesActivos : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Edificio> obtenerEdificiosPorIDSede(BigInteger idSede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosPorIDSede(idSede);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerEdificiosPorIDSede : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Edificio> obtenerEdificiosActivosPorIDSede(BigInteger idSede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosActivosPorIDSede(idSede);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerEdificiosPorIDSede : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public AdministradorEdificio obtenerAdministradorEdificioPorCorreoNumDocumento(String correo, String documento) {
        try {
            AdministradorEdificio registro = administradorEdificioDAO.buscarAdministradorEdificioPorPorCorreoNumDocumento(correo, documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerAdministradorEdificioPorCorreoNumDocumento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public AdministradorEdificio obtenerAdministradorEdificioPorCorreo(String correo) {
        try {
            AdministradorEdificio registro = administradorEdificioDAO.buscarAdministradorEdificioPorPorCorreo(correo);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerAdministradorEdificioPorCorreo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public AdministradorEdificio obtenerAdministradorEdificioPorDocumento(String documento) {
        try {
            AdministradorEdificio registro = administradorEdificioDAO.buscarAdministradorEdificioPorPorDocumento(documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerAdministradorEdificioPorDocumento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void actualizarInformacionAdministradorEdificio(AdministradorEdificio administradorEdificio) {
        try {
            administradorEdificioDAO.editarAdministradorEdificio(administradorEdificio);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO actualizarInformacionAdministradorEdificio : " + e.toString(), e);
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
    public void almacenarNuevoAdministradorEdificioEnSistema(Usuario usuarioNuevo, Persona personaNuevo, AdministradorEdificio personalNuevo, Edificio edificio) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorNombre("ADMINISTRADOREDIFICIO");
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            //
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
            //
            Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
            personalNuevo.setPersona(personaRegistrada);
            administradorEdificioDAO.crearAdministradorEdificio(personalNuevo);
            //
            AdministradorEdificio ultimoAdministradorEdificio = administradorEdificioDAO.obtenerUltimaAdministradorEdificioRegistrada();
            logger.error("ultimoAdministradorEdificio : " + ultimoAdministradorEdificio);
            EncargadoPorEdificio encargadoPorEdificio = new EncargadoPorEdificio();
            encargadoPorEdificio.setEstado(true);
            encargadoPorEdificio.setEdificio(edificio);
            encargadoPorEdificio.setAdministradoredificio(ultimoAdministradorEdificio);
            encargadoPorEdificioDAOInterface.crearEncargadoPorEdificio(encargadoPorEdificio);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO almacenarNuevoAdministradorEdificioEnSistema : " + e.toString(), e);
        }
    }

    @Override
    public void registrarAsocioEncargadoEdificio(AdministradorEdificio administradorEdificio, Edificio edificio) {
        try {
            EncargadoPorEdificio encargadoPorEdificio = new EncargadoPorEdificio();
            encargadoPorEdificio.setEstado(true);
            encargadoPorEdificio.setEdificio(edificio);
            encargadoPorEdificio.setAdministradoredificio(administradorEdificio);
            encargadoPorEdificioDAOInterface.crearEncargadoPorEdificio(encargadoPorEdificio);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO registrarAsocioEncargadoEdificio : " + e.toString(), e);
        }
    }

    @Override
    public void editarAsocioEncargadoEdificio(EncargadoPorEdificio encargadoPorEdificio) {
        try {
            encargadoPorEdificioDAOInterface.editarEncargadoPorEdificio(encargadoPorEdificio);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO registrarAsocioEncargadoEdificio : " + e.toString(), e);
        }
    }

    @Override
    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorIDAdministrador(BigInteger idAdministrador) {
        try {
            List<EncargadoPorEdificio> lista = encargadoPorEdificioDAOInterface.buscarEncargadosPorEdificioPorIDAdministrador(idAdministrador);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO buscarEncargadosPorEdificioPorIDAdministrador : " + e.toString(), e);
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
