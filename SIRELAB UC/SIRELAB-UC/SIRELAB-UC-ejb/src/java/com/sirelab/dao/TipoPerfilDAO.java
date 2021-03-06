/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.TipoPerfilDAOInterface;
import com.sirelab.entidades.TipoPerfil;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class TipoPerfilDAO implements TipoPerfilDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearTipoPerfil(TipoPerfil tipoPerfil) {
        try {
            em.persist(tipoPerfil);
        } catch (Exception e) {
            System.out.println("Error crearTipoPerfil TipoPerfilDAO : " + e.toString());
        }
    }

    @Override
    public void editarTipoPerfil(TipoPerfil tipoPerfil) {
        try {
            em.merge(tipoPerfil);
        } catch (Exception e) {
            System.out.println("Error editarTipoPerfil TipoPerfilDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarTipoPerfil(TipoPerfil tipoPerfil) {
        try {
            em.remove(em.merge(tipoPerfil));
        } catch (Exception e) {
            System.out.println("Error eliminarTipoPerfil TipoPerfilDAO : " + e.toString());
        }
    }

    @Override
    public List<TipoPerfil> consultarTiposPerfiles() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoPerfil p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TipoPerfil> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarTiposPerfiles TipoPerfilDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public TipoPerfil buscarTipoPerfilPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoPerfil p WHERE p.idtipoperfil=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            TipoPerfil registro = (TipoPerfil) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarTipoPerfilPorID TipoPerfilDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public TipoPerfil buscarTipoPerfilPorCodigo(String codigo) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM TipoPerfil p WHERE p.codigo=:codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("codigo", codigo);
            TipoPerfil registro = (TipoPerfil) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarTipoPerfilPorCodigo TipoPerfilDAO : " + e.toString());
            return null;
        }
    }
}
