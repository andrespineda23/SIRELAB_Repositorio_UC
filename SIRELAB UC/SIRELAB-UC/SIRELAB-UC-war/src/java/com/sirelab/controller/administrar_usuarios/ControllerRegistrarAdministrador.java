/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresBOInterface;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarAdministrador implements Serializable {

    @EJB
    AdministrarAdministradoresBOInterface administrarAdministradoresBO;

    //
    private String nuevoNombre, nuevoApellido, nuevoDocumento;
    private String nuevoCorreo, nuevoTelefono1, nuevoTelefono2;
    private String nuevoDireccion, nuevoContrasenia, nuevoContraseniaConfirma, nuevoUsuario;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesPassw, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesPassw2, validacionesUsuario;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;

    public ControllerRegistrarAdministrador() {
    }

    @PostConstruct
    public void init() {
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesUsuario = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesPassw2 = false;
        nuevoApellido = null;
        nuevoContrasenia = null;
        nuevoContraseniaConfirma = null;
        nuevoCorreo = null;
        nuevoDireccion = null;
        nuevoDocumento = null;
        nuevoNombre = null;
        nuevoTelefono1 = null;
        nuevoTelefono2 = null;
        nuevoUsuario = null;
        BasicConfigurator.configure();
    }

    public void validarNombreAdministrador() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarApellidoAdministrador() {
        if (Utilidades.validarNulo(nuevoApellido) && (!nuevoApellido.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoApellido)) {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoApellido", new FacesMessage("El apellido ingresado es incorrecto."));
            } else {
                validacionesApellido = true;
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoApellido", new FacesMessage("El apellido es obligatorio."));
        }
    }

    public void validarCorreoAdministrador() {
        if (Utilidades.validarNulo(nuevoCorreo) && (!nuevoCorreo.isEmpty())) {
            String correoAdministrador = nuevoCorreo + "@ucentral.edu.co";
            if (Utilidades.validarCorreoElectronico(correoAdministrador)) {
                Persona registro = administrarAdministradoresBO.obtenerAdministradorPorCorreo(nuevoCorreo);
                if (null == registro) {
                    validacionesCorreo = true;
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCorreo", new FacesMessage("El correo ya se encuentra registrado."));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCorreo", new FacesMessage("El correo se encuentra incorrecto."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCorreo", new FacesMessage("El correo es obligatorio."));
        }
    }

    public void validarIdentificacionAdministrador() {
        if (Utilidades.validarNulo(nuevoDocumento) && (!nuevoDocumento.isEmpty())) {
            if (Utilidades.validarNumeroIdentificacion(nuevoDocumento)) {
                Persona registro = administrarAdministradoresBO.obtenerAdministradorPorDocumento(nuevoDocumento);
                if (null == registro) {
                    validacionesID = true;
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDocumento", new FacesMessage("El documento ya se encuentra registrado."));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDocumento", new FacesMessage("El numero identificación se encuentra incorrecto."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDocumento", new FacesMessage("El numero identificación es obligatorio."));
        }
    }

    public void validarDatosNumericosAdministrador(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(nuevoTelefono1) && (!nuevoTelefono1.isEmpty())) {
                if ((Utilidades.isNumber(nuevoTelefono1)) == false) {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTel1 = true;
                }
            }
        } else {
            if (Utilidades.validarNulo(nuevoTelefono2) && (!nuevoTelefono2.isEmpty())) {
                if ((Utilidades.isNumber(nuevoTelefono2)) == false) {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTel2 = true;
                }
            }
        }
    }

    public void validarDireccionAdministrador() {
        if ((Utilidades.validarNulo(nuevoDireccion)) && (!nuevoDireccion.isEmpty())) {
            if (Utilidades.validarDirecciones(nuevoDireccion)) {
                validacionesDireccion = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                validacionesDireccion = false;
            }
        }
    }

    public void validarContraseniaAdministrador() {
        if ((Utilidades.validarNulo(nuevoContrasenia)) && (!nuevoContrasenia.isEmpty())) {
            validacionesPassw = true;
        } else {
            FacesContext.getCurrentInstance().addMessage("form:nuevoContrasenia", new FacesMessage("La contraseña es obligatoria."));
            validacionesPassw = false;
        }
    }

    public void validarContraseniaConfirmaAdministrador() {
        if ((Utilidades.validarNulo(nuevoContrasenia)) && (Utilidades.validarNulo(nuevoContraseniaConfirma))
                && (!nuevoContrasenia.isEmpty()) && (!nuevoContraseniaConfirma.isEmpty())) {
            if (nuevoContrasenia.equals(nuevoContraseniaConfirma)) {
                validacionesPassw2 = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:nuevoContraseniaConfirma", new FacesMessage("Las contraseñas ingresadas no concuerdan."));
                validacionesPassw2 = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:nuevoContraseniaConfirma", new FacesMessage("Las contraseñas son obligatorias."));
            validacionesPassw2 = false;
        }
    }

    public void validarUsuarioAdministrador() {
        if (Utilidades.validarNulo(nuevoUsuario) && (!nuevoUsuario.isEmpty())) {
            Persona registro = administrarAdministradoresBO.obtenerAdministradorPorUsuario(nuevoUsuario);
            if (null == registro) {
                validacionesUsuario = true;
            } else {
                validacionesUsuario = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoUsuario", new FacesMessage("El usuario ya se encuentra registrado."));
            }
        } else {
            validacionesUsuario = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoUsuario", new FacesMessage("El usuario es obligatorio."));
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
        if (validacionesPassw == false) {
            retorno = false;
        }
        if (validacionesPassw2 == false) {
            retorno = false;
        }
        if (validacionesTel1 == false) {
            retorno = false;
        }
        if (validacionesTel2 == false) {
            retorno = false;
        }
        if (validacionesUsuario == false) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo administrador
     */
    public void registrarNuevoAdministrador() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoAdministradorEnSistema();
            limpiarFormulario();
            activarLimpiar = false;
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenarNuevoAdministradorEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setEnlinea(false);
            usuarioNuevo.setNumeroconexiones(1);
            usuarioNuevo.setNombreusuario(nuevoUsuario);
            usuarioNuevo.setPasswordusuario(nuevoContrasenia);
            Persona personaNueva = new Persona();
            personaNueva.setNombrespersona(nuevoNombre);
            personaNueva.setApellidospersona(nuevoApellido);
            personaNueva.setDireccionpersona(nuevoDireccion);
            personaNueva.setEmailpersona(nuevoCorreo);
            personaNueva.setIdentificacionpersona(nuevoDocumento);
            personaNueva.setTelefono1persona(nuevoTelefono1);
            personaNueva.setTelefono2persona(nuevoTelefono2);
            administrarAdministradoresBO.almacenarNuevaPersonaEnSistema(usuarioNuevo, personaNueva);
            cancelarRegistroAdministrador();
            activarCasillas = true;
            colorMensaje = "green";
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarAdministrador almacenarNuevoAdministradorEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarAdministrador almacenarNuevoAdministradorEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        validacionesNombre = false;
        validacionesUsuario = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesPassw2 = false;
        mensajeFormulario = "";
        nuevoApellido = null;
        nuevoContrasenia = null;
        nuevoContraseniaConfirma = null;
        nuevoCorreo = null;
        nuevoDireccion = null;
        nuevoDocumento = null;
        nuevoNombre = null;
        nuevoTelefono1 = null;
        nuevoTelefono2 = null;
        nuevoUsuario = null;
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroAdministrador() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarCasillas = false;
        colorMensaje = "black";
        validacionesNombre = false;
        validacionesUsuario = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesPassw2 = false;
        nuevoApellido = null;
        nuevoContrasenia = null;
        nuevoContraseniaConfirma = null;
        nuevoCorreo = null;
        nuevoDireccion = null;
        nuevoDocumento = null;
        nuevoNombre = null;
        nuevoTelefono1 = null;
        nuevoTelefono2 = null;
        nuevoUsuario = null;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET
    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoApellido() {
        return nuevoApellido;
    }

    public void setNuevoApellido(String nuevoApellido) {
        this.nuevoApellido = nuevoApellido;
    }

    public String getNuevoDocumento() {
        return nuevoDocumento;
    }

    public void setNuevoDocumento(String nuevoDocumento) {
        this.nuevoDocumento = nuevoDocumento;
    }

    public String getNuevoCorreo() {
        return nuevoCorreo;
    }

    public void setNuevoCorreo(String nuevoCorreo) {
        this.nuevoCorreo = nuevoCorreo;
    }

    public String getNuevoTelefono1() {
        return nuevoTelefono1;
    }

    public void setNuevoTelefono1(String nuevoTelefono1) {
        this.nuevoTelefono1 = nuevoTelefono1;
    }

    public String getNuevoTelefono2() {
        return nuevoTelefono2;
    }

    public void setNuevoTelefono2(String nuevoTelefono2) {
        this.nuevoTelefono2 = nuevoTelefono2;
    }

    public String getNuevoDireccion() {
        return nuevoDireccion;
    }

    public void setNuevoDireccion(String nuevoDireccion) {
        this.nuevoDireccion = nuevoDireccion;
    }

    public String getNuevoContrasenia() {
        return nuevoContrasenia;
    }

    public void setNuevoContrasenia(String nuevoContrasenia) {
        this.nuevoContrasenia = nuevoContrasenia;
    }

    public String getNuevoContraseniaConfirma() {
        return nuevoContraseniaConfirma;
    }

    public void setNuevoContraseniaConfirma(String nuevoContraseniaConfirma) {
        this.nuevoContraseniaConfirma = nuevoContraseniaConfirma;
    }

    public String getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void setNuevoUsuario(String nuevoUsuario) {
        this.nuevoUsuario = nuevoUsuario;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
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

}
