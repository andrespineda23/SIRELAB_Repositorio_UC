/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.estructuralaboratorio;

import com.sirelab.bo.interfacebo.planta.GestionarPlantaTransferenciaElementosBOInterface;
import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.List;
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
public class ControllerTransferenciaElementos implements Serializable {

    @EJB
    GestionarPlantaTransferenciaElementosBOInterface gestionarPlantaTransferenciaElementosBO;

    private List<Laboratorio> listaLaboratorioTransferencia;
    private Laboratorio laboratorioTransferencia;
    private List<SalaLaboratorio> listaSalaLaboratorioTransferencia;
    private SalaLaboratorio salaLaboratorioTransferencia;
    private boolean activarSalaTransferencia;
    private List<ModuloLaboratorio> listaModuloLaboratorioTransferencia;
    private ModuloLaboratorio moduloLaboratorioTransferencia;
    private boolean activarModuloTransferencia;
    private List<EquipoElemento> listaEquipoElementoTransferencia;
    private EquipoElemento equipoElementoTransferencia;
    private boolean activarEquipoTransferencia;
    private List<ComponenteEquipo> listaComponenteEquipoTransferencia;
    private ComponenteEquipo componenteEquipoTransferencia;
    private boolean activarComponenteTransferencia;
    private List<Laboratorio> listaLaboratorio;
    private Laboratorio laboratorio;
    private boolean activarLaboratorio;
    private List<SalaLaboratorio> listaSalaLaboratorio;
    private SalaLaboratorio salaLaboratorio;
    private boolean activarSala;
    private List<ModuloLaboratorio> listaModuloLaboratorio;
    private ModuloLaboratorio moduloLaboratorio;
    private boolean activarModulo;
    private List<EquipoElemento> listaEquipoElemento;
    private EquipoElemento equipoElemento;
    private boolean activarEquipo;
    private String paginaAnterior;
    private boolean procesoCompletado;
    private String mensajeFormulario, colorMensaje;
    private boolean inactivarAceptar;
    private boolean transferirComponente;

    public ControllerTransferenciaElementos() {
    }

    @PostConstruct
    public void init() {

    }

    public void recibirPaginaAnterior(String pagina) {
        mensajeFormulario = "N/A";
        transferirComponente = true;
        colorMensaje = "black";
        this.paginaAnterior = pagina;
        laboratorioTransferencia = null;
        activarComponenteTransferencia = true;
        activarEquipoTransferencia = true;
        activarModuloTransferencia = true;
        activarSalaTransferencia = true;
        salaLaboratorioTransferencia = null;
        moduloLaboratorioTransferencia = null;
        equipoElementoTransferencia = null;
        componenteEquipoTransferencia = null;
        laboratorio = null;
        activarEquipo = true;
        activarModulo = true;
        activarSala = true;
        salaLaboratorio = null;
        moduloLaboratorio = null;
        equipoElemento = null;
        inactivarAceptar = false;
        procesoCompletado = true;
        activarLaboratorio = true;
        listaLaboratorioTransferencia = gestionarPlantaTransferenciaElementosBO.obtenerLaboratoriosRegistrados();
    }

    public String retornarPaginaAnterior() {
        laboratorioTransferencia = null;
        inactivarAceptar = false;
        transferirComponente = true;
        activarComponenteTransferencia = true;
        activarEquipoTransferencia = true;
        activarModuloTransferencia = true;
        activarSalaTransferencia = true;
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        salaLaboratorioTransferencia = null;
        activarLaboratorio = true;
        procesoCompletado = true;
        moduloLaboratorioTransferencia = null;
        equipoElementoTransferencia = null;
        componenteEquipoTransferencia = null;
        listaComponenteEquipoTransferencia = null;
        listaEquipoElementoTransferencia = null;
        listaLaboratorioTransferencia = null;
        listaModuloLaboratorioTransferencia = null;
        listaSalaLaboratorioTransferencia = null;
        laboratorio = null;
        activarEquipo = true;
        activarModulo = true;
        activarSala = true;
        salaLaboratorio = null;
        moduloLaboratorio = null;
        equipoElemento = null;
        listaEquipoElemento = null;
        listaLaboratorio = null;
        listaModuloLaboratorio = null;
        listaSalaLaboratorio = null;
        return paginaAnterior;
    }

