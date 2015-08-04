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
@Table(name = "reservaequipoelemento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaEquipoElemento.findAll", query = "SELECT r FROM ReservaEquipoElemento r"),
    @NamedQuery(name = "ReservaEquipoElemento.findByIdreservaequipoelemento", query = "SELECT r FROM ReservaEquipoElemento r WHERE r.idreservaequipoelemento = :idreservaequipoelemento"),
    @NamedQuery(name = "ReservaEquipoElemento.findByCantidad", query = "SELECT r FROM ReservaEquipoElemento r WHERE r.cantidad = :cantidad")})
public class ReservaEquipoElemento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreservaequipoelemento")
    private BigInteger idreservaequipoelemento;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "reserva", referencedColumnName = "idreserva")
    @ManyToOne(optional = false)
    private Reserva reserva;
    @JoinColumn(name = "equipoelemento", referencedColumnName = "idequipoelemento")
    @ManyToOne(optional = false)
    private EquipoElemento equipoelemento;

    public ReservaEquipoElemento() {
    }

    public ReservaEquipoElemento(BigInteger idreservaequipoelemento) {
        this.idreservaequipoelemento = idreservaequipoelemento;
    }

    public BigInteger getIdreservaequipoelemento() {
        return idreservaequipoelemento;
    }

    public void setIdreservaequipoelemento(BigInteger idreservaequipoelemento) {
        this.idreservaequipoelemento = idreservaequipoelemento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
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
        hash += (idreservaequipoelemento != null ? idreservaequipoelemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaEquipoElemento)) {
            return false;
        }
        ReservaEquipoElemento other = (ReservaEquipoElemento) object;
        if ((this.idreservaequipoelemento == null && other.idreservaequipoelemento != null) || (this.idreservaequipoelemento != null && !this.idreservaequipoelemento.equals(other.idreservaequipoelemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.ReservaEquipoElemento[ idreservaequipoelemento=" + idreservaequipoelemento + " ]";
    }
    
}
