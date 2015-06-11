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
@Table(name = "tipoperfil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPerfil.findAll", query = "SELECT t FROM TipoPerfil t"),
    @NamedQuery(name = "TipoPerfil.findByIdtipoperfil", query = "SELECT t FROM TipoPerfil t WHERE t.idtipoperfil = :idtipoperfil"),
    @NamedQuery(name = "TipoPerfil.findByNombre", query = "SELECT t FROM TipoPerfil t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoPerfil.findByCodigo", query = "SELECT t FROM TipoPerfil t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "TipoPerfil.findByNombreregistro", query = "SELECT t FROM TipoPerfil t WHERE t.nombreregistro = :nombreregistro"),
    @NamedQuery(name = "TipoPerfil.findByCodigoregistro", query = "SELECT t FROM TipoPerfil t WHERE t.codigoregistro = :codigoregistro")})
public class TipoPerfil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoperfil")
    private BigInteger idtipoperfil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombreregistro")
    private String nombreregistro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigoregistro")
    private String codigoregistro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoperfil")
    private Collection<EncargadoLaboratorio> encargadoLaboratorioCollection;
    @Transient
    private String informacionPerfil;

    public TipoPerfil() {
    }

    public TipoPerfil(BigInteger idtipoperfil) {
        this.idtipoperfil = idtipoperfil;
    }

    public TipoPerfil(BigInteger idtipoperfil, String nombre, String codigo, String nombreregistro, String codigoregistro) {
        this.idtipoperfil = idtipoperfil;
        this.nombre = nombre;
        this.codigo = codigo;
        this.nombreregistro = nombreregistro;
        this.codigoregistro = codigoregistro;
    }

    public String getInformacionPerfil() {
        getNombre();
        getNombreregistro();
        if (null != nombre && null != nombreregistro) {
            informacionPerfil = nombre + " - " + nombreregistro;
        } else {
            informacionPerfil = "";
        }
        return informacionPerfil;
    }

    public void setInformacionPerfil(String informacionPerfil) {
        this.informacionPerfil = informacionPerfil;
    }

    public BigInteger getIdtipoperfil() {
        return idtipoperfil;
    }

    public void setIdtipoperfil(BigInteger idtipoperfil) {
        this.idtipoperfil = idtipoperfil;
    }

    public String getNombre() {
        return nombre.toUpperCase();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getCodigo() {
        return codigo.toUpperCase();
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo.toUpperCase();
    }

    public String getNombreregistro() {
        return nombreregistro.toUpperCase();
    }

    public void setNombreregistro(String nombreregistro) {
        this.nombreregistro = nombreregistro.toUpperCase();
    }

    public String getCodigoregistro() {
        return codigoregistro.toUpperCase();
    }

    public void setCodigoregistro(String codigoregistro) {
        this.codigoregistro = codigoregistro.toUpperCase();
    }

    @XmlTransient
    public Collection<EncargadoLaboratorio> getEncargadoLaboratorioCollection() {
        return encargadoLaboratorioCollection;
    }

    public void setEncargadoLaboratorioCollection(Collection<EncargadoLaboratorio> encargadoLaboratorioCollection) {
        this.encargadoLaboratorioCollection = encargadoLaboratorioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoperfil != null ? idtipoperfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPerfil)) {
            return false;
        }
        TipoPerfil other = (TipoPerfil) object;
        if ((this.idtipoperfil == null && other.idtipoperfil != null) || (this.idtipoperfil != null && !this.idtipoperfil.equals(other.idtipoperfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.TipoPerfil[ idtipoperfil=" + idtipoperfil + " ]";
    }

}
