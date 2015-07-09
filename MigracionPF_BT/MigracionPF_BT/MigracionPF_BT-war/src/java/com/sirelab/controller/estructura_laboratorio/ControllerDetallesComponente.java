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
public class ControllerDetallesComponente implements Serializable {

    @EJB
    GestionarPlantaComponentesEquiposBOInterface gestionarPlantaComponentesEquiposBO;

    private String editarNombreComponente, editarCodigoComponente, editarDescripcionComponente, editarMarcaComponente, editarModeloComponente, editarSerialComponente;
    private List<TipoComponente> listaTiposComponentes;
    private TipoComponente editarTipoComponente;
    private boolean validacionesNombre, validacionesCodigo, validacionesDescripcion, validacionesMarca, validacionesTipo;
    private boolean validacionesModelo, validacionesSerial;
    private String mensajeFormulario;
    private EquipoElemento editarEquipoElemento;
    private ComponenteEquipo componenteEquipoDetalle;
    private BigInteger idComponenteEquipo;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerDetallesComponente() {
    }

    @PostConstruct
    public void init() {
    }

    public void recibirIDComponenteEquipo(BigInteger idRegistro) {
        this.idComponenteEquipo = idRegistro;
        cargarInformacionRegistro();
        mensajeFormulario = "";
        BasicConfigurator.configure();
    }

    private void cargarInformacionRegistro() {
        componenteEquipoDetalle = gestionarPlantaComponentesEquiposBO.consultarComponenteEquipoPorID(idComponenteEquipo);
        if (null != componenteEquipoDetalle) {
            editarCodigoComponente = componenteEquipoDetalle.getCodigocomponete();
            editarDescripcionComponente = componenteEquipoDetalle.getDescripcioncomponente();
            editarMarcaComponente = componenteEquipoDetalle.getMarcacomponente();
            editarModeloComponente = componenteEquipoDetalle.getModelocomponente();
            editarNombreComponente = componenteEquipoDetalle.getNombrecomponente();
            editarSerialComponente = componenteEquipoDetalle.getSerialcomponente();
            editarEquipoElemento = componenteEquipoDetalle.getEquipoelemento();
            editarTipoComponente = componenteEquipoDetalle.getTipocomponente();
            listaTiposComponentes = gestionarPlantaComponentesEquiposBO.consultarTiposComponentesRegistrados();
            //
            validacionesSerial = true;
            validacionesModelo = true;
            validacionesCodigo = true;
            validacionesDescripcion = true;
            validacionesNombre = true;
            validacionesMarca = true;
            validacionesTipo = true;
        }
        modificacionesRegistro = false;
    }

    public void validarNombreComponente() {
        if (Utilidades.validarNulo(editarNombreComponente) && (!editarNombreComponente.isEmpty())) {
            if (!Utilidades.validarCaracterString(editarNombreComponente)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombreComponente", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombreComponente", new FacesMessage("El nombre es obligatorio."));
        }
        modificacionesRegistro = true;
    }

