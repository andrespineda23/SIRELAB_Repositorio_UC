/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaEquiposElementosBOInterface;
import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.Proveedor;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
import com.sirelab.entidades.TipoActivo;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
public class ControllerAdministrarEquipos implements Serializable {

    @EJB
    GestionarPlantaEquiposElementosBOInterface gestionarPlantaEquiposElementosBO;
    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarValidadorTipoUsuario;

    private String parametroNombre, parametroInventario, parametroMarca, parametroModelo, parametroSerie;
    private List<Laboratorio> listaLaboratorio;
    private Laboratorio parametroLaboratorio;
    private boolean activarLaboratorio;
    private List<SalaLaboratorio> listaSalasLaboratorios;
    private SalaLaboratorio parametroSalaLaboratorio;
    private List<ModuloLaboratorio> listaModulosLaboratorios;
    private ModuloLaboratorio parametroModuloLaboratorio;
    private List<TipoActivo> listaTiposActivos;
    private TipoActivo parametroTipoActivo;
    private List<Proveedor> listaProveedores;
    private Proveedor parametroProveedor;
    private List<EstadoEquipo> listaEstadosEquipos;
    private EstadoEquipo parametroEstadoEquipo;
    private boolean activarAreaProfundizacion;
    private boolean activarSalaLaboratorio;
    private boolean activarModuloLaboratorio;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<EquipoElemento> listaEquiposElementos;
    private List<EquipoElemento> listaEquiposElementosTabla;
    private int posicionEquipoTabla;
    private int tamTotalEquipo;
    private boolean bloquearPagSigEquipo, bloquearPagAntEquipo;
    //
    private String altoTabla;
    //
    private String paginaAnterior;
    //
    private boolean perfilConsulta;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private TipoPerfil tipoPerfil;
    private boolean activarLabArea;

