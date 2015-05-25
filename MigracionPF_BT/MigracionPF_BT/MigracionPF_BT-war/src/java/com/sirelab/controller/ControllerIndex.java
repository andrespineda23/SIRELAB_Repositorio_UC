package com.sirelab.controller;

import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Controlador : ControllerIndex Este controlador es el encargado del index/home
 * del sistema de información
 *
 * @author ANDRES PINEDA
 * @version 1.0
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
    //
    private UsuarioLogin usuarioLoginSistema;

    private Logger logger = Logger.getLogger("ControllerIndex");
    private boolean validacionesUsuario, validacionesContrasenia;
    private String mensajeFormulario;
    private boolean validacionesCorreo, validacionesID;
    private String mensajeFormularioRecupera;
    private String paginaRecuperacion;

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
                    Persona personaRecuperada = gestionarLoginSistemaBO.configurarContraseñaPersona(recuperar);
                    enviarCorreoRecuperacion(personaRecuperada);
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
            System.out.println("Error recuperarContraseñaUsuario ControllerIndex : " + e.toString());
            paginaRecuperacion = "";
        }
    }

    public String retornarPaginaRecuperacion() {
        recuperarContraseñaUsuario();
        return paginaRecuperacion;
    }

    /**
     * Metodo encargado de enviar via e-mail al usuario que solicito la
     * recuperación de la contraseña un correo con la información de su nueva
     * contraseña
     *
     * @param personaRecuperada Usuario que solicita el cambio de contraseña
     */
    public void enviarCorreoRecuperacion(Persona personaRecuperada) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("proyecto.sirelab@gmail.com", "ucentral");
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("proyecto.sirelab@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(personaRecuperada.getEmailpersona()));
            message.setSubject("Recuperación de Contraseña - SIRELAB UC");
            message.setText("Se solicito la recuperación de la contraseña de SIRELAB, la contraseña restaurada es la siguiente: " + personaRecuperada.getUsuario().getPasswordusuario() + " . Se solicita ingresar al sistema y cambiar la contraseña.");

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

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
        if ((Utilidades.validarNulo(correoRecuperacion)) && (!correoRecuperacion.isEmpty())) {
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
        if ((Utilidades.validarNulo(identificacionRecuperacion)) && (!identificacionRecuperacion.isEmpty())) {
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

    public boolean validarProcesoLogin() {
        boolean retorno = true;
        if (validacionesContrasenia == false) {
            retorno = false;
        }
        if (validacionesUsuario == false) {
            retorno = false;
        }
        return retorno;
    }

    public void ingresarAlSistema() {
        if (validarProcesoLogin() == true) {
            loginUsuario();
        } else {
            mensajeFormulario = "Existen errores en el proceso de ingreso.";
            paginaSiguiente = "";
        }
    }

    /**
     * Metodo encargado del login al sistema de información
     */
    public void loginUsuario() {
        paginaSiguiente = null;
        try {
            Persona personaLogin = null;
            if ((usuarioLogin != null && (!usuarioLogin.isEmpty())) && (passwordLogin != null && (!passwordLogin.isEmpty()))) {
                personaLogin = gestionarLoginSistemaBO.obtenerPersonaLogin(usuarioLogin, passwordLogin);
                usuarioLogin = null;
                passwordLogin = null;
                if (personaLogin != null) {
                    String nombreTipoUsuario = personaLogin.getUsuario().getTipousuario().getNombretipousuario();
                    usuarioLoginSistema = new UsuarioLogin();
                    if ("ADMINISTRADOR".equals(nombreTipoUsuario)) {
                        usuarioLoginSistema.setNombreTipoUsuario(nombreTipoUsuario);
                        usuarioLoginSistema.setIdUsuarioLogin(personaLogin.getIdpersona());
                        usuarioLoginSistema.setUserUsuario(personaLogin.getUsuario().getNombreusuario());
                        paginaSiguiente = "inicio_administrador";
                    } else {
                        Object usuarioFinal = gestionarLoginSistemaBO.obtenerUsuarioFinalLogin(nombreTipoUsuario, personaLogin.getIdpersona());
                        if ("ESTUDIANTE".equals(nombreTipoUsuario)) {
                            Estudiante estudianteLogin = (Estudiante) usuarioFinal;
                            usuarioLoginSistema.setNombreTipoUsuario(nombreTipoUsuario);
                            usuarioLoginSistema.setIdUsuarioLogin(estudianteLogin.getIdestudiante());
                            usuarioLoginSistema.setUserUsuario(estudianteLogin.getPersona().getUsuario().getNombreusuario());
                            paginaSiguiente = "faces/paginas_estudiante/inicio_estudiante.xhtml";
                        } else {
                            if ("DOCENTE".equals(nombreTipoUsuario)) {
                                Docente docenteLogin = (Docente) usuarioFinal;
                                usuarioLoginSistema.setNombreTipoUsuario(nombreTipoUsuario);
                                usuarioLoginSistema.setIdUsuarioLogin(docenteLogin.getIddocente());
                                usuarioLoginSistema.setUserUsuario(docenteLogin.getPersona().getUsuario().getNombreusuario());
                                paginaSiguiente = "faces/paginas_docente/inicio_docente.xhtml";
                            } else {
                                if ("ENCARGADOLAB".equals(nombreTipoUsuario)) {
                                    EncargadoLaboratorio encargadoLabLogin = (EncargadoLaboratorio) usuarioFinal;
                                    usuarioLoginSistema.setNombreTipoUsuario(nombreTipoUsuario);
                                    usuarioLoginSistema.setIdUsuarioLogin(encargadoLabLogin.getIdencargadolaboratorio());
                                    usuarioLoginSistema.setUserUsuario(encargadoLabLogin.getPersona().getUsuario().getNombreusuario());
                                    paginaSiguiente = "faces/paginas_encargadolab/inicio_encargadolab.xhtml";
                                } else {
                                    EntidadExterna entidadExternaLogin = (EntidadExterna) usuarioFinal;
                                    usuarioLoginSistema.setNombreTipoUsuario(nombreTipoUsuario);
                                    usuarioLoginSistema.setIdUsuarioLogin(entidadExternaLogin.getIdentidadexterna());
                                    usuarioLoginSistema.setUserUsuario(entidadExternaLogin.getPersona().getUsuario().getNombreusuario());
                                    paginaSiguiente = "faces/paginas_entidad/inicio_entidad.xhtml";
                                }
                            }
                        }
                    }
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessionUsuario", usuarioLoginSistema);
                    //httpServletRequest.getSession().setAttribute("sessionUsuario", usuarioLoginSistema);
                } else {
                    mensajeFormulario = "El usuario ingresado no existe.";
                }
            }
        } catch (NoResultException nre) {
            System.out.println("NoResultException loginUsuario ControllerIndex : " + nre.toString());
        } catch (Exception e) {
            System.out.println("Exception loginUsuario ControllerIndex : " + e.toString());
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
        if (null == usuarioLoginSistema) {
            paginaSiguiente = "";
            return paginaSiguiente;
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
        if ((Utilidades.validarNulo(usuarioLogin)) && (!usuarioLogin.isEmpty())) {
            validacionesUsuario = true;
        } else {
            FacesContext.getCurrentInstance().addMessage("form:formLogin:usuarioLogin", new FacesMessage("El usuario es obligatorio."));
            validacionesUsuario = false;
        }
    }

    public void validarPasswordLogin() {
        if ((Utilidades.validarNulo(passwordLogin)) && (!passwordLogin.isEmpty())) {
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
