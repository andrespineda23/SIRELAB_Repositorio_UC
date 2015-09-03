/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.TipoComponente;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface TipoComponenteDAOInterface {
    public void crearTipoComponente(TipoComponente tipoComponente) ;
    public void editarTipoComponente(TipoComponente tipoComponente);
    public void eliminarTipoComponente(TipoComponente tipoComponente);
    public List<TipoComponente> consultarTiposComponentes();
    public TipoComponente buscarTipoComponentePorID(BigInteger idRegistro);
    
}
