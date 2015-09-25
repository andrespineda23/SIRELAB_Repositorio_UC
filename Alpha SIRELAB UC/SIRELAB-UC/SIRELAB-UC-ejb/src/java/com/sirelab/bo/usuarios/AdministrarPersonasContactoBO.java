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
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.PersonaContacto;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarPersonasContactoBO implements AdministrarPersonasContactoBOInterface {

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

    @Override
    public ConvenioPorEntidad buscarConvenioPorEntidadPorId(BigInteger idRegistro) {
        try {
            ConvenioPorEntidad registro = convenioPorEntidadDAO.buscarConvenioPorEntidadPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO buscarConvenioPorEntidadPorId: " + e.toString());
            return null;
        }
    }

    @Override
    public List<PersonaContacto> consultarPersonasContactoPorParametro(Map<String, String> filtros) {
        try {
            List<PersonaContacto> lista = personaContactoDAO.buscarPersonasContactoPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO consultarPersonasContactoPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PersonaContacto> buscarPersonasContactoPorConvenioEntidad(BigInteger convenioentidad) {
        try {
            List<PersonaContacto> lista = personaContactoDAO.consultarPersonasContactoPorConvenioEntidad(convenioentidad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO buscarPersonasContactoPorConvenioEntidad: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearPersonaContado(PersonaContacto personacontacto) {
        try {
            personaContactoDAO.crearPersonaContacto(personacontacto);
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO crearPersonaContado: " + e.toString());
        }
    }

    @Override
    public void editarPersonaContado(PersonaContacto personacontacto) {
        try {
            personaContactoDAO.editarPersonaContacto(personacontacto);
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO editarPersonaContado: " + e.toString());
        }
    }

    @Override
    public PersonaContacto obtenerPersonaContactoPorUsuario(String usuario) {
        try {
            PersonaContacto registro = personaContactoDAO.buscarPersonaContactoPorUsuario(usuario);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO obtenerPersonaContactoPorUsuario: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        try {
            usuarioDAO.crearUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO crearUsuario: " + e.toString());
        }
    }

    @Override
    public PersonaContacto obtenerPersonaContactoPorId(BigInteger idRegistro) {
        try {
            PersonaContacto registro = personaContactoDAO.buscarPersonaContactoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO obtenerPersonaContactoPorId: " + e.toString());
            return null;
        }
    }

    @Override
    public void editarUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO editarUsuario: " + e.toString());
        }
    }

    @Override
    public List<EntidadExterna> obtenerEntidadesExternasRegistradas() {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.consultarEntidadesExternas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO obtenerEntidadesExternasRegistradas: " + e.toString());
            return null;
        }
    }

    @Override
    public List<EntidadExterna> obtenerEntidadesExternasActivosRegistradas() {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.consultarEntidadesExternasActivas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO obtenerEntidadesExternasActivosRegistradas: " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Convenio> obtenerConveniosRegistradas() {
        try {
            List<Convenio> lista = convenioDAO.consultarConvenios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO obtenerConveniosRegistradas: " + e.toString());
            return null;
        }
    }

    //@Override

    public List<Convenio> obtenerConveniosActivosRegistradas() {
        try {
            List<Convenio> lista = convenioDAO.consultarConveniosActivos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO obtenerConveniosActivosRegistradas: " + e.toString());
            return null;
        }
    }

    @Override
    public ConvenioPorEntidad buscarConvenioPorEntidadPorEntidadYConvenio(BigInteger entidad, BigInteger convenio) {
        try {
            ConvenioPorEntidad registro = convenioPorEntidadDAO.buscarConvenioPorEntidadPorParametros(entidad, convenio);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO buscarConvenioPorEntidadPorEntidadYConvenio: " + e.toString());
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> obtenerConveniosPorEntidadRegistrados() {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidad();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarPersonasContactoBO obtenerConveniosPorEntidadRegistrados: " + e.toString());
            return null;
        }
    }
}
