/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import java.io.Serializable;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesReservaModulo implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private ReservaModuloLaboratorio reservaModuloLaboratorio;

    public ControllerDetallesReservaModulo() {
    }

    public void recibirIdReservaModuloLaboratorio(BigInteger idRegistro) {
        reservaModuloLaboratorio = administrarReservasBO.obtenerReservaModuloLaboratorioPorId(idRegistro);
    }

    public ReservaModuloLaboratorio getReservaModuloLaboratorio() {
        return reservaModuloLaboratorio;
    }

    public void setReservaModuloLaboratorio(ReservaModuloLaboratorio reservaModuloLaboratorio) {
        this.reservaModuloLaboratorio = reservaModuloLaboratorio;
    }

}
