/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.controller.paginasiniciales.ControllerPaginasIniciales;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerAyuda implements Serializable {

    private String dateAsString;
    
    static Logger logger = Logger.getLogger(ControllerAyuda.class);

    public ControllerAyuda() {
    }

    @PostConstruct
    public void init() {
        Date fecha = new Date();
        DateFormat df = DateFormat.getDateInstance();
        dateAsString = df.format(fecha);
    }

    public void impirmir() {
        logger.error("date : " + dateAsString);
    }

    public String getDateAsString() {
        return dateAsString;
    }

    public void setDateAsString(String dateAsString) {
        this.dateAsString = dateAsString;
    }

}
