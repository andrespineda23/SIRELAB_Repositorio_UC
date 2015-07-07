/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaLaboratoriosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
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

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarLaboratorios implements Serializable {

    @EJB
    GestionarPlantaLaboratoriosBOInterface gestionarPlantaLaboratoriosBO;

    private String parametroNombre, parametroCodigo;
    private List<Facultad> listaFacultades;
    private Facultad parametroFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private boolean activarDepartamento;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<Laboratorio> listaLaboratorios;
    private List<Laboratorio> listaLaboratoriosTabla;
    private int posicionLaboratorioTabla;
    private int tamTotalLaboratorio;
    private boolean bloquearPagSigLaboratorio, bloquearPagAntLaboratorio;
    //
    private String altoTabla;
    //
    private String paginaAnterior;
    private UsuarioLogin usuarioLoginSistema;
    //
    private boolean perfilConsulta;

    public ControllerAdministrarLaboratorios() {
    }

    @PostConstruct
    public void init() {
        perfilConsulta = false;
        activarDepartamento = true;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroFacultad = new Facultad();
        parametroDepartamento = new Departamento();
        altoTabla = "150";
        inicializarFiltros();
        listaLaboratoriosTabla = null;
        listaLaboratorios = null;
        posicionLaboratorioTabla = 0;
        tamTotalLaboratorio = 0;
        bloquearPagAntLaboratorio = true;
        bloquearPagSigLaboratorio = true;
    }

    private void cargarInformacionPerfil() {
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            EncargadoLaboratorio encargadoUser = gestionarPlantaLaboratoriosBO.obtenerEncargadoLaboratorioPorID(usuarioLoginSistema.getIdUsuarioLogin());
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
        cargarInformacionPerfil();
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroFacultad", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty())) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if (Utilidades.validarNulo(parametroFacultad) == true) {
            if (parametroFacultad.getIdfacultad() != null) {
                filtros.put("parametroFacultad", parametroFacultad.getIdfacultad().toString());
            }
        }
        if (Utilidades.validarNulo(parametroDepartamento) == true) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
            }
        }
    }

    public void buscarLaboratoriosPorParametros() {
        try {
            inicializarFiltros();
            listaLaboratorios = null;
            listaLaboratorios = gestionarPlantaLaboratoriosBO.consultarLaboratoriosPorParametro(filtros);
            if (listaLaboratorios != null) {
                if (listaLaboratorios.size() > 0) {
                    activarExport = false;
                    listaLaboratoriosTabla = new ArrayList<Laboratorio>();
                    tamTotalLaboratorio = listaLaboratorios.size();
                    posicionLaboratorioTabla = 0;
                    cargarDatosTablaLaboratorio();
                } else {
                    activarExport = true;
                    listaLaboratoriosTabla = null;
                    tamTotalLaboratorio = 0;
                    posicionLaboratorioTabla = 0;
                    bloquearPagAntLaboratorio = true;
                    bloquearPagSigLaboratorio = true;
                }
            } else {
                listaLaboratoriosTabla = null;
                tamTotalLaboratorio = 0;
                posicionLaboratorioTabla = 0;
                bloquearPagAntLaboratorio = true;
                bloquearPagSigLaboratorio = true;
            }
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarPlantaLaboratorios buscarLaboratoriosPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaLaboratorio() {
        if (tamTotalLaboratorio < 10) {
            for (int i = 0; i < tamTotalLaboratorio; i++) {
                listaLaboratoriosTabla.add(listaLaboratorios.get(i));
            }
            bloquearPagSigLaboratorio = true;
            bloquearPagAntLaboratorio = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaLaboratoriosTabla.add(listaLaboratorios.get(i));
            }
            bloquearPagSigLaboratorio = false;
            bloquearPagAntLaboratorio = true;
        }
    }

    public void cargarPaginaSiguienteLaboratorio() {
        listaLaboratoriosTabla = new ArrayList<Laboratorio>();
        posicionLaboratorioTabla = posicionLaboratorioTabla + 10;
        int diferencia = tamTotalLaboratorio - posicionLaboratorioTabla;
        if (diferencia > 10) {
            for (int i = posicionLaboratorioTabla; i < (posicionLaboratorioTabla + 10); i++) {
                listaLaboratoriosTabla.add(listaLaboratorios.get(i));
            }
            bloquearPagSigLaboratorio = false;
            bloquearPagAntLaboratorio = false;
        } else {
            for (int i = posicionLaboratorioTabla; i < (posicionLaboratorioTabla + diferencia); i++) {
                listaLaboratoriosTabla.add(listaLaboratorios.get(i));
            }
            bloquearPagSigLaboratorio = true;
            bloquearPagAntLaboratorio = false;
        }
    }

    public void cargarPaginaAnteriorLaboratorio() {
        listaLaboratoriosTabla = new ArrayList<Laboratorio>();
        posicionLaboratorioTabla = posicionLaboratorioTabla - 10;
        int diferencia = tamTotalLaboratorio - posicionLaboratorioTabla;
        if (diferencia == tamTotalLaboratorio) {
            for (int i = posicionLaboratorioTabla; i < (posicionLaboratorioTabla + 10); i++) {
                listaLaboratoriosTabla.add(listaLaboratorios.get(i));
            }
            bloquearPagSigLaboratorio = false;
            bloquearPagAntLaboratorio = true;
        } else {
            for (int i = posicionLaboratorioTabla; i < (posicionLaboratorioTabla + 10); i++) {
                listaLaboratoriosTabla.add(listaLaboratorios.get(i));
            }
            bloquearPagSigLaboratorio = false;
            bloquearPagAntLaboratorio = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarDepartamento = true;
        parametroCodigo = null;
        activarExport = true;
        parametroNombre = null;
        parametroDepartamento = new Departamento();
        parametroFacultad = new Facultad();
        inicializarFiltros();
        listaDepartamentos = null;
        listaLaboratorios = null;
        listaLaboratoriosTabla = null;
        posicionLaboratorioTabla = 0;
        tamTotalLaboratorio = 0;
        bloquearPagAntLaboratorio = true;
        bloquearPagSigLaboratorio = true;
        return paginaAnterior;
    }

    public void actualizarFacultades() {
        if (Utilidades.validarNulo(parametroFacultad)) {
            parametroDepartamento = new Departamento();
            listaDepartamentos = gestionarPlantaLaboratoriosBO.consultarDepartamentosPorIDFacultad(parametroFacultad.getIdfacultad());
            activarDepartamento = false;
        } else {
            parametroDepartamento = new Departamento();
            listaDepartamentos = null;
            activarDepartamento = true;
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
    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
    }

    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = gestionarPlantaLaboratoriosBO.consultarFacultadesRegistradas();
        }
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Facultad getParametroFacultad() {
        return parametroFacultad;
    }

    public void setParametroFacultad(Facultad parametroFacultad) {
        this.parametroFacultad = parametroFacultad;
    }

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

    public boolean isActivarDepartamento() {
        return activarDepartamento;
    }

    public void setActivarDepartamento(boolean activarDepartamento) {
        this.activarDepartamento = activarDepartamento;
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

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getParametroCodigo() {
        return parametroCodigo;
    }

    public void setParametroCodigo(String parametroCodigo) {
        this.parametroCodigo = parametroCodigo;
    }

    public List<Laboratorio> getListaLaboratoriosTabla() {
        return listaLaboratoriosTabla;
    }

    public void setListaLaboratoriosTabla(List<Laboratorio> listaLaboratoriosTabla) {
        this.listaLaboratoriosTabla = listaLaboratoriosTabla;
    }

    public boolean isBloquearPagSigLaboratorio() {
        return bloquearPagSigLaboratorio;
    }

    public void setBloquearPagSigLaboratorio(boolean bloquearPagSigLaboratorio) {
        this.bloquearPagSigLaboratorio = bloquearPagSigLaboratorio;
    }

    public boolean isBloquearPagAntLaboratorio() {
        return bloquearPagAntLaboratorio;
    }

    public void setBloquearPagAntLaboratorio(boolean bloquearPagAntLaboratorio) {
        this.bloquearPagAntLaboratorio = bloquearPagAntLaboratorio;
    }

    public boolean isPerfilConsulta() {
        return perfilConsulta;
    }

    public void setPerfilConsulta(boolean perfilConsulta) {
        this.perfilConsulta = perfilConsulta;
    }

}
