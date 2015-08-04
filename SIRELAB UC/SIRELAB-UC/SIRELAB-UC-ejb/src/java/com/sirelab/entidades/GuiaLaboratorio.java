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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "guialaboratorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GuiaLaboratorio.findAll", query = "SELECT g FROM GuiaLaboratorio g"),
    @NamedQuery(name = "GuiaLaboratorio.findByIdguialaboratorio", query = "SELECT g FROM GuiaLaboratorio g WHERE g.idguialaboratorio = :idguialaboratorio"),
    @NamedQuery(name = "GuiaLaboratorio.findByNombreguia", query = "SELECT g FROM GuiaLaboratorio g WHERE g.nombreguia = :nombreguia"),
    @NamedQuery(name = "GuiaLaboratorio.findByDescripcion", query = "SELECT g FROM GuiaLaboratorio g WHERE g.descripcion = :descripcion"),
    @NamedQuery(name = "GuiaLaboratorio.findByUbicacionguia", query = "SELECT g FROM GuiaLaboratorio g WHERE g.ubicacionguia = :ubicacionguia")})
public class GuiaLaboratorio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idguialaboratorio")
    private BigInteger idguialaboratorio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombreguia")
    private String nombreguia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "ubicacionguia")
    private String ubicacionguia;
    @JoinColumn(name = "asignatura", referencedColumnName = "idasignatura")
    @ManyToOne(optional = false)
    private Asignatura asignatura;

    public GuiaLaboratorio() {
    }

    public GuiaLaboratorio(BigInteger idguialaboratorio) {
        this.idguialaboratorio = idguialaboratorio;
    }

    public GuiaLaboratorio(BigInteger idguialaboratorio, String nombreguia, String descripcion, String ubicacionguia) {
        this.idguialaboratorio = idguialaboratorio;
        this.nombreguia = nombreguia;
        this.descripcion = descripcion;
        this.ubicacionguia = ubicacionguia;
    }

    public BigInteger getIdguialaboratorio() {
        return idguialaboratorio;
    }

    public void setIdguialaboratorio(BigInteger idguialaboratorio) {
        this.idguialaboratorio = idguialaboratorio;
    }

    public String getNombreguia() {
        return nombreguia;
    }

    public void setNombreguia(String nombreguia) {
        this.nombreguia = nombreguia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacionguia() {
        return ubicacionguia;
    }

    public void setUbicacionguia(String ubicacionguia) {
        this.ubicacionguia = ubicacionguia;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idguialaboratorio != null ? idguialaboratorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GuiaLaboratorio)) {
            return false;
        }
        GuiaLaboratorio other = (GuiaLaboratorio) object;
        if ((this.idguialaboratorio == null && other.idguialaboratorio != null) || (this.idguialaboratorio != null && !this.idguialaboratorio.equals(other.idguialaboratorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.GuiaLaboratorio[ idguialaboratorio=" + idguialaboratorio + " ]";
    }
    
}