    public void actualizarLaboratorioTransferencia() {
        if (Utilidades.validarNulo(laboratorioTransferencia)) {
            salaLaboratorioTransferencia = new SalaLaboratorio();
            listaSalaLaboratorioTransferencia = gestionarPlantaTransferenciaElementosBO.obtenerSalasLaboratorioPorLaboratorio(laboratorioTransferencia.getIdlaboratorio());
            activarSalaTransferencia = false;
            activarComponenteTransferencia = true;
            activarEquipoTransferencia = true;
            activarModuloTransferencia = true;
            listaComponenteEquipoTransferencia = null;
            listaEquipoElementoTransferencia = null;
            listaModuloLaboratorioTransferencia = null;
            equipoElementoTransferencia = null;
            moduloLaboratorioTransferencia = null;
            componenteEquipoTransferencia = null;
        } else {
            salaLaboratorioTransferencia = new SalaLaboratorio();
            listaSalaLaboratorioTransferencia = null;
            activarSalaTransferencia = true;
            activarComponenteTransferencia = true;
            activarEquipoTransferencia = true;
            activarModuloTransferencia = true;
            listaComponenteEquipoTransferencia = null;
            listaEquipoElementoTransferencia = null;
            listaModuloLaboratorioTransferencia = null;
            equipoElementoTransferencia = null;
            moduloLaboratorioTransferencia = null;
            componenteEquipoTransferencia = null;
        }
        inactivarElementosDestino();
    }

    public void actualizarSalaTransferencia() {
        if (Utilidades.validarNulo(salaLaboratorioTransferencia)) {
            moduloLaboratorioTransferencia = new ModuloLaboratorio();
            listaModuloLaboratorioTransferencia = gestionarPlantaTransferenciaElementosBO.obtenerModulosLaboratorioPorSala(salaLaboratorioTransferencia.getIdsalalaboratorio());
            activarModuloTransferencia = false;
            activarComponenteTransferencia = true;
            activarEquipoTransferencia = true;
            listaComponenteEquipoTransferencia = null;
            listaEquipoElementoTransferencia = null;
            equipoElementoTransferencia = null;
            componenteEquipoTransferencia = null;
        } else {
            moduloLaboratorioTransferencia = new ModuloLaboratorio();
            activarComponenteTransferencia = true;
            activarEquipoTransferencia = true;
            listaComponenteEquipoTransferencia = null;
            listaEquipoElementoTransferencia = null;
            listaModuloLaboratorioTransferencia = null;
            equipoElementoTransferencia = null;
            componenteEquipoTransferencia = null;
        }
        inactivarElementosDestino();
    }

    public void actualizarModuloTransferencia() {
        if (Utilidades.validarNulo(moduloLaboratorioTransferencia)) {
            equipoElementoTransferencia = new EquipoElemento();
            listaEquipoElementoTransferencia = gestionarPlantaTransferenciaElementosBO.obtenerEquiposElementosPorModulo(moduloLaboratorioTransferencia.getIdmodulolaboratorio());
            activarEquipoTransferencia = false;
            activarComponenteTransferencia = true;
            listaComponenteEquipoTransferencia = null;
            componenteEquipoTransferencia = null;
        } else {
            equipoElementoTransferencia = new EquipoElemento();
            activarComponenteTransferencia = true;
            listaComponenteEquipoTransferencia = null;
            listaEquipoElementoTransferencia = null;
            componenteEquipoTransferencia = null;
        }
        inactivarElementosDestino();
    }

    public void actualizarLaboratorio() {
        if (Utilidades.validarNulo(laboratorio)) {
            salaLaboratorio = new SalaLaboratorio();
            listaSalaLaboratorio = gestionarPlantaTransferenciaElementosBO.obtenerSalasLaboratorioPorLaboratorio(laboratorio.getIdlaboratorio());
            activarSala = false;
            activarEquipo = true;
            activarModulo = true;
            listaEquipoElemento = null;
            listaModuloLaboratorio = null;
            equipoElemento = null;
            moduloLaboratorio = null;
        } else {
            salaLaboratorio = new SalaLaboratorio();
            listaSalaLaboratorio = null;
            activarSala = true;
            activarEquipo = true;
            activarModulo = true;
            listaEquipoElemento = null;
            listaModuloLaboratorio = null;
            equipoElemento = null;
            moduloLaboratorio = null;
        }
    }

