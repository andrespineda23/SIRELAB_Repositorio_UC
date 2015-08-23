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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "laboratorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Laboratorio.findAll", query = "SELECT l FROM Laboratorio l"),
    @NamedQuery(name = "Laboratorio.findByIdlaboratorio", query = "SELECT l FROM Laboratorio l WHERE l.idlaboratorio = :idlaboratorio"),
    @NamedQuery(name = "Laboratorio.findByNombrelaboratorio", query = "SELECT l FROM Laboratorio l WHERE l.nombrelaboratorio = :nombrelaboratorio"),
    @NamedQuery(name = "Laboratorio.findByCodigolaboratorio", query = "SELECT l FROM Laboratorio l WHERE l.codigolaboratorio = :codigolaboratorio")})
public class Laboratorio implements Serializable {

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratorio")
    private Collection<EncargadoLaboratorio> encargadoLaboratorioCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlaboratorio")
    private BigInteger idlaboratorio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombrelaboratorio")
    private String nombrelaboratorio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigolaboratorio")
    private String codigolaboratorio;
    @JoinColumn(name = "departamento", referencedColumnName = "iddepartamento")
    @ManyToOne(optional = false)
    private Departamento departamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "laboratorio")
    private Collection<LaboratoriosPorAreas> laboratoriosPorAreasCollection;
    @Transient
    private String strEstado;

    public Laboratorio() {
    }

    public Laboratorio(BigInteger idlaboratorio) {
        this.idlaboratorio = idlaboratorio;
    }

    public Laboratorio(BigInteger idlaboratorio, String nombrelaboratorio, String codigolaboratorio) {
        this.idlaboratorio = idlaboratorio;
        this.nombrelaboratorio = nombrelaboratorio;
        this.codigolaboratorio = codigolaboratorio;
    }

    public BigInteger getIdlaboratorio() {
        return idlaboratorio;
    }

    public void setIdlaboratorio(BigInteger idlaboratorio) {
        this.idlaboratorio = idlaboratorio;
    }

    public String getNombrelaboratorio() {
        return nombrelaboratorio.toUpperCase();
    }

    public void setNombrelaboratorio(String nombrelaboratorio) {
        this.nombrelaboratorio = nombrelaboratorio.toUpperCase();
    }

    public String getCodigolaboratorio() {
        return codigolaboratorio;
    }

    public void setCodigolaboratorio(String codigolaboratorio) {
        this.codigolaboratorio = codigolaboratorio;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @XmlTransient
    public Collection<LaboratoriosPorAreas> getLaboratoriosPorAreasCollection() {
        return laboratoriosPorAreasCollection;
    }

    public void setLaboratoriosPorAreasCollection(Collection<LaboratoriosPorAreas> laboratoriosPorAreasCollection) {
        this.laboratoriosPorAreasCollection = laboratoriosPorAreasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlaboratorio != null ? idlaboratorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Laboratorio)) {
            return false;
        }
        Laboratorio other = (Laboratorio) object;
        if ((this.idlaboratorio == null && other.idlaboratorio != null) || (this.idlaboratorio != null && !this.idlaboratorio.equals(other.idlaboratorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.Laboratorio[ idlaboratorio=" + idlaboratorio + " ]";
    }

    @XmlTransient
    public Collection<EncargadoLaboratorio> getEncargadoLaboratorioCollection() {
        return encargadoLaboratorioCollection;
    }

    public void setEncargadoLaboratorioCollection(Collection<EncargadoLaboratorio> encargadoLaboratorioCollection) {
        this.encargadoLaboratorioCollection = encargadoLaboratorioCollection;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getStrEstado() {
        getEstado();
        if (null != estado) {
            if (estado == true) {
                strEstado = "ACTIVO";
            } else {
                strEstado = "INACTIVO";
            }
        }
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }

}
