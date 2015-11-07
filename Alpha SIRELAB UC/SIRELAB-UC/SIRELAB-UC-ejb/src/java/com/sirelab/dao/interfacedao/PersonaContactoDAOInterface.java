/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.PersonaContacto;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface PersonaContactoDAOInterface {

    public PersonaContacto buscarPersonaContactoPorCorreo(String correo);

    public PersonaContacto buscarPersonaContactoPorIDPersona(BigInteger idPersona);

    public void crearPersonaContacto(PersonaContacto personacontacto);

    public void editarPersonaContacto(PersonaContacto personacontacto);

    public void eliminarPersonaContacto(PersonaContacto personacontacto);

    public List<PersonaContacto> consultarPersonasContacto();

    public PersonaContacto buscarPersonaContactoPorID(BigInteger idRegistro);

    public List<PersonaContacto> consultarPersonasContactoPorConvenioEntidad(BigInteger convenioentidad);

    public PersonaContacto buscarPersonaContactoPorUsuario(String usuario);

    public List<PersonaContacto> buscarPersonasContactoPorFiltrado(Map<String, String> filters);
}
