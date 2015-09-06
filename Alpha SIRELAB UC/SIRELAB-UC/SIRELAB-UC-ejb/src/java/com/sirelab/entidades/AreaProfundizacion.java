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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "areaprofundizacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AreaProfundizacion.findAll", query = "SELECT a FROM AreaProfundizacion a"),
    @NamedQuery(name = "AreaProfundizacion.findByIdareaprofundizacion", query = "SELECT a FROM AreaProfundizacion a WHERE a.idareaprofundizacion = :idareaprofundizacion"),
    @NamedQuery(name = "AreaProfundizacion.findByNombrearea", query = "SELECT a FROM AreaProfundizacion a WHERE a.nombrearea = :nombrearea"),
    @NamedQuery(name = "AreaProfundizacion.findByCodigoarea", query = "SELECT a FROM AreaProfundizacion a WHERE a.codigoarea = :codigoarea")})
public class AreaProfundizacion implements Serializable {

    @Column(name = "estado")
    private Boolean estado;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idareaprofundizacion")
    private BigInteger idareaprofundizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombrearea")
    private String nombrearea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigoarea")
    private String codigoarea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "areaprofundizacion")
    private Collection<LaboratoriosPorAreas> laboratoriosPorAreasCollection;
    @Transient
    private String strEstado;
    @Transient
    private String strNombreEstado;

    public AreaProfundizacion() {
    }

    public AreaProfundizacion(BigInteger idareaprofundizacion) {
        this.idareaprofundizacion = idareaprofundizacion;
    }

    public AreaProfundizacion(BigInteger idareaprofundizacion, String nombrearea, String codigoarea) {
        this.idareaprofundizacion = idareaprofundizacion;
        this.nombrearea = nombrearea;
        this.codigoarea = codigoarea;
    }

    public BigInteger getIdareaprofundizacion() {
        return idareaprofundizacion;
    }

    public void setIdareaprofundizacion(BigInteger idareaprofundizacion) {
        this.idareaprofundizacion = idareaprofundizacion;
    }

    public String getNombrearea() {
        return nombrearea.toUpperCase();
    }

    public void setNombrearea(String nombrearea) {
        this.nombrearea = nombrearea.toUpperCase();
    }

    public String getCodigoarea() {
        return codigoarea;
    }

    public void setCodigoarea(String codigoarea) {
        this.codigoarea = codigoarea;
    }

    @XmlTransient
    public Collection<LaboratoriosPorAreas> getLaboratoriosPorAreasCollection() {
        return laboratoriosPorAreasCollection;
    }

    public void setLaboratoriosPorAreasCollection(Collection<LaboratoriosPorAreas> laboratoriosPorAreasCollection) {
        this.laboratoriosPorAreasCollection = laboratoriosPorAreasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idareaprofundizacion != null ? idareaprofundizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaProfundizacion)) {
            return false;
        }
        AreaProfundizacion other = (AreaProfundizacion) object;
        if ((this.idareaprofundizacion == null && other.idareaprofundizacion != null) || (this.idareaprofundizacion != null && !this.idareaprofundizacion.equals(other.idareaprofundizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.AreaProfundizacion[ idareaprofundizacion=" + idareaprofundizacion + " ]";
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getStrEstado() {
        getEstado();
        if (estado != null) {
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

    public String getStrNombreEstado() {
        getNombrearea();
        getStrEstado();
        strNombreEstado = nombrearea + " - " + strEstado;
        return strNombreEstado;
    }

    public void setStrNombreEstado(String strNombreEstado) {
        this.strNombreEstado = strNombreEstado;
    }

}
