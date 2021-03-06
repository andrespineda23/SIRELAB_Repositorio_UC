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
public class ControllerRegistrarComponente implements Serializable {

    @EJB
    GestionarPlantaComponentesEquiposBOInterface gestionarPlantaComponentesEquiposBO;

    private String nuevoNombreComponente, nuevoCodigoComponente, nuevoDescripcionComponente, nuevoMarcaComponente, nuevoModeloComponente, nuevoSerialComponente, nuevoCostoComponente;
    private List<TipoComponente> listaTiposComponentes;
    private TipoComponente nuevoTipoComponente;
    private boolean validacionesNombre, validacionesCodigo, validacionesDescripcion, validacionesMarca, validacionesTipo;
    private boolean validacionesModelo, validacionesSerial, validacionesCosto;
    private String mensajeFormulario;
    private EquipoElemento equipoElementoRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarComponente() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        mensajeError = "";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesSerial = true;
        validacionesTipo = false;
        validacionesModelo = true;
        validacionesModelo = true;
        validacionesCodigo = false;
        validacionesCosto = true;
        validacionesDescripcion = false;
        validacionesNombre = false;
        validacionesMarca = true;
        //
        nuevoCostoComponente = "0";
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
        if (Utilidades.validarNulo(nuevoNombreComponente) && (!nuevoNombreComponente.isEmpty()) && (nuevoNombreComponente.trim().length() > 0)) {
            int tam = nuevoNombreComponente.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoNombreComponente)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombreComponente", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombreComponente", new FacesMessage("El tamaño minimo permitido es 4 caractereso. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombreComponente", new FacesMessage("El nombre es obligatorio. " + constantes.INVENTARIO_NOMBRE));
        }

    }

    public void validarCodigoComponente() {
        if (Utilidades.validarNulo(nuevoCodigoComponente) && (!nuevoCodigoComponente.isEmpty()) && (nuevoCodigoComponente.trim().length() > 0)) {
            int tam = nuevoCodigoComponente.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigoComponente)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoComponente", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.INVENTARIO_CODIGO));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoComponente", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoComponente", new FacesMessage("El codigo es obligatorio. " + constantes.INVENTARIO_CODIGO));
        }
    }

    public void validarMarcaComponente() {
        if (Utilidades.validarNulo(nuevoMarcaComponente) && (!nuevoMarcaComponente.isEmpty()) && (nuevoMarcaComponente.trim().length() > 0)) {
            int tam = nuevoMarcaComponente.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoMarcaComponente)) {
                    validacionesMarca = true;
                } else {
                    validacionesMarca = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoMarcaComponente", new FacesMessage("La marca se encuentra incorrecta. " + constantes.INVENTARIO_MARCA));
                }
            } else {
                validacionesMarca = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoMarcaComponente", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_MARCA));
            }
        } else {
            validacionesMarca = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoMarcaComponente", new FacesMessage("La marca es obligatoria. " + constantes.INVENTARIO_MARCA));
        }
    }

    public void validarCostoComponente() {
        if (Utilidades.validarNulo(nuevoCostoComponente) && (!nuevoCostoComponente.isEmpty()) && (nuevoCostoComponente.trim().length() > 0)) {
            if (nuevoCostoComponente.length() <= 10) {
                if (Utilidades.isNumber(nuevoCostoComponente)) {
                    validacionesCosto = true;
                } else {
                    validacionesCosto = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCostoComponente", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
                }
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:editarCostoComponente", new FacesMessage("El tamaño maximo permitido es 10 caracteres. " + constantes.INVENTARIO_COST_ALQ));
            }

        }
    }

    public void validarSerialComponente() {
        if (Utilidades.validarNulo(nuevoSerialComponente) && (!nuevoSerialComponente.isEmpty()) && (nuevoSerialComponente.trim().length() > 0)) {
            int tam = nuevoSerialComponente.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoSerialComponente)) {
                    validacionesSerial = true;
                } else {
                    validacionesSerial = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoSerialComponente", new FacesMessage("El serial se encuentra incorrecto. " + constantes.INVENTARIO_SERIAL));
                }
            } else {
                validacionesSerial = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoSerialComponente", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_SERIAL));
            }
        } else {
            validacionesSerial = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoSerialComponente", new FacesMessage("El serial es obligatorio. " + constantes.INVENTARIO_SERIAL));
        }
    }

    public void validarModeloComponente() {
        if (Utilidades.validarNulo(nuevoModeloComponente) && (!nuevoModeloComponente.isEmpty()) && (nuevoModeloComponente.trim().length() > 0)) {
            int tam = nuevoModeloComponente.length();
            if (tam >= 2) {
                if ((Utilidades.validarCaracteresAlfaNumericos(nuevoModeloComponente)) == false) {
                    validacionesModelo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoModeloComponente", new FacesMessage("El modelo ingresado se encuentra incorrecto. " + constantes.INVENTARIO_MODELO));
                } else {
                    validacionesModelo = true;
                }
            } else {
                validacionesModelo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoModeloComponente", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_MODELO));
            }
        } else {
            validacionesModelo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoModeloComponente", new FacesMessage("El modelo es obligatorio. " + constantes.INVENTARIO_MODELO));
        }
    }

    public void validarDescripcionComponente() {
        if (Utilidades.validarNulo(nuevoDescripcionComponente) && (!nuevoDescripcionComponente.isEmpty()) && (nuevoDescripcionComponente.trim().length() > 0)) {
            int tam = nuevoDescripcionComponente.length();
            if (tam >= 20) {
                if ((Utilidades.validarCaracteresAlfaNumericos(nuevoDescripcionComponente)) == false) {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionComponente", new FacesMessage("La descripción se encuentra incorrecta. " + constantes.INVENTARIO_DESCRIP));
                } else {
                    validacionesDescripcion = true;
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionComponente", new FacesMessage("El tamaño minimo permitido es 20 caracteres. " + constantes.INVENTARIO_DESCRIP));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionComponente", new FacesMessage("La descripción es obligatoria. " + constantes.INVENTARIO_DESCRIP));
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
        mensajeError ="";
        nuevoCostoComponente = "0";
        nuevoSerialComponente = null;
        nuevoModeloComponente = null;
        nuevoTipoComponente = null;
        listaTiposComponentes = null;
        //
        validacionesCosto = true;
        validacionesSerial = true;
        validacionesModelo = true;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesTipo = false;
        validacionesNombre = false;
        validacionesMarca = true;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        return "administrarcomponentes";
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesTipo == false) {
            mensajeError = mensajeError + " - Tipo Componente - ";
            retorno = false;
        }
        if (validacionesSerial == false) {
            mensajeError = mensajeError + " - Serie - ";
            retorno = false;
        }
        if (validacionesModelo == false) {
            mensajeError = mensajeError + " - Modelo - ";
            retorno = false;
        }
        if (validacionesCosto == false) {
            mensajeError = mensajeError + " - Costo - ";
            retorno = false;
        }
        if (validacionesCodigo == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            mensajeError = mensajeError + " - Descripción - ";
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
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El codigo ya esta registrado en el sistema.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: "+mensajeError;
        }
    }

    public void almacenaNuevoComponenteEnSistema() {
        try {
            ComponenteEquipo componenteNuevo = new ComponenteEquipo();
            componenteNuevo.setNombrecomponente(nuevoNombreComponente);
            componenteNuevo.setCodigocomponete(nuevoCodigoComponente);
            componenteNuevo.setCostocomponente(nuevoCostoComponente);
            componenteNuevo.setMarcacomponente(nuevoMarcaComponente);
            componenteNuevo.setDescripcioncomponente(nuevoDescripcionComponente);
            componenteNuevo.setSerialcomponente(nuevoSerialComponente);
            componenteNuevo.setEstadocomponente(true);
            componenteNuevo.setModelocomponente(nuevoModeloComponente);
            componenteNuevo.setEquipoelemento(equipoElementoRegistro);
            componenteNuevo.setTipocomponente(nuevoTipoComponente);
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            gestionarPlantaComponentesEquiposBO.crearComponenteEquipo(componenteNuevo, usuarioLoginSistema.getUserUsuario());
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarComponente almacenaNuevoComponenteEnSistema : " + e.toString(), e);
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
        nuevoCostoComponente = "0";
        //
        validacionesSerial = true;
        validacionesModelo = true;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesTipo = false;
        validacionesNombre = false;
        validacionesMarca = true;
        mensajeError = "";
        validacionesCosto = true;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        activarAceptar = false;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
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

    public String getNuevoCostoComponente() {
        return nuevoCostoComponente;
    }

    public void setNuevoCostoComponente(String nuevoCostoComponente) {
        this.nuevoCostoComponente = nuevoCostoComponente;
    }

}
