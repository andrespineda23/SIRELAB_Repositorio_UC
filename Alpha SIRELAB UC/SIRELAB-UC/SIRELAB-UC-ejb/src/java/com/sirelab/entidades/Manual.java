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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "manual")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manual.findAll", query = "SELECT m FROM Manual m"),
    @NamedQuery(name = "Manual.findByIdmanual", query = "SELECT m FROM Manual m WHERE m.idmanual = :idmanual"),
    @NamedQuery(name = "Manual.findByNombremanual", query = "SELECT m FROM Manual m WHERE m.nombremanual = :nombremanual"),
    @NamedQuery(name = "Manual.findByUbicacionmanual", query = "SELECT m FROM Manual m WHERE m.ubicacionmanual = :ubicacionmanual"),
    @NamedQuery(name = "Manual.findByTipomanual", query = "SELECT m FROM Manual m WHERE m.tipomanual = :tipomanual"),
    @NamedQuery(name = "Manual.findByCodigomanual", query = "SELECT m FROM Manual m WHERE m.codigomanual = :codigomanual")})
public class Manual implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmanual")
    private BigInteger idmanual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombremanual")
    private String nombremanual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ubicacionmanual")
    private String ubicacionmanual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipomanual")
    private String tipomanual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "codigomanual")
    private String codigomanual;

    public Manual() {
    }

    public Manual(BigInteger idmanual) {
        this.idmanual = idmanual;
    }

    public Manual(BigInteger idmanual, String nombremanual, String ubicacionmanual, String tipomanual, String codigomanual) {
        this.idmanual = idmanual;
        this.nombremanual = nombremanual;
        this.ubicacionmanual = ubicacionmanual;
        this.tipomanual = tipomanual;
        this.codigomanual = codigomanual;
    }

    public BigInteger getIdmanual() {
        return idmanual;
    }

    public void setIdmanual(BigInteger idmanual) {
        this.idmanual = idmanual;
    }

    public String getNombremanual() {
        if (null != nombremanual) {
            return nombremanual.toUpperCase();
        }
        return nombremanual;
    }

    public void setNombremanual(String nombremanual) {
        this.nombremanual = nombremanual.toUpperCase();
    }

    public String getUbicacionmanual() {
        return ubicacionmanual;
    }

    public void setUbicacionmanual(String ubicacionmanual) {
        this.ubicacionmanual = ubicacionmanual;
    }

    public String getTipomanual() {
        return tipomanual;
    }

    public void setTipomanual(String tipomanual) {
        this.tipomanual = tipomanual.toUpperCase();
    }

    public String getCodigomanual() {
        return codigomanual;
    }

    public void setCodigomanual(String codigomanual) {
        this.codigomanual = codigomanual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmanual != null ? idmanual.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manual)) {
            return false;
        }
        Manual other = (Manual) object;
        if ((this.idmanual == null && other.idmanual != null) || (this.idmanual != null && !this.idmanual.equals(other.idmanual))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.Manual[ idmanual=" + idmanual + " ]";
    }

}
