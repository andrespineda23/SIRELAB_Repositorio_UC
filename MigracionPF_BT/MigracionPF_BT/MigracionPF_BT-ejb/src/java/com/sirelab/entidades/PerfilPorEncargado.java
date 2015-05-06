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
 * @author AndresPineda
 */
@Entity
@Table(name = "perfilporencargado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilPorEncargado.findAll", query = "SELECT p FROM PerfilPorEncargado p"),
    @NamedQuery(name = "PerfilPorEncargado.findByIdperfilporencargado", query = "SELECT p FROM PerfilPorEncargado p WHERE p.idperfilporencargado = :idperfilporencargado"),
    @NamedQuery(name = "PerfilPorEncargado.findByIndicetabla", query = "SELECT p FROM PerfilPorEncargado p WHERE p.indicetabla = :indicetabla")})
public class PerfilPorEncargado implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilporencargado")
    private Collection<EncargadoLaboratorio> encargadoLaboratorioCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idperfilporencargado")
    private BigInteger idperfilporencargado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "indicetabla")
    private BigInteger indicetabla;
    @JoinColumn(name = "tipoperfil", referencedColumnName = "idtipoperfil")
    @ManyToOne(optional = false)
    private TipoPerfil tipoperfil;
    @Transient
    private String nombreRegistro;
    @Transient
    private String informacionPerfil;

    public PerfilPorEncargado() {
    }

    public PerfilPorEncargado(BigInteger idperfilporencargado) {
        this.idperfilporencargado = idperfilporencargado;
    }

    public PerfilPorEncargado(BigInteger idperfilporencargado, BigInteger indicetabla) {
        this.idperfilporencargado = idperfilporencargado;
        this.indicetabla = indicetabla;
    }

    public BigInteger getIdperfilporencargado() {
        return idperfilporencargado;
    }

    public void setIdperfilporencargado(BigInteger idperfilporencargado) {
        this.idperfilporencargado = idperfilporencargado;
    }

    public BigInteger getIndicetabla() {
        return indicetabla;
    }

    public void setIndicetabla(BigInteger indicetabla) {
        this.indicetabla = indicetabla;
    }

    public TipoPerfil getTipoperfil() {
        return tipoperfil;
    }

    public void setTipoperfil(TipoPerfil tipoperfil) {
        this.tipoperfil = tipoperfil;
    }

    public String getNombreRegistro() {
        return nombreRegistro;
    }

    public void setNombreRegistro(String nombreRegistro) {
        this.nombreRegistro = nombreRegistro;
    }

    public String getInformacionPerfil() {
        getNombreRegistro();
        getTipoperfil();
        if (null != nombreRegistro && null != tipoperfil) {
            informacionPerfil = nombreRegistro + " - " + tipoperfil.getNombre();
        } else {
            informacionPerfil = "";
        }
        return informacionPerfil;
    }

    public void setInformacionPerfil(String informacionPerfil) {
        this.informacionPerfil = informacionPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idperfilporencargado != null ? idperfilporencargado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilPorEncargado)) {
            return false;
        }
        PerfilPorEncargado other = (PerfilPorEncargado) object;
        if ((this.idperfilporencargado == null && other.idperfilporencargado != null) || (this.idperfilporencargado != null && !this.idperfilporencargado.equals(other.idperfilporencargado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.PerfilPorEncargado[ idperfilporencargado=" + idperfilporencargado + " ]";
    }

    @XmlTransient
    public Collection<EncargadoLaboratorio> getEncargadoLaboratorioCollection() {
        return encargadoLaboratorioCollection;
    }

    public void setEncargadoLaboratorioCollection(Collection<EncargadoLaboratorio> encargadoLaboratorioCollection) {
        this.encargadoLaboratorioCollection = encargadoLaboratorioCollection;
    }

}
