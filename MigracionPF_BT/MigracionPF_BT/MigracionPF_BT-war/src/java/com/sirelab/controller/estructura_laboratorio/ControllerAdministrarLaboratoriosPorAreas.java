/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaLaboratoriosPorAreasBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.LaboratoriosPorAreas;
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
public class ControllerAdministrarLaboratoriosPorAreas implements Serializable {

    @EJB
    GestionarPlantaLaboratoriosPorAreasBOInterface gestionarPlantaLaboratoriosPorAreasBO;

    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio parametroLaboratorio;
    private boolean activarLaboratorio;
    private List<AreaProfundizacion> listaAreasProfundizacion;
    private AreaProfundizacion parametroArea;
    //
    private Map<String, String> filtros;
    //
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreas;
    private List<LaboratoriosPorAreas> listaLaboratoriosPorAreasTabla;
    private int posicionLaboratorioPorAreaTabla;
    private int tamTotalLaboratorioPorArea;
    private boolean bloquearPagSigLaboratorioPorArea, bloquearPagAntLaboratorioPorArea;
    //
    private String paginaAnterior;
    //
    private UsuarioLogin usuarioLoginSistema;
    private boolean perfilConsulta;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerAdministrarLaboratoriosPorAreas() {
    }

    @PostConstruct
    public void init() {
        activarLaboratorio = true;
        parametroDepartamento = new Departamento();
        parametroLaboratorio = new Laboratorio();
        listaDepartamentos = gestionarPlantaLaboratoriosPorAreasBO.consultarDepartamentosRegistrados();
        listaAreasProfundizacion = gestionarPlantaLaboratoriosPorAreasBO.consultarAreasProfundizacionRegistradas();
        inicializarFiltros();
        listaLaboratorios = null;
        listaLaboratoriosPorAreasTabla = null;
        listaLaboratoriosPorAreas = null;
        posicionLaboratorioPorAreaTabla = 0;
        tamTotalLaboratorioPorArea = 0;
        bloquearPagAntLaboratorioPorArea = true;
        bloquearPagSigLaboratorioPorArea = true;
        BasicConfigurator.configure();
    }

