/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "ingresoinsumo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IngresoInsumo.findAll", query = "SELECT i FROM IngresoInsumo i"),
    @NamedQuery(name = "IngresoInsumo.findByIdingresoinsumo", query = "SELECT i FROM IngresoInsumo i WHERE i.idingresoinsumo = :idingresoinsumo"),
    @NamedQuery(name = "IngresoInsumo.findByFechaingreso", query = "SELECT i FROM IngresoInsumo i WHERE i.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "IngresoInsumo.findByCantidadingreso", query = "SELECT i FROM IngresoInsumo i WHERE i.cantidadingreso = :cantidadingreso"),
    @NamedQuery(name = "IngresoInsumo.findByCostoingreso", query = "SELECT i FROM IngresoInsumo i WHERE i.costoingreso = :costoingreso")})
public class IngresoInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idingresoinsumo")
    private BigInteger idingresoinsumo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidadingreso")
    private int cantidadingreso;
    @Column(name = "costoingreso")
    private Integer costoingreso;
    @JoinColumn(name = "proveedor", referencedColumnName = "idproveedor")
    @ManyToOne(optional = false)
    private Proveedor proveedor;
    @JoinColumn(name = "insumo", referencedColumnName = "idinsumo")
    @ManyToOne(optional = false)
    private Insumo insumo;

    public IngresoInsumo() {
    }

    public IngresoInsumo(BigInteger idingresoinsumo) {
        this.idingresoinsumo = idingresoinsumo;
    }

    public IngresoInsumo(BigInteger idingresoinsumo, Date fechaingreso, int cantidadingreso) {
        this.idingresoinsumo = idingresoinsumo;
        this.fechaingreso = fechaingreso;
        this.cantidadingreso = cantidadingreso;
    }

    public BigInteger getIdingresoinsumo() {
        return idingresoinsumo;
    }

    public void setIdingresoinsumo(BigInteger idingresoinsumo) {
        this.idingresoinsumo = idingresoinsumo;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public int getCantidadingreso() {
        return cantidadingreso;
    }

    public void setCantidadingreso(int cantidadingreso) {
        this.cantidadingreso = cantidadingreso;
    }

    public Integer getCostoingreso() {
        return costoingreso;
    }

    public void setCostoingreso(Integer costoingreso) {
        this.costoingreso = costoingreso;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idingresoinsumo != null ? idingresoinsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngresoInsumo)) {
            return false;
        }
        IngresoInsumo other = (IngresoInsumo) object;
        if ((this.idingresoinsumo == null && other.idingresoinsumo != null) || (this.idingresoinsumo != null && !this.idingresoinsumo.equals(other.idingresoinsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.IngresoInsumo[ idingresoinsumo=" + idingresoinsumo + " ]";
    }
    
}