    public void validarCodigoComponente() {
        if (Utilidades.validarNulo(editarCodigoComponente) && (!editarCodigoComponente.isEmpty())) {
            if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigoComponente)) {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigoComponente", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesCodigo = true;
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigoComponente", new FacesMessage("El codigo es obligatorio."));
        }
        modificacionesRegistro = true;
    }

    public void validarMarcaComponente() {
        if (Utilidades.validarNulo(editarMarcaComponente) && (!editarMarcaComponente.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(editarMarcaComponente)) {
                validacionesMarca = true;
            } else {
                validacionesMarca = false;
                FacesContext.getCurrentInstance().addMessage("form:editarMarcaComponente", new FacesMessage("La marca se encuentra incorrecta."));
            }
        } else {
            validacionesMarca = false;
            FacesContext.getCurrentInstance().addMessage("form:editarMarcaComponente", new FacesMessage("La marca es obligatoria."));
        }
        modificacionesRegistro = true;
    }

    public void validarSerialComponente() {
        if (Utilidades.validarNulo(editarSerialComponente) && (!editarSerialComponente.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(editarSerialComponente)) {
                validacionesSerial = true;
            } else {
                validacionesSerial = false;
                FacesContext.getCurrentInstance().addMessage("form:editarSerialComponente", new FacesMessage("El serial se encuentra incorrecto."));
            }
        } else {
            validacionesSerial = false;
            FacesContext.getCurrentInstance().addMessage("form:editarSerialComponente", new FacesMessage("El serial es obligatorio."));
        }
        modificacionesRegistro = true;
    }

    public void validarModeloComponente() {
        if (Utilidades.validarNulo(editarModeloComponente) && (!editarModeloComponente.isEmpty())) {
            if ((Utilidades.validarCaracteresAlfaNumericos(editarModeloComponente)) == false) {
                validacionesModelo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarModeloComponente", new FacesMessage("La capacidad ingresada se encuentra incorrecta."));
            } else {
                validacionesModelo = true;
            }
        } else {
            validacionesModelo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarModeloComponente", new FacesMessage("La capacidad es obligatoria."));
        }
        modificacionesRegistro = true;
    }

    public void validarDescripcionComponente() {
        if (Utilidades.validarNulo(editarDescripcionComponente) && (!editarDescripcionComponente.isEmpty())) {
            if ((Utilidades.validarCaracteresAlfaNumericos(editarDescripcionComponente)) == false) {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDescripcionComponente", new FacesMessage("La descripción se encuentra incorrecta."));
            } else {
                validacionesDescripcion = true;
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDescripcionComponente", new FacesMessage("La descripción es obligatoria."));
        }
        modificacionesRegistro = true;
    }

    public void validarTipoComponente() {
        if (Utilidades.validarNulo(editarTipoComponente)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarTipoComponente", new FacesMessage("El Tipo Componente es obligatorio."));
        }
        modificacionesRegistro = true;
    }

    public void modificarEstadoComponente() {
        if (modificacionesRegistro == false) {
            modificacionesRegistro = true;
        }
    }

    public String limpiarRegistroComponenteLaboratorio() {
        editarNombreComponente = null;
        editarCodigoComponente = null;
        editarMarcaComponente = null;
        editarDescripcionComponente = null;
        editarSerialComponente = null;
        editarModeloComponente = null;
        editarEquipoElemento = null;
        editarTipoComponente = null;
        //
        validacionesSerial = true;
        validacionesModelo = true;
        validacionesTipo = false;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesNombre = false;
        validacionesMarca = true;
        mensajeFormulario = "";
        //
        listaTiposComponentes = null;
        modificacionesRegistro = false;
        idComponenteEquipo = null;
        componenteEquipoDetalle = null;
        return "administrar_componentes";
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesSerial == false) {
            retorno = false;
        }
        if (validacionesTipo == false) {
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
        ComponenteEquipo componente = gestionarPlantaComponentesEquiposBO.consultarComponentePorCodigoYEquipo(editarCodigoComponente, editarEquipoElemento.getIdequipoelemento());
        if (null != componente) {
            if (!componenteEquipoDetalle.getIdcomponenteequipo().equals(componente.getIdcomponenteequipo())) {
                retorno = false;
            }
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del editar docente
     */
    public void registrarModificarComponente() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenaModificacionComponente();
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
                cargarInformacionRegistro();
            } else {
                mensajeFormulario = "El codigo ya esta registrado con el equipo asociado.";
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenaModificacionComponente() {
        try {
            componenteEquipoDetalle.setNombrecomponente(editarNombreComponente);
            componenteEquipoDetalle.setCodigocomponete(editarCodigoComponente);
            componenteEquipoDetalle.setMarcacomponente(editarMarcaComponente);
            componenteEquipoDetalle.setDescripcioncomponente(editarDescripcionComponente);
            componenteEquipoDetalle.setSerialcomponente(editarSerialComponente);
            componenteEquipoDetalle.setModelocomponente(editarModeloComponente);
            componenteEquipoDetalle.setTipocomponente(editarTipoComponente);
            gestionarPlantaComponentesEquiposBO.editarComponenteEquipo(componenteEquipoDetalle);
        } catch (Exception e) {
            logger.error("Error ControllerDetallesComponente almacenaModificacionComponente:  "+e.toString());
            System.out.println("Error ControllerDetallesComponente almacenaModificacionComponente : " + e.toString());
        }
    }

    //GET-SET
    public String getEditarNombreComponente() {
        return editarNombreComponente;
    }

    public void setEditarNombreComponente(String editarNombreComponente) {
        this.editarNombreComponente = editarNombreComponente;
    }

    public String getEditarCodigoComponente() {
        return editarCodigoComponente;
    }

    public void setEditarCodigoComponente(String editarCodigoComponente) {
        this.editarCodigoComponente = editarCodigoComponente;
    }

    public String getEditarDescripcionComponente() {
        return editarDescripcionComponente;
    }

    public void setEditarDescripcionComponente(String editarDescripcionComponente) {
        this.editarDescripcionComponente = editarDescripcionComponente;
    }

    public String getEditarMarcaComponente() {
        return editarMarcaComponente;
    }

    public void setEditarMarcaComponente(String editarMarcaComponente) {
        this.editarMarcaComponente = editarMarcaComponente;
    }

    public String getEditarModeloComponente() {
        return editarModeloComponente;
    }

    public void setEditarModeloComponente(String editarModeloComponente) {
        this.editarModeloComponente = editarModeloComponente;
    }

    public String getEditarSerialComponente() {
        return editarSerialComponente;
    }

    public void setEditarSerialComponente(String editarSerialComponente) {
        this.editarSerialComponente = editarSerialComponente;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public EquipoElemento getEditarEquipoElemento() {
        return editarEquipoElemento;
    }

    public void setEditarEquipoElemento(EquipoElemento editarEquipoElemento) {
        this.editarEquipoElemento = editarEquipoElemento;
    }

    public ComponenteEquipo getComponenteEquipoDetalle() {
        return componenteEquipoDetalle;
    }

    public void setComponenteEquipoDetalle(ComponenteEquipo componenteEquipoDetalle) {
        this.componenteEquipoDetalle = componenteEquipoDetalle;
    }

    public BigInteger getIdComponenteEquipo() {
        return idComponenteEquipo;
    }

    public void setIdComponenteEquipo(BigInteger idComponenteEquipo) {
        this.idComponenteEquipo = idComponenteEquipo;
    }

    public List<TipoComponente> getListaTiposComponentes() {
        return listaTiposComponentes;
    }

    public void setListaTiposComponentes(List<TipoComponente> listaTiposComponentes) {
        this.listaTiposComponentes = listaTiposComponentes;
    }

    public TipoComponente getEditarTipoComponente() {
        return editarTipoComponente;
    }

    public void setEditarTipoComponente(TipoComponente editarTipoComponente) {
        this.editarTipoComponente = editarTipoComponente;
    }

}
