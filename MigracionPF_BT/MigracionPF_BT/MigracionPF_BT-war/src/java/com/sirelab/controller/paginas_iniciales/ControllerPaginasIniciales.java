/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.paginas_iniciales;

import com.sirelab.utilidades.UsuarioLogin;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerPaginasIniciales implements Serializable {

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
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        httpServletRequest.logout();
        /*
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
         System.out.println("session : " + session);
         session.ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
         ec.invalidateSession();
         */
        return "index";
    }

    public UsuarioLogin getUsuarioLoginSistema() {
        return usuarioLoginSistema;
    }

    public void setUsuarioLoginSistema(UsuarioLogin usuarioLoginSistema) {
        this.usuarioLoginSistema = usuarioLoginSistema;
    }

}
