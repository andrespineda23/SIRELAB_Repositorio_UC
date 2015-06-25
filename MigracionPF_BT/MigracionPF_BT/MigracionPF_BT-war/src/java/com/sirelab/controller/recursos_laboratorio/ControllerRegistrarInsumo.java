/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.GestionarRecursoInsumosBOInterface;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.Proveedor;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarInsumo implements Serializable {

    @EJB
    GestionarRecursoInsumosBOInterface gestionarRecursoInsumosBO;

    private List<Proveedor> listaProveedores;
    //
    private String nuevoNombre, nuevoCodigo, nuevoMarca, nuevoModelo;
    private String nuevoCantidadMin, nuevoCantidadExistencia;
    private String nuevoDescripcion;
    private Proveedor nuevoProveedor;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesMarca, validacionesModelo;
    private boolean validacionesCantidadMin, validacionesCantidadExistencia;
    private boolean validacionesDescripcion, validacionesProveedor;
    private String mensajeFormulario;

    public ControllerRegistrarInsumo() {
    }

    @PostConstruct
    public void init() {
        nuevoCantidadExistencia = "0";
        nuevoCantidadMin = "0";
        nuevoCodigo = null;
        nuevoDescripcion = null;
        nuevoMarca = null;
        nuevoModelo = null;
        nuevoNombre = null;
        nuevoProveedor = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        validacionesProveedor = false;
        validacionesCantidadExistencia = true;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        mensajeFormulario = "";
    }

    public void validarProveedorInsumo() {
        if (Utilidades.validarNulo(nuevoProveedor)) {
            validacionesProveedor = true;
        } else {
            validacionesProveedor = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoProveedor", new FacesMessage("El proveedor es obligatorio."));
        }
    }

    public void validarNombreInsumo() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoNombre)) {
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

    public void validarCodigoInsumo() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                validacionesCodigo = true;
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El Codigo ingresado es incorrecto."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El Codigo es obligatorio."));
        }
    }

    public void validarCantidadesInsumo(int tipo) {
        if (tipo == 1) {
            if (Utilidades.validarNulo(nuevoCantidadExistencia) && (!nuevoCantidadExistencia.isEmpty())) {
                if ((Utilidades.isNumber(nuevoCantidadExistencia)) == false) {
                    validacionesCantidadExistencia = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadExistencia", new FacesMessage("La cantidad ingresada se encuentra incorrecta."));
                } else {
                    validacionesCantidadExistencia = true;
                }
            }
        } else {
            if (Utilidades.validarNulo(nuevoCantidadMin) && (!nuevoCantidadMin.isEmpty())) {
                if ((Utilidades.isNumber(nuevoCantidadMin)) == false) {
                    validacionesCantidadMin = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadMin", new FacesMessage("La cantidad ingresada se encuentra incorrecta."));
                } else {
                    validacionesCantidadMin = true;
                }
            }
        }
    }

    public void validarMarcaModeloInsumo(int tipo) {
        if (tipo == 1) {
            if ((Utilidades.validarNulo(nuevoMarca)) && (!nuevoMarca.isEmpty())) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoMarca)) {
                    validacionesMarca = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoMarca", new FacesMessage("La marca se encuentra incorrecta."));
                    validacionesMarca = false;
                }
            }
        } else {

            if ((Utilidades.validarNulo(nuevoModelo)) && (!nuevoModelo.isEmpty())) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoModelo)) {
                    validacionesModelo = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoModelo", new FacesMessage("El modelo se encuentra incorrecto."));
                    validacionesModelo = false;
                }
            }
        }
    }

    public void validarDescripcionInsumo(int tipoReg) {
        if (Utilidades.validarNulo(nuevoDescripcion) && (!nuevoDescripcion.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(nuevoDescripcion)) {
                validacionesDescripcion = true;
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("La descripción se encuentra incorrecta."));
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
        Insumo registro = gestionarRecursoInsumosBO.obtenerInsumoPorIDCodigoYProveedor(nuevoCodigo, nuevoProveedor.getIdproveedor());
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoInsumo() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoInsumoEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado con el proveedor seleccionado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenarNuevoInsumoEnSistema() {
        try {
            Insumo nuevaInsumo = new Insumo();
            nuevaInsumo.setCodigoinsumo(nuevoCodigo);
            nuevaInsumo.setNombreinsumo(nuevoNombre);
            nuevaInsumo.setModeloinsumo(nuevoModelo);
            nuevaInsumo.setMarcainsumo(nuevoMarca);
            if (Utilidades.validarNulo(nuevoCantidadExistencia) && (!nuevoCantidadExistencia.isEmpty())) {
                nuevaInsumo.setCantidadexistencia(Integer.valueOf(nuevoCantidadExistencia));
            } else {
                nuevaInsumo.setCantidadexistencia(Integer.valueOf("0"));
            }
            if (Utilidades.validarNulo(nuevoCantidadMin) && (!nuevoCantidadMin.isEmpty())) {
                nuevaInsumo.setCantidadminimia(Integer.valueOf(nuevoCantidadMin));
            } else {
                nuevaInsumo.setCantidadminimia(Integer.valueOf("0"));
            }
            nuevaInsumo.setDescripcioninsumo(nuevoDescripcion);
            nuevaInsumo.setProveedor(nuevoProveedor);
            gestionarRecursoInsumosBO.crearNuevoInsumo(nuevaInsumo);
            limpiarFormulario();
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarInsumos almacenarNuevoInsumoEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        nuevoCantidadExistencia = "0";
        nuevoCantidadMin = "0";
        nuevoCodigo = null;
        nuevoDescripcion = null;
        nuevoMarca = null;
        nuevoModelo = null;
        nuevoNombre = null;
        nuevoProveedor = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        validacionesProveedor = false;
        validacionesCantidadExistencia = true;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
    }

    public void cancelarRegistroInsumo() {
        nuevoCantidadExistencia = null;
        nuevoCantidadMin = null;
        nuevoCodigo = null;
        nuevoDescripcion = null;
        nuevoMarca = null;
        nuevoModelo = null;
        nuevoNombre = null;
        nuevoProveedor = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        validacionesProveedor = false;
        validacionesCantidadExistencia = true;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        mensajeFormulario = "";
        listaProveedores = null;
    }

    //GET-SET
    public List<Proveedor> getListaProveedores() {
        if (listaProveedores == null) {
            listaProveedores = gestionarRecursoInsumosBO.consultarProveedoresRegistrados();
        }
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
    }

    public String getNuevoMarca() {
        return nuevoMarca;
    }

    public void setNuevoMarca(String nuevoMarca) {
        this.nuevoMarca = nuevoMarca;
    }

    public String getNuevoModelo() {
        return nuevoModelo;
    }

    public void setNuevoModelo(String nuevoModelo) {
        this.nuevoModelo = nuevoModelo;
    }

    public String getNuevoCantidadMin() {
        return nuevoCantidadMin;
    }

    public void setNuevoCantidadMin(String nuevoCantidadMin) {
        this.nuevoCantidadMin = nuevoCantidadMin;
    }

    public String getNuevoCantidadExistencia() {
        return nuevoCantidadExistencia;
    }

    public void setNuevoCantidadExistencia(String nuevoCantidadExistencia) {
        this.nuevoCantidadExistencia = nuevoCantidadExistencia;
    }

    public String getNuevoDescripcion() {
        return nuevoDescripcion;
    }

    public void setNuevoDescripcion(String nuevoDescripcion) {
        this.nuevoDescripcion = nuevoDescripcion;
    }

    public Proveedor getNuevoInsumo() {
        return nuevoProveedor;
    }

    public void setNuevoInsumo(Proveedor nuevoProveedor) {
        this.nuevoProveedor = nuevoProveedor;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public Proveedor getNuevoProveedor() {
        return nuevoProveedor;
    }

    public void setNuevoProveedor(Proveedor nuevoProveedor) {
        this.nuevoProveedor = nuevoProveedor;
    }

}
