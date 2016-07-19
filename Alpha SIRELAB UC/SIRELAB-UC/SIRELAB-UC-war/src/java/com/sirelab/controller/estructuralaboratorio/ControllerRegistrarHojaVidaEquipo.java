/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaHojasVidaEquiposBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.HojaVidaEquipo;
import com.sirelab.entidades.TipoEvento;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ControllerRegistrarHojaVidaEquipo implements Serializable {

    @EJB
    GestionarPlantaHojasVidaEquiposBOInterface gestionarPlantaHojasVidaEquiposBO;

    private String inputDetalle;
    private String inputFechaEvento, inputFechaFinEvento, inputFechaRegistro;
    private String inputCosto;
    private TipoEvento inputTipoEvento;
    private boolean validacionesFechaRegistro, validacionesFechaEvento, validacionesFechaFinEvento;
    private List<TipoEvento> listaTiposEventos;
    private boolean validacionesDetalle, validacionesCosto, validacionesTipo;
    private String mensajeFormulario;
    private BigInteger idEquipo;
    private EquipoElemento equipoElemento;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private boolean fechaDiferidaEvento, fechaDiferidaRegistro;
    private MensajesConstantes constantes;

    public ControllerRegistrarHojaVidaEquipo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        inputDetalle = null;
        inputCosto = "0";
        inputTipoEvento = null;
        validacionesDetalle = false;
        validacionesCosto = true;
        listaTiposEventos = null;
        inputFechaEvento = null;
        inputFechaFinEvento = null;
        inputFechaRegistro = null;
        validacionesTipo = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        validacionesFechaFinEvento = false;
        BasicConfigurator.configure();
        fechaDiferidaEvento = true;
        fechaDiferidaRegistro = true;
    }

    public void recibirIDEquipo(BigInteger idRegistro) {
        this.idEquipo = idRegistro;
        equipoElemento = gestionarPlantaHojasVidaEquiposBO.consultarEquipoElementoPorID(idEquipo);
    }

    public void validarDetalle() {
        if (Utilidades.validarNulo(inputDetalle) && (!inputDetalle.isEmpty()) && (inputDetalle.trim().length() > 0)) {
            int tam = inputDetalle.length();
            if (tam >= 4) {
                if (Utilidades.validarCaracterString(inputDetalle)) {
                    validacionesDetalle = true;
                } else {
                    validacionesDetalle = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.INVENTARIO_NOMBRE));
                }
            } else {
                validacionesDetalle = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El tamaño minimo es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesDetalle = false;
            FacesContext.getCurrentInstance().addMessage("form:inputDetalle", new FacesMessage("El nombre se encuentra incorrecto. " + constantes.INVENTARIO_NOMBRE));
        }
    }

    public void validarFechaEvento() {
        if (Utilidades.validarNulo(inputFechaEvento)) {
            try {
                Date fecha = new Date(inputFechaEvento);
                validacionesFechaEvento = true;
            } catch (Exception e) {
                validacionesFechaEvento = true;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaEvento", new FacesMessage("La fecha ingresada fue incorrecta. El sistema asigno la fecha del día."));
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                inputFechaEvento = formateador.format(new Date());
            }
        } else {
            validacionesFechaEvento = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaEvento", new FacesMessage("La fecha del evento no puede ser vacia."));
        }
    }

    public void validarFechaFinEvento() {
        if (Utilidades.validarNulo(inputFechaFinEvento)) {
            try {
                Date fecha = new Date(inputFechaFinEvento);
                validacionesFechaFinEvento = true;
            } catch (Exception e) {
                validacionesFechaFinEvento = true;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaFinEvento", new FacesMessage("La fecha ingresada fue incorrecta. El sistema asigno la fecha del día."));
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                inputFechaFinEvento = formateador.format(new Date());
            }
        } else {
            validacionesFechaFinEvento = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaFinEvento", new FacesMessage("La fecha del evento no puede ser vacia."));
        }
    }

    public void validarFechaRegistro() {
        if (Utilidades.validarNulo(inputFechaRegistro)) {
            try {
                Date fecha = new Date(inputFechaRegistro);
                validacionesFechaRegistro = true;
            } catch (Exception e) {
                validacionesFechaRegistro = true;
                FacesContext.getCurrentInstance().addMessage("form:inputFechaRegistro", new FacesMessage("La fecha ingresada fue incorrecta. El sistema asigno la fecha del día."));
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                inputFechaRegistro = formateador.format(new Date());
            }
        } else {
            validacionesFechaRegistro = false;
            FacesContext.getCurrentInstance().addMessage("form:inputFechaRegistro", new FacesMessage("La fecha del registro no puede ser vacia."));
        }
    }

    public void validarCosto() {
        if (Utilidades.validarNulo(inputCosto) && (!inputCosto.isEmpty()) && (inputCosto.trim().length() > 0)) {
            if (Utilidades.isNumber(inputCosto)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:inputCosto", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
            }
        }
    }

    public void validarTipoEvento() {
        if (Utilidades.validarNulo(inputTipoEvento)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputTipoEvento", new FacesMessage("El campo Tipo Evento es obligatorio."));
        }
    }

    private boolean validarValidacionesRegistro() {
        boolean retorno = true;
        if (validacionesDetalle == false) {
            retorno = false;
        }
        if (validacionesTipo == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarHojaVidaEquipo() {
        if (validarValidacionesRegistro() == true) {
            Date date1 = new Date(inputFechaEvento);
            Date date2 = new Date(inputFechaFinEvento);
            int comparacion = 1;
            comparacion = date1.compareTo(date2);
            if (comparacion != 1) {
                almacenarRegistroNuevo();
                restaurarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "La fecha registro es menor que la fecha evento, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarRegistroNuevo() {
        try {
            HojaVidaEquipo reggNuevo = new HojaVidaEquipo();
            reggNuevo.setDetalleevento(inputDetalle);
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

            Date fecha1 = new Date(inputFechaRegistro);
            Date fecha2 = new Date(inputFechaEvento);
            Date fecha3 = new Date(inputFechaFinEvento);
            formateador.format(new Date());
            reggNuevo.setFecharegistro(fecha1);
            reggNuevo.setFechaevento(fecha2);
            reggNuevo.setFechafinevento(fecha3);
            reggNuevo.setCosto(inputCosto);
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            reggNuevo.setUsuariomodificacion(usuarioLoginSistema.getUserUsuario());
            reggNuevo.setTipoevento(inputTipoEvento);
            reggNuevo.setEquipoelemento(equipoElemento);
            reggNuevo.setObservaciones(inputTipoEvento.getObservacion() + ": " + equipoElemento.getNombreequipo() + " / " + equipoElemento.getInventarioequipo());
            gestionarPlantaHojasVidaEquiposBO.crearHojaVidaEquipo(reggNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarHojaVidaEquipo almacenarRegistroNuevo:  " + e.toString(), e);
        }
    }

    public void cancelarHojaVidaEquipo() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        inputDetalle = null;
        inputCosto = "0";
        inputFechaRegistro = null;
        inputFechaEvento = null;
        inputFechaFinEvento = null;
        validacionesDetalle = false;
        validacionesCosto = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        validacionesFechaFinEvento = false;
        fechaDiferidaEvento = true;
        fechaDiferidaRegistro = true;
    }

    public String cerrarPagina() {
        cancelarHojaVidaEquipo();
        return "hojadevida";
    }

    private void restaurarFormulario() {
        inputDetalle = null;
        inputCosto = "0";
        inputTipoEvento = null;
        inputFechaEvento = null;
        inputFechaFinEvento = null;
        inputFechaRegistro = null;
        validacionesDetalle = false;
        validacionesTipo = false;
        validacionesFechaRegistro = false;
        validacionesFechaEvento = false;
        fechaDiferidaEvento = true;
        fechaDiferidaRegistro = true;
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
    public String getInputDetalle() {
        return inputDetalle;
    }

    public void setInputDetalle(String inputDetalle) {
        this.inputDetalle = inputDetalle;
    }

    public String getInputFechaEvento() {
        return inputFechaEvento;
    }

    public void setInputFechaEvento(String inputFechaEvento) {
        this.inputFechaEvento = inputFechaEvento;
    }

    public String getInputFechaRegistro() {
        return inputFechaRegistro;
    }

    public void setInputFechaRegistro(String inputFechaRegistro) {
        this.inputFechaRegistro = inputFechaRegistro;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public BigInteger getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(BigInteger idEquipo) {
        this.idEquipo = idEquipo;
    }

    public TipoEvento getInputTipoEvento() {
        return inputTipoEvento;
    }

    public void setInputTipoEvento(TipoEvento inputTipoEvento) {
        this.inputTipoEvento = inputTipoEvento;
    }

    public List<TipoEvento> getListaTiposEventos() {
        if (listaTiposEventos == null) {
            listaTiposEventos = gestionarPlantaHojasVidaEquiposBO.consultarTiposEventosRegistrados();
        }
        return listaTiposEventos;
    }

    public void setListaTiposEventos(List<TipoEvento> listaTiposEventos) {
        this.listaTiposEventos = listaTiposEventos;
    }

    public EquipoElemento getEquipoElemento() {
        return equipoElemento;
    }

    public void setEquipoElemento(EquipoElemento equipoElemento) {
        this.equipoElemento = equipoElemento;
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

    public boolean isFechaDiferidaEvento() {
        return fechaDiferidaEvento;
    }

    public void setFechaDiferidaEvento(boolean fechaDiferidaEvento) {
        this.fechaDiferidaEvento = fechaDiferidaEvento;
    }

    public boolean isFechaDiferidaRegistro() {
        return fechaDiferidaRegistro;
    }

    public void setFechaDiferidaRegistro(boolean fechaDiferidaRegistro) {
        this.fechaDiferidaRegistro = fechaDiferidaRegistro;
    }

    public String getInputFechaFinEvento() {
        return inputFechaFinEvento;
    }

    public void setInputFechaFinEvento(String inputFechaFinEvento) {
        this.inputFechaFinEvento = inputFechaFinEvento;
    }

    public String getInputCosto() {
        return inputCosto;
    }

    public void setInputCosto(String inputCosto) {
        this.inputCosto = inputCosto;
    }
}
