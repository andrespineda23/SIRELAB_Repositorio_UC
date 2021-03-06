/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.AyudaFechaReserva;
import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaEquiposElementosBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.Proveedor;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.TipoActivo;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ControllerRegistrarEquipoCargado implements Serializable {

    @EJB
    GestionarPlantaEquiposElementosBOInterface gestionarPlantaEquiposElementosBO;

    private List<TipoActivo> listaTiposActivos;
    private List<Proveedor> listaProveedores;
    private List<EstadoEquipo> listaEstadosEquipos;

    private String nuevoNombreEquipo, nuevoInventarioEquipo, nuevoMarcaEquipo, nuevoModeloEquipo, nuevoSerieEquipo;
    private String nuevoCostoAlquilerEquipo, nuevoEspecificacionEquipo, nuevoCostoInversionEquipo;
    private String nuevoFechaAdquisicionEquipo;
    private Laboratorio nuevoLaboratorio;
    private SalaLaboratorio nuevoSalaLaboratorioEquipo;
    private ModuloLaboratorio nuevoModuloLaboratorioEquipo;
    private TipoActivo nuevoTipoActivoEquipo;
    private EstadoEquipo nuevoEstadoEquipoEquipo;
    private Proveedor nuevoProveedorEquipo;
    //
    private boolean validacionesNombre, validacionesInventario, validacionesMarca, validacionesModelo, validacionesSerie;
    private boolean validacionesCosto, validacionesInversion, validacionesEspecificacion;
    private boolean validacionesFecha;
    private boolean validacionesTipo, validacionesEstado, validacionesProveedor;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean nuevoTipo;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private boolean fechaDiferida;
    private MensajesConstantes constantes;
    private String mensajeError;

    public ControllerRegistrarEquipoCargado() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarAceptar = false;
        mensajeError = "";
        activarLimpiar = true;
        fechaDiferida = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        nuevoCostoAlquilerEquipo = "0";
        nuevoCostoInversionEquipo = "0";
        nuevoTipo = false;
        nuevoFechaAdquisicionEquipo = null;
        validacionesCosto = true;
        validacionesEspecificacion = true;
        validacionesEstado = false;
        validacionesFecha = true;
        validacionesInventario = false;
        validacionesInversion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        validacionesNombre = false;
        validacionesProveedor = false;
        validacionesSerie = true;
        validacionesTipo = false;
        //

        BasicConfigurator.configure();
    }

    public void cargarInformacionModuloLaboratorio(BigInteger modulo) {
        nuevoModuloLaboratorioEquipo = gestionarPlantaEquiposElementosBO.obtenerModuloLaboratorioPorID(modulo);
        if (null != nuevoModuloLaboratorioEquipo) {
            nuevoSalaLaboratorioEquipo = nuevoModuloLaboratorioEquipo.getSalalaboratorio();
            nuevoLaboratorio = nuevoModuloLaboratorioEquipo.getSalalaboratorio().getLaboratorio();
        }
    }

    public void validarNombreEquipo() {
        if (Utilidades.validarNulo(nuevoNombreEquipo) && (!nuevoNombreEquipo.isEmpty()) && (nuevoNombreEquipo.trim().length() > 0)) {
            int tam = nuevoNombreEquipo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoNombreEquipo)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombreEquipo", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombreEquipo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombreEquipo", new FacesMessage("El nombre es obligatorio. " + constantes.INVENTARIO_NOMBRE));
        }

    }

    public void validarInventarioEquipo() {
        if (Utilidades.validarNulo(nuevoInventarioEquipo) && (!nuevoInventarioEquipo.isEmpty()) && (nuevoInventarioEquipo.trim().length() > 0)) {
            int tam = nuevoInventarioEquipo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoInventarioEquipo)) {
                    validacionesInventario = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoInventarioEquipo", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.INVENTARIO_CODIGO));
                } else {
                    validacionesInventario = true;
                }
            } else {
                validacionesInventario = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoInventarioEquipo", new FacesMessage("\"El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_CODIGO));
            }
        } else {
            validacionesInventario = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoInventarioEquipo", new FacesMessage("El codigo es obligatorio. " + constantes.INVENTARIO_CODIGO));
        }
    }

    public void validarMarcaEquipo() {
        if (Utilidades.validarNulo(nuevoMarcaEquipo) && (!nuevoMarcaEquipo.isEmpty()) && (nuevoMarcaEquipo.trim().length() > 0)) {
            int tam = nuevoMarcaEquipo.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoMarcaEquipo)) {
                    validacionesMarca = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoMarcaEquipo", new FacesMessage("La marca ingresada es incorrecta. " + constantes.INVENTARIO_MARCA));
                } else {
                    validacionesMarca = true;
                }
            } else {
                validacionesMarca = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoMarcaEquipo", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_MARCA));
            }
        } else {
            validacionesMarca = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoMarcaEquipo", new FacesMessage("La marca es obligatoria. " + constantes.INVENTARIO_MARCA));
        }
    }

    public void actualizarEstadoEquipo() {
        if (Utilidades.validarNulo(nuevoEstadoEquipoEquipo)) {
            validacionesEstado = true;
        } else {
            validacionesEstado = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoEstadoEquipoEquipo", new FacesMessage("El estado del equipo es obligatorio."));
        }
    }

    public void actualizarTipoActivo() {
        if (Utilidades.validarNulo(nuevoTipoActivoEquipo)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoTipoActivoEquipo", new FacesMessage("El tipo activo es obligatorio."));
        }
    }

    public void actualizarProveedor() {
        if (Utilidades.validarNulo(nuevoProveedorEquipo)) {
            validacionesProveedor = true;
        } else {
            validacionesProveedor = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoProveedorEquipo", new FacesMessage("El proveedor es obligatorio."));
        }
    }

    public void validarModeloEquipo() {
        if (Utilidades.validarNulo(nuevoModeloEquipo) && (!nuevoModeloEquipo.isEmpty()) && (nuevoModeloEquipo.trim().length() > 0)) {
            int tam = nuevoModeloEquipo.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoModeloEquipo)) {
                    validacionesModelo = true;
                } else {
                    validacionesModelo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoModeloEquipo", new FacesMessage("El modelo se encuentra incorrecto. " + constantes.INVENTARIO_MODELO));
                }
            } else {
                validacionesModelo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoModeloEquipo", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_MODELO));
            }
        }
    }

    public void validarSerieEquipo() {
        if (Utilidades.validarNulo(nuevoSerieEquipo) && (!nuevoSerieEquipo.isEmpty()) && (nuevoSerieEquipo.trim().length() > 0)) {
            int tam = nuevoSerieEquipo.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoSerieEquipo)) {
                    validacionesSerie = true;
                } else {
                    validacionesSerie = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoSerieEquipo", new FacesMessage("La serie se encuentra incorrecta. " + constantes.INVENTARIO_SERIAL));
                }
            } else {
                validacionesSerie = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoSerieEquipo", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_SERIAL));
            }
        }
    }

    public void validarCostoAlquilerEquipo() {
        if (Utilidades.validarNulo(nuevoCostoAlquilerEquipo) && (!nuevoCostoAlquilerEquipo.isEmpty()) && (nuevoCostoAlquilerEquipo.trim().length() > 0)) {
            if (Utilidades.isNumber(nuevoCostoAlquilerEquipo)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoAlquilerEquipo", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
            }
        }
    }

    public void validarInversionEquipo() {
        if (Utilidades.validarNulo(nuevoCostoInversionEquipo) && (!nuevoCostoInversionEquipo.isEmpty()) && (nuevoCostoInversionEquipo.trim().length() > 0)) {
            if ((Utilidades.isNumber(nuevoCostoInversionEquipo)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCostoInversionEquipo", new FacesMessage("El valor de inversión se encuentra incorrecto. " + constantes.INVENTARIO_COST_INV));
            } else {
                validacionesInversion = true;
            }
        }
    }

    public void validarFechaEquipo() {

        if (Utilidades.validarNulo(nuevoFechaAdquisicionEquipo)) {
            try {
                Date fechaValidacion = new Date(nuevoFechaAdquisicionEquipo);
                validacionesFecha = true;
            } catch (Exception e) {
                validacionesFecha = true;
                FacesContext.getCurrentInstance().addMessage("form:nuevoFechaAdquisicionEquipo", new FacesMessage("La fecha ingresada fue incorrecta. El sistema asigno la fecha del día"));
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                nuevoFechaAdquisicionEquipo = formateador.format(new Date());
            }
        }
    }

    public void validarEspecificacionEquipo() {
        if (Utilidades.validarNulo(nuevoEspecificacionEquipo) && (!nuevoEspecificacionEquipo.isEmpty()) && (nuevoEspecificacionEquipo.trim().length() > 0)) {
            int tam = nuevoEspecificacionEquipo.length();
            if (tam >= 20) {
                if ((Utilidades.validarCaracteresAlfaNumericos(nuevoEspecificacionEquipo)) == false) {
                    validacionesEspecificacion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoEspecificacionEquipo", new FacesMessage("La especificación ingresada se encuentra incorrecta. " + constantes.INVENTARIO_DESCRIP));
                } else {
                    validacionesEspecificacion = true;
                }
            } else {
                validacionesEspecificacion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoEspecificacionEquipo", new FacesMessage("El tamaño minimo es 20 caracteres. " + constantes.INVENTARIO_DESCRIP));
            }
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesSerie == false) {
            mensajeError = mensajeError + " - Serie - ";
            retorno = false;
        }
        if (validacionesTipo == false) {
            mensajeError = mensajeError + " - Tipo Activo - ";
            retorno = false;
        }
        if (validacionesProveedor == false) {
            mensajeError = mensajeError + " - Proveedor - ";
            retorno = false;
        }
        if (validacionesNombre == false) {
            mensajeError = mensajeError + " - Nombre - ";
            retorno = false;
        }
        if (validacionesMarca == false) {
            mensajeError = mensajeError + " - Marca - ";
            retorno = false;
        }
        if (validacionesModelo == false) {
            mensajeError = mensajeError + " - Modelo - ";
            retorno = false;
        }
        if (validacionesCosto == false) {
            mensajeError = mensajeError + " - Costo Alquiler - ";
            retorno = false;
        }
        if (validacionesFecha == false) {
            mensajeError = mensajeError + " - Fecha - ";
            retorno = false;
        }
        if (validacionesEspecificacion == false) {
            mensajeError = mensajeError + " - Especificación - ";
            retorno = false;
        }
        if (validacionesEstado == false) {
            mensajeError = mensajeError + " - Estado - ";
            retorno = false;
        }
        if (validacionesInventario == false) {
            mensajeError = mensajeError + " - Codigo - ";
            retorno = false;
        }
        if (validacionesInversion == false) {
            mensajeError = mensajeError + " - Costo Inversión - ";
            retorno = false;
        }
        return retorno;
    }

    private boolean validarCodigoRepetido() {
        boolean retorno = true;
        EquipoElemento equipo = gestionarPlantaEquiposElementosBO.obtenerEquipoElementoPorCodigoYModulo(nuevoInventarioEquipo, nuevoModuloLaboratorioEquipo.getIdmodulolaboratorio());
        if (null != equipo) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void registrarNuevoModulo() {
        if (validarResultadosValidacion() == true) {
            if (validarCodigoRepetido() == true) {
                almacenarNuevoEquipoEnSistema();
                limpiarFormulario();
                activarLimpiar = false;
                activarAceptar = true;
                activarCasillas = true;
                colorMensaje = "green";
                mensajeFormulario = "El formulario ha sido ingresado con exito.";
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El codigo ya esta registrado en el sistema.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
        }
    }

    public void almacenarNuevoEquipoEnSistema() {
        try {
            EquipoElemento equipoNuevo = new EquipoElemento();
            equipoNuevo.setNombreequipo(nuevoNombreEquipo);
            equipoNuevo.setInventarioequipo(nuevoInventarioEquipo);
            equipoNuevo.setMarcaequipo(nuevoMarcaEquipo);
            equipoNuevo.setSeriequipo(nuevoSerieEquipo);
            equipoNuevo.setModeloequipo(nuevoModeloEquipo);
            equipoNuevo.setPrivado(nuevoTipo);
            equipoNuevo.setCantidadequipo(Integer.valueOf("1"));
            if (Utilidades.validarNulo(nuevoCostoInversionEquipo) && (!nuevoCostoInversionEquipo.isEmpty()) && (nuevoCostoInversionEquipo.trim().length() > 0)) {
                equipoNuevo.setCostoadquisicion(Integer.valueOf(nuevoCostoInversionEquipo));
            } else {
                equipoNuevo.setCostoadquisicion(Integer.valueOf("0"));
            }
            if (Utilidades.validarNulo(nuevoCostoAlquilerEquipo) && (!nuevoCostoAlquilerEquipo.isEmpty()) && (nuevoCostoAlquilerEquipo.trim().length() > 0)) {
                equipoNuevo.setCostoalquiler(Integer.valueOf(nuevoCostoAlquilerEquipo));
            } else {
                equipoNuevo.setCostoalquiler(Integer.valueOf("0"));
            }
            if (Utilidades.validarNulo(nuevoFechaAdquisicionEquipo)) {
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = formateador.parse(nuevoFechaAdquisicionEquipo);
                formateador.format(fecha);
                equipoNuevo.setFechaadquisicion(fecha);
            } else {
                equipoNuevo.setFechaadquisicion(null);
            }
            equipoNuevo.setEspecificacionestecnicas(nuevoEspecificacionEquipo);
            equipoNuevo.setModulolaboratorio(nuevoModuloLaboratorioEquipo);
            equipoNuevo.setTipoactivo(nuevoTipoActivoEquipo);
            equipoNuevo.setEstadoequipo(nuevoEstadoEquipoEquipo);
            equipoNuevo.setProveedor(nuevoProveedorEquipo);
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            gestionarPlantaEquiposElementosBO.crearNuevoEquipoElemento(equipoNuevo, usuarioLoginSistema.getUserUsuario());
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaEquipoElemento almacenarNuevoEquipoEnSistema : " + e.toString(), e);
        }
    }

    public void limpiarFormulario() {
        nuevoNombreEquipo = null;
        nuevoInventarioEquipo = null;
        mensajeError = "";
        nuevoModeloEquipo = null;
        nuevoMarcaEquipo = null;
        nuevoSerieEquipo = null;
        nuevoCostoAlquilerEquipo = null;
        nuevoEspecificacionEquipo = null;
        listaProveedores = null;
        listaTiposActivos = null;
        listaEstadosEquipos = null;
        nuevoCostoInversionEquipo = null;
        Date fecha = new Date();
        DateFormat df = DateFormat.getDateInstance();
        nuevoFechaAdquisicionEquipo = df.format(fecha);
        nuevoTipoActivoEquipo = null;
        nuevoEstadoEquipoEquipo = null;
        nuevoProveedorEquipo = null;
        //
        fechaDiferida = true;
        nuevoTipo = false;
        validacionesCosto = true;
        validacionesEspecificacion = true;
        validacionesEstado = false;
        validacionesFecha = true;
        validacionesInventario = false;
        validacionesInversion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        validacionesNombre = false;
        validacionesProveedor = false;
        validacionesSerie = true;
        validacionesTipo = false;
        nuevoFechaAdquisicionEquipo = null;
    }

    public void limpiarRegistroEquipoElemento() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        nuevoNombreEquipo = null;
        nuevoInventarioEquipo = null;
        nuevoModeloEquipo = null;
        nuevoMarcaEquipo = null;
        nuevoSerieEquipo = null;
        nuevoTipo = false;
        nuevoEspecificacionEquipo = null;
        nuevoCostoAlquilerEquipo = "0";
        nuevoCostoInversionEquipo = "0";
        nuevoFechaAdquisicionEquipo = null;
        nuevoTipoActivoEquipo = null;
        nuevoEstadoEquipoEquipo = null;
        nuevoProveedorEquipo = null;
        listaProveedores = null;
        listaTiposActivos = null;
        listaEstadosEquipos = null;
        //
        validacionesCosto = true;
        validacionesEspecificacion = true;
        validacionesEstado = false;
        validacionesFecha = true;
        mensajeError = "";
        validacionesInventario = false;
        validacionesInversion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        validacionesNombre = false;
        fechaDiferida = true;
        validacionesProveedor = false;
        validacionesSerie = true;
        validacionesTipo = false;
        mensajeFormulario = "";
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarAceptar = false;
        activarLimpiar = true;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    //GET - SET
    public List<TipoActivo> getListaTiposActivos() {
        if (null == listaTiposActivos) {
            listaTiposActivos = gestionarPlantaEquiposElementosBO.consultarTiposActivosRegistrador();
        }
        return listaTiposActivos;
    }

    public void setListaTiposActivos(List<TipoActivo> listaTiposActivos) {
        this.listaTiposActivos = listaTiposActivos;
    }

    public List<Proveedor> getListaProveedores() {
        if (null == listaProveedores) {
            listaProveedores = gestionarPlantaEquiposElementosBO.consultarProveedoresRegistrados();
        }
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public List<EstadoEquipo> getListaEstadosEquipos() {
        if (null == listaEstadosEquipos) {
            listaEstadosEquipos = gestionarPlantaEquiposElementosBO.consultarEstadosEquiposRegistrados();
        }
        return listaEstadosEquipos;
    }

    public void setListaEstadosEquipos(List<EstadoEquipo> listaEstadosEquipos) {
        this.listaEstadosEquipos = listaEstadosEquipos;
    }

    public String getNuevoNombreEquipo() {
        return nuevoNombreEquipo;
    }

    public void setNuevoNombreEquipo(String nuevoNombreEquipo) {
        this.nuevoNombreEquipo = nuevoNombreEquipo;
    }

    public String getNuevoInventarioEquipo() {
        return nuevoInventarioEquipo;
    }

    public void setNuevoInventarioEquipo(String nuevoInventarioEquipo) {
        this.nuevoInventarioEquipo = nuevoInventarioEquipo;
    }

    public String getNuevoMarcaEquipo() {
        return nuevoMarcaEquipo;
    }

    public void setNuevoMarcaEquipo(String nuevoMarcaEquipo) {
        this.nuevoMarcaEquipo = nuevoMarcaEquipo;
    }

    public String getNuevoModeloEquipo() {
        return nuevoModeloEquipo;
    }

    public void setNuevoModeloEquipo(String nuevoModeloEquipo) {
        this.nuevoModeloEquipo = nuevoModeloEquipo;
    }

    public String getNuevoSerieEquipo() {
        return nuevoSerieEquipo;
    }

    public void setNuevoSerieEquipo(String nuevoSerieEquipo) {
        this.nuevoSerieEquipo = nuevoSerieEquipo;
    }

    public String getNuevoCostoAlquilerEquipo() {
        return nuevoCostoAlquilerEquipo;
    }

    public void setNuevoCostoAlquilerEquipo(String nuevoCostoAlquilerEquipo) {
        this.nuevoCostoAlquilerEquipo = nuevoCostoAlquilerEquipo;
    }

    public String getNuevoEspecificacionEquipo() {
        return nuevoEspecificacionEquipo;
    }

    public void setNuevoEspecificacionEquipo(String nuevoEspecificacionEquipo) {
        this.nuevoEspecificacionEquipo = nuevoEspecificacionEquipo;
    }

    public String getNuevoCostoInversionEquipo() {
        return nuevoCostoInversionEquipo;
    }

    public void setNuevoCostoInversionEquipo(String nuevoCostoInversionEquipo) {
        this.nuevoCostoInversionEquipo = nuevoCostoInversionEquipo;
    }

    public String getNuevoFechaAdquisicionEquipo() {
        return nuevoFechaAdquisicionEquipo;
    }

    public void setNuevoFechaAdquisicionEquipo(String nuevoFechaAdquisicionEquipo) {
        this.nuevoFechaAdquisicionEquipo = nuevoFechaAdquisicionEquipo;
    }

    public Laboratorio getNuevoLaboratorio() {
        return nuevoLaboratorio;
    }

    public void setNuevoLaboratorio(Laboratorio nuevoLaboratorio) {
        this.nuevoLaboratorio = nuevoLaboratorio;
    }

    public SalaLaboratorio getNuevoSalaLaboratorioEquipo() {
        return nuevoSalaLaboratorioEquipo;
    }

    public void setNuevoSalaLaboratorioEquipo(SalaLaboratorio nuevoSalaLaboratorioEquipo) {
        this.nuevoSalaLaboratorioEquipo = nuevoSalaLaboratorioEquipo;
    }

    public ModuloLaboratorio getNuevoModuloLaboratorioEquipo() {
        return nuevoModuloLaboratorioEquipo;
    }

    public void setNuevoModuloLaboratorioEquipo(ModuloLaboratorio nuevoModuloLaboratorioEquipo) {
        this.nuevoModuloLaboratorioEquipo = nuevoModuloLaboratorioEquipo;
    }

    public TipoActivo getNuevoTipoActivoEquipo() {
        return nuevoTipoActivoEquipo;
    }

    public void setNuevoTipoActivoEquipo(TipoActivo nuevoTipoActivoEquipo) {
        this.nuevoTipoActivoEquipo = nuevoTipoActivoEquipo;
    }

    public EstadoEquipo getNuevoEstadoEquipoEquipo() {
        return nuevoEstadoEquipoEquipo;
    }

    public void setNuevoEstadoEquipoEquipo(EstadoEquipo nuevoEstadoEquipoEquipo) {
        this.nuevoEstadoEquipoEquipo = nuevoEstadoEquipoEquipo;
    }

    public Proveedor getNuevoProveedorEquipo() {
        return nuevoProveedorEquipo;
    }

    public void setNuevoProveedorEquipo(Proveedor nuevoProveedorEquipo) {
        this.nuevoProveedorEquipo = nuevoProveedorEquipo;
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

    public boolean isActivarAceptar() {
        return activarAceptar;
    }

    public void setActivarAceptar(boolean activarAceptar) {
        this.activarAceptar = activarAceptar;
    }

    public boolean isFechaDiferida() {
        return fechaDiferida;
    }

    public void setFechaDiferida(boolean fechaDiferida) {
        this.fechaDiferida = fechaDiferida;
    }

    public boolean isNuevoTipo() {
        return nuevoTipo;
    }

    public void setNuevoTipo(boolean nuevoTipo) {
        this.nuevoTipo = nuevoTipo;
    }

}