    public void actualizarSala() {
        if (Utilidades.validarNulo(salaLaboratorio)) {
            moduloLaboratorio = new ModuloLaboratorio();
            listaModuloLaboratorio = gestionarPlantaTransferenciaElementosBO.obtenerModulosLaboratorioPorSala(salaLaboratorio.getIdsalalaboratorio());
            activarModulo = false;
            activarEquipo = true;
            listaEquipoElemento = null;
            equipoElemento = null;
        } else {
            moduloLaboratorio = new ModuloLaboratorio();
            activarEquipo = true;
            listaEquipoElemento = null;
            listaModuloLaboratorio = null;
            equipoElemento = null;
        }
    }

    public void actualizarModulo() {
        if (transferirComponente == false) {
            if (Utilidades.validarNulo(moduloLaboratorio)) {
                equipoElemento = new EquipoElemento();
                listaEquipoElemento = gestionarPlantaTransferenciaElementosBO.obtenerEquiposElementosPorModulo(moduloLaboratorio.getIdmodulolaboratorio());
                activarEquipo = false;
            } else {
                equipoElemento = new EquipoElemento();
                listaEquipoElemento = null;
            }
        } else {
            activarEquipo = true;
            equipoElemento = new EquipoElemento();
            listaEquipoElemento = null;
        }
    }

    public void activarComponente() {
        if (transferirComponente == false) {
            if (Utilidades.validarNulo(equipoElementoTransferencia)) {
                activarComponenteTransferencia = false;
                actualizarEquipoTransferencia();
            } else {
                inactivarElementosDestino();
                activarComponenteTransferencia = true;
                componenteEquipoTransferencia = null;
                listaComponenteEquipoTransferencia = null;
            }
        } else {
            activarComponenteTransferencia = true;
            componenteEquipoTransferencia = null;
            listaComponenteEquipoTransferencia = null;
            actualizarEquipoTransferencia();
        }
    }

    public void actualizarComponenteTransferencia() {
        if (Utilidades.validarNulo(componenteEquipoTransferencia)) {
            activarInformacionDestino();
        } else {
            inactivarElementosDestino();
        }
    }

    public void actualizarEquipoTransferencia() {
        if (transferirComponente == false) {
            if (Utilidades.validarNulo(equipoElementoTransferencia)) {
                componenteEquipoTransferencia = new ComponenteEquipo();
                listaComponenteEquipoTransferencia = gestionarPlantaTransferenciaElementosBO.obtenerComponentesPorEquipo(equipoElementoTransferencia.getIdequipoelemento());
                inactivarElementosDestino();
            } else {
                inactivarElementosDestino();
                activarComponenteTransferencia = true;
                componenteEquipoTransferencia = null;
            }
        } else {
            activarInformacionDestino();
            listaComponenteEquipoTransferencia = null;
            componenteEquipoTransferencia = null;
        }
    }

    public void activarInformacionDestino() {
        if (activarComponenteTransferencia == true) {
            if (Utilidades.validarNulo(equipoElementoTransferencia)) {
                activarLaboratorio = false;
                listaLaboratorio = gestionarPlantaTransferenciaElementosBO.obtenerLaboratoriosRegistrados();
                laboratorio = new Laboratorio();
            } else {
                inactivarElementosDestino();
            }
        } else {
            if (Utilidades.validarNulo(componenteEquipoTransferencia)) {
                activarLaboratorio = false;
                listaLaboratorio = gestionarPlantaTransferenciaElementosBO.obtenerLaboratoriosRegistrados();
                laboratorio = new Laboratorio();
            } else {
                inactivarElementosDestino();
            }
        }
    }

    public void almacenarTransferenciaElemento() {
        boolean validar = true;
        if (transferirComponente == true) {
            if (!Utilidades.validarNulo(equipoElementoTransferencia)) {
                validar = false;
            }
            if (!Utilidades.validarNulo(moduloLaboratorio)) {
                validar = false;
            }
        } else {
            if (!Utilidades.validarNulo(componenteEquipoTransferencia)) {
                validar = false;
            }
            if (!Utilidades.validarNulo(equipoElemento)) {
                validar = false;
            }
        }
        if (validar == true) {
            UsuarioLogin usuarioLoginSistema = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
            if (transferirComponente == true) {
                gestionarPlantaTransferenciaElementosBO.almacenarTransferenciaEquipo(equipoElementoTransferencia, moduloLaboratorio, usuarioLoginSistema.getUserUsuario());
            } else {
                gestionarPlantaTransferenciaElementosBO.almacenarTransferenciaComponente(componenteEquipoTransferencia, equipoElemento, usuarioLoginSistema.getUserUsuario());
            }
            inactivarAceptar = true;
            mensajeFormulario = "Transferencia realizada con éxito.";
            colorMensaje = "green";
        } else {
            mensajeFormulario = "Seleccione todos los parámetros para el proceso de transferencia de elementos";
            colorMensaje = "#FF0000";
        }
    }

