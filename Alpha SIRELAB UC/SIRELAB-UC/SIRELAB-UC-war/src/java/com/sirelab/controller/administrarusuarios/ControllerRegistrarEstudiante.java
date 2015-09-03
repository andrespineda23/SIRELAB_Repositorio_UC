package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.EnvioCorreo;
import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Controlador : ControllerRegistrarUsuario Este controlador esta enlazado con
 * la pagina registrarestudiante.xhtml y su funcion es controlar las operaciones
 * del proceso de registro de un estudiante extemporaneo que realiza el ingreso
 * al sistema
 *
 * @author ANDRES PINEDA
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarEstudiante implements Serializable {

    //
    @EJB
    GestionarLoginSistemaBOInterface gestionarLoginSistemaBO;
    //

    private String inputNombre, inputApellido, inputID, inputEmail, inputEmailOpcional, inputTelefono1, inputTelefono2, inputDireccion;
    private int inputSemestre, inputTipo;
    private List<Carrera> listaCarreras;
    private Carrera inputCarrera;
    private List<PlanEstudios> listaPlanesEstudios;
    private PlanEstudios inputPlanEstudio;
    private boolean activarPlanEstudio;
    private String paginaAnterior;
    private boolean activarNumSemestre;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesCarrera, validacionesPlanEstudio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarEstudiante() {
    }

    @PostConstruct
    public void init() {
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesCarrera = false;
        validacionesPlanEstudio = false;
        validacionesCorreoOpcional = true;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        //
        activarNumSemestre = false;
        activarPlanEstudio = true;
        inputApellido = null;
        inputCarrera = null;
        inputEmailOpcional = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputPlanEstudio = null;
        inputSemestre = 1;
        inputTipo = 1;
        listaPlanesEstudios = null;
        BasicConfigurator.configure();
    }

    public void actualizarTipoUsuarioSeleccionado() {
        if (inputTipo == 1) {
            activarNumSemestre = false;
        }
        if (inputTipo == 2) {
            activarNumSemestre = true;
        }
    }

    public void actualizarCarreras() {
        try {
            if (Utilidades.validarNulo(inputCarrera)) {
                activarPlanEstudio = false;
                inputPlanEstudio = null;
                listaPlanesEstudios = gestionarLoginSistemaBO.obtenerListasPlanesEstudioPorCarrera(inputCarrera.getIdcarrera());
                validacionesCarrera = true;
            } else {
                validacionesCarrera = false;
                validacionesPlanEstudio = false;
                activarPlanEstudio = true;
                listaPlanesEstudios = null;
                inputPlanEstudio = null;
                FacesContext.getCurrentInstance().addMessage("form:inputCarrera", new FacesMessage("El campo Carrera es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarEstudiante actualizarCarreras:  " + e.toString());
            System.out.println("Error ControllerRegistrarEstudiante actualizarCarreras : " + e.toString());
        }
    }

    public void actualizarPlanEstudio() {
        if (Utilidades.validarNulo(inputPlanEstudio)) {
            validacionesPlanEstudio = true;
        } else {
            validacionesPlanEstudio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputPlanEstudio", new FacesMessage("El campo Plan Estudio es obligatorio."));
        }
    }

    public void validarNombreEstudiante() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty()) && (inputNombre.trim().length() > 0)) {
            int tam = inputNombre.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(inputNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo es 2 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarApellidoEstudiante() {
        if (Utilidades.validarNulo(inputApellido) && (!inputApellido.isEmpty()) && (inputApellido.trim().length() > 0)) {
            int tam = inputApellido.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(inputApellido)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido ingresado es incorrecto."));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El tamaño minimo es 2 caracteres."));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido es obligatorio."));
        }
    }

    public void validarCorreoEstudiante() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty()) && (inputEmail.trim().length() > 0)) {
            int tam = inputApellido.length();
            if (tam >= 4) {
                String correoEstudiante = inputEmail + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correoEstudiante)) {
                    Estudiante registro = gestionarLoginSistemaBO.obtenerEstudiantePorCorreo(inputEmail);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        validacionesCorreo = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo ya se encuentra registrado."));
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El tamaño minimo es 4 caracteres."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio."));
        }
    }

    public void validarCorreoOOpcionalEstudiante() {
        if (Utilidades.validarNulo(inputEmailOpcional) && (!inputEmailOpcional.isEmpty()) && (inputEmailOpcional.trim().length() > 0)) {
            int tam = inputEmailOpcional.length();
            if (tam >= 15) {
                if (Utilidades.validarCorreoElectronico(inputEmailOpcional)) {
                    validacionesCorreoOpcional = true;
                } else {
                    validacionesCorreoOpcional = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmailOpcional", new FacesMessage("El correo se encuentra incorrecto."));
                }
            } else {
                validacionesCorreoOpcional = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEmailOpcional", new FacesMessage("El tamaño minimo es 15 caracteres."));
            }
        }
    }

    public void validarIdentificacionEstudiante() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty()) && (inputID.trim().length() > 0)) {
            int tam = inputID.length();
            if (tam >= 8) {
                if (Utilidades.validarNumeroIdentificacion(inputID)) {
                    Estudiante registro = gestionarLoginSistemaBO.obtenerEstudiantePorDocumento(inputID);
                    if (null == registro) {
                        validacionesID = true;
                    } else {
                        validacionesID = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El documento ya se encuentra registrado."));
                    }
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación se encuentra incorrecto."));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El tamaño minimo es 8 caracteres."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación es obligatorio."));
        }
    }

    public void validarDatosNumericosEstudiante(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty()) && (inputTelefono1.trim().length() > 0)) {
                int tam = inputTelefono1.length();
                if (tam == 7) {
                    if ((Utilidades.isNumber(inputTelefono1)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        } else {
            if (Utilidades.validarNulo(inputTelefono2) && (!inputTelefono2.isEmpty()) && (inputTelefono2.trim().length() > 0)) {
                int tam = inputTelefono2.length();
                if (tam == 10) {
                    if ((Utilidades.isNumber(inputTelefono2)) == false) {
                        validacionesTel2 = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                    } else {
                        validacionesTel2 = true;
                    }
                } else {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                }
            }
        }
    }

    public void validarDireccionEstudiante() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty()) && (inputDireccion.trim().length() > 0)) {
            int tam = inputDireccion.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(inputDireccion)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("El tamaño minimo es 8 caracteres."));
                validacionesDireccion = false;
            }
        }
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
    public void registrarNuevoEstudiante() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoEstudianteEnSistema();
            //EnvioCorreo correo = new EnvioCorreo();
            //correo.enviarCorreoCreacionCuenta(inputEmail+ "@ucentral.edu.co");
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }

    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroEstudiante() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        validacionesCarrera = false;
        validacionesPlanEstudio = false;
        validacionesCorreoOpcional = true;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        //
        activarPlanEstudio = true;
        inputApellido = null;
        inputCarrera = null;
        inputEmailOpcional = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputPlanEstudio = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputSemestre = 1;
        inputTipo = 1;
        listaCarreras = null;
        listaPlanesEstudios = null;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información el nuevo
     * estudiante
     */
    private void almacenarNuevoEstudianteEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEstado(false);
            usuarioNuevo.setNumeroconexiones(0);
            usuarioNuevo.setNombreusuario(inputID);
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuarioNuevo.setPasswordusuario(obj.encriptarContrasenia(inputID));
            Persona personaNueva = new Persona();
            personaNueva.setApellidospersona(inputApellido);
            personaNueva.setDireccionpersona(inputDireccion);
            personaNueva.setEmailsecundario(inputEmailOpcional);
            personaNueva.setEmailpersona(inputEmail);
            personaNueva.setIdentificacionpersona(inputID);
            personaNueva.setNombrespersona(inputNombre);
            personaNueva.setTelefono1persona(inputTelefono1);
            personaNueva.setTelefono2persona(inputTelefono2);
            Estudiante estudianteNueva = new Estudiante();
            estudianteNueva.setPlanestudio(inputPlanEstudio);
            if (inputTipo == 1) {
                estudianteNueva.setSemestreestudiante(inputSemestre);
            } else {
                estudianteNueva.setSemestreestudiante(99);
            }
            estudianteNueva.setTipoestudiante(inputTipo);
            gestionarLoginSistemaBO.almacenarNuevoEstudianteEnSistema(usuarioNuevo, personaNueva, estudianteNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarEstudiante almacenarNuevoEstudianteEnSistema  " + e.toString());
            System.out.println("Error ControllerRegistrarUsuario almacenarNuevoEstudianteEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        validacionesCarrera = false;
        validacionesPlanEstudio = false;
        validacionesCorreoOpcional = true;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        //
        activarPlanEstudio = true;
        inputApellido = null;
        inputCarrera = null;
        inputEmailOpcional = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputPlanEstudio = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputSemestre = 1;
        inputTipo = 1;
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

    public void recibirPaginaAnterior(String page) {
        paginaAnterior = page;
    }

    public String retornarPaginaAnterior() {
        if (null != paginaAnterior) {
            return paginaAnterior;
        }
        return "index.xhtml";
    }

    //GET - SET
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

    public int getInputSemestre() {
        return inputSemestre;
    }

    public void setInputSemestre(int inputSemestre) {
        this.inputSemestre = inputSemestre;
    }

    public List<Carrera> getListaCarreras() {
        if (listaCarreras == null) {
            listaCarreras = gestionarLoginSistemaBO.obtenerListasCarreras();
        }
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public Carrera getInputCarrera() {
        return inputCarrera;
    }

    public void setInputCarrera(Carrera inputCarrera) {
        this.inputCarrera = inputCarrera;
    }

    public List<PlanEstudios> getListaPlanesEstudios() {
        return listaPlanesEstudios;
    }

    public void setListaPlanesEstudios(List<PlanEstudios> listaPlanesEstudios) {
        this.listaPlanesEstudios = listaPlanesEstudios;
    }

    public PlanEstudios getInputPlanEstudio() {
        return inputPlanEstudio;
    }

    public void setInputPlanEstudio(PlanEstudios inputPlanEstudio) {
        this.inputPlanEstudio = inputPlanEstudio;
    }

    public boolean isActivarPlanEstudio() {
        return activarPlanEstudio;
    }

    public void setActivarPlanEstudio(boolean activarPlanEstudio) {
        this.activarPlanEstudio = activarPlanEstudio;
    }

    public int getInputTipo() {
        return inputTipo;
    }

    public void setInputTipo(int inputTipo) {
        this.inputTipo = inputTipo;
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

    public String getInputEmailOpcional() {
        return inputEmailOpcional;
    }

    public void setInputEmailOpcional(String inputEmailOpcional) {
        this.inputEmailOpcional = inputEmailOpcional;
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
