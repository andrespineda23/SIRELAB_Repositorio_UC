/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.AsociacionServicios;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalasBOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
import com.sirelab.entidades.ServiciosSala;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarSala implements Serializable {

    @EJB
    GestionarPlantaSalasBOInterface gestionarPlantaSalasBO;

    private String nuevoNombreSala, nuevoCodigoSala, nuevoDescripcionSala, nuevoUbicacionSala, nuevoCapacidadSala, nuevoCostoSala, nuevoInversionSala;
    private Laboratorio nuevoLaboratorioSala;
    private Sede nuevoSedeSala;
    private Edificio nuevoEdificioSala;
    private boolean activarNuevoEdificio;
    private boolean costoAlquilerDigitado;
    private List<Sede> listaSedes;
    private List<Edificio> listaEdificios;
    private List<AsociacionServicios> listaAsociacionServicios;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesDescripcion, validacionesUbicacion;
    private boolean validacionesCapacidad, validacionesCosto, validacionesInversion;
    private boolean validacionesSede, validacionesEdificio;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private boolean nuevoTipo;
    private BigInteger idLaboratorio;
    private MensajesConstantes constantes;

    public ControllerRegistrarSala() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        nuevoTipo = false;
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        costoAlquilerDigitado = false;
        activarCasillas = false;
        mensajeFormulario = "N/A";
        validacionesCosto = false;
        validacionesCapacidad = false;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesEdificio = false;
        validacionesInversion = true;
        validacionesNombre = false;
        validacionesSede = false;
        validacionesUbicacion = false;
        //
        activarNuevoEdificio = true;
        nuevoNombreSala = null;
        nuevoCodigoSala = null;
        nuevoUbicacionSala = null;
        nuevoDescripcionSala = null;
        nuevoCostoSala = null;
        nuevoCapacidadSala = null;
        nuevoInversionSala = "0";
        nuevoSedeSala = null;
        nuevoEdificioSala = null;
        listaEdificios = null;
        BasicConfigurator.configure();
    }

    private void cargarAsociacionServicios() {
        List<ServiciosSala> lista = gestionarPlantaSalasBO.consultarServiciosSalaActivosRegistradas();
        listaAsociacionServicios = new ArrayList<AsociacionServicios>();
        if (null != lista) {
            for (int i = 0; i < lista.size(); i++) {
                AsociacionServicios obj = new AsociacionServicios(null, false);
                obj.setServicio(lista.get(i));
                listaAsociacionServicios.add(obj);
            }
        }
    }

    public void recibirIdLaboratorio(BigInteger laboratorio) {
        idLaboratorio = laboratorio;
        nuevoLaboratorioSala = gestionarPlantaSalasBO.obtenerLaboratorioPorId(idLaboratorio);
        cargarAsociacionServicios();
    }

    public void validarNombreSala() {
        if (Utilidades.validarNulo(nuevoNombreSala) && (!nuevoNombreSala.isEmpty()) && (nuevoNombreSala.trim().length() > 0)) {
            int tam = nuevoNombreSala.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoNombreSala)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombreSala", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombreSala", new FacesMessage("El tamaño minimo es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombreSala", new FacesMessage("El nombre es obligatorio. " + constantes.INVENTARIO_NOMBRE));
        }

    }

    public void validarCodigoSala() {
        if (Utilidades.validarNulo(nuevoCodigoSala) && (!nuevoCodigoSala.isEmpty()) && (nuevoCodigoSala.trim().length() > 0)) {
            int tam = nuevoCodigoSala.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoCodigoSala)) {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoSala", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.INVENTARIO_CODIGO));
                } else {
                    validacionesCodigo = true;
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoSala", new FacesMessage("El tamaño minimo es 4 caracteres. " + constantes.INVENTARIO_CODIGO));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigoSala", new FacesMessage("El codigo es obligatorio. " + constantes.INVENTARIO_CODIGO));
        }
    }

    public void validarUbicacionSala() {
        if (Utilidades.validarNulo(nuevoUbicacionSala) && (!nuevoUbicacionSala.isEmpty()) && (nuevoUbicacionSala.trim().length() > 0)) {
            int tam = nuevoUbicacionSala.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoUbicacionSala)) {
                    validacionesUbicacion = true;
                } else {
                    validacionesUbicacion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoUbicacionSala", new FacesMessage("La ubicación se encuentra incorrecta. " + constantes.INVENTARIO_UBICACION));
                }
            } else {
                validacionesUbicacion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoUbicacionSala", new FacesMessage("El tamaño minimo es 2 caracteres. " + constantes.INVENTARIO_UBICACION));
            }
        } else {
            validacionesUbicacion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoUbicacionSala", new FacesMessage("La ubicación es obligatoria. " + constantes.INVENTARIO_UBICACION));
        }
    }

    public void validarCostoAlquilerSala() {
        if (Utilidades.validarNulo(nuevoCostoSala) && (!nuevoCostoSala.isEmpty()) && (nuevoCostoSala.trim().length() > 0)) {
            if (Utilidades.isNumber(nuevoCostoSala)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoSala", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
            }
        } else {
            validacionesCosto = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCostoSala", new FacesMessage("El costo es obligatorio. " + constantes.INVENTARIO_COST_ALQ));
        }
    }

    public void validarCapacidadSala() {
        if (Utilidades.validarNulo(nuevoCapacidadSala) && (!nuevoCapacidadSala.isEmpty()) && (nuevoCapacidadSala.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoCapacidadSala)) == false) {
                validacionesCapacidad = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCapacidadSala", new FacesMessage("La capacidad ingresada se encuentra incorrecta. " + constantes.INVENTARIO_CAPACIDAD));
            } else {
                validacionesCapacidad = true;
            }
        } else {
            validacionesCapacidad = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCapacidadSala", new FacesMessage("La capacidad es obligatoria. " + constantes.INVENTARIO_CAPACIDAD));
        }
    }

    public void validarDescripcionSala() {
        if (Utilidades.validarNulo(nuevoDescripcionSala) && (!nuevoDescripcionSala.isEmpty()) && (nuevoDescripcionSala.trim().length() > 0)) {
            int tam = nuevoDescripcionSala.length();
            if (tam >= 20) {
                if ((Utilidades.validarCaracteresAlfaNumericos(nuevoDescripcionSala)) == false) {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionSala", new FacesMessage("La descripción se encuentra incorrecta. " + constantes.INVENTARIO_DESCRIP));
                } else {
                    validacionesDescripcion = true;
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionSala", new FacesMessage("El tamaño minimo es 20 caracteres. " + constantes.INVENTARIO_DESCRIP));
            }
        } else {
            validacionesDescripcion = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcionSala", new FacesMessage("La descripción es obligatoria. " + constantes.INVENTARIO_DESCRIP));
        }
    }

    public void validarInversionSala() {
        if (Utilidades.validarNulo(nuevoInversionSala) && (!nuevoInversionSala.isEmpty()) && (nuevoInversionSala.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoInversionSala)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoInversionSala", new FacesMessage("El valor de inversión se encuentra incorrecto. " + constantes.INVENTARIO_COST_INV));
            } else {
                validacionesInversion = true;
            }
        }
    }

    public void actualizarNuevoSede() {
        if (Utilidades.validarNulo(nuevoSedeSala)) {
            nuevoEdificioSala = null;
            listaEdificios = gestionarPlantaSalasBO.consultarEdificiosActivosPorIDSede(nuevoSedeSala.getIdsede());
            activarNuevoEdificio = false;
            validacionesSede = true;
        } else {
            validacionesSede = false;
            validacionesEdificio = false;
            nuevoEdificioSala = null;
            listaEdificios = null;
            activarNuevoEdificio = true;
            FacesContext.getCurrentInstance().addMessage("form:nuevoSedeSala", new FacesMessage("La sede es obligatoria."));
        }
    }

    public void actualizarEdificios() {
        if (Utilidades.validarNulo(nuevoEdificioSala)) {
            validacionesEdificio = true;
        } else {
            validacionesEdificio = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoEdificioSala", new FacesMessage("El edificio es obligatorio."));
        }
    }

    public void cambiarCostoAlquilerDigitado() {
        if (costoAlquilerDigitado == true) {
            nuevoCostoSala = "0";
            validacionesCosto = true;
        } else {
            nuevoCostoSala = "0";
            validacionesCosto = false;
        }
    }

    public String limpiarRegistroSalaLaboratorio() {
        activarNuevoEdificio = true;
        activarAceptar = false;
        costoAlquilerDigitado = false;
        nuevoNombreSala = null;
        nuevoCodigoSala = null;
        nuevoUbicacionSala = null;
        nuevoTipo = false;
        nuevoDescripcionSala = null;
        nuevoCostoSala = null;
        nuevoCapacidadSala = null;
        nuevoInversionSala = "0";
        nuevoSedeSala = null;
        nuevoLaboratorioSala = null;
        nuevoEdificioSala = null;
        listaEdificios = null;
        listaSedes = null;
        //
        validacionesCosto = false;
        validacionesCapacidad = false;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesEdificio = false;
        validacionesInversion = true;
        validacionesNombre = false;
        validacionesSede = false;
        validacionesUbicacion = false;
        mensajeFormulario = "N/A";
        listaAsociacionServicios = null;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        idLaboratorio = null;
        return "administrarlaboratorios";
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesCapacidad == false) {
            retorno = false;
        }
        if (validacionesCodigo == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesEdificio == false) {
            retorno = false;
        }
        if (validacionesInversion == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesSede == false) {
            retorno = false;
        }
        if (validacionesUbicacion == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        SalaLaboratorio sala = gestionarPlantaSalasBO.obtenerSalaLaboratorioPorCodigoEdificioyLaboratorio(nuevoCodigoSala, nuevoEdificioSala.getIdedificio(), nuevoLaboratorioSala.getIdlaboratorio());
        if (null != sala) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarAsociacionServicio() {
        boolean retorno = false;
        if (null != listaAsociacionServicios) {
            int contador = 0;
            for (int i = 0; i < listaAsociacionServicios.size(); i++) {
                if (listaAsociacionServicios.get(i).isActivo() == true) {
                    contador++;
                }
            }
            if (contador > 0) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void registrarNuevoSala() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                if (validarAsociacionServicio() == true) {
                    almacenaNuevoSalaEnSistema();
                    limpiarFormulario();
                    activarLimpiar = false;
                    activarAceptar = true;
                    activarCasillas = true;
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "Para ingresar la sala es necesario que exista una asociacion con un servicio.";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "El codigo ya esta registrado con el edificio y laboratorio por area seleccionado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void limpiarFormulario() {
        activarNuevoEdificio = true;
        nuevoNombreSala = null;
        nuevoCodigoSala = null;
        nuevoUbicacionSala = null;
        nuevoTipo = false;
        costoAlquilerDigitado = false;
        nuevoDescripcionSala = null;
        nuevoCostoSala = null;
        nuevoCapacidadSala = null;
        nuevoInversionSala = "0";
        nuevoSedeSala = null;
        nuevoEdificioSala = null;
        listaEdificios = null;
        //
        validacionesCosto = false;
        validacionesCapacidad = false;
        validacionesCodigo = false;
        validacionesDescripcion = false;
        validacionesEdificio = false;
        validacionesInversion = true;
        validacionesNombre = false;
        validacionesSede = false;
        validacionesUbicacion = false;
        cargarAsociacionServicios();
    }

    public void almacenaNuevoSalaEnSistema() {
        try {
            SalaLaboratorio salaNuevo = new SalaLaboratorio();
            salaNuevo.setNombresala(nuevoNombreSala);
            salaNuevo.setCodigosala(nuevoCodigoSala);
            salaNuevo.setSalaprivada(nuevoTipo);
            salaNuevo.setCostosaladigitado(costoAlquilerDigitado);
            salaNuevo.setPisoubicacion(nuevoUbicacionSala);
            salaNuevo.setDescripcionsala(nuevoDescripcionSala);
            salaNuevo.setCostoalquiler(Long.parseLong(nuevoCostoSala));
            salaNuevo.setEstadosala(true);
            salaNuevo.setCapacidadsala(Integer.parseInt(nuevoCapacidadSala));
            if (Utilidades.validarNulo(nuevoInversionSala) && (!nuevoInversionSala.isEmpty()) && (nuevoInversionSala.trim().length() > 0)) {
                salaNuevo.setValorinversion(new BigInteger(nuevoInversionSala));
            } else {
                salaNuevo.setValorinversion(new BigInteger("0"));
            }
            salaNuevo.setEdificio(nuevoEdificioSala);
            salaNuevo.setLaboratorio(nuevoLaboratorioSala);
            List<ServiciosSala> lista = obtenerServiciosARegistrar();
            gestionarPlantaSalasBO.crearNuevaSalaLaboratorio(salaNuevo, lista);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaSalas almacenaNuevoSalaEnSistema:  " + e.toString(),e);
            logger.error("Error ControllerGestionarPlantaSalas almacenaNuevoSalaEnSistema : " + e.toString(),e);
        }
    }

    private List<ServiciosSala> obtenerServiciosARegistrar() {
        List<ServiciosSala> lista = new ArrayList<ServiciosSala>();
        for (int i = 0; i < listaAsociacionServicios.size(); i++) {
            if (listaAsociacionServicios.get(i).isActivo() == true) {
                lista.add(listaAsociacionServicios.get(i).getServicio());
            }
        }
        return lista;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        activarAceptar = false;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET-SET    
    public String getNuevoNombreSala() {
        return nuevoNombreSala;
    }

    public void setNuevoNombreSala(String nuevoNombreSala) {
        this.nuevoNombreSala = nuevoNombreSala;
    }

    public String getNuevoCodigoSala() {
        return nuevoCodigoSala;
    }

    public void setNuevoCodigoSala(String nuevoCodigoSala) {
        this.nuevoCodigoSala = nuevoCodigoSala;
    }

    public String getNuevoDescripcionSala() {
        return nuevoDescripcionSala;
    }

    public void setNuevoDescripcionSala(String nuevoDescripcionSala) {
        this.nuevoDescripcionSala = nuevoDescripcionSala;
    }

    public String getNuevoUbicacionSala() {
        return nuevoUbicacionSala;
    }

    public void setNuevoUbicacionSala(String nuevoUbicacionSala) {
        this.nuevoUbicacionSala = nuevoUbicacionSala;
    }

    public String getNuevoCapacidadSala() {
        return nuevoCapacidadSala;
    }

    public void setNuevoCapacidadSala(String nuevoCapacidadSala) {
        this.nuevoCapacidadSala = nuevoCapacidadSala;
    }

    public String getNuevoCostoSala() {
        return nuevoCostoSala;
    }

    public void setNuevoCostoSala(String nuevoCostoSala) {
        this.nuevoCostoSala = nuevoCostoSala;
    }

    public String getNuevoInversionSala() {
        return nuevoInversionSala;
    }

    public void setNuevoInversionSala(String nuevoInversionSala) {
        this.nuevoInversionSala = nuevoInversionSala;
    }

    public Sede getNuevoSedeSala() {
        return nuevoSedeSala;
    }

    public void setNuevoSedeSala(Sede nuevoSedeSala) {
        this.nuevoSedeSala = nuevoSedeSala;
    }

    public Edificio getNuevoEdificioSala() {
        return nuevoEdificioSala;
    }

    public void setNuevoEdificioSala(Edificio nuevoEdificioSala) {
        this.nuevoEdificioSala = nuevoEdificioSala;
    }

    public boolean isActivarNuevoEdificio() {
        return activarNuevoEdificio;
    }

    public void setActivarNuevoEdificio(boolean activarNuevoEdificio) {
        this.activarNuevoEdificio = activarNuevoEdificio;
    }

    public List<Sede> getListaSedes() {
        if (listaSedes == null) {
            listaSedes = gestionarPlantaSalasBO.consultarSedesActivosRegistradas();
        }
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

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isActivarLimpiar() {
        return activarLimpiar;
    }

    public void setActivarLimpiar(boolean activarLimpiar) {
        this.activarLimpiar = activarLimpiar;
    }

    public Laboratorio getNuevoLaboratorioSala() {
        return nuevoLaboratorioSala;
    }

    public void setNuevoLaboratorioSala(Laboratorio nuevoLaboratorioSala) {
        this.nuevoLaboratorioSala = nuevoLaboratorioSala;
    }

    public boolean isActivarAceptar() {
        return activarAceptar;
    }

    public void setActivarAceptar(boolean activarAceptar) {
        this.activarAceptar = activarAceptar;
    }

    public boolean isNuevoTipo() {
        return nuevoTipo;
    }

    public void setNuevoTipo(boolean nuevoTipo) {
        this.nuevoTipo = nuevoTipo;
    }

    public List<AsociacionServicios> getListaAsociacionServicios() {
        return listaAsociacionServicios;
    }

    public void setListaAsociacionServicios(List<AsociacionServicios> listaAsociacionServicios) {
        this.listaAsociacionServicios = listaAsociacionServicios;
    }

    public boolean isCostoAlquilerDigitado() {
        return costoAlquilerDigitado;
    }

    public void setCostoAlquilerDigitado(boolean costoAlquilerDigitado) {
        this.costoAlquilerDigitado = costoAlquilerDigitado;
    }

}
