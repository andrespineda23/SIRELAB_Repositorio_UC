/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.usuarios.AdministrarDocentesBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.TipoCargo;
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
 * Controlador: ControllerDetallesDocente Este controlador se encarga del
 * funcionamiento de la pagina detallesdocente.xhtml
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesDocente implements Serializable {

    @EJB
    AdministrarDocentesBOInterface administrarDocentesBO;

    private Docente docenteDetalles;
    private BigInteger idDocente;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private List<Facultad> listaFacultad;
    private Facultad facultadDocente;
    private List<Departamento> listaDepartamento;
    private Departamento departamentoDocente;
    private boolean activoDepartamento;
    private List<TipoCargo> listaTipoCargo;
    private TipoCargo cargoDocente;
    private String nombreDocente, apellidoDocente, correoDocente, identificacionDocente;
    private String telefono1Docente, telefono2Docente, direccionDocente;
    private String correoOpcionalDocente;
    //
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesCargo, validacionesFacultad, validacionesDepartamento;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerDetallesDocente() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        colorMensaje = "black";
        validacionesNombre = true;
        validacionesCorreoOpcional = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesCargo = true;
        validacionesFacultad = true;
        validacionesDepartamento = true;
        mensajeFormulario = "N/A";
        activoDepartamento = true;
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
     * Metodo encargado de asignar los valores del docente que sera visualizado
     */
    public void asignarValoresVariablesDocente() {
        mensajeError = "";
        cargoDocente = docenteDetalles.getCargo();
        nombreDocente = docenteDetalles.getPersona().getNombrespersona();
        apellidoDocente = docenteDetalles.getPersona().getApellidospersona();
        correoDocente = docenteDetalles.getPersona().getEmailpersona();
        identificacionDocente = docenteDetalles.getPersona().getIdentificacionpersona();
        telefono1Docente = docenteDetalles.getPersona().getTelefono1persona();
        telefono2Docente = docenteDetalles.getPersona().getTelefono2persona();
        direccionDocente = docenteDetalles.getPersona().getDireccionpersona();
        facultadDocente = docenteDetalles.getDepartamento().getFacultad();
        departamentoDocente = docenteDetalles.getDepartamento();
        correoOpcionalDocente = docenteDetalles.getPersona().getEmailsecundario();
        listaFacultad = administrarDocentesBO.obtenerListaFacultades();
        if (Utilidades.validarNulo(facultadDocente)) {
            listaDepartamento = administrarDocentesBO.obtenerDepartamentosPorIDFacultad(facultadDocente.getIdfacultad());
        }
        listaTipoCargo = administrarDocentesBO.obtenerListaTiposCargos();
    }

    /**
     * Metodo encargado de recibir el ID del docente que sera visualizado
     *
     * @param idDocente ID del docente
     */
    public void recibirIDDocentesDetalles(BigInteger idDocente) {
        this.idDocente = idDocente;
        docenteDetalles = administrarDocentesBO.obtenerDocentePorIDDocente(idDocente);
        if (docenteDetalles.getPersona().getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesDocente();
    }

    /**
     * Metodo encargado de activar las opciones de editar
     */
    public void activarEditarRegistro() {
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        activoDepartamento = false;
    }

    /**
     * Metodo encargado de restaurar la información del docente
     */
    public String restaurarInformacionDocente() {
        docenteDetalles = new Docente();
        docenteDetalles = administrarDocentesBO.obtenerDocentePorIDDocente(idDocente);
        if (docenteDetalles.getPersona().getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesCorreoOpcional = true;
        validacionesCargo = true;
        validacionesFacultad = true;
        validacionesDepartamento = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        activoDepartamento = true;
        listaDepartamento = null;
        listaFacultad = null;
        asignarValoresVariablesDocente();
        return "administrardocentes";
    }

    /**
     * Metodo encargado de actualizar la facultad y obtener la informacion de
     * los departaments relacionados
     */
    public void actualizarFacultades() {
        try {
            if (Utilidades.validarNulo(facultadDocente)) {
                activoDepartamento = false;
                departamentoDocente = null;
                listaDepartamento = administrarDocentesBO.obtenerDepartamentosPorIDFacultad(facultadDocente.getIdfacultad());
                validacionesFacultad = true;
                modificacionesRegistroDocente();
            } else {
                validacionesFacultad = false;
                validacionesDepartamento = false;
                activoDepartamento = true;
                departamentoDocente = null;
                listaDepartamento = null;
                FacesContext.getCurrentInstance().addMessage("form:facultadDocente", new FacesMessage("El campo Facutad es obligatorio."));
            }
            modificacionesRegistroDocente();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesDocente actualizarFacultades:  " + e.toString(), e);
            logger.error("Error ControllerDetallesDocente actualizarFacultades : " + e.toString(), e);
        }
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(departamentoDocente)) {
            validacionesDepartamento = true;
        } else {
            validacionesDepartamento = false;
            FacesContext.getCurrentInstance().addMessage("form:departamentoDocente", new FacesMessage("El campo Departamento es obligatorio."));
        }
        modificacionesRegistroDocente();
    }

    public void actualizarCargos() {
        if (Utilidades.validarNulo(cargoDocente)) {
            validacionesCargo = true;
        } else {
            validacionesCargo = false;
            FacesContext.getCurrentInstance().addMessage("form:cargoDocente", new FacesMessage("El campo Cargo es obligatorio."));
        }
        modificacionesRegistroDocente();
    }

    public void validarNombreDocente() {
        if (Utilidades.validarNulo(nombreDocente) && (!nombreDocente.isEmpty()) && (nombreDocente.trim().length() > 0)) {
            int tam = nombreDocente.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nombreDocente)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreDocente", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.USUARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreDocente", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.USUARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreDocente", new FacesMessage("El nombre es obligatorio. " + constantes.USUARIO_NOMBRE));
        }
        modificacionesRegistroDocente();
    }

    public void validarApellidoDocente() {
        if (Utilidades.validarNulo(apellidoDocente) && (!apellidoDocente.isEmpty()) && (apellidoDocente.trim().length() > 0)) {
            int tam = apellidoDocente.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(apellidoDocente)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:apellidoDocente", new FacesMessage("El apellido ingresado es incorrecto. " + constantes.USUARIO_APELLIDO));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:apellidoDocente", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.USUARIO_APELLIDO));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:apellidoDocente", new FacesMessage("El apellido es obligatorio. " + constantes.USUARIO_APELLIDO));
        }
        modificacionesRegistroDocente();
    }

    public void validarCorreoDocente() {
        if (Utilidades.validarNulo(correoDocente) && (!correoDocente.isEmpty()) && (correoDocente.trim().length() > 0)) {
            int tam = correoDocente.length();
            if (tam >= 4) {
                String correoDoc = correoDocente + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correoDoc)) {
                    Persona registro = administrarDocentesBO.obtenerPersonaSistemaPorCorreo(correoDocente);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        if (!docenteDetalles.getPersona().getIdpersona().equals(registro.getIdpersona())) {
                            validacionesCorreo = false;
                            FacesContext.getCurrentInstance().addMessage("form:correoDocente", new FacesMessage("El correo ya se encuentra registrado."));
                        } else {
                            validacionesCorreo = true;
                        }
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoDocente", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.USUARIO_CORREO));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:correoDocente", new FacesMessage("El correo se encuentra incorrecto. " + constantes.USUARIO_CORREO));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:correoDocente", new FacesMessage("El correo es obligatorio. " + constantes.USUARIO_CORREO));
        }
        modificacionesRegistroDocente();
    }

    public void validarCorreoOpcionalDocente() {
        if (Utilidades.validarNulo(correoOpcionalDocente) && (!correoOpcionalDocente.isEmpty()) && (correoOpcionalDocente.trim().length() > 0)) {
            int tam = correoOpcionalDocente.length();
            if (tam >= 15) {
                if (Utilidades.validarCorreoElectronico(correoOpcionalDocente)) {
                    validacionesCorreoOpcional = true;
                } else {
                    validacionesCorreoOpcional = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoOpcionalDocente", new FacesMessage("El correo se encuentra incorrecto. " + constantes.USUARIO_CORREO_OPC));
                }
            } else {
                validacionesCorreoOpcional = false;
                FacesContext.getCurrentInstance().addMessage("form:correoOpcionalDocente", new FacesMessage("El tamaño minimo permitido es 15 caracteres. " + constantes.USUARIO_CORREO_OPC));
            }
        }
        modificacionesRegistroDocente();
    }

    public void validarIdentificacionDocente() {
        if (Utilidades.validarNulo(identificacionDocente) && (!identificacionDocente.isEmpty()) && (identificacionDocente.trim().length() > 0)) {
            int tam = identificacionDocente.length();
            if (tam >= 6) {
                if (Utilidades.validarNumeroIdentificacion(identificacionDocente)) {
                    Persona registro = administrarDocentesBO.obtenerPersonaSistemaPorIdentificacion(identificacionDocente);
                    if (null == registro) {
                        validacionesID = true;
                    } else {
                        if (!docenteDetalles.getPersona().getIdpersona().equals(registro.getIdpersona())) {
                            validacionesID = false;
                            FacesContext.getCurrentInstance().addMessage("form:identificacionDocente", new FacesMessage("El documento ya se encuentra registrado."));
                        } else {
                            validacionesID = true;
                        }
                    }
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:identificacionDocente", new FacesMessage("El numero identificación se encuentra incorrecto. " + constantes.USUARIO_ID));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:identificacionDocente", new FacesMessage("El tamaño minimo permitido es 6 caracteres. " + constantes.USUARIO_ID));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:identificacionDocente", new FacesMessage("El numero identificación es obligatorio. " + constantes.USUARIO_ID));
        }
        modificacionesRegistroDocente();
    }

    public void validarDatosNumericosDocente(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(telefono1Docente) && (!telefono1Docente.isEmpty()) && (telefono1Docente.trim().length() > 0)) {
                validacionesTel1 = true;
            }
        } else {
            if (Utilidades.validarNulo(telefono2Docente) && (!telefono2Docente.isEmpty()) && (telefono2Docente.trim().length() > 0)) {
                validacionesTel2 = true;
            }
        }
        modificacionesRegistroDocente();
    }

    public void validarDireccionDocente() {
        if ((Utilidades.validarNulo(direccionDocente)) && (!direccionDocente.isEmpty()) && (direccionDocente.trim().length() > 0)) {
            int tam = direccionDocente.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(direccionDocente)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:direccionDocente", new FacesMessage("La dirección se encuentra incorrecta. " + constantes.USUARIO_DIRECCION));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:direccionDocente", new FacesMessage("El tamaño minimo permitido es 8 caracteres. " + constantes.USUARIO_DIRECCION));
                validacionesDireccion = false;
            }
        }
        modificacionesRegistroDocente();
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
        if (validacionesCargo == false) {
            mensajeError = mensajeError + " - Cargo - ";
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

    public void almacenarModificacionesDocente() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                modificarInformacionDocente();
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No se presento algun cambio en el registro. No se realizo ningun proceso de almacenamiento.";
            restaurarInformacionDocente();
        }
    }

    /**
     * Metodo encargado de almacenar en el sistema de información las
     * modificaciones del registro
     */
    public void modificarInformacionDocente() {
        try {
            docenteDetalles.getPersona().setApellidospersona(apellidoDocente);
            docenteDetalles.getPersona().setDireccionpersona(direccionDocente);
            docenteDetalles.getPersona().setEmailpersona(correoDocente);
            docenteDetalles.getPersona().setEmailsecundario(correoOpcionalDocente);
            docenteDetalles.getPersona().setIdentificacionpersona(identificacionDocente);
            docenteDetalles.getPersona().setNombrespersona(nombreDocente);
            docenteDetalles.getPersona().setTelefono1persona(telefono1Docente);
            docenteDetalles.getPersona().setTelefono2persona(telefono2Docente);
            docenteDetalles.setDepartamento(departamentoDocente);
            docenteDetalles.setCargo(cargoDocente);
            administrarDocentesBO.actualizarInformacionPersona(docenteDetalles.getPersona());
            administrarDocentesBO.actualizarInformacionDocente(docenteDetalles);
            restaurarInformacionDocente();
        } catch (Exception e) {
            logger.error("Error modificarInformacionDocente almacenarNuevoDocenteEnSistema : " + e.toString(), e);
        }
    }

    /**
     * Metodo encargado de registrar alguna modificación del registro
     */
    public void modificacionesRegistroDocente() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    /**
     * Metodo encargado de activar el docente
     */
    public void activarDocente() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(true);
                docenteDetalles.getPersona().getUsuario().setEstado(bool);
                administrarDocentesBO.actualizarInformacionUsuario(docenteDetalles.getPersona().getUsuario());
                restaurarInformacionDocente();
                mensajeFormulario = "Se ha activado el docente.";
                colorMensaje = "green";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesDocente activarDocente:  " + e.toString(), e);
            logger.error("Error ControllerDetallesDocentes activarDocente : " + e.toString(), e);
        }
    }

    /**
     * Metodo encargado de inactivar el docente
     */
    public void inactivarDocente() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(false);
                docenteDetalles.getPersona().getUsuario().setEstado(bool);
                administrarDocentesBO.actualizarInformacionUsuario(docenteDetalles.getPersona().getUsuario());
                docenteDetalles = new Docente();
                restaurarInformacionDocente();
                colorMensaje = "green";
                mensajeFormulario = "Se ha inactivado el docente.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesDocente inactivarDocente:  " + e.toString(), e);
        }
    }

    // GET - SET
    public Docente getDocenteDetalles() {
        return docenteDetalles;
    }

    public void setDocenteDetalles(Docente docenteDetalles) {
        this.docenteDetalles = docenteDetalles;
    }

    public BigInteger getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(BigInteger idDocente) {
        this.idDocente = idDocente;
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

    public Facultad getFacultadDocente() {
        return facultadDocente;
    }

    public void setFacultadDocente(Facultad facultadDocente) {
        this.facultadDocente = facultadDocente;
    }

    public List<Departamento> getListaDepartamento() {
        return listaDepartamento;
    }

    public void setListaDepartamento(List<Departamento> listaDepartamento) {
        this.listaDepartamento = listaDepartamento;
    }

    public Departamento getDepartamentoDocente() {
        return departamentoDocente;
    }

    public void setDepartamentoDocente(Departamento departamentoDocente) {
        this.departamentoDocente = departamentoDocente;
    }

    public boolean isActivoDepartamento() {
        return activoDepartamento;
    }

    public void setActivoDepartamento(boolean activoDepartamento) {
        this.activoDepartamento = activoDepartamento;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getApellidoDocente() {
        return apellidoDocente;
    }

    public void setApellidoDocente(String apellidoDocente) {
        this.apellidoDocente = apellidoDocente;
    }

    public String getCorreoDocente() {
        return correoDocente;
    }

    public void setCorreoDocente(String correoDocente) {
        this.correoDocente = correoDocente;
    }

    public String getIdentificacionDocente() {
        return identificacionDocente;
    }

    public void setIdentificacionDocente(String identificacionDocente) {
        this.identificacionDocente = identificacionDocente;
    }

    public String getTelefono1Docente() {
        return telefono1Docente;
    }

    public void setTelefono1Docente(String telefono1Docente) {
        this.telefono1Docente = telefono1Docente;
    }

    public String getTelefono2Docente() {
        return telefono2Docente;
    }

    public void setTelefono2Docente(String telefono2Docente) {
        this.telefono2Docente = telefono2Docente;
    }

    public String getDireccionDocente() {
        return direccionDocente;
    }

    public void setDireccionDocente(String direccionDocente) {
        this.direccionDocente = direccionDocente;
    }

    public List<TipoCargo> getListaTipoCargo() {
        return listaTipoCargo;
    }

    public void setListaTipoCargo(List<TipoCargo> listaTipoCargo) {
        this.listaTipoCargo = listaTipoCargo;
    }

    public TipoCargo getCargoDocente() {
        return cargoDocente;
    }

    public void setCargoDocente(TipoCargo cargoDocente) {
        this.cargoDocente = cargoDocente;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getCorreoOpcionalDocente() {
        return correoOpcionalDocente;
    }

    public void setCorreoOpcionalDocente(String correoOpcionalDocente) {
        this.correoOpcionalDocente = correoOpcionalDocente;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
