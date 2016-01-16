/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarPersonasContactoBOInterface;
import com.sirelab.dao.interfacedao.ConvenioDAOInterface;
import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.dao.interfacedao.PersonaContactoDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PersonaContacto;
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
public class AdministrarPersonasContactoBO implements AdministrarPersonasContactoBOInterface {

    static Logger logger = Logger.getLogger(AdministrarPersonasContactoBO.class);
    
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    PersonaContactoDAOInterface personaContactoDAO;
    @EJB
    ConvenioPorEntidadDAOInterface convenioPorEntidadDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    ConvenioDAOInterface convenioDAO;
    @EJB
    EntidadExternaDAOInterface entidadExternaDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;

    @Override
    public ConvenioPorEntidad buscarConvenioPorEntidadPorId(BigInteger idRegistro) {
        try {
            ConvenioPorEntidad registro = convenioPorEntidadDAO.buscarConvenioPorEntidadPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO buscarConvenioPorEntidadPorId: " + e.toString());
            return null;
        }
    }

    @Override
    public List<PersonaContacto> consultarPersonasContactoPorParametro(Map<String, String> filtros) {
        try {
            List<PersonaContacto> lista = personaContactoDAO.buscarPersonasContactoPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO consultarPersonasContactoPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PersonaContacto> buscarPersonasContactoPorConvenioEntidad(BigInteger convenioentidad) {
        try {
            List<PersonaContacto> lista = personaContactoDAO.consultarPersonasContactoPorConvenioEntidad(convenioentidad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO buscarPersonasContactoPorConvenioEntidad: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearPersonaContado(Usuario usuario, Persona persona, PersonaContacto personacontacto) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorID(new BigInteger("5"));
            usuario.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuario);
            Usuario usuarioN = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            persona.setUsuario(usuarioN);
            personaDAO.crearPersona(persona);
            Persona personaN = personaDAO.obtenerUltimaPersonaRegistrada();
            personacontacto.setPersona(personaN);
            personaContactoDAO.crearPersonaContacto(personacontacto);
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO crearPersonaContado: " + e.toString());
        }
    }

    @Override
    public void editarPersonaContado(PersonaContacto personacontacto) {
        try {
            usuarioDAO.editarUsuario(personacontacto.getPersona().getUsuario());
            personaDAO.editarPersona(personacontacto.getPersona());
            personaContactoDAO.editarPersonaContacto(personacontacto);
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO editarPersonaContado: " + e.toString());
        }
    }

    @Override
    public PersonaContacto obtenerPersonaContactoPorUsuario(String usuario) {
        try {
            PersonaContacto registro = personaContactoDAO.buscarPersonaContactoPorUsuario(usuario);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerPersonaContactoPorUsuario: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        try {
            usuarioDAO.crearUsuario(usuario);
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO crearUsuario: " + e.toString());
        }
    }

    @Override
    public PersonaContacto obtenerPersonaContactoPorId(BigInteger idRegistro) {
        try {
            PersonaContacto registro = personaContactoDAO.buscarPersonaContactoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerPersonaContactoPorId: " + e.toString());
            return null;
        }
    }

    @Override
    public void editarUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO editarUsuario: " + e.toString());
        }
    }

    @Override
    public List<EntidadExterna> obtenerEntidadesExternasRegistradas() {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.consultarEntidadesExternas();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerEntidadesExternasRegistradas: " + e.toString());
            return null;
        }
    }

    @Override
    public List<EntidadExterna> obtenerEntidadesExternasActivosRegistradas() {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.consultarEntidadesExternasActivas();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerEntidadesExternasActivosRegistradas: " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Convenio> obtenerConveniosRegistradas() {
        try {
            List<Convenio> lista = convenioDAO.consultarConvenios();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerConveniosRegistradas: " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Convenio> obtenerConveniosActivosRegistradas() {
        try {
            List<Convenio> lista = convenioDAO.consultarConveniosActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerConveniosActivosRegistradas: " + e.toString());
            return null;
        }
    }

    @Override
    public ConvenioPorEntidad buscarConvenioPorEntidadPorEntidadYConvenio(BigInteger entidad, BigInteger convenio) {
        try {
            ConvenioPorEntidad registro = convenioPorEntidadDAO.buscarConvenioPorEntidadPorParametros(entidad, convenio);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO buscarConvenioPorEntidadPorEntidadYConvenio: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> obtenerConveniosPorEntidadRegistrados() {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidad();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerConveniosPorEntidadRegistrados: " + e.toString());
            return null;
        }
    }

    @Override
    public PersonaContacto obtenerPersonaContactoPorCorreo(String correo) {
        try {
            PersonaContacto registro = personaContactoDAO.buscarPersonaContactoPorCorreo(correo);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerConveniosPorEntidadRegistrados: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> obtenerConvenioPorEntidadPorIdEntidad(BigInteger idEntidad) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorEntidad(idEntidad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarPersonasContactoBO obtenerConvenioPorEntidadPorIdEntidad: " + e.toString());
            return null;
        }
    }
}
