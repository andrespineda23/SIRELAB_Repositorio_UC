/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.TipoComponenteDAOInterface;
import com.sirelab.entidades.TipoComponente;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class TipoComponenteDAO implements TipoComponenteDAOInterface {

    static Logger logger = Logger.getLogger(TipoComponenteDAO.class);
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearTipoComponente(TipoComponente tipoComponente) {
        try {
            em.persist(tipoComponente);
            em.flush();
        } catch (Exception e) {
            logger.error("Error crearTipoComponente TipoComponenteDAO : " + e.toString());
        }
    }

    @Override
    public void editarTipoComponente(TipoComponente tipoComponente) {
        try {
            em.merge(tipoComponente);
        } catch (Exception e) {
            logger.error("Error editarTipoComponente TipoComponenteDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarTipoComponente(TipoComponente tipoComponente) {
        try {
            em.remove(em.merge(tipoComponente));
        } catch (Exception e) {
            logger.error("Error eliminarTipoComponente TipoComponenteDAO : " + e.toString());
        }
    }

    @Override
    public List<TipoComponente> consultarTiposComponentes() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoComponente p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TipoComponente> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            logger.error("Error consultarTiposComponentes TipoComponenteDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public TipoComponente buscarTipoComponentePorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoComponente p WHERE p.idtipocomponente=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            TipoComponente registro = (TipoComponente) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            logger.error("Error buscarTipoComponentePorID TipoComponenteDAO : " + e.toString());
            return null;
        }
    }
}
