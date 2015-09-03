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
@Table(name = "manualhasequipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ManualHasEquipo.findAll", query = "SELECT m FROM ManualHasEquipo m"),
    @NamedQuery(name = "ManualHasEquipo.findByIdmanualhasequipo", query = "SELECT m FROM ManualHasEquipo m WHERE m.idmanualhasequipo = :idmanualhasequipo")})
public class ManualHasEquipo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmanualhasequipo")
    private BigInteger idmanualhasequipo;
    @JoinColumn(name = "manual", referencedColumnName = "idmanual")
    @ManyToOne(optional = false)
    private Manual manual;
    @JoinColumn(name = "equipoelemento", referencedColumnName = "idequipoelemento")
    @ManyToOne(optional = false)
    private EquipoElemento equipoelemento;

    public ManualHasEquipo() {
    }

    public ManualHasEquipo(BigInteger idmanualhasequipo) {
        this.idmanualhasequipo = idmanualhasequipo;
    }

    public BigInteger getIdmanualhasequipo() {
        return idmanualhasequipo;
    }

    public void setIdmanualhasequipo(BigInteger idmanualhasequipo) {
        this.idmanualhasequipo = idmanualhasequipo;
    }

    public Manual getManual() {
        return manual;
    }

    public void setManual(Manual manual) {
        this.manual = manual;
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
        hash += (idmanualhasequipo != null ? idmanualhasequipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManualHasEquipo)) {
            return false;
        }
        ManualHasEquipo other = (ManualHasEquipo) object;
        if ((this.idmanualhasequipo == null && other.idmanualhasequipo != null) || (this.idmanualhasequipo != null && !this.idmanualhasequipo.equals(other.idmanualhasequipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.ManualHasEquipo[ idmanualhasequipo=" + idmanualhasequipo + " ]";
    }
    
}
