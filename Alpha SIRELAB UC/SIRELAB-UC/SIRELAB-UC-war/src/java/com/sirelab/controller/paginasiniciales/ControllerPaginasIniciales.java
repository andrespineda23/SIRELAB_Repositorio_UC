/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.paginasiniciales;

import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.utilidades.UsuarioLogin;
import java.io.IOException;
import java.io.Serializable;
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
import org.apache.log4j.Logger;

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

    static Logger logger = Logger.getLogger(ControllerPaginasIniciales.class);

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
            logger.error("Error al cerrar la sesion del usuario : " + e.toString(), e);
        }
        return "index";
    }

    public boolean inactivarOpcLaboratorio() {
        boolean retorno = false;
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        return retorno;
    }

    public UsuarioLogin getUsuarioLoginSistema() {
        return usuarioLoginSistema;
    }

    public void setUsuarioLoginSistema(UsuarioLogin usuarioLoginSistema) {
        this.usuarioLoginSistema = usuarioLoginSistema;
    }

}
