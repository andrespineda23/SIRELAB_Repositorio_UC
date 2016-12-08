/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.ayuda.AyudaReservaSala;
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
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesReservaSala2 implements Serializable {

    static Logger logger = Logger.getLogger(ControllerDetallesReservaSala.class);

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private ReservaSala reservaSala;

    public ControllerDetallesReservaSala2() {
    }

    public String cerrarPagina() {
        reservaSala = null;
        AyudaReservaSala.getInstance().setReservaSala(null);
        return "inicioestudiante";
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
        if (null == reservaSala) {
            reservaSala = AyudaReservaSala.getInstance().getReservaSala();
        }
        return reservaSala;
    }

    public void setReservaSala(ReservaSala reservaSala) {
        this.reservaSala = reservaSala;
    }

}
