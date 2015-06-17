package com.sirelab.controller.estructura_universidad;

import com.sirelab.bo.interfacebo.GestionarAsignaturasBOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.PlanEstudios;
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

/**
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarAsignaturas implements Serializable {

    @EJB
    GestionarAsignaturasBOInterface gestionarAsignaturasBO;

    private String parametroNombre, parametroCreditos, parametroCodigo;
    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private List<Carrera> listaCarreras;
    private Carrera parametroCarrera;
    private List<PlanEstudios> listaPlanesEstudios;
    private PlanEstudios parametroPlanEstudio;
    private boolean activarDepartamento;
    private boolean activarCarrera;
    private boolean activarPlanEstudio;
    private boolean activarNuevoDepartamento;
    private boolean activarNuevoCarrera;
    private boolean activarNuevoPlanEstudio;
    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<Asignatura> listaAsignaturas;
    private List<Asignatura> listaAsignaturasTabla;
    private int posicionAsignaturaTabla;
    private int tamTotalAsignatura;
    private boolean bloquearPagSigAsignatura, bloquearPagAntAsignatura;
    //
    private String altoTabla;

    public ControllerAdministrarAsignaturas() {
    }

    @PostConstruct
    public void init() {
        activarDepartamento = true;
        activarCarrera = true;
        activarPlanEstudio = true;
        activarNuevoDepartamento = true;
        activarNuevoCarrera = true;
        activarNuevoPlanEstudio = true;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroCreditos = null;
        parametroDepartamento = new Departamento();
        parametroCarrera = new Carrera();
        parametroPlanEstudio = new PlanEstudios();
        altoTabla = "150";
        inicializarFiltros();
        listaPlanesEstudios = null;
        listaDepartamentos = null;
        listaCarreras = null;
        listaAsignaturas = null;
        listaAsignaturasTabla = null;
        posicionAsignaturaTabla = 0;
        tamTotalAsignatura = 0;
        bloquearPagAntAsignatura = true;
        bloquearPagSigAsignatura = true;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroPlanEstudio", null);
        filtros.put("parametroCarrera", null);
        filtros.put("parametroFacultad", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty())) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if ((Utilidades.validarNulo(parametroCreditos) == true) && (!parametroCreditos.isEmpty())) {
            filtros.put("parametroCreditos", parametroCreditos.toString());
        }
        if (Utilidades.validarNulo(parametroDepartamento) == true) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
            }
        }
        if (Utilidades.validarNulo(parametroCarrera) == true) {
            if (parametroCarrera.getIdcarrera() != null) {
                filtros.put("parametroCarrera", parametroCarrera.getIdcarrera().toString());
            }
        }
        if (Utilidades.validarNulo(parametroPlanEstudio) == true) {
            if (parametroPlanEstudio.getIdplanestudios() != null) {
                filtros.put("parametroPlanEstudio", parametroPlanEstudio.getIdplanestudios().toString());
            }
        }
    }

    public void buscarAsignaturasPorParametros() {
        try {
            inicializarFiltros();
            listaAsignaturas = null;
            listaAsignaturas = gestionarAsignaturasBO.consultarAsignaturasPorParametro(filtros);
            if (listaAsignaturas != null) {
                if (listaAsignaturas.size() > 0) {
                    activarExport = false;
                    listaAsignaturasTabla = new ArrayList<Asignatura>();
                    tamTotalAsignatura = listaAsignaturas.size();
                    posicionAsignaturaTabla = 0;
                    cargarDatosTablaAsignatura();
                } else {
                    activarExport = true;
                    listaAsignaturasTabla = null;
                    tamTotalAsignatura = 0;
                    posicionAsignaturaTabla = 0;
                    bloquearPagAntAsignatura = true;
                    bloquearPagSigAsignatura = true;
                }
            } else {
                listaAsignaturasTabla = null;
                tamTotalAsignatura = 0;
                posicionAsignaturaTabla = 0;
                bloquearPagAntAsignatura = true;
                bloquearPagSigAsignatura = true;
            }
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarAsignaturas buscarAsignaturasPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaAsignatura() {
        if (tamTotalAsignatura < 10) {
            for (int i = 0; i < tamTotalAsignatura; i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = true;
            bloquearPagAntAsignatura = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = true;
        }
    }

    public void cargarPaginaSiguienteAsignatura() {
        listaAsignaturasTabla = new ArrayList<Asignatura>();
        posicionAsignaturaTabla = posicionAsignaturaTabla + 10;
        int diferencia = tamTotalAsignatura - posicionAsignaturaTabla;
        if (diferencia > 10) {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = false;
        } else {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + diferencia); i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = true;
            bloquearPagAntAsignatura = false;
        }
    }

    public void cargarPaginaAnteriorAsignatura() {
        listaAsignaturasTabla = new ArrayList<Asignatura>();
        posicionAsignaturaTabla = posicionAsignaturaTabla - 10;
        int diferencia = tamTotalAsignatura - posicionAsignaturaTabla;
        if (diferencia == tamTotalAsignatura) {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = true;
        } else {
            for (int i = posicionAsignaturaTabla; i < (posicionAsignaturaTabla + 10); i++) {
                listaAsignaturasTabla.add(listaAsignaturas.get(i));
            }
            bloquearPagSigAsignatura = false;
            bloquearPagAntAsignatura = false;
        }
    }

    public void limpiarProcesoBusqueda() {
        activarDepartamento = true;
        activarCarrera = true;
        activarPlanEstudio = true;
        activarExport = true;
        parametroNombre = null;
        parametroCreditos = null;
        parametroDepartamento = new Departamento();
        parametroCarrera = new Carrera();
        parametroPlanEstudio = new PlanEstudios();
        inicializarFiltros();
        listaDepartamentos = null;
        listaCarreras = null;
        listaPlanesEstudios = null;
        listaAsignaturas = null;
        listaAsignaturasTabla = null;
        posicionAsignaturaTabla = 0;
        tamTotalAsignatura = 0;
        bloquearPagAntAsignatura = true;
        bloquearPagSigAsignatura = true;
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(parametroDepartamento)) {
            parametroCarrera = new Carrera();
            listaCarreras = gestionarAsignaturasBO.consultarCarrerasPorIDDepartamento(parametroDepartamento.getIddepartamento());
            activarCarrera = false;
            listaPlanesEstudios = null;
            parametroPlanEstudio = new PlanEstudios();
            activarPlanEstudio = true;
        } else {
            activarCarrera = true;
            listaCarreras = null;
            parametroCarrera = new Carrera();
            listaPlanesEstudios = null;
            parametroPlanEstudio = new PlanEstudios();
            activarPlanEstudio = true;
        }
    }

    public void actualizarCarreras() {
        if (Utilidades.validarNulo(parametroCarrera)) {
            parametroPlanEstudio = new PlanEstudios();
            listaPlanesEstudios = gestionarAsignaturasBO.consultarPlanesEstudiosPorIDCarrera(parametroCarrera.getIdcarrera());
            activarPlanEstudio = false;
        } else {
            listaPlanesEstudios = null;
            parametroPlanEstudio = new PlanEstudios();
            activarPlanEstudio = true;
        }
    }


    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "Gestionar_PlanesEstudios_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Gestionar_PlanesEstudios_XLS", false, false, "UTF-8", null, null);
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

    public String getParametroCreditos() {
        return parametroCreditos;
    }

    public void setParametroCreditos(String parametroCreditos) {
        this.parametroCreditos = parametroCreditos;
    }

    public String getParametroCodigo() {
        return parametroCodigo;
    }

    public void setParametroCodigo(String parametroCodigo) {
        this.parametroCodigo = parametroCodigo;
    }

    public List<Departamento> getListaDepartamentos() {
        if (listaDepartamentos == null) {
            listaDepartamentos = gestionarAsignaturasBO.consultarDepartamentosRegistrados();
        }
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

    public List<Carrera> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public Carrera getParametroCarrera() {
        return parametroCarrera;
    }

    public void setParametroCarrera(Carrera parametroCarrera) {
        this.parametroCarrera = parametroCarrera;
    }

    public List<PlanEstudios> getListaPlanesEstudios() {
        return listaPlanesEstudios;
    }

    public void setListaPlanesEstudios(List<PlanEstudios> listaPlanesEstudios) {
        this.listaPlanesEstudios = listaPlanesEstudios;
    }

    public PlanEstudios getParametroPlanEstudio() {
        return parametroPlanEstudio;
    }

    public void setParametroPlanEstudio(PlanEstudios parametroPlanEstudio) {
        this.parametroPlanEstudio = parametroPlanEstudio;
    }

    public boolean isActivarDepartamento() {
        return activarDepartamento;
    }

    public void setActivarDepartamento(boolean activarDepartamento) {
        this.activarDepartamento = activarDepartamento;
    }

    public boolean isActivarCarrera() {
        return activarCarrera;
    }

    public void setActivarCarrera(boolean activarCarrera) {
        this.activarCarrera = activarCarrera;
    }

    public boolean isActivarPlanEstudio() {
        return activarPlanEstudio;
    }

    public void setActivarPlanEstudio(boolean activarPlanEstudio) {
        this.activarPlanEstudio = activarPlanEstudio;
    }

    public boolean isActivarNuevoDepartamento() {
        return activarNuevoDepartamento;
    }

    public void setActivarNuevoDepartamento(boolean activarNuevoDepartamento) {
        this.activarNuevoDepartamento = activarNuevoDepartamento;
    }

    public boolean isActivarNuevoCarrera() {
        return activarNuevoCarrera;
    }

    public void setActivarNuevoCarrera(boolean activarNuevoCarrera) {
        this.activarNuevoCarrera = activarNuevoCarrera;
    }

    public boolean isActivarNuevoPlanEstudio() {
        return activarNuevoPlanEstudio;
    }

    public void setActivarNuevoPlanEstudio(boolean activarNuevoPlanEstudio) {
        this.activarNuevoPlanEstudio = activarNuevoPlanEstudio;
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

    public List<Asignatura> getListaAsignaturas() {
        return listaAsignaturas;
    }

    public void setListaAsignaturas(List<Asignatura> listaAsignaturas) {
        this.listaAsignaturas = listaAsignaturas;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<Asignatura> getListaAsignaturasTabla() {
        return listaAsignaturasTabla;
    }

    public void setListaAsignaturasTabla(List<Asignatura> listaAsignaturasTabla) {
        this.listaAsignaturasTabla = listaAsignaturasTabla;
    }

    public boolean isBloquearPagSigAsignatura() {
        return bloquearPagSigAsignatura;
    }

    public void setBloquearPagSigAsignatura(boolean bloquearPagSigAsignatura) {
        this.bloquearPagSigAsignatura = bloquearPagSigAsignatura;
    }

    public boolean isBloquearPagAntAsignatura() {
        return bloquearPagAntAsignatura;
    }

    public void setBloquearPagAntAsignatura(boolean bloquearPagAntAsignatura) {
        this.bloquearPagAntAsignatura = bloquearPagAntAsignatura;
    }

}
