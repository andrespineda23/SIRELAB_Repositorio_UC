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
@Table(name = "horarioatencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorarioAtencion.findAll", query = "SELECT h FROM HorarioAtencion h"),
    @NamedQuery(name = "HorarioAtencion.findByIdhorarioatencion", query = "SELECT h FROM HorarioAtencion h WHERE h.idhorarioatencion = :idhorarioatencion"),
    @NamedQuery(name = "HorarioAtencion.findByCodigohorario", query = "SELECT h FROM HorarioAtencion h WHERE h.codigohorario = :codigohorario"),
    @NamedQuery(name = "HorarioAtencion.findByDescripcionhorario", query = "SELECT h FROM HorarioAtencion h WHERE h.descripcionhorario = :descripcionhorario")})
public class HorarioAtencion implements Serializable {
    @Size(max = 2)
    @Column(name = "horaaperturasabado")
    private String horaaperturasabado;
    @Size(max = 2)
    @Column(name = "horacierresabado")
    private String horacierresabado;
    @Size(max = 2)
    @Column(name = "horaapertura")
    private String horaapertura;
    @Size(max = 2)
    @Column(name = "horacierre")
    private String horacierre;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhorarioatencion")
    private BigInteger idhorarioatencion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigohorario")
    private String codigohorario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcionhorario")
    private String descripcionhorario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "horarioatencion")
    private Collection<Edificio> edificioCollection;

    public HorarioAtencion() {
    }

    public HorarioAtencion(BigInteger idhorarioatencion) {
        this.idhorarioatencion = idhorarioatencion;
    }

    public HorarioAtencion(BigInteger idhorarioatencion, String codigohorario, String descripcionhorario) {
        this.idhorarioatencion = idhorarioatencion;
        this.codigohorario = codigohorario;
        this.descripcionhorario = descripcionhorario;
    }

    public BigInteger getIdhorarioatencion() {
        return idhorarioatencion;
    }

    public void setIdhorarioatencion(BigInteger idhorarioatencion) {
        this.idhorarioatencion = idhorarioatencion;
    }

    public String getCodigohorario() {
        return codigohorario.toUpperCase();
    }

    public void setCodigohorario(String codigohorario) {
        this.codigohorario = codigohorario.toUpperCase();
    }

    public String getDescripcionhorario() {
        return descripcionhorario.toUpperCase();
    }

    public void setDescripcionhorario(String descripcionhorario) {
        this.descripcionhorario = descripcionhorario.toUpperCase();
    }

    @XmlTransient
    public Collection<Edificio> getEdificioCollection() {
        return edificioCollection;
    }

    public void setEdificioCollection(Collection<Edificio> edificioCollection) {
        this.edificioCollection = edificioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhorarioatencion != null ? idhorarioatencion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HorarioAtencion)) {
            return false;
        }
        HorarioAtencion other = (HorarioAtencion) object;
        if ((this.idhorarioatencion == null && other.idhorarioatencion != null) || (this.idhorarioatencion != null && !this.idhorarioatencion.equals(other.idhorarioatencion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.HorarioAtencion[ idhorarioatencion=" + idhorarioatencion + " ]";
    }

    public String getHoraapertura() {
        return horaapertura;
    }

    public void setHoraapertura(String horaapertura) {
        this.horaapertura = horaapertura;
    }

    public String getHoracierre() {
        return horacierre;
    }

    public void setHoracierre(String horacierre) {
        this.horacierre = horacierre;
    }

    public String getHoraaperturasabado() {
        return horaaperturasabado;
    }

    public void setHoraaperturasabado(String horaaperturasabado) {
        this.horaaperturasabado = horaaperturasabado;
    }

    public String getHoracierresabado() {
        return horacierresabado;
    }

    public void setHoracierresabado(String horacierresabado) {
        this.horacierresabado = horacierresabado;
    }
    
}
