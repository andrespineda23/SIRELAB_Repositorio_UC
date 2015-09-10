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
@Table(name = "tipomovimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoMovimiento.findAll", query = "SELECT t FROM TipoMovimiento t"),
    @NamedQuery(name = "TipoMovimiento.findByIdtipomovimiento", query = "SELECT t FROM TipoMovimiento t WHERE t.idtipomovimiento = :idtipomovimiento"),
    @NamedQuery(name = "TipoMovimiento.findByNombretipo", query = "SELECT t FROM TipoMovimiento t WHERE t.nombretipo = :nombretipo")})
public class TipoMovimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipomovimiento")
    private BigInteger idtipomovimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombretipo")
    private String nombretipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipomovimiento")
    private Collection<MovimientoInsumo> movimientoInsumoCollection;

    public TipoMovimiento() {
    }

    public TipoMovimiento(BigInteger idtipomovimiento) {
        this.idtipomovimiento = idtipomovimiento;
    }

    public TipoMovimiento(BigInteger idtipomovimiento, String nombretipo) {
        this.idtipomovimiento = idtipomovimiento;
        this.nombretipo = nombretipo;
    }

    public BigInteger getIdtipomovimiento() {
        return idtipomovimiento;
    }

    public void setIdtipomovimiento(BigInteger idtipomovimiento) {
        this.idtipomovimiento = idtipomovimiento;
    }

    public String getNombretipo() {
        if (null != nombretipo) {
            return nombretipo.toUpperCase();
        }
        return nombretipo;
    }

    public void setNombretipo(String nombretipo) {
        this.nombretipo = nombretipo.toUpperCase();
    }

    @XmlTransient
    public Collection<MovimientoInsumo> getMovimientoInsumoCollection() {
        return movimientoInsumoCollection;
    }

    public void setMovimientoInsumoCollection(Collection<MovimientoInsumo> movimientoInsumoCollection) {
        this.movimientoInsumoCollection = movimientoInsumoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipomovimiento != null ? idtipomovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoMovimiento)) {
            return false;
        }
        TipoMovimiento other = (TipoMovimiento) object;
        if ((this.idtipomovimiento == null && other.idtipomovimiento != null) || (this.idtipomovimiento != null && !this.idtipomovimiento.equals(other.idtipomovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.TipoMovimiento[ idtipomovimiento=" + idtipomovimiento + " ]";
    }

}
