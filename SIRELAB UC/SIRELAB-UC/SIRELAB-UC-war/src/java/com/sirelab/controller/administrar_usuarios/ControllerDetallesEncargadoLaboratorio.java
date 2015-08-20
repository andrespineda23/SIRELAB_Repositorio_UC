/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.TipoPerfil;
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
 * encarga del funcionamiento de la pagina detalles_encargadolaboratorio.xhtml
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
    private List<TipoPerfil> listaTiposPerfiles;
    private TipoPerfil perfilEncargadoLaboratorio;
    private String nombreEncargadoLaboratorio, apellidoEncargadoLaboratorio, correoEncargadoLaboratorio, correoOpcionalEncargadoLaboratorio, identificacionEncargadoLaboratorio;
    private String telefono1EncargadoLaboratorio, telefono2EncargadoLaboratorio, direccionEncargadoLaboratorio;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2, validacionesPerfil;
    private boolean validacionesDireccion, validacionesFacultad, validacionesDepartamento, validacionesLaboratorio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesEncargadoLaboratorio() {
    }

    @PostConstruct
    public void init() {
        colorMensaje = "black";
        validacionesPerfil = true;
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
        mensajeFormulario = "N/A";
        //
        activoDepartamento = true;
        activoLaboratorio = true;
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
     * Metodo encargado de asignar los valores del encargado laboratorio que
     * sera visualizado
     */
    public void asignarValoresVariablesEncargadoLaboratorio() {
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
        perfilEncargadoLaboratorio = encargadoLaboratorioDetalles.getTipoperfil();
        listaTiposPerfiles = administrarEncargadosLaboratoriosBO.consultarPerfilesPorEncargadoRegistrados();
        listaFacultad = administrarEncargadosLaboratoriosBO.obtenerListaFacultades();
        if (null != facultadEncargadoLaboratorio) {
            listaDepartamento = administrarEncargadosLaboratoriosBO.obtenerDepartamentosPorIDFacultad(facultadEncargadoLaboratorio.getIdfacultad());
        }
        if (departamentoEncargadoLaboratorio != null) {
            listaLaboratorio = administrarEncargadosLaboratoriosBO.obtenerLaboratoriosPorIDDepartamento(departamentoEncargadoLaboratorio.getIddepartamento());
        }
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
        validacionesPerfil = true;
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
        return "administrar_encargadoslaboratorios";
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
            logger.error("Error ControllerDetallesEncargadoLaboratorio actualizarFacultades:  " + e.toString());
            System.out.println("Error ControllerDetallesEncargadoLaboratorio actualizarFacultades : " + e.toString());
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
            logger.error("Error ControllerDetallesEncargadoLaboratorio actualizarDepartamentos:  " + e.toString());
            System.out.println("Error ControllerDetallesEncargadoLaboratorio actualizarDepartamentos : " + e.toString());
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

    public void actualizarTipoPerfil() {
        if (Utilidades.validarNulo(perfilEncargadoLaboratorio)) {
            validacionesPerfil = true;
        } else {
            validacionesPerfil = false;
            FacesContext.getCurrentInstance().addMessage("form:perfilEncargadoLaboratorio", new FacesMessage("El campo Perfil por Encargado es obligatorio."));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarNombreEncargadoLaboratorio() {
        if (Utilidades.validarNulo(nombreEncargadoLaboratorio) && (!nombreEncargadoLaboratorio.isEmpty()) && (nombreEncargadoLaboratorio.trim().length() > 0)) {
            int tam = nombreEncargadoLaboratorio.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nombreEncargadoLaboratorio)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreEncargadoLaboratorio", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreEncargadoLaboratorio", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarApellidoEncargadoLaboratorio() {
        if (Utilidades.validarNulo(apellidoEncargadoLaboratorio) && (!apellidoEncargadoLaboratorio.isEmpty()) && (apellidoEncargadoLaboratorio.trim().length() > 0)) {
            int tam = apellidoEncargadoLaboratorio.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(apellidoEncargadoLaboratorio)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:apellidoEncargadoLaboratorio", new FacesMessage("El apellido ingresado es incorrecto."));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:apellidoEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:apellidoEncargadoLaboratorio", new FacesMessage("El apellido es obligatorio."));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarCorreoEncargadoLaboratorio() {
        if (Utilidades.validarNulo(correoEncargadoLaboratorio) && (!correoEncargadoLaboratorio.isEmpty()) && (correoEncargadoLaboratorio.trim().length() > 0)) {
            int tam = correoEncargadoLaboratorio.length();
            if (tam >= 4) {
                String correo = correoEncargadoLaboratorio + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correo)) {
                    EncargadoLaboratorio registro = administrarEncargadosLaboratoriosBO.obtenerEncargadoLaboratorioPorCorreo(correoEncargadoLaboratorio);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        if (!encargadoLaboratorioDetalles.getIdencargadolaboratorio().equals(registro.getIdencargadolaboratorio())) {
                            validacionesCorreo = false;
                            FacesContext.getCurrentInstance().addMessage("form:correoEncargadoLaboratorio", new FacesMessage("El correo ya se encuentra registrado."));
                        } else {
                            validacionesCorreo = true;
                        }
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoEncargadoLaboratorio", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:correoEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:correoEncargadoLaboratorio", new FacesMessage("El correo es obligatorio."));
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
                    FacesContext.getCurrentInstance().addMessage("form:correoOpcionalEncargadoLaboratorio", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreoOpcional = false;
                FacesContext.getCurrentInstance().addMessage("form:correoOpcionalEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 15 caracteres."));
            }
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarIdentificacionEncargadoLaboratorio() {
        if (Utilidades.validarNulo(identificacionEncargadoLaboratorio) && (!identificacionEncargadoLaboratorio.isEmpty()) && (identificacionEncargadoLaboratorio.trim().length() > 0)) {
            int tam = identificacionEncargadoLaboratorio.length();
            if (tam >= 8) {
                if (Utilidades.validarNumeroIdentificacion(identificacionEncargadoLaboratorio)) {
                    EncargadoLaboratorio registro = administrarEncargadosLaboratoriosBO.obtenerEncargadoLaboratorioPorDocumento(identificacionEncargadoLaboratorio);
                    if (null == registro) {
                        validacionesID = true;
                    } else {
                        if (!encargadoLaboratorioDetalles.getIdencargadolaboratorio().equals(registro.getIdencargadolaboratorio())) {
                            validacionesID = false;
                            FacesContext.getCurrentInstance().addMessage("form:identificacionEncargadoLaboratorio", new FacesMessage("El documento ya se encuentra registrado."));
                        } else {
                            validacionesID = true;
                        }
                    }
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:identificacionEncargadoLaboratorio", new FacesMessage("El numero identificación se encuentra incorrecto."));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:identificacionEncargadoLaboratorio", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:identificacionEncargadoLaboratorio", new FacesMessage("El numero identificación es obligatorio."));
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    public void validarDatosNumericosEncargadoLaboratorio(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(telefono1EncargadoLaboratorio) && (!telefono1EncargadoLaboratorio.isEmpty()) && (telefono1EncargadoLaboratorio.trim().length() > 0)) {
                int tam = telefono1EncargadoLaboratorio.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(telefono1EncargadoLaboratorio)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono1EncargadoLaboratorio", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono1EncargadoLaboratorio", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        } else {
            if (Utilidades.validarNulo(telefono2EncargadoLaboratorio) && (!telefono2EncargadoLaboratorio.isEmpty()) && (telefono2EncargadoLaboratorio.trim().length() > 0)) {
                int tam = telefono2EncargadoLaboratorio.length();
                if (tam == 10) {
                    if ((Utilidades.isNumber(telefono2EncargadoLaboratorio)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono2EncargadoLaboratorio", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono2EncargadoLaboratorio", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
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
                    FacesContext.getCurrentInstance().addMessage("form:direccionEncargadoLaboratorio", new FacesMessage("La dirección se encuentra incorrecta."));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:direccionEncargadoLaboratorio", new FacesMessage("LEl tamaño minimo permitido es 8 caracteres."));
                validacionesDireccion = false;
            }
        }
        modificacionesRegistroEncargadoLaboratorio();
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesApellido == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
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
        if (validacionesFacultad == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            retorno = false;
        }
        if (validacionesTel1 == false) {
            retorno = false;
        }
        if (validacionesPerfil == false) {
            retorno = false;
        }
        if (validacionesTel2 == false) {
            retorno = false;
        }
        return retorno;
    }

    public void almacenarModificacionesEncargadoLaboratorio() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                modificarInformacionEncargadoLaboratorio();
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
            encargadoLaboratorioDetalles.setTipoperfil(perfilEncargadoLaboratorio);
            encargadoLaboratorioDetalles.setLaboratorio(laboratorioEncargadoLaboratorio);
            administrarEncargadosLaboratoriosBO.actualizarInformacionPersona(encargadoLaboratorioDetalles.getPersona());
            administrarEncargadosLaboratoriosBO.actualizarInformacionEncargadoLaboratorio(encargadoLaboratorioDetalles);
            restaurarInformacionEncargadoLaboratorio();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEncargadoLaboratorio modificarInformacionEncargadoLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesEncargadoLaboratorio modificarInformacionEncargadoLaboratorio : " + e.toString());

        }
    }

    /**
     * Metodo encargado de registrar alguna modificación del registro
     */
    public void modificacionesRegistroEncargadoLaboratorio() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
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
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEncargadoLaboratorio activarEncargadoLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesEncargadosLaboratorios activarEncargadoLaboratorio : " + e.toString());
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
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEncargadoLaboratorio inactivarEncargadoLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesEncargadosLaboratorios inactivarEncargadoLaboratorio : " + e.toString());
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

    public List<TipoPerfil> getListaTiposPerfiles() {
        return listaTiposPerfiles;
    }

    public void setListaTiposPerfiles(List<TipoPerfil> listaTiposPerfiles) {
        this.listaTiposPerfiles = listaTiposPerfiles;
    }

    public TipoPerfil getPerfilEncargadoLaboratorio() {
        return perfilEncargadoLaboratorio;
    }

    public void setPerfilEncargadoLaboratorio(TipoPerfil perfilEncargadoLaboratorio) {
        this.perfilEncargadoLaboratorio = perfilEncargadoLaboratorio;
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
