package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.EnvioCorreo;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.usuarios.AdministrarDocentesBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.TipoCargo;
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
 * Controlador: ControllerRegistrarDocente Este controlador se encarga del
 * proceso de registro de un nuevo docente en el sistema de información
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarDocente implements Serializable {

    //
    @EJB
    AdministrarDocentesBOInterface administrarDocentesBO;
    //

    private String inputNombre, inputApellido, inputID, inputEmail, inputEmailOpcional, inputTelefono1, inputTelefono2, inputDireccion;
    ;
    private List<Facultad> listaFacultades;
    private Facultad inputFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento inputDepartamento;
    private boolean activarDepartamento;
    private List<TipoCargo> listaTiposCargos;
    private TipoCargo inputCargo;
    private String paginaAnterior;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesCargo, validacionesFacultad, validacionesDepartamento;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarDocente() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesCargo = false;
        validacionesFacultad = false;
        validacionesCorreoOpcional = true;
        validacionesDepartamento = false;
        activarDepartamento = true;
        inputApellido = null;
        inputFacultad = null;
        inputDireccion = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputEmailOpcional = null;
        inputDepartamento = null;
        inputCargo = null;
        listaDepartamentos = null;
        BasicConfigurator.configure();
    }

    /**
     * Metodo encargado de la actualizacion de las facultades por parte del
     * usuario y obtiene los departamentos relacionados
     */
    public void actualizarFacultades() {
        try {
            if (Utilidades.validarNulo(inputFacultad)) {
                activarDepartamento = false;
                inputDepartamento = null;
                listaDepartamentos = administrarDocentesBO.obtenerDepartamentosActivosPorIDFacultad(inputFacultad.getIdfacultad());
                validacionesFacultad = true;
            } else {
                validacionesDepartamento = false;
                validacionesFacultad = false;
                activarDepartamento = true;
                listaDepartamentos = null;
                inputDepartamento = null;
                FacesContext.getCurrentInstance().addMessage("form:inputFacultad", new FacesMessage("El campo Facultad es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarDocente actualizarFacultades:  " + e.toString());
            logger.error("Error ControllerRegistrarDocente actualizarFacultades : " + e.toString());
        }
    }

    public void actualizarDepartamentos() {
        try {
            if (Utilidades.validarNulo(inputDepartamento)) {
                validacionesDepartamento = true;
            } else {
                validacionesDepartamento = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDepartamento", new FacesMessage("El campo Departamento es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarDocente actualizarDepartamentos:  " + e.toString());
            logger.error("Error ControllerRegistrarDocente actualizarDepartamentos : " + e.toString());
        }
    }

    public void actualizarCargos() {
        try {
            if (Utilidades.validarNulo(inputCargo)) {
                validacionesCargo = true;
            } else {
                validacionesCargo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputCargo", new FacesMessage("El campo Cargo es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarDocente actualizarCargos:  " + e.toString());
            logger.error("Error ControllerRegistrarDocente actualizarCargos : " + e.toString());
        }
    }

    public void validarNombreDocente() {
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

    }

    public void validarApellidoDocente() {
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
    }

    public void validarCorreoDocente() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty()) && (inputEmail.trim().length() > 0)) {
            int tam = inputEmail.length();
            if (tam >= 4) {
                String correoDocente = inputEmail + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correoDocente)) {
                    Docente registro = administrarDocentesBO.obtenerDocentePorCorreo(inputEmail);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        validacionesCorreo = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo ya se encuentra registrado. " + constantes.USUARIO_CORREO));
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.USUARIO_CORREO));
                }
            } else {
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio. " + constantes.USUARIO_CORREO));
        }
    }

    public void validarCorreoOpcionalDocente() {
        if (Utilidades.validarNulo(inputEmailOpcional) && (!inputEmailOpcional.isEmpty()) && (inputEmailOpcional.trim().length() > 0)) {
            int tam = inputEmailOpcional.length();
            if (tam >= 15) {
                if (Utilidades.validarCorreoElectronico(inputEmailOpcional)) {
                    validacionesCorreoOpcional = true;
                } else {
                    validacionesCorreoOpcional = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmailOpcional", new FacesMessage("El correo se encuentra incorrecto. " + constantes.USUARIO_CORREO_OPC));
                }
            } else {
                validacionesCorreoOpcional = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEmailOpcional", new FacesMessage("El tamaño minimo permitido es 15 caracteres. " + constantes.USUARIO_CORREO_OPC));
            }
        }
    }

    public void validarIdentificacionDocente() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty()) && (inputID.trim().length() > 0)) {
            int tam = inputID.length();
            if (tam >= 6) {
                if (Utilidades.validarNumeroIdentificacion(inputID)) {
                    Docente registro = administrarDocentesBO.obtenerDocentePorDocumento(inputID);
                    if (null == registro) {
                        validacionesID = true;
                    } else {
                        validacionesID = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El documento ya se encuentra registrado."));
                    }
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
    }

    public void validarDatosNumericosDocente(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty()) && (inputTelefono1.trim().length() > 0)) {
                int tam = inputTelefono1.length();
                if (tam == 4) {
                    if ((Utilidades.validarCaracteresAlfaNumericos(inputTelefono1)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero de extensión se encuentra incorrecto. " + constantes.USUARIO_TELEXT));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero de extensión se encuentra incorrecto. " + constantes.USUARIO_TELEXT));
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
    }

    public void validarDireccionDocente() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty()) && (inputDireccion.trim().length() > 0)) {
            int tam = inputDireccion.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(inputDireccion)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("La dirección se encuentra incorrecta. " + constantes.USUARIO_DIRECCION));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. " + constantes.USUARIO_DIRECCION));
                validacionesDireccion = false;
            }
        }
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
        if (validacionesCargo == false) {
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
     * del nuevo docente
     */
    public void registrarNuevoDocente() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoDocenteEnSistema();
            //EnvioCorreo correo = new EnvioCorreo();
            //correo.enviarCorreoCreacionCuenta(inputEmail+ "@ucentral.edu.co");
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            logger.error("Funciona sin problema");
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroDocente() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        activarCasillas = false;
        colorMensaje = "black";
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesCorreoOpcional = true;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesCargo = false;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        //
        activarDepartamento = true;
        inputApellido = null;
        inputFacultad = null;
        inputDireccion = null;
        inputEmail = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmailOpcional = null;
        inputID = null;
        inputNombre = null;
        inputDepartamento = null;
        inputCargo = null;
        listaFacultades = null;
        listaDepartamentos = null;
    }

    public void limpiarFormulario() {
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesCargo = false;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        validacionesCorreoOpcional = true;
        //
        activarDepartamento = true;
        inputApellido = null;
        inputFacultad = null;
        inputDireccion = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmail = null;
        inputID = null;
        inputEmailOpcional = null;
        inputNombre = null;
        inputDepartamento = null;
        inputCargo = null;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información el nuevo
     * docente
     */
    private void almacenarNuevoDocenteEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setNumeroconexiones(0);
            usuarioNuevo.setNombreusuario(inputID);
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuarioNuevo.setPasswordusuario(obj.encriptarContrasenia(inputID));
            Persona personaNueva = new Persona();
            personaNueva.setApellidospersona(inputApellido);
            personaNueva.setDireccionpersona(inputDireccion);
            personaNueva.setEmailpersona(inputEmail);
            personaNueva.setEmailsecundario(inputEmailOpcional);
            personaNueva.setIdentificacionpersona(inputID);
            personaNueva.setNombrespersona(inputNombre);
            personaNueva.setTelefono1persona(inputTelefono1);
            personaNueva.setTelefono2persona(inputTelefono2);
            Docente docenteNueva = new Docente();
            docenteNueva.setDepartamento(inputDepartamento);
            docenteNueva.setCargo(inputCargo);
            administrarDocentesBO.almacenarNuevoDocenteEnSistema(usuarioNuevo, personaNueva, docenteNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarDocente almacenarNuevoDocenteEnSistema:  " + e.toString());
            logger.error("Error ControllerRegistrarDocente almacenarNuevoDocenteEnSistema : " + e.toString());
        }
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
        return "administrardocentes.xhtml";
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

    public List<TipoCargo> getListaTiposCargos() {
        if (null == listaTiposCargos) {
            listaTiposCargos = administrarDocentesBO.obtenerListaTiposCargos();
        }
        return listaTiposCargos;
    }

    public void setListaTiposCargos(List<TipoCargo> listaTiposCargos) {
        this.listaTiposCargos = listaTiposCargos;
    }

    public TipoCargo getInputCargo() {
        return inputCargo;
    }

    public void setInputCargo(TipoCargo inputCargo) {
        this.inputCargo = inputCargo;
    }

    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = administrarDocentesBO.obtenerListaFacultadesActivas();
        }
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Facultad getInputFacultad() {
        return inputFacultad;
    }

    public void setInputFacultad(Facultad inputFacultad) {
        this.inputFacultad = inputFacultad;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getInputDepartamento() {
        return inputDepartamento;
    }

    public void setInputDepartamento(Departamento inputDepartamento) {
        this.inputDepartamento = inputDepartamento;
    }

    public boolean isActivarDepartamento() {
        return activarDepartamento;
    }

    public void setActivarDepartamento(boolean activarDepartamento) {
        this.activarDepartamento = activarDepartamento;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
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
