/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.ReservaSala;
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

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerConsultarReservasSala implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private Persona personEnLinea;
    private String tipoUsuarioEnLinea;
    private List<ReservaSala> listaReservasSala;
    private List<ReservaSala> listaReservasSalaTemporal;
    private List<ReservaSala> listaReservasSalaTabla;
    private int posicionReservaSalaTabla;
    private int tamTotalReservaSala;
    private boolean bloquearPagSigReservaSala, bloquearPagAntReservaSala;
    private String cantidadRegistros;
    private int tipoConsulta;

    public ControllerConsultarReservasSala() {
    }

    @PostConstruct
    public void init() {
        tipoConsulta = 1;
    }

    public void obtenerInformacionReservasUsuario(String tipoUsuario, BigInteger registro) {
        tipoUsuarioEnLinea = tipoUsuario;
        System.out.println("tipoUsuario : "+tipoUsuario);
        System.out.println("registro : "+registro);
        personEnLinea = administrarReservasBO.obtenerPersonaConsultarReservas(tipoUsuario, registro);
        listaReservasSala = administrarReservasBO.consultarReservasSalaPorPersona(personEnLinea.getIdpersona());
        if (Utilidades.validarNulo(listaReservasSala)) {
            listaReservasSalaTemporal = listaReservasSala;
            cargarDatosEnEspera();
            inicializarDatosPaginacion();
        } else {
            posicionReservaSalaTabla = 0;
            tamTotalReservaSala = 0;
            bloquearPagAntReservaSala = true;
            bloquearPagSigReservaSala = true;
            posicionReservaSalaTabla = 0;
            cantidadRegistros = String.valueOf("0");
            cargarDatosTablaReservaSala();
        }
    }

    private void inicializarDatosPaginacion() {
        bloquearPagAntReservaSala = true;
        bloquearPagSigReservaSala = true;
        listaReservasSalaTabla = new ArrayList<ReservaSala>();
        tamTotalReservaSala = listaReservasSalaTemporal.size();
        posicionReservaSalaTabla = 0;
        cantidadRegistros = String.valueOf(tamTotalReservaSala);
        cargarDatosTablaReservaSala();
    }

    public String limpiarDatos() {
        tipoConsulta = 1;
        personEnLinea = null;
        listaReservasSala = null;
        listaReservasSalaTabla = null;
        listaReservasSalaTemporal = null;

        posicionReservaSalaTabla = 0;
        tamTotalReservaSala = 0;
        bloquearPagAntReservaSala = true;
        bloquearPagSigReservaSala = true;
        cantidadRegistros = "N/A";
        System.out.println("tipoUsuarioEnLinea : "+tipoUsuarioEnLinea);
        if ("DOCENTE".equalsIgnoreCase(tipoUsuarioEnLinea)) {
            return "inicio_docente";
        } else {
            if ("ESTUDIANTE".equalsIgnoreCase(tipoUsuarioEnLinea)) {
                return "inicio_docente";
            } else {
                return "inicio_docente";
            }
        }
    }

    private void cargarDatosTablaReservaSala() {
        if (tamTotalReservaSala < 10) {
            for (int i = 0; i < tamTotalReservaSala; i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = true;
            bloquearPagAntReservaSala = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = true;
        }
    }

    public void cargarPaginaSiguienteReservaSala() {
        listaReservasSalaTabla = new ArrayList<ReservaSala>();
        posicionReservaSalaTabla = posicionReservaSalaTabla + 10;
        int diferencia = tamTotalReservaSala - posicionReservaSalaTabla;
        if (diferencia > 10) {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = false;
        } else {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + diferencia); i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = true;
            bloquearPagAntReservaSala = false;
        }
    }

    public void cargarPaginaAnteriorReservaSala() {
        listaReservasSalaTabla = new ArrayList<ReservaSala>();
        posicionReservaSalaTabla = posicionReservaSalaTabla - 10;
        int diferencia = tamTotalReservaSala - posicionReservaSalaTabla;
        if (diferencia == tamTotalReservaSala) {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = true;
        } else {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = false;
        }
    }

    public void cambiarTipoConsulta() {
        if (tipoConsulta == 1) {
            cargarDatosEnEspera();
            inicializarDatosPaginacion();
        } else {
            cargarDatosHistoricos();
            inicializarDatosPaginacion();
        }
    }

    private void cargarDatosHistoricos() {
        if (Utilidades.validarNulo(listaReservasSala)) {
            listaReservasSalaTemporal = new ArrayList<ReservaSala>();
            for (int i = 0; i < listaReservasSala.size(); i++) {
                if (Utilidades.fechaIngresadaCorrecta(listaReservasSala.get(i).getReserva().getFechareserva()) == false) {
                    listaReservasSalaTemporal.add(listaReservasSala.get(i));
                }
            }
        }
    }

    private void cargarDatosEnEspera() {
        if (Utilidades.validarNulo(listaReservasSala)) {
            listaReservasSalaTemporal = new ArrayList<ReservaSala>();
            for (int i = 0; i < listaReservasSala.size(); i++) {
                if (Utilidades.fechaIngresadaCorrecta(listaReservasSala.get(i).getReserva().getFechareserva()) == true) {
                    listaReservasSalaTemporal.add(listaReservasSala.get(i));
                }
            }
        }
    }

    //GET-SET
    public Persona getPersonEnLinea() {
        return personEnLinea;
    }

    public void setPersonEnLinea(Persona personEnLinea) {
        this.personEnLinea = personEnLinea;
    }

    public List<ReservaSala> getListaReservasSala() {
        return listaReservasSala;
    }

    public void setListaReservasSala(List<ReservaSala> listaReservasSala) {
        this.listaReservasSala = listaReservasSala;
    }

    public List<ReservaSala> getListaReservasSalaTemporal() {
        return listaReservasSalaTemporal;
    }

    public void setListaReservasSalaTemporal(List<ReservaSala> listaReservasSalaTemporal) {
        this.listaReservasSalaTemporal = listaReservasSalaTemporal;
    }

    public List<ReservaSala> getListaReservasSalaTabla() {
        return listaReservasSalaTabla;
    }

    public void setListaReservasSalaTabla(List<ReservaSala> listaReservasSalaTabla) {
        this.listaReservasSalaTabla = listaReservasSalaTabla;
    }

    public int getPosicionReservaSalaTabla() {
        return posicionReservaSalaTabla;
    }

    public void setPosicionReservaSalaTabla(int posicionReservaSalaTabla) {
        this.posicionReservaSalaTabla = posicionReservaSalaTabla;
    }

    public int getTamTotalReservaSala() {
        return tamTotalReservaSala;
    }

    public void setTamTotalReservaSala(int tamTotalReservaSala) {
        this.tamTotalReservaSala = tamTotalReservaSala;
    }

    public boolean isBloquearPagSigReservaSala() {
        return bloquearPagSigReservaSala;
    }

    public void setBloquearPagSigReservaSala(boolean bloquearPagSigReservaSala) {
        this.bloquearPagSigReservaSala = bloquearPagSigReservaSala;
    }

    public boolean isBloquearPagAntReservaSala() {
        return bloquearPagAntReservaSala;
    }

    public void setBloquearPagAntReservaSala(boolean bloquearPagAntReservaSala) {
        this.bloquearPagAntReservaSala = bloquearPagAntReservaSala;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public int getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(int tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

}
