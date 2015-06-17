package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.AdministrarDocentesBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Facultad;
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
 * Controlador: ControllerAdministrarDocentes Este controlador se encarga del
 * funcionamiento de la pagina administrar_docentes.xhtml
 *
 * @author ANDRES PINEDA
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarDocentes implements Serializable {

    @EJB
    AdministrarDocentesBOInterface administrarDocentesBO;

    private String parametroNombre, parametroApellido, parametroDocumento, parametroCorreo, parametroCargo;
    private int parametroEstado;
    private List<Facultad> listaFacultades;
    private Facultad parametroFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private boolean activarDepartamento;
    private Map<String, String> filtros;
    //
    private boolean activoDepartamento;
    //
    private boolean activarExport;
    //
    private List<Docente> listaDocentes;
    private List<Docente> listaDocentesTabla;
    private int posicionDocenteTabla;
    private int tamTotalDocente;
    private boolean bloquearPagSigDocente, bloquearPagAntDocente;
    //
    private String paginaAnterior;

    public ControllerAdministrarDocentes() {
    }

    @PostConstruct
    public void init() {
        activoDepartamento = true;
        activarExport = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroCargo = null;
        parametroDepartamento = null;
        inicializarFiltros();
        listaDocentes = null;
        listaDocentesTabla = null;
        posicionDocenteTabla = 0;
        tamTotalDocente = 0;
        bloquearPagSigDocente = true;
        bloquearPagAntDocente = true;
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    /**
     * Metodo encargado de inicializar los filtros de busqueda para el proceso
     * de consulta de docentes
     */
    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroApellido", null);
        filtros.put("parametroDocumento", null);
        filtros.put("parametroCorreo", null);
        filtros.put("parametroCargo", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroFacultad", null);
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
        if (Utilidades.validarNulo(parametroDepartamento) == true) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
            }
        }
        if (Utilidades.validarNulo(parametroFacultad) == true) {
            if (parametroFacultad.getIdfacultad() != null) {
                filtros.put("parametroFacultad", parametroFacultad.getIdfacultad().toString());
            }
        }
        if ((Utilidades.validarNulo(parametroCargo)) && (!parametroCargo.isEmpty())) {
            filtros.put("parametroCargo", parametroCargo);
        }
    }

    /**
     * Metodo encargado de buscar los docentes por medio de los parametros
     * ingresados por el usuario
     */
    public void buscarDocentesPorParametros() {
        try {
            inicializarFiltros();
            listaDocentes = null;
            listaDocentes = administrarDocentesBO.consultarDocentesPorParametro(filtros);
            if (listaDocentes != null) {
                if (listaDocentes.size() > 0) {
                    activarExport = false;
                    listaDocentesTabla = new ArrayList<Docente>();
                    tamTotalDocente = listaDocentes.size();
                    posicionDocenteTabla = 0;
                    cargarDatosTablaDocente();
                } else {
                    activarExport = true;
                    listaDocentesTabla = null;
                    tamTotalDocente = 0;
                    posicionDocenteTabla = 0;
                    bloquearPagAntDocente = true;
                    bloquearPagSigDocente = true;
                }
            } else {
                listaDocentesTabla = null;
                tamTotalDocente = 0;
                posicionDocenteTabla = 0;
                bloquearPagAntDocente = true;
                bloquearPagSigDocente = true;
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarDocentes buscarDocentesPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaDocente() {
        if (tamTotalDocente < 10) {
            for (int i = 0; i < tamTotalDocente; i++) {
                listaDocentesTabla.add(listaDocentes.get(i));
            }
            bloquearPagSigDocente = true;
            bloquearPagAntDocente = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaDocentesTabla.add(listaDocentes.get(i));
            }
            bloquearPagSigDocente = false;
            bloquearPagAntDocente = true;
        }
    }

    public void cargarPaginaSiguienteDocente() {
        listaDocentesTabla = new ArrayList<Docente>();
        posicionDocenteTabla = posicionDocenteTabla + 10;
        int diferencia = tamTotalDocente - posicionDocenteTabla;
        if (diferencia > 10) {
            for (int i = posicionDocenteTabla; i < (posicionDocenteTabla + 10); i++) {
                listaDocentesTabla.add(listaDocentes.get(i));
            }
            bloquearPagSigDocente = false;
            bloquearPagAntDocente = false;
        } else {
            for (int i = posicionDocenteTabla; i < (posicionDocenteTabla + diferencia); i++) {
                listaDocentesTabla.add(listaDocentes.get(i));
            }
            bloquearPagSigDocente = true;
            bloquearPagAntDocente = false;
        }
    }

    public void cargarPaginaAnteriorDocente() {
        listaDocentesTabla = new ArrayList<Docente>();
        posicionDocenteTabla = posicionDocenteTabla - 10;
        int diferencia = tamTotalDocente - posicionDocenteTabla;
        if (diferencia == tamTotalDocente) {
            for (int i = posicionDocenteTabla; i < (posicionDocenteTabla + 10); i++) {
                listaDocentesTabla.add(listaDocentes.get(i));
            }
            bloquearPagSigDocente = false;
            bloquearPagAntDocente = true;
        } else {
            for (int i = posicionDocenteTabla; i < (posicionDocenteTabla + 10); i++) {
                listaDocentesTabla.add(listaDocentes.get(i));
            }
            bloquearPagSigDocente = false;
            bloquearPagAntDocente = false;
        }
    }

    /**
     *
     * Metodo encargado de limpiar los parametros de busqueda
     */
    public String limpiarProcesoBusqueda() {
        activoDepartamento = true;
        activarExport = true;
        parametroNombre = null;
        parametroApellido = null;
        parametroDocumento = null;
        parametroCorreo = null;
        parametroCargo = null;
        parametroDepartamento = null;
        parametroFacultad = null;
        parametroEstado = 1;
        inicializarFiltros();
        listaDocentes = null;
        listaDocentesTabla = null;
        tamTotalDocente = 0;
        posicionDocenteTabla = 0;
        bloquearPagAntDocente = true;
        bloquearPagSigDocente = true;
        return paginaAnterior;
    }

    /**
     * Metodo encargado de actualizar las facultades
     */
    public void actualizarFacultades() {
        try {
            if (Utilidades.validarNulo(parametroFacultad)) {
                activoDepartamento = false;
                parametroDepartamento = new Departamento();
                listaDepartamentos = administrarDocentesBO.obtenerDepartamentosPorIDFacultad(parametroFacultad.getIdfacultad());
            } else {
                activoDepartamento = true;
                listaDepartamentos = null;
                parametroDepartamento = new Departamento();
            }
        } catch (Exception e) {
            System.out.println("Error ControllerAdministrarDocentes actualizarFacultades : " + e.toString());
        }
    }

    /**
     * Metodo encargado direccionar a la pagina de detalles de un docente
     */
    public String verDetallesDocente() {
        limpiarProcesoBusqueda();
        return "detalles_docente";
    }

    /*
     //EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDFTablasAnchas();
     exporter.export(context, tabla, "Administrar_Docentes_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Administrar_Docentes_XLS", false, false, "UTF-8", null, null);
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

    public String getParametroCargo() {
        return parametroCargo;
    }

    public void setParametroCargo(String parametroCargo) {
        this.parametroCargo = parametroCargo;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

    public List<Facultad> getListaFacultades() {
        if (listaFacultades == null) {
            listaFacultades = administrarDocentesBO.obtenerListaFacultades();
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

    public boolean isActivoDepartamento() {
        return activoDepartamento;
    }

    public void setActivoDepartamento(boolean activoDepartamento) {
        this.activoDepartamento = activoDepartamento;
    }

    public boolean isActivarExport() {
        return activarExport;
    }

    public void setActivarExport(boolean activarExport) {
        this.activarExport = activarExport;
    }

    public List<Docente> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(List<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public List<Docente> getListaDocentesTabla() {
        return listaDocentesTabla;
    }

    public void setListaDocentesTabla(List<Docente> listaDocentesTabla) {
        this.listaDocentesTabla = listaDocentesTabla;
    }

    public boolean isBloquearPagSigDocente() {
        return bloquearPagSigDocente;
    }

    public void setBloquearPagSigDocente(boolean bloquearPagSigDocente) {
        this.bloquearPagSigDocente = bloquearPagSigDocente;
    }

    public boolean isBloquearPagAntDocente() {
        return bloquearPagAntDocente;
    }

    public void setBloquearPagAntDocente(boolean bloquearPagAntDocente) {
        this.bloquearPagAntDocente = bloquearPagAntDocente;
    }

}
