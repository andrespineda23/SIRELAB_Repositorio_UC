/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresEdificioBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoPorEdificio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.TipoPerfil;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;

/**
 *
 * @author AndresPineda
 */
public class AdministrarPerfil implements Serializable {

    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarValidadorTipoUsuario;
    @EJB
    AdministrarAdministradoresEdificioBOInterface administrarAdministradoresEdificioBO;

    private static AdministrarPerfil instance;

    private AdministrarPerfil() {
    }

    public static AdministrarPerfil getInstance() {
        if (instance == null) {
            instance = new AdministrarPerfil();
        }
        return instance;
    }

    public Map<String, Object> validarSesionAdicionales(String nombre, String codigo) {
        Map<String, Object> lista = new HashMap<String, Object>();
        if ("DEPARTAMENTO".equalsIgnoreCase(nombre)) {
            Departamento registro = administrarValidadorTipoUsuario.obtenerDepartamentoPorCodigo(codigo);
            if (null != registro) {
                lista.put("DEPARTAMENTO", registro);
            }
        }
        if ("AREAPROFUNDIZACION".equalsIgnoreCase(nombre)) {
            AreaProfundizacion registro = administrarValidadorTipoUsuario.obtenerAreaProfundizacionPorCodigo(codigo);
            if (null != registro) {
                lista.put("AREAPROFUNDIZACION", registro);
            }
        }
        if ("LABORATORIO".equalsIgnoreCase(nombre)) {
            Laboratorio registro = administrarValidadorTipoUsuario.obtenerLaboratorioPorCodigo(codigo);
            if (null != registro) {
                lista.put("LABORATORIO", registro);
            }
        }
        return lista;
    }

    public TipoPerfil buscarTipoPerfilPorIDEncargado(BigInteger idRegistro) {
        System.out.println("idRegistro: "+idRegistro);
        TipoPerfil perfil = administrarValidadorTipoUsuario.buscarTipoPerfilPorIDEncargado(idRegistro);
        System.out.println("perfil: "+perfil);
        return perfil;
    }

    public Edificio buscarEdificioPorIdEncargadoEdificio(BigInteger idRegistro) {
        EncargadoPorEdificio obj = administrarAdministradoresEdificioBO.obtenerEncargadoPorEdificioPorIDEncargadoPorEdificio(idRegistro);
        if (null != obj) {
            return obj.getEdificio();
        } else {
            return null;
        }
    }
}
