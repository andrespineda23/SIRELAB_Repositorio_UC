/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoProveedoresBOInterface;
import com.sirelab.entidades.Proveedor;
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
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarProveedor implements Serializable {

    @EJB
    GestionarRecursoProveedoresBOInterface gestionarRecursoProveedoresBO;

    private String nuevoNombre, nuevoNIT, nuevoTelefono, nuevoDireccion;
    private String nuevoVendedor, nuevoTelVendedor;
    //
    private boolean validacionesNombre, validacionesNIT, validacionesTelefono, validacionesDireccion;
    private boolean validacionesVendedor, validacionesTelVendedor;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;

    public ControllerRegistrarProveedor() {
    }

    @PostConstruct
    public void init() {
        nuevoDireccion = null;
        nuevoNIT = null;
        nuevoNombre = null;
        nuevoTelVendedor = null;
        nuevoTelefono = null;
        nuevoVendedor = null;
        //
        validacionesDireccion = false;
        validacionesNIT = false;
        validacionesNombre = false;
        validacionesTelVendedor = true;
        validacionesTelefono = false;
        validacionesVendedor = true;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        activarAceptar = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
    }

    public void validarNombreProveedor() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.RECURSO_NOM));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres. "+constantes.RECURSO_NOM));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio. "+constantes.RECURSO_NOM));
        }
    }

    public void validarNITProveedor() {
        if (Utilidades.validarNulo(nuevoNIT) && (!nuevoNIT.isEmpty()) && (nuevoNIT.trim().length() > 0)) {
            int tam = nuevoNIT.length();
            if (tam >= 8) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoNIT)) {
                    Proveedor registro = gestionarRecursoProveedoresBO.obtenerProveedorPorNIT(nuevoNIT);
                    if (registro == null) {
                        validacionesNIT = true;
                    } else {
                        validacionesNIT = false;
                        FacesContext.getCurrentInstance().addMessage("form:nuevoNIT", new FacesMessage("El NIT ingresado ya esta registrado."));
                    }
                } else {
                    validacionesNIT = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNIT", new FacesMessage("El NIT ingresado es incorrecto. "+constantes.RECURSO_NIT));
                }
            } else {
                validacionesNIT = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNIT", new FacesMessage("El tamaño minimo permitido es 8 caracteres. "+constantes.RECURSO_NIT));
            }
        } else {
            validacionesNIT = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNIT", new FacesMessage("El NIT es obligatorio. "+constantes.RECURSO_NIT));
        }
    }

    public void validarTelefonoProveedor() {
        if (Utilidades.validarNulo(nuevoTelefono) && (!nuevoTelefono.isEmpty()) && (nuevoTelefono.trim().length() > 0)) {
            if ((Utilidades.validarCaracteresAlfaNumericos(nuevoTelefono)) == false) {
                validacionesTelefono = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono", new FacesMessage("El numero telefonico se encuentra incorrecto. "+constantes.ENTIDAD_TELEFONO));
            } else {
                validacionesTelefono = true;
            }
        } else {
            validacionesTelefono = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono", new FacesMessage("El numero telefonico es obligatorio. "+constantes.ENTIDAD_TELEFONO));
        }
    }

    public void validarDireccionProveedor() {
        if ((Utilidades.validarNulo(nuevoDireccion)) && (!nuevoDireccion.isEmpty()) && (nuevoDireccion.trim().length() > 0)) {
            int tam = nuevoDireccion.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(nuevoDireccion)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección se encuentra incorrecta. "+constantes.USUARIO_DIRECCION));
                    validacionesDireccion = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. "+constantes.USUARIO_DIRECCION));
                validacionesDireccion = false;
            }
        }
    }

    public void validarDatosOpcionales(int tipoReg) {
        if (tipoReg == 1) {
            if (Utilidades.validarNulo(nuevoVendedor) && (!nuevoVendedor.isEmpty()) && (nuevoVendedor.trim().length() > 0)) {
                int tam = nuevoVendedor.length();
                if (tam >= 2) {
                    if (Utilidades.validarCaracterString(nuevoVendedor)) {
                        validacionesVendedor = true;
                    } else {
                        validacionesVendedor = false;
                        FacesContext.getCurrentInstance().addMessage("form:nuevoVendedor", new FacesMessage("El nombre se encuentra incorrecto. "+constantes.RECURSO_VENDEDOR));
                    }
                } else {
                    validacionesVendedor = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoVendedor", new FacesMessage("El tamaño minimo permitido es 2 caracteres. "+constantes.RECURSO_VENDEDOR));
                }
            }
        } else {
            if (Utilidades.validarNulo(nuevoTelVendedor) && (!nuevoTelVendedor.isEmpty()) && (nuevoTelVendedor.trim().length() > 0)) {
                int tam = nuevoTelVendedor.length();
                if (tam >= 7) {
                    if (Utilidades.validarCaracteresAlfaNumericos(nuevoTelVendedor)) {
                        validacionesTelVendedor = true;
                    } else {
                        validacionesTelVendedor = false;
                        FacesContext.getCurrentInstance().addMessage("form:nuevoTelVendedor", new FacesMessage("El telefono se encuentra incorrecto. "+constantes.ENTIDAD_TELEFONO));
                    }
                } else {
                    validacionesTelVendedor = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoTelVendedor", new FacesMessage("El tamaño minimo permitido es 7 caracteres. "+constantes.ENTIDAD_TELEFONO));
                }
            }
        }

    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesNIT == false) {
            retorno = false;
        }
        if (validacionesDireccion == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesTelefono == false) {
            retorno = false;
        }
        if (validacionesVendedor == false) {
            retorno = false;
        }
        if (validacionesTelVendedor == false) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoProveedor() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoProveedorEnSistema();
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

    public void almacenarNuevoProveedorEnSistema() {
        try {
            Proveedor nuevaProveedor = new Proveedor();
            nuevaProveedor.setNitproveedor(nuevoNIT);
            nuevaProveedor.setNombreproveedor(nuevoNombre);
            nuevaProveedor.setDireccionproveedor(nuevoDireccion);
            nuevaProveedor.setTelefonoproveedor(nuevoTelefono);
            if (Utilidades.validarNulo(nuevoVendedor)) {
                nuevaProveedor.setVendedorproveedor(nuevoVendedor);
            } else {
                nuevaProveedor.setVendedorproveedor("");
            }
            if (Utilidades.validarNulo(nuevoTelVendedor)) {
                nuevaProveedor.setTelefonovendedor(nuevoTelVendedor);
            } else {
                nuevaProveedor.setTelefonovendedor("");
            }
            gestionarRecursoProveedoresBO.crearNuevoProveedor(nuevaProveedor);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarProveedor almacenarNuevoProveedorEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarProveedor almacenarNuevoProveedorEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        nuevoDireccion = null;
        nuevoNIT = null;
        nuevoNombre = null;
        nuevoTelVendedor = null;
        nuevoTelefono = null;
        nuevoVendedor = null;
        //
        validacionesDireccion = false;
        validacionesNIT = false;
        validacionesNombre = false;
        validacionesTelVendedor = true;
        validacionesTelefono = false;
        validacionesVendedor = true;
        mensajeFormulario = "";
    }

    public void cancelarRegistroProveedor() {
        nuevoDireccion = null;
        nuevoNIT = null;
        nuevoNombre = null;
        nuevoTelVendedor = null;
        nuevoTelefono = null;
        nuevoVendedor = null;
        //
        validacionesDireccion = false;
        validacionesNIT = false;
        validacionesNombre = false;
        validacionesTelVendedor = true;
        validacionesTelefono = false;
        validacionesVendedor = true;
        activarAceptar = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarAceptar = false;
        activarLimpiar = true;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET
    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoNIT() {
        return nuevoNIT;
    }

    public void setNuevoNIT(String nuevoNIT) {
        this.nuevoNIT = nuevoNIT;
    }

    public String getNuevoTelefono() {
        return nuevoTelefono;
    }

    public void setNuevoTelefono(String nuevoTelefono) {
        this.nuevoTelefono = nuevoTelefono;
    }

    public String getNuevoDireccion() {
        return nuevoDireccion;
    }

    public void setNuevoDireccion(String nuevoDireccion) {
        this.nuevoDireccion = nuevoDireccion;
    }

    public String getNuevoVendedor() {
        return nuevoVendedor;
    }

    public void setNuevoVendedor(String nuevoVendedor) {
        this.nuevoVendedor = nuevoVendedor;
    }

    public String getNuevoTelVendedor() {
        return nuevoTelVendedor;
    }

    public void setNuevoTelVendedor(String nuevoTelVendedor) {
        this.nuevoTelVendedor = nuevoTelVendedor;
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
