/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaSala;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaEquipoElemento;
import com.sirelab.utilidades.UsuarioLogin;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerReservaSala3 implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private AyudaReservaSala reservaSala;
    private Reserva reservaPersona;
    private Integer valorReserva;
    private String nombreAsignatura;
    private String rutaGuia;

    public ControllerReservaSala3() {
    }

    @PostConstruct
    public void init() {
        reservaSala = (AyudaReservaSala) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reservaSala");
        nombreAsignatura = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombreAsignatura");
        rutaGuia = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rutaGuia");
        reservaPersona = (Reserva) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("reservaPersona");
        valorReserva = 0;
        obtenerCostoFinalReserva();
    }

    private void obtenerCostoFinalReserva() {
        valorReserva = 0;
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        if (("ENTIDADEXTERNA".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) || ("PERSONACONTACTO".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario()))) {
            List<ReservaEquipoElemento> lista = administrarReservasBO.obtenerReservasEquipoPorIdReserva(reservaPersona.getIdreserva());
            if (null != lista) {
                Integer costo = 0;
                for (int i = 0; i < lista.size(); i++) {
                    costo = costo + (lista.get(i).getEquipoelemento().getCostoalquiler() * lista.get(i).getEquipoelemento().getCantidadequipo());
                }
                valorReserva = valorReserva + costo;
            }
            valorReserva = valorReserva + Long.valueOf(reservaSala.getSalaLaboratorio().getCostoalquiler()).intValue();
            reservaPersona.setValorreserva(valorReserva);
            administrarReservasBO.actualizarValorReserva(reservaPersona);
        }
    }

    public String cerrarDatosReserva() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("reservaSala");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("nombreAsignatura");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("reservaPersona");
        valorReserva = 0;
        reservaPersona = null;
        reservaSala = null;
        nombreAsignatura = "";
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("DOCENTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            return "iniciodocente";
        } else {
            if ("ESTUDIANTE".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                return "inicioestudiante";
            } else {
                return "iniciodocente";
            }
        }
    }
    
    public void descargarGuiaLaboratorio() throws FileNotFoundException, IOException {
        File ficheroPDF = new File(rutaGuia);
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


    public AyudaReservaSala getReservaSala() {
        return reservaSala;
    }

    public void setReservaSala(AyudaReservaSala reservaSala) {
        this.reservaSala = reservaSala;
    }

    public Reserva getReservaPersona() {
        return reservaPersona;
    }

    public void setReservaPersona(Reserva reservaPersona) {
        this.reservaPersona = reservaPersona;
    }

    public Integer getValorReserva() {
        return valorReserva;
    }

    public void setValorReserva(Integer valorReserva) {
        this.valorReserva = valorReserva;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

}