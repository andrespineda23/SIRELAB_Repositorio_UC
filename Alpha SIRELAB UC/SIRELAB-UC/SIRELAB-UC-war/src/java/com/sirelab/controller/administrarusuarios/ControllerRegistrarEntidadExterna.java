/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEntidadesExternasBOInterface;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.SectorEntidad;
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

    private String inputNombre, inputID, inputEmail, inputTelefono1, inputDireccion;
    private String paginaAnterior;
    private List<SectorEntidad> listaSectorEntidad;
    private SectorEntidad inputSector;
    //
    private boolean validacionesNombre, validacionesSector, validacionesCorreo;
    private boolean validacionesID, validacionesTel1;
    private boolean validacionesDireccion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarEntidadExterna() {
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
        validacionesSector = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = false;
        validacionesDireccion = false;
        //
        inputSector = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        BasicConfigurator.configure();
    }

    public void validarNombreEntidadExterna() {
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

    public void validarSectorEntidadExterna() {
        if (Utilidades.validarNulo(inputSector)) {
            validacionesSector = true;
        } else {
            validacionesSector = false;
            FacesContext.getCurrentInstance().addMessage("form:inputSector", new FacesMessage("El sector es obligatorio."));
        }
    }

    public void validarCorreoEntidadExterna() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty()) && (inputEmail.trim().length() > 0)) {
            int tam = inputEmail.length();
            if (tam >= 15) {
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
                    FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo se encuentra incorrecto. " + constantes.ENTIDAD_CORREO));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El tamaño minimo permitido es 15 caracteres. " + constantes.ENTIDAD_CORREO));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio. " + constantes.ENTIDAD_CORREO));
        }
    }

    public void validarIdentificacionEntidadExterna() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty()) && (inputID.trim().length() > 0)) {
            int tam = inputID.length();
            if (tam >= 6) {
                if (Utilidades.validarNumeroIdentificacion(inputID)) {
                    EntidadExterna registro = administrarEntidadesExternasBO.obtenerEntidadExternaPorIdentificacion(inputID);
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

    public void validarDatosNumericosEntidadExterna(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty()) && (inputTelefono1.trim().length() > 0)) {
                int tam = inputTelefono1.length();
                if (tam <= 45) {
                    if ((Utilidades.validarCaracteresAlfaNumericos(inputTelefono1)) == false) {
                        validacionesTel1 = false;
                        FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.ENTIDAD_TELEFONO));
                    } else {
                        validacionesTel1 = true;
                    }
                } else {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto. " + constantes.ENTIDAD_TELEFONO));
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
        if (validacionesSector == false) {
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
        if (validacionesTel1 == false) {
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
        validacionesSector = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = false;
        validacionesDireccion = false;
        mensajeFormulario = "";
        inputSector = null;
        inputDireccion = null;
        inputTelefono1 = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
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
        validacionesSector = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesTel1 = false;
        validacionesDireccion = false;
        listaSectorEntidad = null;
        mensajeFormulario = "";
        inputSector = null;
        inputDireccion = null;
        inputTelefono1 = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información de la
     * nueva entidad externa
     */
    public void almacenarNuevoEntidadExternaEnSistema() {
        try {
            EntidadExterna entidadexternaNueva = new EntidadExterna();
            entidadexternaNueva.setEstado(true);
            entidadexternaNueva.setSector(inputSector);
            entidadexternaNueva.setNombreentidad(inputNombre);
            entidadexternaNueva.setDireccionentidad(inputDireccion);
            entidadexternaNueva.setEmailentidad(inputEmail);
            entidadexternaNueva.setIdentificacion(inputID);
            entidadexternaNueva.setTelefonoentidad(inputTelefono1);
            administrarEntidadesExternasBO.almacenarNuevaEntidadExternaEnSistema(entidadexternaNueva);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarEncargadoLaboratorio almacenarNuevoEntidadExternaEnSistema:  " + e.toString());
            logger.error("Error ControllerRegistrarEntidadExterna almacenarNuevoEntidadExternaEnSistema : " + e.toString());
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
        return "administrarentidadesexternas.xhtml";
    }

    //GET - SET
    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public List<SectorEntidad> getListaSectorEntidad() {
        if (null == listaSectorEntidad) {
            listaSectorEntidad = administrarEntidadesExternasBO.obtenerSectorEntidadRegistrado();
        }
        return listaSectorEntidad;
    }

    public void setListaSectorEntidad(List<SectorEntidad> listaSectorEntidad) {
        this.listaSectorEntidad = listaSectorEntidad;
    }

    public SectorEntidad getInputSector() {
        return inputSector;
    }

    public void setInputSector(SectorEntidad inputSector) {
        this.inputSector = inputSector;
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

    public String getInputDireccion() {
        return inputDireccion;
    }

    public void setInputDireccion(String inputDireccion) {
        this.inputDireccion = inputDireccion;
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
