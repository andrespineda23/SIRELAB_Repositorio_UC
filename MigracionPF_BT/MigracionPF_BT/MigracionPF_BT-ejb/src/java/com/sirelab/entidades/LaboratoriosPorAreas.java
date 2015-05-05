/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "laboratoriosporareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LaboratoriosPorAreas.findAll", query = "SELECT l FROM LaboratoriosPorAreas l"),
    @NamedQuery(name = "LaboratoriosPorAreas.findByIdlaboratoriosporareas", query = "SELECT l FROM LaboratoriosPorAreas l WHERE l.idlaboratoriosporareas = :idlaboratoriosporareas")})
public class LaboratoriosPorAreas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlaboratoriosporareas")
    private BigInteger idlaboratoriosporareas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratoriosporareas")
    private Collection<SalaLaboratorio> salaLaboratorioCollection;
    @JoinColumn(name = "laboratorio", referencedColumnName = "idlaboratorio")
    @ManyToOne(optional = false)
    private Laboratorio laboratorio;
    @JoinColumn(name = "areaprofundizacion", referencedColumnName = "idareaprofundizacion")
    @ManyToOne(optional = false)
    private AreaProfundizacion areaprofundizacion;
    @Transient
    private String informacionRegistro;

    public LaboratoriosPorAreas() {
    }

    public LaboratoriosPorAreas(BigInteger idlaboratoriosporareas) {
        this.idlaboratoriosporareas = idlaboratoriosporareas;
    }

    public BigInteger getIdlaboratoriosporareas() {
        return idlaboratoriosporareas;
    }

    public void setIdlaboratoriosporareas(BigInteger idlaboratoriosporareas) {
        this.idlaboratoriosporareas = idlaboratoriosporareas;
    }

    @XmlTransient
    public Collection<SalaLaboratorio> getSalaLaboratorioCollection() {
        return salaLaboratorioCollection;
    }

    public void setSalaLaboratorioCollection(Collection<SalaLaboratorio> salaLaboratorioCollection) {
        this.salaLaboratorioCollection = salaLaboratorioCollection;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public AreaProfundizacion getAreaprofundizacion() {
        return areaprofundizacion;
    }

    public void setAreaprofundizacion(AreaProfundizacion areaprofundizacion) {
        this.areaprofundizacion = areaprofundizacion;
    }

    public String getInformacionRegistro() {
        getAreaprofundizacion();
        getLaboratorio();
        if (null != areaprofundizacion && null != laboratorio) {
            informacionRegistro = laboratorio.getCodigolaboratorio() + " - " + areaprofundizacion.getNombrearea();
        } else {
            informacionRegistro = "";
        }
        return informacionRegistro.toUpperCase();
    }

    public void setInformacionRegistro(String informacionRegistro) {
        this.informacionRegistro = informacionRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlaboratoriosporareas != null ? idlaboratoriosporareas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LaboratoriosPorAreas)) {
            return false;
        }
        LaboratoriosPorAreas other = (LaboratoriosPorAreas) object;
        if ((this.idlaboratoriosporareas == null && other.idlaboratoriosporareas != null) || (this.idlaboratoriosporareas != null && !this.idlaboratoriosporareas.equals(other.idlaboratoriosporareas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.LaboratoriosPorAreas[ idlaboratoriosporareas=" + idlaboratoriosporareas + " ]";
    }

}
