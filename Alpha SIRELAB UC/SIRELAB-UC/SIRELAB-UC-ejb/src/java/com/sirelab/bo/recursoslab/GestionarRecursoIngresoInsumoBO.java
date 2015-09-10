/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoIngresoInsumoBOInterface;
import com.sirelab.dao.interfacedao.IngresoInsumoDAOInterface;
import com.sirelab.dao.interfacedao.InsumoDAOInterface;
import com.sirelab.dao.interfacedao.ProveedorDAOInterface;
import com.sirelab.entidades.IngresoInsumo;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.Proveedor;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarRecursoIngresoInsumoBO implements GestionarRecursoIngresoInsumoBOInterface {

    @EJB
    InsumoDAOInterface insumoDAO;
    @EJB
    IngresoInsumoDAOInterface ingresoInsumoDAO;
    @EJB
    ProveedorDAOInterface proveedorDAOInterface;

    @Override
    public List<Insumo> consultarInsumosRegistrados() {
        try {
            List<Insumo> lista = insumoDAO.consultarInsumos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarRecursoIngresoInsumoBO consultarInsumosRegistrados: " + e.toString());
            return null;
        }
    }

    @Override
    public void crearIngresoInsumo(IngresoInsumo ingresoInsumo) {
        try {
            ingresoInsumoDAO.crearIngresoInsumo(ingresoInsumo);
            Insumo modificar = ingresoInsumo.getInsumo();
            int cantidad = modificar.getCantidadexistencia() + ingresoInsumo.getCantidadingreso();
            modificar.setCantidadexistencia(cantidad);
            insumoDAO.editarInsumo(modificar);
        } catch (Exception e) {
            System.out.println("Error GestionarRecursoIngresoInsumoBO crearIngresoInsumo: " + e.toString());
        }
    }

    @Override
    public void crearInsumoInsumoConInsumo(IngresoInsumo ingresoInsumo, Insumo insumo) {
        try {
            insumoDAO.crearInsumo(insumo);
            Insumo nuevo = insumoDAO.obtenerUltimaInsumoRegistrada();
            ingresoInsumo.setInsumo(nuevo);
            ingresoInsumoDAO.crearIngresoInsumo(ingresoInsumo);
        } catch (Exception e) {
            System.out.println("Error GestionarRecursoIngresoInsumoBO crearInsumoInsumoConInsumo: " + e.toString());
        }
    }

    @Override
    public List<Proveedor> obtenerProveedoresRegistrados() {
        try {
            List<Proveedor> lista = proveedorDAOInterface.consultarProveedores();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarRecursoIngresoInsumoBO crearInsumoInsumoConInsumo: " + e.toString());
            return null;
        }
    }

}
