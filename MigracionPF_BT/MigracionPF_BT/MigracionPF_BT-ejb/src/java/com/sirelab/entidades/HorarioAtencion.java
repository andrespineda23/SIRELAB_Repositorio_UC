/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "horarioatencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorarioAtencion.findAll", query = "SELECT h FROM HorarioAtencion h"),
    @NamedQuery(name = "HorarioAtencion.findByIdhorarioatencion", query = "SELECT h FROM HorarioAtencion h WHERE h.idhorarioatencion = :idhorarioatencion"),
    @NamedQuery(name = "HorarioAtencion.findByCodigohorario", query = "SELECT h FROM HorarioAtencion h WHERE h.codigohorario = :codigohorario"),
    @NamedQuery(name = "HorarioAtencion.findByFechainicio", query = "SELECT h FROM HorarioAtencion h WHERE h.fechainicio = :fechainicio"),
    @NamedQuery(name = "HorarioAtencion.findByFechafinal", query = "SELECT h FROM HorarioAtencion h WHERE h.fechafinal = :fechafinal")})
public class HorarioAtencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhorarioatencion")
    private Long idhorarioatencion;
    @Size(max = 45)
    @Column(name = "codigohorario")
    private String codigohorario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechafinal")
    @Temporal(TemporalType.DATE)
    private Date fechafinal;

    public HorarioAtencion() {
    }

    public HorarioAtencion(Long idhorarioatencion) {
        this.idhorarioatencion = idhorarioatencion;
    }

    public HorarioAtencion(Long idhorarioatencion, Date fechainicio, Date fechafinal) {
        this.idhorarioatencion = idhorarioatencion;
        this.fechainicio = fechainicio;
        this.fechafinal = fechafinal;
    }

    public Long getIdhorarioatencion() {
        return idhorarioatencion;
    }

    public void setIdhorarioatencion(Long idhorarioatencion) {
        this.idhorarioatencion = idhorarioatencion;
    }

    public String getCodigohorario() {
        return codigohorario;
    }

    public void setCodigohorario(String codigohorario) {
        this.codigohorario = codigohorario;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
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
    
}
