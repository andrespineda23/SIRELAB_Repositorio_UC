/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.paginasiniciales;

import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.utilidades.UsuarioLogin;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
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

    private UsuarioLogin usuarioLoginSistema;

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
        cambiarEstadoEnLinea();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        httpServletRequest.logout();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            if (null != session) {
                //session.invalidate();
            } else {
                System.out.println("vacio logout");
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar la sesion del usuario : " + e.toString());
        }
        /*
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         System.out.println("session : " + session);
         session.ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
         ec.invalidateSession();
         */
        return "index";
    }

    private void cambiarEstadoEnLinea() {
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            gestionarLoginSistemaBO.cerrarSesionEnLineaUsuario(1, usuarioLoginSistema.getIdUsuarioLogin());
        } else {
            if ("DOCENTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                gestionarLoginSistemaBO.cerrarSesionEnLineaUsuario(2, usuarioLoginSistema.getIdUsuarioLogin());
            } else {
                if ("ESTUDIANTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                    gestionarLoginSistemaBO.cerrarSesionEnLineaUsuario(3, usuarioLoginSistema.getIdUsuarioLogin());
                } else {
                    if ("ENTIDADEXTERNA".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                        gestionarLoginSistemaBO.cerrarSesionEnLineaUsuario(4, usuarioLoginSistema.getIdUsuarioLogin());
                    } else {
                        gestionarLoginSistemaBO.cerrarSesionEnLineaUsuario(5, usuarioLoginSistema.getIdUsuarioLogin());
                    }
                }
            }
        }
    }

    public UsuarioLogin getUsuarioLoginSistema() {
        return usuarioLoginSistema;
    }

    public void setUsuarioLoginSistema(UsuarioLogin usuarioLoginSistema) {
        this.usuarioLoginSistema = usuarioLoginSistema;
    }

}
