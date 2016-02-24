/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerConsultarReservas implements Serializable {

    static Logger logger = Logger.getLogger(ControllerConsultarReservas.class);

    @EJB
    AdministrarReservasBOInterface administrarReservasBO;

    private Persona personEnLinea;
    private String tipoUsuarioEnLinea;
    private List<ReservaSala> listaReservasSala;
    private List<ReservaSala> listaReservasSalaTemporal;
    private List<ReservaSala> listaReservasSalaTabla;
    private int posicionReservaSalaTabla;
    private int tamTotalReservaSala;
    private boolean bloquearPagSigReservaSala, bloquearPagAntReservaSala;
    private String cantidadRegistros;
    private int tipoConsulta;

    private List<ReservaModuloLaboratorio> listaReservasModuloLaboratorio;
    private List<ReservaModuloLaboratorio> listaReservasModuloLaboratorioTemporal;
    private List<ReservaModuloLaboratorio> listaReservasModuloLaboratorioTabla;
    private int posicionReservaModuloLaboratorioTabla;
    private int tamTotalReservaModuloLaboratorio;
    private boolean bloquearPagSigReservaModuloLaboratorio, bloquearPagAntReservaModuloLaboratorio;

    public ControllerConsultarReservas() {
    }

    @PostConstruct
    public void init() {
    }

    public void obtenerInformacionReservasUsuario(String tipoUsuario, BigInteger registro) {
        tipoConsulta = 1;
        tipoUsuarioEnLinea = tipoUsuario;
        personEnLinea = administrarReservasBO.obtenerPersonaConsultarReservas(tipoUsuario, registro);
        listaReservasModuloLaboratorio = administrarReservasBO.consultarReservasModuloPorPersona(personEnLinea.getIdpersona());
        listaReservasSala = administrarReservasBO.consultarReservasSalaPorPersona(personEnLinea.getIdpersona());
        if (Utilidades.validarNulo(listaReservasSala)) {
            listaReservasSalaTemporal = listaReservasSala;
            cambiarTipoConsulta();
        } else {
            cargarDatosTablaReservaSala();
            posicionReservaSalaTabla = 0;
            tamTotalReservaSala = 0;
            bloquearPagAntReservaSala = true;
            bloquearPagSigReservaSala = true;
            posicionReservaSalaTabla = 0;
            cantidadRegistros = String.valueOf("0");
        }
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
        bloquearPagAntReservaSala = true;
        bloquearPagSigReservaSala = true;
        listaReservasSalaTabla = new ArrayList<ReservaSala>();
        tamTotalReservaSala = listaReservasSalaTemporal.size();
        posicionReservaSalaTabla = 0;
        cantidadRegistros = String.valueOf(tamTotalReservaSala);
        cargarDatosTablaReservaSala();
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
        
        tipoConsulta = 1;
        personEnLinea = null;
        listaReservasSala = null;
        listaReservasSalaTabla = null;
        listaReservasSalaTemporal = null;

        posicionReservaSalaTabla = 0;
        tamTotalReservaSala = 0;
        bloquearPagAntReservaSala = true;
        bloquearPagSigReservaSala = true;
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
                if (Utilidades.fechaDiferidaIngresadaCorrectaReservas(listaReservasModuloLaboratorio.get(i).getReserva().getFechareserva()) == false) {
                    listaReservasModuloLaboratorioTemporal.add(listaReservasModuloLaboratorio.get(i));
                }
            }
        }
        if (Utilidades.validarNulo(listaReservasSala)) {
            listaReservasSalaTemporal = new ArrayList<ReservaSala>();
            for (int i = 0; i < listaReservasSala.size(); i++) {
                if (Utilidades.fechaDiferidaIngresadaCorrectaReservas(listaReservasSala.get(i).getReserva().getFechareserva()) == false) {
                    listaReservasSalaTemporal.add(listaReservasSala.get(i));
                }
            }
        }
    }

    private void cargarDatosEnEspera() {
        if (Utilidades.validarNulo(listaReservasModuloLaboratorio)) {
            listaReservasModuloLaboratorioTemporal = new ArrayList<ReservaModuloLaboratorio>();
            for (int i = 0; i < listaReservasModuloLaboratorio.size(); i++) {
                if (Utilidades.fechaDiferidaIngresadaCorrectaReservas(listaReservasModuloLaboratorio.get(i).getReserva().getFechareserva()) == true) {
                    listaReservasModuloLaboratorioTemporal.add(listaReservasModuloLaboratorio.get(i));
                }
            }
        }
        if (Utilidades.validarNulo(listaReservasSala)) {
            listaReservasSalaTemporal = new ArrayList<ReservaSala>();
            for (int i = 0; i < listaReservasSala.size(); i++) {
                if (Utilidades.fechaDiferidaIngresadaCorrectaReservas(listaReservasSala.get(i).getReserva().getFechareserva()) == true) {
                    listaReservasSalaTemporal.add(listaReservasSala.get(i));
                }
            }
        }
    }

    public String paginaDetalleReserva() {
        limpiarDatos();
        return "detallesreservamodulo";
    }

   

    private void cargarDatosTablaReservaSala() {
        if (tamTotalReservaSala < 10) {
            for (int i = 0; i < tamTotalReservaSala; i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = true;
            bloquearPagAntReservaSala = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = true;
        }
    }

    public void cargarPaginaSiguienteReservaSala() {
        listaReservasSalaTabla = new ArrayList<ReservaSala>();
        posicionReservaSalaTabla = posicionReservaSalaTabla + 10;
        int diferencia = tamTotalReservaSala - posicionReservaSalaTabla;
        if (diferencia > 10) {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = false;
        } else {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + diferencia); i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = true;
            bloquearPagAntReservaSala = false;
        }
    }

    public void cargarPaginaAnteriorReservaSala() {
        listaReservasSalaTabla = new ArrayList<ReservaSala>();
        posicionReservaSalaTabla = posicionReservaSalaTabla - 10;
        int diferencia = tamTotalReservaSala - posicionReservaSalaTabla;
        if (diferencia == tamTotalReservaSala) {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = true;
        } else {
            for (int i = posicionReservaSalaTabla; i < (posicionReservaSalaTabla + 10); i++) {
                listaReservasSalaTabla.add(listaReservasSalaTemporal.get(i));
            }
            bloquearPagSigReservaSala = false;
            bloquearPagAntReservaSala = false;
        }
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

    public List<ReservaSala> getListaReservasSala() {
        return listaReservasSala;
    }

    public void setListaReservasSala(List<ReservaSala> listaReservasSala) {
        this.listaReservasSala = listaReservasSala;
    }

    public List<ReservaSala> getListaReservasSalaTemporal() {
        return listaReservasSalaTemporal;
    }

    public void setListaReservasSalaTemporal(List<ReservaSala> listaReservasSalaTemporal) {
        this.listaReservasSalaTemporal = listaReservasSalaTemporal;
    }

    public List<ReservaSala> getListaReservasSalaTabla() {
        return listaReservasSalaTabla;
    }

    public void setListaReservasSalaTabla(List<ReservaSala> listaReservasSalaTabla) {
        this.listaReservasSalaTabla = listaReservasSalaTabla;
    }

    public int getPosicionReservaSalaTabla() {
        return posicionReservaSalaTabla;
    }

    public void setPosicionReservaSalaTabla(int posicionReservaSalaTabla) {
        this.posicionReservaSalaTabla = posicionReservaSalaTabla;
    }

    public int getTamTotalReservaSala() {
        return tamTotalReservaSala;
    }

    public void setTamTotalReservaSala(int tamTotalReservaSala) {
        this.tamTotalReservaSala = tamTotalReservaSala;
    }

    public boolean isBloquearPagSigReservaSala() {
        return bloquearPagSigReservaSala;
    }

    public void setBloquearPagSigReservaSala(boolean bloquearPagSigReservaSala) {
        this.bloquearPagSigReservaSala = bloquearPagSigReservaSala;
    }

    public boolean isBloquearPagAntReservaSala() {
        return bloquearPagAntReservaSala;
    }

    public void setBloquearPagAntReservaSala(boolean bloquearPagAntReservaSala) {
        this.bloquearPagAntReservaSala = bloquearPagAntReservaSala;
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
    
}
