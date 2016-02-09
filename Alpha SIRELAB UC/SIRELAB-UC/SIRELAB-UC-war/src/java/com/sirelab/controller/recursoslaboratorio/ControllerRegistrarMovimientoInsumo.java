/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoMovimientosInsumoBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.MovimientoInsumo;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.TipoMovimiento;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarMovimientoInsumo implements Serializable {

    @EJB
    GestionarRecursoMovimientosInsumoBOInterface gestionarRecursoMovimientosInsumoBO;

    private String nuevoCantidadMovimiento, nuevoCostoMovimiento;
    private String nuevoFechaMovimiento;
    private boolean validacionesTipo, validacionesCantidad, validacionesCosto, validacionesFecha;
    private String mensajeFormulario;
    private Insumo insumoRegistro;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private boolean fechaDiferida;
    private TipoMovimiento nuevoTipoMovimiento;
    private List<TipoMovimiento> listaTipoMovimiento;
    private List<EquipoElemento> listaEquiposElementos;
    private EquipoElemento equipoElemento;
    private boolean asociarEquipo;
    private MensajesConstantes constantes;
    private Integer fechaAnio;
    private AyudaFechaReserva fechaRegistroMes;
    private AyudaFechaReserva fechaRegistroDia;
    private List<AyudaFechaReserva> listaMesesRegistro;
    private List<AyudaFechaReserva> listaDiasRegistro;

    public ControllerRegistrarMovimientoInsumo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        asociarEquipo = true;
        fechaDiferida = true;
        validacionesCantidad = false;
        validacionesCosto = false;
        validacionesTipo = false;
        validacionesFecha = true;
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        mensajeFormulario = "N/A";
        //
        nuevoTipoMovimiento = null;
        nuevoCantidadMovimiento = null;
        nuevoCostoMovimiento = null;
        Date fecha = new Date();
        DateFormat df = DateFormat.getDateInstance();
        nuevoFechaMovimiento = df.format(fecha);
        BasicConfigurator.configure();
    }

    public void recibirIDInsumo(BigInteger idRegistro) {
        insumoRegistro = gestionarRecursoMovimientosInsumoBO.obtenerInsumoPorID(idRegistro);
        cargarFechaRegistro();
    }

    private void cargarFechaRegistro() {
        Date fechaHoy = new Date();
        fechaAnio = fechaHoy.getYear() + 1900;
        listaMesesRegistro = new ArrayList<AyudaFechaReserva>();
        for (int i = 0; i < 12; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setParametro(i);
            int mes = i + 1;
            ayuda.setMensajeMostrar(String.valueOf(mes));
            listaMesesRegistro.add(ayuda);
        }
        fechaRegistroMes = obtenerMesExacto(fechaHoy.getMonth());
        actualizarInformacionRegistroDia();
        fechaRegistroDia = obtenerDiaExacto(fechaHoy.getDate());
    }

    private AyudaFechaReserva obtenerMesExacto(int mes) {
        AyudaFechaReserva ayuda = null;
        for (int i = 0; i < listaMesesRegistro.size(); i++) {
            if (mes == listaMesesRegistro.get(i).getParametro()) {
                ayuda = listaMesesRegistro.get(i);
                break;
            }
        }
        return ayuda;
    }

    private AyudaFechaReserva obtenerDiaExacto(int dia) {
        AyudaFechaReserva ayuda = null;

        for (int i = 0; i < listaDiasRegistro.size(); i++) {
            if (dia == listaDiasRegistro.get(i).getParametro()) {
                ayuda = listaDiasRegistro.get(i);
                break;
            }
        }
        return ayuda;
    }

    public void actualizarInformacionRegistroDia() {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.set(Integer.valueOf(fechaAnio), fechaRegistroMes.getParametro(), 1);
        int diaFin = ahoraCal.getActualMaximum(Calendar.DATE);
        int diaInicio = 1;
        listaDiasRegistro = new ArrayList<AyudaFechaReserva>();
        for (int i = diaInicio; i < diaFin + 1; i++) {
            AyudaFechaReserva ayuda = new AyudaFechaReserva();
            ayuda.setMensajeMostrar(String.valueOf(i));
            ayuda.setParametro(i);
            listaDiasRegistro.add(ayuda);
        }
    }

    public void actualizarAsocioEquipo() {
        if (asociarEquipo == true) {
            equipoElemento = null;
        }
    }

    public void validarTipoMovimiento() {
        if (Utilidades.validarNulo(nuevoTipoMovimiento)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTipoMovimiento", new FacesMessage("El tipo movimiento es obligatorio."));
        }

    }

    public void validarCantidadMovimiento() {
        if (Utilidades.validarNulo(nuevoCantidadMovimiento) && (!nuevoCantidadMovimiento.isEmpty()) && (nuevoCantidadMovimiento.trim().length() > 0)) {
            if (!Utilidades.isNumber(nuevoCantidadMovimiento)) {
                validacionesCantidad = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadMovimiento", new FacesMessage("La cantidad se encuentra incorrecta. " + constantes.RECURSO_CANT_E));
            } else {
                int cantidad = insumoRegistro.getCantidadexistencia() - Integer.valueOf(nuevoCantidadMovimiento);
                if (cantidad < insumoRegistro.getCantidadminimia()) {
                    validacionesCantidad = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadMovimiento", new FacesMessage("La cantidad es menor a la cantidad minima de insumo (" + insumoRegistro.getCantidadminimia() + ")."));
                } else {
                    validacionesCantidad = true;
                }
            }
        } else {
            validacionesCantidad = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadMovimiento", new FacesMessage("La cantidad es obligatoria. " + constantes.RECURSO_CANT_E));
        }
    }

    public void validarCostoMovimiento() {
        if (Utilidades.validarNulo(nuevoCostoMovimiento) && (!nuevoCostoMovimiento.isEmpty()) && (nuevoCostoMovimiento.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoCostoMovimiento)) == false) {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoMovimiento", new FacesMessage("El costo se encuentra incorrecto. " + constantes.RECURSO_COSTO));
            } else {
                validacionesCosto = true;
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCostoMovimiento", new FacesMessage("El costo es obligatorio. " + constantes.RECURSO_COSTO));
        }
    }

    public void validarFechaMovimiento() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, fechaAnio);
        cal.set(Calendar.MONTH, fechaRegistroMes.getParametro());
        cal.set(Calendar.DATE, fechaRegistroDia.getParametro());
        if ((Utilidades.fechaIngresadaCorrecta(cal.getTime().toString())) == false) {
            validacionesFecha = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoFechaMovimiento", new FacesMessage("La fecha se encuentra incorrecta. Formato (dd/mm/yyyy)"));
        } else {
            validacionesFecha = true;
        }
    }

    public String limpiarRegistroMovimientoLaboratorio() {
        nuevoTipoMovimiento = null;
        nuevoCantidadMovimiento = null;
        nuevoCostoMovimiento = null;
        nuevoFechaMovimiento = null;
        //
        validacionesCantidad = false;
        validacionesCosto = false;
        validacionesTipo = false;
        validacionesFecha = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        listaTipoMovimiento = null;
        colorMensaje = "black";
        fechaDiferida = true;
        asociarEquipo = true;
        listaEquiposElementos = null;
        equipoElemento = null;
        activarCasillas = false;
        return "administrarmovimientoinsumo";
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCantidad == false) {
            retorno = false;
        }
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesTipo == false) {
            retorno = false;
        }
        if (validacionesFecha == false) {
            retorno = false;
        }
        if (asociarEquipo == false) {
            if (!Utilidades.validarNulo(equipoElemento)) {
                retorno = false;
            }
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoMovimiento() {
        if (validarResultadosValidacion() == true) {
            almacenaNuevoMovimientoEnSistema();
            limpiarFormulario();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    public void almacenaNuevoMovimientoEnSistema() {
        try {
            MovimientoInsumo movimientoNuevo = new MovimientoInsumo();
            movimientoNuevo.setTipomovimiento(nuevoTipoMovimiento);
            movimientoNuevo.setCantidadmovimiento(Integer.parseInt(nuevoCantidadMovimiento));
            movimientoNuevo.setCostomovimiento(Long.parseLong(nuevoCostoMovimiento));

            String pattern = "dd/MM/yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, fechaAnio);
            cal.set(Calendar.MONTH, fechaRegistroMes.getParametro());
            cal.set(Calendar.DATE, fechaRegistroDia.getParametro());
            Date fecha1 = null;
            try {
                fecha1 = format.parse(cal.getTime().toString());
                movimientoNuevo.setFechamovimiento(fecha1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            movimientoNuevo.setInsumo(insumoRegistro);
            if (asociarEquipo == true) {
                gestionarRecursoMovimientosInsumoBO.crearMovimientoInsumo(movimientoNuevo);
            } else {
                gestionarRecursoMovimientosInsumoBO.crearMovimientoInsumoAEquipo(movimientoNuevo, equipoElemento);
            }
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarMovimiento almacenaNuevoMovimientoEnSistema:  " + e.toString());
            logger.error("Error ControllerRegistrarMovimiento almacenaNuevoMovimientoEnSistema : " + e.toString());
        }
    }

    private void limpiarFormulario() {
        nuevoTipoMovimiento = null;
        nuevoCantidadMovimiento = null;
        Date fecha = new Date();
        DateFormat df = DateFormat.getDateInstance();
        nuevoFechaMovimiento = df.format(fecha);
        nuevoCostoMovimiento = null;
        asociarEquipo = true;
        equipoElemento = null;
        //
        validacionesCantidad = false;
        validacionesCosto = false;
        fechaDiferida = true;
        validacionesTipo = false;
        validacionesFecha = false;
        cargarFechaRegistro();
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
    public TipoMovimiento getNuevoTipoMovimiento() {
        return nuevoTipoMovimiento;
    }

    public void setNuevoTipoMovimiento(TipoMovimiento nuevoTipoMovimiento) {
        this.nuevoTipoMovimiento = nuevoTipoMovimiento;
    }

    public List<TipoMovimiento> getListaTipoMovimiento() {
        if (null == listaTipoMovimiento) {
            listaTipoMovimiento = gestionarRecursoMovimientosInsumoBO.obtenerTipoMovimientoRegistrado();
        }
        return listaTipoMovimiento;
    }

    public void setListaTipoMovimiento(List<TipoMovimiento> listaTipoMovimiento) {
        this.listaTipoMovimiento = listaTipoMovimiento;
    }

    public String getNuevoCantidadMovimiento() {
        return nuevoCantidadMovimiento;
    }

    public void setNuevoCantidadMovimiento(String nuevoCantidadMovimiento) {
        this.nuevoCantidadMovimiento = nuevoCantidadMovimiento;
    }

    public String getNuevoCostoMovimiento() {
        return nuevoCostoMovimiento;
    }

    public void setNuevoCostoMovimiento(String nuevoCostoMovimiento) {
        this.nuevoCostoMovimiento = nuevoCostoMovimiento;
    }

    public String getNuevoFechaMovimiento() {
        return nuevoFechaMovimiento;
    }

    public void setNuevoFechaMovimiento(String nuevoFechaMovimiento) {
        this.nuevoFechaMovimiento = nuevoFechaMovimiento;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public Insumo getInsumoRegistro() {
        return insumoRegistro;
    }

    public void setInsumoRegistro(Insumo insumoRegistro) {
        this.insumoRegistro = insumoRegistro;
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

    public boolean isFechaDiferida() {
        return fechaDiferida;
    }

    public void setFechaDiferida(boolean fechaDiferida) {
        this.fechaDiferida = fechaDiferida;
    }

    public List<EquipoElemento> getListaEquiposElementos() {
        if (null == listaEquiposElementos) {
            listaEquiposElementos = gestionarRecursoMovimientosInsumoBO.obtenerEquipoElementoRegistrado();
        }
        return listaEquiposElementos;
    }

    public void setListaEquiposElementos(List<EquipoElemento> listaEquiposElementos) {
        this.listaEquiposElementos = listaEquiposElementos;
    }

    public EquipoElemento getEquipoElemento() {
        return equipoElemento;
    }

    public void setEquipoElemento(EquipoElemento equipoElemento) {
        this.equipoElemento = equipoElemento;
    }

    public boolean isAsociarEquipo() {
        return asociarEquipo;
    }

    public void setAsociarEquipo(boolean asociarEquipo) {
        this.asociarEquipo = asociarEquipo;
    }

    public Integer getFechaAnio() {
        return fechaAnio;
    }

    public void setFechaAnio(Integer fechaAnio) {
        this.fechaAnio = fechaAnio;
    }

    public AyudaFechaReserva getFechaRegistroMes() {
        return fechaRegistroMes;
    }

    public void setFechaRegistroMes(AyudaFechaReserva fechaRegistroMes) {
        this.fechaRegistroMes = fechaRegistroMes;
    }

    public AyudaFechaReserva getFechaRegistroDia() {
        return fechaRegistroDia;
    }

    public void setFechaRegistroDia(AyudaFechaReserva fechaRegistroDia) {
        this.fechaRegistroDia = fechaRegistroDia;
    }

    public List<AyudaFechaReserva> getListaMesesRegistro() {
        return listaMesesRegistro;
    }

    public void setListaMesesRegistro(List<AyudaFechaReserva> listaMesesRegistro) {
        this.listaMesesRegistro = listaMesesRegistro;
    }

    public List<AyudaFechaReserva> getListaDiasRegistro() {
        return listaDiasRegistro;
    }

    public void setListaDiasRegistro(List<AyudaFechaReserva> listaDiasRegistro) {
        this.listaDiasRegistro = listaDiasRegistro;
    }

}
