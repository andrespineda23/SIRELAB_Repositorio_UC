/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEstudiantesBOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.PlanEstudios;
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
 * Controlador: ControllerDetallesEstudiante Este controlador esta encargado del
 * funcionamiento de la pagina detallesestudiante.xhtml
 *
 * @author ANDRES PINEDA
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesEstudiante implements Serializable {

    @EJB
    AdministrarEstudiantesBOInterface administrarEstudiantesBO;

    private Estudiante estudianteDetalles;
    private BigInteger idEstudiante;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean disabledActivar, disabledInactivar;
    private boolean visibleGuardar;
    private List<Carrera> listaCarrera;
    private Carrera carreraEstudiante;
    private List<PlanEstudios> listaPlanEstudios;
    private PlanEstudios planEstudioEstudiante;
    private boolean activoPlanEstudio;
    private String nombreEstudiante, apellidoEstudiante, correoEstudiante, correoOpcionalEstudiante, identificacionEstudiante;
    private String telefono1Estudiante, telefono2Estudiante, direccionEstudiante;
    private int tipoEstudiante;
    private Integer semestreEstudiante;
    private boolean activarNumSemestre;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesCarrera, validacionesPlanEstudio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesEstudiante() {
    }

    @PostConstruct
    public void init() {
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        validacionesCarrera = true;
        validacionesPlanEstudio = true;
        validacionesNombre = true;
        validacionesCorreoOpcional = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        //
        activoPlanEstudio = true;
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
     * Metodo encargado de asignar los valores del estudiante que sera
     * visualizado
     */
    public void asignarValoresVariablesEstudiante() {
        semestreEstudiante = estudianteDetalles.getSemestreestudiante();
        nombreEstudiante = estudianteDetalles.getPersona().getNombrespersona();
        apellidoEstudiante = estudianteDetalles.getPersona().getApellidospersona();
        correoEstudiante = estudianteDetalles.getPersona().getEmailpersona();
        correoOpcionalEstudiante = estudianteDetalles.getPersona().getEmailsecundario();
        identificacionEstudiante = estudianteDetalles.getPersona().getIdentificacionpersona();
        telefono1Estudiante = estudianteDetalles.getPersona().getTelefono1persona();
        telefono2Estudiante = estudianteDetalles.getPersona().getTelefono2persona();
        direccionEstudiante = estudianteDetalles.getPersona().getDireccionpersona();
        carreraEstudiante = estudianteDetalles.getPlanestudio().getCarrera();
        planEstudioEstudiante = estudianteDetalles.getPlanestudio();
        tipoEstudiante = estudianteDetalles.getTipoestudiante();
        if (tipoEstudiante == 1) {
            activarNumSemestre = false;
        }
        if (tipoEstudiante == 2) {
            activarNumSemestre = true;
            semestreEstudiante = 10;
        }
        listaCarrera = administrarEstudiantesBO.obtenerListaCarreras();
        if (null != carreraEstudiante) {
            listaPlanEstudios = administrarEstudiantesBO.obtenerListasPlanesEstudioPorCarrera(carreraEstudiante.getIdcarrera());
        }
    }

    /**
     * Metodo encargado de recibir el ID del estudiante que sera visualizado
     *
     * @param idEstudiante ID del estudiante
     */
    public void recibirIDEstudiantesDetalles(BigInteger idEstudiante) {
        this.idEstudiante = idEstudiante;
        estudianteDetalles = administrarEstudiantesBO.obtenerEstudiantePorIDEstudiante(idEstudiante);
        if (estudianteDetalles.getPersona().getUsuario().getEstado() == true) {
            disabledActivar = true;
            disabledInactivar = false;
        } else {
            disabledActivar = false;
            disabledInactivar = true;
        }
        asignarValoresVariablesEstudiante();
    }

    /**
     * Metodo encargado de activar las opciones de editar
     */
    public void activarEditarRegistro() {
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        activoPlanEstudio = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    /**
     * Metodo encargado de restaurar la información del estudiante
     */
    public String restaurarInformacionEstudiante() {
        estudianteDetalles = new Estudiante();
        estudianteDetalles = administrarEstudiantesBO.obtenerEstudiantePorIDEstudiante(idEstudiante);
        if (estudianteDetalles.getPersona().getUsuario().getEstado() == true) {
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
        activoPlanEstudio = true;
        listaCarrera = null;
        listaPlanEstudios = null;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        validacionesCorreoOpcional = true;
        validacionesPlanEstudio = true;
        validacionesNombre = true;
        validacionesApellido = true;
        validacionesCorreo = true;
        validacionesID = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        mensajeFormulario = "N/A";
        validacionesCarrera = true;
        asignarValoresVariablesEstudiante();
        return "administrarestudiantes";
    }

    public void actualizarTipoUsuarioSeleccionado() {
        if (tipoEstudiante == 1) {
            activarNumSemestre = false;
        }
        if (tipoEstudiante == 2) {
            activarNumSemestre = true;
        }
        modificacionesRegistroEstudiante();
    }

    /**
     * Metodo encargado de actualizar las carreras y obtener la informacion de
     * los planes de estudio relacionados
     */
    public void actualizarCarreras() {
        try {
            if (Utilidades.validarNulo(carreraEstudiante)) {
                activoPlanEstudio = false;
                planEstudioEstudiante = null;
                listaPlanEstudios = administrarEstudiantesBO.obtenerListasPlanesEstudioPorCarrera(carreraEstudiante.getIdcarrera());
                validacionesCarrera = true;
            } else {
                validacionesCarrera = false;
                validacionesPlanEstudio = false;
                activoPlanEstudio = true;
                listaPlanEstudios = null;
                planEstudioEstudiante = null;
                FacesContext.getCurrentInstance().addMessage("form:carreraEstudiante", new FacesMessage("La carrera es obligatoria."));
            }
            modificacionesRegistroEstudiante();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEstudiante actualizarCarreras:  " + e.toString());
            System.out.println("Error ControllerDetallesEstudiante actualizarCarreras : " + e.toString());
        }
    }

    public void actualizarPlanEstudio() {
        if (Utilidades.validarNulo(planEstudioEstudiante)) {
            validacionesPlanEstudio = true;
        } else {
            validacionesPlanEstudio = false;
            FacesContext.getCurrentInstance().addMessage("form:planEstudioEstudiante", new FacesMessage("El campo Plan Estudio es obligatorio."));
        }
        modificacionesRegistroEstudiante();
    }

    public void validarNombreEstudiante() {
        if (Utilidades.validarNulo(nombreEstudiante) && (!nombreEstudiante.isEmpty()) && (nombreEstudiante.trim().length() > 0)) {
            int tam = nombreEstudiante.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(nombreEstudiante)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreEstudiante", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreEstudiante", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreEstudiante", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistroEstudiante();
    }

    public void validarApellidoEstudiante() {
        if (Utilidades.validarNulo(apellidoEstudiante) && (!apellidoEstudiante.isEmpty()) && (apellidoEstudiante.trim().length() > 0)) {
            int tam = apellidoEstudiante.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(apellidoEstudiante)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:apellidoEstudiante", new FacesMessage("El apellido ingresado es incorrecto."));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:apellidoEstudiante", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:apellidoEstudiante", new FacesMessage("El apellido es obligatorio."));
        }
        modificacionesRegistroEstudiante();
    }

    public void validarCorreoEstudiante() {
        if (Utilidades.validarNulo(correoEstudiante) && (!correoEstudiante.isEmpty()) && (correoEstudiante.trim().length() > 0)) {
            int tam = correoEstudiante.length();
            if (tam >= 4) {
                String correo = correoEstudiante + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correo)) {
                    Estudiante registro = administrarEstudiantesBO.obtenerEstudiantePorCorreo(correoEstudiante);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        if (!estudianteDetalles.getIdestudiante().equals(registro.getIdestudiante())) {
                            validacionesCorreo = false;
                            FacesContext.getCurrentInstance().addMessage("form:correoEstudiante", new FacesMessage("El correo ya se encuentra registrado."));
                        } else {
                            validacionesCorreo = true;
                        }
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoEstudiante", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:correoEstudiante", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:correoEstudiante", new FacesMessage("El correo es obligatorio."));
        }
        modificacionesRegistroEstudiante();
    }

    public void validarCorreoOpcionalEstudiante() {
        if (Utilidades.validarNulo(correoOpcionalEstudiante) && (!correoOpcionalEstudiante.isEmpty()) && (correoOpcionalEstudiante.trim().length() > 0)) {
            int tam = correoOpcionalEstudiante.length();
            if (tam >= 15) {
                if (Utilidades.validarCorreoElectronico(correoOpcionalEstudiante)) {
                    validacionesCorreoOpcional = true;
                } else {
                    validacionesCorreoOpcional = false;
                    FacesContext.getCurrentInstance().addMessage("form:correoOpcionalEstudiante", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreoOpcional = false;
                FacesContext.getCurrentInstance().addMessage("form:correoOpcionalEstudiante", new FacesMessage("El tamaño minimo permitido es 15 caracteres."));
            }
        }
        modificacionesRegistroEstudiante();
    }

    public void validarIdentificacionEstudiante() {
        if (Utilidades.validarNulo(identificacionEstudiante) && (!identificacionEstudiante.isEmpty()) && (identificacionEstudiante.trim().length() > 0)) {
            int tam = identificacionEstudiante.length();
            if (tam >= 8) {
                if (Utilidades.validarNumeroIdentificacion(identificacionEstudiante)) {
                    Estudiante registro = administrarEstudiantesBO.obtenerEstudianteDocumento(identificacionEstudiante);
                    if (null == registro) {
                        validacionesID = true;
                    } else {
                        if (!estudianteDetalles.getIdestudiante().equals(registro.getIdestudiante())) {
                            validacionesID = false;
                            FacesContext.getCurrentInstance().addMessage("form:identificacionEstudiante", new FacesMessage("El documento ya se encuentra registrado."));
                        } else {
                            validacionesID = true;
                        }
                    }
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:identificacionEstudiante", new FacesMessage("El numero identificación se encuentra incorrecto."));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:identificacionEstudiante", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:identificacionEstudiante", new FacesMessage("El numero identificación es obligatorio."));
        }
        modificacionesRegistroEstudiante();
    }

    public void validarDatosNumericosEstudiante(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(telefono1Estudiante) && (!telefono1Estudiante.isEmpty()) && (telefono1Estudiante.trim().length() > 0)) {
                int tam = telefono1Estudiante.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(telefono1Estudiante)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono1Estudiante", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono1Estudiante", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        } else {
            if (Utilidades.validarNulo(telefono2Estudiante) && (!telefono2Estudiante.isEmpty()) && (telefono2Estudiante.trim().length() > 0)) {
                int tam = telefono2Estudiante.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(telefono2Estudiante)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:telefono2Estudiante", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:telefono2Estudiante", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        }
        modificacionesRegistroEstudiante();
    }

    public void validarDireccionEstudiante() {
        if ((Utilidades.validarNulo(direccionEstudiante)) && (!direccionEstudiante.isEmpty()) && (direccionEstudiante.trim().length() > 0)) {
            int tam = direccionEstudiante.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(direccionEstudiante)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:direccionEstudiante", new FacesMessage("La dirección se encuentra incorrecta."));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:direccionEstudiante", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
                validacionesDireccion = false;
            }
        }
        modificacionesRegistroEstudiante();
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesApellido == false) {
            retorno = false;
        }
        if (validacionesCarrera == false) {
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
        if (validacionesPlanEstudio == false) {
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
     * del nuevo estudiante
     */
    public void almacenarModificacionesEstudiante() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                modificarInformacionEstudiante();
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
    public void modificarInformacionEstudiante() {
        try {
            estudianteDetalles.getPersona().setApellidospersona(apellidoEstudiante);
            estudianteDetalles.getPersona().setDireccionpersona(direccionEstudiante);
            estudianteDetalles.getPersona().setEmailpersona(correoEstudiante);
            estudianteDetalles.getPersona().setEmailsecundario(correoOpcionalEstudiante);
            estudianteDetalles.getPersona().setIdentificacionpersona(identificacionEstudiante);
            estudianteDetalles.getPersona().setNombrespersona(nombreEstudiante);
            estudianteDetalles.getPersona().setTelefono1persona(telefono1Estudiante);
            estudianteDetalles.getPersona().setTelefono2persona(telefono2Estudiante);
            estudianteDetalles.setPlanestudio(planEstudioEstudiante);
            estudianteDetalles.setTipoestudiante(tipoEstudiante);
            if (tipoEstudiante == 1) {
                estudianteDetalles.setSemestreestudiante(semestreEstudiante);
            } else {
                estudianteDetalles.setSemestreestudiante(99);
            }
            administrarEstudiantesBO.actualizarInformacionPersona(estudianteDetalles.getPersona());
            administrarEstudiantesBO.actualizarInformacionEstudiante(estudianteDetalles);
            restaurarInformacionEstudiante();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEstudiante almacenarNuevoEstudianteEnSistema:  " + e.toString());
            System.out.println("Error ControllerDetallesEstudiante almacenarNuevoEstudianteEnSistema : " + e.toString());
        }
    }

    /**
     * Metodo encargado de registrar alguna modificación del registro
     */
    public void modificacionesRegistroEstudiante() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    /**
     * Metodo encargado de activar el estudiante
     */
    public void activarEstudiante() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(true);
                estudianteDetalles.getPersona().getUsuario().setEstado(bool);
                administrarEstudiantesBO.actualizarInformacionUsuario(estudianteDetalles.getPersona().getUsuario());
                restaurarInformacionEstudiante();
                colorMensaje = "green";
                mensajeFormulario = "Se ha activado el estudiante.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEstudiante activarEstudiante:  " + e.toString());
            System.out.println("Error ControllerDetallesEstudiantes activarEstudiante : " + e.toString());
        }
    }

    /**
     * Metodo encargado de inactivar el estudiante
     */
    public void inactivarEstudiante() {
        try {
            if (modificacionRegistro == false) {
                Boolean bool = new Boolean(false);
                estudianteDetalles.getPersona().getUsuario().setEstado(bool);
                administrarEstudiantesBO.actualizarInformacionUsuario(estudianteDetalles.getPersona().getUsuario());
                estudianteDetalles = new Estudiante();
                restaurarInformacionEstudiante();
                colorMensaje = "green";
                mensajeFormulario = "Se ha inactivado el estudiante.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Guarde primero los cambios para continuar con este proceso.";
            }
        } catch (Exception e) {
            logger.error("Error ControllerDetallesEstudiante inactivarEstudiante:  " + e.toString());
            System.out.println("Error ControllerDetallesEstudiantes inactivarEstudiante : " + e.toString());
        }
    }

    // GET - SET
    public Estudiante getEstudianteDetalles() {
        return estudianteDetalles;
    }

    public void setEstudianteDetalles(Estudiante estudianteDetalles) {
        this.estudianteDetalles = estudianteDetalles;
    }

    public boolean isActivarEditar() {
        return activarEditar;
    }

    public void setActivarEditar(boolean activarEditar) {
        this.activarEditar = activarEditar;
    }

    public boolean isModificacionRegistro() {
        return modificacionRegistro;
    }

    public void setModificacionRegistro(boolean modificacionRegistro) {
        this.modificacionRegistro = modificacionRegistro;
    }

    public boolean isDisabledEditar() {
        return disabledEditar;
    }

    public void setDisabledEditar(boolean disabledEditar) {
        this.disabledEditar = disabledEditar;
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

    public List<Carrera> getListaCarrera() {
        return listaCarrera;
    }

    public void setListaCarrera(List<Carrera> listaCarrera) {
        this.listaCarrera = listaCarrera;
    }

    public Carrera getCarreraEstudiante() {
        return carreraEstudiante;
    }

    public void setCarreraEstudiante(Carrera carreraEstudiante) {
        this.carreraEstudiante = carreraEstudiante;
    }

    public List<PlanEstudios> getListaPlanEstudios() {
        return listaPlanEstudios;
    }

    public void setListaPlanEstudios(List<PlanEstudios> listaPlanEstudios) {
        this.listaPlanEstudios = listaPlanEstudios;
    }

    public PlanEstudios getPlanEstudioEstudiante() {
        return planEstudioEstudiante;
    }

    public void setPlanEstudioEstudiante(PlanEstudios planEstudioEstudiante) {
        this.planEstudioEstudiante = planEstudioEstudiante;
    }

    public boolean isActivoPlanEstudio() {
        return activoPlanEstudio;
    }

    public void setActivoPlanEstudio(boolean activoPlanEstudio) {
        this.activoPlanEstudio = activoPlanEstudio;
    }

    public BigInteger getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(BigInteger idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoEstudiante() {
        return apellidoEstudiante;
    }

    public void setApellidoEstudiante(String apellidoEstudiante) {
        this.apellidoEstudiante = apellidoEstudiante;
    }

    public String getCorreoEstudiante() {
        return correoEstudiante;
    }

    public void setCorreoEstudiante(String correoEstudiante) {
        this.correoEstudiante = correoEstudiante;
    }

    public String getIdentificacionEstudiante() {
        return identificacionEstudiante;
    }

    public void setIdentificacionEstudiante(String identificacionEstudiante) {
        this.identificacionEstudiante = identificacionEstudiante;
    }

    public String getTelefono1Estudiante() {
        return telefono1Estudiante;
    }

    public void setTelefono1Estudiante(String telefono1Estudiante) {
        this.telefono1Estudiante = telefono1Estudiante;
    }

    public String getTelefono2Estudiante() {
        return telefono2Estudiante;
    }

    public void setTelefono2Estudiante(String telefono2Estudiante) {
        this.telefono2Estudiante = telefono2Estudiante;
    }

    public String getDireccionEstudiante() {
        return direccionEstudiante;
    }

    public void setDireccionEstudiante(String direccionEstudiante) {
        this.direccionEstudiante = direccionEstudiante;
    }

    public Integer getSemestreEstudiante() {
        return semestreEstudiante;
    }

    public void setSemestreEstudiante(Integer semestreEstudiante) {
        this.semestreEstudiante = semestreEstudiante;
    }

    public int getTipoEstudiante() {
        return tipoEstudiante;
    }

    public void setTipoEstudiante(int tipoEstudiante) {
        this.tipoEstudiante = tipoEstudiante;
    }

    public boolean isActivarNumSemestre() {
        return activarNumSemestre;
    }

    public void setActivarNumSemestre(boolean activarNumSemestre) {
        this.activarNumSemestre = activarNumSemestre;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getCorreoOpcionalEstudiante() {
        return correoOpcionalEstudiante;
    }

    public void setCorreoOpcionalEstudiante(String correoOpcionalEstudiante) {
        this.correoOpcionalEstudiante = correoOpcionalEstudiante;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
