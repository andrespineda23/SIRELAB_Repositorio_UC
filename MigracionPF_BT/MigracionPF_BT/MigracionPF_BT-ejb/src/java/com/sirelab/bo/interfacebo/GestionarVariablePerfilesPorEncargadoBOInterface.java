/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo;

import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.PerfilPorEncargado;
import com.sirelab.entidades.TipoPerfil;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariablePerfilesPorEncargadoBOInterface {

    public List<TipoPerfil> consultarTiposPerfilesRegistrados();

    public List<Departamento> consultarDepartamentosRegistrados();

    public List<Laboratorio> consultarLaboratoriosRegistrados();

    public void crearPerfilPorEncargado(PerfilPorEncargado perfilPorEncargado);

    public void modificarPerfilPorEncargado(PerfilPorEncargado perfilPorEncargado);

    public List<PerfilPorEncargado> consultarPerfilesPorIDPerfil(BigInteger perfil);

}
