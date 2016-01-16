/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.variables;

import com.sirelab.bo.interfacebo.variables.GestionarVariableTiposEventosBOInterface;
import com.sirelab.dao.interfacedao.TipoEventoDAOInterface;
import com.sirelab.entidades.TipoEvento;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import org.apache.log4j.Logger;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarVariableTiposEventosBO implements GestionarVariableTiposEventosBOInterface {

    static Logger logger = Logger.getLogger(GestionarVariableTiposEventosBO.class);
    
    @EJB
    TipoEventoDAOInterface tipoEventoDAO;

    @Override
    public void crearTipoEvento(TipoEvento tipoEvento) {
        try {
            tipoEventoDAO.crearTipoEvento(tipoEvento);;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposEventosBO crearTipoEvento : " + e.toString());
        }
    }

    @Override
    public void editarTipoEvento(TipoEvento tipoEvento) {
        try {
            tipoEventoDAO.editarTipoEvento(tipoEvento);
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposEventosBO editarTipoEvento : " + e.toString());
        }
    }

    @Override
    public void borrarTipoEvento(TipoEvento tipoEvento) {
        try {
            tipoEventoDAO.eliminarTipoEvento(tipoEvento);
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposEventosBO borrarTipoEvento : " + e.toString());
        }
    }

    @Override
    public TipoEvento consultarTipoEventoPorID(BigInteger idRegistro) {
        try {
            TipoEvento registro = tipoEventoDAO.buscarTipoEventoPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposEventosBO borrarTipoEvento : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<TipoEvento> consultarTiposEventosRegistrados() {
        try {
            List<TipoEvento> lista = tipoEventoDAO.consultarTiposEventos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarVariableTiposEventosBO borrarTipoEvento : " + e.toString());
            return null;
        }
    }
}
