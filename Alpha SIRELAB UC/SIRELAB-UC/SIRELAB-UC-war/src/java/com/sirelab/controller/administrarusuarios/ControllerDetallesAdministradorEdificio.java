/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresEdificioBOInterface;
import com.sirelab.entidades.AdministradorEdificio;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoPorEdificio;
import com.sirelab.entidades.Sede;
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
public class ControllerDetallesAdministradorEdificio implements Serializable {

    @EJB
    AdministrarAdministradoresEdificioBOInterface administrarAdministradoresEdificioBO;

    private EncargadoPorEdificio encargadoPorEdificioDetalles;
    private BigInteger idEncargadoPorEdificio;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private List<Sede> listaSede;
    private Sede sedeAdministradorEdificio;
    private List<Edificio> listaEdificio;
    private Edificio edificioAdministradorEdificio;
    private boolean activoEdificio;
    private String numAtencionAdministradorEdificio;
    private String nombreAdministradorEdificio, apellidoAdministradorEdificio, correoAdministradorEdificio, correoOpcionalAdministradorEdificio, identificacionAdministradorEdificio;
    private String telefono1AdministradorEdificio, telefono2AdministradorEdificio, direccionAdministradorEdificio;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2, validacionesNAtencion;
    private boolean validacionesDireccion, validacionesSede, validacionesEdificio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesAdministradorEdificio() {
    }