    public ControllerAdministrarEquipos() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarModuloLaboratorio = true;
        activarSalaLaboratorio = true;
        activarAreaProfundizacion = true;
        activarExport = true;
        parametroNombre = null;
        parametroInventario = null;
        parametroMarca = null;
        parametroModelo = null;
        parametroSerie = null;
        parametroSalaLaboratorio = new SalaLaboratorio();
        parametroModuloLaboratorio = new ModuloLaboratorio();
        parametroEstadoEquipo = new EstadoEquipo();
        parametroTipoActivo = new TipoActivo();
        parametroProveedor = new Proveedor();
        parametroLaboratorio = new Laboratorio();
        listaTiposActivos = null;
        listaEstadosEquipos = null;
        listaProveedores = null;
        listaLaboratorio = null;
        altoTabla = "150";
        inicializarFiltros();
        listaModulosLaboratorios = null;
        listaSalasLaboratorios = null;
        listaEquiposElementosTabla = null;
        listaEquiposElementos = null;
        posicionEquipoTabla = 0;
        tamTotalEquipo = 0;
        bloquearPagAntEquipo = true;
        bloquearPagSigEquipo = true;
        BasicConfigurator.configure();
    }

    private Map<String, Object> validarSesionAdicionales(String nombre, String codigo) {
        Map<String, Object> lista = new HashMap<String, Object>();
        if ("DEPARTAMENTO".equalsIgnoreCase(nombre)) {
            Departamento registro = administrarValidadorTipoUsuario.obtenerDepartamentoPorCodigo(codigo);
            if (null != registro) {
                lista.put("DEPARTAMENTO", registro);
            }
        }
        if ("LABORATORIO".equalsIgnoreCase(nombre)) {
            Laboratorio registro = administrarValidadorTipoUsuario.obtenerLaboratorioPorCodigo(codigo);
            if (null != registro) {
                lista.put("LABORATORIO", registro);
            }
        }
        return lista;
    }

    private void cargarInformacionPerfil() {
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ADMINISTRADOR".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            perfilConsulta = true;
        } else {
            if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                boolean validarPerfilConsulta = validarSesionConsulta(usuarioLoginSistema.getIdUsuarioLogin());
                if (validarPerfilConsulta == false) {
                    perfilConsulta = true;
                    Map<String, Object> datosPerfil = validarSesionAdicionales(tipoPerfil.getNombre(), tipoPerfil.getCodigoregistro());
                    if (null != datosPerfil) {
                        if (datosPerfil.containsKey("DEPARTAMENTO") || datosPerfil.containsKey("LABORATORIO")) {
                            if (datosPerfil.containsKey("DEPARTAMENTO")) {
                                Departamento parametroDepartamento = (Departamento) datosPerfil.get("DEPARTAMENTO");
                                listaLaboratorio = administrarValidadorTipoUsuario.obtenerLaboratoriosActivosPorIDDepartamento(parametroDepartamento.getIddepartamento());
                                activarLaboratorio = false;
                            }
                            if (datosPerfil.containsKey("LABORATORIO")) {
                                activarLaboratorio = true;
                                parametroLaboratorio = (Laboratorio) datosPerfil.get("LABORATORIO");
                                listaLaboratorio = new ArrayList<Laboratorio>();
                                listaLaboratorio.add(parametroLaboratorio);
                            }
                        } else {
                            activarLaboratorio = false;
                        }
                    } else {
                        activarLaboratorio = false;
                    }
                } else {
                    perfilConsulta = false;
                }
            } else {
                if ("ADMINISTRADOREDIFICIO".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                    Edificio edificio = obtenerEdificio(usuarioLoginSistema.getIdUsuarioLogin());
                    activarLaboratorio = true;
                    listaSalasLaboratorios = administrarValidadorTipoUsuario.obtenerSalaLaboratorioPorEdificio(edificio.getIdedificio());
                    listaLaboratorio = new ArrayList<Laboratorio>();
                    parametroLaboratorio = new Laboratorio();

                }
            }
        }
    }

    private Edificio obtenerEdificio(BigInteger usuario) {
        Edificio edificio = administrarValidadorTipoUsuario.buscarEdificioPorIdEncargadoEdificio(usuario);
        return edificio;
    }

    private boolean validarSesionConsulta(BigInteger usuario) {
        boolean retorno = false;
        tipoPerfil = administrarValidadorTipoUsuario.buscarTipoPerfilPorIDEncargado(usuario);
        if (null != tipoPerfil) {
            if ("CONSULTA".equalsIgnoreCase(tipoPerfil.getNombre())) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
        cargarInformacionPerfil();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroInventario", null);
        filtros.put("parametroMarca", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroSerie", null);
        filtros.put("parametroModelo", null);
        filtros.put("parametroProveedor", null);
        filtros.put("parametroTipoActivo", null);
        filtros.put("parametroModuloLaboratorio", null);
        filtros.put("parametroSalaLaboratorio", null);
        filtros.put("parametroLaboratorio", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty()) && (parametroNombre.trim().length() > 0)) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroInventario) == true) && (!parametroInventario.isEmpty()) && (parametroInventario.trim().length() > 0)) {
            filtros.put("parametroInventario", parametroInventario.toString());
        }
        if ((Utilidades.validarNulo(parametroMarca) == true) && (!parametroMarca.isEmpty()) && (parametroMarca.trim().length() > 0)) {
            filtros.put("parametroMarca", parametroMarca.toString());
        }
        if ((Utilidades.validarNulo(parametroModelo) == true) && (!parametroModelo.isEmpty()) && (parametroModelo.trim().length() > 0)) {
            filtros.put("parametroModelo", parametroModelo.toString());
        }
        if ((Utilidades.validarNulo(parametroSerie) == true) && (!parametroSerie.isEmpty()) && (parametroSerie.trim().length() > 0)) {
            filtros.put("parametroSerie", parametroSerie.toString());
        }
        if (Utilidades.validarNulo(parametroLaboratorio)) {
            if (parametroLaboratorio.getIdlaboratorio() != null) {
                filtros.put("parametroLaboratorio", parametroLaboratorio.getIdlaboratorio().toString());
            }
        }
        if (Utilidades.validarNulo(parametroEstadoEquipo)) {
            if (parametroEstadoEquipo.getIdestadoequipo() != null) {
                filtros.put("parametroEstado", parametroEstadoEquipo.getIdestadoequipo().toString());
            }
        }
        if (Utilidades.validarNulo(parametroSalaLaboratorio)) {
            if (parametroSalaLaboratorio.getIdsalalaboratorio() != null) {
                filtros.put("parametroSalaLaboratorio", parametroSalaLaboratorio.getIdsalalaboratorio().toString());
            }
        }
        if (Utilidades.validarNulo(parametroModuloLaboratorio)) {
            if (parametroModuloLaboratorio.getIdmodulolaboratorio() != null) {
                filtros.put("parametroModuloLaboratorio", parametroModuloLaboratorio.getIdmodulolaboratorio().toString());
            }
        }
        if (Utilidades.validarNulo(parametroProveedor)) {
            if (parametroProveedor.getIdproveedor() != null) {
                filtros.put("parametroProveedor", parametroProveedor.getIdproveedor().toString());
            }
        }
        if (Utilidades.validarNulo(parametroTipoActivo)) {
            if (parametroTipoActivo.getIdtipoactivo() != null) {
                filtros.put("parametroTipoActivo", parametroTipoActivo.getIdtipoactivo().toString());
            }
        }
    }

    public void buscarEquiposElementosPorParametros() {
        try {
            inicializarFiltros();
            listaEquiposElementos = null;
            listaEquiposElementos = gestionarPlantaEquiposElementosBO.consultarEquiposElementosPorParametro(filtros);
            if (listaEquiposElementos != null) {
                if (listaEquiposElementos.size() > 0) {
                    activarExport = false;
                    listaEquiposElementosTabla = new ArrayList<EquipoElemento>();
                    tamTotalEquipo = listaEquiposElementos.size();
                    posicionEquipoTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalEquipo);
                    cargarDatosTablaEquipo();
                } else {
                    activarExport = true;
                    listaEquiposElementosTabla = null;
                    tamTotalEquipo = 0;
                    posicionEquipoTabla = 0;
                    bloquearPagAntEquipo = true;
                    bloquearPagSigEquipo = true;
                    cantidadRegistros = String.valueOf(tamTotalEquipo);
                }
            } else {
                listaEquiposElementosTabla = null;
                tamTotalEquipo = 0;
                posicionEquipoTabla = 0;
                bloquearPagAntEquipo = true;
                cantidadRegistros = String.valueOf(tamTotalEquipo);
                bloquearPagSigEquipo = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaEquiposElementos buscarEquiposElementosPorParametros:  " + e.toString());
            logger.error("Error ControllerGestionarPlantaEquiposElementos buscarEquiposElementosPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaEquipo() {
        if (tamTotalEquipo < 10) {
            for (int i = 0; i < tamTotalEquipo; i++) {
                listaEquiposElementosTabla.add(listaEquiposElementos.get(i));
            }
            bloquearPagSigEquipo = true;
            bloquearPagAntEquipo = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaEquiposElementosTabla.add(listaEquiposElementos.get(i));
            }
            bloquearPagSigEquipo = false;
            bloquearPagAntEquipo = true;
        }
    }

    public void cargarPaginaSiguienteEquipo() {
        listaEquiposElementosTabla = new ArrayList<EquipoElemento>();
        posicionEquipoTabla = posicionEquipoTabla + 10;
        int diferencia = tamTotalEquipo - posicionEquipoTabla;
        if (diferencia > 10) {
            for (int i = posicionEquipoTabla; i < (posicionEquipoTabla + 10); i++) {
                listaEquiposElementosTabla.add(listaEquiposElementos.get(i));
            }
            bloquearPagSigEquipo = false;
            bloquearPagAntEquipo = false;
        } else {
            for (int i = posicionEquipoTabla; i < (posicionEquipoTabla + diferencia); i++) {
                listaEquiposElementosTabla.add(listaEquiposElementos.get(i));
            }
            bloquearPagSigEquipo = true;
            bloquearPagAntEquipo = false;
        }
    }

    public void cargarPaginaAnteriorEquipo() {
        listaEquiposElementosTabla = new ArrayList<EquipoElemento>();
        posicionEquipoTabla = posicionEquipoTabla - 10;
        int diferencia = tamTotalEquipo - posicionEquipoTabla;
        if (diferencia == tamTotalEquipo) {
            for (int i = posicionEquipoTabla; i < (posicionEquipoTabla + 10); i++) {
                listaEquiposElementosTabla.add(listaEquiposElementos.get(i));
            }
            bloquearPagSigEquipo = false;
            bloquearPagAntEquipo = true;
        } else {
            for (int i = posicionEquipoTabla; i < (posicionEquipoTabla + 10); i++) {
                listaEquiposElementosTabla.add(listaEquiposElementos.get(i));
            }
            bloquearPagSigEquipo = false;
            bloquearPagAntEquipo = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarAreaProfundizacion = true;
        activarSalaLaboratorio = true;
        activarModuloLaboratorio = true;
        activarExport = true;
        parametroNombre = null;
        parametroInventario = null;
        parametroMarca = null;
        parametroModelo = null;
        parametroSerie = null;
        parametroSalaLaboratorio = new SalaLaboratorio();
        parametroLaboratorio = new Laboratorio();
        parametroModuloLaboratorio = new ModuloLaboratorio();
        parametroTipoActivo = new TipoActivo();
        parametroEstadoEquipo = new EstadoEquipo();
        parametroProveedor = new Proveedor();

        listaLaboratorio = null;
        listaSalasLaboratorios = null;
        listaModulosLaboratorios = null;
        listaEstadosEquipos = null;
        listaProveedores = null;
        listaTiposActivos = null;

        listaEquiposElementos = null;
        listaEquiposElementosTabla = null;
        posicionEquipoTabla = 0;
        tamTotalEquipo = 0;
        bloquearPagAntEquipo = true;
        bloquearPagSigEquipo = true;
        cantidadRegistros = "N/A";
        inicializarFiltros();
        return paginaAnterior;
    }

    public void limpiarDatos() {
        cantidadRegistros = "N/A";
        activarAreaProfundizacion = true;
        activarSalaLaboratorio = true;
        activarModuloLaboratorio = true;
        activarExport = true;
        parametroNombre = null;
        parametroInventario = null;
        parametroMarca = null;
        parametroModelo = null;
        parametroSerie = null;
        parametroSalaLaboratorio = new SalaLaboratorio();
        parametroLaboratorio = new Laboratorio();
        parametroModuloLaboratorio = new ModuloLaboratorio();
        parametroTipoActivo = new TipoActivo();
        parametroEstadoEquipo = new EstadoEquipo();
        parametroProveedor = new Proveedor();
        listaSalasLaboratorios = null;
        listaModulosLaboratorios = null;
        listaEquiposElementos = null;
        listaEquiposElementosTabla = null;
        posicionEquipoTabla = 0;
        tamTotalEquipo = 0;
        bloquearPagAntEquipo = true;
        bloquearPagSigEquipo = true;
        inicializarFiltros();
        cargarInformacionPerfil();
    }

    public void actualizarLaboratorios() {
        if (Utilidades.validarNulo(parametroLaboratorio)) {
            parametroSalaLaboratorio = new SalaLaboratorio();
            listaSalasLaboratorios = gestionarPlantaEquiposElementosBO.consultarSalasLaboratorioPorIDLaboratorio(parametroLaboratorio.getIdlaboratorio());;
            activarSalaLaboratorio = false;
            activarModuloLaboratorio = true;
            listaModulosLaboratorios = null;
            parametroModuloLaboratorio = new ModuloLaboratorio();
        } else {
            activarModuloLaboratorio = true;
            listaModulosLaboratorios = null;
            parametroModuloLaboratorio = new ModuloLaboratorio();
            activarSalaLaboratorio = true;
            listaSalasLaboratorios = null;
            parametroSalaLaboratorio = new SalaLaboratorio();
        }
    }

    public void actualizarSalasLaboratorio() {
        if (Utilidades.validarNulo(parametroSalaLaboratorio)) {
            parametroModuloLaboratorio = new ModuloLaboratorio();
            listaModulosLaboratorios = gestionarPlantaEquiposElementosBO.consultarModulosLaboratorioPorIDSalaLaboratorio(parametroSalaLaboratorio.getIdsalalaboratorio());
            activarModuloLaboratorio = false;
        } else {
            activarModuloLaboratorio = true;
            listaModulosLaboratorios = null;
            parametroModuloLaboratorio = new ModuloLaboratorio();
        }
    }

    /*    
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "PlataEquiposdeModuloPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "PlataEquiposdeModuloXLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    public String cambiarPaginaDetalles() {
        limpiarProcesoBusqueda();
        return "detallesequipo";
    }

    // GET - SET
    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
    }

    public String getParametroInventario() {
        return parametroInventario;
    }

    public void setParametroInventario(String parametroInventario) {
        this.parametroInventario = parametroInventario;
    }

    public String getParametroMarca() {
        return parametroMarca;
    }

    public void setParametroMarca(String parametroMarca) {
        this.parametroMarca = parametroMarca;
    }

    public String getParametroModelo() {
        return parametroModelo;
    }

    public void setParametroModelo(String parametroModelo) {
        this.parametroModelo = parametroModelo;
    }

    public String getParametroSerie() {
        return parametroSerie;
    }

    public void setParametroSerie(String parametroSerie) {
        this.parametroSerie = parametroSerie;
    }

    public List<Laboratorio> getListaLaboratorio() {
        if (listaLaboratorio == null) {
            listaLaboratorio = gestionarPlantaEquiposElementosBO.consultarLaboratoriosRegistrados();
        }
        return listaLaboratorio;
    }

    public void setListaLaboratorio(List<Laboratorio> listaLaboratorio) {
        this.listaLaboratorio = listaLaboratorio;
    }

    public Laboratorio getParametroLaboratorio() {
        return parametroLaboratorio;
    }

    public void setParametroLaboratorio(Laboratorio parametroLaboratorio) {
        this.parametroLaboratorio = parametroLaboratorio;
    }

    public List<SalaLaboratorio> getListaSalasLaboratorios() {
        return listaSalasLaboratorios;
    }

    public void setListaSalasLaboratorios(List<SalaLaboratorio> listaSalasLaboratorios) {
        this.listaSalasLaboratorios = listaSalasLaboratorios;
    }

    public SalaLaboratorio getParametroSalaLaboratorio() {
        return parametroSalaLaboratorio;
    }

    public void setParametroSalaLaboratorio(SalaLaboratorio parametroSalaLaboratorio) {
        this.parametroSalaLaboratorio = parametroSalaLaboratorio;
    }

    public List<ModuloLaboratorio> getListaModulosLaboratorios() {
        return listaModulosLaboratorios;
    }

    public void setListaModulosLaboratorios(List<ModuloLaboratorio> listaModulosLaboratorios) {
        this.listaModulosLaboratorios = listaModulosLaboratorios;
    }

    public ModuloLaboratorio getParametroModuloLaboratorio() {
        return parametroModuloLaboratorio;
    }

    public void setParametroModuloLaboratorio(ModuloLaboratorio parametroModuloLaboratorio) {
        this.parametroModuloLaboratorio = parametroModuloLaboratorio;
    }

    public List<TipoActivo> getListaTiposActivos() {
        if (listaTiposActivos == null) {
            listaTiposActivos = gestionarPlantaEquiposElementosBO.consultarTiposActivosRegistrador();
        }
        return listaTiposActivos;
    }

    public void setListaTiposActivos(List<TipoActivo> listaTiposActivos) {
        this.listaTiposActivos = listaTiposActivos;
    }

    public TipoActivo getParametroTipoActivo() {
        return parametroTipoActivo;
    }

    public void setParametroTipoActivo(TipoActivo parametroTipoActivo) {
        this.parametroTipoActivo = parametroTipoActivo;
    }

    public List<Proveedor> getListaProveedores() {
        if (listaProveedores == null) {
            listaProveedores = gestionarPlantaEquiposElementosBO.consultarProveedoresRegistrados();
        }
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Proveedor getParametroProveedor() {
        return parametroProveedor;
    }

    public void setParametroProveedor(Proveedor parametroProveedor) {
        this.parametroProveedor = parametroProveedor;
    }

    public List<EstadoEquipo> getListaEstadosEquipos() {
        if (listaEstadosEquipos == null) {
            listaEstadosEquipos = gestionarPlantaEquiposElementosBO.consultarEstadosEquiposRegistrados();
        }
        return listaEstadosEquipos;
    }

    public void setListaEstadosEquipos(List<EstadoEquipo> listaEstadosEquipos) {
        this.listaEstadosEquipos = listaEstadosEquipos;
    }

    public EstadoEquipo getParametroEstadoEquipo() {
        return parametroEstadoEquipo;
    }

    public void setParametroEstadoEquipo(EstadoEquipo parametroEstadoEquipo) {
        this.parametroEstadoEquipo = parametroEstadoEquipo;
    }

    public boolean isActivarAreaProfundizacion() {
        return activarAreaProfundizacion;
    }

    public void setActivarAreaProfundizacion(boolean activarAreaProfundizacion) {
        this.activarAreaProfundizacion = activarAreaProfundizacion;
    }

    public boolean isActivarSalaLaboratorio() {
        return activarSalaLaboratorio;
    }

    public void setActivarSalaLaboratorio(boolean activarSalaLaboratorio) {
        this.activarSalaLaboratorio = activarSalaLaboratorio;
    }

    public boolean isActivarModuloLaboratorio() {
        return activarModuloLaboratorio;
    }

    public void setActivarModuloLaboratorio(boolean activarModuloLaboratorio) {
        this.activarModuloLaboratorio = activarModuloLaboratorio;
    }

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<EquipoElemento> getListaEquiposElementos() {
        return listaEquiposElementos;
    }

    public void setListaEquiposElementos(List<EquipoElemento> listaEquiposElementos) {
        this.listaEquiposElementos = listaEquiposElementos;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<EquipoElemento> getListaEquiposElementosTabla() {
        return listaEquiposElementosTabla;
    }

    public void setListaEquiposElementosTabla(List<EquipoElemento> listaEquiposElementosTabla) {
        this.listaEquiposElementosTabla = listaEquiposElementosTabla;
    }

    public boolean isBloquearPagSigEquipo() {
        return bloquearPagSigEquipo;
    }

    public void setBloquearPagSigEquipo(boolean bloquearPagSigEquipo) {
        this.bloquearPagSigEquipo = bloquearPagSigEquipo;
    }

    public boolean isBloquearPagAntEquipo() {
        return bloquearPagAntEquipo;
    }

    public void setBloquearPagAntEquipo(boolean bloquearPagAntEquipo) {
        this.bloquearPagAntEquipo = bloquearPagAntEquipo;
    }

    public boolean isPerfilConsulta() {
        return perfilConsulta;
    }

    public void setPerfilConsulta(boolean perfilConsulta) {
        this.perfilConsulta = perfilConsulta;
    }

    public String getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(String cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public boolean isActivarLabArea() {
        return activarLabArea;
    }

    public void setActivarLabArea(boolean activarLabArea) {
        this.activarLabArea = activarLabArea;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

}
