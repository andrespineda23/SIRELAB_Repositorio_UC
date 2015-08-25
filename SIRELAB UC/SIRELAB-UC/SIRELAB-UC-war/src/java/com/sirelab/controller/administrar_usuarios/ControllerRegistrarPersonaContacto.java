/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarPersonasContactoBOInterface;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.PersonaContacto;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarPersonaContacto implements Serializable {

    //
    @EJB
    AdministrarPersonasContactoBOInterface administrarPersonasContactoBO;
    //

    private ConvenioPorEntidad convenioPorEntidad;
    private String inputNombre, inputApellido, inputID, inputEmail, inputTelefono1, inputTelefono2, inputUsuario;

    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesUsuario;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarPersonaContacto() {
    }

    @PostConstruct
    public void init() {
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesUsuario = false;
        inputApellido = null;
        inputUsuario = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        BasicConfigurator.configure();
    }

    public void recibirConvenioPorEntidad(BigInteger idregistro) {
        convenioPorEntidad = administrarPersonasContactoBO.buscarConvenioPorEntidadPorId(idregistro);
    }

    public void validarNombrePersonaContacto() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty()) && (inputNombre.trim().length() > 0)) {
            int tam = inputNombre.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(inputNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarApellidoPersonaContacto() {
        if (Utilidades.validarNulo(inputApellido) && (!inputApellido.isEmpty()) && (inputApellido.trim().length() > 0)) {
            int tam = inputApellido.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(inputApellido)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido ingresado es incorrecto."));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido es obligatorio."));
        }
    }

    public void validarCorreoPersonaContacto() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty()) && (inputEmail.trim().length() > 0)) {
            int tam = inputEmail.length();
            if (tam >= 4) {
                String correoPersonaContacto = inputEmail;
                if (Utilidades.validarCorreoElectronico(correoPersonaContacto)) {
                    validacionesCorreo = true;
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
                }
            } else {
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio."));
        }
    }

    public void validarIdentificacionPersonaContacto() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty()) && (inputID.trim().length() > 0)) {
            int tam = inputID.length();
            if (tam >= 8) {
                if (Utilidades.validarNumeroIdentificacion(inputID)) {
                    validacionesID = true;
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación se encuentra incorrecto."));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación es obligatorio."));
        }
    }

    public void validarDatosNumericosPersonaContacto(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty()) && (inputTelefono1.trim().length() > 0)) {
                int tam = inputTelefono1.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(inputTelefono1)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        } else {
            if (Utilidades.validarNulo(inputTelefono2) && (!inputTelefono2.isEmpty()) && (inputTelefono2.trim().length() > 0)) {
                int tam = inputTelefono2.length();
                if (tam == 10) {
                    if ((Utilidades.isNumber(inputTelefono2)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        }
    }

    public void validarUsuarioPersonaContacto() {
        if ((Utilidades.validarNulo(inputUsuario)) && (!inputUsuario.isEmpty()) && (inputUsuario.trim().length() > 0)) {
            int tam = inputUsuario.length();
            if (tam >= 8) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputUsuario)) {
                    PersonaContacto registro = administrarPersonasContactoBO.obtenerPersonaContactoPorUsuario(inputUsuario);
                    if (null == registro) {
                        validacionesUsuario = false;
                    } else {
                        FacesContext.getCurrentInstance().addMessage("form:inputUsuario", new FacesMessage("El usuario ya se encuentra registrado."));
                        validacionesUsuario = false;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:inputUsuario", new FacesMessage("El usuario ingresado es incorrecto."));
                    validacionesUsuario = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputUsuario", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
                validacionesUsuario = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:inputUsuario", new FacesMessage("El campo Usuario es incorrecto."));
            validacionesUsuario = false;
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesApellido == false) {
            retorno = false;
        }
        if (validacionesCorreo == false) {
            retorno = false;
        }
        if (validacionesUsuario == false) {
            retorno = false;
        }
        if (validacionesID == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesTel1 == false) {
            retorno = false;
        }
        if (validacionesTel2 == false) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoPersonaContacto() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoPersonaContactoEnSistema();
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroPersonaContacto() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        activarCasillas = false;
        colorMensaje = "black";
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesUsuario = false;
        //
        inputApellido = null;
        inputUsuario = null;
        inputEmail = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputID = null;
        inputNombre = null;
    }

    public void limpiarFormulario() {
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesUsuario = false;
        //
        inputApellido = null;
        inputUsuario = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información el nuevo
     * docente
     */
    private void almacenarNuevoPersonaContactoEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEnlinea(false);
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setNumeroconexiones(0);
            usuarioNuevo.setNombreusuario(inputUsuario);
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuarioNuevo.setPasswordusuario(obj.encriptarContrasenia(inputID));
            PersonaContacto personaNueva = new PersonaContacto();
            personaNueva.setApellido(inputApellido);
            personaNueva.setNombreusuario(inputUsuario);
            personaNueva.setCorreo(inputEmail);
            personaNueva.setIdentificacion(inputID);
            personaNueva.setNombre(inputNombre);
            personaNueva.setConvenioporentidad(convenioPorEntidad);
            personaNueva.setTelefonofijo(inputTelefono1);
            personaNueva.setTelefonocelular(inputTelefono2);
            administrarPersonasContactoBO.crearUsuario(usuarioNuevo);
            administrarPersonasContactoBO.crearPersonaContado(personaNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarPersonaContacto almacenarNuevoPersonaContactoEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarPersonaContacto almacenarNuevoPersonaContactoEnSistema : " + e.toString());
        }
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        activarAceptar = false;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET
    public ConvenioPorEntidad getConvenioPorEntidad() {
        return convenioPorEntidad;
    }

    public void setConvenioPorEntidad(ConvenioPorEntidad convenioPorEntidad) {
        this.convenioPorEntidad = convenioPorEntidad;
    }

    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public String getInputApellido() {
        return inputApellido;
    }

    public void setInputApellido(String inputApellido) {
        this.inputApellido = inputApellido;
    }

    public String getInputID() {
        return inputID;
    }

    public void setInputID(String inputID) {
        this.inputID = inputID;
    }

    public String getInputEmail() {
        return inputEmail;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public String getInputTelefono1() {
        return inputTelefono1;
    }

    public void setInputTelefono1(String inputTelefono1) {
        this.inputTelefono1 = inputTelefono1;
    }

    public String getInputTelefono2() {
        return inputTelefono2;
    }

    public void setInputTelefono2(String inputTelefono2) {
        this.inputTelefono2 = inputTelefono2;
    }

    public String getInputUsuario() {
        return inputUsuario;
    }

    public void setInputUsuario(String inputUsuario) {
        this.inputUsuario = inputUsuario;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isActivarLimpiar() {
        return activarLimpiar;
    }

    public void setActivarLimpiar(boolean activarLimpiar) {
        this.activarLimpiar = activarLimpiar;
    }

    public boolean isActivarAceptar() {
        return activarAceptar;
    }

    public void setActivarAceptar(boolean activarAceptar) {
        this.activarAceptar = activarAceptar;
    }

}
