/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaEquiposElementosBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.Proveedor;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.TipoActivo;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.text.DateFormat;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarEquipo implements Serializable {

    @EJB
    GestionarPlantaEquiposElementosBOInterface gestionarPlantaEquiposElementosBO;

    private List<Laboratorio> listaLaboratorio;
    private List<SalaLaboratorio> listaSalasLaboratorios;
    private List<ModuloLaboratorio> listaModulosLaboratorios;
    private List<TipoActivo> listaTiposActivos;
    private List<Proveedor> listaProveedores;
    private List<EstadoEquipo> listaEstadosEquipos;
    private boolean activarNuevoSalaLaboratorio;
    private boolean activarNuevoModuloLaboratorio;

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
    private boolean validacionesFecha, validacionesLaboratorio, validacionesSala, validacionesModulo;
    private boolean validacionesTipo, validacionesEstado, validacionesProveedor;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private boolean fechaDiferida;
    private MensajesConstantes constantes;

    public ControllerRegistrarEquipo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        activarAceptar = false;
        activarLimpiar = true;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        nuevoCostoAlquilerEquipo = "0";
        nuevoCostoInversionEquipo = "0";
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        nuevoFechaAdquisicionEquipo = df.format(now);
        validacionesCosto = true;
        validacionesEspecificacion = true;
        validacionesEstado = false;
        validacionesFecha = true;
        validacionesInventario = false;
        validacionesInversion = true;
        validacionesLaboratorio = false;
        validacionesMarca = true;
        validacionesModelo = true;
        validacionesModulo = false;
        validacionesNombre = false;
        validacionesProveedor = false;
        validacionesSala = false;
        validacionesSerie = true;
        validacionesTipo = false;
        //
        activarNuevoModuloLaboratorio = true;
        activarNuevoSalaLaboratorio = true;
        listaModulosLaboratorios = null;
        listaSalasLaboratorios = null;
        fechaDiferida = false;
        BasicConfigurator.configure();
    }

    public void validarNombreEquipo() {
        if (Utilidades.validarNulo(nuevoNombreEquipo) && (!nuevoNombreEquipo.isEmpty()) && (nuevoNombreEquipo.trim().length() > 0)) {
            int tam = nuevoNombreEquipo.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracterString(nuevoNombreEquipo)) {
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
                FacesContext.getCurrentInstance().addMessage("form:nuevoInventarioEquipo", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_CODIGO));
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
            if ((Utilidades.fechaIngresadaCorrecta(new Date(nuevoFechaAdquisicionEquipo))) == false) {
                validacionesFecha = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoFechaAdquisicionEquipo", new FacesMessage("La fecha ingresada se encuentra incorrecta. Formato (dd/mm/yyyy)"));
            } else {
                validacionesFecha = true;
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
                FacesContext.getCurrentInstance().addMessage("form:nuevoEspecificacionEquipo", new FacesMessage("El tamaño minimo permitido es 20 caracteres " + constantes.INVENTARIO_DESCRIP));
            }
        }
    }

    public void actualizarNuevoLaboratorio() {
        if (Utilidades.validarNulo(nuevoLaboratorio)) {
            nuevoSalaLaboratorioEquipo = null;
            listaSalasLaboratorios = gestionarPlantaEquiposElementosBO.consultarSalasLaboratorioActivosIDLaboratorio(nuevoLaboratorio.getIdlaboratorio());
            activarNuevoSalaLaboratorio = false;

            nuevoModuloLaboratorioEquipo = null;
            activarNuevoModuloLaboratorio = true;
            listaModulosLaboratorios = null;

            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            validacionesSala = false;
            validacionesModulo = false;

            nuevoSalaLaboratorioEquipo = null;
            activarNuevoSalaLaboratorio = true;
            listaSalasLaboratorios = null;

            nuevoModuloLaboratorioEquipo = null;
            activarNuevoModuloLaboratorio = true;
            listaModulosLaboratorios = null;
            FacesContext.getCurrentInstance().addMessage("form:nuevoLaboratorio", new FacesMessage("El laboratorio es obligatorio."));
        }
    }

    public void actualizarNuevoSalaLaboratorio() {
        if (Utilidades.validarNulo(nuevoSalaLaboratorioEquipo)) {
            nuevoModuloLaboratorioEquipo = null;
            listaModulosLaboratorios = gestionarPlantaEquiposElementosBO.consultarModulosLaboratorioActivosPorIDSalaLaboratorio(nuevoSalaLaboratorioEquipo.getIdsalalaboratorio());
            activarNuevoModuloLaboratorio = false;
            validacionesSala = true;
        } else {
            validacionesSala = false;
            validacionesModulo = false;
            nuevoModuloLaboratorioEquipo = null;
            activarNuevoModuloLaboratorio = true;
            listaModulosLaboratorios = null;
            FacesContext.getCurrentInstance().addMessage("form:nuevoSalaLaboratorioEquipo", new FacesMessage("La sala de laboratorio es obligatoria."));
        }
    }

    public void actualizarModulo() {
        if (Utilidades.validarNulo(nuevoModuloLaboratorioEquipo)) {
            validacionesModulo = true;
        } else {
            validacionesModulo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoModuloLaboratorioEquipo", new FacesMessage("El modulo/banco de trabajo es obligatorio."));
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesSerie == false) {
            retorno = false;
        }
        if (validacionesTipo == false) {
            retorno = false;
        }
        if (validacionesProveedor == false) {
            retorno = false;
        }
        if (validacionesSala == false) {
            retorno = false;
        }
        if (validacionesModulo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesMarca == false) {
            retorno = false;
        }
        if (validacionesModelo == false) {
            retorno = false;
        }
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesEspecificacion == false) {
            retorno = false;
        }
        if (validacionesEstado == false) {
            retorno = false;
        }
        if (validacionesFecha == false) {
            retorno = false;
        }
        if (validacionesInventario == false) {
            retorno = false;
        }
        if (validacionesInversion == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
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
                colorMensaje = "red";
                mensajeFormulario = "El codigo ya esta registrado con el edificio y laboratorio por area seleccionado.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
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
            equipoNuevo.setFechaadquisicion(new Date(nuevoFechaAdquisicionEquipo));
            equipoNuevo.setEspecificacionestecnicas(nuevoEspecificacionEquipo);
            equipoNuevo.setModulolaboratorio(nuevoModuloLaboratorioEquipo);
            equipoNuevo.setTipoactivo(nuevoTipoActivoEquipo);
            equipoNuevo.setEstadoequipo(nuevoEstadoEquipoEquipo);
            equipoNuevo.setProveedor(nuevoProveedorEquipo);
            gestionarPlantaEquiposElementosBO.crearNuevoEquipoElemento(equipoNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaEquipoElemento almacenarNuevoEquipoEnSistema:  " + e.toString());
            System.out.println("Error ControllerGestionarPlantaEquipoElemento almacenarNuevoEquipoEnSistema : " + e.toString());
        }
    }

    public void limpiarFormulario() {
        fechaDiferida = true;
        activarNuevoModuloLaboratorio = true;
        activarNuevoSalaLaboratorio = true;
        nuevoNombreEquipo = null;
        nuevoInventarioEquipo = null;
        nuevoModeloEquipo = null;
        nuevoMarcaEquipo = null;
        nuevoSerieEquipo = null;
        nuevoCostoAlquilerEquipo = null;
        nuevoEspecificacionEquipo = null;
        nuevoCostoInversionEquipo = null;
        Date now = new Date();
        DateFormat df = DateFormat.getDateInstance();
        nuevoFechaAdquisicionEquipo = df.format(now);
        nuevoLaboratorio = null;
        nuevoSalaLaboratorioEquipo = null;
        nuevoModuloLaboratorioEquipo = null;
        nuevoTipoActivoEquipo = null;
        nuevoEstadoEquipoEquipo = null;
        nuevoProveedorEquipo = null;
        //
        validacionesCosto = true;
        validacionesEspecificacion = true;
        validacionesEstado = false;
        validacionesFecha = true;
        validacionesInventario = false;
        validacionesInversion = true;
        validacionesLaboratorio = false;
        validacionesMarca = true;
        validacionesModelo = true;
        validacionesModulo = false;
        validacionesNombre = false;
        validacionesProveedor = false;
        validacionesSala = false;
        validacionesSerie = true;
        validacionesTipo = false;
    }

    public void limpiarRegistroEquipoElemento() {
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        activarAceptar = false;
        fechaDiferida = true;
        colorMensaje = "black";
        activarCasillas = false;
        activarNuevoModuloLaboratorio = true;
        activarNuevoSalaLaboratorio = true;
        nuevoNombreEquipo = null;
        nuevoInventarioEquipo = null;
        nuevoModeloEquipo = null;
        nuevoMarcaEquipo = null;
        nuevoSerieEquipo = null;
        nuevoEspecificacionEquipo = null;
        nuevoCostoAlquilerEquipo = "0";
        nuevoCostoInversionEquipo = "0";
        nuevoFechaAdquisicionEquipo = null;
        nuevoLaboratorio = null;
        nuevoSalaLaboratorioEquipo = null;
        nuevoModuloLaboratorioEquipo = null;
        nuevoTipoActivoEquipo = null;
        nuevoEstadoEquipoEquipo = null;
        nuevoProveedorEquipo = null;
        listaSalasLaboratorios = null;
        listaModulosLaboratorios = null;
        //
        validacionesCosto = true;
        validacionesEspecificacion = true;
        validacionesEstado = false;
        validacionesFecha = true;
        validacionesInventario = false;
        validacionesInversion = true;
        validacionesLaboratorio = false;
        validacionesMarca = true;
        validacionesModelo = true;
        validacionesModulo = false;
        validacionesNombre = false;
        validacionesProveedor = false;
        validacionesSala = false;
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
    public List<Laboratorio> getListaLaboratorio() {
        if (null == listaLaboratorio) {
            listaLaboratorio = gestionarPlantaEquiposElementosBO.consultarLaboratoriosActivosRegistrados();
        }
        return listaLaboratorio;
    }

    public void setListaLaboratorio(List<Laboratorio> listaLaboratorio) {
        this.listaLaboratorio = listaLaboratorio;
    }

    public List<SalaLaboratorio> getListaSalasLaboratorios() {
        return listaSalasLaboratorios;
    }

    public void setListaSalasLaboratorios(List<SalaLaboratorio> listaSalasLaboratorios) {
        this.listaSalasLaboratorios = listaSalasLaboratorios;
    }

    public List<ModuloLaboratorio> getListaModulosLaboratorios() {
        return listaModulosLaboratorios;
    }

    public void setListaModulosLaboratorios(List<ModuloLaboratorio> listaModulosLaboratorios) {
        this.listaModulosLaboratorios = listaModulosLaboratorios;
    }

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

    public boolean isActivarNuevoSalaLaboratorio() {
        return activarNuevoSalaLaboratorio;
    }

    public void setActivarNuevoSalaLaboratorio(boolean activarNuevoSalaLaboratorio) {
        this.activarNuevoSalaLaboratorio = activarNuevoSalaLaboratorio;
    }

    public boolean isActivarNuevoModuloLaboratorio() {
        return activarNuevoModuloLaboratorio;
    }

    public void setActivarNuevoModuloLaboratorio(boolean activarNuevoModuloLaboratorio) {
        this.activarNuevoModuloLaboratorio = activarNuevoModuloLaboratorio;
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

}
