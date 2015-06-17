package com.sirelab.bo;

import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.dao.interfacedao.EstudianteDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateless
public class GestionarLoginSistemaBO implements GestionarLoginSistemaBOInterface {

    @EJB
    CarreraDAOInterface carreraDAO;
    @EJB
    PlanEstudiosDAOInterface planEstudiosDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    EstudianteDAOInterface estudianteDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    DocenteDAOInterface docenteDAO;
    @EJB
    EntidadExternaDAOInterface entidadExternaDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;

    @Override
    public List<Departamento> obtenerListasDepartamentos() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarLoginSistemaBO obtenerListasDepartamentos : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Carrera> obtenerListasCarreras() {
        try {
            List<Carrera> lista = carreraDAO.consultarCarreras();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarLoginSistemaBO obtenerListasCarreras : " + e.toString());
            return null;
        }
    }

    @Override
    public List<PlanEstudios> obtenerListasPlanesEstudioPorCarrera(BigInteger idCarrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosPorCarrera(idCarrera);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarLoginSistemaBO obtenerListasPlanesEstudioPorCarrera : " + e.toString());
            return null;
        }
    }

    @Override
    public Estudiante obtenerEstudiantePorCorreo(String correo) {
        try {
            Estudiante registro = estudianteDAO.buscarEstudiantePorCorreo(correo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarLoginSistemaBO obtenerEstudiantePorCorreo : " + e.toString());
            return null;
        }
    }

    @Override
    public Estudiante obtenerEstudiantePorDocumento(String documento) {
        try {
            Estudiante registro = estudianteDAO.buscarEstudiantePorDocumento(documento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarLoginSistemaBO obtenerEstudiantePorDocumento : " + e.toString());
            return null;
        }
    }

    @Override
    public void almacenarNuevoEstudianteEnSistema(Usuario usuarioNuevo, Persona personaNuevo, Estudiante estudianteNuevo) {
        try {
            int sec = 1;
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorNombre("ESTUDIANTE");
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
            Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
            estudianteNuevo.setPersona(personaRegistrada);
            estudianteDAO.crearEstudiante(estudianteNuevo);
        } catch (Exception e) {
            System.out.println("Error GestionarLoginSistemaBO almacenarNuevoEstudianteEnSistema : " + e.toString());
        }
    }

    @Override
    public Persona obtenerPersonaRecuperarContrasenia(String correo, String identificacion) {
        try {
            Persona registro = personaDAO.buscarPersonaPorCorreoYNumeroIdentificacion(correo, identificacion);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarLoginSistemaBO obtenerPersonaRecuperarContrasenia : " + e.toString());
            return null;
        }
    }

    @Override
    public Persona configurarContraseñaPersona(Persona persona) {
        try {
            boolean bandera = true;
            int nuevaPass = 0;
            while (bandera) {
                nuevaPass = (int) (Math.random() * 9999999) + 1;
                if (nuevaPass >= 100000) {
                    bandera = false;
                }
            }
            String newPass = String.valueOf(nuevaPass) + "SIRELAB";
            persona.getUsuario().setPasswordusuario(newPass);
            usuarioDAO.editarUsuario(persona.getUsuario());
            Persona registro = personaDAO.buscarPersonaPorID(persona.getIdpersona());
            return registro;
        } catch (Exception e) {
            System.out.println("Error configurarContraseñaPersona GestionarLoginSistemaBO : " + e.toString());
            return null;
        }
    }

    @Override
    public Persona obtenerPersonaLogin(String usuario, String password) {
        try {
            Persona registro = personaDAO.obtenerPersonaLoginUserPassword(usuario, password);
            return registro;
        } catch (Exception e) {
            System.out.println("Error obtenerPersonaLogin GestionarLoginSistemaBO : " + e.toString());
            return null;
        }
    }

    //@Override
    public Object obtenerUsuarioFinalLogin(BigInteger idTipoUsuario, BigInteger idPersona) {
        // idTipoUsuario : 2- Docente / 3- Estudiante / 4-EncargadoLab / 5-EntidadExterna
        try {
            Object registro = null;
            BigInteger secuencia = new BigInteger("3");
            if (secuencia.equals(idTipoUsuario)) {
                registro = estudianteDAO.buscarEstudiantePorIDPersona(idPersona);
            } else {
                secuencia = new BigInteger("2");
                if (secuencia.equals(idTipoUsuario)) {
                    registro = docenteDAO.buscarDocentePorIDPersona(idPersona);
                } else {
                    secuencia = new BigInteger("4");
                    if (secuencia.equals(idTipoUsuario)) {
                        registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorIDPersona(idPersona);
                    } else {
                        registro = entidadExternaDAO.buscarEntidadExternaPorIDPersona(idPersona);
                    }
                }
            }
            return registro;
        } catch (Exception e) {
            System.out.println("Error obtenerUsuarioFinalLogin GestionarLoginSistemaBO : " + e.toString());
            return null;
        }
    }
}
