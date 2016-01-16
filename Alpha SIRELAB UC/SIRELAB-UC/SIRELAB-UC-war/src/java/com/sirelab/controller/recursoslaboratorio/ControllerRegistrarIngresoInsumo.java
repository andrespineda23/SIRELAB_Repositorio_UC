/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.recursoslaboratorio;

import com.sirelab.ayuda.MensajesConstantes;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoIngresoInsumoBOInterface;
import com.sirelab.bo.interfacebo.recursos.GestionarRecursoInsumosBOInterface;
import com.sirelab.entidades.IngresoInsumo;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.Proveedor;
import com.sirelab.utilidades.Utilidades;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerRegistrarIngresoInsumo implements Serializable {

    @EJB
    GestionarRecursoIngresoInsumoBOInterface gestionarRecursoIngresoInsumoBO;
    @EJB
    GestionarRecursoInsumosBOInterface gestionarRecursoInsumoBO;

    private String nuevoFecha;
    private String nuevoCantidad, nuevoCosto;
    private List<Insumo> listaInsumos;
    private Insumo nuevoInsumo;
    private List<Proveedor> listaProveedores;
    private Proveedor nuevoProveedor;
    //
    private boolean validacionesFecha, validacionesCantidad, validacionesCosto, validacionesInsumo, validacionesProveedor;
    //
    private String mensajeFormulario;
    private Logger logger = Logger.getLogger(getClass().getName());
    private boolean activarCasillas;
    private String colorMensaje;
    private boolean activarLimpiar;
    private boolean activarAceptar;
    private boolean elInsumoEsNuevo;
    private boolean fechaDiferida;

    //
    private String nuevoNombre, nuevoCodigo, nuevoMarca, nuevoModelo;
    private String nuevoCantidadMin;
    private String nuevoDescripcion;
    //
    private boolean validacionesNombre, validacionesCodigo, validacionesMarca, validacionesModelo;
    private boolean validacionesCantidadMin;
    private boolean validacionesDescripcion;
    private boolean activarInsumo;
    private String paginaAnterior;
    private MensajesConstantes constantes;

    public ControllerRegistrarIngresoInsumo() {
    }

    @PostConstruct
    public void init() {
        constantes = new MensajesConstantes();
        fechaDiferida = true;
        activarInsumo = false;
        activarLimpiar = true;
        activarAceptar = false;
        colorMensaje = "black";
        activarCasillas = false;
        mensajeFormulario = "N/A";
        Date fecha = new Date();
        DateFormat df = DateFormat.getDateInstance();
        nuevoFecha = df.format(fecha);
        nuevoCantidad = "1";
        nuevoCosto = "0";
        nuevoInsumo = null;
        nuevoProveedor = null;
        validacionesFecha = true;
        validacionesCantidad = true;
        validacionesCosto = true;
        validacionesInsumo = false;
        validacionesProveedor = false;
        elInsumoEsNuevo = true;
        BasicConfigurator.configure();
        nuevoCantidadMin = "0";
        nuevoCodigo = null;
        nuevoDescripcion = null;
        nuevoMarca = null;
        nuevoModelo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
    }

    public void recibirPaginaAnterior(String page) {
        this.paginaAnterior = page;
    }

    public void actualizarInformacionInsumo() {
        nuevoInsumo = null;
        nuevoCantidadMin = "0";
        nuevoCodigo = null;
        nuevoDescripcion = null;
        nuevoMarca = null;
        nuevoModelo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        if (elInsumoEsNuevo == true) {
            activarInsumo = true;
        } else {
            activarInsumo = false;
        }
    }

    public void validarFechaIngresoInsumo() {
        if (Utilidades.validarNulo(nuevoFecha)) {
            if (!Utilidades.fechaIngresadaCorrecta(nuevoFecha)) {
                validacionesFecha = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoFecha", new FacesMessage("La fecha ingresada es incorrecta. Formato (dd/mm/yyyy)"));
            } else {
                validacionesFecha = true;
            }
        } else {
            validacionesFecha = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoFecha", new FacesMessage("La fecha es obligatoria. Formato (dd/mm/yyyy)"));
        }
    }

    public void validarInsumoIngresoInsumo() {
        if (Utilidades.validarNulo(nuevoInsumo)) {
            validacionesInsumo = true;
        } else {
            validacionesInsumo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoInsumo", new FacesMessage("El insumo es obligatorio."));
        }
    }

    public void validarProveedorIngresoInsumo() {
        if (Utilidades.validarNulo(nuevoProveedor)) {
            validacionesProveedor = true;
        } else {
            validacionesProveedor = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoProveedor", new FacesMessage("El proveedor es obligatorio."));
        }
    }

    public void validarCantidadIngresoInsumo() {
        if (Utilidades.validarNulo(nuevoCantidad) && (!nuevoCantidad.isEmpty()) && (nuevoCantidad.trim().length() > 0)) {
            int tam = nuevoCantidad.length();
            if (tam >= 1) {
                if (Utilidades.isNumber(nuevoCantidad) == true) {
                    validacionesCantidad = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCantidad", new FacesMessage("La cantidad ingresada es incorrecta. " + constantes.RECURSO_CANT_E));
                    validacionesCantidad = false;
                }
            } else {
                validacionesCantidad = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCantidad", new FacesMessage("La cantidad es obligatoria. " + constantes.RECURSO_CANT_E));
            }
        } else {
            validacionesCantidad = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCantidad", new FacesMessage("La cantidad es obligatoria. " + constantes.RECURSO_CANT_E));
        }
    }

    public void validarCostoIngresoInsumo() {
        if (Utilidades.validarNulo(nuevoCosto) && (!nuevoCosto.isEmpty()) && (nuevoCosto.trim().length() > 0)) {
            int tam = nuevoCosto.length();
            if (tam >= 4) {
                if (Utilidades.isNumber(nuevoCosto) == true) {
                    validacionesCosto = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCosto", new FacesMessage("El costo ingresado es incorrecto. " + constantes.RECURSO_COSTO));
                    validacionesCosto = false;
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("form:nuevoCosto", new FacesMessage("El tamaño minimo permitido es 4 caracteres. " + constantes.RECURSO_COSTO));
                validacionesCosto = false;
            }
        }
    }

    private boolean validarResultadosValidacion() {
        boolean retorno = true;
        if (validacionesCosto == false) {
            retorno = false;
        }
        if (validacionesFecha == false) {
            retorno = false;
        }
        if (validacionesCantidad == false) {
            retorno = false;
        }
        if (validacionesInsumo == false) {
            retorno = false;
        }
        if (validacionesProveedor == false) {
            retorno = false;
        }
        return retorno;
    }

    public void registrarNuevoIngresoInsumo() {
        if (validarResultadosValidacion() == true) {
            almacenarNuevoIngresoInsumoEnSistema();
            limpiarFormularioIngreso();
            activarLimpiar = false;
            activarAceptar = true;
            activarCasillas = true;
            colorMensaje = "green";
            mensajeFormulario = "El formulario ha sido ingresado con exito.";
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoIngresoInsumoEnSistema() {
        try {
            IngresoInsumo ingresoNuevo = new IngresoInsumo();
            ingresoNuevo.setFechaingreso(new Date(nuevoFecha));
            Integer cantidad = Integer.valueOf(nuevoCantidad);
            ingresoNuevo.setCantidadingreso(cantidad);
            if (Utilidades.validarNulo(nuevoCosto) && (!nuevoCosto.isEmpty()) && (nuevoCosto.trim().length() > 0)) {
                Integer costo = Integer.valueOf(nuevoCosto);
                ingresoNuevo.setCostoingreso(costo);
            } else {
                ingresoNuevo.setCostoingreso(0);
            }
            ingresoNuevo.setProveedor(nuevoProveedor);
            ingresoNuevo.setInsumo(nuevoInsumo);
            gestionarRecursoIngresoInsumoBO.crearIngresoInsumo(ingresoNuevo);
        } catch (Exception e) {
            logger.error("Error ControllerRegistrarIngresoInsumo almacenarNuevoIngresoInsumoEnSistema:  " + e.toString());
            logger.error("Error ControllerRegistrarIngresoInsumo almacenarNuevoIngresoInsumoEnSistema : " + e.toString());
        }
    }

    public void limpiarFormularioIngreso() {
        nuevoCosto = "0";
        activarInsumo = false;
        nuevoCantidad = "1";
        Date fecha = new Date();
        DateFormat df = DateFormat.getDateInstance();
        nuevoFecha = df.format(fecha);
        fechaDiferida = true;
        nuevoInsumo = null;
        nuevoProveedor = null;
        validacionesFecha = true;
        validacionesCantidad = true;
        validacionesCosto = true;
        validacionesInsumo = false;
        validacionesProveedor = false;
        mensajeFormulario = "";
        elInsumoEsNuevo = true;
    }

    public String cancelarRegistroIngresoInsumo() {
        nuevoCosto = "0";
        nuevoCantidad = "1";
        nuevoFecha = null;
        activarInsumo = false;
        validacionesFecha = true;
        fechaDiferida = true;
        nuevoInsumo = null;
        nuevoProveedor = null;
        validacionesCantidad = true;
        validacionesCosto = true;
        validacionesInsumo = false;
        validacionesProveedor = false;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
        activarAceptar = false;
        activarCasillas = false;
        elInsumoEsNuevo = true;
        cancelarRegistroInsumo();
        return paginaAnterior;
    }

    public void cambiarActivarCasillas() {
        mensajeFormulario = "N/A";
        colorMensaje = "black";
        activarLimpiar = true;
        activarAceptar = false;
        if (activarCasillas == true) {
            activarCasillas = false;
        }
    }

    public void validarNombreInsumo() {
        if (Utilidades.validarNulo(nuevoNombre) && (!nuevoNombre.isEmpty()) && (nuevoNombre.trim().length() > 0)) {
            int tam = nuevoNombre.length();
            if (tam >= 4) {
                if (!Utilidades.validarCaracteresAlfaNumericos(nuevoNombre)) {
                    validacionesNombre = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre ingresado es incorrecto."));
                } else {
                    validacionesNombre = true;
                }
            } else {
                validacionesNombre = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesNombre = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoNombre", new FacesMessage("El nombre es obligatorio."));
        }
    }

    public void validarCodigoInsumo() {
        if (Utilidades.validarNulo(nuevoCodigo) && (!nuevoCodigo.isEmpty()) && (nuevoCodigo.trim().length() > 0)) {
            int tam = nuevoCodigo.length();
            if (tam >= 4) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoCodigo)) {
                    validacionesCodigo = true;
                } else {
                    validacionesCodigo = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El Codigo ingresado es incorrecto."));
                }
            } else {
                validacionesCodigo = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El tamaño minimo permitido es 4 caracteres."));
            }
        } else {
            validacionesCodigo = false;
            FacesContext.getCurrentInstance().addMessage("form:nuevoCodigo", new FacesMessage("El Codigo es obligatorio."));
        }
    }

    public void validarCantidadesInsumo(int tipo) {
        if (tipo == 1) {
            //
        } else {
            if (Utilidades.validarNulo(nuevoCantidadMin) && (!nuevoCantidadMin.isEmpty()) && (nuevoCantidadMin.trim().length() > 0)) {
                if ((Utilidades.isNumber(nuevoCantidadMin)) == false) {
                    validacionesCantidadMin = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoCantidadMin", new FacesMessage("La cantidad ingresada se encuentra incorrecta."));
                } else {
                    validacionesCantidadMin = true;
                }
            }
        }
    }

    public void validarMarcaModeloInsumo(int tipo) {
        if (tipo == 1) {
            if ((Utilidades.validarNulo(nuevoMarca)) && (!nuevoMarca.isEmpty()) && (nuevoMarca.trim().length() > 0)) {
                int tam = nuevoMarca.length();
                if (tam >= 2) {
                    if (Utilidades.validarCaracteresAlfaNumericos(nuevoMarca)) {
                        validacionesMarca = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage("form:nuevoMarca", new FacesMessage("La marca se encuentra incorrecta."));
                        validacionesMarca = false;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoMarca", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
                    validacionesMarca = false;
                }
            }
        } else {
            if ((Utilidades.validarNulo(nuevoModelo)) && (!nuevoModelo.isEmpty()) && (nuevoModelo.trim().length() > 0)) {
                int tam = nuevoModelo.length();
                if (tam >= 2) {
                    if (Utilidades.validarCaracteresAlfaNumericos(nuevoModelo)) {
                        validacionesModelo = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage("form:nuevoModelo", new FacesMessage("El modelo se encuentra incorrecto."));
                        validacionesModelo = false;
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage("form:nuevoModelo", new FacesMessage("El tamaño minimo permitido es 2 caracteres."));
                    validacionesModelo = false;
                }
            }
        }
    }

    public void validarDescripcionInsumo(int tipoReg) {
        if (Utilidades.validarNulo(nuevoDescripcion) && (!nuevoDescripcion.isEmpty()) && (nuevoDescripcion.trim().length() > 0)) {
            int tam = nuevoDescripcion.length();
            if (tam >= 20) {
                if (Utilidades.validarCaracteresAlfaNumericos(nuevoDescripcion)) {
                    validacionesDescripcion = true;
                } else {
                    validacionesDescripcion = false;
                    FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("La descripción se encuentra incorrecta."));
                }
            } else {
                validacionesDescripcion = false;
                FacesContext.getCurrentInstance().addMessage("form:nuevoDescripcion", new FacesMessage("El tamaño minimo permitido es 20 caracteres."));
            }
        }
    }

    private boolean validarResultadosValidacionInsumo() {
        boolean retorno = true;
        if (validacionesCodigo == false) {
            retorno = false;
        }

        if (validacionesCantidadMin == false) {
            retorno = false;
        }
        if (validacionesDescripcion == false) {
            retorno = false;
        }
        if (validacionesMarca == false) {
            retorno = false;
        }
        if (validacionesModelo == false) {
            retorno = false;
        }
        if (validacionesNombre == false) {
            retorno = false;
        }

        return retorno;
    }

    public boolean validarCodigoRepetido() {
        boolean retorno = true;
        Insumo registro = gestionarRecursoInsumoBO.obtenerInsumoPorCodigo(nuevoCodigo);
        if (null != registro) {
            retorno = false;
        }
        return retorno;
    }

    public void limpiarFormularioInsumo() {
        nuevoCantidadMin = "0";
        nuevoCodigo = null;
        nuevoDescripcion = null;
        nuevoMarca = null;
        nuevoModelo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        validacionesNombre = false;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
    }

    public void cancelarRegistroInsumo() {
        nuevoCantidadMin = "0";
        nuevoCodigo = null;
        nuevoDescripcion = null;
        nuevoMarca = null;
        nuevoModelo = null;
        nuevoNombre = null;
        validacionesCodigo = false;
        activarAceptar = false;
        validacionesNombre = false;
        validacionesCantidadMin = true;
        validacionesDescripcion = true;
        validacionesMarca = true;
        validacionesModelo = true;
        mensajeFormulario = "N/A";
        activarLimpiar = true;
        colorMensaje = "black";
    }

    public void almacenarElementosEnBaseDatos() {
        if (elInsumoEsNuevo == true) {
            registrarNuevoIngresoInsumo();
        } else {
            registrarInsumoEIngresoEnSistema();
        }
    }

    private void registrarInsumoEIngresoEnSistema() {
        if (validarResultadosValidacion() == true) {
            if (validarResultadosValidacionInsumo() == true) {
                if (validarCodigoRepetido() == true) {
                    almacenarNuevoInsumoEIngresoEnSistema();
                    limpiarFormularioIngreso();
                    limpiarFormularioInsumo();
                    activarLimpiar = false;
                    activarAceptar = true;
                    activarCasillas = true;
                    colorMensaje = "green";
                    mensajeFormulario = "El formulario ha sido ingresado con exito.";
                } else {
                    colorMensaje = "red";
                    mensajeFormulario = "El codigo ingresado del insumo ya se encuentra registrado.";
                }
            } else {
                colorMensaje = "red";
                mensajeFormulario = "Existen errores en el formulario del insumo, por favor corregir para continuar.";
            }
        } else {
            colorMensaje = "red";
            mensajeFormulario = "Existen errores en el formulario del ingreso, por favor corregir para continuar.";
        }
    }

    private void almacenarNuevoInsumoEIngresoEnSistema() {
        IngresoInsumo ingresoNuevo = new IngresoInsumo();
        ingresoNuevo.setFechaingreso(new Date(nuevoFecha));
        Integer cantidad = Integer.valueOf(nuevoCantidad);
        ingresoNuevo.setCantidadingreso(cantidad);
        if (Utilidades.validarNulo(nuevoCosto) && (!nuevoCosto.isEmpty()) && (nuevoCosto.trim().length() > 0)) {
            Integer costo = Integer.valueOf(nuevoCosto);
            ingresoNuevo.setCostoingreso(costo);
        } else {
            ingresoNuevo.setCostoingreso(0);
        }
        ingresoNuevo.setProveedor(nuevoProveedor);
        //
        Insumo nuevaInsumo = new Insumo();
        nuevaInsumo.setCodigoinsumo(nuevoCodigo);
        nuevaInsumo.setNombreinsumo(nuevoNombre);
        nuevaInsumo.setModeloinsumo(nuevoModelo);
        nuevaInsumo.setMarcainsumo(nuevoMarca);
        nuevaInsumo.setCantidadexistencia(Integer.valueOf(nuevoCantidad));
        if (Utilidades.validarNulo(nuevoCantidadMin) && (!nuevoCantidadMin.isEmpty()) && (nuevoCantidadMin.trim().length() > 0)) {
            nuevaInsumo.setCantidadminimia(Integer.valueOf(nuevoCantidadMin));
        } else {
            nuevaInsumo.setCantidadminimia(Integer.valueOf("0"));
        }
        nuevaInsumo.setDescripcioninsumo(nuevoDescripcion);
        gestionarRecursoIngresoInsumoBO.crearInsumoInsumoConInsumo(ingresoNuevo, nuevaInsumo);
    }

    //GET-SET
    public String getNuevoFecha() {
        return nuevoFecha;
    }

    public void setNuevoFecha(String nuevoFecha) {
        this.nuevoFecha = nuevoFecha;
    }

    public String getNuevoCantidad() {
        return nuevoCantidad;
    }

    public void setNuevoCantidad(String nuevoCantidad) {
        this.nuevoCantidad = nuevoCantidad;
    }

    public String getNuevoCosto() {
        return nuevoCosto;
    }

    public void setNuevoCosto(String nuevoCosto) {
        this.nuevoCosto = nuevoCosto;
    }

    public List<Insumo> getListaInsumos() {
        return listaInsumos;
    }

    public void setListaInsumos(List<Insumo> listaInsumos) {
        if (null == listaInsumos) {
            listaInsumos = gestionarRecursoIngresoInsumoBO.consultarInsumosRegistrados();
        }
        this.listaInsumos = listaInsumos;
    }

    public Insumo getNuevoInsumo() {
        return nuevoInsumo;
    }

    public void setNuevoInsumo(Insumo nuevoInsumo) {
        this.nuevoInsumo = nuevoInsumo;
    }

    public List<Proveedor> getListaProveedores() {
        if (null == listaProveedores) {
            listaProveedores = gestionarRecursoIngresoInsumoBO.obtenerProveedoresRegistrados();
        }
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Proveedor getNuevoProveedor() {
        return nuevoProveedor;
    }

    public void setNuevoProveedor(Proveedor nuevoProveedor) {
        this.nuevoProveedor = nuevoProveedor;
    }

    public String getMensajeFormulario() {
        return mensajeFormulario;
    }

    public void setMensajeFormulario(String mensajeFormulario) {
        this.mensajeFormulario = mensajeFormulario;
    }

    public boolean isActivarCasillas() {
        return activarCasillas;
    }

    public void setActivarCasillas(boolean activarCasillas) {
        this.activarCasillas = activarCasillas;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public void setColorMensaje(String colorMensaje) {
        this.colorMensaje = colorMensaje;
    }

    public boolean isActivarLimpiar() {
        return activarLimpiar;
    }

    public void setActivarLimpiar(boolean activarLimpiar) {
        this.activarLimpiar = activarLimpiar;
    }

    public boolean isActivarAceptar() {
        return activarAceptar;
    }

    public void setActivarAceptar(boolean activarAceptar) {
        this.activarAceptar = activarAceptar;
    }

    public boolean isElInsumoEsNuevo() {
        return elInsumoEsNuevo;
    }

    public void setElInsumoEsNuevo(boolean elInsumoEsNuevo) {
        this.elInsumoEsNuevo = elInsumoEsNuevo;
    }

    public String getNuevoNombre() {
        return nuevoNombre;
    }

    public void setNuevoNombre(String nuevoNombre) {
        this.nuevoNombre = nuevoNombre;
    }

    public String getNuevoCodigo() {
        return nuevoCodigo;
    }

    public void setNuevoCodigo(String nuevoCodigo) {
        this.nuevoCodigo = nuevoCodigo;
    }

    public String getNuevoMarca() {
        return nuevoMarca;
    }

    public void setNuevoMarca(String nuevoMarca) {
        this.nuevoMarca = nuevoMarca;
    }

    public String getNuevoModelo() {
        return nuevoModelo;
    }

    public void setNuevoModelo(String nuevoModelo) {
        this.nuevoModelo = nuevoModelo;
    }

    public String getNuevoCantidadMin() {
        return nuevoCantidadMin;
    }

    public void setNuevoCantidadMin(String nuevoCantidadMin) {
        this.nuevoCantidadMin = nuevoCantidadMin;
    }

    public String getNuevoDescripcion() {
        return nuevoDescripcion;
    }

    public void setNuevoDescripcion(String nuevoDescripcion) {
        this.nuevoDescripcion = nuevoDescripcion;
    }

    public boolean isActivarInsumo() {
        return activarInsumo;
    }

    public void setActivarInsumo(boolean activarInsumo) {
        this.activarInsumo = activarInsumo;
    }

    public boolean isFechaDiferida() {
        return fechaDiferida;
    }

    public void setFechaDiferida(boolean fechaDiferida) {
        this.fechaDiferida = fechaDiferida;
    }

}
