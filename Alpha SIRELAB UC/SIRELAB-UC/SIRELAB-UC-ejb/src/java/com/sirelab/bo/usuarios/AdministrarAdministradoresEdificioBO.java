/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarAdministradoresEdificioBOInterface;
import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.dao.interfacedao.AdministradorEdificioDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoPorEdificioDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.AdministradorEdificio;
import com.sirelab.entidades.EncargadoPorEdificio;
import com.sirelab.entidades.Sede;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarAdministradoresEdificioBO implements AdministrarAdministradoresEdificioBOInterface {

    @EJB
    AdministradorEdificioDAOInterface administradorEdificioDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    SedeDAOInterface sedeDAO;
    @EJB
    EdificioDAOInterface edificioDAO;
    @EJB
    EncargadoPorEdificioDAOInterface encargadoPorEdificioDAOInterface;

    @Override
    public List<EncargadoPorEdificio> consultarEncargadosPorEdificioPorParametro(Map<String, String> filtros) {
        try {
            List<EncargadoPorEdificio> lista = encargadoPorEdificioDAOInterface.buscarEncargadosPorEdificioPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO consultarEncargadosPorEdificioPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public EncargadoPorEdificio obtenerEncargadoPorEdificioPorIDEncargadoPorEdificio(BigInteger idEncargadoPorEdificio) {
        try {
            EncargadoPorEdificio registro = encargadoPorEdificioDAOInterface.buscarEncargadoPorEdificioPorID(idEncargadoPorEdificio);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoPorEdificioPorIDEncargadoPorEdificio : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Sede> obtenerListaSedes() {
        try {
            List<Sede> lista = sedeDAO.consultarSedes();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerListaSedes : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Sede> obtenerListaSedesActivos() {
        try {
            List<Sede> lista = sedeDAO.consultarSedesActivos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerListaSedesActivos : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Edificio> obtenerEdificiosPorIDSede(BigInteger idSede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosPorIDSede(idSede);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerEdificiosPorIDSede : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Edificio> obtenerEdificiosActivosPorIDSede(BigInteger idSede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosActivosPorIDSede(idSede);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerEdificiosPorIDSede : " + e.toString());
            return null;
        }
    }

    @Override
    public AdministradorEdificio obtenerAdministradorEdificioPorCorreoNumDocumento(String correo, String documento) {
        try {
            AdministradorEdificio registro = administradorEdificioDAO.buscarAdministradorEdificioPorPorCorreoNumDocumento(correo, documento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerAdministradorEdificioPorCorreoNumDocumento : " + e.toString());
            return null;
        }
    }

    @Override
    public AdministradorEdificio obtenerAdministradorEdificioPorCorreo(String correo) {
        try {
            AdministradorEdificio registro = administradorEdificioDAO.buscarAdministradorEdificioPorPorCorreo(correo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerAdministradorEdificioPorCorreo : " + e.toString());
            return null;
        }
    }

    @Override
    public AdministradorEdificio obtenerAdministradorEdificioPorDocumento(String documento) {
        try {
            AdministradorEdificio registro = administradorEdificioDAO.buscarAdministradorEdificioPorPorDocumento(documento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerAdministradorEdificioPorDocumento : " + e.toString());
            return null;
        }
    }

    @Override
    public void actualizarInformacionAdministradorEdificio(AdministradorEdificio administradorEdificio) {
        try {
            administradorEdificioDAO.editarAdministradorEdificio(administradorEdificio);
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO actualizarInformacionAdministradorEdificio : " + e.toString());
        }
    }

    //@Override
    public void actualizarInformacionPersona(Persona persona) {
        try {
            personaDAO.editarPersona(persona);
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO actualizarInformacionPersona : " + e.toString());
        }
    }

    //@Override
    public void actualizarInformacionUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO actualizarInformacionUsuario : " + e.toString());

        }
    }

    @Override
    public void almacenarNuevoAdministradorEdificioEnSistema(Usuario usuarioNuevo, Persona personaNuevo, AdministradorEdificio personalNuevo, Edificio edificio) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorNombre("ADMINISTRADOREDIFICIO");
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            //
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
            //
            Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
            personalNuevo.setPersona(personaRegistrada);
            administradorEdificioDAO.crearAdministradorEdificio(personalNuevo);
            //
            AdministradorEdificio ultimoAdministradorEdificio = administradorEdificioDAO.obtenerUltimaAdministradorEdificioRegistrada();
            System.out.println("ultimoAdministradorEdificio : "+ultimoAdministradorEdificio);
            EncargadoPorEdificio encargadoPorEdificio = new EncargadoPorEdificio();
            encargadoPorEdificio.setEstado(true);
            encargadoPorEdificio.setEdificio(edificio);
            encargadoPorEdificio.setAdministradoredificio(ultimoAdministradorEdificio);
            encargadoPorEdificioDAOInterface.crearEncargadoPorEdificio(encargadoPorEdificio);
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO almacenarNuevoAdministradorEdificioEnSistema : " + e.toString());
        }
    }

    @Override
    public void registrarAsocioEncargadoEdificio(AdministradorEdificio administradorEdificio, Edificio edificio) {
        try {
            EncargadoPorEdificio encargadoPorEdificio = new EncargadoPorEdificio();
            encargadoPorEdificio.setEstado(true);
            encargadoPorEdificio.setEdificio(edificio);
            encargadoPorEdificio.setAdministradoredificio(administradorEdificio);
            encargadoPorEdificioDAOInterface.crearEncargadoPorEdificio(encargadoPorEdificio);
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO registrarAsocioEncargadoEdificio : " + e.toString());
        }
    }

    @Override
    public void editarAsocioEncargadoEdificio(EncargadoPorEdificio encargadoPorEdificio) {
        try {
            encargadoPorEdificioDAOInterface.editarEncargadoPorEdificio(encargadoPorEdificio);
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO registrarAsocioEncargadoEdificio : " + e.toString());
        }
    }

    @Override
    public List<EncargadoPorEdificio> buscarEncargadosPorEdificioPorIDAdministrador(BigInteger idAdministrador) {
        try {
            List<EncargadoPorEdificio> lista = encargadoPorEdificioDAOInterface.buscarEncargadosPorEdificioPorIDAdministrador(idAdministrador);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO buscarEncargadosPorEdificioPorIDAdministrador : " + e.toString());
            return null;
        }
    }

}
