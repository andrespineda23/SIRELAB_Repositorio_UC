/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaModulo;
import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Reserva;
import com.sirelab.entidades.ReservaEquipoElemento;
import com.sirelab.utilidades.UsuarioLogin;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerReservaModulo3 implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private AyudaReservaModulo reservaModulo;
    private Reserva reservaPersona;
    private Integer valorReserva;

    public ControllerReservaModulo3() {
        reservaModulo = AyudaReservaModulo.getInstance();
        reservaPersona = reservaModulo.getReserva();
    }

    @PostConstruct
    public void init() {
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
            valorReserva = valorReserva + reservaModulo.getModuloLaboratorio().getCostoalquiler().intValue();
            reservaPersona.setValorreserva(valorReserva);
            administrarReservasBO.actualizarValorReserva(reservaPersona);
        }
    }

    public String cerrarDatosReserva() {
        valorReserva = 0;
        reservaPersona = null;
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

    public AyudaReservaModulo getReservaModulo() {
        return reservaModulo;
    }

    public void setReservaModulo(AyudaReservaModulo reservaModulo) {
        this.reservaModulo = reservaModulo;
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

}
