package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarEdificiosBOInterface;
import com.sirelab.dao.interfacedao.EdificioDAOInterface;
import com.sirelab.dao.interfacedao.HorarioAtencionDAOInterface;
import com.sirelab.dao.interfacedao.SedeDAOInterface;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.HorarioAtencion;
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
public class GestionarEdificiosBO implements GestionarEdificiosBOInterface {

    static Logger logger = Logger.getLogger(GestionarEdificiosBO.class);
    
    @EJB
    EdificioDAOInterface edificioDAO;
    @EJB
    SedeDAOInterface sedeDAO;
    @EJB
    HorarioAtencionDAOInterface horarioAtencionDAO;

    //@Override
    public List<Sede> consultarSedesRegistradas() {
        try {
            List<Sede> lista = sedeDAO.consultarSedes();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarEdificioBO consultarSedesRegistradas : " + e.toString(),e);
            return null;
        }
    }
    //@Override
    public List<Sede> consultarSedesActivosRegistradas() {
        try {
            List<Sede> lista = sedeDAO.consultarSedesActivos();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarEdificioBO consultarSedesRegistradas : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<HorarioAtencion> consultarHorariosAtencionRegistrados() {
        try {
            List<HorarioAtencion> lista = horarioAtencionDAO.consultarHorariosAtencion();
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarEdificioBO consultarHorariosAtencionRegistrados : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public List<Edificio> consultarEdificiosPorParametro(Map<String, String> filtros) {
        try {
            List<Edificio> lista = edificioDAO.buscarEdificiosPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarEdificioBO consultarEdificiosPorParametro : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void crearNuevaEdificio(Edificio edificio) {
        try {
            edificioDAO.crearEdificio(edificio);
        } catch (Exception e) {
            logger.error("Error GestionarEdificioBO crearNuevaEdificio : " + e.toString(),e);
        }
    }

    @Override
    public void modificarInformacionEdificio(Edificio edificio) {
        try {
            edificioDAO.editarEdificio(edificio);
        } catch (Exception e) {
            logger.error("Error GestionarEdificioBO crearNuevaEdificio : " + e.toString(),e);
        }
    }

    @Override
    public Edificio obtenerEdificioPorIDEdificio(BigInteger idEdificio) {
        try {
            Edificio registro = edificioDAO.buscarEdificioPorID(idEdificio);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarEdificioBO obtenerEdificioPorIDEdificio : " + e.toString(),e);
            return null;
        }
    }
}
