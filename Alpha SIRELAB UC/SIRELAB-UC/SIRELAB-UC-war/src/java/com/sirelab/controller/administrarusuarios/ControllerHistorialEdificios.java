/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrarusuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresEdificioBOInterface;
import com.sirelab.entidades.AdministradorEdificio;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoPorEdificio;
import com.sirelab.entidades.Sede;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerHistorialEdificios implements Serializable {

    @EJB
    AdministrarAdministradoresEdificioBOInterface administrarAdministradoresEdificioBO;

    private AdministradorEdificio administradorEdificio;
    private BigInteger idAdministrador;
    private List<Sede> listaSedes;
    private List<Edificio> listaEdificios;
    private Sede sedeNuevo;
    private Edificio edificioNuevo;
    private boolean activarEdificio;
    private String colorMensaje, mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean validacionesSede, validacionesEdificio;
    private boolean activarNuevoRegistro;
    private List<EncargadoPorEdificio> listaEncargadosPorEdificio;
    private List<EncargadoPorEdificio> listaEncargadosPorEdificioTabla;
    private int posicionEncargadoPorEdificioTabla;
    private int tamTotalEncargadoPorEdificio;
    private boolean bloquearPagSigEncargadoPorEdificio, bloquearPagAntEncargadoPorEdificio;

    public ControllerHistorialEdificios() {
    }

    @PostConstruct
    public void init() {
        activarNuevoRegistro = true;
        validacionesEdificio = false;
        validacionesSede = false;
        colorMensaje = "black";
        mensajeFormulario = "NN";
        activarEdificio = true;
        BasicConfigurator.configure();
    }

    public void recibirIdAdministradorEdificio(BigInteger id) {
        this.idAdministrador = id;
        listaEncargadosPorEdificio = administrarAdministradoresEdificioBO.buscarEncargadosPorEdificioPorIDAdministrador(idAdministrador);
        listaEncargadosPorEdificioTabla = new ArrayList<EncargadoPorEdificio>();
        tamTotalEncargadoPorEdificio = listaEncargadosPorEdificio.size();
        posicionEncargadoPorEdificioTabla = 0;
        cargarDatosTablaEncargadoPorEdificio();
    }

    private void cargarDatosTablaEncargadoPorEdificio() {
        if (tamTotalEncargadoPorEdificio < 5) {
            for (int i = 0; i < tamTotalEncargadoPorEdificio; i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = true;
            bloquearPagAntEncargadoPorEdificio = true;
        } else {
            for (int i = 0; i < 5; i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = false;
            bloquearPagAntEncargadoPorEdificio = true;
        }
    }

    public void cargarPaginaSiguienteEncargadoPorEdificio() {
        listaEncargadosPorEdificioTabla = new ArrayList<EncargadoPorEdificio>();
        posicionEncargadoPorEdificioTabla = posicionEncargadoPorEdificioTabla + 5;
        listaEncargadosPorEdificio = listaEncargadosPorEdificioTabla;
        int diferencia = tamTotalEncargadoPorEdificio - posicionEncargadoPorEdificioTabla;
        if (diferencia > 5) {
            for (int i = posicionEncargadoPorEdificioTabla; i < (posicionEncargadoPorEdificioTabla + 5); i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = false;
            bloquearPagAntEncargadoPorEdificio = false;
        } else {
            for (int i = posicionEncargadoPorEdificioTabla; i < (posicionEncargadoPorEdificioTabla + diferencia); i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = true;
            bloquearPagAntEncargadoPorEdificio = false;
        }
    }

    public void cargarPaginaAnteriorEncargadoPorEdificio() {
        listaEncargadosPorEdificioTabla = new ArrayList<EncargadoPorEdificio>();
        posicionEncargadoPorEdificioTabla = posicionEncargadoPorEdificioTabla - 5;
        listaEncargadosPorEdificio = listaEncargadosPorEdificioTabla;
        int diferencia = tamTotalEncargadoPorEdificio - posicionEncargadoPorEdificioTabla;
        if (diferencia == tamTotalEncargadoPorEdificio) {
            for (int i = posicionEncargadoPorEdificioTabla; i < (posicionEncargadoPorEdificioTabla + 5); i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = false;
            bloquearPagAntEncargadoPorEdificio = true;
        } else {
            for (int i = posicionEncargadoPorEdificioTabla; i < (posicionEncargadoPorEdificioTabla + 5); i++) {
                listaEncargadosPorEdificioTabla.add(listaEncargadosPorEdificio.get(i));
            }
            bloquearPagSigEncargadoPorEdificio = false;
            bloquearPagAntEncargadoPorEdificio = false;
        }
    }

    public void actualizarNuevoRegistro() {
        if (activarNuevoRegistro == true) {
            listaEdificios = null;
            listaSedes = null;
            edificioNuevo = null;
            sedeNuevo = null;
            activarEdificio = true;
            validacionesEdificio = true;
            validacionesSede = true;
        } else {
            listaEdificios = null;
            listaSedes = administrarAdministradoresEdificioBO.obtenerListaSedesActivos();
            edificioNuevo = null;
            sedeNuevo = null;
            activarEdificio = true;
            validacionesEdificio = false;
            validacionesSede = false;
        }
    }

    public void actualizarSedes() {
        try {
            if (Utilidades.validarNulo(sedeNuevo)) {
                activarEdificio = false;
                edificioNuevo = null;
                listaEdificios = administrarAdministradoresEdificioBO.obtenerEdificiosActivosPorIDSede(sedeNuevo.getIdsede());
                validacionesSede = true;
                eliminarEdificiosRegistrados();
            } else {
                validacionesEdificio = false;
                validacionesSede = false;
                activarEdificio = true;
                edificioNuevo = null;
                listaEdificios = null;
                FacesContext.getCurrentInstance().addMessage("form:sedeAdministradorEdificio", new FacesMessage("El campo Sede es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerHistorialEdificios actualizarSedes:  " + e.toString());
            System.out.println("Error ControllerHistorialEdificios actualizarSedes : " + e.toString());
        }
    }

    private void eliminarEdificiosRegistrados() {
        if (Utilidades.validarNulo(listaEncargadosPorEdificio)) {
            for (int i = 0; i < listaEncargadosPorEdificio.size(); i++) {
                if (Utilidades.validarNulo(listaEdificios)) {
                    for (int j = 0; j < listaEdificios.size(); j++) {
                        if (listaEncargadosPorEdificio.get(i).getEdificio().getIdedificio().equals(listaEdificios.get(j).getIdedificio())) {
                            listaEdificios.remove(j);
                        }
                    }
                }
            }
        }
    }

    /**
     * Metodo encargado de actualizar los edificios y obtener la informacion de
     * las carreras relacionadas
     */
    public void actualizarEdificios() {
        try {
            if (Utilidades.validarNulo(edificioNuevo)) {
                validacionesEdificio = true;
            } else {
                validacionesEdificio = false;
                FacesContext.getCurrentInstance().addMessage("form:edificioNuevo", new FacesMessage("El campo Edificio es obligatorio."));
            }
        } catch (Exception e) {
            logger.error("Error ControllerHistorialEdificios actualizarEdificios:  " + e.toString());
            System.out.println("Error ControllerHistorialEdificios actualizarEdificios : " + e.toString());
        }
    }

    public void cancelarRegistro() {
        idAdministrador = null;
        administradorEdificio = null;
        listaSedes = null;
        listaEdificios = null;
        activarNuevoRegistro = true;
        validacionesEdificio = false;
        validacionesSede = false;
        sedeNuevo = null;
        edificioNuevo = null;
        activarEdificio = true;
        listaEncargadosPorEdificio = null;
        listaEncargadosPorEdificio = null;
        listaEncargadosPorEdificioTabla = null;
        tamTotalEncargadoPorEdificio = 0;
        posicionEncargadoPorEdificioTabla = 0;
        bloquearPagAntEncargadoPorEdificio = true;
        bloquearPagSigEncargadoPorEdificio = true;
    }

    public void limpiarDatos() {
        listaEncargadosPorEdificio = null;
        listaEncargadosPorEdificioTabla = null;
        tamTotalEncargadoPorEdificio = 0;
        posicionEncargadoPorEdificioTabla = 0;
        bloquearPagAntEncargadoPorEdificio = true;
        bloquearPagSigEncargadoPorEdificio = true;
        listaSedes = null;
        listaEdificios = null;
        activarNuevoRegistro = true;
        validacionesEdificio = false;
        validacionesSede = false;
        sedeNuevo = null;
        edificioNuevo = null;
        activarEdificio = true;
        listaEncargadosPorEdificio = null;
        recibirIdAdministradorEdificio(this.idAdministrador);
    }

    public void guardarCambiosPagina() {
        if (cantidadRegistrosAntiguos() == true) {
            if (validarRegistros() == true) {
                almacenarModificacionHistorial();
                limpiarDatos();
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El formulario de nuevo registro presenta errores.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Debe existir al menos un edificio asociado al personal.";
        }
    }

    private void almacenarModificacionHistorial() {
        if (activarNuevoRegistro == true) {
            for (int i = 0; i < listaEncargadosPorEdificio.size(); i++) {
                administrarAdministradoresEdificioBO.editarAsocioEncargadoEdificio(listaEncargadosPorEdificio.get(i));
            }
        } else {
            for (int i = 0; i < listaEncargadosPorEdificio.size(); i++) {
                listaEncargadosPorEdificio.get(i).setEstado(false);
                administrarAdministradoresEdificioBO.editarAsocioEncargadoEdificio(listaEncargadosPorEdificio.get(i));
            }
            administrarAdministradoresEdificioBO.registrarAsocioEncargadoEdificio(administradorEdificio, edificioNuevo);
        }
    }

    private boolean validarRegistros() {
        boolean retorno = true;
        if (validacionesEdificio == false) {
            retorno = false;
        }
        if (validacionesSede == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean cantidadRegistrosAntiguos() {
        boolean retorno = false;
        int contador = 0;
        for (int i = 0; i < listaEncargadosPorEdificio.size(); i++) {
            if (listaEncargadosPorEdificio.get(i).getEstado() == true) {
                contador++;
            }
        }
        if (contador == 1) {
            retorno = true;
        }
        return retorno;
    }

    //GET-SET
    public AdministradorEdificio getAdministradorEdificio() {
        return administradorEdificio;
    }

    public void setAdministradorEdificio(AdministradorEdificio administradorEdificio) {
        this.administradorEdificio = administradorEdificio;
    }

    public BigInteger getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(BigInteger idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public List<Edificio> getListaEdificios() {
        return listaEdificios;
    }

    public void setListaEdificios(List<Edificio> listaEdificios) {
        this.listaEdificios = listaEdificios;
    }

    public Sede getSedeNuevo() {
        return sedeNuevo;
    }

    public void setSedeNuevo(Sede sedeNuevo) {
        this.sedeNuevo = sedeNuevo;
    }

    public Edificio getEdificioNuevo() {
        return edificioNuevo;
    }

    public void setEdificioNuevo(Edificio edificioNuevo) {
        this.edificioNuevo = edificioNuevo;
    }

    public boolean isActivarEdificio() {
        return activarEdificio;
    }

    public void setActivarEdificio(boolean activarEdificio) {
        this.activarEdificio = activarEdificio;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public boolean isActivarNuevoRegistro() {
        return activarNuevoRegistro;
    }

    public void setActivarNuevoRegistro(boolean activarNuevoRegistro) {
        this.activarNuevoRegistro = activarNuevoRegistro;
    }

    public List<EncargadoPorEdificio> getListaEncargadosPorEdificio() {
        return listaEncargadosPorEdificio;
    }

    public void setListaEncargadosPorEdificio(List<EncargadoPorEdificio> listaEncargadosPorEdificio) {
        this.listaEncargadosPorEdificio = listaEncargadosPorEdificio;
    }

    public List<EncargadoPorEdificio> getListaEncargadosPorEdificioTabla() {
        return listaEncargadosPorEdificioTabla;
    }

    public void setListaEncargadosPorEdificioTabla(List<EncargadoPorEdificio> listaEncargadosPorEdificioTabla) {
        this.listaEncargadosPorEdificioTabla = listaEncargadosPorEdificioTabla;
    }

    public int getPosicionEncargadoPorEdificioTabla() {
        return posicionEncargadoPorEdificioTabla;
    }

    public void setPosicionEncargadoPorEdificioTabla(int posicionEncargadoPorEdificioTabla) {
        this.posicionEncargadoPorEdificioTabla = posicionEncargadoPorEdificioTabla;
    }

    public int getTamTotalEncargadoPorEdificio() {
        return tamTotalEncargadoPorEdificio;
    }

    public void setTamTotalEncargadoPorEdificio(int tamTotalEncargadoPorEdificio) {
        this.tamTotalEncargadoPorEdificio = tamTotalEncargadoPorEdificio;
    }

    public boolean isBloquearPagSigEncargadoPorEdificio() {
        return bloquearPagSigEncargadoPorEdificio;
    }

    public void setBloquearPagSigEncargadoPorEdificio(boolean bloquearPagSigEncargadoPorEdificio) {
        this.bloquearPagSigEncargadoPorEdificio = bloquearPagSigEncargadoPorEdificio;
    }

    public boolean isBloquearPagAntEncargadoPorEdificio() {
        return bloquearPagAntEncargadoPorEdificio;
    }

    public void setBloquearPagAntEncargadoPorEdificio(boolean bloquearPagAntEncargadoPorEdificio) {
        this.bloquearPagAntEncargadoPorEdificio = bloquearPagAntEncargadoPorEdificio;
    }

}
