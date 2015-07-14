/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.TipoUsuario;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariableTiposUsuarioBOInterface {

    public void crearTipoUsuario(TipoUsuario tipoUsuario);

    public void editarTipoUsuario(TipoUsuario tipoUsuario);

    public void borrarTipoUsuario(TipoUsuario tipoUsuario);

    public TipoUsuario consultarTipoUsuarioPorID(BigInteger idRegistro);

    public List<TipoUsuario> consultarTiposUsuariosRegistrados();

}
