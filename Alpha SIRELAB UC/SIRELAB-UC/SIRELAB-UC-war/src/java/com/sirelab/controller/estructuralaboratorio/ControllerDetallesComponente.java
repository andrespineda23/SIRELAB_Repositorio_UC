/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaComponentesEquiposBOInterface;
import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.TipoComponente;
import com.sirelab.utilidades.UsuarioLogin;
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

    private String editarNombreComponente, editarCodigoComponente, editarDescripcionComponente, editarMarcaComponente, editarModeloComponente, editarSerialComponente, editarCostoComponente;
    private List<TipoComponente> listaTiposComponentes;
    private TipoComponente editarTipoComponente;
    private boolean validacionesNombre, validacionesCodigo, validacionesDescripcion, validacionesMarca, validacionesTipo;
    private boolean validacionesModelo, validacionesSerial, validacionesCosto;
    private String mensajeFormulario;
    private EquipoElemento editarEquipoElemento;
    private ComponenteEquipo componenteEquipoDetalle;
    private BigInteger idComponenteEquipo;
    private boolean modificacionesRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerDetallesComponente() {
    }

    @PostConstruct
    public void init() {
    }

    public void recibirIDComponenteEquipo(BigInteger idRegistro) {
        constantes = new MensajesConstantes();
        this.idComponenteEquipo = idRegistro;
        cargarInformacionRegistro();
        colorMensaje = "black";
        mensajeFormulario = "N/A";
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
            editarCostoComponente = componenteEquipoDetalle.getCostocomponente();
            editarEquipoElemento = componenteEquipoDetalle.getEquipoelemento();
            editarTipoComponente = componenteEquipoDetalle.getTipocomponente();
            listaTiposComponentes = gestionarPlantaComponentesEquiposBO.consultarTiposComponentesRegistrados();
            //
            validacionesSerial = true;
            validacionesModelo = true;
            validacionesCodigo = true;
            validacionesDescripcion = true;
            validacionesNombre = true;
            validacionesCosto = true;
            validacionesMarca = true;
            validacionesTipo = true;
        }
        mensajeError = "";
        modificacionesRegistro = false;
    }

    public void validarNombreComponente() {
        if (Utilidades.validarNulo(editarNombreComponente) && (!editarNombreComponente.isEmpty()) && (editarNombreComponente.trim().length() > 0)) {
            int tam = editarNombreComponente.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarNombreComponente)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarNombreComponente", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:editarNombreComponente", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:editarNombreComponente", new FacesMessage("El nombre es obligatorio. " + constantes.INVENTARIO_NOMBRE));
        }
        modificacionesRegistro = true;
    }

    public void validarCodigoComponente() {
        if (Utilidades.validarNulo(editarCodigoComponente) && (!editarCodigoComponente.isEmpty()) && (editarCodigoComponente.trim().length() > 0)) {
            int tam = editarCodigoComponente.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(editarCodigoComponente)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCodigoComponente", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.INVENTARIO_CODIGO));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCodigoComponente", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarCodigoComponente", new FacesMessage("El codigo es obligatorio. " + constantes.INVENTARIO_CODIGO));
        }
        modificacionesRegistro = true;
    }

    public void validarMarcaComponente() {
        if (Utilidades.validarNulo(editarMarcaComponente) && (!editarMarcaComponente.isEmpty()) && (editarMarcaComponente.trim().length() > 0)) {
            int tam = editarCodigoComponente.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(editarMarcaComponente)) {
                    validacionesMarca = true;
                } else {
                    validacionesMarca = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarMarcaComponente", new FacesMessage("La marca se encuentra incorrecta. " + constantes.INVENTARIO_MARCA));
                }
            } else {
                validacionesMarca = false;
                FacesContext.getCurrentInstance().addMessage("form:editarMarcaComponente", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_MARCA));
            }
        } else {
            validacionesMarca = false;
            FacesContext.getCurrentInstance().addMessage("form:editarMarcaComponente", new FacesMessage("La marca es obligatoria. " + constantes.INVENTARIO_MARCA));
        }
        modificacionesRegistro = true;
    }

    public void validarCostoComponente() {
        if (Utilidades.validarNulo(editarCostoComponente) && (!editarCostoComponente.isEmpty()) && (editarCostoComponente.trim().length() > 0)) {
            if (editarCostoComponente.length() <= 10) {
                if (Utilidades.isNumber(editarCostoComponente)) {
                    validacionesCosto = true;
                } else {
                    validacionesCosto = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarCostoComponente", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
                }
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCostoComponente", new FacesMessage("El tamaño maximo permitido es 10 caracteres. " + constantes.INVENTARIO_COST_ALQ));
            }
        }
        modificacionesRegistro = true;
    }

    public void validarSerialComponente() {
        if (Utilidades.validarNulo(editarSerialComponente) && (!editarSerialComponente.isEmpty()) && (editarSerialComponente.trim().length() > 0)) {
            int tam = editarSerialComponente.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(editarSerialComponente)) {
                    validacionesSerial = true;
                } else {
                    validacionesSerial = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarSerialComponente", new FacesMessage("El serial se encuentra incorrecto. " + constantes.INVENTARIO_SERIAL));
                }
            } else {
                validacionesSerial = false;
                FacesContext.getCurrentInstance().addMessage("form:editarSerialComponente", new FacesMessage("El tamaño minimo permitido es 2 caracteres.  " + constantes.INVENTARIO_SERIAL));
            }
        } else {
            validacionesSerial = false;
            FacesContext.getCurrentInstance().addMessage("form:editarSerialComponente", new FacesMessage("El serial es obligatorio. " + constantes.INVENTARIO_SERIAL));
        }
        modificacionesRegistro = true;
    }

    public void validarModeloComponente() {
        if (Utilidades.validarNulo(editarModeloComponente) && (!editarModeloComponente.isEmpty()) && (editarModeloComponente.trim().length() > 0)) {
            int tam = editarModeloComponente.length();
            if (tam >= 2) {
                if ((Utilidades.validarCaracteresAlfaNumericos(editarModeloComponente)) == false) {
                    validacionesModelo = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarModeloComponente", new FacesMessage("El modelo ingresado se encuentra incorrecto. " + constantes.INVENTARIO_MODELO));
                } else {
                    validacionesModelo = true;
                }
            } else {
                validacionesModelo = false;
                FacesContext.getCurrentInstance().addMessage("form:editarModeloComponente", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_MODELO));
            }
        } else {
            validacionesModelo = false;
            FacesContext.getCurrentInstance().addMessage("form:editarModeloComponente", new FacesMessage("El modelo es obligatorio. " + constantes.INVENTARIO_MODELO));
        }
        modificacionesRegistro = true;
    }

    public void validarDescripcionComponente() {
        if (Utilidades.validarNulo(editarDescripcionComponente) && (!editarDescripcionComponente.isEmpty()) && (editarDescripcionComponente.trim().length() > 0)) {
            int tam = editarDescripcionComponente.length();
            if (tam >= 20) {
                if ((Utilidades.validarCaracteresAlfaNumericos(editarDescripcionComponente)) == false) {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:editarDescripcionComponente", new FacesMessage("La descripción se encuentra incorrecta. " + constantes.INVENTARIO_DESCRIP));
                } else {
                    validacionesDescripcion = true;
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:editarDescripcionComponente", new FacesMessage("El tamaño minimo permitido es 20 caracteres. " + constantes.INVENTARIO_DESCRIP));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:editarDescripcionComponente", new FacesMessage("La descripción es obligatoria. " + constantes.INVENTARIO_DESCRIP));
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
        mensajeError = "";
        editarCostoComponente = "0";
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
        validacionesCosto = true;
        validacionesNombre = false;
        validacionesMarca = true;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        //
        listaTiposComponentes = null;
        modificacionesRegistro = false;
        idComponenteEquipo = null;
        componenteEquipoDetalle = null;
        return "administrarcomponentes";
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesSerial == false) {
            mensajeError = mensajeError + " - Serial - ";
            retorno = false;
        }
        if (validacionesTipo == false) {
            mensajeError = mensajeError + " - Tipo Componente - ";
            retorno = false;
        }
        if (validacionesModelo == false) {
            mensajeError = mensajeError + " - Modelo - ";
            retorno = false;
        }
        if (validacionesCodigo == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            mensajeError = mensajeError + " - Descripcion - ";
            retorno = false;
        }
        if (validacionesCosto == false) {
            mensajeError = mensajeError + " - Costo - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        if (validacionesMarca == false) {
            mensajeError = mensajeError + " - Marca - ";
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
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
                cargarInformacionRegistro();
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El codigo ya esta registrado en el sistema.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: "+mensajeError;
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
            componenteEquipoDetalle.setCostocomponente(editarCostoComponente);
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            gestionarPlantaComponentesEquiposBO.editarComponenteEquipo(componenteEquipoDetalle, usuarioLoginSistema.getUserUsuario());
        } catch (Exception e) {
            logger.error("Error ControllerDetallesComponente almacenaModificacionComponente:  " + e.toString(), e);
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

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public String getEditarCostoComponente() {
        return editarCostoComponente;
    }

    public void setEditarCostoComponente(String editarCostoComponente) {
        this.editarCostoComponente = editarCostoComponente;
    }

}
