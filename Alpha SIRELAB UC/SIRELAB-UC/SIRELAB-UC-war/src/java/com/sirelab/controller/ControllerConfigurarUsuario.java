/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller;

import com.sirelab.bo.interfacebo.GestionarConfigurarUsuarioBOInterface;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
import com.sirelab.utilidades.UsuarioLogin;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControllerConfigurarUsuario implements Serializable {

    @EJB
    GestionarConfigurarUsuarioBOInterface gestionarConfigurarUsuarioBO;

    private Persona personaConfigurar;
    private String inputNombre, inputApellido, inputID, inputEmail, inputTelefono1, inputTelefono2, inputDireccion;
    private String inputContrasenia, inputContraseniaNueva, inputContraseniaConfirma;
    private String mensajeFormulario2, mensajeFormulario1;
    private boolean validacionesNombre, validacionesApellido, validacionesID, validacionesEmail;
    private boolean validacionesTelefono1, validacionesTelefono2, validacionesDireccion;
    private boolean validacionesContrasenia, validacionesContraseniaNueva, validacionesContraseniaConfirma;
    private UsuarioLogin usuarioLogin;
    private String mensajeNombre, mensajeApellido;
    private boolean activarEmail;

    public ControllerConfigurarUsuario() {
    }

    @PostConstruct
    public void init() {
        mensajeFormulario1 = "";
        mensajeFormulario2 = "";
        validacionesApellido = true;
        validacionesNombre = true;
        validacionesID = true;
        validacionesEmail = true;
        validacionesTelefono1 = true;
        validacionesTelefono2 = true;
        validacionesDireccion = true;
        validacionesContrasenia = false;
        validacionesContraseniaNueva = false;
        validacionesContraseniaConfirma = false;
    }

    public void recibirUsuarioLogin() {
        obtenerPersonaConfigurar();
    }

    private void obtenerPersonaConfigurar() {
        usuarioLogin = (UsuarioLogin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sessionUsuario");
        personaConfigurar = gestionarConfigurarUsuarioBO.obtenerPersonaPorUsuarioModificar(usuarioLogin.getIdUsuarioLogin(), usuarioLogin.getNombreTipoUsuario());
        cargarDatosPersona();
    }

    private void cargarDatosPersona() {
        if ("ENTIDADEXTERNA".equalsIgnoreCase(usuarioLogin.getNombreTipoUsuario())) {
            inputEmail = personaConfigurar.getEmailpersona();
            mensajeApellido = "Nombre Secundario Entidad";
            mensajeNombre = "Nombre Inicial Entidad";
            activarEmail = false;
        } else {
            inputEmail = personaConfigurar.getEmailpersona() + "@ucentral.edu.co";
            activarEmail = true;
            mensajeApellido = "Nombre(s) Persona";
            mensajeNombre = "Apellido(s) Persona";
        }
        inputApellido = personaConfigurar.getApellidospersona();
        inputDireccion = personaConfigurar.getDireccionpersona();
        inputID = personaConfigurar.getIdentificacionpersona();
        inputNombre = personaConfigurar.getNombrespersona();
        inputTelefono1 = personaConfigurar.getTelefono1persona();
        inputTelefono2 = personaConfigurar.getTelefono2persona();
    }

    public String cancelarProcesosModificacion() {
        cancelarActualizarContrasenia();
        cancelarModificacionUsuario();
        String pagina = "";
        // 1- Administrador / 2- Docente / 3- Estudiante / 4-EncargadoLab / 5-EntidadExterna
        BigInteger secuencia = new BigInteger("1");
        if (secuencia.equals(personaConfigurar.getUsuario().getTipousuario().getIdtipousuario())) {
            pagina = "paginainicioA";
        } else {
            secuencia = new BigInteger("4");
            if (secuencia.equals(personaConfigurar.getUsuario().getTipousuario().getIdtipousuario())) {
                pagina = "paginainicioL";
            } else {
                secuencia = new BigInteger("3");
            }
        }
        return pagina;
    }

    public void validarNombrePersona() {
        if (Utilidades.validarNulo(inputNombre) && (!inputNombre.isEmpty())  && (inputNombre.trim().length() > 0)) {
            if (!Utilidades.validarCaracterString(inputNombre)) {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:form1:inputNombre", new FacesMessage("El nombre ingresado es incorrecto."));
            } else {
                validacionesNombre = true;
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:form1:inputNombre", new FacesMessage("El nombre es obligatorio."));
        }

    }

    public void validarApellidoPersona() {
        if (Utilidades.validarNulo(inputApellido) && (!inputApellido.isEmpty())  && (inputApellido.trim().length() > 0)) {
            if (!Utilidades.validarCaracterString(inputApellido)) {
                validacionesApellido = false;
                FacesContext.getCurrentInstance().addMessage("form:form1:inputApellido", new FacesMessage("El apellido ingresado es incorrecto."));
            } else {
                validacionesApellido = true;
            }
        } else {
            validacionesApellido = false;
            FacesContext.getCurrentInstance().addMessage("form:form1:inputApellido", new FacesMessage("El apellido es obligatorio."));
        }
    }

    public void validarEmailPersona() {
        if (Utilidades.validarNulo(inputEmail) && (!inputEmail.isEmpty())  && (inputEmail.trim().length() > 0)) {
            String correoPersona = inputEmail + "@ucentral.edu.co";
            if (Utilidades.validarCorreoElectronico(correoPersona)) {
                Persona registro = gestionarConfigurarUsuarioBO.obtenerPersonaPorEmail(inputEmail);
                if (null == registro) {
                    validacionesEmail = true;
                } else {
                    validacionesEmail = false;
                    FacesContext.getCurrentInstance().addMessage("form:form1:inputEmail", new FacesMessage("El correo ya se encuentra registrado."));
                }
            } else {
                validacionesEmail = false;
                FacesContext.getCurrentInstance().addMessage("form:form1:inputEmail", new FacesMessage("El correo se encuentra incorrecto."));
            }
        } else {
            validacionesEmail = false;
            FacesContext.getCurrentInstance().addMessage("form:inputEmail", new FacesMessage("El correo es obligatorio."));
        }
    }

    public void validarIdentificacionPersona() {
        if (Utilidades.validarNulo(inputID) && (!inputID.isEmpty())  && (inputID.trim().length() > 0)) {
            if (Utilidades.validarNumeroIdentificacion(inputID)) {
                Persona registro = gestionarConfigurarUsuarioBO.obtenerPersonaPorDocumento(inputID);
                if (null == registro) {
                    validacionesID = true;
                } else {
                    if (personaConfigurar.getIdentificacionpersona().equals(registro.getIdentificacionpersona())) {
                        validacionesID = true;
                    } else {
                        validacionesID = false;
                        FacesContext.getCurrentInstance().addMessage("form:form1:inputID", new FacesMessage("El documento ya se encuentra registrado."));
                    }

                }
            } else {
                validacionesID = false;
                FacesContext.getCurrentInstance().addMessage("form:form1:inputID", new FacesMessage("El numero identificación se encuentra incorrecto."));
            }
        } else {
            validacionesID = false;
            FacesContext.getCurrentInstance().addMessage("form:form1:inputID", new FacesMessage("El numero identificación es obligatorio."));
        }
    }

    public void validarDatosNumericosPersona(int tipoTel) {
        if (tipoTel == 1) {
            if (Utilidades.validarNulo(inputTelefono1) && (!inputTelefono1.isEmpty())  && (inputTelefono1.trim().length() > 0)) {
                if ((Utilidades.isNumber(inputTelefono1)) == false) {
                    validacionesTelefono1 = false;
                    FacesContext.getCurrentInstance().addMessage("form:form1:inputTelefono1", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTelefono1 = true;
                }
            }
        } else {
            if (Utilidades.validarNulo(inputTelefono2) && (!inputTelefono2.isEmpty())  && (inputTelefono2.trim().length() > 0)) {
                if ((Utilidades.isNumber(inputTelefono2)) == false) {
                    validacionesTelefono2 = false;
                    FacesContext.getCurrentInstance().addMessage("form:form1:inputTelefono2", new FacesMessage("El numero telefonico se encuentra incorrecto."));
                } else {
                    validacionesTelefono2 = true;
                }
            }
        }
    }

    public void validarDireccionPersona() {
        if ((Utilidades.validarNulo(inputDireccion)) && (!inputDireccion.isEmpty())  && (inputDireccion.trim().length() > 0)) {
            if (Utilidades.validarDirecciones(inputDireccion)) {
                validacionesDireccion = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:form1:inputDireccion", new FacesMessage("La dirección se encuentra incorrecta."));
                validacionesDireccion = false;
            }
        }
    }

    public void validarContraseniaPersona() {
        if ((Utilidades.validarNulo(inputContrasenia)) && (!inputContrasenia.isEmpty())  && (inputContrasenia.trim().length() > 0)) {
            EncriptarContrasenia obj = new EncriptarContrasenia();
            String contrasenia = obj.encriptarContrasenia(inputContrasenia);
            if (personaConfigurar.getUsuario().getPasswordusuario().equals(contrasenia)) {
                validacionesContrasenia = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:form2:inputContrasenia", new FacesMessage("La contraseña ingresada no es la correspondiente del usuario."));
                validacionesContrasenia = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:form2:inputContrasenia", new FacesMessage("La contraseña es obligatoria."));
            validacionesContrasenia = false;
        }
    }

    public void validarContraseniaNuevaPersona() {
        if ((Utilidades.validarNulo(inputContraseniaNueva)) && (!inputContraseniaNueva.isEmpty())  && (inputContraseniaNueva.trim().length() > 0)) {
            EncriptarContrasenia obj = new EncriptarContrasenia();
            String contrasenia = obj.encriptarContrasenia(inputContraseniaNueva);
            if (!personaConfigurar.getUsuario().getPasswordusuario().equals(contrasenia)) {
                validacionesContraseniaNueva = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:form2:inputContraseniaNueva", new FacesMessage("La contraseña ingresada es igual que la actual del usuario."));
                validacionesContraseniaNueva = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:form2:inputContraseniaNueva", new FacesMessage("La contraseña es obligatoria."));
            validacionesContraseniaNueva = false;
        }
    }

    public void validarContraseniaConfirmaPersona() {
        if ((Utilidades.validarNulo(inputContraseniaNueva)) && (Utilidades.validarNulo(inputContraseniaConfirma))
                && (!inputContraseniaNueva.isEmpty()) && (!inputContraseniaConfirma.isEmpty())  && (inputContraseniaConfirma.trim().length() > 0)) {
            if (inputContraseniaNueva.equals(inputContraseniaConfirma)) {
                validacionesContraseniaConfirma = true;
            } else {
                FacesContext.getCurrentInstance().addMessage("form:form2:inputContraseniaConfirma", new FacesMessage("Las contraseñas ingresadas no concuerdan."));
                validacionesContraseniaConfirma = false;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("form:form2:inputContraseniaConfirma", new FacesMessage("Las contraseñas son obligatorias."));
            validacionesContraseniaConfirma = false;
        }
    }

    private boolean validarResultadosModificacion() {
        boolean retorno = true;
        if (validacionesApellido == false) {
            retorno = false;
        }
        if (validacionesEmail == false) {
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
        if (validacionesTelefono1 == false) {
            retorno = false;
        }
        if (validacionesTelefono2 == false) {
            retorno = false;
        }
        return retorno;
    }

    private boolean validarResultadosContrasenia() {
        boolean retorno = true;
        if (validacionesContrasenia == false) {
            retorno = false;
        }
        if (validacionesContraseniaConfirma == false) {
            retorno = false;
        }
        if (validacionesContraseniaNueva == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarModificacionUsuario() {
        if (validarResultadosModificacion() == true) {
            personaConfigurar.setApellidospersona(inputApellido);
            personaConfigurar.setDireccionpersona(inputDireccion);
            personaConfigurar.setEmailpersona(inputEmail);
            personaConfigurar.setIdentificacionpersona(inputID);
            personaConfigurar.setNombrespersona(inputNombre);
            personaConfigurar.setTelefono1persona(inputTelefono1);
            personaConfigurar.setTelefono2persona(inputTelefono2);
            gestionarConfigurarUsuarioBO.actualizarInformacionPersona(personaConfigurar);
            mensajeFormulario1 = "El formulario ha sido registrado con exito.";
            obtenerPersonaConfigurar();
        } else {
            mensajeFormulario1 = "Existen errores en el formulario. Por favor corregir para guardar los datos";
        }
    }

    public void cancelarModificacionUsuario() {
        obtenerPersonaConfigurar();
        validacionesApellido = true;
        validacionesNombre = true;
        validacionesID = true;
        validacionesEmail = true;
        validacionesTelefono1 = true;
        validacionesTelefono2 = true;
        validacionesDireccion = true;
    }

    public void actualizarContrasenia() {
        if (validarResultadosContrasenia() == true) {
            Usuario usuario = personaConfigurar.getUsuario();
            EncriptarContrasenia obj = new EncriptarContrasenia();
            usuario.setPasswordusuario(obj.encriptarContrasenia(inputContraseniaNueva));
            gestionarConfigurarUsuarioBO.actualizarContraseniaPersona(usuario);
            mensajeFormulario2 = "Se registro con exito el cambio de contraseña";
        } else {
            mensajeFormulario2 = "Existen errores en el formulario. Por favor corregir para guardar los datos";
        }
    }

    public void cancelarActualizarContrasenia() {
        validacionesContrasenia = false;
        validacionesContraseniaConfirma = false;
        validacionesContraseniaNueva = false;
        inputContrasenia = null;
        inputContraseniaConfirma = null;
        inputContraseniaNueva = null;
    }

    //GET-SET
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

    public String getInputContrasenia() {
        return inputContrasenia;
    }

    public void setInputContrasenia(String inputContrasenia) {
        this.inputContrasenia = inputContrasenia;
    }

    public String getInputContraseniaNueva() {
        return inputContraseniaNueva;
    }

    public void setInputContraseniaNueva(String inputContraseniaNueva) {
        this.inputContraseniaNueva = inputContraseniaNueva;
    }

    public String getInputContraseniaConfirma() {
        return inputContraseniaConfirma;
    }

    public void setInputContraseniaConfirma(String inputContraseniaConfirma) {
        this.inputContraseniaConfirma = inputContraseniaConfirma;
    }

    public String getMensajeFormulario2() {
        return mensajeFormulario2;
    }

    public void setMensajeFormulario2(String mensajeFormulario2) {
        this.mensajeFormulario2 = mensajeFormulario2;
    }

    public String getMensajeFormulario1() {
        return mensajeFormulario1;
    }

    public void setMensajeFormulario1(String mensajeFormulario1) {
        this.mensajeFormulario1 = mensajeFormulario1;
    }

    public String getMensajeNombre() {
        return mensajeNombre;
    }

    public void setMensajeNombre(String mensajeNombre) {
        this.mensajeNombre = mensajeNombre;
    }

    public String getMensajeApellido() {
        return mensajeApellido;
    }

    public void setMensajeApellido(String mensajeApellido) {
        this.mensajeApellido = mensajeApellido;
    }

    public boolean isActivarEmail() {
        return activarEmail;
    }

    public void setActivarEmail(boolean activarEmail) {
        this.activarEmail = activarEmail;
    }

}
