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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author AndresPineda
 */
@Entity
@Table(name = "salalaboratorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SalaLaboratorio.findAll", query = "SELECT s FROM SalaLaboratorio s"),
    @NamedQuery(name = "SalaLaboratorio.findByIdsalalaboratorio", query = "SELECT s FROM SalaLaboratorio s WHERE s.idsalalaboratorio = :idsalalaboratorio"),
    @NamedQuery(name = "SalaLaboratorio.findByCodigosala", query = "SELECT s FROM SalaLaboratorio s WHERE s.codigosala = :codigosala"),
    @NamedQuery(name = "SalaLaboratorio.findByNombresala", query = "SELECT s FROM SalaLaboratorio s WHERE s.nombresala = :nombresala"),
    @NamedQuery(name = "SalaLaboratorio.findByPisoubicacion", query = "SELECT s FROM SalaLaboratorio s WHERE s.pisoubicacion = :pisoubicacion"),
    @NamedQuery(name = "SalaLaboratorio.findByDescripcionsala", query = "SELECT s FROM SalaLaboratorio s WHERE s.descripcionsala = :descripcionsala"),
    @NamedQuery(name = "SalaLaboratorio.findByCostoalquiler", query = "SELECT s FROM SalaLaboratorio s WHERE s.costoalquiler = :costoalquiler"),
    @NamedQuery(name = "SalaLaboratorio.findByEstadosala", query = "SELECT s FROM SalaLaboratorio s WHERE s.estadosala = :estadosala"),
    @NamedQuery(name = "SalaLaboratorio.findByCapacidadsala", query = "SELECT s FROM SalaLaboratorio s WHERE s.capacidadsala = :capacidadsala"),
    @NamedQuery(name = "SalaLaboratorio.findByValorinversion", query = "SELECT s FROM SalaLaboratorio s WHERE s.valorinversion = :valorinversion")})
public class SalaLaboratorio implements Serializable {

