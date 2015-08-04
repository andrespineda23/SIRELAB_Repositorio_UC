/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Reserva;
import java.io.Serializable;
import java.math.BigInteger;
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
public class ControllerConsultarReservasUsuario implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private Docente docenteEnLinea;
    private List<Reserva> listaReservas;

    public ControllerConsultarReservasUsuario() {
    }

    @PostConstruct
    public void init() {

    }

    public void obtenerInformacionReservasUsuario(BigInteger registro) {
        docenteEnLinea = administrarReservasBO.consultarDocentePorID(registro);
        listaReservas = administrarReservasBO.consultarReservasPorPersona(docenteEnLinea.getPersona().getIdpersona());
    }

    public void limpiarDatos() {
        docenteEnLinea = null;
        listaReservas = null;
    }

    //GET-SET
    public Docente getDocenteEnLinea() {
        return docenteEnLinea;
    }

    public void setDocenteEnLinea(Docente docenteEnLinea) {
        this.docenteEnLinea = docenteEnLinea;
    }

    public List<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

}
