/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoMovimientosInsumoBOInterface;
import com.sirelab.dao.interfacedao.InsumoDAOInterface;
import com.sirelab.dao.interfacedao.MovimientoInsumoDAOInterface;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.MovimientoInsumo;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class GestionarRecursoMovimientosInsumoBO implements GestionarRecursoMovimientosInsumoBOInterface {

    @EJB
    MovimientoInsumoDAOInterface movimientoInsumoDAO;
    @EJB
    InsumoDAOInterface insumoDAO;

    @Override
    public List<MovimientoInsumo> consultarMovimientosInsumoPorIDInsumo(BigInteger insumo) {
        try {
            List<MovimientoInsumo> lista = movimientoInsumoDAO.consultarMovimientosInsumoPorInsumo(insumo);
            return lista;
        } catch (Exception e) {
            System.out.println("GestionarRecursoMovimientosInsumoBO consultarMovimientosInsumoPorIDInsumo: " + e.toString());
            return null;
        }
    }

    @Override
    public Insumo obtenerInsumoPorID(BigInteger insumo) {
        try {
            Insumo registro = insumoDAO.buscarInsumoPorID(insumo);
            return registro;
        } catch (Exception e) {
            System.out.println("GestionarRecursoMovimientosInsumoBO obtenerInsumoPorID: " + e.toString());
            return null;
        }
    }

    @Override
    public MovimientoInsumo obtenerMovimientoInsumoPorID(BigInteger movimientoInsumo) {
        try {
            MovimientoInsumo registro = movimientoInsumoDAO.buscarMovimientoInsumoPorID(movimientoInsumo);
            return registro;
        } catch (Exception e) {
            System.out.println("GestionarRecursoMovimientosInsumoBO obtenerMovimientoInsumoPorID: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearMovimientoInsumo(MovimientoInsumo movimientoInsumo) {
        try {
            movimientoInsumoDAO.crearMovimientoInsumo(movimientoInsumo);
        } catch (Exception e) {
            System.out.println("GestionarRecursoMovimientosInsumoBO crearMovimientoInsumo: " + e.toString());
        }
    }

    @Override
    public void editarMovimientoInsumo(MovimientoInsumo movimientoInsumo) {
        try {
            movimientoInsumoDAO.editarMovimientoInsumo(movimientoInsumo);
        } catch (Exception e) {
            System.out.println("GestionarRecursoMovimientosInsumoBO editarMovimientoInsumo: " + e.toString());
        }
    }
}
