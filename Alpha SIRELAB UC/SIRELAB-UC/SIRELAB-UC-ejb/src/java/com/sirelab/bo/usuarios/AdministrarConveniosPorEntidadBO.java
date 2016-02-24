/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.usuarios;

import com.sirelab.bo.interfacebo.usuarios.AdministrarConveniosPorEntidadBOInterface;
import com.sirelab.dao.interfacedao.ConvenioDAOInterface;
import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.dao.interfacedao.EntidadExternaDAOInterface;
import com.sirelab.dao.interfacedao.PersonaContactoDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.EntidadExterna;
import com.sirelab.entidades.PersonaContacto;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarConveniosPorEntidadBO implements AdministrarConveniosPorEntidadBOInterface {

    static Logger logger = Logger.getLogger(AdministrarConveniosPorEntidadBO.class);
    
    @EJB
    PersonaContactoDAOInterface personaContactoDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    ConvenioPorEntidadDAOInterface convenioPorEntidadDAO;
    @EJB
    ConvenioDAOInterface convenioDAOInterface;
    @EJB
    EntidadExternaDAOInterface entidadExternaDAO;

    @Override
    public List<Convenio> consultarConveniosRegistrados() {
        try {
            List<Convenio> lista = convenioDAOInterface.consultarConvenios();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO consultarConveniosRegistrados: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Convenio> consultarConveniosActivosRegistrados() {
        try {
            List<Convenio> lista = convenioDAOInterface.consultarConveniosActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO consultarConveniosRegistrados: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EntidadExterna> consultarEntidadesExternasRegistradas() {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.consultarEntidadesExternas();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO consultarEntidadesExternasRegistradas: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EntidadExterna> consultarEntidadesExternasActivasRegistradas() {
        try {
            List<EntidadExterna> lista = entidadExternaDAO.consultarEntidadesExternasActivas();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO consultarEntidadesExternasActivasRegistradas: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> buscarConveniosPorEntidad() {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidad();
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO buscarConveniosPorEntidad: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> buscarConveniosPorEntidadPorIdEntidad(BigInteger entidad) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorEntidad(entidad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO buscarConveniosPorEntidadPorIdEntidad: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> buscarConveniosPorEntidadPorIdConvenio(BigInteger convenio) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorConvenio(convenio);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO buscarConveniosPorEntidadPorIdConvenio: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void crearConvenioPorEntidad(ConvenioPorEntidad convenio) {
        try {
            convenioPorEntidadDAO.crearConvenioPorEntidad(convenio);
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO crearConvenioPorEntidad: " + e.toString(),e);
        }
    }

    @Override
    public void editarConvenioPorEntidad(ConvenioPorEntidad convenio) {
        try {
            if (false == convenio.getEstado()) {
                gestionarProcesosRelacionadosConvenioEntidad(convenio, false);
            } else {
                gestionarProcesosRelacionadosConvenioEntidad(convenio, true);
            }
            convenioPorEntidadDAO.editarConvenioPorEntidad(convenio);
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO editarConvenioPorEntidad: " + e.toString(),e);
        }
    }

    private void gestionarProcesosRelacionadosConvenioEntidad(ConvenioPorEntidad convenio, boolean estado) {
        List<PersonaContacto> listaPC = personaContactoDAO.consultarPersonasContactoPorConvenioEntidad(convenio.getIdconvenioporentidad());
        if (null != listaPC) {
            for (int j = 0; j < listaPC.size(); j++) {
                PersonaContacto obj = listaPC.get(j);
                obj.getPersona().getUsuario().setEstado(estado);
                usuarioDAO.editarUsuario(obj.getPersona().getUsuario());
            }
        }
    }

    @Override
    public ConvenioPorEntidad obtenerConvenioPorEntidadPorID(BigInteger idRegistro) {
        try {
            ConvenioPorEntidad registro = convenioPorEntidadDAO.buscarConvenioPorEntidadPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO obtenerConvenioPorEntidadPorID: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public ConvenioPorEntidad obtenerConvenioPorEntidadPorParametros(BigInteger entidad, BigInteger convenio) {
        try {
            ConvenioPorEntidad registro = convenioPorEntidadDAO.buscarConvenioPorEntidadPorParametros(entidad, convenio);
            return registro;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO obtenerConvenioPorEntidadPorParametros: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<ConvenioPorEntidad> obtenerConvenioPorEntidadPorIdEntidad(BigInteger idEntidad) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorEntidad(idEntidad);
            return lista;
        } catch (Exception e) {
            logger.error("Error AdministrarConveniosPorEntidadBO obtenerConvenioPorEntidadPorIdEntidad: " + e.toString(),e);
            return null;
        }
    }

}
