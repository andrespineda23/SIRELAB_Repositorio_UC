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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ELECTRONICA
 */
@Entity
@Table(name = "convenioporentidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConvenioPorEntidad.findAll", query = "SELECT c FROM ConvenioPorEntidad c"),
    @NamedQuery(name = "ConvenioPorEntidad.findByIdconvenioporentidad", query = "SELECT c FROM ConvenioPorEntidad c WHERE c.idconvenioporentidad = :idconvenioporentidad"),
    @NamedQuery(name = "ConvenioPorEntidad.findByEstado", query = "SELECT c FROM ConvenioPorEntidad c WHERE c.estado = :estado")})
public class ConvenioPorEntidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconvenioporentidad")
    private BigInteger idconvenioporentidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convenioporentidad")
    private Collection<PersonaContacto> personaContactoCollection;
    @JoinColumn(name = "entidadexterna", referencedColumnName = "identidadexterna")
    @ManyToOne(optional = false)
    private EntidadExterna entidadexterna;
    @JoinColumn(name = "convenio", referencedColumnName = "idconvenio")
    @ManyToOne(optional = false)
    private Convenio convenio;
    @Transient
    private String strEstado;

    public ConvenioPorEntidad() {
    }

    public ConvenioPorEntidad(BigInteger idconvenioporentidad) {
        this.idconvenioporentidad = idconvenioporentidad;
    }

    public ConvenioPorEntidad(BigInteger idconvenioporentidad, boolean estado) {
        this.idconvenioporentidad = idconvenioporentidad;
        this.estado = estado;
    }

    public BigInteger getIdconvenioporentidad() {
        return idconvenioporentidad;
    }

    public void setIdconvenioporentidad(BigInteger idconvenioporentidad) {
        this.idconvenioporentidad = idconvenioporentidad;
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

    @XmlTransient
    public Collection<PersonaContacto> getPersonaContactoCollection() {
        return personaContactoCollection;
    }

    public void setPersonaContactoCollection(Collection<PersonaContacto> personaContactoCollection) {
        this.personaContactoCollection = personaContactoCollection;
    }

    public EntidadExterna getEntidadexterna() {
        return entidadexterna;
    }

    public void setEntidadexterna(EntidadExterna entidadexterna) {
        this.entidadexterna = entidadexterna;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconvenioporentidad != null ? idconvenioporentidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConvenioPorEntidad)) {
            return false;
        }
        ConvenioPorEntidad other = (ConvenioPorEntidad) object;
        if ((this.idconvenioporentidad == null && other.idconvenioporentidad != null) || (this.idconvenioporentidad != null && !this.idconvenioporentidad.equals(other.idconvenioporentidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.ConvenioPorEntidad[ idconvenioporentidad=" + idconvenioporentidad + " ]";
    }

}
