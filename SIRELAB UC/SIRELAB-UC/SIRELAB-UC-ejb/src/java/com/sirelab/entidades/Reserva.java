/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r"),
    @NamedQuery(name = "Reserva.findByIdreserva", query = "SELECT r FROM Reserva r WHERE r.idreserva = :idreserva"),
    @NamedQuery(name = "Reserva.findByFechareserva", query = "SELECT r FROM Reserva r WHERE r.fechareserva = :fechareserva"),
    @NamedQuery(name = "Reserva.findByHorainicio", query = "SELECT r FROM Reserva r WHERE r.horainicio = :horainicio"),
    @NamedQuery(name = "Reserva.findByHorafin", query = "SELECT r FROM Reserva r WHERE r.horafin = :horafin"),
    @NamedQuery(name = "Reserva.findByNumeroreserva", query = "SELECT r FROM Reserva r WHERE r.numeroreserva = :numeroreserva"),
    @NamedQuery(name = "Reserva.findByValorreserva", query = "SELECT r FROM Reserva r WHERE r.valorreserva = :valorreserva")})
public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreserva")
    private BigInteger idreserva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechareserva")
    @Temporal(TemporalType.DATE)
    private Date fechareserva;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numeroreserva")
    private String numeroreserva;
    @Column(name = "valorreserva")
    private Integer valorreserva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reserva")
    private Collection<ReservaModuloLaboratorio> reservaModuloLaboratorioCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reserva")
    private Collection<ReservaSala> reservaSalaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reserva")
    private Collection<HistoriaUsoReserva> historiaUsoReservaCollection;
    @JoinColumn(name = "tiporeserva", referencedColumnName = "idtiporeserva")
    @ManyToOne(optional = false)
    private TipoReserva tiporeserva;
    @JoinColumn(name = "persona", referencedColumnName = "idpersona")
    @ManyToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "periodoacademico", referencedColumnName = "idperiodoacademico")
    @ManyToOne(optional = false)
    private PeriodoAcademico periodoacademico;
    @JoinColumn(name = "estadoreserva", referencedColumnName = "idestadoreserva")
    @ManyToOne(optional = false)
    private EstadoReserva estadoreserva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reserva")
    private Collection<ReservaEquipoElemento> reservaEquipoElementoCollection;

    public Reserva() {
    }

    public Reserva(BigInteger idreserva) {
        this.idreserva = idreserva;
    }

    public Reserva(BigInteger idreserva, Date fechareserva, String horainicio, String horafin, String numeroreserva) {
        this.idreserva = idreserva;
        this.fechareserva = fechareserva;
        this.horainicio = horainicio;
        this.horafin = horafin;
        this.numeroreserva = numeroreserva;
    }

    public BigInteger getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(BigInteger idreserva) {
        this.idreserva = idreserva;
    }

    public Date getFechareserva() {
        return fechareserva;
    }

    public void setFechareserva(Date fechareserva) {
        this.fechareserva = fechareserva;
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

    public String getNumeroreserva() {
        return numeroreserva;
    }

    public void setNumeroreserva(String numeroreserva) {
        this.numeroreserva = numeroreserva;
    }

    public Integer getValorreserva() {
        return valorreserva;
    }

    public void setValorreserva(Integer valorreserva) {
        this.valorreserva = valorreserva;
    }

    @XmlTransient
    public Collection<ReservaModuloLaboratorio> getReservaModuloLaboratorioCollection() {
        return reservaModuloLaboratorioCollection;
    }

    public void setReservaModuloLaboratorioCollection(Collection<ReservaModuloLaboratorio> reservaModuloLaboratorioCollection) {
        this.reservaModuloLaboratorioCollection = reservaModuloLaboratorioCollection;
    }

    @XmlTransient
    public Collection<ReservaSala> getReservaSalaCollection() {
        return reservaSalaCollection;
    }

    public void setReservaSalaCollection(Collection<ReservaSala> reservaSalaCollection) {
        this.reservaSalaCollection = reservaSalaCollection;
    }

    @XmlTransient
    public Collection<HistoriaUsoReserva> getHistoriaUsoReservaCollection() {
        return historiaUsoReservaCollection;
    }

    public void setHistoriaUsoReservaCollection(Collection<HistoriaUsoReserva> historiaUsoReservaCollection) {
        this.historiaUsoReservaCollection = historiaUsoReservaCollection;
    }

    public TipoReserva getTiporeserva() {
        return tiporeserva;
    }

    public void setTiporeserva(TipoReserva tiporeserva) {
        this.tiporeserva = tiporeserva;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public PeriodoAcademico getPeriodoacademico() {
        return periodoacademico;
    }

    public void setPeriodoacademico(PeriodoAcademico periodoacademico) {
        this.periodoacademico = periodoacademico;
    }

    public EstadoReserva getEstadoreserva() {
        return estadoreserva;
    }

    public void setEstadoreserva(EstadoReserva estadoreserva) {
        this.estadoreserva = estadoreserva;
    }

    @XmlTransient
    public Collection<ReservaEquipoElemento> getReservaEquipoElementoCollection() {
        return reservaEquipoElementoCollection;
    }

    public void setReservaEquipoElementoCollection(Collection<ReservaEquipoElemento> reservaEquipoElementoCollection) {
        this.reservaEquipoElementoCollection = reservaEquipoElementoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreserva != null ? idreserva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.idreserva == null && other.idreserva != null) || (this.idreserva != null && !this.idreserva.equals(other.idreserva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.Reserva[ idreserva=" + idreserva + " ]";
    }
    
}
