package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarFacultadesBOInterface;
import com.sirelab.dao.interfacedao.DepartamentoDAOInterface;
import com.sirelab.dao.interfacedao.FacultadDAOInterface;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Facultad;
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
public class GestionarFacultadesBO implements GestionarFacultadesBOInterface {

    static Logger logger = Logger.getLogger(GestionarFacultadesBO.class);
    
    @EJB
    FacultadDAOInterface facultadDAO;
    @EJB
    DepartamentoDAOInterface departamentoDAO;

    @Override
    public List<Facultad> consultarFacultadesPorParametro(Map<String, String> filtros) {
        try {
            List<Facultad> lista = facultadDAO.buscarFacultadesPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarFacultadBO consultarFacultadesPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearNuevaFacultad(Facultad facultad) {
        try {
            facultadDAO.crearFacultad(facultad);
        } catch (Exception e) {
            logger.error("Error GestionarFacultadBO crearNuevaFacultad : " + e.toString());
        }
    }

    @Override
    public void modificarInformacionFacultad(Facultad facultad) {
        try {
            facultadDAO.editarFacultad(facultad);
        } catch (Exception e) {
            logger.error("Error GestionarFacultadBO crearNuevaFacultad : " + e.toString());
        }
    }

    @Override
    public Facultad obtenerFacultadPorIDFacultad(BigInteger idFacultad) {
        try {
            Facultad registro = facultadDAO.buscarFacultadPorID(idFacultad);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarFacultadBO obtenerFacultadPorIDFacultad : " + e.toString());
            return null;
        }
    }

    @Override
    public Facultad obtenerFacultadPorIDCodigo(String codigo) {
        try {
            Facultad registro = facultadDAO.buscarFacultadPorCodigo(codigo);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarFacultadBO obtenerFacultadPorIDCodigo : " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarCambioEstadoFacultad(BigInteger facultad) {
        try {
            List<Departamento> lista = departamentoDAO.buscarDepartamentosPorIDFacultad(facultad);
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
            logger.error("Error GestionarFacultadBO Override : " + e.toString());
            return null;
        }
    }

}
