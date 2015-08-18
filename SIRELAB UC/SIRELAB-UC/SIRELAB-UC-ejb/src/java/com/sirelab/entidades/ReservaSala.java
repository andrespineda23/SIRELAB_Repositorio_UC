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
@Table(name = "reservasala")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaSala.findAll", query = "SELECT r FROM ReservaSala r"),
    @NamedQuery(name = "ReservaSala.findByIdreservasala", query = "SELECT r FROM ReservaSala r WHERE r.idreservasala = :idreservasala"),
    @NamedQuery(name = "ReservaSala.findByTiporeservasala", query = "SELECT r FROM ReservaSala r WHERE r.tiporeservasala = :tiporeservasala")})
public class ReservaSala implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreservasala")
    private BigInteger idreservasala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tiporeservasala")
    private String tiporeservasala;
    @JoinColumn(name = "salalaboratorio", referencedColumnName = "idsalalaboratorio")
    @ManyToOne(optional = false)
    private SalaLaboratorio salalaboratorio;
    @JoinColumn(name = "reserva", referencedColumnName = "idreserva")
    @ManyToOne(optional = false)
    private Reserva reserva;
    @JoinColumn(name = "guialaboratorio", referencedColumnName = "idguialaboratorio")
    @ManyToOne(optional = false)
    private GuiaLaboratorio guialaboratorio;
    @JoinColumn(name = "asignaturaporplanestudio", referencedColumnName = "idasignaturaporplanestudio")
    @ManyToOne(optional = false)
    private AsignaturaPorPlanEstudio asignaturaporplanestudio;

    public ReservaSala() {
    }

    public ReservaSala(BigInteger idreservasala) {
        this.idreservasala = idreservasala;
    }

    public BigInteger getIdreservasala() {
        return idreservasala;
    }

    public void setIdreservasala(BigInteger idreservasala) {
        this.idreservasala = idreservasala;
    }

    public String getTiporeservasala() {
        return tiporeservasala;
    }

    public void setTiporeservasala(String tiporeservasala) {
        this.tiporeservasala = tiporeservasala;
    }

    public SalaLaboratorio getSalalaboratorio() {
        return salalaboratorio;
    }

    public void setSalalaboratorio(SalaLaboratorio salalaboratorio) {
        this.salalaboratorio = salalaboratorio;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public GuiaLaboratorio getGuialaboratorio() {
        return guialaboratorio;
    }

    public void setGuialaboratorio(GuiaLaboratorio guialaboratorio) {
        this.guialaboratorio = guialaboratorio;
    }

    public AsignaturaPorPlanEstudio getAsignaturaporplanestudio() {
        return asignaturaporplanestudio;
    }

    public void setAsignaturaporplanestudio(AsignaturaPorPlanEstudio asignaturaporplanestudio) {
        this.asignaturaporplanestudio = asignaturaporplanestudio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreservasala != null ? idreservasala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaSala)) {
            return false;
        }
        ReservaSala other = (ReservaSala) object;
        if ((this.idreservasala == null && other.idreservasala != null) || (this.idreservasala != null && !this.idreservasala.equals(other.idreservasala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.ReservaSala[ idreservasala=" + idreservasala + " ]";
    }
    
}
