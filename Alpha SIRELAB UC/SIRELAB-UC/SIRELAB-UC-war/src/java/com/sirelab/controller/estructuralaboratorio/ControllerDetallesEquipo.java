/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaEquiposElementosBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Edificio;
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
    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarValidadorTipoUsuario;

    private EquipoElemento equipoElementoDetalles;
    private BigInteger idEquipoElemento;
    private boolean activarEditar, disabledEditar;
    private boolean modificacionRegistro;
    private boolean visibleGuardar;
    private List<Laboratorio> listaLaboratorio;
    private Laboratorio laboratorioEquipoElemento;
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
    private String fechaEquipoElemento;
    private boolean editarTipo;
    private String mensajeError;
    //
    //
    private boolean validacionesNombre, validacionesInventario, validacionesMarca, validacionesModelo, validacionesSerie;
    private boolean validacionesCosto, validacionesInversion, validacionesEspecificacion;
    private boolean validacionesFecha, validacionesLaboratorio, validacionesSala, validacionesModulo;
    private boolean validacionesTipo, validacionesEstado, validacionesProveedor;
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String colorMensaje;
    private boolean fechaDiferida;
    private MensajesConstantes constantes;
    private boolean perfilConsulta, activarLaboratorio;
    private EstadoEquipo estadoEquipoRespaldo;
    private boolean activarCasillas;

    public ControllerDetallesEquipo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
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
        activarCasillas = false;
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
        cargarInformacionPerfil();
    }

    public void asignarValoresVariablesEquipoElemento() {
        nombreEquipoElemento = equipoElementoDetalles.getNombreequipo();
        inventarioEquipoElemento = equipoElementoDetalles.getInventarioequipo();
        alquilerEquipoElemento = equipoElementoDetalles.getCostoalquiler().toString();
        cantidadEquipoElemento = String.valueOf(equipoElementoDetalles.getCantidadequipo());
        inversionEquipoElemento = equipoElementoDetalles.getCostoadquisicion().toString();
        editarTipo = equipoElementoDetalles.getPrivado();
        marcaEquipoElemento = equipoElementoDetalles.getMarcaequipo();
        modeloEquipoElemento = equipoElementoDetalles.getModeloequipo();
        serieEquipoElemento = equipoElementoDetalles.getSeriequipo();
        mensajeError = "";

        especificacionEquipoElemento = equipoElementoDetalles.getEspecificacionestecnicas();
        Date fecha = equipoElementoDetalles.getFechaadquisicion();
        if (null != fecha) {
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            fechaEquipoElemento = formateador.format(fecha);
        }

        laboratorioEquipoElemento = equipoElementoDetalles.getModulolaboratorio().getSalalaboratorio().getLaboratorio();
        salaEquipoElemento = equipoElementoDetalles.getModulolaboratorio().getSalalaboratorio();
        moduloEquipoElemento = equipoElementoDetalles.getModulolaboratorio();

        listaLaboratorio = gestionarPlantaEquiposElementosBO.consultarLaboratoriosRegistrados();
        listaSalasLaboratorio = gestionarPlantaEquiposElementosBO.consultarSalasLaboratorioPorIDLaboratorio(laboratorioEquipoElemento.getIdlaboratorio());
        listaModulosLaboratorio = gestionarPlantaEquiposElementosBO.consultarModulosLaboratorioPorIDSalaLaboratorio(salaEquipoElemento.getIdsalalaboratorio());

        tipoActivoEquipoElemento = equipoElementoDetalles.getTipoactivo();
        estadoEquipoElemento = equipoElementoDetalles.getEstadoequipo();
        estadoEquipoRespaldo = equipoElementoDetalles.getEstadoequipo();
        proveedorEquipoElemento = equipoElementoDetalles.getProveedor();

        listaTiposActivos = gestionarPlantaEquiposElementosBO.consultarTiposActivosRegistrador();
        listaEstadosEquipos = gestionarPlantaEquiposElementosBO.consultarEstadosEquiposRegistrados();
        listaProveedores = gestionarPlantaEquiposElementosBO.consultarProveedoresRegistrados();
        fechaDiferida = true;
    }

    public void modificacionFecha() {
        modificacionRegistro = true;
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

    private void cargarInformacionPerfil() {
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            perfilConsulta = true;
        } else {
            if ("ADMINISTRADOREDIFICIO".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                Edificio edificio = obtenerEdificio(usuarioLoginSistema.getIdUsuarioLogin());
                activarLaboratorio = true;
                listaSalasLaboratorio = administrarValidadorTipoUsuario.obtenerSalaLaboratorioPorEdificio(edificio.getIdedificio());
                listaLaboratorio = new ArrayList<Laboratorio>();
                laboratorioEquipoElemento = new Laboratorio();
            }
        }
    }

    public void actualizarTipoEquipo() {
        modificacionesRegistroEquipoElemento();
    }

    private Edificio obtenerEdificio(BigInteger usuario) {
        Edificio edificio = administrarValidadorTipoUsuario.buscarEdificioPorIdEncargadoEdificio(usuario);
        return edificio;
    }

    public String restaurarInformacionEquipoElemento() {
        if (activarCasillas = false) {
            equipoElementoDetalles = new EquipoElemento();
            equipoElementoDetalles = gestionarPlantaEquiposElementosBO.obtenerEquipoElementoPorIDEquipoElemento(idEquipoElemento);
            asignarValoresVariablesEquipoElemento();
        } else {
            activarCasillas = false;
            nombreEquipoElemento = null;
            inventarioEquipoElemento = null;
            alquilerEquipoElemento = null;
            cantidadEquipoElemento = null;
            inversionEquipoElemento = null;
            editarTipo = false;
            marcaEquipoElemento = null;
            modeloEquipoElemento = null;
            serieEquipoElemento = null;
            mensajeError = "";
            especificacionEquipoElemento = null;
            fechaEquipoElemento = null;
            laboratorioEquipoElemento = null;
            salaEquipoElemento = null;
            moduloEquipoElemento = null;

            listaLaboratorio = null;
            listaSalasLaboratorio = null;
            listaModulosLaboratorio = null;

            tipoActivoEquipoElemento = null;
            estadoEquipoElemento = null;
            estadoEquipoRespaldo = null;
            proveedorEquipoElemento = null;

            listaTiposActivos = null;
            listaEstadosEquipos = null;
            listaProveedores = null;
            fechaDiferida = true;
        }
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
        return "administrarequipos";
    }

    public void actualizarLaboratorios() {
        try {
            if (Utilidades.validarNulo(laboratorioEquipoElemento)) {
                activarSalaLaboratorio = false;
                salaEquipoElemento = null;
                listaSalasLaboratorio = gestionarPlantaEquiposElementosBO.consultarSalasLaboratorioPorIDLaboratorio(laboratorioEquipoElemento.getIdlaboratorio());

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

                FacesContext.getCurrentInstance().addMessage("form:laboratorioEquipoElemento", new FacesMessage("El campo Laboratorio por Area es obligatorio."));
            }
            modificacionesRegistroEquipoElemento();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlantaEquipo actualizarAreasProfundizacion:  " + e.toString(), e);
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
            logger.error("Error ControllerDetallesPlantaEquipo actualizarSalasLaboratorio:  " + e.toString(), e);
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
            logger.error("Error ControllerDetallesPlantaEquipo actualizarSalasLaboratorio:  " + e.toString(), e);
        }
    }

    public void validarNombreEquipo() {
        if (Utilidades.validarNulo(nombreEquipoElemento) && (!nombreEquipoElemento.isEmpty()) && (nombreEquipoElemento.trim().length() > 0)) {
            int tam = nombreEquipoElemento.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nombreEquipoElemento)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nombreEquipoElemento", new FacesMessage("El nombre ingresado es incorrecto. " + constantes.INVENTARIO_NOMBRE));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nombreEquipoElemento", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_NOMBRE));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nombreEquipoElemento", new FacesMessage("El nombre es obligatorio. " + constantes.INVENTARIO_NOMBRE));
        }
        modificacionesRegistroEquipoElemento();
    }

    public void validarInventarioEquipo() {
        if (Utilidades.validarNulo(inventarioEquipoElemento) && (!inventarioEquipoElemento.isEmpty()) && (inventarioEquipoElemento.trim().length() > 0)) {
            int tam = inventarioEquipoElemento.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(inventarioEquipoElemento)) {
                    validacionesInventario = false;
                    FacesContext.getCurrentInstance().addMessage("form:inventarioEquipoElemento", new FacesMessage("El codigo ingresado es incorrecto. " + constantes.INVENTARIO_CODIGO));
                } else {
                    validacionesInventario = true;
                }
            } else {
                validacionesInventario = false;
                FacesContext.getCurrentInstance().addMessage("form:inventarioEquipoElemento", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.INVENTARIO_CODIGO));
            }
        } else {
            validacionesInventario = false;
            FacesContext.getCurrentInstance().addMessage("form:inventarioEquipoElemento", new FacesMessage("El codigo es obligatorio. " + constantes.INVENTARIO_CODIGO));
        }
        modificacionesRegistroEquipoElemento();
    }

    public void validarMarcaEquipo() {
        if (Utilidades.validarNulo(marcaEquipoElemento) && (!marcaEquipoElemento.isEmpty()) && (marcaEquipoElemento.trim().length() > 0)) {
            int tam = marcaEquipoElemento.length();
            if (tam >= 2) {
                if (!Utilidades.validarCaracteresAlfaNumericos(marcaEquipoElemento)) {
                    validacionesMarca = false;
                    FacesContext.getCurrentInstance().addMessage("form:marcaEquipoElemento", new FacesMessage("La marca ingresada es incorrecta. " + constantes.INVENTARIO_MARCA));
                } else {
                    validacionesMarca = true;
                }
            } else {
                validacionesMarca = false;
                FacesContext.getCurrentInstance().addMessage("form:marcaEquipoElemento", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_MARCA));
            }
        } else {
            validacionesMarca = false;
            FacesContext.getCurrentInstance().addMessage("form:marcaEquipoElemento", new FacesMessage("La marca es obligatoria. " + constantes.INVENTARIO_MARCA));
        }
        modificacionesRegistroEquipoElemento();
    }

    public void actualizarEstadoEquipo() {
        if (Utilidades.validarNulo(estadoEquipoElemento)) {
            validacionesEstado = true;
        } else {
            validacionesEstado = false;
            FacesContext.getCurrentInstance().addMessage("form:estadoEquipoElemento", new FacesMessage("El estado del equipo es obligatorio."));
        }
        modificacionesRegistroEquipoElemento();
    }

    public void actualizarTipoActivo() {
        if (Utilidades.validarNulo(tipoActivoEquipoElemento)) {
            validacionesTipo = true;
        } else {
            validacionesTipo = false;
            FacesContext.getCurrentInstance().addMessage("form:tipoActivoEquipoElemento", new FacesMessage("El tipo activo es obligatorio."));
        }
        modificacionesRegistroEquipoElemento();
    }

    public void actualizarProveedor() {
        if (Utilidades.validarNulo(proveedorEquipoElemento)) {
            validacionesProveedor = true;
        } else {
            validacionesProveedor = false;
            FacesContext.getCurrentInstance().addMessage("form:proveedorEquipoElemento", new FacesMessage("El proveedor es obligatorio."));
        }
        modificacionesRegistroEquipoElemento();
    }

    public void validarModeloEquipo() {
        if (Utilidades.validarNulo(modeloEquipoElemento) && (!modeloEquipoElemento.isEmpty()) && (modeloEquipoElemento.trim().length() > 0)) {
            int tam = modeloEquipoElemento.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(modeloEquipoElemento)) {
                    validacionesModelo = true;
                } else {
                    validacionesModelo = false;
                    FacesContext.getCurrentInstance().addMessage("form:modeloEquipoElemento", new FacesMessage("El modelo se encuentra incorrecto. " + constantes.INVENTARIO_MODELO));
                }
            } else {
                validacionesMarca = false;
                FacesContext.getCurrentInstance().addMessage("form:marcaEquipoElemento", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_MODELO));
            }
        }
        modificacionesRegistroEquipoElemento();
    }

    public void validarSerieEquipo() {
        if (Utilidades.validarNulo(serieEquipoElemento) && (!serieEquipoElemento.isEmpty()) && (serieEquipoElemento.trim().length() > 0)) {
            int tam = serieEquipoElemento.length();
            if (tam >= 2) {
                if (Utilidades.validarCaracteresAlfaNumericos(serieEquipoElemento)) {
                    validacionesSerie = true;
                } else {
                    validacionesSerie = false;
                    FacesContext.getCurrentInstance().addMessage("form:serieEquipoElemento", new FacesMessage("La serie se encuentra incorrecta. " + constantes.INVENTARIO_SERIAL));
                }
            } else {
                validacionesSerie = false;
                FacesContext.getCurrentInstance().addMessage("form:serieEquipoElemento", new FacesMessage("El tamaño minimo permitido es 2 caracteres. " + constantes.INVENTARIO_SERIAL));
            }
        }
        modificacionesRegistroEquipoElemento();
    }

    public void validarCostoAlquilerEquipo() {
        if (Utilidades.validarNulo(alquilerEquipoElemento) && (!alquilerEquipoElemento.isEmpty()) && (alquilerEquipoElemento.trim().length() > 0)) {
            if (Utilidades.isNumber(alquilerEquipoElemento)) {
                validacionesCosto = true;
            } else {
                validacionesCosto = false;
                FacesContext.getCurrentInstance().addMessage("form:alquilerEquipoElemento", new FacesMessage("El costo se encuentra incorrecto. " + constantes.INVENTARIO_COST_ALQ));
            }
        }
        modificacionesRegistroEquipoElemento();
    }

    public void validarInversionEquipo() {
        if (Utilidades.validarNulo(inversionEquipoElemento) && (!inversionEquipoElemento.isEmpty()) && (inversionEquipoElemento.trim().length() > 0)) {
            if ((Utilidades.isNumber(inversionEquipoElemento)) == false) {
                validacionesInversion = false;
                FacesContext.getCurrentInstance().addMessage("form:inversionEquipoElemento", new FacesMessage("El valor de inversión se encuentra incorrecto. " + constantes.INVENTARIO_COST_INV));
            } else {
                validacionesInversion = true;
            }
        }
        modificacionesRegistroEquipoElemento();
    }

    public void validarFechaEquipo() {
        if (Utilidades.validarNulo(fechaEquipoElemento)) {
            try {
                Date fechaValidacion = new Date(fechaEquipoElemento);
                validacionesFecha = true;
            } catch (Exception e) {
                validacionesFecha = true;
                FacesContext.getCurrentInstance().addMessage("form:fechaEquipoElemento", new FacesMessage("La fecha ingresada fue incorrecta. El sistema asigno la fecha del día"));
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                fechaEquipoElemento = formateador.format(new Date());
            }
        }
        modificacionesRegistroEquipoElemento();
    }

    public void validarEspecificacionEquipo() {
        if (Utilidades.validarNulo(especificacionEquipoElemento) && (!especificacionEquipoElemento.isEmpty()) && (especificacionEquipoElemento.trim().length() > 0)) {
            int tam = especificacionEquipoElemento.length();
            if (tam >= 20) {
                if ((Utilidades.validarCaracteresAlfaNumericos(especificacionEquipoElemento)) == false) {
                    validacionesEspecificacion = false;
                    FacesContext.getCurrentInstance().addMessage("form:especificacionEquipoElemento", new FacesMessage("La especificación ingresada se encuentra incorrecta. " + constantes.INVENTARIO_DESCRIP));
                } else {
                    validacionesEspecificacion = true;
                }
            } else {
                validacionesEspecificacion = false;
                FacesContext.getCurrentInstance().addMessage("form:especificacionEquipoElemento", new FacesMessage("El tamaño minimo permitido es 20 caracteres. " + constantes.INVENTARIO_DESCRIP));
            }
        }
        modificacionesRegistroEquipoElemento();
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        mensajeError = "";
        if (validacionesFecha == false) {
            mensajeError = mensajeError + " - Fecha - ";
            retorno = false;
        }
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
        if (validacionesSala == false) {
            mensajeError = mensajeError + " - Sala - ";
            retorno = false;
        }
        if (validacionesModulo == false) {
            mensajeError = mensajeError + " - Modulo - ";
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
        if (validacionesLaboratorio == false) {
            mensajeError = mensajeError + " - Laboratorio - ";
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
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "El codigo ya esta registrado en el sistema.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar. Errores: " + mensajeError;
            }
        } else {
            colorMensaje = "black";
            mensajeFormulario = "No se presento algun cambio en el registro. No se realizo ningun proceso de almacenamiento.";
            restaurarInformacionEquipoElemento();
        }
    }

    public void modificarInformacionEquipoElemento() {
        try {
            equipoElementoDetalles.setPrivado(editarTipo);
            equipoElementoDetalles.setNombreequipo(nombreEquipoElemento);
            equipoElementoDetalles.setInventarioequipo(inventarioEquipoElemento);
            equipoElementoDetalles.setMarcaequipo(marcaEquipoElemento);
            equipoElementoDetalles.setModeloequipo(modeloEquipoElemento);
            equipoElementoDetalles.setSeriequipo(serieEquipoElemento);

            equipoElementoDetalles.setEspecificacionestecnicas(especificacionEquipoElemento);
            if (Utilidades.validarNulo(fechaEquipoElemento)) {
                SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = formateador.parse(fechaEquipoElemento);
                equipoElementoDetalles.setFechaadquisicion(fecha);
            } else {
                equipoElementoDetalles.setFechaadquisicion(null);
            }
            equipoElementoDetalles.setCantidadequipo(Integer.valueOf(cantidadEquipoElemento).intValue());
            if (Utilidades.validarNulo(inversionEquipoElemento) && (!inversionEquipoElemento.isEmpty()) && (inversionEquipoElemento.trim().length() > 0)) {
                equipoElementoDetalles.setCostoadquisicion(Integer.valueOf(inversionEquipoElemento));
            } else {
                equipoElementoDetalles.setCostoadquisicion(Integer.valueOf("0"));
            }
            if (Utilidades.validarNulo(inversionEquipoElemento) && (!inversionEquipoElemento.isEmpty()) && (inversionEquipoElemento.trim().length() > 0)) {
                equipoElementoDetalles.setCostoalquiler(Integer.valueOf(alquilerEquipoElemento));
            } else {
                equipoElementoDetalles.setCostoalquiler(Integer.valueOf("0"));
            }

            equipoElementoDetalles.setModulolaboratorio(moduloEquipoElemento);
            equipoElementoDetalles.setEstadoequipo(estadoEquipoElemento);
            equipoElementoDetalles.setTipoactivo(tipoActivoEquipoElemento);
            equipoElementoDetalles.setProveedor(proveedorEquipoElemento);
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            if (estadoEquipoRespaldo.getIdestadoequipo().equals(estadoEquipoElemento.getIdestadoequipo())) {
                gestionarPlantaEquiposElementosBO.modificarInformacionEquipoElemento(equipoElementoDetalles, usuarioLoginSistema.getUserUsuario(), false);
            } else {
                gestionarPlantaEquiposElementosBO.modificarInformacionEquipoElemento(equipoElementoDetalles, usuarioLoginSistema.getUserUsuario(), true);
            }
            equipoElementoDetalles = new EquipoElemento();
            equipoElementoDetalles = gestionarPlantaEquiposElementosBO.obtenerEquipoElementoPorIDEquipoElemento(idEquipoElemento);
            asignarValoresVariablesEquipoElemento();
        } catch (Exception e) {
            logger.error("Error ControllerDetallesPlantaEquipo modificarInformacionEquipoElemento:  " + e.toString(), e);
        }
    }

    public void eliminarEquipoElemento() {
        Integer componente = gestionarPlantaEquiposElementosBO.obtenerComponentesAsociados(idEquipoElemento);
        if (null != componente) {
            if (componente == 0) {
                boolean registro = gestionarPlantaEquiposElementosBO.eliminarEquipo(equipoElementoDetalles);
                if (registro == true) {
                    activarCasillas = true;
                    disabledEditar = true;
                    activarEditar = true;
                    visibleGuardar = true;
                    activarLaboratorio = true;
                    activarEditar = true;
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "El registro ha sido eliminado con éxito. Regrese nuevamente a la pagina de consulta.";
                } else {
                    colorMensaje = "#FF0000";
                    mensajeFormulario = "Ocurrio un error en la eliminación del registro. Intente más tarde.";
                }
            } else {
                colorMensaje = "#FF0000";
                mensajeFormulario = "El registro no puede ser eliminado dado que tiene componentes asociados.";
            }
        } else {
            colorMensaje = "#FF0000";
            mensajeFormulario = "Ocurrio un error en la eliminación del registro. Intente más tarde.";
        }
    }

    public void modificacionesRegistroEquipoElemento() {
        modificacionRegistro = true;
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

    public List<Laboratorio> getListaLaboratorio() {
        return listaLaboratorio;
    }

    public void setListaLaboratorio(List<Laboratorio> listaLaboratorio) {
        this.listaLaboratorio = listaLaboratorio;
    }

    public Laboratorio getLaboratorioEquipoElemento() {
        return laboratorioEquipoElemento;
    }

    public void setLaboratorioEquipoElemento(Laboratorio laboratorioEquipoElemento) {
        this.laboratorioEquipoElemento = laboratorioEquipoElemento;
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

    public String getFechaEquipoElemento() {
        return fechaEquipoElemento;
    }

    public void setFechaEquipoElemento(String fechaEquipoElemento) {
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

    public boolean isFechaDiferida() {
        return fechaDiferida;
    }

    public void setFechaDiferida(boolean fechaDiferida) {
        this.fechaDiferida = fechaDiferida;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public boolean isEditarTipo() {
        return editarTipo;
    }

    public void setEditarTipo(boolean editarTipo) {
        this.editarTipo = editarTipo;
    }

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

}
