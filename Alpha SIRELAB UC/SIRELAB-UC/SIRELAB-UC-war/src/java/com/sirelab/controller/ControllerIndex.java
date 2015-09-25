package com.sirelab.controller;

import com.sirelab.ayuda.EnvioCorreo;
import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.entidades.AdministradorEdificio;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
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
import javax.persistence.NoResultException;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Controlador : ControllerIndex Este controlador es el encargado del index/home
 * del sistema de información
 *
 * @author ANDRES PINEDA
 * @version 1.1
 */
@ManagedBean
@SessionScoped
public class ControllerIndex implements Serializable {

    //
    @EJB
    GestionarLoginSistemaBOInterface gestionarLoginSistemaBO;
    //
    private String correoRecuperacion, identificacionRecuperacion;
    private String usuarioLogin, passwordLogin;
    private String paginaSiguiente;
    private int numeroConexiones;
    //
    private UsuarioLogin usuarioLoginSistema;

    private boolean validacionesUsuario, validacionesContrasenia;
    private String mensajeFormulario;
    private boolean validacionesCorreo, validacionesID;
    private String mensajeFormularioRecupera;
    private String paginaRecuperacion;

    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerIndex() {
    }

    @PostConstruct
    public void init() {
        validacionesUsuario = false;
        validacionesContrasenia = false;
        mensajeFormulario = "";
        validacionesCorreo = false;
        validacionesID = false;
        mensajeFormularioRecupera = "";
        usuarioLogin = null;
        passwordLogin = null;
        correoRecuperacion = null;
        identificacionRecuperacion = null;
        paginaRecuperacion = "";
        BasicConfigurator.configure();
    }

    public void validarUsuario() {
        if (usuarioLogin != null && (!usuarioLogin.isEmpty()) && (usuarioLogin.trim().length() > 0)) {
            validacionesUsuario = true;
        } else {
            validacionesUsuario = false;
        }
    }

    public void validarContrasenia() {
        if (passwordLogin != null && (!passwordLogin.isEmpty()) && (passwordLogin.trim().length() > 0)) {
            validacionesContrasenia = true;
        } else {
            validacionesContrasenia = false;
        }
    }

