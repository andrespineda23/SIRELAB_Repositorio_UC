/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.GuiaLaboratorio;
import com.sirelab.entidades.ReservaEquipoElemento;
import com.sirelab.entidades.ReservaSala;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesReservaSala implements Serializable {

    static Logger logger = Logger.getLogger(ControllerDetallesReservaSala.class);

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private ReservaSala reservaSala;
    private boolean activarGuiaNueva;
    private boolean activarGuia;
    private List<GuiaLaboratorio> listaGuiaLaboratorios;
    private GuiaLaboratorio parametrioGuia;
    private BigInteger idReserva;
    private List<ReservaEquipoElemento> listaEquiposReservados;
    private List<ReservaEquipoElemento> listaEquiposReservadosTabla;
    private int posicionEquiposTabla;
    private int tamTotalEquipos;
    private boolean bloquearPagSigEquipos, bloquearPagAntEquipos;

    public ControllerDetallesReservaSala() {
    }

    public void recibirIdReservaSala(BigInteger idRegistro) {
        listaEquiposReservados = null;
        listaEquiposReservadosTabla = null;
        posicionEquiposTabla = 0;
        tamTotalEquipos = 0;
        bloquearPagAntEquipos = true;
        bloquearPagSigEquipos = true;
        this.idReserva = idRegistro;
        activarGuiaNueva = true;
        activarGuia = false;
        parametrioGuia = null;
        listaGuiaLaboratorios = null;
        reservaSala = administrarReservasBO.obtenerReservaSalaPorId(idRegistro);
        if (null != reservaSala) {
            listaEquiposReservados = administrarReservasBO.obtenerReservasEquipoPorIdReserva(reservaSala.getReserva().getIdreserva());
            listaEquiposReservadosTabla = new ArrayList<ReservaEquipoElemento>();
            tamTotalEquipos = listaEquiposReservados.size();
            posicionEquiposTabla = 0;
            cargarDatosTablaEquipos();

        }
    }

    private void cargarDatosTablaEquipos() {
        if (tamTotalEquipos < 10) {
            for (int i = 0; i < tamTotalEquipos; i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = true;
            bloquearPagAntEquipos = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = false;
            bloquearPagAntEquipos = true;
        }
    }

    public void cargarPaginaSiguienteEquipos() {
        listaEquiposReservadosTabla = new ArrayList<ReservaEquipoElemento>();
        posicionEquiposTabla = posicionEquiposTabla + 10;
        int diferencia = tamTotalEquipos - posicionEquiposTabla;
        if (diferencia > 10) {
            for (int i = posicionEquiposTabla; i < (posicionEquiposTabla + 10); i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = false;
            bloquearPagAntEquipos = false;
        } else {
            for (int i = posicionEquiposTabla; i < (posicionEquiposTabla + diferencia); i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = true;
            bloquearPagAntEquipos = false;
        }
    }

    public void cargarPaginaAnteriorEquipos() {
        listaEquiposReservadosTabla = new ArrayList<ReservaEquipoElemento>();
        posicionEquiposTabla = posicionEquiposTabla - 10;
        int diferencia = tamTotalEquipos - posicionEquiposTabla;
        if (diferencia == tamTotalEquipos) {
            for (int i = posicionEquiposTabla; i < (posicionEquiposTabla + 10); i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = false;
            bloquearPagAntEquipos = true;
        } else {
            for (int i = posicionEquiposTabla; i < (posicionEquiposTabla + 10); i++) {
                listaEquiposReservadosTabla.add(listaEquiposReservados.get(i));
            }
            bloquearPagSigEquipos = false;
            bloquearPagAntEquipos = false;
        }
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
            logger.error("Error ControllerDetallesReservaSala guardarNuevaGuiaLaboratorio: " + e.toString(), e);
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

    public List<ReservaEquipoElemento> getListaEquiposReservados() {
        return listaEquiposReservados;
    }

    public void setListaEquiposReservados(List<ReservaEquipoElemento> listaEquiposReservados) {
        this.listaEquiposReservados = listaEquiposReservados;
    }

    public List<ReservaEquipoElemento> getListaEquiposReservadosTabla() {
        return listaEquiposReservadosTabla;
    }

    public void setListaEquiposReservadosTabla(List<ReservaEquipoElemento> listaEquiposReservadosTabla) {
        this.listaEquiposReservadosTabla = listaEquiposReservadosTabla;
    }

    public int getPosicionEquiposTabla() {
        return posicionEquiposTabla;
    }

    public void setPosicionEquiposTabla(int posicionEquiposTabla) {
        this.posicionEquiposTabla = posicionEquiposTabla;
    }

    public int getTamTotalEquipos() {
        return tamTotalEquipos;
    }

    public void setTamTotalEquipos(int tamTotalEquipos) {
        this.tamTotalEquipos = tamTotalEquipos;
    }

    public boolean isBloquearPagSigEquipos() {
        return bloquearPagSigEquipos;
    }

    public void setBloquearPagSigEquipos(boolean bloquearPagSigEquipos) {
        this.bloquearPagSigEquipos = bloquearPagSigEquipos;
    }

    public boolean isBloquearPagAntEquipos() {
        return bloquearPagAntEquipos;
    }

    public void setBloquearPagAntEquipos(boolean bloquearPagAntEquipos) {
        this.bloquearPagAntEquipos = bloquearPagAntEquipos;
    }

}
