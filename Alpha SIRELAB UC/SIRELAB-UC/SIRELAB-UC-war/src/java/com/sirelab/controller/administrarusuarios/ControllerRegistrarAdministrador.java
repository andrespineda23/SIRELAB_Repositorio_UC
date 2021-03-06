/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresBOInterface;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
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
    private String nuevoDireccion;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarAdministrador() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        mensajeError = "";
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        nuevoApellido = null;
        nuevoCorreo = null;
        nuevoDireccion = null;
        nuevoDocumento = null;
        nuevoNombre = null;
        nuevoTelefono1 = null;
        nuevoTelefono2 = null;
        BasicConfigurator.configure();
    }

    public void validarNombreAdministrador() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.USUARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.USUARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio. " + constantes.USUARIO_NOMBRE));
        }

    }

    public void validarApellidoAdministrador() {
        if (Utilidades.validarNulo(nuevoApellido) && (!nuevoApellido.isEmpty()) && (nuevoApellido.trim().length() > 0)) {
            int tam = nuevoApellido.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nuevoApellido)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoApellido", new FacesMessage("El apellido ingresado es incorrecto. " + constantes.USUARIO_APELLIDO));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoApellido", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.USUARIO_APELLIDO));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoApellido", new FacesMessage("El apellido es obligatorio. " + constantes.USUARIO_APELLIDO));
        }
    }

    public void validarCorreoAdministrador() {
        if (Utilidades.validarNulo(nuevoCorreo) && (!nuevoCorreo.isEmpty()) && (nuevoCorreo.trim().length() > 0)) {
            int tam = nuevoCorreo.length();
            if (tam >= 4) {
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
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCorreo", new FacesMessage("El correo se encuentra incorrecto. " + constantes.USUARIO_CORREO));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCorreo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.USUARIO_CORREO));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCorreo", new FacesMessage("El correo es obligatorio. " + constantes.USUARIO_CORREO));
        }
    }

    public void validarIdentificacionAdministrador() {
        if (Utilidades.validarNulo(nuevoDocumento) && (!nuevoDocumento.isEmpty()) && (nuevoDocumento.trim().length() > 0)) {
            int tam = nuevoDocumento.length();
            if (tam >= 8) {
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
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDocumento", new FacesMessage("El numero identificación se encuentra incorrecto. " + constantes.USUARIO_ID));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDocumento", new FacesMessage("El tamaño minimo permitido es 8 caracteres. " + constantes.USUARIO_ID));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDocumento", new FacesMessage("El numero identificación es obligatorio. " + constantes.USUARIO_ID));
        }
    }

    public void validarDatosNumericosAdministrador(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(nuevoTelefono1) && (!nuevoTelefono1.isEmpty()) && (nuevoTelefono1.trim().length() > 0)) {
                int tam = nuevoTelefono1.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(nuevoTelefono1)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.USUARIO_TELFIJO));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.USUARIO_TELFIJO));
                }
            }
        } else {
            if (Utilidades.validarNulo(nuevoTelefono2) && (!nuevoTelefono2.isEmpty()) && (nuevoTelefono2.trim().length() > 0)) {
                int tam = nuevoTelefono2.length();
                if (tam == 10) {
                    if ((Utilidades.isNumber(nuevoTelefono2)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.USUARIO_TELCEL));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.USUARIO_TELCEL));
                }
            }
        }
    }

    public void validarDireccionAdministrador() {
        if ((Utilidades.validarNulo(nuevoDireccion)) && (!nuevoDireccion.isEmpty()) && (nuevoDireccion.trim().length() > 0)) {
            int tam = nuevoDireccion.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(nuevoDireccion)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección se encuentra incorrecta. " + constantes.USUARIO_DIRECCION));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. " + constantes.USUARIO_DIRECCION));
                validacionesDireccion = false;
            }
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesApellido == false) {
            mensajeError = mensajeError + " - Apellido - ";
            retorno = false;
        }
        if (validacionesCorreo == false) {
            mensajeError = mensajeError + " - Correo - ";
            retorno = false;
        }
        if (validacionesDireccion == false) {
            mensajeError = mensajeError + " - Dirección - ";
            retorno = false;
        }
        if (validacionesID == false) {
            mensajeError = mensajeError + " - Identificación - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        if (validacionesTel1 == false) {
            mensajeError = mensajeError + " - Tel. FIjo - ";
            retorno = false;
        }
        if (validacionesTel2 == false) {
            mensajeError = mensajeError + " - Tel. Celular - ";
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
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    public void almacenarNuevoAdministradorEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setNumeroconexiones(1);
            usuarioNuevo.setNombreusuario(nuevoCorreo);
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuarioNuevo.setPasswordusuario(obj.encriptarContrasenia(nuevoDocumento));
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
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarAdministrador almacenarNuevoAdministradorEnSistema : " + e.toString(), e);
        }
    }

    public void limpiarFormulario() {
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        mensajeFormulario = "";
        nuevoApellido = null;
        nuevoCorreo = null;
        nuevoDireccion = null;
        nuevoDocumento = null;
        nuevoNombre = null;
        nuevoTelefono1 = null;
        nuevoTelefono2 = null;
        mensajeError = "";
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroAdministrador() {
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
        validacionesDireccion = true;
        nuevoApellido = null;
        nuevoCorreo = null;
        nuevoDireccion = null;
        nuevoDocumento = null;
        nuevoNombre = null;
        nuevoTelefono1 = null;
        nuevoTelefono2 = null;
        mensajeError = "";
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

    public boolean isActivarAceptar() {
        return activarAceptar;
    }

    public void setActivarAceptar(boolean activarAceptar) {
        this.activarAceptar = activarAceptar;
    }

}
