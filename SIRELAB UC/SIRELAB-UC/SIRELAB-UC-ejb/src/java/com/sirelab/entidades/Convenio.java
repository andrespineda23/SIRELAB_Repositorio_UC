/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "convenio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Convenio.findAll", query = "SELECT c FROM Convenio c"),
    @NamedQuery(name = "Convenio.findByIdconvenio", query = "SELECT c FROM Convenio c WHERE c.idconvenio = :idconvenio"),
    @NamedQuery(name = "Convenio.findByFechainicial", query = "SELECT c FROM Convenio c WHERE c.fechainicial = :fechainicial"),
    @NamedQuery(name = "Convenio.findByFechafinal", query = "SELECT c FROM Convenio c WHERE c.fechafinal = :fechafinal"),
    @NamedQuery(name = "Convenio.findByDescripcion", query = "SELECT c FROM Convenio c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Convenio.findByValor", query = "SELECT c FROM Convenio c WHERE c.valor = :valor"),
    @NamedQuery(name = "Convenio.findByEstado", query = "SELECT c FROM Convenio c WHERE c.estado = :estado"),
    @NamedQuery(name = "Convenio.findByNombreconvenio", query = "SELECT c FROM Convenio c WHERE c.nombreconvenio = :nombreconvenio")})
public class Convenio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconvenio")
    private BigInteger idconvenio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechainicial")
    @Temporal(TemporalType.DATE)
    private Date fechainicial;
    @Column(name = "fechafinal")
    @Temporal(TemporalType.DATE)
    private Date fechafinal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private int valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreconvenio")
    private String nombreconvenio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convenio")
    private Collection<ConvenioPorEntidad> convenioPorEntidadCollection;
    @Transient
    private String strEstado;

    public Convenio() {
    }

    public Convenio(BigInteger idconvenio) {
        this.idconvenio = idconvenio;
    }

    public Convenio(BigInteger idconvenio, Date fechainicial, String descripcion, int valor, boolean estado, String nombreconvenio) {
        this.idconvenio = idconvenio;
        this.fechainicial = fechainicial;
        this.descripcion = descripcion;
        this.valor = valor;
        this.estado = estado;
        this.nombreconvenio = nombreconvenio;
    }

    public BigInteger getIdconvenio() {
        return idconvenio;
    }

    public void setIdconvenio(BigInteger idconvenio) {
        this.idconvenio = idconvenio;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public String getDescripcion() {
        if (null != descripcion) {
            return descripcion.toUpperCase();
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion.toUpperCase();
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

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

    public String getNombreconvenio() {
        if (null != nombreconvenio) {
            return nombreconvenio.toUpperCase();
        }
        return nombreconvenio;
    }

    public void setNombreconvenio(String nombreconvenio) {
        this.nombreconvenio = nombreconvenio;
    }

    @XmlTransient
    public Collection<ConvenioPorEntidad> getConvenioPorEntidadCollection() {
        return convenioPorEntidadCollection;
    }

    public void setConvenioPorEntidadCollection(Collection<ConvenioPorEntidad> convenioPorEntidadCollection) {
        this.convenioPorEntidadCollection = convenioPorEntidadCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconvenio != null ? idconvenio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Convenio)) {
            return false;
        }
        Convenio other = (Convenio) object;
        if ((this.idconvenio == null && other.idconvenio != null) || (this.idconvenio != null && !this.idconvenio.equals(other.idconvenio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.Convenio[ idconvenio=" + idconvenio + " ]";
    }

}
