/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.PerfilPorEncargado;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Controlador: ControllerRegistrarEncargadoLaboratorio Este controlador se
 * encarga del registro de un nuevo encargado de laboratorio al sistema de
 * información
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarEncargadoLaboratorio implements Serializable {

    //
    @EJB
    AdministrarEncargadosLaboratoriosBOInterface administrarEncargadosLaboratoriosBO;
    //

    private String inputNombre, inputApellido, inputID, inputEmail, inputContrasenia, inputContraseniaConfirma, inputTelefono1, inputTelefono2, inputDireccion;
    private List<Facultad> listaFacultades;
    private Facultad inputFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento inputDepartamento;
    private boolean activarDepartamento;
    private List<Laboratorio> listaLaboratorios;
    private Laboratorio inputLaboratorio;
    private boolean activarLaboratorio;
    private List<PerfilPorEncargado> listaPerfilesPorEncargado;
    private PerfilPorEncargado inputPerfil;
    private String paginaAnterior;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesPassw, validacionesTel1, validacionesTel2, validacionesPerfil;
    private boolean validacionesDireccion, validacionesPassw2, validacionesFacultad, validacionesDepartamento, validacionesLaboratorio;
    private String mensajeFormulario;

    public ControllerRegistrarEncargadoLaboratorio() {
    }

    @PostConstruct
    public void init() {
        validacionesPerfil = false;
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesPassw2 = false;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        validacionesLaboratorio = false;
        mensajeFormulario = "";
        //
        activarDepartamento = true;
        activarLaboratorio = true;
        inputApellido = null;
        inputFacultad = null;
        inputContrasenia = null;
        inputContraseniaConfirma = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputPerfil = null;
        inputNombre = null;
        inputDepartamento = null;
        listaFacultades = administrarEncargadosLaboratoriosBO.obtenerListaFacultades();
        listaPerfilesPorEncargado = administrarEncargadosLaboratoriosBO.consultarPerfilesPorEncargadoRegistrados();
        listaDepartamentos = null;
        listaLaboratorios = null;
    }

    /**
     * Metodo encargado de la actualizacion de las facultades por parte del
     * usuario y obtiene los departamentos relacionados
     */
    public void actualizarFacultades() {
        try {
            if (Utilidades.validarNulo(inputFacultad)) {
                activarDepartamento = false;
                inputDepartamento = null;
                listaDepartamentos = administrarEncargadosLaboratoriosBO.obtenerDepartamentosPorIDFacultad(inputFacultad.getIdfacultad());
                activarLaboratorio = true;
                inputLaboratorio = null;
                listaLaboratorios = null;
                validacionesFacultad = true;
            } else {
                validacionesFacultad = false;
                validacionesDepartamento = false;
                validacionesLaboratorio = false;
                activarDepartamento = true;
                listaDepartamentos = null;
                inputDepartamento = null;
                activarLaboratorio = true;
                inputLaboratorio = null;
                listaLaboratorios = null;
                FacesContext.getCurrentInstance().addMessage("form:inputDepartamento", new FacesMessage("El campo Facultad es obligatorio."));
            }
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarEncargadoLaboratorio actualizarDepartamentos : " + e.toString());
        }
    }

    /**
     * Metodo encargado de la actualizacion de los departamentos por parte del
     * usuario y obtiene los laboratorios relacionados
     */
    public void actualizarDepartamentos() {
        try {
            if (Utilidades.validarNulo(inputDepartamento)) {
                activarLaboratorio = false;
                inputLaboratorio = null;
                listaLaboratorios = administrarEncargadosLaboratoriosBO.obtenerLaboratoriosPorIDDepartamento(inputDepartamento.getIddepartamento());
                validacionesDepartamento = true;
            } else {
                validacionesDepartamento = false;
                validacionesLaboratorio = false;
                activarLaboratorio = true;
                inputLaboratorio = null;
                listaLaboratorios = null;
                FacesContext.getCurrentInstance().addMessage("form:inputDepartamento", new FacesMessage("El campo Departamento es obligatorio."));
            }
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarEncargadoLaboratorio actualizarDepartamentos : " + e.toString());
        }
    }

    public void actualizarLaboratorio() {
        if (Utilidades.validarNulo(inputLaboratorio)) {
            validacionesLaboratorio = true;
        } else {
            validacionesLaboratorio = false;
            FacesContext.getCurrentInstance().addMessage("form:inputLaboratorio", new FacesMessage("El campo Laboratorio es obligatorio."));
        }
    }

    public void actualizarPerfilPorEncargado() {
        if (Utilidades.validarNulo(inputPerfil)) {
            validacionesPerfil = true;
        } else {
            validacionesPerfil = false;
            FacesContext.getCurrentInstance().addMessage("form:inputPerfil", new FacesMessage("El campo Perfil por Encargado es obligatorio."));
        }
    }

    public void validarNombreEncargadoLaboratorio() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty())) {
            if (!Utilidades.validarCaracterString(inputNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:inputNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarApellidoEncargadoLaboratorio() {
        if (Utilidades.validarNulo(inputApellido) && (!inputApellido.isEmpty())) {
            if (!Utilidades.validarCaracterString(inputApellido)) {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido ingresado es incorrecto."));
            } else {
                validacionesApellido = true;
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:inputApellido", new FacesMessage("El apellido es obligatorio."));
        }
    }

    public void validarCorreoEncargadoLaboratorio() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty())) {
            String correoEncargadoLaboratorio = inputEmail + "@ucentral.edu.co";
            if (Utilidades.validarCorreoElectronico(correoEncargadoLaboratorio)) {
                EncargadoLaboratorio registro = administrarEncargadosLaboratoriosBO.obtenerEncargadoLaboratorioPorCorreo(inputEmail);
                if (null == registro) {
                    validacionesCorreo = true;
                } else {
                    validacionesCorreo = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo ya se encuentra registrado."));
                }
            } else {
                validacionesCorreo = false;
                FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo se encuentra incorrecto."));
            }
        } else {
            validacionesCorreo = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio."));
        }
    }

    public void validarIdentificacionEncargadoLaboratorio() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputID)) {
                EncargadoLaboratorio registro = administrarEncargadosLaboratoriosBO.obtenerEncargadoLaboratorioPorDocumento(inputID);
                if (null == registro) {
                    validacionesID = true;
                } else {
                    validacionesID = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El documento ya se encuentra registrado."));
                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación se encuentra incorrecto."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:inputID", new FacesMessage("El numero identificación es obligatorio."));
        }
    }

    public void validarDatosNumericosEncargadoLaboratorio(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty())) {
                if ((Utilidades.isNumber(inputTelefono1)) == false) {
                    validacionesTel1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTel1 = true;
                }
            }
        } else {
            if (Utilidades.validarNulo(inputTelefono2) && (!inputTelefono2.isEmpty())) {
                if ((Utilidades.isNumber(inputTelefono2)) == false) {
                    validacionesTel2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTel2 = true;
                }
            }
        }
    }

    public void validarDireccionEncargadoLaboratorio() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputDireccion)) {
                validacionesDireccion = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                validacionesDireccion = false;
            }
        }
    }

    public void validarContraseniaEncargadoLaboratorio() {
        if ((Utilidades.validarNulo(inputContrasenia)) && (!inputContrasenia.isEmpty())) {
            validacionesPassw = true;
        } else {
            FacesContext.getCurrentInstance().addMessage("form:inputContrasenia", new FacesMessage("La contraseña es obligatoria."));
            validacionesPassw = false;
        }
    }

    public void validarContraseniaConfirmaEncargadoLaboratorio() {
        if ((Utilidades.validarNulo(inputContrasenia)) && (Utilidades.validarNulo(inputContraseniaConfirma))
                && (!inputContrasenia.isEmpty()) && (!inputContraseniaConfirma.isEmpty())) {
            if (inputContrasenia.equals(inputContraseniaConfirma)) {
                validacionesPassw2 = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputContraseniaConfirma", new FacesMessage("Las contraseñas ingresadas no concuerdan."));
                validacionesPassw2 = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:inputContraseniaConfirma", new FacesMessage("Las contraseñas son obligatorias."));
            validacionesPassw2 = false;
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesApellido == false) {
            retorno = false;
        }
        if (validacionesDepartamento == false) {
            retorno = false;
        }
        if (validacionesCorreo == false) {
            retorno = false;
        }
        if (validacionesDireccion == false) {
            retorno = false;
        }
        if (validacionesID == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }
        if (validacionesPassw == false) {
            retorno = false;
        }
        if (validacionesPerfil == false) {
            retorno = false;
        }
        if (validacionesPassw2 == false) {
            retorno = false;
        }
        if (validacionesFacultad == false) {
            retorno = false;
        }
        if (validacionesLaboratorio == false) {
            retorno = false;
        }
        if (validacionesTel1 == false) {
            retorno = false;
        }
        if (validacionesTel2 == false) {
            retorno = false;
        }
        return retorno;
    }

    /**
     * Metodo encargado de realizar el registro y validaciones de la información
     * del nuevo encargado
     */
    public void registrarNuevoEncargadoLaboratorio() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoEncargadoLaboratorioEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroEncargadoLaboratorio() {
        activarDepartamento = true;
        inputApellido = null;
        inputFacultad = null;
        inputContrasenia = null;
        inputContraseniaConfirma = null;
        inputDireccion = null;
        inputEmail = null;
        inputID = null;
        inputNombre = null;
        inputDepartamento = null;
        inputLaboratorio = null;
        listaFacultades = null;
        listaDepartamentos = null;
        listaLaboratorios = null;
        listaPerfilesPorEncargado = null;
        inputPerfil = null;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información el nuevo
     * encargado
     */
    private void almacenarNuevoEncargadoLaboratorioEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setNombreusuario(inputEmail);
            usuarioNuevo.setPasswordusuario(inputContrasenia);
            Persona personaNueva = new Persona();
            personaNueva.setApellidospersona(inputApellido);
            personaNueva.setDireccionpersona(inputDireccion);
            personaNueva.setEmailpersona(inputEmail);
            personaNueva.setIdentificacionpersona(inputID);
            personaNueva.setNombrespersona(inputNombre);
            personaNueva.setTelefono1persona(inputTelefono1);
            personaNueva.setTelefono2persona(inputTelefono2);
            EncargadoLaboratorio encargadoNueva = new EncargadoLaboratorio();
            encargadoNueva.setLaboratorio(inputLaboratorio);
            encargadoNueva.setPerfilporencargado(inputPerfil);
            administrarEncargadosLaboratoriosBO.almacenarNuevoEncargadoLaboratorioEnSistema(usuarioNuevo, personaNueva, encargadoNueva);
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarEncargadoLaboratorio almacenarNuevoEncargadoLaboratorioEnSistema : " + e.toString());
        }
    }

    public void recibirPaginaAnterior(String page) {
        paginaAnterior = page;
    }

    public String retornarPaginaAnterior() {
        if (null != paginaAnterior) {
            return paginaAnterior;
        }
        return "index.xhtml";
    }

    //GET - SET
    public String getInputNombre() {
        return inputNombre;
    }

    public void setInputNombre(String inputNombre) {
        this.inputNombre = inputNombre;
    }

    public String getInputApellido() {
        return inputApellido;
    }

    public void setInputApellido(String inputApellido) {
        this.inputApellido = inputApellido;
    }

    public String getInputID() {
        return inputID;
    }

    public void setInputID(String inputID) {
        this.inputID = inputID;
    }

    public String getInputEmail() {
        return inputEmail;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public String getInputContrasenia() {
        return inputContrasenia;
    }

    public void setInputContrasenia(String inputContrasenia) {
        this.inputContrasenia = inputContrasenia;
    }

    public String getInputContraseniaConfirma() {
        return inputContraseniaConfirma;
    }

    public void setInputContraseniaConfirma(String inputContraseniaConfirma) {
        this.inputContraseniaConfirma = inputContraseniaConfirma;
    }

    public String getInputTelefono1() {
        return inputTelefono1;
    }

    public void setInputTelefono1(String inputTelefono1) {
        this.inputTelefono1 = inputTelefono1;
    }

    public String getInputTelefono2() {
        return inputTelefono2;
    }

    public void setInputTelefono2(String inputTelefono2) {
        this.inputTelefono2 = inputTelefono2;
    }

    public String getInputDireccion() {
        return inputDireccion;
    }

    public void setInputDireccion(String inputDireccion) {
        this.inputDireccion = inputDireccion;
    }

    public List<Facultad> getListaFacultades() {
        return listaFacultades;
    }

    public void setListaFacultades(List<Facultad> listaFacultades) {
        this.listaFacultades = listaFacultades;
    }

    public Facultad getInputFacultad() {
        return inputFacultad;
    }

    public void setInputFacultad(Facultad inputFacultad) {
        this.inputFacultad = inputFacultad;
    }

    public List<Departamento> getListaDepartamentos() {
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public Departamento getInputDepartamento() {
        return inputDepartamento;
    }

    public void setInputDepartamento(Departamento inputDepartamento) {
        this.inputDepartamento = inputDepartamento;
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

    public Laboratorio getInputLaboratorio() {
        return inputLaboratorio;
    }

    public void setInputLaboratorio(Laboratorio inputLaboratorio) {
        this.inputLaboratorio = inputLaboratorio;
    }

    public boolean isActivarLaboratorio() {
        return activarLaboratorio;
    }

    public void setActivarLaboratorio(boolean activarLaboratorio) {
        this.activarLaboratorio = activarLaboratorio;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public List<PerfilPorEncargado> getListaPerfilesPorEncargado() {
        return listaPerfilesPorEncargado;
    }

    public void setListaPerfilesPorEncargado(List<PerfilPorEncargado> listaPerfilesPorEncargado) {
        this.listaPerfilesPorEncargado = listaPerfilesPorEncargado;
    }

    public PerfilPorEncargado getInputPerfil() {
        return inputPerfil;
    }

    public void setInputPerfil(PerfilPorEncargado inputPerfil) {
        this.inputPerfil = inputPerfil;
    }

}
