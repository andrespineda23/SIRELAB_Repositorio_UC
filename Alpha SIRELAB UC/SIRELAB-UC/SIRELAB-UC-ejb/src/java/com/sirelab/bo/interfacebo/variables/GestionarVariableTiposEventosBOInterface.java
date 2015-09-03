/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.variables;

import com.sirelab.entidades.TipoEvento;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarVariableTiposEventosBOInterface {

    public void crearTipoEvento(TipoEvento tipoEvento);

    public void editarTipoEvento(TipoEvento tipoEvento);

    public void borrarTipoEvento(TipoEvento tipoEvento);

    public TipoEvento consultarTipoEventoPorID(BigInteger idRegistro);

    public List<TipoEvento> consultarTiposEventosRegistrados();
}
