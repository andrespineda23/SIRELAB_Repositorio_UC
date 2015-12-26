/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.TipoPerfil;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariableTiposPerfilesBOInterface {

    public void crearTipoPerfil(TipoPerfil tipoPerfil);

    public void editarTipoPerfil(TipoPerfil tipoPerfil);

    public void borrarTipoPerfil(TipoPerfil tipoPerfil);

    public List<TipoPerfil> consultarTiposPerfilesRegistrados();

    public TipoPerfil consultarTipoPerfilPorID(BigInteger idRegistro);

    public TipoPerfil consultarTipoPerfilPorCodigo(String codigo);

    public Departamento consultarDepartamentoPorCodigo(String codigo);

    public Laboratorio consultaLaboratorioPorCodigo(String codigo);
}
