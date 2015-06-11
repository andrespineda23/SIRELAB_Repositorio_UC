/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursos_laboratorio;

import com.sirelab.bo.interfacebo.GestionarRecursoAreasProfundizacionBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerDetallesAreaProfundizacion implements Serializable {
    
    @EJB
    GestionarRecursoAreasProfundizacionBOInterface gestionarRecursoAreaProfundizacionBO;
    
    private AreaProfundizacion areaProfundizacionDetalle;
    private BigInteger idAreaProfundizacion;
    private String editarNombre, editarCodigo;
    private boolean validacionesNombre, validacionesCodigo;
    private String mensajeFormulario;
    
    public ControllerDetallesAreaProfundizacion() {
    }
    
    @PostConstruct
    public void init() {
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "";
    }
    
    public void restaurarInformacionAreaProfundizacion() {
        validacionesCodigo = true;
        validacionesNombre = true;
        mensajeFormulario = "";
        areaProfundizacionDetalle = new AreaProfundizacion();
        recibirIDAreasProfundizacionDetalles(idAreaProfundizacion);
    }
    
    public void asignarValoresVariablesAreaProfundizacion() {
        editarCodigo = areaProfundizacionDetalle.getCodigoarea();
        editarNombre = areaProfundizacionDetalle.getNombrearea();
    }
    
    public void recibirIDAreasProfundizacionDetalles(BigInteger idArea) {
        this.idAreaProfundizacion = idArea;
        areaProfundizacionDetalle = gestionarRecursoAreaProfundizacionBO.obtenerAreaProfundizacionPorIDAreaProfundizacion(idAreaProfundizacion);
        asignarValoresVariablesAreaProfundizacion();
    }
    
    public void validarNombreAreaProfundizacion() {
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
    
    public void validarCodigoAreaProfundizacion() {
        if (Utilidades.validarNulo(editarCodigo) && (!editarCodigo.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigo)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigo", new FacesMessage("El codigo es obligatorio."));
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
        AreaProfundizacion registro = gestionarRecursoAreaProfundizacionBO.obtenerAreaProfundizacionPorCodigo(editarCodigo);
        if (null != registro) {
            if (!areaProfundizacionDetalle.getIdareaprofundizacion().equals(registro.getIdareaprofundizacion())) {
                retorno = false;
            }
        }
        return retorno;
    }
    
    public void modificarInformacionAreaProfundizacion() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarModificacionAreaProfundizacion();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
                recibirIDAreasProfundizacionDetalles(this.idAreaProfundizacion);
            } else {
                mensajeFormulario = "El codigo ingresado ya se encuentra registrado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }
    
    private void almacenarModificacionAreaProfundizacion() {
        try {
            areaProfundizacionDetalle.setNombrearea(editarNombre);
            areaProfundizacionDetalle.setCodigoarea(editarCodigo);
            gestionarRecursoAreaProfundizacionBO.modificarInformacionAreaProfundizacion(areaProfundizacionDetalle);
        } catch (Exception e) {
            System.out.println("Error ControllerDetallesAreaProfundizacion almacenarModificacionAreaProfundizacion : " + e.toString());
        }
    }

    //GET-SET
    public AreaProfundizacion getAreaProfundizacionDetalle() {
        return areaProfundizacionDetalle;
    }
    
    public void setAreaProfundizacionDetalle(AreaProfundizacion areaProfundizacionDetalle) {
        this.areaProfundizacionDetalle = areaProfundizacionDetalle;
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
    
    public String getMensajeFormulario() {
        return mensajeFormulario;
    }
    
    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }
    
}
