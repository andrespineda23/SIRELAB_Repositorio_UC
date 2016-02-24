/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarPersonasContactoBOInterface;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.Persona;
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
public class ControllerRegistrarPersonaContactoCargado implements Serializable {

    //
    @EJB
    AdministrarPersonasContactoBOInterface administrarPersonasContactoBO;
    //

    private ConvenioPorEntidad convenioPorEntidad;
    private String inputNombre, inputApellido, inputID, inputEmail, inputEmailOpc, inputTelefono1, inputTelefono2, inputDireccion;

    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpc;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarPersonaContactoCargado() {
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
        validacionesCorreoOpc = true;
        validacionesDireccion = false;
        inputApellido = null;
        inputDireccion = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        BasicConfigurator.configure();
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
                    PersonaContacto registro = administrarPersonasContactoBO.obtenerPersonaContactoPorCorreo(inputEmail);
                    if (registro == null) {
                        validacionesCorreo = true;
                    } else {
                        validacionesCorreo = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo ingresado se encuentra registrado."));
                    }
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

    public void validarCorreoOpcPersonaContacto() {
        if (Utilidades.validarNulo(inputEmailOpc) && (!inputEmailOpc.isEmpty()) && (inputEmailOpc.trim().length() > 0)) {
            int tam = inputEmailOpc.length();
            if (tam >= 4) {
                String correoPersonaContacto = inputEmailOpc;
                if (Utilidades.validarCorreoElectronico(correoPersonaContacto)) {
                    validacionesCorreoOpc = true;
                } else {
                    validacionesCorreoOpc = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmailOpc", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
                }
            } else {
            }
        } else {
            validacionesCorreoOpc = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmailOpc", new FacesMessage("El correo es obligatorio."));
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

    public void validarDireccionPersonaContacto() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty()) && (inputDireccion.trim().length() > 0)) {
            int tam = inputDireccion.length();
            if (tam >= 8) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputDireccion)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("La dirección es incorrecta."));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
                validacionesDireccion = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("El campo Direccion es incorrecto."));
            validacionesDireccion = false;
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
        if (validacionesDireccion == false) {
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
        if (validacionesCorreoOpc == false) {
            retorno = false;
        }
        if (validacionesTel2 == false) {
            retorno = false;
        }
        return retorno;
    }

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
        validacionesCorreoOpc = true;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = false;
        //
        inputApellido = null;
        inputDireccion = null;
        inputEmailOpc = null;
        inputEmail = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputID = null;
        inputNombre = null;
        convenioPorEntidad = null;
    }

    public void limpiarFormulario() {
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesCorreoOpc = true;
        validacionesTel2 = true;
        validacionesDireccion = false;
        //
        inputApellido = null;
        inputDireccion = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmailOpc = null;
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
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setNumeroconexiones(0);
            usuarioNuevo.setNombreusuario(inputID);
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuarioNuevo.setPasswordusuario(obj.encriptarContrasenia(inputID));
            Persona personaNueva = new Persona();
            personaNueva.setApellidospersona(inputApellido);
            personaNueva.setDireccionpersona(inputDireccion);
            personaNueva.setEmailpersona(inputEmail);
            personaNueva.setIdentificacionpersona(inputID);
            personaNueva.setNombrespersona(inputNombre);
            personaNueva.setTelefono1persona(inputTelefono1);
            personaNueva.setTelefono2persona(inputTelefono2);
            personaNueva.setDireccionpersona(inputDireccion);
            personaNueva.setEmailsecundario(inputEmailOpc);
            PersonaContacto personaCNueva = new PersonaContacto();
            personaCNueva.setConvenioporentidad(convenioPorEntidad);
            administrarPersonasContactoBO.crearPersonaContado(usuarioNuevo, personaNueva, personaCNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarPersonaContacto almacenarNuevoPersonaContactoEnSistema:  " + e.toString(),e);
            logger.error("Error ControllerRegistrarPersonaContacto almacenarNuevoPersonaContactoEnSistema : " + e.toString(),e);
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
    public String getInputEmailOpc() {
        return inputEmailOpc;
    }

    public void setInputEmailOpc(String inputEmailOpc) {
        this.inputEmailOpc = inputEmailOpc;
    }

    public String getInputDireccion() {
        return inputDireccion;
    }

    public void setInputDireccion(String inputDireccion) {
        this.inputDireccion = inputDireccion;
    }

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