    public void limpiarFormulario() {
        laboratorioTransferencia = null;
        activarComponenteTransferencia = true;
        activarEquipoTransferencia = true;
        activarModuloTransferencia = true;
        activarSalaTransferencia = true;
        salaLaboratorioTransferencia = null;
        moduloLaboratorioTransferencia = null;
        equipoElementoTransferencia = null;
        transferirComponente = true;
        componenteEquipoTransferencia = null;
        inactivarElementosDestino();
        listaLaboratorioTransferencia = gestionarPlantaTransferenciaElementosBO.obtenerLaboratoriosRegistrados();
    }

    private void inactivarElementosDestino() {
        laboratorio = null;
        activarLaboratorio = true;
        activarEquipo = true;
        activarModulo = true;
        activarSala = true;
        salaLaboratorio = null;
        moduloLaboratorio = null;
        equipoElemento = null;
        listaEquipoElemento = null;
        listaLaboratorio = null;
        listaModuloLaboratorio = null;
        listaSalaLaboratorio = null;
    }

    public List<Laboratorio> getListaLaboratorioTransferencia() {
        return listaLaboratorioTransferencia;
    }

    public void setListaLaboratorioTransferencia(List<Laboratorio> listaLaboratorioTransferencia) {
        this.listaLaboratorioTransferencia = listaLaboratorioTransferencia;
    }

    public Laboratorio getLaboratorioTransferencia() {
        return laboratorioTransferencia;
    }

    public void setLaboratorioTransferencia(Laboratorio laboratorioTransferencia) {
        this.laboratorioTransferencia = laboratorioTransferencia;
    }

    public List<SalaLaboratorio> getListaSalaLaboratorioTransferencia() {
        return listaSalaLaboratorioTransferencia;
    }

    public void setListaSalaLaboratorioTransferencia(List<SalaLaboratorio> listaSalaLaboratorioTransferencia) {
        this.listaSalaLaboratorioTransferencia = listaSalaLaboratorioTransferencia;
    }

    public SalaLaboratorio getSalaLaboratorioTransferencia() {
        return salaLaboratorioTransferencia;
    }

    public void setSalaLaboratorioTransferencia(SalaLaboratorio salaLaboratorioTransferencia) {
        this.salaLaboratorioTransferencia = salaLaboratorioTransferencia;
    }

    public boolean isActivarSalaTransferencia() {
        return activarSalaTransferencia;
    }

    public void setActivarSalaTransferencia(boolean activarSalaTransferencia) {
        this.activarSalaTransferencia = activarSalaTransferencia;
    }

    public List<ModuloLaboratorio> getListaModuloLaboratorioTransferencia() {
        return listaModuloLaboratorioTransferencia;
    }

    public void setListaModuloLaboratorioTransferencia(List<ModuloLaboratorio> listaModuloLaboratorioTransferencia) {
        this.listaModuloLaboratorioTransferencia = listaModuloLaboratorioTransferencia;
    }

    public ModuloLaboratorio getModuloLaboratorioTransferencia() {
        return moduloLaboratorioTransferencia;
    }

    public void setModuloLaboratorioTransferencia(ModuloLaboratorio moduloLaboratorioTransferencia) {
        this.moduloLaboratorioTransferencia = moduloLaboratorioTransferencia;
    }

    public boolean isActivarModuloTransferencia() {
        return activarModuloTransferencia;
    }

    public void setActivarModuloTransferencia(boolean activarModuloTransferencia) {
        this.activarModuloTransferencia = activarModuloTransferencia;
    }

    public List<EquipoElemento> getListaEquipoElementoTransferencia() {
        return listaEquipoElementoTransferencia;
    }

    public void setListaEquipoElementoTransferencia(List<EquipoElemento> listaEquipoElementoTransferencia) {
        this.listaEquipoElementoTransferencia = listaEquipoElementoTransferencia;
    }

