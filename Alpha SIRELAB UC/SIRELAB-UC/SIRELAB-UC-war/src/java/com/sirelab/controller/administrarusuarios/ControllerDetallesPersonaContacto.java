/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.usuarios.AdministrarPersonasContactoBOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.PersonaContacto;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesPersonaContacto implements Serializable {

    //
    @EJB
    AdministrarPersonasContactoBOInterface administrarPersonasContactoBO;
    //
    private PersonaContacto personaContactoEditar;
    private BigInteger idPersonaContacto;
    private String inputNombre, inputApellido, inputID, inputEmail, inputEmailOpc, inputTelefono1, inputTelefono2, inputDireccion;
    private boolean inputEstado;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpc;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesConvenioPorEntidad;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean modificacionRegistro;
    private boolean activarEstado;
    private List<ConvenioPorEntidad> listaCoveniosPorEntidad;
    private ConvenioPorEntidad inputConvenioPorEntidad;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerDetallesPersonaContacto() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            activarEstado = false;
        }
        BasicConfigurator.configure();
    }

    public void recibirIDPersonaContactoDetalles(BigInteger idregistro) {
        this.idPersonaContacto = idregistro;
        personaContactoEditar = administrarPersonasContactoBO.obtenerPersonaContactoPorId(idregistro);
        if (null != personaContactoEditar) {
            asignarValoresVariablesPersonaContacto();
        }
    }

    /**
     * Metodo encargado de asignar los valores de la entidad externa que sera
     * visualizado
     */
    public void asignarValoresVariablesPersonaContacto() {
        validacionesNombre = true;
        mensajeError = "";
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesCorreoOpc = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesConvenioPorEntidad = true;
        validacionesDireccion = true;
        modificacionRegistro = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        inputConvenioPorEntidad = personaContactoEditar.getConvenioporentidad();
        inputNombre = personaContactoEditar.getPersona().getNombrespersona();
        inputApellido = personaContactoEditar.getPersona().getApellidospersona();
        inputEmail = personaContactoEditar.getPersona().getEmailpersona();
        inputEmailOpc = personaContactoEditar.getPersona().getEmailsecundario();
        inputEstado = personaContactoEditar.getPersona().getUsuario().getEstado();
        inputID = personaContactoEditar.getPersona().getIdentificacionpersona();
        inputTelefono1 = personaContactoEditar.getPersona().getTelefono1persona();
        inputTelefono2 = personaContactoEditar.getPersona().getTelefono2persona();
        inputDireccion = personaContactoEditar.getPersona().getDireccionpersona();
        listaCoveniosPorEntidad = administrarPersonasContactoBO.obtenerConveniosPorEntidadRegistrados();
    }

    /**
     * Metodo encargado de restaurar la información de la entidad externa
     */
    public String restaurarInformacionPersonaContacto() {
        personaContactoEditar = new PersonaContacto();
        personaContactoEditar = administrarPersonasContactoBO.obtenerPersonaContactoPorId(this.idPersonaContacto);
        asignarValoresVariablesPersonaContacto();
        modificacionRegistro = false;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        return "administrarpersonacontacto";
    }

    public void actualizarEstado() {
        modificacionRegistro = true;
    }

    public void validarNombrePersonaContacto() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty()) && (inputNombre.trim().length() > 0)) {
            int tam = inputNombre.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(inputNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.USUARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.USUARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre es obligatorio. " + constantes.USUARIO_NOMBRE));
        }
        modificacionRegistro = true;
    }

    public void validarApellidoPersonaContacto() {
        if (Utilidades.validarNulo(inputApellido) && (!inputApellido.isEmpty()) && (inputApellido.trim().length() > 0)) {
            int tam = inputApellido.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(inputApellido)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido ingresado es incorrecto. " + constantes.USUARIO_APELLIDO));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.USUARIO_APELLIDO));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido es obligatorio. " + constantes.USUARIO_APELLIDO));
        }
        modificacionRegistro = true;
    }

    public void validarCorreoPersonaContacto() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty()) && (inputEmail.trim().length() > 0)) {
            int tam = inputEmail.length();
            if (tam >= 4) {
                String correoPersonaContacto = inputEmail;
                if (Utilidades.validarCorreoElectronico(correoPersonaContacto)) {
                    PersonaContacto registro = administrarPersonasContactoBO.obtenerPersonaContactoPorCorreo(inputEmail);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        if (personaContactoEditar.getIdpersonacontacto().equals(registro.getIdpersonacontacto())) {
                            validacionesCorreo = false;
                            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo ingresado ya se encuentra registrado."));
                        } else {
                            validacionesCorreo = true;
                        }
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo ingresado es incorreccto. " + constantes.USUARIO_CORREO));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.USUARIO_CORREO));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio. " + constantes.USUARIO_CORREO));
        }
        modificacionRegistro = true;
    }

    public void validarCorreoOpcPersonaContacto() {
        if (Utilidades.validarNulo(inputEmailOpc) && (!inputEmailOpc.isEmpty()) && (inputEmailOpc.trim().length() > 0)) {
            int tam = inputEmailOpc.length();
            if (tam >= 15) {
                String correoPersonaContacto = inputEmailOpc;
                if (Utilidades.validarCorreoElectronico(correoPersonaContacto)) {
                    validacionesCorreoOpc = true;
                } else {
                    validacionesCorreoOpc = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmailOpc", new FacesMessage("El correo ingresado es incorrecto. " + constantes.USUARIO_CORREO_OPC));
                }
            } else {
                validacionesCorreoOpc = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEmailOpc", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.USUARIO_CORREO_OPC));
            }
        }
        modificacionRegistro = true;
    }

    public void validarIdentificacionPersonaContacto() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty()) && (inputID.trim().length() > 0)) {
            int tam = inputID.length();
            if (tam >= 6) {
                if (Utilidades.validarNumeroIdentificacion(inputID)) {
                    validacionesID = true;
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación se encuentra incorrecto. " + constantes.USUARIO_ID));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.USUARIO_ID));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación es obligatorio. " + constantes.USUARIO_ID));
        }
        modificacionRegistro = true;
    }

    public void validarDireccionPersonaContacto() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty()) && (inputDireccion.trim().length() > 0)) {
            int tam = inputDireccion.length();
            if (tam >= 8) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputDireccion)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("La dirección es incorrecta. " + constantes.USUARIO_DIRECCION));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. " + constantes.USUARIO_DIRECCION));
                validacionesDireccion = false;
            }
        }
    }

    public void validarDatosNumericosPersonaContacto(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty()) && (inputTelefono1.trim().length() > 0)) {
                int tam = inputTelefono1.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(inputTelefono1)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.USUARIO_TELFIJO));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.USUARIO_TELFIJO));
                }
            }
        } else {
            if (Utilidades.validarNulo(inputTelefono2) && (!inputTelefono2.isEmpty()) && (inputTelefono2.trim().length() > 0)) {
                int tam = inputTelefono2.length();
                if (tam == 10) {
                    if ((Utilidades.isNumber(inputTelefono2)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.USUARIO_TELCEL));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.USUARIO_TELCEL));
                }
            }
        }
        modificacionRegistro = true;
    }

    public void validarConvenioPersonaContacto() {
        if (Utilidades.validarNulo(inputConvenioPorEntidad)) {
            validacionesConvenioPorEntidad = true;
        } else {
            validacionesConvenioPorEntidad = false;
            FacesContext.getCurrentInstance().addMessage("form:inputConvenioPorEntidad", new FacesMessage("El convenio y su asociación con entidad es obligatorio."));
        }
        modificacionRegistro = true;
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesApellido == false) {
            mensajeError = mensajeError + "- Apellido - ";
            retorno = false;
        }
        if (validacionesCorreo == false) {
            mensajeError = mensajeError + "- Correo Principal - ";
            retorno = false;
        }
        if (validacionesDireccion == false) {
            mensajeError = mensajeError + "- Dirección - ";
            retorno = false;
        }
        if (validacionesID == false) {
            mensajeError = mensajeError + "- Identificación - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + "- Nombre - ";
            retorno = false;
        }
        if (validacionesTel1 == false) {
            mensajeError = mensajeError + "- Telefono Fijo - ";
            retorno = false;
        }
        if (validacionesCorreoOpc == false) {
            mensajeError = mensajeError + "- Correo Opcional - ";
            retorno = false;
        }
        if (validacionesTel2 == false) {
            mensajeError = mensajeError + "- Telefono Celular - ";
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarModificacionPersonaContacto() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                almacenarModificacionPersonaContacto();
                recibirIDPersonaContactoDetalles(this.idPersonaContacto);
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No se presento algun cambio en el registro. No se realizo ningun proceso de almacenamiento.";
        }
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información el nuevo
     * docente
     */
    private void almacenarModificacionPersonaContacto() {
        try {

            personaContactoEditar.getPersona().setApellidospersona(inputApellido);
            personaContactoEditar.getPersona().setDireccionpersona(inputDireccion);
            personaContactoEditar.getPersona().setEmailpersona(inputEmail);
            personaContactoEditar.getPersona().setIdentificacionpersona(inputID);
            personaContactoEditar.getPersona().setNombrespersona(inputNombre);
            personaContactoEditar.getPersona().setTelefono1persona(inputTelefono1);
            personaContactoEditar.getPersona().setTelefono2persona(inputTelefono2);
            personaContactoEditar.getPersona().setDireccionpersona(inputDireccion);
            personaContactoEditar.getPersona().setEmailsecundario(inputEmailOpc);
            personaContactoEditar.getPersona().getUsuario().setEstado(inputEstado);
            personaContactoEditar.setConvenioporentidad(inputConvenioPorEntidad);
            administrarPersonasContactoBO.editarPersonaContado(personaContactoEditar);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarPersonaContacto almacenarModificacionPersonaContacto:  " + e.toString(), e);
            logger.error("Error ControllerRegistrarPersonaContacto almacenarModificacionPersonaContacto : " + e.toString(), e);
        }
    }

    //GET-SET
    public PersonaContacto getPersonaContactoEditar() {
        return personaContactoEditar;
    }

    public void setPersonaContactoEditar(PersonaContacto personaContactoEditar) {
        this.personaContactoEditar = personaContactoEditar;
    }

    public BigInteger getIdPersonaContacto() {
        return idPersonaContacto;
    }

    public void setIdPersonaContacto(BigInteger idPersonaContacto) {
        this.idPersonaContacto = idPersonaContacto;
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

    public String getInputDireccion() {
        return inputDireccion;
    }

    public void setInputDireccion(String inputDireccion) {
        this.inputDireccion = inputDireccion;
    }

    public boolean isInputEstado() {
        return inputEstado;
    }

    public void setInputEstado(boolean inputEstado) {
        this.inputEstado = inputEstado;
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

    public boolean isModificacionRegistro() {
        return modificacionRegistro;
    }

    public void setModificacionRegistro(boolean modificacionRegistro) {
        this.modificacionRegistro = modificacionRegistro;
    }

    public boolean isActivarEstado() {
        return activarEstado;
    }

    public void setActivarEstado(boolean activarEstado) {
        this.activarEstado = activarEstado;
    }

    public List<ConvenioPorEntidad> getListaCoveniosPorEntidad() {
        return listaCoveniosPorEntidad;
    }

    public void setListaCoveniosPorEntidad(List<ConvenioPorEntidad> listaCoveniosPorEntidad) {
        this.listaCoveniosPorEntidad = listaCoveniosPorEntidad;
    }

    public ConvenioPorEntidad getInputConvenioPorEntidad() {
        return inputConvenioPorEntidad;
    }

    public void setInputConvenioPorEntidad(ConvenioPorEntidad inputConvenioPorEntidad) {
        this.inputConvenioPorEntidad = inputConvenioPorEntidad;
    }

    public String getInputEmailOpc() {
        return inputEmailOpc;
    }

    public void setInputEmailOpc(String inputEmailOpc) {
        this.inputEmailOpc = inputEmailOpc;
    }

}
