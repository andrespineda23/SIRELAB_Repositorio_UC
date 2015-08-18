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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "asignaturaporplanestudio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsignaturaPorPlanEstudio.findAll", query = "SELECT a FROM AsignaturaPorPlanEstudio a"),
    @NamedQuery(name = "AsignaturaPorPlanEstudio.findByIdasignaturaporplanestudio", query = "SELECT a FROM AsignaturaPorPlanEstudio a WHERE a.idasignaturaporplanestudio = :idasignaturaporplanestudio")})
public class AsignaturaPorPlanEstudio implements Serializable {

    @Column(name = "estado")
    private Boolean estado;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idasignaturaporplanestudio")
    private BigInteger idasignaturaporplanestudio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturaporplanestudio")
    private Collection<ReservaSala> reservaSalaCollection;
    @JoinColumn(name = "planestudio", referencedColumnName = "idplanestudios")
    @ManyToOne(optional = false)
    private PlanEstudios planestudio;
    @JoinColumn(name = "asignatura", referencedColumnName = "idasignatura")
    @ManyToOne(optional = false)
    private Asignatura asignatura;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturaporplanestudio")
    private Collection<GuiaLaboratorio> guiaLaboratorioCollection;
    @Transient
    private String strEstado;

    public AsignaturaPorPlanEstudio() {
    }

    public AsignaturaPorPlanEstudio(BigInteger idasignaturaporplanestudio) {
        this.idasignaturaporplanestudio = idasignaturaporplanestudio;
    }

    public BigInteger getIdasignaturaporplanestudio() {
        return idasignaturaporplanestudio;
    }

    public void setIdasignaturaporplanestudio(BigInteger idasignaturaporplanestudio) {
        this.idasignaturaporplanestudio = idasignaturaporplanestudio;
    }

    @XmlTransient
    public Collection<ReservaSala> getReservaSalaCollection() {
        return reservaSalaCollection;
    }

    public void setReservaSalaCollection(Collection<ReservaSala> reservaSalaCollection) {
        this.reservaSalaCollection = reservaSalaCollection;
    }

    public PlanEstudios getPlanestudio() {
        return planestudio;
    }

    public void setPlanestudio(PlanEstudios planestudio) {
        this.planestudio = planestudio;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    @XmlTransient
    public Collection<GuiaLaboratorio> getGuiaLaboratorioCollection() {
        return guiaLaboratorioCollection;
    }

    public void setGuiaLaboratorioCollection(Collection<GuiaLaboratorio> guiaLaboratorioCollection) {
        this.guiaLaboratorioCollection = guiaLaboratorioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idasignaturaporplanestudio != null ? idasignaturaporplanestudio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsignaturaPorPlanEstudio)) {
            return false;
        }
        AsignaturaPorPlanEstudio other = (AsignaturaPorPlanEstudio) object;
        if ((this.idasignaturaporplanestudio == null && other.idasignaturaporplanestudio != null) || (this.idasignaturaporplanestudio != null && !this.idasignaturaporplanestudio.equals(other.idasignaturaporplanestudio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.AsignaturaPorPlanEstudio[ idasignaturaporplanestudio=" + idasignaturaporplanestudio + " ]";
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

}
