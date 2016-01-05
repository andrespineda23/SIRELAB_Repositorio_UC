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
@Table(name = "entidadexterna")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntidadExterna.findAll", query = "SELECT e FROM EntidadExterna e"),
    @NamedQuery(name = "EntidadExterna.findByIdentidadexterna", query = "SELECT e FROM EntidadExterna e WHERE e.identidadexterna = :identidadexterna"),
    @NamedQuery(name = "EntidadExterna.findByEstado", query = "SELECT e FROM EntidadExterna e WHERE e.estado = :estado"),
    @NamedQuery(name = "EntidadExterna.findByNombreentidad", query = "SELECT e FROM EntidadExterna e WHERE e.nombreentidad = :nombreentidad"),
    @NamedQuery(name = "EntidadExterna.findByEmailentidad", query = "SELECT e FROM EntidadExterna e WHERE e.emailentidad = :emailentidad"),
    @NamedQuery(name = "EntidadExterna.findByTelefonoentidad", query = "SELECT e FROM EntidadExterna e WHERE e.telefonoentidad = :telefonoentidad"),
    @NamedQuery(name = "EntidadExterna.findByDireccionentidad", query = "SELECT e FROM EntidadExterna e WHERE e.direccionentidad = :direccionentidad"),
    @NamedQuery(name = "EntidadExterna.findByIdentificacion", query = "SELECT e FROM EntidadExterna e WHERE e.identificacion = :identificacion")})
public class EntidadExterna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "identidadexterna")
    private BigInteger identidadexterna;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreentidad")
    private String nombreentidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "emailentidad")
    private String emailentidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "telefonoentidad")
    private String telefonoentidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "direccionentidad")
    private String direccionentidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @JoinColumn(name = "sector", referencedColumnName = "idsectorentidad")
    @ManyToOne(optional = false)
    private SectorEntidad sector;
    @Transient
    private String strEstado;

    public String getStrEstado() {
        getEstado();
        if (estado == true) {
            strEstado = "ACTIVO";
        } else {
            strEstado = "INACTIVO";
        }
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }

    public EntidadExterna() {
    }

    public EntidadExterna(BigInteger identidadexterna) {
        this.identidadexterna = identidadexterna;
    }

    public EntidadExterna(BigInteger identidadexterna, boolean estado, String nombreentidad, String emailentidad, String telefonoentidad, String direccionentidad, String identificacion) {
        this.identidadexterna = identidadexterna;
        this.estado = estado;
        this.nombreentidad = nombreentidad;
        this.emailentidad = emailentidad;
        this.telefonoentidad = telefonoentidad;
        this.direccionentidad = direccionentidad;
        this.identificacion = identificacion;
    }

    public BigInteger getIdentidadexterna() {
        return identidadexterna;
    }

    public void setIdentidadexterna(BigInteger identidadexterna) {
        this.identidadexterna = identidadexterna;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombreentidad() {
        if(null != nombreentidad){
            return nombreentidad.toUpperCase();
        }
        return nombreentidad;
    }

    public void setNombreentidad(String nombreentidad) {
        this.nombreentidad = nombreentidad.toUpperCase();
    }

    public String getEmailentidad() {
        return emailentidad;
    }

    public void setEmailentidad(String emailentidad) {
        this.emailentidad = emailentidad;
    }

    public String getTelefonoentidad() {
        return telefonoentidad;
    }

    public void setTelefonoentidad(String telefonoentidad) {
        this.telefonoentidad = telefonoentidad;
    }

    public String getDireccionentidad() {
        return direccionentidad;
    }

    public void setDireccionentidad(String direccionentidad) {
        this.direccionentidad = direccionentidad;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public SectorEntidad getSector() {
        return sector;
    }

    public void setSector(SectorEntidad sector) {
        this.sector = sector;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identidadexterna != null ? identidadexterna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntidadExterna)) {
            return false;
        }
        EntidadExterna other = (EntidadExterna) object;
        if ((this.identidadexterna == null && other.identidadexterna != null) || (this.identidadexterna != null && !this.identidadexterna.equals(other.identidadexterna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.EntidadExterna[ identidadexterna=" + identidadexterna + " ]";
    }

}
