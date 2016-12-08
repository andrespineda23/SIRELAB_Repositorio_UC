/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.paginasiniciales;

import com.sirelab.ayuda.AyudaReservaSala;
import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private UsuarioLogin usuarioLoginSistema;
    private BigInteger idReserva;
    static Logger logger = Logger.getLogger(ControllerPaginasIniciales.class);
    private String numeroReserva;

    public ControllerPaginasIniciales() {
    }

    @PostConstruct
    public void init() {
        numeroReserva = null;
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
    }

    private void consultarReservaPorCodigo() {
        if (Utilidades.validarNulo(numeroReserva) && !numeroReserva.isEmpty() && numeroReserva.trim().length() > 0) {
            ReservaSala reserva = administrarReservasBO.consultarReservaSalaPorCodigo(numeroReserva);
            if (null != reserva) {
                idReserva = reserva.getIdreservasala();
                AyudaReservaSala.getInstance().setReservaSala(reserva);
                numeroReserva = "";
            } else {
                idReserva = null;
                FacesContext.getCurrentInstance().addMessage("form:numeroReserva", new FacesMessage("La reserva consultada no existe."));
            }
        } else {
            idReserva = null;
            FacesContext.getCurrentInstance().addMessage("form:numeroReserva", new FacesMessage("El campo es obligatorio."));
        }
    }

    public String paginaConsultaReserva() {
        String pagina = "";
        consultarReservaPorCodigo();
        if (null != idReserva) {
            pagina = "detallesreservasala2";
        }
        idReserva = null;
        return pagina;
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

    public String getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(String numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

}
