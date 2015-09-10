/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.recursos;

import com.sirelab.entidades.IngresoInsumo;
import com.sirelab.entidades.Insumo;
import com.sirelab.entidades.Proveedor;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface GestionarRecursoIngresoInsumoBOInterface {

    public List<Insumo> consultarInsumosRegistrados();

    public void crearIngresoInsumo(IngresoInsumo ingresoInsumo);

    public void crearInsumoInsumoConInsumo(IngresoInsumo ingresoInsumo, Insumo insumo);

    public List<Proveedor> obtenerProveedoresRegistrados();
}
