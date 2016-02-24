/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoManualesBOInterface;
import com.sirelab.dao.interfacedao.ManualDAOInterface;
import com.sirelab.entidades.Manual;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarRecursoManualesBO implements GestionarRecursoManualesBOInterface {

    static Logger logger = Logger.getLogger(GestionarRecursoManualesBO.class);
    
    @EJB
    ManualDAOInterface manualDAO;

    @Override
    public List<Manual> consultarManualesPorParametro(Map<String, String> filtros) {
        try {
            List<Manual> lista = manualDAO.buscarManualesPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoManualesBO consultarManualesPorParametro : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Manual obtenerManualPorID(BigInteger manual) {
        try {
            Manual registro = manualDAO.buscarManualPorID(manual);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoManualesBO obtenerManualPorID : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Manual consultarManualPorUbicacion(String ubicacion) {
        try {
            Manual registro = manualDAO.buscarManualPorUbicacion(ubicacion);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoManualesBO consultarManualPorUbicacion : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void crearManual(Manual manual) {
        try {
            manualDAO.crearManual(manual);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoManualesBO crearManual : " + e.toString(),e);
        }
    }

    @Override
    public void editarManual(Manual manual) {
        try {
            manualDAO.editarManual(manual);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoManualesBO editarManual : " + e.toString(),e);
        }
    }

}
