/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposPerfilesBOInterface;
import com.sirelab.dao.interfacedao.AreaProfundizacionDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.TipoPerfilDAOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
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
public class GestionarVariableTiposPerfilesBO implements GestionarVariableTiposPerfilesBOInterface {

    @EJB
    TipoPerfilDAOInterface tipoPerfilDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    AreaProfundizacionDAOInterface areaProfundizacionDAO;

    @Override
    public Departamento consultarDepartamentoPorCodigo(String codigo) {
        try {
            Departamento registro = departamentoDAO.buscarDepartamentoPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO consultarDepartamentoPorCodigo : " + e.toString());
            return null;
        }
    }

    @Override
    public Laboratorio consultaLaboratorioPorCodigo(String codigo) {
        try {
            Laboratorio registro = laboratorioDAO.buscarLaboratorioPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO consultaLaboratorioPorCodigo : " + e.toString());
            return null;
        }
    }

    @Override
    public AreaProfundizacion consultarAreaProfundizacionPorCodigo(String codigo) {
        try {
            AreaProfundizacion registro = areaProfundizacionDAO.buscarAreaProfundizacionPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO consultarAreaProfundizacionPorCodigo : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearTipoPerfil(TipoPerfil tipoPerfil) {
        try {
            tipoPerfilDAO.crearTipoPerfil(tipoPerfil);;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO crearTipoPerfil : " + e.toString());
        }
    }

    @Override
    public void editarTipoPerfil(TipoPerfil tipoPerfil) {
        try {
            tipoPerfilDAO.editarTipoPerfil(tipoPerfil);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO editarTipoPerfil : " + e.toString());
        }
    }

    @Override
    public void borrarTipoPerfil(TipoPerfil tipoPerfil) {
        try {
            tipoPerfilDAO.eliminarTipoPerfil(tipoPerfil);
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO borrarTipoPerfil : " + e.toString());
        }
    }

    @Override
    public TipoPerfil consultarTipoPerfilPorID(BigInteger idRegistro) {
        try {
            TipoPerfil registro = tipoPerfilDAO.buscarTipoPerfilPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO consultarTipoPerfilPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public TipoPerfil consultarTipoPerfilPorCodigo(String codigo) {
        try {
            TipoPerfil registro = tipoPerfilDAO.buscarTipoPerfilPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO consultarTipoPerfilPorCodigo : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TipoPerfil> consultarTiposPerfilesRegistrados() {
        try {
            List<TipoPerfil> lista = tipoPerfilDAO.consultarTiposPerfiles();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarVariableTiposPerfilesBO borrarTipoPerfil : " + e.toString());
            return null;
        }
    }
}
