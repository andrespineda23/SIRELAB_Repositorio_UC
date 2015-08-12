/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEntidadesExternasBOInterface;
import com.sirelab.entidades.EntidadExterna;
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
 * Controlador: ControllerDetallesEntidadExterna Este controlador se encarga del
 * funcionamiento de la pagina detalles_entidadexterna.xhtml
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesEntidadExterna implements Serializable {

    @EJB
    AdministrarEntidadesExternasBOInterface administrarEntidadesExternasBO;

    private EntidadExterna entidadExternaDetalles;
    private BigInteger idEntidadExterna;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private String nombreEntidadExterna, apellidoEntidadExterna, correoEntidadExterna, identificacionEntidadExterna;
    private String telefono1EntidadExterna, telefono2EntidadExterna, direccionEntidadExterna;
    private String idUnicoEntidadExterna, nombreUnicoEntidadExterna, emailUnicoEntidadExterna;
    //
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesIDEntidad, validacionesNombreEntidad, validacionesEmailEntidad;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesEntidadExterna() {
    }

    @PostConstruct
    public void init() {
        colorMensaje = "black";
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesIDEntidad = true;
        validacionesEmailEntidad = true;
        validacionesNombreEntidad = true;
        mensajeFormulario = "N/A";
        //
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
        BasicConfigurator.configure();
    }

    /**
     * Metodo encargado de asignar los valores de la entidad externa que sera
     * visualizado
     */
    public void asignarValoresVariablesEntidadExterna() {
        idUnicoEntidadExterna = entidadExternaDetalles.getIdentificacionentidad();
        nombreEntidadExterna = entidadExternaDetalles.getPersona().getNombrespersona();
        apellidoEntidadExterna = entidadExternaDetalles.getPersona().getApellidospersona();
        correoEntidadExterna = entidadExternaDetalles.getPersona().getEmailpersona();
        identificacionEntidadExterna = entidadExternaDetalles.getPersona().getIdentificacionpersona();
        telefono1EntidadExterna = entidadExternaDetalles.getPersona().getTelefono1persona();
        telefono2EntidadExterna = entidadExternaDetalles.getPersona().getTelefono2persona();
        direccionEntidadExterna = entidadExternaDetalles.getPersona().getDireccionpersona();
        nombreUnicoEntidadExterna = entidadExternaDetalles.getNombreentidad();
        emailUnicoEntidadExterna = entidadExternaDetalles.getEmailentidad();
    }

    /**
     * Metodo encargado de recibir el ID de la entidad externa que sera
     * visualizado
     *
     * @param idEntidadExterna ID de la entidad externa
     */
    public void recibirIDEntidadesExternasDetalles(BigInteger idEntidadExterna) {
        this.idEntidadExterna = idEntidadExterna;
        entidadExternaDetalles = administrarEntidadesExternasBO.obtenerEntidadExternaPorIDEntidadExterna(idEntidadExterna);
        if (entidadExternaDetalles.getPersona().getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesEntidadExterna();
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
     * Metodo encargado de restaurar la información de la entidad externa
     */
    public String restaurarInformacionEntidadExterna() {
        entidadExternaDetalles = new EntidadExterna();
        entidadExternaDetalles = administrarEntidadesExternasBO.obtenerEntidadExternaPorIDEntidadExterna(idEntidadExterna);
        if (entidadExternaDetalles.getPersona().getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesEntidadExterna();
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesIDEntidad = true;
        validacionesEmailEntidad = true;
        validacionesNombreEntidad = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        return "administrar_entidadesxternas";
    }

    public void validarNombreEntidadExterna() {
        if (Utilidades.validarNulo(nombreEntidadExterna) && (!nombreEntidadExterna.isEmpty())  && (nombreEntidadExterna.trim().length() > 0)) {
            if (!Utilidades.validarCaracterString(nombreEntidadExterna)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreEntidadExterna", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreEntidadExterna", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarApellidoEntidadExterna() {
        if (Utilidades.validarNulo(apellidoEntidadExterna) && (!apellidoEntidadExterna.isEmpty())  && (apellidoEntidadExterna.trim().length() > 0)) {
            if (!Utilidades.validarCaracterString(apellidoEntidadExterna)) {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:apellidoEntidadExterna", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesApellido = true;
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:apellidoEntidadExterna", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarCorreoEntidadExterna() {
        if (Utilidades.validarNulo(correoEntidadExterna) && (!correoEntidadExterna.isEmpty())  && (correoEntidadExterna.trim().length() > 0)) {
            if (Utilidades.validarCorreoElectronico(correoEntidadExterna)) {
                EntidadExterna registro = administrarEntidadesExternasBO.obtenerEntidadExternaPorCorreo(correoEntidadExterna);
                if (null == registro) {
                    validacionesCorreo = true;
                } else {
                    if (!entidadExternaDetalles.getIdentidadexterna().equals(registro.getIdentidadexterna())) {
                        validacionesCorreo = false;
                        FacesContext.getCurrentInstance().addMessage("form:correoEntidadExterna", new FacesMessage("El correo ya se encuentra registrado."));
                    } else {
                        validacionesCorreo = true;
                    }
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:correoEntidadExterna", new FacesMessage("El correo se encuentra incorrecto."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:correoEntidadExterna", new FacesMessage("El correo es obligatorio."));
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarIdentificacionEntidadExterna() {
        if (Utilidades.validarNulo(identificacionEntidadExterna) && (!identificacionEntidadExterna.isEmpty())  && (identificacionEntidadExterna.trim().length() > 0)) {
            if (Utilidades.validarNumeroIdentificacion(identificacionEntidadExterna)) {
                EntidadExterna registro = administrarEntidadesExternasBO.obtenerEntidadExternaPorDocumento(identificacionEntidadExterna);
                if (null == registro) {
                    validacionesID = true;
                } else {
                    if (!entidadExternaDetalles.getIdentidadexterna().equals(registro.getIdentidadexterna())) {
                        validacionesID = false;
                        FacesContext.getCurrentInstance().addMessage("form:identificacionEntidadExterna", new FacesMessage("El documento ya se encuentra registrado."));
                    } else {
                        validacionesID = true;
                    }
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:identificacionEntidadExterna", new FacesMessage("El numero identificación se encuentra incorrecto."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:identificacionEntidadExterna", new FacesMessage("El numero identificación es obligatorio."));
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarDatosNumericosEntidadExterna(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(telefono1EntidadExterna) && (!telefono1EntidadExterna.isEmpty())  && (telefono1EntidadExterna.trim().length() > 0)) {
                if ((Utilidades.isNumber(telefono1EntidadExterna)) == false) {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono1EntidadExterna", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTel1 = true;
                }
            }
        } else {
            if (Utilidades.validarNulo(telefono2EntidadExterna) && (!telefono2EntidadExterna.isEmpty())  && (telefono2EntidadExterna.trim().length() > 0)) {
                if ((Utilidades.isNumber(telefono2EntidadExterna)) == false) {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono2EntidadExterna", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTel2 = true;
                }
            }
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarDireccionEntidadExterna() {
        if ((Utilidades.validarNulo(direccionEntidadExterna)) && (!direccionEntidadExterna.isEmpty())  && (direccionEntidadExterna.trim().length() > 0)) {
            if (Utilidades.validarDirecciones(direccionEntidadExterna)) {
                validacionesDireccion = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:direccionEntidadExterna", new FacesMessage("La dirección se encuentra incorrecta."));
                validacionesDireccion = false;
            }
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarDatosEntidadExterna(int tipo) {
        if (tipo == 1) {
            if (Utilidades.validarNulo(idUnicoEntidadExterna) && (!idUnicoEntidadExterna.isEmpty())  && (idUnicoEntidadExterna.trim().length() > 0)) {
                if (Utilidades.validarCaracteresAlfaNumericos(idUnicoEntidadExterna)) {
                    EntidadExterna registro = administrarEntidadesExternasBO.obtenerEntidadExternaPorIdentificacion(idUnicoEntidadExterna);
                    if (null == registro) {
                        validacionesIDEntidad = true;
                    } else {
                        if (!entidadExternaDetalles.getIdentidadexterna().equals(registro.getIdentidadexterna())) {
                            validacionesIDEntidad = false;
                            FacesContext.getCurrentInstance().addMessage("form:idUnicoEntidadExterna", new FacesMessage("El documento ya se encuentra registrado."));
                        } else {
                            validacionesIDEntidad = true;
                        }
                    }
                } else {
                    validacionesIDEntidad = false;
                    FacesContext.getCurrentInstance().addMessage("form:idUnicoEntidadExterna", new FacesMessage("El numero identificación se encuentra incorrecto."));
                }
            } else {
                validacionesIDEntidad = false;
                FacesContext.getCurrentInstance().addMessage("form:idUnicoEntidadExterna", new FacesMessage("El numero identificación es obligatorio."));
            }
        }
        if (tipo == 2) {
            if (Utilidades.validarNulo(nombreUnicoEntidadExterna) && (!nombreUnicoEntidadExterna.isEmpty())  && (nombreUnicoEntidadExterna.trim().length() > 0)) {
                if (!Utilidades.validarCaracterString(nombreUnicoEntidadExterna)) {
                    validacionesNombreEntidad = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreUnicoEntidadExterna", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombreEntidad = true;
                }
            }
        }
        if (tipo == 3) {
            if (Utilidades.validarNulo(emailUnicoEntidadExterna) && (!emailUnicoEntidadExterna.isEmpty())  && (emailUnicoEntidadExterna.trim().length() > 0)) {
                if (Utilidades.validarCorreoElectronico(emailUnicoEntidadExterna)) {
                    validacionesEmailEntidad = true;
                } else {
                    validacionesEmailEntidad = false;
                    FacesContext.getCurrentInstance().addMessage("form:emailUnicoEntidadExterna", new FacesMessage("El correo se encuentra incorrecto."));
                }
            }
        }
        modificacionesRegistroEntidadExterna();
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesApellido == false) {
            retorno = false;
        }
        if (validacionesEmailEntidad == false) {
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
        if (validacionesIDEntidad == false) {
            retorno = false;
        }
        if (validacionesNombreEntidad == false) {
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
     * del nuevo encargado
     */
    public void almacenarModificacionesEntidadExterna() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                modificarInformacionEntidadExterna();
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No se presento algun cambio en el registro. No se realizo ningun proceso de almacenamiento.";
        }
    }

    /**
     * Metodo encargado de almacenar en el sistema de información las
     * modificaciones del registro
     */
    public void modificarInformacionEntidadExterna() {
        try {
            entidadExternaDetalles.getPersona().setApellidospersona(apellidoEntidadExterna);
            entidadExternaDetalles.getPersona().setDireccionpersona(direccionEntidadExterna);
            entidadExternaDetalles.getPersona().setEmailpersona(correoEntidadExterna);
            entidadExternaDetalles.getPersona().setIdentificacionpersona(identificacionEntidadExterna);
            entidadExternaDetalles.getPersona().setNombrespersona(nombreEntidadExterna);
            entidadExternaDetalles.getPersona().setTelefono1persona(telefono1EntidadExterna);
            entidadExternaDetalles.getPersona().setTelefono2persona(telefono2EntidadExterna);
            entidadExternaDetalles.setNombreentidad(nombreUnicoEntidadExterna);
            entidadExternaDetalles.setEmailentidad(emailUnicoEntidadExterna);
            entidadExternaDetalles.setIdentificacionentidad(idUnicoEntidadExterna);
            administrarEntidadesExternasBO.actualizarInformacionPersona(entidadExternaDetalles.getPersona());
            administrarEntidadesExternasBO.actualizarInformacionEntidadExterna(entidadExternaDetalles);
            restaurarInformacionEntidadExterna();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEntidadExterna modificarInformacionEntidadExterna:  " + e.toString());
            System.out.println("Error ControllerDetallesEntidadExterna modificarInformacionEntidadExterna : " + e.toString());
        }
    }

    /**
     * Metodo encargado de registrar alguna modificación del registro
     */
    public void modificacionesRegistroEntidadExterna() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    /**
     * Metodo encargado de activar la entidad externa
     */
    public void activarEntidadExterna() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(true);
                entidadExternaDetalles.getPersona().getUsuario().setEstado(bool);
                administrarEntidadesExternasBO.actualizarInformacionUsuario(entidadExternaDetalles.getPersona().getUsuario());
                restaurarInformacionEntidadExterna();
                colorMensaje = "green";
                mensajeFormulario = "Se ha activado la entidad/convenio.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEntidadExterna activarEntidadExterna:  " + e.toString());
            System.out.println("Error ControllerDetallesEntidadesExternas activarEntidadExterna : " + e.toString());
        }
    }

    /**
     * Metodo encargado de inactivar la entidad externa
     */
    public void inactivarEntidadExterna() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(false);
                entidadExternaDetalles.getPersona().getUsuario().setEstado(bool);
                administrarEntidadesExternasBO.actualizarInformacionUsuario(entidadExternaDetalles.getPersona().getUsuario());
                entidadExternaDetalles = new EntidadExterna();
                restaurarInformacionEntidadExterna();
                colorMensaje = "green";
                mensajeFormulario = "Se ha inactivado la entidad/convenio.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEntidadExterna inactivarEntidadExterna:  " + e.toString());
            System.out.println("Error ControllerDetallesEntidadesExternas inactivarEntidadExterna : " + e.toString());
        }
    }

    // GET - SET
    public EntidadExterna getEntidadExternaDetalles() {
        return entidadExternaDetalles;
    }

    public void setEntidadExternaDetalles(EntidadExterna entidadExternaDetalles) {
        this.entidadExternaDetalles = entidadExternaDetalles;
    }

    public BigInteger getIdEntidadExterna() {
        return idEntidadExterna;
    }

    public void setIdEntidadExterna(BigInteger idEntidadExterna) {
        this.idEntidadExterna = idEntidadExterna;
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

    public String getNombreEntidadExterna() {
        return nombreEntidadExterna;
    }

    public void setNombreEntidadExterna(String nombreEntidadExterna) {
        this.nombreEntidadExterna = nombreEntidadExterna;
    }

    public String getApellidoEntidadExterna() {
        return apellidoEntidadExterna;
    }

    public void setApellidoEntidadExterna(String apellidoEntidadExterna) {
        this.apellidoEntidadExterna = apellidoEntidadExterna;
    }

    public String getCorreoEntidadExterna() {
        return correoEntidadExterna;
    }

    public void setCorreoEntidadExterna(String correoEntidadExterna) {
        this.correoEntidadExterna = correoEntidadExterna;
    }

    public String getIdentificacionEntidadExterna() {
        return identificacionEntidadExterna;
    }

    public void setIdentificacionEntidadExterna(String identificacionEntidadExterna) {
        this.identificacionEntidadExterna = identificacionEntidadExterna;
    }

    public String getTelefono1EntidadExterna() {
        return telefono1EntidadExterna;
    }

    public void setTelefono1EntidadExterna(String telefono1EntidadExterna) {
        this.telefono1EntidadExterna = telefono1EntidadExterna;
    }

    public String getTelefono2EntidadExterna() {
        return telefono2EntidadExterna;
    }

    public void setTelefono2EntidadExterna(String telefono2EntidadExterna) {
        this.telefono2EntidadExterna = telefono2EntidadExterna;
    }

    public String getDireccionEntidadExterna() {
        return direccionEntidadExterna;
    }

    public void setDireccionEntidadExterna(String direccionEntidadExterna) {
        this.direccionEntidadExterna = direccionEntidadExterna;
    }

    public String getIdUnicoEntidadExterna() {
        return idUnicoEntidadExterna;
    }

    public void setIdUnicoEntidadExterna(String idUnicoEntidadExterna) {
        this.idUnicoEntidadExterna = idUnicoEntidadExterna;
    }

    public String getNombreUnicoEntidadExterna() {
        return nombreUnicoEntidadExterna;
    }

    public void setNombreUnicoEntidadExterna(String nombreUnicoEntidadExterna) {
        this.nombreUnicoEntidadExterna = nombreUnicoEntidadExterna;
    }

    public String getEmailUnicoEntidadExterna() {
        return emailUnicoEntidadExterna;
    }

    public void setEmailUnicoEntidadExterna(String emailUnicoEntidadExterna) {
        this.emailUnicoEntidadExterna = emailUnicoEntidadExterna;
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
