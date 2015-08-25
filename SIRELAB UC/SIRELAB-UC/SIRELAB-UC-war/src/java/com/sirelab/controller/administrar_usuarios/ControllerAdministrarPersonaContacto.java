/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarPersonasContactoBOInterface;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.PersonaContacto;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarPersonaContacto implements Serializable {

    @EJB
    AdministrarPersonasContactoBOInterface administrarPersonasContactoBO;

    private ConvenioPorEntidad convenioPorEntidad;
    private List<PersonaContacto> listaPersonasContacto;
    private List<PersonaContacto> listaPersonasContactoTabla;
    private int posicionPersonaContactoTabla;
    private int tamTotalPersonaContacto;
    private boolean bloquearPagSigPersonaContacto, bloquearPagAntPersonaContacto;
    //
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarPersonaContacto() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        listaPersonasContacto = null;
        listaPersonasContactoTabla = null;
        posicionPersonaContactoTabla = 0;
        tamTotalPersonaContacto = 0;
        bloquearPagAntPersonaContacto = true;
        bloquearPagSigPersonaContacto = true;
        BasicConfigurator.configure();
    }

    public void recibirconvenioPorEntidad(BigInteger idRegistro) {
        convenioPorEntidad = administrarPersonasContactoBO.buscarConvenioPorEntidadPorId(idRegistro);
        if (null != convenioPorEntidad) {
            buscarPersonasContacto();
        }
    }

    private void buscarPersonasContacto() {
        try {
            listaPersonasContacto = administrarPersonasContactoBO.buscarPersonasContactoPorConvenioEntidad(convenioPorEntidad.getIdconvenioporentidad());
            if (listaPersonasContacto != null) {
                if (listaPersonasContacto.size() > 0) {
                    listaPersonasContactoTabla = new ArrayList<PersonaContacto>();
                    tamTotalPersonaContacto = listaPersonasContacto.size();
                    posicionPersonaContactoTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalPersonaContacto);
                    cargarDatosTablaPersonaContacto();
                } else {
                    listaPersonasContactoTabla = null;
                    tamTotalPersonaContacto = 0;
                    posicionPersonaContactoTabla = 0;
                    bloquearPagAntPersonaContacto = true;
                    cantidadRegistros = String.valueOf(tamTotalPersonaContacto);
                    bloquearPagSigPersonaContacto = true;
                }
            } else {
                listaPersonasContactoTabla = null;
                tamTotalPersonaContacto = 0;
                posicionPersonaContactoTabla = 0;
                bloquearPagAntPersonaContacto = true;
                bloquearPagSigPersonaContacto = true;
                cantidadRegistros = String.valueOf(tamTotalPersonaContacto);
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarPersonasContacto buscarPersonasContacto:  " + e.toString());
            System.out.println("Error ControllerAdministrarPersonasContacto buscarPersonasContacto : " + e.toString());
        }
    }

    private void cargarDatosTablaPersonaContacto() {
        if (tamTotalPersonaContacto < 10) {
            for (int i = 0; i < tamTotalPersonaContacto; i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = true;
            bloquearPagAntPersonaContacto = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = false;
            bloquearPagAntPersonaContacto = true;
        }
    }

    public void cargarPaginaSiguientePersonaContacto() {
        listaPersonasContactoTabla = new ArrayList<PersonaContacto>();
        posicionPersonaContactoTabla = posicionPersonaContactoTabla + 10;
        int diferencia = tamTotalPersonaContacto - posicionPersonaContactoTabla;
        if (diferencia > 10) {
            for (int i = posicionPersonaContactoTabla; i < (posicionPersonaContactoTabla + 10); i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = false;
            bloquearPagAntPersonaContacto = false;
        } else {
            for (int i = posicionPersonaContactoTabla; i < (posicionPersonaContactoTabla + diferencia); i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = true;
            bloquearPagAntPersonaContacto = false;
        }
    }

    public void cargarPaginaAnteriorPersonaContacto() {
        listaPersonasContactoTabla = new ArrayList<PersonaContacto>();
        posicionPersonaContactoTabla = posicionPersonaContactoTabla - 10;
        int diferencia = tamTotalPersonaContacto - posicionPersonaContactoTabla;
        if (diferencia == tamTotalPersonaContacto) {
            for (int i = posicionPersonaContactoTabla; i < (posicionPersonaContactoTabla + 10); i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = false;
            bloquearPagAntPersonaContacto = true;
        } else {
            for (int i = posicionPersonaContactoTabla; i < (posicionPersonaContactoTabla + 10); i++) {
                listaPersonasContactoTabla.add(listaPersonasContacto.get(i));
            }
            bloquearPagSigPersonaContacto = false;
            bloquearPagAntPersonaContacto = false;
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public String limpiarProcesoBusqueda() {
        listaPersonasContacto = null;
        listaPersonasContactoTabla = null;
        bloquearPagAntPersonaContacto = true;
        bloquearPagSigPersonaContacto = true;
        posicionPersonaContactoTabla = 0;
        tamTotalPersonaContacto = 0;
        cantidadRegistros = "N/A";
        return "administrar_conveniosporentidad";
    }

    public String paginaNuevoRegistro() {
        limpiarDatos();
        return "registrar_personacontacto";
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un entidadexterna
     */
    public String verDetallesPersonaContacto() {
        limpiarDatos();
        return "detalles_personacontacto";
    }

    public void limpiarDatos() {
        listaPersonasContacto = null;
        listaPersonasContactoTabla = null;
        bloquearPagAntPersonaContacto = true;
        bloquearPagSigPersonaContacto = true;
        posicionPersonaContactoTabla = 0;
        tamTotalPersonaContacto = 0;
        cantidadRegistros = "N/A";
    }

    // GET - SET
    public List<PersonaContacto> getListaPersonasContacto() {
        return listaPersonasContacto;
    }

    public void setListaPersonasContacto(List<PersonaContacto> listaPersonasContacto) {
        this.listaPersonasContacto = listaPersonasContacto;
    }

    public List<PersonaContacto> getListaPersonasContactoTabla() {
        return listaPersonasContactoTabla;
    }

    public void setListaPersonasContactoTabla(List<PersonaContacto> listaPersonasContactoTabla) {
        this.listaPersonasContactoTabla = listaPersonasContactoTabla;
    }

    public boolean isBloquearPagSigPersonaContacto() {
        return bloquearPagSigPersonaContacto;
    }

    public void setBloquearPagSigPersonaContacto(boolean bloquearPagSigPersonaContacto) {
        this.bloquearPagSigPersonaContacto = bloquearPagSigPersonaContacto;
    }

    public boolean isBloquearPagAntPersonaContacto() {
        return bloquearPagAntPersonaContacto;
    }

    public void setBloquearPagAntPersonaContacto(boolean bloquearPagAntPersonaContacto) {
        this.bloquearPagAntPersonaContacto = bloquearPagAntPersonaContacto;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public ConvenioPorEntidad getConvenioPorEntidad() {
        return convenioPorEntidad;
    }

    public void setConvenioPorEntidad(ConvenioPorEntidad convenioPorEntidad) {
        this.convenioPorEntidad = convenioPorEntidad;
    }

}
