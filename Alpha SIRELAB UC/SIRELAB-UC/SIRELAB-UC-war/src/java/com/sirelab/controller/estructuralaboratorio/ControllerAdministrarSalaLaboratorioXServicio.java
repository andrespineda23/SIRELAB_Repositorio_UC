/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalaLaboratorioxServiciosBOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.SalaLaboratorioxServicios;
import com.sirelab.entidades.ServiciosSala;
import com.sirelab.entidades.TipoPerfil;
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
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerAdministrarSalaLaboratorioXServicio implements Serializable {

    @EJB
    GestionarPlantaSalaLaboratorioxServiciosBOInterface gestionarPlantaSalaLaboratorioxServiciosBO;

    private List<Departamento> listaDepartamentos;
    private Departamento parametroDepartamento;
    private boolean activarDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio parametroLaboratorio;
    private boolean activarLaboratorio;
    private List<SalaLaboratorio> listaSalaLaboratorio;
    private SalaLaboratorio parametroSala;
    private boolean activarSala;
    private List<ServiciosSala> listaServiciosSala;
    private ServiciosSala parametroServicio;
    //
    private Map<String, String> filtros;
    //
    private List<SalaLaboratorioxServicios> listaSalaLaboratorioxServicios;
    private List<SalaLaboratorioxServicios> listaSalaLaboratorioxServiciosTabla;
    private int posicionSalaLaboratorioxServicioTabla;
    private int tamTotalSalaLaboratorioxServicio;
    private boolean bloquearPagSigSalaLaboratorioxServicio, bloquearPagAntSalaLaboratorioxServicio;
    //
    private String paginaAnterior;
    //
    private TipoPerfil tipoPerfil;
    private boolean perfilConsulta;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;
    private int parametroEstado;

    public ControllerAdministrarSalaLaboratorioXServicio() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
        activarLaboratorio = true;
        parametroEstado = 1;
        parametroDepartamento = new Departamento();
        parametroLaboratorio = new Laboratorio();
        parametroSala = new SalaLaboratorio();
        parametroServicio = new ServiciosSala();
        inicializarFiltros();
        listaLaboratorios = null;
        listaSalaLaboratorio = null;
        listaSalaLaboratorioxServiciosTabla = null;
        listaSalaLaboratorioxServicios = null;
        posicionSalaLaboratorioxServicioTabla = 0;
        tamTotalSalaLaboratorioxServicio = 0;
        bloquearPagAntSalaLaboratorioxServicio = true;
        bloquearPagSigSalaLaboratorioxServicio = true;
        BasicConfigurator.configure();
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    private void inicializarFiltros() {
        filtros = new HashMap<String, String>();
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroLaboratorio", null);
        filtros.put("parametroSala", null);
        filtros.put("parametroServicio", null);
        filtros.put("parametroEstado", null);
        agregarFiltrosAdicionales();
    }

    private void agregarFiltrosAdicionales() {
        if (Utilidades.validarNulo(parametroDepartamento)) {
            if (parametroDepartamento.getIddepartamento() != null) {
                filtros.put("parametroDepartamento", parametroDepartamento.getIddepartamento().toString());
            }
        }
        if (Utilidades.validarNulo(parametroSala)) {
            if (parametroSala.getIdsalalaboratorio() != null) {
                filtros.put("parametroSala", parametroSala.getIdsalalaboratorio().toString());
            }
        }
        if (Utilidades.validarNulo(parametroServicio)) {
            if (parametroServicio.getIdserviciossala() != null) {
                filtros.put("parametroServicio", parametroServicio.getIdserviciossala().toString());
            }
        }
        if (Utilidades.validarNulo(parametroLaboratorio)) {
            if (parametroLaboratorio.getIdlaboratorio() != null) {
                filtros.put("parametroLaboratorio", parametroLaboratorio.getIdlaboratorio().toString());
            }
        }
        if (1 == parametroEstado) {
            filtros.put("parametroEstado", "true");
        } else {
            if (parametroEstado == 2) {
                filtros.put("parametroEstado", "false");
            }
        }
    }

    public void buscarLaboratoriosPorParametros() {
        try {
            inicializarFiltros();
            listaSalaLaboratorioxServicios = null;
            listaSalaLaboratorioxServicios = gestionarPlantaSalaLaboratorioxServiciosBO.consultarSalaLaboratorioxServiciosPorParametro(filtros);
            if (listaSalaLaboratorioxServicios != null) {
                if (listaSalaLaboratorioxServicios.size() > 0) {
                    listaSalaLaboratorioxServiciosTabla = new ArrayList<SalaLaboratorioxServicios>();
                    tamTotalSalaLaboratorioxServicio = listaSalaLaboratorioxServicios.size();
                    posicionSalaLaboratorioxServicioTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalSalaLaboratorioxServicio);
                    cargarDatosTablaSalaLaboratorioxServicio();
                } else {
                    listaSalaLaboratorioxServiciosTabla = null;
                    tamTotalSalaLaboratorioxServicio = 0;
                    posicionSalaLaboratorioxServicioTabla = 0;
                    bloquearPagAntSalaLaboratorioxServicio = true;
                    cantidadRegistros = String.valueOf(tamTotalSalaLaboratorioxServicio);
                    bloquearPagSigSalaLaboratorioxServicio = true;
                }
            } else {
                listaSalaLaboratorioxServiciosTabla = null;
                tamTotalSalaLaboratorioxServicio = 0;
                posicionSalaLaboratorioxServicioTabla = 0;
                bloquearPagAntSalaLaboratorioxServicio = true;
                cantidadRegistros = String.valueOf(tamTotalSalaLaboratorioxServicio);
                bloquearPagSigSalaLaboratorioxServicio = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerAdministrarSalaLaboratorioxServicios buscarLaboratoriosPorParametros:  " + e.toString());
            System.out.println("Error ControllerAdministrarSalaLaboratorioxServicios buscarLaboratoriosPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaSalaLaboratorioxServicio() {
        if (tamTotalSalaLaboratorioxServicio < 5) {
            for (int i = 0; i < tamTotalSalaLaboratorioxServicio; i++) {
                listaSalaLaboratorioxServiciosTabla.add(listaSalaLaboratorioxServicios.get(i));
            }
            bloquearPagSigSalaLaboratorioxServicio = true;
            bloquearPagAntSalaLaboratorioxServicio = true;
        } else {
            for (int i = 0; i < 5; i++) {
                listaSalaLaboratorioxServiciosTabla.add(listaSalaLaboratorioxServicios.get(i));
            }
            bloquearPagSigSalaLaboratorioxServicio = false;
            bloquearPagAntSalaLaboratorioxServicio = true;
        }
    }

    public void cargarPaginaSiguienteSalaLaboratorioxServicio() {
        listaSalaLaboratorioxServiciosTabla = new ArrayList<SalaLaboratorioxServicios>();
        posicionSalaLaboratorioxServicioTabla = posicionSalaLaboratorioxServicioTabla + 5;
        int diferencia = tamTotalSalaLaboratorioxServicio - posicionSalaLaboratorioxServicioTabla;
        if (diferencia > 5) {
            for (int i = posicionSalaLaboratorioxServicioTabla; i < (posicionSalaLaboratorioxServicioTabla + 5); i++) {
                listaSalaLaboratorioxServiciosTabla.add(listaSalaLaboratorioxServicios.get(i));
            }
            bloquearPagSigSalaLaboratorioxServicio = false;
            bloquearPagAntSalaLaboratorioxServicio = false;
        } else {
            for (int i = posicionSalaLaboratorioxServicioTabla; i < (posicionSalaLaboratorioxServicioTabla + diferencia); i++) {
                listaSalaLaboratorioxServiciosTabla.add(listaSalaLaboratorioxServicios.get(i));
            }
            bloquearPagSigSalaLaboratorioxServicio = true;
            bloquearPagAntSalaLaboratorioxServicio = false;
        }
    }

    public void cargarPaginaAnteriorSalaLaboratorioxServicio() {
        listaSalaLaboratorioxServiciosTabla = new ArrayList<SalaLaboratorioxServicios>();
        posicionSalaLaboratorioxServicioTabla = posicionSalaLaboratorioxServicioTabla - 5;
        int diferencia = tamTotalSalaLaboratorioxServicio - posicionSalaLaboratorioxServicioTabla;
        if (diferencia == tamTotalSalaLaboratorioxServicio) {
            for (int i = posicionSalaLaboratorioxServicioTabla; i < (posicionSalaLaboratorioxServicioTabla + 5); i++) {
                listaSalaLaboratorioxServiciosTabla.add(listaSalaLaboratorioxServicios.get(i));
            }
            bloquearPagSigSalaLaboratorioxServicio = false;
            bloquearPagAntSalaLaboratorioxServicio = true;
        } else {
            for (int i = posicionSalaLaboratorioxServicioTabla; i < (posicionSalaLaboratorioxServicioTabla + 5); i++) {
                listaSalaLaboratorioxServiciosTabla.add(listaSalaLaboratorioxServicios.get(i));
            }
            bloquearPagSigSalaLaboratorioxServicio = false;
            bloquearPagAntSalaLaboratorioxServicio = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarLaboratorio = true;
        parametroDepartamento = new Departamento();
        parametroServicio = new ServiciosSala();
        parametroLaboratorio = new Laboratorio();
        parametroSala = new SalaLaboratorio();
        inicializarFiltros();

        listaLaboratorios = null;
        listaDepartamentos = null;
        listaServiciosSala = null;
        parametroEstado = 1;

        listaSalaLaboratorioxServicios = null;
        listaSalaLaboratorioxServiciosTabla = null;
        posicionSalaLaboratorioxServicioTabla = 0;
        tamTotalSalaLaboratorioxServicio = 0;
        bloquearPagAntSalaLaboratorioxServicio = true;
        bloquearPagSigSalaLaboratorioxServicio = true;
        cantidadRegistros = "N/A";
        return paginaAnterior;
    }

    public void limpiarDatos() {
        cantidadRegistros = "N/A";
        activarLaboratorio = true;
        parametroDepartamento = new Departamento();
        parametroServicio = new ServiciosSala();
        parametroLaboratorio = new Laboratorio();
        inicializarFiltros();
        parametroEstado = 1;
        listaLaboratorios = null;
        listaSalaLaboratorioxServicios = null;
        listaSalaLaboratorioxServiciosTabla = null;
        posicionSalaLaboratorioxServicioTabla = 0;
        tamTotalSalaLaboratorioxServicio = 0;
        bloquearPagAntSalaLaboratorioxServicio = true;
        bloquearPagSigSalaLaboratorioxServicio = true;
    }

    public void actualizarDepartamentos() {
        if (Utilidades.validarNulo(parametroDepartamento)) {
            parametroLaboratorio = new Laboratorio();
            listaLaboratorios = gestionarPlantaSalaLaboratorioxServiciosBO.consultarLaboratoriosPorIDDepartamento(parametroDepartamento.getIddepartamento());
            activarLaboratorio = false;
        } else {
            parametroLaboratorio = new Laboratorio();
            listaLaboratorios = null;
            activarLaboratorio = true;

            parametroSala = new SalaLaboratorio();
            listaSalaLaboratorio = null;
            activarSala = true;
        }
    }

    public void actualizarLaboratorio() {
        if (Utilidades.validarNulo(parametroLaboratorio)) {
            parametroSala = new SalaLaboratorio();
            listaSalaLaboratorio = gestionarPlantaSalaLaboratorioxServiciosBO.consultarSalaLaboratoriosActivosPorIDLaboratorio(parametroLaboratorio.getIdlaboratorio());
            activarSala = false;
        } else {
            parametroSala = new SalaLaboratorio();
            listaSalaLaboratorio = null;
            activarSala = true;
        }
    }


    /*//EXPORTAR
     public void exportPDF() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarPDF();
     exporter.export(context, tabla, "GestionarPlantaLaboratoriosPDF", false, false, "UTF-8", null, null);
     context.responseComplete();
     }

     public void exportXLS() throws IOException {
     DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formT:form:datosBusqueda");
     FacesContext context = FacesContext.getCurrentInstance();
     Exporter exporter = new ExportarXLS();
     exporter.export(context, tabla, "GestionarPlantaLaboratoriosXLS", false, false, "UTF-8", null, null);
     context.responseComplete();
     }
     */
    // GET - SET
    public List<Departamento> getListaDepartamentos() {
        if (null == listaDepartamentos) {
            listaDepartamentos = gestionarPlantaSalaLaboratorioxServiciosBO.consultarDepartamentosActivosRegistrados();
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

    public boolean isActivarDepartamento() {
        return activarDepartamento;
    }

    public void setActivarDepartamento(boolean activarDepartamento) {
        this.activarDepartamento = activarDepartamento;
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

    public List<SalaLaboratorio> getListaSalaLaboratorio() {
        return listaSalaLaboratorio;
    }

    public void setListaSalaLaboratorio(List<SalaLaboratorio> listaSalaLaboratorio) {
        this.listaSalaLaboratorio = listaSalaLaboratorio;
    }

    public SalaLaboratorio getParametroSala() {
        return parametroSala;
    }

    public void setParametroSala(SalaLaboratorio parametroSala) {
        this.parametroSala = parametroSala;
    }

    public boolean isActivarSala() {
        return activarSala;
    }

    public void setActivarSala(boolean activarSala) {
        this.activarSala = activarSala;
    }

    public List<ServiciosSala> getListaServiciosSala() {
        if (null == listaServiciosSala) {
            listaServiciosSala = gestionarPlantaSalaLaboratorioxServiciosBO.consultarServiciosSalaRegistradas();
        }
        return listaServiciosSala;
    }

    public void setListaServiciosSala(List<ServiciosSala> listaServiciosSala) {
        this.listaServiciosSala = listaServiciosSala;
    }

    public ServiciosSala getParametroServicio() {
        return parametroServicio;
    }

    public void setParametroServicio(ServiciosSala parametroServicio) {
        this.parametroServicio = parametroServicio;
    }

    public List<SalaLaboratorioxServicios> getListaSalaLaboratorioxServicios() {
        return listaSalaLaboratorioxServicios;
    }

    public void setListaSalaLaboratorioxServicios(List<SalaLaboratorioxServicios> listaSalaLaboratorioxServicios) {
        this.listaSalaLaboratorioxServicios = listaSalaLaboratorioxServicios;
    }

    public List<SalaLaboratorioxServicios> getListaSalaLaboratorioxServiciosTabla() {
        return listaSalaLaboratorioxServiciosTabla;
    }

    public void setListaSalaLaboratorioxServiciosTabla(List<SalaLaboratorioxServicios> listaSalaLaboratorioxServiciosTabla) {
        this.listaSalaLaboratorioxServiciosTabla = listaSalaLaboratorioxServiciosTabla;
    }

    public int getPosicionSalaLaboratorioxServicioTabla() {
        return posicionSalaLaboratorioxServicioTabla;
    }

    public void setPosicionSalaLaboratorioxServicioTabla(int posicionSalaLaboratorioxServicioTabla) {
        this.posicionSalaLaboratorioxServicioTabla = posicionSalaLaboratorioxServicioTabla;
    }

    public int getTamTotalSalaLaboratorioxServicio() {
        return tamTotalSalaLaboratorioxServicio;
    }

    public void setTamTotalSalaLaboratorioxServicio(int tamTotalSalaLaboratorioxServicio) {
        this.tamTotalSalaLaboratorioxServicio = tamTotalSalaLaboratorioxServicio;
    }

    public boolean isBloquearPagSigSalaLaboratorioxServicio() {
        return bloquearPagSigSalaLaboratorioxServicio;
    }

    public void setBloquearPagSigSalaLaboratorioxServicio(boolean bloquearPagSigSalaLaboratorioxServicio) {
        this.bloquearPagSigSalaLaboratorioxServicio = bloquearPagSigSalaLaboratorioxServicio;
    }

    public boolean isBloquearPagAntSalaLaboratorioxServicio() {
        return bloquearPagAntSalaLaboratorioxServicio;
    }

    public void setBloquearPagAntSalaLaboratorioxServicio(boolean bloquearPagAntSalaLaboratorioxServicio) {
        this.bloquearPagAntSalaLaboratorioxServicio = bloquearPagAntSalaLaboratorioxServicio;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public TipoPerfil getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(TipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
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

    public int getParametroEstado() {
        return parametroEstado;
    }

    public void setParametroEstado(int parametroEstado) {
        this.parametroEstado = parametroEstado;
    }

}
