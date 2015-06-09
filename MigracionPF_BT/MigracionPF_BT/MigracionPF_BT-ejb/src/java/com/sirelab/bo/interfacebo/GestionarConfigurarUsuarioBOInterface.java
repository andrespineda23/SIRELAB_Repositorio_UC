/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo;

import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;

/**
 *
 * @author AndresPineda
 */
public interface GestionarConfigurarUsuarioBOInterface {

    public Persona obtenerPersonaPorUsuarioModificar(BigInteger idUsuario, String tipoUsuario);

    public void actualizarInformacionPersona(Persona persona);

    public void actualizarContraseniaPersona(Usuario usuario);

    public Persona obtenerPersonaPorEmail(String correo);

    public Persona obtenerPersonaPorDocumento(String documento);
}
