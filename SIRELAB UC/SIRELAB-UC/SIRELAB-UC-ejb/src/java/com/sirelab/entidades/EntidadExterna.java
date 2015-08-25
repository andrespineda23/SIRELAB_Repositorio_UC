/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.awt.AlphaComposite;
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
 * @author ELECTRONICA
 */
@Entity
@Table(name = "entidadexterna")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntidadExterna.findAll", query = "SELECT e FROM EntidadExterna e"),
    @NamedQuery(name = "EntidadExterna.findByIdentidadexterna", query = "SELECT e FROM EntidadExterna e WHERE e.identidadexterna = :identidadexterna"),
    @NamedQuery(name = "EntidadExterna.findBySector", query = "SELECT e FROM EntidadExterna e WHERE e.sector = :sector"),
    @NamedQuery(name = "EntidadExterna.findByEstado", query = "SELECT e FROM EntidadExterna e WHERE e.estado = :estado")})
public class EntidadExterna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "identidadexterna")
    private BigInteger identidadexterna;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "sector")
    private String sector;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "persona", referencedColumnName = "idpersona")
    @ManyToOne(optional = false)
    private Persona persona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidadexterna")
    private Collection<ConvenioPorEntidad> convenioPorEntidadCollection;
    @Transient
    private String strEstado;

    public EntidadExterna() {
    }

    public EntidadExterna(BigInteger identidadexterna) {
        this.identidadexterna = identidadexterna;
    }

    public EntidadExterna(BigInteger identidadexterna, String sector, boolean estado) {
        this.identidadexterna = identidadexterna;
        this.sector = sector;
        this.estado = estado;
    }

    public BigInteger getIdentidadexterna() {
        return identidadexterna;
    }

    public void setIdentidadexterna(BigInteger identidadexterna) {
        this.identidadexterna = identidadexterna;
    }

    public String getSector() {
        if (null != sector) {
            return sector.toUpperCase();
        }
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector.toUpperCase();
    }

    public boolean getEstado() {
        return estado;
    }

    public String getStrEstado() {
        getEstado();
        if (estado == true) {
            strEstado = "ACTIVO";
        } else {
            strEstado = "INACTIVO";
        }
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @XmlTransient
    public Collection<ConvenioPorEntidad> getConvenioPorEntidadCollection() {
        return convenioPorEntidadCollection;
    }

    public void setConvenioPorEntidadCollection(Collection<ConvenioPorEntidad> convenioPorEntidadCollection) {
        this.convenioPorEntidadCollection = convenioPorEntidadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identidadexterna != null ? identidadexterna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadExterna)) {
            return false;
        }
        EntidadExterna other = (EntidadExterna) object;
        if ((this.identidadexterna == null && other.identidadexterna != null) || (this.identidadexterna != null && !this.identidadexterna.equals(other.identidadexterna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.EntidadExterna[ identidadexterna=" + identidadexterna + " ]";
    }

}
