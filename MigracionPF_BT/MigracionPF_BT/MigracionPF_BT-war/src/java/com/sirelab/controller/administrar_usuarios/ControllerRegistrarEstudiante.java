package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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

    private String inputNombre, inputApellido, inputID, inputEmail, inputContrasenia, inputContraseniaConfirma, inputTelefono1, inputTelefono2, inputDireccion;
    private int inputSemestre, inputTipo;
    private List<Carrera> listaCarreras;
    private Carrera inputCarrera;
    private List<PlanEstudios> listaPlanesEstudios;
    private PlanEstudios inputPlanEstudio;
    private boolean activarPlanEstudio;
    private String paginaAnterior;
    private boolean activarNumSemestre;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesPassw, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesPassw2, validacionesCarrera, validacionesPlanEstudio;
    private String mensajeFormulario;

    public ControllerRegistrarEstudiante() {
    }

    @PostConstruct
    public void init() {
        mensajeFormulario = "";
        validacionesCarrera = false;
        validacionesPlanEstudio = false;
        validacionesPassw2 = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        //
        activarNumSemestre = false;
        activarPlanEstudio = true;
        inputApellido = null;
        inputCarrera = null;
        inputContrasenia = null;
        inputContraseniaConfirma = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputPlanEstudio = null;
        inputSemestre = 1;
        inputTipo = 1;
        listaCarreras = gestionarLoginSistemaBO.obtenerListasCarreras();
        listaPlanesEstudios = null;
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
            System.out.println("Error ControllerLogin actualizarDepartamentos : " + e.toString());
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
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(inputNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarApellidoEstudiante() {
        if (Utilidades.validarNulo(inputApellido) && (!inputApellido.isEmpty())) {
            if (!Utilidades.validarCaracterString(inputApellido)) {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido ingresado es incorrecto."));
            } else {
                validacionesApellido = true;
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido es obligatorio."));
        }
    }

    public void validarCorreoEstudiante() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty())) {
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
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio."));
        }
    }

    public void validarIdentificacionEstudiante() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputID)) {
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
            FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación es obligatorio."));
        }
    }

    public void validarDatosNumericosEstudiante(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty())) {
                if ((Utilidades.isNumber(inputTelefono1)) == false) {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTel1 = true;
                }
            }
        } else {
            if (Utilidades.validarNulo(inputTelefono2) && (!inputTelefono2.isEmpty())) {
                if ((Utilidades.isNumber(inputTelefono2)) == false) {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTel2 = true;
                }
            }
        }
    }

    public void validarDireccionEstudiante() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputDireccion)) {
                validacionesDireccion = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                validacionesDireccion = false;
            }
        }
    }

    public void validarContraseniaEstudiante() {
        if ((Utilidades.validarNulo(inputContrasenia)) && (!inputContrasenia.isEmpty())) {
            validacionesPassw = true;
        } else {
            FacesContext.getCurrentInstance().addMessage("form:inputContrasenia", new FacesMessage("La contraseña es obligatoria."));
            validacionesPassw = false;
        }
    }

    public void validarContraseniaConfirmaEstudiante() {
        if ((Utilidades.validarNulo(inputContrasenia)) && (Utilidades.validarNulo(inputContraseniaConfirma))
                && (!inputContrasenia.isEmpty()) && (!inputContraseniaConfirma.isEmpty())) {
            if (inputContrasenia.equals(inputContraseniaConfirma)) {
                validacionesPassw2 = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputContraseniaConfirma", new FacesMessage("Las contraseñas ingresadas no concuerdan."));
                validacionesPassw2 = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:inputContraseniaConfirma", new FacesMessage("Las contraseñas son obligatorias."));
            validacionesPassw2 = false;
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
        if (validacionesDireccion == false) {
            retorno = false;
        }
        if (validacionesID == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesPassw == false) {
            retorno = false;
        }
        if (validacionesPassw2 == false) {
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
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }

    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroEstudiante() {
        mensajeFormulario = "";
        validacionesCarrera = false;
        validacionesPlanEstudio = false;
        validacionesPassw2 = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        //
        activarPlanEstudio = true;
        inputApellido = null;
        inputCarrera = null;
        inputContrasenia = null;
        inputContraseniaConfirma = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputPlanEstudio = null;
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
            usuarioNuevo.setNombreusuario(inputEmail);
            usuarioNuevo.setPasswordusuario(inputContrasenia);
            Persona personaNueva = new Persona();
            personaNueva.setApellidospersona(inputApellido);
            personaNueva.setDireccionpersona(inputDireccion);
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
            cancelarRegistroEstudiante();
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarUsuario almacenarNuevoEstudianteEnSistema : " + e.toString());
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

    public String getInputContrasenia() {
        return inputContrasenia;
    }

    public void setInputContrasenia(String inputContrasenia) {
        this.inputContrasenia = inputContrasenia;
    }

    public String getInputContraseniaConfirma() {
        return inputContraseniaConfirma;
    }

    public void setInputContraseniaConfirma(String inputContraseniaConfirma) {
        this.inputContraseniaConfirma = inputContraseniaConfirma;
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

}
