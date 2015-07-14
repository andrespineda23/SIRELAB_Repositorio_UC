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
@Table(name = "tipocomponente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoComponente.findAll", query = "SELECT t FROM TipoComponente t"),
    @NamedQuery(name = "TipoComponente.findByIdtipocomponente", query = "SELECT t FROM TipoComponente t WHERE t.idtipocomponente = :idtipocomponente"),
    @NamedQuery(name = "TipoComponente.findByNombretipo", query = "SELECT t FROM TipoComponente t WHERE t.nombretipo = :nombretipo")})
public class TipoComponente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipocomponente")
    private BigInteger idtipocomponente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombretipo")
    private String nombretipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipocomponente")
    private Collection<ComponenteEquipo> componenteEquipoCollection;

    public TipoComponente() {
    }

    public TipoComponente(BigInteger idtipocomponente) {
        this.idtipocomponente = idtipocomponente;
    }

    public TipoComponente(BigInteger idtipocomponente, String nombretipo) {
        this.idtipocomponente = idtipocomponente;
        this.nombretipo = nombretipo;
    }

    public BigInteger getIdtipocomponente() {
        return idtipocomponente;
    }

    public void setIdtipocomponente(BigInteger idtipocomponente) {
        this.idtipocomponente = idtipocomponente;
    }

    public String getNombretipo() {
        return nombretipo.toUpperCase();
    }

    public void setNombretipo(String nombretipo) {
        this.nombretipo = nombretipo.toUpperCase();
    }

    @XmlTransient
    public Collection<ComponenteEquipo> getComponenteEquipoCollection() {
        return componenteEquipoCollection;
    }

    public void setComponenteEquipoCollection(Collection<ComponenteEquipo> componenteEquipoCollection) {
        this.componenteEquipoCollection = componenteEquipoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipocomponente != null ? idtipocomponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoComponente)) {
            return false;
        }
        TipoComponente other = (TipoComponente) object;
        if ((this.idtipocomponente == null && other.idtipocomponente != null) || (this.idtipocomponente != null && !this.idtipocomponente.equals(other.idtipocomponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.TipoComponente[ idtipocomponente=" + idtipocomponente + " ]";
    }
    
}
