package com.sirelab.bo.interfacebo.universidad;

import com.sirelab.entidades.Sede;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface GestionarSedeBOInterface {

    public List<Sede> consultarSedesPorParametro(Map<String, String> filtros);

    public void crearNuevaSede(Sede sede);

    public void modificarInformacionSede(Sede sede);

    public Sede obtenerSedePorIDSede(BigInteger idSede);

    public boolean eliminarSede(Sede sede);

    public Integer obtenerEdificiosAsociados(BigInteger sede);

    public Boolean validarcambioEstadoSede(BigInteger sede);
}
