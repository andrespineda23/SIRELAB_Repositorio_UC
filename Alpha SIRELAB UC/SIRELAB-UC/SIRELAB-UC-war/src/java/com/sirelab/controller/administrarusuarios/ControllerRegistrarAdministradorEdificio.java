/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresEdificioBOInterface;
import com.sirelab.entidades.AdministradorEdificio;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Sede;
import com.sirelab.entidades.Persona;
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
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarAdministradorEdificio implements Serializable {

    //
    @EJB
    AdministrarAdministradoresEdificioBOInterface administrarAdministradoresEdificioBO;
    //

    private String inputNombre, inputApellido, inputID, inputEmail, inputEmailOpcional, inputTelefono1, inputTelefono2, inputDireccion;
    private List<Sede> listaSedes;
    private Sede inputSede;
    private List<Edificio> listaEdificios;
    private Edificio inputEdificio;
    private boolean activarEdificio;
    private String inputNAtencion;
    private String paginaAnterior;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo, validacionesCorreoOpcional;
    private boolean validacionesID, validacionesTel1, validacionesTel2, validacionesNAtencion;
    private boolean validacionesDireccion, validacionesSede, validacionesEdificio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarAdministradorEdificio() {
    }

    @PostConstruct
    public void init() {
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesNAtencion = true;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesSede = false;
        validacionesEdificio = false;
        validacionesCorreoOpcional = true;
        //
        activarEdificio = true;

        inputApellido = null;
        inputSede = null;
        inputEmailOpcional = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNAtencion = null;
        inputNombre = null;
        inputEdificio = null;
        listaEdificios = null;

        BasicConfigurator.configure();
    }

    /**
     * Metodo encargado de la actualizacion de las sedees por parte del usuario
     * y obtiene los departamentos relacionados
     */
    public void actualizarSedes() {
        try {
            if (Utilidades.validarNulo(inputSede)) {
                activarEdificio = false;
                inputEdificio = null;
                listaEdificios = administrarAdministradoresEdificioBO.obtenerEdificiosActivosPorIDSede(inputSede.getIdsede());
                validacionesSede = true;
            } else {
                validacionesSede = false;
                validacionesEdificio = false;

                activarEdificio = true;
                listaEdificios = null;
                inputEdificio = null;
                FacesContext.getCurrentInstance().addMessage("form:inputEdificio", new FacesMessage("El campo Sede es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarAdministradorEdificio actualizarEdificios:  " + e.toString());
            System.out.println("Error ControllerRegistrarAdministradorEdificio actualizarEdificios : " + e.toString());
        }
    }

    /**
     * Metodo encargado de la actualizacion de los departamentos por parte del
     * usuario y obtiene los laboratorios relacionados
     */
    public void actualizarEdificios() {
        try {
            if (Utilidades.validarNulo(inputEdificio)) {
                validacionesEdificio = true;
            } else {
                validacionesEdificio = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEdificio", new FacesMessage("El campo Edificio es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarAdministradorEdificio actualizarEdificios:  " + e.toString());
            System.out.println("Error ControllerRegistrarAdministradorEdificio actualizarEdificios : " + e.toString());
        }
    }

    public void actualizarNAtencion() {
        if (Utilidades.validarNulo(inputNAtencion)) {
            if (!Utilidades.validarCaracteresAlfaNumericos(inputNAtencion)) {
                validacionesNAtencion = true;
                FacesContext.getCurrentInstance().addMessage("form:inputNAtencion", new FacesMessage("El número ingresado ingresado es incorrecto."));
            } else {
                validacionesNAtencion = true;
            }
        }
    }

    public void validarNombreAdministradorEdificio() {
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
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarApellidoAdministradorEdificio() {
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
                FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido es obligatorio."));
        }
    }

    public void validarCorreoAdministradorEdificio() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty()) && (inputEmail.trim().length() > 0)) {
            int tam = inputEmail.length();
            if (tam >= 4) {
                String correoAdministradorEdificio = inputEmail + "@ucentral.edu.co";
                if (Utilidades.validarCorreoElectronico(correoAdministradorEdificio)) {
                    AdministradorEdificio registro = administrarAdministradoresEdificioBO.obtenerAdministradorEdificioPorCorreo(inputEmail);
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
                FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio."));
        }
    }

    public void validarCorreoOpcionalAdministradorEdificio() {
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
                FacesContext.getCurrentInstance().addMessage("form:inputEmailOpcional", new FacesMessage("El tamaño minimo permitido es 15 caracteres."));
            }
        }
    }

    public void validarIdentificacionAdministradorEdificio() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty()) && (inputID.trim().length() > 0)) {
            int tam = inputID.length();
            if (tam >= 8) {
                if (Utilidades.validarNumeroIdentificacion(inputID)) {
                    AdministradorEdificio registro = administrarAdministradoresEdificioBO.obtenerAdministradorEdificioPorDocumento(inputID);
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
                FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación es obligatorio."));
        }
    }

    public void validarDatosNumericosAdministradorEdificio(int tipoTel) {
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

    public void validarDireccionAdministradorEdificio() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty()) && (inputDireccion.trim().length() > 0)) {
            int tam = inputDireccion.length();
            if (tam > 7) {
                if (Utilidades.validarDirecciones(inputDireccion)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
                validacionesDireccion = false;
            }
        }
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
        if (validacionesNAtencion == false) {
            retorno = false;
        }
        if (validacionesSede == false) {
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
     * del nuevo encargado
     */
    public void registrarNuevoAdministradorEdificio() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoAdministradorEdificioEnSistema();
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

    public void limpiarFormulario() {
        activarEdificio = true;

        inputApellido = null;
        inputSede = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputEdificio = null;
        inputEmailOpcional = null;
        inputNAtencion = null;
        validacionesNAtencion = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesCorreoOpcional = true;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesSede = false;
        validacionesEdificio = false;

        mensajeFormulario = "";
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroAdministradorEdificio() {
        mensajeFormulario = "N/A";
        activarCasillas = false;
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarEdificio = true;

        inputApellido = null;
        inputSede = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputNombre = null;
        inputEdificio = null;
        listaSedes = null;
        listaEdificios = null;

        inputNAtencion = null;
        validacionesNAtencion = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesSede = false;
        validacionesEdificio = false;

    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información el nuevo
     * encargado
     */
    private void almacenarNuevoAdministradorEdificioEnSistema() {
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
            AdministradorEdificio nuevoRegistro = new AdministradorEdificio();
            nuevoRegistro.setNumeroatencion(inputNAtencion);
            administrarAdministradoresEdificioBO.almacenarNuevoAdministradorEdificioEnSistema(usuarioNuevo, personaNueva, nuevoRegistro, inputEdificio);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarAdministradorEdificio almacenarNuevoAdministradorEdificioEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarAdministradorEdificio almacenarNuevoAdministradorEdificioEnSistema : " + e.toString());
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
        return "index";
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

    public String getInputEmailOpcional() {
        return inputEmailOpcional;
    }

    public void setInputEmailOpcional(String inputEmailOpcional) {
        this.inputEmailOpcional = inputEmailOpcional;
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

    public List<Sede> getListaSedes() {
        if (null == listaSedes) {
            listaSedes = administrarAdministradoresEdificioBO.obtenerListaSedesActivos();
        }
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public Sede getInputSede() {
        return inputSede;
    }

    public void setInputSede(Sede inputSede) {
        this.inputSede = inputSede;
    }

    public List<Edificio> getListaEdificios() {
        return listaEdificios;
    }

    public void setListaEdificios(List<Edificio> listaEdificios) {
        this.listaEdificios = listaEdificios;
    }

    public Edificio getInputEdificio() {
        return inputEdificio;
    }

    public void setInputEdificio(Edificio inputEdificio) {
        this.inputEdificio = inputEdificio;
    }

    public boolean isActivarEdificio() {
        return activarEdificio;
    }

    public void setActivarEdificio(boolean activarEdificio) {
        this.activarEdificio = activarEdificio;
    }

    public String getInputNAtencion() {
        return inputNAtencion;
    }

    public void setInputNAtencion(String inputNAtencion) {
        this.inputNAtencion = inputNAtencion;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
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
