/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "movimientoinsumoaequipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovimientoInsumoAEquipo.findAll", query = "SELECT m FROM MovimientoInsumoAEquipo m"),
    @NamedQuery(name = "MovimientoInsumoAEquipo.findByIdmovimientoinsumoaequipo", query = "SELECT m FROM MovimientoInsumoAEquipo m WHERE m.idmovimientoinsumoaequipo = :idmovimientoinsumoaequipo")})
public class MovimientoInsumoAEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmovimientoinsumoaequipo")
    private BigInteger idmovimientoinsumoaequipo;
    @JoinColumn(name = "movimientoinsumo", referencedColumnName = "idmovimientoinsumo")
    @ManyToOne(optional = false)
    private MovimientoInsumo movimientoinsumo;
    @JoinColumn(name = "equipo", referencedColumnName = "idequipoelemento")
    @ManyToOne(optional = false)
    private EquipoElemento equipo;

    public MovimientoInsumoAEquipo() {
    }

    public MovimientoInsumoAEquipo(BigInteger idmovimientoinsumoaequipo) {
        this.idmovimientoinsumoaequipo = idmovimientoinsumoaequipo;
    }

    public BigInteger getIdmovimientoinsumoaequipo() {
        return idmovimientoinsumoaequipo;
    }

    public void setIdmovimientoinsumoaequipo(BigInteger idmovimientoinsumoaequipo) {
        this.idmovimientoinsumoaequipo = idmovimientoinsumoaequipo;
    }

    public MovimientoInsumo getMovimientoinsumo() {
        return movimientoinsumo;
    }

    public void setMovimientoinsumo(MovimientoInsumo movimientoinsumo) {
        this.movimientoinsumo = movimientoinsumo;
    }

    public EquipoElemento getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoElemento equipo) {
        this.equipo = equipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmovimientoinsumoaequipo != null ? idmovimientoinsumoaequipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientoInsumoAEquipo)) {
            return false;
        }
        MovimientoInsumoAEquipo other = (MovimientoInsumoAEquipo) object;
        if ((this.idmovimientoinsumoaequipo == null && other.idmovimientoinsumoaequipo != null) || (this.idmovimientoinsumoaequipo != null && !this.idmovimientoinsumoaequipo.equals(other.idmovimientoinsumoaequipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.MovimientoInsumoAEquipo[ idmovimientoinsumoaequipo=" + idmovimientoinsumoaequipo + " ]";
    }
    
}
