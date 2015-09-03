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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "tipocargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCargo.findAll", query = "SELECT t FROM TipoCargo t"),
    @NamedQuery(name = "TipoCargo.findByIdtipocargo", query = "SELECT t FROM TipoCargo t WHERE t.idtipocargo = :idtipocargo"),
    @NamedQuery(name = "TipoCargo.findByNombrecargo", query = "SELECT t FROM TipoCargo t WHERE t.nombrecargo = :nombrecargo")})
public class TipoCargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipocargo")
    private BigInteger idtipocargo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombrecargo")
    private String nombrecargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargo")
    private Collection<Docente> docenteCollection;

    public TipoCargo() {
    }

    public TipoCargo(BigInteger idtipocargo) {
        this.idtipocargo = idtipocargo;
    }

    public TipoCargo(BigInteger idtipocargo, String nombrecargo) {
        this.idtipocargo = idtipocargo;
        this.nombrecargo = nombrecargo;
    }

    public BigInteger getIdtipocargo() {
        return idtipocargo;
    }

    public void setIdtipocargo(BigInteger idtipocargo) {
        this.idtipocargo = idtipocargo;
    }

    public String getNombrecargo() {
        return nombrecargo;
    }

    public void setNombrecargo(String nombrecargo) {
        this.nombrecargo = nombrecargo;
    }

    @XmlTransient
    public Collection<Docente> getDocenteCollection() {
        return docenteCollection;
    }

    public void setDocenteCollection(Collection<Docente> docenteCollection) {
        this.docenteCollection = docenteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocargo != null ? idtipocargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCargo)) {
            return false;
        }
        TipoCargo other = (TipoCargo) object;
        if ((this.idtipocargo == null && other.idtipocargo != null) || (this.idtipocargo != null && !this.idtipocargo.equals(other.idtipocargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.TipoCargo[ idtipocargo=" + idtipocargo + " ]";
    }
    
}