    @Column(name = "salaprivada")
    private Boolean salaprivada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salalaboratorio")
    private Collection<ReservaSala> reservaSalaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salalaboratorio")
    private Collection<ModuloLaboratorio> moduloLaboratorioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsalalaboratorio")
    private BigInteger idsalalaboratorio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigosala")
    private String codigosala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombresala")
    private String nombresala;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pisoubicacion")
    private String pisoubicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "descripcionsala")
    private String descripcionsala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costoalquiler")
    private long costoalquiler;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estadosala")
    private boolean estadosala;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacidadsala")
    private int capacidadsala;
    @Column(name = "valorinversion")
    private BigInteger valorinversion;
    @JoinColumn(name = "laboratoriosporareas", referencedColumnName = "idlaboratoriosporareas")
    @ManyToOne(optional = false)
    private LaboratoriosPorAreas laboratoriosporareas;
    @JoinColumn(name = "edificio", referencedColumnName = "idedificio")
    @ManyToOne(optional = false)
    private Edificio edificio;
    @Transient
    private String strEstado;
    @Transient
    private String strPrivada;
    @Transient
    private String strNombreEstado;

    public SalaLaboratorio() {
    }

    public SalaLaboratorio(BigInteger idsalalaboratorio) {
        this.idsalalaboratorio = idsalalaboratorio;
    }

    public SalaLaboratorio(BigInteger idsalalaboratorio, String codigosala, String nombresala, String pisoubicacion, String descripcionsala, long costoalquiler, boolean estadosala, int capacidadsala) {
        this.idsalalaboratorio = idsalalaboratorio;
        this.codigosala = codigosala;
        this.nombresala = nombresala;
        this.pisoubicacion = pisoubicacion;
        this.descripcionsala = descripcionsala;
        this.costoalquiler = costoalquiler;
        this.estadosala = estadosala;
        this.capacidadsala = capacidadsala;
    }

    public BigInteger getIdsalalaboratorio() {
        return idsalalaboratorio;
    }

    public void setIdsalalaboratorio(BigInteger idsalalaboratorio) {
        this.idsalalaboratorio = idsalalaboratorio;
    }

    public String getCodigosala() {
        return codigosala;
    }

    public void setCodigosala(String codigosala) {
        this.codigosala = codigosala;
    }

    public String getNombresala() {
        return nombresala.toUpperCase();
    }

    public void setNombresala(String nombresala) {
        this.nombresala = nombresala.toUpperCase();
    }

    public String getPisoubicacion() {
        return pisoubicacion.toUpperCase();
    }

    public void setPisoubicacion(String pisoubicacion) {
        this.pisoubicacion = pisoubicacion.toUpperCase();
    }

    public String getDescripcionsala() {
        return descripcionsala.toUpperCase();
    }

    public void setDescripcionsala(String descripcionsala) {
        this.descripcionsala = descripcionsala.toUpperCase();
    }

    public long getCostoalquiler() {
        return costoalquiler;
    }

    public void setCostoalquiler(long costoalquiler) {
        this.costoalquiler = costoalquiler;
    }

    public boolean getEstadosala() {
        return estadosala;
    }

    public void setEstadosala(boolean estadosala) {
        this.estadosala = estadosala;
    }

    public String getStrEstado() {
        getEstadosala();
        if (estadosala == true) {
            strEstado = "ACTIVO";
        } else {
            strEstado = "INACTIVO";
        }
        return strEstado;
    }

    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }

    public int getCapacidadsala() {
        return capacidadsala;
    }

    public void setCapacidadsala(int capacidadsala) {
        this.capacidadsala = capacidadsala;
    }

    public BigInteger getValorinversion() {
        return valorinversion;
    }

    public void setValorinversion(BigInteger valorinversion) {
        this.valorinversion = valorinversion;
    }

    public LaboratoriosPorAreas getLaboratoriosporareas() {
        return laboratoriosporareas;
    }

    public void setLaboratoriosporareas(LaboratoriosPorAreas laboratoriosporareas) {
        this.laboratoriosporareas = laboratoriosporareas;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsalalaboratorio != null ? idsalalaboratorio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalaLaboratorio)) {
            return false;
        }
        SalaLaboratorio other = (SalaLaboratorio) object;
        if ((this.idsalalaboratorio == null && other.idsalalaboratorio != null) || (this.idsalalaboratorio != null && !this.idsalalaboratorio.equals(other.idsalalaboratorio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sirelab.entidades.SalaLaboratorio[ idsalalaboratorio=" + idsalalaboratorio + " ]";
    }

    @XmlTransient
    public Collection<ModuloLaboratorio> getModuloLaboratorioCollection() {
        return moduloLaboratorioCollection;
    }

    public void setModuloLaboratorioCollection(Collection<ModuloLaboratorio> moduloLaboratorioCollection) {
        this.moduloLaboratorioCollection = moduloLaboratorioCollection;
    }

    @XmlTransient
    public Collection<ReservaSala> getReservaSalaCollection() {
        return reservaSalaCollection;
    }

    public void setReservaSalaCollection(Collection<ReservaSala> reservaSalaCollection) {
        this.reservaSalaCollection = reservaSalaCollection;
    }

    public Boolean getSalaprivada() {
        return salaprivada;
    }

    public void setSalaprivada(Boolean salaprivada) {
        this.salaprivada = salaprivada;
    }

    public String getStrPrivada() {
        getSalaprivada();
        if (null != salaprivada) {
            if (salaprivada == true) {
                strPrivada = "PRIVADA";
            } else {
                strPrivada = "PUBLICA";
            }
        }
        return strPrivada;
    }

    public void setStrPrivada(String strPrivada) {
        this.strPrivada = strPrivada;
    }

    public String getStrNombreEstado() {
        getStrEstado();
        getNombresala();
        strNombreEstado = nombresala + " - " + strEstado;
        return strNombreEstado;
    }

    public void setStrNombreEstado(String strNombreEstado) {
        this.strNombreEstado = strNombreEstado;
    }

}
