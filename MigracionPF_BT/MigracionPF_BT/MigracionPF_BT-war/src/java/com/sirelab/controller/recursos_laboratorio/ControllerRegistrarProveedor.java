/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

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
        mensajeFormulario = "";
        BasicConfigurator.configure();
    }

    public void validarNombreProveedor() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarNITProveedor() {
        if (Utilidades.validarNulo(nuevoNIT) && (!nuevoNIT.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(nuevoNIT)) {
                Proveedor registro = gestionarRecursoProveedoresBO.obtenerProveedorPorNIT(nuevoVendedor);
                if (registro == null) {
                    validacionesNIT = true;
                } else {
                    validacionesNIT = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNIT", new FacesMessage("El NIT ingresado ya esta registrado."));
                }
            } else {
                validacionesNIT = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNIT", new FacesMessage("El NIT ingresado es incorrecto."));
            }
        } else {
            validacionesNIT = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNIT", new FacesMessage("El NIT es obligatorio."));
        }
    }

    public void validarTelefonoProveedor() {
        if (Utilidades.validarNulo(nuevoTelefono) && (!nuevoTelefono.isEmpty())) {
            if ((Utilidades.isNumber(nuevoTelefono)) == false) {
                validacionesTelefono = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono", new FacesMessage("El numero telefonico se encuentra incorrecto."));
            } else {
                validacionesTelefono = true;
            }
        } else {
            validacionesTelefono = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTelefono", new FacesMessage("El numero telefonico es obligatorio."));
        }
    }

    public void validarDireccionProveedor() {
        if ((Utilidades.validarNulo(nuevoDireccion)) && (!nuevoDireccion.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(nuevoDireccion)) {
                validacionesDireccion = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:nuevoDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                validacionesDireccion = false;
            }
        }
    }

    public void validarDatosOpcionales(int tipoReg) {
        if (tipoReg == 1) {
            if (Utilidades.validarNulo(nuevoVendedor) && (!nuevoVendedor.isEmpty())) {
                if (Utilidades.validarCaracterString(nuevoVendedor)) {
                    validacionesVendedor = true;
                } else {
                    validacionesVendedor = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoVendedor", new FacesMessage("El nombre se encuentra incorrecto."));
                }
            }
        } else {
            if (Utilidades.validarNulo(nuevoTelVendedor) && (!nuevoTelVendedor.isEmpty())) {
                if (Utilidades.isNumber(nuevoTelVendedor)) {
                    validacionesTelVendedor = true;
                } else {
                    validacionesTelVendedor = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoTelVendedor", new FacesMessage("El telefono se encuentra incorrecto."));
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
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
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
            cancelarRegistroProveedor();
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarProveedor almacenarNuevoProveedorEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarProveedor almacenarNuevoProveedorEnSistema : " + e.toString());
        }
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
        mensajeFormulario = "";
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

}
