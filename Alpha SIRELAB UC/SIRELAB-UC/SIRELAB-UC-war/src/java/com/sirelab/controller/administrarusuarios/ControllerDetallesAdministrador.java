/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresBOInterface;
import com.sirelab.entidades.Persona;
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
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesAdministrador implements Serializable {

    @EJB
    AdministrarAdministradoresBOInterface administrarAdministradoresBO;

    private Persona administradorDetalles;
    private BigInteger idAdministrador;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private String nombreAdministrador, apellidoAdministrador, correoAdministrador, identificacionAdministrador;
    private String telefono1Administrador, telefono2Administrador, direccionAdministrador;
    //
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;

    public ControllerDetallesAdministrador() {
    }

    @PostConstruct
    public void init() {
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            disabledEditar = false;
        }
        constantes = new MensajesConstantes();
        BasicConfigurator.configure();
    }

    /**
     * Metodo encargado de asignar los valores del administrador que sera
     * visualizado
     */
    public void asignarValoresVariablesAdministrador() {
        nombreAdministrador = administradorDetalles.getNombrespersona();
        apellidoAdministrador = administradorDetalles.getApellidospersona();
        correoAdministrador = administradorDetalles.getEmailpersona();
        identificacionAdministrador = administradorDetalles.getIdentificacionpersona();
        telefono1Administrador = administradorDetalles.getTelefono1persona();
        telefono2Administrador = administradorDetalles.getTelefono2persona();
        direccionAdministrador = administradorDetalles.getDireccionpersona();
    }

    /**
     * Metodo encargado de recibir el ID del administrador que sera visualizado
     *
     * @param idAdministrador ID del administrador
     */
    public void recibirIDAdministradoresDetalles(BigInteger idAdministrador) {
        this.idAdministrador = idAdministrador;
        administradorDetalles = administrarAdministradoresBO.obtenerAdministradorPorIDPersona(idAdministrador);
        if (administradorDetalles.getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesAdministrador();
    }

    /**
     * Metodo encargado de activar las opciones de editar
     */
    public void activarEditarRegistro() {
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    /**
     * Metodo encargado de restaurar la información del administrador
     */
    public String restaurarInformacionAdministrador() {
        administradorDetalles = new Persona();
        administradorDetalles = administrarAdministradoresBO.obtenerAdministradorPorIDPersona(idAdministrador);
        if (administradorDetalles.getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesAdministrador();
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        return "administraradministradores";
    }

    public void validarNombreAdministrador() {
        if (Utilidades.validarNulo(nombreAdministrador) && (!nombreAdministrador.isEmpty()) && (nombreAdministrador.trim().length() > 0)) {
            int tam = nombreAdministrador.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nombreAdministrador)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreAdministrador", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.USUARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreAdministrador", new FacesMessage("El tamaño minimo permitido es 2 caracteres. "+constantes.USUARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreAdministrador", new FacesMessage("El nombre es obligatorio. "+constantes.USUARIO_NOMBRE));
        }
        modificacionesRegistroAdministrador();
    }

    public void validarApellidoAdministrador() {
        if (Utilidades.validarNulo(apellidoAdministrador) && (!apellidoAdministrador.isEmpty()) && (apellidoAdministrador.trim().length() > 0)) {
            int tam = apellidoAdministrador.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(apellidoAdministrador)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:apellidoAdministrador", new FacesMessage("El apellido ingresado es incorrecto. "+constantes.USUARIO_APELLIDO));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:apellidoAdministrador", new FacesMessage("El tamaño minimo permitido es 2 caracteres. "+constantes.USUARIO_APELLIDO));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:apellidoAdministrador", new FacesMessage("El apellido es obligatorio. "+constantes.USUARIO_APELLIDO));
        }
        modificacionesRegistroAdministrador();
    }

    public void validarCorreoAdministrador() {
        if (Utilidades.validarNulo(correoAdministrador) && (!correoAdministrador.isEmpty()) && (correoAdministrador.trim().length() > 0)) {
            int tam = correoAdministrador.length();
            if (tam >= 4) {
                String correoDoc = correoAdministrador + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correoDoc)) {
                    Persona registro = administrarAdministradoresBO.obtenerAdministradorPorCorreo(correoAdministrador);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        if (!administradorDetalles.getIdpersona().equals(registro.getIdpersona())) {
                            validacionesCorreo = false;
                            FacesContext.getCurrentInstance().addMessage("form:correoAdministrador", new FacesMessage("El correo ya se encuentra registrado."));
                        } else {
                            validacionesCorreo = true;
                        }
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoAdministrador", new FacesMessage("El correo se encuentra incorrecto. "+constantes.USUARIO_CORREO));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:correoAdministrador", new FacesMessage("El tamaño minimo permitido es 4 caracteres. "+constantes.USUARIO_CORREO));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:correoAdministrador", new FacesMessage("El correo es obligatorio. "+constantes.USUARIO_CORREO));
        }
        modificacionesRegistroAdministrador();
    }

    public void validarIdentificacionAdministrador() {
        if (Utilidades.validarNulo(identificacionAdministrador) && (!identificacionAdministrador.isEmpty()) && (identificacionAdministrador.trim().length() > 0)) {
            int tam = identificacionAdministrador.length();
            if (tam >= 8) {
                if (Utilidades.validarNumeroIdentificacion(identificacionAdministrador)) {
                    Persona registro = administrarAdministradoresBO.obtenerAdministradorPorDocumento(identificacionAdministrador);
                    if (null == registro) {
                        validacionesID = true;
                    } else {
                        if (!administradorDetalles.getIdpersona().equals(registro.getIdpersona())) {
                            validacionesID = false;
                            FacesContext.getCurrentInstance().addMessage("form:identificacionAdministrador", new FacesMessage("El documento ya se encuentra registrado."));
                        } else {
                            validacionesID = true;
                        }
                    }
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:identificacionAdministrador", new FacesMessage("El numero identificación se encuentra incorrecto. "+constantes.USUARIO_ID));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:identificacionAdministrador", new FacesMessage("El tamaño minimo permitido es 8 caracteres. "+constantes.USUARIO_ID));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:identificacionAdministrador", new FacesMessage("El numero identificación es obligatorio. "+constantes.USUARIO_ID));
        }
        modificacionesRegistroAdministrador();
    }

    public void validarDatosNumericosAdministrador(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(telefono1Administrador) && (!telefono1Administrador.isEmpty()) && (telefono1Administrador.trim().length() > 0)) {
                int tam = telefono1Administrador.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(telefono1Administrador)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono1Administrador", new FacesMessage("El numero telefonico se encuentra incorrecto. "+constantes.USUARIO_TELFIJO));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono1Administrador", new FacesMessage("El numero telefonico se encuentra incorrecto. "+constantes.USUARIO_TELFIJO));
                }
            }
        } else {
            if (Utilidades.validarNulo(telefono2Administrador) && (!telefono2Administrador.isEmpty()) && (telefono2Administrador.trim().length() > 0)) {
                int tam = telefono2Administrador.length();
                if (tam == 10) {
                    if ((Utilidades.isNumber(telefono2Administrador)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono2Administrador", new FacesMessage("El numero telefonico se encuentra incorrecto. "+constantes.USUARIO_TELCEL));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono2Administrador", new FacesMessage("El numero telefonico se encuentra incorrecto. "+constantes.USUARIO_TELCEL));
                }
            }
        }
        modificacionesRegistroAdministrador();
    }

    public void validarDireccionAdministrador() {
        if ((Utilidades.validarNulo(direccionAdministrador)) && (!direccionAdministrador.isEmpty()) && (direccionAdministrador.trim().length() > 0)) {
            int tam = direccionAdministrador.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(direccionAdministrador)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:direccionAdministrador", new FacesMessage("La dirección se encuentra incorrecta. "+constantes.USUARIO_DIRECCION));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:direccionAdministrador", new FacesMessage("El tamaño minimo permitido es 8 caracteres. "+constantes.USUARIO_DIRECCION));
                validacionesDireccion = false;
            }
        }
        modificacionesRegistroAdministrador();
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
        if (validacionesTel2 == false) {
            retorno = false;
        }
        return retorno;
    }

    public void almacenarModificacionesAdministrador() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                modificarInformacionAdministrador();
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            restaurarInformacionAdministrador();
            colorMensaje = "black";
            mensajeFormulario = "No se presento algun cambio en el registro. No se realizo ningun proceso de almacenamiento.";
        }
    }

    /**
     * Metodo encargado de almacenar en el sistema de información las
     * modificaciones del registro
     */
    public void modificarInformacionAdministrador() {
        try {
            administradorDetalles.setApellidospersona(apellidoAdministrador);
            administradorDetalles.setDireccionpersona(direccionAdministrador);
            administradorDetalles.setEmailpersona(correoAdministrador);
            administradorDetalles.setIdentificacionpersona(identificacionAdministrador);
            administradorDetalles.setNombrespersona(nombreAdministrador);
            administradorDetalles.setTelefono1persona(telefono1Administrador);
            administradorDetalles.setTelefono2persona(telefono2Administrador);
            administrarAdministradoresBO.actualizarInformacionAdministrador(administradorDetalles);
            restaurarInformacionAdministrador();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAdministrador modificarInformacionAdministrador:  " + e.toString(),e);
            logger.error("Error ControllerDetallesAdministrador modificarInformacionAdministrador : " + e.toString(),e);
        }
    }

    /**
     * Metodo encargado de registrar alguna modificación del registro
     */
    public void modificacionesRegistroAdministrador() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    /**
     * Metodo encargado de activar el administrador
     */
    public void activarAdministrador() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(true);
                administradorDetalles.getUsuario().setEstado(bool);
                administrarAdministradoresBO.actualizarInformacionUsuario(administradorDetalles.getUsuario());
                restaurarInformacionAdministrador();
                colorMensaje = "green";
                mensajeFormulario = "Se ha activado el administrador.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAdministrador activarAdministrador:  " + e.toString(),e);
            logger.error("Error ControllerDetallesAdministradores activarAdministrador : " + e.toString(),e);
        }
    }

    /**
     * Metodo encargado de inactivar el administrador
     */
    public void inactivarAdministrador() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(false);
                administradorDetalles.getUsuario().setEstado(bool);
                administrarAdministradoresBO.actualizarInformacionUsuario(administradorDetalles.getUsuario());
                administradorDetalles = new Persona();
                restaurarInformacionAdministrador();
                colorMensaje = "green";
                mensajeFormulario = "Se ha inactivado el administrador.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAdministrador inactivarAdministrador:  " + e.toString(),e);
            logger.error("Error ControllerDetallesAdministradores inactivarAdministrador : " + e.toString(),e);
        }
    }

    // GET - SET
    public Persona getAdministradorDetalles() {
        return administradorDetalles;
    }

    public void setAdministradorDetalles(Persona administradorDetalles) {
        this.administradorDetalles = administradorDetalles;
    }

    public BigInteger getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(BigInteger idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public boolean isActivarEditar() {
        return activarEditar;
    }

    public void setActivarEditar(boolean activarEditar) {
        this.activarEditar = activarEditar;
    }

    public boolean isDisabledEditar() {
        return disabledEditar;
    }

    public void setDisabledEditar(boolean disabledEditar) {
        this.disabledEditar = disabledEditar;
    }

    public boolean isModificacionRegistro() {
        return modificacionRegistro;
    }

    public void setModificacionRegistro(boolean modificacionRegistro) {
        this.modificacionRegistro = modificacionRegistro;
    }

    public boolean isDisabledActivar() {
        return disabledActivar;
    }

    public void setDisabledActivar(boolean disabledActivar) {
        this.disabledActivar = disabledActivar;
    }

    public boolean isDisabledInactivar() {
        return disabledInactivar;
    }

    public void setDisabledInactivar(boolean disabledInactivar) {
        this.disabledInactivar = disabledInactivar;
    }

    public boolean isVisibleGuardar() {
        return visibleGuardar;
    }

    public void setVisibleGuardar(boolean visibleGuardar) {
        this.visibleGuardar = visibleGuardar;
    }

    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    public String getApellidoAdministrador() {
        return apellidoAdministrador;
    }

    public void setApellidoAdministrador(String apellidoAdministrador) {
        this.apellidoAdministrador = apellidoAdministrador;
    }

    public String getCorreoAdministrador() {
        return correoAdministrador;
    }

    public void setCorreoAdministrador(String correoAdministrador) {
        this.correoAdministrador = correoAdministrador;
    }

    public String getIdentificacionAdministrador() {
        return identificacionAdministrador;
    }

    public void setIdentificacionAdministrador(String identificacionAdministrador) {
        this.identificacionAdministrador = identificacionAdministrador;
    }

    public String getTelefono1Administrador() {
        return telefono1Administrador;
    }

    public void setTelefono1Administrador(String telefono1Administrador) {
        this.telefono1Administrador = telefono1Administrador;
    }

    public String getTelefono2Administrador() {
        return telefono2Administrador;
    }

    public void setTelefono2Administrador(String telefono2Administrador) {
        this.telefono2Administrador = telefono2Administrador;
    }

    public String getDireccionAdministrador() {
        return direccionAdministrador;
    }

    public void setDireccionAdministrador(String direccionAdministrador) {
        this.direccionAdministrador = direccionAdministrador;
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

}
