package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEntidadesExternasBOInterface;
import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
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
public class AdministrarEntidadesExternasBO implements AdministrarEntidadesExternasBOInterface {

    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    EntidadExternaDAOInterface entidadExternaDAO;
    @EJB
    ConvenioPorEntidadDAOInterface convenioPorEntidadDAO;

    @Override
    public List<EntidadExterna> consultarEntidadesExternasPorParametro(Map<String, String> filtros) {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.buscarEntidadesExternasPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO consultarEntidadesExternasPorParametro : " + e.toString());
            return null;
        }

    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorIDEntidadExterna(BigInteger idEntidadExterna) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorID(idEntidadExterna);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorIDEntidadExterna : " + e.toString());
            return null;
        }
    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorCorreoNumDocumento(String correo, String documento) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorCorreoNumDocumento(correo, documento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorCorreoNumDocumento : " + e.toString());
            return null;
        }
    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorDocumento(String documento) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorDocumento(documento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorDocumento : " + e.toString());
            return null;
        }
    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorCorreo(String correo) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorCorreo(correo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorCorreo : " + e.toString());
            return null;
        }
    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorIdentificacion(String identificacion) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorIdentificacionEntidad(identificacion);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorIdentificacion : " + e.toString());
            return null;
        }
    }

    @Override
    public void actualizarInformacionEntidadExterna(EntidadExterna entidadExterna) {
        try {
            entidadExternaDAO.editarEntidadExterna(entidadExterna);
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO actualizarInformacionEntidadExterna : " + e.toString());
        }
    }

    //@Override 
    public void actualizarInformacionPersona(Persona persona) {
        try {
            personaDAO.editarPersona(persona);
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO actualizarInformacionPersona : " + e.toString());
        }
    }

    //@Override 
    public void actualizarInformacionUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO actualizarInformacionUsuario : " + e.toString());
        }
    }

    @Override
    public void almacenarNuevaEntidadExternaEnSistema(Usuario usuarioNuevo, Persona personaNuevo, EntidadExterna entidadNueva) {
        try {
            int sec = 1;
            //usuarioNuevo.setIdusuario(idUsuario);
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorNombre("ENTIDADEXTERNA");
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            //personaNuevo.setIdpersona(idPersona);
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
            Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
            //estudianteNuevo.setIdestudiante(idEstudiante);
            entidadNueva.setPersona(personaRegistrada);
            entidadExternaDAO.crearEntidadExterna(entidadNueva);
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO almacenarNuevaEntidadExternaEnSistema : " + e.toString());
        }
    }

    @Override
    public Boolean validarCambioEstadoEntidad(BigInteger entidad) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorEntidad(entidad);
            if (null == lista) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error AdministrarEntidadesExternasBO validarCambioEstadoEntidad : " + e.toString());
            return null;
        }
    }

}
