/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Persona;
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
 * Controlador: ControllerDetallesEncargadoLaboratorio Este controlador se
 * encarga del funcionamiento de la pagina detallesencargadolaboratorio.xhtml
 *
 * @author ANDRES PINEDA
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesEncargadoLaboratorio implements Serializable {

    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarEncargadosLaboratoriosBO;

    private EncargadoLaboratorio encargadoLaboratorioDetalles;
    private BigInteger idEncargadoLaboratorio;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private List<Facultad> listaFacultad;
    private Facultad facultadEncargadoLaboratorio;
    private List<Departamento> listaDepartamento;
    private Departamento departamentoEncargadoLaboratorio;
    private boolean activoDepartamento;
    private List<Laboratorio> listaLaboratorio;
    private Laboratorio laboratorioEncargadoLaboratorio;
    private boolean activoLaboratorio;
    private String nombreEncargadoLaboratorio, apellidoEncargadoLaboratorio, correoEncargadoLaboratorio, correoOpcionalEncargadoLaboratorio, identificacionEncargadoLaboratorio;
    private String telefono1EncargadoLaboratorio, telefono2EncargadoLaboratorio, direccionEncargadoLaboratorio;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesFacultad, validacionesDepartamento, validacionesLaboratorio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerDetallesEncargadoLaboratorio() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        colorMensaje = "black";
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        mensajeFormulario = "N/A";
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            disabledEditar = false;
        }
        BasicConfigurator.configure();
    }

    /**
     * Metodo encargado de asignar los valores del encargado laboratorio que
     * sera visualizado
     */
    public void asignarValoresVariablesEncargadoLaboratorio() {
        mensajeError = "";
        nombreEncargadoLaboratorio = encargadoLaboratorioDetalles.getPersona().getNombrespersona();
        apellidoEncargadoLaboratorio = encargadoLaboratorioDetalles.getPersona().getApellidospersona();
        correoEncargadoLaboratorio = encargadoLaboratorioDetalles.getPersona().getEmailpersona();
        correoOpcionalEncargadoLaboratorio = encargadoLaboratorioDetalles.getPersona().getEmailsecundario();
        identificacionEncargadoLaboratorio = encargadoLaboratorioDetalles.getPersona().getIdentificacionpersona();
        telefono1EncargadoLaboratorio = encargadoLaboratorioDetalles.getPersona().getTelefono1persona();
        telefono2EncargadoLaboratorio = encargadoLaboratorioDetalles.getPersona().getTelefono2persona();
        direccionEncargadoLaboratorio = encargadoLaboratorioDetalles.getPersona().getDireccionpersona();
        facultadEncargadoLaboratorio = encargadoLaboratorioDetalles.getLaboratorio().getDepartamento().getFacultad();
        departamentoEncargadoLaboratorio = encargadoLaboratorioDetalles.getLaboratorio().getDepartamento();
        laboratorioEncargadoLaboratorio = encargadoLaboratorioDetalles.getLaboratorio();
        listaFacultad = administrarEncargadosLaboratoriosBO.obtenerListaFacultades();
        if (null != facultadEncargadoLaboratorio) {
            listaDepartamento = administrarEncargadosLaboratoriosBO.obtenerDepartamentosPorIDFacultad(facultadEncargadoLaboratorio.getIdfacultad());
        }
        if (departamentoEncargadoLaboratorio != null) {
            listaLaboratorio = administrarEncargadosLaboratoriosBO.obtenerLaboratoriosPorIDDepartamento(departamentoEncargadoLaboratorio.getIddepartamento());
        }
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesCorreoOpcional = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesFacultad = true;
        validacionesDepartamento = true;
        validacionesLaboratorio = true;
        //
        activoDepartamento = true;
        activoLaboratorio = true;
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
    }

    /**
     * Metodo encargado de recibir el ID del encargado laboratorio que sera
     * visualizado
     *
     * @param idEncargadoLaboratorio ID del encargadoLaboratorio
     */
    public void recibirIDEncargadosLaboratoriosDetalles(BigInteger idEncargadoLaboratorio) {
        this.idEncargadoLaboratorio = idEncargadoLaboratorio;
        encargadoLaboratorioDetalles = administrarEncargadosLaboratoriosBO.obtenerEncargadoLaboratorioPorIDEncargadoLaboratorio(idEncargadoLaboratorio);
        if (encargadoLaboratorioDetalles.getPersona().getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesEncargadoLaboratorio();
        System.out.println("Llego");
    }

    /**
     * Metodo encargado de activar las opciones de editar
     */
    public void activarEditarRegistro() {
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        activoDepartamento = false;
        activoLaboratorio = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    /**
     * Metodo encargado de restaurar la información del encargado laboratorio
     */
    public String restaurarInformacionEncargadoLaboratorio() {
        mensajeError = "";
        encargadoLaboratorioDetalles = new EncargadoLaboratorio();
        encargadoLaboratorioDetalles = administrarEncargadosLaboratoriosBO.obtenerEncargadoLaboratorioPorIDEncargadoLaboratorio(idEncargadoLaboratorio);
        if (encargadoLaboratorioDetalles.getPersona().getUsuario().getEstado() == true) {
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
        activoDepartamento = true;
        activoLaboratorio = true;
        listaLaboratorio = null;
        listaDepartamento = null;
        listaFacultad = null;
        validacionesCorreoOpcional = true;
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesFacultad = true;
        validacionesDepartamento = true;
        validacionesLaboratorio = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        asignarValoresVariablesEncargadoLaboratorio();
        return "administrarencargadoslaboratorios";
    }

    /**
     * Metodo encargado de actualizar la facultad y obtener la informacion de
     * los departaments relacionados
     */
    public void actualizarFacultades() {
        try {
            if (Utilidades.validarNulo(facultadEncargadoLaboratorio)) {
                activoDepartamento = false;
                departamentoEncargadoLaboratorio = null;
                listaDepartamento = administrarEncargadosLaboratoriosBO.obtenerDepartamentosPorIDFacultad(facultadEncargadoLaboratorio.getIdfacultad());
                laboratorioEncargadoLaboratorio = null;
                listaLaboratorio = null;
                activoLaboratorio = true;
                validacionesFacultad = true;
            } else {
                validacionesDepartamento = false;
                validacionesFacultad = false;
                validacionesLaboratorio = false;
                activoDepartamento = true;
                departamentoEncargadoLaboratorio = null;
                listaDepartamento = null;
                activoLaboratorio = true;
                laboratorioEncargadoLaboratorio = null;
                listaLaboratorio = null;
                FacesContext.getCurrentInstance().addMessage("form:facultadEncargadoLaboratorio", new FacesMessage("El campo Facultad es obligatorio."));
            }
            modificacionesRegistroEncargadoLaboratorio();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEncargadoLaboratorio actualizarFacultades : " + e.toString(), e);
        }
    }

    /**
     * Metodo encargado de actualizar los departamentos y obtener la informacion
     * de las carreras relacionadas
     */
    public void actualizarDepartamentos() {
        try {
            if (Utilidades.validarNulo(departamentoEncargadoLaboratorio)) {
                activoLaboratorio = false;
                laboratorioEncargadoLaboratorio = null;
                listaLaboratorio = administrarEncargadosLaboratoriosBO.obtenerLaboratoriosPorIDDepartamento(departamentoEncargadoLaboratorio.getIddepartamento());
                validacionesDepartamento = true;
            } else {
                validacionesDepartamento = false;
                validacionesLaboratorio = false;
                activoLaboratorio = true;
                laboratorioEncargadoLaboratorio = null;
                listaLaboratorio = null;
                FacesContext.getCurrentInstance().addMessage("form:departamentoEncargadoLaboratorio", new FacesMessage("El campo Departamento es obligatorio."));
            }
            modificacionesRegistroEncargadoLaboratorio();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEncargadoLaboratorio actualizarDepartamentos:  " + e.toString(), e);
        }
    }

    public void actualizarLaboratorio() {
        if (Utilidades.validarNulo(laboratorioEncargadoLaboratorio)) {
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:laboratorioEncargadoLaboratorio", new FacesMessage("El campo Laboratorio es obligatorio."));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarNombreEncargadoLaboratorio() {
        if (Utilidades.validarNulo(nombreEncargadoLaboratorio) && (!nombreEncargadoLaboratorio.isEmpty()) && (nombreEncargadoLaboratorio.trim().length() > 0)) {
            int tam = nombreEncargadoLaboratorio.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nombreEncargadoLaboratorio)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreEncargadoLaboratorio", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.USUARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.USUARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreEncargadoLaboratorio", new FacesMessage("El nombre es obligatorio. " + constantes.USUARIO_NOMBRE));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarApellidoEncargadoLaboratorio() {
        if (Utilidades.validarNulo(apellidoEncargadoLaboratorio) && (!apellidoEncargadoLaboratorio.isEmpty()) && (apellidoEncargadoLaboratorio.trim().length() > 0)) {
            int tam = apellidoEncargadoLaboratorio.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(apellidoEncargadoLaboratorio)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:apellidoEncargadoLaboratorio", new FacesMessage("El apellido ingresado es incorrecto. " + constantes.USUARIO_APELLIDO));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:apellidoEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.USUARIO_APELLIDO));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:apellidoEncargadoLaboratorio", new FacesMessage("El apellido es obligatorio. " + constantes.USUARIO_APELLIDO));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarCorreoEncargadoLaboratorio() {
        if (Utilidades.validarNulo(correoEncargadoLaboratorio) && (!correoEncargadoLaboratorio.isEmpty()) && (correoEncargadoLaboratorio.trim().length() > 0)) {
            int tam = correoEncargadoLaboratorio.length();
            if (tam >= 4) {
                String correo = correoEncargadoLaboratorio + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correo)) {
                    Persona registro = administrarEncargadosLaboratoriosBO.obtenerPersonaSistemaPorCorreo(correoEncargadoLaboratorio);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        if (!encargadoLaboratorioDetalles.getPersona().getIdpersona().equals(registro.getIdpersona())) {
                            validacionesCorreo = false;
                            FacesContext.getCurrentInstance().addMessage("form:correoEncargadoLaboratorio", new FacesMessage("El correo ya se encuentra registrado."));
                        } else {
                            validacionesCorreo = true;
                        }
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoEncargadoLaboratorio", new FacesMessage("El correo se encuentra incorrecto. " + constantes.USUARIO_CORREO));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:correoEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.USUARIO_CORREO));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:correoEncargadoLaboratorio", new FacesMessage("El correo es obligatorio. " + constantes.USUARIO_CORREO));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarCorreoOpcionalEncargadoLaboratorio() {
        if (Utilidades.validarNulo(correoOpcionalEncargadoLaboratorio) && (!correoOpcionalEncargadoLaboratorio.isEmpty()) && (correoOpcionalEncargadoLaboratorio.trim().length() > 0)) {
            int tam = correoOpcionalEncargadoLaboratorio.length();
            if (tam >= 15) {
                if (Utilidades.validarCorreoElectronico(correoOpcionalEncargadoLaboratorio)) {
                    validacionesCorreoOpcional = true;
                } else {
                    validacionesCorreoOpcional = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoOpcionalEncargadoLaboratorio", new FacesMessage("El correo se encuentra incorrecto. " + constantes.USUARIO_CORREO_OPC));
                }
            } else {
                validacionesCorreoOpcional = false;
                FacesContext.getCurrentInstance().addMessage("form:correoOpcionalEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 15 caracteres. " + constantes.USUARIO_CORREO_OPC));
            }
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarIdentificacionEncargadoLaboratorio() {
        if (Utilidades.validarNulo(identificacionEncargadoLaboratorio) && (!identificacionEncargadoLaboratorio.isEmpty()) && (identificacionEncargadoLaboratorio.trim().length() > 0)) {
            int tam = identificacionEncargadoLaboratorio.length();
            if (tam >= 6) {
                if (Utilidades.validarNumeroIdentificacion(identificacionEncargadoLaboratorio)) {
                    Persona registro = administrarEncargadosLaboratoriosBO.obtenerPersonaSistemaPorIdentificacion(identificacionEncargadoLaboratorio);
                    if (null == registro) {
                        validacionesID = true;
                    } else {
                        if (!encargadoLaboratorioDetalles.getPersona().getIdpersona().equals(registro.getIdpersona())) {
                            validacionesID = false;
                            FacesContext.getCurrentInstance().addMessage("form:identificacionEncargadoLaboratorio", new FacesMessage("El documento ya se encuentra registrado."));
                        } else {
                            validacionesID = true;
                        }
                    }
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:identificacionEncargadoLaboratorio", new FacesMessage("El numero identificación se encuentra incorrecto. " + constantes.USUARIO_ID));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:identificacionEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.USUARIO_ID));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:identificacionEncargadoLaboratorio", new FacesMessage("El numero identificación es obligatorio. " + constantes.USUARIO_ID));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarDatosNumericosEncargadoLaboratorio(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(telefono1EncargadoLaboratorio) && (!telefono1EncargadoLaboratorio.isEmpty()) && (telefono1EncargadoLaboratorio.trim().length() > 0)) {
                validacionesTel1 = true;
            }
        } else {
            if (Utilidades.validarNulo(telefono2EncargadoLaboratorio) && (!telefono2EncargadoLaboratorio.isEmpty()) && (telefono2EncargadoLaboratorio.trim().length() > 0)) {
                validacionesTel2 = true;
            }
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarDireccionEncargadoLaboratorio() {
        if ((Utilidades.validarNulo(direccionEncargadoLaboratorio)) && (!direccionEncargadoLaboratorio.isEmpty()) && (direccionEncargadoLaboratorio.trim().length() > 0)) {
            int tam = direccionEncargadoLaboratorio.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(direccionEncargadoLaboratorio)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:direccionEncargadoLaboratorio", new FacesMessage("La dirección se encuentra incorrecta. " + constantes.USUARIO_DIRECCION));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:direccionEncargadoLaboratorio", new FacesMessage("LEl tamaño minimo permitido es 8 caracteres. " + constantes.USUARIO_DIRECCION));
                validacionesDireccion = false;
            }
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesApellido == false) {
            mensajeError = mensajeError + " - Apellido - ";
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            mensajeError = mensajeError + " - Departamento - ";
            retorno = false;
        }
        if (validacionesCorreo == false) {
            mensajeError = mensajeError + " - Correo Inst. - ";
            retorno = false;
        }
        if (validacionesCorreoOpcional == false) {
            mensajeError = mensajeError + " - Correo Pers. - ";
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
        if (validacionesFacultad == false) {
            mensajeError = mensajeError + " - Facultad - ";
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            mensajeError = mensajeError + " - Laboratorio - ";
            retorno = false;
        }
        if (validacionesTel1 == false) {
            mensajeError = mensajeError + " - Núm. Extensión - ";
            retorno = false;
        }
        if (validacionesTel2 == false) {
            mensajeError = mensajeError + " - Telefono Celular - ";
            retorno = false;
        }
        return retorno;
    }

    public void almacenarModificacionesEncargadoLaboratorio() {
        System.out.println("Llego");
        if (modificacionRegistro == true) {
            System.out.println("Llego paso 1");
            if (validarResultadosValidacion() == true) {
                System.out.println("Llego paso 2");
                modificarInformacionEncargadoLaboratorio();
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
        System.out.println("finalizo");
    }

    /**
     * Metodo encargado de almacenar en el sistema de información las
     * modificaciones del registro
     */
    public void modificarInformacionEncargadoLaboratorio() {
        try {
            encargadoLaboratorioDetalles.getPersona().setApellidospersona(apellidoEncargadoLaboratorio);
            encargadoLaboratorioDetalles.getPersona().setDireccionpersona(direccionEncargadoLaboratorio);
            encargadoLaboratorioDetalles.getPersona().setEmailpersona(correoEncargadoLaboratorio);
            encargadoLaboratorioDetalles.getPersona().setEmailsecundario(correoOpcionalEncargadoLaboratorio);
            encargadoLaboratorioDetalles.getPersona().setIdentificacionpersona(identificacionEncargadoLaboratorio);
            encargadoLaboratorioDetalles.getPersona().setNombrespersona(nombreEncargadoLaboratorio);
            encargadoLaboratorioDetalles.getPersona().setTelefono1persona(telefono1EncargadoLaboratorio);
            encargadoLaboratorioDetalles.getPersona().setTelefono2persona(telefono2EncargadoLaboratorio);
            encargadoLaboratorioDetalles.setLaboratorio(laboratorioEncargadoLaboratorio);
            administrarEncargadosLaboratoriosBO.actualizarInformacionPersona(encargadoLaboratorioDetalles.getPersona());
            administrarEncargadosLaboratoriosBO.actualizarInformacionEncargadoLaboratorio(encargadoLaboratorioDetalles);
            restaurarInformacionEncargadoLaboratorio();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEncargadoLaboratorio modificarInformacionEncargadoLaboratorio : " + e.toString(), e);

        }
    }

    /**
     * Metodo encargado de registrar alguna modificación del registro
     */
    public void modificacionesRegistroEncargadoLaboratorio() {
        modificacionRegistro = true;
    }

    /**
     * Metodo encargado de activar el encargado laboratorio
     */
    public void activarEncargadoLaboratorio() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(true);
                encargadoLaboratorioDetalles.getPersona().getUsuario().setEstado(bool);
                administrarEncargadosLaboratoriosBO.actualizarInformacionUsuario(encargadoLaboratorioDetalles.getPersona().getUsuario());
                restaurarInformacionEncargadoLaboratorio();
                colorMensaje = "green";
                mensajeFormulario = "Se ha activado el personal de laboratorio.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEncargadosLaboratorios activarEncargadoLaboratorio : " + e.toString(), e);
        }
    }

    /**
     * Metodo encargado de inactivar el encargado laboratorio
     */
    public void inactivarEncargadoLaboratorio() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(false);
                encargadoLaboratorioDetalles.getPersona().getUsuario().setEstado(bool);
                administrarEncargadosLaboratoriosBO.actualizarInformacionUsuario(encargadoLaboratorioDetalles.getPersona().getUsuario());
                encargadoLaboratorioDetalles = new EncargadoLaboratorio();
                restaurarInformacionEncargadoLaboratorio();
                colorMensaje = "green";
                mensajeFormulario = "Se ha inactivado el personal de laboratorio.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEncargadoLaboratorio inactivarEncargadoLaboratorio:  " + e.toString(), e);
        }
    }

    // GET - 
    public EncargadoLaboratorio getEncargadoLaboratorioDetalles() {
        return encargadoLaboratorioDetalles;
    }

    public void setEncargadoLaboratorioDetalles(EncargadoLaboratorio encargadoLaboratorioDetalles) {
        this.encargadoLaboratorioDetalles = encargadoLaboratorioDetalles;
    }

    public BigInteger getIdEncargadoLaboratorio() {
        return idEncargadoLaboratorio;
    }

    public void setIdEncargadoLaboratorio(BigInteger idEncargadoLaboratorio) {
        this.idEncargadoLaboratorio = idEncargadoLaboratorio;
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

    public List<Facultad> getListaFacultad() {
        return listaFacultad;
    }

    public void setListaFacultad(List<Facultad> listaFacultad) {
        this.listaFacultad = listaFacultad;
    }

    public Facultad getFacultadEncargadoLaboratorio() {
        return facultadEncargadoLaboratorio;
    }

    public void setFacultadEncargadoLaboratorio(Facultad facultadEncargadoLaboratorio) {
        this.facultadEncargadoLaboratorio = facultadEncargadoLaboratorio;
    }

    public List<Departamento> getListaDepartamento() {
        return listaDepartamento;
    }

    public void setListaDepartamento(List<Departamento> listaDepartamento) {
        this.listaDepartamento = listaDepartamento;
    }

    public Departamento getDepartamentoEncargadoLaboratorio() {
        return departamentoEncargadoLaboratorio;
    }

    public void setDepartamentoEncargadoLaboratorio(Departamento departamentoEncargadoLaboratorio) {
        this.departamentoEncargadoLaboratorio = departamentoEncargadoLaboratorio;
    }

    public boolean isActivoDepartamento() {
        return activoDepartamento;
    }

    public void setActivoDepartamento(boolean activoDepartamento) {
        this.activoDepartamento = activoDepartamento;
    }

    public List<Laboratorio> getListaLaboratorio() {
        return listaLaboratorio;
    }

    public void setListaLaboratorio(List<Laboratorio> listaLaboratorio) {
        this.listaLaboratorio = listaLaboratorio;
    }

    public Laboratorio getLaboratorioEncargadoLaboratorio() {
        return laboratorioEncargadoLaboratorio;
    }

    public void setLaboratorioEncargadoLaboratorio(Laboratorio laboratorioEncargadoLaboratorio) {
        this.laboratorioEncargadoLaboratorio = laboratorioEncargadoLaboratorio;
    }

    public boolean isActivoLaboratorio() {
        return activoLaboratorio;
    }

    public void setActivoLaboratorio(boolean activoLaboratorio) {
        this.activoLaboratorio = activoLaboratorio;
    }

    public String getNombreEncargadoLaboratorio() {
        return nombreEncargadoLaboratorio;
    }

    public void setNombreEncargadoLaboratorio(String nombreEncargadoLaboratorio) {
        this.nombreEncargadoLaboratorio = nombreEncargadoLaboratorio;
    }

    public String getApellidoEncargadoLaboratorio() {
        return apellidoEncargadoLaboratorio;
    }

    public void setApellidoEncargadoLaboratorio(String apellidoEncargadoLaboratorio) {
        this.apellidoEncargadoLaboratorio = apellidoEncargadoLaboratorio;
    }

    public String getCorreoEncargadoLaboratorio() {
        return correoEncargadoLaboratorio;
    }

    public void setCorreoEncargadoLaboratorio(String correoEncargadoLaboratorio) {
        this.correoEncargadoLaboratorio = correoEncargadoLaboratorio;
    }

    public String getIdentificacionEncargadoLaboratorio() {
        return identificacionEncargadoLaboratorio;
    }

    public void setIdentificacionEncargadoLaboratorio(String identificacionEncargadoLaboratorio) {
        this.identificacionEncargadoLaboratorio = identificacionEncargadoLaboratorio;
    }

    public String getTelefono1EncargadoLaboratorio() {
        return telefono1EncargadoLaboratorio;
    }

    public void setTelefono1EncargadoLaboratorio(String telefono1EncargadoLaboratorio) {
        this.telefono1EncargadoLaboratorio = telefono1EncargadoLaboratorio;
    }

    public String getTelefono2EncargadoLaboratorio() {
        return telefono2EncargadoLaboratorio;
    }

    public void setTelefono2EncargadoLaboratorio(String telefono2EncargadoLaboratorio) {
        this.telefono2EncargadoLaboratorio = telefono2EncargadoLaboratorio;
    }

    public String getDireccionEncargadoLaboratorio() {
        return direccionEncargadoLaboratorio;
    }

    public void setDireccionEncargadoLaboratorio(String direccionEncargadoLaboratorio) {
        this.direccionEncargadoLaboratorio = direccionEncargadoLaboratorio;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getCorreoOpcionalEncargadoLaboratorio() {
        return correoOpcionalEncargadoLaboratorio;
    }

    public void setCorreoOpcionalEncargadoLaboratorio(String correoOpcionalEncargadoLaboratorio) {
        this.correoOpcionalEncargadoLaboratorio = correoOpcionalEncargadoLaboratorio;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
