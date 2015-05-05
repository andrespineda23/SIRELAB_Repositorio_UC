/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.GestionarPlantaEquiposElementosBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.EstadoEquipo;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.Proveedor;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.TipoActivo;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerAdminstrarEquipos implements Serializable {

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
    private List<EquipoElemento> filtrarListaEquiposElementos;
    //
    private String altoTabla;

    public ControllerAdminstrarEquipos() {
    }

    @PostConstruct
    public void init() {
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
        listaTiposActivos = gestionarPlantaEquiposElementosBO.consultarTiposActivosRegistrador();
        listaEstadosEquipos = gestionarPlantaEquiposElementosBO.consultarEstadosEquiposRegistrados();
        listaProveedores = gestionarPlantaEquiposElementosBO.consultarProveedoresRegistrados();
        listaLaboratoriosPorAreas = gestionarPlantaEquiposElementosBO.consultarLaboratoriosPorAreasRegistradas();
        altoTabla = "150";
        inicializarFiltros();
        listaModulosLaboratorios = null;
        listaSalasLaboratorios = null;
        filtrarListaEquiposElementos = null;
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
        if (parametroEstadoEquipo.getIdestadoequipo() != null) {
            filtros.put("parametroEstado", parametroEstadoEquipo.getIdestadoequipo().toString());
        }
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
        if (parametroLaboratorioPorArea.getIdlaboratoriosporareas() != null) {
            filtros.put("parametroLaboratorioPorArea", parametroLaboratorioPorArea.getIdlaboratoriosporareas().toString());
        }
        if (parametroSalaLaboratorio.getIdsalalaboratorio() != null) {
            filtros.put("parametroSalaLaboratorio", parametroSalaLaboratorio.getIdsalalaboratorio().toString());
        }
        if (parametroModuloLaboratorio.getIdmodulolaboratorio() != null) {
            filtros.put("parametroModuloLaboratorio", parametroModuloLaboratorio.getIdmodulolaboratorio().toString());
        }
        if (parametroProveedor.getIdproveedor() != null) {
            filtros.put("parametroProveedor", parametroProveedor.getIdproveedor().toString());
        }
        if (parametroTipoActivo.getIdtipoactivo() != null) {
            filtros.put("parametroTipoActivo", parametroTipoActivo.getIdtipoactivo().toString());
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
                } else {
                    activarExport = true;

                }
            }
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarPlantaEquiposElementos buscarEquiposElementosPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarAreaProfundizacion = true;
        activarSalaLaboratorio = true;
        activarModuloLaboratorio = true;
        listaEquiposElementos = null;
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
        inicializarFiltros();
        listaSalasLaboratorios = null;
        listaModulosLaboratorios = null;
    }

    public String dispararPaginaNuevoRegistro() {
        limpiarProcesoBusqueda();
        return "adicionarplantaequipo";
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

    public List<EquipoElemento> getFiltrarListaEquiposElementos() {
        return filtrarListaEquiposElementos;
    }

    public void setFiltrarListaEquiposElementos(List<EquipoElemento> filtrarListaEquiposElementos) {
        this.filtrarListaEquiposElementos = filtrarListaEquiposElementos;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

}
