/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.GestionarVariablePerfilesPorEncargadoBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.PerfilPorEncargadoDAOInterface;
import com.sirelab.dao.interfacedao.TipoPerfilDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.PerfilPorEncargado;
import com.sirelab.entidades.TipoPerfil;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarVariablePerfilesPorEncargadoBO implements GestionarVariablePerfilesPorEncargadoBOInterface {

    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    PerfilPorEncargadoDAOInterface perfilPorEncargadoDAO;
    @EJB
    TipoPerfilDAOInterface tipoPerfilDAO;

    @Override
    public List<TipoPerfil> consultarTiposPerfilesRegistrados() {
        try {
            List<TipoPerfil> lista = tipoPerfilDAO.consultarTiposPerfiles();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePerfilesPorEncargadoBO consultarTiposPerfilesRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> consultarLaboratoriosRegistrados() {
        try {
            List<Laboratorio> lista = laboratorioDAO.consultarLaboratorios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePerfilesPorEncargadoBO consultarLaboratoriosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> consultarDepartamentosRegistrados() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePerfilesPorEncargadoBO consultarDepartamentosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearPerfilPorEncargado(PerfilPorEncargado perfilPorEncargado) {
        try {
            perfilPorEncargadoDAO.crearPerfilPorEncargado(perfilPorEncargado);
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePerfilesPorEncargadoBO consultarPerfilesPorIDPerfil : " + e.toString());
        }
    }

    @Override
    public void modificarPerfilPorEncargado(PerfilPorEncargado perfilPorEncargado) {
        try {
            perfilPorEncargadoDAO.editarPerfilPorEncargado(perfilPorEncargado);
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePerfilesPorEncargadoBO modificarPerfilPorEncargado : " + e.toString());
        }
    }

    @Override
    public List<PerfilPorEncargado> consultarPerfilesPorIDPerfil(BigInteger perfil) {
        try {
            List<PerfilPorEncargado> lista = null;
            if (null != perfil) {
                lista = perfilPorEncargadoDAO.consultarPerfilesPorEncargadoPorTipoPerfil(perfil);
            } else {
                lista = perfilPorEncargadoDAO.consultarPerfilesPorEncargado();
            }
            List<PerfilPorEncargado> listaRetorno = null;
            if (null != lista) {
                listaRetorno = cargarDatosStringPerfilPorEncargado(lista);
            }
            return listaRetorno;
        } catch (Exception e) {
            System.out.println("Error GestionarVariablePerfilesPorEncargadoBO consultarPerfilesPorIDPerfil : " + e.toString());
            return null;
        }
    }

    private List<PerfilPorEncargado> cargarDatosStringPerfilPorEncargado(List<PerfilPorEncargado> lista) {
        List<PerfilPorEncargado> registro = lista;
        for (int i = 0; i < lista.size(); i++) {
            if ("DEPARTAMENTO".equalsIgnoreCase(lista.get(i).getTipoperfil().getNombre())) {
                Departamento departamento = departamentoDAO.buscarDepartamentoPorID(lista.get(i).getIndicetabla());
                if (null != departamento) {
                    lista.get(i).setNombreRegistro(departamento.getNombredepartamento());
                } else {
                    lista.get(i).setNombreRegistro("");
                }
            } else {
                if ("LABORATORIO".equalsIgnoreCase(lista.get(i).getTipoperfil().getNombre())) {
                    Laboratorio laboratorio = laboratorioDAO.buscarLaboratorioPorID(lista.get(i).getIndicetabla());
                    if (null != laboratorio) {
                        lista.get(i).setNombreRegistro(laboratorio.getNombrelaboratorio());
                    } else {
                        lista.get(i).setNombreRegistro("");
                    }
                }
            }
        }
        return registro;
    }
}
