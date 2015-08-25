/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.usuarios;

import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.PersonaContacto;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministrarPersonasContactoBOInterface {

    public ConvenioPorEntidad buscarConvenioPorEntidadPorId(BigInteger idRegistro);

    public List<PersonaContacto> buscarPersonasContactoPorConvenioEntidad(BigInteger convenioentidad);

    public void crearPersonaContado(PersonaContacto personacontacto);

    public void editarPersonaContado(PersonaContacto personacontacto);

    public PersonaContacto obtenerPersonaContactoPorUsuario(String usuario);

    public void crearUsuario(Usuario usuario);

    public PersonaContacto obtenerPersonaContactoPorId(BigInteger idRegistro);

    public void editarUsuario(Usuario usuario);

}
