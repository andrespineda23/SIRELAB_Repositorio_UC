/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao.interfacedao;

import com.sirelab.entidades.TipoEvento;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface TipoEventoDAOInterface {

    public void crearTipoEvento(TipoEvento tipoEvento);

    public void editarTipoEvento(TipoEvento tipoEvento);

    public void eliminarTipoEvento(TipoEvento tipoEvento);

    public List<TipoEvento> consultarTiposEventos();

    public TipoEvento buscarTipoEventoPorID(BigInteger idRegistro);

}
