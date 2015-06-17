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
@Table(name = "tipoevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoEvento.findAll", query = "SELECT t FROM TipoEvento t"),
    @NamedQuery(name = "TipoEvento.findByIdtipoevento", query = "SELECT t FROM TipoEvento t WHERE t.idtipoevento = :idtipoevento"),
    @NamedQuery(name = "TipoEvento.findByDetalleevento", query = "SELECT t FROM TipoEvento t WHERE t.detalleevento = :detalleevento")})
public class TipoEvento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoevento")
    private BigInteger idtipoevento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "detalleevento")
    private String detalleevento;

    public TipoEvento() {
    }

    public TipoEvento(BigInteger idtipoevento) {
        this.idtipoevento = idtipoevento;
    }

    public TipoEvento(BigInteger idtipoevento, String detalleevento) {
        this.idtipoevento = idtipoevento;
        this.detalleevento = detalleevento;
    }

    public BigInteger getIdtipoevento() {
        return idtipoevento;
    }

    public void setIdtipoevento(BigInteger idtipoevento) {
        this.idtipoevento = idtipoevento;
    }

    public String getDetalleevento() {
        return detalleevento.toUpperCase();
    }

    public void setDetalleevento(String detalleevento) {
        this.detalleevento = detalleevento.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoevento != null ? idtipoevento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoEvento)) {
            return false;
        }
        TipoEvento other = (TipoEvento) object;
        if ((this.idtipoevento == null && other.idtipoevento != null) || (this.idtipoevento != null && !this.idtipoevento.equals(other.idtipoevento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.TipoEvento[ idtipoevento=" + idtipoevento + " ]";
    }
    
}