    public boolean validarProcesoRecuperacion() {
        boolean retorno = true;
        if (validacionesCorreo == false) {
            retorno = false;
        }
        if (validacionesID == false) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de recuperar la contraseña de un usuario registrado en
     * el sistema de información
     */
    public void recuperarContraseñaUsuario() {
        try {
            if (validarProcesoRecuperacion() == true) {
                Persona recuperar = gestionarLoginSistemaBO.obtenerPersonaRecuperarContrasenia(correoRecuperacion + "@ucentral.edu.co", identificacionRecuperacion);
                correoRecuperacion = null;
                identificacionRecuperacion = null;
                if (recuperar != null) {
                    String nuevaContrasenia = gestionarLoginSistemaBO.generarNuevaContrasenia();
                    EncriptarContrasenia obj = new EncriptarContrasenia();
                    recuperar.getUsuario().setPasswordusuario(obj.encriptarContrasenia(nuevaContrasenia));
                    Persona personaRecuperada = gestionarLoginSistemaBO.configurarContraseñaPersona(recuperar);
                    EnvioCorreo correo = new EnvioCorreo();
                    correo.enviarCorreoRecuperacion(personaRecuperada, nuevaContrasenia);
                    mensajeFormularioRecupera = "Contraseña recuperada.";
                    paginaRecuperacion = "recuperacionexitosa";
                } else {
                    mensajeFormularioRecupera = "El usuario ingresado no existe.";
                    paginaRecuperacion = "";
                }
            } else {
                mensajeFormularioRecupera = "Existen errores en el proceso de recuperación.";
                paginaRecuperacion = "";
            }
        } catch (Exception e) {
            System.out.println("Error ControllerIndex recuperarContraseñaUsuario :" + e.toString());
            paginaRecuperacion = "";
        }
    }

    public String retornarPaginaRecuperacion() {
        recuperarContraseñaUsuario();
        return paginaRecuperacion;
    }

    /**
     * Metodo encargado de restaurar la información del proceso de recuperar
     * contraseña
     */
    public void cancelarRecuperarContrasenia() {
        correoRecuperacion = null;
        identificacionRecuperacion = null;
    }

    public void validarCorreoRecuperacion() {
        if ((Utilidades.validarNulo(correoRecuperacion)) && (!correoRecuperacion.isEmpty()) && (correoRecuperacion.trim().length() > 0)) {
            String correo = correoRecuperacion + "@ucentral.edu.co";
            if (Utilidades.validarCorreoElectronico(correo) == false) {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:formRecuperacion:correoRecuperacion", new FacesMessage("El correo esta incorrecto."));
            } else {
                validacionesCorreo = true;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:formRecuperacion:correoRecuperacion", new FacesMessage("El correo es obligatorio."));
            validacionesCorreo = false;
        }
    }

    public void validarIDRecuperacion() {
        if ((Utilidades.validarNulo(identificacionRecuperacion)) && (!identificacionRecuperacion.isEmpty()) && (identificacionRecuperacion.trim().length() > 0)) {
            if (Utilidades.validarCaracteresAlfaNumericos(identificacionRecuperacion) == false) {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:formRecuperacion:identificacionRecuperacion", new FacesMessage("La identificación ingresada es incorrecta."));
            } else {
                validacionesID = true;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:formRecuperacion:identificacionRecuperacion", new FacesMessage("La identificación es obligatoria."));
            validacionesID = false;
        }
    }

    private boolean validarProcesoLogin() {
        boolean retorno = true;
        if (validacionesContrasenia == false) {
            retorno = false;
        }
        if (validacionesUsuario == false) {
            retorno = false;
        }
        return retorno;
    }

    private void ingresarAlSistema() {
        if (validarProcesoLogin() == true) {
            loginUsuario();
        } else {
            mensajeFormulario = "Existen errores en el proceso de ingreso.";
            paginaSiguiente = "";
        }
    }

    /**
     * Metodo encargado del login al sistema de información idTipoUsuario : 1-
     * Administrador / 2- Docente / 3- Estudiante / 4-EncargadoLab /
     * 5-EntidadExterna
     *
     */
    private void loginUsuario() {
        paginaSiguiente = null;
        try {
            Persona personaLogin = null;
            if ((usuarioLogin != null && (!usuarioLogin.isEmpty()) && (usuarioLogin.trim().length() > 0)) && (passwordLogin != null && (!passwordLogin.isEmpty()) && (passwordLogin.trim().length() > 0))) {
                personaLogin = gestionarLoginSistemaBO.obtenerPersonaLogin(usuarioLogin, passwordLogin);
                usuarioLogin = null;
                passwordLogin = null;
                if (personaLogin != null) {
                    BigInteger idTipoUsuario = personaLogin.getUsuario().getTipousuario().getIdtipousuario();
                    usuarioLoginSistema = new UsuarioLogin();
                    BigInteger secuenciaLogin = new BigInteger("1");
                    if (secuenciaLogin.equals(idTipoUsuario)) {
                        usuarioLoginSistema.setNombreTipoUsuario("ADMINISTRADOR");
                        usuarioLoginSistema.setIdUsuarioLogin(personaLogin.getIdpersona());
                        usuarioLoginSistema.setUserUsuario(personaLogin.getUsuario().getNombreusuario());
                        paginaSiguiente = "inicioadministrador";
                    } else {
                        secuenciaLogin = new BigInteger("3");
                        Object usuarioFinal = gestionarLoginSistemaBO.obtenerUsuarioFinalLogin(idTipoUsuario, personaLogin.getIdpersona());
                        if (secuenciaLogin.equals(idTipoUsuario)) {
                            Estudiante estudianteLogin = (Estudiante) usuarioFinal;
                            usuarioLoginSistema.setNombreTipoUsuario("ESTUDIANTE");
                            usuarioLoginSistema.setIdUsuarioLogin(estudianteLogin.getIdestudiante());
                            usuarioLoginSistema.setUserUsuario(estudianteLogin.getPersona().getUsuario().getNombreusuario());
                            paginaSiguiente = "inicioestudiante";
                        } else {
                            secuenciaLogin = new BigInteger("2");
                            if (secuenciaLogin.equals(idTipoUsuario)) {
                                Docente docenteLogin = (Docente) usuarioFinal;
                                usuarioLoginSistema.setNombreTipoUsuario("DOCENTE");
                                usuarioLoginSistema.setIdUsuarioLogin(docenteLogin.getIddocente());
                                usuarioLoginSistema.setUserUsuario(docenteLogin.getPersona().getUsuario().getNombreusuario());
                                paginaSiguiente = "iniciodocente";
                            } else {
                                secuenciaLogin = new BigInteger("4");
                                if (secuenciaLogin.equals(idTipoUsuario)) {
                                    EncargadoLaboratorio encargadoLabLogin = (EncargadoLaboratorio) usuarioFinal;
                                    usuarioLoginSistema.setNombreTipoUsuario("ENCARGADOLAB");
                                    usuarioLoginSistema.setIdUsuarioLogin(encargadoLabLogin.getIdencargadolaboratorio());
                                    usuarioLoginSistema.setUserUsuario(encargadoLabLogin.getPersona().getUsuario().getNombreusuario());
                                    paginaSiguiente = "iniciolaboratorista";
                                } else {
                                    secuenciaLogin = new BigInteger("5");
                                    if (secuenciaLogin.equals(idTipoUsuario)) {
                                        EntidadExterna entidadExternaLogin = (EntidadExterna) usuarioFinal;
                                        usuarioLoginSistema.setNombreTipoUsuario("ENTIDADEXTERNA");
                                        usuarioLoginSistema.setIdUsuarioLogin(entidadExternaLogin.getIdentidadexterna());
                                        usuarioLoginSistema.setUserUsuario(entidadExternaLogin.getPersona().getUsuario().getNombreusuario());
                                        paginaSiguiente = "inicioentidadexterna";
                                    } else {
                                        AdministradorEdificio administradorEdificio = (AdministradorEdificio) usuarioFinal;
                                        usuarioLoginSistema.setNombreTipoUsuario("ADMINISTRADOREDIFICIO");
                                        usuarioLoginSistema.setIdUsuarioLogin(administradorEdificio.getIdadministradoredificio());
                                        usuarioLoginSistema.setUserUsuario(administradorEdificio.getPersona().getUsuario().getNombreusuario());
                                        paginaSiguiente = "inicioadministradoredificio";
                                    }
                                }
                            }
                        }
                    }
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionUsuario", usuarioLoginSistema);
                    numeroConexiones = personaLogin.getUsuario().getNumeroconexiones();
                    personaLogin.getUsuario().setNumeroconexiones(numeroConexiones + 1);
                    gestionarLoginSistemaBO.actualizarUsuario(personaLogin.getUsuario());
                } else {
                    mensajeFormulario = "El usuario ingresado no existe.";
                }
            }
        } catch (NoResultException nre) {
            System.out.println("NoResultException loginUsuario ControllerIndex : " + nre.toString());
        } catch (Exception e) {
            System.out.println("Error ControllerIndex loginUsuario: " + e.toString());
        }
    }

    /**
     * Metodo encargado de direccionar a la pagina respectiva del usuario que
     * realiza el login dentro del sistema
     *
     * @return
     */
    public String retornarPaginaSiguiente() {
        ingresarAlSistema();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (null != session) {
            session.setAttribute("sessionUsuario", usuarioLoginSistema);
        }
        if (null == usuarioLoginSistema) {
            paginaSiguiente = "";
            return paginaSiguiente;
        }
        if (numeroConexiones == 1) {
            //paginaSiguiente = "cambiarcontrasenia";
            System.out.println("cambiarcontrasenia");
            //return paginaSiguiente;
        }
        return paginaSiguiente;
    }

    public void limpiarProcesoControlador() {
        usuarioLogin = null;
        passwordLogin = null;
        correoRecuperacion = null;
        identificacionRecuperacion = null;
        validacionesUsuario = false;
        validacionesContrasenia = false;
        mensajeFormulario = "";
        validacionesCorreo = false;
        validacionesID = false;
        mensajeFormularioRecupera = "";
    }

    public void validarUsuarioLogin() {
        if ((Utilidades.validarNulo(usuarioLogin)) && (!usuarioLogin.isEmpty()) && (usuarioLogin.trim().length() > 0)) {
            validacionesUsuario = true;
        } else {
            FacesContext.getCurrentInstance().addMessage("form:formLogin:usuarioLogin", new FacesMessage("El usuario es obligatorio."));
            validacionesUsuario = false;
        }
    }

    public void validarPasswordLogin() {
        if ((Utilidades.validarNulo(passwordLogin)) && (!passwordLogin.isEmpty()) && (passwordLogin.trim().length() > 0)) {
            validacionesContrasenia = true;
        } else {
            FacesContext.getCurrentInstance().addMessage("form:formLogin:inputContrasenia", new FacesMessage("La contraseña es obligatoria."));
            validacionesContrasenia = false;
        }
    }

    //GET-SET
    public String getCorreoRecuperacion() {
        return correoRecuperacion;
    }

    public void setCorreoRecuperacion(String correoRecuperacion) {
        this.correoRecuperacion = correoRecuperacion;
    }

    public String getIdentificacionRecuperacion() {
        return identificacionRecuperacion;
    }

    public void setIdentificacionRecuperacion(String identificacionRecuperacion) {
        this.identificacionRecuperacion = identificacionRecuperacion;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }

    public String getPaginaSiguiente() {
        return paginaSiguiente;
    }

    public void setPaginaSiguiente(String paginaSiguiente) {
        this.paginaSiguiente = paginaSiguiente;
    }

    public UsuarioLogin getUsuarioLoginSistema() {
        return usuarioLoginSistema;
    }

    public void setUsuarioLoginSistema(UsuarioLogin usuarioLoginSistema) {
        this.usuarioLoginSistema = usuarioLoginSistema;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getMensajeFormularioRecupera() {
        return mensajeFormularioRecupera;
    }

    public void setMensajeFormularioRecupera(String mensajeFormularioRecupera) {
        this.mensajeFormularioRecupera = mensajeFormularioRecupera;
    }

}
