/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller;

import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerCambiarContrasenia implements Serializable {

    @EJB
    GestionarLoginSistemaBOInterface gestionarLoginSistemaBO;

    private String mensajeFormulario;
    private String colorMensaje;
    private String contraseniaAntigua, contraseniaNueva, contraseniaNueva2;
    private String paginaAnterior;
    private Usuario usuario;

    public ControllerCambiarContrasenia() {
    }

    @PostConstruct
    public void init() {
        mensajeFormulario = "black";
        colorMensaje = "N/A";
        contraseniaAntigua = null;
        contraseniaNueva = null;
        contraseniaNueva2 = null;
    }

    public void recibirPaginaAnterior(String page) {
        paginaAnterior = page;
    }

    private boolean validarCampos() {
        boolean retorno = true;
        if (!Utilidades.validarNulo(contraseniaAntigua) || (contraseniaAntigua.isEmpty()) || (contraseniaAntigua.trim().length() == 0)) {
            retorno = false;
        }
        if (!Utilidades.validarNulo(contraseniaNueva) || (contraseniaNueva.isEmpty()) || (contraseniaNueva.trim().length() == 0)) {
            retorno = false;
        }
        if (!Utilidades.validarNulo(contraseniaNueva2) || (contraseniaNueva2.isEmpty()) || (contraseniaNueva2.trim().length() == 0)) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarContraseniaAntigua() {
        boolean retorno = false;
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        usuario = gestionarLoginSistemaBO.obtenerUsuarioCambioContrasenia(usuarioLoginSistema.getNombreTipoUsuario(), usuarioLoginSistema.getIdUsuarioLogin());
        EncriptarContrasenia obj = new EncriptarContrasenia();
        String nueva = obj.encriptarContrasenia(contraseniaAntigua);
        if (usuario.getPasswordusuario().equalsIgnoreCase(nueva)) {
            retorno = true;
        }
        return retorno;
    }

    private boolean validarNuevaContrasenia() {
        boolean retorno = false;
        if (contraseniaNueva.equalsIgnoreCase(contraseniaNueva2)) {
            retorno = true;
        }
        return retorno;
    }

    private boolean validarContrasenias() {
        boolean retorno = true;
        if (contraseniaNueva.equalsIgnoreCase(contraseniaAntigua)) {
            retorno = false;
        }
        return retorno;
    }

    public void cambiarContrasenia() {
        if (validarCampos() == true) {
            if (validarContraseniaAntigua() == true) {
                if (validarNuevaContrasenia() == true) {
                    if (validarContrasenias() == true) {
                        EncriptarContrasenia obj = new EncriptarContrasenia();
                        String nueva = obj.encriptarContrasenia(contraseniaNueva);
                        usuario.setPasswordusuario(nueva);
                        gestionarLoginSistemaBO.actualizarUsuario(usuario);
                        colorMensaje = "green";
                        mensajeFormulario = "La contraseña fue modificada con éxito.";
                    } else {
                        colorMensaje = "#FF0000";
                        mensajeFormulario = "La nueva contraseña es igual que la contraseña antigua, seleccione una nueva contraseña.";
                    }
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "Las nuevas contraseñas no coinciden, por favor corregir para continuar con el proces.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "La antigua contraseña no es la asignada para el usuario, por favor corregir.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Alguno de los campos se encuentra vacio, por favor corregir para continuar con el proceso.";
        }
    }

    public void limpiarProceso() {
        mensajeFormulario = "black";
        colorMensaje = "N/A";
        contraseniaAntigua = null;
        contraseniaNueva = null;
        contraseniaNueva2 = null;
        usuario = null;
    }

    public String cancelarProceso() {
        limpiarProceso();
        return paginaAnterior;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public String getContraseniaAntigua() {
        return contraseniaAntigua;
    }

    public void setContraseniaAntigua(String contraseniaAntigua) {
        this.contraseniaAntigua = contraseniaAntigua;
    }

    public String getContraseniaNueva() {
        return contraseniaNueva;
    }

    public void setContraseniaNueva(String contraseniaNueva) {
        this.contraseniaNueva = contraseniaNueva;
    }

    public String getContraseniaNueva2() {
        return contraseniaNueva2;
    }

    public void setContraseniaNueva2(String contraseniaNueva2) {
        this.contraseniaNueva2 = contraseniaNueva2;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

}
