/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.GuiaLaboratorio;
import com.sirelab.entidades.ReservaSala;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesReservaSala implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private ReservaSala reservaSala;
    private boolean activarGuiaNueva;
    private boolean activarGuia;
    private List<GuiaLaboratorio> listaGuiaLaboratorios;
    private GuiaLaboratorio parametrioGuia;
    private BigInteger idReserva;

    public ControllerDetallesReservaSala() {
    }

    public void recibirIdReservaSala(BigInteger idRegistro) {
        this.idReserva = idRegistro;
        activarGuiaNueva = true;
        activarGuia = false;
        parametrioGuia = null;
        listaGuiaLaboratorios = null;
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idRegistro);
    }

    public void activarCambiarGuia() {
        activarGuiaNueva = false;
        activarGuia = true;
        listaGuiaLaboratorios = administrarReservasBO.consultarGuiasLaboratorioPorIdAreaPlan(reservaSala.getAsignaturaporplanestudio().getIdasignaturaporplanestudio());
        parametrioGuia = null;
    }

    public void guardarNuevaGuiaLaboratorio() {
        try {
            reservaSala.setGuialaboratorio(parametrioGuia);
            administrarReservasBO.actualizarGuiaLaboratorioReserva(reservaSala);
            activarGuiaNueva = true;
            activarGuia = false;
            parametrioGuia = null;
            listaGuiaLaboratorios = null;
            reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idReserva);
        } catch (Exception e) {
            System.out.println("Error ControllerDetallesReservaSala guardarNuevaGuiaLaboratorio: " + e.toString());
        }
    }

    public void descargarGuiaLaboratorio() throws FileNotFoundException, IOException {
        File ficheroPDF = new File(reservaSala.getGuialaboratorio().getUbicacionguia());
        FacesContext ctx = FacesContext.getCurrentInstance();
        FileInputStream fis = new FileInputStream(ficheroPDF);
        byte[] bytes = new byte[1000];
        int read = 0;
        if (!ctx.getResponseComplete()) {
            String fileName = ficheroPDF.getName();
            String contentType = "application/pdf";
            HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            ServletOutputStream out = response.getOutputStream();
            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            ctx.responseComplete();
        }
    }

    public ReservaSala getReservaSala() {
        return reservaSala;
    }

    public void setReservaSala(ReservaSala reservaSala) {
        this.reservaSala = reservaSala;
    }

    public boolean isActivarGuiaNueva() {
        return activarGuiaNueva;
    }

    public void setActivarGuiaNueva(boolean activarGuiaNueva) {
        this.activarGuiaNueva = activarGuiaNueva;
    }

    public boolean isActivarGuia() {
        return activarGuia;
    }

    public void setActivarGuia(boolean activarGuia) {
        this.activarGuia = activarGuia;
    }

    public List<GuiaLaboratorio> getListaGuiaLaboratorios() {
        return listaGuiaLaboratorios;
    }

    public void setListaGuiaLaboratorios(List<GuiaLaboratorio> listaGuiaLaboratorios) {
        this.listaGuiaLaboratorios = listaGuiaLaboratorios;
    }

    public GuiaLaboratorio getParametrioGuia() {
        return parametrioGuia;
    }

    public void setParametrioGuia(GuiaLaboratorio parametrioGuia) {
        this.parametrioGuia = parametrioGuia;
    }

}
