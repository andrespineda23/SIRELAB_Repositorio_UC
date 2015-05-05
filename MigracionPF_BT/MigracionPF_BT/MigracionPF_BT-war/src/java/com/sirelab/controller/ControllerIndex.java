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

    public ControllerIndex() {
    }

    @PostConstruct
    public void init() {
        usuarioLogin = null;
        passwordLogin = null;
    }

    /**
     * Metodo encargado de recuperar la contraseña de un usuario registrado en
     * el sistema de información
     */
    public void recuperarContraseñaUsuario() {
        //RequestContext context = RequestContext.getCurrentInstance();
        try {
            if ((correoRecuperacion != null && (!correoRecuperacion.isEmpty())) && (identificacionRecuperacion != null && (!identificacionRecuperacion.isEmpty()))) {
                boolean validarCorreo = Utilidades.validarCorreoElectronico(correoRecuperacion);
                if (validarCorreo == true) {
                    Persona recuperar = gestionarLoginSistemaBO.obtenerPersonaRecuperarContrasenia(correoRecuperacion, identificacionRecuperacion);
                    correoRecuperacion = null;
                    identificacionRecuperacion = null;
                    //context.execute("recuperarContrasenia.hide()");
                    if (recuperar != null) {
                        Persona personaRecuperada = gestionarLoginSistemaBO.configurarContraseñaPersona(recuperar);
                        enviarCorreoRecuperacion(personaRecuperada);
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "El cambio de contraseña se ha realizado con exito.", "Proceso de Recuperacion Contraseña");
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage("message", message);
                        //context.execute("cambioContraseniaOK.show()");
                    } else {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario ingresado no existe.", "Proceso de Recuperacion Contraseña");
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage("message", message);
                        //context.execute("errorUsuarioNoExiste.show()");
                    }
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El correo ingresado se encuentra erroneo.", "Proceso de Recuperacion Contraseña");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("message", message);
                    //context.execute("errorEmailEstudiante.show()");
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las credenciales se encuentran erroneas.", "Proceso de Recuperacion Contraseña");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("message", message);
                //context.execute("errorDatosRecuperacion.show()");
            }
        } catch (Exception e) {
            System.out.println("Error recuperarContraseñaUsuario ControllerIndex : " + e.toString());
        }
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

    /**
     * Metodo encargado del login al sistema de información
     */
    public void loginUsuario() {
        paginaSiguiente = null;
        try {
            Persona personaLogin = null;
            if ((usuarioLogin != null && (!usuarioLogin.isEmpty())) && (passwordLogin != null && (!passwordLogin.isEmpty()))) {
                System.out.println("usuarioLogin : " + usuarioLogin);
                System.out.println("passwordLogin : " + passwordLogin);
                personaLogin = gestionarLoginSistemaBO.obtenerPersonaLogin(usuarioLogin, passwordLogin);
                usuarioLogin = null;
                passwordLogin = null;
                System.out.println("personaLogin : " + personaLogin);
                if (personaLogin != null) {
                    String nombreTipoUsuario = personaLogin.getUsuario().getTipousuario().getNombretipousuario();
                    usuarioLoginSistema = new UsuarioLogin();
                    System.out.println("nombreTipoUsuario : " + nombreTipoUsuario);
                    if ("ADMINISTRADOR".equals(nombreTipoUsuario)) {
                        System.out.println("Administrador");
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
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario ingresado no existe.", "Error de Ingreso");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("message", message);
                    //context.execute("errorUsuarioNoExiste.show();");
                }

            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las credenciales de ingreso estan vacias.", "Error de Ingreso");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("message", message);
                //context.execute("errorCredencialesLogin.show();");
            }
        } catch (NoResultException nre) {
            //context.execute("NuevoRegistroEstudiante.hide();");
            //context.execute("errorUsuarioNoExiste.show();");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario ingresado no existe.", "Error de Ingreso");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage("message", message);
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
        loginUsuario();
        System.out.println("paginaSiguiente : "+paginaSiguiente);
        if (usuarioLoginSistema == null) {
            paginaSiguiente = null;
            return paginaSiguiente;
        }
        return paginaSiguiente;
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

}
