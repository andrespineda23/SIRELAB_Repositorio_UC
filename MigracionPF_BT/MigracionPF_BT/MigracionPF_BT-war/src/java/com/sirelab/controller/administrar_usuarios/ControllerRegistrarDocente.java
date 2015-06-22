package com.sirelab.controller.administrar_usuarios;

import com.sirelab.bo.interfacebo.AdministrarDocentesBOInterface;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
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
 * Controlador: ControllerRegistrarDocente Este controlador se encarga del
 * proceso de registro de un nuevo docente en el sistema de información
 *
 * @author ANDRES PINEDA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarDocente implements Serializable {

    //
    @EJB
    AdministrarDocentesBOInterface administrarDocentesBO;
    //

    private String inputNombre, inputApellido, inputID, inputEmail, inputContrasenia, inputContraseniaConfirma, inputTelefono1, inputTelefono2, inputDireccion;
    private String inputCargo;
    private List<Facultad> listaFacultades;
    private Facultad inputFacultad;
    private List<Departamento> listaDepartamentos;
    private Departamento inputDepartamento;
    private boolean activarDepartamento;
    private String paginaAnterior;
    private boolean validacionesNombre, validacionesApellido, validacionesCorreo;
    private boolean validacionesID, validacionesPassw, validacionesTel1, validacionesTel2;
    private boolean validacionesDireccion, validacionesPassw2, validacionesCargo, validacionesFacultad, validacionesDepartamento;
    private String mensajeFormulario;

    public ControllerRegistrarDocente() {
    }

    @PostConstruct
    public void init() {
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesPassw2 = false;
        validacionesCargo = false;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        mensajeFormulario = "";
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
        inputCargo = null;
        listaDepartamentos = null;
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
                listaDepartamentos = administrarDocentesBO.obtenerDepartamentosPorIDFacultad(inputFacultad.getIdfacultad());
                validacionesFacultad = true;
            } else {
                validacionesDepartamento = false;
                validacionesFacultad = false;
                activarDepartamento = true;
                listaDepartamentos = null;
                inputDepartamento = null;
                FacesContext.getCurrentInstance().addMessage("form:inputFacultad", new FacesMessage("El campo Facultad es obligatorio."));
            }
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarDocente actualizarFacultades : " + e.toString());
        }
    }

    public void actualizarDepartamentos() {
        try {
            if (Utilidades.validarNulo(inputDepartamento)) {
                validacionesDepartamento = true;
            } else {
                validacionesDepartamento = false;
                FacesContext.getCurrentInstance().addMessage("form:inputDepartamento", new FacesMessage("El campo Departamento es obligatorio."));
            }
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarDocente actualizarDepartamentos : " + e.toString());
        }
    }

    public void validarNombreDocente() {
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

    public void validarApellidoDocente() {
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

    public void validarCorreoDocente() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty())) {
            String correoDocente = inputEmail + "@ucentral.edu.co";
            if (Utilidades.validarCorreoElectronico(correoDocente)) {
                Docente registro = administrarDocentesBO.obtenerDocentePorCorreo(inputEmail);
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

    public void validarIdentificacionDocente() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputID)) {
                Docente registro = administrarDocentesBO.obtenerDocentePorDocumento(inputID);
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

    public void validarDatosNumericosDocente(int tipoTel) {
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

    public void validarDireccionDocente() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputDireccion)) {
                validacionesDireccion = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                validacionesDireccion = false;
            }
        }
    }

    public void validarCargoDocente() {
        if ((Utilidades.validarNulo(inputCargo)) && (!inputCargo.isEmpty())) {
            if (Utilidades.validarCaracteresAlfaNumericos(inputCargo)) {
                validacionesCargo = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:inputCargo", new FacesMessage("El cargo ingresado se encuentra incorrecto."));
                validacionesCargo = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:inputCargo", new FacesMessage("El campo Cargo es obligatorio."));
            validacionesCargo = false;
        }
    }

    public void validarContraseniaDocente() {
        if ((Utilidades.validarNulo(inputContrasenia)) && (!inputContrasenia.isEmpty())) {
            validacionesPassw = true;
        } else {
            FacesContext.getCurrentInstance().addMessage("form:inputContrasenia", new FacesMessage("La contraseña es obligatoria."));
            validacionesPassw = false;
        }
    }

    public void validarContraseniaConfirmaDocente() {
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
        if (validacionesPassw2 == false) {
            retorno = false;
        }
        if (validacionesFacultad == false) {
            retorno = false;
        }
        if (validacionesCargo == false) {
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
     * del nuevo docente
     */
    public void registrarNuevoDocente() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoDocenteEnSistema();
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    /**
     * Metodo encargado de cancelar el proceso de registro
     */
    public void cancelarRegistroDocente() {
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesPassw2 = false;
        validacionesCargo = false;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        mensajeFormulario = "";
        //
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
        inputCargo = null;
        listaFacultades = null;
        listaDepartamentos = null;
    }

    public void limpiarFormulario() {
        validacionesNombre = false;
        validacionesApellido = false;
        validacionesCorreo = false;
        validacionesID = false;
        validacionesPassw = false;
        validacionesTel1 = true;
        validacionesTel2 = true;
        validacionesDireccion = true;
        validacionesPassw2 = false;
        validacionesCargo = false;
        validacionesFacultad = false;
        validacionesDepartamento = false;
        //
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
        inputCargo = null;
    }

    /**
     * Metodo encargado de almacenar dentro del sistema de información el nuevo
     * docente
     */
    private void almacenarNuevoDocenteEnSistema() {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setEstado(true);
            usuarioNuevo.setNombreusuario(inputEmail);
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuarioNuevo.setPasswordusuario(obj.encriptarContrasenia(inputContrasenia));
            Persona personaNueva = new Persona();
            personaNueva.setApellidospersona(inputApellido);
            personaNueva.setDireccionpersona(inputDireccion);
            personaNueva.setEmailpersona(inputEmail);
            personaNueva.setIdentificacionpersona(inputID);
            personaNueva.setNombrespersona(inputNombre);
            personaNueva.setTelefono1persona(inputTelefono1);
            personaNueva.setTelefono2persona(inputTelefono2);
            Docente docenteNueva = new Docente();
            docenteNueva.setDepartamento(inputDepartamento);
            docenteNueva.setCargodocente(inputCargo);
            administrarDocentesBO.almacenarNuevoDocenteEnSistema(usuarioNuevo, personaNueva, docenteNueva);
            limpiarFormulario();
        } catch (Exception e) {
            System.out.println("Error ControllerRegistrarDocente almacenarNuevoDocenteEnSistema : " + e.toString());
        }
    }

    public void recibirPaginaAnterior(String page) {
        paginaAnterior = page;
    }

    public String retornarPaginaAnterior() {
        if (null != paginaAnterior) {
            return paginaAnterior;
        }
        return "administrar_docentes.xhtml";
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

    public String getInputCargo() {
        return inputCargo;
    }

    public void setInputCargo(String inputCargo) {
        this.inputCargo = inputCargo;
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

}
