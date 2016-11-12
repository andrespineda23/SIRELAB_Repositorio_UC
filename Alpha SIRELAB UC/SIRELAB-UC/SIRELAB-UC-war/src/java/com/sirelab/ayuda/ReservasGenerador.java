/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.ayuda;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class ReservasGenerador implements Serializable {

    private String ID_RESERVA;
    private String RESERVA;
    private String FECHA_RESERVA;
    private String HORA_RESERVA;
    private String HORA_REAL;
    private String TIPO_RESERVA;
    private String SERVICIO;
    private String LABORATORIO;
    private String SALA_LABORATORIO;
    private String ESTADO;
    private String TIPO_USUARIO;
    private String USUARIO;
    private String ID_USUARIO;

    public ReservasGenerador() {
    }

    public String getID_RESERVA() {
        return ID_RESERVA;
    }

    public void setID_RESERVA(String ID_RESERVA) {
        this.ID_RESERVA = ID_RESERVA;
    }

    public String getRESERVA() {
        return RESERVA;
    }

    public void setRESERVA(String RESERVA) {
        this.RESERVA = RESERVA;
    }

    public String getFECHA_RESERVA() {
        return FECHA_RESERVA;
    }

    public void setFECHA_RESERVA(String FECHA_RESERVA) {
        this.FECHA_RESERVA = FECHA_RESERVA;
    }

    public String getHORA_RESERVA() {
        return HORA_RESERVA;
    }

    public void setHORA_RESERVA(String HORA_RESERVA) {
        this.HORA_RESERVA = HORA_RESERVA;
    }

    public String getHORA_REAL() {
        return HORA_REAL;
    }

    public void setHORA_REAL(String HORA_REAL) {
        this.HORA_REAL = HORA_REAL;
    }

    public String getTIPO_RESERVA() {
        return TIPO_RESERVA;
    }

    public void setTIPO_RESERVA(String TIPO_RESERVA) {
        this.TIPO_RESERVA = TIPO_RESERVA;
    }

    public String getSERVICIO() {
        return SERVICIO;
    }

    public void setSERVICIO(String SERVICIO) {
        this.SERVICIO = SERVICIO;
    }

    public String getLABORATORIO() {
        return LABORATORIO;
    }

    public void setLABORATORIO(String LABORATORIO) {
        this.LABORATORIO = LABORATORIO;
    }

    public String getSALA_LABORATORIO() {
        return SALA_LABORATORIO;
    }

    public void setSALA_LABORATORIO(String SALA_LABORATORIO) {
        this.SALA_LABORATORIO = SALA_LABORATORIO;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getTIPO_USUARIO() {
        return TIPO_USUARIO;
    }

    public void setTIPO_USUARIO(String TIPO_USUARIO) {
        this.TIPO_USUARIO = TIPO_USUARIO;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public void setUSUARIO(String USUARIO) {
        this.USUARIO = USUARIO;
    }

    public String getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(String ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

}
