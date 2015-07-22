/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaEquiposElementosBOInterface;
import com.sirelab.entidades.EncargadoLaboratorio;
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

    private String parametroNombre, parametroInventario, parametroMarca, parametroModelo, parametroSerie;
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private LaboratoriosPorAreas parametroLaboratorioPorArea;
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
    private UsuarioLogin usuarioLoginSistema;
    private boolean perfilConsulta;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

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
        parametroLaboratorioPorArea = new LaboratoriosPorAreas();
        listaTiposActivos = null;
        listaEstadosEquipos = null;
        listaProveedores = null;
        listaLaboratoriosPorAreas = null;
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

    private void cargarInformacionPerfil() {
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            EncargadoLaboratorio encargadoUser = gestionarPlantaEquiposElementosBO.obtenerEncargadoLaboratorioPorID(usuarioLoginSistema.getIdUsuarioLogin());
            if ("CONSULTA".equalsIgnoreCase(encargadoUser.getTipoperfil().getNombre())) {
                //proceso de solo consulta de la pagina
                perfilConsulta = true;
            } /*else {
             if ("DEPARTAMENTO".equalsIgnoreCase(encargadoUser.getTipoperfil().getNombre())) {
             Departamento departamento = gestionarPlantaLaboratoriosBO.consultarDepartamentoPorNombre(encargadoUser.getTipoperfil().getNombre());
             if (null != departamento) {
             listaDepartamentos = new ArrayList<Departamento>();
             listaDepartamentos.add(departamento);
             }
             } else {
             if ("LABORATORIO".equalsIgnoreCase(encargadoUser.getTipoperfil().getNombre())) {
             } else {
             if ("AREA PROFUNDIZACION".equalsIgnoreCase(encargadoUser.getTipoperfil().getNombre())) {
             }
             }
             }

             }
             */

        }
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
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
        filtros.put("parametroLaboratorioPorArea", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroInventario) == true) && (!parametroInventario.isEmpty())) {
            filtros.put("parametroInventario", parametroInventario.toString());
        }
        if ((Utilidades.validarNulo(parametroMarca) == true) && (!parametroMarca.isEmpty())) {
            filtros.put("parametroMarca", parametroMarca.toString());
        }
        if ((Utilidades.validarNulo(parametroModelo) == true) && (!parametroModelo.isEmpty())) {
            filtros.put("parametroModelo", parametroModelo.toString());
        }
        if ((Utilidades.validarNulo(parametroSerie) == true) && (!parametroSerie.isEmpty())) {
            filtros.put("parametroSerie", parametroSerie.toString());
        }
        if (Utilidades.validarNulo(parametroLaboratorioPorArea)) {
            if (parametroLaboratorioPorArea.getIdlaboratoriosporareas() != null) {
                filtros.put("parametroLaboratorioPorArea", parametroLaboratorioPorArea.getIdlaboratoriosporareas().toString());
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
            System.out.println("Error ControllerGestionarPlantaEquiposElementos buscarEquiposElementosPorParametros : " + e.toString());
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
        parametroLaboratorioPorArea = new LaboratoriosPorAreas();
        parametroModuloLaboratorio = new ModuloLaboratorio();
        parametroTipoActivo = new TipoActivo();
        parametroEstadoEquipo = new EstadoEquipo();
        parametroProveedor = new Proveedor();

        listaLaboratoriosPorAreas = null;
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
        parametroLaboratorioPorArea = new LaboratoriosPorAreas();
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
    }

    public void actualizarLaboratoriosAreasProfundizacion() {
        if (Utilidades.validarNulo(parametroLaboratorioPorArea)) {
            parametroSalaLaboratorio = new SalaLaboratorio();
            listaSalasLaboratorios = gestionarPlantaEquiposElementosBO.consultarSalasLaboratorioPorIDLaboratorioAreaProfundizacion(parametroLaboratorioPorArea.getIdlaboratoriosporareas());;
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
     exporter.export(context, tabla, "Plata_Equipos_de_Modulo_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Plata_Equipos_de_Modulo_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    public String cambiarPaginaDetalles() {
        limpiarProcesoBusqueda();
        return "detalles_equipo";
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

    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreas() {
        if (listaLaboratoriosPorAreas == null) {
            listaLaboratoriosPorAreas = gestionarPlantaEquiposElementosBO.consultarLaboratoriosPorAreasRegistradas();
        }
        return listaLaboratoriosPorAreas;
    }

    public void setListaLaboratoriosPorAreas(List<LaboratoriosPorAreas> listaLaboratoriosPorAreas) {
        this.listaLaboratoriosPorAreas = listaLaboratoriosPorAreas;
    }

    public LaboratoriosPorAreas getParametroLaboratorioPorArea() {
        return parametroLaboratorioPorArea;
    }

    public void setParametroLaboratorioPorArea(LaboratoriosPorAreas parametroLaboratorioPorArea) {
        this.parametroLaboratorioPorArea = parametroLaboratorioPorArea;
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

}
