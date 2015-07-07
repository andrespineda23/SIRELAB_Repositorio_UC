/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoAreasProfundizacionBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
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
public class ControllerRegistrarAreaProfundizacion implements Serializable {

    @EJB
    GestionarRecursoAreasProfundizacionBOInterface gestionarRecursoAreaProfundizacionBO;

    private String nuevoNombre, nuevoCodigo;
    private boolean validacionesNombre, validacionesCodigo;
    private String mensajeFormulario;

    public ControllerRegistrarAreaProfundizacion() {
    }

    @PostConstruct
    public void init() {
        nuevoCodigo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        mensajeFormulario = "";
    }

    public void validarNombreAreaProfundizacion() {
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

    public void validarCodigoAreaProfundizacion() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El codigo es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }

        if (validacionesNombre == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        AreaProfundizacion registro = gestionarRecursoAreaProfundizacionBO.obtenerAreaProfundizacionPorCodigo(nuevoCodigo);
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoAreaProfundizacion() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoAreaProfundizacionEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoAreaProfundizacionEnSistema() {
        try {
            AreaProfundizacion areaNuevo = new AreaProfundizacion();
            areaNuevo.setNombrearea(nuevoNombre);
            areaNuevo.setCodigoarea(nuevoCodigo);
            gestionarRecursoAreaProfundizacionBO.crearNuevaAreaProfundizacion(areaNuevo);
            cancelarRegistroAreaProfundizacion();
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarAreaProfundizacion almacenarNuevoLaboratorioEnSistema : " + e.toString());
        }
    }

    public void cancelarRegistroAreaProfundizacion() {
        nuevoCodigo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        mensajeFormulario = "";
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

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

}
