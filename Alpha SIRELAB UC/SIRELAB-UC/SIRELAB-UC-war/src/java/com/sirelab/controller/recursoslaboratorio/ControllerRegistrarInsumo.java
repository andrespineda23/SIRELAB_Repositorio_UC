/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoInsumosBOInterface;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarInsumo implements Serializable {

    @EJB
    GestionarRecursoInsumosBOInterface gestionarRecursoInsumosBO;

    //
    private String nuevoNombre, nuevoCodigo, nuevoMarca, nuevoModelo;
    private String nuevoCantidadMin, nuevoCantidadExistencia;
    private String nuevoDescripcion;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesMarca, validacionesModelo;
    private boolean validacionesCantidadMin, validacionesCantidadExistencia;
    private boolean validacionesDescripcion;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;

    public ControllerRegistrarInsumo() {
    }

    @PostConstruct
    public void init() {
        activarAceptar = false;
        nuevoCantidadExistencia = "0";
        nuevoCantidadMin = "0";
        nuevoCodigo = null;
        nuevoDescripcion = null;
        nuevoMarca = null;
        nuevoModelo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        validacionesCantidadExistencia = true;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        BasicConfigurator.configure();
    }

    public void validarNombreInsumo() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCodigoInsumo() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty()) && (nuevoCodigo.trim().length() > 0)) {
            int tam = nuevoCodigo.length();
            if (tam >= 4) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                    validacionesCodigo = true;
                } else {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El Codigo ingresado es incorrecto."));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El Codigo es obligatorio."));
        }
    }

    public void validarCantidadesInsumo(int tipo) {
        if (tipo == 1) {
            if (Utilidades.validarNulo(nuevoCantidadExistencia) && (!nuevoCantidadExistencia.isEmpty()) && (nuevoCantidadExistencia.trim().length() > 0)) {
                if ((Utilidades.isNumber(nuevoCantidadExistencia)) == false) {
                    validacionesCantidadExistencia = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadExistencia", new FacesMessage("La cantidad ingresada se encuentra incorrecta."));
                } else {
                    validacionesCantidadExistencia = true;
                }
            }
        } else {
            if (Utilidades.validarNulo(nuevoCantidadMin) && (!nuevoCantidadMin.isEmpty()) && (nuevoCantidadMin.trim().length() > 0)) {
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
            if ((Utilidades.validarNulo(nuevoMarca)) && (!nuevoMarca.isEmpty()) && (nuevoMarca.trim().length() > 0)) {
                int tam = nuevoMarca.length();
                if (tam >= 2) {
                    if (Utilidades.validarCaracteresAlfaNumericos(nuevoMarca)) {
                        validacionesMarca = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage("form:nuevoMarca", new FacesMessage("La marca se encuentra incorrecta."));
                        validacionesMarca = false;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoMarca", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
                    validacionesMarca = false;
                }
            }
        } else {
            if ((Utilidades.validarNulo(nuevoModelo)) && (!nuevoModelo.isEmpty()) && (nuevoModelo.trim().length() > 0)) {
                int tam = nuevoModelo.length();
                if (tam >= 2) {
                    if (Utilidades.validarCaracteresAlfaNumericos(nuevoModelo)) {
                        validacionesModelo = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage("form:nuevoModelo", new FacesMessage("El modelo se encuentra incorrecto."));
                        validacionesModelo = false;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoModelo", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
                    validacionesModelo = false;
                }
            }
        }
    }

    public void validarDescripcionInsumo(int tipoReg) {
        if (Utilidades.validarNulo(nuevoDescripcion) && (!nuevoDescripcion.isEmpty()) && (nuevoDescripcion.trim().length() > 0)) {
            int tam = nuevoDescripcion.length();
            if (tam >= 20) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoDescripcion)) {
                    validacionesDescripcion = true;
                } else {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("La descripción se encuentra incorrecta."));
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("El tamaño minimo permitido es 20 caracteres."));
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

        return retorno;
    }

    public boolean validarCodigoRepetido() {
        boolean retorno = true;
        Insumo registro = gestionarRecursoInsumosBO.obtenerInsumoPorCodigo(nuevoCodigo);
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
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
            }
        } else {
            colorMensaje = "red";
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
            if (Utilidades.validarNulo(nuevoCantidadExistencia) && (!nuevoCantidadExistencia.isEmpty()) && (nuevoCantidadExistencia.trim().length() > 0)) {
                nuevaInsumo.setCantidadexistencia(Integer.valueOf(nuevoCantidadExistencia));
            } else {
                nuevaInsumo.setCantidadexistencia(Integer.valueOf("0"));
            }
            if (Utilidades.validarNulo(nuevoCantidadMin) && (!nuevoCantidadMin.isEmpty()) && (nuevoCantidadMin.trim().length() > 0)) {
                nuevaInsumo.setCantidadminimia(Integer.valueOf(nuevoCantidadMin));
            } else {
                nuevaInsumo.setCantidadminimia(Integer.valueOf("0"));
            }
            nuevaInsumo.setDescripcioninsumo(nuevoDescripcion);
            gestionarRecursoInsumosBO.crearNuevoInsumo(nuevaInsumo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarInsumo almacenarNuevoInsumoEnSistema:  " + e.toString());
            System.out.println("Error ControllerRegistrarInsumo almacenarNuevoInsumoEnSistema : " + e.toString());
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
        validacionesCodigo = false;
        validacionesNombre = false;
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
        validacionesCodigo = false;
        activarAceptar = false;
        validacionesNombre = false;
        validacionesCantidadExistencia = true;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
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