    private void cargarInformacionPerfil() {
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            EncargadoLaboratorio encargadoUser = gestionarPlantaLaboratoriosPorAreasBO.obtenerEncargadoLaboratorioPorID(usuarioLoginSistema.getIdUsuarioLogin());
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
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroLaboratorio", null);
        filtros.put("parametroArea", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if (Utilidades.validarNulo(parametroDepartamento)) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
            }
        }
        if (Utilidades.validarNulo(parametroLaboratorio)) {
            if (parametroLaboratorio.getIdlaboratorio() != null) {
                filtros.put("parametroLaboratorio", parametroLaboratorio.getIdlaboratorio().toString());
            }
        }
        if (Utilidades.validarNulo(parametroArea)) {
            if (parametroArea.getIdareaprofundizacion() != null) {
                filtros.put("parametroArea", parametroArea.getIdareaprofundizacion().toString());
            }
        }
    }

    public void buscarLaboratoriosPorParametros() {
        try {
            inicializarFiltros();
            listaLaboratoriosPorAreas = null;
            listaLaboratoriosPorAreas = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratoriosPorAreasPorParametro(filtros);
            if (listaLaboratoriosPorAreas != null) {
                if (listaLaboratoriosPorAreas.size() > 0) {
                    listaLaboratoriosPorAreasTabla = new ArrayList<LaboratoriosPorAreas>();
                    tamTotalLaboratorioPorArea = listaLaboratoriosPorAreas.size();
                    posicionLaboratorioPorAreaTabla = 0;
                    cargarDatosTablaLaboratorioPorArea();
                } else {
                    listaLaboratoriosPorAreasTabla = null;
                    tamTotalLaboratorioPorArea = 0;
                    posicionLaboratorioPorAreaTabla = 0;
                    bloquearPagAntLaboratorioPorArea = true;
                    bloquearPagSigLaboratorioPorArea = true;
                }
            } else {
                listaLaboratoriosPorAreasTabla = null;
                tamTotalLaboratorioPorArea = 0;
                posicionLaboratorioPorAreaTabla = 0;
                bloquearPagAntLaboratorioPorArea = true;
                bloquearPagSigLaboratorioPorArea = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarLaboratoriosPorAreas buscarLaboratoriosPorParametros:  "+e.toString());
            System.out.println("Error ControllerAdministrarLaboratoriosPorAreas buscarLaboratoriosPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaLaboratorioPorArea() {
        if (tamTotalLaboratorioPorArea < 10) {
            for (int i = 0; i < tamTotalLaboratorioPorArea; i++) {
                listaLaboratoriosPorAreasTabla.add(listaLaboratoriosPorAreas.get(i));
            }
            bloquearPagSigLaboratorioPorArea = true;
            bloquearPagAntLaboratorioPorArea = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaLaboratoriosPorAreasTabla.add(listaLaboratoriosPorAreas.get(i));
            }
            bloquearPagSigLaboratorioPorArea = false;
            bloquearPagAntLaboratorioPorArea = true;
        }
    }

    public void cargarPaginaSiguienteLaboratorioPorArea() {
        listaLaboratoriosPorAreasTabla = new ArrayList<LaboratoriosPorAreas>();
        posicionLaboratorioPorAreaTabla = posicionLaboratorioPorAreaTabla + 10;
        int diferencia = tamTotalLaboratorioPorArea - posicionLaboratorioPorAreaTabla;
        if (diferencia > 10) {
            for (int i = posicionLaboratorioPorAreaTabla; i < (posicionLaboratorioPorAreaTabla + 10); i++) {
                listaLaboratoriosPorAreasTabla.add(listaLaboratoriosPorAreas.get(i));
            }
            bloquearPagSigLaboratorioPorArea = false;
            bloquearPagAntLaboratorioPorArea = false;
        } else {
            for (int i = posicionLaboratorioPorAreaTabla; i < (posicionLaboratorioPorAreaTabla + diferencia); i++) {
                listaLaboratoriosPorAreasTabla.add(listaLaboratoriosPorAreas.get(i));
            }
            bloquearPagSigLaboratorioPorArea = true;
            bloquearPagAntLaboratorioPorArea = false;
        }
    }

    public void cargarPaginaAnteriorLaboratorioPorArea() {
        listaLaboratoriosPorAreasTabla = new ArrayList<LaboratoriosPorAreas>();
        posicionLaboratorioPorAreaTabla = posicionLaboratorioPorAreaTabla - 10;
        int diferencia = tamTotalLaboratorioPorArea - posicionLaboratorioPorAreaTabla;
        if (diferencia == tamTotalLaboratorioPorArea) {
            for (int i = posicionLaboratorioPorAreaTabla; i < (posicionLaboratorioPorAreaTabla + 10); i++) {
                listaLaboratoriosPorAreasTabla.add(listaLaboratoriosPorAreas.get(i));
            }
            bloquearPagSigLaboratorioPorArea = false;
            bloquearPagAntLaboratorioPorArea = true;
        } else {
            for (int i = posicionLaboratorioPorAreaTabla; i < (posicionLaboratorioPorAreaTabla + 10); i++) {
                listaLaboratoriosPorAreasTabla.add(listaLaboratoriosPorAreas.get(i));
            }
            bloquearPagSigLaboratorioPorArea = false;
            bloquearPagAntLaboratorioPorArea = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarLaboratorio = true;
        parametroDepartamento = new Departamento();
        parametroArea = new AreaProfundizacion();
        parametroLaboratorio = new Laboratorio();
        inicializarFiltros();
        listaLaboratorios = null;
        listaLaboratoriosPorAreas = null;
        listaLaboratoriosPorAreasTabla = null;
        posicionLaboratorioPorAreaTabla = 0;
        tamTotalLaboratorioPorArea = 0;
        bloquearPagAntLaboratorioPorArea = true;
        bloquearPagSigLaboratorioPorArea = true;
        return paginaAnterior;
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(parametroDepartamento)) {
            parametroLaboratorio = new Laboratorio();
            listaLaboratorios = gestionarPlantaLaboratoriosPorAreasBO.consultarLaboratoriosPorIDDepartamento(parametroDepartamento.getIddepartamento());
            activarLaboratorio = false;
        } else {
            parametroLaboratorio = new Laboratorio();
            listaLaboratorios = null;
            activarLaboratorio = true;
        }
    }

    /*//EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Gestionar_Planta_Laboratorios_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Gestionar_Planta_Laboratorios_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    // GET - SET
    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getParametroDepartamento() {
        return parametroDepartamento;
    }

    public void setParametroDepartamento(Departamento parametroDepartamento) {
        this.parametroDepartamento = parametroDepartamento;
    }

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public Laboratorio getParametroLaboratorio() {
        return parametroLaboratorio;
    }

    public void setParametroLaboratorio(Laboratorio parametroLaboratorio) {
        this.parametroLaboratorio = parametroLaboratorio;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public List<AreaProfundizacion> getListaAreasProfundizacion() {
        return listaAreasProfundizacion;
    }

    public void setListaAreasProfundizacion(List<AreaProfundizacion> listaAreasProfundizacion) {
        this.listaAreasProfundizacion = listaAreasProfundizacion;
    }

    public AreaProfundizacion getParametroArea() {
        return parametroArea;
    }

    public void setParametroArea(AreaProfundizacion parametroArea) {
        this.parametroArea = parametroArea;
    }

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreas() {
        return listaLaboratoriosPorAreas;
    }

    public void setListaLaboratoriosPorAreas(List<LaboratoriosPorAreas> listaLaboratoriosPorAreas) {
        this.listaLaboratoriosPorAreas = listaLaboratoriosPorAreas;
    }

    public List<LaboratoriosPorAreas> getListaLaboratoriosPorAreasTabla() {
        return listaLaboratoriosPorAreasTabla;
    }

    public void setListaLaboratoriosPorAreasTabla(List<LaboratoriosPorAreas> listaLaboratoriosPorAreasTabla) {
        this.listaLaboratoriosPorAreasTabla = listaLaboratoriosPorAreasTabla;
    }

    public boolean isBloquearPagSigLaboratorioPorArea() {
        return bloquearPagSigLaboratorioPorArea;
    }

    public void setBloquearPagSigLaboratorioPorArea(boolean bloquearPagSigLaboratorioPorArea) {
        this.bloquearPagSigLaboratorioPorArea = bloquearPagSigLaboratorioPorArea;
    }

    public boolean isBloquearPagAntLaboratorioPorArea() {
        return bloquearPagAntLaboratorioPorArea;
    }

    public void setBloquearPagAntLaboratorioPorArea(boolean bloquearPagAntLaboratorioPorArea) {
        this.bloquearPagAntLaboratorioPorArea = bloquearPagAntLaboratorioPorArea;
    }

    public boolean isPerfilConsulta() {
        return perfilConsulta;
    }

    public void setPerfilConsulta(boolean perfilConsulta) {
        this.perfilConsulta = perfilConsulta;
    }

}
