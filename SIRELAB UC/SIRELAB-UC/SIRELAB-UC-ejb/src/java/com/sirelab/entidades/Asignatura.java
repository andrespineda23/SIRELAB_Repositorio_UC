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
 * @author ELECTRONICA
 */
@Entity
@Table(name = "asignatura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignatura.findAll", query = "SELECT a FROM Asignatura a"),
    @NamedQuery(name = "Asignatura.findByIdasignatura", query = "SELECT a FROM Asignatura a WHERE a.idasignatura = :idasignatura"),
    @NamedQuery(name = "Asignatura.findByNombreasignatura", query = "SELECT a FROM Asignatura a WHERE a.nombreasignatura = :nombreasignatura"),
    @NamedQuery(name = "Asignatura.findByCodigoasignatura", query = "SELECT a FROM Asignatura a WHERE a.codigoasignatura = :codigoasignatura"),
    @NamedQuery(name = "Asignatura.findByNumerocreditos", query = "SELECT a FROM Asignatura a WHERE a.numerocreditos = :numerocreditos")})
public class Asignatura implements Serializable {

    @Column(name = "estado")
    private Boolean estado;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idasignatura")
    private BigInteger idasignatura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreasignatura")
    private String nombreasignatura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codigoasignatura")
    private String codigoasignatura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numerocreditos")
    private int numerocreditos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignatura")
    private Collection<AsignaturaPorPlanEstudio> asignaturaPorPlanEstudioCollection;
    @Transient
    private String strEstado;

    public Asignatura() {
    }

    public Asignatura(BigInteger idasignatura) {
        this.idasignatura = idasignatura;
    }

    public Asignatura(BigInteger idasignatura, String nombreasignatura, String codigoasignatura, int numerocreditos) {
        this.idasignatura = idasignatura;
        this.nombreasignatura = nombreasignatura;
        this.codigoasignatura = codigoasignatura;
        this.numerocreditos = numerocreditos;
    }

    public BigInteger getIdasignatura() {
        return idasignatura;
    }

    public void setIdasignatura(BigInteger idasignatura) {
        this.idasignatura = idasignatura;
    }

    public String getNombreasignatura() {
        return nombreasignatura;
    }

    public void setNombreasignatura(String nombreasignatura) {
        this.nombreasignatura = nombreasignatura;
    }

    public String getCodigoasignatura() {
        return codigoasignatura;
    }

    public void setCodigoasignatura(String codigoasignatura) {
        this.codigoasignatura = codigoasignatura;
    }

    public int getNumerocreditos() {
        return numerocreditos;
    }

    public void setNumerocreditos(int numerocreditos) {
        this.numerocreditos = numerocreditos;
    }

    @XmlTransient
    public Collection<AsignaturaPorPlanEstudio> getAsignaturaPorPlanEstudioCollection() {
        return asignaturaPorPlanEstudioCollection;
    }

    public void setAsignaturaPorPlanEstudioCollection(Collection<AsignaturaPorPlanEstudio> asignaturaPorPlanEstudioCollection) {
        this.asignaturaPorPlanEstudioCollection = asignaturaPorPlanEstudioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idasignatura != null ? idasignatura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignatura)) {
            return false;
        }
        Asignatura other = (Asignatura) object;
        if ((this.idasignatura == null && other.idasignatura != null) || (this.idasignatura != null && !this.idasignatura.equals(other.idasignatura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.Asignatura[ idasignatura=" + idasignatura + " ]";
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
