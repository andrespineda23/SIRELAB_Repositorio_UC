/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
@Table(name = "hojavidaequipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HojaVidaEquipo.findAll", query = "SELECT h FROM HojaVidaEquipo h"),
    @NamedQuery(name = "HojaVidaEquipo.findByIdhojavidaequipo", query = "SELECT h FROM HojaVidaEquipo h WHERE h.idhojavidaequipo = :idhojavidaequipo"),
    @NamedQuery(name = "HojaVidaEquipo.findByFecharegistro", query = "SELECT h FROM HojaVidaEquipo h WHERE h.fecharegistro = :fecharegistro"),
    @NamedQuery(name = "HojaVidaEquipo.findByFechaevento", query = "SELECT h FROM HojaVidaEquipo h WHERE h.fechaevento = :fechaevento"),
    @NamedQuery(name = "HojaVidaEquipo.findByDetalleevento", query = "SELECT h FROM HojaVidaEquipo h WHERE h.detalleevento = :detalleevento"),
    @NamedQuery(name = "HojaVidaEquipo.findByUsuariomodificacion", query = "SELECT h FROM HojaVidaEquipo h WHERE h.usuariomodificacion = :usuariomodificacion"),
    @NamedQuery(name = "HojaVidaEquipo.findByCosto", query = "SELECT h FROM HojaVidaEquipo h WHERE h.costo = :costo"),
    @NamedQuery(name = "HojaVidaEquipo.findByObservaciones", query = "SELECT h FROM HojaVidaEquipo h WHERE h.observaciones = :observaciones"),
    @NamedQuery(name = "HojaVidaEquipo.findByFechafinevento", query = "SELECT h FROM HojaVidaEquipo h WHERE h.fechafinevento = :fechafinevento")})
public class HojaVidaEquipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhojavidaequipo")
    private BigInteger idhojavidaequipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecharegistro")
    @Temporal(TemporalType.DATE)
    private Date fecharegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaevento")
    @Temporal(TemporalType.DATE)
    private Date fechaevento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 800)
    @Column(name = "detalleevento")
    private String detalleevento;
    @Size(max = 30)
    @Column(name = "usuariomodificacion")
    private String usuariomodificacion;
    @Size(max = 10)
    @Column(name = "costo")
    private String costo;
    @Size(max = 500)
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "fechafinevento")
    @Temporal(TemporalType.DATE)
    private Date fechafinevento;
    @JoinColumn(name = "tipoevento", referencedColumnName = "idtipoevento")
    @ManyToOne(optional = false)
    private TipoEvento tipoevento;
    @JoinColumn(name = "equipoelemento", referencedColumnName = "idequipoelemento")
    @ManyToOne(optional = false)
    private EquipoElemento equipoelemento;

    public HojaVidaEquipo() {
    }

    public HojaVidaEquipo(BigInteger idhojavidaequipo) {
        this.idhojavidaequipo = idhojavidaequipo;
    }

    public HojaVidaEquipo(BigInteger idhojavidaequipo, Date fecharegistro, Date fechaevento, String detalleevento) {
        this.idhojavidaequipo = idhojavidaequipo;
        this.fecharegistro = fecharegistro;
        this.fechaevento = fechaevento;
        this.detalleevento = detalleevento;
    }

    public BigInteger getIdhojavidaequipo() {
        return idhojavidaequipo;
    }

    public void setIdhojavidaequipo(BigInteger idhojavidaequipo) {
        this.idhojavidaequipo = idhojavidaequipo;
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public Date getFechaevento() {
        return fechaevento;
    }

    public void setFechaevento(Date fechaevento) {
        this.fechaevento = fechaevento;
    }

    public String getDetalleevento() {
        return detalleevento;
    }

    public void setDetalleevento(String detalleevento) {
        this.detalleevento = detalleevento;
    }

    public String getUsuariomodificacion() {
        return usuariomodificacion;
    }

    public void setUsuariomodificacion(String usuariomodificacion) {
        this.usuariomodificacion = usuariomodificacion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechafinevento() {
        return fechafinevento;
    }

    public void setFechafinevento(Date fechafinevento) {
        this.fechafinevento = fechafinevento;
    }

    public TipoEvento getTipoevento() {
        return tipoevento;
    }

    public void setTipoevento(TipoEvento tipoevento) {
        this.tipoevento = tipoevento;
    }

    public EquipoElemento getEquipoelemento() {
        return equipoelemento;
    }

    public void setEquipoelemento(EquipoElemento equipoelemento) {
        this.equipoelemento = equipoelemento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhojavidaequipo != null ? idhojavidaequipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HojaVidaEquipo)) {
            return false;
        }
        HojaVidaEquipo other = (HojaVidaEquipo) object;
        if ((this.idhojavidaequipo == null && other.idhojavidaequipo != null) || (this.idhojavidaequipo != null && !this.idhojavidaequipo.equals(other.idhojavidaequipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.HojaVidaEquipo[ idhojavidaequipo=" + idhojavidaequipo + " ]";
    }

}
