/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposUsuarioBOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.entidades.TipoUsuario;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarVariableTiposUsuarioBO implements GestionarVariableTiposUsuarioBOInterface {

    static Logger logger = Logger.getLogger(GestionarVariableTiposUsuarioBO.class);
    
    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;

    @Override
    public void crearTipoUsuario(TipoUsuario tipoUsuario) {
        try {
            tipoUsuarioDAO.crearTipoUsuario(tipoUsuario);;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposUsuariosBO crearTipoUsuario : " + e.toString());
        }
    }

    @Override
    public void editarTipoUsuario(TipoUsuario tipoUsuario) {
        try {
            tipoUsuarioDAO.editarTipoUsuario(tipoUsuario);
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposUsuariosBO editarTipoUsuario : " + e.toString());
        }
    }

    @Override
    public void borrarTipoUsuario(TipoUsuario tipoUsuario) {
        try {
            tipoUsuarioDAO.eliminarTipoUsuario(tipoUsuario);
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposUsuariosBO borrarTipoUsuario : " + e.toString());
        }
    }

    @Override
    public TipoUsuario consultarTipoUsuarioPorID(BigInteger idRegistro) {
        try {
            TipoUsuario registro = tipoUsuarioDAO.buscarTipoUsuarioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposUsuariosBO borrarTipoUsuario : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TipoUsuario> consultarTiposUsuariosRegistrados() {
        try {
            List<TipoUsuario> lista = tipoUsuarioDAO.consultarTiposUsuarios();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposUsuariosBO borrarTipoUsuario : " + e.toString());
            return null;
        }
    }
}
