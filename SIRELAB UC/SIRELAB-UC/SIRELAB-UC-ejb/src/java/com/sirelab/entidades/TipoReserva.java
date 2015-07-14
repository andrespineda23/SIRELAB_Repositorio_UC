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
 * @author ELECTRONICA
 */
@Entity
@Table(name = "tiporeserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoReserva.findAll", query = "SELECT t FROM TipoReserva t"),
    @NamedQuery(name = "TipoReserva.findByIdtiporeserva", query = "SELECT t FROM TipoReserva t WHERE t.idtiporeserva = :idtiporeserva"),
    @NamedQuery(name = "TipoReserva.findByNombretiporeserva", query = "SELECT t FROM TipoReserva t WHERE t.nombretiporeserva = :nombretiporeserva")})
public class TipoReserva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtiporeserva")
    private BigInteger idtiporeserva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombretiporeserva")
    private String nombretiporeserva;

    public TipoReserva() {
    }

    public TipoReserva(BigInteger idtiporeserva) {
        this.idtiporeserva = idtiporeserva;
    }

    public TipoReserva(BigInteger idtiporeserva, String nombretiporeserva) {
        this.idtiporeserva = idtiporeserva;
        this.nombretiporeserva = nombretiporeserva;
    }

    public BigInteger getIdtiporeserva() {
        return idtiporeserva;
    }

    public void setIdtiporeserva(BigInteger idtiporeserva) {
        this.idtiporeserva = idtiporeserva;
    }

    public String getNombretiporeserva() {
        return nombretiporeserva.toUpperCase();
    }

    public void setNombretiporeserva(String nombretiporeserva) {
        this.nombretiporeserva = nombretiporeserva.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtiporeserva != null ? idtiporeserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoReserva)) {
            return false;
        }
        TipoReserva other = (TipoReserva) object;
        if ((this.idtiporeserva == null && other.idtiporeserva != null) || (this.idtiporeserva != null && !this.idtiporeserva.equals(other.idtiporeserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.TipoReserva[ idtiporeserva=" + idtiporeserva + " ]";
    }
    
}