    @PostConstruct
    public void init() {
        colorMensaje = "black";
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            disabledEditar = false;
        }
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
    }

    /**
     * Metodo encargado de asignar los valores del encargado laboratorio que
     * sera visualizado
     */
    public void asignarValoresVariablesAdministradorEdificio() {
        nombreAdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getNombrespersona();
        apellidoAdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getApellidospersona();
        correoAdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getEmailpersona();
        correoOpcionalAdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getEmailsecundario();
        identificacionAdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getIdentificacionpersona();
        telefono1AdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getTelefono1persona();
        telefono2AdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getTelefono2persona();
        direccionAdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getDireccionpersona();
        sedeAdministradorEdificio = encargadoPorEdificioDetalles.getEdificio().getSede();
        edificioAdministradorEdificio = encargadoPorEdificioDetalles.getEdificio();
        numAtencionAdministradorEdificio = encargadoPorEdificioDetalles.getAdministradoredificio().getNumeroatencion();
        listaSede = administrarAdministradoresEdificioBO.obtenerListaSedes();
        if (null != sedeAdministradorEdificio) {
            listaEdificio = administrarAdministradoresEdificioBO.obtenerEdificiosPorIDSede(sedeAdministradorEdificio.getIdsede());
        }
        validacionesNAtencion = true;
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesCorreoOpcional = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesSede = true;
        validacionesEdificio = true;
        //
        activoEdificio = true;
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
    }

    /**
     * Metodo encargado de recibir el ID del encargado laboratorio que sera
     * visualizado
     *
     * @param idEncargadoPorEdificio ID del encargadoLaboratorio
     */
    public void recibirIDEncargadoEdificioDetalles(BigInteger idEncargadoPorEdificio) {
        this.idEncargadoPorEdificio = idEncargadoPorEdificio;
        encargadoPorEdificioDetalles = administrarAdministradoresEdificioBO.obtenerEncargadoPorEdificioPorIDEncargadoPorEdificio(idEncargadoPorEdificio);
        if (encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesAdministradorEdificio();
    }

    /**
     * Metodo encargado de activar las opciones de editar
     */
    public void activarEditarRegistro() {
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        activoEdificio = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    /**
     * Metodo encargado de restaurar la información del encargado laboratorio
     */
    public String restaurarInformacionAdministradorEdificio() {
        encargadoPorEdificioDetalles = new EncargadoPorEdificio();
        encargadoPorEdificioDetalles = administrarAdministradoresEdificioBO.obtenerEncargadoPorEdificioPorIDEncargadoPorEdificio(idEncargadoPorEdificio);
        if (encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }

        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        activoEdificio = true;
        listaEdificio = null;
        listaSede = null;
        validacionesCorreoOpcional = true;
        validacionesNAtencion = true;
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesSede = true;
        validacionesEdificio = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        asignarValoresVariablesAdministradorEdificio();
        return "administraradministradoresedificio";
    }

    /**
     * Metodo encargado de actualizar la sede y obtener la informacion de los
     * departaments relacionados
     */
    public void actualizarSedes() {
        try {
            if (Utilidades.validarNulo(sedeAdministradorEdificio)) {
                activoEdificio = false;
                edificioAdministradorEdificio = null;
                listaEdificio = administrarAdministradoresEdificioBO.obtenerEdificiosPorIDSede(sedeAdministradorEdificio.getIdsede());
                validacionesSede = true;
            } else {
                validacionesEdificio = false;
                validacionesSede = false;
                activoEdificio = true;
                edificioAdministradorEdificio = null;
                listaEdificio = null;
                FacesContext.getCurrentInstance().addMessage("form:sedeAdministradorEdificio", new FacesMessage("El campo Sede es obligatorio."));
            }
            modificacionesRegistroAdministradorEdificio();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAdministradorEdificio actualizarSedes:  " + e.toString());
            System.out.println("Error ControllerDetallesAdministradorEdificio actualizarSedes : " + e.toString());
        }
    }

    /**
     * Metodo encargado de actualizar los edificios y obtener la informacion de
     * las carreras relacionadas
     */
    public void actualizarEdificios() {
        try {
            if (Utilidades.validarNulo(edificioAdministradorEdificio)) {
                validacionesEdificio = true;
            } else {
                validacionesEdificio = false;
                FacesContext.getCurrentInstance().addMessage("form:edificioAdministradorEdificio", new FacesMessage("El campo Edificio es obligatorio."));
            }
            modificacionesRegistroAdministradorEdificio();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAdministradorEdificio actualizarEdificios:  " + e.toString());
            System.out.println("Error ControllerDetallesAdministradorEdificio actualizarEdificios : " + e.toString());
        }
    }

    public void validarNAtencionAdministradorEdificio() {
        if (Utilidades.validarNulo(numAtencionAdministradorEdificio) && (!numAtencionAdministradorEdificio.isEmpty()) && (numAtencionAdministradorEdificio.trim().length() > 0)) {
            if (!Utilidades.validarCaracterString(numAtencionAdministradorEdificio)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:numAtencionAdministradorEdificio", new FacesMessage("El Número ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        }
        modificacionesRegistroAdministradorEdificio();
    }

    public void validarNombreAdministradorEdificio() {
        if (Utilidades.validarNulo(nombreAdministradorEdificio) && (!nombreAdministradorEdificio.isEmpty()) && (nombreAdministradorEdificio.trim().length() > 0)) {
            int tam = nombreAdministradorEdificio.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nombreAdministradorEdificio)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreAdministradorEdificio", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreAdministradorEdificio", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreAdministradorEdificio", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistroAdministradorEdificio();
    }

    public void validarApellidoAdministradorEdificio() {
        if (Utilidades.validarNulo(apellidoAdministradorEdificio) && (!apellidoAdministradorEdificio.isEmpty()) && (apellidoAdministradorEdificio.trim().length() > 0)) {
            int tam = apellidoAdministradorEdificio.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(apellidoAdministradorEdificio)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:apellidoAdministradorEdificio", new FacesMessage("El apellido ingresado es incorrecto."));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:apellidoAdministradorEdificio", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:apellidoAdministradorEdificio", new FacesMessage("El apellido es obligatorio."));
        }
        modificacionesRegistroAdministradorEdificio();
    }

    public void validarCorreoAdministradorEdificio() {
        if (Utilidades.validarNulo(correoAdministradorEdificio) && (!correoAdministradorEdificio.isEmpty()) && (correoAdministradorEdificio.trim().length() > 0)) {
            int tam = correoAdministradorEdificio.length();
            if (tam >= 4) {
                String correo = correoAdministradorEdificio + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correo)) {
                    AdministradorEdificio registro = administrarAdministradoresEdificioBO.obtenerAdministradorEdificioPorCorreo(correoAdministradorEdificio);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        if (!encargadoPorEdificioDetalles.getAdministradoredificio().getIdadministradoredificio().equals(registro.getIdadministradoredificio())) {
                            validacionesCorreo = false;
                            FacesContext.getCurrentInstance().addMessage("form:correoAdministradorEdificio", new FacesMessage("El correo ya se encuentra registrado."));
                        } else {
                            validacionesCorreo = true;
                        }
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoAdministradorEdificio", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:correoAdministradorEdificio", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:correoAdministradorEdificio", new FacesMessage("El correo es obligatorio."));
        }
        modificacionesRegistroAdministradorEdificio();
    }

    public void validarCorreoOpcionalAdministradorEdificio() {
        if (Utilidades.validarNulo(correoOpcionalAdministradorEdificio) && (!correoOpcionalAdministradorEdificio.isEmpty()) && (correoOpcionalAdministradorEdificio.trim().length() > 0)) {
            int tam = correoOpcionalAdministradorEdificio.length();
            if (tam >= 15) {
                if (Utilidades.validarCorreoElectronico(correoOpcionalAdministradorEdificio)) {
                    validacionesCorreoOpcional = true;
                } else {
                    validacionesCorreoOpcional = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoOpcionalAdministradorEdificio", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreoOpcional = false;
                FacesContext.getCurrentInstance().addMessage("form:correoOpcionalAdministradorEdificio", new FacesMessage("El tamaño minimo permitido es 15 caracteres."));
            }
        }
        modificacionesRegistroAdministradorEdificio();
    }

    public void validarIdentificacionAdministradorEdificio() {
        if (Utilidades.validarNulo(identificacionAdministradorEdificio) && (!identificacionAdministradorEdificio.isEmpty()) && (identificacionAdministradorEdificio.trim().length() > 0)) {
            int tam = identificacionAdministradorEdificio.length();
            if (tam >= 8) {
                if (Utilidades.validarNumeroIdentificacion(identificacionAdministradorEdificio)) {
                    AdministradorEdificio registro = administrarAdministradoresEdificioBO.obtenerAdministradorEdificioPorDocumento(identificacionAdministradorEdificio);
                    if (null == registro) {
                        validacionesID = true;
                    } else {
                        if (!encargadoPorEdificioDetalles.getAdministradoredificio().getIdadministradoredificio().equals(registro.getIdadministradoredificio())) {
                            validacionesID = false;
                            FacesContext.getCurrentInstance().addMessage("form:identificacionAdministradorEdificio", new FacesMessage("El documento ya se encuentra registrado."));
                        } else {
                            validacionesID = true;
                        }
                    }
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:identificacionAdministradorEdificio", new FacesMessage("El numero identificación se encuentra incorrecto."));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:identificacionAdministradorEdificio", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:identificacionAdministradorEdificio", new FacesMessage("El numero identificación es obligatorio."));
        }
        modificacionesRegistroAdministradorEdificio();
    }

    public void validarDatosNumericosAdministradorEdificio(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(telefono1AdministradorEdificio) && (!telefono1AdministradorEdificio.isEmpty()) && (telefono1AdministradorEdificio.trim().length() > 0)) {
                int tam = telefono1AdministradorEdificio.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(telefono1AdministradorEdificio)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono1AdministradorEdificio", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono1AdministradorEdificio", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        } else {
            if (Utilidades.validarNulo(telefono2AdministradorEdificio) && (!telefono2AdministradorEdificio.isEmpty()) && (telefono2AdministradorEdificio.trim().length() > 0)) {
                int tam = telefono2AdministradorEdificio.length();
                if (tam == 10) {
                    if ((Utilidades.isNumber(telefono2AdministradorEdificio)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono2AdministradorEdificio", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono2AdministradorEdificio", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        }
        modificacionesRegistroAdministradorEdificio();
    }

    public void validarDireccionAdministradorEdificio() {
        if ((Utilidades.validarNulo(direccionAdministradorEdificio)) && (!direccionAdministradorEdificio.isEmpty()) && (direccionAdministradorEdificio.trim().length() > 0)) {
            int tam = direccionAdministradorEdificio.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(direccionAdministradorEdificio)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:direccionAdministradorEdificio", new FacesMessage("La dirección se encuentra incorrecta."));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:direccionAdministradorEdificio", new FacesMessage("LEl tamaño minimo permitido es 8 caracteres."));
                validacionesDireccion = false;
            }
        }
        modificacionesRegistroAdministradorEdificio();
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesApellido == false) {
            retorno = false;
        }
        if (validacionesEdificio == false) {
            retorno = false;
        }
        if (validacionesCorreo == false) {
            retorno = false;
        }
        if (validacionesCorreoOpcional == false) {
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
        if (validacionesSede == false) {
            retorno = false;
        }
        if (validacionesTel1 == false) {
            retorno = false;
        }
        if (validacionesNAtencion == false) {
            retorno = false;
        }
        if (validacionesTel2 == false) {
            retorno = false;
        }
        return retorno;
    }

    public void almacenarModificacionesAdministradorEdificio() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                modificarInformacionAdministradorEdificio();
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
    public void modificarInformacionAdministradorEdificio() {
        try {
            encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().setApellidospersona(apellidoAdministradorEdificio);
            encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().setDireccionpersona(direccionAdministradorEdificio);
            encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().setEmailpersona(correoAdministradorEdificio);
            encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().setEmailsecundario(correoOpcionalAdministradorEdificio);
            encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().setIdentificacionpersona(identificacionAdministradorEdificio);
            encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().setNombrespersona(nombreAdministradorEdificio);
            encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().setTelefono1persona(telefono1AdministradorEdificio);
            encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().setTelefono2persona(telefono2AdministradorEdificio);
            encargadoPorEdificioDetalles.getAdministradoredificio().setNumeroatencion(numAtencionAdministradorEdificio);
            administrarAdministradoresEdificioBO.actualizarInformacionPersona(encargadoPorEdificioDetalles.getAdministradoredificio().getPersona());
            administrarAdministradoresEdificioBO.actualizarInformacionAdministradorEdificio(encargadoPorEdificioDetalles.getAdministradoredificio());
            restaurarInformacionAdministradorEdificio();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAdministradorEdificio modificarInformacionAdministradorEdificio:  " + e.toString());
            System.out.println("Error ControllerDetallesAdministradorEdificio modificarInformacionAdministradorEdificio : " + e.toString());

        }
    }

    /**
     * Metodo encargado de registrar alguna modificación del registro
     */
    public void modificacionesRegistroAdministradorEdificio() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    /**
     * Metodo encargado de activar el encargado laboratorio
     */
    public void activarAdministradorEdificio() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(true);
                encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getUsuario().setEstado(bool);
                administrarAdministradoresEdificioBO.actualizarInformacionUsuario(encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getUsuario());
                restaurarInformacionAdministradorEdificio();
                colorMensaje = "green";
                mensajeFormulario = "Se ha activado el personal de laboratorio.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAdministradorEdificio activarAdministradorEdificio:  " + e.toString());
            System.out.println("Error ControllerDetallesAdministradoresEdificio activarAdministradorEdificio : " + e.toString());
        }
    }

    /**
     * Metodo encargado de inactivar el encargado laboratorio
     */
    public void inactivarAdministradorEdificio() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(false);
                encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getUsuario().setEstado(bool);
                administrarAdministradoresEdificioBO.actualizarInformacionUsuario(encargadoPorEdificioDetalles.getAdministradoredificio().getPersona().getUsuario());
                encargadoPorEdificioDetalles = new EncargadoPorEdificio();
                restaurarInformacionAdministradorEdificio();
                colorMensaje = "green";
                mensajeFormulario = "Se ha inactivado el personal de laboratorio.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesAdministradorEdificio inactivarAdministradorEdificio:  " + e.toString());
            System.out.println("Error ControllerDetallesAdministradoresEdificio inactivarAdministradorEdificio : " + e.toString());
        }
    }

    // GET - SET
    public EncargadoPorEdificio getEncargadoPorEdificioDetalles() {
        return encargadoPorEdificioDetalles;
    }

    public void setEncargadoPorEdificioDetalles(EncargadoPorEdificio encargadoPorEdificioDetalles) {
        this.encargadoPorEdificioDetalles = encargadoPorEdificioDetalles;
    }

    public BigInteger getIdEncargadoPorEdificio() {
        return idEncargadoPorEdificio;
    }

    public void setIdEncargadoPorEdificio(BigInteger idEncargadoPorEdificio) {
        this.idEncargadoPorEdificio = idEncargadoPorEdificio;
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

    public List<Sede> getListaSede() {
        return listaSede;
    }

    public void setListaSede(List<Sede> listaSede) {
        this.listaSede = listaSede;
    }

    public Sede getSedeAdministradorEdificio() {
        return sedeAdministradorEdificio;
    }

    public void setSedeAdministradorEdificio(Sede sedeAdministradorEdificio) {
        this.sedeAdministradorEdificio = sedeAdministradorEdificio;
    }

    public List<Edificio> getListaEdificio() {
        return listaEdificio;
    }

    public void setListaEdificio(List<Edificio> listaEdificio) {
        this.listaEdificio = listaEdificio;
    }

    public Edificio getEdificioAdministradorEdificio() {
        return edificioAdministradorEdificio;
    }

    public void setEdificioAdministradorEdificio(Edificio edificioAdministradorEdificio) {
        this.edificioAdministradorEdificio = edificioAdministradorEdificio;
    }

    public boolean isActivoEdificio() {
        return activoEdificio;
    }

    public void setActivoEdificio(boolean activoEdificio) {
        this.activoEdificio = activoEdificio;
    }

    public String getNumAtencionAdministradorEdificio() {
        return numAtencionAdministradorEdificio;
    }

    public void setNumAtencionAdministradorEdificio(String numAtencionAdministradorEdificio) {
        this.numAtencionAdministradorEdificio = numAtencionAdministradorEdificio;
    }

    public String getNombreAdministradorEdificio() {
        return nombreAdministradorEdificio;
    }

    public void setNombreAdministradorEdificio(String nombreAdministradorEdificio) {
        this.nombreAdministradorEdificio = nombreAdministradorEdificio;
    }

    public String getApellidoAdministradorEdificio() {
        return apellidoAdministradorEdificio;
    }

    public void setApellidoAdministradorEdificio(String apellidoAdministradorEdificio) {
        this.apellidoAdministradorEdificio = apellidoAdministradorEdificio;
    }

    public String getCorreoAdministradorEdificio() {
        return correoAdministradorEdificio;
    }

    public void setCorreoAdministradorEdificio(String correoAdministradorEdificio) {
        this.correoAdministradorEdificio = correoAdministradorEdificio;
    }

    public String getCorreoOpcionalAdministradorEdificio() {
        return correoOpcionalAdministradorEdificio;
    }

    public void setCorreoOpcionalAdministradorEdificio(String correoOpcionalAdministradorEdificio) {
        this.correoOpcionalAdministradorEdificio = correoOpcionalAdministradorEdificio;
    }

    public String getIdentificacionAdministradorEdificio() {
        return identificacionAdministradorEdificio;
    }

    public void setIdentificacionAdministradorEdificio(String identificacionAdministradorEdificio) {
        this.identificacionAdministradorEdificio = identificacionAdministradorEdificio;
    }

    public String getTelefono1AdministradorEdificio() {
        return telefono1AdministradorEdificio;
    }

    public void setTelefono1AdministradorEdificio(String telefono1AdministradorEdificio) {
        this.telefono1AdministradorEdificio = telefono1AdministradorEdificio;
    }

    public String getTelefono2AdministradorEdificio() {
        return telefono2AdministradorEdificio;
    }

    public void setTelefono2AdministradorEdificio(String telefono2AdministradorEdificio) {
        this.telefono2AdministradorEdificio = telefono2AdministradorEdificio;
    }

    public String getDireccionAdministradorEdificio() {
        return direccionAdministradorEdificio;
    }

    public void setDireccionAdministradorEdificio(String direccionAdministradorEdificio) {
        this.direccionAdministradorEdificio = direccionAdministradorEdificio;
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