    public EquipoElemento getEquipoElementoTransferencia() {
        return equipoElementoTransferencia;
    }

    public void setEquipoElementoTransferencia(EquipoElemento equipoElementoTransferencia) {
        this.equipoElementoTransferencia = equipoElementoTransferencia;
    }

    public boolean isActivarEquipoTransferencia() {
        return activarEquipoTransferencia;
    }

    public void setActivarEquipoTransferencia(boolean activarEquipoTransferencia) {
        this.activarEquipoTransferencia = activarEquipoTransferencia;
    }

    public List<ComponenteEquipo> getListaComponenteEquipoTransferencia() {
        return listaComponenteEquipoTransferencia;
    }

    public void setListaComponenteEquipoTransferencia(List<ComponenteEquipo> listaComponenteEquipoTransferencia) {
        this.listaComponenteEquipoTransferencia = listaComponenteEquipoTransferencia;
    }

    public ComponenteEquipo getComponenteEquipoTransferencia() {
        return componenteEquipoTransferencia;
    }

    public void setComponenteEquipoTransferencia(ComponenteEquipo componenteEquipoTransferencia) {
        this.componenteEquipoTransferencia = componenteEquipoTransferencia;
    }

    public boolean isActivarComponenteTransferencia() {
        return activarComponenteTransferencia;
    }

    public void setActivarComponenteTransferencia(boolean activarComponenteTransferencia) {
        this.activarComponenteTransferencia = activarComponenteTransferencia;
    }

    public List<Laboratorio> getListaLaboratorio() {
        return listaLaboratorio;
    }

    public void setListaLaboratorio(List<Laboratorio> listaLaboratorio) {
        this.listaLaboratorio = listaLaboratorio;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public List<SalaLaboratorio> getListaSalaLaboratorio() {
        return listaSalaLaboratorio;
    }

    public void setListaSalaLaboratorio(List<SalaLaboratorio> listaSalaLaboratorio) {
        this.listaSalaLaboratorio = listaSalaLaboratorio;
    }

    public SalaLaboratorio getSalaLaboratorio() {
        return salaLaboratorio;
    }

    public void setSalaLaboratorio(SalaLaboratorio salaLaboratorio) {
        this.salaLaboratorio = salaLaboratorio;
    }

    public boolean isActivarSala() {
        return activarSala;
    }

    public void setActivarSala(boolean activarSala) {
        this.activarSala = activarSala;
    }

    public List<ModuloLaboratorio> getListaModuloLaboratorio() {
        return listaModuloLaboratorio;
    }

    public void setListaModuloLaboratorio(List<ModuloLaboratorio> listaModuloLaboratorio) {
        this.listaModuloLaboratorio = listaModuloLaboratorio;
    }

    public ModuloLaboratorio getModuloLaboratorio() {
        return moduloLaboratorio;
    }

    public void setModuloLaboratorio(ModuloLaboratorio moduloLaboratorio) {
        this.moduloLaboratorio = moduloLaboratorio;
    }

    public boolean isActivarModulo() {
        return activarModulo;
    }

    public void setActivarModulo(boolean activarModulo) {
        this.activarModulo = activarModulo;
    }

    public List<EquipoElemento> getListaEquipoElemento() {
        return listaEquipoElemento;
    }

    public void setListaEquipoElemento(List<EquipoElemento> listaEquipoElemento) {
        this.listaEquipoElemento = listaEquipoElemento;
    }

    public EquipoElemento getEquipoElemento() {
        return equipoElemento;
    }

    public void setEquipoElemento(EquipoElemento equipoElemento) {
        this.equipoElemento = equipoElemento;
    }

    public boolean isActivarEquipo() {
        return activarEquipo;
    }

    public void setActivarEquipo(boolean activarEquipo) {
        this.activarEquipo = activarEquipo;
    }

    public boolean isProcesoCompletado() {
        return procesoCompletado;
    }

    public void setProcesoCompletado(boolean procesoCompletado) {
        this.procesoCompletado = procesoCompletado;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isInactivarAceptar() {
        return inactivarAceptar;
    }

    public void setInactivarAceptar(boolean inactivarAceptar) {
        this.inactivarAceptar = inactivarAceptar;
    }

    public boolean isTransferirComponente() {
        return transferirComponente;
    }

    public void setTransferirComponente(boolean transferirComponente) {
        this.transferirComponente = transferirComponente;
    }

}
