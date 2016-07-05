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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "planestudios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanEstudios.findAll", query = "SELECT p FROM PlanEstudios p"),
    @NamedQuery(name = "PlanEstudios.findByIdplanestudios", query = "SELECT p FROM PlanEstudios p WHERE p.idplanestudios = :idplanestudios"),
    @NamedQuery(name = "PlanEstudios.findByCodigoplanestudio", query = "SELECT p FROM PlanEstudios p WHERE p.codigoplanestudio = :codigoplanestudio"),
    @NamedQuery(name = "PlanEstudios.findByNombreplanestudio", query = "SELECT p FROM PlanEstudios p WHERE p.nombreplanestudio = :nombreplanestudio"),
    @NamedQuery(name = "PlanEstudios.findByEstado", query = "SELECT p FROM PlanEstudios p WHERE p.estado = :estado")})
public class PlanEstudios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idplanestudios")
    private BigInteger idplanestudios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigoplanestudio")
    private String codigoplanestudio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "nombreplanestudio")
    private String nombreplanestudio;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "carrera", referencedColumnName = "idcarrera")
    @ManyToOne(optional = false)
    private Carrera carrera;
    @Transient
    private String strEstado;
    @Transient
    private String strNombreEstado;
    @Transient
    private String strNombreCodigo;

    public PlanEstudios() {
    }

    public PlanEstudios(BigInteger idplanestudios) {
        this.idplanestudios = idplanestudios;
    }

    public PlanEstudios(BigInteger idplanestudios, String codigoplanestudio, String nombreplanestudio) {
        this.idplanestudios = idplanestudios;
        this.codigoplanestudio = codigoplanestudio;
        this.nombreplanestudio = nombreplanestudio;
    }

    public BigInteger getIdplanestudios() {
        return idplanestudios;
    }

    public void setIdplanestudios(BigInteger idplanestudios) {
        this.idplanestudios = idplanestudios;
    }

    public String getCodigoplanestudio() {
        if (null != codigoplanestudio) {
            return codigoplanestudio.toUpperCase();
        }
        return codigoplanestudio;
    }

    public void setCodigoplanestudio(String codigoplanestudio) {
        this.codigoplanestudio = codigoplanestudio;
    }

    public String getNombreplanestudio() {
        if (null != nombreplanestudio) {
            return nombreplanestudio.toUpperCase();
        }
        return nombreplanestudio;
    }

    public void setNombreplanestudio(String nombreplanestudio) {
        this.nombreplanestudio = nombreplanestudio;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idplanestudios != null ? idplanestudios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanEstudios)) {
            return false;
        }
        PlanEstudios other = (PlanEstudios) object;
        if ((this.idplanestudios == null && other.idplanestudios != null) || (this.idplanestudios != null && !this.idplanestudios.equals(other.idplanestudios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.PlanEstudios[ idplanestudios=" + idplanestudios + " ]";
    }

    public String getStrNombreEstado() {
        getStrEstado();
        getCodigoplanestudio();
        strNombreEstado = codigoplanestudio + " - " + strEstado;
        return strNombreEstado;
    }

    public void setStrNombreEstado(String strNombreEstado) {
        this.strNombreEstado = strNombreEstado;
    }

    public String getStrNombreCodigo() {
        getCodigoplanestudio();
        getNombreplanestudio();
        strNombreCodigo = nombreplanestudio + " - " + codigoplanestudio;
        return strNombreCodigo;
    }

    public void setStrNombreCodigo(String strNombreCodigo) {
        this.strNombreCodigo = strNombreCodigo;
    }

}
