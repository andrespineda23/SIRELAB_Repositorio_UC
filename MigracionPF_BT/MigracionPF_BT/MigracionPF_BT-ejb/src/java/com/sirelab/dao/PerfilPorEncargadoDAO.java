/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.dao;

import com.sirelab.dao.interfacedao.PerfilPorEncargadoDAOInterface;
import com.sirelab.entidades.PerfilPorEncargado;
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
public class PerfilPorEncargadoDAO implements PerfilPorEncargadoDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearPerfilPorEncargado(PerfilPorEncargado perfil) {
        try {
            em.clear();
            em.persist(perfil);
        } catch (Exception e) {
            System.out.println("Error crearPerfilPorEncargado PerfilPorEncargadoDAO : " + e.toString());
        }
    }

    @Override
    public void editarPerfilPorEncargado(PerfilPorEncargado perfil) {
        try {
            em.clear();
            em.merge(perfil);
        } catch (Exception e) {
            System.out.println("Error editarPerfilPorEncargado PerfilPorEncargadoDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarPerfilPorEncargado(PerfilPorEncargado perfil) {
        try {
            em.clear();
            em.remove(em.merge(perfil));
        } catch (Exception e) {
            System.out.println("Error eliminarPerfilPorEncargado PerfilPorEncargadoDAO : " + e.toString());
        }
    }

    @Override
    public List<PerfilPorEncargado> consultarPerfilesPorEncargado() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PerfilPorEncargado p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<PerfilPorEncargado> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarPerfilesPorEncargado PerfilPorEncargadoDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public PerfilPorEncargado buscarPerfilPorEncargadoPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM PerfilPorEncargado p WHERE p.idperfilporencargado=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            PerfilPorEncargado registro = (PerfilPorEncargado) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarPerfilPorEncargadoPorID PerfilPorEncargadoDAO : " + e.toString());
            return null;
        }
    }
}
