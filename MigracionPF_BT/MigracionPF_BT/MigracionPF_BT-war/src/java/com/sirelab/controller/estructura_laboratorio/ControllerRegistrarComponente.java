/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaComponentesEquiposBOInterface;
import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.TipoComponente;
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
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarComponente implements Serializable {

    @EJB
    GestionarPlantaComponentesEquiposBOInterface gestionarPlantaComponentesEquiposBO;

    private String nuevoNombreComponente, nuevoCodigoComponente, nuevoDescripcionComponente, nuevoMarcaComponente, nuevoModeloComponente, nuevoSerialComponente;
    private List<TipoComponente> listaTiposComponentes;
    private TipoComponente nuevoTipoComponente;
    private boolean validacionesNombre, validacionesCodigo, validacionesDescripcion, validacionesMarca, validacionesTipo;
    private boolean validacionesModelo, validacionesSerial;
    private String mensajeFormulario;
    private EquipoElemento equipoElementoRegistro;
     private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerRegistrarComponente() {
    }

    @PostConstruct
    public void init() {
        validacionesSerial = true;
        validacionesTipo = false;
        validacionesModelo = true;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesNombre = false;
        validacionesMarca = true;
        mensajeFormulario = "";
        //
        nuevoTipoComponente = null;
        nuevoNombreComponente = null;
        nuevoCodigoComponente = null;
        nuevoMarcaComponente = null;
        nuevoDescripcionComponente = null;
        nuevoSerialComponente = null;
        nuevoModeloComponente = null;
        BasicConfigurator.configure();
    }

    public void recibirIDEquipoElemento(BigInteger idRegistro) {
        equipoElementoRegistro = gestionarPlantaComponentesEquiposBO.consultarEquipoElementoPorID(idRegistro);
        listaTiposComponentes = gestionarPlantaComponentesEquiposBO.consultarTiposComponentesRegistrados();
    }

    public void validarNombreComponente() {
        if (Utilidades.validarNulo(nuevoNombreComponente) && (!nuevoNombreComponente.isEmpty())) {
            if (!Utilidades.validarCaracterString(nuevoNombreComponente)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombreComponente", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombreComponente", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarCodigoComponente() {
        if (Utilidades.validarNulo(nuevoCodigoComponente) && (!nuevoCodigoComponente.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigoComponente)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoComponente", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoComponente", new FacesMessage("El codigo es obligatorio."));
        }
    }

    public void validarMarcaComponente() {
        if (Utilidades.validarNulo(nuevoMarcaComponente) && (!nuevoMarcaComponente.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(nuevoMarcaComponente)) {
                validacionesMarca = true;
            } else {
                validacionesMarca = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoMarcaComponente", new FacesMessage("La marca se encuentra incorrecta."));
            }
        } else {
            validacionesMarca = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoMarcaComponente", new FacesMessage("La marca es obligatoria."));
        }
    }

    public void validarSerialComponente() {
        if (Utilidades.validarNulo(nuevoSerialComponente) && (!nuevoSerialComponente.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(nuevoSerialComponente)) {
                validacionesSerial = true;
            } else {
                validacionesSerial = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoSerialComponente", new FacesMessage("El serial se encuentra incorrecto."));
            }
        } else {
            validacionesSerial = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoSerialComponente", new FacesMessage("El serial es obligatorio."));
        }
    }

    public void validarModeloComponente() {
        if (Utilidades.validarNulo(nuevoModeloComponente) && (!nuevoModeloComponente.isEmpty())) {
            if ((Utilidades.validarCaracteresAlfaNumericos(nuevoModeloComponente)) == false) {
                validacionesModelo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoModeloComponente", new FacesMessage("La capacidad ingresada se encuentra incorrecta."));
            } else {
                validacionesModelo = true;
            }
        } else {
            validacionesModelo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoModeloComponente", new FacesMessage("La capacidad es obligatoria."));
        }
    }

    public void validarDescripcionComponente() {
        if (Utilidades.validarNulo(nuevoDescripcionComponente) && (!nuevoDescripcionComponente.isEmpty())) {
            if ((Utilidades.validarCaracteresAlfaNumericos(nuevoDescripcionComponente)) == false) {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionComponente", new FacesMessage("La descripción se encuentra incorrecta."));
            } else {
                validacionesDescripcion = true;
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionComponente", new FacesMessage("La descripción es obligatoria."));
        }
    }

    public void validarTipoComponente() {
        if (Utilidades.validarNulo(nuevoTipoComponente)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTipoComponente", new FacesMessage("El Tipo Componente es obligatorio."));
        }
    }

    public String limpiarRegistroComponenteLaboratorio() {
        nuevoNombreComponente = null;
        nuevoCodigoComponente = null;
        nuevoMarcaComponente = null;
        nuevoDescripcionComponente = null;
        nuevoSerialComponente = null;
        nuevoModeloComponente = null;
        nuevoTipoComponente = null;
        listaTiposComponentes = null;
        //
        validacionesSerial = true;
        validacionesModelo = true;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesTipo = false;
        validacionesNombre = false;
        validacionesMarca = true;
        mensajeFormulario = "";
        return "administrar_componentes";
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesTipo == false) {
            retorno = false;
        }
        if (validacionesSerial == false) {
            retorno = false;
        }
        if (validacionesModelo == false) {
            retorno = false;
        }
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesMarca == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        ComponenteEquipo componente = gestionarPlantaComponentesEquiposBO.consultarComponentePorCodigoYEquipo(nuevoCodigoComponente, equipoElementoRegistro.getIdequipoelemento());
        if (null != componente) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoComponente() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenaNuevoComponenteEnSistema();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
                limpiarFormulario();
            } else {
                mensajeFormulario = "El codigo ya esta registrado con el equipo asociado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenaNuevoComponenteEnSistema() {
        try {
            ComponenteEquipo componenteNuevo = new ComponenteEquipo();
            componenteNuevo.setNombrecomponente(nuevoNombreComponente);
            componenteNuevo.setCodigocomponete(nuevoCodigoComponente);
            componenteNuevo.setMarcacomponente(nuevoMarcaComponente);
            componenteNuevo.setDescripcioncomponente(nuevoDescripcionComponente);
            componenteNuevo.setSerialcomponente(nuevoSerialComponente);
            componenteNuevo.setEstadocomponente(true);
            componenteNuevo.setModelocomponente(nuevoModeloComponente);
            componenteNuevo.setEquipoelemento(equipoElementoRegistro);
            componenteNuevo.setTipocomponente(nuevoTipoComponente);
            gestionarPlantaComponentesEquiposBO.crearComponenteEquipo(componenteNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarComponente almacenaNuevoComponenteEnSistema:  "+e.toString());
            System.out.println("Error ControllerRegistrarComponente almacenaNuevoComponenteEnSistema : " + e.toString());
        }
    }

    private void limpiarFormulario() {
        nuevoNombreComponente = null;
        nuevoTipoComponente = null;
        nuevoCodigoComponente = null;
        nuevoMarcaComponente = null;
        nuevoDescripcionComponente = null;
        nuevoSerialComponente = null;
        nuevoModeloComponente = null;
        //
        validacionesSerial = true;
        validacionesModelo = true;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesTipo = false;
        validacionesNombre = false;
        validacionesMarca = true;
    }

    //GET-SET
    public String getNuevoNombreComponente() {
        return nuevoNombreComponente;
    }

    public void setNuevoNombreComponente(String nuevoNombreComponente) {
        this.nuevoNombreComponente = nuevoNombreComponente;
    }

    public String getNuevoCodigoComponente() {
        return nuevoCodigoComponente;
    }

    public void setNuevoCodigoComponente(String nuevoCodigoComponente) {
        this.nuevoCodigoComponente = nuevoCodigoComponente;
    }

    public String getNuevoDescripcionComponente() {
        return nuevoDescripcionComponente;
    }

    public void setNuevoDescripcionComponente(String nuevoDescripcionComponente) {
        this.nuevoDescripcionComponente = nuevoDescripcionComponente;
    }

    public String getNuevoMarcaComponente() {
        return nuevoMarcaComponente;
    }

    public void setNuevoMarcaComponente(String nuevoMarcaComponente) {
        this.nuevoMarcaComponente = nuevoMarcaComponente;
    }

    public String getNuevoModeloComponente() {
        return nuevoModeloComponente;
    }

    public void setNuevoModeloComponente(String nuevoModeloComponente) {
        this.nuevoModeloComponente = nuevoModeloComponente;
    }

    public String getNuevoSerialComponente() {
        return nuevoSerialComponente;
    }

    public void setNuevoSerialComponente(String nuevoSerialComponente) {
        this.nuevoSerialComponente = nuevoSerialComponente;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public EquipoElemento getEquipoElementoRegistro() {
        return equipoElementoRegistro;
    }

    public void setEquipoElementoRegistro(EquipoElemento equipoElementoRegistro) {
        this.equipoElementoRegistro = equipoElementoRegistro;
    }

    public List<TipoComponente> getListaTiposComponentes() {
        return listaTiposComponentes;
    }

    public void setListaTiposComponentes(List<TipoComponente> listaTiposComponentes) {
        this.listaTiposComponentes = listaTiposComponentes;
    }

    public TipoComponente getNuevoTipoComponente() {
        return nuevoTipoComponente;
    }

    public void setNuevoTipoComponente(TipoComponente nuevoTipoComponente) {
        this.nuevoTipoComponente = nuevoTipoComponente;
    }

}
