/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.PerfilPorEncargado;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface PerfilPorEncargadoDAOInterface {

    public void crearPerfilPorEncargado(PerfilPorEncargado perfil);

    public void editarPerfilPorEncargado(PerfilPorEncargado perfil);

    public void eliminarPerfilPorEncargado(PerfilPorEncargado perfil);

    public List<PerfilPorEncargado> consultarPerfilesPorEncargado();
    
    public List<PerfilPorEncargado> consultarPerfilesPorEncargadoPorTipoPerfil(BigInteger perfil);

    public PerfilPorEncargado buscarPerfilPorEncargadoPorID(BigInteger idRegistro);
}
