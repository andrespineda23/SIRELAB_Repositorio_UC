/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructura_laboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaSalasBOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Sede;
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
    private int parametroPrivada;

    //
    private Map<String, String> filtros;
    //
    private boolean activarExport;
    //
    private List<SalaLaboratorio> listaSalasLaboratorios;
    private List<SalaLaboratorio> listaSalasLaboratoriosTabla;
    private int posicionSalaTabla;
    private int tamTotalSala;
    private boolean bloquearPagSigSala, bloquearPagAntSala;
    //
    private String altoTabla;
    //
    private UsuarioLogin usuarioLoginSistema;
    //
    private boolean perfilConsulta;
    private String paginaAnterior;
    private Logger logger = Logger.getLogger(getClass().getName());
    private String cantidadRegistros;

    public ControllerAdministrarSalas() {
    }

    @PostConstruct
    public void init() {
        cantidadRegistros = "N/A";
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
        listaSedes = null;
        listaDepartamentos = null;
        listaAreasProfundizacion = null;
        altoTabla = "150";
        inicializarFiltros();
        listaLaboratorios = null;
        listaEdificios = null;
        parametroEstado = 1;
        parametroPrivada = 1;
        listaSalasLaboratoriosTabla = null;
        listaSalasLaboratorios = null;
        posicionSalaTabla = 0;
        tamTotalSala = 0;
        bloquearPagAntSala = true;
        bloquearPagSigSala = true;
        BasicConfigurator.configure();
    }

    private void cargarInformacionPerfil() {
        usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        if ("ENCARGADOLAB".equalsIgnoreCase(usuarioLoginSistema.getNombreTipoUsuario())) {
            EncargadoLaboratorio encargadoUser = gestionarPlantaSalasBO.obtenerEncargadoLaboratorioPorID(usuarioLoginSistema.getIdUsuarioLogin());
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
        filtros.put("parametroNombre", null);
        filtros.put("parametroCodigo", null);
        filtros.put("parametroCapacidad", null);
        filtros.put("parametroEstado", null);
        filtros.put("parametroAreaProfundizacion", null);
        filtros.put("parametroDepartamento", null);
        filtros.put("parametroLaboratorio", null);
        filtros.put("parametroEdificio", null);
        filtros.put("parametroPrivada", null);
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
        if (parametroPrivada == 1 || parametroPrivada == 2) {
            if (parametroPrivada == 1) {
                filtros.put("parametroPrivada", "false");
            } else {
                filtros.put("parametroPrivada", "true");
            }
        }
        if ((Utilidades.validarNulo(parametroNombre) == true) && (!parametroNombre.isEmpty()) && (parametroNombre.trim().length() > 0)) {
            filtros.put("parametroNombre", parametroNombre.toString());
        }
        if ((Utilidades.validarNulo(parametroCapacidad) == true) && (!parametroCapacidad.isEmpty()) && (parametroCapacidad.trim().length() > 0)) {
            filtros.put("parametroCapacidad", parametroCapacidad.toString());
        }
        if ((Utilidades.validarNulo(parametroCodigo) == true) && (!parametroCodigo.isEmpty()) && (parametroCodigo.trim().length() > 0)) {
            filtros.put("parametroCodigo", parametroCodigo.toString());
        }
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
        if (Utilidades.validarNulo(parametroEdificio)) {
            if (parametroEdificio.getIdedificio() != null) {
                filtros.put("parametroEdificio", parametroEdificio.getIdedificio().toString());
            }
        }
        if (Utilidades.validarNulo(parametroSede)) {
            if (parametroSede.getIdsede() != null) {
                filtros.put("parametroSede", parametroSede.getIdsede().toString());
            }
        }
        if (Utilidades.validarNulo(parametroAreaProfundizacion)) {
            if (parametroAreaProfundizacion.getIdareaprofundizacion() != null) {
                filtros.put("parametroAreaProfundizacion", parametroAreaProfundizacion.getIdareaprofundizacion().toString());
            }
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
                    listaSalasLaboratoriosTabla = new ArrayList<SalaLaboratorio>();
                    tamTotalSala = listaSalasLaboratorios.size();
                    posicionSalaTabla = 0;
                    cantidadRegistros = String.valueOf(tamTotalSala);
                    cargarDatosTablaSala();
                } else {
                    activarExport = true;
                    listaSalasLaboratoriosTabla = null;
                    tamTotalSala = 0;
                    posicionSalaTabla = 0;
                    bloquearPagAntSala = true;
                    cantidadRegistros = String.valueOf(tamTotalSala);
                    bloquearPagSigSala = true;
                }
            } else {
                listaSalasLaboratoriosTabla = null;
                tamTotalSala = 0;
                posicionSalaTabla = 0;
                bloquearPagAntSala = true;
                cantidadRegistros = String.valueOf(tamTotalSala);
                bloquearPagSigSala = true;
            }
        } catch (Exception e) {
            logger.error("Error ControllerGestionarPlantaSalas buscarSalasLaboratorioPorParametros:  " + e.toString());
            System.out.println("Error ControllerGestionarPlantaSalas buscarSalasLaboratorioPorParametros : " + e.toString());
        }
    }

    private void cargarDatosTablaSala() {
        if (tamTotalSala < 10) {
            for (int i = 0; i < tamTotalSala; i++) {
                listaSalasLaboratoriosTabla.add(listaSalasLaboratorios.get(i));
            }
            bloquearPagSigSala = true;
            bloquearPagAntSala = true;
        } else {
            for (int i = 0; i < 10; i++) {
                listaSalasLaboratoriosTabla.add(listaSalasLaboratorios.get(i));
            }
            bloquearPagSigSala = false;
            bloquearPagAntSala = true;
        }
    }

    public void cargarPaginaSiguienteSala() {
        listaSalasLaboratoriosTabla = new ArrayList<SalaLaboratorio>();
        posicionSalaTabla = posicionSalaTabla + 10;
        int diferencia = tamTotalSala - posicionSalaTabla;
        if (diferencia > 10) {
            for (int i = posicionSalaTabla; i < (posicionSalaTabla + 10); i++) {
                listaSalasLaboratoriosTabla.add(listaSalasLaboratorios.get(i));
            }
            bloquearPagSigSala = false;
            bloquearPagAntSala = false;
        } else {
            for (int i = posicionSalaTabla; i < (posicionSalaTabla + diferencia); i++) {
                listaSalasLaboratoriosTabla.add(listaSalasLaboratorios.get(i));
            }
            bloquearPagSigSala = true;
            bloquearPagAntSala = false;
        }
    }

    public void cargarPaginaAnteriorSala() {
        listaSalasLaboratoriosTabla = new ArrayList<SalaLaboratorio>();
        posicionSalaTabla = posicionSalaTabla - 10;
        int diferencia = tamTotalSala - posicionSalaTabla;
        if (diferencia == tamTotalSala) {
            for (int i = posicionSalaTabla; i < (posicionSalaTabla + 10); i++) {
                listaSalasLaboratoriosTabla.add(listaSalasLaboratorios.get(i));
            }
            bloquearPagSigSala = false;
            bloquearPagAntSala = true;
        } else {
            for (int i = posicionSalaTabla; i < (posicionSalaTabla + 10); i++) {
                listaSalasLaboratoriosTabla.add(listaSalasLaboratorios.get(i));
            }
            bloquearPagSigSala = false;
            bloquearPagAntSala = false;
        }
    }

    public String limpiarProcesoBusqueda() {
        activarEdificio = true;
        activarLaboratorio = true;
        parametroEstado = 1;
        parametroPrivada = 1;
        activarExport = true;
        parametroNombre = null;
        parametroCodigo = null;
        parametroCapacidad = null;
        parametroAreaProfundizacion = new AreaProfundizacion();
        parametroDepartamento = new Departamento();
        parametroLaboratorio = new Laboratorio();
        parametroEdificio = new Edificio();
        parametroSede = new Sede();

        listaEdificios = null;
        listaLaboratorios = null;
        listaAreasProfundizacion = null;
        listaDepartamentos = null;
        listaSedes = null;
        cantidadRegistros = "N/A";

        listaSalasLaboratorios = null;
        listaSalasLaboratoriosTabla = null;
        posicionSalaTabla = 0;
        tamTotalSala = 0;
        bloquearPagAntSala = true;
        bloquearPagSigSala = true;
        inicializarFiltros();
        return paginaAnterior;
    }

    public void limpiarDatos() {
        cantidadRegistros = "N/A";
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
        listaEdificios = null;
        parametroPrivada = 1;
        listaLaboratorios = null;
        listaSalasLaboratorios = null;
        listaSalasLaboratoriosTabla = null;
        posicionSalaTabla = 0;
        tamTotalSala = 0;
        bloquearPagAntSala = true;
        bloquearPagSigSala = true;
        inicializarFiltros();
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
    
    public String cambiarPaginaModuloCargado() {
        limpiarProcesoBusqueda();
        return "registrar_modulo_cargado";
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
        if (listaDepartamentos == null) {
            listaDepartamentos = gestionarPlantaSalasBO.consultarDepartamentosRegistrados();
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
        if (listaAreasProfundizacion == null) {
            listaAreasProfundizacion = gestionarPlantaSalasBO.consultarAreasProfundizacionRegistradas();
        }
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
        if (listaSedes == null) {
            listaSedes = gestionarPlantaSalasBO.consultarSedesRegistradas();
        }
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

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<SalaLaboratorio> getListaSalasLaboratoriosTabla() {
        return listaSalasLaboratoriosTabla;
    }

    public void setListaSalasLaboratoriosTabla(List<SalaLaboratorio> listaSalasLaboratoriosTabla) {
        this.listaSalasLaboratoriosTabla = listaSalasLaboratoriosTabla;
    }

    public boolean isBloquearPagSigSala() {
        return bloquearPagSigSala;
    }

    public void setBloquearPagSigSala(boolean bloquearPagSigSala) {
        this.bloquearPagSigSala = bloquearPagSigSala;
    }

    public boolean isBloquearPagAntSala() {
        return bloquearPagAntSala;
    }

    public void setBloquearPagAntSala(boolean bloquearPagAntSala) {
        this.bloquearPagAntSala = bloquearPagAntSala;
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

    public int getParametroPrivada() {
        return parametroPrivada;
    }

    public void setParametroPrivada(int parametroPrivada) {
        this.parametroPrivada = parametroPrivada;
    }

}
