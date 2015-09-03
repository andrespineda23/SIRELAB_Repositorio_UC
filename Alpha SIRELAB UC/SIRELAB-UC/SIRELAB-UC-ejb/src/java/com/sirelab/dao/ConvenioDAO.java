/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.ConvenioDAOInterface;
import com.sirelab.entidades.Convenio;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ELECTRONICA
 */
@Stateless
public class ConvenioDAO implements ConvenioDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearConvenio(Convenio convenio) {
        try {
            em.persist(convenio);
        } catch (Exception e) {
            System.out.println("Error crearConvenio ConvenioDAO : " + e.toString());
        }
    }

    @Override
    public void editarConvenio(Convenio convenio) {
        try {
            em.merge(convenio);
        } catch (Exception e) {
            System.out.println("Error editarConvenio ConvenioDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarConvenio(Convenio convenio) {
        try {
            em.remove(em.merge(convenio));
        } catch (Exception e) {
            System.out.println("Error eliminarConvenio ConvenioDAO : " + e.toString());
        }
    }

    @Override
    public List<Convenio> consultarConvenios() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Convenio p ORDER BY p.nombreconvenio ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Convenio> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarConvenios ConvenioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public Convenio buscarConvenioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Convenio p WHERE p.idconvenio=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Convenio registro = (Convenio) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarConvenioPorID ConvenioDAO : " + e.toString());
            return null;
        }
    }
}
