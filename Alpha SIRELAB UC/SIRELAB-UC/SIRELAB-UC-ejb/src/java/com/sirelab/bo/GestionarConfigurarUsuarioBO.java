/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo;

import com.sirelab.bo.interfacebo.GestionarConfigurarUsuarioBOInterface;
import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EstudianteDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarConfigurarUsuarioBO implements GestionarConfigurarUsuarioBOInterface {
    
    static Logger logger = Logger.getLogger(GestionarConfigurarUsuarioBO.class);

    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    EstudianteDAOInterface estudianteDAO;
    @EJB
    DocenteDAOInterface docenteDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    

    //@Override
    public Persona obtenerPersonaPorUsuarioModificar(BigInteger idUsuario, String tipoUsuario) {
        try {
            BigInteger secuenciaPersona = null;
            if ("ESTUDIANTE".equalsIgnoreCase(tipoUsuario)) {
                Estudiante estudiante = estudianteDAO.buscarEstudiantePorID(idUsuario);
                if (null != estudiante) {
                    secuenciaPersona = estudiante.getPersona().getIdpersona();
                }
            } else {
                if ("DOCENTE".equalsIgnoreCase(tipoUsuario)) {
                    Docente docente = docenteDAO.buscarDocentePorID(idUsuario);
                    if (null != docente) {
                        secuenciaPersona = docente.getPersona().getIdpersona();
                    }
                } else {
                    if ("ADMINISTRADOR".equalsIgnoreCase(tipoUsuario)) {
                        Persona persona = personaDAO.buscarPersonaPorID(idUsuario);
                        return persona;
                    } else {
                        EncargadoLaboratorio encargado = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idUsuario);
                        if (null != encargado) {
                            secuenciaPersona = encargado.getPersona().getIdpersona();
                        }
                    }
                }
            }
            Persona persona = personaDAO.buscarPersonaPorID(secuenciaPersona);
            return persona;
        } catch (Exception e) {
            logger.error("Error GestionarConfigurarUsuarioBO obtenerPersonaPorUsuarioModificar: " + e.toString());
            return null;
        }
    }

    @Override
    public void actualizarInformacionPersona(Persona persona) {
        try {
            personaDAO.editarPersona(persona);
        } catch (Exception e) {
            logger.error("Error GestionarConfigurarUsuarioBO actualizarInformacionPersona: " + e.toString());
        }
    }

    @Override
    public void actualizarContraseniaPersona(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            logger.error("Error GestionarConfigurarUsuarioBO actualizarContraseniaPersona: " + e.toString());
        }
    }

    @Override
    public Persona obtenerPersonaPorEmail(String correo) {
        try {
            Persona persona = personaDAO.buscarPersonaPorCorreo(correo);
            return persona;
        } catch (Exception e) {
            logger.error("Error GestionarConfigurarUsuarioBO obtenerPersonaPorEmail: " + e.toString());
            return null;
        }
    }

    @Override
    public Persona obtenerPersonaPorDocumento(String documento) {
        try {
            Persona persona = personaDAO.buscarPersonaPorDocumento(documento);
            return persona;
        } catch (Exception e) {
            logger.error("Error GestionarConfigurarUsuarioBO obtenerPersonaPorDocumento: " + e.toString());
            return null;
        }
    }
}
