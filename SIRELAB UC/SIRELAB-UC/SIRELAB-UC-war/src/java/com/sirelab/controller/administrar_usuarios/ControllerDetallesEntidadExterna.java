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
    private String nombreEntidadExterna, sectorEntidadExterna, correoEntidadExterna, correoOpcEntidadExterna, identificacionEntidadExterna;
    private String telefono1EntidadExterna, telefono2EntidadExterna, direccionEntidadExterna;
    //
    private boolean validacionesNombre, validacionesSector, validacionesCorreo, validacionesCorreoOpc;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesEntidadExterna() {
    }

    @PostConstruct
    public void init() {
        colorMensaje = "black";
        validacionesNombre = true;
        validacionesSector = true;
        validacionesCorreo = true;
        validacionesCorreoOpc = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
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
        nombreEntidadExterna = entidadExternaDetalles.getPersona().getNombrespersona();
        sectorEntidadExterna = entidadExternaDetalles.getSector();
        correoEntidadExterna = entidadExternaDetalles.getPersona().getEmailpersona();
        identificacionEntidadExterna = entidadExternaDetalles.getPersona().getIdentificacionpersona();
        telefono1EntidadExterna = entidadExternaDetalles.getPersona().getTelefono1persona();
        telefono2EntidadExterna = entidadExternaDetalles.getPersona().getTelefono2persona();
        direccionEntidadExterna = entidadExternaDetalles.getPersona().getDireccionpersona();
        correoOpcEntidadExterna = entidadExternaDetalles.getPersona().getEmailsecundario();
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
        validacionesSector = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        return "administrar_entidadesxternas";
    }

    public void validarNombreEntidadExterna() {
        if (Utilidades.validarNulo(nombreEntidadExterna) && (!nombreEntidadExterna.isEmpty()) && (nombreEntidadExterna.trim().length() > 0)) {
            int tam = nombreEntidadExterna.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nombreEntidadExterna)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreEntidadExterna", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreEntidadExterna", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreEntidadExterna", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarSectorEntidadExterna() {
        if (Utilidades.validarNulo(sectorEntidadExterna) && (!sectorEntidadExterna.isEmpty()) && (sectorEntidadExterna.trim().length() > 0)) {
            int tam = sectorEntidadExterna.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(sectorEntidadExterna)) {
                    validacionesSector = false;
                    FacesContext.getCurrentInstance().addMessage("form:sectorEntidadExterna", new FacesMessage("El sector ingresado es incorrecto."));
                } else {
                    validacionesSector = true;
                }
            } else {
                validacionesSector = false;
                FacesContext.getCurrentInstance().addMessage("form:sectorEntidadExterna", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesSector = false;
            FacesContext.getCurrentInstance().addMessage("form:sectorEntidadExterna", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarCorreoEntidadExterna() {
        if (Utilidades.validarNulo(correoEntidadExterna) && (!correoEntidadExterna.isEmpty()) && (correoEntidadExterna.trim().length() > 0)) {
            int tam = correoEntidadExterna.length();
            if (tam >= 15) {
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
                FacesContext.getCurrentInstance().addMessage("form:correoEntidadExterna", new FacesMessage("El tamaño minimo permitido es 15 caracteres."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:correoEntidadExterna", new FacesMessage("El correo es obligatorio."));
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarCorreoOpcEntidadExterna() {
        if (Utilidades.validarNulo(correoOpcEntidadExterna) && (!correoOpcEntidadExterna.isEmpty()) && (correoOpcEntidadExterna.trim().length() > 0)) {
            int tam = correoOpcEntidadExterna.length();
            if (tam >= 15) {
                if (Utilidades.validarCorreoElectronico(correoOpcEntidadExterna)) {
                    validacionesCorreoOpc = true;
                } else {
                    validacionesCorreoOpc = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoOpcEntidadExterna", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreoOpc = false;
                FacesContext.getCurrentInstance().addMessage("form:correoOpcEntidadExterna", new FacesMessage("El tamaño minimo permitido es 15 caracteres."));
            }
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarIdentificacionEntidadExterna() {
        if (Utilidades.validarNulo(identificacionEntidadExterna) && (!identificacionEntidadExterna.isEmpty()) && (identificacionEntidadExterna.trim().length() > 0)) {
            int tam = identificacionEntidadExterna.length();
            if (tam >= 8) {
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
                FacesContext.getCurrentInstance().addMessage("form:identificacionEntidadExterna", new FacesMessage("El tamaño minimo permitido es 15 caracteres."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:identificacionEntidadExterna", new FacesMessage("El numero identificación es obligatorio."));
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarDatosNumericosEntidadExterna(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(telefono1EntidadExterna) && (!telefono1EntidadExterna.isEmpty()) && (telefono1EntidadExterna.trim().length() > 0)) {
                int tam = telefono1EntidadExterna.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(telefono1EntidadExterna)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono1EntidadExterna", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono1EntidadExterna", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        } else {
            if (Utilidades.validarNulo(telefono2EntidadExterna) && (!telefono2EntidadExterna.isEmpty()) && (telefono2EntidadExterna.trim().length() > 0)) {
                int tam = telefono2EntidadExterna.length();
                if (tam == 10) {
                    if ((Utilidades.isNumber(telefono2EntidadExterna)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono2EntidadExterna", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono2EntidadExterna", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        }
        modificacionesRegistroEntidadExterna();
    }

    public void validarDireccionEntidadExterna() {
        if ((Utilidades.validarNulo(direccionEntidadExterna)) && (!direccionEntidadExterna.isEmpty()) && (direccionEntidadExterna.trim().length() > 0)) {
            int tam = direccionEntidadExterna.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(direccionEntidadExterna)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:direccionEntidadExterna", new FacesMessage("La dirección se encuentra incorrecta."));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:direccionEntidadExterna", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
                validacionesDireccion = false;
            }
        }
        modificacionesRegistroEntidadExterna();
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesSector == false) {
            retorno = false;
        }
        if (validacionesCorreo == false) {
            retorno = false;
        }
        if (validacionesCorreoOpc == false) {
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
            entidadExternaDetalles.getPersona().setApellidospersona(nombreEntidadExterna);
            entidadExternaDetalles.getPersona().setDireccionpersona(direccionEntidadExterna);
            entidadExternaDetalles.getPersona().setEmailpersona(correoEntidadExterna);
            entidadExternaDetalles.getPersona().setIdentificacionpersona(identificacionEntidadExterna);
            entidadExternaDetalles.getPersona().setNombrespersona(nombreEntidadExterna);
            entidadExternaDetalles.getPersona().setTelefono1persona(telefono1EntidadExterna);
            entidadExternaDetalles.getPersona().setTelefono2persona(telefono2EntidadExterna);
            entidadExternaDetalles.setSector(sectorEntidadExterna);
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

    private boolean validarCambioEstado() {
        boolean retorno = true;
        Boolean validacion = administrarEntidadesExternasBO.validarCambioEstadoEntidad(entidadExternaDetalles.getIdentidadexterna());
        if (null != validacion) {
            if (validacion == true) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    /**
     * Metodo encargado de activar la entidad externa
     */
    public void activarEntidadExterna() {
        try {
            if (modificacionRegistro == false) {
                entidadExternaDetalles.setEstado(true);
                administrarEntidadesExternasBO.actualizarInformacionEntidadExterna(entidadExternaDetalles);
                restaurarInformacionEntidadExterna();
                colorMensaje = "green";
                mensajeFormulario = "Se ha activado la entidad externa.";
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
                if (validarCambioEstado() == true) {
                    entidadExternaDetalles.setEstado(false);
                    administrarEntidadesExternasBO.actualizarInformacionEntidadExterna(entidadExternaDetalles);
                    entidadExternaDetalles = new EntidadExterna();
                    restaurarInformacionEntidadExterna();
                    colorMensaje = "green";
                    mensajeFormulario = "Se ha inactivado la entidad externa.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "Existen convenios asociados. Imposible cambiar de estado.";
                }
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

    public String getSectorEntidadExterna() {
        return sectorEntidadExterna;
    }

    public void setSectorEntidadExterna(String sectorEntidadExterna) {
        this.sectorEntidadExterna = sectorEntidadExterna;
    }

    public String getCorreoOpcEntidadExterna() {
        return correoOpcEntidadExterna;
    }

    public void setCorreoOpcEntidadExterna(String correoOpcEntidadExterna) {
        this.correoOpcEntidadExterna = correoOpcEntidadExterna;
    }
}
