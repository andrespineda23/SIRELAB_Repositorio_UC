package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEncargadosLaboratoriosBOInterface;
import com.sirelab.dao.interfacedao.AreaProfundizacionDAOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.EncargadoLaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.dao.interfacedao.LaboratorioDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.TipoPerfilDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateless
public class AdministrarEncargadosLaboratoriosBO implements AdministrarEncargadosLaboratoriosBOInterface {

    @EJB
    EncargadoLaboratorioDAOInterface encargadoLaboratorioDAO;
    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    LaboratorioDAOInterface laboratorioDAO;
    @EJB
    TipoPerfilDAOInterface TipoPerfilDAO;
    @EJB
    AreaProfundizacionDAOInterface areaProfundizacionDAO;

    @Override
    public List<TipoPerfil> consultarPerfilesPorEncargadoRegistrados() {
        try {
            List<TipoPerfil> lista = TipoPerfilDAO.consultarTiposPerfiles();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO consultarPerfilesPorEncargadoRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public List<EncargadoLaboratorio> consultarEncargadoLaboratoriosPorParametro(Map<String, String> filtros) {
        try {
            List<EncargadoLaboratorio> lista = encargadoLaboratorioDAO.buscarEncargadosLaboratoriosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO consultarEncargadoLaboratoriosPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorIDEncargadoLaboratorio(BigInteger idEncargadoLaboratorio) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idEncargadoLaboratorio);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoLaboratorioPorIDEncargadoLaboratorio : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<Facultad> obtenerListaFacultades() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultades();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerListaFacultades : " + e.toString());
            return null;
        }
    }
    //@Override
    public List<Facultad> obtenerListaFacultadesActivos() {
        try {
            List<Facultad> lista = facultadDAO.consultarFacultadesActivas();
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerListaFacultadesActivos : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Departamento> obtenerDepartamentosPorIDFacultad(BigInteger idFacultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(idFacultad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }
    @Override
    public List<Departamento> obtenerDepartamentosActivosPorIDFacultad(BigInteger idFacultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosActivosPorIDFacultad(idFacultad);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerDepartamentosPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Laboratorio> obtenerLaboratoriosPorIDDepartamento(BigInteger idDepartamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioPorIDDepartamento(idDepartamento);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerLaboratoriosPorIDDepartamento : " + e.toString());
            return null;
        }
    }
    @Override
    public List<Laboratorio> obtenerLaboratoriosActivosPorIDDepartamento(BigInteger idDepartamento) {
        try {
            List<Laboratorio> lista = laboratorioDAO.buscarLaboratorioActivosPorIDDepartamento(idDepartamento);
            return lista;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerLaboratoriosActivosPorIDDepartamento : " + e.toString());
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorCorreoNumDocumento(String correo, String documento) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorPorCorreoNumDocumento(correo, documento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoLaboratorioPorCorreoNumDocumento : " + e.toString());
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorCorreo(String correo) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorPorCorreo(correo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoLaboratorioPorCorreo : " + e.toString());
            return null;
        }
    }

    @Override
    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorDocumento(String documento) {
        try {
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorPorDocumento(documento);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO obtenerEncargadoLaboratorioPorDocumento : " + e.toString());
            return null;
        }
    }

    @Override
    public void actualizarInformacionEncargadoLaboratorio(EncargadoLaboratorio personalLab) {
        try {
            encargadoLaboratorioDAO.editarEncargadoLaboratorio(personalLab);
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO actualizarInformacionEncargadoLaboratorio : " + e.toString());
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
    public void almacenarNuevoEncargadoLaboratorioEnSistema(Usuario usuarioNuevo, Persona personaNuevo, EncargadoLaboratorio personalNuevo) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorNombre("ENCARGADOLAB");
            usuarioNuevo.setTipousuario(tipoUsuario);
            usuarioDAO.crearUsuario(usuarioNuevo);
            Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
            personaNuevo.setUsuario(usuarioRegistrado);
            personaDAO.crearPersona(personaNuevo);
            Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
            personalNuevo.setPersona(personaRegistrada);
            encargadoLaboratorioDAO.crearEncargadoLaboratorio(personalNuevo);
        } catch (Exception e) {
            System.out.println("Error AdministrarEncargadosLaboratoriosBO almacenarNuevoEncargadoLaboratorioEnSistema : " + e.toString());
        }
    }

    @Override
    public TipoPerfil buscarTipoPerfilPorIDEncargado(BigInteger idRegistro) {
        try {
            System.out.println("Ingreso al metodo");
            EncargadoLaboratorio registro = encargadoLaboratorioDAO.buscarEncargadoLaboratorioPorID(idRegistro);
            if (null != registro) {
                System.out.println("Ok");
                return registro.getTipoperfil();
            } else {
                System.out.println("Null");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error AdministrarValidadorTipoUsuario buscarTipoPerfilPorIDEncargado: " + e.toString());
            return null;
        }
    }

    @Override
    public Departamento obtenerDepartamentoPorCodigo(String codigo) {
        try {
            Departamento registro = departamentoDAO.buscarDepartamentoPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarValidadorTipoUsuario obtenerDepartamentoPorCodigo: " + e.toString());
            return null;
        }
    }

    @Override
    public AreaProfundizacion obtenerAreaProfundizacionPorCodigo(String codigo) {
        try {
            AreaProfundizacion registro = areaProfundizacionDAO.buscarAreaProfundizacionPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarValidadorTipoUsuario obtenerAreaProfundizacionPorCodigo: " + e.toString());
            return null;
        }
    }

    @Override
    public Laboratorio obtenerLaboratorioPorCodigo(String codigo) {
        try {
            Laboratorio registro = laboratorioDAO.buscarLaboratorioPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            System.out.println("Error AdministrarValidadorTipoUsuario obtenerLaboratorioPorCodigo: " + e.toString());
            return null;
        }
    }
}
