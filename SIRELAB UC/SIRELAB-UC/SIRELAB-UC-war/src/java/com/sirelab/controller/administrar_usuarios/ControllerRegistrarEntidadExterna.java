/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.ayuda.EnvioCorreo;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEntidadesExternasBOInterface;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Controlador: ControllerRegistrarEntidadExterna Este controlador se encarga
 * del proceso de registro de una nueva entidad externa al sistema de
 * informacion
 *
 * @author ANDRES PINEDA
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarEntidadExterna implements Serializable {

    //
    @EJB
    AdministrarEntidadesExternasBOInterface administrarEntidadesExternasBO;
    //

    private String inputNombre, inputApellido, inputID, inputEmail, inputTelefono1, inputTelefono2, inputDireccion;
    private String inputIDEntidad, inputNombreEntidad, inputEmailEntidad;
    private String paginaAnterior;
    //
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesIDEntidad, validacionesNombreEntidad, validacionesEmailEntidad;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarEntidadExterna() {
    }

    @PostConstruct
    public void init() {
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
        validacionesIDEntidad = false;
        validacionesEmailEntidad = false;
        validacionesNombreEntidad = false;
        //
        inputNombreEntidad = null;
        inputEmailEntidad = null;
        inputApellido = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputIDEntidad = null;
        BasicConfigurator.configure();
    }

    public void validarNombreEntidadExterna() {
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

    public void validarApellidoEntidadExterna() {
        if (Utilidades.validarNulo(inputApellido) && (!inputApellido.isEmpty()) && (inputApellido.trim().length() > 0)) {
            int tam = inputApellido.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracterString(inputApellido)) {
                    validacionesApellido = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesApellido = true;
                }
            } else {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCorreoEntidadExterna() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty()) && (inputEmail.trim().length() > 0)) {
            int tam = inputEmail.length();
            if (tam >= 4) {
                if (Utilidades.validarCorreoElectronico(inputEmail)) {
                    EntidadExterna registro = administrarEntidadesExternasBO.obtenerEntidadExternaPorCorreo(inputEmail);
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

    public void validarIdentificacionEntidadExterna() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty()) && (inputID.trim().length() > 0)) {
            int tam = inputID.length();
            if (tam >= 8) {
                if (Utilidades.validarNumeroIdentificacion(inputID)) {
                    EntidadExterna registro = administrarEntidadesExternasBO.obtenerEntidadExternaPorDocumento(inputID);
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

    public void validarDatosNumericosEntidadExterna(int tipoTel) {
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

    public void validarDireccionEntidadExterna() {
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
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres."));
                validacionesDireccion = false;
            }
        }
    }

    public void validarDatosEntidadExterna(int tipo) {
        if (tipo == 1) {
            if (Utilidades.validarNulo(inputIDEntidad) && (!inputIDEntidad.isEmpty()) && (inputIDEntidad.trim().length() > 0)) {
                if (Utilidades.validarCaracteresAlfaNumericos(inputIDEntidad)) {
                    EntidadExterna registro = administrarEntidadesExternasBO.obtenerEntidadExternaPorIdentificacion(inputIDEntidad);
                    if (null == registro) {
                        validacionesIDEntidad = true;
                    } else {
                        validacionesIDEntidad = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputIDEntidad", new FacesMessage("El documento ya se encuentra registrado."));
                    }
                } else {
                    validacionesIDEntidad = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputIDEntidad", new FacesMessage("El numero identificación se encuentra incorrecto."));
                }
            } else {
                validacionesIDEntidad = false;
                FacesContext.getCurrentInstance().addMessage("form:inputIDEntidad", new FacesMessage("El numero identificación es obligatorio."));
            }
        }
        if (tipo == 2) {
            if (Utilidades.validarNulo(inputNombreEntidad) && (!inputNombreEntidad.isEmpty()) && (inputNombreEntidad.trim().length() > 0)) {
                if (!Utilidades.validarCaracterString(inputNombreEntidad)) {
                    validacionesNombreEntidad = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputNombreEntidad", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombreEntidad = true;
                }
            }
        }
        if (tipo == 3) {
            if (Utilidades.validarNulo(inputEmailEntidad) && (!inputEmailEntidad.isEmpty()) && (inputEmailEntidad.trim().length() > 0)) {
                if (Utilidades.validarCorreoElectronico(inputEmailEntidad)) {
                    validacionesEmailEntidad = true;
                } else {
                    validacionesEmailEntidad = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmailEntidad", new FacesMessage("El correo se encuentra incorrecto."));
                }
            }
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesApellido == false) {
            retorno = false;
        }
        if (validacionesEmailEntidad == false) {
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

        if (validacionesIDEntidad == false) {
            retorno = false;
        }
        if (validacionesNombreEntidad == false) {
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
     * de la nueva entidad externa
     */
    public void registrarNuevoEntidadExterna() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoEntidadExternaEnSistema();
            //EnvioCorreo correo = new EnvioCorreo();
            //correo.enviarCorreoCreacionCuenta(inputEmail);
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
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesIDEntidad = false;
        validacionesEmailEntidad = false;
        validacionesNombreEntidad = false;
        mensajeFormulario = "";
        inputApellido = null;
        inputDireccion = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputIDEntidad = null;
        inputNombreEntidad = null;
        inputEmailEntidad = null;
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroEntidadExterna() {
        mensajeFormulario = "N/A";
        activarCasillas = false;
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesIDEntidad = false;
        validacionesEmailEntidad = false;
        validacionesNombreEntidad = false;
        mensajeFormulario = "";
        inputApellido = null;
        inputDireccion = null;
        inputTelefono1 = null;
        inputTelefono2 = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputIDEntidad = null;
        inputNombreEntidad = null;
        inputEmailEntidad = null;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información de la
     * nueva entidad externa
     */
    public void almacenarNuevoEntidadExternaEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setEnlinea(false);
            usuarioNuevo.setNumeroconexiones(0);
            usuarioNuevo.setNombreusuario(inputID);
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuarioNuevo.setPasswordusuario(obj.encriptarContrasenia(inputID));
            Persona personaNueva = new Persona();
            personaNueva.setApellidospersona(inputApellido);
            personaNueva.setEmailpersona(inputEmail);
            personaNueva.setEmailsecundario(inputEmail);
            personaNueva.setIdentificacionpersona(inputID);
            personaNueva.setNombrespersona(inputNombre);
            if ((Utilidades.validarNulo(inputTelefono1)) && (!inputTelefono1.isEmpty()) && (inputTelefono1.trim().length() > 0)) {
                personaNueva.setTelefono1persona(inputTelefono1);
            } else {
                personaNueva.setTelefono1persona("");
            }
            if ((Utilidades.validarNulo(inputTelefono2)) && (!inputTelefono2.isEmpty()) && (inputTelefono2.trim().length() > 0)) {
                personaNueva.setTelefono2persona(inputTelefono2);
            } else {
                personaNueva.setTelefono2persona("");
            }
            if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty()) && (inputDireccion.trim().length() > 0)) {
                personaNueva.setDireccionpersona(inputDireccion);
            } else {
                personaNueva.setDireccionpersona("");
            }
            EntidadExterna entidadexternaNueva = new EntidadExterna();
            if ((Utilidades.validarNulo(inputEmailEntidad)) && (!inputEmailEntidad.isEmpty()) && (inputEmailEntidad.trim().length() > 0)) {
                entidadexternaNueva.setEmailentidad(inputEmailEntidad);
            } else {
                entidadexternaNueva.setEmailentidad("");
            }
            entidadexternaNueva.setIdentificacionentidad(inputIDEntidad);
            if ((Utilidades.validarNulo(inputNombreEntidad)) && (!inputNombreEntidad.isEmpty()) && (inputNombreEntidad.trim().length() > 0)) {
                entidadexternaNueva.setNombreentidad(inputNombreEntidad);
            } else {
                entidadexternaNueva.setNombreentidad("");
            }
            administrarEntidadesExternasBO.almacenarNuevaEntidadExternaEnSistema(usuarioNuevo, personaNueva, entidadexternaNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarEncargadoLaboratorio almacenarNuevoEntidadExternaEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarEntidadExterna almacenarNuevoEntidadExternaEnSistema : " + e.toString());
        }
    }

    public void recibirPaginaAnterior(String page) {
        paginaAnterior = page;
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

    public String retornarPaginaAnterior() {
        cancelarRegistroEntidadExterna();
        if (null != paginaAnterior) {
            return paginaAnterior;
        }
        return "administrar_entidadesexternas.xhtml";
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

    public String getInputIDEntidad() {
        return inputIDEntidad;
    }

    public void setInputIDEntidad(String inputIDEntidad) {
        this.inputIDEntidad = inputIDEntidad;
    }

    public String getInputNombreEntidad() {
        return inputNombreEntidad;
    }

    public void setInputNombreEntidad(String inputNombreEntidad) {
        this.inputNombreEntidad = inputNombreEntidad;
    }

    public String getInputEmailEntidad() {
        return inputEmailEntidad;
    }

    public void setInputEmailEntidad(String inputEmailEntidad) {
        this.inputEmailEntidad = inputEmailEntidad;
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
