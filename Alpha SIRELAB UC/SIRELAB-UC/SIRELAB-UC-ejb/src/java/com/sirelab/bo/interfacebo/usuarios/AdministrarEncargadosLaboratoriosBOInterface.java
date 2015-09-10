package com.sirelab.bo.interfacebo.usuarios;

import com.sirelab.entidades.AreaProfundizacion;
import com.sirelab.entidades.Departamento;
import com.sirelab.entidades.EncargadoLaboratorio;
import com.sirelab.entidades.Facultad;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.TipoPerfil;
import com.sirelab.entidades.Usuario;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDRES PINEDA
 */
public interface AdministrarEncargadosLaboratoriosBOInterface {

    public List<TipoPerfil> consultarPerfilesPorEncargadoRegistrados();

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

    public TipoPerfil buscarTipoPerfilPorIDEncargado(BigInteger idRegistro);

    public Departamento obtenerDepartamentoPorCodigo(String codigo);

    public AreaProfundizacion obtenerAreaProfundizacionPorCodigo(String codigo);

    public Laboratorio obtenerLaboratorioPorCodigo(String codigo);

}
