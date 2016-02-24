package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresBOInterface;
import com.sirelab.bo.universidad.GestionarSedeBO;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
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
 * @author ANDRES PINEDA
 */
@Stateful
public class AdministrarAdministradoresBO implements AdministrarAdministradoresBOInterface {

    static Logger logger = Logger.getLogger(AdministrarAdministradoresBO.class);
    
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    PersonaDAOInterface personaDAO;

    @Override
    public List<Persona> consultarAdministradoresPorParametro(Map<String, String> filtros) {
        try {
            List<Persona> lista = personaDAO.buscarAdministradoresPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO consultarAdministradoresPorParametro : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Persona obtenerAdministradorPorIDPersona(BigInteger idPersona) {
        try {
            Persona registro = personaDAO.buscarPersonaPorID(idPersona);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO obtenerAdministradorPorIDPersona : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Persona obtenerAdministradorPorCorreoNumDocumento(String correo, String documento) {
        try {
            Persona registro = personaDAO.buscarPersonaPorCorreoYNumeroIdentificacion(correo, documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO obtenerAdministradorPorCorreoNumDocumento : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public Persona obtenerAdministradorPorDocumento(String documento) {
        try {
            Persona registro = personaDAO.buscarPersonaPorDocumento(documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO obtenerAdministradorPorDocumento : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public Persona obtenerAdministradorPorCorreo(String correo) {
        try {
            Persona registro = personaDAO.buscarPersonaPorCorreo(correo);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO obtenerAdministradorPorCorreo : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public Persona obtenerAdministradorPorUsuario(String usuario) {
        try {
            Persona registro = personaDAO.buscarPersonaPorUsuario(usuario);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO obtenerAdministradorPorUsuario : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void actualizarInformacionAdministrador(Persona administrador) {
        try {
            personaDAO.editarPersona(administrador);
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO almacenarNuevaPersonaEnSistema : " + e.toString(),e);
        }
    }

    //@Override
    public void actualizarInformacionUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO almacenarNuevaPersonaEnSistema : " + e.toString(),e);
        }
    }

    @Override
    public void almacenarNuevaPersonaEnSistema(Usuario usuarioNuevo, Persona personaNuevo) {
        try {
            //usuarioNuevo.setIdusuario(idUsuario);
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorNombre("ADMINISTRADOR");
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            //personaNuevo.setIdpersona(idPersona);
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
        } catch (Exception e) {
            logger.error("Error AdministrarAdministradoresBO almacenarNuevaPersonaEnSistema : " + e.toString(),e);
        }
    }
}
