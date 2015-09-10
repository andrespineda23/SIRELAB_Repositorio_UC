/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.AdministradorEdificio;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministradorEdificioDAOInterface {

    public void crearAdministradorEdificio(AdministradorEdificio administradoredificio);

    public void editarAdministradorEdificio(AdministradorEdificio administradoredificio);

    public void eliminarAdministradorEdificio(AdministradorEdificio administradoredificio);

    public List<AdministradorEdificio> consultarAdministradoresEdificio();

    public AdministradorEdificio buscarAdministradorEdificioPorID(BigInteger idRegistro);

    public AdministradorEdificio buscarAdministradorEdificioPorIDPersona(BigInteger idPersona);

    public AdministradorEdificio buscarAdministradorEdificioPorPorCorreoNumDocumento(String correo, String documento);

    public AdministradorEdificio buscarAdministradorEdificioPorPorCorreo(String correo);

    public AdministradorEdificio buscarAdministradorEdificioPorPorDocumento(String documento);

    public AdministradorEdificio obtenerUltimaAdministradorEdificioRegistrada();
}
