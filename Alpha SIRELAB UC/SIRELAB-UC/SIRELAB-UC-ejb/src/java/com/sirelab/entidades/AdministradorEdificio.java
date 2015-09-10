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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "administradoredificio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdministradorEdificio.findAll", query = "SELECT a FROM AdministradorEdificio a"),
    @NamedQuery(name = "AdministradorEdificio.findByIdadministradoredificio", query = "SELECT a FROM AdministradorEdificio a WHERE a.idadministradoredificio = :idadministradoredificio"),
    @NamedQuery(name = "AdministradorEdificio.findByNumeroatencion", query = "SELECT a FROM AdministradorEdificio a WHERE a.numeroatencion = :numeroatencion")})
public class AdministradorEdificio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idadministradoredificio")
    private BigInteger idadministradoredificio;
    @Size(max = 45)
    @Column(name = "numeroatencion")
    private String numeroatencion;
    @JoinColumn(name = "persona", referencedColumnName = "idpersona")
    @ManyToOne(optional = false)
    private Persona persona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "administradoredificio")
    private Collection<EncargadoPorEdificio> encargadoPorEdificioCollection;

    public AdministradorEdificio() {
    }

    public AdministradorEdificio(BigInteger idadministradoredificio) {
        this.idadministradoredificio = idadministradoredificio;
    }

    public BigInteger getIdadministradoredificio() {
        return idadministradoredificio;
    }

    public void setIdadministradoredificio(BigInteger idadministradoredificio) {
        this.idadministradoredificio = idadministradoredificio;
    }

    public String getNumeroatencion() {
        return numeroatencion;
    }

    public void setNumeroatencion(String numeroatencion) {
        this.numeroatencion = numeroatencion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @XmlTransient
    public Collection<EncargadoPorEdificio> getEncargadoPorEdificioCollection() {
        return encargadoPorEdificioCollection;
    }

    public void setEncargadoPorEdificioCollection(Collection<EncargadoPorEdificio> encargadoPorEdificioCollection) {
        this.encargadoPorEdificioCollection = encargadoPorEdificioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idadministradoredificio != null ? idadministradoredificio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdministradorEdificio)) {
            return false;
        }
        AdministradorEdificio other = (AdministradorEdificio) object;
        if ((this.idadministradoredificio == null && other.idadministradoredificio != null) || (this.idadministradoredificio != null && !this.idadministradoredificio.equals(other.idadministradoredificio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.AdministradorEdificio[ idadministradoredificio=" + idadministradoredificio + " ]";
    }
    
}
