/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.planta;

import com.sirelab.entidades.ComponenteEquipo;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.Laboratorio;
import com.sirelab.entidades.ModuloLaboratorio;
import com.sirelab.entidades.SalaLaboratorio;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author AndresPineda
 */
public interface GestionarPlantaTransferenciaElementosBOInterface {

    public List<Laboratorio> obtenerLaboratoriosRegistrados();

    public List<SalaLaboratorio> obtenerSalasLaboratorioPorLaboratorio(BigInteger laboratorio);

    public List<ModuloLaboratorio> obtenerModulosLaboratorioPorSala(BigInteger sala);

    public List<EquipoElemento> obtenerEquiposElementosPorModulo(BigInteger modulo);

    public List<ComponenteEquipo> obtenerComponentesPorEquipo(BigInteger equipo);

    public void almacenarTransferenciaEquipo(EquipoElemento equipoTransferir, ModuloLaboratorio moduloNuevo, String usuario);

    public void almacenarTransferenciaComponente(ComponenteEquipo componenteTransferir, EquipoElemento equipoNuevo, String usuario);
}
