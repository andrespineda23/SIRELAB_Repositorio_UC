/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.ayuda.AdministrarPerfil;
import com.sirelab.bo.interfacebo.planta.GestionarPlantaModulosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarModulos implements Serializable {

    @EJB
    GestionarPlantaModulosBOInterface gestionarPlantaModulosBO;

    private String parametroCodigo, parametroDetalle;
    private int parametroEstado;
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private LaboratoriosPorAreas parametroLaboratorioPorArea;
    private boolean activarLabArea;
    private List<SalaLaboratorio> listaSalasLaboratorios;
    private SalaLaboratorio parametroSalaLaboratorio;
    private boolean activarAreaProfundizacion;
    private boolean activarSala;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<ModuloLaboratorio> listaModulosLaboratorios;
    private List<ModuloLaboratorio> listaModulosLaboratoriosTabla;
    private int posicionModuloTabla;
    private int tamTotalModulo;
    private boolean bloquearPagSigModulo, bloquearPagAntModulo;
    //
    private String altoTabla;
    //
    //
    private String paginaAnterior;
    private UsuarioLogin usuarioLoginSistema;
    //
    private boolean perfilConsulta;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private TipoPerfil tipoPerfil;

    public ControllerAdministrarModulos() {
    }

    @PostConstruct
    public void init() {
        activarSala = true;
        cantidadRegistros = "N/A";
        activarAreaProfundizacion = true;
        activarExport = true;
        parametroCodigo = null;
        parametroDetalle = null;
        parametroLaboratorioPorArea = new LaboratoriosPorAreas();
        parametroSalaLaboratorio = new SalaLaboratorio();
        listaLaboratoriosPorAreas = null;
        altoTabla = "150";
        inicializarFiltros();
        listaSalasLaboratorios = null;
        parametroEstado = 1;
        listaModulosLaboratoriosTabla = null;
        listaModulosLaboratorios = null;
        posicionModuloTabla = 0;
        tamTotalModulo = 0;
        bloquearPagAntModulo = true;
        bloquearPagSigModulo = true;
        BasicConfigurator.configure();
    }

    private void cargarInformacionPerfil() {
        UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            perfilConsulta = validarSesionConsulta(usuarioLoginSistema.getIdUsuarioLogin());
            if (perfilConsulta == false) {
                Map<String, Object> datosPerfil = AdministrarPerfil.getInstance().validarSesionAdicionales(tipoPerfil.getNombre(), tipoPerfil.getCodigoregistro());
                if (null != datosPerfil) {
                    if (datosPerfil.containsKey("DEPARTAMENTO") || datosPerfil.containsKey("LABORATORIO")) {
                        if (datosPerfil.containsKey("DEPARTAMENTO")) {
                            Departamento parametroDepartamento = (Departamento) datosPerfil.get("DEPARTAMENTO");
                            listaLaboratoriosPorAreas = gestionarPlantaModulosBO.obtenerLaboratoriosPorAreasPorDepartamento(parametroDepartamento.getIddepartamento());
                        }
                        if (datosPerfil.containsKey("LABORATORIO")) {
                            Laboratorio parametroLaboratorio = (Laboratorio) datosPerfil.get("LABORATORIO");
                            listaLaboratoriosPorAreas = gestionarPlantaModulosBO.obtenerLaboratoriosPorAreasPorLaboratorio(parametroLaboratorio.getIdlaboratorio());
                        }
                    } else {
                        activarLabArea = false;
                    }
                } else {
                    activarLabArea = false;
                }
            }
        } else {
            if ("ADMINISTRADOREDIFICIO".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
                Edificio edificio = obtenerEdificio(usuarioLoginSistema.getIdUsuarioLogin());
                listaSalasLaboratorios = gestionarPlantaModulosBO.consultarSalasLaboratorioPorIDEdificio(edificio.getIdedificio());
                activarLabArea = true;
                parametroLaboratorioPorArea = listaSalasLaboratorios.get(0).getLaboratoriosporareas();
                listaLaboratoriosPorAreas = new ArrayList<LaboratoriosPorAreas>();
                listaLaboratoriosPorAreas.add(parametroLaboratorioPorArea);
            }
        }
    }

    private Edificio obtenerEdificio(BigInteger usuario) {
        Edificio edificio = AdministrarPerfil.getInstance().buscarEdificioPorIdEncargadoEdificio(usuario);
        return edificio;
    }

    private boolean validarSesionConsulta(BigInteger usuario) {
        boolean retorno = false;
        tipoPerfil = AdministrarPerfil.getInstance().buscarTipoPerfilPorIDEncargado(usuario);
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
        filtros.put("parametroCodigo", null);
        filtros.put("parametroDetalle", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroLaboratorioPorArea", null);
        filtros.put("parametroSalaLaboratorio", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if (parametroEstado == 1 || parametroEstado == 2) {
            if (parametroEstado == 1) {
                filtros.put("parametroEstado", "true");
            }
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "true");
            }
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty()) && (parametroCodigo.trim().length() > 0)) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if ((Utilidades.validarNulo(parametroDetalle) == true) && (!parametroDetalle.isEmpty()) && (parametroDetalle.trim().length() > 0)) {
            filtros.put("parametroDetalle", parametroDetalle.toString());
        }
        if (Utilidades.validarNulo(parametroLaboratorioPorArea)) {
            if (parametroLaboratorioPorArea.getIdlaboratoriosporareas() != null) {
                filtros.put("parametroLaboratorioPorArea", parametroLaboratorioPorArea.getIdlaboratoriosporareas().toString());
            }
        }
        if (Utilidades.validarNulo(parametroSalaLaboratorio)) {
            if (parametroSalaLaboratorio.getIdsalalaboratorio() != null) {
                filtros.put("parametroSalaLaboratorio", parametroSalaLaboratorio.getIdsalalaboratorio().toString());
            }
        }
    }

    public void buscarModulosLaboratorioPorParametros() {
        try {
            inicializarFiltros();
            listaModulosLaboratorios = null;
            listaModulosLaboratorios = gestionarPlantaModulosBO.consultarModulosLaboratorioPorParametro(filtros);
            if (listaModulosLaboratorios != null) {
                if (listaModulosLaboratorios.size() > 0) {
                    activarExport = false;
                    listaModulosLaboratoriosTabla = new ArrayList<ModuloLaboratorio>();
                    tamTotalModulo = listaModulosLaboratorios.size();
                    posicionModuloTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalModulo);
                    cargarDatosTablaModulo();
                } else {
                    activarExport = true;
                    listaModulosLaboratoriosTabla = null;
                    tamTotalModulo = 0;
                    posicionModuloTabla = 0;
                    bloquearPagAntModulo = true;
                    cantidadRegistros = String.valueOf(tamTotalModulo);
                    bloquearPagSigModulo = true;
                }
            } else {
                listaModulosLaboratoriosTabla = null;
                tamTotalModulo = 0;
                posicionModuloTabla = 0;
                bloquearPagAntModulo = true;
                cantidadRegistros = String.valueOf(tamTotalModulo);
                bloquearPagSigModulo = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaModulos buscarModulosLaboratorioPorParametros:  " + e.toString());
            System.out.println("Error ControllerGestionarPlantaModulos buscarModulosLaboratorioPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaModulo() {
        if (tamTotalModulo < 10) {
            for (int i = 0; i < tamTotalModulo; i++) {
                listaModulosLaboratoriosTabla.add(listaModulosLaboratorios.get(i));
            }
            bloquearPagSigModulo = true;
            bloquearPagAntModulo = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaModulosLaboratoriosTabla.add(listaModulosLaboratorios.get(i));
            }
            bloquearPagSigModulo = false;
            bloquearPagAntModulo = true;
        }
    }

    public void cargarPaginaSiguienteModulo() {
        listaModulosLaboratoriosTabla = new ArrayList<ModuloLaboratorio>();
        posicionModuloTabla = posicionModuloTabla + 10;
        int diferencia = tamTotalModulo - posicionModuloTabla;
        if (diferencia > 10) {
            for (int i = posicionModuloTabla; i < (posicionModuloTabla + 10); i++) {
                listaModulosLaboratoriosTabla.add(listaModulosLaboratorios.get(i));
            }
            bloquearPagSigModulo = false;
            bloquearPagAntModulo = false;
        } else {
            for (int i = posicionModuloTabla; i < (posicionModuloTabla + diferencia); i++) {
                listaModulosLaboratoriosTabla.add(listaModulosLaboratorios.get(i));
            }
            bloquearPagSigModulo = true;
            bloquearPagAntModulo = false;
        }
    }

    public void cargarPaginaAnteriorModulo() {
        listaModulosLaboratoriosTabla = new ArrayList<ModuloLaboratorio>();
        posicionModuloTabla = posicionModuloTabla - 10;
        int diferencia = tamTotalModulo - posicionModuloTabla;
        if (diferencia == tamTotalModulo) {
            for (int i = posicionModuloTabla; i < (posicionModuloTabla + 10); i++) {
                listaModulosLaboratoriosTabla.add(listaModulosLaboratorios.get(i));
            }
            bloquearPagSigModulo = false;
            bloquearPagAntModulo = true;
        } else {
            for (int i = posicionModuloTabla; i < (posicionModuloTabla + 10); i++) {
                listaModulosLaboratoriosTabla.add(listaModulosLaboratorios.get(i));
            }
            bloquearPagSigModulo = false;
            bloquearPagAntModulo = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarAreaProfundizacion = true;
        cantidadRegistros = "N/A";
        activarSala = true;
        parametroEstado = 1;
        activarExport = true;
        parametroCodigo = null;
        parametroDetalle = null;
        parametroLaboratorioPorArea = new LaboratoriosPorAreas();
        parametroSalaLaboratorio = new SalaLaboratorio();

        listaLaboratoriosPorAreas = null;
        listaSalasLaboratorios = null;

        listaModulosLaboratorios = null;
        listaModulosLaboratoriosTabla = null;
        posicionModuloTabla = 0;
        tamTotalModulo = 0;
        bloquearPagAntModulo = true;
        bloquearPagSigModulo = true;
        inicializarFiltros();
        return paginaAnterior;
    }

    public void limpiarDatos() {
        cantidadRegistros = "N/A";
        activarAreaProfundizacion = true;
        activarSala = true;
        parametroEstado = 1;
        activarExport = true;
        parametroCodigo = null;
        parametroDetalle = null;
        parametroLaboratorioPorArea = new LaboratoriosPorAreas();
        parametroSalaLaboratorio = new SalaLaboratorio();
        listaModulosLaboratorios = null;
        listaModulosLaboratoriosTabla = null;
        posicionModuloTabla = 0;
        tamTotalModulo = 0;
        bloquearPagAntModulo = true;
        bloquearPagSigModulo = true;
        inicializarFiltros();
        cargarInformacionPerfil();
    }

    public void actualizarLaboratoriosAreasProfundizacion() {
        if (Utilidades.validarNulo(parametroLaboratorioPorArea)) {
            parametroSalaLaboratorio = new SalaLaboratorio();
            listaSalasLaboratorios = gestionarPlantaModulosBO.consultarSalasLaboratoriosPorIDLaboratorioArea(parametroLaboratorioPorArea.getIdlaboratoriosporareas());
            activarSala = false;
        } else {
            parametroSalaLaboratorio = new SalaLaboratorio();
            listaSalasLaboratorios = null;
            activarSala = true;
        }
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "PlataModulosLaboratorioPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "PlataModulosLaboratorioXLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    public String cambiarPaginaDetalles() {
        limpiarProcesoBusqueda();
        return "detallesmodulo";
    }

    public String cambiarPaginaEquipoCargado() {
        limpiarProcesoBusqueda();
        return "registrarequipocargado";
    }

    // GET - SET
    public String getParametroCodigo() {
        return parametroCodigo;
    }

    public void setParametroCodigo(String parametroCodigo) {
        this.parametroCodigo = parametroCodigo;
    }

    public String getParametroDetalle() {
        return parametroDetalle;
    }

    public void setParametroDetalle(String parametroDetalle) {
        this.parametroDetalle = parametroDetalle;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreas() {
        if (listaLaboratoriosPorAreas == null) {
            listaLaboratoriosPorAreas = gestionarPlantaModulosBO.consultarLaboratoriosPorAreasRegistradas();
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

    public boolean isActivarAreaProfundizacion() {
        return activarAreaProfundizacion;
    }

    public void setActivarAreaProfundizacion(boolean activarAreaProfundizacion) {
        this.activarAreaProfundizacion = activarAreaProfundizacion;
    }

    public boolean isActivarSala() {
        return activarSala;
    }

    public void setActivarSala(boolean activarSala) {
        this.activarSala = activarSala;
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

    public List<ModuloLaboratorio> getListaModulosLaboratorios() {
        return listaModulosLaboratorios;
    }

    public void setListaModulosLaboratorios(List<ModuloLaboratorio> listaModulosLaboratorios) {
        this.listaModulosLaboratorios = listaModulosLaboratorios;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<ModuloLaboratorio> getListaModulosLaboratoriosTabla() {
        return listaModulosLaboratoriosTabla;
    }

    public void setListaModulosLaboratoriosTabla(List<ModuloLaboratorio> listaModulosLaboratoriosTabla) {
        this.listaModulosLaboratoriosTabla = listaModulosLaboratoriosTabla;
    }

    public boolean isBloquearPagSigModulo() {
        return bloquearPagSigModulo;
    }

    public void setBloquearPagSigModulo(boolean bloquearPagSigModulo) {
        this.bloquearPagSigModulo = bloquearPagSigModulo;
    }

    public boolean isBloquearPagAntModulo() {
        return bloquearPagAntModulo;
    }

    public void setBloquearPagAntModulo(boolean bloquearPagAntModulo) {
        this.bloquearPagAntModulo = bloquearPagAntModulo;
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
