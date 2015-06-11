package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.AdministrarEstudiantesBOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Controlador: ControllerAdministrarEstudiantes Este controlador se encarga de
 * las operaciones de la pagina administrar_estudiantes.xhtml
 *
 * @author ANDRES PINEDA
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarEstudiantes implements Serializable {

    @EJB
    AdministrarEstudiantesBOInterface administrarEstudiantesBO;

    private String parametroNombre, parametroApellido, parametroDocumento, parametroCorreo, parametroSemestre;
    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private List<Carrera> listaCarreras;
    private Carrera parametroCarrera;
    private List<PlanEstudios> listaPlanesEstudios;
    private PlanEstudios parametroPlanEst;
    private int parametroEstado, parametroTipo;
    private Map<String, String> filtros;
    //
    private boolean activoCarrera, activoPlan;
    //
    private boolean activarExport;
    //
    private List<Estudiante> listaEstudiantes;
    private List<Estudiante> filtrarListaEstudiantes;

    public ControllerAdministrarEstudiantes() {
    }

    @PostConstruct
    public void init() {
        activarExport = true;
        activoCarrera = true;
        activoPlan = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroSemestre = null;
        parametroDepartamento = null;
        parametroCarrera = null;
        parametroPlanEst = null;
        parametroEstado = 1;
        listaDepartamentos = administrarEstudiantesBO.obtenerListasDepartamentos();
        inicializarFiltros();
        listaEstudiantes = null;
        filtrarListaEstudiantes = null;
    }

    /**
     * Metodo encargado de inicializar los filtros de busqueda para el proceso
     * de consulta de estudiantes
     */
    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroApellido", null);
        filtros.put("parametroDocumento", null);
        filtros.put("parametroCorreo", null);
        filtros.put("parametroSemestre", null);
        filtros.put("parametroTipo", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroCarrera", null);
        filtros.put("parametroPlanEst", null);
        agregarFiltrosAdicionales();
    }

    /**
     * Metodo encargado de agregar los valores al filtro de busqueda
     */
    private void agregarFiltrosAdicionales() {
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre);
        }
        if ((Utilidades.validarNulo(parametroApellido) == true) && (!parametroApellido.isEmpty())) {
            filtros.put("parametroApellido", parametroApellido);
        }
        if ((Utilidades.validarNulo(parametroDocumento) == true) && (!parametroDocumento.isEmpty())) {
            filtros.put("parametroDocumento", parametroDocumento);
        }
        if ((Utilidades.validarNulo(parametroCorreo) == true) && (!parametroCorreo.isEmpty())) {
            filtros.put("parametroCorreo", parametroCorreo + "@ucentral.edu.co");
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
        if (parametroTipo == 1 || parametroTipo == 2) {
            filtros.put("parametroTipo", String.valueOf(parametroTipo));
        }
        if (Utilidades.validarNulo(parametroDepartamento)) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
            }
        }
        if (Utilidades.validarNulo(parametroCarrera)) {
            if (parametroCarrera.getIdcarrera() != null) {
                filtros.put("parametroCarrera", parametroCarrera.getIdcarrera().toString());
            }
        }
        if (Utilidades.validarNulo(parametroPlanEst)) {
            if (parametroPlanEst.getIdplanestudios() != null) {
                filtros.put("parametroPlanEst", parametroPlanEst.getIdplanestudios().toString());
            }
        }
        if ((Utilidades.validarNulo(parametroSemestre)) && (!parametroSemestre.isEmpty())) {
            filtros.put("parametroSemestre", String.valueOf(parametroSemestre));
        }
    }

    /**
     * Metodo encargado de buscar los estudiantes por medio de los parametros
     * ingresados por el usuario
     */
    public void buscarEstudiantesPorParametros() {
        try {
            inicializarFiltros();
            listaEstudiantes = null;
            listaEstudiantes = administrarEstudiantesBO.consultarEstudiantesPorParametro(filtros);
            if (listaEstudiantes != null) {
                if (listaEstudiantes.size() > 0) {
                    activarExport = false;
                } else {
                    activarExport = true;
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta no ha retornado ningun resultado de busqueda.", "Consulta de Estudiantes");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage("message", message);
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "La consulta no ha retornado ningun resultado de busqueda.", "Consulta de Estudiantes");
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("message", message);
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarEstudiantes buscarEstudiantesPorParametros : " + e.toString());
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public void limpiarProcesoBusqueda() {
        activarExport = true;
        activoCarrera = true;
        activoPlan = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroSemestre = null;
        parametroDepartamento = null;
        parametroCarrera = null;
        parametroPlanEst = null;
        parametroEstado = 1;
        parametroTipo = 1;
        inicializarFiltros();
        listaEstudiantes = null;
    }

    /**
     * Metodo encargado de actualizar los departamentos
     */
    public void actualizarDepartamentos() {
        try {
            if (Utilidades.validarNulo(parametroDepartamento)) {
                activoCarrera = false;
                parametroCarrera = new Carrera();
                listaCarreras = administrarEstudiantesBO.obtenerListasCarrerasPorDepartamento(parametroDepartamento.getIddepartamento());
                activoPlan = true;
                parametroPlanEst = new PlanEstudios();
                listaPlanesEstudios = null;
            } else {
                activoCarrera = true;
                activoPlan = true;
                listaCarreras = null;
                parametroCarrera = new Carrera();
                listaPlanesEstudios = null;
                parametroPlanEst = new PlanEstudios();
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarEstudiantes actualizarDepartamentos : " + e.toString());
        }
    }

    /**
     * Metodo encargado de actualizar las carreras
     */
    public void actualizarCarreras() {
        try {
            if (Utilidades.validarNulo(parametroCarrera)) {
                activoPlan = false;
                parametroPlanEst = new PlanEstudios();
                listaPlanesEstudios = administrarEstudiantesBO.obtenerListasPlanesEstudioPorCarrera(parametroCarrera.getIdcarrera());
            } else {
                activoPlan = true;
                listaPlanesEstudios = null;
                parametroPlanEst = new PlanEstudios();
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarEstudiantes actualizarCarreras : " + e.toString());
        }
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un estudiante
     */
    public String verDetallesEstudiante() {
        limpiarProcesoBusqueda();
        return "detalles_estudiante";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "Administrar_Estudiantes_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Administrar_Estudiantes_XLS", false, false, "UTF-8", null, null);
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

    public String getParametroApellido() {
        return parametroApellido;
    }

    public void setParametroApellido(String parametroApellido) {
        this.parametroApellido = parametroApellido;
    }

    public String getParametroDocumento() {
        return parametroDocumento;
    }

    public void setParametroDocumento(String parametroDocumento) {
        this.parametroDocumento = parametroDocumento;
    }

    public String getParametroCorreo() {
        return parametroCorreo;
    }

    public void setParametroCorreo(String parametroCorreo) {
        this.parametroCorreo = parametroCorreo;
    }

    public String getParametroSemestre() {
        return parametroSemestre;
    }

    public void setParametroSemestre(String parametroSemestre) {
        this.parametroSemestre = parametroSemestre;
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

    public PlanEstudios getParametroPlanEst() {
        return parametroPlanEst;
    }

    public void setParametroPlanEst(PlanEstudios parametroPlanEst) {
        this.parametroPlanEst = parametroPlanEst;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

    public Map<String, String> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, String> filtros) {
        this.filtros = filtros;
    }

    public List<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(List<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public List<Estudiante> getFiltrarListaEstudiantes() {
        return filtrarListaEstudiantes;
    }

    public void setFiltrarListaEstudiantes(List<Estudiante> filtrarListaEstudiantes) {
        this.filtrarListaEstudiantes = filtrarListaEstudiantes;
    }

    public boolean isActivoCarrera() {
        return activoCarrera;
    }

    public void setActivoCarrera(boolean activoCarrera) {
        this.activoCarrera = activoCarrera;
    }

    public boolean isActivoPlan() {
        return activoPlan;
    }

    public void setActivoPlan(boolean activoPlan) {
        this.activoPlan = activoPlan;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public int getParametroTipo() {
        return parametroTipo;
    }

    public void setParametroTipo(int parametroTipo) {
        this.parametroTipo = parametroTipo;
    }

}
