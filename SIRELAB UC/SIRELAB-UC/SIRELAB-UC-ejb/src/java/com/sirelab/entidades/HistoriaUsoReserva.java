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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "historiausoreserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoriaUsoReserva.findAll", query = "SELECT h FROM HistoriaUsoReserva h"),
    @NamedQuery(name = "HistoriaUsoReserva.findByIdhistoriausoreserva", query = "SELECT h FROM HistoriaUsoReserva h WHERE h.idhistoriausoreserva = :idhistoriausoreserva"),
    @NamedQuery(name = "HistoriaUsoReserva.findByHorainicio", query = "SELECT h FROM HistoriaUsoReserva h WHERE h.horainicio = :horainicio"),
    @NamedQuery(name = "HistoriaUsoReserva.findByHorafin", query = "SELECT h FROM HistoriaUsoReserva h WHERE h.horafin = :horafin"),
    @NamedQuery(name = "HistoriaUsoReserva.findByReservarealizada", query = "SELECT h FROM HistoriaUsoReserva h WHERE h.reservarealizada = :reservarealizada")})
public class HistoriaUsoReserva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhistoriausoreserva")
    private BigInteger idhistoriausoreserva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "horainicio")
    private String horainicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "horafin")
    private String horafin;
    @Column(name = "reservarealizada")
    private Boolean reservarealizada;
    @JoinColumn(name = "reserva", referencedColumnName = "idreserva")
    @ManyToOne(optional = false)
    private Reserva reserva;

    public HistoriaUsoReserva() {
    }

    public HistoriaUsoReserva(BigInteger idhistoriausoreserva) {
        this.idhistoriausoreserva = idhistoriausoreserva;
    }

    public HistoriaUsoReserva(BigInteger idhistoriausoreserva, String horainicio, String horafin) {
        this.idhistoriausoreserva = idhistoriausoreserva;
        this.horainicio = horainicio;
        this.horafin = horafin;
    }

    public BigInteger getIdhistoriausoreserva() {
        return idhistoriausoreserva;
    }

    public void setIdhistoriausoreserva(BigInteger idhistoriausoreserva) {
        this.idhistoriausoreserva = idhistoriausoreserva;
    }

    public String getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public String getHorafin() {
        return horafin;
    }

    public void setHorafin(String horafin) {
        this.horafin = horafin;
    }

    public Boolean getReservarealizada() {
        return reservarealizada;
    }

    public void setReservarealizada(Boolean reservarealizada) {
        this.reservarealizada = reservarealizada;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhistoriausoreserva != null ? idhistoriausoreserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoriaUsoReserva)) {
            return false;
        }
        HistoriaUsoReserva other = (HistoriaUsoReserva) object;
        if ((this.idhistoriausoreserva == null && other.idhistoriausoreserva != null) || (this.idhistoriausoreserva != null && !this.idhistoriausoreserva.equals(other.idhistoriausoreserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.HistoriaUsoReserva[ idhistoriausoreserva=" + idhistoriausoreserva + " ]";
    }
    
}
