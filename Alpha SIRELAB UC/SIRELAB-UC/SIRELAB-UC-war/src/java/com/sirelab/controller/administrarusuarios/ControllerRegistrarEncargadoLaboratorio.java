/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.EnvioCorreo;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.TipoPerfil;
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
 * Controlador: ControllerRegistrarEncargadoLaboratorio Este controlador se
 * encarga del registro de un nuevo encargado de laboratorio al sistema de
 * información
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarEncargadoLaboratorio implements Serializable {

    //
    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarEncargadosLaboratoriosBO;
    //

    private String inputNombre, inputApellido, inputID, inputEmail, inputEmailOpcional, inputTelefono1, inputTelefono2, inputDireccion;
    private List<Facultad> listaFacultades;
    private Facultad inputFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento inputDepartamento;
    private boolean activarDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio inputLaboratorio;
    private boolean activarLaboratorio;
    private List<TipoPerfil> listaTiposPerfiles;
    private TipoPerfil inputPerfil;
    private String paginaAnterior;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2, validacionesPerfil;
    private boolean validacionesDireccion, validacionesFacultad, validacionesDepartamento, validacionesLaboratorio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarEncargadoLaboratorio() {
    }

    @PostConstruct
    public void init() {
        mensajeError = "";
        constantes = new MensajesConstantes();
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesPerfil = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
        validacionesCorreoOpcional = true;
        //
        activarDepartamento = true;
        activarLaboratorio = true;
        inputApellido = null;
        inputFacultad = null;
        inputEmailOpcional = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputPerfil = null;
        inputNombre = null;
        inputDepartamento = null;
        listaDepartamentos = null;
        listaLaboratorios = null;
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
                listaDepartamentos = administrarEncargadosLaboratoriosBO.obtenerDepartamentosActivosPorIDFacultad(inputFacultad.getIdfacultad());
                activarLaboratorio = true;
                inputLaboratorio = null;
                listaLaboratorios = null;
                validacionesFacultad = true;
            } else {
                validacionesFacultad = false;
                validacionesDepartamento = false;
                validacionesLaboratorio = false;
                activarDepartamento = true;
                listaDepartamentos = null;
                inputDepartamento = null;
                activarLaboratorio = true;
                inputLaboratorio = null;
                listaLaboratorios = null;
                FacesContext.getCurrentInstance().addMessage("form:inputDepartamento", new FacesMessage("El campo Facultad es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarEncargadoLaboratorio actualizarDepartamentos:  " + e.toString(), e);
            logger.error("Error ControllerRegistrarEncargadoLaboratorio actualizarDepartamentos : " + e.toString(), e);
        }
    }

    /**
     * Metodo encargado de la actualizacion de los departamentos por parte del
     * usuario y obtiene los laboratorios relacionados
     */
    public void actualizarDepartamentos() {
        try {
            if (Utilidades.validarNulo(inputDepartamento)) {
                activarLaboratorio = false;
                inputLaboratorio = null;
                listaLaboratorios = administrarEncargadosLaboratoriosBO.obtenerLaboratoriosActivosPorIDDepartamento(inputDepartamento.getIddepartamento());
                validacionesDepartamento = true;
            } else {
                validacionesDepartamento = false;
                validacionesLaboratorio = false;
                activarLaboratorio = true;
                inputLaboratorio = null;
                listaLaboratorios = null;
                FacesContext.getCurrentInstance().addMessage("form:inputDepartamento", new FacesMessage("El campo Departamento es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarEncargadoLaboratorio actualizarDepartamentos:  " + e.toString(), e);
        }
    }

    public void actualizarLaboratorio() {
        if (Utilidades.validarNulo(inputLaboratorio)) {
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputLaboratorio", new FacesMessage("El campo Laboratorio es obligatorio."));
        }
    }

    public void actualizarTipoPerfil() {
        if (Utilidades.validarNulo(inputPerfil)) {
            validacionesPerfil = true;
        } else {
            validacionesPerfil = false;
            FacesContext.getCurrentInstance().addMessage("form:inputPerfil", new FacesMessage("El campo Perfil por Encargado es obligatorio."));
        }
    }

    public void validarNombreEncargadoLaboratorio() {
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

    public void validarApellidoEncargadoLaboratorio() {
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

    public void validarCorreoEncargadoLaboratorio() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty()) && (inputEmail.trim().length() > 0)) {
            int tam = inputEmail.length();
            if (tam >= 4) {
                String correoEncargadoLaboratorio = inputEmail + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correoEncargadoLaboratorio)) {
                    EncargadoLaboratorio registro = administrarEncargadosLaboratoriosBO.obtenerEncargadoLaboratorioPorCorreo(inputEmail);
                    if (null == registro) {
                        validacionesCorreo = true;
                    } else {
                        validacionesCorreo = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo ya se encuentra registrado."));
                    }
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo se encuentra incorrecto. " + constantes.USUARIO_CORREO));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.USUARIO_CORREO));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio. " + constantes.USUARIO_CORREO));
        }
    }

    public void validarCorreoOpcionalEncargadoLaboratorio() {
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

    public void validarIdentificacionEncargadoLaboratorio() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty()) && (inputID.trim().length() > 0)) {
            int tam = inputID.length();
            if (tam >= 6) {
                if (Utilidades.validarNumeroIdentificacion(inputID)) {
                    EncargadoLaboratorio registro = administrarEncargadosLaboratoriosBO.obtenerEncargadoLaboratorioPorDocumento(inputID);
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

    public void validarDatosNumericosEncargadoLaboratorio(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty()) && (inputTelefono1.trim().length() > 0)) {
                int tam = inputTelefono1.length();
                if (tam == 4) {
                    if ((Utilidades.isNumber(inputTelefono1)) == false) {
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

    public void validarDireccionEncargadoLaboratorio() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty()) && (inputDireccion.trim().length() > 0)) {
            int tam = inputDireccion.length();
            if (tam > 7) {
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
        mensajeError = "";
        if (validacionesApellido == false) {
            mensajeError = mensajeError + "- Apellido - ";
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            mensajeError = mensajeError + "- Departamento - ";
            retorno = false;
        }
        if (validacionesCorreo == false) {
            mensajeError = mensajeError + "- Correo Inst. - ";
            retorno = false;
        }
        if (validacionesCorreoOpcional == false) {
            mensajeError = mensajeError + "- Correo Pers. - ";
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
        if (validacionesPerfil == false) {
            mensajeError = mensajeError + "- Tipo Perfil - ";
            retorno = false;
        }
        if (validacionesFacultad == false) {
            mensajeError = mensajeError + "- Facultad - ";
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            mensajeError = mensajeError + "- Laboratorio - ";
            retorno = false;
        }
        if (validacionesTel1 == false) {
            mensajeError = mensajeError + "- Num. Extensión - ";
            retorno = false;
        }
        if (validacionesTel2 == false) {
            mensajeError = mensajeError + "- Telefono Cel. - ";
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo encargado
     */
    public void registrarNuevoEncargadoLaboratorio() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoEncargadoLaboratorioEnSistema();
            //EnvioCorreo correo = new EnvioCorreo();
            //correo.enviarCorreoCreacionCuenta(inputEmail+ "@ucentral.edu.co");
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    public void limpiarFormulario() {
        activarDepartamento = true;
        activarLaboratorio = true;
        inputApellido = null;
        inputFacultad = null;
        mensajeError = "";
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputDepartamento = null;
        inputEmailOpcional = null;
        inputLaboratorio = null;
        inputPerfil = null;
        validacionesPerfil = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesCorreoOpcional = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
        mensajeFormulario = "";
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroEncargadoLaboratorio() {
        mensajeFormulario = "N/A";
        activarCasillas = false;
        activarAceptar = false;
        mensajeError = "";
        activarLimpiar = true;
        colorMensaje = "black";
        activarDepartamento = true;
        activarLaboratorio = true;
        inputApellido = null;
        inputFacultad = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputNombre = null;
        inputDepartamento = null;
        inputLaboratorio = null;
        listaFacultades = null;
        listaDepartamentos = null;
        listaLaboratorios = null;
        listaTiposPerfiles = null;
        inputPerfil = null;
        validacionesPerfil = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información el nuevo
     * encargado
     */
    private void almacenarNuevoEncargadoLaboratorioEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setNumeroconexiones(0);
            usuarioNuevo.setNombreusuario(inputID);
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuarioNuevo.setPasswordusuario(obj.encriptarContrasenia(inputID));
            Persona personaNueva = new Persona();
            personaNueva.setApellidospersona(inputApellido);
            personaNueva.setEmailpersona(inputEmail);
            personaNueva.setIdentificacionpersona(inputID);
            personaNueva.setNombrespersona(inputNombre);
            if (Utilidades.validarNulo(inputEmailOpcional) && (!inputEmailOpcional.isEmpty()) && (inputEmailOpcional.trim().length() > 0)) {
                personaNueva.setEmailsecundario(inputEmailOpcional);
            } else {
                personaNueva.setEmailsecundario("");
            }
            if (Utilidades.validarNulo(inputDireccion) && (!inputDireccion.isEmpty()) && (inputDireccion.trim().length() > 0)) {
                personaNueva.setDireccionpersona(inputDireccion);
            } else {
                personaNueva.setDireccionpersona("");
            }
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty()) && (inputTelefono1.trim().length() > 0)) {
                personaNueva.setTelefono1persona(inputTelefono1);
            } else {
                personaNueva.setTelefono1persona("");
            }
            if (Utilidades.validarNulo(inputTelefono2) && (!inputTelefono2.isEmpty()) && (inputTelefono2.trim().length() > 0)) {
                personaNueva.setTelefono2persona(inputTelefono2);
            } else {
                personaNueva.setTelefono2persona("");
            }
            EncargadoLaboratorio encargadoNueva = new EncargadoLaboratorio();
            encargadoNueva.setLaboratorio(inputLaboratorio);
            encargadoNueva.setTipoperfil(inputPerfil);
            administrarEncargadosLaboratoriosBO.almacenarNuevoEncargadoLaboratorioEnSistema(usuarioNuevo, personaNueva, encargadoNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarEncargadoLaboratorio almacenarNuevoEncargadoLaboratorioEnSistema:  " + e.toString(), e);
            logger.error("Error ControllerRegistrarEncargadoLaboratorio almacenarNuevoEncargadoLaboratorioEnSistema : " + e.toString(), e);
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

    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = administrarEncargadosLaboratoriosBO.obtenerListaFacultadesActivos();
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

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getInputLaboratorio() {
        return inputLaboratorio;
    }

    public void setInputLaboratorio(Laboratorio inputLaboratorio) {
        this.inputLaboratorio = inputLaboratorio;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
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

    public List<TipoPerfil> getListaTiposPerfiles() {
        if (listaTiposPerfiles == null) {
            listaTiposPerfiles = administrarEncargadosLaboratoriosBO.consultarPerfilesPorEncargadoRegistrados();
        }
        return listaTiposPerfiles;
    }

    public void setListaTiposPerfiles(List<TipoPerfil> listaTiposPerfiles) {
        this.listaTiposPerfiles = listaTiposPerfiles;
    }

    public TipoPerfil getInputPerfil() {
        return inputPerfil;
    }

    public void setInputPerfil(TipoPerfil inputPerfil) {
        this.inputPerfil = inputPerfil;
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
