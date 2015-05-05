/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.TipoPerfil;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface TipoPerfilDAOInterface {

    public void crearTipoPerfil(TipoPerfil tipoPerfil);

    public void editarTipoPerfil(TipoPerfil tipoPerfil);

    public void eliminarTipoPerfil(TipoPerfil tipoPerfil);

    public List<TipoPerfil> consultarTiposPerfiles();

    public TipoPerfil buscarTipoPerfilPorID(BigInteger idRegistro);

    public TipoPerfil buscarTipoPerfilPorCodigo(String codigo);
}
