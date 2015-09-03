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
@Table(name = "estadoreserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoReserva.findAll", query = "SELECT e FROM EstadoReserva e"),
    @NamedQuery(name = "EstadoReserva.findByIdestadoreserva", query = "SELECT e FROM EstadoReserva e WHERE e.idestadoreserva = :idestadoreserva"),
    @NamedQuery(name = "EstadoReserva.findByNombreestadoreserva", query = "SELECT e FROM EstadoReserva e WHERE e.nombreestadoreserva = :nombreestadoreserva")})
public class EstadoReserva implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoreserva")
    private Collection<Reserva> reservaCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idestadoreserva")
    private BigInteger idestadoreserva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreestadoreserva")
    private String nombreestadoreserva;

    public EstadoReserva() {
    }

    public EstadoReserva(BigInteger idestadoreserva) {
        this.idestadoreserva = idestadoreserva;
    }

    public EstadoReserva(BigInteger idestadoreserva, String nombreestadoreserva) {
        this.idestadoreserva = idestadoreserva;
        this.nombreestadoreserva = nombreestadoreserva;
    }

    public BigInteger getIdestadoreserva() {
        return idestadoreserva;
    }

    public void setIdestadoreserva(BigInteger idestadoreserva) {
        this.idestadoreserva = idestadoreserva;
    }

    public String getNombreestadoreserva() {
        return nombreestadoreserva.toUpperCase();
    }

    public void setNombreestadoreserva(String nombreestadoreserva) {
        this.nombreestadoreserva = nombreestadoreserva.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestadoreserva != null ? idestadoreserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoReserva)) {
            return false;
        }
        EstadoReserva other = (EstadoReserva) object;
        if ((this.idestadoreserva == null && other.idestadoreserva != null) || (this.idestadoreserva != null && !this.idestadoreserva.equals(other.idestadoreserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.EstadoReserva[ idestadoreserva=" + idestadoreserva + " ]";
    }

    @XmlTransient
    public Collection<Reserva> getReservaCollection() {
        return reservaCollection;
    }

    public void setReservaCollection(Collection<Reserva> reservaCollection) {
        this.reservaCollection = reservaCollection;
    }

}
