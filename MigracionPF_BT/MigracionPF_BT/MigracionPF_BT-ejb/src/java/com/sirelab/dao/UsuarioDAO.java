package com.sirelab.dao;

import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ANDRES PINEDA
 */
@Stateless
public class UsuarioDAO implements UsuarioDAOInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "SIRELAB-UC-ejbPU")
    private EntityManager em;

    @Override
    public void crearUsuario(Usuario usuario) {
        try {
            System.out.println("Creo usuario");
            em.persist(usuario);
            em.flush();
            System.out.println("Fin crear usuario");
        } catch (Exception e) {
            System.out.println("Error crearUsuario UsuarioDAO : " + e.toString());
        }
    }

    @Override
    public void editarUsuario(Usuario usuario) {
        try {
            em.merge(usuario);
            em.flush();
        } catch (Exception e) {
            System.out.println("Error editarUsuario UsuarioDAO : " + e.toString());
        }
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        try {
            em.remove(em.merge(usuario));
            em.flush();
        } catch (Exception e) {
            System.out.println("Error eliminarUsuario UsuarioDAO : " + e.toString());
        }
    }

    @Override
    public List<Usuario> consultarUsuarios() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Usuario p");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Usuario> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarUsuarios UsuarioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public Usuario buscarUsuarioPorID(BigInteger idRegistro) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Usuario p WHERE p.idusuario=:idRegistro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.setParameter("idRegistro", idRegistro);
            Usuario registro = (Usuario) query.getSingleResult();
            return registro;
        } catch (Exception e) {
            System.out.println("Error buscarUsuarioPorID UsuarioDAO : " + e.toString());
            return null;
        }
    }

    @Override
    public Usuario obtenerUltimoUsuarioRegistrado() {
        try {
            em.clear();
            Query query = em.createQuery("SELECT p FROM Usuario p ORDER BY p.idusuario DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Usuario> registros = query.getResultList();
            if (registros != null) {
                Usuario ultimoRegistro = registros.get(0);
                System.out.println("ultimoRegistro : " + ultimoRegistro.getIdusuario());
                return ultimoRegistro;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error obtenerUltimoUsuarioRegistrado UsuarioDAO : " + e.toString());
            return null;
        }
    }
}
