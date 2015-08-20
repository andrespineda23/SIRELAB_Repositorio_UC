/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.reservas;

import com.sirelab.bo.interfacebo.reservas.AdministrarReservasBOInterface;
import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.dao.interfacedao.EstudianteDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.ReservaDAOInterface;
import com.sirelab.dao.interfacedao.ReservaSalaDAOInterface;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.ReservaSala;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarReservasBO implements AdministrarReservasBOInterface {

    @EJB
    ReservaDAOInterface reservaDAO;
    @EJB
    ReservaSalaDAOInterface reservaSalaDAO;
    @EJB
    DocenteDAOInterface docenteDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    EstudianteDAOInterface estudianteDAO;
    @EJB
    EntidadExternaDAOInterface entidadExternaDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;

    @Override
    public List<ReservaSala> consultarReservasSalaPorPersona(BigInteger persona) {
        try {
            List<ReservaSala> lista = reservaSalaDAO.buscarReservaSalasSalaPorPersona(persona);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarReservasSalaPorPersona: " + e.toString());
            return null;
        }
    }

    //@Override
    public Docente consultarDocentePorID(BigInteger idRegistro) {
        try {
            Docente registro = docenteDAO.buscarDocentePorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO consultarDocentePorID: " + e.toString());
            return null;
        }
    }

    @Override
    public Persona obtenerPersonaConsultarReservas(String tipoUsuario, BigInteger idPersona) {
        // idTipoUsuario : 2- Docente / 3- Estudiante / 4-EncargadoLab / 5-EntidadExterna
        try {
            Persona registro = null;
            if ("ESTUDIANTE".equalsIgnoreCase(tipoUsuario)) {
                Estudiante estudiante = estudianteDAO.buscarEstudiantePorID(idPersona);
                registro = estudiante.getPersona();
            } else {
                if ("DOCENTE".equalsIgnoreCase(tipoUsuario)) {
                    Docente docente = docenteDAO.buscarDocentePorID(idPersona);
                    registro = docente.getPersona();
                } else {
                    EntidadExterna entidad = entidadExternaDAO.buscarEntidadExternaPorID(idPersona);
                    registro = entidad.getPersona();
                }
            }
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarReservasBO obtenerPersonaConsultarReservas : " + e.toString());
            return null;
        }
    }
}
