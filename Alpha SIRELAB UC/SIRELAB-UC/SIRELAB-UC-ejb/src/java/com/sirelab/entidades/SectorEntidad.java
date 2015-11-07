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
 * @author AndresPineda
 */
@Entity
@Table(name = "sectorentidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SectorEntidad.findAll", query = "SELECT s FROM SectorEntidad s"),
    @NamedQuery(name = "SectorEntidad.findByIdsectorentidad", query = "SELECT s FROM SectorEntidad s WHERE s.idsectorentidad = :idsectorentidad"),
    @NamedQuery(name = "SectorEntidad.findByNombre", query = "SELECT s FROM SectorEntidad s WHERE s.nombre = :nombre")})
public class SectorEntidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsectorentidad")
    private BigInteger idsectorentidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sector")
    private Collection<EntidadExterna> entidadExternaCollection;

    public SectorEntidad() {
    }

    public SectorEntidad(BigInteger idsectorentidad) {
        this.idsectorentidad = idsectorentidad;
    }

    public SectorEntidad(BigInteger idsectorentidad, String nombre) {
        this.idsectorentidad = idsectorentidad;
        this.nombre = nombre;
    }

    public BigInteger getIdsectorentidad() {
        return idsectorentidad;
    }

    public void setIdsectorentidad(BigInteger idsectorentidad) {
        this.idsectorentidad = idsectorentidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public Collection<EntidadExterna> getEntidadExternaCollection() {
        return entidadExternaCollection;
    }

    public void setEntidadExternaCollection(Collection<EntidadExterna> entidadExternaCollection) {
        this.entidadExternaCollection = entidadExternaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsectorentidad != null ? idsectorentidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SectorEntidad)) {
            return false;
        }
        SectorEntidad other = (SectorEntidad) object;
        if ((this.idsectorentidad == null && other.idsectorentidad != null) || (this.idsectorentidad != null && !this.idsectorentidad.equals(other.idsectorentidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.SectorEntidad[ idsectorentidad=" + idsectorentidad + " ]";
    }
    
}
