package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarSedeBOInterface;
import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.Sede;
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
public class GestionarSedeBO implements GestionarSedeBOInterface {

    static Logger logger = Logger.getLogger(GestionarSedeBO.class);
    
    @EJB
    SedeDAOInterface sedeDAO;
    @EJB
    EdificioDAOInterface edificioDAO;

    @Override
    public List<Sede> consultarSedesPorParametro(Map<String, String> filtros) {
        try {
            List<Sede> lista = sedeDAO.buscarSedesPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarSedeBO consultarSedesPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevaSede(Sede sede) {
        try {
            sedeDAO.crearSede(sede);
        } catch (Exception e) {
            logger.error("Error GestionarSedeBO crearNuevaSede : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionSede(Sede sede) {
        try {
            sedeDAO.editarSede(sede);
        } catch (Exception e) {
            logger.error("Error GestionarSedeBO crearNuevaSede : " + e.toString());
        }
    }

    @Override
    public Sede obtenerSedePorIDSede(BigInteger idSede) {
        try {
            Sede registro = sedeDAO.buscarSedePorID(idSede);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarSedeBO obtenerSedePorIDSede : " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarcambioEstadoSede(BigInteger sede) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosPorIDSede(sede);
            if (null == lista) {
                return true;
            } else {
                int contador = 0;
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getEstado() == true) {
                        contador++;
                    }
                }
                if (contador == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            logger.error("Error GestionarSedeBO validarcambioEstadoSede : " + e.toString());
            return null;
        }
    }
}
