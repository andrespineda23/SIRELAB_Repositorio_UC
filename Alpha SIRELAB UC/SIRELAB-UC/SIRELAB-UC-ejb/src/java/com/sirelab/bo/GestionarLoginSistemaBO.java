package com.sirelab.bo;

import com.sirelab.bo.interfacebo.GestionarLoginSistemaBOInterface;
import com.sirelab.dao.interfacedao.AdministradorEdificioDAOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.DocenteDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.EstudianteDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.AdministradorEdificio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Docente;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateless
public class GestionarLoginSistemaBO implements GestionarLoginSistemaBOInterface {

    static Logger logger = Logger.getLogger(GestionarLoginSistemaBO.class);

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
    AdministradorEdificioDAOInterface administradorEdificioDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    DocenteDAOInterface docenteDAO;
    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;

    private final String NUMEROS = "0123456789";
    private final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public void cerrarSesionEnLineaUsuario(int tipoUsuario, BigInteger secuencia) {
        try {
            if (tipoUsuario == 1) {
                Persona registro = personaDAO.buscarPersonaPorID(secuencia);
                actualizarUsuario(registro.getUsuario());
            } else {
                if (tipoUsuario == 2) {
                    Docente registro = docenteDAO.buscarDocentePorID(secuencia);
                    actualizarUsuario(registro.getPersona().getUsuario());
                } else {
                    if (tipoUsuario == 3) {
                        Estudiante registro = estudianteDAO.buscarEstudiantePorID(secuencia);
                        actualizarUsuario(registro.getPersona().getUsuario());
                    } else {
                        EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(secuencia);
                        actualizarUsuario(registro.getPersona().getUsuario());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO cerrarSesionEnLineaUsuario : " + e.toString(), e);
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO actualizarUsuario : " + e.toString(), e);
        }
    }

    @Override
    public List<Departamento> obtenerListasDepartamentos() {
        try {
            List<Departamento> lista = departamentoDAO.consultarDepartamentos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO obtenerListasDepartamentos : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Carrera> obtenerListasCarreras() {
        try {
            List<Carrera> lista = carreraDAO.consultarCarreras();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO obtenerListasCarreras : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<Carrera> obtenerListasCarrerasActivos() {
        try {
            List<Carrera> lista = carreraDAO.consultarCarrerasActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO obtenerListasCarreras : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<PlanEstudios> obtenerListasPlanesEstudioPorCarrera(BigInteger idCarrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosPorCarrera(idCarrera);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO obtenerListasPlanesEstudioPorCarrera : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public List<PlanEstudios> obtenerListasPlanesEstudioActivosPorCarrera(BigInteger idCarrera) {
        try {
            List<PlanEstudios> lista = planEstudiosDAO.consultarPlanesEstudiosActivosPorCarrera(idCarrera);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO obtenerListasPlanesEstudioPorCarrera : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Estudiante obtenerEstudiantePorCorreo(String correo) {
        try {
            Estudiante registro = estudianteDAO.buscarEstudiantePorCorreo(correo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO obtenerEstudiantePorCorreo : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Estudiante obtenerEstudiantePorDocumento(String documento) {
        try {
            Estudiante registro = estudianteDAO.buscarEstudiantePorDocumento(documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO obtenerEstudiantePorDocumento : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public void almacenarNuevoEstudianteEnSistema(Usuario usuarioNuevo, Persona personaNuevo, Estudiante estudianteNuevo) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorID(new BigInteger("3"));
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
            Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
            estudianteNuevo.setPersona(personaRegistrada);
            estudianteDAO.crearEstudiante(estudianteNuevo);
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO almacenarNuevoEstudianteEnSistema : " + e.toString(), e);
        }
    }

    @Override
    public Persona obtenerPersonaRecuperarContrasenia(String correo, String identificacion) {
        try {
            Persona registro = personaDAO.buscarPersonaPorCorreoYNumeroIdentificacion(correo, identificacion);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarLoginSistemaBO obtenerPersonaRecuperarContrasenia : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public String generarNuevaContrasenia() {
        String key = MAYUSCULAS + MINUSCULAS + NUMEROS;
        String pswd = "";
        for (int i = 0; i < 12; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }
        return pswd;
    }

    @Override
    public Persona configurarContraseñaPersona(Persona persona) {
        try {
            usuarioDAO.editarUsuario(persona.getUsuario());
            Persona registro = personaDAO.buscarPersonaPorID(persona.getIdpersona());
            return registro;
        } catch (Exception e) {
            logger.error("Error configurarContraseñaPersona GestionarLoginSistemaBO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Persona obtenerPersonaLogin(String usuario, String password) {
        try {
            EncriptarContrasenia obj = new EncriptarContrasenia();
            Persona registro = personaDAO.obtenerPersonaLoginUserPassword(usuario, obj.encriptarContrasenia(password));
            return registro;
        } catch (Exception e) {
            logger.error("Error obtenerPersonaLogin GestionarLoginSistemaBO : " + e.toString(), e);
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
                        registro = administradorEdificioDAO.buscarAdministradorEdificioPorIDPersona(idPersona);
                    }
                }
            }
            return registro;
        } catch (Exception e) {
            logger.error("Error obtenerUsuarioFinalLogin GestionarLoginSistemaBO : " + e.toString(), e);
            return null;
        }
    }

    @Override
    public Usuario obtenerUsuarioCambioContrasenia(String usuario, BigInteger identificacion) {
        try {
            Usuario user = null;
            if ("ADMINISTRADOR".equalsIgnoreCase(usuario)) {
                Persona admi = personaDAO.buscarPersonaPorID(identificacion);
                user = admi.getUsuario();
            } else if ("ESTUDIANTE".equalsIgnoreCase(usuario)) {
                Estudiante estu = estudianteDAO.buscarEstudiantePorID(identificacion);
                user = estu.getPersona().getUsuario();
            } else if ("DOCENTE".equalsIgnoreCase(usuario)) {
                Docente doce = docenteDAO.buscarDocentePorID(identificacion);
                user = doce.getPersona().getUsuario();
            } else if ("ADMINISTRADOREDIFICIO".equalsIgnoreCase(usuario)) {
                AdministradorEdificio admini = administradorEdificioDAO.buscarAdministradorEdificioPorID(identificacion);
                user = admini.getPersona().getUsuario();
            } else if ("ENCARGADOLAB".equalsIgnoreCase(usuario)) {
                EncargadoLaboratorio enc = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(identificacion);
                user = enc.getPersona().getUsuario();
            }
            return user;
        } catch (Exception e) {
            logger.error("Error obtenerUsuarioFinalLogin GestionarLoginSistemaBO : " + e.toString(), e);
            return null;
        }
    }

    public Persona obtenerPersonaSistemaPorCorreo(String correo) {
        try {
            return personaDAO.buscarPersonaPorCorreo(correo);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerPersonaSistemaPorCorreo : " + e.toString(), e);
            return null;
        }
    }

    public Persona obtenerPersonaSistemaPorIdentificacion(String identificacion) {
        try {
            return personaDAO.buscarPersonaPorDocumento(identificacion);
        } catch (Exception e) {
            logger.error("Error AdministrarEncargadosLaboratoriosBO obtenerPersonaSistemaPorIdentificacion : " + e.toString(), e);
            return null;
        }
    }
}
