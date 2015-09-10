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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "encargadoporedificio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncargadoPorEdificio.findAll", query = "SELECT e FROM EncargadoPorEdificio e"),
    @NamedQuery(name = "EncargadoPorEdificio.findByIdencargadoporedificio", query = "SELECT e FROM EncargadoPorEdificio e WHERE e.idencargadoporedificio = :idencargadoporedificio"),
    @NamedQuery(name = "EncargadoPorEdificio.findByEstado", query = "SELECT e FROM EncargadoPorEdificio e WHERE e.estado = :estado")})
public class EncargadoPorEdificio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idencargadoporedificio")
    private BigInteger idencargadoporedificio;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "edificio", referencedColumnName = "idedificio")
    @ManyToOne(optional = false)
    private Edificio edificio;
    @JoinColumn(name = "administradoredificio", referencedColumnName = "idadministradoredificio")
    @ManyToOne(optional = false)
    private AdministradorEdificio administradoredificio;
    @Transient
    private String strEstado;

    public EncargadoPorEdificio() {
    }

    public EncargadoPorEdificio(BigInteger idencargadoporedificio) {
        this.idencargadoporedificio = idencargadoporedificio;
    }

    public BigInteger getIdencargadoporedificio() {
        return idencargadoporedificio;
    }

    public void setIdencargadoporedificio(BigInteger idencargadoporedificio) {
        this.idencargadoporedificio = idencargadoporedificio;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    public AdministradorEdificio getAdministradoredificio() {
        return administradoredificio;
    }

    public void setAdministradoredificio(AdministradorEdificio administradoredificio) {
        this.administradoredificio = administradoredificio;
    }

    public String getStrEstado() {
        getEstado();
        if (null != estado) {
            if (estado == true) {
                strEstado = "ACTIVO";
            } else {
                strEstado = "INACTIVO";
            }
        }
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idencargadoporedificio != null ? idencargadoporedificio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncargadoPorEdificio)) {
            return false;
        }
        EncargadoPorEdificio other = (EncargadoPorEdificio) object;
        if ((this.idencargadoporedificio == null && other.idencargadoporedificio != null) || (this.idencargadoporedificio != null && !this.idencargadoporedificio.equals(other.idencargadoporedificio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.EncargadoPorEdificio[ idencargadoporedificio=" + idencargadoporedificio + " ]";
    }

}
