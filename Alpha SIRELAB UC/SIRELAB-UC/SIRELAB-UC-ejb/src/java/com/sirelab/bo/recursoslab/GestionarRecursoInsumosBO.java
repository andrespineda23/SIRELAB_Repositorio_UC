/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoInsumosBOInterface;
import com.sirelab.dao.interfacedao.InsumoDAOInterface;
import com.sirelab.entidades.Insumo;
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
public class GestionarRecursoInsumosBO implements GestionarRecursoInsumosBOInterface {

    static Logger logger = Logger.getLogger(GestionarRecursoInsumosBO.class);
    
    @EJB
    InsumoDAOInterface insumoDAO;

    @Override
    public List<Insumo> consultarInsumosPorParametro(Map<String, String> filtros) {
        try {
            List<Insumo> lista = insumoDAO.buscarInsumosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoInsumosBO consultarInsumosPorParametro : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void crearNuevoInsumo(Insumo insumo) {
        try {
            insumoDAO.crearInsumo(insumo);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoInsumosBO crearNuevoInsumo : " + e.toString(),e);
        }
    }

    @Override
    public void modificarInformacionInsumo(Insumo insumo) {
        try {
            insumoDAO.editarInsumo(insumo);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoInsumosBO modificarInformacionInsumo : " + e.toString(),e);
        }
    }

    @Override 
    public Insumo obtenerInsumoPorIDInsumo(BigInteger idInsumo) {
        try {
            Insumo registro = insumoDAO.buscarInsumoPorID(idInsumo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoInsumosBO obtenerInsumoPorIDProveedor : " + e.toString(),e);
            return null;
        }
    }
    
    @Override 
    public Insumo obtenerInsumoPorCodigo(String codigo) {
        try {
            Insumo registro = insumoDAO.buscarInsumoPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoInsumosBO obtenerInsumoPorCodigo : " + e.toString(),e);
            return null;
        }
    }

}
