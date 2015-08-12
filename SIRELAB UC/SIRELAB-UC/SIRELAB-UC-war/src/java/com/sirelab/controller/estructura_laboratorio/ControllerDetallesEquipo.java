/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaEquiposElementosBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.Proveedor;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.TipoActivo;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerDetallesEquipo implements Serializable {

    @EJB
    GestionarPlantaEquiposElementosBOInterface gestionarPlantaEquiposElementosBO;

    private EquipoElemento equipoElementoDetalles;
    private BigInteger idEquipoElemento;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean visibleGuardar;
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private LaboratoriosPorAreas laboratorioPorAreaEquipoElemento;
    private List<SalaLaboratorio> listaSalasLaboratorio;
    private SalaLaboratorio salaEquipoElemento;
    private boolean activarSalaLaboratorio;
    private List<ModuloLaboratorio> listaModulosLaboratorio;
    private ModuloLaboratorio moduloEquipoElemento;
    private boolean activarModuloLaboratorio;
    private List<TipoActivo> listaTiposActivos;
    private TipoActivo tipoActivoEquipoElemento;
    private List<EstadoEquipo> listaEstadosEquipos;
    private EstadoEquipo estadoEquipoElemento;
    private List<Proveedor> listaProveedores;
    private Proveedor proveedorEquipoElemento;
    private String nombreEquipoElemento, inventarioEquipoElemento;
    private String marcaEquipoElemento, modeloEquipoElemento, serieEquipoElemento;
    private String alquilerEquipoElemento, cantidadEquipoElemento, inversionEquipoElemento;
    private String especificacionEquipoElemento;
    private Date fechaEquipoElemento;
    //
    //
    private boolean validacionesNombre, validacionesInventario, validacionesMarca, validacionesModelo, validacionesSerie;
    private boolean validacionesCosto, validacionesInversion, validacionesEspecificacion;
    private boolean validacionesFecha, validacionesLaboratorio, validacionesSala, validacionesModulo;
    private boolean validacionesTipo, validacionesEstado, validacionesProveedor;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;

    public ControllerDetallesEquipo() {
    }

    @PostConstruct
    public void init() {
        colorMensaje = "black";
        validacionesCosto = true;
        validacionesEspecificacion = true;
        validacionesEstado = true;
        validacionesFecha = true;
        validacionesInventario = true;
        validacionesInversion = true;
        validacionesLaboratorio = true;
        validacionesMarca = true;
        validacionesModelo = true;
        validacionesModulo = true;
        validacionesNombre = true;
        validacionesProveedor = true;
        validacionesSala = true;
        validacionesSerie = true;
        validacionesTipo = true;
        mensajeFormulario = "N/A";
        //
        activarSalaLaboratorio = true;
        activarModuloLaboratorio = true;
        //
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) httpServletRequest.getSession().getAttribute("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            disabledEditar = false;
        }
        BasicConfigurator.configure();
    }

    public void recibirIDEquiposElementoDetalles(BigInteger idEquipoElemento) {
        this.idEquipoElemento = idEquipoElemento;
        equipoElementoDetalles = gestionarPlantaEquiposElementosBO.obtenerEquipoElementoPorIDEquipoElemento(idEquipoElemento);
        asignarValoresVariablesEquipoElemento();
    }

    public void asignarValoresVariablesEquipoElemento() {
        nombreEquipoElemento = equipoElementoDetalles.getNombreequipo();
        inventarioEquipoElemento = equipoElementoDetalles.getInventarioequipo();
        alquilerEquipoElemento = equipoElementoDetalles.getCostoalquiler().toString();
        cantidadEquipoElemento = String.valueOf(equipoElementoDetalles.getCantidadequipo());
        inversionEquipoElemento = equipoElementoDetalles.getCostoadquisicion().toString();

        marcaEquipoElemento = equipoElementoDetalles.getMarcaequipo();
        modeloEquipoElemento = equipoElementoDetalles.getModeloequipo();
        serieEquipoElemento = equipoElementoDetalles.getSeriequipo();

        especificacionEquipoElemento = equipoElementoDetalles.getEspecificacionestecnicas();
        fechaEquipoElemento = equipoElementoDetalles.getFechaadquisicion();

        laboratorioPorAreaEquipoElemento = equipoElementoDetalles.getModulolaboratorio().getSalalaboratorio().getLaboratoriosporareas();
        salaEquipoElemento = equipoElementoDetalles.getModulolaboratorio().getSalalaboratorio();
        moduloEquipoElemento = equipoElementoDetalles.getModulolaboratorio();

        listaLaboratoriosPorAreas = gestionarPlantaEquiposElementosBO.consultarLaboratoriosPorAreasRegistradas();
        listaSalasLaboratorio = gestionarPlantaEquiposElementosBO.consultarSalasLaboratorioPorIDAreaProfundizacion(laboratorioPorAreaEquipoElemento.getIdlaboratoriosporareas());
        listaModulosLaboratorio = gestionarPlantaEquiposElementosBO.consultarModulosLaboratorioPorIDSalaLaboratorio(salaEquipoElemento.getIdsalalaboratorio());

        tipoActivoEquipoElemento = equipoElementoDetalles.getTipoactivo();
        estadoEquipoElemento = equipoElementoDetalles.getEstadoequipo();
        proveedorEquipoElemento = equipoElementoDetalles.getProveedor();

        listaTiposActivos = gestionarPlantaEquiposElementosBO.consultarTiposActivosRegistrador();
        listaEstadosEquipos = gestionarPlantaEquiposElementosBO.consultarEstadosEquiposRegistrados();
        listaProveedores = gestionarPlantaEquiposElementosBO.consultarProveedoresRegistrados();

    }

    public void activarEditarRegistro() {
        activarEditar = false;
        disabledEditar = true;
        modificacionRegistro = false;
        visibleGuardar = true;
        activarModuloLaboratorio = false;
        activarSalaLaboratorio = false;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
    }

    public String restaurarInformacionEquipoElemento() {
        equipoElementoDetalles = new EquipoElemento();
        equipoElementoDetalles = gestionarPlantaEquiposElementosBO.obtenerEquipoElementoPorIDEquipoElemento(idEquipoElemento);
        asignarValoresVariablesEquipoElemento();
        activarEditar = true;
        disabledEditar = false;
        modificacionRegistro = false;
        visibleGuardar = false;
        activarSalaLaboratorio = true;
        activarModuloLaboratorio = true;
        //
        validacionesCosto = true;
        validacionesEspecificacion = true;
        validacionesEstado = true;
        validacionesFecha = true;
        validacionesInventario = true;
        validacionesInversion = true;
        validacionesLaboratorio = true;
        validacionesMarca = true;
        validacionesModelo = true;
        validacionesModulo = true;
        validacionesNombre = true;
        validacionesProveedor = true;
        validacionesSala = true;
        validacionesSerie = true;
        validacionesTipo = true;
        colorMensaje = "black";
        mensajeFormulario = "N/A";
        //
        return "administrar_equipos";
    }

    public void actualizarLaboratoriosAreasProfundizacion() {
        try {
            if (Utilidades.validarNulo(laboratorioPorAreaEquipoElemento)) {
                activarSalaLaboratorio = false;
                salaEquipoElemento = null;
                listaSalasLaboratorio = gestionarPlantaEquiposElementosBO.consultarSalasLaboratorioPorIDLaboratorioAreaProfundizacion(laboratorioPorAreaEquipoElemento.getIdlaboratoriosporareas());

                activarModuloLaboratorio = true;
                listaModulosLaboratorio = null;
                moduloEquipoElemento = null;
                validacionesLaboratorio = true;
            } else {
                validacionesLaboratorio = false;
                validacionesSala = false;
                validacionesModulo = false;

                activarSalaLaboratorio = true;
                listaSalasLaboratorio = null;
                salaEquipoElemento = null;

                activarModuloLaboratorio = true;
                listaModulosLaboratorio = null;
                moduloEquipoElemento = null;

                FacesContext.getCurrentInstance().addMessage("form:laboratorioPorAreaEquipoElemento", new FacesMessage("El campo Laboratorio por Area es obligatorio."));
            }
            modificacionesRegistroEquipoElemento();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlantaEquipo actualizarAreasProfundizacion:  " + e.toString());
            System.out.println("Error ControllerDetallesPlantaEquipo actualizarAreasProfundizacion : " + e.toString());
        }
    }

    public void actualizarSalasLaboratorio() {
        try {
            if (Utilidades.validarNulo(salaEquipoElemento)) {
                activarModuloLaboratorio = false;
                moduloEquipoElemento = null;
                listaModulosLaboratorio = gestionarPlantaEquiposElementosBO.consultarModulosLaboratorioPorIDSalaLaboratorio(salaEquipoElemento.getIdsalalaboratorio());
                validacionesSala = true;
            } else {
                validacionesSala = false;
                validacionesModulo = false;
                activarModuloLaboratorio = true;
                listaModulosLaboratorio = null;
                moduloEquipoElemento = null;
                FacesContext.getCurrentInstance().addMessage("form:salaEquipoElemento", new FacesMessage("El campo Sala de Laboratorio es obligatorio."));
            }
            modificacionesRegistroEquipoElemento();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlantaEquipo actualizarSalasLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesPlantaEquipo actualizarSalasLaboratorio : " + e.toString());
        }
    }

    public void actualizarModulosLaboratorio() {
        try {
            if (Utilidades.validarNulo(moduloEquipoElemento)) {
                validacionesModulo = true;
            } else {
                validacionesModulo = false;
                FacesContext.getCurrentInstance().addMessage("form:moduloEquipoElemento", new FacesMessage("El campo Modulo/Banco de Trabajo es obligatorio."));
            }
            modificacionesRegistroEquipoElemento();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlantaEquipo actualizarSalasLaboratorio:  " + e.toString());
            System.out.println("Error ControllerDetallesPlantaEquipo actualizarSalasLaboratorio : " + e.toString());
        }
    }

    public void validarNombreEquipo() {
        if (Utilidades.validarNulo(nombreEquipoElemento) && (!nombreEquipoElemento.isEmpty())  && (nombreEquipoElemento.trim().length() > 0)) {
            if (!Utilidades.validarCaracterString(nombreEquipoElemento)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreEquipoElemento", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreEquipoElemento", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarInventarioEquipo() {
        if (Utilidades.validarNulo(inventarioEquipoElemento) && (!inventarioEquipoElemento.isEmpty())  && (inventarioEquipoElemento.trim().length() > 0)) {
            if (!Utilidades.validarCaracteresAlfaNumericos(inventarioEquipoElemento)) {
                validacionesInventario = false;
                FacesContext.getCurrentInstance().addMessage("form:inventarioEquipoElemento", new FacesMessage("El codigo ingresado es incorrecto."));
            } else {
                validacionesInventario = true;
            }
        } else {
            validacionesInventario = false;
            FacesContext.getCurrentInstance().addMessage("form:inventarioEquipoElemento", new FacesMessage("El codigo es obligatorio."));
        }
    }

    public void validarMarcaEquipo() {
        if (Utilidades.validarNulo(marcaEquipoElemento) && (!marcaEquipoElemento.isEmpty())  && (marcaEquipoElemento.trim().length() > 0)) {
            if (!Utilidades.validarCaracteresAlfaNumericos(marcaEquipoElemento)) {
                validacionesMarca = false;
                FacesContext.getCurrentInstance().addMessage("form:marcaEquipoElemento", new FacesMessage("La marca ingresada es incorrecta."));
            } else {
                validacionesMarca = true;
            }
        } else {
            validacionesMarca = false;
            FacesContext.getCurrentInstance().addMessage("form:marcaEquipoElemento", new FacesMessage("La marca es obligatoria."));
        }
    }

    public void actualizarEstadoEquipo() {
        if (Utilidades.validarNulo(estadoEquipoElemento)) {
            validacionesEstado = true;
        } else {
            validacionesEstado = false;
            FacesContext.getCurrentInstance().addMessage("form:estadoEquipoElemento", new FacesMessage("El estado del equipo es obligatorio."));
        }
    }

    public void actualizarTipoActivo() {
        if (Utilidades.validarNulo(tipoActivoEquipoElemento)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:tipoActivoEquipoElemento", new FacesMessage("El tipo activo es obligatorio."));
        }
    }

    public void actualizarProveedor() {
        if (Utilidades.validarNulo(proveedorEquipoElemento)) {
            validacionesProveedor = true;
        } else {
            validacionesProveedor = false;
            FacesContext.getCurrentInstance().addMessage("form:proveedorEquipoElemento", new FacesMessage("El proveedor es obligatorio."));
        }
    }

    public void validarModeloEquipo() {
        if (Utilidades.validarNulo(modeloEquipoElemento) && (!modeloEquipoElemento.isEmpty())  && (modeloEquipoElemento.trim().length() > 0)) {
            if (Utilidades.validarCaracteresAlfaNumericos(modeloEquipoElemento)) {
                validacionesModelo = true;
            } else {
                validacionesModelo = false;
                FacesContext.getCurrentInstance().addMessage("form:modeloEquipoElemento", new FacesMessage("El modelo se encuentra incorrecto."));
            }
        }
    }

    public void validarSerieEquipo() {
        if (Utilidades.validarNulo(serieEquipoElemento) && (!serieEquipoElemento.isEmpty())  && (serieEquipoElemento.trim().length() > 0)) {
            if (Utilidades.validarCaracteresAlfaNumericos(serieEquipoElemento)) {
                validacionesSerie = true;
            } else {
                validacionesSerie = false;
                FacesContext.getCurrentInstance().addMessage("form:serieEquipoElemento", new FacesMessage("La serie se encuentra incorrecta."));
            }
        }
    }

    public void validarCostoAlquilerEquipo() {
        if (Utilidades.validarNulo(alquilerEquipoElemento) && (!alquilerEquipoElemento.isEmpty())  && (alquilerEquipoElemento.trim().length() > 0)) {
            if (Utilidades.isNumber(alquilerEquipoElemento)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:alquilerEquipoElemento", new FacesMessage("El costo se encuentra incorrecto."));
            }
        }
    }

    public void validarInversionEquipo() {
        if (Utilidades.validarNulo(inversionEquipoElemento) && (!inversionEquipoElemento.isEmpty())  && (inversionEquipoElemento.trim().length() > 0)) {
            if ((Utilidades.isNumber(inversionEquipoElemento)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:inversionEquipoElemento", new FacesMessage("El valor de inversión se encuentra incorrecto."));
            } else {
                validacionesInversion = true;
            }
        }
    }

    public void validarFechaEquipo() {
        if (Utilidades.validarNulo(fechaEquipoElemento)) {
            if ((Utilidades.fechaIngresadaCorrecta(fechaEquipoElemento)) == false) {
                validacionesFecha = false;
                FacesContext.getCurrentInstance().addMessage("form:fechaEquipoElemento", new FacesMessage("La fecha ingresada se encuentra incorrecta."));
            } else {
                validacionesFecha = true;
            }
        }
    }

    public void validarEspecificacionEquipo() {
        if (Utilidades.validarNulo(especificacionEquipoElemento) && (!especificacionEquipoElemento.isEmpty())  && (especificacionEquipoElemento.trim().length() > 0)) {
            if ((Utilidades.validarCaracteresAlfaNumericos(especificacionEquipoElemento)) == false) {
                validacionesEspecificacion = false;
                FacesContext.getCurrentInstance().addMessage("form:especificacionEquipoElemento", new FacesMessage("La especificación ingresada se encuentra incorrecta."));
            } else {
                validacionesEspecificacion = true;
            }
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
        EquipoElemento equipo = gestionarPlantaEquiposElementosBO.obtenerEquipoElementoPorCodigoYModulo(inventarioEquipoElemento, moduloEquipoElemento.getIdmodulolaboratorio());
        if (null != equipo) {
            if (!equipoElementoDetalles.getIdequipoelemento().equals(equipo.getIdequipoelemento())) {
                retorno = false;
            }
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo docente
     */
    public void almacenarModificacionesEquipoElemento() {
        if (modificacionRegistro == true) {
            if (validarResultadosValidacion() == true) {
                if (validarCodigoRepetido() == true) {
                    modificarInformacionEquipoElemento();
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
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No se presento algun cambio en el registro. No se realizo ningun proceso de almacenamiento.";
            restaurarInformacionEquipoElemento();
        }
    }

    public void modificarInformacionEquipoElemento() {
        try {
            equipoElementoDetalles.setNombreequipo(inventarioEquipoElemento);
            equipoElementoDetalles.setInventarioequipo(nombreEquipoElemento);
            equipoElementoDetalles.setMarcaequipo(nombreEquipoElemento);
            equipoElementoDetalles.setModeloequipo(nombreEquipoElemento);
            equipoElementoDetalles.setSeriequipo(nombreEquipoElemento);

            equipoElementoDetalles.setEspecificacionestecnicas(especificacionEquipoElemento);
            equipoElementoDetalles.setFechaadquisicion(fechaEquipoElemento);

            equipoElementoDetalles.setCantidadequipo(Integer.valueOf(cantidadEquipoElemento).intValue());
            if (Utilidades.validarNulo(inversionEquipoElemento) && (!inversionEquipoElemento.isEmpty())  && (inversionEquipoElemento.trim().length() > 0)) {
                equipoElementoDetalles.setCostoadquisicion(Integer.valueOf(inversionEquipoElemento));
            } else {
                equipoElementoDetalles.setCostoadquisicion(Integer.valueOf("0"));
            }
            if (Utilidades.validarNulo(inversionEquipoElemento) && (!inversionEquipoElemento.isEmpty())  && (inversionEquipoElemento.trim().length() > 0)) {
                equipoElementoDetalles.setCostoalquiler(Integer.valueOf(alquilerEquipoElemento));
            } else {
                equipoElementoDetalles.setCostoalquiler(Integer.valueOf("0"));
            }

            equipoElementoDetalles.setModulolaboratorio(moduloEquipoElemento);
            equipoElementoDetalles.setEstadoequipo(estadoEquipoElemento);
            equipoElementoDetalles.setTipoactivo(tipoActivoEquipoElemento);
            equipoElementoDetalles.setProveedor(proveedorEquipoElemento);

            gestionarPlantaEquiposElementosBO.modificarInformacionEquipoElemento(equipoElementoDetalles);
            restaurarInformacionEquipoElemento();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlantaEquipo almacenarNuevoEquipoElementoEnSistema:  " + e.toString());
            System.out.println("Error ControllerDetallesPlantaEquipo almacenarNuevoEquipoElementoEnSistema : " + e.toString());
        }
    }

    public void modificacionesRegistroEquipoElemento() {
        if (modificacionRegistro == false) {
            modificacionRegistro = true;
        }
    }

    //GET - SET
    public EquipoElemento getEquipoElementoDetalles() {
        return equipoElementoDetalles;
    }

    public void setEquipoElementoDetalles(EquipoElemento equipoElementoDetalles) {
        this.equipoElementoDetalles = equipoElementoDetalles;
    }

    public BigInteger getIdEquipoElemento() {
        return idEquipoElemento;
    }

    public void setIdEquipoElemento(BigInteger idEquipoElemento) {
        this.idEquipoElemento = idEquipoElemento;
    }

    public boolean isActivarEditar() {
        return activarEditar;
    }

    public void setActivarEditar(boolean activarEditar) {
        this.activarEditar = activarEditar;
    }

    public boolean isDisabledEditar() {
        return disabledEditar;
    }

    public void setDisabledEditar(boolean disabledEditar) {
        this.disabledEditar = disabledEditar;
    }

    public boolean isModificacionRegistro() {
        return modificacionRegistro;
    }

    public void setModificacionRegistro(boolean modificacionRegistro) {
        this.modificacionRegistro = modificacionRegistro;
    }

    public boolean isVisibleGuardar() {
        return visibleGuardar;
    }

    public void setVisibleGuardar(boolean visibleGuardar) {
        this.visibleGuardar = visibleGuardar;
    }

    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreas() {
        return listaLaboratoriosPorAreas;
    }

    public void setListaLaboratoriosPorAreas(List<LaboratoriosPorAreas> listaLaboratoriosPorAreas) {
        this.listaLaboratoriosPorAreas = listaLaboratoriosPorAreas;
    }

    public LaboratoriosPorAreas getLaboratorioPorAreaEquipoElemento() {
        return laboratorioPorAreaEquipoElemento;
    }

    public void setLaboratorioPorAreaEquipoElemento(LaboratoriosPorAreas laboratorioPorAreaEquipoElemento) {
        this.laboratorioPorAreaEquipoElemento = laboratorioPorAreaEquipoElemento;
    }

    public List<SalaLaboratorio> getListaSalasLaboratorio() {
        return listaSalasLaboratorio;
    }

    public void setListaSalasLaboratorio(List<SalaLaboratorio> listaSalasLaboratorio) {
        this.listaSalasLaboratorio = listaSalasLaboratorio;
    }

    public SalaLaboratorio getSalaEquipoElemento() {
        return salaEquipoElemento;
    }

    public void setSalaEquipoElemento(SalaLaboratorio salaEquipoElemento) {
        this.salaEquipoElemento = salaEquipoElemento;
    }

    public boolean isActivarSalaLaboratorio() {
        return activarSalaLaboratorio;
    }

    public void setActivarSalaLaboratorio(boolean activarSalaLaboratorio) {
        this.activarSalaLaboratorio = activarSalaLaboratorio;
    }

    public List<ModuloLaboratorio> getListaModulosLaboratorio() {
        return listaModulosLaboratorio;
    }

    public void setListaModulosLaboratorio(List<ModuloLaboratorio> listaModulosLaboratorio) {
        this.listaModulosLaboratorio = listaModulosLaboratorio;
    }

    public ModuloLaboratorio getModuloEquipoElemento() {
        return moduloEquipoElemento;
    }

    public void setModuloEquipoElemento(ModuloLaboratorio moduloEquipoElemento) {
        this.moduloEquipoElemento = moduloEquipoElemento;
    }

    public boolean isActivarModuloLaboratorio() {
        return activarModuloLaboratorio;
    }

    public void setActivarModuloLaboratorio(boolean activarModuloLaboratorio) {
        this.activarModuloLaboratorio = activarModuloLaboratorio;
    }

    public List<TipoActivo> getListaTiposActivos() {
        return listaTiposActivos;
    }

    public void setListaTiposActivos(List<TipoActivo> listaTiposActivos) {
        this.listaTiposActivos = listaTiposActivos;
    }

    public TipoActivo getTipoActivoEquipoElemento() {
        return tipoActivoEquipoElemento;
    }

    public void setTipoActivoEquipoElemento(TipoActivo tipoActivoEquipoElemento) {
        this.tipoActivoEquipoElemento = tipoActivoEquipoElemento;
    }

    public List<EstadoEquipo> getListaEstadosEquipos() {
        return listaEstadosEquipos;
    }

    public void setListaEstadosEquipos(List<EstadoEquipo> listaEstadosEquipos) {
        this.listaEstadosEquipos = listaEstadosEquipos;
    }

    public EstadoEquipo getEstadoEquipoElemento() {
        return estadoEquipoElemento;
    }

    public void setEstadoEquipoElemento(EstadoEquipo estadoEquipoElemento) {
        this.estadoEquipoElemento = estadoEquipoElemento;
    }

    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Proveedor getProveedorEquipoElemento() {
        return proveedorEquipoElemento;
    }

    public void setProveedorEquipoElemento(Proveedor proveedorEquipoElemento) {
        this.proveedorEquipoElemento = proveedorEquipoElemento;
    }

    public String getNombreEquipoElemento() {
        return nombreEquipoElemento;
    }

    public void setNombreEquipoElemento(String nombreEquipoElemento) {
        this.nombreEquipoElemento = nombreEquipoElemento;
    }

    public String getInventarioEquipoElemento() {
        return inventarioEquipoElemento;
    }

    public void setInventarioEquipoElemento(String inventarioEquipoElemento) {
        this.inventarioEquipoElemento = inventarioEquipoElemento;
    }

    public String getMarcaEquipoElemento() {
        return marcaEquipoElemento;
    }

    public void setMarcaEquipoElemento(String marcaEquipoElemento) {
        this.marcaEquipoElemento = marcaEquipoElemento;
    }

    public String getModeloEquipoElemento() {
        return modeloEquipoElemento;
    }

    public void setModeloEquipoElemento(String modeloEquipoElemento) {
        this.modeloEquipoElemento = modeloEquipoElemento;
    }

    public String getSerieEquipoElemento() {
        return serieEquipoElemento;
    }

    public void setSerieEquipoElemento(String serieEquipoElemento) {
        this.serieEquipoElemento = serieEquipoElemento;
    }

    public String getAlquilerEquipoElemento() {
        return alquilerEquipoElemento;
    }

    public void setAlquilerEquipoElemento(String alquilerEquipoElemento) {
        this.alquilerEquipoElemento = alquilerEquipoElemento;
    }

    public String getCantidadEquipoElemento() {
        return cantidadEquipoElemento;
    }

    public void setCantidadEquipoElemento(String cantidadEquipoElemento) {
        this.cantidadEquipoElemento = cantidadEquipoElemento;
    }

    public String getInversionEquipoElemento() {
        return inversionEquipoElemento;
    }

    public void setInversionEquipoElemento(String inversionEquipoElemento) {
        this.inversionEquipoElemento = inversionEquipoElemento;
    }

    public String getEspecificacionEquipoElemento() {
        return especificacionEquipoElemento;
    }

    public void setEspecificacionEquipoElemento(String especificacionEquipoElemento) {
        this.especificacionEquipoElemento = especificacionEquipoElemento;
    }

    public Date getFechaEquipoElemento() {
        return fechaEquipoElemento;
    }

    public void setFechaEquipoElemento(Date fechaEquipoElemento) {
        this.fechaEquipoElemento = fechaEquipoElemento;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

}
