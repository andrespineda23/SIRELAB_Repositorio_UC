/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerConsultarReservasModulo implements Serializable {

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private Persona personEnLinea;
    private String tipoUsuarioEnLinea;
    private List<ReservaModuloLaboratorio> listaReservasModuloLaboratorio;
    private List<ReservaModuloLaboratorio> listaReservasModuloLaboratorioTemporal;
    private List<ReservaModuloLaboratorio> listaReservasModuloLaboratorioTabla;
    private int posicionReservaModuloLaboratorioTabla;
    private int tamTotalReservaModuloLaboratorio;
    private boolean bloquearPagSigReservaModuloLaboratorio, bloquearPagAntReservaModuloLaboratorio;
    private String cantidadRegistros;
    private int tipoConsulta;

    public ControllerConsultarReservasModulo() {
    }

    @PostConstruct
    public void init() {
    }

    public void obtenerInformacionReservasUsuario(String tipoUsuario, BigInteger registro) {
        tipoConsulta = 1;
        tipoUsuarioEnLinea = tipoUsuario;
        personEnLinea = administrarReservasBO.obtenerPersonaConsultarReservas(tipoUsuario, registro);
        listaReservasModuloLaboratorio = administrarReservasBO.consultarReservasModuloPorPersona(personEnLinea.getIdpersona());
        if (Utilidades.validarNulo(listaReservasModuloLaboratorio)) {
            listaReservasModuloLaboratorioTemporal = listaReservasModuloLaboratorio;
            cargarDatosEnEspera();
            inicializarDatosPaginacion();
        } else {
            posicionReservaModuloLaboratorioTabla = 0;
            tamTotalReservaModuloLaboratorio = 0;
            bloquearPagAntReservaModuloLaboratorio = true;
            bloquearPagSigReservaModuloLaboratorio = true;
            posicionReservaModuloLaboratorioTabla = 0;
            cantidadRegistros = String.valueOf("0");
            cargarDatosTablaReservaModuloLaboratorio();
        }
    }

    private void inicializarDatosPaginacion() {
        bloquearPagAntReservaModuloLaboratorio = true;
        bloquearPagSigReservaModuloLaboratorio = true;
        listaReservasModuloLaboratorioTabla = new ArrayList<ReservaModuloLaboratorio>();
        tamTotalReservaModuloLaboratorio = listaReservasModuloLaboratorioTemporal.size();
        posicionReservaModuloLaboratorioTabla = 0;
        cantidadRegistros = String.valueOf(tamTotalReservaModuloLaboratorio);
        cargarDatosTablaReservaModuloLaboratorio();
    }

    public String limpiarDatos() {
        tipoConsulta = 1;
        personEnLinea = null;
        listaReservasModuloLaboratorio = null;
        listaReservasModuloLaboratorioTabla = null;
        listaReservasModuloLaboratorioTemporal = null;

        posicionReservaModuloLaboratorioTabla = 0;
        tamTotalReservaModuloLaboratorio = 0;
        bloquearPagAntReservaModuloLaboratorio = true;
        bloquearPagSigReservaModuloLaboratorio = true;
        cantidadRegistros = "N/A";
        if ("DOCENTE".equalsIgnoreCase(tipoUsuarioEnLinea)) {
            return "iniciodocente";
        } else {
            if ("ESTUDIANTE".equalsIgnoreCase(tipoUsuarioEnLinea)) {
                return "inicioestudiante";
            } else {
                return "iniciodocente";
            }
        }
    }

    private void cargarDatosTablaReservaModuloLaboratorio() {
        if (tamTotalReservaModuloLaboratorio < 10) {
            for (int i = 0; i < tamTotalReservaModuloLaboratorio; i++) {
                listaReservasModuloLaboratorioTabla.add(listaReservasModuloLaboratorioTemporal.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = true;
            bloquearPagAntReservaModuloLaboratorio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaReservasModuloLaboratorioTabla.add(listaReservasModuloLaboratorioTemporal.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = false;
            bloquearPagAntReservaModuloLaboratorio = true;
        }
    }

    public void cargarPaginaSiguienteReservaModuloLaboratorio() {
        listaReservasModuloLaboratorioTabla = new ArrayList<ReservaModuloLaboratorio>();
        posicionReservaModuloLaboratorioTabla = posicionReservaModuloLaboratorioTabla + 10;
        int diferencia = tamTotalReservaModuloLaboratorio - posicionReservaModuloLaboratorioTabla;
        if (diferencia > 10) {
            for (int i = posicionReservaModuloLaboratorioTabla; i < (posicionReservaModuloLaboratorioTabla + 10); i++) {
                listaReservasModuloLaboratorioTabla.add(listaReservasModuloLaboratorioTemporal.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = false;
            bloquearPagAntReservaModuloLaboratorio = false;
        } else {
            for (int i = posicionReservaModuloLaboratorioTabla; i < (posicionReservaModuloLaboratorioTabla + diferencia); i++) {
                listaReservasModuloLaboratorioTabla.add(listaReservasModuloLaboratorioTemporal.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = true;
            bloquearPagAntReservaModuloLaboratorio = false;
        }
    }

    public void cargarPaginaAnteriorReservaModuloLaboratorio() {
        listaReservasModuloLaboratorioTabla = new ArrayList<ReservaModuloLaboratorio>();
        posicionReservaModuloLaboratorioTabla = posicionReservaModuloLaboratorioTabla - 10;
        int diferencia = tamTotalReservaModuloLaboratorio - posicionReservaModuloLaboratorioTabla;
        if (diferencia == tamTotalReservaModuloLaboratorio) {
            for (int i = posicionReservaModuloLaboratorioTabla; i < (posicionReservaModuloLaboratorioTabla + 10); i++) {
                listaReservasModuloLaboratorioTabla.add(listaReservasModuloLaboratorioTemporal.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = false;
            bloquearPagAntReservaModuloLaboratorio = true;
        } else {
            for (int i = posicionReservaModuloLaboratorioTabla; i < (posicionReservaModuloLaboratorioTabla + 10); i++) {
                listaReservasModuloLaboratorioTabla.add(listaReservasModuloLaboratorioTemporal.get(i));
            }
            bloquearPagSigReservaModuloLaboratorio = false;
            bloquearPagAntReservaModuloLaboratorio = false;
        }
    }

    public void cambiarTipoConsulta() {
        if (tipoConsulta == 1) {
            cargarDatosEnEspera();
            inicializarDatosPaginacion();
        } else {
            cargarDatosHistoricos();
            inicializarDatosPaginacion();
        }
    }

    private void cargarDatosHistoricos() {
        if (Utilidades.validarNulo(listaReservasModuloLaboratorio)) {
            listaReservasModuloLaboratorioTemporal = new ArrayList<ReservaModuloLaboratorio>();
            for (int i = 0; i < listaReservasModuloLaboratorio.size(); i++) {
                if (Utilidades.fechaIngresadaCorrecta(listaReservasModuloLaboratorio.get(i).getReserva().getFechareserva().toString()) == false) {
                    listaReservasModuloLaboratorioTemporal.add(listaReservasModuloLaboratorio.get(i));
                }
            }
        }
    }

    private void cargarDatosEnEspera() {
        if (Utilidades.validarNulo(listaReservasModuloLaboratorio)) {
            listaReservasModuloLaboratorioTemporal = new ArrayList<ReservaModuloLaboratorio>();
            for (int i = 0; i < listaReservasModuloLaboratorio.size(); i++) {
                if (Utilidades.fechaIngresadaCorrecta(listaReservasModuloLaboratorio.get(i).getReserva().getFechareserva().toString()) == true) {
                    listaReservasModuloLaboratorioTemporal.add(listaReservasModuloLaboratorio.get(i));
                }
            }
        }
    }

    public String paginaDetalleReserva() {
        limpiarDatos();
        return "detallesreservamodulo";
    }

    //GET-SET
    public Persona getPersonEnLinea() {
        return personEnLinea;
    }

    public void setPersonEnLinea(Persona personEnLinea) {
        this.personEnLinea = personEnLinea;
    }

    public String getTipoUsuarioEnLinea() {
        return tipoUsuarioEnLinea;
    }

    public void setTipoUsuarioEnLinea(String tipoUsuarioEnLinea) {
        this.tipoUsuarioEnLinea = tipoUsuarioEnLinea;
    }

    public List<ReservaModuloLaboratorio> getListaReservasModuloLaboratorio() {
        return listaReservasModuloLaboratorio;
    }

    public void setListaReservasModuloLaboratorio(List<ReservaModuloLaboratorio> listaReservasModuloLaboratorio) {
        this.listaReservasModuloLaboratorio = listaReservasModuloLaboratorio;
    }

    public List<ReservaModuloLaboratorio> getListaReservasModuloLaboratorioTemporal() {
        return listaReservasModuloLaboratorioTemporal;
    }

    public void setListaReservasModuloLaboratorioTemporal(List<ReservaModuloLaboratorio> listaReservasModuloLaboratorioTemporal) {
        this.listaReservasModuloLaboratorioTemporal = listaReservasModuloLaboratorioTemporal;
    }

    public List<ReservaModuloLaboratorio> getListaReservasModuloLaboratorioTabla() {
        return listaReservasModuloLaboratorioTabla;
    }

    public void setListaReservasModuloLaboratorioTabla(List<ReservaModuloLaboratorio> listaReservasModuloLaboratorioTabla) {
        this.listaReservasModuloLaboratorioTabla = listaReservasModuloLaboratorioTabla;
    }

    public int getPosicionReservaModuloLaboratorioTabla() {
        return posicionReservaModuloLaboratorioTabla;
    }

    public void setPosicionReservaModuloLaboratorioTabla(int posicionReservaModuloLaboratorioTabla) {
        this.posicionReservaModuloLaboratorioTabla = posicionReservaModuloLaboratorioTabla;
    }

    public int getTamTotalReservaModuloLaboratorio() {
        return tamTotalReservaModuloLaboratorio;
    }

    public void setTamTotalReservaModuloLaboratorio(int tamTotalReservaModuloLaboratorio) {
        this.tamTotalReservaModuloLaboratorio = tamTotalReservaModuloLaboratorio;
    }

    public boolean isBloquearPagSigReservaModuloLaboratorio() {
        return bloquearPagSigReservaModuloLaboratorio;
    }

    public void setBloquearPagSigReservaModuloLaboratorio(boolean bloquearPagSigReservaModuloLaboratorio) {
        this.bloquearPagSigReservaModuloLaboratorio = bloquearPagSigReservaModuloLaboratorio;
    }

    public boolean isBloquearPagAntReservaModuloLaboratorio() {
        return bloquearPagAntReservaModuloLaboratorio;
    }

    public void setBloquearPagAntReservaModuloLaboratorio(boolean bloquearPagAntReservaModuloLaboratorio) {
        this.bloquearPagAntReservaModuloLaboratorio = bloquearPagAntReservaModuloLaboratorio;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public int getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(int tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

}
