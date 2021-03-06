/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaModulosBOInterface;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
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

    public ControllerAdministrarModulos() {
    }

    @PostConstruct
    public void init() {
        activarSala = true;
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
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            EncargadoLaboratorio encargadoUser = gestionarPlantaModulosBO.obtenerEncargadoLaboratorioPorID(usuarioLoginSistema.getIdUsuarioLogin());
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
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty())) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if ((Utilidades.validarNulo(parametroDetalle) == true) && (!parametroDetalle.isEmpty())) {
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
                    cargarDatosTablaModulo();
                } else {
                    activarExport = true;
                    listaModulosLaboratoriosTabla = null;
                    tamTotalModulo = 0;
                    posicionModuloTabla = 0;
                    bloquearPagAntModulo = true;
                    bloquearPagSigModulo = true;
                }
            } else {
                listaModulosLaboratoriosTabla = null;
                tamTotalModulo = 0;
                posicionModuloTabla = 0;
                bloquearPagAntModulo = true;
                bloquearPagSigModulo = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaModulos buscarModulosLaboratorioPorParametros:  "+e.toString());
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
        return paginaAnterior;
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
     exporter.export(context, tabla, "Plata_Modulos_Laboratorio_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Plata_Modulos_Laboratorio_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    public String cambiarPaginaDetalles() {
        limpiarProcesoBusqueda();
        return "detalles_modulo";
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

}
