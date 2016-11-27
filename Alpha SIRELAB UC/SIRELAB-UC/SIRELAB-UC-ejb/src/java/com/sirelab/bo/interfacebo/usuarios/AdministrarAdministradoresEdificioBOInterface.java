/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.usuarios;

import com.sirelab.entidades.AdministradorEdificio;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoPorEdificio;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Sede;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministrarAdministradoresEdificioBOInterface {

    public List<EncargadoPorEdificio> consultarEncargadosPorEdificioPorParametro(Map<String, String> filtros);

    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorIDAdministrador(BigInteger idAdministrador);

    public List<Sede> obtenerListaSedes();

    public List<Sede> obtenerListaSedesActivos();

    public List<Edificio> obtenerEdificiosPorIDSede(BigInteger idSede);

    public List<Edificio> obtenerEdificiosActivosPorIDSede(BigInteger idSede);

    public AdministradorEdificio obtenerAdministradorEdificioPorCorreoNumDocumento(String correo, String documento);

    public AdministradorEdificio obtenerAdministradorEdificioPorCorreo(String correo);

    public AdministradorEdificio obtenerAdministradorEdificioPorDocumento(String documento);

    public void actualizarInformacionAdministradorEdificio(AdministradorEdificio administradorEdificio);

    public void actualizarInformacionPersona(Persona persona);

    public void actualizarInformacionUsuario(Usuario usuario);

    public void almacenarNuevoAdministradorEdificioEnSistema(Usuario usuarioNuevo, Persona personaNuevo, AdministradorEdificio personalNuevo, Edificio edificio);

    public void registrarAsocioEncargadoEdificio(AdministradorEdificio administradorEdificio, Edificio edificio);

    public void editarAsocioEncargadoEdificio(EncargadoPorEdificio encargadoPorEdificio);

    public EncargadoPorEdificio obtenerEncargadoPorEdificioPorIDEncargadoPorEdificio(BigInteger idEncargadoPorEdificio);

    public Persona obtenerPersonaSistemaPorCorreo(String correo);

    public Persona obtenerPersonaSistemaPorIdentificacion(String identificacion);
}
