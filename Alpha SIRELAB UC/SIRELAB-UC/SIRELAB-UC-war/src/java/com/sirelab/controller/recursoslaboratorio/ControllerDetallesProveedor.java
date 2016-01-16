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
    private MensajesConstantes constantes;

    public ControllerDetallesProveedor() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
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
        return "administrarproveedores";
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
        if (Utilidades.validarNulo(editarNombre) && (!editarNombre.isEmpty()) && (editarNombre.trim().length() > 0)) {
            int tam = editarNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(editarNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre ingresado es incorrecto. "+constantes.RECURSO_NOM));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres. "+constantes.RECURSO_NOM));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombre", new FacesMessage("El nombre es obligatorio. "+constantes.RECURSO_NOM));
        }
    }

    public void validarNITProveedor() {
        if (Utilidades.validarNulo(editarNIT) && (!editarNIT.isEmpty()) && (editarNIT.trim().length() > 0)) {
            int tam = editarNIT.length();
            if (tam >= 8) {
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
                    FacesContext.getCurrentInstance().addMessage("form:editarNIT", new FacesMessage("El NIT ingresado es incorrecto. "+constantes.RECURSO_NIT));
                }
            } else {
                validacionesNIT = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNIT", new FacesMessage("El tamaño minimo permitido es 8 caracteres. "+constantes.RECURSO_NIT));
            }
        } else {
            validacionesNIT = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNIT", new FacesMessage("El NIT es obligatorio. "+constantes.RECURSO_NIT));
        }
    }

    public void validarTelefonoProveedor() {
        if (Utilidades.validarNulo(editarTelefono) && (!editarTelefono.isEmpty()) && (editarTelefono.trim().length() > 0)) {
            if ((Utilidades.validarCaracteresAlfaNumericos(editarTelefono)) == false) {
                validacionesTelefono = false;
                FacesContext.getCurrentInstance().addMessage("form:editarTelefono", new FacesMessage("El numero telefonico se encuentra incorrecto. "+constantes.ENTIDAD_TELEFONO));
            } else {
                validacionesTelefono = true;
            }
        } else {
            validacionesTelefono = false;
            FacesContext.getCurrentInstance().addMessage("form:editarTelefono", new FacesMessage("El numero telefonico es obligatorio. "+constantes.ENTIDAD_TELEFONO));
        }
    }

    public void validarDireccionProveedor() {
        if ((Utilidades.validarNulo(editarDireccion)) && (!editarDireccion.isEmpty()) && (editarDireccion.trim().length() > 0)) {
            int tam = editarDireccion.length();
            if (tam >= 8) {
                if (Utilidades.validarDirecciones(editarDireccion)) {
                    validacionesDireccion = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("La dirección se encuentra incorrecta. "+constantes.USUARIO_DIRECCION));
                    validacionesDireccion = false;
                }
            } else {
                validacionesDireccion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDireccion", new FacesMessage("El tamaño minimo permitido es 8 caracteres. "+constantes.USUARIO_DIRECCION));
            }
        }
    }

    public void validarDatosOpcionales(int tipoReg) {
        if (tipoReg == 1) {
            if (Utilidades.validarNulo(editarVendedor) && (!editarVendedor.isEmpty()) && (editarVendedor.trim().length() > 0)) {
                int tam = editarVendedor.length();
                if (tam >= 2) {
                    if (Utilidades.validarCaracterString(editarVendedor)) {
                        validacionesVendedor = true;
                    } else {
                        validacionesVendedor = false;
                        FacesContext.getCurrentInstance().addMessage("form:editarVendedor", new FacesMessage("El nombre se encuentra incorrecto. "+constantes.RECURSO_VENDEDOR));
                    }
                } else {
                    validacionesVendedor = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarVendedor", new FacesMessage("El tamaño minimo permitido es 2 caracteres. "+constantes.RECURSO_VENDEDOR));
                }
            }
        } else {
            if (Utilidades.validarNulo(editarTelVendedor) && (!editarTelVendedor.isEmpty()) && (editarTelVendedor.trim().length() > 0)) {
                int tam = editarTelVendedor.length();
                if (tam >= 7) {
                    if (Utilidades.validarCaracteresAlfaNumericos(editarTelVendedor)) {
                        validacionesTelVendedor = true;
                    } else {
                        validacionesTelVendedor = false;
                        FacesContext.getCurrentInstance().addMessage("form:editarTelVendedor", new FacesMessage("El telefono se encuentra incorrecto. "+constantes.ENTIDAD_TELEFONO));
                    }
                } else {
                    validacionesTelVendedor = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarTelVendedor", new FacesMessage("El tamaño minimo permitido es 7 caracteres. "+constantes.ENTIDAD_TELEFONO));
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
            logger.error("Error ControllerDetallesProveedor almacenarModificacionesProveedorEnSistema : " + e.toString());
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
