/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoInsumosBOInterface;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.Proveedor;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesInsumo implements Serializable {

    @EJB
    GestionarRecursoInsumosBOInterface gestionarRecursoInsumosBO;

    private List<Proveedor> listaProveedores;

    private Insumo insumoDetalles;
    private BigInteger idInsumo;
    //
    private String editarNombre, editarCodigo, editarMarca, editarModelo;
    private String editarCantidadMin, editarCantidadExistencia;
    private String editarDescripcion;
    private Proveedor editarProveedor;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesMarca, validacionesModelo;
    private boolean validacionesCantidadMin, validacionesCantidadExistencia;
    private boolean validacionesDescripcion, validacionesProveedor;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesInsumo() {
    }

    @PostConstruct
    public void init() {
        validacionesCodigo = true;
        validacionesNombre = true;
        validacionesProveedor = true;
        validacionesCantidadExistencia = true;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        BasicConfigurator.configure();
    }

    public String restaurarInformacionInsumo() {
        validacionesCodigo = true;
        validacionesNombre = true;
        validacionesProveedor = true;
        validacionesCantidadExistencia = true;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        insumoDetalles = new Insumo();
        recibirIDInsumosDetalles(idInsumo);
        return "administrar_insumos";
    }

    public void asignarValoresVariablesInsumo() {
        editarCantidadExistencia = insumoDetalles.getCantidadexistencia().toString();
        editarCantidadMin = insumoDetalles.getCantidadminimia().toString();
        editarCodigo = insumoDetalles.getCodigoinsumo();
        editarDescripcion = insumoDetalles.getDescripcioninsumo();
        editarMarca = insumoDetalles.getMarcainsumo();
        editarModelo = insumoDetalles.getModeloinsumo();
        editarNombre = insumoDetalles.getNombreinsumo();
        editarProveedor = insumoDetalles.getProveedor();
        listaProveedores = gestionarRecursoInsumosBO.consultarProveedoresRegistrados();
    }

    public void recibirIDInsumosDetalles(BigInteger idDetalle) {
        this.idInsumo = idDetalle;
        insumoDetalles = gestionarRecursoInsumosBO.obtenerInsumoPorIDInsumo(idInsumo);
        asignarValoresVariablesInsumo();
    }

    public void validarProveedorInsumo() {
        if (Utilidades.validarNulo(editarProveedor)) {
            validacionesProveedor = true;
        } else {
            validacionesProveedor = false;
            FacesContext.getCurrentInstance().addMessage("form:editarProveedor", new FacesMessage("El proveedor es obligatorio."));
        }
    }

    public void validarNombreInsumo() {
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

    public void validarCodigoInsumo() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                validacionesCodigo = true;
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El Codigo ingresado es incorrecto."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El Codigo es obligatorio."));
        }
    }

    public void validarCantidadesInsumo(int tipo) {
        if (tipo == 1) {
            if (Utilidades.validarNulo(editarCantidadExistencia) && (!editarCantidadExistencia.isEmpty())) {
                if ((Utilidades.isNumber(editarCantidadExistencia)) == false) {
                    validacionesCantidadExistencia = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCantidadExistencia", new FacesMessage("La cantidad ingresada se encuentra incorrecta."));
                } else {
                    validacionesCantidadExistencia = true;
                }
            }
        } else {
            if (Utilidades.validarNulo(editarCantidadMin) && (!editarCantidadMin.isEmpty())) {
                if ((Utilidades.isNumber(editarCantidadMin)) == false) {
                    validacionesCantidadMin = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCantidadMin", new FacesMessage("La cantidad ingresada se encuentra incorrecta."));
                } else {
                    validacionesCantidadMin = true;
                }
            }
        }
    }

    public void validarMarcaModeloInsumo(int tipo) {
        if (tipo == 1) {
            if ((Utilidades.validarNulo(editarMarca)) && (!editarMarca.isEmpty())) {
                if (Utilidades.validarCaracteresAlfaNumericos(editarMarca)) {
                    validacionesMarca = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:editarMarca", new FacesMessage("La marca se encuentra incorrecta."));
                    validacionesMarca = false;
                }
            }
        } else {

            if ((Utilidades.validarNulo(editarModelo)) && (!editarModelo.isEmpty())) {
                if (Utilidades.validarCaracteresAlfaNumericos(editarModelo)) {
                    validacionesModelo = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:editarModelo", new FacesMessage("El modelo se encuentra incorrecto."));
                    validacionesModelo = false;
                }
            }
        }
    }

    public void validarDescripcionInsumo(int tipoReg) {
        if (Utilidades.validarNulo(editarDescripcion) && (!editarDescripcion.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(editarDescripcion)) {
                validacionesDescripcion = true;
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDescripcion", new FacesMessage("La descripción se encuentra incorrecta."));
            }
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesCantidadExistencia == false) {
            retorno = false;
        }
        if (validacionesCantidadMin == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesMarca == false) {
            retorno = false;
        }
        if (validacionesModelo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesProveedor == false) {
            retorno = false;
        }
        return retorno;
    }

    public boolean validarCodigoRepetido() {
        boolean retorno = true;
        Insumo registro = gestionarRecursoInsumosBO.obtenerInsumoPorIDCodigoYProveedor(editarCodigo, editarProveedor.getIdproveedor());
        if (null != registro) {
            if (!insumoDetalles.getIdinsumo().equals(registro.getIdinsumo())) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void registrarModificacionInsumo() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarModificacionInsumoEnSistema();
                recibirIDInsumosDetalles(this.idInsumo);
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el proveedor seleccionado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarModificacionInsumoEnSistema() {
        try {
            insumoDetalles.setCodigoinsumo(editarCodigo);
            insumoDetalles.setNombreinsumo(editarNombre);
            insumoDetalles.setModeloinsumo(editarModelo);
            insumoDetalles.setMarcainsumo(editarMarca);
            if (Utilidades.validarNulo(editarCantidadExistencia) && (!editarCantidadExistencia.isEmpty())) {
                insumoDetalles.setCantidadexistencia(Integer.valueOf(editarCantidadExistencia));
            } else {
                insumoDetalles.setCantidadexistencia(Integer.valueOf("0"));
            }
            if (Utilidades.validarNulo(editarCantidadMin) && (!editarCantidadMin.isEmpty())) {
                insumoDetalles.setCantidadminimia(Integer.valueOf(editarCantidadMin));
            } else {
                insumoDetalles.setCantidadminimia(Integer.valueOf("0"));
            }
            insumoDetalles.setDescripcioninsumo(editarDescripcion);
            insumoDetalles.setProveedor(editarProveedor);
            gestionarRecursoInsumosBO.modificarInformacionInsumo(insumoDetalles);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesInsumo almacenarModificacionInsumoEnSistema:  " + e.toString());
            System.out.println("Error ControllerDetallesInsumo almacenarModificacionInsumoEnSistema : " + e.toString());
        }
    }

    //GET-SET
    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Insumo getInsumoDetalles() {
        return insumoDetalles;
    }

    public void setInsumoDetalles(Insumo insumoDetalles) {
        this.insumoDetalles = insumoDetalles;
    }

    public String getEditarNombre() {
        return editarNombre;
    }

    public void setEditarNombre(String editarNombre) {
        this.editarNombre = editarNombre;
    }

    public String getEditarCodigo() {
        return editarCodigo;
    }

    public void setEditarCodigo(String editarCodigo) {
        this.editarCodigo = editarCodigo;
    }

    public String getEditarMarca() {
        return editarMarca;
    }

    public void setEditarMarca(String editarMarca) {
        this.editarMarca = editarMarca;
    }

    public String getEditarModelo() {
        return editarModelo;
    }

    public void setEditarModelo(String editarModelo) {
        this.editarModelo = editarModelo;
    }

    public String getEditarCantidadMin() {
        return editarCantidadMin;
    }

    public void setEditarCantidadMin(String editarCantidadMin) {
        this.editarCantidadMin = editarCantidadMin;
    }

    public String getEditarCantidadExistencia() {
        return editarCantidadExistencia;
    }

    public void setEditarCantidadExistencia(String editarCantidadExistencia) {
        this.editarCantidadExistencia = editarCantidadExistencia;
    }

    public String getEditarDescripcion() {
        return editarDescripcion;
    }

    public void setEditarDescripcion(String editarDescripcion) {
        this.editarDescripcion = editarDescripcion;
    }

    public Proveedor getEditarProveedor() {
        return editarProveedor;
    }

    public void setEditarProveedor(Proveedor editarProveedor) {
        this.editarProveedor = editarProveedor;
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
