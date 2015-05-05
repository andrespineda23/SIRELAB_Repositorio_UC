/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.GestionarPlantaSalasBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
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
public class ControllerAdministrarSalas implements Serializable {

    @EJB
    GestionarPlantaSalasBOInterface gestionarPlantaSalasBO;

    private String parametroNombre, parametroCodigo, parametroCapacidad;
    private int parametroEstado;
    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio parametroLaboratorio;
    private List<AreaProfundizacion> listaAreasProfundizacion;
    private AreaProfundizacion parametroAreaProfundizacion;
    private List<Sede> listaSedes;
    private Sede parametroSede;
    private List<Edificio> listaEdificios;
    private Edificio parametroEdificio;
    private boolean activarLaboratorio;
    private boolean activarEdificio;

    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<SalaLaboratorio> listaSalasLaboratorios;
    private List<SalaLaboratorio> filtrarListaSalasLaboratorios;
    //
    private String altoTabla;
    //

    public ControllerAdministrarSalas() {
    }

    @PostConstruct
    public void init() {
        activarLaboratorio = true;
        activarEdificio = true;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroCapacidad = null;
        parametroAreaProfundizacion = new AreaProfundizacion();
        parametroDepartamento = new Departamento();
        parametroLaboratorio = new Laboratorio();
        parametroEdificio = new Edificio();
        parametroSede = new Sede();
        listaSedes = gestionarPlantaSalasBO.consultarSedesRegistradas();
        listaDepartamentos = gestionarPlantaSalasBO.consultarDepartamentosRegistrados();
        listaAreasProfundizacion = gestionarPlantaSalasBO.consultarAreasProfundizacionRegistradas();
        altoTabla = "150";
        inicializarFiltros();
        listaLaboratorios = null;
        listaEdificios = null;
        filtrarListaSalasLaboratorios = null;
        parametroEstado = 1;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        filtros.put("parametroCapacidad", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroAreaProfundizacion", null);
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroLaboratorio", null);
        filtros.put("parametroEdificio", null);
        filtros.put("parametroSede", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if (parametroEstado == 1 || parametroEstado == 2) {
            if (parametroEstado == 1) {
                filtros.put("parametroEstado", "true");
            } else {
                filtros.put("parametroEstado", "false");
            }
        }
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty())) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCapacidad) == true) && (!parametroCapacidad.isEmpty())) {
            filtros.put("parametroCapacidad", parametroCapacidad.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty())) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
        if (parametroDepartamento.getIddepartamento() != null) {
            filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
        }
        if (parametroLaboratorio.getIdlaboratorio() != null) {
            filtros.put("parametroLaboratorio", parametroLaboratorio.getIdlaboratorio().toString());
        }
        if (parametroEdificio.getIdedificio() != null) {
            filtros.put("parametroEdificio", parametroEdificio.getIdedificio().toString());
        }
        if (parametroSede.getIdsede() != null) {
            filtros.put("parametroSede", parametroSede.getIdsede().toString());
        }
        if (parametroAreaProfundizacion.getIdareaprofundizacion() != null) {
            filtros.put("parametroAreaProfundizacion", parametroAreaProfundizacion.getIdareaprofundizacion().toString());
        }
    }

    public void buscarSalasLaboratorioPorParametros() {
        try {
            inicializarFiltros();
            listaSalasLaboratorios = null;
            listaSalasLaboratorios = gestionarPlantaSalasBO.consultarSalasLaboratoriosPorParametro(filtros);
            if (listaSalasLaboratorios != null) {
                if (listaSalasLaboratorios.size() > 0) {
                    activarExport = false;
                } else {
                    activarExport = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error ControllerGestionarPlantaSalas buscarSalasLaboratorioPorParametros : " + e.toString());
        }
    }

    public void limpiarProcesoBusqueda() {
        activarEdificio = true;
        activarLaboratorio = true;
        parametroEstado = 1;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroCapacidad = null;
        parametroAreaProfundizacion = new AreaProfundizacion();
        parametroDepartamento = new Departamento();
        parametroLaboratorio = new Laboratorio();
        parametroEdificio = new Edificio();
        parametroSede = new Sede();
        inicializarFiltros();
        listaEdificios = null;
        listaLaboratorios = null;
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(parametroDepartamento)) {
            parametroLaboratorio = new Laboratorio();
            listaLaboratorios = gestionarPlantaSalasBO.consultarLaboratoriosPorIDDepartamento(parametroDepartamento.getIddepartamento());
            activarLaboratorio = false;
        } else {
            parametroLaboratorio = new Laboratorio();
            activarLaboratorio = true;
            listaLaboratorios = null;
        }
    }

    public void actualizarSedes() {
        if (Utilidades.validarNulo(parametroSede)) {
            parametroEdificio = new Edificio();
            listaEdificios = gestionarPlantaSalasBO.consultarEdificiosPorIDSede(parametroSede.getIdsede());
            activarEdificio = false;
        } else {
            parametroEdificio = new Edificio();
            activarEdificio = true;
            listaEdificios = null;
        }
    }


    /*//EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "Plata_Salas_Laboratorio_PDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "Plata_Salas_Laboratorio_XLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    public String cambiarPaginaDetalles() {
        limpiarProcesoBusqueda();
        return "detalles_sala";
    }

    //GET-SET
    public String getParametroNombre() {
        return parametroNombre;
    }

    public void setParametroNombre(String parametroNombre) {
        this.parametroNombre = parametroNombre;
    }

    public String getParametroCodigo() {
        return parametroCodigo;
    }

    public void setParametroCodigo(String parametroCodigo) {
        this.parametroCodigo = parametroCodigo;
    }

    public String getParametroCapacidad() {
        return parametroCapacidad;
    }

    public void setParametroCapacidad(String parametroCapacidad) {
        this.parametroCapacidad = parametroCapacidad;
    }

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
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

    public List<AreaProfundizacion> getListaAreasProfundizacion() {
        return listaAreasProfundizacion;
    }

    public void setListaAreasProfundizacion(List<AreaProfundizacion> listaAreasProfundizacion) {
        this.listaAreasProfundizacion = listaAreasProfundizacion;
    }

    public AreaProfundizacion getParametroAreaProfundizacion() {
        return parametroAreaProfundizacion;
    }

    public void setParametroAreaProfundizacion(AreaProfundizacion parametroAreaProfundizacion) {
        this.parametroAreaProfundizacion = parametroAreaProfundizacion;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    public Sede getParametroSede() {
        return parametroSede;
    }

    public void setParametroSede(Sede parametroSede) {
        this.parametroSede = parametroSede;
    }

    public List<Edificio> getListaEdificios() {
        return listaEdificios;
    }

    public void setListaEdificios(List<Edificio> listaEdificios) {
        this.listaEdificios = listaEdificios;
    }

    public Edificio getParametroEdificio() {
        return parametroEdificio;
    }

    public void setParametroEdificio(Edificio parametroEdificio) {
        this.parametroEdificio = parametroEdificio;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public boolean isActivarEdificio() {
        return activarEdificio;
    }

    public void setActivarEdificio(boolean activarEdificio) {
        this.activarEdificio = activarEdificio;
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

    public List<SalaLaboratorio> getListaSalasLaboratorios() {
        return listaSalasLaboratorios;
    }

    public void setListaSalasLaboratorios(List<SalaLaboratorio> listaSalasLaboratorios) {
        this.listaSalasLaboratorios = listaSalasLaboratorios;
    }

    public List<SalaLaboratorio> getFiltrarListaSalasLaboratorios() {
        return filtrarListaSalasLaboratorios;
    }

    public void setFiltrarListaSalasLaboratorios(List<SalaLaboratorio> filtrarListaSalasLaboratorios) {
        this.filtrarListaSalasLaboratorios = filtrarListaSalasLaboratorios;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

}
