/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.paginasiniciales;

import com.sirelab.ayuda.AdministrarPerfil;
import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.utilidades.UsuarioLogin;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerPaginasIniciales implements Serializable {

    //
    @EJB
    GestionarLoginSistemaBOInterface gestionarLoginSistemaBO;
    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarValidadorTipoUsuario;

    private UsuarioLogin usuarioLoginSistema;
    private TipoPerfil tipoPerfil;

    public ControllerPaginasIniciales() {
    }

    @PostConstruct
    public void init() {
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
    }

    /**
     * Metodo encargado de cerrar la sesion de un usuario y regresarlo al index
     * del proyecto
     *
     * @return Pagina Index
     * @throws IOException
     * @throws ServletException
     */
    public String cerrarSession() throws IOException, ServletException {
        usuarioLoginSistema = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        httpServletRequest.logout();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            if (null != session) {
                //session.invalidate();
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar la sesion del usuario : " + e.toString());
        }
        return "index";
    }

    public boolean inactivarOpcLaboratorio() {
        boolean retorno = false;
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            boolean perfilConsulta = validarSesionConsulta();
            if (perfilConsulta == false) {
                Map<String, Object> datosPerfil = validarSesionAdicionales(tipoPerfil.getNombre(), tipoPerfil.getCodigoregistro());
                if (null != datosPerfil) {
                    if (datosPerfil.containsKey("LABORATORIO")) {
                        Laboratorio parametro = (Laboratorio) datosPerfil.get("LABORATORIO");
                        retorno = true;
                    }
                }
            }
        }
        return retorno;
    }

    private Map<String, Object> validarSesionAdicionales(String nombre, String codigo) {
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

    private boolean validarSesionConsulta() {
        boolean retorno = false;
        tipoPerfil = administrarValidadorTipoUsuario.buscarTipoPerfilPorIDEncargado(usuarioLoginSistema.getIdUsuarioLogin());
        if (null != tipoPerfil) {
            if ("CONSULTA".equalsIgnoreCase(tipoPerfil.getNombre())) {
                retorno = true;
            }
        }
        return retorno;
    }

    public UsuarioLogin getUsuarioLoginSistema() {
        return usuarioLoginSistema;
    }

    public void setUsuarioLoginSistema(UsuarioLogin usuarioLoginSistema) {
        this.usuarioLoginSistema = usuarioLoginSistema;
    }

}
