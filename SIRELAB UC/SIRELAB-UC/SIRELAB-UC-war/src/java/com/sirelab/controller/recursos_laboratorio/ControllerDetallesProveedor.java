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
import java.math.BigInteger;
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
public class ControllerDetallesProveedor implements Serializable {

    @EJB
    GestionarRecursoProveedoresBOInterface gestionarRecursoProveedoresBO;

    private Proveedor proveedorDetalle;
    private BigInteger idProveedor;
    private String editarNombre, editarNIT, editarTelefono, editarDireccion;
    private String editarVendedor, editarTelVendedor;
    //
    private boolean validacionesNombre, validacionesNIT, validacionesTelefono, validacionesDireccion;
    private boolean validacionesVendedor, validacionesTelVendedor;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesProveedor() {
    }

    @PostConstruct
    public void init() {
        validacionesDireccion = true;
        validacionesNIT = true;
        validacionesNombre = true;
        validacionesTelVendedor = true;
        validacionesTelefono = true;
        validacionesVendedor = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionProveedor() {
        validacionesDireccion = true;
        validacionesNIT = true;
        validacionesNombre = true;
        validacionesTelVendedor = true;
        validacionesTelefono = true;
        validacionesVendedor = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        proveedorDetalle = new Proveedor();
        recibirIDProveedoresDetalles(idProveedor);
        return "administrar_proveedores";
    }

    public void asignarValoresVariablesProveedor() {
        editarDireccion = proveedorDetalle.getDireccionproveedor();
        editarNIT = proveedorDetalle.getNitproveedor();
        editarNombre = proveedorDetalle.getNombreproveedor();
        editarTelVendedor = proveedorDetalle.getTelefonovendedor();
        editarTelefono = proveedorDetalle.getTelefonoproveedor();
        editarVendedor = proveedorDetalle.getVendedorproveedor();
    }

    public void recibirIDProveedoresDetalles(BigInteger idDetalle) {
        this.idProveedor = idDetalle;
        proveedorDetalle = gestionarRecursoProveedoresBO.obtenerProveedorPorIDProveedor(idDetalle);
        asignarValoresVariablesProveedor();
    }

    public void validarNombreProveedor() {
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(editarNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarNITProveedor() {
        if (Utilidades.validarNulo(editarNIT) && (!editarNIT.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(editarNIT)) {
                Proveedor registro = gestionarRecursoProveedoresBO.obtenerProveedorPorNIT(editarNIT);
                if (registro == null) {
                    validacionesNIT = true;
                } else {
                    if (!proveedorDetalle.getIdproveedor().equals(registro.getIdproveedor())) {
                        validacionesNIT = false;
                        FacesContext.getCurrentInstance().addMessage("form:editarNIT", new FacesMessage("El NIT ingresado ya esta registrado."));
                    } else {
                        validacionesNIT = true;
                    }
                }
            } else {
                validacionesNIT = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNIT", new FacesMessage("El NIT ingresado es incorrecto."));
            }
        } else {
            validacionesNIT = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNIT", new FacesMessage("El NIT es obligatorio."));
        }
    }

    public void validarTelefonoProveedor() {
        if (Utilidades.validarNulo(editarTelefono) && (!editarTelefono.isEmpty())) {
            if ((Utilidades.isNumber(editarTelefono)) == false) {
                validacionesTelefono = false;
                FacesContext.getCurrentInstance().addMessage("form:editarTelefono", new FacesMessage("El numero telefonico se encuentra incorrecto."));
            } else {
                validacionesTelefono = true;
            }
        } else {
            validacionesTelefono = false;
            FacesContext.getCurrentInstance().addMessage("form:editarTelefono", new FacesMessage("El numero telefonico es obligatorio."));
        }
    }

    public void validarDireccionProveedor() {
        if ((Utilidades.validarNulo(editarDireccion)) && (!editarDireccion.isEmpty())) {
            if (Utilidades.validarDirecciones(editarDireccion)) {
                validacionesDireccion = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                validacionesDireccion = false;
            }
        }
    }

    public void validarDatosOpcionales(int tipoReg) {
        if (tipoReg == 1) {
            if (Utilidades.validarNulo(editarVendedor) && (!editarVendedor.isEmpty())) {
                if (Utilidades.validarCaracterString(editarVendedor)) {
                    validacionesVendedor = true;
                } else {
                    validacionesVendedor = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarVendedor", new FacesMessage("El nombre se encuentra incorrecto."));
                }
            }
        } else {
            if (Utilidades.validarNulo(editarTelVendedor) && (!editarTelVendedor.isEmpty())) {
                if (Utilidades.isNumber(editarTelVendedor)) {
                    validacionesTelVendedor = true;
                } else {
                    validacionesTelVendedor = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarTelVendedor", new FacesMessage("El telefono se encuentra incorrecto."));
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
        if (validacionesVendedor == false) {
            retorno = false;
        }
        if (validacionesTelefono == false) {
            retorno = false;
        }
        if (validacionesTelVendedor == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionesProveedor() {
        if (validarResultadosValidacion() == true) {
            almacenarModificacionesProveedorEnSistema();
            recibirIDProveedoresDetalles(this.idProveedor);
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionesProveedorEnSistema() {
        try {
            proveedorDetalle.setNitproveedor(editarNIT);
            proveedorDetalle.setNombreproveedor(editarNombre);
            proveedorDetalle.setDireccionproveedor(editarDireccion);
            proveedorDetalle.setTelefonoproveedor(editarTelefono);
            if (Utilidades.validarNulo(editarVendedor)) {
                proveedorDetalle.setVendedorproveedor(editarVendedor);
            } else {
                proveedorDetalle.setVendedorproveedor("");
            }
            if (Utilidades.validarNulo(editarTelVendedor)) {
                proveedorDetalle.setTelefonovendedor(editarTelVendedor);
            } else {
                proveedorDetalle.setTelefonovendedor("");
            }
            gestionarRecursoProveedoresBO.modificarInformacionProveedor(proveedorDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesProveedor almacenarModificacionesProveedorEnSistema:  " + e.toString());
            System.out.println("Error ControllerDetallesProveedor almacenarModificacionesProveedorEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public Proveedor getProveedorDetalle() {
        return proveedorDetalle;
    }

    public void setProveedorDetalle(Proveedor proveedorDetalle) {
        this.proveedorDetalle = proveedorDetalle;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarNIT() {
        return editarNIT;
    }

    public void setEditarNIT(String editarNIT) {
        this.editarNIT = editarNIT;
    }

    public String getEditarTelefono() {
        return editarTelefono;
    }

    public void setEditarTelefono(String editarTelefono) {
        this.editarTelefono = editarTelefono;
    }

    public String getEditarDireccion() {
        return editarDireccion;
    }

    public void setEditarDireccion(String editarDireccion) {
        this.editarDireccion = editarDireccion;
    }

    public String getEditarVendedor() {
        return editarVendedor;
    }

    public void setEditarVendedor(String editarVendedor) {
        this.editarVendedor = editarVendedor;
    }

    public String getEditarTelVendedor() {
        return editarTelVendedor;
    }

    public void setEditarTelVendedor(String editarTelVendedor) {
        this.editarTelVendedor = editarTelVendedor;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
