package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarEntidadesExternasBOInterface;
import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.dao.interfacedao.PersonaContactoDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.SectorEntidadDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PersonaContacto;
import com.sirelab.entidades.SectorEntidad;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateful
public class AdministrarEntidadesExternasBO implements AdministrarEntidadesExternasBOInterface {

    static Logger logger = Logger.getLogger(AdministrarEntidadesExternasBO.class);
    
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    EntidadExternaDAOInterface entidadExternaDAO;
    @EJB
    ConvenioPorEntidadDAOInterface convenioPorEntidadDAO;
    @EJB
    SectorEntidadDAOInterface sectorEntidadDAO;
    @EJB
    PersonaContactoDAOInterface personaContactoDAO;

    @Override
    public List<EntidadExterna> consultarEntidadesExternasPorParametro(Map<String, String> filtros) {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.buscarEntidadesExternasPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO consultarEntidadesExternasPorParametro : " + e.toString());
            return null;
        }

    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorIDEntidadExterna(BigInteger idEntidadExterna) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorID(idEntidadExterna);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorIDEntidadExterna : " + e.toString());
            return null;
        }
    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorCorreoNumDocumento(String correo, String documento) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorCorreoNumDocumento(correo, documento);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorCorreoNumDocumento : " + e.toString());
            return null;
        }
    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorCorreo(String correo) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorCorreo(correo);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorCorreo : " + e.toString());
            return null;
        }
    }

    @Override
    public EntidadExterna obtenerEntidadExternaPorIdentificacion(String identificacion) {
        try {
            EntidadExterna registro = entidadExternaDAO.buscarEntidadExternaPorIdentificacionEntidad(identificacion);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO obtenerEntidadExternaPorIdentificacion : " + e.toString());
            return null;
        }
    }

    @Override
    public void actualizarInformacionEntidadExterna(EntidadExterna entidadExterna) {
        try {
            if (false == entidadExterna.getEstado()) {
                gestionarProcesosRelacionadosEntidadExterna(entidadExterna, false);
            } else {
                gestionarProcesosRelacionadosEntidadExterna(entidadExterna, true);
            }
            entidadExternaDAO.editarEntidadExterna(entidadExterna);
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO actualizarInformacionEntidadExterna : " + e.toString());
        }
    }

    private void gestionarProcesosRelacionadosEntidadExterna(EntidadExterna entidadExterna, boolean estado) {
        List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorEntidad(entidadExterna.getIdentidadexterna());
        if (null != lista) {
            for (int i = 0; i < lista.size(); i++) {
                List<PersonaContacto> listaPC = personaContactoDAO.consultarPersonasContactoPorConvenioEntidad(lista.get(i).getIdconvenioporentidad());
                if (null != listaPC) {
                    for (int j = 0; j < listaPC.size(); j++) {
                        PersonaContacto obj = listaPC.get(j);
                        obj.getPersona().getUsuario().setEstado(estado);
                        usuarioDAO.editarUsuario(obj.getPersona().getUsuario());
                    }
                }
                ConvenioPorEntidad obj2 = lista.get(i);
                obj2.setEstado(estado);
                convenioPorEntidadDAO.editarConvenioPorEntidad(obj2);
            }
        }
    }

    //@Override 
    public void actualizarInformacionPersona(Persona persona) {
        try {
            personaDAO.editarPersona(persona);
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO actualizarInformacionPersona : " + e.toString());
        }
    }

    //@Override 
    public void actualizarInformacionUsuario(Usuario usuario) {
        try {
            usuarioDAO.editarUsuario(usuario);
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO actualizarInformacionUsuario : " + e.toString());
        }
    }

    @Override
    public void almacenarNuevaEntidadExternaEnSistema(EntidadExterna entidadNueva) {
        try {
            entidadExternaDAO.crearEntidadExterna(entidadNueva);
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO almacenarNuevaEntidadExternaEnSistema : " + e.toString());
        }
    }

    @Override
    public Boolean validarCambioEstadoEntidad(BigInteger entidad) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorEntidad(entidad);
            if (null == lista) {
                return true;
            } else {
                int contador = 0;
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getEstado() == true) {
                        contador++;
                    }
                }
                if (contador == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO validarCambioEstadoEntidad : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SectorEntidad> obtenerSectorEntidadRegistrado() {
        try {
            List<SectorEntidad> lista = sectorEntidadDAO.consultarSectoresEntidad();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarEntidadesExternasBO validarCambioEstadoEntidad : " + e.toString());
            return null;
        }
    }

}
