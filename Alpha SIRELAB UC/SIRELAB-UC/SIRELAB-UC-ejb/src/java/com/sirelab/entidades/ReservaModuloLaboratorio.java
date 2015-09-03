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
@Table(name = "reservamodulolaboratorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaModuloLaboratorio.findAll", query = "SELECT r FROM ReservaModuloLaboratorio r"),
    @NamedQuery(name = "ReservaModuloLaboratorio.findByIdreservamodulolaboratorio", query = "SELECT r FROM ReservaModuloLaboratorio r WHERE r.idreservamodulolaboratorio = :idreservamodulolaboratorio")})
public class ReservaModuloLaboratorio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreservamodulolaboratorio")
    private BigInteger idreservamodulolaboratorio;
    @JoinColumn(name = "reserva", referencedColumnName = "idreserva")
    @ManyToOne(optional = false)
    private Reserva reserva;
    @JoinColumn(name = "modulolaboratorio", referencedColumnName = "idmodulolaboratorio")
    @ManyToOne(optional = false)
    private ModuloLaboratorio modulolaboratorio;

    public ReservaModuloLaboratorio() {
    }

    public ReservaModuloLaboratorio(BigInteger idreservamodulolaboratorio) {
        this.idreservamodulolaboratorio = idreservamodulolaboratorio;
    }

    public BigInteger getIdreservamodulolaboratorio() {
        return idreservamodulolaboratorio;
    }

    public void setIdreservamodulolaboratorio(BigInteger idreservamodulolaboratorio) {
        this.idreservamodulolaboratorio = idreservamodulolaboratorio;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public ModuloLaboratorio getModulolaboratorio() {
        return modulolaboratorio;
    }

    public void setModulolaboratorio(ModuloLaboratorio modulolaboratorio) {
        this.modulolaboratorio = modulolaboratorio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreservamodulolaboratorio != null ? idreservamodulolaboratorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaModuloLaboratorio)) {
            return false;
        }
        ReservaModuloLaboratorio other = (ReservaModuloLaboratorio) object;
        if ((this.idreservamodulolaboratorio == null && other.idreservamodulolaboratorio != null) || (this.idreservamodulolaboratorio != null && !this.idreservamodulolaboratorio.equals(other.idreservamodulolaboratorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.ReservaModuloLaboratorio[ idreservamodulolaboratorio=" + idreservamodulolaboratorio + " ]";
    }
    
}
