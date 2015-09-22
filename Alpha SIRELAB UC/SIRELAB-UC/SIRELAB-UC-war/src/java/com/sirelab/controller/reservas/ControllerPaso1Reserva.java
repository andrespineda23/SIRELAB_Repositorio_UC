/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaSala;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerPaso1Reserva implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private Date fecha;
    private List<Integer> listaHora;
    private Integer horaIngreso;
    //
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio parametroLaboratorio;
    private List<SalaLaboratorio> listaSalaLaboratorio;
    private SalaLaboratorio parametroSala;
    private boolean activarSala;
    private String mensajeFormulario, colorMensaje;

    public ControllerPaso1Reserva() {
    }

    @PostConstruct
    public void init() {
        activarSala = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        fecha = null;
        cargarHoras();
        parametroLaboratorio = null;
        parametroSala = null;
    }

    private void cargarHoras() {
        horaIngreso = null;
        listaHora = new ArrayList<Integer>();
        listaHora.add(7);
        listaHora.add(9);
        listaHora.add(11);
        listaHora.add(13);
        listaHora.add(15);
        listaHora.add(18);
        listaHora.add(20);
    }

    private boolean validarFecha() {
        boolean retorno = true;
        if (Utilidades.validarNulo(fecha)) {
            if (!Utilidades.fechaIngresadaCorrecta(fecha)) {
                System.out.println("fecha : " + fecha);
                retorno = false;
            }
        } else {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarHora() {
        boolean retorno = true;
        if (!Utilidades.validarNulo(horaIngreso)) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarLaboratorio() {
        boolean retorno = true;
        if (!Utilidades.validarNulo(parametroLaboratorio)) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarSala() {
        boolean retorno = true;
        if (!Utilidades.validarNulo(parametroSala)) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCamposReserva() {
        boolean retorno = true;
        if (validarSala() == false) {
            retorno = false;
        }
        if (validarFecha() == false) {
            System.out.println("Error");
            retorno = false;
        }
        if (validarLaboratorio() == false) {
            retorno = false;
        }
        if (validarHora() == false) {
            retorno = false;
        }
        return retorno;
    }

    public String consultarReservaARealizar() {
        String paso2 = "";
        if (validarCamposReserva() == true) {
            Boolean respuestaReserva = administrarReservasBO.validarReservaSalaDisposible(fecha, String.valueOf(horaIngreso), parametroSala.getIdsalalaboratorio());
            if (null != respuestaReserva) {
                if (respuestaReserva == true) {
                    AyudaReservaSala reservaSala = new AyudaReservaSala();
                    reservaSala.setFechaReserva(fecha);
                    reservaSala.setSalaLaboratorio(parametroSala);
                    reservaSala.setHoraInicio(String.valueOf(horaIngreso));
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reservaSala", reservaSala);
                    paso2 = "paso2reserva";
                    System.out.println("Información guardada con exito en la sesión");
                } else {
                    mensajeFormulario = "Existe una reserva solicitada en el tiempo y sala de laboratorio asignado.";
                    colorMensaje = "red";
                }
            }
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar";
            colorMensaje = "red";
        }
        return paso2;
    }

    public void actualizarLaboratorio() {
        if (Utilidades.validarNulo(parametroLaboratorio)) {
            parametroSala = new SalaLaboratorio();
            listaSalaLaboratorio = administrarReservasBO.consultarSalaLaboratorioPorIdLaboratorio(parametroLaboratorio.getIdlaboratorio());
            activarSala = false;
        } else {
            parametroSala = new SalaLaboratorio();
            activarSala = true;
            listaSalaLaboratorio = null;
        }
    }
    
    public void cancelarProcesoReserva(){
        activarSala = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        fecha = null;
        listaHora = null;
        listaLaboratorios = null;
        listaSalaLaboratorio = null;
        parametroLaboratorio = null;
        parametroSala = null;
        horaIngreso = null;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Integer> getListaHora() {
        return listaHora;
    }

    public void setListaHora(List<Integer> listaHora) {
        this.listaHora = listaHora;
    }

    public Integer getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(Integer horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public List<Laboratorio> getListaLaboratorios() {
        if (null == listaLaboratorios) {
            listaLaboratorios = administrarReservasBO.consultarLaboratoriosRegistradosActivos();
        }
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getParametroLaboratorio() {
        return parametroLaboratorio;
    }

    public void setParametroLaboratorio(Laboratorio parametroLaboratorio) {
        this.parametroLaboratorio = parametroLaboratorio;
    }

    public List<SalaLaboratorio> getListaSalaLaboratorio() {
        return listaSalaLaboratorio;
    }

    public void setListaSalaLaboratorio(List<SalaLaboratorio> listaSalaLaboratorio) {
        this.listaSalaLaboratorio = listaSalaLaboratorio;
    }

    public SalaLaboratorio getParametroSala() {
        return parametroSala;
    }

    public void setParametroSala(SalaLaboratorio parametroSala) {
        this.parametroSala = parametroSala;
    }

    public boolean isActivarSala() {
        return activarSala;
    }

    public void setActivarSala(boolean activarSala) {
        this.activarSala = activarSala;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
