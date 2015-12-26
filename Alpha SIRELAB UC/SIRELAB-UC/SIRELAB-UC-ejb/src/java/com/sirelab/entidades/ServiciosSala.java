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
 * @author AndresPineda
 */
@Entity
@Table(name = "serviciossala")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServiciosSala.findAll", query = "SELECT s FROM ServiciosSala s"),
    @NamedQuery(name = "ServiciosSala.findByIdserviciossala", query = "SELECT s FROM ServiciosSala s WHERE s.idserviciossala = :idserviciossala"),
    @NamedQuery(name = "ServiciosSala.findByNombreservicio", query = "SELECT s FROM ServiciosSala s WHERE s.nombreservicio = :nombreservicio"),
    @NamedQuery(name = "ServiciosSala.findByCodigoservicio", query = "SELECT s FROM ServiciosSala s WHERE s.codigoservicio = :codigoservicio"),
    @NamedQuery(name = "ServiciosSala.findByCostoservicio", query = "SELECT s FROM ServiciosSala s WHERE s.costoservicio = :costoservicio"),
    @NamedQuery(name = "ServiciosSala.findByEstado", query = "SELECT s FROM ServiciosSala s WHERE s.estado = :estado")})
public class ServiciosSala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idserviciossala")
    private BigInteger idserviciossala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombreservicio")
    private String nombreservicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigoservicio")
    private String codigoservicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costoservicio")
    private int costoservicio;
    @Column(name = "estado")
    private Boolean estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviciosala")
    private Collection<SalaLaboratorioxServicios> salaLaboratorioxServiciosCollection;

    public ServiciosSala() {
    }

    public ServiciosSala(BigInteger idserviciossala) {
        this.idserviciossala = idserviciossala;
    }

    public ServiciosSala(BigInteger idserviciossala, String nombreservicio, String codigoservicio, int costoservicio) {
        this.idserviciossala = idserviciossala;
        this.nombreservicio = nombreservicio;
        this.codigoservicio = codigoservicio;
        this.costoservicio = costoservicio;
    }

    public BigInteger getIdserviciossala() {
        return idserviciossala;
    }

    public void setIdserviciossala(BigInteger idserviciossala) {
        this.idserviciossala = idserviciossala;
    }

    public String getNombreservicio() {
        return nombreservicio;
    }

    public void setNombreservicio(String nombreservicio) {
        this.nombreservicio = nombreservicio;
    }

    public String getCodigoservicio() {
        return codigoservicio;
    }

    public void setCodigoservicio(String codigoservicio) {
        this.codigoservicio = codigoservicio;
    }

    public int getCostoservicio() {
        return costoservicio;
    }

    public void setCostoservicio(int costoservicio) {
        this.costoservicio = costoservicio;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @XmlTransient
    public Collection<SalaLaboratorioxServicios> getSalaLaboratorioxServiciosCollection() {
        return salaLaboratorioxServiciosCollection;
    }

    public void setSalaLaboratorioxServiciosCollection(Collection<SalaLaboratorioxServicios> salaLaboratorioxServiciosCollection) {
        this.salaLaboratorioxServiciosCollection = salaLaboratorioxServiciosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idserviciossala != null ? idserviciossala.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServiciosSala)) {
            return false;
        }
        ServiciosSala other = (ServiciosSala) object;
        if ((this.idserviciossala == null && other.idserviciossala != null) || (this.idserviciossala != null && !this.idserviciossala.equals(other.idserviciossala))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.ServiciosSala[ idserviciossala=" + idserviciossala + " ]";
    }

}
