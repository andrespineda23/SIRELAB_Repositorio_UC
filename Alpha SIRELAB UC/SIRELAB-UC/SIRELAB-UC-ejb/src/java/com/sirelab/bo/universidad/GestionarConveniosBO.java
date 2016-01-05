/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.universidad;

import com.sirelab.bo.interfacebo.universidad.GestionarConveniosBOInterface;
import com.sirelab.dao.interfacedao.ConvenioDAOInterface;
import com.sirelab.dao.interfacedao.ConvenioPorEntidadDAOInterface;
import com.sirelab.dao.interfacedao.PersonaContactoDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Convenio;
import com.sirelab.entidades.ConvenioPorEntidad;
import com.sirelab.entidades.PersonaContacto;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class GestionarConveniosBO implements GestionarConveniosBOInterface {

    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    ConvenioDAOInterface convenioDAO;
    @EJB
    ConvenioPorEntidadDAOInterface convenioPorEntidadDAO;
    @EJB
    PersonaContactoDAOInterface personaContactoDAO;

    @Override
    public List<Convenio> consultarConveniosPorParametro(Map<String, String> filtros) {
        try {
            List<Convenio> lista = convenioDAO.buscarConvenioPorFiltrado(filtros);
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarConveniosBO consultarConveniosPorParametro : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearConvenio(Convenio convenio) {
        try {
            convenioDAO.crearConvenio(convenio);
        } catch (Exception e) {
            System.out.println("Error GestionarConveniosBO crearConvenio : " + e.toString());
        }
    }

    @Override
    public void editarConvenio(Convenio convenio) {
        try {
            if (false == convenio.getEstado()) {
                gestionarProcesosRelacionadosConvenio(convenio, false);
            } 
            System.out.println("EJB");
            convenioDAO.editarConvenio(convenio);
        } catch (Exception e) {
            System.out.println("Error GestionarConveniosBO editarConvenio : " + e.toString());
        }
    }

    private void gestionarProcesosRelacionadosConvenio(Convenio convenio, boolean estado) {
        List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorConvenio(convenio.getIdconvenio());
        if (null != lista) {
            for (int i = 0; i < lista.size(); i++) {
                List<PersonaContacto> listaPC = personaContactoDAO.consultarPersonasContactoPorConvenioEntidad(lista.get(i).getIdconvenioporentidad());
                if (null != listaPC) {
                    for (int j = 0; j < listaPC.size(); j++) {
                        PersonaContacto obj = listaPC.get(j);
                        obj.getPersona().getUsuario().setEstado(estado);
                        usuarioDAO.editarUsuario(obj.getPersona().getUsuario());
                    }
                }
                ConvenioPorEntidad obj2 = lista.get(i);
                obj2.setEstado(estado);
                convenioPorEntidadDAO.editarConvenioPorEntidad(obj2);
            }
        }
    }

    @Override
    public void borrarConvenio(Convenio convenio) {
        try {
            convenioDAO.eliminarConvenio(convenio);
        } catch (Exception e) {
            System.out.println("Error GestionarConveniosBO borrarConvenio : " + e.toString());
        }
    }

    @Override
    public Convenio consultarConvenioPorID(BigInteger idRegistro) {
        try {
            Convenio registro = convenioDAO.buscarConvenioPorID(idRegistro);
            return registro;
        } catch (Exception e) {
            System.out.println("Error GestionarConveniosBO consultarConvenioPorID : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Convenio> consultarConveniosRegistrados() {
        try {
            List<Convenio> lista = convenioDAO.consultarConvenios();
            return lista;
        } catch (Exception e) {
            System.out.println("Error GestionarConveniosBO consultarConveniosRegistrados : " + e.toString());
            return null;
        }
    }

    @Override
    public Boolean validarCambioEstadoConvenio(BigInteger convenio) {
        try {
            List<ConvenioPorEntidad> lista = convenioPorEntidadDAO.consultarConveniosPorEntidadPorConvenio(convenio);
            if (null == lista) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error GestionarConveniosBO validarCambioEstadoConvenio : " + e.toString());
            return null;
        }
    }
}
