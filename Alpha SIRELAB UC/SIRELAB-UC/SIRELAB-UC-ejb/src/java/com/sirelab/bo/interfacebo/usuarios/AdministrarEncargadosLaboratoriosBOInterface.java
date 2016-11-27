package com.sirelab.bo.interfacebo.usuarios;

import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.Edificio;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface AdministrarEncargadosLaboratoriosBOInterface {

    public Edificio buscarEdificioPorIdEncargadoEdificio(BigInteger idRegistro);

    public List<EncargadoLaboratorio> consultarEncargadoLaboratoriosPorParametro(Map<String, String> filtros);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorIDEncargadoLaboratorio(BigInteger idEncargadoLaboratorio);

    public List<Facultad> obtenerListaFacultades();

    public List<Facultad> obtenerListaFacultadesActivos();

    public List<Departamento> obtenerDepartamentosPorIDFacultad(BigInteger idFacultad);

    public List<Laboratorio> obtenerLaboratoriosPorIDDepartamento(BigInteger idDepartamento);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorCorreoNumDocumento(String correo, String documento);

    public void actualizarInformacionEncargadoLaboratorio(EncargadoLaboratorio personalLab);

    public void almacenarNuevoEncargadoLaboratorioEnSistema(Usuario usuarioNuevo, Persona personaNuevo, EncargadoLaboratorio personalNuevo);

    public List<Laboratorio> obtenerLaboratoriosActivosPorIDDepartamento(BigInteger idDepartamento);

    public void actualizarInformacionPersona(Persona persona);

    public void actualizarInformacionUsuario(Usuario usuario);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorCorreo(String correo);

    public EncargadoLaboratorio obtenerEncargadoLaboratorioPorDocumento(String documento);

    public List<Departamento> obtenerDepartamentosActivosPorIDFacultad(BigInteger idFacultad);

    public Departamento obtenerDepartamentoPorCodigo(String codigo);

    public Laboratorio obtenerLaboratorioPorCodigo(String codigo);

    public List<SalaLaboratorio> obtenerSalaLaboratorioPorEdificio(BigInteger edificio);

    public Persona obtenerPersonaSistemaPorIdentificacion(String identificacion);

    public Persona obtenerPersonaSistemaPorCorreo(String correo);

}
