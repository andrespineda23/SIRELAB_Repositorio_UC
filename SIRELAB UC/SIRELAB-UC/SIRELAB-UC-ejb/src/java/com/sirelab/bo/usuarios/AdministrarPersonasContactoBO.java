/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarPersonasContactoBOInterface;
import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.dao.interfacedao.PersonaContactoDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.PersonaContacto;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
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
}
