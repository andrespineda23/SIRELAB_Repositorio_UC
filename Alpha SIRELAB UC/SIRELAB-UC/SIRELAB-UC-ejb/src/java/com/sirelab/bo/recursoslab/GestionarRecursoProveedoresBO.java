package com.sirelab.bo.recursoslab;

import com.sirelab.bo.interfacebo.recursos.GestionarRecursoProveedoresBOInterface;
import com.sirelab.dao.interfacedao.ProveedorDAOInterface;
import com.sirelab.entidades.Proveedor;
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
public class GestionarRecursoProveedoresBO implements GestionarRecursoProveedoresBOInterface {

    static Logger logger = Logger.getLogger(GestionarRecursoProveedoresBO.class);
    
    @EJB
    ProveedorDAOInterface proveedorDAO;

    @Override
    public List<Proveedor> consultarProveedoresPorParametro(Map<String, String> filtros) {
        try {
            List<Proveedor> lista = proveedorDAO.buscarProveedoresPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoProveedoresBO consultarProveedoresPorParametro : " + e.toString(),e);
            return null;
        }
    }

    @Override
    public void crearNuevoProveedor(Proveedor proveedor) {
        try {
            proveedorDAO.crearProveedor(proveedor);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoProveedoresBO crearNuevoProveedor : " + e.toString(),e);
        }
    }

    @Override
    public void modificarInformacionProveedor(Proveedor proveedor) {
        try {
            proveedorDAO.editarProveedor(proveedor);
        } catch (Exception e) {
            logger.error("Error GestionarRecursoProveedoresBO modificarInformacionProveedor : " + e.toString(),e);
        }
    }

    @Override
    public Proveedor obtenerProveedorPorIDProveedor(BigInteger idProveedor) {
        try {
            Proveedor registro = proveedorDAO.buscarProveedorPorID(idProveedor);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoProveedoresBO obtenerProveedorPorIDProveedor : " + e.toString(),e);
            return null;
        }
    }
    
    @Override
    public Proveedor obtenerProveedorPorNIT(String nitProveedor) {
        try {
            Proveedor registro = proveedorDAO.buscarProveedorPorNIT(nitProveedor);
            return registro;
        } catch (Exception e) {
            logger.error("Error GestionarRecursoProveedoresBO obtenerProveedorPorNIT : " + e.toString(),e);
            return null;
        }
    }

}
