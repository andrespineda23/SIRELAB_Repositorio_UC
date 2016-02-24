/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoMovimientosInsumoBOInterface;
import com.sirelab.dao.interfacedao.EquipoElementoDAOInterface;
import com.sirelab.dao.interfacedao.InsumoDAOInterface;
import com.sirelab.dao.interfacedao.MovimientoInsumoAEquipoDAOInterface;
import com.sirelab.dao.interfacedao.MovimientoInsumoDAOInterface;
import com.sirelab.dao.interfacedao.TipoMovimientoDAOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.MovimientoInsumo;
import com.sirelab.entidades.MovimientoInsumoAEquipo;
import com.sirelab.entidades.TipoMovimiento;
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
public class GestionarRecursoMovimientosInsumoBO implements GestionarRecursoMovimientosInsumoBOInterface {

    static Logger logger = Logger.getLogger(GestionarRecursoMovimientosInsumoBO.class);
    
    @EJB
    MovimientoInsumoDAOInterface movimientoInsumoDAO;
    @EJB
    InsumoDAOInterface insumoDAO;
    @EJB
    TipoMovimientoDAOInterface tipoMovimientoDAO;
    @EJB
    EquipoElementoDAOInterface equipoElementoDAO;
    @EJB
    MovimientoInsumoAEquipoDAOInterface movimientoInsumoAEquipoDAO;

    @Override
    public List<MovimientoInsumo> consultarMovimientosInsumoPorIDInsumo(BigInteger insumo) {
        try {
            List<MovimientoInsumo> lista = movimientoInsumoDAO.consultarMovimientosInsumoPorInsumo(insumo);
            return lista;
        } catch (Exception e) {
            logger.error("GestionarRecursoMovimientosInsumoBO consultarMovimientosInsumoPorIDInsumo: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public Insumo obtenerInsumoPorID(BigInteger insumo) {
        try {
            Insumo registro = insumoDAO.buscarInsumoPorID(insumo);
            return registro;
        } catch (Exception e) {
            logger.error("GestionarRecursoMovimientosInsumoBO obtenerInsumoPorID: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public MovimientoInsumo obtenerMovimientoInsumoPorID(BigInteger movimientoInsumo) {
        try {
            MovimientoInsumo registro = movimientoInsumoDAO.buscarMovimientoInsumoPorID(movimientoInsumo);
            return registro;
        } catch (Exception e) {
            logger.error("GestionarRecursoMovimientosInsumoBO obtenerMovimientoInsumoPorID: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void crearMovimientoInsumo(MovimientoInsumo movimientoInsumo) {
        try {
            movimientoInsumoDAO.crearMovimientoInsumo(movimientoInsumo);
            Insumo registro = movimientoInsumo.getInsumo();
            int cantidad = registro.getCantidadexistencia() - movimientoInsumo.getCantidadmovimiento();
            registro.setCantidadexistencia(cantidad);
            insumoDAO.editarInsumo(registro);
        } catch (Exception e) {
            logger.error("GestionarRecursoMovimientosInsumoBO crearMovimientoInsumo: " + e.toString(),e);
        }
    }
    @Override
    public void crearMovimientoInsumoAEquipo(MovimientoInsumo movimientoInsumo, EquipoElemento equipo) {
        try {
            movimientoInsumoDAO.crearMovimientoInsumo(movimientoInsumo);
            Insumo registro = movimientoInsumo.getInsumo();
            int cantidad = registro.getCantidadexistencia() - movimientoInsumo.getCantidadmovimiento();
            registro.setCantidadexistencia(cantidad);
            insumoDAO.editarInsumo(registro);
            MovimientoInsumo movimiento = movimientoInsumoDAO.obtenerUltimaMovimientoInsumoRegistrada();
            MovimientoInsumoAEquipo movimientoInsumoAEquipo = new MovimientoInsumoAEquipo();
            movimientoInsumoAEquipo.setEquipo(equipo);
            movimientoInsumoAEquipo.setMovimientoinsumo(movimiento);
            movimientoInsumoAEquipoDAO.crearMovimientoInsumoAEquipo(movimientoInsumoAEquipo);
        } catch (Exception e) {
            logger.error("GestionarRecursoMovimientosInsumoBO crearMovimientoInsumo: " + e.toString(),e);
        }
    }

    @Override
    public void editarMovimientoInsumo(MovimientoInsumo movimientoInsumo) {
        try {
            movimientoInsumoDAO.editarMovimientoInsumo(movimientoInsumo);
        } catch (Exception e) {
            logger.error("GestionarRecursoMovimientosInsumoBO editarMovimientoInsumo: " + e.toString(),e);
        }
    }

    @Override
    public List<TipoMovimiento> obtenerTipoMovimientoRegistrado() {
        try {
            List<TipoMovimiento> lista = tipoMovimientoDAO.consultarTiposMovimientos();
            return lista;
        } catch (Exception e) {
            logger.error("GestionarRecursoMovimientosInsumoBO obtenerTipoMovimientoRegistrado: " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<EquipoElemento> obtenerEquipoElementoRegistrado() {
        try {
            List<EquipoElemento> lista = equipoElementoDAO.consultarEquiposElementos();
            return lista;
        } catch (Exception e) {
            logger.error("GestionarRecursoMovimientosInsumoBO obtenerEquipoElementoRegistrado: " + e.toString(),e);
            return null;
        }
    }
}
